/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.sysmgr.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.OrderBy;

import com.asiainfo.biapp.si.coc.jauth.frame.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @describe 资源表
 * @author liukai
 * @date 2013-6-24
 */
@Entity
@Table(name="LOC_SYS_RESOURCE")
public class Resource extends BaseEntity {
	/**    
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id @Column(name="ID")
	@GenericGenerator(name="idGenerator", strategy="uuid") 
	@GeneratedValue(generator="idGenerator") //使用uuid的生成策略  
    private String id;
	/**
	 * 归属
	 */
	@Column(name="appsyscode")
	private String appSysCode;
	/**
	 * 资源名称
	 */
	@Column(name="resourcename")
	private String resourceName;
	/**
	 * 类型
	 */
	@Column(name="type")
	private String type;
	/**
	 * 地址
	 */
	@Column(name="address")
	private String address;
	/**
	 * 父资源ID
	 */
	@Column(name="parent_id")
	private String parentId;
	/**
	 * 组织ID
	 */
	@Column(name="orginfo_id")
	private String orginfoId;
	/**
	 * 创建时间
	 */
	@Column(name="createtime")
	private Date createTime;
	/**
	 * 创建时间
	 */
	@JsonIgnore
	@ManyToOne  //注解的方式需要指定延迟加载策略
	@JoinColumn(name="parent_id",insertable=false,updatable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	private Resource parentResource;
	/**
	 * 资源代码
	 */
	@Column(name="resourcecode")
	private String resourceCode;
	/**
	 * 排序
	 */
	@Column(name="disporder")
	private Integer dispOrder;
	/**
	 * 状态
	 */
	@Column(name="status")
	private Integer status;
	/**
	 * 权限名称
	 */
	@Column(name="sessionname")
	private String sessionName;
	/**
	 * 
	 */
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="LOC_SYS_ROLERESOURCE",  
	joinColumns={@JoinColumn(name="RESOURCE_ID")},inverseJoinColumns={@JoinColumn(name="ROLE_ID")})  
	public Set<Role> roleSet = new HashSet<>(0);
	/** 子组织列表 */
	@OneToMany(cascade = CascadeType.ALL)
	@OrderBy(clause = "disporder")
	@JoinColumn(name="parent_id",insertable=false,updatable=false)
	private Set<Resource> children = new HashSet<>(0);
	
	@Override
	public String getId() {
		return id;
	}
	@Override
	public void setId(String id) {
		this.id = id;
	}
	public String getAppSysCode() {
		return appSysCode;
	}
	public void setAppSysCode(String appSysCode) {
		this.appSysCode = appSysCode;
	}
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
	public Resource getParentResource() {
		return parentResource;
	}
	public void setParentResource(Resource parentResource) {
		this.parentResource = parentResource;
	}
	public String getResourceCode() {
		return resourceCode;
	}
	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
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
	public Set<Role> getRoleSet() {
		return roleSet;
	}
	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}
	public Set<Resource> getChildren() {
		return children;
	}
	public void setChildren(Set<Resource> children) {
		this.children = children;
	}

}
