/*
 * @(#)SourceInfo.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.source.entity;

import io.swagger.annotations.ApiParam;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Title : SourceInfo
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
@Table(name = "LOC_SOURCE_INFO")
public class SourceInfo extends BaseEntity {

    private static final long serialVersionUID = 2035013017939483936L;

    /**
     * 指标编码
     */
    @Id
    @Column(name = "SOURCE_ID")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @ApiParam(value = "指标编码")
    private String sourceId;

    /**
     * 指标名称
     */
    @Column(name = "SOURCE_NAME")
    @ApiParam(value = "指标名称")
    private String sourceName;

    /**
     * 数据ID
     */
    @Column(name = "SOURCE_TABLE_ID")
    @ApiParam(value = "数据ID")
    private String sourceTableId;

    /**
     * 列名
     */
    @Column(name = "COLUMN_NAME")
    @ApiParam(value = "列名")
    private String columnName;

    /**
     * 列运算规则
     */
    @Column(name = "SOURCE_COLUMN_RULE")
    @ApiParam(value = "列运算规则")
    private String sourceColumnRule;

    /**
     * 列中文名
     */
    @Column(name = "COLUMN_CN_NAME")
    @ApiParam(value = "列中文名")
    private String columnCnName;

    /**
     * 列数据类型
     */
    @Column(name = "COO_COLUMN_TYPE")
    @ApiParam(value = "列数据类型")
    private String cooColumnType;

    /**
     * 列数据长度
     */
    @Column(name = "COLUMN_LENGTH")
    @ApiParam(value = "列数据长度")
    private Integer columnLength;

    /**
     * 单位
     */
    @Column(name = "COLUMN_UNIT")
    @ApiParam(value = "单位")
    private String columnUnit;

    /**
     * 业务口径
     */
    @Column(name = "COLUMN_CALIBER")
    @ApiParam(value = "业务口径")
    private String columnCaliber;

    /**
     * 列序
     */
    @Column(name = "COLUMN_NUM")
    @ApiParam(value = "列序")
    private Integer columnNum;

    @JsonIgnore
    @ApiParam(value = "源配置")
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = SourceTableInfo.class)
    @JoinColumn(name = "SOURCE_TABLE_ID", insertable = false, updatable = false)
    private SourceTableInfo sourceTableInfo;

    public SourceTableInfo getSourceTableInfo() {
        return sourceTableInfo;
    }

    public void setSourceTableInfo(SourceTableInfo sourceTableInfo) {
        this.sourceTableInfo = sourceTableInfo;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceTableId() {
        return sourceTableId;
    }

    public void setSourceTableId(String sourceTableId) {
        this.sourceTableId = sourceTableId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getSourceColumnRule() {
        return sourceColumnRule;
    }

    public void setSourceColumnRule(String sourceColumnRule) {
        this.sourceColumnRule = sourceColumnRule;
    }

    public String getColumnCnName() {
        return columnCnName;
    }

    public void setColumnCnName(String columnCnName) {
        this.columnCnName = columnCnName;
    }

    public String getCooColumnType() {
        return cooColumnType;
    }

    public void setCooColumnType(String cooColumnType) {
        this.cooColumnType = cooColumnType;
    }

    public Integer getColumnLength() {
        return columnLength;
    }

    public void setColumnLength(Integer columnLength) {
        this.columnLength = columnLength;
    }

    public String getColumnUnit() {
        return columnUnit;
    }

    public void setColumnUnit(String columnUnit) {
        this.columnUnit = columnUnit;
    }

    public String getColumnCaliber() {
        return columnCaliber;
    }

    public void setColumnCaliber(String columnCaliber) {
        this.columnCaliber = columnCaliber;
    }

    public Integer getColumnNum() {
        return columnNum;
    }

    public void setColumnNum(Integer columnNum) {
        this.columnNum = columnNum;
    }

}
