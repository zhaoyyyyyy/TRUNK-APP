/*
 * @(#)CategoryInfo.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.entity;

import io.swagger.annotations.ApiParam;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;

/**
 * Title : CategoryInfo
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
 * 1    2017年11月20日     wangrd        Created
 * </pre>
 * <p/>
 *
 * @author wangrd
 * @version 1.0.0.2017年11月20日
 */
@Entity
@Table(name = "LOC_CATEGORY_INFO")
public class CategoryInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
    @Id
    @Column(name = "CATEGORY_ID")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @ApiParam(value = "分类ID")
    private String categoryId;

    /**
     * 系统ID
     */
    @Column(name = "SYS_ID")
    @ApiParam(value = "系统ID")
    private String sysId;

    /**
     * 系统类型
     */
    @Column(name = "SYS_TYPE")
    @ApiParam(value = "系统类型")
    private String sysType;

    /**
     * 分类描述
     */
    @Column(name = "CATEGORY_DESC")
    @ApiParam(value = "分类描述")
    private String categoryDesc;

    /**
     * 分类名称
     */
    @Column(name = "CATEGORY_NAME")
    @ApiParam(value = "分类名称")
    private String categoryName;

    /**
     * 父分类ID
     */
    @Column(name = "PARENT_ID")
    @ApiParam(value = "父分类ID")
    private String parentId;

    /**
     * 分类ID路径
     */
    @Column(name = "CATEGORY_PATH")
    @ApiParam(value = "分类ID路径")
    private String categoryPath;

    /**
     * 叶子节点
     */
    @Column(name = "IS_LEAF")
    @ApiParam(value = "叶子节点")
    private Integer isLeaf;

    /**
     * 状态
     */
    @Column(name = "STATUS_ID")
    @ApiParam(value = "状态")
    private Integer statusId;

    /**
     * 排序
     */
    @Column(name = "SORT_NUM")
    @ApiParam(value = "排序")
    private Integer sortNum;

    /**
     * 层次
     */
    @Column(name = "LEVEL_ID")
    @ApiParam(value = "层次")
    private Integer levelId;

    @ApiParam(value = "子分类")
    @OrderBy(value = "SORT_NUM")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "PARENT_ID", insertable = false, updatable = false)
    private Set<CategoryInfo> children = new HashSet<>();

    public Set<CategoryInfo> getChildren() {
        return children;
    }

    public void setChildren(Set<CategoryInfo> children) {
        this.children = children;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public String getSysType() {
        return sysType;
    }

    public void setSysType(String sysType) {
        this.sysType = sysType;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        this.categoryPath = categoryPath;
    }

    public Integer getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Integer isLeaf) {
        this.isLeaf = isLeaf;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

}
