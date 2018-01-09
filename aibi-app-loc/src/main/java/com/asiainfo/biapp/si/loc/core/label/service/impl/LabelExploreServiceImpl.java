
package com.asiainfo.biapp.si.loc.core.label.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.common.LabelInfoContants;
import com.asiainfo.biapp.si.loc.base.common.LabelRuleContants;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.cache.CocCacheProxy;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelVerticalColumnRel;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTable;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTableColumn;
import com.asiainfo.biapp.si.loc.core.label.model.ExploreQueryParam;
import com.asiainfo.biapp.si.loc.core.label.model.LabelElementFactory;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelExploreService;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelRuleVo;

@Service
@Transactional
public class LabelExploreServiceImpl implements ILabelExploreService {


	@Override
	public String getFromSqlForMultiLabel(List<LabelRuleVo> labelRuleList, ExploreQueryParam queryParam) throws BaseException {
		LabelElementFactory fac = new LabelElementFactory();
		StringBuffer wherelabel = new StringBuffer(" (");
		/***/
		for (LabelRuleVo rule : labelRuleList) {
			if (LabelRuleContants.ELEMENT_TYPE_LABEL_ID == rule.getElementType()) {
				String labelIdStr = rule.getCalcuElement();
				LabelInfo labelInfo = CocCacheProxy.getCacheProxy().getLabelInfoById(labelIdStr);
				if (labelInfo.getLabelTypeId() == LabelInfoContants.LABEL_TYPE_VERT) {
					String verticalLabelSql = this.getVerticalLabelSql(rule, queryParam);
					
					
				}else{
					MdaSysTableColumn column = labelInfo.getMdaSysTableColumn();
					MdaSysTable table = column.getMdaSysTable();
					String alias = "t_" + table.getTableId(); // 表的别名:t_tabelId
					/** 1、获取where标签 sql条件	 */
					String asName = alias + "." + column.getColumnName(); // 格式为：“别名.字段名”
					fac.setLabelElement(labelInfo.getLabelTypeId());
					String labelConditionSql = fac.getLabelElement().getConditionSql(rule, column, asName,
							queryParam.getInterval(), queryParam.getUpdateCycle(), queryParam.isValidate());
					wherelabel.append(labelConditionSql);
				}
			} else if (LabelRuleContants.ELEMENT_TYPE_LIST_ID == rule.getElementType()) {
				//TODO 客户群清单
				
			}
			
		}//end for

		
		
		
		return null;
	}

	
	@Override
	public String getVerticalLabelSql(LabelRuleVo verticalLabelRule, ExploreQueryParam queryParam)
			throws BaseException {
		//解析不同标签工厂实例
	    LabelElementFactory fac = new LabelElementFactory();
		// 获得纵表标签
		LabelInfo labelInfo = CocCacheProxy.getCacheProxy().getLabelInfoById(verticalLabelRule.getCalcuElement());
		// 纵表标签列关系
		Map<String, LabelVerticalColumnRel> map = new HashMap<String, LabelVerticalColumnRel>();
		Set<LabelVerticalColumnRel> verticalColumnRels = labelInfo.getVerticalColumnRels();
		for (LabelVerticalColumnRel rel : verticalColumnRels) {
			String key = rel.getColumnId();
			map.put(key, rel);
		}
		// 根据纵表标签获得其子规则
		List<LabelRuleVo> labelRuleList = verticalLabelRule.getChildLabelRuleList();
		String dayTableName = "";
		// 拼接where条件语句
		StringBuffer wherelabel = new StringBuffer(" (");
		for (int i = 0; i < labelRuleList.size(); i++) {
			if (i != 0) {
				wherelabel.append(" ").append("and");
			}
			LabelRuleVo ciLabelRule = labelRuleList.get(i);
			int elementType = ciLabelRule.getElementType();
			if (elementType == LabelRuleContants.ELEMENT_TYPE_LABEL_ID) {
				LabelVerticalColumnRel rel = map.get(ciLabelRule.getCalcuElement());
				MdaSysTableColumn column = rel.getMdaSysTableColumn();
				MdaSysTable table = column.getMdaSysTable();
				String tableName = table.getTableName();
				String alias = "t_" + table.getTableId(); // 表的别名:tabelName_tabelId
				/** 1、日表、月表表名提取拼接   纵表对应只有一个元数据表 */
				if(StringUtil.isEmpty(dayTableName)){
					tableName = getTableName(queryParam, labelInfo, tableName);
				    dayTableName = tableName.toUpperCase() + " " + alias;//只有一个元数据表（DW_COC_LABEL_USER_002_201712 t_2）	
				}
				/** 2、获取where标签 sql条件*/
				String asName = alias + "." + column.getColumnName(); // 格式为：“别名.字段名”
				fac.setLabelElement(rel.getLabelTypeId());
				String labelConditionSql = fac.getLabelElement().getConditionSql(ciLabelRule, column, asName,
						queryParam.getInterval(), queryParam.getUpdateCycle(), queryParam.isValidate());
				wherelabel.append(labelConditionSql);
			} 
		}
		wherelabel.append(")");
		StringBuffer whereSb = new StringBuffer("where 1=1 and ");
		whereSb.append(wherelabel);
		StringBuffer fromSqlSb = new StringBuffer("");
		fromSqlSb.append(" from ").append(dayTableName).append(" ").append(whereSb.toString());;
		return fromSqlSb.toString();
	}

	@Override
	public String getCountSqlStr(List<LabelRuleVo> ciLabelRuleList, ExploreQueryParam queryParam) throws BaseException {
		// 判断标签探索中是否只包含and和括号
		boolean andFlag = true;
		// 解析不同标签工厂实例
		LabelElementFactory fac = new LabelElementFactory();
		// 表名，别名 map( key:宽表名 value：表别名)
		Map<String, String> tableAliasMap = new LinkedHashMap<String, String>();
		// 别名，要查询的字段集合 map (key:表别名 value：要查询的字段集合)
		Map<String, Set<String>> aliasColumnMap = new HashMap<String, Set<String>>();
		StringBuffer wherelabel = new StringBuffer(" (");
		for (int i = 0; i < ciLabelRuleList.size(); i++) {
			LabelRuleVo ciLabelRule = ciLabelRuleList.get(i);
			int elementType = ciLabelRule.getElementType();
			if (elementType == LabelRuleContants.ELEMENT_TYPE_LABEL_ID) {
				String labelIdStr = ciLabelRule.getCalcuElement();
				LabelInfo ciLabelInfo = CocCacheProxy.getCacheProxy().getLabelInfoById(labelIdStr);
				MdaSysTableColumn column = ciLabelInfo.getMdaSysTableColumn();
				MdaSysTable table = column.getMdaSysTable();
				String tableName = table.getTableName();
				String alias = "t_" + table.getTableId(); // 表的别名:t_tabelId
				/** 1、别名、字段集合 map处理 	*/
				Set<String> columnSet = new HashSet<String>();
				if (aliasColumnMap.get(alias) != null) {
					columnSet = aliasColumnMap.get(alias);
				}
				columnSet.add(column.getColumnName().toUpperCase());
				aliasColumnMap.put(alias, columnSet);
				/** 2、日表、月表表名提取拼接 map处理 	*/
				tableName = getTableName(queryParam, ciLabelInfo, tableName);
				tableAliasMap.put(tableName.toUpperCase(), alias);
				/** 3、获取where标签 sql条件	 */
				String asName = alias + "." + column.getColumnName(); // 格式为：“别名.字段名”
				fac.setLabelElement(ciLabelInfo.getLabelTypeId());
				String labelConditionSql = fac.getLabelElement().getConditionSql(ciLabelRule, column, asName,
						queryParam.getInterval(), queryParam.getUpdateCycle(), queryParam.isValidate());
				wherelabel.append(labelConditionSql);
			} else {
				wherelabel.append(" ").append(ciLabelRule.getCalcuElement());
				if (!ciLabelRule.getCalcuElement().equals("and") && !ciLabelRule.getCalcuElement().equals("(")
						&& !ciLabelRule.getCalcuElement().equals(")")) {
					andFlag = false;
				}
			}
		} // end for
		wherelabel.append(")");
		// 省专区，地市专区需要权限,普通专区不需要权限、 拼接where中的cityId，用于权限
		boolean isNeedAuthen = false;// TODO
		StringBuffer whereSb = new StringBuffer("where 1=1 and ");
		whereSb.append(wherelabel);
		String leftJoinSqlStr = this.getLeftJoinSqlStr(tableAliasMap, aliasColumnMap, andFlag, whereSb, isNeedAuthen);
		StringBuffer fromSqlSb = new StringBuffer("");
		fromSqlSb.append(" from ").append(leftJoinSqlStr).append(" ");
		return fromSqlSb.toString();

	}

	/**
	 * 根据前缀、周期、日期拼接表名
	 */
	private String getTableName(ExploreQueryParam queryParam, LabelInfo ciLabelInfo, String tableName) {
		// 日表、月表表名提取
		if (LabelInfoContants.LABEL_CYCLE_TYPE_D == ciLabelInfo.getUpdateCycle()) {
			String dayDate = queryParam.getDayDate();
			// TODO log.warn("使用日标签时，日期为空");
			String tablePostfix = "_" + dayDate;
			tableName += tablePostfix;
		} else if (LabelInfoContants.LABEL_CYCLE_TYPE_M == ciLabelInfo.getUpdateCycle()) {
			String monthDate = queryParam.getMonthDate();
			String tablePostfix = "_" + monthDate;
			tableName += tablePostfix;
		}
		return tableName;
	}

	/**
	 * 标签探索拼接left join语句，当标签连接全为and时，sql格式为 (select a.product_no,a.L_1 from
	 * table1)t_0, (select b.product_no,b.L_2 from table2 b)t_0 where ...
	 * 当标签连接包含or时，sql 格式为 (select a.product_no,a.L1) t_0 left join (select
	 * b.product_no,b.L2) t_1 on t_0.product_no=t_1.product_no
	 * 
	 * @param tableAlias
	 *            表名，别名 map
	 * @param aliasColumn
	 *            日表 别名字段map
	 * @param andFlag
	 *            是否只包含and
	 * @param whereSb
	 *            where条件
	 * @throws Exception
	 * @version ZJ
	 */
	private String getLeftJoinSqlStr(Map<String, String> tableAlias, Map<String, Set<String>> aliasColumn,
			boolean andFlag, StringBuffer whereSb, boolean isNeedAuthen) throws BaseException {
		if (tableAlias == null || tableAlias.size() == 0) {
			throw new RuntimeException("没有需要的表");
		}
		StringBuffer joinSb = new StringBuffer("");
		String relatedColumn = "PRODUCT_NO";// TODO RELATED_COLUMN
		int i = 0;
		String firstAlias = "";
		for (String table : tableAlias.keySet()) {
			String alias = tableAlias.get(table);
			Set<String> columnSet = aliasColumn.get(alias);
			String sql = " (select " + relatedColumn;
			if (isNeedAuthen == true) {
				String cityColumn = "CITY_COLUMN";
				String countyColumn = "COUNTY_COLUMN";
				sql = sql + "," + cityColumn + "," + countyColumn;
			}
			for (String str : columnSet) {
				sql = sql + "," + str;
			}
			sql = sql + " from " + table + " ) " + alias + " ";
			if (i == 0) {
				firstAlias = alias;
				joinSb.append(sql);
			} else {
				if (andFlag == true) {
					joinSb.append(",").append(sql);
					whereSb.append(" and ").append(firstAlias).append(".").append(relatedColumn).append("=")
							.append(alias).append(".").append(relatedColumn);
				} else {
					joinSb.append(" left join ").append(sql).append(" on ").append(firstAlias).append(".")
							.append(relatedColumn).append("=").append(alias).append(".").append(relatedColumn)
							.append(" ");
				}
			}
			i++;
		} // end for
		if (StringUtil.isNotEmpty(whereSb.toString())) {
			joinSb.append(whereSb.toString());
		}
		return joinSb.toString();
	}
}
