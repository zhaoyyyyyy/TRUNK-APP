/*
 * @(#)SourceTableInfo.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.source.entity;

import io.swagger.annotations.ApiParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;

/**
 * Title : SourceTableInfo
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
@Table(name = "LOC_SOURCE_TABLE_INFO")
public class SourceTableInfo extends BaseEntity {

    private static final long serialVersionUID = 2035013017939483936L;
    
    public SourceTableInfo() {  
        super();  
    }  
    
    public SourceTableInfo(String sourceTableId,String sourceTableName,Integer dataStatus,Integer isDoing,String startTime,String endTime,String exceptionDesc){
        super();  
        this.sourceTableId = sourceTableId;
        this.sourceTableName = sourceTableName;
        this.dataStatus = dataStatus;
        this.isDoing = isDoing;
        this.startTime = startTime;
        this.endTime = endTime;
        this.exceptionDesc = exceptionDesc;
    }

    /**
     * 数据源表ID
     */
    @Id
    @Column(name = "SOURCE_TABLE_ID")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @ApiParam(value = "数据源表ID")
    private String sourceTableId;

    /**
     * 专区ID
     */
    @Column(name = "CONFIG_ID")
    @ApiParam(value = "专区ID")
    private String configId;

    /**
     * 指标源表文件名
     */
    @Column(name = "SOURCE_FILE_NAME")
    @ApiParam(value = "指标源表文件名")
    private String sourceFileName;

    /**
     * 指标源表表名
     */
    @Column(name = "SOURCE_TABLE_NAME")
    @ApiParam(value = "指标源表表名")
    private String sourceTableName;

    /**
     * 表名SCHEMA
     */
    @Column(name = "TABLE_SCHEMA")
    @ApiParam(value = "表名SCHEMA")
    private String tableSchema;

    /**
     * 指标源表中文名
     */
    @Column(name = "SOURCE_TABLE_CN_NAME")
    @ApiParam(value = "指标源表中文名")
    private String sourceTableCnName;

    /**
     * 指标源表类型 1:普通指标源表;2:纵表源表;3:客户群源表
     */
    @Column(name = "SOURCE_TABLE_TYPE")
    @ApiParam(value = "指标源表类型")
    private Integer sourceTableType;

    /**
     * 表/文件后缀日期 仅支持日期格式后缀如YYYY-MM-DD
     */
    @Column(name = "TABLE_SUFFIX")
    @ApiParam(value = "表/文件后缀日期")
    private String tableSuffix;

    /**
     * 读取周期 1.一次性 2.每日
     */
    @Column(name = "READ_CYCLE")
    @ApiParam(value = "读取周期")
    private Integer readCycle;

    /**
     * 标签主键标识类型 1.PHONE 2.IMEI
     */
    @Column(name = "KEY_TYPE")
    @ApiParam(value = "标签主键标识类型")
    private Integer keyType;

    /**
     * 来源主键标识类型 1.PHONE 2.IMEI
     */
    @Column(name = "ID_TYPE")
    @ApiParam(value = "来源主键标识类型")
    private Integer idType;

    /**
     * 来源主键字段名
     */
    @Column(name = "ID_COLUMN")
    @ApiParam(value = "来源主键字段名")
    private String idColumn;

    /**
     * 来源主键数据类型
     */
    @Column(name = "ID_DATA_TYPE")
    @ApiParam(value = "来源主键数据类型")
    private String idDataType;

    /**
     * 业务口径
     */
    @Column(name = "COLUMN_CALIBER")
    @ApiParam(value = "业务口径")
    private String columnCaliber;

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
     * 数据源ID
     */
    @Column(name = "DATA_SOURCE_ID")
    @ApiParam(value = "数据源ID")
    private String dataSourceId;

    /**
     * 数据源类型
     */
    @Column(name = "DATA_SOURCE_TYPE")
    @ApiParam(value = "数据源类型")
    private Integer dataSourceType;

    /**
     * 数据抽取方式 1:纯JDBC方式;2:BDI方式;3:sqlLoad方式
     */
    @Column(name = "DATA_EXTRACTION_TYPE")
    @ApiParam(value = "数据抽取方式")
    private Integer dataExtractionType;

    /**
     * 数据存储类型 1:分区；2：分表
     */
    @Column(name = "DATA_STORE")
    @ApiParam(value = "数据存储类型")
    private Integer dataStore;

    /**
     * 日期分区字段
     */
    @Column(name = "DATE_COLUMN_NAME")
    @ApiParam(value = "日期分区字段")
    private String dateColumnName;

    /**
     * 过滤条件
     */
    @Column(name = "WHERE_SQL")
    @ApiParam(value = "过滤条件")
    private String whereSql;

    /**
     * 状态 0.失效 1.有效
     */
    @Column(name = "STATUS_ID")
    @ApiParam(value = "状态")
    private Integer statusId;

    @ApiParam(value = "指标信息列")
    @OneToMany(fetch = FetchType.LAZY, targetEntity = SourceInfo.class)
    @JoinColumn(name = "SOURCE_TABLE_ID", insertable = false, updatable = false)
    private List<SourceInfo> sourceInfoList = new ArrayList<>(0);
    
    @OneToMany(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE) 
    @JoinColumn(name = "SOURCE_TABLE_ID", insertable = false, updatable = false)
    private List<TargetTableStatus> targetTableStatusList= new ArrayList<>(0);
    
    @Transient
    private Integer dataStatus;
    @Transient
    private Integer isDoing;
    @Transient
    private String startTime;
    @Transient
    private String endTime;
    @Transient
    private String exceptionDesc;
    
    public Integer getDataStore() {
        return dataStore;
    }

    public void setDataStore(Integer dataStore) {
        this.dataStore = dataStore;
    }

    public List<SourceInfo> getSourceInfoList() {
        return sourceInfoList;
    }

    public void setSourceInfoList(List<SourceInfo> sourceInfoList) {
        this.sourceInfoList = sourceInfoList;
    }

    public String getSourceTableId() {
        return sourceTableId;
    }

    public void setSourceTableId(String sourceTableId) {
        this.sourceTableId = sourceTableId;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getSourceFileName() {
        return sourceFileName;
    }

    public void setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }

    public String getSourceTableName() {
        return sourceTableName;
    }

    public void setSourceTableName(String sourceTableName) {
        this.sourceTableName = sourceTableName;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public String getSourceTableCnName() {
        return sourceTableCnName;
    }

    public void setSourceTableCnName(String sourceTableCnName) {
        this.sourceTableCnName = sourceTableCnName;
    }

    public Integer getSourceTableType() {
        return sourceTableType;
    }

    public void setSourceTableType(Integer sourceTableType) {
        this.sourceTableType = sourceTableType;
    }

    public String getTableSuffix() {
        return tableSuffix;
    }

    public void setTableSuffix(String tableSuffix) {
        this.tableSuffix = tableSuffix;
    }

    public Integer getReadCycle() {
        return readCycle;
    }

    public void setReadCycle(Integer readCycle) {
        this.readCycle = readCycle;
    }

    public Integer getKeyType() {
        return keyType;
    }

    public void setKeyType(Integer keyType) {
        this.keyType = keyType;
    }

    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public String getIdColumn() {
        return idColumn;
    }

    public void setIdColumn(String idColumn) {
        this.idColumn = idColumn;
    }

    public String getIdDataType() {
        return idDataType;
    }

    public void setIdDataType(String idDataType) {
        this.idDataType = idDataType;
    }

    public String getColumnCaliber() {
        return columnCaliber;
    }

    public void setColumnCaliber(String columnCaliber) {
        this.columnCaliber = columnCaliber;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        if(createTime != null){
        	return formatter.format(createTime);
        }
        return "";
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public Integer getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(Integer dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    public Integer getDataExtractionType() {
        return dataExtractionType;
    }

    public void setDataExtractionType(Integer dataExtractionType) {
        this.dataExtractionType = dataExtractionType;
    }

    public String getDateColumnName() {
        return dateColumnName;
    }

    public void setDateColumnName(String dateColumnName) {
        this.dateColumnName = dateColumnName;
    }

    public String getWhereSql() {
        return whereSql;
    }

    public void setWhereSql(String whereSql) {
        this.whereSql = whereSql;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    
    public List<TargetTableStatus> getTargetTableStatusList() {
        return targetTableStatusList;
    }

    
    public void setTargetTableStatusList(List<TargetTableStatus> targetTableStatusList) {
        this.targetTableStatusList = targetTableStatusList;
    }

    
    public Integer getDataStatus() {
        return dataStatus;
    }

    
    public void setDataStatus(Integer dataStatus) {
        this.dataStatus = dataStatus;
    }

    
    public Integer getIsDoing() {
        return isDoing;
    }

    
    public void setIsDoing(Integer isDoing) {
        this.isDoing = isDoing;
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
    
    public String getExceptionDesc() {
        return exceptionDesc;
    }

    
    public void setExceptionDesc(String exceptionDesc) {
        this.exceptionDesc = exceptionDesc;
    }

  
    
}
