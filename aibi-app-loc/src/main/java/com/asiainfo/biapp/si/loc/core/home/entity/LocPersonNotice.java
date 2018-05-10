package com.asiainfo.biapp.si.loc.core.home.entity;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;
import io.swagger.annotations.ApiParam;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;

/**
 * Title : LocPersonNotice
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 7.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2018年4月20日    hejw3        Created</pre>
 * <p/>
 *
 * @author hejw3
 * @version 1.0.0.2018年4月20日
 */
@Entity
@Table(name = "LOC_PERSON_NOTICE")
public class LocPersonNotice extends BaseEntity {


    @Id
    @Column(name = "NOTICE_ID")
    @ApiParam(value = "通知ID ")
    private String noticeId;

    @Column(name = "NOTICE_NAME")
    @ApiParam(value = "通知标题")
    private String noticeName;

    @Column(name = "NOTICE_DETAIL")
    @ApiParam(value = "通知内容")
    private String noticeDetail;

    @Column(name = "NOTICE_TYPE_ID")
    @ApiParam(value = "通知类型Id")
    private Integer noticeTypeId;

    @Column(name = "NOTICE_SEND_TIME")
    @ApiParam(value = "通知发送时间")
    private Date noticeSendTime;

    @Column(name = "LABEL_ID")
    @ApiParam(value = "标签ID")
    private String labelId;

    @Column(name = "STATUS")
    @ApiParam(value = "状态")
    private Integer status;

    @Column(name = "RELEASE_USER_ID")
    @ApiParam(value = "发布人")
    private String releaseUserId;

    @Column(name = "IS_SUCCESS")
    @ApiParam(value = "成功失败标识")
    private Integer isSuccess;

    @Column(name = "RECEIVE_USER_ID")
    @ApiParam(value = "接收用户ID")
    private String receiveUserId;

    @Column(name = "READ_STATUS")
    @ApiParam(value = "读取状态")
    private Integer readStatus;

    @Column(name = "IS_SHOW_TIP")
    @ApiParam(value = "是否在主页提示")
    private Integer isShowTip;

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeName() {
        return noticeName;
    }

    public void setNoticeName(String noticeName) {
        this.noticeName = noticeName;
    }

    public String getNoticeDetail() {
        return noticeDetail;
    }

    public void setNoticeDetail(String noticeDetail) {
        this.noticeDetail = noticeDetail;
    }

    public Integer getNoticeTypeId() {
        return noticeTypeId;
    }

    public void setNoticeTypeId(Integer noticeTypeId) {
        this.noticeTypeId = noticeTypeId;
    }

    public Date getNoticeSendTime() {
        return noticeSendTime;
    }

    public void setNoticeSendTime(Date noticeSendTime) {
        this.noticeSendTime = noticeSendTime;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getReleaseUserId() {
        return releaseUserId;
    }

    public void setReleaseUserId(String releaseUserId) {
        this.releaseUserId = releaseUserId;
    }

    public Integer getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Integer isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(String receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public Integer getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Integer readStatus) {
        this.readStatus = readStatus;
    }

    public Integer getIsShowTip() {
        return isShowTip;
    }

    public void setIsShowTip(Integer isShowTip) {
        this.isShowTip = isShowTip;
    }
    
    
    
    @Transient
    private int unreadSize;

    
    /**
     * Description: the unreadSize to get
     *
     * @return the unreadSize
     * @see LocPersonNotice#unreadSize
     */
    public int getUnreadSize() {
        return unreadSize;
    }

    
    /**
     * Description: the unreadSize to set
     *
     * @param unreadSize
     * @see LocPersonNotice#unreadSize
     */
    public void setUnreadSize(int unreadSize) {
        this.unreadSize = unreadSize;
    }
    
}
