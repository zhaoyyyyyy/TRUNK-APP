package com.asiainfo.biapp.si.loc.core.home.service.impl;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.core.home.dao.ILocUserReadInfoDao;
import com.asiainfo.biapp.si.loc.core.home.entity.LocUserReadInfo;
import com.asiainfo.biapp.si.loc.core.home.entity.LocUserReadInfoPK;
import com.asiainfo.biapp.si.loc.core.home.service.ILocUserReadInfoService;
import com.asiainfo.biapp.si.loc.core.home.vo.LocUserReadInfoVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Title : LocUserReadInfoServiceImpl
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
public class LocUserReadInfoServiceImpl extends BaseServiceImpl<LocUserReadInfo, String> implements ILocUserReadInfoService {

    @Autowired
    private ILocUserReadInfoDao locUserReadInfoDao;

    @Override
    protected BaseDao<LocUserReadInfo, String> getBaseDao() {
        return locUserReadInfoDao;
    }

    @Override
    public LocUserReadInfo selectLocUserReadInfo(LocUserReadInfoVo locUserReadInfoVo) {
        return locUserReadInfoDao.selectLocUserReadInfo(locUserReadInfoVo);
    }

    @Override
    public void insertLocUserReadInfo(LocUserReadInfo locUserReadInfo) {
        locUserReadInfoDao.insertLocUserReadInfo(locUserReadInfo);
    }

    @Override
    public void updateLocUserReadInfo(LocUserReadInfo locUserReadInfo) {
        locUserReadInfoDao.updateLocUserReadInfo(locUserReadInfo);
    }

    @Override
    public void deleteLocUserReadInfo(LocUserReadInfoPK locUserReadInfoPK) {
        locUserReadInfoDao.delete(locUserReadInfoPK);
    }

    /**
     * {@inheritDoc}
     * @see com.asiainfo.biapp.si.loc.core.home.service.ILocUserReadInfoService#selectTotalSize()
     */
    @Override
    public int selectTotalSize() {
        return locUserReadInfoDao.selectCount();
    }
}
