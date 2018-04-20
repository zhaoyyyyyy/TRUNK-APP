package com.asiainfo.biapp.si.loc.core.label.model.sub;

import com.asiainfo.biapp.si.loc.base.common.CommonConstants;
import com.asiainfo.biapp.si.loc.base.utils.DataBaseAdapter;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.ServiceConstants;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelRule;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTableColumn;
import com.asiainfo.biapp.si.loc.core.label.model.LabelElement;

/**
 * 枚举型标签
 * @author luyan3
 * @version ZJ
 */
public class EnumLabel extends LabelElement {

	@Override
	public String getConditionSql(LabelRule ciLabelRule,
			MdaSysTableColumn column, String asName, Integer interval,
			Integer updateCycle, boolean isValidate) {
		StringBuffer wherelabel = new StringBuffer("");
		int flag = -1;
		if (ciLabelRule.getLabelFlag() != null) {
			flag = ciLabelRule.getLabelFlag();
		}
		DataBaseAdapter dataBaseAdapter = new DataBaseAdapter();//TODO 数据库适配
		if (StringUtil.isNotEmpty(ciLabelRule.getTableName())) {
			String valueTabName = ciLabelRule.getTableName();
			String columnName = CommonConstants.LABEL_EXACT_VALUE_TABLE_COLUMN;
			//TODO 查询枚举值
			String tableSql = dataBaseAdapter.getEnumFromTabel(valueTabName, columnName, column.getColumnDataTypeId());
			if (ServiceConstants.LABEL_RULE_FLAG_NO == flag) {
				if (ServiceConstants.MdaSysTableColumn.COLUMN_DATA_TYPE_ID_NUM == column.getColumnDataTypeId()) {
					asName = dataBaseAdapter.getColumnToChar(asName);
				}
				wherelabel.append(" ").append(asName).append(" not in (").append(tableSql).append(")");
			} else if (ServiceConstants.LABEL_RULE_FLAG_YES == flag) {
				if (ServiceConstants.MdaSysTableColumn.COLUMN_DATA_TYPE_ID_NUM == column.getColumnDataTypeId()) {
					 asName = dataBaseAdapter.getColumnToChar(asName);
				}
				wherelabel.append(" ").append(asName).append("  in (").append(tableSql).append(")");
			}
		} else {
			String attrValsStr = ciLabelRule.getAttrVal();
			String[] vals = attrValsStr.split(",");
			if (ServiceConstants.LABEL_RULE_FLAG_NO == flag) {
				if (vals.length == 1 || isValidate) {
					if (ServiceConstants.MdaSysTableColumn.COLUMN_DATA_TYPE_ID_VARCHAR == column.getColumnDataTypeId()) {
						wherelabel.append(" ").append(asName).append(" ")
						.append(CommonConstants.NE).append(" '").append(vals[0]).append("'");
					} else if (ServiceConstants.MdaSysTableColumn.COLUMN_DATA_TYPE_ID_NUM == column.getColumnDataTypeId()) {
						wherelabel.append(" ").append(asName).append(" ")
						.append(CommonConstants.NE).append(" ").append(vals[0]);
					}
				} else {
					if (attrValsStr.length() == attrValsStr.lastIndexOf(",")) {
						attrValsStr = attrValsStr.substring(0, attrValsStr.length() - 1);
					}
					if (ServiceConstants.MdaSysTableColumn.COLUMN_DATA_TYPE_ID_VARCHAR == column.getColumnDataTypeId()) {
						wherelabel.append(" ").append(asName).append(" not in ( ");
						for (int k = 0; k < vals.length; k++) {
							wherelabel.append("'").append(vals[k]).append("'");
							if (k != vals.length - 1) {
								wherelabel.append(",");
							}
						}
						wherelabel.append(" )");
					} else if (ServiceConstants.MdaSysTableColumn.COLUMN_DATA_TYPE_ID_NUM == column.getColumnDataTypeId()) {
						wherelabel.append(" ").append(asName).append(" not in (").append(attrValsStr).append(")");
					}
				}
			} else if (ServiceConstants.LABEL_RULE_FLAG_YES == flag) {
				if (vals.length == 1 || isValidate) {
					if (ServiceConstants.MdaSysTableColumn.COLUMN_DATA_TYPE_ID_VARCHAR == column.getColumnDataTypeId()) {
						wherelabel.append(" ").append(asName).append(" = '").append(vals[0]).append("'");
					} else if (ServiceConstants.MdaSysTableColumn.COLUMN_DATA_TYPE_ID_NUM == column.getColumnDataTypeId()) {
						wherelabel.append(" ").append(asName).append(" = ").append(vals[0]);
					}
				} else {
					if (attrValsStr.length() == attrValsStr.lastIndexOf(",")) {
						attrValsStr = attrValsStr.substring(0, attrValsStr.length() - 1);
					}
					if (ServiceConstants.MdaSysTableColumn.COLUMN_DATA_TYPE_ID_VARCHAR == column.getColumnDataTypeId()) {
						wherelabel.append(" ").append(asName).append(" in ( ");
						for (int k = 0; k < vals.length; k++) {
							wherelabel.append("'").append(vals[k]).append("'");
							if (k != vals.length - 1) {
								wherelabel.append(",");
							}
						}
						wherelabel.append(" )");
					} else if (ServiceConstants.MdaSysTableColumn.COLUMN_DATA_TYPE_ID_NUM == column.getColumnDataTypeId()) {
						wherelabel.append(" ").append(asName).append(" in (").append(attrValsStr).append(")");
					}
				}
			}
		}
		return wherelabel.toString();
	}

}
