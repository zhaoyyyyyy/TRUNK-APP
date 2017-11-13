package com.asiainfo.biapp.si.coc.jauth.log.vo;

import com.asiainfo.biapp.si.coc.jauth.log.entity.LogInterfaceDetail;

public class LogInterfaceDetailVo extends LogInterfaceDetail{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 操作时间：开始
	 */
	private String opTimeStart;
	/**
	 * 操作时间：结束
	 */
	private String opTimeEnd;
	public String getOpTimeStart() {
		return opTimeStart;
	}
	public void setOpTimeStart(String opTimeStart) {
		this.opTimeStart = opTimeStart;
	}
	public String getOpTimeEnd() {
		return opTimeEnd;
	}
	public void setOpTimeEnd(String opTimeEnd) {
		this.opTimeEnd = opTimeEnd;
	}
	
}
