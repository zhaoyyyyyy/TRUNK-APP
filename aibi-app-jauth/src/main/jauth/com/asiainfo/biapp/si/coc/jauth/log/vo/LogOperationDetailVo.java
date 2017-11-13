package com.asiainfo.biapp.si.coc.jauth.log.vo;

import com.asiainfo.biapp.si.coc.jauth.log.entity.LogOperationDetail;

public class LogOperationDetailVo extends LogOperationDetail{

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
	/**
	 * 资源类型 
	 */
	private String type;
	/**
	 * 资源名称
	 */
	private String resourceName;
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
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	
}
