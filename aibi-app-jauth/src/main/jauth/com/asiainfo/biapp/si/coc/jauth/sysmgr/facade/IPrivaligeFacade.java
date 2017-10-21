/*
 * @(#)IPrivaligeFacade.java 2016-5-27
 * 
 * aibi 版权所有2006~2016。
 */

package com.asiainfo.biapp.si.coc.jauth.sysmgr.facade;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Organization;

/**
 * 描述：权限接口
 *
 * @author zhougz
 * @version 1.0 2016-5-27
 */
public interface IPrivaligeFacade {
	
	/**
	 * 
	 * @describe 列出某个用户有权限访问的组织
	 * @author zhougz
	 * @date 2016-5-27
	 * @param userId
	 * @return
	 */
    @Cacheable(value="dataDic_cache",key="#userId+'queryOwenOrgByUserId'")
	public List<Organization> queryOwenOrgByUserId(String userId) ;

}
