package com.asiainfo.biapp.si.loc.base.exception;

import com.asiainfo.biapp.si.loc.base.utils.LogUtil;

/**
 * 用户权限异常
 * @author zhougz3
 *
 */
public class UserAuthException extends BaseException{
	
	private String errorCode = super.USER_AUTH_CODE;
	
	public UserAuthException(String message){
		super.setMsg(message);
		super.setCode(errorCode);
	}
	
	public UserAuthException(String message,Exception e){
		//LogUtil.error("获取用户权限异常",e);
		super.setMsg(message);
		super.setCode(errorCode);
	}
	
	public UserAuthException(){
		super.setCode(errorCode);
	}
}
