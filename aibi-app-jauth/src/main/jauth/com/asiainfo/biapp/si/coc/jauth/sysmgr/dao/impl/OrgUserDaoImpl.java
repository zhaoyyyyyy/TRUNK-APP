/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.coc.jauth.frame.Constants;
import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.page.Page;
import com.asiainfo.biapp.si.coc.jauth.frame.util.StringUtil;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.OrgUserDao;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.OrgUser;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.OrgUserVo;

/**
 * @describe 组织成员Dao实现
 * @author ljs
 * @date 2013-6-26
 */
@Repository
public class OrgUserDaoImpl extends BaseDaoImpl<OrgUser,String> implements OrgUserDao {


	/**
	 * 根据条件查询组织成员
	 * 
	 * @author ljs
	 * @param page
	 * @param orgUserVo
	 * @return
	 */
	@Override
	public JQGridPage<OrgUser> findUserPageByParams(Page<OrgUser> page,
			OrgUserVo orgUserVo) {
		// 拼装hql 及参数
		Map<String, Object> params = new HashMap<>();
		StringBuilder hql = new StringBuilder();
		hql.append("from OrgUser where 1=1");
		if (!StringUtil.isEmpty(orgUserVo.getOrgCode())) {
			hql.append(" and orgCode = :orgCode");
			params.put("orgCode", orgUserVo.getOrgCode());
		}
		if (!StringUtil.isEmpty(orgUserVo.getNameOrUserName())) {
			hql
					.append(" and (userName like :name or user.userName like :name)");
			params.put("name", "%" + orgUserVo.getNameOrUserName() + "%");
		}
		if (!StringUtil.isEmpty(orgUserVo.getStatus())) {
			hql.append(" and status = :status");
			params.put("status", orgUserVo.getStatus());
		}
		if (orgUserVo.getJoinTime_min() != null
				&& orgUserVo.getJoinTime_max() != null) {
			hql.append(" and joinTime between :joinTime_min and :joinTime_max");
			params.put("joinTime_min", orgUserVo.getJoinTime_min());
			params.put("joinTime_max", orgUserVo.getJoinTime_max());
		}
		return (JQGridPage<OrgUser>) super.findPageByHql(page, hql.toString(),
				params);
	}

	/**
	 * @describe 查询组织成员集合
	 * @author XieGaosong
	 * @param List
	 *            <OrgUser>
	 * @date 2013-6-28
	 */
	public List<OrgUser> findOrgUserList(OrgUserVo orgUserVo) {
		List<OrgUser> list = null;
		StringBuilder hql = new StringBuilder();
		Map<String, Object> map = new HashMap<>();
		hql.append("from OrgUser where 1=1");
		if (!StringUtil.isEmpty(orgUserVo.getOrgCode())) {
			hql.append(" and orgCode =:orgCode");
			map.put("orgCode", orgUserVo.getOrgCode());
		}

		if (!StringUtil.isEmpty(orgUserVo.getUserName())) {
			hql.append(" and userName like :userName");
			map.put("userName", "%" + orgUserVo.getUserName() + "%");
		}

		if (!StringUtil.isEmpty(orgUserVo.getStatus())) {
			hql.append(" and status = :status");
			map.put("status", orgUserVo.getStatus());
		}

		if (orgUserVo.getJoinTime_min() != null
				&& orgUserVo.getJoinTime_max() != null) {
			hql.append(" and joinTime between :joinTime_min and :joinTime_max");
			map.put("joinTime_min", orgUserVo.getJoinTime_min());
			map.put("joinTime_max", orgUserVo.getJoinTime_max());
		}


		if (!StringUtil.isEmpty(orgUserVo.getUserId())) {
			hql.append(" and userId is not null ");
		}

		list = super.findListByHql(hql.toString(), map);
		return list;
	}

	/**
	 * 根据id改变组织成员状态
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public Serializable updateStatus(String id) {
		OrgUser orgUser = findOneByHql("from OrgUser where id =?", new Object[] { id });
		updateStatus(id, orgUser.getStatus());
		return orgUser.getStatus();
	}

	/**
	 * 根据id设置组织成员状态为status
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	@Override
	public Serializable updateStatus(String id, String status) {
		return excuteHql("update OrgUser set status=? where id=?",
				new Object[] { status, id });
	}

	/**
	 * 根据id改变组织成员角色
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public Serializable updateType(String id) {
		OrgUser orgUser = get(id);
		
		orgUser.setType(Constants.ORGUSER_TYPE.MANAGER);
		this.updateType(id, orgUser.getType());
		return orgUser.getType();
	}

	/**
	 * 根据id设置组织成员角色为type
	 * 
	 * @param id
	 * @param type
	 * @return
	 */
	@Override
	public Serializable updateType(String id, String type) {
		return excuteHql("update OrgUser set type=? where id=?", new Object[] {
				type, id });
	}

	/**
	 * @describe 将用户ID设置为null
	 * @author XieGaosong
	 * @date 2013-7-1
	 */
	public Serializable updateKickedOut(String id) {
		return excuteHql(
				"update OrgUser set userId='',isAdmin='',type='04' where id=?",
				new Object[] { id });
	}

	/**
	 * @describe 根据ID修改名字
	 * @author XieGaosong
	 * @param
	 * @date 2013-7-3
	 */
	public void updateAdvance(OrgUser orgUser) {
		excuteHql("update OrgUser set userName = ? where id = ?", new Object[] {
				orgUser.getUserName(), orgUser.getId() });
	}

	/**
	 * @describe 查询正式成员 pageList
	 * @author XieGaosong
	 * @param
	 * @date 2013-7-5
	 */
	public JQGridPage<OrgUser> findOfficialMemberPageByParams(
			Page<OrgUser> page, OrgUserVo orgUserVo) {
		StringBuilder hql = new StringBuilder();
		Map<String, Object> map = new HashMap<>();
		hql.append("from OrgUser ou where 1=1");

		if (!StringUtil.isEmpty(orgUserVo.getOrgCode())) {
			hql.append(" and orgCode =:orgCode");
			map.put("orgCode", orgUserVo.getOrgCode());
		}

		if (!StringUtil.isEmpty(orgUserVo.getUserName())) {
			hql.append(" and userName like :userName");
			map.put("userName", "%" + orgUserVo.getUserName() + "%");
		}

		if (!StringUtil.isEmpty(orgUserVo.getStatus())) {
			hql.append(" and status = :status");
			map.put("status", orgUserVo.getStatus());
		}

		if (orgUserVo.getJoinTime_min() != null
				&& orgUserVo.getJoinTime_max() != null) {
			hql.append(" and joinTime between :joinTime_min and :joinTime_max");
			map.put("joinTime_min", orgUserVo.getJoinTime_min());
			map.put("joinTime_max", orgUserVo.getJoinTime_max());
		}
		hql.append(" and userId is not null ");
		return (JQGridPage<OrgUser>) super.findPageByHql(page, hql.toString(),
				map);
	}

	/**
	 * 查找组织用户
	 * 
	 * @author ljs
	 * @param userId
	 * @param orgCode
	 * @return
	 */
	@Override
	public OrgUser getOrgUser(String userId, String orgCode) {
		return super.findOneByHql(
				"from OrgUser where userId =? and orgCode =?", new Object[] {
						userId, orgCode });
	}

	/***
	 * @describe查看是否有预录成员
	 * @author XieGaosong
	 * @param
	 * @date 2013-7-26
	 */
	public OrgUser getPrerecord(String userName, String orgCode) {
		return this.findOneByHql(
				"from OrgUser where userName=? and orgCode =?", userName,
				orgCode);
	}

	/***
	 * @describe 让组织成员的预录成员成为正式成员
	 * @author XieGaosong
	 * @param
	 * @date 2013-7-26
	 */
	public void saveOrgUserMembers(OrgUser orgUser) {
		this.update(orgUser);
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
	public List<OrgUser> listOrgUser(String userId, String type) {
		return super.findListByHql(
				"from OrgUser where userId = ? and type = ?", userId, type);
	}



	/**
	 * @describe 根据组织Code查询出集合
	 * @author XieGaosong
	 * @date 2013-8-19
	 * @param orgCode
	 * @return
	 */
	public List<OrgUser> listOrgUser(String orgCode) {
		return super.findListByHql("from OrgUser where orgCode = ?", orgCode);
	}

	/**
	 * 
	 * @describe 查询出组织用户集合集合
	 * @author ljs
	 * @date 2013-10-18
	 * @param orgCode
	 * @param userTypes
	 *            想显示的用户类型
	 * @param notUserTypes
	 *            不想显示的用户类型
	 * @return
	 */
	@Override
	public List<OrgUser> listOrgUser(String orgCode, String[] userTypes,
			String[] notUserTypes) {
		// 拼装hql 及参数
		Map<String, Object> params = new HashMap<>();
		StringBuilder hql = new StringBuilder();
		if (orgCode == null) {
			return null;
		}
		hql.append("from OrgUser where orgCode = :orgCode");
		params.put("orgCode", orgCode);
		if (userTypes != null) {
			int i = 0;
			for (String userType : userTypes) {
				if (i == 0) {
					hql.append(" and type = :userType" + i);
					params.put("userType" + i, userType);
				} else {
					hql.append(" or type = :userType" + i);
					params.put("userType" + i, userType);
				}
			}
		} else if (notUserTypes != null) {
			int i = 0;
			for (String notUserType : notUserTypes) {
				if (i == 0) {
					hql.append(" and type != :notUserType" + i);
					params.put("notUserType" + i, notUserType);
				}
			}
		}
		return super.findListByHql(hql.toString(), params);
	}

	/**
	 * @describe 根据组织Code删除组织成员
	 * @author XieGaosong
	 * @date 2013-8-23
	 * @param orgCode
	 * @return
	 */
	public void deleteOrgUser(String orgCode) {
		super.excuteHql("delete from OrgUser where orgCode = ?", orgCode);
	}

	/**
	 * 
	 * @describe 根据orgCode和userId删除组织成员
	 * @author ljs
	 * @date 2013-9-5
	 * @param orgCode
	 * @param userId
	 */
	@Override
	public void deleteOrgUser(String orgCode, String userId) {
		super.excuteHql("delete from OrgUser where orgCode = ? and userId = ?",
				orgCode, userId);
	}


	/**
	 * 
	 * @author liukai
	 * @param
	 * @date 2013-10-25
	 */
	public List<OrgUser> findOrgUserByUserId(String id) {
		String hql = "from OrgUser u where u.userId=?";
		return super.findListByHql(hql, id);
	}

	/**
	 * 
	 * @describe 根据用户ID修改性别
	 * @author liukai
	 * @param
	 * @date 2013-10-30
	 */
	public void updateOrgUserSexByUserId(String userId, String sex) {
		super.excuteSql(
				"update t_sys_org_user u set u.SEX=? where u.USER_ID=?", sex,
				userId);
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
	public List<OrgUser> listOtherOrgUser(String userId, String type) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder hql = new StringBuilder();
		hql.append("from OrgUser u where 1=1");
		if (!StringUtil.isEmpty(userId)) {
			hql
					.append(" and u.orgCode in (select u2.orgCode from OrgUser u2 where u2.userId = :userId) and (u.userId != :userId or u.userId is null)");
			params.put("userId", userId);
		}
		if (!StringUtil.isEmpty(type)) {
			hql.append(" and type = :type");
			params.put("type", type);
		}
		return super.findListByHql(hql.toString(), params);
	}


}
