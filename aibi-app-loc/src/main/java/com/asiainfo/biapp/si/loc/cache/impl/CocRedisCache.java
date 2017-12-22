package com.asiainfo.biapp.si.loc.cache.impl;

import org.apache.log4j.Logger;

import com.asiainfo.biapp.si.loc.base.LocCacheBase;
import com.asiainfo.biapp.si.loc.cache.CocCacheAble;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;

public class CocRedisCache implements CocCacheAble{

	private Logger log = Logger.getLogger(CocRedisCache.class);
	
	@Override
	public void reflashAllCache() {
		LocCacheBase.getInstance().refreshCache();
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
	public void addSessionValue(String token,String key,String obj) {
		try {
			LocCacheBase.getInstance().setSessionCache(token, key, obj);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
	}

	@Override
	public String getSessionvalue(String token,String key){
		try {
			return LocCacheBase.getInstance().getSessionCache(token, key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
		
	}


}
