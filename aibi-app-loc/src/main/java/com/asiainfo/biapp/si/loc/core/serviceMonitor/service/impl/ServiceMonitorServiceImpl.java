/*
 * @(#)ServiceMonitorServiceImpl.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */
package com.asiainfo.biapp.si.loc.core.serviceMonitor.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.core.prefecture.entity.PreConfigInfo;
import com.asiainfo.biapp.si.loc.core.prefecture.service.IPreConfigInfoService;
import com.asiainfo.biapp.si.loc.core.prefecture.vo.PreConfigInfoVo;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.dao.ICustomGenerateViewDao;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.dao.ICustomPushViewDao;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.dao.ILabelGenerateViewDao;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.dao.IServiceMonitorDao;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.entity.CustomGenerateView;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.entity.CustomPushView;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.entity.LabelGenerateView;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.entity.ServiceMonitor;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.service.IServiceMonitorService;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.vo.CustomGenerateViewVo;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.vo.CustomPushViewVo;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.vo.LabelGenerateViewVo;
import com.asiainfo.biapp.si.loc.core.source.dao.ITargetTableStatusDao;
import com.asiainfo.biapp.si.loc.core.source.entity.TargetTableStatus;
import com.asiainfo.biapp.si.loc.core.source.vo.TargetTableStatusVo;

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
    
    @Autowired
    private ITargetTableStatusDao iTargetTableStatusDao;
    
    @Autowired
    private ILabelGenerateViewDao iLabelGenerateViewDao;
    
    @Autowired
    private ICustomGenerateViewDao iCustomGenerateViewDao;
    
    @Autowired
    private ICustomPushViewDao iCustomPushViewDao;
    
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
        List<PreConfigInfo> preConfigInfos = iPreConfigInfoService.selectPreConfigInfoList(new PreConfigInfoVo(),user);
        if(preConfigInfos != null && preConfigInfos.size() > 0){
            List<String> configIds = new ArrayList<String>();
            for(PreConfigInfo preConfigInfo : preConfigInfos){
                configIds.add(preConfigInfo.getConfigId());
            }
            serviceMonitors = iServiceMonitorDao.selectServiceMonitorList(configIds,dataDate);
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
        return iServiceMonitorDao.selectServiceMonitorByConfigId(configId, dataDate);
    }


    @Override
    public Page<TargetTableStatus> queryDataPreparePagebyConfigId(Page<TargetTableStatus> page, TargetTableStatusVo targetTableStatusVo,String configId)  throws BaseException {
        if (StringUtils.isBlank(configId)) {
            throw new ParamRequiredException("专区id不能为空");
        }
        return iTargetTableStatusDao.selectTargetTableStatusPageListByConfigId(page,targetTableStatusVo,configId);
    }

    @Override
    public Page<LabelGenerateView> queryLabelGenerateViewPage(Page<LabelGenerateView> page,LabelGenerateViewVo labelGenerateViewVo, String configId) throws BaseException {
        if (StringUtils.isBlank(configId)) {
            throw new ParamRequiredException("专区id不能为空");
        }
        return iLabelGenerateViewDao.queryLabelGenerateViewPage(page,labelGenerateViewVo,configId);
    }

    @Override
    public Page<CustomGenerateView> queryCustomGenerateViewPage(Page<CustomGenerateView> page,CustomGenerateViewVo customGenerateViewVo, String configId) throws BaseException {
        if (StringUtils.isBlank(configId)) {
            throw new ParamRequiredException("专区id不能为空");
        }
        return iCustomGenerateViewDao.queryCustomGenerateViewPage(page,customGenerateViewVo,configId);
    }
    
    @Override
    public Page<CustomPushView> queryCustomPushViewPage(Page<CustomPushView> page,CustomPushViewVo customPushViewVo, String configId) throws BaseException {
        if (StringUtils.isBlank(configId)) {
            throw new ParamRequiredException("专区id不能为空");
        }
        return iCustomPushViewDao.queryCustomPushViewPage(page,customPushViewVo,configId);
    }


}
