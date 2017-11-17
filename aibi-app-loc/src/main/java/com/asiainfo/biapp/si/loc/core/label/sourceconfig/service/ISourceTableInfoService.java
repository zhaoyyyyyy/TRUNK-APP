/*
 * @(#)ISourceTableInfoService.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.sourceconfig.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.label.sourceconfig.entity.SourceTableInfo;
import com.asiainfo.biapp.si.loc.core.label.sourceconfig.vo.SourceTableInfoVo;

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
     * 根据条件分页查询
     *
     * @param page
     * @param sourceTableInfoVo
     * @return
     */
    public Page<SourceTableInfo> findSourceTableInfoPageList(Page<SourceTableInfo> page,
            SourceTableInfoVo sourceTableInfoVo) throws BaseException;

    /**
     * 根据条件查询列表
     *
     * @param sourceTableInfoVo
     * @return
     */
    public List<SourceTableInfo> findSourceTableInfoList(SourceTableInfoVo sourceTableInfoVo) throws BaseException;

    /**
     * 通过ID得到一个实体
     *
     * @param sourceTableId
     * @return
     * @throws BaseException
     */
    public SourceTableInfo getById(String sourceTableId) throws BaseException;

    /**
     * 新增一个实体
     *
     * @param sourceTableInfo
     * @throws BaseException
     */
    public void saveT(SourceTableInfo sourceTableInfo) throws BaseException;

    /**
     * 修改一个实体
     *
     * @param sourceTableInfo
     * @throws BaseException
     */
    public void updateT(SourceTableInfo sourceTableInfo);

    /**
     * 通过ID删除一个实体
     *
     * @param sourceTableId
     * @throws BaseException
     */
    public void deleteById(String sourceTableId) throws BaseException;

}
