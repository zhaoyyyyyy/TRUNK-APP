/*
 * @(#)CustomPushView.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */
package com.asiainfo.biapp.si.loc.core.serviceMonitor.entity;

import io.swagger.annotations.ApiParam;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;

/**
 * 
 * Title : 运营监控明细：客户群推送表格
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History :
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2018年4月24日    admin        Created</pre>
 * <p/>
 *
 * @author  shaosq
 * @version 1.0.0.2018年4月24日
 */
@Entity
@Table(name = "V_CUSTOM_PUSH_VIEW")
public class CustomPushView extends BaseEntity{


    /**  */
    private static final long serialVersionUID = 6523295956944914880L;
    
    @Id
    @Column(name = "LABEL_ID")
    @ApiParam(value = "客户群编码")
    private String labelId;
    
    @Column(name = "LABEL_NAME")
    @ApiParam(value = "客户群名称")
    private String labelName;
    
    @Column(name = "SYS_NAME")
    @ApiParam(value = "推送平台")
    private String sysName;
    
    @Column(name = "PUSH_STATUS")
    @ApiParam(value = "推送状态")
    private Integer pushStatus;
    
    @Column(name = "DATA_DATE")
    @ApiParam(value = "数据时间")
    private String dataDate;
    
    @Column(name = "START_TIME")
    @ApiParam(value = "推送时间")
    private String startTime;
    
    @Column(name = "EXCE_INFO")
    @ApiParam(value = "失败异常信息")
    private String execInfo;
    
    public String getLabelId() {
        return labelId;
    }

    
    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    
    public String getLabelName() {
        return labelName;
    }

    
    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    
    
    public String getDataDate() {
        return dataDate;
    }


    
    public void setDataDate(String dataDate) {
        this.dataDate = dataDate;
    }


    
    public String getSysName() {
        return sysName;
    }


    
    public void setSysName(String sysName) {
        this.sysName = sysName;
    }


    
    public Integer getPushStatus() {
        return pushStatus;
    }


    
    public void setPushStatus(Integer pushStatus) {
        this.pushStatus = pushStatus;
    }


    
    public String getStartTime() {
        return startTime;
    }


    
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }


    
    public String getExecInfo() {
        return execInfo;
    }


    
    public void setExecInfo(String execInfo) {
        this.execInfo = execInfo;
    }
    
    
}
