package com.asiainfo.biapp.si.coc.jauth.sysmgr.vo;

import java.util.Date;

import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.OrgUser;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.User;


/**
 * 成员申请审核vo(值对象)用于记录数据
 * @author ljs
 * @date 2013-6-26
 */
public class OrgUserVo extends OrgUser {

	/**
	 */
	private static final long serialVersionUID = 1L;
	/** 姓名/账户  */
	private String nameOrUserName;
	/** 加入时间区间最小值 */
	private Date joinTime_min;
	/** 加入时间区间最大值 */
	private Date joinTime_max;
	
	public String getNameOrUserName() {
		return nameOrUserName;
	}
	public void setNameOrUserName(String nameOrUserName) {
		this.nameOrUserName = nameOrUserName;
	}
	public Date getJoinTime_min() {
		return joinTime_min;
	}
	public void setJoinTime_min(Date joinTimeMin) {
		joinTime_min = joinTimeMin;
	}
	public Date getJoinTime_max() {
		return joinTime_max;
	}
	public void setJoinTime_max(Date joinTimeMax) {
		joinTime_max = joinTimeMax;
	}
	
	
	/** 用户   */
	private User user;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Object[] propertyToArray() {
		return new String[]{getUserName(),getSex(),getUser().getPhoneNumber(),getUser().getEmail()};
	}
}
