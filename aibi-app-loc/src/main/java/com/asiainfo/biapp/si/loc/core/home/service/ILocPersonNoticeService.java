package com.asiainfo.biapp.si.loc.core.home.service;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.home.entity.LocPersonNotice;
import com.asiainfo.biapp.si.loc.core.home.vo.LocPersonNoticeVo;

/**
 * Title : ILocPersonNoticeService
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
public interface ILocPersonNoticeService extends BaseService<LocPersonNotice, String> {

    Page<LocPersonNotice> selectLocPersonNoticeList(Page<LocPersonNotice> page, LocPersonNoticeVo locPersonNoticeVo) throws BaseException;
    
    int selectUnreadSize();
    
    LocPersonNotice selectLocPersonNoticeById(LocPersonNoticeVo locPersonNoticeVo);

    void insertLocPersonNotice(LocPersonNotice locPersonNotice);

    void updateLocPersonNotice(LocPersonNotice locPersonNotice);

    void deleteLocPersonNotice(LocPersonNotice locPersonNotice);
}
