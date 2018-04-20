package com.asiainfo.biapp.si.loc.core.label.model.sub;

import java.util.Date;

import org.apache.log4j.Logger;

import com.asiainfo.biapp.si.loc.base.common.CommonConstants;
import com.asiainfo.biapp.si.loc.base.utils.DataBaseAdapter;
import com.asiainfo.biapp.si.loc.base.utils.DateUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.ServiceConstants;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelRule;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTableColumn;
import com.asiainfo.biapp.si.loc.core.label.model.LabelElement;

/**
 * 日期型标签
 * @author luyan3
 * @version ZJ
 */
public class DateLabel extends LabelElement {

	private Logger log = Logger.getLogger(DateLabel.class);
	
	@Override
	public String getConditionSql(LabelRule ciLabelRule,
			MdaSysTableColumn column, String asName, Integer interval,
			Integer updateCycle, boolean isValidate) {
		StringBuffer wherelabel = new StringBuffer("");
		int flag = -1;
		if (ciLabelRule.getLabelFlag() != null) {
			flag = ciLabelRule.getLabelFlag();
		}
		String nullInput = "-1";
		// 日期根据是否动态更新，更新条件
		String leftZoneSign = ciLabelRule.getLeftZoneSign();
		String rightZoneSign = ciLabelRule.getRightZoneSign();
		String startTime = ciLabelRule.getStartTime();
		String endTime = ciLabelRule.getEndTime();
		Integer isNeedOffsetObj = ciLabelRule.getIsNeedOffset();
		int isNeedOffset = 0;
		if (null != isNeedOffsetObj) {
			isNeedOffset = ciLabelRule.getIsNeedOffset();
		} 
		//TDDO 日期格式，从配置文件里面获得：1.字符串形式:yyyyMMdd  2.日期格式：yyyy-MM-dd
        String timeFormat = DateUtil.FORMAT_YYYY_MM_DD;
		String dateFormat = DateUtil.FORMAT_YYYY_MM_DD;
		if (DateUtil.FORMAT_YYYYMMDD.equals(timeFormat)) {
			
			dateFormat = DateUtil.FORMAT_YYYYMMDD;
			String dateStrFormat = DateUtil.FORMAT_YYYY_MM_DD;
			String afterFormat = DateUtil.FORMAT_YYYYMMDD;
			startTime = DateUtil.string2StringFormat(startTime, dateStrFormat, afterFormat);
			endTime = DateUtil.string2StringFormat(endTime, dateStrFormat, afterFormat);
		}
        
        if (StringUtil.isNotEmpty(startTime)) {
            if (ServiceConstants.IS_NEED_OFFSET_YES == isNeedOffset && interval != 0) {
                startTime = DateUtil.calculateOffsetDate(startTime, interval, dateFormat,
                    updateCycle);
            } else {
                Date startDate = DateUtil.string2Date(startTime, dateFormat);
                startTime = DateUtil.date2String(startDate, dateFormat);
            }
            if (leftZoneSign.equals(CommonConstants.GE)) {
            	if (DateUtil.FORMAT_YYYY_MM_DD.equals(timeFormat)){
            		startTime = startTime + " 00:00:00";
            	}
            } else if (leftZoneSign.equals(CommonConstants.GT)) {
            	if (DateUtil.FORMAT_YYYY_MM_DD.equals(timeFormat)){
            		startTime = startTime + " 23:59:59";
            	}
            }
        }

        if (StringUtil.isNotEmpty(endTime)) {
            if (ServiceConstants.IS_NEED_OFFSET_YES == isNeedOffset && interval != 0) {
                endTime = DateUtil.calculateOffsetDate(endTime, interval, dateFormat, updateCycle);
            } else {
                Date endDate = DateUtil.string2Date(endTime, dateFormat);
                endTime = DateUtil.date2String(endDate, dateFormat);
            }
            if (rightZoneSign.equals(CommonConstants.LE)) {
            	if (DateUtil.FORMAT_YYYY_MM_DD.equals(timeFormat)){
            		endTime = endTime + " 23:59:59";
            	}
            } else if (rightZoneSign.equals(CommonConstants.LT)) {
            	if (DateUtil.FORMAT_YYYY_MM_DD.equals(timeFormat)){
            		endTime = endTime + " 00:00:00";
            	}
            }
        }
		if (ServiceConstants.LABEL_RULE_FLAG_NO == flag) {
			if (StringUtil.isNotEmpty(startTime)
					&& StringUtil.isEmpty(endTime)) {
				if (leftZoneSign.equals(CommonConstants.GE)) {
					wherelabel.append(" ").append(asName).append(" ")
					.append(CommonConstants.LT).append(" '").append(startTime).append("'");
				} else if (leftZoneSign.equals(CommonConstants.GT)) {
					wherelabel.append(" ").append(asName).append(" ")
					.append(CommonConstants.LE).append(" '").append(startTime).append("'");
				}
			} else if (StringUtil.isNotEmpty(endTime)
					&& StringUtil.isEmpty(startTime)) {
				if (rightZoneSign.equals(CommonConstants.LE)) {
					wherelabel.append(" ").append(asName).append(" ")
					.append(CommonConstants.GT).append(" '").append(endTime).append("'");
				} else if (rightZoneSign.equals(CommonConstants.LT)) {
					wherelabel.append(" ").append(asName).append(" ")
					.append(CommonConstants.GE).append(" '").append(endTime).append("'");
				}
			} else if (StringUtil.isNotEmpty(startTime)
					&& StringUtil.isNotEmpty(endTime)) {
				if (leftZoneSign.equals(CommonConstants.GE)) {
					wherelabel.append(" ( ").append(asName).append(" ")
					.append(CommonConstants.LT).append(" '").append(startTime).append("'");
				} else if (leftZoneSign.equals(CommonConstants.GT)) {
					wherelabel.append(" ( ").append(asName).append(" ")
					.append(CommonConstants.LE).append(" '").append(startTime).append("'");
				}
				wherelabel.append(" or");
				if (rightZoneSign.equals(CommonConstants.LE)) {
					wherelabel.append(" ").append(asName).append(" ")
					.append(CommonConstants.GT).append(" '").append(endTime).append("' )");
				} else if (rightZoneSign.equals(CommonConstants.LT)) {
					wherelabel.append(" ").append(asName).append(" ")
					.append(CommonConstants.GE).append(" '").append(endTime).append("' )");
				}
			} else if (StringUtil.isEmpty(startTime)
					&& StringUtil.isEmpty(endTime) 
					&& StringUtil.isNotEmpty(ciLabelRule.getExactValue())) {
				String exactValsStr = ciLabelRule.getExactValue();
				String[] vals = exactValsStr.split(",");
				if (vals.length != 3) {
					log.error("精确日期传值错误");
					throw new RuntimeException("精确日期传值错误");
				}
				String year = vals[0];
				String month = vals[1];
				String day = vals[2];
				if (!month.equals(nullInput) && month.length() == 1) {
					month = "0" + month;
				}
				if (!day.equals(nullInput) && day.length() == 1) {
					day = "0" + day;
				}
				if (DateUtil.FORMAT_YYYYMMDD.equals(timeFormat)) {
					
					DataBaseAdapter dbAdapter = new DataBaseAdapter();
					
					if (year.equals(nullInput) && month.equals(nullInput) && !day.equals(nullInput)) {
						wherelabel.append(" ").append(dbAdapter.getSubString(asName, 7, 2)).append(" <> ").append(" '").append(day).append("'");
					} else if (year.equals(nullInput) && !month.equals(nullInput) && day.equals(nullInput)) {
						wherelabel.append(" ").append(dbAdapter.getSubString(asName, 5, 2)).append(" <> ").append(" '").append(month).append("'");
					} else if (!year.equals(nullInput) && month.equals(nullInput) && day.equals(nullInput)) {
						wherelabel.append(" ").append(dbAdapter.getSubString(asName, 1, 4)).append(" <> ").append("  '").append(year).append("'");
					} else if (!year.equals(nullInput) && !month.equals(nullInput) && day.equals(nullInput)) {
						wherelabel.append(" ").append(dbAdapter.getSubString(asName, 1, 6)).append(" <> ").append(" '").append(year).append(month).append("'");
					} else if (year.equals(nullInput) && !month.equals(nullInput) && !day.equals(nullInput)) {
						wherelabel.append(" ").append(dbAdapter.getSubString(asName, 5, 4)).append(" <> ").append(" '").append(month).append(day).append("'");
					} else if (!year.equals(nullInput) && month.equals(nullInput) && !day.equals(nullInput)) {
						wherelabel.append(" (").append(dbAdapter.getSubString(asName, 1, 4)).append(" <> ").append("  '").append(year).append("'")
						.append(" or ").append(dbAdapter.getSubString(asName, 7, 2)).append(" <> ").append("  '").append(day).append(" ')");
					} else if (!year.equals(nullInput) && !month.equals(nullInput) && !day.equals(nullInput)) {
						wherelabel.append(" ").append(dbAdapter.getSubString(asName, 1, 8)).append(" <> ").append(" '").append(year)
						.append(month).append(day).append("'");
					}

				}else{
					
					if (year.equals(nullInput) && month.equals(nullInput) && !day.equals(nullInput)) {
						wherelabel.append(" ").append(asName).append(" not like '%-").append(day).append(" %'");
					} else if (year.equals(nullInput) && !month.equals(nullInput) && day.equals(nullInput)) {
						wherelabel.append(" ").append(asName).append(" not like '%-").append(month).append("-%'");
					} else if (!year.equals(nullInput) && month.equals(nullInput) && day.equals(nullInput)) {
						wherelabel.append(" ").append(asName).append(" not like '").append(year).append("-%'");
					} else if (!year.equals(nullInput) && !month.equals(nullInput) && day.equals(nullInput)) {
						wherelabel.append(" ").append(asName).append(" not like '").append(year).append("-").append(month).append("-%'");
					} else if (year.equals(nullInput) && !month.equals(nullInput) && !day.equals(nullInput)) {
						wherelabel.append(" ").append(asName).append(" not like '%-").append(month).append("-").append(day).append("%'");
					} else if (!year.equals(nullInput) && month.equals(nullInput) && !day.equals(nullInput)) {
						wherelabel.append(" (").append(asName).append(" not like '").append(year).append("-%'")
						.append(" or ").append(asName).append(" not like '%-").append(day).append(" %')");
					} else if (!year.equals(nullInput) && !month.equals(nullInput) && !day.equals(nullInput)) {
						wherelabel.append(" ").append(asName).append(" not like '").append(year)
						.append("-").append(month).append("-").append(day).append("%'");
					}
				}
			}
		} else if (ServiceConstants.LABEL_RULE_FLAG_YES == flag) {
			if (StringUtil.isNotEmpty(startTime) && StringUtil.isEmpty(endTime)) {
				wherelabel.append(" ").append(asName).append(" ").append(leftZoneSign)
				.append(" '").append(startTime).append("'");
			} else if (StringUtil.isNotEmpty(endTime) && StringUtil.isEmpty(startTime)) {
				wherelabel.append(" ").append(asName).append(" ")
				.append(rightZoneSign).append(" '").append(endTime).append("'");
			} else if (StringUtil.isNotEmpty(startTime) && StringUtil.isNotEmpty(endTime)) {
				wherelabel.append(" ( ").append(asName).append(" ").append(leftZoneSign)
				.append(" '").append(startTime).append("'");
				wherelabel.append(" and");
				wherelabel.append(" ").append(asName).append(" ")
				.append(rightZoneSign).append(" '").append(endTime).append("' )");
			} else if (StringUtil.isEmpty(startTime) && StringUtil.isEmpty(endTime)
					&& StringUtil.isNotEmpty(ciLabelRule.getExactValue())) {
				String exactValsStr = ciLabelRule.getExactValue();
				String[] vals = exactValsStr.split(",");
				if (vals.length != 3) {
					log.error("精确日期传值错误");
					throw new RuntimeException("精确日期传值错误");
				}
				String year = vals[0];
				String month = vals[1];
				String day = vals[2];
				if (!month.equals(nullInput) && month.length() == 1) {
					month = "0" + month;
				}
				if (!day.equals(nullInput) && day.length() == 1) {
					day = "0" + day;
				}
				if (DateUtil.FORMAT_YYYYMMDD.equals(timeFormat)) {
					DataBaseAdapter dbAdapter = new DataBaseAdapter();
					if (year.equals(nullInput) && month.equals(nullInput) && !day.equals(nullInput)) {
						wherelabel.append(" ").append(dbAdapter.getSubString(asName, 7, 2)).append(" = ").append(" '").append(day).append("'");
					} else if (year.equals(nullInput) && !month.equals(nullInput) && day.equals(nullInput)) {
						wherelabel.append(" ").append(dbAdapter.getSubString(asName, 5, 2)).append(" = ").append(" '").append(month).append("'");
					} else if (!year.equals(nullInput) && month.equals(nullInput) && day.equals(nullInput)) {
						wherelabel.append(" ").append(dbAdapter.getSubString(asName, 1, 4)).append(" = ").append("  '").append(year).append("'");
					} else if (!year.equals(nullInput) && !month.equals(nullInput) && day.equals(nullInput)) {
						wherelabel.append(" ").append(dbAdapter.getSubString(asName, 1, 6)).append(" = ").append(" '").append(year).append(month).append("'");
					} else if (year.equals(nullInput) && !month.equals(nullInput) && !day.equals(nullInput)) {
						wherelabel.append(" ").append(dbAdapter.getSubString(asName, 5, 4)).append(" = ").append(" '").append(month).append(day).append("'");
					} else if (!year.equals(nullInput) && month.equals(nullInput) && !day.equals(nullInput)) {
						wherelabel.append(" (").append(dbAdapter.getSubString(asName, 1, 4)).append(" = ").append("  '").append(year).append("'")
						.append(" or ").append(dbAdapter.getSubString(asName, 7, 2)).append(" = ").append("  '").append(day).append(" ')");
					} else if (!year.equals(nullInput) && !month.equals(nullInput) && !day.equals(nullInput)) {
						wherelabel.append(" ").append(dbAdapter.getSubString(asName, 1, 8)).append(" = ").append(" '").append(year)
						.append(month).append(day).append("'");
					}
				}else{
					
					if (year.equals(nullInput) && month.equals(nullInput) && !day.equals(nullInput)) {
						wherelabel.append(" ").append(asName).append(" like '%-").append(day).append(" %'");
					} else if (year.equals(nullInput) && !month.equals(nullInput) && day.equals(nullInput)) {
						wherelabel.append(" ").append(asName).append(" like '%-").append(month).append("-%'");
					} else if (!year.equals(nullInput) && month.equals(nullInput) && day.equals(nullInput)) {
						wherelabel.append(" ").append(asName).append(" like '").append(year).append("-%'");
					} else if (!year.equals(nullInput) && !month.equals(nullInput) && day.equals(nullInput)) {
						wherelabel.append(" ").append(asName).append(" like '").append(year).append("-").append(month).append("-%'");
					} else if (year.equals(nullInput) && !month.equals(nullInput) && !day.equals(nullInput)) {
						wherelabel.append(" ").append(asName).append(" like '%-").append(month).append("-").append(day).append("%'");
					} else if (!year.equals(nullInput) && month.equals(nullInput) && !day.equals(nullInput)) {
						wherelabel.append(" (").append(asName).append(" like '").append(year).append("-%'")
						.append(" and ").append(asName).append(" like '%-").append(day).append(" %')");
					} else if (!year.equals(nullInput) && !month.equals(nullInput) && !day.equals(nullInput)) {
						wherelabel.append(" ").append(asName).append(" like '").append(year).append("-")
						.append(month).append("-").append(day).append("%'");
					}
				}
			}
		}
		return wherelabel.toString();
	}

}
