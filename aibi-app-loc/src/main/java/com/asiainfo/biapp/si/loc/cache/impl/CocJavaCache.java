package com.asiainfo.biapp.si.loc.cache.impl;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.asiainfo.biapp.si.loc.auth.model.DicData;
import com.asiainfo.biapp.si.loc.cache.CocCacheAble;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;

public class CocJavaCache implements CocCacheAble{
	
	private static volatile ConcurrentMap<String,String> concurrentMap = new ConcurrentHashMap<String,String>();
	
	
	
	@Override
	public void reflashAllCache() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getSYSConfigInfoByKey(String key) {
		return concurrentMap.get(key);
	}



	@Override
	public LabelInfo getLabelInfoById(String labelid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) {
		
	}


	@Override
	public void addSessionValue(String token, String key, Serializable obj) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public <T extends Serializable> T getSessionvalue(String token, String key) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<DicData> getDicDataByCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getNewLabelDay() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer getNewLabelDayStatus() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getNewLabelMonth() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer getNewLabelMonthStatus() {
		// TODO Auto-generated method stub
		return null;
	}


	

}
