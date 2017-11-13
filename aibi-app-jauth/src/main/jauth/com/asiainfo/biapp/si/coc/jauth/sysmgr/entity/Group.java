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
 * @describe 用户范围
 * @author liukai
 * @date 2013-6-27
 */
@Entity
@Table(name="LOC_SYS_GROUP")
public class Group extends BaseEntity {
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
	 * 组织信息ID
	 */
	@Column(name="orginfo_id")
	public String orginfoId;
	/**
	 * 数据范围名称
	 */
	
	@Column(name="groupname")
	private String groupName;
	/**
	 * 数据范围描述
	 */
	@Column(name="groupdesc")
	private String groupDesc;
	/**
	 * 创建人ID
	 */
	@Column(name="createuserid")
	private String createUserId;
	/**
	 * 创建时间
	 */
	@Column(name="createtime")
	private Date createTime;
	/**
	 * 变更时间
	 */
	@Column(name="updatetime")
	private Date updateTime;
	/**
	 * 创建人组织
	 */
	@Column(name="createororgid")
	private String createOrgId;
	/**
	 * 数据范围代码
	 */
	@Column(name="groupcode")
	private String groupCode;

	@ManyToMany(fetch=FetchType.LAZY)  
	@JoinTable(name="LOC_SYS_USERGROUP",  
	joinColumns={@JoinColumn(name="GROUP_ID")},inverseJoinColumns={@JoinColumn(name="USER_ID")})
	@JsonIgnore
	private Set<User> userSet;
	
	@ManyToMany(fetch=FetchType.LAZY)  
	@JoinTable(name="LOC_SYS_GROUPORG",  
	joinColumns={@JoinColumn(name="GROUP_ID")},inverseJoinColumns={@JoinColumn(name="ORG_CODE",referencedColumnName="ORGCODE")})  
	private Set<Organization> organizationSet;

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

	public String getOrginfoId() {
		return orginfoId;
	}

	public void setOrginfoId(String orginfoId) {
		this.orginfoId = orginfoId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateOrgId() {
		return createOrgId;
	}

	public void setCreateOrgId(String createOrgId) {
		this.createOrgId = createOrgId;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public Set<User> getUserSet() {
		return userSet;
	}

	public void setUserSet(Set<User> userSet) {
		this.userSet = userSet;
	}

	public Set<Organization> getOrganizationSet() {
		return organizationSet;
	}

	public void setOrganizationSet(Set<Organization> organizationSet) {
		this.organizationSet = organizationSet;
	}

}
