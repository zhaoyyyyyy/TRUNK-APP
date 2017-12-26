package com.asiainfo.biapp.si.loc.cache.impl;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.asiainfo.biapp.si.loc.base.LocCacheBase;
import com.asiainfo.biapp.si.loc.cache.CocCacheAble;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;

public class CocRedisCache implements CocCacheAble{

	private Logger log = Logger.getLogger(CocRedisCache.class);
//	private static volatile ConcurrentMap<String,String> concurrentMap = new ConcurrentHashMap<String,String>();
//	private static final String LOC = "LOC_";
//	private static final String SESSION = "SESSION_";
	
	@Override
	public void reflashAllCache() {
		LocCacheBase.getInstance().init();
	}

	@Override
	public String getSYSConfigInfoByKey(String key) {
		return LocCacheBase.getInstance().getSysConfigInfoByKey(key);
	}

	@Override
	public LabelInfo getLabelInfoById(String labelid) {
		try {
			return LocCacheBase.getInstance().getEffectiveLabel(labelid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public void addSessionValue(String token,String key,Serializable obj) {
		try {
			LocCacheBase.getInstance().setSessionCache(token, key, obj);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
	}

	@Override
	public <T extends Serializable> T getSessionvalue(String token,String key){
		try {
			return LocCacheBase.getInstance().getSessionCache(token, key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
		
	}


}
