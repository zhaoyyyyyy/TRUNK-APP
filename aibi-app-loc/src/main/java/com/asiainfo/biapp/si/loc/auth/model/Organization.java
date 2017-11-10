
package com.asiainfo.biapp.si.loc.auth.model;

import java.util.HashSet;
import java.util.Set;

public class Organization {
	private String id;
	private String simpleName;
	private String orgCode;
	private String parentId;
	private Set<Organization> children  = new HashSet<Organization>();
	public String getSimpleName() {
		return simpleName;
	}
	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Set<Organization> getChildren() {
		return children;
	}
	public void setChildren(Set<Organization> children) {
		this.children = children;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	
	
}

