package com.asiainfo.biapp.si.coc.jauth.sysmgr.vo;

import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Role;

/**
 * 角色Vo
 * 
 * @author liukai
 * @date 2013-6-21
 */
public class RoleVo extends Role {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 角色创建时间（开始）
	 */
	public String roleCreateTimeStart;
	/**
	 * 角色创建时间（结束）
	 */
	public String roleCreateTimeEnd;

	public String getRoleCreateTimeStart() {
		return roleCreateTimeStart;
	}

	public void setRoleCreateTimeStart(String roleCreateTimeStart) {
		this.roleCreateTimeStart = roleCreateTimeStart;
	}

	public String getRoleCreateTimeEnd() {
		return roleCreateTimeEnd;
	}

	public void setRoleCreateTimeEnd(String roleCreateTimeEnd) {
		this.roleCreateTimeEnd = roleCreateTimeEnd;
	}

}
