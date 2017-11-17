/*
 * @(#)ISourceInfoService.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.sourceconfig.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.label.sourceconfig.entity.SourceInfo;
import com.asiainfo.biapp.si.loc.core.label.sourceconfig.vo.SourceInfoVo;

/**
 * Title : ISourceInfoService
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2017年11月15日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月15日
 */
public interface ISourceInfoService extends BaseService<SourceInfo, String> {

    /**
     * 根据条件分页查询
     *
     * @param page
     * @param sourceInfoVo
     * @return
     */
    public Page<SourceInfo> findSourceInfoPageList(Page<SourceInfo> page, SourceInfoVo sourceInfoVo)
            throws BaseException;

    /**
     * 根据条件查询列表
     *
     * @param sourceInfoVo
     * @return
     */
    public List<SourceInfo> findSourceInfoList(SourceInfoVo sourceInfoVo) throws BaseException;

    /**
     * 通过ID得到一个实体 Description:
     *
     * @param sourceId
     * @return
     * @throws BaseException
     */
    public SourceInfo getById(String sourceId) throws BaseException;

    /**
     * 新增或修改一个实体 Description:
     *
     * @param sourceInfo
     * @throws BaseException
     */
    public void saveT(SourceInfo sourceInfo) throws BaseException;

    /**
     * 修改一个实体 Description:
     *
     * @param sourceInfo
     * @throws BaseException
     */
    public void updateT(SourceInfo sourceInfo);

    /**
     * 通过ID删除一个实体 Description:
     *
     * @param sourceId
     * @throws BaseException
     */
    public void deleteById(String sourceId) throws BaseException;

}
