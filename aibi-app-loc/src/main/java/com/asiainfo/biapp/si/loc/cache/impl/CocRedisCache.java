package com.asiainfo.biapp.si.loc.cache.impl;

import com.asiainfo.biapp.si.loc.base.LocCacheBase;
import com.asiainfo.biapp.si.loc.cache.CocCacheAble;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;

public class CocRedisCache implements CocCacheAble{

	@Override
	public void reflashAllCache() {
		LocCacheBase.getInstance().refreshCache();
	}

	@Override
	public String getValueByKey(String key) {
		return LocCacheBase.getInstance().getConfigInfoByKey(key);
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


}
