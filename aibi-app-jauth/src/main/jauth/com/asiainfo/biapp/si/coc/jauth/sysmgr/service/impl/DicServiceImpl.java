package com.asiainfo.biapp.si.coc.jauth.sysmgr.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDao;
import com.asiainfo.biapp.si.coc.jauth.frame.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.DicDao;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Dic;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.DicService;

/**
 * @describe 数据字典业务逻辑层
 * @author zhougz
 * @date 2013-5-13
 */
@Service
@Transactional
public class DicServiceImpl extends BaseServiceImpl<Dic,String> implements DicService {
	@Autowired
	private DicDao dicDao;
	
	/**
	 * @describe 通过字典编号拿到字典对象
	 * @author zhougz
	 * @param
	 * @date 2013-9-18
	 */
	public Dic getDicByDicCode(String dicCode){
		return this.dicDao.getDicByDicCode(dicCode);
	}
	/**
	 * @describe 全局校验dic
	 * @author wangzhao
	 * @param
	 * @date 2013-11-21
	 */
	@Override
	public List<Dic> findDicListByDic(String dic) {
	    return this.dicDao.findDicListByDic(dic);
	}
	@Override
	protected BaseDao<Dic, String> getBaseDao() {
		return dicDao;
	}
}
