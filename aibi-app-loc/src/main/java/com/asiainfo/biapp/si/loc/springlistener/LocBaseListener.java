package com.asiainfo.biapp.si.loc.springlistener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.asiainfo.biapp.si.loc.base.LocCacheBase;
import com.asiainfo.biapp.si.loc.core.label.dao.ILabelInfoDao;


public class LocBaseListener implements ApplicationListener<ContextRefreshedEvent>{

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		System.out.println("-------------------------------------LocBaseListener--------------------------------------");
		ILabelInfoDao iLabelInfoDao = (ILabelInfoDao)event.getApplicationContext().getBean("labelInfoDaoImpl");
//		LocCacheBase.getInstance().init(iLabelInfoDao);
//		if(event.getApplicationContext().getParent() == null){
//			LocCacheBase.getInstance().init(iLabelInfoDao);
//		}
		
	}

}
