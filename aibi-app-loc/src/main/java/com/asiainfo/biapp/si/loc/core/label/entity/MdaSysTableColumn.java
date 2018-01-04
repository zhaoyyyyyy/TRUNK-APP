/*
 * @(#)MdaSysTableColumn.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;
import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableInfo;

import io.swagger.annotations.ApiParam;

/**
 * Title : MdaSysTableColumn
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2017年11月21日    lilin7        Created
 * </pre>
 * <p/>
 *
 * @author lilin7
 * @version 1.0.0.2017年11月21日
 */
@Entity
@Table(name = "LOC_MDA_SYS_TABLE_COLUMN")
public class MdaSysTableColumn extends BaseEntity {

    /**  */
    private static final long serialVersionUID = 2249256048929416837L;

    /**
     * 元数据表列id
     */
    @Id
    @Column(name = "COLUMN_ID")
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", 
    strategy = "com.asiainfo.biapp.si.loc.base.extend.LocGenerateId",
    parameters = {
        @Parameter(name = "name", value = "COLUMN_SEQ"), // 来自DIM_SEQUECE_INFO表的 SEQUECE_NAME
        @Parameter(name = "prefix", value = "COl"), // ID前缀
        @Parameter(name = "size", value = "3") // 占位符表示 001-999
    }) 
    @ApiParam(value = "列Id")
    private String columnId;
    
    /**
     * 标签Id
     */
    @Column(name = "LABEL_ID")
    @ApiParam(value = "标签Id")
    private String labelId; 
    
    /**
     * 所属表Id
     */
    @Column(name = "TABLE_ID")
    @ApiParam(value = "所属表id")
    private String tableId;

	/**
     * 列名
     */
    @Column(name = "COLUMN_NAME")
    @ApiParam(value = "列名")
    private String columnName;

    /**
     * 列中文名
     */
    @Column(name = "COLUMN_CN_NAME")
    @ApiParam(value = "列中文名")
    private String columnCnName;

    /**
     * 列数据类型Id
     */
    @Column(name = "COLUMN_DATA_TYPE_ID")
    @ApiParam(value = "列数据类型Id")
    private Integer columnDataTypeId;

    /**
     * 对应维表表名
     */
    @Column(name="DIM_TRANS_ID")
    @ApiParam(value="对应维表表名")
    private String dimTransId;
    
    /**
     * 单位
     */
    @Column(name = "UNIT")
    @ApiParam(value = "单位")
    private String unit;

    /**
     * 数据类型
     */
    @Column(name = "DATA_TYPE")
    @ApiParam(value = "数据类型")
    private String dataType;
    
    /**
     * 列状态
     */
    @Column(name = "COLUMN_STATUS")
    @ApiParam(value = "列状态")
    private Integer columnStatus;
    
    @Transient
    private DimTableInfo dimtableInfo;
    
    @ManyToOne(cascade = CascadeType.REFRESH, optional = false)  
    @JoinColumn(name="TABLE_ID",insertable=false,updatable=false)  
    private MdaSysTable mdaSysTable;
    
	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

    public String getDimTransId() {
		return dimTransId;
	}

	public void setDimTransId(String dimTransId) {
		this.dimTransId = dimTransId;
	}



	public DimTableInfo getDimtableInfo() {
		return dimtableInfo;
	}

	public void setDimtableInfo(DimTableInfo dimtableInfo) {
		this.dimtableInfo = dimtableInfo;
	}

	public MdaSysTable getMdaSysTable() {
		return mdaSysTable;
	}

	public void setMdaSysTable(MdaSysTable mdaSysTable) {
		this.mdaSysTable = mdaSysTable;
	}

	public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }



    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnCnName() {
        return columnCnName;
    }

    public void setColumnCnName(String columnCnName) {
        this.columnCnName = columnCnName;
    }

    public Integer getColumnDataTypeId() {
        return columnDataTypeId;
    }

    public void setColumnDataTypeId(Integer columnDataTypeId) {
        this.columnDataTypeId = columnDataTypeId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Integer getColumnStatus() {
        return columnStatus;
    }

    public void setColumnStatus(Integer columnStatus) {
        this.columnStatus = columnStatus;
    }

}
