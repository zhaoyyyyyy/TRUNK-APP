package com.asiainfo.biapp.si.loc.auth.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.auth.model.DicData;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;

public interface IDicDataService {

	
	/**
	 * 通过字典编码拿到字典数据集合
	 * @return
	 */
	public List<DicData> queryDataListByCode(String code) throws BaseException;
	
	
	/**
	 * 通过字典编码拿到字典数据集合(分页)
	 * @return
	 */
	
	public Page<DicData> findDicDataList(Page<DicData> page, String dicCode) throws BaseException;
}
