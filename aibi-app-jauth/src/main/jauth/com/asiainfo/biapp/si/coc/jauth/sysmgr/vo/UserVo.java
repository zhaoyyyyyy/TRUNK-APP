/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.sysmgr.vo;

import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.User;

/**
 * @describe 
 * @author liukai
 * @date 2013-6-28
 */
public class UserVo extends User {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 创建时间（开始）
	 */
	private String createTimeStart;
	/**
	 * 创建时间（结束）
	 */
	private String createTimeEnd;
	/**
	 * 角色名称
	 */
	private String roleName;
	/**
	 * 组织名称
	 */
	private String orgName;
	
	/**
	 * 距离生日的时间
	 */
	private int distBirthDay;
	/**
	 * 头像
	 */
	private String headImg;

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public int getDistBirthDay() {
		return distBirthDay;
	}

	public void setDistBirthDay(int distBirthDay) {
		this.distBirthDay = distBirthDay;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	
}
