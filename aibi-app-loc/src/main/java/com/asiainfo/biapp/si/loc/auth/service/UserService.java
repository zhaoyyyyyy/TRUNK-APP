package com.asiainfo.biapp.si.loc.auth.service;

import com.asiainfo.biapp.si.loc.auth.model.User;

/**
 * 
 * @author Administrator
 *
 */
public interface UserService {
	
	/**
	 * 登录方法，通过用户名密码拿到token
	 * @param userName 用户名
	 * @param password 密码
	 * @return token   秘钥
	 */
	public String getTokenByUsernamePassword(String userName,String password);
	
	/**
	 * 通过用户身份token(秘钥)拿到用户信息
	 * @return
	 */
	public User getUserByToken(String token);

}
