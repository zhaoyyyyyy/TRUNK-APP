package com.asiainfo.biapp.si.loc.auth.utils;

import javax.servlet.http.HttpServletRequest;

import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.auth.service.UserService;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.UserAuthException;
import com.asiainfo.biapp.si.loc.base.extend.SpringContextHolder;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;

public class AuthUtils {
	public static final String JWT_TOKEN_REQUSET_PARAM = "token";
	public static final String JWT_TOKEN_HEADER_PARAM = "X-Authorization";
	public static final String JAUTH_ME_URL = "/api/me";
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws UserAuthException
	 */
	public static User getLoginUser(HttpServletRequest request) throws BaseException {
		String token = getTokenByRequest(request);
		User user = getUserByToken(token);
		return user;
	}
	
	/**
	 * 通过token拿到用户基本信息
	 * @return
	 */
	public static User getUserByToken(String token) throws BaseException {
		
		//1 TODO 先判断是否存在此用户
		
		//2  如果没有在调用用户接口去取
	 	UserService userService = (UserService)SpringContextHolder.getBean("userService");
	 	User user = userService.getUserByToken(token);
		return user;
	}

	
	/**
	 * 在用户提交请求的时候拿到用户token
	 * @return
	 */
	public static String getTokenByRequest(HttpServletRequest request) throws BaseException {
    	String token = request.getParameter(JWT_TOKEN_REQUSET_PARAM);
    	if(StringUtil.isEmpty(token)){
    		token = request.getHeader(JWT_TOKEN_HEADER_PARAM);
    	}
    	if(StringUtil.isEmpty(token)){
    		throw new UserAuthException("");
    	}
		return token;
	}
}
