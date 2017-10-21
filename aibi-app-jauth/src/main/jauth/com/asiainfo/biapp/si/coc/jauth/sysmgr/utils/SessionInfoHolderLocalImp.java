/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.sysmgr.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.asiainfo.biapp.si.coc.jauth.frame.Constants;
import com.asiainfo.biapp.si.coc.jauth.frame.ssh.extend.SpringContextHolder;
import com.asiainfo.biapp.si.coc.jauth.security.model.UserContext;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.User;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.UserService;


/**
 * @describe TODO
 * @author zhougz
 * @date 2013-6-4
 */
@Component
public class SessionInfoHolderLocalImp implements SessionInfoHolder{
	
//	private User user;
	
	public String getAppSysCode(){
		return 1+"";
		//return Constants.DEF_APPSYSCODE;
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
			User u = null;
			if(u != null){
				return u;
			}else{
				Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
				UserContext userContext = (UserContext) authentication.getPrincipal();
				UserService userService = (UserService)SpringContextHolder.getBean("userServiceImpl");
				User user = userService.getUserByName(userContext.getUsername());
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public User getLoginUser(HttpServletRequest request) {
		return this.getLoginUser();
	}
}
