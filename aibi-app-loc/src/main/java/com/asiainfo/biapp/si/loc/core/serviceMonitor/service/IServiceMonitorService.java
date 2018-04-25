/*
 * @(#)IServiceMonitorService.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */
package com.asiainfo.biapp.si.loc.core.serviceMonitor.service;


import java.util.List;

import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.entity.CustomGenerateView;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.entity.CustomPushView;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.entity.LabelGenerateView;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.entity.ServiceMonitor;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.vo.CustomGenerateViewVo;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.vo.CustomPushViewVo;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.vo.LabelGenerateViewVo;
import com.asiainfo.biapp.si.loc.core.source.entity.TargetTableStatus;
import com.asiainfo.biapp.si.loc.core.source.vo.TargetTableStatusVo;

/**
 * 
 * Title : IServiceMonitorService
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
 * <pre>1    2018年4月20日    admin        Created</pre>
 * <p/>
 *
 * @author  shaosq
 * @version 1.0.0.2018年4月20日
 */
public interface IServiceMonitorService  extends BaseService<ServiceMonitor, String>{
    
    /**
     * Description:查询所有专区监控总览信息
     * @param dataDate 
     *
     * @return
     * @throws BaseException
     */
    public List<ServiceMonitor> queryData(User user, String dataDate) throws BaseException;
     
     /**
      * Description:查询专区监控总览信息
      *
      * @param configId 
      * @param dataDate 
      * @return
      * @throws BaseException
      */ 
     public ServiceMonitor queryDataByPreConfig(String configId,String dataDate) throws BaseException;

     /**
      * 
      * Description: 根据专区分页查询数据准备信息
      *
      * @param page
      * @param targetTableStatusVo
      * @param configId
      * @return
      * @throws BaseException
      */
     public Page<TargetTableStatus> queryDataPreparePagebyConfigId(Page<TargetTableStatus> page, TargetTableStatusVo targetTableStatusVo,String configId) throws BaseException;

     /**
      * 
      * Description: 根据专区分页查询标签生成信息
      *
      * @param page
      * @param LabelGenerateViewVo
      * @param configId
      * @return
      * @throws BaseException
      */
     public Page<LabelGenerateView> queryLabelGenerateViewPage(Page<LabelGenerateView> page,LabelGenerateViewVo labelGenerateViewVo, String configId)  throws BaseException;

     /**
      * 
      * Description: 根据专区分页查询客户群生成信息
      *
      * @param page
      * @param CustomGenerateViewVo
      * @param configId
      * @return
      * @throws BaseException
      */
     public Page<CustomGenerateView> queryCustomGenerateViewPage(Page<CustomGenerateView> page, CustomGenerateViewVo customGenerateViewVo,String configId)throws BaseException;

     /**
      * 
      * Description: 根据专区分页查询客户群生成信息
      *
      * @param page
      * @param CustomPushViewVo
      * @param configId
      * @return
      * @throws BaseException
      */
     public Page<CustomPushView> queryCustomPushViewPage(Page<CustomPushView> page,CustomPushViewVo customPushViewVo, String configId) throws BaseException;


}
