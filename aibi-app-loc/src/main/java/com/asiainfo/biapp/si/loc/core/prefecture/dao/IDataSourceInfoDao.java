/*
 * @(#)IDataSourceInfoDao.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.prefecture.dao;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.core.prefecture.entity.DataSourceInfo;
import com.asiainfo.biapp.si.loc.core.prefecture.vo.DataSourceInfoVo;

/**
 * Title : IDataSourceInfoDao
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
public interface IDataSourceInfoDao extends BaseDao<DataSourceInfo, String> {

    /**
     * 根据条件查询数据源列表
     * 
     * @param dataSourceInfo
     * @return
     */
    public List<DataSourceInfo> selectDataSourceInfoList(DataSourceInfoVo dataSourceInfoVo);

    /**
     * 根据数据源名称查询一个数据源
     * 
     * @param dataSourceName
     * @return
     */
    public DataSourceInfo selectOneByDataSourceName(String dataSourceName);

}
