/*
 * @(#)LabelVerticalColumnRel.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.labelconfig.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;

import io.swagger.annotations.ApiParam;

/**
 * Title : LabelVerticalColumnRel
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2017年11月21日     wangrd        Created</pre>
 * <p/>
 *
 * @author   wangrd
 * @version 1.0.0.2017年11月21日
 */
@Entity
@Table(name = "LOC_LABEL_VERTICAL_COLUMN_REL")
public class LabelVerticalColumnRel extends BaseEntity{
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 标签ID
     */
    @Id
    @Column(name = "LABEL_ID")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @ApiParam(value = "标签ID")
    private String labelId;
    
    /**
     * 列ID
     */
    @Column(name = "COLUMN_ID")
    @ApiParam(value = "列ID")
    private Integer columnId;
    
    /**
     * 列对应标签类型ID
     */
    @Column(name = "LABEL_TYPE_ID")
    @ApiParam(value = "列对应标签类型ID")
    private Integer labelTypeId;
    
    /**
     * 是否必选列
     */
    @Column(name = "IS_MUST_COLUMN")
    @ApiParam(value = "是否必选列")
    private Integer isMustColumn;
    
    /**
     * 列顺序
     */
    @Column(name = "SORT_NUM")
    @ApiParam(value = "列顺序")
    private Integer sortNum;

    
    public String getLabelId() {
        return labelId;
    }

    
    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    
    public Integer getColumnId() {
        return columnId;
    }

    
    public void setColumnId(Integer columnId) {
        this.columnId = columnId;
    }

    
    public Integer getLabelTypeId() {
        return labelTypeId;
    }

    
    public void setLabelTypeId(Integer labelTypeId) {
        this.labelTypeId = labelTypeId;
    }

    
    public Integer getIsMustColumn() {
        return isMustColumn;
    }

    
    public void setIsMustColumn(Integer isMustColumn) {
        this.isMustColumn = isMustColumn;
    }

    
    public Integer getSortNum() {
        return sortNum;
    }

    
    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }
    
    

}
