/*
 * @(#)ServiceMonitorServiceImpl.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */
package com.asiainfo.biapp.si.loc.core.serviceMonitor.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.core.prefecture.entity.PreConfigInfo;
import com.asiainfo.biapp.si.loc.core.prefecture.service.IPreConfigInfoService;
import com.asiainfo.biapp.si.loc.core.prefecture.vo.PreConfigInfoVo;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.dao.IServiceMonitorDao;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.entity.ServiceMonitor;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.service.IServiceMonitorService;

/**
 * 
 * Title : ServiceMonitorServiceImpl
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

@Service
public class ServiceMonitorServiceImpl extends BaseServiceImpl<ServiceMonitor, String>  implements IServiceMonitorService {

    @Autowired
    private IPreConfigInfoService iPreConfigInfoService;
    
    @Autowired
    private IServiceMonitorDao iServiceMonitorDao;
    
    @Override
    protected BaseDao<ServiceMonitor, String> getBaseDao() {
        return iServiceMonitorDao;
    }
    
    @Override
    public List<ServiceMonitor> queryData(User user,String dataDate) throws BaseException {
        if (StringUtils.isBlank(dataDate)) {
            throw new ParamRequiredException("数据日期不能为空");
        }
        List<ServiceMonitor> serviceMonitors = new ArrayList<ServiceMonitor>();
        PreConfigInfoVo preConfigInfoVo =  new PreConfigInfoVo();
        preConfigInfoVo.setConfigStatus(1);
        //获取用户权限下所有有效的专区
        List<PreConfigInfo> preConfigInfos = iPreConfigInfoService.selectPreConfigInfoList(preConfigInfoVo,user);
        if(!preConfigInfos.isEmpty()){
            List<String> configIds = new ArrayList<String>();
            Map<String,Integer> configSortMap = new HashMap<String,Integer>();
            for(int i=0;i<preConfigInfos.size();i++){
                PreConfigInfo preConfigInfo  = preConfigInfos.get(i);
                configIds.add(preConfigInfo.getConfigId());
                configSortMap.put(preConfigInfo.getConfigId(), i+1);
            }
            //获取所有专区下监控数据
            serviceMonitors = iServiceMonitorDao.selectServiceMonitorList(configIds,dataDate);
            if(serviceMonitors.isEmpty()){
                //没有查到监控数据的专区赋值
                for(String configId : configIds){
                    serviceMonitors.add(createServiceMonitor(configId, dataDate));
                }
            }else if(serviceMonitors.size() <configIds.size()){
                List<String> hasConfigIds = new ArrayList<String>();
                for(ServiceMonitor serviceMonitor : serviceMonitors){
                    hasConfigIds.add(serviceMonitor.getConfigId());
                }
                configIds.removeAll(hasConfigIds);
                //没有查到监控数据的专区赋值
                for(String configId : configIds){
                    serviceMonitors.add(createServiceMonitor(configId, dataDate));
                }
            }
            
            for(ServiceMonitor serviceMonitor : serviceMonitors){
                serviceMonitor.setSortOrder(configSortMap.get(serviceMonitor.getConfigId())); 
            }
            
            //根据专区顺序排序
            Collections.sort(serviceMonitors, new Comparator<ServiceMonitor>(){
                @Override
                public int compare(ServiceMonitor o1, ServiceMonitor o2) {
                    if(o1.getSortOrder() != null && o2.getSortOrder() != null ){
                        return o1.getSortOrder() < o2.getSortOrder()? -1 :1;
                    }else{
                        return -1;
                    }
                }
                
            });
        }
        return serviceMonitors;
    }


    @Override
    public ServiceMonitor queryDataByPreConfig(String configId, String dataDate) throws BaseException {
        if (StringUtils.isBlank(configId)) {
            throw new ParamRequiredException("专区id不能为空");
        }
        if (StringUtils.isBlank(dataDate)) {
            throw new ParamRequiredException("数据日期不能为空");
        }
        ServiceMonitor serviceMonitor = iServiceMonitorDao.selectServiceMonitorByConfigId(configId, dataDate);
        if(serviceMonitor == null){
            serviceMonitor = createServiceMonitor(configId, dataDate);
        }
        return serviceMonitor;
    }
    
    /**
     * 
     * Description: 
     *
     * @return
     * @throws BaseException 
     */
    private ServiceMonitor createServiceMonitor(String configId,String dataDate) throws BaseException{
        ServiceMonitor serviceMonitor = new ServiceMonitor();
        serviceMonitor.setConfigId(configId);
        PreConfigInfo preConfigInfo =  iPreConfigInfoService.selectPreConfigInfoById(configId);
        if(preConfigInfo != null) {
            serviceMonitor.setSourceName(preConfigInfo.getSourceName()); 
        }
        serviceMonitor.setDataDate(dataDate);
        serviceMonitor.setNotPrepareCount(0);
        serviceMonitor.setPreparedCount(0);
        serviceMonitor.setExtractingCount(0);
        serviceMonitor.setExtractFailCount(0);
        serviceMonitor.setExtractSuccessCount(0);
        serviceMonitor.setGenFailCount(0);
        serviceMonitor.setGenSuccessCount(0);
        serviceMonitor.setCurSaveCount(0);
        serviceMonitor.setCustomFailCount(0);
        serviceMonitor.setCustomPrepareCount(0);
        serviceMonitor.setCustomCreatingCount(0);
        serviceMonitor.setCustomSuccessCount(0);
        serviceMonitor.setCustomAppointCount(0);
        serviceMonitor.setPushFailCount(0);
        serviceMonitor.setPushSuccessCount(0);
        serviceMonitor.setPushingCount(0);
        return serviceMonitor;
    }

}
