/*
 * @(#)ServiceMonitorController.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */
package com.asiainfo.biapp.si.loc.core.serviceMonitor.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.base.controller.BaseController;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.utils.WebResult;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.entity.ServiceMonitor;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.service.IServiceMonitorService;

/**
 * 
 * Title : ServiceMonitorController
 * <p/>
 * Description : 运行监控相关
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
 * <pre>1    2018年4月18日    shaosq    Created</pre>
 * <p/>
 *
 * @author  shaosq
 * @version 1.0.0.2018年4月18日
 */
@Api(value = "011.01->-运行监控", description = "邵思迁")
@RequestMapping("api/monitor")
@RestController
public class ServiceMonitorController  extends BaseController<ServiceMonitor>{
    
    @Autowired
    private IServiceMonitorService iServiceMonitorService;
    
    @ApiOperation(value = "查询所有专区运行监控总览信息")
    @ApiImplicitParam(name = "dataDate", value = "数据日期", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/overview/queryData", method = RequestMethod.POST)
    public WebResult<List<ServiceMonitor>> queryData(String dataDate){
        WebResult<List<ServiceMonitor>> webResult = new WebResult<List<ServiceMonitor>>();
        List<ServiceMonitor> serviceMonitors = new  ArrayList<ServiceMonitor>();
        User user=new User();
        try {
            user = this.getLoginUser();
            serviceMonitors = iServiceMonitorService.queryData(user,dataDate);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取运行监控总览信息成功", serviceMonitors);
    }
    
    @ApiOperation(value = "查询专区运行监控总览信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "configId", value = "专区ID", required = true, paramType = "query", dataType = "string") ,
        @ApiImplicitParam(name = "dataDate", value = "最新数据时间", required = true, paramType = "query", dataType = "string")})
    @RequestMapping(value = "/overview/queryMonitorMainByConfig", method = RequestMethod.POST)
    public WebResult<ServiceMonitor> queryMonitorMainByConfig(String configId,String dataDate){
        WebResult<ServiceMonitor> webResult = new WebResult<ServiceMonitor>();
        ServiceMonitor serviceMonitorObj = new ServiceMonitor();
        try {
            serviceMonitorObj = iServiceMonitorService.queryDataByPreConfig(configId,dataDate);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取专区运行监控总览信息成功", serviceMonitorObj);
    }
    
}
