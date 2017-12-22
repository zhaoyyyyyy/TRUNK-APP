package com.asiainfo.biapp.si.loc.cache.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.asiainfo.biapp.si.loc.cache.CocCacheAble;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;

public class CocJavaCache implements CocCacheAble{
	
	private static volatile ConcurrentMap<String,String> concurrentMap = new ConcurrentHashMap<String,String>();
	
	
	
	@Override
	public void reflashAllCache() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getValueByKey(String key) {
		return concurrentMap.get(key);
	}



	@Override
	public LabelInfo getLabelInfoById(String labelid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) {
		
	}


	

}
