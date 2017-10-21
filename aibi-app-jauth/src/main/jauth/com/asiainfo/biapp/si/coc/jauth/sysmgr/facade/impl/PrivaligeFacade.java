/*
 * @(#)IPrivaligeFacade.java 2016-5-27
 * 
 * aibi 版权所有2006~2016。
 */

package com.asiainfo.biapp.si.coc.jauth.sysmgr.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Organization;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.facade.IPrivaligeFacade;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.OrganizationService;

/**
 * 描述：权限对外接口
 *
 * @author zhougz
 * @version 1.0 2016-5-27
 */
public class PrivaligeFacade implements IPrivaligeFacade{
	
	
	@Autowired
	private OrganizationService organizationService;
	
	/**
	 * 
	 * @describe 列出某个用户有权限访问的组织
	 * @author zhougz
	 * @date 2016-5-27
	 * @param userId
	 * @return
	 */
	public List<Organization> queryOwenOrgByUserId(String userId) {
		return organizationService.queryOwenOrgByUserId(userId);
	}
}
