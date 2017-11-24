/*
 * @(#)DataSourceInfoDaoImpl.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.prefecture.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.core.prefecture.dao.IDataSourceInfoDao;
import com.asiainfo.biapp.si.loc.core.prefecture.entity.DataSourceInfo;
import com.asiainfo.biapp.si.loc.core.prefecture.vo.DataSourceInfoVo;

/**
 * Title : DataSourceInfoDaoImpl
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
@Repository
public class DataSourceInfoDaoImpl extends BaseDaoImpl<DataSourceInfo, String> implements IDataSourceInfoDao {

    public List<DataSourceInfo> findDataSourceInfoList(DataSourceInfoVo dataSourceInfoVo) {
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from DataSourceInfo d where 1=1 ");
        if (StringUtils.isNotBlank(dataSourceInfoVo.getDataSourceId())) {
            hql.append("and d.dataSourceId = :dataSourceId ");
            params.put("dataSourceId", dataSourceInfoVo.getDataSourceId());
        }
        if (StringUtils.isNotBlank(dataSourceInfoVo.getConfigId())) {
            hql.append("and d.configId = :configId ");
            params.put("configId", dataSourceInfoVo.getConfigId());
        }
        if (StringUtils.isNotBlank(dataSourceInfoVo.getDataSourceName())) {
            hql.append("and d.dataSourceName = :dataSourceName ");
            params.put("dataSourceName", dataSourceInfoVo.getDataSourceName());
        }
        if (null != dataSourceInfoVo.getDataSourceType()) {
            hql.append("and d.dataSourceType = :dataSourceType ");
            params.put("dataSourceType", dataSourceInfoVo.getDataSourceType());
        }
        if (StringUtils.isNotBlank(dataSourceInfoVo.getDbType())) {
            hql.append("and d.dbType = :dbType ");
            params.put("dbType", dataSourceInfoVo.getDbType());
        }
        if (StringUtils.isNotBlank(dataSourceInfoVo.getDbDriver())) {
            hql.append("and d.dbDriver = :dbDriver ");
            params.put("dbDriver", dataSourceInfoVo.getDbDriver());
        }
        if (StringUtils.isNotBlank(dataSourceInfoVo.getDbUrl())) {
            hql.append("and d.dvUrl = :dvUrl ");
            params.put("dvUrl", dataSourceInfoVo.getDbUrl());
        }
        if (StringUtils.isNotBlank(dataSourceInfoVo.getDbUsername())) {
            hql.append("and d.dbUsername = :dbUsername ");
            params.put("dbUsername", dataSourceInfoVo.getDbUsername());
        }
        if (StringUtils.isNotBlank(dataSourceInfoVo.getDbPassword())) {
            hql.append("and d.dbPassword = :dbPassword ");
            params.put("dbPassword", dataSourceInfoVo.getDbPassword());
        }
        if (null != dataSourceInfoVo.getIsLocal()) {
            hql.append("and d.isLocal = :isLocal ");
            params.put("isLocal", dataSourceInfoVo.getIsLocal());
        }
        if (StringUtils.isNotBlank(dataSourceInfoVo.getFtpAdd())) {
            hql.append("and d.ftpAdd = :ftpAdd ");
            params.put("ftpAdd", dataSourceInfoVo.getFtpAdd());
        }
        if (StringUtils.isNotBlank(dataSourceInfoVo.getFtpPoint())) {
            hql.append("and d.ftpPoint = :ftpPoint ");
            params.put("ftpPoint", dataSourceInfoVo.getFtpPoint());
        }
        if (StringUtils.isNotBlank(dataSourceInfoVo.getFtpUser())) {
            hql.append("and d.ftpUser = :ftpUser ");
            params.put("ftpUser", dataSourceInfoVo.getFtpUser());
        }
        if (StringUtils.isNotBlank(dataSourceInfoVo.getFtpPwd())) {
            hql.append("and d.ftpPwd = :ftpPwd ");
            params.put("ftpPwd", dataSourceInfoVo.getFtpPwd());
        }
        if (StringUtils.isNotBlank(dataSourceInfoVo.getFtpDir())) {
            hql.append("and d.ftpDir = :ftpDir ");
            params.put("ftpDir", dataSourceInfoVo.getFtpDir());
        }
        if (null != dataSourceInfoVo.getInvalidTime()) {
            hql.append("and d.invalidTime = :invalidTime ");
            params.put("invalidTime", dataSourceInfoVo.getInvalidTime());
        }
        if (null != dataSourceInfoVo.getSortNum()) {
            hql.append("and d.sortNum = :sortNum ");
            params.put("sortNum", dataSourceInfoVo.getSortNum());
        }
        if (null != dataSourceInfoVo.getConfigStatus()) {
            hql.append("and d.configStatus = :configStatus ");
            params.put("configStatus", dataSourceInfoVo.getConfigStatus());
        }
        return super.findListByHql(hql.toString(), params);
    }

    public DataSourceInfo findOneByDataSourceName(String dataSourceName) {
        return super.findOneByHql("from DataSourceInfo d where d.dataSourceName = ?0", dataSourceName);
    }

}
