/*
 * @(#)DimTableDataId.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.dimtable.entity;

import javax.persistence.Column;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;

import io.swagger.annotations.ApiParam;

/**
 * Title : DimTableDataId
 * <p/>
 * Description : 维表数据表的联合主键
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

public class DimTableDataId extends BaseEntity{

    private static final long serialVersionUID = 1L;
    
    /**
     * 维表表名,主键之一
     */
    @Column(name = "DIM_TABLENAME")
    @ApiParam(value = "维表表名")
    private String dimTableName;
    
    /**
     * 维表主键,主键之一
     */
    @Column(name = "DIM_CODE")
    @ApiParam(value = "维表主键")
    private String dimCode;

    public DimTableDataId() {
    }
    /**
     * @param dimTableName
     */
    public DimTableDataId(String dimTableName) {
        this.dimTableName = dimTableName;
    }
	/**
     * @param dimTableName
     * @param dimCode
     */
    public DimTableDataId(String dimTableName, String dimCode) {
        super();
        this.dimTableName = dimTableName;
        this.dimCode = dimCode;
    }

    public String getDimTableName() {
		return dimTableName;
	}

	public void setDimTableName(String dimTableName) {
		this.dimTableName = dimTableName;
	}

	public String getDimCode() {
		return dimCode;
	}

	public void setDimCode(String dimCode) {
		this.dimCode = dimCode;
	}
	
	@Override
	public boolean equals(Object other) {  
        if ((this == other))  
            return true;  
        if ((other == null))  
            return false;  
        if (!(other instanceof DimTableDataId))  
            return false;  
        DimTableDataId castOther = (DimTableDataId) other;  
  

        return ((this.getDimTableName() == castOther.getDimTableName()) || 
        			(this.getDimTableName() != null && castOther.getDimTableName() != null && 
        			this.getDimTableName().equals(castOther.getDimTableName()))) && 
        			((this.getDimCode() == castOther.getDimCode()) || (this.getDimCode() != null && 
        			castOther.getDimCode() != null && this.getDimCode().equals(castOther.getDimCode()))); 
    }  
	
	@Override
    public int hashCode() {  
        int result = 17;  
  
        result = 37 * result + (this.getDimTableName() == null ? 0 : this.getDimTableName().hashCode());  
        result = 37 * result + (this.getDimCode() == null ? 0 : this.getDimCode().hashCode());
        
        return result;  
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "DimTableDataId [dimTableName=" + dimTableName + ", dimCode=" + dimCode + "]";
    }
    
    

}
