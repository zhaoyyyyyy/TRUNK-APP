/*
 * @(#)LabelRuleVo.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.vo;

import java.util.List;

import com.asiainfo.biapp.si.loc.core.label.entity.LabelRule;

/**
 * Title : LabelRuleVo
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2017年11月21日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月21日
 */
public class LabelRuleVo extends LabelRule {

    private static final long serialVersionUID = 2035013017939483936L;

    //view field
  	private Integer labelTypeId;//用于页面，非数据库属性
  	private String customOrLabelName;//用于页面展示客户群或者标签名称
  	
	private Integer updateCycle;
	private String dataDate;//数据时间字段
	private String queryWay;//用于显示
	private String attrName;//属性名称，页面回显用
	
	private List<LabelRuleVo> childLabelRuleList;// 纵表标签子标签或客户群规则
	
	//标签数Param
	private String effectDate;
	
	private String unit;
	
	public Integer getLabelTypeId() {
		return labelTypeId;
	}
	public void setLabelTypeId(Integer labelTypeId) {
		this.labelTypeId = labelTypeId;
	}
	public String getCustomOrLabelName() {
		return customOrLabelName;
	}
	public void setCustomOrLabelName(String customOrLabelName) {
		this.customOrLabelName = customOrLabelName;
	}
	public Integer getUpdateCycle() {
		return updateCycle;
	}
	public void setUpdateCycle(Integer updateCycle) {
		this.updateCycle = updateCycle;
	}
	public String getDataDate() {
		return dataDate;
	}
	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}
	
	private Integer labelOrCustomSort;//只标识客户群和标签的排序序号，用户在购物车中删除标签或者客户群
	public Integer getLabelOrCustomSort() {
		return labelOrCustomSort;
	}
	public void setLabelOrCustomSort(Integer labelOrCustomSort) {
		this.labelOrCustomSort = labelOrCustomSort;
	}
    
    public String getQueryWay() {
        return queryWay;
    }
    
    public void setQueryWay(String queryWay) {
        this.queryWay = queryWay;
    }
    
    public String getAttrName() {
        return attrName;
    }
    
    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }
	public List<LabelRuleVo> getChildLabelRuleList() {
		return childLabelRuleList;
	}
	public void setChildLabelRuleList(List<LabelRuleVo> childLabelRuleList) {
		this.childLabelRuleList = childLabelRuleList;
	}
	public String getEffectDate() {
		return effectDate;
	}
	public void setEffectDate(String effectDate) {
		this.effectDate = effectDate;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Override
    public LabelRuleVo clone() throws CloneNotSupportedException {
		LabelRuleVo ciLabelRule = (LabelRuleVo) super.clone();
        return ciLabelRule;
    }
	
}
