package com.asiainfo.biapp.si.loc.core.home.dao.impl;

import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.home.dao.ILocPersonNoticeDao;
import com.asiainfo.biapp.si.loc.core.home.entity.LocPersonNotice;
import com.asiainfo.biapp.si.loc.core.home.vo.LocPersonNoticeVo;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title : LocPersonNoticeDaoImpl
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
public class LocPersonNoticeDaoImpl extends BaseDaoImpl<LocPersonNotice, String> implements ILocPersonNoticeDao {
    private static final int FIRST_INDEX = 0;
    
    @Override
    public Page<LocPersonNotice> selectLocPersonNoticeList(Page<LocPersonNotice> page,LocPersonNoticeVo locPersonNoticeVo) {
        Map<String, Object> reMap = fromBean(locPersonNoticeVo);
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");
        return super.findPageByHql(page,reMap.get("hql").toString(), params);
    }


    @Override
    public void insertPersonNoticeList(LocPersonNotice locPersonNotice) {
        super.saveOrUpdate(locPersonNotice);
    }

    @Override
    public void updatePersonNoticeList(LocPersonNotice locPersonNotice) {
        super.update(locPersonNotice);
    }

    @Override
    public void deletePersonNoticeListById(LocPersonNotice locPersonNotice) {
        super.delete(locPersonNotice.getNoticeId());
    }

    private Map<String, Object> fromBean(LocPersonNoticeVo locPersonNoticeVo) {
        Map<String, Object> reMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from LocPersonNotice l where 1=1 ");
        if (StringUtil.isNotBlank(locPersonNoticeVo.getLabelId())) {
            hql.append("and l.labelId = :labelId ");
            params.put("categoryId", locPersonNoticeVo.getLabelId());
        }
        if (StringUtil.isNotBlank(locPersonNoticeVo.getNoticeDetail())) {
            hql.append("and l.noticeDetail = :noticeDetail ");
            params.put("noticeDetail", locPersonNoticeVo.getNoticeDetail());
        }
        if (StringUtil.isNotBlank(locPersonNoticeVo.getNoticeId())) {
            hql.append("and l.noticeId = :noticeId ");
            params.put("noticeId", locPersonNoticeVo.getNoticeId());
        }
        if (StringUtil.isNotBlank(locPersonNoticeVo.getNoticeName())) {
            hql.append("and l.noticeName = :noticeName ");
            params.put("noticeName", locPersonNoticeVo.getNoticeName());
        }
        if (null != locPersonNoticeVo.getNoticeTypeId()) {
            hql.append("and l.noticeTypeId = :noticeTypeId ");
            params.put("noticeTypeId", locPersonNoticeVo.getNoticeTypeId());
        }
        if (null != locPersonNoticeVo.getNoticeSendTime()) {
            hql.append("and l.noticeSendTime = :noticeSendTime ");
            params.put("noticeSendTime", locPersonNoticeVo.getNoticeSendTime());
        }
        if (null != locPersonNoticeVo.getStatus()) {
            hql.append("and l.status = :status ");
            params.put("status", locPersonNoticeVo.getStatus());
        }
        if (StringUtil.isNotBlank(locPersonNoticeVo.getReleaseUserId())) {
            hql.append("and l.releaseUserId = :releaseUserId ");
            params.put("releaseUserId", locPersonNoticeVo.getReleaseUserId());
        }
        if (StringUtil.isNotBlank(locPersonNoticeVo.getReceiveUserId())) {
            hql.append("and l.receiveUserId = :receiveUserId ");
            params.put("receiveUserId", locPersonNoticeVo.getReceiveUserId());
        }
        if (null != locPersonNoticeVo.getReadStatus()) {
            hql.append("and l.readStatus = :readStatus ");
            params.put("readStatus", locPersonNoticeVo.getReadStatus());
        }
        if (null != locPersonNoticeVo.getIsSuccess()) {
            hql.append("and l.isSuccess = :isSuccess ");
            params.put("isSuccess", locPersonNoticeVo.getIsSuccess());
        }
        if (null != locPersonNoticeVo.getIsShowTip()) {
            hql.append("and l.isShowTip = :isShowTip ");
            params.put("isShowTip", locPersonNoticeVo.getIsShowTip());
        }
        hql.append("order by l.noticeSendTime desc ");
        reMap.put("hql", hql);
        reMap.put("params", params);
        return reMap;
    }


    /**
     * {@inheritDoc}
     * @see com.asiainfo.biapp.si.loc.core.home.dao.ILocPersonNoticeDao#selectLocPersonNoticeById(com.asiainfo.biapp.si.loc.base.page.Page, com.asiainfo.biapp.si.loc.core.home.vo.LocPersonNoticeVo)
     */
    @Override
    public LocPersonNotice selectLocPersonNoticeById(LocPersonNoticeVo locPersonNoticeVo) {
        Map<String, Object> reMap = fromBean(locPersonNoticeVo);
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");
        List<LocPersonNotice> locPersonNoticeList = super.findListByHql(reMap.get("hql").toString(), params);
        if (locPersonNoticeList !=null && !locPersonNoticeList.isEmpty()){
            return locPersonNoticeList.get(FIRST_INDEX);
        }
        return null;
    }


    /**
     * {@inheritDoc}
     * @see com.asiainfo.biapp.si.loc.core.home.dao.ILocPersonNoticeDao#selectUnreadSize()
     */
    @Override
    public int selectUnreadSize() {
        String hql = " from LocPersonNotice where readStatus = 2";
        return super.getCount(hql, null);
    }
}
