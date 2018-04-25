/*
 * @(#)ServiceMonitorDAO.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */
package com.asiainfo.biapp.si.loc.core.serviceMonitor.dao;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.entity.ServiceMonitor;

/**
 * 
 * Title : ServiceMonitorDAO
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
public interface IServiceMonitorDao extends BaseDao<ServiceMonitor, String>{
    
    /**
     * 获取所有专区运营监控信息
     *
     * @return
     */
    public List<ServiceMonitor> selectServiceMonitorList(List<String> configIds,String dataDate);
    
    /**
     * 获取专区运营监控信息
     *
     * @return
     */
    public ServiceMonitor selectServiceMonitorByConfigId(String configId,String dataDate);

    
}
