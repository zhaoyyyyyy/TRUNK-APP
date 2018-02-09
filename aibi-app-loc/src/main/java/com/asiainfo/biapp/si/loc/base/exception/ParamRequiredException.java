package com.asiainfo.biapp.si.loc.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 请求参数异常
 * @author zhougz3
 *
 */
public class ParamRequiredException extends BaseException{
	
	
	private String errorCode = super.PARAM_REQUIRED_CODE;
	
	
	public ParamRequiredException(String message){
		super.setMsg(message);
		super.setCode(errorCode);
	}
	
	public ParamRequiredException(){
		super.setCode(errorCode);
	}
}