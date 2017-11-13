package com.asiainfo.biapp.si.coc.jauth.sysmgr.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDao;
import com.asiainfo.biapp.si.coc.jauth.frame.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.IpWhiteListDao;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.IpWhiteList;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.IpWhiteListService;

@Service
@Transactional
public class IpWhiteListServiceImpl extends BaseServiceImpl<IpWhiteList, String> 
       implements IpWhiteListService{
	@Autowired 
	private IpWhiteListDao ipwhitelistDao;
	@Override
	protected BaseDao<IpWhiteList, String> getBaseDao() {
		return ipwhitelistDao;
	}
	/**
	 * 查询白名单列表
	 * 
	 * @describe TODO
	 * @author lilin
	 * @param
	 * @date 2017-10-23
	 */
	@Override
	public List<IpWhiteList> findIpWhiteList() {
		return ipwhitelistDao.findIpWhiteList();
	}
	
}
