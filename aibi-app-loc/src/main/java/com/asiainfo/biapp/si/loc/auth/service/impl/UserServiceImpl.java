package com.asiainfo.biapp.si.loc.auth.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.auth.service.IUserService;
import com.asiainfo.biapp.si.loc.auth.utils.TokenModel;
import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.exception.UserAuthException;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.base.utils.HttpUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;

import net.sf.json.JSONObject;

/**
 * 
 * @author Administrator
 *
 */
@Service("userService")
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User, String> implements IUserService{

	@Value("${jauth-url}")  
    private String jauthUrl; 

	@Override
	protected BaseDao<User, String> getBaseDao() {
		return null;
	}

	@Override
	public TokenModel getTokenByUsernamePassword(String username, String password) throws BaseException{
		if(StringUtil.isEmpty(username)){
			throw new ParamRequiredException("用户名不能为空");
		}
		if(StringUtil.isEmpty(password)){
			throw new ParamRequiredException("密码不能为空");
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("username", username);
		map.put("password", password);
		
		try{
			String	tokenStr = HttpUtil.sendPost(jauthUrl+"/api/auth/login", map);
			JSONObject jsObject = JSONObject.fromObject(tokenStr);
			TokenModel tokenModel = new TokenModel();
			tokenModel.setToken(jsObject.getString("token"));
			tokenModel.setRefreshToken(jsObject.getString("refreshToken"));
			return tokenModel;
		}catch(Exception e){
			throw new UserAuthException("错误的用户名/密码");
			
		}
	}

	@Override
	public User getUserByToken(String token) throws BaseException{
		
		
		String tokenStr = null;
		try{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("token", token);
			tokenStr = HttpUtil.sendGet(jauthUrl+"/api/auth/me", map);
			
		}catch(Exception e){
			throw new UserAuthException("无效的token");
		}
		JSONObject jsObject = JSONObject.fromObject(tokenStr);
		User user = new User();
		user.setUserName(jsObject.getString("username"));
		return user;
	}

	
	
}
