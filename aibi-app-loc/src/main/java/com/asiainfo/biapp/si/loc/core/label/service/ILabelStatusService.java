/*
 * @(#)ILabelStatusService.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelStatus;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelStatusVo;

/**
 * Title : ILabelStatusService
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2017年11月20日     wangrd        Created</pre>
 * <p/>
 *
 * @author   wangrd
 * @version 1.0.0.2017年11月20日
 */
public interface ILabelStatusService extends BaseService<LabelStatus, String>{
    
    /**
     * 根据条件分页查询
     * 
     * @param page
     * @param LabelStatusVo
     * @return 
     */
    public Page<LabelStatus> selectLabelStatusPageList(Page<LabelStatus> page, LabelStatusVo labelStatusVo) throws BaseException;

    /**
     * 根据条件查询列表
     * 
     * @param LabelStatusVo
     * @return
     */
    public List<LabelStatus> selectLabelStatusList(LabelStatusVo labelStatusVo) throws BaseException;
    
    /**
     * 通过ID得到一个实体类Description
     * 
     * @param labelId
     * @return 
     * @throws BaseException
     */
    public LabelStatus selectLabelStatusById(String labelId) throws BaseException;
    
    /**
     * 新增或修改一个实体 Description:
     *
     * @param labelStatus
     * @throws BaseException
     */
    public void addLabelStatus(LabelStatus labelStatus) throws BaseException;
    
    /**
     * 修改一个实体 Description:
     *
     * @param labelStatus
     * @throws BaseException
     */
    public void modifyLabelStatus(LabelStatus labelStatus) throws BaseException;
    
    /**
     * 通过ID删除一个实体 Description:
     *
     * @param labelId
     * @throws BaseException
     */
    public void deleteLabelStatusById(String labelId) throws BaseException;
}
