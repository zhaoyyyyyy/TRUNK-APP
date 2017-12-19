package com.asiainfo.biapp.si.loc.core.label.model.sub;

import com.asiainfo.biapp.si.loc.base.common.CommonConstants;
import com.asiainfo.biapp.si.loc.base.common.LabelRuleContants;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelRule;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTableColumn;
import com.asiainfo.biapp.si.loc.core.label.model.LabelElement;

/**
 * 指标型标签
 * @author luyan3
 * @version ZJ
 */
public class KpiLabel extends LabelElement {

	@Override
	public String getConditionSql(LabelRule ciLabelRule,
				MdaSysTableColumn column, String asName, Integer interval,
			Integer updateCycle, boolean isValidate) {
		StringBuffer wherelabel = new StringBuffer("");
		int flag = -1;
		if (ciLabelRule.getLabelFlag() != null) {
			flag = ciLabelRule.getLabelFlag();
		}
		String minVal = ciLabelRule.getContiueMinVal();
		String maxVal = ciLabelRule.getContiueMaxVal();
		String leftZoneSign = ciLabelRule.getLeftZoneSign();
		String rightZoneSign = ciLabelRule.getRightZoneSign();
		if (LabelRuleContants.LABEL_RULE_FLAG_NO == flag) {
			if (StringUtil.isNotEmpty(minVal) && StringUtil.isEmpty(maxVal)) {
				if (leftZoneSign.equals(CommonConstants.GE)) {
					wherelabel.append(" ").append(asName).append(" ")
					.append(CommonConstants.LT).append(" ").append(minVal);
				} else if (leftZoneSign.equals(CommonConstants.GT)) {
					wherelabel.append(" ").append(asName).append(" ")
					.append(CommonConstants.LE).append(" ").append(minVal);
				}
			} else if (StringUtil.isNotEmpty(maxVal) && StringUtil.isEmpty(minVal)) {
				if (rightZoneSign.equals(CommonConstants.LE)) {
					wherelabel.append(" ").append(asName).append(" ")
					.append(CommonConstants.GT).append(" ").append(maxVal);
				} else if (rightZoneSign.equals(CommonConstants.LT)) {
					wherelabel.append(" ").append(asName).append(" ")
					.append(CommonConstants.GE).append(" ").append(maxVal);
				}
			} else if (StringUtil.isNotEmpty(minVal) && StringUtil.isNotEmpty(maxVal)) {
				if (leftZoneSign.equals(CommonConstants.GE)) {
					wherelabel.append(" ( ").append(asName).append(" ")
					.append(CommonConstants.LT).append(" ").append(minVal);
				} else if (leftZoneSign.equals(CommonConstants.GT)) {
					wherelabel.append(" ( ").append(asName).append(" ")
					.append(CommonConstants.LE).append(" ").append(minVal);
				}
				wherelabel.append(" or");
				if (rightZoneSign.equals(CommonConstants.LE)) {
					wherelabel.append(" ").append(asName).append(" ")
					.append(CommonConstants.GT).append(" ").append(maxVal).append(" )");
				} else if (rightZoneSign.equals(CommonConstants.LT)) {
					wherelabel.append(" ").append(asName).append(" ").append(CommonConstants.GE)
					.append(" ").append(maxVal).append(" )");
				}
			} else if (StringUtil.isEmpty(minVal) && StringUtil.isEmpty(maxVal)) {
				String exactValsStr = ciLabelRule.getExactValue();
				String[] vals = exactValsStr.split(",");
				if (vals.length == 1) {
					wherelabel.append(" ").append(asName).append(" ")
					.append(CommonConstants.NE).append(" ").append(vals[0]);
				} else {
					wherelabel.append(" (");
					for (int j = 0; j < vals.length; j++) {
						wherelabel.append(" ").append(asName).append(" ")
						.append(CommonConstants.NE).append(" ").append(vals[j]);
						if (j != vals.length - 1) {
							wherelabel.append(" and");
						}
					}
					wherelabel.append(" )");
				}
			}
		} else if (LabelRuleContants.LABEL_RULE_FLAG_YES == flag) {
			if (StringUtil.isNotEmpty(minVal) && StringUtil.isEmpty(maxVal)) {
				wherelabel.append(" ").append(asName).append(" ")
				.append(leftZoneSign).append(" ").append(minVal);
			} else if (StringUtil.isNotEmpty(maxVal) && StringUtil.isEmpty(minVal)) {
				wherelabel.append(" ").append(asName).append(" ")
				.append(rightZoneSign).append(" ").append(maxVal);
			} else if (StringUtil.isNotEmpty(minVal) && StringUtil.isNotEmpty(maxVal)) {
				wherelabel.append(" ( ").append(asName).append(" ")
				.append(leftZoneSign).append(" ").append(minVal);
				wherelabel.append(" and");
				wherelabel.append(" ").append(asName).append(" ").append(rightZoneSign)
				.append(" ").append(maxVal).append(" )");
			} else if (StringUtil.isEmpty(minVal) && StringUtil.isEmpty(maxVal)) {
				String exactValsStr = ciLabelRule.getExactValue();
				String[] vals = exactValsStr.split(",");
				if (vals.length == 1) {
					wherelabel.append(" ").append(asName).append(" = ").append(vals[0]);
				} else {
					if (exactValsStr.length() == exactValsStr.lastIndexOf(",")) {
						exactValsStr = exactValsStr.substring(0, exactValsStr.length() - 1);
					}
					wherelabel.append(" ").append(asName).append(" in (").append(exactValsStr).append(")");
				}
			}
		}
		return wherelabel.toString();
	}

}
