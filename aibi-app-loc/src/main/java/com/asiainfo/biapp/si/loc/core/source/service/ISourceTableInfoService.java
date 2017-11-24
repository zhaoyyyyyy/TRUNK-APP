/*
 * @(#)ISourceTableInfoService.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.source.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.source.entity.SourceTableInfo;
import com.asiainfo.biapp.si.loc.core.source.vo.SourceTableInfoVo;

/**
 * Title : ISourceTableInfoService
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
public interface ISourceTableInfoService extends BaseService<SourceTableInfo, String> {

    /**
     * 
     * Description: 分页查询指标数据源信息配置
     *
     * @param page
     * @param sourceTableInfoVo
     * @return
     * @throws BaseException
     */
    public Page<SourceTableInfo> findSourceTableInfoPageList(Page<SourceTableInfo> page,
            SourceTableInfoVo sourceTableInfoVo) throws BaseException;

    /**
     * 
     * Description: 查询指标数据源信息列表
     *
     * @param sourceTableInfoVo
     * @return
     * @throws BaseException
     */
    public List<SourceTableInfo> findSourceTableInfoList(SourceTableInfoVo sourceTableInfoVo) throws BaseException;

    /**
     * 
     * Description: 通过ID拿到指标数据源信息配置
     *
     * @param sourceTableId
     * @return
     * @throws BaseException
     */
    public SourceTableInfo getById(String sourceTableId) throws BaseException;

    /**
     * 
     * Description: 新增一个指标数据源信息配置
     *
     * @param sourceTableInfo
     * @throws BaseException
     */
    public void saveT(SourceTableInfo sourceTableInfo) throws BaseException;

    /**
     * 
     * Description: 修改指标数据源信息配置
     *
     * @param sourceTableInfo
     * @throws BaseException
     */
    public void updateT(SourceTableInfo sourceTableInfo) throws BaseException;

    /**
     * 
     * Description: 删除指标数据源信息配置
     *
     * @param sourceTableId
     * @throws BaseException
     */
    public void deleteById(String sourceTableId) throws BaseException;

}
