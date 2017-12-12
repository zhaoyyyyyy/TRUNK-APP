package com.asiainfo.biapp.si.bd.cache;

public class CocCacheProxy implements CocCacheAble {
	
	private CocCacheAble cache;
	
	public CocCacheProxy(){
		super();
		if(1==1){
			cache = new CocRedisCache();
		}
	}
	
	@Override
	public void reflashAllCache() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String getValueByKey(String key) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object getLabelInfoById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}



	

}
