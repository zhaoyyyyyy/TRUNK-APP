package com.asiainfo.biapp.si.loc.core.label.model.sub;

import com.asiainfo.biapp.si.loc.base.common.CommonConstants;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.ServiceConstants;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelRule;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTableColumn;
import com.asiainfo.biapp.si.loc.core.label.model.LabelElement;

/**
 * 文本型标签
 * 
 * @author luyan3
 * @version ZJ
 */
public class TextLabel extends LabelElement {

	@Override
	public String getConditionSql(LabelRule ciLabelRule, MdaSysTableColumn column, String asName, Integer interval,
			Integer updateCycle, boolean isValidate) {
		StringBuffer wherelabel = new StringBuffer("");
		int flag = -1;
		if (ciLabelRule.getLabelFlag() != null) {
			flag = ciLabelRule.getLabelFlag();
		}
		String exactValsStr = ciLabelRule.getExactValue();
		if (StringUtil.isEmpty(exactValsStr)) {
			if (ServiceConstants.LABEL_RULE_FLAG_NO == flag) {
				wherelabel.append(" ").append(asName).append(" not like '%").append(ciLabelRule.getDarkValue())
						.append("%'");
			} else if (ServiceConstants.LABEL_RULE_FLAG_YES == flag) {
				wherelabel.append(" ").append(asName).append(" like '%").append(ciLabelRule.getDarkValue())
						.append("%'");
			}
		} else {
			String[] vals = exactValsStr.split(",");
			if (ServiceConstants.LABEL_RULE_FLAG_YES == flag) {
				if (vals.length == 1 || isValidate) {
					wherelabel.append(" ").append(asName).append(" = '").append(vals[0]).append("'");
				} else {
					wherelabel.append(" ").append(asName).append(" in (");
					for (int j = 0; j < vals.length; j++) {
						wherelabel.append("'").append(vals[j]).append("'");
						if (j != vals.length - 1) {
							wherelabel.append(",");
						}
					}
					wherelabel.append(")");
				}
			} else if (ServiceConstants.LABEL_RULE_FLAG_NO == flag) {
				if (vals.length == 1 || isValidate) {
					wherelabel.append(" ").append(asName).append(" ").append(CommonConstants.NE).append(" '")
							.append(vals[0]).append("'");
				} else {
					wherelabel.append(" ").append(asName).append(" not in (");
					for (int j = 0; j < vals.length; j++) {
						wherelabel.append("'").append(vals[j]).append("'");
						if (j != vals.length - 1) {
							wherelabel.append(",");
						}
					}
					wherelabel.append(")");
				}
			}
		}
		return wherelabel.toString();
	}

}
