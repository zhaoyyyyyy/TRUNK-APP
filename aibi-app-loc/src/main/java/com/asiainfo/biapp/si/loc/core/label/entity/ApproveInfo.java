/*
 * @(#)ApproveInfo.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.entity;

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
 * Title : ApproveInfo
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
 * 1    2017年12月14日    lilin7        Created
 * </pre>
 * <p/>
 *
 * @author lilin7
 * @version 1.0.0.2017年12月14日
 */

@Entity
@Table(name = "LOC_APPROVE_INFO")
public class ApproveInfo extends BaseEntity {

    /**  */
    private static final long serialVersionUID = -8497173141044941345L;

    /**
     * 审批信息Id
     */
    @Id
    @Column(name = "APPROVE_ID")
    @ApiParam(value = "审批信息Id")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String approveId;

    /**
     * 资源Id
     */
    @Column(name = "RESOURCE_ID")
    @ApiParam(value = "资源Id")
    private String resourceId;

    /**
     * 资源名称
     */
    @Column(name = "RESOURCE_NAME")
    @ApiParam(value = "资源名称")
    private String resourceName;

    /**
     * 资源类型Id
     */
    @Column(name = "RESOURCE_TYPE_ID")
    @ApiParam(value = "资源类型Id")
    private String resourceTypeId;

    /**
     * 审批人
     */
    @Column(name = "APPROVE_USER_ID")
    @ApiParam(value = "审批人")
    private String approveUserId;

    /**
     * 审批时间
     */
    @Column(name = "APPROVE_TIME")
    @ApiParam(value = "审批时间")
    private Date approveTime;

    /**
     * 审批意见
     */
    @Column(name = "APPROVE_OPINION")
    @ApiParam(value = "审批意见")
    private String approveOpinion;

    /**
     * 审批状态Id
     */
    @Column(name = "APPROVE_STATUS_ID")
    @ApiParam(value = "审批状态Id")
    private String approveStatusId;

    public String getApproveId() {
        return approveId;
    }

    public void setApproveId(String approveId) {
        this.approveId = approveId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceTypeId() {
        return resourceTypeId;
    }

    public void setResourceTypeId(String resourceTypeId) {
        this.resourceTypeId = resourceTypeId;
    }

    public String getApproveUserId() {
        return approveUserId;
    }

    public void setApproveUserId(String approveUserId) {
        this.approveUserId = approveUserId;
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public String getApproveOpinion() {
        return approveOpinion;
    }

    public void setApproveOpinion(String approveOpinion) {
        this.approveOpinion = approveOpinion;
    }

    public String getApproveStatusId() {
        return approveStatusId;
    }

    public void setApproveStatusId(String approveStatusId) {
        this.approveStatusId = approveStatusId;
    }

}
