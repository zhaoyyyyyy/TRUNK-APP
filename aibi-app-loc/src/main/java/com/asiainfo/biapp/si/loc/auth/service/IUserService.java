package com.asiainfo.biapp.si.loc.auth.service;

import com.asiainfo.biapp.si.loc.auth.model.TokenModel;
import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;

/**
 * 用户业务处理层
 * @author zhougz3
 */
public interface IUserService {
	
	/**
	 * 登录方法，单点登录通过用户名获取token
	 * @param userName 用户名
	 * @param password 密码
	 * @return TokenModel   秘钥  包括当前秘钥跟刷新秘钥
	 */
	public TokenModel getTokenByUsername(String username) throws BaseException;
	
	/**
	 * 登录方法，通过用户名密码拿到token
	 * @param userName 用户名
	 * @param password 密码
	 * @return TokenModel   秘钥  包括当前秘钥跟刷新秘钥
	 */
	public TokenModel getTokenByUsernamePassword(String userName,String password) throws BaseException;
	
	/**
	 * 通过用户身份token(秘钥)拿到用户信息
	 * @return
	 */
	public User getUserByToken(String token) throws BaseException;
	
	/**
	 * 得到一个系统token(在没有登录用户的时候可以使用，不能将此接口开放出去，内部调用即可)
	 * @return
	 */
	public TokenModel getSysToken() throws BaseException ;

}
