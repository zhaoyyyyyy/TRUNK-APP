package com.asiainfo.biapp.si.loc.base.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.asiainfo.biapp.si.loc.base.BaseConstants;
import com.asiainfo.biapp.si.loc.base.LocCacheBase;
import com.asiainfo.biapp.si.loc.core.label.dao.ILabelInfoDao;


public class LocBaseListener implements ApplicationListener<ContextRefreshedEvent>{

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		System.out.println("-------------------------------------LocBaseListener--------------------------------------");
//		ILabelInfoDao iLabelInfoDao = (ILabelInfoDao)event.getApplicationContext().getBean("labelInfoDaoImpl");
		BaseConstants.JAUTH_URL = event.getApplicationContext().getEnvironment().getProperty("jauth-url");
//		try {
//			RedisUtils.setAdressAndPort(LocConfigUtil.getInstance(BaseConstants.JAUTH_URL).getProperties("SYSConfig_REDIS_IP"),LocConfigUtil.getInstance(BaseConstants.JAUTH_URL).getProperties("SYSConfig_REDIS_PORT"));
//		} catch (BaseException e) {
//			e.printStackTrace();
//		}
		LocCacheBase.getInstance().init();
		
		
	}

}
