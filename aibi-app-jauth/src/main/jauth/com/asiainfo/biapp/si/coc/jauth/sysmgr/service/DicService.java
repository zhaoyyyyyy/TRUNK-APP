package com.asiainfo.biapp.si.coc.jauth.sysmgr.service;

import java.util.List;

import com.asiainfo.biapp.si.coc.jauth.frame.service.BaseService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Dic;

public interface DicService extends BaseService<Dic,String>{
	
	/**
	 * @describe 通过字典编号拿到字典对象
	 * @author zhougz
	 * @param
	 * @date 2013-9-18
	 */
	public Dic getDicByDicCode(String dicCode);

	/**
	 * @describe 全局校验dic
	 * @author wangzhao
	 * @param
	 * @date 2013-11-21
	 */
	public List<Dic> findDicListByDic(String dic);
}
