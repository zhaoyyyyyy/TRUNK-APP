package com.asiainfo.biapp.si.loc.core.home.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.home.dao.ILocUserReadInfoDao;
import com.asiainfo.biapp.si.loc.core.home.entity.LocUserReadInfo;
import com.asiainfo.biapp.si.loc.core.home.entity.LocUserReadInfoPK;
import com.asiainfo.biapp.si.loc.core.home.vo.LocUserReadInfoVo;

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
    
    /*@Override
    public LocUserReadInfo selectLocUserReadInfo(LocUserReadInfoVo locUserReadInfoVo) {
        Map<String, Object> reMap = fromBean(locUserReadInfoVo);
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");
        List<LocUserReadInfo> userReadInfoList = super.findListByHql(reMap.get("hql").toString(), params);
        if (null != userReadInfoList && !userReadInfoList.isEmpty()){
            return userReadInfoList.get(FIRST_INDEX);
        }
        return null;
    }*/
    
    @Override
	public LocUserReadInfo selectLocUserReadInfo(LocUserReadInfoVo locUserReadInfoVo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertLocUserReadInfo(LocUserReadInfo locUserReadInfo) {
		// TODO Auto-generated method stub
		
	}

	/*@Override
    public void insertLocUserReadInfo(LocUserReadInfo locUserReadInfo) {
        super.saveOrUpdate(locUserReadInfo);
    }
*/
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
        if (null != locUserReadInfoVo){
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
            reMap.put("params", params);
        } else {
            reMap.put("params", null);
        }
        hql.append(" order by l.readTime desc");
        reMap.put("hql", hql);
       
        return reMap;
    }

    /**
     * {@inheritDoc}
     * @see com.asiainfo.biapp.si.loc.core.home.dao.ILocUserReadInfoDao#selectCount()
     */
    @Override
    public int selectCount(LocUserReadInfoVo locUserReadInfoVo) {
        Map<String, Object> reMap = fromBean(locUserReadInfoVo);
        if (reMap.get("params") == null) {
            return super.getCount(reMap.get("hql").toString(), null);
        }
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");
        return super.getCount(reMap.get("hql").toString(), params);
    }

    /**
     * {@inheritDoc}
     * @see com.asiainfo.biapp.si.loc.core.home.dao.ILocUserReadInfoDao#deleteLocUserReadInfo(java.lang.String)
     */
    @Override
    public void deleteLocUserReadInfo(String announcementIds) {
        if (StringUtil.isNotEmpty(announcementIds)){
            String sql = "delete from LOC_USER_READ_INFO where ANNOUNCEMENT_ID in (" +announcementIds + ")"; 
            super.excuteSql(sql, null);
        }
    }

}
