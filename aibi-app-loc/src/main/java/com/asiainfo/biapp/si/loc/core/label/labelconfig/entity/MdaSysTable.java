/*
 * @(#)MdaSysTable.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.labelconfig.entity;

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
 * Title : MdaSysTable
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
 * 1    2017年11月20日    lilin7        Created
 * </pre>
 * <p/>
 *
 * @author lilin7
 * @version 1.0.0.2017年11月20日
 */
@Entity
@Table(name = "LOC_MDA_SYS_TABLE")
public class MdaSysTable extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 元数据表ID
     */
    @Id
    @Column(name = "TABLE_ID")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @ApiParam(value = "表ID")
    private String tableId;

    /**
     * 数据源ID
     */
    @Column(name = "CONFIG_ID")
    @ApiParam(value = "数据源ID")
    private Integer configId;

    /**
     * 表名
     */
    @Column(name = "TABLE_NAME")
    @ApiParam(value = "表名")
    private String tableName;

    /**
     * 表中文名
     */
    @Column(name = "TABLE_CN_NAME")
    @ApiParam(value = "表中文名")
    private String tableCnName;

    /**
     * 表描述
     */
    @Column(name = "TABLE_DESC")
    @ApiParam(value = "表描述")
    private String tableDesc;

    /**
     * 表后缀
     */
    @Column(name = "TABLE_POSTFIX")
    @ApiParam(value = "表后缀")
    private String tablePostfix;

    /**
     * 表规则
     */
    @Column(name = "TABLE_SCHEMA")
    @ApiParam(value = "表规则")
    private String tableSchema;

    /**
     * 宽表类型
     */
    @Column(name = "TABLE_TYPE")
    @ApiParam(value = "宽表类型")
    private Integer tableType;

    /**
     * 更新周期
     */
    @Column(name = "UPDATE_CYCLE")
    @ApiParam(value = "更新周期")
    private Integer updateCycle;

    /**
     * 创建人
     */
    @Column(name = "CREATE_USER_ID")
    @ApiParam(value = "创建人")
    private String createUserId;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    @ApiParam(value = "创建时间")
    private Date createTime;

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableCnName() {
        return tableCnName;
    }

    public void setTableCnName(String tableCnName) {
        this.tableCnName = tableCnName;
    }

    public String getTableDesc() {
        return tableDesc;
    }

    public void setTableDesc(String tableDesc) {
        this.tableDesc = tableDesc;
    }

    public String getTablePostfix() {
        return tablePostfix;
    }

    public void setTablePostfix(String tablePostfix) {
        this.tablePostfix = tablePostfix;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public Integer getTableType() {
        return tableType;
    }

    public void setTableType(Integer tableType) {
        this.tableType = tableType;
    }

    public Integer getUpdateCycle() {
        return updateCycle;
    }

    public void setUpdateCycle(Integer updateCycle) {
        this.updateCycle = updateCycle;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
