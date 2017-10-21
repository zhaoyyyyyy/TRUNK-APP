/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.sysmgr.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.coc.jauth.frame.Constants;
import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDao;
import com.asiainfo.biapp.si.coc.jauth.frame.dao.UUIDHexGenerator;
import com.asiainfo.biapp.si.coc.jauth.frame.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.coc.jauth.frame.util.StringUtil;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.OrganizationDao;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Organization;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.User;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.GroupService;
//import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.OrgUserService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.OrganizationService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.utils.SessionInfoHolder;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.OrganizationVo;

/**
 * @describe 组织管理业务处理层
 * @author zhougz
 * @date 2013-6-19
 */
@Service
@Transactional
public class OrganizationServiceImpl extends BaseServiceImpl<Organization,String> implements OrganizationService{

	@Autowired
	private OrganizationDao organizationDao;
	@Autowired
	private GroupService groupService;
	@Autowired
	private SessionInfoHolder sessionInfoHolder;
	
	@Autowired
//	private OrgUserService orgUserService;
	
	@Override
	protected BaseDao<Organization,String> getBaseDao() {
		return organizationDao;
	}
	
	/**
	 * 
	 * @describe 列出某个用户有权限访问的组织
	 * @author zhougz
	 * @date 2016-5-27
	 * @param userId
	 * @return
	 */
	public List<Organization> queryOwenOrgByUserId(String userId) {
		return organizationDao.queryOwenOrgByUserId(userId);
	}
	/**
	 * 
	 * @describe 查询用户的所有父节点
	 * @author liukai
	 * @param
	 * @date 2013-12-25
	 */
	public List<Organization> selectUserAllParentCode(User user){
		return organizationDao.selectUserAllParentCode(user);
	}

    /**
	 * @describe 创建组织(业务入口)
	 * @author ljs
	 * @param organization
	 * @return
	 */
	private String createOrganization(Organization organization){
    	String orgCode = organization.getOrgCode();
    	if(StringUtil.isEmpty(orgCode)){
    		orgCode = UUIDHexGenerator.getInstance().generate();
    	}
    	organization.setOrgCode(orgCode);
		organization.setCreateTime(getSystemTime());
		
		User user = sessionInfoHolder.getLoginUser();
		
    	organization.setCreater(user.getUserName());
    	organization.setCreaterName(user.getRealName());
			organization.setFullName(organization.getSimpleName());
		organizationDao.createOrganization(organization);
		return orgCode;
	}
	
	/**
	 * @describe 修改组织(业务入口)
	 * @param organization
	 * @return
	 */
	private String updateOrganization(Organization organization){
    	organization.setFullName(organization.getParentOrg().getFullName()+" "+organization.getSimpleName());
    	organization.setTreePath(organization.getParentOrg().getTreePath()+Constants.SPLIT_CHAR+organization.getOrgCode());
    	organizationDao.updateOrganization(organization);
		return organization.getOrgCode();
	}
	
	/**
	 * @describe 创建组织
	 * @author zhougz
	 * @param orgName    组织名称
	 * @param orgType    组织类型
	 * @param parentCode 父节点编号
	 * @return orgCode 本组织编码
	 * @date 2013-6-19
	 */
	@Override
	public String createOrganization(String orgName, String orgType,String parentCode) {
		Organization organization = new Organization();
		organization.setSimpleName(orgName);
		organization.setOrgType(orgType);
		Organization parentOrg = this.getOrgByOrgCode(parentCode);
		organization.setParentOrg(parentOrg);
		organization.setParentId(parentOrg.getId());
		return this.createOrganization(organization);
	}
	
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
	@Override
	public String createOrganization(String simpleName,String orgType,String parentCode,String orgStatus,String interrogateType){
		Organization organization = new Organization();
		organization.setSimpleName(simpleName);
    	organization.setOrgType(orgType);
    	Organization parentOrg = this.getOrgByOrgCode(parentCode);
		organization.setParentId(parentOrg.getId());
    	organization.setParentOrg(parentOrg);
    	organization.setOrgStatus(orgStatus);
    	organization.setInterrogateType(interrogateType);
		return this.createOrganization(organization);
	}
	
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
	@Override
	public String updateOrganization(String orgCode,String simpleName,String parentCode,String orgStatus,String interrogateType){
		Organization organization = getOrgByOrgCode(orgCode);
		organization.setSimpleName(simpleName);
		Organization parentOrg = this.getOrgByOrgCode(parentCode);
		organization.setParentId(parentOrg.getId());
		organization.setOrgStatus(orgStatus);
		organization.setInterrogateType(interrogateType);
		return this.updateOrganization(organization);
	}
	
	/**
	 * @describe 通过编号拿到对象
	 * @author zhougz
	 * @param
	 * @date 2013-6-19
	 */
	@Override
	public Organization getOrgByOrgCode(String orgCode) {
		return organizationDao.getOrgByOrgCode(orgCode);
	}
	
	/**
	 * @describe 通过编号拿到对象
	 * @author zhougz
	 * @param
	 * @date 2013-6-19
	 */
	@Override
	public Organization getOrgByOrgId(String orgCode) {
		return organizationDao.getOrgByOrgId(orgCode);
	}
    
    /**
     *  根据orgCode改变组织状态为指定值
     * @param orgCode
     * @param orgStatus
     * @return
     */
	@Override
    public Serializable updateStatus(String orgCode, String orgStatus){
		Organization organization = this.getOrgByOrgCode(orgCode);
    	return organizationDao.updateStatus(orgCode, orgStatus);
    }

    /**
     * 根据orgCode改变组织类型为指定码值
     * @param orgCode
     * @param orgType
     * @return
     */
	@Override
    public Serializable updateType(String orgCode, String orgType){
    	return organizationDao.updateType(orgCode, orgType);
    }
    
    /**
     * 根据orgCode改变组织审核方式为指定值
     * @param orgCode
     * @param interrogateType
     * @return
     */
	@Override
    public Serializable updateInterrogateType(String orgCode, String interrogateType){
		return organizationDao.updateInterrogateType(orgCode, interrogateType);
    }
	 
	 /***
     * @describe 修改组织的父节点与名称
     * @author XieGaosong
     * @param orgCode 组织编码
     * @param parentOrgCode 修改后的父组织编码
     * @param simpleName  修改后简称
     * @date 2013-6-27
     */
    public Serializable updateOrganization(String orgCode,String parentOrgCode,String simpleName){
    	Organization organization= this.getOrgByOrgCode(orgCode);
    	Organization parentOrg = this.getOrgByOrgCode(parentOrgCode);
    	organization.setFullName((StringUtil.isEmpty(parentOrg.getFullName())?"":parentOrg.getFullName()+" ")+simpleName);
    	organization.setSimpleName(simpleName);
    	organization.setParentId(parentOrg.getId());
    	organization.setTreePath(parentOrg.getTreePath()+Constants.SPLIT_CHAR+organization.getId());
    	return organizationDao.updateOrganization(organization);
    }
    
    /**
     * @deprecated 新建Organization
     * @author ljs
     * @param organizationVo
     * @return
     */
    @Override
    public Serializable createOrganizationByVo(OrganizationVo organizationVo){
    	Organization organization = new Organization();
		organization.setSimpleName(organizationVo.getSimpleName());
		organization.setOrderNum(organizationVo.getOrderNum());
		organization.setOrgType(organizationVo.getOrgType());
		organization.setParentOrg(getOrgByOrgCode(organizationVo.getParentOrgCode()));
		organization.setParentId(organization.getParentOrg().getId());
		organization.setInterrogateType(organizationVo.getInterrogateType());
		organization.setOrgStatus(organizationVo.getOrgStatus());
		organization.setCreateTime(getSystemTime());
		
		return this.createOrganization(organization);
    }
    
    /**
     * @deprecated 修改Organization
     * @author ljs
     * @param organizationVo
     * @return
     */
    @Override
    public Serializable updateOrganizationByVo(OrganizationVo organizationVo){
    	Organization organization = get(organizationVo.getId());
		organization.setSimpleName(organizationVo.getSimpleName());
		organization.setOrderNum(organizationVo.getOrderNum());
		organization.setOrgType(organizationVo.getOrgType());
		organization.setParentId(getOrgByOrgCode(organizationVo.getParentOrgCode()).getId());
		organization.setInterrogateType(organizationVo.getInterrogateType());
		organization.setOrgStatus(organizationVo.getOrgStatus());
    	
		return this.updateOrganization(organization);
    }
    
    /**
     * @describe 检测orgCode是否存在
     * @author ljs
     * @date 2013-7-29
     * @param orgCode
     * @return
     */
    @Override
    public boolean checkOrgCode(String orgCode){
    	int i = organizationDao.getCount("from Organization where orgCode =?", orgCode);
    	if(i < 1){
    		return false;
    	}else {
    		return true;
    	}
    }
    
    /**
     * 
     * @describe 根据orgCode删除组织
     * @author ljs
     * @date 2013-7-30
     * @param orgCode
     * @return
     */
    @Override
    public boolean deleteOrgByOrgCode(String orgCode){
    	Organization organization = getOrgByOrgCode(orgCode);
//    	this.organizationDao.deleteObject(organization);
    	try {
    		if(organization.getChildren() == null || organization.getChildren().size() == 0){
    			this.organizationDao.deleteObject(organization);
    			return true;
    		}
		} catch (Exception e) {
			this.organizationDao.deleteObject(organization);
			return true;
		}
		return false;
    }
    
    /**
     * 
     * @describe 只删除组织(删除组织的入口)
     * @author ljs
     * @date 2013-7-30
     * @param orgCode
     * @return
     */
    @Override
    public boolean deleteOrganization(String orgCode){
    	groupService.deleteGroupOrgByOrgCode(orgCode);//删除数据范围组织关系
//    	orgUserService.deleteOrgUser(orgCode);//删除组织用户
    	return organizationDao.deleteOrganization(orgCode);
    }
    
    /**
     * @describe 新建各种类型的组织
     * @author ljs
     * @date 2013-7-31
     * @param organizationVo
     * @return
     */
    @Override
    public Serializable createAllTypeOrgByVo(OrganizationVo organizationVo){
    	User user = sessionInfoHolder.getLoginUser();
    	Organization organization = new Organization();
    	organization.setOrgCode(organizationVo.getOrgCode());
		organization.setSimpleName(organizationVo.getSimpleName());
		organization.setOrderNum(organizationVo.getOrderNum());
		organization.setOrgType(organizationVo.getOrgType());
		organization.setParentId(organizationVo.getParentId());
		organization.setInterrogateType(organizationVo.getInterrogateType());
		organization.setOrgStatus(organizationVo.getOrgStatus());
		organization.setCreateTime(getSystemTime());
		organization.setCreater(user.getUserName());
    	
		return this.createOrganization(organization);
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
	public List<Organization> listOrganization(String orgType){
		return organizationDao.listOrganization(orgType);
	}
	
	/**
	 * 
	 * @describe 根据组织类型获取orgCode的子组织列表
	 * @author ljs
	 * @date 2013-9-23
	 * @param orgType
	 * @param orgCode
	 * @return
	 */
	@Override
	public List<Organization> listOrganizationCache(String orgType ,String orgCode){
		return organizationDao.listOrganization(orgType, getOrgByOrgCode(orgCode).getId());
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
	public List<Organization> listOrganizationByUserId(String userId){
		return organizationDao.listOrganizationByUserId(userId);
	}
	
	/**
	 * @describe 列出某个用户加入的组织
	 * @author XieGaosong
	 * @date 2013-10-10
	 * @param userId
	 * @return
	 */
	public List<Organization> findListOrganization(String orgCode){
		return organizationDao.findListOrganization(orgCode);
	}
}
