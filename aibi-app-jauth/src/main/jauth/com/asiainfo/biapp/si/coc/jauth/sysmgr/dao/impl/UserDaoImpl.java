/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.coc.jauth.frame.Constants;
import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.page.Page;
import com.asiainfo.biapp.si.coc.jauth.frame.util.DateUtil;
import com.asiainfo.biapp.si.coc.jauth.frame.util.StringUtil;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.UserDao;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Group;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Organization;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Resource;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Role;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.User;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.UserVo;

/**
 * @describe
 * @author liukai
 * @date 2013-6-28
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<User,String> implements UserDao {



	/**
	 * 根据查询条件查询出角色
	 * @describe
	 * @author liukai
	 * @param
	 * @date 2013-6-21
	 */
	public JQGridPage<User> findUserList(JQGridPage<User> page, UserVo userVo) {
		// 拼装hql 及参数
		Map<String, Object> params = new HashMap<>();
		StringBuffer hql = new StringBuffer(
				"from User r where r.status!=3 and r.id !='-2' ");
		// 管理员
		if (userVo.getIsAdmin()!=null) {
			hql.append(" and r.isAdmin != :isAdmin");
			params.put("isAdmin", 1);
		}
		// 本用户
		if (StringUtils.isNotBlank(userVo.getId())) {
			hql.append(" and r.id != :id");
			params.put("id", userVo.getId());
		}
		// 组织
		if (StringUtils.isNotBlank(userVo.orginfoId)) {
			hql.append(" and r.orginfoId = :orginfoId");
			params.put("orginfoId", userVo.orginfoId);
		}
		// 组织名称
		if (StringUtils.isNotBlank(userVo.getOrgName())) {
			hql.append(" and exists(select 'X' from r.orgSet l where l.fullName like :fullName)");
			params.put("fullName", "%" + userVo.getOrgName() + "%");
		}
		// 角色名称
		if (StringUtils.isNotBlank(userVo.getRoleName())) {
			hql.append(" and exists(select 1 from r.roleSet l where l.roleName like :roleName)");
			params.put("roleName", "%" + userVo.getRoleName() + "%");
		}
		// 创建时间
		if (StringUtils.isNotBlank(userVo.getCreateTimeStart())) {
			hql.append(" and r.createTime >= :createTimeStart");
			params.put("createTimeStart", DateUtil.parse(userVo.getCreateTimeStart(),DateUtil.FMT_DATE_YYYYMMDDHHmmss));
		}
		if (StringUtils.isNotBlank(userVo.getCreateTimeEnd())) {
			hql.append(" and r.createTime <= :createTimeEnd");
			params.put("createTimeEnd", DateUtil.parse(userVo.getCreateTimeEnd(),DateUtil.FMT_DATE_YYYYMMDDHHmmss));
		}
		// 用户名称
		if (StringUtils.isNotBlank(userVo.getUserName())) {
			hql.append(" and r.userName like :userName");
			params.put("userName", "%" + userVo.getUserName() + "%");
		}
		// 真实姓名
		if (StringUtils.isNotBlank(userVo.getRealName())) {
			hql.append(" and r.realName like :realName");
			params.put("realName", "%" + userVo.getRealName() + "%");
		}
		if(StringUtils.isNotBlank(page.getSortCol())){
			if(page.getSortCol().contains("Names")){
				hql.append(" order by r.createTime "+page.getSortOrder()+",r.userName");
			}else{
				hql.append(" order by r."+page.getSortCol()+" "+page.getSortOrder());
			}
		}else{
			hql.append(" order by r.createTime desc,r.userName");
		}
		
		return (JQGridPage<User>) super.findPageByHql(page, hql.toString(),
				params);
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
		// 拼装hql 及参数
		Map<String, Object> params = new HashMap<>();
		StringBuffer hql = new StringBuffer(
				"from User r where r.status!=3 and r.id !='-2'");
		// 组织名称
		if (StringUtils.isNotBlank(userVo.getOrgName())) {
			hql
					.append(" and exists (select 'X' from OrgUser o where o.userId=r.id and o.organization.fullName like :orgName)");
			params.put("orgName", "%" + userVo.getOrgName() + "%");
		}
		// 角色名称
		if (StringUtils.isNotBlank(userVo.getRoleName())) {
			hql
					.append(" and exists(select 1 from r.roleSet l where l.roleName like :roleName)");
			params.put("roleName", "%" + userVo.getRoleName() + "%");
		}
		// 创建时间
		if (StringUtils.isNotBlank(userVo.getCreateTimeStart())) {
			hql.append(" and r.createTime >= TO_TIMESTAMP(:createTimeStart,'"
					+ Constants.ORACLEFORMAT + "') ");
			params.put("createTimeStart", userVo.getCreateTimeStart());
		}
		if (StringUtils.isNotBlank(userVo.getCreateTimeEnd())) {
			hql.append(" and r.createTime <= TO_TIMESTAMP(:createTimeEnd,'"
					+ Constants.ORACLEFORMAT + "')");
			params.put("createTimeEnd", userVo.getCreateTimeEnd());
		}
		// 用户名称
		if (StringUtils.isNotBlank(userVo.getUserName())) {
			hql.append(" and r.userName like :userName");
			params.put("userName", "%" + userVo.getUserName() + "%");
		}
		// 真实姓名
		if (StringUtils.isNotBlank(userVo.getRealName())) {
			hql.append(" and r.realName like :realName");
			params.put("realName", "%" + userVo.getRealName() + "%");
		}
		// 该用户有数据范围
		if (userVo.getGroupSet() != null && userVo.getGroupSet().size() > 0) {
			hql.append(" and  exists (select 'X' from r.orgSet o where o.orgCode in (:orgSet)) ");
			Set<String> set = new HashSet<String>();

			for (Group group : userVo.getGroupSet()) {
				for (Organization o : group.getOrganizationSet()) {
					set.add(o.getOrgCode());
				}

			}
			params.put("orgSet", set);
		}
		hql.append(" order by r.createTime desc,r.userName");
		return super.findListByHql(hql.toString(), params);
	}

	/**
	 * 
	 * @根据查询条件 查询用户
	 * @author liukai
	 * @param
	 * @date 2013-7-2
	 */
	public List<User> findUserListByHql(UserVo userVo) {
		// 拼装hql 及参数
		Map<String, Object> params = new HashMap<>();
		StringBuffer hql = new StringBuffer(
				"from User r where r.status!=3 and r.id !='-2' ");
		// 组织名称
		if (StringUtils.isNotBlank(userVo.getOrgName())) {
			hql
					.append(" and exists (select 'X' from OrgUser o where o.userId=r.id and o.organization.fullName like :orgName)");
			params.put("orgName", "%" + userVo.getOrgName() + "%");
		}
		// 角色名称
		if (StringUtils.isNotBlank(userVo.getRoleName())) {
			hql
					.append(" and exists(select 1 from r.roleSet l where l.roleName like :roleName)");
			params.put("roleName", "%" + userVo.getRoleName() + "%");
		}
		// 真实姓名
		if (StringUtils.isNotBlank(userVo.getRealName())) {
			hql.append(" and r.realName like :realName");
			params.put("realName", "%" + userVo.getRealName() + "%");
		}
		// 创建时间
		if (StringUtils.isNotBlank(userVo.getCreateTimeStart())) {
			hql.append(" and r.createTime >= TO_TIMESTAMP(:createTimeStart,'"
					+ Constants.ORACLEFORMAT + "') ");
			params.put("createTimeStart", userVo.getCreateTimeStart());
		}
		if (StringUtils.isNotBlank(userVo.getCreateTimeEnd())) {
			hql.append(" and r.createTime <= TO_TIMESTAMP(:createTimeEnd,'"
					+ Constants.ORACLEFORMAT + "')");
			params.put("createTimeEnd", userVo.getCreateTimeEnd());
		}
		// 用户名称
		if (StringUtils.isNotBlank(userVo.getUserName())) {
			hql.append(" and r.userName like :userName");
			params.put("userName", "%" + userVo.getUserName() + "%");
		}
		// 该用户有数据范围
		if (!userVo.getGroupSet().isEmpty()) {
			hql.append(" and  exists (select 'X' from r.orgSet o where o.orgCode in (:orgSet)) ");
			Set<String> set = new HashSet<>();
			for (Group group : userVo.getGroupSet()) {
				for (Organization o : group.getOrganizationSet()) {
					set.add(o.getOrgCode());
				}

			}
			params.put("orgSet", set);
		}
		return super.findListByHql(hql.toString(), params);
	}

	/**
	 * 
	 * @describe 根据ID获取用户
	 * @author liukai
	 * @param
	 * @date 2013-7-1
	 */
	public User get(String id) {
		return super.get(id);
	}

	/**
	 * 
	 * @describe 根据ID删除用户
	 * @author liukai
	 * @param
	 * @date 2013-7-31
	 */
	public void delete(String id) {
		String sql = "update T_SYS_USER u set u.STATUS=3 where u.ID=?";
		super.excuteSql(sql, id);
	}

	/**
	 * 保存或更新用户
	 */
	public void saveOrUpdate(User user) {
		super.saveOrUpdate(user);
	}

	/**
	 * @describe 根据用户名，查询用户对象
	 * @author XieGaosong
	 * @param
	 * @date 2013-7-10
	 */
	public User getUserByName(String userName) {
		
		User user = this.findOneByHql(
				"from User where upper(userName)= ?0 and STATUS!=3",
				new Object[] { userName.toUpperCase() });
		
		
		if (user != null) {
			List<Resource> resources = new ArrayList<>();
			Set<Role> roles = user.getRoleSet();
			for (Role role : roles) {
				Set<Resource> tempRes = role.getResourceSet();
				for (Resource res : tempRes) {
					resources.add(res);
				}
			}
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
		StringBuffer hql = new StringBuffer();
		Map<String, Object> params = new HashMap<>();
		hql.append("from User su where 1=1 and su.status=1");
		if (!StringUtil.isEmpty(userVo.getRealName())) {
			hql.append(" and su.realName like :realName");
			params.put("realName", "%" + userVo.getRealName() + "%");
		}

		
		if (StringUtil.isNotEmpty(userVo.getOrgName())) {
			hql.append(" and exists (select 'orgName' from su.orgSet org where org.simpleName like :simpleName");
			params.put("simpleName", "%" + userVo.getOrgName() + "%");
		}
		
		Page<User> pagelis = this.findPageByHql(page, hql.toString(), params);
		return pagelis.getData();
	}

	/**
	 * 
	 * @describe 根据当前用户ID，查询出不是自己好友的用户
	 * @author ljs
	 * @date 2013-8-28
	 * @param userId
	 * @return
	 */
	public List<User> findNotUserFriendList(String userId) {
		return super
				.findListByHql(
						"from User su where not exists (select tu.id from UserFriend tu where exists"
								+ "(select tg.id from UserFriendGroup tg where tg.id=tu.groupId and tg.userId=?) "
								+ "and su.id=tu.userId)  and  exists (select 'X' from su.roleSet o where o.id not in "
								+ "('8a8180a63f84d050013f84d1b5c30001')) and su.id !=? )",
						userId, userId);
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
		return super.findListByHql(
						"from User su where su.id not in (select tu.id from UserFriend tu where exists"
								+ "(select tg.id from UserFriendGroup tg where tg.id=tu.groupId and tg.userId=?) "
								+ "and su.id=tu.userId)  and  exists (select 'X' from su.roleSet o where o.id not in "
								+ "('8a8180a63f84d050013f84d1b5c30001')) and su.id !=? ) and su.appSysCode = ?",
						userId, userId, orgCode);
	}

	/**
	 * 
	 * @describe 根据用户Id，查询出用户的好友
	 * @author ljs
	 * @date 2013-8-12
	 * @param userId
	 * @return
	 */
	public List<User> findUserFriends(String userId) {
		return this
				.findListByHql(
						"from User su where exists (select tu.id from UserFriend tu where exists(select tg.id from UserFriendGroup tg where tg.id=tu.groupId and tg.userId=? and tu.friendType=1 ) and su.id=tu.userId ) order by su.totalIntegra desc,su.integra desc",
						userId);
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
	public List<User> findUserFriends(String userId, String orgCode) {
		return super
				.findListByHql(
						"from User su where exists (select tu.id from UserFriend tu where exists(select tg.id from UserFriendGroup tg where tg.id=tu.groupId and tg.userId=?) and su.id=tu.userId and su.appSysCode = ? ) order by su.totalIntegra desc,su.integra desc",
						userId, orgCode);
	}

}
