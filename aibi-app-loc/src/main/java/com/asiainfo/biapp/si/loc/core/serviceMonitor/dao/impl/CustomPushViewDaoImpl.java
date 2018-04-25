/*
 * @(#)CustomPushViewDaoImpl.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */
package com.asiainfo.biapp.si.loc.core.serviceMonitor.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.dao.ICustomPushViewDao;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.entity.CustomPushView;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.vo.CustomPushViewVo;

/**
 * 
 * Title : CustomPushViewDaoImpl
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2018年4月24日    shaosq        Created</pre>
 * <p/>
 *
 * @author  shaosq
 * @version 1.0.0.2018年4月24日
 */
@Repository("customPushViewDaoImpl")
public class CustomPushViewDaoImpl extends BaseDaoImpl<CustomPushView, String> implements ICustomPushViewDao{

    @SuppressWarnings("unchecked")
    @Override
    public Page<CustomPushView> queryCustomPushViewPage(Page<CustomPushView> page,CustomPushViewVo customPushViewVo, String configId) {
        Map<String, Object> reMap = fromBean(customPushViewVo);
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");
        String hqls =reMap.get("hql").toString();
        hqls += "and d.labelId in(select a.labelId from LabelInfo a where a.configId= :configId) ";
        hqls += "order by d.labelName asc,d.dataDate desc ";
        params.put("configId", configId);
        return super.findPageByHql(page, hqls.toString(), params);
    }
    
    /**
     * 
     * 模糊查询对象转化
     *
     * @param customPushViewVo
     * @return
     */
    public Map<String, Object> fromBean(CustomPushView customPushViewVo) {
        Map<String, Object> reMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from CustomPushView d where 1=1 ");
        if (StringUtils.isNotBlank(customPushViewVo.getLabelId())) {
            hql.append("and d.labelId = :labelId ");
            params.put("tableId", customPushViewVo.getLabelId());
        }
        if (StringUtils.isNotBlank(customPushViewVo.getLabelName())) {
            hql.append("and d.labelName LIKE :labelName ");
            params.put("labelName", "%" + customPushViewVo.getLabelName() + "%");
        }
        if (StringUtils.isNotBlank(customPushViewVo.getDataDate())) {
            hql.append("and d.dataDate = :dataDate ");
            params.put("dataDate", customPushViewVo.getDataDate());
        }
        if (null != customPushViewVo.getSysName()) {
            hql.append("and d.sysName = :sysName ");
            params.put("sysName", customPushViewVo.getSysName());
        }
        if (null != customPushViewVo.getPushStatus()) {
            hql.append("and d.pushStatus = :pushStatus ");
            params.put("pushStatus", customPushViewVo.getPushStatus());
        }
        if (null != customPushViewVo.getStartTime()) {
            hql.append("and d.startTime = :startTime ");
            params.put("startTime", customPushViewVo.getStartTime());
        }
        if (StringUtils.isNotBlank(customPushViewVo.getExecInfo())) {
            hql.append("and d.execInfo LIKE :execInfo ");
            params.put("execInfo", "%" + customPushViewVo.getExecInfo() + "%");
        }
        reMap.put("hql", hql);
        reMap.put("params", params);
        return reMap;
    }
}
