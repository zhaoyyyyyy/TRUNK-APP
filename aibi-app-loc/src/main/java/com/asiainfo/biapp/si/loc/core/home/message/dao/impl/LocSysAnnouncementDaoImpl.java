package com.asiainfo.biapp.si.loc.core.home.message.dao.impl;

import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.home.message.dao.ILocSysAnnouncementDao;
import com.asiainfo.biapp.si.loc.core.home.message.entity.LocSysAnnouncement;
import com.asiainfo.biapp.si.loc.core.home.message.vo.LocSysAnnouncementVo;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Title : LocSysAnnouncementDaoImpl
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
@Repository
public class LocSysAnnouncementDaoImpl extends BaseDaoImpl<LocSysAnnouncement, String> implements ILocSysAnnouncementDao {
    @Override
    public Page<LocSysAnnouncement> selectILocSysAnnouncement(Page<LocSysAnnouncement> page, LocSysAnnouncementVo locSysAnnouncementVo) {
        Map<String, Object> reMap = fromBean(locSysAnnouncementVo);
        @SuppressWarnings("unchecked")
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");
        return super.findPageByHql(page, reMap.get("hql").toString(), params);
    }

    @Override
    public void insertLocSysAnnouncement(LocSysAnnouncement locSysAnnouncement) {
        super.saveOrUpdate(locSysAnnouncement);
    }

    @Override
    public void updateLocSysAnnouncement(LocSysAnnouncement locSysAnnouncement) {
        super.update(locSysAnnouncement);
    }

    @Override
    public void deleteLocSysAnnouncement(LocSysAnnouncement locSysAnnouncement) {
        super.delete(locSysAnnouncement.getAnnouncementId());
    }


    private Map<String, Object> fromBean(LocSysAnnouncementVo locSysAnnouncementVo) {
        Map<String, Object> reMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from LocSysAnnouncement l where 1=1 ");
        if (StringUtil.isNotBlank(locSysAnnouncementVo.getAnnouncementId())) {
            hql.append("and l.announcementId = :announcementId ");
            params.put("announcementId", locSysAnnouncementVo.getAnnouncementId());
        }
        if (StringUtil.isNotBlank(locSysAnnouncementVo.getAnnouncementName())) {
            hql.append("and l.announcementName = :announcementName ");
            params.put("announcementName", locSysAnnouncementVo.getAnnouncementName());
        }
        if (StringUtil.isNotBlank(locSysAnnouncementVo.getAnnouncementDetail())) {
            hql.append("and l.announcementDetail = :announcementDetail ");
            params.put("announcementDetail", locSysAnnouncementVo.getAnnouncementDetail());
        }
        if (null != locSysAnnouncementVo.getTypeId()) {
            hql.append("and l.typeId = :typeId ");
            params.put("typeId", locSysAnnouncementVo.getTypeId());
        }
        if (null != locSysAnnouncementVo.getPriorityId()) {
            hql.append("and l.priorityId = :priorityId ");
            params.put("priorityId", locSysAnnouncementVo.getPriorityId());
        }
        if (null != locSysAnnouncementVo.getEffectiveTime()) {
            hql.append("and l.effectiveTime = :effectiveTime ");
            params.put("effectiveTime", locSysAnnouncementVo.getEffectiveTime());
        }
        if (null != locSysAnnouncementVo.getStatus()) {
            hql.append("and l.status = :status ");
            params.put("status", locSysAnnouncementVo.getStatus());
        }
        if (null != locSysAnnouncementVo.getReleaseDate()) {
            hql.append("and l.releaseDate = :releaseDate ");
            params.put("releaseDate", locSysAnnouncementVo.getReleaseDate());
        }
        if (StringUtil.isNotBlank(locSysAnnouncementVo.getReleaseUserId())) {
            hql.append("and l.releaseUserId = :releaseUserId ");
            params.put("releaseUserId", locSysAnnouncementVo.getReleaseUserId());
        }
        if (null != locSysAnnouncementVo.getIsSuccess()) {
            hql.append("and l.isSuccess = :isSuccess ");
            params.put("isSuccess", locSysAnnouncementVo.getIsSuccess());
        }
        hql.append("Order by l.releaseDate Desc");
        reMap.put("hql", hql);
        reMap.put("params", params);
        return reMap;
    }

    /**
     * {@inheritDoc}
     * @see com.asiainfo.biapp.si.loc.core.home.message.dao.ILocSysAnnouncementDao#searchILocSysAnnouncement(com.asiainfo.biapp.si.loc.base.page.Page, com.asiainfo.biapp.si.loc.core.home.message.vo.LocSysAnnouncementVo)
     */
    @Override
    public Page<LocSysAnnouncement> searchILocSysAnnouncement(Page<LocSysAnnouncement> page,
            LocSysAnnouncementVo locSysAnnouncementVo) {
        Map<String, Object> reMap = searchFromBean(locSysAnnouncementVo);
        @SuppressWarnings("unchecked")
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");
        return super.findPageByHql(page, reMap.get("hql").toString(), params);
    }
    
    private Map<String, Object> searchFromBean(LocSysAnnouncementVo locSysAnnouncementVo) {
        Map<String, Object> reMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from LocSysAnnouncement l where 1=1 ");
        if (StringUtil.isNotBlank(locSysAnnouncementVo.getAnnouncementName())) {
            hql.append("and l.announcementName like :announcementName ");
            params.put("announcementName", "%"+locSysAnnouncementVo.getAnnouncementName()+"%");
        }
        if (StringUtil.isNotBlank(locSysAnnouncementVo.getAnnouncementDetail())) {
            hql.append("or l.announcementDetail like :announcementDetail ");
            params.put("announcementDetail", "%"+locSysAnnouncementVo.getAnnouncementDetail()+"%");
        }
        hql.append("Order by l.releaseDate Desc");
        reMap.put("hql", hql);
        reMap.put("params", params);
        return reMap;
    }
    
    
    
}
