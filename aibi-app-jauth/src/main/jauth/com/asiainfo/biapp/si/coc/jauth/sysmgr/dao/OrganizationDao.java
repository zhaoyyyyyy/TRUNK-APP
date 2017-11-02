/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.sysmgr.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDao;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Organization;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.User;

/**
 * @author zhougz
 * @date 2013-6-19
 */
public interface OrganizationDao extends BaseDao<Organization,String>{
	
	/**
	 * 
	 * @describe 列出某个用户有权限访问的组织
	 * @author zhougz
	 * @date 2016-5-27
	 * @param userId
	 * @return
	 */
	public List<Organization> queryOwenOrgByUserId(String userId) ;
	
	/**
     * 删除某个实体
     * @param entity
     */
	@CacheEvict(value="org_cache",allEntries=true)
    public void deleteObject(Organization organization);
	/**
	 * 
	 * @describe 查询用户的所有父节点
	 * @author liukai
	 * @param
	 * @date 2013-12-25
	 */
	public List<Organization> selectUserAllParentCode(User user);
    
	/**
	 * @describe 通过编号拿到对象
	 * @author zhougz
	 * @param
	 * @date 2013-6-19
	 * @Cacheable(value="org_cache",key="#orgCode + 'getOrgByOrgCode'")
	 */
	public Organization getOrgByOrgCode(String orgCode);
	
	/**
	 * @describe 通过ID拿到对象
	 * @author zhougz
	 * @param
	 * @date 2013-6-19
	 * @Cacheable(value="org_cache",key="#orgCode + 'getOrgByOrgCode'")
	 */
	public Organization getOrgByOrgId(String orgCode);
	
	/**
	 * 根据orgCode改变组织状态
	 * @param orgCode
	 * @return
	 */
	@CacheEvict(value="org_cache",key="#orgCode + 'getOrgByOrgCode'")
    public Serializable updateStatus(String orgCode);
    
    /**
     *  根据orgCode改变组织状态为指定码值
     * @param orgCode
     * @param orgStatus
     * @return
     */
	@CacheEvict(value="org_cache",key="#orgCode + 'getOrgByOrgCode'")
    public Serializable updateStatus(String orgCode, String orgStatus);
    
    /**
     * 根据orgCode改变组织类型为指定码值
     * @param orgCode
     * @param orgType
     * @return
     */
	@CacheEvict(value="org_cache",key="#orgCode + 'getOrgByOrgCode'")
    public Serializable updateType(String orgCode, String orgType);
    
    /**
     * 根据orgCode改变组织审核方式为指定值
     * @param orgCode
     * @param interrogateType
     * @return
     */
	@CacheEvict(value="org_cache",key="#orgCode + 'getOrgByOrgCode'")
    public Serializable updateInterrogateType(String orgCode, String interrogateType);
    
	/**
	 * 修改组织
	 * @author ljs
	 * @param organization
	 * @return
	 */
	@CacheEvict(value="org_cache",allEntries=true)
	public Serializable updateOrganization(Organization organization);
	
	/**
	 * 
	 * @describe 创建组织
	 * @author ljs
	 * @date 2013-7-29
	 * @param organization
	 * @return
	 */
	@CacheEvict(value="org_cache",allEntries=true)
	public Serializable createOrganization(Organization organization);

    /**
     * 
     * @describe 只删除组织
     * @author ljs
     * @date 2013-7-30
     * @param orgCode
     * @return
     */
	@CacheEvict(value="org_cache",allEntries=true)
    public boolean deleteOrganization(String orgCode);
	
	/**
	 * 
	 * @describe 根据组织类型获取组织列表
	 * @author ljs
	 * @date 2013-9-23
	 * @param orgType
	 * @return
	 */
	public List<Organization> listOrganization(String orgType);
	
	/**
	 * 
	 * @describe 根据组织类型获取parentId的子组织列表
	 * @author ljs
	 * @date 2013-9-23
	 * @param orgType
	 * @param parentId
	 * @return
	 */
	public List<Organization> listOrganization(String orgType ,String parentId);
	
	/**
	 * 
	 * @describe 列出某个用户加入的组织
	 * @author ljs
	 * @date 2013-10-10
	 * @param userId
	 * @return
	 */
	public List<Organization> listOrganizationByUserId(String userId);
	
	
	/**
	 * @describe 列出某个用户加入的组织
	 * @author XieGaosong
	 * @date 2013-10-10
	 * @param userId
	 * @return
	 */
	public List<Organization> findListOrganization(String orgCode);
}
