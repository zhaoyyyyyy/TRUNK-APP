/*
 * @(#)TemplateAttrRel.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;

import io.swagger.annotations.ApiParam;

/**
 * Title : TemplateAttrRel
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
@Table(name = "LOC_TEMPLATE_ATTR_REL")
public class TemplateAttrRel extends BaseEntity{

    private static final long serialVersionUID = 1L;
    
    /**
     * 模板编码
     */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "TEMPLATE_ID")
    @ApiParam(value = "模板编码")
    private String templateId;
    
    /**
     * 标签ID
     */
    @Column(name = "LABEL_ID")
    @ApiParam(value = "标签ID")
    private String labelId;
    
    /**
     * 标签列名
     */
    @Column(name = "LABEL_COLUMN_ID")
    @ApiParam(value = "标签列名")
    private String labelColumnId;
    
    /**
     * 是否纵表属性
     */
    @Column(name = "IS_VERTICAL_ATTR")
    @ApiParam(value = "是否纵表属性")
    private Integer isVerticalAttr;
    
    /**
     * 排序
     */
    @Column(name = "SORT_NUM")
    @ApiParam(value = "排序")
    private Integer sortNum;
    
    /**
     * 状态
     */
    @Column(name = "STATUS")
    @ApiParam(value = "状态")
    private Integer status;

    
    public String getTemplateId() {
        return templateId;
    }

    
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    
    public String getLabelId() {
        return labelId;
    }

    
    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    
    public String getLabelColumnId() {
        return labelColumnId;
    }

    
    public void setLabelColumnId(String labelColumnId) {
        this.labelColumnId = labelColumnId;
    }

    
    public Integer getIsVerticalAttr() {
        return isVerticalAttr;
    }

    
    public void setIsVerticalAttr(Integer isVerticalAttr) {
        this.isVerticalAttr = isVerticalAttr;
    }

    
    public Integer getSortNum() {
        return sortNum;
    }

    
    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    
    public Integer getStatus() {
        return status;
    }

    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    

}
