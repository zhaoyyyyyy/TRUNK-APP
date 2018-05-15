/*
 * @(#)CategoryInfo.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.entity;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;
import com.asiainfo.biapp.si.loc.base.extend.SpringContextHolder;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.core.label.service.ICategoryInfoService;
import com.asiainfo.biapp.si.loc.core.label.vo.CategoryInfoVo;

import io.swagger.annotations.ApiParam;

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
    @GeneratedValue(generator = "idGenerator")
    @ApiParam(value = "分类ID")
    //COC自定义主键自增
    @GenericGenerator(name = "idGenerator",
        strategy = "com.asiainfo.biapp.si.loc.base.extend.LocGenerateId",
        parameters = {
                @Parameter(name = "name", value = "LABEL_CATEGORY_SEQ"), //来自DIM_SEQUECE_INFO表的 SEQUECE_NAME
                @Parameter(name = "prefix", value = "C"), //ID前缀
                @Parameter(name = "size", value = "3") //占位符表示 001-999
        }
    )
    private String categoryId;

    /**
     * 系统ID
     */
    @Column(name = "SYS_ID")
    @ApiParam(value = "专区/场景ID",required = true)
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
    
    /**
     * 拖拽目的排序
     */
    @Transient
    private  transient String targetSortNum;
    
    
    
    public String getTargetSortNum() {
		return targetSortNum;
	}

	public void setTargetSortNum(String targetSortNum) {
		this.targetSortNum = targetSortNum;
	}

	@ApiParam(value = "子分类")
    @Transient
//    @OrderBy(value = "sortNum")
//    @OneToMany(cascade = CascadeType.ALL ,fetch=FetchType.EAGER)
//    @JoinColumn(name = "PARENT_ID", insertable = false, updatable = false)
    private Set<CategoryInfo> children = new LinkedHashSet<CategoryInfo>();

    public Set<CategoryInfo> getChildren() {
    	List<CategoryInfo> list = null;
    	try {
		ICategoryInfoService categoryInfoService = (ICategoryInfoService) SpringContextHolder.getBean("categoryInfoServiceImpl");
	    	CategoryInfoVo categoryInfoVo = new CategoryInfoVo();
	    	categoryInfoVo.setParentId(categoryId);
	    	categoryInfoVo.setSysId(sysId);
		list = categoryInfoService.selectCategoryInfoList(categoryInfoVo);
		} catch (Exception e) {LogUtil.error("标签目录以父查子异常",e);}
        return (null==list||list.isEmpty())?new LinkedHashSet<CategoryInfo>() : new LinkedHashSet<CategoryInfo>(list);
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
