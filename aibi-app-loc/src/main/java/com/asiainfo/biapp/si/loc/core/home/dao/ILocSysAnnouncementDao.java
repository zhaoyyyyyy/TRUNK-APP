
package com.asiainfo.biapp.si.loc.core.home.dao;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.core.home.entity.LocSysAnnouncement;
import com.asiainfo.biapp.si.loc.core.home.vo.LocSysAnnouncementVo;

/**
 * Title : ILocSysAnnouncementDao
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 7.0 +
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2018年4月20日    hejw3        Created
 * </pre>
 * <p/>
 *
 * @author hejw3
 * @version 1.0.0.2018年4月20日
 */
public interface ILocSysAnnouncementDao extends BaseDao<LocSysAnnouncement, String> {

    /**
     * Descript:查询 and
     *
     * @param locSysAnnouncementVo
     * @return
     */
    Page<LocSysAnnouncement> selectILocSysAnnouncement(Page<LocSysAnnouncement> page,
            LocSysAnnouncementVo locSysAnnouncementVo);
    
    /**
     * 
     * Description:根据id获取系统公告 
     *
     * @return
     */
    LocSysAnnouncement selectLocSysAnnouncementById(String id);

    /**
     * 新增
     *
     * @param locSysAnnouncement
     */
    void insertLocSysAnnouncement(LocSysAnnouncement locSysAnnouncement);

    /**
     * 修改
     *
     * @param locSysAnnouncement
     */
    void updateLocSysAnnouncement(LocSysAnnouncement locSysAnnouncement);

    /**
     * 删除
     *
     * @param locSysAnnouncement
     */
    void deleteLocSysAnnouncement(LocSysAnnouncement locSysAnnouncement);
    
    /**
     * 批量删除   ids格式; ‘1,2,3’
     * Description: 
     *
     * @param ids
     */
    void deleteLocSysAnnouncement(String ids);


    /**
     * 逻辑批量删除   ids格式; ‘1,2,3’
     * Description: 
     *
     * @param ids
     */
    void updateSysAnnouncementStatus(String ids);
    
    /**
     * 模糊搜索 or
     */

    Page<LocSysAnnouncement> searchILocSysAnnouncement(Page<LocSysAnnouncement> page,
            LocSysAnnouncementVo locSysAnnouncementVo);
    
}
