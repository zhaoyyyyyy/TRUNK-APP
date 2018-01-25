/*
 * @(#)DimOrgLevel.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.back.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;

import io.swagger.annotations.ApiParam;

/**
 * Title : DimOrgLevel
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2018
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
 * 1    2018年1月24日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2018年1月24日
 */
@Entity
@Table(name = "DIM_ORG_LEVEL")
public class DimOrgLevel extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private DimOrgLevelId dimOrgLevelId;

    @Column(name = "LEVEL_ID")
    @ApiParam(value = "层级")
    private Integer levelId;

    @Column(name = "SORT_NUM")
    @ApiParam(value = "排序")
    private Integer sortNum;

    public DimOrgLevelId getDimOrgLevelId() {
        return dimOrgLevelId;
    }

    public void setDimOrgLevelId(DimOrgLevelId dimOrgLevelId) {
        this.dimOrgLevelId = dimOrgLevelId;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

}
