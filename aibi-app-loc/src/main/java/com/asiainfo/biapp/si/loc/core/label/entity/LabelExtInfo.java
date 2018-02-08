/*
 * @(#)ILabelExtInfoDao.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;

import io.swagger.annotations.ApiParam;

/**
 * Title : ILabelExtInfoDao
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
 * 1    2017年11月20日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月20日
 */
@Entity
@Table(name = "LOC_LABEL_EXT_INFO")
public class LabelExtInfo extends BaseEntity {

    private static final long serialVersionUID = 2035013017939483936L;

    /**
     * 标签ID
     */
    @Id
    @Column(name = "LABEL_ID")
    @ApiParam(value = "标签ID")
    private String labelId;

    /**
     * 标签所属原表类型 1：移动自有宽表 2：移动自有客户群 3：客户宽表 4：客户群 5:专区纵表
     */
    @Column(name = "ORIGINAL_TABLE_TYPE")
    @ApiParam(value = "标签所属原表类型")
    private Integer originalTableType;

    /**
     * 标签运算规则展示
     */
    @Column(name = "LABEL_OPT_RULE_SHOW")
    @ApiParam(value = "标签运算规则展示")
    private String labelOptRuleShow;

    /**
     * 所选月标签数据时间
     */
    @Column(name = "MONTH_LABEL_DATE")
    @ApiParam(value = "所选月标签数据时间")
    private String monthLabelDate;

    /**
     * 所选日标签数据时间
     */
    @Column(name = "DAY_LABEL_DATE")
    @ApiParam(value = "所选日标签数据时间")
    private String dayLabelDate;

    /**
     * 策略ID
     */
    @Column(name = "TACTICS_ID")
    @ApiParam(value = "策略ID")
    private String tacticsId;

    /**
     * 服务器ID
     */
    @Column(name = "SERVER_ID")
    @ApiParam(value = "服务器ID")
    private String serverId;

    /**
     * 偏移设置时间
     */
    @Column(name = "OFFSET_DATE")
    @ApiParam(value = "偏移设置时间")
    private String offsetDate;

    /**
     * 清单最大数量
     */
    @Column(name = "LIST_MAX_NUM")
    @ApiParam(value = "清单最大数量")
    private Integer listMaxNum;

    /**
     * 优先级 数字越大越高
     */
    @Column(name = "PRIORITY")
    @ApiParam(value = "优先级")
    private Integer priority;

    /**
     * 标签级别
     */
    @Column(name = "LABEL_LEVEL")
    @ApiParam(value = "标签级别")
    private Integer labelLevel;

    /**
     * 技术口径
     */
    @Column(name = "TECH_CALIBER")
    @ApiParam(value = "技术口径")
    private String techCaliber;

    /**
     * 最新总客户数
     */
    @Column(name = "CUSTOM_NUM")
    @ApiParam(value = "最新总客户数")
    private Integer customNum;

    /**
     * 标签准确率 小于1
     */
    @Column(name = "LABEL_PRECISION")
    @ApiParam(value = "标签准确率")
    private Double labelPrecision;

    /**
     * 标签覆盖率 小于1
     */
    @Column(name = "LABEL_COVERATE")
    @ApiParam(value = "标签覆盖率")
    private Double labelCoverate;

    /**
     * 标签平均分
     */
    @Column(name = "AVG_SCORE")
    @ApiParam(value = "标签平均分")
    private Double avgScore;

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public Integer getOriginalTableType() {
        return originalTableType;
    }

    public void setOriginalTableType(Integer originalTableType) {
        this.originalTableType = originalTableType;
    }

    public String getLabelOptRuleShow() {
        return labelOptRuleShow;
    }

    public void setLabelOptRuleShow(String labelOptRuleShow) {
        this.labelOptRuleShow = labelOptRuleShow;
    }

    public String getMonthLabelDate() {
        return monthLabelDate;
    }

    public void setMonthLabelDate(String monthLabelDate) {
        this.monthLabelDate = monthLabelDate;
    }

    public String getDayLabelDate() {
        return dayLabelDate;
    }

    public void setDayLabelDate(String dayLabelDate) {
        this.dayLabelDate = dayLabelDate;
    }

    public String getTacticsId() {
        return tacticsId;
    }

    public void setTacticsId(String tacticsId) {
        this.tacticsId = tacticsId;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getOffsetDate() {
        return offsetDate;
    }

    public void setOffsetDate(String offsetDate) {
        this.offsetDate = offsetDate;
    }

    public Integer getListMaxNum() {
        return listMaxNum;
    }

    public void setListMaxNum(Integer listMaxNum) {
        this.listMaxNum = listMaxNum;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getLabelLevel() {
        return labelLevel;
    }

    public void setLabelLevel(Integer labelLevel) {
        this.labelLevel = labelLevel;
    }

    public String getTechCaliber() {
        return techCaliber;
    }

    public void setTechCaliber(String techCaliber) {
        this.techCaliber = techCaliber;
    }

    public Integer getCustomNum() {
        return customNum;
    }

    public void setCustomNum(Integer customNum) {
        this.customNum = customNum;
    }

    public Double getLabelPrecision() {
        return labelPrecision;
    }

    public void setLabelPrecision(Double labelPrecision) {
        this.labelPrecision = labelPrecision;
    }

    public Double getLabelCoverate() {
        return labelCoverate;
    }

    public void setLabelCoverate(Double labelCoverate) {
        this.labelCoverate = labelCoverate;
    }

    public Double getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(Double avgScore) {
        this.avgScore = avgScore;
    }

}
