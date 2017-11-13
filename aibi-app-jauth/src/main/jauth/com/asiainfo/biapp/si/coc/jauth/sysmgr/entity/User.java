/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.sysmgr.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import com.asiainfo.biapp.si.coc.jauth.frame.entity.BaseExportEntity;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.utils.DataDicUtil;

/**
 * @describe 用户表
 * @author liukai
 * @date 2013-6-27
 */
@Entity
@Table(name="LOC_SYS_USER")
public class User extends BaseExportEntity {
	
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
	 * 用户名
	 */
	@Column(name="username")
	private String userName;
	/**
	 * 密码
	 */
	@Column(name="password")
	private String password;
	
	/**
	 * 角色集合
	 */
	@ManyToMany(fetch=FetchType.LAZY)  
	@JoinTable(name="LOC_SYS_USEROLE",  
	joinColumns={@JoinColumn(name="USER_ID")},inverseJoinColumns={@JoinColumn(name="ROLE_ID")})  
	private Set<Role> roleSet;
    
	/**
	 * 数据范围集合
	 */
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="LOC_SYS_USERGROUP",  
	joinColumns={@JoinColumn(name="USER_ID")},inverseJoinColumns={@JoinColumn(name="GROUP_ID")})  
	private Set<Group> groupSet;
	/**
	 * 组织属性
	 */
	@ManyToMany(fetch=FetchType.LAZY)  
	@JoinTable(name="LOC_SYS_ORG_USER",  
	joinColumns={@JoinColumn(name="USER_ID")},inverseJoinColumns={@JoinColumn(name="ORGCODE",referencedColumnName="ORGCODE")})
	private Set<Organization> orgSet;
	
	/**
	 * 状态
	 */
	@Column(name="status")
	private Integer status;
	/**
	 * 创建人ID
	 */
	@Column(name="createuserid")
	private String createUserId;
	/**
	 * 创建人组织ID
	 */
	@Column(name="creatororgid")
	private String createOrgId;
	/**
	 * 创建时间
	 */
	@Column(name="createtime")
	private Date createTime;
	/**
	 * 是否是管理员
	 */
	@Column(name="isadmin")
	private Integer isAdmin;
	/**
	 *真实姓名
	 */
	@Column(name="realname")
	private String realName;
	/**
	 * 手机号
	 */
	@Column(name="phonenumber")
	private String phoneNumber;
	/**
	 * 邮箱
	 */
	@Column(name="email")
	private String email;
	/**
	 * 性别
	 */
	@Column(name="sex")
	private String sex;



	public Set<Organization> getOrgSet() {
		return orgSet;
	}

	public void setOrgSet(Set<Organization> orgSet) {
		this.orgSet = orgSet;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateOrgId() {
		return createOrgId;
	}

	public void setCreateOrgId(String createOrgId) {
		this.createOrgId = createOrgId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Set<Role> getRoleSet() {
		return roleSet;
	}

	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}

	public Set<Group> getGroupSet() {
		return groupSet;
	}

	public void setGroupSet(Set<Group> groupSet) {
		this.groupSet = groupSet;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	/**
	 * 为展示组织名称
	 */
	@Transient
	public String orgNames;

	public String getOrgNames() {
		if (!orgSet.isEmpty()) {
			StringBuffer s = new StringBuffer();
			for (Organization org : orgSet) {
				if (!StringUtils.isBlank(s)) {
					s.append("/");
				}
				s.append(org.getSimpleName());
			}
			return s.toString();
		} else {
			return null;
		}
	}
	public void setOrgNames(String orgNames) {
		this.orgNames = orgNames;
	}

	/**
	 * 为展示角色名称
	 */
	@Transient
	public String roleNames;

	public String getRoleNames() {
		if (!roleSet.isEmpty()) {
			StringBuffer s = new StringBuffer();
			for (Role role : roleSet) {
				if (!StringUtils.isBlank(s)) {
					s.append("/");
				}
				s.append(role.getRoleName());
			}
			return s.toString();
		} else {
			return null;
		}
	}

	/**
	 * 展示数据范围
	 */
	@Transient
	public String groupNames;
	
	public String getGroupNames() {
		if (!groupSet.isEmpty()) {
			StringBuffer s = new StringBuffer();
			for (Group group : groupSet) {
				if (!StringUtils.isBlank(s)) {
					s.append("/");
				}
				s.append(group.getGroupName());
			}
			return s.toString();
		} else {
			return null;
		}
	}

	/**
	 * 用于显示是否是管理员
	 */
	@Transient
	public boolean userIsAdmin;

	public boolean getUserIsAdmin() {
		if ("1".equals(this.isAdmin)) {
			return true;
		}
		return false;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	// 导出列
	@Override
	public Object[] propertyToArray() {
		String statusTypeDesc = "";
		if (null != getStatus()) {
			// 状态
			statusTypeDesc = DataDicUtil.getCodeDesc("YHZT", getStatus()
					.toString());
		}
		String create = "";
		if (null != getCreateTime())
			create = getCreateTime().toString();
		return new String[] { getUserName(), getRealName(), getSex(),
				getOrgNames(), getRoleNames(), getGroupNames(), statusTypeDesc};
	}




}
