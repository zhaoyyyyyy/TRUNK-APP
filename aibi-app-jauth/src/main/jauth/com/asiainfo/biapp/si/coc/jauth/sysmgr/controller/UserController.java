package com.asiainfo.biapp.si.coc.jauth.sysmgr.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

import com.asiainfo.biapp.si.coc.jauth.frame.Constants;
import com.asiainfo.biapp.si.coc.jauth.frame.json.JSONResult;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.util.Bcrypt;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Group;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Role;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.User;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.UserService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.utils.SessionInfoHolder;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.UserVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @describe
 * @author liukai
 * @date 2013-6-28
 */

@Api(value = "用户管理", description = "用户登录,角色,资源等接口1")
@RequestMapping("api/user")
@RestController
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private SessionInfoHolder sessionInfoHolder;
	
	private String success = "success";

	/**
	 * 
	 * @describe 用户查询
	 * @author liukai
	 * @param
	 * @date 2013-6-28
	 */
	@ApiOperation(value = "显示用户的列表（分页形式）")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "cols", value = "列名称", required = true, paramType = "query", dataType = "string", defaultValue = "userName,realName,sex,orgNames,roleNames,groupNames,status,createTime,id"),
			@ApiImplicitParam(name = "userName", value = "用户姓名", required = false, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "realName", value = "用户真实姓名", required = false, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "orgNames", value = "用户所在组织", required = false, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "roleName", value = "用户角色", required = false, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "createTimeStart", value = "用户创建时间（开始）", required = false, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "createTimeEnd", value = "用户创建时间（结束）", required = false, paramType = "query", dataType = "string") })
	@RequestMapping(value = "/userPage/query", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public String list(@ModelAttribute JQGridPage<User> page, String cols,
			@ApiIgnore UserVo userVo) {
		User user = userService.get(sessionInfoHolder.getLoginId());
		if (user.getGroupSet() != null && !user.getGroupSet().isEmpty()) {
			userVo.setGroupSet(user.getGroupSet());
		}
		if (user.getIsAdmin() == 1) {
			JQGridPage<User> userList = userService.findUserList(page, userVo);
			return JSONResult.page2Json(userList, cols);
		} else {
			userVo.setOrginfoId(user.getOrginfoId());// 本组织
			userVo.setId(user.getId());// 当前用户
			userVo.setIsAdmin(2);
			JQGridPage<User> userList = userService.findUserList(page, userVo);
			return JSONResult.page2Json(userList, cols);
		}
	}

	/**
	 * 
	 * @describe 分配角色
	 * @author liukai
	 * @param
	 * @date 2013-7-1
	 */
	@ApiOperation(value = "查询角色")
	@ApiImplicitParam(name = "id", value = "用户信息主键", required = true, paramType = "query", dataType = "string")
	@RequestMapping(value = "/role/query", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Map<String, Object> toDistributionRole(String id) {
		String[] ids = id.split(",");
		String roleId = null;

		List<User> userList = new ArrayList<>();
		// 如果只选择了一个用户则回显
		if (ids.length == 1) {
			User user = userService.get(ids[0]);
			StringBuffer sb = new StringBuffer();
			for (Role r : user.getRoleSet()) {
				if (sb.length() > 0) {
					sb.append(",");
				}
				sb.append(r.getId());
			}
			roleId = sb.toString();
		}
		for (String i : ids) {
			userList.add(userService.get(i));
		}

		Map<String, Object> map = new HashMap<>();
		map.put("userList", userList);
		map.put("roleId", roleId);
		return map;
	}

	/**
	 * 
	 * @describe 分配用户角色
	 * @author liukai
	 * @param
	 * @date 2013-7-2
	 */
	@ApiOperation(value = "修改角色")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "用户信息主键", required = true, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "roleId", value = "角色信息主键", required = true, paramType = "query", dataType = "string") })
	@RequestMapping(value = "/role/update", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public String distributionRole(String id, String roleId) {
		String[] ids = id.split(",");
		for (String i : ids) {
			User user = userService.get(i);
			String[] roleIds = roleId.split(",");
			Set<Role> roleSet = new HashSet<>();
			for (String rId : roleIds) {
				Role role = new Role();
				role.setId(rId);
				roleSet.add(role);
			}
			user.setRoleSet(roleSet);
			userService.saveOrUpdate(user);
		}
		return success;
	}

	/**
	 * 
	 * @describe 分配数据范围
	 * @author liukai
	 * @param
	 * @date 2013-7-1
	 */
	@ApiOperation(value = "查询数据范围")
	@ApiImplicitParam(name = "id", value = "用户信息主键", required = true, paramType = "query", dataType = "string")
	@RequestMapping(value = "/group/query", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Map<String, Object> toDistributionGroup(String id) {
		String[] ids = id.split(",");
		String groupId = null;
		// 如果只有一个用户则进行回显
		if (ids.length == 1) {
			User user = userService.get(ids[0]);
			StringBuffer sb = new StringBuffer();
			for (Group g : user.getGroupSet()) {
				if (sb.length() > 0) {
					sb.append(",");
				}
				sb.append(g.getId());
			}
			groupId = sb.toString();
		}

		List<User> userList = new ArrayList<>();
		for (String i : ids) {
			userList.add(userService.get(i));
		}

		Map<String, Object> map = new HashMap<>();
		map.put("userList", userList);
		map.put("groupId", groupId);
		return map;
	}

	/**
	 * 
	 * @describe 分配数据范围
	 * @author liukai
	 * @param
	 * @date 2013-7-2
	 */
	@ApiOperation(value = "修改数据范围")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "用户信息主键", required = true, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "groupId", value = "数据范围信息主键", required = true, paramType = "query", dataType = "string") })
	@RequestMapping(value = "/group/update", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public String distributionGroup(String id, String groupId) {
		String[] ids = id.split(",");
		for (String i : ids) {
			User user = userService.get(i);
			String[] groupIds = groupId.split(",");
			Set<Group> groupSet = new HashSet<>();
			for (String gId : groupIds) {
				Group group = new Group();
				group.setId(gId);
				groupSet.add(group);
			}
			user.setGroupSet(groupSet);
			userService.saveOrUpdate(user);
		}
		return success;
	}

	/**
	 * 
	 * @describe 进入编写用户模块
	 * @author liukai
	 * @param
	 * @date 2013-7-2
	 */
	@ApiOperation(value = "查询详细信息")
	@ApiImplicitParam(name = "id", value = "用户信息主键", required = true, paramType = "query", dataType = "string")
	@RequestMapping(value = "/userInfo/query", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Map<String, Object> initEditUser(String id) {
		User user = userService.get(id);
		User luser = sessionInfoHolder.getLoginUser();
		Map<String, Object> map = new HashMap<>();
		map.put("user", user);
		map.put("luser", luser);
		return map;
	}

	/**
	 * 
	 * @describe 保存用户
	 * @author liukai
	 * @param
	 * @date 2013-7-3
	 */
	@ApiOperation(value = "修改用户信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "用户信息主键", required = false, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "realName", value = "真实姓名", required = false, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "sex", value = "性别", required = false, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "phoneNumber", value = "手机", required = false, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "grade", value = "等级", required = false, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "totalIntegra", value = "积分", required = false, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "email", value = "邮箱", required = false, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "isAdmin", value = "是否允许进入后台", required = false, paramType = "query", dataType = "string") })
	@RequestMapping(value = "/userInfo/update", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public String save(User user) {
		User oldUser = userService.get(user.getId());
		oldUser = fromTobean(user, oldUser);
		userService.saveOrUpdate(oldUser);
		return success;
	}

	/**
	 * 
	 * @describe 封装From信息
	 * @author liukai
	 * @param
	 * @date 2013-7-3
	 */
	private User fromTobean(User user, User oldUser) {
		oldUser.setRealName(user.getRealName());
		oldUser.setPhoneNumber(user.getPhoneNumber());
		oldUser.setEmail(user.getEmail());
		oldUser.setSex(user.getSex());
		return oldUser;
	}

	/**
	 * 
	 * @describe 保存密码
	 * @author liukai
	 * @param
	 * @date 2013-9-24
	 */
	@ApiOperation(value = "修改密码")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "用户信息主键", required = true, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query", dataType = "string") })
	@RequestMapping(value = "/password/update", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public String savePassword(String id, String password) {
		User user = userService.get(id);
		user.setPassword(Bcrypt.BcryptCode(password));
		userService.saveOrUpdate(user);
		return success;
	}

	@RequestMapping(value = "/newUser", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public String newUser(User user) {
		User luser = sessionInfoHolder.getLoginUser();
		user.setOrginfoId(luser.getOrginfoId());
		user.setCreateOrgId(luser.getOrginfoId());
		user.setCreateUserId(luser.getUserName());
		user.setIsAdmin(2);
		user.setStatus(2);
		user.setPassword(Bcrypt.BcryptCode(user.getPassword()));
		userService.saveOrUpdate(user);
		return success;
	}

	/**
	 * 
	 * @describe 变更用户状态
	 * @author liukai
	 * @param
	 * @date 2013-7-1
	 */
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public void changeStatus(String id) {
		User user = userService.get(id);
		// 原状态正常
		if (user.getStatus().equals(Constants.USER_ENABLE_STATUS)) {
			user.setStatus(Constants.USER_LOCKED_STATUS);
			// 原状态 停用
		} else if (user.getStatus().equals(Constants.USER_LOCKED_STATUS)) {
			user.setStatus(Constants.USER_ENABLE_STATUS);
		}
		userService.saveOrUpdate(user);
	}

}
