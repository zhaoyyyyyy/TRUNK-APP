package com.asiainfo.biapp.si.loc.core.home.service;

import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.home.entity.LocSysAnnouncement;
import com.asiainfo.biapp.si.loc.core.home.vo.LocSysAnnouncementVo;

/**
 * Title : ILocSysAnnouncementService
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
public interface ILocSysAnnouncementService extends BaseService<LocSysAnnouncement, String> {
    Page<LocSysAnnouncement> selectLocSysAnnouncement(Page<LocSysAnnouncement> page, LocSysAnnouncementVo locSysAnnouncement);

    Page<LocSysAnnouncement> searchLocSysAnnouncement(Page<LocSysAnnouncement> page, LocSysAnnouncementVo locSysAnnouncement);
    
    void insertLocSysAnnouncement(LocSysAnnouncement locSysAnnouncement);

    void updateLocSysAnnouncement(LocSysAnnouncement locSysAnnouncement);

    void deleteLocSysAnnouncement(LocSysAnnouncement locSysAnnouncement);

    void deleteLocSysAnnouncement(String ids);

}
