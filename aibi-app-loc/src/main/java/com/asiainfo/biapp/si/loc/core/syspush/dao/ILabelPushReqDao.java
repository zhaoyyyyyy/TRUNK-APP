/*
 * @(#)LabelPushReqDao.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.dao;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelPushReq;
import com.asiainfo.biapp.si.loc.core.syspush.vo.LabelPushReqVo;

/**
 * Title : LabelPushReqDao
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2018年1月18日    wangrd        Created</pre>
 * <p/>
 *
 * @author  wangrd
 * @version 1.0.0.2018年1月18日
 */
public interface ILabelPushReqDao extends BaseDao<LabelPushReq, String>{
    
    /**
     * 根据条件分页查询
     *
     * @param page
     * @param LabelPushReq
     * @return
     */
    public Page<LabelPushReq> selectLabelPushReqPageList(Page<LabelPushReq> page, LabelPushReqVo labelPushReqVo);

    /**
     * 根据条件查询列表
     *
     * @param LabelPushReq
     * @return
     */
    public List<LabelPushReq> selectLabelPushReqList(LabelPushReqVo labelPushReqVo);

}
