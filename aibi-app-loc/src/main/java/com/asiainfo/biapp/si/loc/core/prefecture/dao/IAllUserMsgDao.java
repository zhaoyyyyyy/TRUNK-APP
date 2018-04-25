/*
 * @(#)IAllUserMsgDao.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.prefecture.dao;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.core.prefecture.entity.AllUserMsg;
import com.asiainfo.biapp.si.loc.core.prefecture.vo.AllUserMsgVo;

/**
 * Title : IAllUserMsgDao
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2018年1月24日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2018年1月24日
 */
public interface IAllUserMsgDao extends BaseDao<AllUserMsg, String> {

    /**
     * Description: 根据条件分页查询用户全量表
     *
     * @param page
     * @param allUserMsgVo
     * @return
     */
    public Page<AllUserMsg> selectAllUserMsgPageList(Page<AllUserMsg> page, AllUserMsgVo allUserMsgVo);

    /**
     * Description: 根据条件查询用户全量表列表
     *
     * @param allUserMsgVo
     * @return
     */
    public List<AllUserMsg> selectAllUserMsgList(AllUserMsgVo allUserMsgVo);

}
