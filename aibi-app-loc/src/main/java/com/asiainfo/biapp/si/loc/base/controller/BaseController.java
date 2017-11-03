package com.asiainfo.biapp.si.loc.base.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.auth.utils.AuthUtils;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.UserAuthException;

/**
 * Title : 基础控制层
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2015
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2017-11-02    zhougz3        Created</pre>
 * <p/>
 *
 * @author  zhougz3
 * @version 1.0.0.2017-11-02
 */
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
	
	
	/**
	 * 拿到当前登录用户及此用户权限信息
	 * @return
	 * @throws UserAuthException
	 */
	protected User getLoginUser() throws BaseException{
		return AuthUtils.getLoginUser(request);
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
