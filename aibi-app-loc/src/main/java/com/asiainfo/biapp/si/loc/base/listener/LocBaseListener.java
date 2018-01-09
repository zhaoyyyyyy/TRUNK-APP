package com.asiainfo.biapp.si.loc.base.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.asiainfo.biapp.si.loc.base.BaseConstants;
import com.asiainfo.biapp.si.loc.base.LocCacheBase;


public class LocBaseListener implements ApplicationListener<ContextRefreshedEvent>{

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		System.out.println("-------------------------------------LocBaseListener--------------------------------------");
		BaseConstants.JAUTH_URL = event.getApplicationContext().getEnvironment().getProperty("jauth-url");
		LocCacheBase.getInstance().init();
		
		
	}

}
