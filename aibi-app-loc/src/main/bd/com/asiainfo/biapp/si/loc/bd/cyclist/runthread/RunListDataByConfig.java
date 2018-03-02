package com.asiainfo.biapp.si.loc.bd.cyclist.runthread;

import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;

import com.asiainfo.biapp.si.loc.base.extend.SpringContextHolder;
import com.asiainfo.biapp.si.loc.cache.CocCacheProxy;
import com.asiainfo.biapp.si.loc.core.label.model.ExploreQueryParam;
import com.asiainfo.biapp.si.loc.core.label.service.ICustomerManagerService;
import com.asiainfo.biapp.si.loc.core.label.service.impl.CustomerManagerServiceImpl;

public class RunListDataByConfig implements Callable<String> {

	private List<String> iListIds;
	private String iConfigId;
	private String iDataDate;
	private ICustomerManagerService listMServiceImpl;
	
	public RunListDataByConfig(List<String> listId,String configId,String dataDate){
		this.iListIds = listId;
		this.iConfigId = configId;
		this.iDataDate = dataDate;
		
	}

	@Override
	public String call() throws Exception {
		System.out.println("call()方法被自动调用,干活！！！             " + Thread.currentThread().getName());
		listMServiceImpl = (CustomerManagerServiceImpl)SpringContextHolder.getBean("customerManagerServiceImpl");
		if(iListIds == null || iListIds.isEmpty()){
			return " iListId is null or is empty !!!";
		}
		ExploreQueryParam eqp = new ExploreQueryParam(iDataDate,CocCacheProxy.getCacheProxy().getNewLabelMonth(), CocCacheProxy.getCacheProxy().getNewLabelDay());
		for(String listId : iListIds){
			String ifRun = listMServiceImpl.validateLabelDataDate(listId, CocCacheProxy.getCacheProxy().getNewLabelMonth(), CocCacheProxy.getCacheProxy().getNewLabelDay());
			if(StringUtils.isNoneBlank(ifRun) && ifRun.equals("2")){
				listMServiceImpl.createCustomerList(listId, eqp);
			}
		}
		return "call()方法被自动调用，任务的结果是：" + iListIds.toString() + "    " + Thread.currentThread().getName(); 
	}

}
