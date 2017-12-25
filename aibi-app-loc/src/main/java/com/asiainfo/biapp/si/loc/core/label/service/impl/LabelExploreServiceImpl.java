
package com.asiainfo.biapp.si.loc.core.label.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.common.LabelInfoContants;
import com.asiainfo.biapp.si.loc.base.common.LabelRuleContants;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTable;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTableColumn;
import com.asiainfo.biapp.si.loc.core.label.model.ExploreQueryParam;
import com.asiainfo.biapp.si.loc.core.label.model.LabelElementFactory;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelExploreService;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelInfoService;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelRuleVo;

@Service
@Transactional
public class LabelExploreServiceImpl implements ILabelExploreService {

	@Autowired
	private ILabelInfoService labelInfoService;

	@Override
	public String getCountSqlStr(List<LabelRuleVo> ciLabelRuleList, ExploreQueryParam queryParam) throws BaseException {

		// 判断标签探索中是否只包含and和括号
		boolean andFlag = true;
		// 解析不同标签工厂实例
		LabelElementFactory fac = new LabelElementFactory();
		// 表名，别名 map
		Map<String, String> tableAlias = new LinkedHashMap<String, String>();
		// 别名，要查询的字段集合 map
		Map<String, Set<String>> aliasColumn = new HashMap<String, Set<String>>();
		StringBuffer fromSqlSb = new StringBuffer("");
		StringBuffer wherelabel = new StringBuffer(" (");
		StringBuffer whereSb = new StringBuffer("where 1=1 and ");
		
		for (int i = 0; i < ciLabelRuleList.size(); i++) {
			LabelRuleVo ciLabelRule = ciLabelRuleList.get(i);
			int elementType = ciLabelRule.getElementType();
			if (elementType == LabelRuleContants.ELEMENT_TYPE_LABEL_ID) {
				String labelIdStr = ciLabelRule.getCalcuElement();
				//TODO从缓存中取出    LabelInfo ciLabelInfo =cache.getEffectiveLabel(labelIdStr);
				LabelInfo ciLabelInfo = labelInfoService.selectLabelInfoById(labelIdStr);
				MdaSysTableColumn column = ciLabelInfo.getMdaSysTableColumn();
				MdaSysTable table = column.getMdaSysTable();
				String tableName = table.getTableName();
				String alias = "t_" + table.getTableId(); // 表的别名:t_tabelId
				String asName = alias + "." + column.getColumnName(); // 格式为：“别名.字段名”
				Set<String> columnSet = null;
				if (null != aliasColumn && aliasColumn.get(alias) != null) {
					columnSet = aliasColumn.get(alias);
					columnSet.add(column.getColumnName().toUpperCase());
					aliasColumn.put(alias, columnSet);
				} else {
					columnSet = new HashSet<String>();
					columnSet.add(column.getColumnName().toUpperCase());
					aliasColumn.put(alias, columnSet);
				}
				// 日表、月表表名提取拼接
				tableName = getTableName(queryParam, ciLabelInfo, tableName);
				tableAlias.put(tableName.toUpperCase(), alias);
				// 获取sql条件
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
		}

		wherelabel.append(")");
		// 省专区，地市专区需要权限,普通专区不需要权限
		// 拼接where中的cityId，用于权限
		whereSb.append(wherelabel);
		boolean isNeedAuthen = false;// TODO
		String leftJoinSqlStr = this.getLeftJoinSqlStr(tableAlias, aliasColumn, andFlag, whereSb, isNeedAuthen);
		fromSqlSb.append(" from ").append(leftJoinSqlStr).append(" ");
		return fromSqlSb.toString();

	}

	private String getTableName(ExploreQueryParam queryParam, LabelInfo ciLabelInfo, String tableName) {
		// 日表、月表表名提取
		if (LabelInfoContants.LABEL_CYCLE_TYPE_D == ciLabelInfo.getUpdateCycle()) {
			String dayDate = queryParam.getDayDate();
			// if (StringUtil.isEmpty(dayDate)) {
			// dayDate = CacheBase.getInstance().getNewLabelDay();
			// //TODO log.warn("使用日标签时，日期为空");
			// }
			String tablePostfix = "_" + dayDate;
			tableName += tablePostfix;
			// String dayTableName = tableName.toUpperCase() + " " +
			// alias;
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
		String relatedColumn = "RELATED_COLUMN";// TODO

		Iterator<Entry<String, String>> it = tableAlias.entrySet().iterator();
		int i = 0;
		String firstAlias = "";

		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String table = entry.getKey();
			String alias = entry.getValue();
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
		}
		if (StringUtil.isNotEmpty(whereSb.toString())) {
			joinSb.append(whereSb.toString());
		}
		return joinSb.toString();
	}

}
