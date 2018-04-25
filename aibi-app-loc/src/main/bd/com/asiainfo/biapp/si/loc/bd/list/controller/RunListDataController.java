package com.asiainfo.biapp.si.loc.bd.list.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.bd.list.service.impl.CyclicityListDataServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 * 清单数据生成jauth调度接口
 * @author wanghf5
 * @since 2018-04-18
 *
 */
@Api(value = "客户群标签数据生成", description = "用语jauth调用，启动客户群标签生成")
@RequestMapping("api/listData")
@RestController
@Scope("singleton")
public class RunListDataController {
	

	@Autowired
	private CyclicityListDataServiceImpl cyclicityListDataService;
	
	@ApiOperation(value="刷新loc所有缓存数据")
    @PostMapping(value = "/runDayListData")
	public synchronized void runDayListData(String configId,String dataDate){
		LogUtil.info(" RunListDataController  -- >  start run Day List Data configid="+ configId +";dataDate=" + dataDate);
		if(StringUtils.isNotBlank(configId) && StringUtils.isNotBlank(dataDate)){
			cyclicityListDataService.runDayListDataByConfigId(configId, dataDate);
		}else if(StringUtils.isNotBlank(configId) && StringUtils.isBlank(dataDate)){
			cyclicityListDataService.runDayListDataByConfigId(configId);
		}else if(StringUtils.isBlank(configId) && StringUtils.isBlank(dataDate)){
			cyclicityListDataService.runAllDayListData();
		}	
			
		
	}
	
	@ApiOperation(value="刷新loc所有缓存数据")
    @PostMapping(value = "/runMonthListData")
	public synchronized void runMonthListData(String configId,String dataDate){
		LogUtil.info(" RunListDataController  -- >  start run Month List Data configid="+ configId +";dataDate=" + dataDate);
		if(StringUtils.isNotBlank(configId) && StringUtils.isNotBlank(dataDate)){
			cyclicityListDataService.runMonthListDataByConfigId(configId, dataDate);
		}else if(StringUtils.isNotBlank(configId) && StringUtils.isBlank(dataDate)){
			cyclicityListDataService.runMonthListDataByConfigId(configId);
		}else if(StringUtils.isBlank(configId) && StringUtils.isBlank(dataDate)){
			cyclicityListDataService.runAllMonthListData();
		}	
		
	}
}
