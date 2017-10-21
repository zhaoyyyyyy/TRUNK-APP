package com.asiainfo.biapp.si.coc.jauth.sysmgr.vo;

import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Organization;

public class OrganizationVo extends Organization {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8223720002165227023L;
	private String parentOrgCode;
	public String getParentOrgCode() {
		return parentOrgCode;
	}
	public void setParentOrgCode(String parentOrgCode) {
		this.parentOrgCode = parentOrgCode;
	}
}
