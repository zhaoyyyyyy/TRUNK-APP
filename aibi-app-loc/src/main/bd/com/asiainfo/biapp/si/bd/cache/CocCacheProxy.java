package com.asiainfo.biapp.si.bd.cache;

import com.asiainfo.biapp.si.bd.cache.impl.CocJavaCache;
import com.asiainfo.biapp.si.bd.cache.impl.CocRedisCache;

public class CocCacheProxy {
	
	private static volatile CocCacheAble instance = null;
	
	public static CocCacheAble getCacheProxy(){
		
		if(instance == null) {
			synchronized(CocCacheProxy.class){
	            if(instance == null) {
	               new CocCacheProxy();
	            }
	         }
	      }
		return instance;
	}

	 
	public CocCacheProxy(){
		// 改code 赋值 需要
		int cacheCode = 2;
		switch (cacheCode) { 
			case  1:
				System.out.println("cache is redisCache");
				instance = new CocRedisCache();
				break;
			case  2:
				System.out.println("cache is javaCache");
				instance = new CocJavaCache();
				break;
			case 888:
				System.out.println("sorry , unfitting otherCache now !");
				break;
		}
			
	}
	

	public static void main(String[] args) {
		CocCacheProxy.getCacheProxy().setCache("testuser", "wanghf5");
		String nn = CocCacheProxy.getCacheProxy().getValueByKey("testuser");
		System.out.println(nn);

	}


}