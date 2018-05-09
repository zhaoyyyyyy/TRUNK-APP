package com.asiainfo.biapp.si.loc.core.home.message.service.impl;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.core.home.message.dao.ILocPersonNoticeDao;
import com.asiainfo.biapp.si.loc.core.home.message.entity.LocPersonNotice;
import com.asiainfo.biapp.si.loc.core.home.message.service.ILocPersonNoticeService;
import com.asiainfo.biapp.si.loc.core.home.message.vo.LocPersonNoticeVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Title : LocPersonNoticeServiceImpl
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
public class LocPersonNoticeServiceImpl extends BaseServiceImpl<LocPersonNotice, String> implements ILocPersonNoticeService {

    @Autowired
    private ILocPersonNoticeDao iLocPersonNoticeDao;

    @Override
    protected BaseDao<LocPersonNotice, String> getBaseDao() {
        return iLocPersonNoticeDao;
    }

    @Override
    public Page<LocPersonNotice> selectLocPersonNoticeList(Page<LocPersonNotice> page, LocPersonNoticeVo locPersonNoticeVo) throws BaseException {
        return iLocPersonNoticeDao.selectLocPersonNoticeList(page,locPersonNoticeVo);
    }

    @Override
    public void insertLocPersonNotice(LocPersonNotice locPersonNotice) {
        iLocPersonNoticeDao.insertPersonNoticeList(locPersonNotice);
    }

    @Override
    public void updateLocPersonNotice(LocPersonNotice locPersonNotice) {
        iLocPersonNoticeDao.updatePersonNoticeList(locPersonNotice);
    }

    @Override
    public void deleteLocPersonNotice(LocPersonNotice locPersonNotice) {
        iLocPersonNoticeDao.deletePersonNoticeListById(locPersonNotice);
    }
}
