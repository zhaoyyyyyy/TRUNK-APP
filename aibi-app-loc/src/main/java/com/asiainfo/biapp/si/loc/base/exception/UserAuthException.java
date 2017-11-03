package com.asiainfo.biapp.si.loc.base.exception;

import javax.servlet.ServletException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

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
	
	public UserAuthException(){
		super.setCode(errorCode);
	}
}
