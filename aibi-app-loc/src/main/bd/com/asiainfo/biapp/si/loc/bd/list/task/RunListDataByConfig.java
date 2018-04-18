package com.asiainfo.biapp.si.loc.bd.list.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;

import com.asiainfo.biapp.si.loc.base.extend.SpringContextHolder;
import com.asiainfo.biapp.si.loc.cache.CocCacheProxy;
import com.asiainfo.biapp.si.loc.core.label.model.ExploreQueryParam;
import com.asiainfo.biapp.si.loc.core.label.service.ICustomerManagerService;
import com.asiainfo.biapp.si.loc.core.label.service.impl.CustomerManagerServiceImpl;
import com.asiainfo.biapp.si.loc.core.syspush.task.ICustomerPublishTaskService;
import com.asiainfo.biapp.si.loc.core.syspush.task.service.CustomerPublishTaskServiceImpl;

/**
 * 周期性客户群运行线程类
 * @author wanghf5
 * @since 2018-04-18
 *
 */
public class RunListDataByConfig implements Callable<String> {

	private List<String> iListIds;
	private String iConfigId;
	private String iDataDate;
	private ICustomerManagerService listMServiceImpl;
	private ICustomerPublishTaskService customerPublishTaskService;
	
	public RunListDataByConfig(List<String> listId,String configId,String dataDate){
		this.iListIds = listId;
		this.iConfigId = configId;
		this.iDataDate = dataDate;
	}

	@Override
	public String call() throws Exception {
		System.out.println("call()方法被自动调用,干活！！！             " + Thread.currentThread().getName());
		listMServiceImpl = (CustomerManagerServiceImpl)SpringContextHolder.getBean("customerManagerServiceImpl");
		customerPublishTaskService = (CustomerPublishTaskServiceImpl)SpringContextHolder.getBean("customerPublishTaskServiceImpl");
		if(iListIds == null || iListIds.isEmpty()){
			return " iListId is null or is empty !!!";
		}
		List<String> listIds=new ArrayList<>();
		ExploreQueryParam eqp = new ExploreQueryParam(iDataDate,CocCacheProxy.getCacheProxy().getNewLabelMonth(iConfigId), CocCacheProxy.getCacheProxy().getNewLabelDay(iConfigId));
		for(String listId : iListIds){
			String ifRun = listMServiceImpl.validateLabelDataDate(listId, CocCacheProxy.getCacheProxy().getNewLabelMonth(iConfigId), CocCacheProxy.getCacheProxy().getNewLabelDay(iConfigId));
			if(StringUtils.isNoneBlank(ifRun) && ifRun.equals("2")){
				listIds.add(listId);
				listMServiceImpl.createCustomerList(listId, eqp);
			}
			customerPublishTaskService.pushCustom(listId);
			
		}
		return "call()方法被自动调用，任务的结果是：" + listIds.toString() + "    " + Thread.currentThread().getName(); 
	}

}
