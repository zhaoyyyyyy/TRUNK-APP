/*
 * @(#)LabelGenerateView.java
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
 * Title : 运营监控明细：标签生成表格
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
@Table(name = "V_MONITOR_LABEL_GENERATE_VIEW")
public class LabelGenerateView extends BaseEntity{

    /**  */
    private static final long serialVersionUID = -7748530435390027201L;
    
    @Id
    @Column(name = "LABEL_ID")
    @ApiParam(value = "标签编码")
    private String labelId;
    
    @Column(name = "LABEL_NAME")
    @ApiParam(value = "标签名称")
    private String labelName;
    
    @Column(name = "DATA_STATUS")
    @ApiParam(value = "生成状态")
    private Integer dataStatus;
    
    @Column(name = "COLUMN_NAME")
    @ApiParam(value = "目标表列名")
    private String columnName;
    
    @Column(name = "TABLE_NAME")
    @ApiParam(value = "表名")
    private String tableName;
    
    @Column(name = "TABLE_TYPE")
    @ApiParam(value = "表类型")
    private Integer tableType;

    @Column(name = "EXCEPTION_DESC")
    @ApiParam(value = "异常描述")
    private String exceptionDesc;
    
    @Column(name = "DATA_DATE")
    @ApiParam(value = "生成时间")
    private String dataDate;

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

    
    public Integer getDataStatus() {
        return dataStatus;
    }

    
    public void setDataStatus(Integer dataStatus) {
        this.dataStatus = dataStatus;
    }

    
    public String getColumnName() {
        return columnName;
    }

    
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    
    public String getTableName() {
        return tableName;
    }

    
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    
    public Integer getTableType() {
        return tableType;
    }

    
    public void setTableType(Integer tableType) {
        this.tableType = tableType;
    }
    
    
    public String getExceptionDesc() {
        return exceptionDesc;
    }
    
    public void setExceptionDesc(String exceptionDesc) {
        this.exceptionDesc = exceptionDesc;
    }
    
    public String getDataDate() {
        return dataDate;
    }

    public void setDataDate(String dataDate) {
        this.dataDate = dataDate;
    }
}
