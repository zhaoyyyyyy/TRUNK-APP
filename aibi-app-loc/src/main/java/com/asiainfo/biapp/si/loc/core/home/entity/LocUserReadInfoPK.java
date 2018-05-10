package com.asiainfo.biapp.si.loc.core.home.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Title : LocUserReadInfoPK
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
@Embeddable
public class LocUserReadInfoPK implements Serializable {
    private String userId;
    private String announcementId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocUserReadInfoPK that = (LocUserReadInfoPK) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        return announcementId != null ? announcementId.equals(that.announcementId) : that.announcementId == null;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (announcementId != null ? announcementId.hashCode() : 0);
        return result;
    }

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
}
