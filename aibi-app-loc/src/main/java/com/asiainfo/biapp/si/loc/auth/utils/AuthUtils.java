package com.asiainfo.biapp.si.loc.auth.utils;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.auth.service.IUserService;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.UserAuthException;
import com.asiainfo.biapp.si.loc.base.extend.SpringContextHolder;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.cache.CocCacheProxy;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;

/**
 * 
 * Title : AuthUtils
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2017年11月3日    Administrator        Created</pre>
 * <p/>
 *
 * @author  Administrator
 * @version 1.0.0.2017年11月3日
 */
public class AuthUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthUtils.class);
	
	public static final String USER_IN_SESSION = "USER_IN_SESSION";
	
	public static final String JWT_TOKEN_REQUSET_PARAM = "token";
	public static final String JWT_TOKEN_HEADER_PARAM = "X-Authorization";
	public static final String JAUTH_ME_URL = "/api/me";
	
	/**
	 * 
	 * Description: 得到当前登录用户
	 *
	 * @param request 请求对象
	 * @return User 用户对象
	 * @throws BaseException
	 */
	public static User getLoginUser(HttpServletRequest request) throws BaseException {
		String token = getTokenByRequest(request);
		User user = getUserByToken(token);
		return user;
	}
	
	/**
	 * 
	 * Description: 通过token拿到用户
	 *
	 * @param token 票据
	 * @return User 用户对象
	 * @throws BaseException
	 */
	public static User getUserByToken(String token) throws BaseException {
		//1 先判断缓存中是否存在此用户
		User user = CocCacheProxy.getCacheProxy().getSessionvalue(token, USER_IN_SESSION);
		
		if(user == null){
			//2  如果没有在调用用户接口去取
			IUserService userService = (IUserService)SpringContextHolder.getBean("userService");
			user = userService.getUserByToken(token);
			
			CocCacheProxy.getCacheProxy().addSessionValue(token, USER_IN_SESSION,user );
		}
		return user;
	}

	
	/**
	 * 
	 * Description: 拿到当前请求的token 票据
	 *
	 * @param request 用户请求
	 * @return String token
	 * @throws BaseException
	 */
	public static String getTokenByRequest(HttpServletRequest request) throws BaseException {
    	String token = request.getParameter(JWT_TOKEN_REQUSET_PARAM);
    	if(StringUtil.isEmpty(token)){
    		token = request.getHeader(JWT_TOKEN_HEADER_PARAM);
    	}
    	if(StringUtil.isEmpty(token)){
    		throw new UserAuthException("请输入token秘钥");
    	}
		return token;
	}
}
