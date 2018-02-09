/*
 * @(#)LabelPushReq.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;

import io.swagger.annotations.ApiParam;

/**
 * Title : LabelPushReq
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
 * <pre>1    2018年1月18日    wangrd        Created</pre>
 * <p/>
 *
 * @author  wangrd
 * @version 1.0.0.2018年1月18日
 */
@Entity
@Table(name = "LOC_LABEL_PUSH_REQ")
public class LabelPushReq extends BaseEntity{

    private static final long serialVersionUID = 1L;
    
    /**
     * 推送请求ID
     */
    @Id
    @Column(name = "REQ_ID")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @ApiParam(value = "推送请求ID")
    private String reqId;
    
    /**
     * 推送设置记录ID
     */
    @Column(name = "RECORD_ID")
    @ApiParam(value = "推送设置记录ID")
    private String recodeId;

    /**
     * 数据日期
     */
    @Column(name = "DATA_DATE")
    @ApiParam(value = "数据日期")
    private String dataDate;

    /**
     * 推送状态
     */
    @Column(name = "PUSH_STATUS")
    @ApiParam(value = "推送状态")
    private Integer pushStatus;

    /**
     * 是否带清单
     */
    @Column(name = "IS_HAS_LIST")
    @ApiParam(value = "是否带清单")
    private Integer isHasList;

    /**
     * 开始时间
     */
    @Column(name = "START_TIME")
    @ApiParam(value = "开始时间")
    private Date startTime;

    /**
     * 结束时间
     */
    @Column(name = "END_TIME")
    @ApiParam(value = "结束时间")
    private Date endTime;

    /**
     * 清单表名
     */
    @Column(name = "LIST_TABLE_NAME")
    @ApiParam(value = "清单表名")
    private String listTableName;

    /**
     * 失败异常信息
     */
    @Column(name = "EXCE_INFO")
    @ApiParam(value = "失败异常信息")
    private String exceInfo;

    
    public String getReqId() {
        return reqId;
    }

    
    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    
    public String getRecodeId() {
        return recodeId;
    }

    
    public void setRecodeId(String recodeId) {
        this.recodeId = recodeId;
    }

    
    public String getDataDate() {
        return dataDate;
    }

    
    public void setDataDate(String dataDate) {
        this.dataDate = dataDate;
    }

    
    public Integer getPushStatus() {
        return pushStatus;
    }

    
    public void setPushStatus(Integer pushStatus) {
        this.pushStatus = pushStatus;
    }

    
    public Integer getIsHasList() {
        return isHasList;
    }

    
    public void setIsHasList(Integer isHasList) {
        this.isHasList = isHasList;
    }

    
    public Date getStartTime() {
        return startTime;
    }

    
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    
    public Date getEndTime() {
        return endTime;
    }

    
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    
    public String getListTableName() {
        return listTableName;
    }

    
    public void setListTableName(String listTableName) {
        this.listTableName = listTableName;
    }

    
    public String getExceInfo() {
        return exceInfo;
    }

    
    public void setExceInfo(String exceInfo) {
        this.exceInfo = exceInfo;
    }

    
}
