/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.sysmgr.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OrderBy;

import com.asiainfo.biapp.si.coc.jauth.frame.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @describe 组织机构
 * @author zhougz
 * @date 2013-6-19
 */
@Entity
@Table(name="CI_SYS_ORGANIZATION")
public class Organization extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id @Column(name="ID")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name="idGenerator", strategy="uuid") 
	@GeneratedValue(generator="idGenerator") //使用uuid的生成策略
    private String id;
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	

	/** 全名   */
	@Column(name="fullname")
	private String fullName;
	
	/** 缩写   */
	@Column(name="simplename")
	private String simpleName;
	
	/** 类型 */
	@Column(name="orgtype")
	private String orgType;
	
	/** 组织CODE   */
	@Column(name="orgcode")
	private String orgCode;
	
	/** 排序序号   */
	@Column(name="ordernum")
	private Integer orderNum;
	
	/** 父组织ID   */
	@Column(name="parent_id")
	private String parentId;
	
	/** 父组织   */
	@Transient
	private Organization parentOrg;
	
	
	/** 子组织列表   */
	@OrderBy(clause = "orgcode")
	@OneToMany
	@JoinColumn(name="parent_id")
	private Set<Organization> children  = new HashSet<Organization>(0);
	
	/** 创建人姓名   */
	@Column(name="creatername")
	private String createrName;
	
	/** 创建时间   */
	@Column(name="createtime")
	private Date createTime;
	
	/** 创建人账号   */
	@Column(name="creater")
	private String creater;
	
//	/** 创建人   */
//	/** 子组织列表   */
//	@ManyToOne
//	@JoinColumn(name="creater")
//	private User user;
	
	/** 审核方式   */
	@Column(name="interrogatetype")
	private String interrogateType;
	
	/** 组织状态   */
	@Column(name="orgstatus")
	private String orgStatus;
	
	/** 树路径   */
	@Column(name="treepath")
	private String treePath;
	
	
	/**
	 * 用户集合
	 */
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY)  
	@JoinTable(name="CI_SYS_ORG_USER",
	joinColumns={@JoinColumn(name="ORGCODE",referencedColumnName="ORGCODE")},inverseJoinColumns={@JoinColumn(name="USER_ID")})  
	public Set<User> userSet;
	
	public Set<User> getUserSet() {
		return userSet;
	}

	public void setUserSet(Set<User> userSet) {
		this.userSet = userSet;
	}
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getSimpleName() {
		return simpleName;
	}
	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public Set<Organization> getChildren() {
		return children;
	}
	public void setChildren(Set<Organization> children) {
		this.children = children;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
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
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
//	public User getUser() {
//		return user;
//	}
//	public void setUser(User user) {
//		this.user = user;
//	}
	
}
