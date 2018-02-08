/*
 * @(#)LabelAttrRel.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;

import io.swagger.annotations.ApiParam;

/**
 * Title : LabelAttrRel
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2018年1月19日    wangrd        Created</pre>
 * <p/>
 *
 * @author  wangrd
 * @version 1.0.0.2018年1月19日
 */
@Entity
@Table(name = "LOC_LABEL_ATTR_REL")
public class LabelAttrRel extends BaseEntity{

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    @Column(name = "PRI_KEY")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @ApiParam(value = "主键")
    private String priKey;
    
    /**
     * 推送设置记录ID
     */
    @Column(name = "RECORD_ID")
    @ApiParam(value = "推送设置记录ID")
    private String recordId;
    
    /**
     * 客户群标签ID
     */
    @Column(name = "LABEL_ID")
    @ApiParam(value = "客户群标签ID")
    private String labelId;

    /**
     * 属性名
     */
    @Column(name = "ATTR_COL")
    @ApiParam(value = "属性名")
    private String attrCol;

    /**
     * 修改时间
     */
    @Column(name = "MODIFY_TIME")
    @ApiParam(value = "修改时间")
    private Date modifyTime;

    /**
     * 属性字段类型
     */
    @Column(name = "ATTR_COL_TYPE")
    @ApiParam(value = "属性字段类型")
    private String attrColType;

    /**
     * 属性中文名称
     */
    @Column(name = "ATTR_COL_NAME")
    @ApiParam(value = "属性中文名称")
    private String attrColName;

    /**
     * 属性来源
     */
    @Column(name = "ATTR_SOURCE")
    @ApiParam(value = "属性来源")
    private Integer attrSource;

    /**
     * 来源标签(客户群清单)ID
     */
    @Column(name = "LABEL_OR_CUSTOM_ID")
    @ApiParam(value = "来源标签(客户群清单)ID")
    private String labelOrCustomId;

    /**
     * 来源标签(客户群清单)列名
     */
    @Column(name = "LABEL_OR_CUSTOM_COLUMN")
    @ApiParam(value = "来源标签(客户群清单)列名")
    private String labelOrCustomColumn;

    /**
     * 是否纵表属性
     */
    @Column(name = "IS_VERTICAL_ATTR")
    @ApiParam(value = "是否纵表属性")
    private Integer isVerticalAttr;

    /**
     * 清单表名
     */
    @Column(name = "LIST_TABLE_NAME")
    @ApiParam(value = "清单表名")
    private String listTableName;

    /**
     * 状态
     */
    @Column(name = "STATUS")
    @ApiParam(value = "状态")
    private Integer status;

    /**
     * 属性值
     */
    @Column(name = "ATTR_VAL")
    @ApiParam(value = "属性值")
    private String attrVal;

    /**
     * 条件对应表名
     */
    @Column(name = "TABLE_NAME")
    @ApiParam(value = "条件对应表名")
    private String tableName;

    /**
     * 排序类型
     */
    @Column(name = "SORT_TYPE")
    @ApiParam(value = "排序类型")
    private String sortType;

    /**
     * 排序优先级
     */
    @Column(name = "SORT_NUM")
    @ApiParam(value = "排序优先级")
    private Integer sortNum;

    
    
    public String getPriKey() {
        return priKey;
    }

    
    public void setPriKey(String priKey) {
        this.priKey = priKey;
    }


    public String getRecordId() {
        return recordId;
    }

    
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    
    public String getLabelId() {
        return labelId;
    }

    
    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    
    public String getAttrCol() {
        return attrCol;
    }

    
    public void setAttrCol(String attrCol) {
        this.attrCol = attrCol;
    }

    
    public Date getModifyTime() {
        return modifyTime;
    }

    
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    
    public String getAttrColType() {
        return attrColType;
    }

    
    public void setAttrColType(String attrColType) {
        this.attrColType = attrColType;
    }

    
    public String getAttrColName() {
        return attrColName;
    }

    
    public void setAttrColName(String attrColName) {
        this.attrColName = attrColName;
    }

    
    public Integer getAttrSource() {
        return attrSource;
    }

    
    public void setAttrSource(Integer attrSource) {
        this.attrSource = attrSource;
    }

    
    public String getLabelOrCustomId() {
        return labelOrCustomId;
    }

    
    public void setLabelOrCustomId(String labelOrCustomId) {
        this.labelOrCustomId = labelOrCustomId;
    }

    
    public String getLabelOrCustomColumn() {
        return labelOrCustomColumn;
    }

    
    public void setLabelOrCustomColumn(String labelOrCustomColumn) {
        this.labelOrCustomColumn = labelOrCustomColumn;
    }

    
    public Integer getIsVerticalAttr() {
        return isVerticalAttr;
    }

    
    public void setIsVerticalAttr(Integer isVerticalAttr) {
        this.isVerticalAttr = isVerticalAttr;
    }

    
    public String getListTableName() {
        return listTableName;
    }

    
    public void setListTableName(String listTableName) {
        this.listTableName = listTableName;
    }

    
    public Integer getStatus() {
        return status;
    }

    
    public void setStatus(Integer status) {
        this.status = status;
    }

    
    public String getAttrVal() {
        return attrVal;
    }

    
    public void setAttrVal(String attrVal) {
        this.attrVal = attrVal;
    }

    
    public String getTableName() {
        return tableName;
    }

    
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    
    public String getSortType() {
        return sortType;
    }

    
    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    
    public Integer getSortNum() {
        return sortNum;
    }

    
    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }
    
    
}
