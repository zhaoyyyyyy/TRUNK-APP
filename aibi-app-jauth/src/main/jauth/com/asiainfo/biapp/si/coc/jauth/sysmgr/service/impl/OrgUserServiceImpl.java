/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.sysmgr.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.coc.jauth.frame.Constants;
import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDao;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.page.Page;
import com.asiainfo.biapp.si.coc.jauth.frame.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.coc.jauth.frame.ssh.extend.SpringContextHolder;
import com.asiainfo.biapp.si.coc.jauth.frame.util.BeanUtils;
import com.asiainfo.biapp.si.coc.jauth.frame.util.StringUtil;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.OrgUserDao;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.OrgUser;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Organization;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.User;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.OrgUserService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.OrganizationService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.UserService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.utils.SessionInfoHolder;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.OrgUserVo;
/**
 * @author ljs
 * @date 2013-6-26
 */
@Service
@Transactional
public class OrgUserServiceImpl extends BaseServiceImpl<OrgUser,String> implements OrgUserService{

	@Autowired
	private OrgUserDao orgUserDao;
	@Autowired
	private UserService userService;
	@Autowired
	private SessionInfoHolder sessionInfoHolder;
	@Autowired
	private OrganizationService organizationService;
	
	@Override
	protected BaseDao<OrgUser,String> getBaseDao() {
		return orgUserDao;
	}
	
	/**
	 * 
	 * @describe 创建组织成员(业务入口)
	 * @author ljs
	 * @date 2013-7-31
	 * @param orgUser
	 * @return
	 */
	private Serializable createOrgUser(OrgUser orgUser){
		if(StringUtil.isEmpty(orgUser.getUserId())){
			orgUser.setUserId(sessionInfoHolder.getLoginId());
		}
		if(StringUtil.isEmpty(orgUser.getSex())){
			orgUser.setSex(sessionInfoHolder.getLoginUser().getSex());
		}
		if(StringUtil.isEmpty(orgUser.getUserName())){
			orgUser.setUserName(sessionInfoHolder.getLoginUser().getRealName());
		}
		//如果已经存在该组织成员，就不再创建了
		OrgUser orgUserOld = getOrgUser(orgUser.getUserId(), orgUser.getOrgCode());
		if(orgUserOld != null){
			return orgUserOld.getId();
		}
		orgUser.setJoinTime(getSystemDate());
		Organization organization = organizationService.getOrgByOrgCode(orgUser.getOrgCode());
//		orgUser.setSimpleName(organization.getSimpleName());
		if(!StringUtil.isEmpty(orgUser.getId())){
			orgUserDao.update(orgUser);
			return orgUser.getId();
		}else{
			return orgUserDao.save(orgUser);
		}
	}
	
	/**
	 * 
	 * @describe 创建组织成员
	 * @author ljs
	 * @date 2013-7-31
	 * @param orgCode
	 * @param type 成员身份
	 * @return
	 */
	@Override
	public Serializable createOrgUser(String orgCode, String type){
		OrgUser orgUser = new OrgUser();
		orgUser.setType(type);
		orgUser.setStatus(OrgUser.OPEN);
		orgUser.setOrgCode(orgCode);
		
		return this.createOrgUser(orgUser);
	}
	
	/**
	 * 
	 * @describe 创建组织成员
	 * @author ljs
	 * @date 2013-8-26
	 * @param userId
	 * @param orgCode
	 * @param type
	 * @return
	 */
	public Serializable createOrgUser(String userId,String orgCode, String type){
		OrgUser orgUser = null;
		Organization organization = organizationService.getOrgByOrgCode(orgCode);
		if(orgUser == null){
			orgUser = new OrgUser();
		}
		if(StringUtil.isNotEmpty(userId)){
			User user = userService.get(userId);
			orgUser.setUserId(userId);
			orgUser.setUserName(user.getRealName());
			orgUser.setSex(user.getSex());
		}
		orgUser.setType(type);
		orgUser.setStatus(OrgUser.OPEN);
		orgUser.setOrgCode(orgCode);
		
		return this.createOrgUser(orgUser);
	}
	
	/**
	 * 
	 * @describe 根据情况创建组织成员
	 * @author ljs
	 * @date 2013-8-16
	 * @param orgCode
	 */
	@Override
	public void createOrgUserBySituation(String orgCode){
		OrganizationService organizationService = (OrganizationService)SpringContextHolder.getBean("organizationService");
		if(StringUtil.isNotEmpty(orgCode)){
			Organization organization = organizationService.getOrgByOrgCode(orgCode);
			if(this.getOrgUser(sessionInfoHolder.getLoginId(), orgCode) == null){
			}
		}
	}
	
	
	/**
	 * 根据条件查询圈子
	 * @param orgCircleVo
	 * @return
	 */
	@Override
	public JQGridPage<OrgUser> findUserPageByParams(Page<OrgUser> page,OrgUserVo orgUserVo){
		return orgUserDao.findUserPageByParams(page, orgUserVo);
	}
	
	/**
	 * @describe 查询组织成员集合
	 * @author XieGaosong
	 * @param List<OrgUser>
	 * @date 2013-6-28
	 */
	public List<OrgUser> findOrgUserList(OrgUserVo orgUserVo){
		return this.orgUserDao.findOrgUserList(orgUserVo);
	}
	
	
	/**
	 * 根据id改变组织成员状态
	 * @param id
	 * @return
	 */
	@Override
    public Serializable updateStatus(String id){
    	return orgUserDao.updateStatus(id);
    }
    
    /**
     * 根据id设置组织成员状态为status
     * @param id
     * @param status
     * @return
     */
	@Override
    public Serializable updateStatus(String id, String status){
    	return orgUserDao.updateStatus(id, status);
    }
    
	/**
	 * 根据id改变组织成员角色
	 * @param id
	 * @return
	 */
	@Override
    public Serializable updateType(String id){
    	return orgUserDao.updateType(id);
    }
    
    /**
     * 根据id设置组织成员角色为type
     * @param id
     * @param type
     * @return
     */
	@Override
    public Serializable updateType(String id, String type){
    	return orgUserDao.updateType(id, type);
    }
	
	
    /**
     * @describe 将用户ID设置为null
     * @author XieGaosong
     * @date 2013-7-1
     */
    public Serializable updateKickedOut(String id){
    	return orgUserDao.updateKickedOut(id);
    }
    
    
    /**
	 * @describe 导出Excel表格
	 * @author XieGaosong
	 * @date 2013-7-2
	 */
	@SuppressWarnings("unchecked")
	public void saveToExcel(OrgUserVo orgUserVo){
		List list = orgUserDao.findOrgUserList(orgUserVo);
		String[] head = {"用户名","真实姓名","状态","加入时间","班内身份"};
//		MSExcelManager.writeExcel("班级人员信息", head, list);
	}
	
	
	
	/**
	 * @describe 根据ID修改名字
	 * @author XieGaosong
	 * @param
	 * @date 2013-7-3
	 */
	public void updateAdvance(OrgUser orgUser){
		orgUserDao.updateAdvance(orgUser);
	}
	
	/**
	 * @describe 查询正式成员  pageList
	 * @author XieGaosong
	 * @param
	 * @date 2013-7-5
	 */
	public JQGridPage<OrgUser> findOfficialMemberPageByParams(Page<OrgUser> page,OrgUserVo orgUserVo){
		return orgUserDao.findOfficialMemberPageByParams(page, orgUserVo);
	}

	
	/**
	 * 查找组织用户
	 * @author ljs
	 * @param userId
	 * @param orgCode
	 * @return
	 */
	@Override
	public OrgUser getOrgUser(String userId, String orgCode){
		return orgUserDao.getOrgUser(userId, orgCode);
	}
	/***
	 * @describe查看是否有预录成员
	 * @author XieGaosong
	 * @param
	 * @date 2013-7-26
	 */
	public OrgUser getPrerecord(String userName,String orgCode){
		return this.orgUserDao.getPrerecord(userName, orgCode);
	}
	
	/***
	 * @describe 让组织成员的预录成员成为正式成员  
	 * @author XieGaosong
	 * @param
	 * @date 2013-7-26
	 */
	public void saveOrgUserMembers(String userId, String orgCode){
		User user = userService.get(userId);
		OrgUser orgUser = getPrerecord(user.getRealName(),orgCode);
		if(orgUser==null){
			orgUser=new OrgUser();
		}
		orgUser.setUserId(userId);
		orgUser.setSex(user.getSex());
		orgUser.setUserName(user.getRealName());
		orgUser.setType(OrgUser.COMMON);
		orgUser.setStatus(OrgUser.OPEN);
		orgUser.setJoinTime(getSystemDate());
		orgUser.setOrgCode(orgCode);
		this.orgUserDao.saveOrUpdate(orgUser);
	}
	
	/**
	 * 
	 * @describe 根据条件列出组织用户集合
	 * @author ljs
	 * @date 2013-8-12
	 * @param userId
	 * @param type
	 * @return
	 */
	@Override
	public List<OrgUser> listOrgUser(String userId, String type){
		return orgUserDao.listOrgUser(userId, type);
	}
	
	
	
	/**
	 * @describe 根据组织Code查询出集合
	 * @author XieGaosong
	 * @date 2013-8-19
	 * @param orgCode
	 * @return
	 */
	public List<OrgUser> listOrgUser(String orgCode){
		return this.orgUserDao.listOrgUser(orgCode);
	}
	
	/**
	 * 
	 * @describe 查询出组织用户集合集合
	 * @author ljs
	 * @date 2013-10-18
	 * @param orgCode
	 * @param userTypes 想显示的用户类型
	 * @param notUserTypes 不想显示的用户类型
	 * @return
	 */
	@Override
	public List<OrgUser> listOrgUser(String orgCode, String[] userTypes, String[] notUserTypes){
		return orgUserDao.listOrgUser(orgCode, userTypes, notUserTypes);
	}
	
	/**
	 * 
	 * @describe 根据组织Code查询出集合
	 * @author ljs
	 * @date 2013-9-24
	 * @param orgCode
	 * @return
	 */
	@Override
	public List<OrgUser> listOrgUserCache(String orgCode){
		return this.orgUserDao.listOrgUser(orgCode);
	}
	
	/**
	 * @describe 根据组织Code删除组织成员
	 * @author XieGaosong
	 * @date 2013-8-23
	 * @param orgCode
	 * @return
	 */
	public void deleteOrgUser(String orgCode){
		this.orgUserDao.deleteOrgUser(orgCode);
		List<OrgUser> orgUsers = listOrgUser(orgCode);
		for (OrgUser orgUser : orgUsers) {
			deleteOrgUserBase(orgUser.getOrganization().getOrgType(), orgUser.getOrgCode(),orgUser.getUserId());//删除组织成员的公共方法
		}
	}
	
	/**
	 * 
	 * @describe 退出组织
	 * @author ljs
	 * @date 2013-9-5
	 * @param orgCode
	 * @param orgType
	 */
	public void executeExitOrg(String orgCode, String orgType){
		this.deleteOrgUser(orgCode, sessionInfoHolder.getLoginId(), orgType);
//		List<OrgUser> orgUsers = listOrgUser(orgCode);
//		for (OrgUser orgUser : orgUsers) {
//			deleteOrgUserBase(orgUser.getOrganization().getOrgType(), orgUser.getOrgCode(),orgUser.getUserId());//删除组织成员的公共方法
//		}
	}
	
	/**
	 * 
	 * @describe 根据orgCode和userId删除组织成员
	 * @author ljs
	 * @date 2013-9-5
	 * @param orgCode
	 * @param userId
	 * @param orgType
	 */
	public void deleteOrgUser(String orgCode, String userId, String orgType){
		orgUserDao.deleteOrgUser(orgCode, userId);
		deleteOrgUserBase(orgType, orgCode, userId);//删除组织成员的公共方法
	}
	
	/**
	 * 
	 * @describe 踢出
	 * @author ljs
	 * @date 2013-8-26
	 * @param id
	 */
	public void deleteShotOff(String id){
		OrgUser orgUser = get(id);
		orgUserDao.delete(id);
		deleteOrgUserBase(orgUser.getOrganization().getOrgType(), orgUser.getOrgCode(),orgUser.getUserId());//删除组织成员的公共方法
	}
	
	/**
	 * 
	 * @describe 删除组织成员的公共方法
	 * @author ljs
	 * @date 2013-10-8
	 * @param orgType
	 * @param orgCode
	 * @param userId
	 */
	private void deleteOrgUserBase(String orgType, String orgCode, String userId){
//		if(Constants.ORG_TYPE.CIRCLE.equals(orgType)){
//			orgCircleService.updateMinusNum(orgCode);//圈子用户数减一
//			UserOptLog.log(ConstantsIntegra.ZZ.TCQZ);//用户操作记录，退出圈子
//			//从圈子分组中移去
//			UserOrgGroupService userOrgGroupService = (UserOrgGroupService)SpringContextHolder.getBean("userOrgGroupService");
//			userOrgGroupService.deleteUserOrgGroupByOrgCode(orgCode);
//		}else if(Constants.ORG_TYPE.DEPT.equals(orgType)){
//			departmentService.updateMinusNum(orgCode);//部门用户数减一
//			UserOptLog.log(ConstantsIntegra.ZZ.TCBM);//用户操作记录，退出部门
//		}else if(Constants.ORG_TYPE.CLASS.equals(orgType)){
//			if(StringUtil.isEmpty(userId)){
//				orgClassService.dietPreNum(orgCode);//班级预录成员数减一
//			}else{
//				orgClassService.dietOfflcialNum(orgCode);//班级正式成员数减一
//			}
//			UserOptLog.log(ConstantsIntegra.ZZ.TCBJ);//用户操作记录，退出班级
//		}
	}
	
	/**
	 * 
	 * @author liukai
	 * @param
	 * @date 2013-10-25
	 */
	public List<OrgUser> findOrgUserByUserId(String id){
		return orgUserDao.findOrgUserByUserId(id);
	}
	/**
	 * 
	 * @describe 根据用户ID修改性别
	 * @author liukai
	 * @param
	 * @date 2013-10-30
	 */
	public void updateOrgUserSexByUserId(String userId,String sex){
		orgUserDao.updateOrgUserSexByUserId(userId,sex);
	}
	
	/**
	 * @describe 导出Excel表格 人员名片
	 * @author XieGaosong
	 * @date 2013-7-2
	 */
	@SuppressWarnings("unchecked")
	public void getGritExcel(String orgCode, String[] userTypes, String[] notUserTypes){
		List<OrgUser> list=this.listOrgUser(orgCode, userTypes, notUserTypes);
		List listVo=new ArrayList();
		for(OrgUser orgUser : list){
			OrgUserVo orgUserVo = new OrgUserVo();
			BeanUtils.copyProperties(orgUser, orgUserVo);
			orgUserVo.setUser(orgUser.getUser());
			listVo.add(orgUserVo);
		}
		String[] head = {"姓名","性别","电话","邮箱","QQ号码","工作信息","通信地址",};
//		MSExcelManager.writeExcel("人员名片",head,listVo);
	}
	
	
	/**
	 * 
	 * @describe 列出与指定用户在同一组织中的指定类型的组织用户集合
	 * @author ljs
	 * @date 2013-12-10
	 * @param userId
	 * @param type
	 * @return
	 */
	@Override
	public List<OrgUser> listOtherOrgUser(String userId, String type){
		return orgUserDao.listOtherOrgUser(userId, type);
	}

}
