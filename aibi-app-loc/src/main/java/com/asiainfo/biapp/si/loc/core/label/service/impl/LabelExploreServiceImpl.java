
package com.asiainfo.biapp.si.loc.core.label.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.auth.model.Organization;
import com.asiainfo.biapp.si.loc.auth.service.IOrganizationService;
import com.asiainfo.biapp.si.loc.base.common.CommonConstants;
import com.asiainfo.biapp.si.loc.base.common.LabelInfoContants;
import com.asiainfo.biapp.si.loc.base.common.LabelRuleContants;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.SqlRunException;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.bd.common.service.IBackSqlService;
import com.asiainfo.biapp.si.loc.cache.CocCacheProxy;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelVerticalColumnRel;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTable;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTableColumn;
import com.asiainfo.biapp.si.loc.core.label.model.ExploreQueryParam;
import com.asiainfo.biapp.si.loc.core.label.model.LabelElementFactory;
import com.asiainfo.biapp.si.loc.core.label.service.IGroupCalcSqlPaser;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelExploreService;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelInfoService;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelRuleService;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelRuleVo;

@Service
@Transactional
public class LabelExploreServiceImpl implements ILabelExploreService {

	@Resource(name="sparkSqlPaser")
	private IGroupCalcSqlPaser sqlPaser;
	
	@Autowired
	private ILabelInfoService labelInfoService;
	
	@Autowired
	private ILabelRuleService ruleService;
	
	@Autowired
	private IBackSqlService backServiceImpl;
	
	@Autowired
	private IOrganizationService organizationService; 
	
	@Override
	public String getFromSqlForMultiLabel(List<LabelRuleVo> labelRuleList, ExploreQueryParam queryParam) throws BaseException {
		Map<String, String> labelRuleToSql = new HashMap<String, String>();
		List<String> listInfoIds = new ArrayList<String>();
		StringBuffer calcExpr = new StringBuffer();
		Integer duplicateLabelIdCount = 1;
		/***/
		for (LabelRuleVo rule : labelRuleList) {
			if (LabelRuleContants.ELEMENT_TYPE_OPERATOR==rule.getElementType()) {
				if (LabelRuleContants.CALCULATE_ELEMENT_TYPE_OPT_OR
						.equalsIgnoreCase(rule.getCalcuElement())) {
					calcExpr.append(CommonConstants.UNION);// 并
				} else if (LabelRuleContants.CALCULATE_ELEMENT_TYPE_OPT_AND
						.equalsIgnoreCase(rule.getCalcuElement())) {
					calcExpr.append(CommonConstants.INTERSECT);// 交
				} else if (LabelRuleContants.CALCULATE_ELEMENT_TYPE_OPT_EXCEPT
						.equalsIgnoreCase(rule.getCalcuElement())) {
					calcExpr.append(CommonConstants.EXCEPT);// 差
				}
			} else if (LabelRuleContants.ELEMENT_TYPE_BRACKET==rule.getElementType()) {
				calcExpr.append(rule.getCalcuElement());// 括号
			} else if (LabelRuleContants.ELEMENT_TYPE_LABEL_ID == rule.getElementType()) {
				String labelIdStr = rule.getCalcuElement();
				LabelInfo labelInfo = CocCacheProxy.getCacheProxy().getLabelInfoById(labelIdStr);
				String singleLabelSql = "";
				if (labelInfo.getLabelTypeId() == LabelInfoContants.LABEL_TYPE_VERT) {
					singleLabelSql = this.getVerticalLabelSql(rule, queryParam);
				}else{
					List<LabelRuleVo> singleCiLabelRuleList = new ArrayList<LabelRuleVo>();
					singleCiLabelRuleList.add(rule);
					singleLabelSql = this.getCountSqlStr(singleCiLabelRuleList, queryParam);
				}
				String trimSql = singleLabelSql.trim();
				if (trimSql.startsWith("from")) {
					singleLabelSql = trimSql.substring(4);
				}
				if (labelRuleToSql.keySet().contains(rule.getCalcuElement())) {
					listInfoIds.add(rule.getCalcuElement() + "_"+ duplicateLabelIdCount);
					labelRuleToSql.put(rule.getCalcuElement() + "_"+ duplicateLabelIdCount, singleLabelSql);
					calcExpr.append(rule.getCalcuElement() + "_"+ duplicateLabelIdCount);
					duplicateLabelIdCount++;
				} else {
					listInfoIds.add(rule.getCalcuElement());
					labelRuleToSql.put(rule.getCalcuElement(),singleLabelSql);
					calcExpr.append(rule.getCalcuElement());
				}
			} else if (LabelRuleContants.ELEMENT_TYPE_LIST_ID == rule.getElementType()) {
				String listTableId = rule.getCalcuElement();
				String singleLabelSql = this.getListTableSql(listTableId,rule.getAttrVal());
				listInfoIds.add(listTableId);
				labelRuleToSql.put(listTableId,singleLabelSql);
				calcExpr.append(listTableId);
			}//end ELEMENT_TYPE
			
		}//end for
		String sql = "";
		if (listInfoIds.size() == 1) {
			String calcExprStr = calcExpr.toString();
			calcExprStr = calcExprStr.replace(String.valueOf(CommonConstants.LEFT_Q), "")
					.replace(String.valueOf(CommonConstants.RIGHT_Q), "");
			if (labelRuleToSql.containsKey(calcExprStr)) {
				sql = "select " +LabelInfoContants.KHQ_CROSS_COLUMN+ " from " + labelRuleToSql.get(calcExprStr);
			} else {
				sql = "select " +LabelInfoContants.KHQ_CROSS_COLUMN+ " from " + calcExprStr ;
			}
		} else if (listInfoIds.size() > 1) {
			sql =sqlPaser.parseExprToSql(calcExpr.toString(), labelRuleToSql);
		}
		return sql;
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
			String key = rel.getLabelVerticalColumnRelId().getColumnId();
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
						ciLabelRule.getIsNeedOffset(), labelInfo.getUpdateCycle(), queryParam.isValidate());
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
	public String getCountSqlStr(List<LabelRuleVo> originalList, ExploreQueryParam queryParam) throws BaseException {
		List<LabelRuleVo> ciLabelRuleList=ruleService.getNewLabelRuleVoList(originalList);
		// 判断标签探索中是否只包含and和括号
		boolean andFlag = true;
		// 解析不同标签工厂实例
		LabelElementFactory fac = new LabelElementFactory();
		// 表名，别名 map( key:宽表名 value：表别名)
		Map<String, String> tableAliasMap = new LinkedHashMap<String, String>();
		// 别名，要查询的字段集合 map (key:表别名 value：要查询的字段集合)
		Map<String, Set<String>> aliasColumnMap = new HashMap<String, Set<String>>();
		// 保存客户群使用：保存表名及别名20180306
		Set<String> tableNameSet = new LinkedHashSet<String>();
		
		String dataTabelAlias = null;//表的别名（拼接where中的cityId，用于权限）
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
				if (StringUtil.isEmpty(dataTabelAlias)) {
					dataTabelAlias=alias;
				}
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
				tableNameSet.add(tableName.toUpperCase() + " " + alias);
				/** 3、获取where标签 sql条件	 */
				String asName = alias + "." + column.getColumnName(); // 格式为：“别名.字段名”
				fac.setLabelElement(ciLabelInfo.getLabelTypeId());
				String labelConditionSql = fac.getLabelElement().getConditionSql(ciLabelRule, column, asName,
						ciLabelRule.getIsNeedOffset(), ciLabelInfo.getUpdateCycle(), queryParam.isValidate());
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
		boolean isNeedAuthen = false;
		StringBuffer whereSb = new StringBuffer();//where 整体条件
		if(queryParam.isValidate()){
			whereSb.append("where 1=2 and ");
		}else{
			whereSb.append("where 1=1 and ");
		}
		String cityColumn = getWhereForCity(queryParam, dataTabelAlias, whereSb);
		whereSb.append(wherelabel);
		if (StringUtil.isNotEmpty(cityColumn)){
			isNeedAuthen = true;
		}
		// 拼接客户群插入语句的select语句输出的字段 20180306
		if (queryParam.isCreateCustom()) {
			String sqlStr = getLeftJoinSqlStr(tableNameSet);
			StringBuffer selectSql = new StringBuffer("");
			String columnName = LabelInfoContants.KHQ_CROSS_COLUMN;
			selectSql.append("select " + dataTabelAlias + "." + columnName);
			selectSql.append(" from ").append(sqlStr).append(whereSb);
			return selectSql.toString();
		}
		String leftJoinSqlStr = this.getLeftJoinSqlStr(tableAliasMap, aliasColumnMap, andFlag, whereSb, isNeedAuthen,cityColumn);
		StringBuffer fromSqlSb = new StringBuffer("");
		fromSqlSb.append(" from ").append(leftJoinSqlStr).append(" ");
		return fromSqlSb.toString();

	}

	/**
	 * 
	 * Description: 拼接权限字段sql
	 *
	 * @param queryParam   查询参数
	 * @param dataTabelAlias	表的别名
	 * @param whereSb	where之后的权限条件
	 * @return
	 *
	 * @author  tianxy3
	 * @throws BaseException 
	 * @date 2018年2月5日
	 */
	private String getWhereForCity(ExploreQueryParam queryParam, String dataTabelAlias, StringBuffer whereSb) throws BaseException {
		StringBuffer cityColumn = new StringBuffer();
		if (StringUtil.isNotEmpty(dataTabelAlias) && StringUtil.isNotEmpty(queryParam.getOrgId())) {
			/** key:orgCode; //组织编码（重要）  value:level;  //组织级别（重要）*/
			Map<String, Organization> map = organizationService.selectAllOrganization();
			Map<String,String> citySqlMap=new HashMap<>();
			String orgId = queryParam.getOrgId();
			String[] split = orgId.split(",");
			for (int i = 0; i < split.length; i++) {
				String orgCode = split[i];
				Organization organization = map.get(orgCode);
				String levelId ="org_level_"+organization.getLevelId();
				if(citySqlMap.get(levelId)!=null){
					citySqlMap.put(levelId,citySqlMap.get(levelId)+ "'"+orgCode+"',");
				}else{
					citySqlMap.put(levelId, "'"+orgCode+"',");
				}
			}
			cityColumn.append(StringUtils.join(citySqlMap.keySet().toArray(),","));
			for (String levelId : citySqlMap.keySet()) {
				String citySql = citySqlMap.get(levelId);
				whereSb.append(" (").append(dataTabelAlias).append(".").append(levelId).append(" in ")
				.append("(").append(citySql.substring(0, citySql.length()-1)).append(")) and ");
			}
		}
		return cityColumn.toString();
	}

	/**
	 * 根据前缀、周期、日期拼接表名
	 */
	private String getTableName(ExploreQueryParam queryParam, LabelInfo ciLabelInfo, String tableName) {
		// 日表、月表表名提取
		if (LabelInfoContants.LABEL_CYCLE_TYPE_D == ciLabelInfo.getUpdateCycle()) {
			String dayDate = queryParam.getDayDate();
			tableName += dayDate;
		} else if (LabelInfoContants.LABEL_CYCLE_TYPE_M == ciLabelInfo.getUpdateCycle()) {
			String monthDate = queryParam.getMonthDate();
			tableName += monthDate;
		}
		try {
			tableName = backServiceImpl.getCurBackDbSchema()+"."+tableName;
		} catch (SqlRunException e) {
			LogUtil.error(e);
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
			boolean andFlag, StringBuffer whereSb, boolean isNeedAuthen, String cityColumn) throws BaseException {
		if (tableAlias == null || tableAlias.size() == 0) {
			throw new RuntimeException("没有需要的表");
		}
		StringBuffer joinSb = new StringBuffer("");
		String relatedColumn = LabelInfoContants.KHQ_CROSS_COLUMN;
		int i = 0;
		String firstAlias = "";
		for (String table : tableAlias.keySet()) {
			String alias = tableAlias.get(table);
			Set<String> columnSet = aliasColumn.get(alias);
			String sql = " (select " + relatedColumn;
			if (isNeedAuthen == true) {
				sql = sql + "," + cityColumn;
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

	/**
	 * 拼接left join语句
	 * @param tableNameSet
	 * @param dayTableName 日表
	 * @param vertAttrTableNameMap
	 * @return
	 * @throws Exception
	 * @version ZJ
	 */
	private String getLeftJoinSqlStr(Set<String> tableNameSet){
		if (tableNameSet == null || tableNameSet.size() == 0) {
			throw new RuntimeException("没有需要的表");
		}
		StringBuffer joinSb = new StringBuffer("");
		String relatedColumn = LabelInfoContants.KHQ_CROSS_COLUMN;
		int i = 0;
		String lastAlias = "";
		for (String tableNames : tableNameSet) {
			String[] tableName = tableNames.split(" ");
			if (tableName.length < 2) {
				throw new RuntimeException("没有别名");
			}
			if (i == 0) {
				joinSb.append(tableNames).append(" ");
			} else {
				joinSb.append("left join ").append(tableNames).append(" on ")
						.append(lastAlias).append(".").append(relatedColumn).append(" = ")
						.append(tableName[1]).append(".").append(relatedColumn).append(" ");
			}
			lastAlias = tableName[1];
			i++;
		}

		return joinSb.toString();
	}

	@Override
	public String getListTableSql(String customId, String dataDate) throws BaseException {
		LabelInfo customGroup = labelInfoService.get(customId);
		if(customGroup == null){
			throw new RuntimeException("客户群不存在！---customId:"+customId);
		}
		String tableName = "no table";
		if (LabelInfoContants.CUSTOM_CYCLE_TYPE_ONE == customGroup.getUpdateCycle()) {
			tableName = LabelInfoContants.KHQ_CROSS_ONCE_TABLE + customGroup.getConfigId() + "_"+ dataDate;
		} else {
			tableName = LabelInfoContants.KHQ_CROSS_TABLE + customGroup.getConfigId() + "_"+ dataDate;
		}
		String singleLabelSql = backServiceImpl.getCurBackDbSchema()+"."+tableName+" where "+LabelInfoContants.KHQ_CROSS_ID_PARTION+" = '"+customId+"' ";
		return singleLabelSql;
	}
}
