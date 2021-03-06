/*
 * @(#)LabelPushCycle.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.entity;

import io.swagger.annotations.ApiParam;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.syspush.vo.LabelPushCycleVo;

/**
 * Title : LabelPushCycle
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2018年1月17日    wangrd        Created</pre>
 * <p/>
 *
 * @author  wangrd
 * @version 1.0.0.2018年1月17日
 */
@Entity
@Table(name = "LOC_LABEL_PUSH_CYCLE")
public class LabelPushCycle extends BaseEntity{

    private static final long serialVersionUID = 1L;
    
    /**
     * 推送设置记录ID
     */
    @Id
    @Column(name = "RECORD_ID")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @ApiParam(value = "推送设置记录ID")
    private String recordId;
    
    /**
     * 客户群ID
     */
    @Column(name = "CUSTOM_GROUP_ID")
    @ApiParam(value = "客户群ID")
    private String customGroupId;
    
    /**
     * 对端系统ID
     */
    @Column(name = "SYS_ID")
    @ApiParam(value = "对端系统ID")
    private String sysId;
    
    /**
     * 主键标识类型
     */
    @Column(name = "KEY_TYPE")
    @ApiParam(value = "主键标识类型")
    private Integer keyType;
    
    /**
     * 推送周期
     */
    @Column(name = "PUSH_CYCLE")
    @ApiParam(value = "推送周期")
    private Integer pushCycle;
    
    /**
     * 推送目标用户
     */
    @Column(name = "PUSH_USER_IDS")
    @ApiParam(value = "推送目标用户")
    private String pushUserIds;
    
    /**
     * 修改时间
     */
    @Column(name = "MODIFY_TIME")
    @ApiParam(value = "修改时间")
    private Date modifyTime;
    
    /**
     * 状态
     */
    @Column(name = "STATUS")
    @ApiParam(value = "状态")
    private Integer status;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CUSTOM_GROUP_ID", referencedColumnName = "Label_ID", insertable = false, updatable = false)
    private LabelInfo  labelInfo;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SYS_ID", referencedColumnName = "SYS_ID", insertable = false, updatable = false)
    private SysInfo  sysInfo;
    
    /**
     * 推送属性ID
     */
    @Transient
    private String AttrbuteId;

    /**
     * 排序的属性和类型（asc,desc）
     */
    @Transient
    private String sortAttrAndType;
    
    /**
     * 推送的平台ID串，前台传以 , 隔开
     */
    @Transient
    private String sysIds;
    
    
    
    
    
    
    public String getSysIds() {
		return sysIds;
	}


	public void setSysIds(String sysIds) {
		this.sysIds = sysIds;
	}


	public String getSortAttrAndType() {
		return sortAttrAndType;
	}


	public void setSortAttrAndType(String sortAttrAndType) {
		this.sortAttrAndType = sortAttrAndType;
	}

    
    /**
     * no field constructor
     */
    public LabelPushCycle() {
        super();
    }
    /**
     * main field constructor
     */
    public LabelPushCycle(String customGroupId, String sysId, Integer pushCycle, Date modifyTime, Integer status ) {
        super();
        this.customGroupId = customGroupId;
        this.sysId = sysId;
        this.pushCycle = pushCycle;
        this.modifyTime = modifyTime;
        this.status = status;
        
    }
    public LabelPushCycle(LabelPushCycleVo labelPushCycleVo) {
        this.recordId = labelPushCycleVo.getRecordId();
        this.customGroupId = labelPushCycleVo.getCustomGroupId();
        this.sysId = labelPushCycleVo.getSysId();
        this.keyType = labelPushCycleVo.getKeyType();
        this.pushCycle = labelPushCycleVo.getPushCycle();
        this.pushUserIds = labelPushCycleVo.getPushUserIds();
        this.modifyTime = labelPushCycleVo.getModifyTime();
        this.status = labelPushCycleVo.getStatus();
    }
    public LabelPushCycle(String customGroupId, Integer status) {
        super();
        this.customGroupId = customGroupId;
        this.status = status;
    }
    
    public String getAttrbuteId() {
        return AttrbuteId;
    }

    
    public void setAttrbuteId(String attrbuteId) {
        AttrbuteId = attrbuteId;
    }


    public String getRecordId() {
        return recordId;
    }

    
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    
    public String getCustomGroupId() {
        return customGroupId;
    }

    
    public void setCustomGroupId(String customGroupId) {
        this.customGroupId = customGroupId;
    }

    
    public String getSysId() {
        return sysId;
    }

    
    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    
    public Integer getKeyType() {
        return keyType;
    }

    
    public void setKeyType(Integer keyType) {
        this.keyType = keyType;
    }

    
    public Integer getPushCycle() {
        return pushCycle;
    }

    
    public void setPushCycle(Integer pushCycle) {
        this.pushCycle = pushCycle;
    }

    
    public String getPushUserIds() {
        return pushUserIds;
    }

    
    public void setPushUserIds(String pushUserIds) {
        this.pushUserIds = pushUserIds;
    }

    
    public Date getModifyTime() {
        return modifyTime;
    }

    
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    
    public Integer getStatus() {
        return status;
    }

    
    public void setStatus(Integer status) {
        this.status = status;
    }

    public LabelInfo getLabelInfo() {
        return labelInfo;
    }
    
    public void setLabelInfo(LabelInfo labelInfo) {
        this.labelInfo = labelInfo;
    }
    
    public SysInfo getSysInfo() {
        return sysInfo;
    }


    
    public void setSysInfo(SysInfo sysInfo) {
        this.sysInfo = sysInfo;
    }

}
