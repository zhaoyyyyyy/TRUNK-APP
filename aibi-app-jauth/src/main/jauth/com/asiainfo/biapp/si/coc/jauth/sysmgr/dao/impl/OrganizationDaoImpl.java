/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.coc.jauth.frame.util.StringUtil;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.OrganizationDao;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Organization;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.User;

/**
 * @describe 组织机构持久层
 * @author zhougz
 * @date 2013-6-19
 */
@Repository
public class OrganizationDaoImpl extends BaseDaoImpl<Organization,String> implements OrganizationDao {


	/**
	 * 
	 * @describe 列出某个用户有权限访问的组织
	 * @author zhougz
	 * @date 2016-5-27
	 * @param userId
	 * @return
	 */
	@Override
	public List<Organization> queryOwenOrgByUserId(String userId) {
	    
		StringBuffer sql = new StringBuffer("select * from t_sys_organization o where o.id != '99' and ")
		.append(" exists  (select 1 from T_SYS_GROUPORG go  where go.org_code = o.orgcode  and ")
		.append(" exists (select 1 from T_SYS_USERGROUP ug where ug.user_id = ? and ug.group_id = go.group_id)) order by o.id ");
		List<Organization> list = super.findListBySql(sql.toString(), userId);
		
		List<Organization> returnList = new ArrayList<>();
		for(int i = 0;i<list.size();i++){
		    Organization o = new Organization();
		    Object obj = list.get(i);
		    Object[] objArr = (Object[]) obj;
		    o.setOrgCode(objArr[0].toString());
		    o.setSimpleName(objArr[0].toString());
		    returnList.add(o);
		}
		return super.findListBySql(sql.toString(), userId);
	}
	
	/**
	 * 删除某个实体
	 * 
	 * @param entity
	 */
	public void deleteObject(Organization organization) {
		this.delete(organization.getId());
		super.excuteSql("delete from T_SNS_SECURITY_ORG t where t.ORG_CODE=?",
				organization.getOrgCode());
	}

	/**
	 * 
	 * @describe 查询用户的所有父节点
	 * @author liukai
	 * @param
	 * @date 2013-12-25
	 */
	public List<Organization> selectUserAllParentCode(User user) {
		// 该用户有组织
		if (!user.getOrgSet().isEmpty()) {
			Map<String, Object> params = new HashMap<>();
			StringBuffer sql = new StringBuffer(
			"select distinct o.orgCode  from T_SYS_ORGANIZATION o");
			sql.append(" where o.orgcode in(");
			int i = 1;
			for (Organization o : user.getOrgSet()) {
				sql.append(":o" + i + "");
				params.put("o" + i, o.getOrgCode());
				if (i < user.getOrgSet().size()) {
					sql.append(" ,");
				}
				i++;
			}
			sql.append(")and o.parent_id=o.id");
			
			return super.findListBySql(sql.toString(), params);
		} else {
			return null;
		}
	}

	/**
	 * 根据orgCode获取实体
	 */
	@Override
	public Organization getOrgByOrgCode(String orgCode) {
		return this.findOneByHql("from Organization o where o.orgCode =?0 ",
				orgCode);
	}
	
	/**
	 * 根据orgId获取实体
	 */
	@Override
	public Organization getOrgByOrgId(String orgCode) {
		return this.findOneByHql("from Organization o where o.id =?0 ",
				orgCode);
	}

	/**
	 * 根据orgCode改变组织状态
	 * 
	 * @param orgCode
	 * @return
	 */
	@Override
	public Serializable updateStatus(String orgCode) {
		Organization organization = findOneByHql(
				"from Organization where orgCode =?", orgCode);
		if ("OPEN".equals(organization.getOrgStatus())) {
			organization.setOrgStatus("CLOSE");
		} else {
			organization.setOrgStatus("OPEN");
		}
		updateStatus(orgCode, organization.getOrgStatus());
		return organization.getOrgStatus();
	}

	/**
	 * 根据orgCode改变组织状态为指定码值
	 * 
	 * @param orgCode
	 * @param orgStatus
	 * @return
	 */
	@Override
	public Serializable updateStatus(String orgCode, String orgStatus) {
		return excuteHql(
				"update Organization set orgStatus =? where orgCode =?",
				orgStatus, orgCode);
	}

	/**
	 * 根据orgCode改变组织类型为指定码值
	 * 
	 * @param orgCode
	 * @param orgType
	 * @return
	 */
	@Override
	public Serializable updateType(String orgCode, String orgType) {
		return excuteHql("update Organization set orgType =? where orgCode =?",
				orgType, orgCode);
	}

	/**
	 * 根据orgCode改变组织审核方式为指定值
	 * 
	 * @param orgCode
	 * @param interrogateType
	 * @return
	 */
	@Override
	public Serializable updateInterrogateType(String orgCode,
			String interrogateType) {
		return excuteHql(
				"update Organization set interrogateType =? where orgCode =?",
				interrogateType, orgCode);
	}

	/**
	 * 修改组织
	 * 
	 * @author ljs
	 * @param organization
	 * @return
	 */
	@Override
	public Serializable updateOrganization(Organization organization) {
		Organization org = this.get(organization.getId());
		super.update(org);
		return org.getOrgCode();
	}

	/**
	 * 
	 * @describe 创建组织
	 * @author ljs
	 * @date 2013-7-29
	 * @param organization
	 * @return
	 */
	@Override
	public Serializable createOrganization(Organization organization) {
		return save(organization);
	}

	/**
	 * 
	 * @describe 只删除组织
	 * @author ljs
	 * @date 2013-7-30
	 * @param orgCode
	 * @return
	 */
	@Override
	public boolean deleteOrganization(String orgCode) {
		int hs = excuteHql("delete from Organization where orgCode =?", orgCode);
		if (hs > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @describe 根据组织类型获取组织列表
	 * @author ljs
	 * @date 2013-9-23
	 * @param orgType
	 * @return
	 */
	@Override
	public List<Organization> listOrganization(String orgType) {
		// 拼装hql 及参数
		Map<String, Object> params = new HashMap<>();
		StringBuilder hql = new StringBuilder();
		hql.append("from Organization where 1=1");
		if (StringUtil.isNotEmpty(orgType)) {
			hql.append(" and orgType = :orgType");
			params.put("orgType", orgType);
		}
		return super.findListByHql(hql.toString(), params);
	}

	/**
	 * 
	 * @describe 根据组织类型获取parentId的子组织列表
	 * @author ljs
	 * @date 2013-9-23
	 * @param orgType
	 * @param orgCode
	 * @return
	 */
	@Override
	public List<Organization> listOrganization(String orgType, String parentId) {
		// 拼装hql 及参数
		Map<String, Object> params = new HashMap<>();
		StringBuilder hql = new StringBuilder();
		hql.append("from Organization where 1=1");
		if (StringUtil.isNotEmpty(orgType)) {
			hql.append(" and orgType = :orgType");
			params.put("orgType", orgType);
		}
		if (StringUtil.isNotEmpty(parentId)) {
			hql.append(" and parentId = :parentId");
			params.put("parentId", parentId);
		}
		hql.append(" order by simpleName desc");
		return super.findListByHql(hql.toString(), params);
	}

	/**
	 * 
	 * @describe 列出某个用户加入的组织
	 * @author ljs
	 * @date 2013-10-10
	 * @param userId
	 * @return
	 */
	@Override
	public List<Organization> listOrganizationByUserId(String userId) {
		String hql = "from Organization where orgCode in (select orgCode from OrgUser where userId =? and status !='CLOSE') order by orgType asc, orderNum asc, createTime desc";
		return findListByHql(hql, userId);
	}
	
	/**
	 * @describe 列出某个用户加入的组织
	 * @author XieGaosong
	 * @date 2013-10-10
	 * @param userId
	 * @return
	 */
	public List<Organization> findListOrganization(String orgCode) {
		String hql = "from Organization";
		String code = "";
		if (!StringUtil.isEmpty(orgCode)) {
			hql = "from Organization where orgCode=?";
			Organization organization = super.findOneByHql(hql, orgCode);
			if (organization != null) {
				hql = "from Organization where parentId=? order by simpleName desc";
				code = organization.getId();
			}
		}
		return findListByHql(hql, code);
	}
}
