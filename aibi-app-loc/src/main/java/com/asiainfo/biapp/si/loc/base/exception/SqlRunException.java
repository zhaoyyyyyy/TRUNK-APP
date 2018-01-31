package com.asiainfo.biapp.si.loc.base.exception;


/**
 * 请求参数异常
 * @author zhougz3
 *
 */
public class SqlRunException extends BaseException{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorCode = super.PARAM_REQUIRED_CODE;
	
	
	public SqlRunException(String message){
		super.setMsg(message);
		super.setCode(errorCode);
	}
	
	public SqlRunException(){
		super.setCode(errorCode);
	}
}