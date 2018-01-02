package com.asiainfo.biapp.si.loc.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 请求参数异常
 * @author zhougz3
 *
 */
public class LocCacheException extends BaseException{
	
	
	private String errorCode = super.LOC_CACHE_CODE;
	
	
	public LocCacheException(String message){
		super.setMsg(message);
		super.setCode(errorCode);
	}
	
	public LocCacheException(){
		super.setCode(errorCode);
	}
}