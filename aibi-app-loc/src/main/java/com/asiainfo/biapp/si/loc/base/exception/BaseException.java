package com.asiainfo.biapp.si.loc.base.exception;

public class BaseException extends Exception{
	
	
	public final String PARAM_REQUIRED_CODE = "50001";
	
	public final String USER_AUTH_CODE = "50000";

	
	private String msg ;
	private String code ;
	


	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public String getErrorCode(){
		return code;
	}
	
	  
	public String getMessage(){
		return msg;
	}

}
