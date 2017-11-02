package com.asiainfo.biapp.si.coc.jauth.sysmgr.dao;

import java.util.List;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDao;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.page.Page;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Role;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.RoleVo;

/**
 * 角色的Dao
 * 
 * @author liukai
 * @date 2013-6-21
 */
public interface RoleDao extends BaseDao<Role,String> {
	/**
	 * 查询角色
	 * 
	 * @author liukai
	 * @param
	 * @date 2013-6-21
	 */
	public JQGridPage<Role> findRoleList(JQGridPage<Role> page, RoleVo roleVo);

	/**
	 * 根据角色名查询角色
	 * 
	 * @author liukai
	 * @param
	 * @date 2013-6-27
	 */
	public List<Role> findRoleByName(String id, String roleName);
}
