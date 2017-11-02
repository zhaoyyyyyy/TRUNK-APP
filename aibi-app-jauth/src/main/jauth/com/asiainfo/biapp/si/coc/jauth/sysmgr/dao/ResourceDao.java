/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.sysmgr.dao;

import java.util.List;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDao;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Resource;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.User;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.ResourceVo;

/**
 * @author liukai
 * @date 2013-6-25
 */
public interface ResourceDao extends BaseDao<Resource,String>{
	

	/**
	 * 
	 * Description:  根据parentId查询角色
	 *
	 * @param parentId
	 * @return
	 *
	 * @author  tianxy3
	 * @date 2017年9月18日
	 */
	public List<Resource> getResourceByParentId(String parentId);
	
	/**
	 * 查询资源
	 * 
	 * @author zhougz
	 * @param
	 * @date 2016-6-01
	 */
	public JQGridPage<Resource> findResourceList(JQGridPage<Resource> page, ResourceVo roleVo) ;

	/**
	 * 保存资源
	 * 
	 * @author zhougz
	 * @param
	 * @date 2016-6-01
	 */
	public void saveOrUpdate(Resource resource);
	
	
	/**
	 * 
	 * @describe 根据角色查询
	 * @author liukai
	 * @param
	 * @date 2013-7-30
	 */
	public List<Resource> getParentResourceByRole(User user,String parentId);
	/**
	 * @describe 通过编号拿到对象
	 * @author zhougz
	 * @param
	 * @date 2013-6-19
	 */
	public Resource getOrgByOrgCode(String orgCode);
	
	/**
	 * @describe 查询资源集合 (可扩展，自带条件)
	 * @author XieGaosong
	 * @return List<Resource>
	 * @date 2013-7-16
	 */
	public List<Resource> findResourceList(String orgId);
}
