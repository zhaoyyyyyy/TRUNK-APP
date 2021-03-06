/*
 * @(#)TargetTableStatus.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.source.entity;

import io.swagger.annotations.ApiParam;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;

/**
 * Title : TargetTableStatus
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
 * 1    2017年11月15日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月15日
 */
@Entity
@Table(name = "DIM_TARGET_TABLE_STATUS")
public class TargetTableStatus extends BaseEntity {

    private static final long serialVersionUID = 2035013017939483936L;

    /**
     * 表ID
     */
    @Id
    @Column(name = "SOURCE_TABLE_ID")
    @ApiParam(value = "表ID")
    private String sourceTableId;

    /**
     * 数据表名
     */
    @Column(name = "SOURCE_TABLE_NAME")
    @ApiParam(value = "数据表名")
    private String sourceTableName;

    /**
     * 指标源表类型
     */
    @Column(name = "SOURCE_TABLE_TYPE")
    @ApiParam(value = "指标源表类型")
    private Integer sourceTableType;

    /**
     * 是否手动执行
     */
    @Column(name = "MANUAL_EXECUTION")
    @ApiParam(value = "是否手动执行")
    private Integer manualExecution;

    /**
     * 是否正在执行
     */
    @Column(name = "IS_DOING")
    @ApiParam(value = "是否正在执行")
    private Integer isDoing;

    /**
     * 数据日期
     */
    @Column(name = "DATA_DATE")
    @ApiParam(value = "数据日期")
    private String dataDate;

    /**
     * 数据状态
     */
    @Column(name = "DATA_STATUS")
    @ApiParam(value = "数据状态")
    private Integer dataStatus;

    /**
     * 批次
     */
    @Column(name = "DATA_BATCH")
    @ApiParam(value = "批次")
    private Integer dataBatch;
    
    /**
     * 开始时间
     */
    @Column(name = "START_TIME")
    @ApiParam(value = "开始时间")
    private String startTime;
    
    /**
     * 结束时间
     */
    @Column(name = "END_TIME")
    @ApiParam(value = "结束时间")
    private String endTime;

    /**
     * 错误信息描述
     */
    @Column(name = "EXCEPTION_DESC")
    @ApiParam(value = "错误信息描述")
    private String exceptionDesc;
    
    public String getSourceTableId() {
        return sourceTableId;
    }

    public void setSourceTableId(String sourceTableId) {
        this.sourceTableId = sourceTableId;
    }

    public String getSourceTableName() {
        return sourceTableName;
    }

    public void setSourceTableName(String sourceTableName) {
        this.sourceTableName = sourceTableName;
    }

    public Integer getSourceTableType() {
        return sourceTableType;
    }

    public void setSourceTableType(Integer sourceTableType) {
        this.sourceTableType = sourceTableType;
    }

    public Integer getManualExecution() {
        return manualExecution;
    }

    public void setManualExecution(Integer manualExecution) {
        this.manualExecution = manualExecution;
    }

    public Integer getIsDoing() {
        return isDoing;
    }

    public void setIsDoing(Integer isDoing) {
        this.isDoing = isDoing;
    }

    public String getDataDate() {
        return dataDate;
    }

    public void setDataDate(String dataDate) {
        this.dataDate = dataDate;
    }

    public Integer getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(Integer dataStatus) {
        this.dataStatus = dataStatus;
    }

    public Integer getDataBatch() {
        return dataBatch;
    }

    public void setDataBatch(Integer dataBatch) {
        this.dataBatch = dataBatch;
    }

    public String getExceptionDesc() {
        return exceptionDesc;
    }

    public void setExceptionDesc(String exceptionDesc) {
        this.exceptionDesc = exceptionDesc;
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
}
