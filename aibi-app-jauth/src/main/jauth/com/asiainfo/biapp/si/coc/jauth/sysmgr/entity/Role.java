/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.sysmgr.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.asiainfo.biapp.si.coc.jauth.frame.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @describe 角色
 * @author liukai
 * @date 2013-6-21
 */
@Entity
@Table(name="LOC_SYS_ROLE")
public class Role extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id @Column(name="ID")
	@GenericGenerator(name="idGenerator", strategy="uuid") 
	@GeneratedValue(generator="idGenerator") //使用uuid的生成策略  
    private String id;
	@Override
    public String getId() {
		return id;
	}
	@Override
	public void setId(String id) {
		this.id = id;
	}
	
	
	/**
	 *归属
	 */
	@Column(name="appsyscode")
	private String appsysCode;
	
	/**
	 * 角色名称
	 */
	@Column(name="rolename")
	private String roleName;
	
	/**
	 * 组织信息ID
	 */
	@Column(name="orginfo_id")
	private String orginfoId;
	
	/**
	 * 图片位置
	 */
	@Column(name="picturehome")
	private String pictureHome;
	
	/**
	 * 角色描述
	 */
	@Column(name="roledesc")
	private String roleDesc;
	
	/**
	 * 创建人ID
	 */
	@Column(name="createuserid")
	private String createUserId;
	
	/**
	 * 创建人组织ID
	 */
	@Column(name="createuserorgid")
	private String createUserOrgId;
	
	/**
	 * 更新时间
	 */
	@Column(name="updatetime")
	private Date updateTime;
	/**
	 * 创建时间
	 */
	@Column(name="createtime")
	private Date createTime;
	/**
	 * 角色编码
	 */
	@Column(name="rolecode")
	private String roleCode;
	
	
	/**
	 * 资源集合
	 */
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="LOC_SYS_ROLERESOURCE",  
	joinColumns={@JoinColumn(name="ROLE_ID")},inverseJoinColumns={@JoinColumn(name="RESOURCE_ID")})  
	private Set<Resource> resourceSet;
	
	/**
	 * 用户集合
	 */
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="LOC_SYS_USEROLE",  
	joinColumns={@JoinColumn(name="ROLE_ID")},inverseJoinColumns={@JoinColumn(name="USER_ID")})  
	private Set<User> userSet;

	public String getAppsysCode() {
		return appsysCode;
	}

	public void setAppsysCode(String appsysCode) {
		this.appsysCode = appsysCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getOrginfoId() {
		return orginfoId;
	}

	public void setOrginfoId(String orginfoId) {
		this.orginfoId = orginfoId;
	}

	public String getPictureHome() {
		return pictureHome;
	}

	public void setPictureHome(String pictureHome) {
		this.pictureHome = pictureHome;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserOrgId() {
		return createUserOrgId;
	}

	public void setCreateUserOrgId(String createUserOrgId) {
		this.createUserOrgId = createUserOrgId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public Set<Resource> getResourceSet() {
		return resourceSet;
	}

	public void setResourceSet(Set<Resource> resourceSet) {
		this.resourceSet = resourceSet;
	}

	public Set<User> getUserSet() {
		return userSet;
	}

	public void setUserSet(Set<User> userSet) {
		this.userSet = userSet;
	}

}
