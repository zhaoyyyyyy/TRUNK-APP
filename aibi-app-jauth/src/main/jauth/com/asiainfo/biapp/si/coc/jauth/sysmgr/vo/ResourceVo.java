package com.asiainfo.biapp.si.coc.jauth.sysmgr.vo;

import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Resource;

/**
 * 角色Vo
 * 
 * @author liukai
 * @date 2013-6-21
 */
public class ResourceVo extends Resource {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	/**
	 * 资源名称
	 */
	public String resourceName;
	/**
	 * 类型
	 */
	public String type;
	/**
	 * 地址
	 */
	public String address;
	/**
	 * 父资源ID
	 */
	public String parentId;
	/**
	 * 资源代码
	 */
	public String resourceCode;
	/**
	 * 状态
	 */
	public Integer status;

	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getResourceCode() {
		return resourceCode;
	}
	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
