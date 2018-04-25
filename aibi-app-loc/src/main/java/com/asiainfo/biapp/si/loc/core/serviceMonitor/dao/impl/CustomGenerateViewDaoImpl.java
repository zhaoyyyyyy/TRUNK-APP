/*
 * @(#)CustomGenerateViewDaoImpl.java
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
import com.asiainfo.biapp.si.loc.core.serviceMonitor.dao.ICustomGenerateViewDao;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.entity.CustomGenerateView;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.vo.CustomGenerateViewVo;

/**
 * 
 * Title : CustomGenerateViewDaoImpl
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
@Repository("customGenerateViewDaoImpl")
public class CustomGenerateViewDaoImpl extends BaseDaoImpl<CustomGenerateView, String> implements ICustomGenerateViewDao{

    @Override
    public Page<CustomGenerateView> queryCustomGenerateViewPage(Page<CustomGenerateView> page,CustomGenerateViewVo customGenerateViewVo, String configId) {
        Map<String, Object> reMap = fromBean(customGenerateViewVo);
        @SuppressWarnings("unchecked")
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
     * @param customGenerateViewVo
     * @return
     */
    public Map<String, Object> fromBean(CustomGenerateView customGenerateViewVo) {
        Map<String, Object> reMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from CustomGenerateView d where 1=1 ");
        if (StringUtils.isNotBlank(customGenerateViewVo.getLabelId())) {
            hql.append("and d.labelId = :labelId ");
            params.put("tableId", customGenerateViewVo.getLabelId());
        }
        if (StringUtils.isNotBlank(customGenerateViewVo.getLabelName())) {
            hql.append("and d.labelName LIKE :labelName ");
            params.put("labelName", "%" + customGenerateViewVo.getLabelName() + "%");
        }
        if (StringUtils.isNotBlank(customGenerateViewVo.getDataDate())) {
            hql.append("and d.dataDate = :dataDate ");
            params.put("dataDate", customGenerateViewVo.getDataDate());
        }
        if (null != customGenerateViewVo.getDataStatus()) {
            hql.append("and d.dataStatus = :dataStatus ");
            params.put("dataStatus", customGenerateViewVo.getDataStatus());
        }
        reMap.put("hql", hql);
        reMap.put("params", params);
        return reMap;
    }
}
