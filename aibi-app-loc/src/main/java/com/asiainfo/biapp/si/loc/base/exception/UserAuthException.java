package com.asiainfo.biapp.si.loc.base.exception;

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
		e.printStackTrace();
		super.setMsg(message);
		super.setCode(errorCode);
	}
	
	public UserAuthException(){
		super.setCode(errorCode);
	}
}
