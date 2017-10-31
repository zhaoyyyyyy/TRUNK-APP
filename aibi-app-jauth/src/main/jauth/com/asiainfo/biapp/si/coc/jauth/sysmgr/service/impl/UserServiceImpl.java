/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.sysmgr.service.impl;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.coc.jauth.frame.Constants;
import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDao;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.page.Page;
import com.asiainfo.biapp.si.coc.jauth.frame.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.coc.jauth.frame.util.Bcrypt;
import com.asiainfo.biapp.si.coc.jauth.frame.util.MD5;
import com.asiainfo.biapp.si.coc.jauth.frame.util.StringUtil;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.UserDao;
//import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.OrgUser;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Role;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.User;
//import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.OrgUserService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.UserService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.UserVo;

/**
 * @describe
 * @author liukai
 * @date 2013-6-28
 */
@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User,String> implements
		UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	
	@Override
	protected BaseDao<User,String> getBaseDao() {
		return userDao;
	}
	

	/**
	 * 根据查询条件查询出角色
	 * 
	 * @describe 
	 * @author liukai
	 * @param
	 * @date 2013-6-21
	 */
	public JQGridPage<User> findUserList(JQGridPage<User> page, UserVo userVo) {
		return userDao.findUserList(page, userVo);
	}

	/**
	 * 
	 * @describe 根据条件查询出用户列表
	 * @author ljs
	 * @date 2013-9-30
	 * @param userVo
	 * @return
	 */
	@Override
	public List<User> listUser(UserVo userVo) {
		return userDao.listUser(userVo);
	}
	
	/**
	 * 
	 * @describe 用户列表
	 * @author ljs
	 * @date 2013-11-7
	 * @return
	 */
	public List<User> listUser(){
		return userDao.findListByHql("from User u where u.status = ?", 1);
	}


	/**
	 * 
	 * @describe 根据ID获取用户
	 * @author liukai
	 * @param
	 * @date 2013-7-1
	 */
	public User get(String id) {
		User user = userDao.get(id);
		return user;
	}

	/**
	 * 保存或更新用户
	 */
	public void saveOrUpdate(User user) {
		user.setCreateTime(getSystemDate());
		userDao.saveOrUpdate(user);
	}



	/**
	 * 到处数据
	 * 
	 * @author liukai
	 * @param
	 * @date 2013-7-2
	 */
	public void exportData(UserVo userVo) {
		List list = userDao.findUserListByHql(userVo);
		String[] head = { "用户名称", "姓名", "性别", "所在组织", "角色", "数据范围", "状态", "等级","积分",
				"创建时间","出生年月","出生地","身份证号","手机","邮箱","QQ","通讯地址","邮编","兴趣爱好" };
//		MSExcelManager.writeExcel("用户信息", head, list);
	}



	/**
	 * 
	 * @describe 根据ID删除用户
	 * @author liukai
	 * @param
	 * @date 2013-7-31
	 */
	public void delete(String id) {
		userDao.delete(id);
		// 查询该用户所有组织
//		List<OrgUser> orgUserList = orgUserService.findOrgUserByUserId(id);
//		for (OrgUser orgUser : orgUserList) {
//			orgUserService.delete(orgUser.getId());
//		}
	}

	/**
	 * @describe 根据用户名，查询用户对象
	 * @author XieGaosong
	 * @param
	 * @date 2013-7-10
	 */
	public User getUserByName(String userName) {
		User user = userDao.getUserByName(userName);
		if (user != null) {
			user.getRoleSet();
			user.getGroupSet();
		}
		return user;
	}

	/**
	 * @describe 根据当前用户ID，查询出不是自己好友的用户
	 * @author XieGaosong
	 * @param
	 * @date 2013-7-31
	 */
	public List<User> findNotUserFriendList(JQGridPage<User> page, UserVo userVo) {
		return this.userDao.findNotUserFriendList(page, userVo);
	}

	/**
	 * 
	 * @describe 根据当前用户ID，查询出不是自己好友的用户
	 * @author ljs
	 * @date 2013-8-28
	 * @param page
	 * @param userId
	 * @return
	 */
	public List<User> findNotUserFriendList(String userId) {
		return this.userDao.findNotUserFriendList(userId);
	}

	/**
	 * 
	 * @describe 根据当前用户ID，查询出不是自己好友的用户
	 * @author ljs
	 * @date 2013-10-14
	 * @param userId
	 * @param orgCode
	 * @return
	 */
	public List<User> findNotUserFriendList(String userId, String orgCode) {
		return findNotUserFriendList(userId, orgCode);
	}

	/**
	 * 
	 * @describe 根据用户Id，查询出用户的好友
	 * @author ljs
	 * @date 2013-8-12
	 * @param userId
	 * @return
	 */
	@Override
	public List<User> findUserFriends(String userId) {
		return userDao.findUserFriends(userId);
	}

	/**
	 * 
	 * @describe 根据用户Id和站点orgCode，查询出用户的好友
	 * @author ljs
	 * @date 2013-10-14
	 * @param userId
	 * @param orgCode
	 * @return
	 */
	@Override
	public List<User> findUserFriends(String userId, String orgCode) {
		return userDao.findUserFriends(userId, orgCode);
	}


	/**
	 * 
	 * @describe 创建用户
	 * @author ljs
	 * @date 2013-10-15
	 * @param user
	 * @return
	 */
	@Override
	public Serializable createUser(User user) {

		// 与角色表配置关系，建立用户
		if (user.getIsAdmin() == 1) {
			Role role = new Role();
			role.setId("8a8180a63f84be63013f84bf6bc90000");
			Set<Role> roleSet = new HashSet<Role>();
			roleSet.add(role);
			user.setRoleSet(roleSet);
		} else {
			Role role = new Role();
			role.setId("-1");
			Set<Role> roleSet = new HashSet<Role>();
			roleSet.add(role);
			user.setRoleSet(roleSet);
		}

		// 对密码进行加密
		user.setPassword(Bcrypt.BcryptCode(user.getPassword()));
		//user.setPassword(MD5.MD5CODE(user.getPassword()));
		user.setStatus(Constants.USER_ENABLE_STATUS);
		user.setCreateTime(super.getSystemDate());
		
		if(StringUtil.isEmpty(user.getSex())){
			user.setSex("男");
		}
		super.save(user);
		return user.getId();
	}
	
	

	/**
	 * 
	 * @describe 从OA同步用户，创建用户
	 * @author 
	 * @date 2013-10-15
	 * @param 
	 * @
	 */
	@Override
	public Serializable createUserByOA(User user) {
	    Role role = new Role();
	    role.setId("-1");
		Set<Role> roleSet = new HashSet<Role>();
		roleSet.add(role);
		user.setRoleSet(roleSet);	
		super.save(user);
		return user.getId();
	}
	
	private UserVo getUserVo(User user){
		UserVo userVo = new UserVo();
		userVo.setId(user.getId());
		userVo.setRealName(user.getRealName());
		userVo.setUserName(user.getUserName());
		userVo.setSex(user.getSex());
		return userVo;
	}

}
