package com.asiainfo.biapp.si.loc.core.home.entity;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;
import io.swagger.annotations.ApiParam;

import javax.persistence.*;
import java.util.Date;

/**
 * Title : LocUserReadInfo
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
@IdClass(LocUserReadInfoPK.class)
@Table(name = "LOC_USER_READ_INFO")
public class LocUserReadInfo extends BaseEntity {
    @Id
    @Column(name = "USER_ID")
    @ApiParam(value = "用户Id")
    private String userId;

    @Id
    @Column(name = "ANNOUNCEMENT_ID")
    @ApiParam(value = "公告id")
    private String announcementId;

    @Column(name = "READ_TIME")
    @ApiParam(value = "阅读时间")
    private Date readTime;

    @Column(name = "STATUS")
    @ApiParam(value = "状态")
    private Integer status;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(String announcementId) {
        this.announcementId = announcementId;
    }

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
