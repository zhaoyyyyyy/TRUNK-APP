package com.asiainfo.biapp.si.loc.auth.dao;

import java.util.List;

import com.asiainfo.biapp.si.loc.auth.model.DicData;
import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;

public interface IDicDataDao extends BaseDao<DicData, String>{
	
	public List<DicData> selectDataBycode(String code) throws BaseException;

}
