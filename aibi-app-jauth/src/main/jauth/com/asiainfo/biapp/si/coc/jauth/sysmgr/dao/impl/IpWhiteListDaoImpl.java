package com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.IpWhiteListDao;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.IpWhiteList;

/**
 * @describe TODO
 * @author lilin
 * @date 2017-10-23
 */
@Repository
public class IpWhiteListDaoImpl extends BaseDaoImpl<IpWhiteList, String> 
    implements IpWhiteListDao{

	/**
	 * @describe 查询白名单列表
	 * @author lilin
	 * @param
	 * @date 2017-10-23
	 */
	@Override
	public List<IpWhiteList> findIpWhiteList() {
		return this.findListByHql("from IpWhiteList I where 1=1");
//		return this.getAll();
	}


}
