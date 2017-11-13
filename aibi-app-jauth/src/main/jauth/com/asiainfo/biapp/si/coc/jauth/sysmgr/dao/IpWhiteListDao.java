package com.asiainfo.biapp.si.coc.jauth.sysmgr.dao;

import java.util.List;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDao;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.IpWhiteList;

public interface IpWhiteListDao extends BaseDao<IpWhiteList, String>{
	/**
	 * 查询白名单列表
	 * 
	 * @describe TODO
	 * @author lilin
	 * @param
	 * @date 2017-10-23
	 */
    public List<IpWhiteList> findIpWhiteList();

}
