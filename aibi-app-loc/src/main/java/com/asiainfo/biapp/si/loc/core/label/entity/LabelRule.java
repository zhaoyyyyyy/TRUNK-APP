/*
 * @(#)LabelRule.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.entity;

import io.swagger.annotations.ApiParam;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;

/**
 * Title : LabelRule
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
@Entity
@Table(name = "LOC_LABEL_RULE")
public class LabelRule extends BaseEntity {

    private static final long serialVersionUID = 2035013017939483936L;

    /**
     * 规则ID
     */
    @Id
    @Column(name = "RULE_ID")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @ApiParam(value = "规则ID")
    private String ruleId;

    /**
     * 父规则ID
     */
    @Column(name = "PARENT_ID")
    @ApiParam(value = "父规则ID")
    private String parentId;

    /**
     * 客户群标签ID
     */
    @Column(name = "CUSTOM_ID")
    @ApiParam(value = "客户群标签ID")
    private String customId;

    /**
     * 计算元素
     */
    @Column(name = "CALCU_ELEMENT")
    @ApiParam(value = "计算元素")
    private String calcuElement;

    /**
     * 最小值
     */
    @Column(name = "MIN_VAL")
    @ApiParam(value = "最小值")
    private Double minVal;

    /**
     * 最大值
     */
    @Column(name = "MAX_VAL")
    @ApiParam(value = "最大值")
    private Double maxVal;

    /**
     * 计算顺序
     */
    @Column(name = "SORT_NUM")
    @ApiParam(value = "计算顺序")
    private Integer sortNum;

    /**
     * 类型 1客户群，2模板
     */
    @Column(name = "CUSTOM_TYPE")
    @ApiParam(value = "类型")
    private Integer customType;

    /**
     * 计算元素类型 1,运算符，2,标签(指标)ID，3,括号，4,产品ID，5清单表名，6.客户群
     */
    @Column(name = "ELEMENT_TYPE")
    @ApiParam(value = "计算元素类型")
    private Integer elementType;

    /**
     * 是否标识 一、是否取反 1是；2否 二、标识清单数据周期1，日；2，月
     */
    @Column(name = "LABEL_FLAG")
    @ApiParam(value = "是否标识")
    private Integer labelFlag;

    /**
     * 属性值 1.属性类标签的值，用逗号隔开，如1,2,3
     */
    @Column(name = "ATTR_VAL")
    @ApiParam(value = "属性值")
    private String attrVal;

    /**
     * 起始时间
     */
    @Column(name = "START_TIME")
    @ApiParam(value = "起始时间")
    private String startTime;

    /**
     * 截止时间
     */
    @Column(name = "END_TIME")
    @ApiParam(value = "截止时间")
    private String endTime;

    /**
     * 连续最小值
     */
    @Column(name = "CONTIUE_MIN_VAL")
    @ApiParam(value = "连续最小值")
    private String contiueMinVal;

    /**
     * 连续最大值
     */
    @Column(name = "CONTIUE_MAX_VAL")
    @ApiParam(value = "连续最大值")
    private String contiueMaxVal;

    /**
     * 左区间符号
     */
    @Column(name = "LEFT_ZONE_SIGN")
    @ApiParam(value = "左区间符号")
    private String leftZoneSign;

    /**
     * 右区间符号
     */
    @Column(name = "RIGHT_ZONE_SIGN")
    @ApiParam(value = "右区间符号")
    private String rightZoneSign;

    /**
     * 精确值
     */
    @Column(name = "EXACT_VALUE")
    @ApiParam(value = "精确值")
    private String exactValue;

    /**
     * 模糊匹配值
     */
    @Column(name = "DARK_VALUE")
    @ApiParam(value = "模糊匹配值")
    private String darkValue;

    /**
     * 条件对应表名
     */
    @Column(name = "TABLE_NAME")
    @ApiParam(value = "条件对应表名")
    private String tableName;

    /**
     * 是否需要偏移 1需要；0不需要 根据需求变更，更正为，0：不需要；1：日偏移；2：月偏移，需要数据割接
     */
    @Column(name = "IS_NEED_OFFSET")
    @ApiParam(value = "是否需要偏移")
    private Integer isNeedOffset;

    /**
     * 虚标签名称
     */
    @Column(name = "VIRTUAL_LABEL_NAME")
    @ApiParam(value = "虚标签名称")
    private String virtualLabelName;

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }

    public String getCalcuElement() {
        return calcuElement;
    }

    public void setCalcuElement(String calcuElement) {
        this.calcuElement = calcuElement;
    }

    public Double getMinVal() {
        return minVal;
    }

    public void setMinVal(Double minVal) {
        this.minVal = minVal;
    }

    public Double getMaxVal() {
        return maxVal;
    }

    public void setMaxVal(Double maxVal) {
        this.maxVal = maxVal;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Integer getCustomType() {
        return customType;
    }

    public void setCustomType(Integer customType) {
        this.customType = customType;
    }

    public Integer getElementType() {
        return elementType;
    }

    public void setElementType(Integer elementType) {
        this.elementType = elementType;
    }

    public Integer getLabelFlag() {
        return labelFlag;
    }

    public void setLabelFlag(Integer labelFlag) {
        this.labelFlag = labelFlag;
    }

    public String getAttrVal() {
        return attrVal;
    }

    public void setAttrVal(String attrVal) {
        this.attrVal = attrVal;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getContiueMinVal() {
        return contiueMinVal;
    }

    public void setContiueMinVal(String contiueMinVal) {
        this.contiueMinVal = contiueMinVal;
    }

    public String getContiueMaxVal() {
        return contiueMaxVal;
    }

    public void setContiueMaxVal(String contiueMaxVal) {
        this.contiueMaxVal = contiueMaxVal;
    }

    public String getLeftZoneSign() {
        return leftZoneSign;
    }

    public void setLeftZoneSign(String leftZoneSign) {
        this.leftZoneSign = leftZoneSign;
    }

    public String getRightZoneSign() {
        return rightZoneSign;
    }

    public void setRightZoneSign(String rightZoneSign) {
        this.rightZoneSign = rightZoneSign;
    }

    public String getExactValue() {
        return exactValue;
    }

    public void setExactValue(String exactValue) {
        this.exactValue = exactValue;
    }

    public String getDarkValue() {
        return darkValue;
    }

    public void setDarkValue(String darkValue) {
        this.darkValue = darkValue;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getIsNeedOffset() {
        return isNeedOffset;
    }

    public void setIsNeedOffset(Integer isNeedOffset) {
        this.isNeedOffset = isNeedOffset;
    }

    public String getVirtualLabelName() {
        return virtualLabelName;
    }

    public void setVirtualLabelName(String virtualLabelName) {
        this.virtualLabelName = virtualLabelName;
    }

}
