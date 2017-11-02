/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.ResourceDao;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Resource;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.User;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.ResourceVo;

/**
 * @author liukai
 * @date 2013-6-25
 */
@Repository
public class ResourceDaoImpl extends BaseDaoImpl<Resource,String> implements
		ResourceDao {

	/**
	 * 查询资源
	 * 
	 * @author zhougz
	 * @param
	 * @date 2016-6-01
	 */
	public JQGridPage<Resource> findResourceList(JQGridPage<Resource> page, ResourceVo resourceVo) {
		// 拼装hql 及参数
		Map<String, Object> params = new HashMap<>();
		StringBuffer hql = new StringBuffer("from Resource r where 1=1");
		
		// 资源集合
		if (!resourceVo.roleSet.isEmpty()) {
			hql.append(" and exists(select 'X' FROM  r.roleSet role where role in (:roleSet))");
			params.put("roleSet",resourceVo.roleSet);
		}
		// 资源名称
		if (StringUtils.isNotBlank(resourceVo.resourceName)) {
			hql.append(" and r.resourceName LIKE :resourceName");
			params.put("resourceName", "%" + resourceVo.resourceName + "%");
		}
		// 地址名称
		if (StringUtils.isNotBlank(resourceVo.address)) {
			hql.append(" and r.address LIKE :address");
			params.put("address", "%" + resourceVo.address + "%");
		}
		// 编码
		if (StringUtils.isNotBlank(resourceVo.resourceCode)) {
			hql.append(" and r.resourceCode LIKE :resourceCode");
			params.put("resourceCode", "%" + resourceVo.resourceCode + "%");
		}
				
		// 类型
		if (StringUtils.isNotBlank(resourceVo.type)) {
			hql.append(" and r.type = :type");
			params.put("type",  resourceVo.type );
		}
		
		// 父资
		if (StringUtils.isNotBlank(resourceVo.parentId)) {
			hql.append(" and r.parentId = :parentId");
			params.put("parentId",  resourceVo.parentId );
		}
		if(StringUtils.isNotBlank(page.getSortCol())){
			hql.append(" order by r."+page.getSortCol()+" "+page.getSortOrder());
		}else{
			hql.append(" order by r.createTime desc");
		}
		return (JQGridPage<Resource>) super.findPageByHql(page, hql.toString(),params);
	}

	/**
	 * 保存资源
	 * 
	 * @author zhougz
	 * @param
	 * @date 2016-6-01
	 */
	public void saveOrUpdate(Resource resource) {
		super.saveOrUpdate(resource);
	}
	
	
	@Override
	public Resource getOrgByOrgCode(String resourceCode) {
		return this.findOneByHql("from Resource r where r.resourceCode = ?0 and r.status=0",
				resourceCode);
	}

	/**
	 * 
	 * @describe 根据角色查询
	 * @author liukai
	 * @param
	 * @date 2013-7-30
	 */
	public List<Resource> getParentResourceByRole(User user, String parentId) {
		StringBuffer hql = new StringBuffer(
				"from Resource r where exists(select 'X' FROM  r.roleSet role where role in (:roleSet)) and r.parentId =:parentId and r.status=0 order by r.dispOrder");
		Map<String, Object> map = new HashMap<>();
		map.put("roleSet", user.getRoleSet());
		map.put("parentId", parentId);
		return this.findListByHql(hql.toString(), map);
	}

	/**
	 * @describe 查询资源集合 (可扩展，自带条件)
	 * @author XieGaosong
	 * @return List<Resource>
	 * @date 2013-7-16
	 */
	public List<Resource> findResourceList(String orgId) {
		StringBuffer hql = new StringBuffer("from Resource r where 1=1");
		Map<String, String> map = new HashMap<>();
		if(StringUtils.isNotBlank(orgId)){
			hql.append(" and r.orginfoId = :orginfoId");
			map.put("orginfoId",  orgId );
		}
		return this.findListByHql(hql.toString(), map);
	}
	
	@Override
	public List<Resource> getResourceByParentId(String parentId) {
		StringBuffer hql = new StringBuffer(
				"from Resource r where r.parentId =:parentId and r.status=0 order by r.dispOrder");
		Map<String, Object> map = new HashMap<>();
		map.put("parentId", parentId);
		return this.findListByHql(hql.toString(), map);
	}
	
}
