package com.asiainfo.biapp.si.loc.cache.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.loc.cache.CocCacheProxy;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "刷新缓存",description="用于jauth定时任务调起的刷缓存")
@RequestMapping("api/loccacherefresh")
@RestController
public class CocCacheController {
	
	public static volatile boolean cacheIfRunning = false;
	
	
	@ApiOperation(value="刷新loc所有缓存数据")
    @PostMapping(value = "/cacheDataRefresh")
	public String cacheDataRefresh(){
		if(!cacheIfRunning){
			cacheIfRunning = true;
			CocCacheProxy.getCacheProxy().reflashAllCache();
			cacheIfRunning = false;
		}else{
			return "cacheDataRefresh have be running!!!";
		}
		return "cacheDataRefresh success!!!";
		
	}

}
