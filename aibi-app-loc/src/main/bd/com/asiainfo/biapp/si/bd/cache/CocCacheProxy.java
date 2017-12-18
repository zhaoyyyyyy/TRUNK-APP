package com.asiainfo.biapp.si.bd.cache;

import com.asiainfo.biapp.si.bd.cache.impl.CocJavaCache;
import com.asiainfo.biapp.si.bd.cache.impl.CocRedisCache;

public class CocCacheProxy{
	
	private static volatile CocCacheAble instance = null;
	
	public enum CacheSelect {
	        redisCache(1), javaCache(2), otherCache(888);
		
			public int cacheCode;
			
			private CacheSelect(int code){
				this.cacheCode=code;
			}
	    }
	
	public static CocCacheAble getCacheProxy(CacheSelect cacheEnum){
		
		if(instance == null) {
			synchronized(CocCacheProxy.class){
	            if(instance == null) {
	               new CocCacheProxy(cacheEnum);
	            }
	         }
	      }
		return instance;
	}

	 
	public CocCacheProxy(CacheSelect cacheEnum){
		switch (cacheEnum.cacheCode) { 
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
		CocCacheProxy.getCacheProxy(CacheSelect.javaCache).setCache("testuser", "wanghf5");
		String nn = CocCacheProxy.getCacheProxy(CacheSelect.javaCache).getValueByKey("testuser");
		System.out.println(nn);

	}


}
