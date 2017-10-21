package com.asiainfo.biapp.si.coc.jauth.frame.exception;

public class BaseException extends Exception{

	private String errorCode = "unknown.error";
	
	
	
	private static final long serialVersionUID = 1L;

	
	public String getErrorCode() {
	    return this.errorCode;
	}
	
	
	public BaseException(String errorCode) {
	    this.errorCode = errorCode;
	  }
	  
	public String getMessage() {
		// 否则用errorCode查询Properties文件获得出错信息
		return this.getErrorCode();
	}
	
	
}
