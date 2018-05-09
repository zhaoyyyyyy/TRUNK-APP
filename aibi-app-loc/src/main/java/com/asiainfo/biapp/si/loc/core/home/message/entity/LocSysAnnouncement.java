
package com.asiainfo.biapp.si.loc.core.home.message.entity;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;
import com.asiainfo.biapp.si.loc.core.home.message.vo.LocSysAnnouncementVo;

import io.swagger.annotations.ApiParam;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;

/**
 * Title : LocSysAnnouncement
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 7.0 +
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2018年4月20日    hejw3        Created
 * </pre>
 * <p/>
 *
 * @author hejw3
 * @version 1.0.0.2018年4月20日
 */
@Entity
@Table(name = "LOC_SYS_ANNOUNCEMENT")
public class LocSysAnnouncement extends BaseEntity {

    @Id
    @Column(name = "ANNOUNCEMENT_ID")
    @ApiParam(value = "公告id")
    private String announcementId;

    @Column(name = "ANNOUNCEMENT_NAME")
    @ApiParam(value = "公告标题")
    private String announcementName;

    @Column(name = "ANNOUNCEMENT_DETAIL")
    @ApiParam(value = "公告内容")
    private String announcementDetail;

    @Column(name = "TYPE_ID")
    @ApiParam(value = "公告类型Id")
    private Integer typeId;

    @Column(name = "PRIORITY_ID")
    @ApiParam(value = "优先级Id")
    private Integer priorityId;

    @Column(name = "EFFECTIVE_TIME")
    @ApiParam(value = "有效截止时间")
    private Date effectiveTime;

    @Column(name = "STATUS")
    @ApiParam(value = "状态")
    private Integer status;

    @Column(name = "RELEASE_DATE")
    @ApiParam(value = "发布日期")
    private Date releaseDate;

    @Column(name = "RELEASE_USER_ID")
    @ApiParam(value = "发布人")
    private String releaseUserId;

    @Column(name = "IS_SUCCESS")
    @ApiParam(value = "成功失败标识")
    private Integer isSuccess;

    public String getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(String announcementId) {
        this.announcementId = announcementId;
    }

    public String getAnnouncementName() {
        return announcementName;
    }

    public void setAnnouncementName(String announcementName) {
        this.announcementName = announcementName;
    }

    public String getAnnouncementDetail() {
        return announcementDetail;
    }

    public void setAnnouncementDetail(String announcementDetail) {
        this.announcementDetail = announcementDetail;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(Integer priorityId) {
        this.priorityId = priorityId;
    }

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
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
    @Transient
    private Integer readStatus;// 0未读，1已读

    /**
     * Description: the readStatus to get
     *
     * @return the readStatus
     * @see LocSysAnnouncementVo#readStatus
     */
    public Integer getReadStatus() {
        return readStatus;
    }

    /**
     * Description: the readStatus to set
     *
     * @param readStatus
     * @see LocSysAnnouncementVo#readStatus
     */
    public void setReadStatus(Integer readStatus) {
        this.readStatus = readStatus;
    }
}
