/*
 * @(#)LabelInfo.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;

import io.swagger.annotations.ApiParam;

/**
 * Title : LabelInfo
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
 * 1    2017年11月16日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月16日
 */
@Entity
@Table(name = "LOC_LABEL_INFO")
public class LabelInfo extends BaseEntity {

    private static final long serialVersionUID = 2035013017939483936L;

    /**
     * 标签ID
     */
    @Id
    @Column(name = "LABEL_ID")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @ApiParam(value = "标签ID")
    private String labelId;

    /**
     * 主键标识类型
     */
    @Column(name = "KEY_TYPE")
    @ApiParam(value = "主键标识类型")
    private Integer keyType;

    /**
     * 专区ID
     */
    @Column(name = "CONFIG_ID")
    @ApiParam(value = "专区ID")
    private String configId;

    /**
     * 组织ID
     */
    @Column(name = "ORG_ID")
    @ApiParam(value = "组织ID")
    private String orgId;

    /**
     * 规则编码
     */
    @Column(name = "COUNT_RULES_CODE")
    @ApiParam(value = "规则编码")
    private String countRulesCode;

    /**
     * 标签名称
     */
    @Column(name = "LABEL_NAME")
    @ApiParam(value = "标签名称")
    private String labelName;

    /**
     * 更新周期 1、日 ;2、月
     */
    @Column(name = "UPDATE_CYCLE")
    @ApiParam(value = "更新周期")
    private Integer updateCycle;

    /**
     * 标签类型ID
     */
    @Column(name = "LABEL_TYPE_ID")
    @ApiParam(value = "标签类型ID")
    private Integer labelTypeId;

    /**
     * 标签分类ID
     */
    @Column(name = "CATEGORY_ID")
    @ApiParam(value = "标签分类ID")
    private String categoryId;

    /**
     * 创建方式ID
     */
    @Column(name = "CREATE_TYPE_ID")
    @ApiParam(value = "创建方式ID")
    private Integer createTypeId;

    /**
     * 数据状态ID 未生效,已生效,已失效，冷冻期，已下线
     */
    @Column(name = "DATA_STATUS_ID")
    @ApiParam(value = "数据状态ID")
    private Integer dataStatusId;

    /**
     * 最新数据时间
     */
    @Column(name = "DATA_DATE")
    @ApiParam(value = "最新数据时间")
    private String dataDate;

    /**
     * 业务口径说明
     */
    @Column(name = "BUSI_CALIBER")
    @ApiParam(value = "业务口径说明")
    private String busiCaliber;

    /**
     * 生效时间
     */
    @Column(name = "EFFEC_TIME")
    @ApiParam(value = "生效时间")
    private Date effecTime;

    /**
     * 失效时间
     */
    @Column(name = "FAIL_TIME")
    @ApiParam(value = "失效时间")
    private Date failTime;

    /**
     * 创建人
     */
    @Column(name = "CREATE_USER_ID")
    @ApiParam(value = "创建人")
    private String createUserId;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    @ApiParam(value = "创建时间")
    private Date createTime;

    /**
     * 发布时间
     */
    @Column(name = "PUBLISH_TIME")
    @ApiParam(value = "发布时间")
    private Date publishTime;

    /**
     * 发布描述
     */
    @Column(name = "PUBLISH_DESC")
    @ApiParam(value = "发布描述")
    private String publishDesc;

    /**
     * 业务说明
     */
    @Column(name = "BUSI_LEGEND")
    @ApiParam(value = "业务说明")
    private String busiLegend;

    /**
     * 应用建议
     */
    @Column(name = "APPLY_SUGGEST")
    @ApiParam(value = "应用建议")
    private String applySuggest;

    /**
     * 标签ID层级描述
     */
    @Column(name = "LABEL_ID_LEVEL_DESC")
    @ApiParam(value = "标签ID层级描述")
    private String labelIdLevelDesc;

    /**
     * 是否正式标签 0、试用;1、正式
     */
    @Column(name = "IS_REGULAR")
    @ApiParam(value = "是否正式标签")
    private Integer isRegular;

    /**
     * 群类型 0、标签;1、客户群
     */
    @Column(name = "GROUP_TYPE")
    @ApiParam(value = "群类型")
    private Integer groupType;
    
    /**
     * 依赖指标
     */
    @Transient
    private String dependIndex;
    
    /**
     * 具体规则
     */
    @Transient
    private String countRules;
    /**
     * 单位
     */
    @Transient
    private String unit;

    /**
     * 排序字段
     */
    @Column(name = "SORT_NUM")
    @ApiParam(value = "排序字段")
    private Integer sortNum;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="LABEL_ID",referencedColumnName="LABEL_ID",insertable=false,updatable=false)
    private MdaSysTableColumn mdaSysTableColumn;  
    
    @OneToMany(fetch=FetchType.EAGER)  
    @JoinColumn(name="LABEL_ID",referencedColumnName="LABEL_ID",insertable=false,updatable=false)
    private Set<LabelVerticalColumnRel> verticalColumnRels;
	
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="LABEL_ID",referencedColumnName="RESOURCE_ID",insertable=false,updatable=false)
    private ApproveInfo approveInfo;

    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="LABEL_ID",referencedColumnName="LABEL_ID",insertable=false,updatable=false)
    private LabelExtInfo labelExtInfo;
    
    
    public LabelExtInfo getLabelExtInfo() {
		return labelExtInfo;
	}

	public void setLabelExtInfo(LabelExtInfo labelExtInfo) {
		this.labelExtInfo = labelExtInfo;
	}

	public ApproveInfo getApproveInfo() {
        return approveInfo;
    }

    public void setApproveInfo(ApproveInfo approveInfo) {
        this.approveInfo = approveInfo;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public Integer getKeyType() {
        return keyType;
    }

    public void setKeyType(Integer keyType) {
        this.keyType = keyType;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getCountRulesCode() {
        return countRulesCode;
    }

    public void setCountRulesCode(String countRulesCode) {
        this.countRulesCode = countRulesCode;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public Integer getUpdateCycle() {
        return updateCycle;
    }

    public void setUpdateCycle(Integer updateCycle) {
        this.updateCycle = updateCycle;
    }

    public Integer getLabelTypeId() {
        return labelTypeId;
    }

    public void setLabelTypeId(Integer labelTypeId) {
        this.labelTypeId = labelTypeId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getCreateTypeId() {
        return createTypeId;
    }

    public void setCreateTypeId(Integer createTypeId) {
        this.createTypeId = createTypeId;
    }

    public Integer getDataStatusId() {
        return dataStatusId;
    }

    public void setDataStatusId(Integer dataStatusId) {
        this.dataStatusId = dataStatusId;
    }

    public String getDataDate() {
        return dataDate;
    }

    public void setDataDate(String dataDate) {
        this.dataDate = dataDate;
    }

    public String getBusiCaliber() {
        return busiCaliber;
    }

    public void setBusiCaliber(String busiCaliber) {
        this.busiCaliber = busiCaliber;
    }

    public Date getEffecTime() {
        return effecTime;
    }

    public void setEffecTime(Date effecTime) {
        this.effecTime = effecTime;
    }

    public Date getFailTime() {
        return failTime;
    }

    public void setFailTime(Date failTime) {
        this.failTime = failTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(createTime);
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getPublishDesc() {
        return publishDesc;
    }

    public void setPublishDesc(String publishDesc) {
        this.publishDesc = publishDesc;
    }

    public String getBusiLegend() {
        return busiLegend;
    }

    public void setBusiLegend(String busiLegend) {
        this.busiLegend = busiLegend;
    }

    public String getApplySuggest() {
        return applySuggest;
    }

    public void setApplySuggest(String applySuggest) {
        this.applySuggest = applySuggest;
    }

    public String getLabelIdLevelDesc() {
        return labelIdLevelDesc;
    }

    public void setLabelIdLevelDesc(String labelIdLevelDesc) {
        this.labelIdLevelDesc = labelIdLevelDesc;
    }

    public Integer getIsRegular() {
        return isRegular;
    }

    public void setIsRegular(Integer isRegular) {
        this.isRegular = isRegular;
    }

    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

	public MdaSysTableColumn getMdaSysTableColumn() {
		return mdaSysTableColumn;
	}

	public void setMdaSysTableColumn(MdaSysTableColumn mdaSysTableColumn) {
		this.mdaSysTableColumn = mdaSysTableColumn;
	}

	public Set<LabelVerticalColumnRel> getVerticalColumnRels() {
		return verticalColumnRels;
	}

	public void setVerticalColumnRels(Set<LabelVerticalColumnRel> verticalColumnRels) {
		this.verticalColumnRels = verticalColumnRels;
	}

    
    public String getDependIndex() {
        return dependIndex;
    }

    
    public void setDependIndex(String dependIndex) {
        this.dependIndex = dependIndex;
    }

    
    public String getCountRules() {
        return countRules;
    }

    
    public void setCountRules(String countRules) {
        this.countRules = countRules;
    }

    
    public String getUnit() {
        return unit;
    }

    
    public void setUnit(String unit) {
        this.unit = unit;
    }


}
