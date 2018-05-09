package com.asiainfo.biapp.si.loc.auth.model;

import java.util.Date;
import java.util.LinkedHashSet;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;

/**
 * 
 * @author Administrator
 *
 */
public class Resource  extends BaseEntity{

	public final static String MENU = "LOC_MENU";
	public final static String DOM = "LOC_DOM";
	public final static String API = "LOC_API";
	
	
	private String resourceName;
	private String resourceCode;
	private String parentId;
	
	private String type;
	
	private String id;
	private String appSysCode;
	private String address;
	private String orginfoId;
	private Date createTime;
	private Integer dispOrder;
	private Integer status;
	private String sessionName;
	private LinkedHashSet<Resource> children = new LinkedHashSet<>();
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getAppSysCode() {
        return appSysCode;
    }
    public void setAppSysCode(String appSysCode) {
        this.appSysCode = appSysCode;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getOrginfoId() {
        return orginfoId;
    }
    public void setOrginfoId(String orginfoId) {
        this.orginfoId = orginfoId;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Integer getDispOrder() {
        return dispOrder;
    }
    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getSessionName() {
        return sessionName;
    }
    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }
    public LinkedHashSet<Resource> getChildren() {
        return children;
    }
    public void setChildren(LinkedHashSet<Resource> children) {
        this.children = children;
    }
	
}
