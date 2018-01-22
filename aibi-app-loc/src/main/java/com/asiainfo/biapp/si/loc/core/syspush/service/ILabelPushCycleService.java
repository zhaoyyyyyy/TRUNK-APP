/*
 * @(#)ILabelPushCycleService.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelPushCycle;
import com.asiainfo.biapp.si.loc.core.syspush.vo.LabelPushCycleVo;

/**
 * Title : ILabelPushCycleService
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
 * <pre>1    2018年1月17日    wangrd        Created</pre>
 * <p/>
 *
 * @author  wangrd
 * @version 1.0.0.2018年1月17日
 */
public interface ILabelPushCycleService extends BaseService<LabelPushCycle, String>{

    /**
     * 根据条件分页查询 
     *
     * @param page
     * @param labelPushCycle
     * @return
     */
    public Page<LabelPushCycle> selectLabelPushCyclePageList(Page<LabelPushCycle> page, LabelPushCycleVo labelPushCycleVo) throws BaseException;
    
    /**
     * 
     * 根据条件查询列表
     *
     * @param labelPushCycleVo
     * @return
     */
    public List<LabelPushCycle> selectLabelPushCycleList(LabelPushCycleVo labelPushCycleVo) throws BaseException;
    
    /**
     * 通过ID得到一个实体 Description:
     *
     * @param recodeId
     * @return
     * @throws BaseException
     */
    public LabelPushCycle selectLabelPushCycleById(String recodeId) throws BaseException;
    
    /**
     * 新增或修改一个实体 Description:
     *
     * @param labelPushCycle
     * @throws BaseException
     */
    public void addLabelPushCycle(LabelPushCycle labelPushCycle) throws BaseException;

    /**
     * 修改一个实体 Description:
     *
     * @param labelPushCycle
     * @throws BaseException
     */
    public void modifyLabelPushCycle(LabelPushCycle labelPushCycle) throws BaseException;

    /**
     * 通过ID删除一个实体 Description:
     *
     * @param recodeId
     * @throws BaseException
     */
    public void deleteLabelPushCycleById(String recodeId) throws BaseException;
}