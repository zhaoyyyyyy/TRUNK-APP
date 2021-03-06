package com.asiainfo.biapp.si.loc.cache;

import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.cache.impl.CocJavaCache;
import com.asiainfo.biapp.si.loc.cache.impl.CocRedisCache;

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
		int cacheCode = 1;
		switch (cacheCode) { 
			case  1:
				LogUtil.info("cache is redisCache");
				instance = new CocRedisCache();
				break;
			case  2:
				LogUtil.info("cache is javaCache");
				instance = new CocJavaCache();
				break;
			case 888:
				LogUtil.info("sorry , unfitting otherCache now !");
				break;
		}
			
	}
	

	public static void main(String[] args) {
//		CocCacheProxy.getCacheProxy().getLabelInfoById("12212").getMdaSysTableColumn().getMdaSysTable();
//		CocCacheProxy.getCacheProxy().getNewLabelDay()
	}


}
