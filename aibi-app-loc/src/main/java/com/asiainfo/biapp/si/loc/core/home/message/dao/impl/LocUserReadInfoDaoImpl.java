package com.asiainfo.biapp.si.loc.core.home.message.dao.impl;

import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.home.message.dao.ILocUserReadInfoDao;
import com.asiainfo.biapp.si.loc.core.home.message.entity.LocUserReadInfo;
import com.asiainfo.biapp.si.loc.core.home.message.entity.LocUserReadInfoPK;
import com.asiainfo.biapp.si.loc.core.home.message.vo.LocUserReadInfoVo;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title : LocUserReadInfoDaoImpl
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
public class LocUserReadInfoDaoImpl extends BaseDaoImpl<LocUserReadInfo, String> implements ILocUserReadInfoDao {
    private static final int FIRST_INDEX = 0; 

    @Override
    public LocUserReadInfo selectLocUserReadInfo(LocUserReadInfoVo locUserReadInfoVo) {
        Map<String, Object> reMap = fromBean(locUserReadInfoVo);
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");
        List<LocUserReadInfo> userReadInfoList = super.findListByHql(reMap.get("hql").toString(), params);
        if (null != userReadInfoList && userReadInfoList.size()>0){
            return userReadInfoList.get(FIRST_INDEX);
        }
        return null;
    }

    @Override
    public void insertLocUserReadInfo(LocUserReadInfo locUserReadInfo) {
        super.saveOrUpdate(locUserReadInfo);
    }

    @Override
    public void updateLocUserReadInfo(LocUserReadInfo locUserReadInfo) {
        super.update(locUserReadInfo);
    }

    @Override
    public void deleteLocUserReadInfo(LocUserReadInfoPK locUserReadInfoPK) {
        super.delete(locUserReadInfoPK);
    }

    private Map<String, Object> fromBean(LocUserReadInfoVo locUserReadInfoVo) {
        Map<String, Object> reMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from LocUserReadInfo l where 1=1 ");
        if (StringUtil.isNotBlank(locUserReadInfoVo.getAnnouncementId())) {
            hql.append("and l.announcementId = :announcementId ");
            params.put("announcementId", locUserReadInfoVo.getAnnouncementId());
        }
        if (StringUtil.isNotBlank(locUserReadInfoVo.getUserId())) {
            hql.append("and l.userId = :userId ");
            params.put("userId", locUserReadInfoVo.getUserId());
        }
        if (null != locUserReadInfoVo.getReadTime()) {
            hql.append("and l.readTime = :readTime ");
            params.put("readTime", locUserReadInfoVo.getReadTime());
        }
        if (null != locUserReadInfoVo.getStatus()) {
            hql.append(" and l.status= :status ");
            params.put("status", locUserReadInfoVo.getStatus());
        }
        hql.append(" order by l.readTime desc");
        reMap.put("hql", hql);
        reMap.put("params", params);
        return reMap;
    }

}
