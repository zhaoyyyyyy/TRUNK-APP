/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.sysmgr.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.asiainfo.biapp.si.coc.jauth.frame.service.BaseService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Organization;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.User;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.OrganizationVo;

/**
 * @describe 组织实体服务层
 * @author zhougz
 * @date 2013-6-19
 */
public interface OrganizationService extends BaseService<Organization,String>{
	
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
	 * @describe 创建组织
	 * @author zhougz
	 * @param orgName    组织名称
	 * @param orgType    组织类型
	 * @param parentCode 父节点编号
	 * @return orgCode 本组织编码
	 * @date 2013-6-19
	 */
	public String createOrganization(String orgName,String orgType,String parentCode);
	/**
	 * 
	 * @describe 查询用户的所有父节点
	 * @author liukai
	 * @param
	 * @date 2013-12-25
	 */
	public List<Organization> selectUserAllParentCode(User user);
	
	/**
	 * @describe 创建组织
	 * @author ljs
	 * @param simpleName		组织名称
	 * @param orgType			组织类型
	 * @param parentCode		父节点编号
	 * @param orgStatus			组织状态
	 * @param interrogateType	审核方式
	 * @return
	 */
	public String createOrganization(String simpleName,String orgType,String parentCode,String orgStatus,String interrogateType);
	
    /**
     * @deprecated 新建组织
     * @author ljs
     * @param organizationVo
     * @return
     */
    public Serializable createOrganizationByVo(OrganizationVo organizationVo);
	
    /**
     * @describe 新建各种类型的组织
     * @author ljs
     * @date 2013-7-31
     * @param organizationVo
     * @return
     */
    public Serializable createAllTypeOrgByVo(OrganizationVo organizationVo);
	
	/**
	 * @describe 修改组织
	 * @author ljs
	 * @param orgCode			组织编号
	 * @param simpleName		组织名称
	 * @param parentCode		父节点编号
	 * @param orgStatus			组织状态
	 * @param interrogateType	审核方式
	 * @return
	 */
	public String updateOrganization(String orgCode,String simpleName,String parentCode,String orgStatus,String interrogateType);
	
	/**
	 * @describe 通过编号拿到对象
	 * @author zhougz
	 * @param
	 * @date 2013-6-19
	 */
	public Organization getOrgByOrgCode(String orgCode);
	
	/**
	 * @describe 通过编号拿到对象
	 * @author zhougz
	 * @param
	 * @date 2013-6-19
	 */
	public Organization getOrgByOrgId(String orgCode);
    
    /**
     *  根据orgCode改变组织状态为指定值
     * @param orgCode
     * @param orgStatus
     * @return
     */
    public Serializable updateStatus(String orgCode, String orgStatus);
    
    /**
     * 根据orgCode改变组织类型为指定码值
     * @param orgCode
     * @param orgType
     * @return
     */
    public Serializable updateType(String orgCode, String orgType);
    
    /**
     * 根据orgCode改变组织审核方式为指定值
     * @param orgCode
     * @param interrogateType
     * @return
     */
    public Serializable updateInterrogateType(String orgCode, String interrogateType);
    
    /***
     * @describe 通过两个CODE,加simpleName(缩写名称)进行修改
     * @author XieGaosong
     * @param
     * @date 2013-6-27
     */
    public Serializable updateOrganization(String orgCode,String parentOrgCode,String simpleName);
    
    /**
     * @deprecated 修改Organization
     * @author ljs
     * @param organizationVo
     * @return
     */
    public Serializable updateOrganizationByVo(OrganizationVo organizationVo);
    
    /**
     * @describe 检测orgCode是否存在
     * @author ljs
     * @date 2013-7-29
     * @param orgCode
     * @return
     */
    public boolean checkOrgCode(String orgCode);
    
    /**
     * 
     * @describe 根据orgCode删除组织
     * @author ljs
     * @date 2013-7-30
     * @param orgCode
     * @return
     */
    public boolean deleteOrgByOrgCode(String orgCode);
    
    /**
     * 
     * @describe 只删除组织
     * @author ljs
     * @date 2013-7-30
     * @param orgCode
     * @return
     */
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
	 * @describe 根据组织类型获取orgCode的子组织列表
	 * @author ljs
	 * @date 2013-9-23
	 * @param orgType
	 * @param orgCode
	 * @return
	 */
	@Cacheable(value="org_cache",key="#orgType +#orgCode + 'listOrganization'")
	public List<Organization> listOrganizationCache(String orgType ,String orgCode);
	
	/**
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
