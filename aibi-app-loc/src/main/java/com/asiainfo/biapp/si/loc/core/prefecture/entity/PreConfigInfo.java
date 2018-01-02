/*
 * @(#)PreConfigInfo.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.prefecture.entity;

import io.swagger.annotations.ApiParam;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;

/**
 * Title : PreConfigInfo
 * <p/>
 * Description : 专区信息
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8 +
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2017年11月7日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月7日
 */

@Entity
@Table(name = "LOC_PRE_CONFIG_INFO")
public class PreConfigInfo extends BaseEntity {

    private static final long serialVersionUID = 2035013017939483936L;

    /**
     * 主键 专区ID
     */
    @Id
    @Column(name = "CONFIG_ID")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @ApiParam(value = "专区ID")
    private String configId;

    /**
     * 组织编码
     */
    @Column(name = "ORG_ID")
    @ApiParam(value = "组织编码")
    private String orgId;

    /**
     * 专区类型 0：地市专区 1：提供给移动内部 2：不移动内部
     */
    @Column(name = "DATA_ACCESS_TYPE")
    @ApiParam(value = "专区类型")
    private Integer dataAccessType;

    /**
     * 专区名称
     */
    @Column(name = "SOURCE_NAME")
    @ApiParam(value = "专区名称")
    private String sourceName;

    /**
     * 专区英文名
     */
    @Column(name = "SOURCE_EN_NAME")
    @ApiParam(value = "专区英文名")
    private String sourceEnName;

    /**
     * 合同名称
     */
    @Column(name = "CONTRACT_NAME")
    @ApiParam(value = "合同名称")
    private String contractName;

    /**
     * 专区描述
     */
    @Column(name = "CONFIG_DESC")
    @ApiParam(value = "专区描述")
    private String configDesc;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    @ApiParam(value = "创建时间")
    private Date createTime;

    /**
     * 失效时间
     */
    @Column(name = "INVALID_TIME")
    @ApiParam(value = "失效时间")
    private Date invalidTime;

    /**
     * 状态 0.未生效 1.已启用 2.已停用 3.已下线 4.已删除
     */
    @Column(name = "CONFIG_STATUS")
    @ApiParam(value = "状态")
    private Integer configStatus;

    @Transient
    private String createUserId;

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Integer getDataAccessType() {
        return dataAccessType;
    }

    public void setDataAccessType(Integer dataAccessType) {
        this.dataAccessType = dataAccessType;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceEnName() {
        return sourceEnName;
    }

    public void setSourceEnName(String sourceEnName) {
        this.sourceEnName = sourceEnName;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getConfigDesc() {
        return configDesc;
    }

    public void setConfigDesc(String configDesc) {
        this.configDesc = configDesc;
    }

    public String getCreateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        return formatter.format(createTime);
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }

    public Integer getConfigStatus() {
        return configStatus;
    }

    public void setConfigStatus(Integer configStatus) {
        this.configStatus = configStatus;
    }

}
