package com.asiainfo.biapp.si.loc.auth.model;

import java.util.List;
import java.util.Map;

public class User {
	private String userName;
	private String realName;
	/**
	 * 资源权限
	 */
	private List<Resource> resourcePrivaliege;
	
	/**
	 * 资源权限
	 */
	private List<Organization> organizationPrivaliege;
	
	
	
	public void setResourcePrivaliege(List<Resource> resourcePrivaliege) {
		this.resourcePrivaliege = resourcePrivaliege;
	}

	public void setOrganizationPrivaliege(List<Organization> organizationPrivaliege) {
		this.organizationPrivaliege = organizationPrivaliege;
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
	
	
	

	//资源权限（菜单，按钮，接口等）
	public List<Resource> getResourceMenus(){
		return null;
	}
	
	public List<Resource> getResourceButton(){
		return null;
	}
	
	public List<Resource> getResourceApis(){
		return null;
	}
	
	
	
}
