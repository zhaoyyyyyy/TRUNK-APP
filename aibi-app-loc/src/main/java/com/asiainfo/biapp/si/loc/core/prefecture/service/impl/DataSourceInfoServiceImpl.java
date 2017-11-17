/*
 * @(#)DataSourceInfoServiceImpl.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.prefecture.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.core.prefecture.dao.IDataSourceInfoDao;
import com.asiainfo.biapp.si.loc.core.prefecture.entity.DataSourceInfo;
import com.asiainfo.biapp.si.loc.core.prefecture.service.IDataSourceInfoService;
import com.asiainfo.biapp.si.loc.core.prefecture.vo.DataSourceInfoVo;

/**
 * Title : DataSourceInfoServiceImpl
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
@Service
@Transactional
public class DataSourceInfoServiceImpl extends BaseServiceImpl<DataSourceInfo, String> implements
        IDataSourceInfoService {

    @Autowired
    private IDataSourceInfoDao iDataSourceInfoDao;

    @Override
    protected BaseDao<DataSourceInfo, String> getBaseDao() {
        return iDataSourceInfoDao;
    }

    /**
     * 根据条件查询数据源列表
     * 
     * @param dataSourceInfo
     * @return
     */
    public List<DataSourceInfo> findDataSourceInfoList(DataSourceInfoVo dataSourceInfoVo) {
        return iDataSourceInfoDao.findDataSourceInfoList(dataSourceInfoVo);
    }

    /**
     * 根据数据源名称查询一个数据源
     * 
     * @param dataSourceName
     * @return
     */
    public DataSourceInfo findOneByDataSourceName(String dataSourceName) {
        return iDataSourceInfoDao.findOneByDataSourceName(dataSourceName);
    }

    /**
     * 通过主键得到实体
     * 
     * @param id
     * @return
     */
    public DataSourceInfo getById(String dataSourceId) throws BaseException {
        if (StringUtils.isBlank(dataSourceId)) {
            throw new ParamRequiredException("ID不能为空");
        }
        return super.get(dataSourceId);
    }

    /**
     * 新增数据源
     *
     * @throws BaseException
     */
    public void saveT(DataSourceInfo dataSourceInfo) {
        super.saveOrUpdate(dataSourceInfo);
    }

    /**
     * 修改数据源
     *
     * @throws BaseException
     */
    public void updateT(DataSourceInfo dataSourceInfo) {
        super.saveOrUpdate(dataSourceInfo);
    }

    /**
     * 删除数据源
     *
     * @param dataSourceId
     * @throws BaseException
     */
    public void deleteById(String dataSourceId) {
        super.delete(dataSourceId);
    }

}
