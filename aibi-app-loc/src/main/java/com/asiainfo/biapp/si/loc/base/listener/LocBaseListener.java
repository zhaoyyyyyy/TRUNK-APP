package com.asiainfo.biapp.si.loc.base.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.asiainfo.biapp.si.loc.auth.utils.LocConfigUtil;
import com.asiainfo.biapp.si.loc.base.BaseConstants;
import com.asiainfo.biapp.si.loc.base.LocCacheBase;
import com.asiainfo.biapp.si.loc.base.config.RedisConfig;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;


public class LocBaseListener implements ApplicationListener<ContextRefreshedEvent>{

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
//		System.out.println("-------------------------------------LocBaseListener--------------------------------------");
//		BaseConstants.JAUTH_URL = event.getApplicationContext().getEnvironment().getProperty("jauth-url");
//		LocCacheBase.getInstance().init();
//		
//		
//	
//		try {
//			RedisConfig.host = LocConfigUtil.getInstance(BaseConstants.JAUTH_URL).getProperties(BaseConstants.REDIS_IP);
//			RedisConfig.port = Integer.valueOf(LocConfigUtil.getInstance(BaseConstants.JAUTH_URL).getProperties(BaseConstants.REDIS_PORT));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}

}
