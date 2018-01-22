/*
 * @(#)ILabelPushReqService.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelPushReq;
import com.asiainfo.biapp.si.loc.core.syspush.vo.LabelPushReqVo;

/**
 * Title : ILabelPushReqService
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
public interface ILabelPushReqService extends BaseService<LabelPushReq, String>{

    /**
     * 根据条件分页查询 
     *
     * @param page
     * @param LabelPushReq
     * @return
     */
    public Page<LabelPushReq> selectLabelPushReqPageList(Page<LabelPushReq> page, LabelPushReqVo labelPushReqVo) throws BaseException;
    
    /**
     * 
     * 根据条件查询列表
     *
     * @param labelPushReqVo
     * @return
     */
    public List<LabelPushReq> selectLabelPushReqList(LabelPushReqVo labelPushReqVo) throws BaseException;
    
    /**
     * 通过ID得到一个实体 Description:
     *
     * @param reqId
     * @return
     * @throws BaseException
     */
    public LabelPushReq selectLabelPushReqById(String reqId) throws BaseException;
    
    /**
     * 新增或修改一个实体 Description:
     *
     * @param labelPushReq
     * @throws BaseException
     */
    public void addLabelPushReq(LabelPushReq labelPushReq) throws BaseException;

    /**
     * 修改一个实体 Description:
     *
     * @param labelPushReq
     * @throws BaseException
     */
    public void modifyLabelPushReq(LabelPushReq labelPushReq) throws BaseException;

    /**
     * 通过ID删除一个实体 Description:
     *
     * @param reqId
     * @throws BaseException
     */
    public void deleteLabelPushReqById(String reqId) throws BaseException;
}
