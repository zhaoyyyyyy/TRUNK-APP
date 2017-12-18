package com.asiainfo.biapp.si.bd.cache.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.asiainfo.biapp.si.bd.cache.CocCacheAble;

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
	public Object getLabelInfoById(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setCache(String key,String value) {
		concurrentMap.put(key, value);
		
	}

	public static void main(String[] args) {
		
	}


	

}
