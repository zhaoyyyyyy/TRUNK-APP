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

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiParam;

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
public class DimTableData extends BaseEntity{

    private static final long serialVersionUID = 1L;
    
    /**
     * 维表表名,联合主键
     */
    @EmbeddedId
    @JsonIgnore
    private DimTableDataId id;

    @ApiParam(value = "维表表名")
    private String tableName;
    
    @ApiParam(value = "维表主键")
    private String dimKey;
    
    
    /**
     * 维表值列名
     */
    @Column(name = "DIM_VALUE")
    @ApiParam(value = "维表值")
    private String dimValue;
    
    public DimTableData() {  
    }  
  
    public DimTableData(DimTableDataId id) {  
        this.id = id;  
    }
    public DimTableData(DimTableDataId id, String dimValue) {  
    	    this.id = id;  
    	    this.dimValue = dimValue;  
    }

	public DimTableDataId getId() {
		return id;
	}

	public void setId(DimTableDataId id) {
		this.id = id;
	}

	public String getDimValue() {
		return dimValue;
	}

	public void setDimValue(String dimValue) {
		this.dimValue = dimValue;
	}
    
    public String getTableName() {
        return this.id.getDimTableName();
    }

    
    public void setTableName(String dimTableName) {
        this.id.setDimTableName(dimTableName);
    }

    
    public String getDimKey() {
        return this.id.getDimCode();
    }

    
    public void setDimKey(String dimCode) {
        this.id.setDimCode(dimCode);
    }

    @Override
    public boolean equals(Object other) {  
        if ((this == other))  
            return true;  
        if ((other == null))  
            return false;  
        if (!(other instanceof DimTableData))  
            return false;  
        DimTableData castOther = (DimTableData) other;  
  

        return ((this.getId().getDimTableName() == castOther.getId().getDimTableName()) || 
                    (StringUtil.isNoneBlank(this.getId().getDimTableName()) && StringUtil.isNoneBlank(castOther.getId().getDimTableName()) && 
                    this.getId().getDimTableName().equals(castOther.getId().getDimTableName()))) && 
                    ((this.getId().getDimCode() == castOther.getId().getDimCode()) || (StringUtil.isNoneBlank(this.getId().getDimCode()) && 
                    StringUtil.isNoneBlank(castOther.getId().getDimCode()) && this.getId().getDimCode().equals(castOther.getId().getDimCode())) &&
                    ((this.getDimValue() == castOther.getDimValue()) || 
                    (StringUtil.isNoneBlank(this.getDimValue()) && StringUtil.isNoneBlank(castOther.getDimValue()) && 
                    this.getDimValue().equals(castOther.getDimValue())))); 
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "DimTableData [dimTableName=" + id.getDimTableName() + ", dimCode=" + id.getDimCode() + ", dimValue=" + dimValue + "]";
    }  
    
    
    

}
