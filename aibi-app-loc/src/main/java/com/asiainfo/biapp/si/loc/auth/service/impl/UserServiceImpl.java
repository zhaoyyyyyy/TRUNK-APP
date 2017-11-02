package com.asiainfo.biapp.si.loc.auth.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.auth.service.UserService;
import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.base.utils.HttpUtil;

/**
 * 
 * @author Administrator
 *
 */
@Profile("dev")
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService{

	@Override
	protected BaseDao<User, String> getBaseDao() {
		return null;
	}

	@Override
	public String getTokenByUsernamePassword(String userName, String password) {
		
		
		//HttpUtil.sendPost(url, param);
		return null;
	}

	@Override
	public User getUserByToken(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
