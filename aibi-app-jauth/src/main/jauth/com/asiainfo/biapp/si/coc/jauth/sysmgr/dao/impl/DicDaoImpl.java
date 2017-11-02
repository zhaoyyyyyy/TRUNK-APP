package com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.DicDao;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Dic;

/**
 * @author zhougz
 * @date 2013-5-24
 */

@Repository
public class DicDaoImpl extends BaseDaoImpl<Dic,String> implements DicDao{

	/**
	 * @describe 通过字典编号拿到字典对象
	 * @author zhougz
	 * @param
	 * @date 2013-9-18
	 */
	public Dic getDicByDicCode(String dicCode){
		return this.findOneByHql("from Dic d where d.dicCode = ? ", dicCode);
	}

	/**
	 * @describe 全局校验dic
	 * @author wangzhao
	 * @param
	 * @date 2013-11-21
	 */
	@Override
	public List<Dic> findDicListByDic(String dic) {
	    	return this.findListByHql("from Dic d where d.dicCode = ?0", dic);
	}

}
