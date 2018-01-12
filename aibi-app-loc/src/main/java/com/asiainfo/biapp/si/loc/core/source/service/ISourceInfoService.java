/*
 * @(#)ISourceInfoService.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.source.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.source.entity.SourceInfo;
import com.asiainfo.biapp.si.loc.core.source.vo.SourceInfoVo;

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
     * Description: 分页查询指标信息
     *
     * @param page
     * @param sourceInfoVo
     * @return
     * @throws BaseException
     */
    public Page<SourceInfo> selectSourceInfoPageList(Page<SourceInfo> page, SourceInfoVo sourceInfoVo)
            throws BaseException;

    /**
     * Description: 查询指标信息列表
     *
     * @param sourceInfoVo
     * @return
     * @throws BaseException
     */
    public List<SourceInfo> selectSourceInfoList(SourceInfoVo sourceInfoVo) throws BaseException;

    /**
     * Description: 通过ID拿到指标信息
     *
     * @param sourceId
     * @return
     * @throws BaseException
     */
    public SourceInfo selectSourceInfoById(String sourceId) throws BaseException;

    /**
     * Description: 新增一个指标信息
     *
     * @param sourceInfo
     * @throws BaseException
     */
    public void addSourceInfo(SourceInfo sourceInfo) throws BaseException;

    /**
     * Description: 修改指标信息
     *
     * @param sourceInfo
     * @throws BaseException
     */
    public void modifySourceInfo(SourceInfo sourceInfo) throws BaseException;

    /**
     * Description: 删除指标信息
     *
     * @param sourceId
     * @throws BaseException
     */
    public void deleteSourceInfo(String sourceId) throws BaseException;
    
    /**
     * 
     * Description: 根据configId查询指标
     *
     * @param configId
     * @return
     */
    public Page<SourceInfo> selectSourceInfoListByConfigId(Page<SourceInfo> page,String configId,String sourceName) throws BaseException;

}
