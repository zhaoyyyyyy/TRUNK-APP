package com.asiainfo.biapp.si.loc.core.home.dao;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.core.home.entity.LocUserReadInfo;
import com.asiainfo.biapp.si.loc.core.home.entity.LocUserReadInfoPK;
import com.asiainfo.biapp.si.loc.core.home.vo.LocUserReadInfoVo;

/**
 * Title : ILocUserReadInfoDao
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
public interface ILocUserReadInfoDao extends BaseDao<LocUserReadInfo, String> {
    LocUserReadInfo selectLocUserReadInfo(LocUserReadInfoVo locUserReadInfoVo);

    /**
     * 获取总条数
     * Description: 
     *
     * @return
     */
    int selectCount(LocUserReadInfoVo locUserReadInfoVo);
    
    /**
     * 
     * Description: 批量删除 
     *
     * @param announcementIds："'1,2,3'"
     */
    void deleteLocUserReadInfo(String announcementIds);
    
    void insertLocUserReadInfo(LocUserReadInfo locUserReadInfo);

    void updateLocUserReadInfo(LocUserReadInfo locUserReadInfo);

    void deleteLocUserReadInfo(LocUserReadInfoPK locUserReadInfoPK);
}
