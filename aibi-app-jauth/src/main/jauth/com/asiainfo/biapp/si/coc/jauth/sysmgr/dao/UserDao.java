/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.sysmgr.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDao;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.User;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.UserVo;

/**
 * @describe
 * @author liukai
 * @date 2013-6-28
 */
@Repository
public interface UserDao extends BaseDao<User,String>{
	/**
	 * 根据查询条件查询出角色
	 * 
	 * @describe 
	 * @author liukai
	 * @param
	 * @date 2013-6-21
	 */
	public JQGridPage<User> findUserList(JQGridPage<User> page, UserVo userVo);
	
	/**
	 * 
	 * @describe 根据条件查询出用户列表
	 * @author ljs
	 * @date 2013-9-30
	 * @param userVo
	 * @return
	 */
	public List<User> listUser(UserVo userVo);
	/**
	 * 
	 * @根据查询条件 查询用户
	 * @author liukai
	 * @param
	 * @date 2013-7-2
	 */
	public List<User> findUserListByHql(UserVo userVo);
	
	
	/**
	 * @describe 根据用户名，查询用户对象
	 * @author XieGaosong
	 * @param
	 * @date 2013-7-10
	 */
	public User getUserByName(String userName);
	
	/**
	 * @describe 根据当前用户ID，查询出不是自己好友的用户
	 * @author XieGaosong
	 * @param
	 * @date 2013-7-31
	 */
	public List<User> findNotUserFriendList(JQGridPage<User> page,UserVo userVo);
	
	/**
	 * 
	 * @describe 根据当前用户ID，查询出不是自己好友的用户
	 * @author ljs
	 * @date 2013-8-28
	 * @param userId
	 * @return
	 */
	public List<User> findNotUserFriendList(String userId);
	
	/**
	 * 
	 * @describe 根据当前用户ID，查询出不是自己好友的用户
	 * @author ljs
	 * @date 2013-10-14
	 * @param userId
	 * @param orgCode
	 * @return
	 */
	public List<User> findNotUserFriendList(String userId, String orgCode);
	
	/**
	 * 
	 * @describe 根据用户Id，查询出用户的好友
	 * @author ljs
	 * @date 2013-8-12
	 * @param userId
	 * @return
	 */
	public List<User> findUserFriends(String userId);
	
	/**
	 * 
	 * @describe 根据用户Id和站点orgCode，查询出用户的好友
	 * @author ljs
	 * @date 2013-10-14
	 * @param userId
	 * @param orgCode
	 * @return
	 */
	public List<User> findUserFriends(String userId, String orgCode);
	
	/**
	 * 
	 * @describe 根据ID删除用户
	 * @author liukai
	 * @param
	 * @date 2013-7-31
	 */
	public void delete(String id);
	
}
