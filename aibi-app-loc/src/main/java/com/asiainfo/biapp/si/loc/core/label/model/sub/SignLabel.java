package com.asiainfo.biapp.si.loc.core.label.model.sub;

import com.asiainfo.biapp.si.loc.base.common.LabelRuleContants;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelRule;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTableColumn;
import com.asiainfo.biapp.si.loc.core.label.model.LabelElement;

/**
 * 标识型标签
 * @author luyan3
 * @version ZJ
 */
public class SignLabel extends LabelElement {

	@Override
	public String getConditionSql(LabelRule ciLabelRule,
			MdaSysTableColumn column, String asName, Integer interval,
			Integer updateCycle, boolean isValidate) {
		StringBuffer wherelabel = new StringBuffer("");
		int flag = -1;
		if (ciLabelRule.getLabelFlag() != null) {
			flag = ciLabelRule.getLabelFlag();
		}
		if (LabelRuleContants.LABEL_RULE_FLAG_NO == flag) {
			wherelabel.append(" ").append(asName).append(" = ")
					.append(ciLabelRule.getMinVal().intValue());
		} else if (LabelRuleContants.LABEL_RULE_FLAG_YES == flag) {
			wherelabel.append(" ").append(asName).append(" = ")
					.append(ciLabelRule.getMaxVal().intValue());
		}
		return wherelabel.toString();
	}

}
