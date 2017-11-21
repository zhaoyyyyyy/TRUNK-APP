/*
 * @(#)TargetTableStatus.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.sourceconfig.entity;

import io.swagger.annotations.ApiParam;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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
    @Column(name = "LABEL_ID")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @ApiParam(value = "表ID")
    private String labelId;

    /**
     * 数据表名
     */
    @Column(name = "COO_TABLE_NAME")
    @ApiParam(value = "数据表名")
    private String cooTableName;

    /**
     * 指标源表类型
     */
    @Column(name = "COO_TABLE_TYPE")
    @ApiParam(value = "指标源表类型")
    private Integer cooTableType;

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
     * 错误信息描述
     */
    @Column(name = "EXCEPTION_DESC")
    @ApiParam(value = "错误信息描述")
    private String exceptionDesc;

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getCooTableName() {
        return cooTableName;
    }

    public void setCooTableName(String cooTableName) {
        this.cooTableName = cooTableName;
    }

    public Integer getCooTableType() {
        return cooTableType;
    }

    public void setCooTableType(Integer cooTableType) {
        this.cooTableType = cooTableType;
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

}
