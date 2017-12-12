package com.asiainfo.biapp.si.loc.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 请求参数异常
 * @author zhougz3
 *
 */
public class JauthServerException extends BaseException{
	
	
	private String errorCode = super.SERVER_JAUTH_CODE;
	
	
	public JauthServerException(String message){
		super.setMsg(message);
		super.setCode(errorCode);
	}
	
	public JauthServerException(){
		super.setCode(errorCode);
	}
}