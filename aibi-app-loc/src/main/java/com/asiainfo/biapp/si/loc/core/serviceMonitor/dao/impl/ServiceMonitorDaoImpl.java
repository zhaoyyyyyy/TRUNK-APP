/*
 * @(#)serviceMonitorDaoImpl.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */
package com.asiainfo.biapp.si.loc.core.serviceMonitor.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.dao.IServiceMonitorDao;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.entity.ServiceMonitor;
/**
 * 
 * Title : ServiceMonitorDaoImpl
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
 * <pre>1    2018年4月20日    shaosq        Created</pre>
 * <p/>
 *
 * @author  shaosq
 * @version 1.0.0.2018年4月20日
 */
@Repository("serviceMonitorDaoImpl")
public class ServiceMonitorDaoImpl extends BaseDaoImpl<ServiceMonitor, String>  implements IServiceMonitorDao {

    /**
     * 
     * Description: 查询运营监控总览信息
     *
     * @param configIds
     * @param dataDate
     * @return
     */
    @Override
    public List<ServiceMonitor> selectServiceMonitorList(List<String> configIds,String dataDate) {
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from ServiceMonitor s where 1=1 ");
        if (configIds != null && configIds.size() >0) {
            hql.append(" and s.configId in (:configIds) ");
            params.put("configIds", configIds);
        }
        if (StringUtil.isNotBlank(dataDate)) {
            hql.append("and s.dataDate = :dataDate ");
            params.put("dataDate", dataDate);
        }
        return super.findListByHql(hql.toString(), params);
    }
    
    /**
     * 
     * Description: 查询运营监控总览信息
     *
     * @param configId
     * @param dataDate
     * @return
     */
    @Override
    public ServiceMonitor selectServiceMonitorByConfigId(String configId,String dataDate) {
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from ServiceMonitor s where 1=1 ");
        if (StringUtil.isNotBlank(configId)) {
            hql.append("and s.configId = :configId ");
            params.put("configId", configId);
        }
        if (StringUtil.isNotBlank(dataDate)) {
            hql.append("and s.dataDate = :dataDate ");
            params.put("dataDate", dataDate);
        }
        return super.findOneByHql(hql.toString(), params);
    }

}
