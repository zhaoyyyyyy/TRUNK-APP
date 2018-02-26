package com.asiainfo.biapp.si.loc.bd.list.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.loc.bd.cyclist.service.impl.CyclicityListDataServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "客户群标签数据生成", description = "用语jauth调用，启动客户群标签生成")
@RequestMapping("api/listData")
@RestController
public class RunListDataController {

	@Autowired
	private CyclicityListDataServiceImpl cyclicityListDataService;
	
	@ApiOperation(value="刷新loc所有缓存数据")
    @PostMapping(value = "/runDayListData")
	public void runDayListData(String configId,String dataDate){
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
	public void runMonthListData(String configId,String dataDate){
		
		if(StringUtils.isNotBlank(configId) && StringUtils.isNotBlank(dataDate)){
			cyclicityListDataService.runMonthListDataByConfigId(configId, dataDate);
		}else if(StringUtils.isNotBlank(configId) && StringUtils.isBlank(dataDate)){
			cyclicityListDataService.runMonthListDataByConfigId(configId);
		}else if(StringUtils.isBlank(configId) && StringUtils.isBlank(dataDate)){
			cyclicityListDataService.runAllMonthListData();
		}
	}
}
