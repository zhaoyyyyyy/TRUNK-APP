/*
 * @(#)DimTableData.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.dimtable.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiParam;
import parquet.org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Title : DimTableData
 * <p/>
 * Description : 维表数据表(前台库存储)
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 8.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2018年1月3日    hongfb        Created</pre>
 * <p/>
 *
 * @author  hongfb
 * @version 1.0.0.2018年1月3日
 */

@Entity
@Table(name = "LOC_DIMTABLE_DATA")
@JsonIgnoreProperties({"id"})
public class DimTableData extends BaseEntity {

    private static final long serialVersionUID = 1L;
    
    /**
     * 维表主键
     */
    @EmbeddedId
    @JsonIgnore
    private DimTableDataId id;

    /**
     * 维表表名,主键之一
     */
    @Transient
    @ApiParam(value = "维表名称(必填)")
    private String tableName;
    
    /**
     * 维表主键,主键之一
     */
    @Transient
    @ApiParam(value = "维表key(选填)")
    private String dimKey;
    
    /**
     * 维表值列名
     */
    @Column(name = "DIM_VALUE")
    @ApiParam(value = "维表值(选填，支持模糊搜索)")
    private String dimValue;

    public DimTableData() {}  
    public DimTableData(DimTableDataId id, String dimValue) {  
        this.id = id;
        this.dimValue = dimValue;
    }
    public DimTableData(String dimTableName,String dimCode,String dimValue) {  
        this.id.setDimTableName(dimTableName);
        this.id.setDimCode(dimCode);
        this.dimValue = dimValue;
    }

    public DimTableDataId getId() {
        return id;
    }
    
    public void setId(DimTableDataId id) {
        this.id = id;
    }

    public String getTableName() {
        return id.getDimTableName();
    }

    public void setTableName(String dimTableName) {
        this.id.setDimTableName(dimTableName);
    }

    public String getDimKey() {
        return id.getDimCode();
    }

    public void setDimKey(String dimKey) {
        this.id.setDimCode(dimKey);
    }
    
    public String getDimValue() {
        return dimValue;
    }
    
    public void setDimValue(String dimValue) {
        this.dimValue = dimValue;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "DimTableData [dimTableName=" + id.getDimTableName() + ", dimCode=" + id.getDimCode() + ", dimValue=" + dimValue + "]";
    }  

}
