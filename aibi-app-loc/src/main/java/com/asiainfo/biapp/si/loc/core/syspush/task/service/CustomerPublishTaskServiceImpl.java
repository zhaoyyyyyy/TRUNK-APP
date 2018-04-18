/*
 * @(#)DaliyCustomerPublishService.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */
package com.asiainfo.biapp.si.loc.core.syspush.task.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.extend.SpringContextHolder;
import com.asiainfo.biapp.si.loc.base.utils.DateUtil;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.base.utils.ThreadPool;
import com.asiainfo.biapp.si.loc.cache.CocCacheAble;
import com.asiainfo.biapp.si.loc.cache.CocCacheProxy;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelInfoService;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelInfoVo;
import com.asiainfo.biapp.si.loc.core.syspush.common.constant.ServiceConstants;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelPushCycle;
import com.asiainfo.biapp.si.loc.core.syspush.service.ILabelPushCycleService;
import com.asiainfo.biapp.si.loc.core.syspush.task.ICustomerPublishTaskService;
import com.asiainfo.biapp.si.loc.core.syspush.task.ICustomerPublishThread;
import com.asiainfo.biapp.si.loc.core.syspush.vo.LabelPushCycleVo;

/**
 * Title : CustomerPublishDefaultThread
 * <p/>
 * Description : 客户群推送默认实现线程类
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.7 +
 * <p/>
 * Modification History :
 * <p/>
 * <pre>NO.    Date         Modified By    Why & What is modified</pre>
 * <pre>1    2018年2月26日     hongfb        Created</pre>
 * <p/>
 *
 * @author  hongfb
 * @version 1.0.0.2018年2月26日
 */

@Service
public class CustomerPublishTaskServiceImpl implements ICustomerPublishTaskService{
    
    
    @Autowired
    private ILabelInfoService iLabelInfoService;

    @Autowired
    private ILabelPushCycleService labelPushCycleServiceImpl;
    
    /**
     * 通过配置拿到适配的客户群推送的beanId
     * @return
     */
    private ICustomerPublishThread getICustomerPublishThreadBean() {
        String customerPublishThreadType = "Default";
        
        CocCacheAble cacheProxy = CocCacheProxy.getCacheProxy();
        String curCustomerPublishThreadType = cacheProxy.getSYSConfigInfoByKey("CUSTOMER_PUBLISH_THREAD_NAME");
        if (StringUtil.isEmpty(curCustomerPublishThreadType)) {
            curCustomerPublishThreadType = customerPublishThreadType;
        }
        String customerPublishThreadBeanId = "customerPublish"+curCustomerPublishThreadType+"Thread";
        ICustomerPublishThread customerPublishThread = (ICustomerPublishThread)SpringContextHolder.getBean(customerPublishThreadBeanId);
        
        return customerPublishThread;
    }
    
    @Override
    public boolean excutor(int updateCycle) {
        LogUtil.info(this.getClass().getSimpleName()+".excutor() begin");
        long s = System.currentTimeMillis();
        
        boolean res = true;
        
        //1，根据标签信息表，以及标签推送设置信息表，找出需要推送的客户群
        List<LabelInfo> cycleCustoms = this.getCustomsList(updateCycle);
        
        //2，推送
        if (null != cycleCustoms && !cycleCustoms.isEmpty()) {
            for (LabelInfo cycleCustom : cycleCustoms) {
                this.pushCustom(cycleCustom, true);
            }
        } else {
            LogUtil.info("没有周期性是 " + (updateCycle==3?"日":(updateCycle==2?"月":""))+ " 的客户群，本周期不推送。");
        }

        LogUtil.info(this.getClass().getSimpleName()+".excutor() end.cost:"+((System.currentTimeMillis()-s)/1000L)+" s.");
        
        return res;
    }

    @Override
    public boolean pushCustom(String customId) {
        LogUtil.info(this.getClass().getSimpleName()+".excutor() begin");
        long s = System.currentTimeMillis();
        
        //1，推送
        boolean res = true;
        if (StringUtil.isNoneBlank(customId)) {
            LabelInfo cycleCustom = new LabelInfo();
            cycleCustom.setLabelId(customId);
            res = this.pushCustom(cycleCustom, true);
        } else {
            LogUtil.info("周期性客户群ID为空！本周期不推送。");
        }

        LogUtil.info(this.getClass().getSimpleName()+".excutor() end.cost:"+((System.currentTimeMillis()-s)/1000L)+" s.");
        
        return res;
    }
    
    /**
     * 根据标签信息表，以及标签推送设置信息表，找出需要推送的客户群
     * loc_label_info. label_id=loc_label_push_cycle. CUSTOM_GROUP_ID
     * loc_label_info. GROUP_TYPE=2
     * loc_label_info. data_status_id=2
     * loc_label_info. label_type_id=1/2/3
     */
    private List<LabelInfo> getCustomsList(int updateCycle) {
//    SELECT o.* from loc_label_info o where o.GROUP_TYPE=1 and o.DATA_STATUS_ID =2 and o.LABEL_TYPE_ID in(1,2,3)
//            AND o.LABEL_ID not in (select DISTINCT pc.CUSTOM_GROUP_ID from loc_label_push_cycle pc where pc.STATUS=1);
        LabelInfoVo labelInfoVo = new LabelInfoVo();
        labelInfoVo.setGroupType(ServiceConstants.LabelInfo.GROUP_TYPE_G);
        labelInfoVo.setDataStatusId(ServiceConstants.LabelInfo.DATA_STATUS_ID_EFFECT);
        labelInfoVo.setUpdateCycle(updateCycle);
        String labelTypeIds = new StringBuilder(String.valueOf(ServiceConstants.LabelInfo.LABEL_TYPE_ID_SIGN)).append(",")
                .append(ServiceConstants.LabelInfo.LABEL_TYPE_ID_SCORE).append(",")
                .append(ServiceConstants.LabelInfo.LABEL_TYPE_ID_ATTR).toString();
        labelInfoVo.setLabelTypeIds(labelTypeIds);
        
        //确定当前日期是否需要推送
        String modifyTime = DateUtil.date2String(new Date(), DateUtil.FORMAT_YYYY_MM_DD);
        if (updateCycle == 2) { //"月"
            modifyTime = DateUtil.date2String(new Date(), DateUtil.FORMAT_YYYY_MM);
        }
        LabelPushCycleVo labelPushCycleVo = new LabelPushCycleVo();
        labelPushCycleVo.setCustomGroupId(new StringBuilder("SELECT DISTINCT pc.customGroupId ").append("FROM ")
            .append("LabelPushCycle pc where pc.status = ").append(ServiceConstants.LabelPushCycle.STATUS_YES)
            .append(" and pc.modifyTime like '").append(modifyTime).append("%' ").toString());
        
        List<LabelInfo> cycleCustoms = null;
        try {
            cycleCustoms = iLabelInfoService.getCycleCustom(labelInfoVo, labelPushCycleVo);
        } catch (BaseException e) {
            String msg = "查询客户群信息列表出错！";
            LogUtil.error(msg);
        }
        return cycleCustoms;
    }

    
    /**
     * Description:根据客户群去启动客户群推送线程
     * @param cycleCustoms
     */
    private boolean pushCustom(LabelInfo cycleCustom, boolean isJobTask) {
        boolean res = true;
        
        if (null != cycleCustom) {
            //获取清单表名
            String customId = cycleCustom.getLabelId();
            
            //获取推送平台
            StringBuilder selSysIds = new StringBuilder();
            LabelPushCycleVo labelPushCycleVo = new LabelPushCycleVo();
            labelPushCycleVo.setCustomGroupId(customId);
            labelPushCycleVo.setStatus(ServiceConstants.LabelPushCycle.STATUS_YES);
            List<LabelPushCycle> labelPushCycles = null;
            try {
                labelPushCycles = labelPushCycleServiceImpl.queryLabelPushCycle(labelPushCycleVo);
            } catch (BaseException e) {
                res = false;
                String msg = "查询标签推送设置信息失败";
                LogUtil.error(msg, e);
            }
            if (null != labelPushCycles && labelPushCycles.size() > 0) {
                for (LabelPushCycle pushCycle : labelPushCycles) {
                    if (StringUtil.isNotEmpty(pushCycle.getSysId())) {
                        selSysIds.append(pushCycle.getSysId()).append(",");
                    }
                }
                if (StringUtil.isNotEmpty(selSysIds)) {
                    selSysIds.deleteCharAt(selSysIds.length() - 1);

                    //启动推送线程
                    ICustomerPublishThread curCustomerPublishThread = this.getICustomerPublishThreadBean();
                    curCustomerPublishThread.initParamter(labelPushCycles, isJobTask, new ArrayList<Map<String, Object>>());
//                    Executors.newFixedThreadPool(10).execute(curCustomerPublishThread);
    					ThreadPool.getInstance().execute(curCustomerPublishThread);
                }    
            } else {
                LogUtil.info("周期性客户群(" + customId + ")没有设置推送平台，本周期不推送。");
            }
        } else {
            LogUtil.info("周期性客户群为空！本周期不推送。");
        }
        
        return res;
    }
    

}
