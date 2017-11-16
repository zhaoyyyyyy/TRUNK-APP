/*
 * @(#)IDataSourceInfoService.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.prefecture.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.prefecture.entity.DataSourceInfo;
import com.asiainfo.biapp.si.loc.core.prefecture.vo.DataSourceInfoVo;

/**
 * Title : IDataSourceInfoService
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8 +
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2017年11月7日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月7日
 */
public interface IDataSourceInfoService extends BaseService<DataSourceInfo, String> {

    /**
     * 根据条件查询数据源列表
     * 
     * @param dataSourceInfo
     * @return
     */
    public List<DataSourceInfo> findDataSourceInfoList(DataSourceInfoVo dataSourceInfoVo) throws BaseException;

    /**
     * 根据数据源名称查询一个数据源
     * 
     * @param dataSourceName
     * @return
     */
    public DataSourceInfo findOneByDataSourceName(String dataSourceName);

    /**
     * 通过主键得到实体
     * 
     * @param id
     * @return
     */
    public DataSourceInfo getById(String dataSourceId) throws BaseException;

    /**
     * 新增数据源
     *
     * @throws BaseException
     */
    public void saveT(DataSourceInfo dataSourceInfo) throws BaseException;

    /**
     * 修改数据源
     *
     * @throws BaseException
     */
    public void updateT(DataSourceInfo dataSourceInfo);

    /**
     * 删除数据源
     *
     * @param dataSourceId
     * @throws BaseException
     */
    public void deleteById(String dataSourceId) throws BaseException;
}
