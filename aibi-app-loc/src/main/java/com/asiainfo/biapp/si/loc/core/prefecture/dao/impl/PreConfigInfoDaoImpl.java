/*
 * @(#)PreConfigInfoDaoImpl.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.prefecture.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.auth.model.Organization;
import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
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

    public Page<PreConfigInfo> selectPreConfigInfoPageList(Page<PreConfigInfo> page, PreConfigInfoVo preConfigInfoVo,User user) {
        Map<String, Object> reMap = fromBean(preConfigInfoVo,user);
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");
        return super.findPageByHql(page, reMap.get("hql").toString(), params);
    }

    public List<PreConfigInfo> selectPreConfigInfoList(PreConfigInfoVo preConfigInfoVo,User user) {
        Map<String, Object> reMap = fromBean(preConfigInfoVo,user);
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");
        LogUtil.debug(" select hql queryList : " + reMap.get("hql").toString());
        LogUtil.debug(" select hql params : " + params.size());
        return super.findListByHql(reMap.get("hql").toString(), params);
    }

    public PreConfigInfo selectOneBySourceName(String sourceName) {
        return super.findOneByHql("from PreConfigInfo p where p.sourceName = ?0", sourceName);
    }

    public Map<String, Object> fromBean(PreConfigInfoVo preConfigInfoVo,User user) {
        Map<String, Object> reMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        List<String> orgIdList = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        Map<String, List<Organization>> orgMap = user.getOrgPrivaliege();
        LogUtil.debug(" getOrgPrivaliege cost Time = " + (System.currentTimeMillis() - startTime));
        for(int i=1;i<=3;i++){
            List<Organization> orgList = new ArrayList<>();
            if(i==1){
                orgList = orgMap.get("1");
            }else if(i==2){
                orgList = orgMap.get("2");
            }else if(i==3){
                orgList = orgMap.get("3");
            }
            if(orgList==null){
                continue;
            }
            LogUtil.debug(" getOrgPrivaliege orgList cost Time = " + orgList.size());
            for(Organization org : orgList){
                orgIdList.add(org.getOrgCode());
            }
        }
        StringBuffer hql = new StringBuffer("from PreConfigInfo p where 1=1 and p.orgId in (:orgIdList)");
        params.put("orgIdList", orgIdList);
        if (StringUtils.isNotBlank(preConfigInfoVo.getConfigId())) {
            hql.append(" and p.configId = :configId ");
            params.put("configId", preConfigInfoVo.getConfigId());
        }
        if (StringUtils.isNotBlank(preConfigInfoVo.getOrgId())) {
            hql.append(" and p.orgId = :orgId ");
            params.put("orgId", preConfigInfoVo.getOrgId());
        }
        if (null != preConfigInfoVo.getDataAccessType()) {
            hql.append(" and p.dataAccessType = :dataAccessType ");
            params.put("dataAccessType", preConfigInfoVo.getDataAccessType());
        }
        if (StringUtils.isNotBlank(preConfigInfoVo.getSourceName())) {
            hql.append(" and p.sourceName LIKE :sourceName ");
            params.put("sourceName","%" + preConfigInfoVo.getSourceName() + "%");
        }
        if (StringUtils.isNotBlank(preConfigInfoVo.getSourceEnName())) {
            hql.append(" and p.sourceEnName LIKE :sourceEnName ");
            params.put("sourceEnName","%" + preConfigInfoVo.getSourceEnName() + "%");
        }
        if (StringUtils.isNotBlank(preConfigInfoVo.getContractName())) {
            hql.append(" and p.contractName = :contractName ");
            params.put("contractName", preConfigInfoVo.getContractName());
        }
        if (StringUtils.isNotBlank(preConfigInfoVo.getConfigDesc())) {
            hql.append(" and p.configDesc LIKE :configDesc ");
            params.put("configDesc", "%" + preConfigInfoVo.getConfigDesc() + "%");
        }
        if (null != preConfigInfoVo.getInvalidTime()) {
            hql.append(" and p.invalidTime = :invalidTime ");
            params.put("invalidTime", preConfigInfoVo.getInvalidTime());
        }
        if (null != preConfigInfoVo.getConfigStatus()) {
            hql.append(" and p.configStatus = :configStatus ");
            params.put("configStatus", preConfigInfoVo.getConfigStatus());
        }
        hql.append("order by p.createTime desc");
        reMap.put("hql", hql);
        reMap.put("params", params);
        LogUtil.debug(" queryList ---- hql " + hql);
        return reMap;
    }
    
    public List<PreConfigInfo> selectEffectivaPreConfigInfo(){
        List<PreConfigInfo> preConfigInfo = new ArrayList<>();
        try {
            preConfigInfo = super.findListByHql("from PreConfigInfo p where p.configStatus = ?0", 1);
        } catch (BaseException e) {
            LogUtil.error(e);
        }
        return preConfigInfo;
    }
    
}
