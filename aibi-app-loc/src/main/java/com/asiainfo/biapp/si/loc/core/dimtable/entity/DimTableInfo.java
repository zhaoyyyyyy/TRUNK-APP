/*
 * @(#)DimTableInfo.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.dimtable.entity;

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
 * Title : DimTableInfo
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
 * <pre>1    2017年11月27日    wangrd        Created</pre>
 * <p/>
 *
 * @author  wangrd
 * @version 1.0.0.2017年11月27日
 */
@Entity
@Table(name = "LOC_DIMTABLE_INFO")
public class DimTableInfo extends BaseEntity{

    private static final long serialVersionUID = 1L;
    
    /**
     * 维表主键
     */
    @Id
    @Column(name = "DIM_ID")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @ApiParam(value = "维表主键")
    private String dimId;
    
    /**
     * 维表表名
     */
    @Column(name = "DIM_TABLENAME")
    @ApiParam(value = "维表表名")
    private String dimTableName;
    
    /**
     * 维表描述
     */
    @Column(name = "DIM_COMMENT")
    @ApiParam(value = "维表描述")
    private String dimComment;
    
    /**
     * 维表主键列名
     */
    @Column(name = "DIM_CODE_COL")
    @ApiParam(value = "维表主键列名")
    private String dimCodeCol;
    
    /**
     * 维表值列名
     */
    @Column(name = "DIM_VALUE_COL")
    @ApiParam(value = "维表值列名")
    private String dimValueCol;
    
    /**
     * 所属专区ID
     */
    @Column(name = "CONFIG_ID")
    @ApiParam(value = "所属专区ID")
    private String configId;
    
    /**
     * 主键字段类型
     */
    @Column(name = "CODE_COL_TYPE")
    @ApiParam(value = "主键字段类型")
    private String codeColType;
    
    /**
     * Where条件
     */
    @Column(name = "DIM_WHERE")
    @ApiParam(value = "Where条件")
    private String dimWhere;
    
    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    @ApiParam(value = "创建时间")
    private Date createTime;
    
    /**
     * 创建人
     */
    @Column(name = "CREATE_USER_ID")
    @ApiParam(value = "创建人")
    private String createUserId;

    
    public String getDimId() {
        return dimId;
    }

    
    public void setDimId(String dimId) {
        this.dimId = dimId;
    }

    
    public String getDimTableName() {
        return dimTableName;
    }

    
    public void setDimTableName(String dimTableName) {
        this.dimTableName = dimTableName;
    }

    
    public String getDimComment() {
        return dimComment;
    }

    
    public void setDimComment(String dimComment) {
        this.dimComment = dimComment;
    }

    
    public String getDimCodeCol() {
        return dimCodeCol;
    }

    
    public void setDimCodeCol(String dimCodeCol) {
        this.dimCodeCol = dimCodeCol;
    }

    
    public String getDimValueCol() {
        return dimValueCol;
    }

    
    public void setDimValueCol(String dimValueCol) {
        this.dimValueCol = dimValueCol;
    }

    
    public String getConfigId() {
        return configId;
    }

    
    public void setConfigId(String configId) {
        this.configId = configId;
    }

    
    public String getCodeColType() {
        return codeColType;
    }

    
    public void setCodeColType(String codeColType) {
        this.codeColType = codeColType;
    }

    
    public String getDimWhere() {
        return dimWhere;
    }

    
    public void setDimWhere(String dimWhere) {
        this.dimWhere = dimWhere;
    }

    
    public Date getCreateTime() {
        return createTime;
    }

    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    
    public String getCreateUserId() {
        return createUserId;
    }

    
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }
    
    

}
