package com.asiainfo.biapp.si.loc.auth.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.auth.dao.IDicDataDao;
import com.asiainfo.biapp.si.loc.auth.model.DicData;
import com.asiainfo.biapp.si.loc.auth.service.IDicDataService;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;


@Service
@Transactional
public class DicDataServiceImpl implements IDicDataService{

	@Autowired
	private IDicDataDao dicDataDao;
	
	@Override
	public List<DicData> queryDataListByCode(String code) throws BaseException {
		if(StringUtil.isEmpty(code)){
			throw new ParamRequiredException("数据字典编码必须填写");
		}
		return dicDataDao.selectDataBycode(code);
	}

}
