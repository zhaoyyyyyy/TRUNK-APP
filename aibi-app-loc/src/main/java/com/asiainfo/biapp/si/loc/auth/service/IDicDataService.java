package com.asiainfo.biapp.si.loc.auth.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.auth.model.DicData;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;

public interface IDicDataService {

	
	/**
	 * 通过用户身份token(秘钥)拿到用户信息
	 * @return
	 */
	public List<DicData> queryDataListByCode(String code) throws BaseException;
	
}
