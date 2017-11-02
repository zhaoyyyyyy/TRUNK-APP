package com.asiainfo.biapp.si.loc.auth.model;

import java.util.List;
import java.util.Map;

public class User {

	private String userName;
	private String realName;


	/**
	 * 资源权限
	 */
	private Map<String,List<Resource>> resourcePrivaliege;
	
	
	
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
	
	
	/**
	 * 组织权限
	 */
	//数据权限（省中心专区，地市专区，第三方换区，场景?? 个人 ？？专区）
	//private Map<String,List<Resource>> resourcePrivaliege;
	
}
