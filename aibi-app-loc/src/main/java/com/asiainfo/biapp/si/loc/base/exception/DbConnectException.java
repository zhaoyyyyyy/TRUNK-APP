package com.asiainfo.biapp.si.loc.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 请求参数异常
 * @author zhougz3
 *
 */
public class DbConnectException extends BaseException{
	
	
	private String errorCode = super.DB_CONNECT_CODE;
	
	
	public DbConnectException(String message){
		super.setMsg(message);
		super.setCode(errorCode);
	}
	
	public DbConnectException(){
		super.setCode(errorCode);
	}
}