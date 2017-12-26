package com.asiainfo.biapp.si.loc.auth.model;

import java.util.List;
import java.util.Map;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;

public class User extends BaseEntity {
	
	private String userId;
	private String userName;
	private String realName;
	
	/** 菜单权限 */
	private List<Resource> menuResource;
	
	/** 页面元素权限，包括按钮等  */
	private List<Resource> domResource;
	
	/** api访问权限 **/
	private List<Resource> apiResource;
	
	/** 
	 * 组织访问权限跟同专区权限
	 * key 是组织类型
	 * value 组织集合
	 **/
	private Map<String,List<Organization>> orgPrivaliege;
	
	/** 
	 * 数据权限同行政区划权限
	 * key 是组织级别
	 * value 组织集合
	 **/
	private Map<String,List<Organization>> dataPrivaliege;
	
	
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	public List<Resource> getMenuResource() {
		return menuResource;
	}
	public void setMenuResource(List<Resource> menuResource) {
		this.menuResource = menuResource;
	}
	public List<Resource> getDomResource() {
		return domResource;
	}
	public void setDomResource(List<Resource> domResource) {
		this.domResource = domResource;
	}
	public List<Resource> getApiResource() {
		return apiResource;
	}
	public void setApiResource(List<Resource> apiResource) {
		this.apiResource = apiResource;
	}
	public Map<String, List<Organization>> getOrgPrivaliege() {
		return orgPrivaliege;
	}
	public void setOrgPrivaliege(Map<String, List<Organization>> orgPrivaliege) {
		this.orgPrivaliege = orgPrivaliege;
	}
	public Map<String, List<Organization>> getDataPrivaliege() {
		return dataPrivaliege;
	}
	public void setDataPrivaliege(Map<String, List<Organization>> dataPrivaliege) {
		this.dataPrivaliege = dataPrivaliege;
	}
	
	
	
	
}
