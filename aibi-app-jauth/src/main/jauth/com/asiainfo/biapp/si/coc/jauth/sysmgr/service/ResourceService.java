/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.sysmgr.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.page.Page;
import com.asiainfo.biapp.si.coc.jauth.frame.service.BaseService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Resource;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.User;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.ResourceVo;

/**
 * @author liukai
 * @date 2013-6-25
 */
public interface ResourceService extends BaseService<Resource,String>{
	
	
	/**
	 * 
	 * Description: 根据parentId查询角色
	 *
	 * @param parentId
	 * @return
	 *
	 * @author  tianxy3
	 * @date 2017年9月18日
	 */
	@Cacheable(value="resource_cache",key="#parentId + 'getParentResourceByRole'")
	public List<Resource> getResourceByParentId(String parentId);
	
	/**
	 * 保存角色
	 */
	public void saveOrUpdate(Resource resource) ;


	/**
	 * 根据条件查找资源
	 * @author zhougz
	 * @param
	 * @date 2016-6-1
	 */
	public JQGridPage<Resource> findResourceList(JQGridPage<Resource> page, ResourceVo resourceVo) ;
	
	
	/**
	 * 
	 * @describe 根据角色查询
	 * @author liukai
	 * @param
	 * @date 2013-7-30
	 */
	@Cacheable(value="resource_cache",key="#user.userInformationId + #parentId + 'getParentResourceByRole'")
	public List<Resource> getParentResourceByRole(User user, String parentId);
	
	/**
	 * @describe 通过编号拿到对象
	 * @author zhougz
	 * @param
	 * @date 2013-6-19
	 */
	@Cacheable(value="resource_cache",key="#orgCode + 'getOrgByOrgCode'")
	public Resource getOrgByOrgCode(String orgCode);
	
	
	/**
	 * @describe 查询资源集合 (可扩展，自带条件)
	 * @author XieGaosong
	 * @return List<Resource>
	 * @date 2013-7-16
	 */
	@Cacheable(value="resource_cache",key="'findResourceList'")
	public List<Resource> findResourceList(String orgId);

}
