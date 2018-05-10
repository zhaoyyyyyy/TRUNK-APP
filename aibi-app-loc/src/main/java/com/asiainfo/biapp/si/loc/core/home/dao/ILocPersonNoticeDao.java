package com.asiainfo.biapp.si.loc.core.home.dao;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.core.home.entity.LocPersonNotice;
import com.asiainfo.biapp.si.loc.core.home.vo.LocPersonNoticeVo;

/**
 * Title : ILocPersonNoticeDao
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
public interface ILocPersonNoticeDao extends BaseDao<LocPersonNotice, String> {

    /**
     * Description：获取个人通知列表
     *
     * @param locPersonNoticeVo
     * @return
     */
    Page<LocPersonNotice> selectLocPersonNoticeList(Page<LocPersonNotice> page,LocPersonNoticeVo locPersonNoticeVo);
    
    /**
     * Description：根据通知id获取个人通知
     *
     * @param locPersonNoticeVo
     * @return
     */
    LocPersonNotice selectLocPersonNoticeById(LocPersonNoticeVo locPersonNoticeVo);

    /**
     * Description: 新增信息
     * @param locPersonNotice
     */
    void insertPersonNoticeList(LocPersonNotice locPersonNotice);

    /**
     * Description:修改信息
     * @param locPersonNotice
     */
    void updatePersonNoticeList(LocPersonNotice locPersonNotice);

    /**
     * Description:删除信息
     * @param locPersonNotice
     */
    void deletePersonNoticeListById(LocPersonNotice locPersonNotice);
    
    /**
     * 
     * Description:获取为读数量 
     *
     * @return
     */
    int selectUnreadSize();

}
