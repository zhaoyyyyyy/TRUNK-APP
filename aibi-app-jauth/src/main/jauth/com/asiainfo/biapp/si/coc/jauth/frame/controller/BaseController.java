package com.asiainfo.biapp.si.coc.jauth.frame.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDao;
import com.asiainfo.biapp.si.coc.jauth.frame.entity.BaseEntity;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.service.BaseService;
import com.asiainfo.biapp.si.coc.jauth.frame.util.GenericsUtil;
import com.asiainfo.biapp.si.coc.jauth.frame.util.StringUtil;

import net.sf.json.JSONObject;

public abstract class BaseController<T>  {

	private static final long serialVersionUID = -42856136017302010L;

	
    @Autowired
    private BaseDao<T,String> repositories;

	// 分页器
	protected JQGridPage<T> page = new JQGridPage<T>();

	public JQGridPage<T> getPage() {
		return page;
	}

	public void setPage(JQGridPage<T> page) {
		this.page = page;
	}
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
	// 实体
	public T entity;

	public T getEntity() {
		return entity;
	}
	public void setEntity(T entity) {
		this.entity = entity;
	}
	@SuppressWarnings("unchecked")
	public BaseController() {
		try {
			this.entity = (T) GenericsUtil.getGenericClass(this.getClass()).newInstance() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	

	/**
	 * 向显示部分输出流
	 * @param type
	 * @param text
	 */
	private void renderToView(String type, String text) {
		try {
			HttpServletResponse response = getResponse();
			response.setContentType(type + ";charset=UTF-8");
			response.getWriter().write(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 输出文本
	 * 
	 * @param text
	 */
	public void renderText(String text) {
		renderToView("text/plain", text);
	}

	/**
	 * 输出JSON
	 * 
	 * @param success
	 * @param appdx
	 */
	protected void renderJson(boolean success, Object[] appdx) {
		JSONObject jsonObj = new JSONObject();

		jsonObj.accumulate("success", success);

		int i = 0;
		while (i < appdx.length) {
			jsonObj.accumulate(appdx[i].toString(), appdx[(i + 1)]);
			i += 2;
		}
		renderText(jsonObj.toString());
	}

	/**
	 * 输出JSON
	 * 
	 * @param success
	 * @param appdx
	 */
	protected void renderJson(boolean success, String[] msg) {
		Object[] appdx = (Object[]) null;
		if ((msg != null) && (msg.length > 0))
			appdx = new String[] { "msg", msg[0] };
		else {
			appdx = new String[0];
		}

		renderJson(success, appdx);
	}
	
	/**
	 * @describe 将业务层传入进来
	 * @author zhougz
	 * @param
	 * @date 2013-5-15
	 */
	 protected abstract BaseService<T,String> getBaseService();

	/**
	 * @describe  公共删除
	 * @author zhougz
	 * @param
	 * @date 2013-5-15
	 */
	public void commDelete(){
		String id = this.getRequest().getParameter("id");
		getBaseService().delete(id);
	}
	
	/**
	 * @describe  公共保存
	 * @author zhougz
	 * @param
	 * @date 2013-5-15
	 */
	public void commSave(){
		if(this.entity instanceof BaseEntity) {
			String uploadFiles = getRequest().getParameter("$uploadFiles");
			String deleteFiles = getRequest().getParameter("$deleteFiles");
			
			//如果不存文件内容 就创建或者保存文件
			if(StringUtils.isEmpty(uploadFiles)&&StringUtils.isEmpty(deleteFiles)){
				BaseEntity standardEntity = (BaseEntity)this.entity;
				if(StringUtil.isEmpty(standardEntity.getId())) {
					getBaseService().save(this.entity);
				}else {
					getBaseService().update(this.entity);
				}
				this.renderText(standardEntity.getId());
			}
		}
	}
	
    /**
     * @describe 得到系统时间
     * @author zhougz
     * @param
     * @date 2013-5-17
     */
    public Date getSystemDate(){
    	return this.getBaseService().getSystemDate();
    }
    
	/**
	 * 获取系统时间
	 * @return Timestamp
	 */
	public Timestamp getSystemTime(){
		return	this.getBaseService().getSystemTime();
	}
}
