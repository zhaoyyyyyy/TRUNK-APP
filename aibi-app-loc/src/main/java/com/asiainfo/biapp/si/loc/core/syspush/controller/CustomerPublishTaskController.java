/*
 * @(#)CreateCustomSubscribeListThread.java
 *
 * CopyRight (c) 2016 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.core.ServiceConstants;
import com.asiainfo.biapp.si.loc.core.syspush.task.ISeasonalCustomerPublishTask;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * Title : CreateCustomSubscribeListThread
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 8.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2018年3月26日    hongfb        Created</pre>
 * <p/>
 *
 * @author  hongfb
 * @version 1.0.0.2018年3月26日
 */

@Api(value = "009.07->-客户群推送",description="日周期、月周期")
@RequestMapping("api/syspush/task")
@RestController
public class CustomerPublishTaskController {
    
    @Autowired
    private ISeasonalCustomerPublishTask iSeasonalCustomerPublishTask;
    
    @ApiOperation(value = "日周期推送")
    @RequestMapping(value = "/daliyCustomerPublish", method = RequestMethod.POST)
    public String daliyCustomerPublish() {
        LogUtil.info(this.getClass().getSimpleName()+".taskExecute() begin");
        long s = System.currentTimeMillis();
        
        boolean res = iSeasonalCustomerPublishTask.excutor(ServiceConstants.LabelInfo.UPDATE_CYCLE_D);
        
        LogUtil.info(this.getClass().getSimpleName()+".taskExecute() end.cost:"+((System.currentTimeMillis()-s)/1000L)+" s.");
        
        return String.valueOf(res);
    }

    
    @ApiOperation(value = "月周期推送")
    @RequestMapping(value = "/mouthCustomerPublish", method = RequestMethod.POST)
    public String mouthCustomerPublish() {
        LogUtil.info(this.getClass().getSimpleName()+".taskExecute() begin");
        long s = System.currentTimeMillis();
        
        boolean res = iSeasonalCustomerPublishTask.excutor(ServiceConstants.LabelInfo.UPDATE_CYCLE_M);
        
        LogUtil.info(this.getClass().getSimpleName()+".taskExecute() end.cost:"+((System.currentTimeMillis()-s)/1000L)+" s.");
        
        return String.valueOf(res);
    }

    
}
