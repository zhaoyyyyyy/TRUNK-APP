package com.asiainfo.biapp.si.coc.jauth.sysmgr.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDao;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.RoleDao;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Role;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.RoleService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.utils.SessionInfoHolder;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.RoleVo;

/**
 * @author liukai
 * @date 2013-6-21
 */
@Service
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role,String> implements
		RoleService {
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private SessionInfoHolder sessionInfoHolder;
	
	@Override
	protected BaseDao<Role,String> getBaseDao() {
		return roleDao;
	}
	/**
	 * 保存角色
	 */
	public void saveOrUpdate(Role role) {
		
		if (StringUtils.isBlank(role.getId())) {
			role.setId(null);
			role.setCreateTime(getSystemDate());
			role.setCreateUserId(sessionInfoHolder.getLoginUser().getUserName());
			role.setCreateUserOrgId(sessionInfoHolder.getLoginUser().getOrginfoId());
			role.setAppsysCode(sessionInfoHolder.getAppSysCode());
		} else{
			role.setUpdateTime(getSystemDate());
		}
		
		roleDao.saveOrUpdate(role);
	}
	

	/**
	 * 根据查询条件查询出角色
	 * @author liukai
	 * @param
	 * @date 2013-6-21
	 */
	public JQGridPage<Role> findRoleList(JQGridPage<Role> page, RoleVo roleVo) {
		return roleDao.findRoleList(page, roleVo);
	}
	/**
	 * 删除角色
	 * @author liukai
	 * @param
	 * @date 2013-6-27
	 */
	public void delete(String id){
		roleDao.delete(id);
	}
	/**
	 * 
	 * @describe 根据角色ID获取角色信息
	 * @author liukai
	 * @param
	 * @date 2013-6-27
	 */
	public Role get(String id){
		return roleDao.get(id);
	}
	/**
	 * 根据角色名查询角色
	 * @author liukai
	 * @param
	 * @date 2013-6-27
	 */
	public List<Role> findRoleByName(String id,String roleName){
		return roleDao.findRoleByName(id, roleName);
	}

}
