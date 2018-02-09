package com.asiainfo.biapp.si.loc.auth.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.auth.model.DicData;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;

public interface IDicDataService {

	
	/**
	 * 通过字典编码拿到字典数据集合
	 * @return
	 */
	public List<DicData> queryDataListByCode(String code) throws BaseException;
	
	/**
	 * 
	 * Description: 查询所有生效的数据字典，给初始化缓存方法使用
	 *
	 * @return
	 * @throws BaseException
	 */
	public List<DicData> queryAllDicData() throws BaseException ;
	
	
}
