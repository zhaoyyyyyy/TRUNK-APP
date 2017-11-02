/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.sysmgr.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDao;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.ResourceDao;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Resource;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.User;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.ResourceService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.utils.SessionInfoHolder;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.ResourceVo;

/**
 * @author liukai
 * @date 2013-6-25
 */
@Service
@Transactional
public class ResourceServiceImpl extends BaseServiceImpl<Resource,String> implements
		ResourceService {
	@Autowired
	private ResourceDao resourceDao;
	@Autowired
	private SessionInfoHolder sessionInfoHolder;
	
	@Override
	protected BaseDao<Resource,String> getBaseDao() {
		return resourceDao;
	}
	
	
	
	/**
	 * 保存角色
	 */
	public void saveOrUpdate(Resource resource) {
		Resource saveResource;
		if (StringUtils.isEmpty(resource.getId())) {
			saveResource = new Resource();
			BeanUtils.copyProperties(resource, saveResource);
			saveResource.setId(null);
			saveResource.setStatus(0);
			saveResource.setType("菜单");
			saveResource.setCreateTime(getSystemDate());
			saveResource.setAppSysCode("1");
		} else{
			saveResource = resourceDao.get(resource.getId());
			saveResource.setAddress(resource.getAddress());
			saveResource.setResourceName(resource.getResourceName());
			saveResource.setResourceCode(resource.getResourceCode());
			saveResource.setParentId(resource.getParentId());
			saveResource.setDispOrder(resource.getDispOrder());
		}
		resourceDao.saveOrUpdate(saveResource);
		
		
		
	}


	/**
	 * 根据条件查找资源
	 * @author zhougz
	 * @param
	 * @date 2016-6-1
	 */
	public JQGridPage<Resource> findResourceList(JQGridPage<Resource> page, ResourceVo resourceVo) {
		return resourceDao.findResourceList(page, resourceVo);
	}
	
	
	@Override
	public Resource getOrgByOrgCode(String orgCode) {
		return resourceDao.getOrgByOrgCode(orgCode);
	}
	
	/**
	 * 
	 * @describe 根据角色查询
	 * @author liukai
	 * @param
	 * @date 2013-7-30
	 */
	public List<Resource> getParentResourceByRole(User user, String parentId){
		return resourceDao.getParentResourceByRole(user,parentId);
	}

	/**
	 * @describe 查询资源集合 (可扩展，自带条件)
	 * @author XieGaosong
	 * @return List<Resource>
	 * @date 2013-7-16
	 */
	public List<Resource> findResourceList(String orgId){
		return this.resourceDao.findResourceList(orgId);
	}
	
	@Override
	public List<Resource> getResourceByParentId(String parentId) {
		List<Resource> resources=new ArrayList<>();
		List<Resource> list = resourceDao.getResourceByParentId(parentId);
		if (list != null) {
			for (Resource resource : list) {
				String id = resource.getId();
				List<Resource> children = resourceDao.getResourceByParentId(id);
				resources.addAll(children);
			}
			resources.addAll(list);
		}
		return resources;
	}
	
	
}
