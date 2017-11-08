package com.asiainfo.biapp.si.coc.jauth.sysmgr.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.asiainfo.biapp.si.coc.jauth.frame.entity.BaseExportEntity;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.utils.DataDicUtil;

/**
 * 组织成员实体
 * @author ljs
 * @date 2013-6-26
 */
@Entity
//@Table(name="LOC_SYS_ORG_USER")
public class OrgUser extends BaseExportEntity {
	
	/**@describe 组织机构状态*/
	public static final String OPEN = "OPEN";//开启
	public static final String CLOSE = "CLOSE";//关闭
	
	/**@describe 组织成员类型*/
	public static final String ADMIN = "MANAGER";//管理员
	public static final String COMMON = "USER";//普通成员
	public static final String HONORED = "03";//嘉宾
	public static final String PRESCO = "04";//预录成员
	public static final String TEACHER = "TEACHER";//教师
	public static final String MENBER="05";//用于判断  
	
	
	
	/**
	 */
	private static final long serialVersionUID = 1L;
	
	@Id @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
	@Override
    public String getId() {
		return id;
	}
	@Override
    public void setId(String id) {
		this.id = id;
	}
	/** 用户   */
	private User user;
	/** 用户Id   */
	@Column(name="user_id")
	private String userId;
	/** 性别   */
	@Column(name="sex")
	private String sex;
	/** 真实姓名（真实/预录）  */
	@Column(name="username")
	private String userName;
	/** 用户类型(组织内的身份) */
	@Column(name="type")
	private String type;
	/** 状态    */
	@Column(name="status")
	private String status;
	/** 加入时间    */
	@Column(name="jointime")
	private Date joinTime;
	/** 组织CODE */
	@Column(name="orgcode")
	private String orgCode;
	/** 所属组织    */
	private Organization organization;
	/**是否为管理员*/
	@Column(name="isadmin")
	private String isAdmin;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getJoinTime() {
		return joinTime;
	}
	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	@Override
	public Object[] propertyToArray() {
		String CYZT = DataDicUtil.getCodeDesc("CYZT",getStatus());
		String YHSF = DataDicUtil.getCodeDesc("YHSF",getType());
		return new String[]{getUser().getUserName().toString(),getUserName(),CYZT,getJoinTime().toString(),YHSF};
	}
	
	public String getHeadImage(){
		return "";
	}
	public String getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}
}
