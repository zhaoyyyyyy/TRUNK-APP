package com.asiainfo.biapp.si.loc.core.home.service.impl;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.core.home.dao.ILocSysAnnouncementDao;
import com.asiainfo.biapp.si.loc.core.home.entity.LocSysAnnouncement;
import com.asiainfo.biapp.si.loc.core.home.service.ILocSysAnnouncementService;
import com.asiainfo.biapp.si.loc.core.home.vo.LocSysAnnouncementVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Title : LocSysAnnouncementServcieImpl
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
@Service
@Transactional
public class LocSysAnnouncementServcieImpl extends BaseServiceImpl<LocSysAnnouncement, String> implements ILocSysAnnouncementService {

    @Autowired
    private ILocSysAnnouncementDao locSysAnnouncementDao;

    @Override
    protected BaseDao<LocSysAnnouncement, String> getBaseDao() {
        return locSysAnnouncementDao;
    }


    @Override
    public Page<LocSysAnnouncement> selectLocSysAnnouncement(Page<LocSysAnnouncement> page, LocSysAnnouncementVo locSysAnnouncementVo) {
        return locSysAnnouncementDao.selectILocSysAnnouncement(page,locSysAnnouncementVo);
    }

    @Override
    public void insertLocSysAnnouncement(LocSysAnnouncement locSysAnnouncement) {
        locSysAnnouncementDao.insertLocSysAnnouncement(locSysAnnouncement);
    }

    @Override
    public void updateLocSysAnnouncement(LocSysAnnouncement locSysAnnouncement) {
        locSysAnnouncementDao.updateLocSysAnnouncement(locSysAnnouncement);
    }

    @Override
    public void deleteLocSysAnnouncement(LocSysAnnouncement locSysAnnouncement) {
        locSysAnnouncementDao.deleteLocSysAnnouncement(locSysAnnouncement);
    }


    /**
     * {@inheritDoc}
     * @see com.asiainfo.biapp.si.loc.core.home.service.ILocSysAnnouncementService#searchLocSysAnnouncement(com.asiainfo.biapp.si.loc.base.page.Page, com.asiainfo.biapp.si.loc.core.home.vo.LocSysAnnouncementVo)
     */
    @Override
    public Page<LocSysAnnouncement> searchLocSysAnnouncement(Page<LocSysAnnouncement> page,
            LocSysAnnouncementVo locSysAnnouncement) {
        return locSysAnnouncementDao.searchILocSysAnnouncement(page, locSysAnnouncement);
        
    }


    /**
     * {@inheritDoc}
     * @see com.asiainfo.biapp.si.loc.core.home.service.ILocSysAnnouncementService#deleteLocSysAnnouncementByIds(java.lang.String)
     */
    @Override
    public void deleteLocSysAnnouncement(String ids) {
        locSysAnnouncementDao.deleteLocSysAnnouncement(ids);
    }

    

    /**
     * {@inheritDoc}
     * @see com.asiainfo.biapp.si.loc.core.home.service.ILocSysAnnouncementService#deleteByUpdateLocSysAnnouncement(java.lang.String)
     */
    @Override
    public void deleteByUpdateLocSysAnnouncement(String ids) {
        locSysAnnouncementDao.updateSysAnnouncementStatus(ids);
    }


    /**
     * {@inheritDoc}
     * @see com.asiainfo.biapp.si.loc.core.home.service.ILocSysAnnouncementService#getLocSysAnnouncementById(java.lang.String)
     */
    @Override
    public LocSysAnnouncement getLocSysAnnouncementById(String id) {
        return locSysAnnouncementDao.selectLocSysAnnouncementById(id);
    }
}
