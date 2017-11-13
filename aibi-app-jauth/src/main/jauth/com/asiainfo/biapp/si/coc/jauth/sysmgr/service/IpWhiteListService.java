package com.asiainfo.biapp.si.coc.jauth.sysmgr.service;

import java.util.List;

import com.asiainfo.biapp.si.coc.jauth.frame.service.BaseService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.IpWhiteList;
/**
 * @describe 白名单列表service
 * @author lilin
 * @param
 * @date 2017-10-23
 */
public interface IpWhiteListService extends BaseService<IpWhiteList, String>{

	/**
	 * 
	 * @describe 显示白名单列表
	 * @author lilin
	 * @param
	 * @date 2017-10-23
	 */
	public List<IpWhiteList> findIpWhiteList();
}
