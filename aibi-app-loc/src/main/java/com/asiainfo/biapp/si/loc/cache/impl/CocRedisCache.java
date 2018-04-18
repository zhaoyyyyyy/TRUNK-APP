package com.asiainfo.biapp.si.loc.cache.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import com.asiainfo.biapp.si.loc.auth.model.DicData;
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

	@Override
	public List<DicData> getDicDataByCode(String code) {
		try {
			return LocCacheBase.getInstance().getDicDataList(code);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public String getNewLabelDay(String configId) {
		String res = null;
		res= LocCacheBase.getInstance().getNewestLabelDate(configId).getDayNewestDate();
		return res;
	}

	@Override
	public Integer getNewLabelDayStatus(String configId) {
		Integer res = null;
		res= LocCacheBase.getInstance().getNewestLabelDate(configId).getDayNewestStatus();
		return res;
	}

	@Override
	public String getNewLabelMonth(String configId) {
		String res = null;
		res= LocCacheBase.getInstance().getNewestLabelDate(configId).getMonthNewestDate();
		return res;
	}

	@Override
	public Integer getNewLabelMonthStatus(String configId) {
		Integer res = null;
		res= LocCacheBase.getInstance().getNewestLabelDate(configId).getMonthNewestStatus();
		return res;
	}
	
	public List<String> getAllOrgColumnByConfig(String configId){
		return LocCacheBase.getInstance().getAllOrgColumnByConfig(configId);
	}


}
