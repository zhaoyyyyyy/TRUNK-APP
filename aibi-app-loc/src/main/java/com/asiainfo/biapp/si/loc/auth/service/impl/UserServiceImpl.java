package com.asiainfo.biapp.si.loc.auth.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.auth.service.UserService;
import com.asiainfo.biapp.si.loc.auth.utils.AuthUtils;
import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.base.utils.HttpUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;

/**
 * 
 * @author Administrator
 *
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService{


	@Override
	protected BaseDao<User, String> getBaseDao() {
		return null;
	}

	@Override
	public String getTokenByUsernamePassword(String userName, String password) throws BaseException{
		if(StringUtil.isEmpty(userName)){
			throw new ParamRequiredException("用户名不能为空");
		}
		if(StringUtil.isEmpty(password)){
			throw new ParamRequiredException("密码不能为空");
		}
		
		//HttpUtil.sendPost(url, param);
		return null;
	}

	@Override
	public User getUserByToken(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
