/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.sysmgr.utils;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.asiainfo.biapp.si.coc.jauth.frame.ssh.extend.SpringContextHolder;
import com.asiainfo.biapp.si.coc.jauth.security.model.UserContext;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.User;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.UserService;


/**
 * @describe 
 * @author zhougz
 * @date 2013-6-4
 */
@Component
public class SessionInfoHolderLocalImp implements SessionInfoHolder{
	private static final Logger LOGGER = LoggerFactory.getLogger(SessionInfoHolderLocalImp.class);
	
	public String getAppSysCode(){
		return Integer.toString(1);
	}

	@Override
	public String getLoginId() {
		return getLoginUser().getId();
	}

	@Override
	public String getUserName() {
		return getLoginUser().getUserName();
	}
	
	public User getLoginUser() {
		try {	
			Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
			UserContext userContext = (UserContext) authentication.getPrincipal();
			UserService userService = (UserService)SpringContextHolder.getBean("userServiceImpl");
			return userService.getUserByName(userContext.getUsername());
		} catch (Exception e) {
			LOGGER.info("context", e);
		}
		return null;
	}
	
	public User getLoginUser(HttpServletRequest request) {
		return this.getLoginUser();
	}
}
