
package com.asiainfo.biapp.si.loc.auth.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;

public class Organization extends BaseEntity{
	private String id;
	private String simpleName;
	
	private String orgCode; //组织编码（重要）  
	private Integer level;  //组织级别（重要）
	
	private String orgType;
	private String fullName;
	private Integer orderNum;
	private Organization parentOrg;
	private String createrName;
	private Date createTime;
	private String creater;
	private String interrogateType;
	private String orgStatus;
	private String treePath;
	private Integer levelId;
	
    public Integer getLevelId() {
        return levelId;
    }
    
    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }
    public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	
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
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
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
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public Integer getOrderNum() {
        return orderNum;
    }
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
    public Organization getParentOrg() {
        return parentOrg;
    }
    public void setParentOrg(Organization parentOrg) {
        this.parentOrg = parentOrg;
    }
    public String getCreaterName() {
        return createrName;
    }
    public void setCreaterName(String createrName) {
        this.createrName = createrName;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getCreater() {
        return creater;
    }
    public void setCreater(String creater) {
        this.creater = creater;
    }
    public String getInterrogateType() {
        return interrogateType;
    }
    public void setInterrogateType(String interrogateType) {
        this.interrogateType = interrogateType;
    }
    public String getOrgStatus() {
        return orgStatus;
    }
    public void setOrgStatus(String orgStatus) {
        this.orgStatus = orgStatus;
    }
    public String getTreePath() {
        return treePath;
    }
    public void setTreePath(String treePath) {
        this.treePath = treePath;
    }

	
	
}

