package com.asiainfo.biapp.si.loc.base.exception;

public class BaseException extends Exception{
	
	//错误参数异常
	public final String PARAM_REQUIRED_CODE = "50001";

	//用户权限异常
	public final String USER_AUTH_CODE = "50000";

	//SQL运营异常
	public final String SQL_RUN_CODE = "50002";
	
	//调用JAUTH服务异常
	public final String SERVER_JAUTH_CODE = "50003";
		
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
