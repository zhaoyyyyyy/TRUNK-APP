/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.sysmgr.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.page.Page;
import com.asiainfo.biapp.si.coc.jauth.frame.service.BaseService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.OrgUser;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.OrgUserVo;

/**
 * @author ljs
 * @date 2013-6-26
 */
public interface OrgUserService extends BaseService<OrgUser,String>{
	
	/**
	 * 
	 * @describe 创建组织成员
	 * @author ljs
	 * @date 2013-7-31
	 * @param orgCode
	 * @param type 成员身份
	 * @return
	 */
	public Serializable createOrgUser(String orgCode, String type);
	
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
	public Serializable createOrgUser(String userId,String orgCode, String type);
	
	/**
	 * 
	 * @describe 根据情况创建组织成员
	 * @author ljs
	 * @date 2013-8-16
	 * @param orgCode
	 */
	public void createOrgUserBySituation(String orgCode);
	
	
	/**
	 * 根据条件查询圈子
	 * @param orgCircleVo
	 * @return
	 */
	public JQGridPage<OrgUser> findUserPageByParams(Page<OrgUser> page,OrgUserVo orgUserVo);
	
	/**
	 * @describe 查询组织成员集合
	 * @author XieGaosong
	 * @param List<OrgUser>
	 * @date 2013-6-28
	 */
	public List<OrgUser> findOrgUserList(OrgUserVo orgUserVo);
	
	/**
	 * 根据id改变组织成员状态
	 * @param id
	 * @return
	 */
    public Serializable updateStatus(String id);
    
    /**
     * 根据id设置组织成员状态为status
     * @param id
     * @param status
     * @return
     */
    public Serializable updateStatus(String id, String status);   
    
	/**
	 * 根据id改变组织成员角色
	 * @param id
	 * @return
	 */
    public Serializable updateType(String id);
    
    /**
     * 根据id设置组织成员角色为type
     * @param id
     * @param type
     * @return
     */
    public Serializable updateType(String id, String type);
    
    /**
     * @describe 将用户ID设置为null
     * @author XieGaosong
     * @date 2013-7-1
     */
    public Serializable updateKickedOut(String id);
    
    
    /**
	 * @describe 导出Excel表格
	 * @author XieGaosong
	 * @date 2013-7-2
	 */
	public void saveToExcel(OrgUserVo orgUserVo);
	
	
	/**
	 * @describe 根据ID修改名字
	 * @author XieGaosong
	 * @param
	 * @date 2013-7-3
	 */
	public void updateAdvance(OrgUser orgUser);
	
	/**
	 * @describe 查询正式成员  pageList
	 * @author XieGaosong
	 * @param
	 * @date 2013-7-5
	 */
	public JQGridPage<OrgUser> findOfficialMemberPageByParams(Page<OrgUser> page,OrgUserVo orgUserVo);
	
	/**
	 * 查找组织用户
	 * @author ljs
	 * @param userId
	 * @param orgCode
	 * @return
	 */
	public OrgUser getOrgUser(String userId, String orgCode);
	
	/***
	 * @describe查看是否有预录成员
	 * @author XieGaosong
	 * @param
	 * @date 2013-7-26
	 */
	public OrgUser getPrerecord(String userName,String orgCode);
	
	/***
	 * @describe 让组织成员的预录成员成为正式成员  
	 * @author XieGaosong
	 * @param
	 * @date 2013-7-26
	 */
	public void saveOrgUserMembers(String userId, String orgCode);
	
	/**
	 * 
	 * @describe 根据条件列出组织用户集合
	 * @author ljs
	 * @date 2013-8-12
	 * @param userId
	 * @param type
	 * @return
	 */
	public List<OrgUser> listOrgUser(String userId, String type);
	
	
	
	/**
	 * @describe 根据组织Code查询出集合
	 * @author XieGaosong
	 * @date 2013-8-19
	 * @param orgCode
	 * @return
	 */
	public List<OrgUser> listOrgUser(String orgCode);
	
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
	public List<OrgUser> listOrgUser(String orgCode, String[] userTypes, String[] notUserTypes);
	
	/**
	 * 
	 * @describe 根据组织Code查询出集合(有缓存)
	 * @author ljs
	 * @date 2013-9-24
	 * @param orgCode
	 * @return
	 */
	@Cacheable(value="orgUser_cache",key="#orgCode + 'listOrgUser'")
	public List<OrgUser> listOrgUserCache(String orgCode);
	
	/**
	 * @describe 根据组织Code删除组织成员
	 * @author XieGaosong
	 * @date 2013-8-23
	 * @param orgCode
	 * @return
	 */
	public void deleteOrgUser(String orgCode);
	
	/**
	 * 
	 * @describe 退出组织
	 * @author ljs
	 * @date 2013-9-5
	 * @param orgCode
	 * @param orgType
	 */
	public void executeExitOrg(String orgCode, String orgType);
	
	/**
	 * 
	 * @describe 根据orgCode和userId删除组织成员
	 * @author ljs
	 * @date 2013-9-5
	 * @param orgCode
	 * @param userId
	 * @param orgType
	 */
	public void deleteOrgUser(String orgCode, String userId, String orgType);
	
	/**
	 * 
	 * @describe 踢出
	 * @author ljs
	 * @date 2013-8-26
	 * @param id
	 */
	public void deleteShotOff(String id);
	
	/**
	 * 
	 * @author liukai
	 * @param
	 * @date 2013-10-25
	 */
	public List<OrgUser> findOrgUserByUserId(String id);
	/**
	 * 
	 * @describe 根据用户ID修改性别
	 * @author liukai
	 * @param
	 * @date 2013-10-30
	 */
	public void updateOrgUserSexByUserId(String userId,String sex);
	
	/**
	 * @describe 导出Excel表格  人员信息
	 * @author XieGaosong
	 * @date 2013-7-2
	 */
	public void getGritExcel(String orgCode, String[] userTypes, String[] notUserTypes);
	
	/**
	 * 
	 * @describe 列出与指定用户在同一组织中的指定类型的组织用户集合
	 * @author ljs
	 * @date 2013-12-10
	 * @param userId
	 * @param type
	 * @return
	 */
	public List<OrgUser> listOtherOrgUser(String userId, String type);
}
