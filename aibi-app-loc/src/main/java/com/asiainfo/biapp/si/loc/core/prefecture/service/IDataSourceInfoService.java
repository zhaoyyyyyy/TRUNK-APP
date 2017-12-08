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
     * Description: 查询数据源列表
     *
     * @param dataSourceInfoVo
     * @return
     * @throws BaseException
     */
    public List<DataSourceInfo> selectDataSourceInfoList(DataSourceInfoVo dataSourceInfoVo) throws BaseException;

    /**
     * Description: 根据名称查询数据源
     *
     * @param dataSourceName
     * @return
     * @throws BaseException
     */
    public DataSourceInfo selectOneByDataSourceName(String dataSourceName) throws BaseException;;

    /**
     * Description: 通过ID得到数据源
     *
     * @param dataSourceId
     * @return
     * @throws BaseException
     */
    public DataSourceInfo selectDataSourceInfoById(String dataSourceId) throws BaseException;

    /**
     * Description: 新增一个数据源
     *
     * @param dataSourceInfo
     * @throws BaseException
     */
    public void addDataSourceInfo(DataSourceInfo dataSourceInfo) throws BaseException;

    /**
     * Description: 修改数据源
     *
     * @param dataSourceInfo
     * @throws BaseException
     */
    public void modifyDataSourceInfo(DataSourceInfo dataSourceInfo) throws BaseException;;

    /**
     * Description: 删除数据源
     *
     * @param dataSourceId
     * @throws BaseException
     */
    public void deleteDataSourceInfo(String dataSourceId) throws BaseException;
}
