package com.asiainfo.biapp.si.loc.auth.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.auth.dao.IDicDataDao;
import com.asiainfo.biapp.si.loc.auth.model.DicData;
import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;

@Repository
public class DicDataDaoImpl extends BaseDaoImpl<DicData, String> implements IDicDataDao{
	
	
	public List<DicData> selectDataBycode(String code) throws BaseException {
		return this.findListByHql("from DicData b where b.dicCode = ?0 order by orderNum,code",code);
	}

}
