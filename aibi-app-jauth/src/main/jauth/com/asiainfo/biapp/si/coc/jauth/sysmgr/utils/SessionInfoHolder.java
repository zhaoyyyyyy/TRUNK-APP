/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.sysmgr.utils;

import javax.servlet.http.HttpServletRequest;

import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.User;

/**
 * @author zhougz
 * @date 2013-5-22
 */
public interface SessionInfoHolder {
	
	/**
	 * @describe 拿到归属系统
	 * @author zhougz
	 * @param
	 * @date 2013-6-4
	 */
	public String getAppSysCode();
	
	/**
	 * @describe 拿到用户名
	 * @author zhougz
	 * @param
	 * @date 2013-6-4
	 */
	public String getUserName();
	
	/**
	 * @describe 拿到登陆账号
	 * @author zhougz
	 * @param
	 * @date 2013-6-4
	 */
	public String getLoginId();
	
	/**
	 * @author liukai
	 * @param
	 * @date 2013-7-24
	 */
	public User getLoginUser() ;
	
	public User getLoginUser(HttpServletRequest request);
	
}
