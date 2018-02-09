package com.asiainfo.biapp.si.loc.core.label.model;

import com.asiainfo.biapp.si.loc.core.label.entity.LabelRule;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTableColumn;

/**
 * 所有计算标签元素的抽象类，用于获得各种标签的条件sql
 * @author luyan3
 * @version ZJ
 */
public abstract class LabelElement {
	
	/**
	 * 获得单个标签的条件sql
	 * @param ciLabelRule 规则
	 * @param column 标签对应列
	 * @param asName 别名
	 * @param interval 偏移量
	 * @param updateCycle 更新周期
	 * @param isValidate 是否sql校验的
	 * @return
	 * @version ZJ
	 */
	public abstract String getConditionSql(LabelRule ciLabelRule,
			MdaSysTableColumn column, String asName, Integer interval,
			Integer updateCycle, boolean isValidate);
}
