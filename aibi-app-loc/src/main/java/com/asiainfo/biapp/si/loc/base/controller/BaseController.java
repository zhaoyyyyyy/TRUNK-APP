package com.asiainfo.biapp.si.loc.base.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;

public abstract class BaseController<T>  {

	private static final long serialVersionUID = -42856136017302010L;

	

	// 分页器
	protected Class<T> entityClass;
	// 列表头
	protected String cols;

	public String getCols() {
		return cols;
	}

	public void setCols(String cols) {
		this.cols = cols;
	}
	
	
	
	
	public String getCtx(){
		return this.getRequest().getContextPath();
	}
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	
    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){
       this.request = request;
       this.response = response;
    }
	   

	
	/**
	 * 拿到HttpServletRequest
	 * 
	 * @return
	 */
	protected HttpServletRequest getRequest() {
		return this.request;
	}

	/**
	 * 拿到HttpServletResponse
	 * 
	 * @return
	 */
	protected HttpServletResponse getResponse() {
		return this.response;
	}
	

}
