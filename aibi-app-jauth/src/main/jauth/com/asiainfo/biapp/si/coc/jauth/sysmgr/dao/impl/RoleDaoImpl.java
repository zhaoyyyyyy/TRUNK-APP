/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.coc.jauth.frame.Constants;
import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.page.Page;
import com.asiainfo.biapp.si.coc.jauth.frame.util.DateUtil;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.RoleDao;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Role;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.RoleVo;

/**
 * @author liukai
 * @date 2013-6-21
 */
@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role,String> implements RoleDao {
	

	/**
	 * 查询角色
	 * @author liukai
	 * @param
	 * @date 2013-6-21
	 */
	public JQGridPage<Role> findRoleList(JQGridPage<Role> page, RoleVo roleVo) {
		// 拼装hql 及参数
		Map<String, Object> params = new HashMap<>();
		StringBuffer hql = new StringBuffer("from Role r where 1=1");
		//创建人ID
		if (StringUtils.isNotBlank(roleVo.getCreateUserId())) {
			hql.append(" and r.createUserId = :createUserId");
			params.put("createUserId", roleVo.getCreateUserId());
		}
		// 角色名称
		if (StringUtils.isNotBlank(roleVo.getRoleName())) {
			hql.append(" and r.roleName LIKE :roleName");
			params.put("roleName", "%" + roleVo.getRoleName() + "%");
		}
		// 创建时间
		if (StringUtils.isNotBlank(roleVo.roleCreateTimeStart)) {
			hql.append(" and r.createTime >= :roleCreateTimeStart ");
			params.put("roleCreateTimeStart", DateUtil.parse(roleVo.roleCreateTimeStart, DateUtil.FMT_DATE_YYYYMMDDHHmmss));
		}
		if (StringUtils.isNotBlank(roleVo.roleCreateTimeEnd)) {
			hql.append(" and r.createTime <= :roleCreateTimeEnd ");
			params.put("roleCreateTimeEnd",  DateUtil.parse(roleVo.roleCreateTimeEnd, DateUtil.FMT_DATE_YYYYMMDDHHmmss));
		}
		if(StringUtils.isNotBlank(page.getSortCol())){
			hql.append(" order by r."+page.getSortCol()+" "+page.getSortOrder());
		}else{
			hql.append(" order by r.createTime desc");
		}
		return (JQGridPage<Role>) super.findPageByHql(page, hql.toString(),params);
	}

	/**
	 * 保存角色
	 */
	public void saveOrUpdate(Role role) {
		super.saveOrUpdate(role);
	}

	/**
	 * 
	 * @describe 角色删除
	 * @author liukai
	 * @param
	 * @date 2013-6-27
	 */
	public void delete(String id) {
		super.delete(id);
	}

	/**
	 * 
	 * @describe 根据角色ID获取角色信息
	 * @author liukai
	 * @param
	 * @date 2013-6-27
	 */
	public Role get(String id) {
		return super.get(id);
	}

	/**
	 * 根据角色名查询角色
	 * @author liukai
	 * @param
	 * @date 2013-6-27
	 */
	public List<Role> findRoleByName(String id, String roleName) {
		// 拼装hql 及参数
		Map<String, Object> params = new HashMap<>();
		StringBuffer hql = new StringBuffer(
				"from Role r where roleName = :roleName");
		params.put("roleName", roleName);
		// 有ID则是编辑
		if (StringUtils.isNotBlank(id)) {
			hql.append(" and id != :id");
			params.put("id", id);
		}
		return super.findListByHql(hql.toString(), params);
	}

}
