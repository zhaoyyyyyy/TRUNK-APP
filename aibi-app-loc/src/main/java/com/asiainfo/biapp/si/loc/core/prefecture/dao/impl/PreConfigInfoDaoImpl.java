/*
 * @(#)PreConfigInfoDaoImpl.java
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
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.core.prefecture.dao.IPreConfigInfoDao;
import com.asiainfo.biapp.si.loc.core.prefecture.entity.PreConfigInfo;
import com.asiainfo.biapp.si.loc.core.prefecture.vo.PreConfigInfoVo;

/**
 * Title : PreConfigInfoDaoImpl
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
public class PreConfigInfoDaoImpl extends BaseDaoImpl<PreConfigInfo, String> implements IPreConfigInfoDao {

    /**
     * 根据条件分页查询数据源
     * 
     * @param page
     * @param dataSourceInfo
     * @return
     */
    public Page<PreConfigInfo> findPreConfigInfoPageList(Page<PreConfigInfo> page, PreConfigInfoVo preConfigInfoVo) {
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from PreConfigInfo p where 1=1 ");
        if (StringUtils.isNotBlank(preConfigInfoVo.getConfigId())) {
            hql.append("and p.configId = :configId ");
            params.put("configId", preConfigInfoVo.getConfigId());
        }
        if (StringUtils.isNotBlank(preConfigInfoVo.getOrgId())) {
            hql.append("and p.orgId = :orgId ");
            params.put("orgId", preConfigInfoVo.getOrgId());
        }
        if (null != preConfigInfoVo.getDataAccessType()) {
            hql.append("and p.dataAccessType = :dataAccessType ");
            params.put("dataAccessType", preConfigInfoVo.getDataAccessType());
        }
        if (StringUtils.isNotBlank(preConfigInfoVo.getSourceName())) {
            hql.append("and p.sourceName = :sourceName ");
            params.put("sourceName", preConfigInfoVo.getSourceName());
        }
        if (StringUtils.isNotBlank(preConfigInfoVo.getSourceEnName())) {
            hql.append("and p.sourceEnName = :sourceEnName ");
            params.put("sourceEnName", preConfigInfoVo.getSourceEnName());
        }
        if (StringUtils.isNotBlank(preConfigInfoVo.getContractName())) {
            hql.append("and p.contractName = :contractName ");
            params.put("contractName", preConfigInfoVo.getContractName());
        }
        if (StringUtils.isNotBlank(preConfigInfoVo.getConfigDesc())) {
            hql.append("and p.configDesc LIKE :configDesc ");
            params.put("configDesc", "%" + preConfigInfoVo.getConfigDesc() + "%");
        }
        if (null != preConfigInfoVo.getInvalidTime()) {
            hql.append("and p.invalidTime = :invalidTime ");
            params.put("invalidTime", preConfigInfoVo.getInvalidTime());
        }
        if (null != preConfigInfoVo.getConfigStatus()) {
            hql.append("and p.configStatus = :configStatus ");
            params.put("configStatus", preConfigInfoVo.getConfigStatus());
        }
        return super.findPageByHql(page, hql.toString(), params);
    }

    /**
     * 根据条件查询专区列表
     * 
     * @param page
     * @param dataSourceInfo
     * @return
     */
    public List<PreConfigInfo> findPreConfigInfoList(PreConfigInfoVo preConfigInfoVo) {
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from PreConfigInfo p where 1=1 ");
        if (StringUtils.isNotBlank(preConfigInfoVo.getConfigId())) {
            hql.append("and p.configId = :configId ");
            params.put("configId", preConfigInfoVo.getConfigId());
        }
        if (StringUtils.isNotBlank(preConfigInfoVo.getOrgId())) {
            hql.append("and p.orgId = :orgId ");
            params.put("orgId", preConfigInfoVo.getOrgId());
        }
        if (null != preConfigInfoVo.getDataAccessType()) {
            hql.append("and p.dataAccessType = :dataAccessType ");
            params.put("dataAccessType", preConfigInfoVo.getDataAccessType());
        }
        if (StringUtils.isNotBlank(preConfigInfoVo.getSourceName())) {
            hql.append("and p.sourceName = :sourceName ");
            params.put("sourceName", preConfigInfoVo.getSourceName());
        }
        if (StringUtils.isNotBlank(preConfigInfoVo.getSourceEnName())) {
            hql.append("and p.sourceEnName = :sourceEnName ");
            params.put("sourceEnName", preConfigInfoVo.getSourceEnName());
        }
        if (StringUtils.isNotBlank(preConfigInfoVo.getContractName())) {
            hql.append("and p.contractName = :contractName ");
            params.put("contractName", preConfigInfoVo.getContractName());
        }
        if (StringUtils.isNotBlank(preConfigInfoVo.getConfigDesc())) {
            hql.append("and p.configDesc LIKE :configDesc ");
            params.put("configDesc", "%" + preConfigInfoVo.getConfigDesc() + "%");
        }
        if (null != preConfigInfoVo.getInvalidTime()) {
            hql.append("and p.invalidTime = :invalidTime ");
            params.put("invalidTime", preConfigInfoVo.getInvalidTime());
        }
        if (null != preConfigInfoVo.getConfigStatus()) {
            hql.append("and p.configStatus = :configStatus ");
            params.put("configStatus", preConfigInfoVo.getConfigStatus());
        }
        return super.findListByHql(hql.toString(), params);
    }

    /**
     * 根据专区名称查询专区
     * 
     * @param sourceName
     * @return
     */
    public PreConfigInfo findOneBySourceName(String sourceName) {
        return super.findOneByHql("from PreConfigInfo p where p.sourceName = ?0", sourceName);
    }
}
