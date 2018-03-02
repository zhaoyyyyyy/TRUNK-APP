package com.asiainfo.biapp.si.loc.bd.cyclist.runthread;

import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;

import com.asiainfo.biapp.si.loc.base.common.LabelInfoContants;
import com.asiainfo.biapp.si.loc.base.extend.SpringContextHolder;
import com.asiainfo.biapp.si.loc.bd.common.service.impl.BackServiceImpl;
import com.asiainfo.biapp.si.loc.cache.CocCacheProxy;
import com.asiainfo.biapp.si.loc.core.label.model.ExploreQueryParam;
import com.asiainfo.biapp.si.loc.core.label.service.ICustomerManagerService;
import com.asiainfo.biapp.si.loc.core.label.service.impl.CustomerManagerServiceImpl;

public class RunListDataByConfig implements Callable<String> {

	private List<String> iListIds;
	private String iConfigId;
	private String iDataDate;
	private BackServiceImpl backService;
	private ICustomerManagerService listMServiceImpl;
	
	public RunListDataByConfig(List<String> listId,String configId,String dataDate){
		this.iListIds = listId;
		this.iConfigId = configId;
		this.iDataDate = dataDate;
		
	}

	@Override
	public String call() throws Exception {
		System.out.println("call()方法被自动调用,干活！！！             " + Thread.currentThread().getName());
		backService = (BackServiceImpl)SpringContextHolder.getBean("backServiceImpl");
		listMServiceImpl = (CustomerManagerServiceImpl)SpringContextHolder.getBean("customerManagerServiceImpl");
		if(null == null || iListIds.isEmpty()){
			return " iListId is null or is empty !!!";
		}
		ExploreQueryParam eqp = new ExploreQueryParam(iDataDate,CocCacheProxy.getCacheProxy().getNewLabelMonth(), CocCacheProxy.getCacheProxy().getNewLabelDay());
		for(String listId : iListIds){
			
			String ifRun = listMServiceImpl.validateLabelDataDate(listId, CocCacheProxy.getCacheProxy().getNewLabelMonth(), CocCacheProxy.getCacheProxy().getNewLabelDay());
			if(StringUtils.isNoneBlank(ifRun) && ifRun.equals("2")){
				listMServiceImpl.createCustomerList(listId, eqp);
			}
//			//调用田旭阳 接口， 获取 fromsql 
//			String fromSql = "";
//			
//			//拼写 insert into sql 
//			StringBuffer selectSql = new StringBuffer();
//			StringBuffer countSql = new StringBuffer();
//			String insertTab = LabelInfoContants.KHQ_CROSS_TABLE + iConfigId + "_" + iDataDate;
//			selectSql.append(" select ").append(LabelInfoContants.KHQ_CROSS_COLUMN + " ").append(fromSql);
//			countSql.append(" select count(1) ").append(fromSql);
//			System.out.println("----------------- RunListDataByConfig " + Thread.currentThread().getName() 
//					+ " : selectSql = " + selectSql.toString() + "; insertTab=" + insertTab + ";listId = " + listId);
//			backService.insertCustomerData(selectSql.toString(), insertTab, listId);
//			int countNum = backService.queryCount(countSql.toString());
			
			
		}
		
		return "call()方法被自动调用，任务的结果是：" + iListIds.toString() + "    " + Thread.currentThread().getName(); 
	}

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
