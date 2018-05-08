package com.asiainfo.biapp.si.loc.base.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.asiainfo.biapp.si.loc.auth.utils.LocConfigUtil;
import com.asiainfo.biapp.si.loc.base.BaseConstants;
import com.asiainfo.biapp.si.loc.base.LocCacheBase;
import com.asiainfo.biapp.si.loc.base.config.RedisConfig;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;


public class LocBaseListener implements ApplicationListener<ContextRefreshedEvent>{

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		LogUtil.info("-------------------------------------LocBaseListener--------------------------------------");
		BaseConstants.JAUTH_URL = event.getApplicationContext().getEnvironment().getProperty("jauth-url");
		LocCacheBase.getInstance().init();
		
	}

}
