package com.asiainfo.biapp.si.loc.auth.model;

/**
 * 
 * @author Administrator
 *
 */
public class Resource {

	public final static String MENU = "LOC_MENU";
	public final static String DOM = "LOC_DOM";
	public final static String API = "LOC_API";
	
	
	private String resourceName;
	private String resourceCode;
	private String parentId;
	private String resourceType;
	
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getResourceCode() {
		return resourceCode;
	}
	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	
}
