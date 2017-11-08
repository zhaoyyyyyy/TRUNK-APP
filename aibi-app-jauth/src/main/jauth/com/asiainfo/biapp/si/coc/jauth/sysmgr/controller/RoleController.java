/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.sysmgr.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

import com.asiainfo.biapp.si.coc.jauth.frame.controller.BaseController;
import com.asiainfo.biapp.si.coc.jauth.frame.json.JSONResult;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.service.BaseService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Resource;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Role;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.User;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.ResourceService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.RoleService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.utils.SessionInfoHolder;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.RoleVo;

/**
 * @describe
 * @author liukai
 * @date 2013-6-21
 */
@Api(value = "角色管理",description = "用户登录,角色,资源等接口")
@RequestMapping("api/role")
@RestController
public class RoleController extends BaseController<Role> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private RoleService roleService;
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private SessionInfoHolder sessionInfoHolder;

	@Override
	protected BaseService<Role, String> getBaseService() {
		return roleService;
	}

	/**
	 * 查询角色列表
	 * 
	 * @describe
	 * @author liukai
	 * @param
	 * @return
	 * @return
	 * @date 2013-6-21
	 */
	@ApiOperation(value = "显示角色的列表（分页形式）")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "cols", value = "列名称", required = true, paramType = "query", dataType = "string", defaultValue = "pictureHome,roleName,createUserId,createTime,roleDesc,id"),
			@ApiImplicitParam(name = "roleName", value = "角色名称", required = false, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "createTimeStart", value = "角色创建时间（开始）", required = false, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "createTimeEnd", value = "角色创建时间（结束）", required = false, paramType = "query", dataType = "string") })
	@RequestMapping(value = "/rolePage/query", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public String list(@ModelAttribute JQGridPage<Role> page,
			@ApiIgnore RoleVo roleVo, String cols) {
		// zn
		User user = sessionInfoHolder.getLoginUser();
		if (user.getIsAdmin() == 1) {
			JQGridPage<Role> roleList = roleService.findRoleList(page, roleVo);
			return JSONResult.page2Json(roleList, cols);
		} else {
			roleVo.setCreateUserId(user.getUserName());
			JQGridPage<Role> roleList = roleService.findRoleList(page, roleVo);
			return JSONResult.page2Json(roleList, cols);
		}// zn
	}

	/**
	 * 
	 * @describe 进入角色新建页面
	 * @author liukai
	 * @param
	 * @date 2013-6-24
	 */
	@ApiOperation(value = "查询角色资源")
	@ApiImplicitParam(name = "id", value = "角色主键", required = true, paramType = "query", dataType = "string")
	@RequestMapping(value = "/resource/query", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Map<String, Object> resources(String id) {
		String tree = null;
		Role role = new Role();
		User user = sessionInfoHolder.getLoginUser();
		// 有ID则为编辑
		if (StringUtils.isNotBlank(id)) {
			role = roleService.get(id);
			StringBuffer sb = new StringBuffer();
			for (Resource r : role.getResourceSet()) {
				if (sb.length() > 0) {
					sb.append(",");
				}
				sb.append(r.getId());
			}
			tree = sb.toString();
		}

		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("tree", tree);
		returnMap.put("role", role);
		return returnMap;
	}

	/**
	 * 保存角色信息
	 * 
	 * @return
	 */
	@ApiOperation(value = "新建或修改角色")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "角色主键", required = false, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "roleName", value = "角色名称", required = false, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "pictureHome", value = "标志", required = false, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "roleDesc", value = "角色描述", required = false, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "tree", value = "角色赋权（前台）", required = false, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "tree2", value = "角色赋权（后台）", required = false, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "tree3", value = "角色赋权（接口）", required = false, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "tree4", value = "角色赋权（页面）", required = false, paramType = "query", dataType = "string") })
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public String save() {
		String roleName = request.getParameter("roleName");
		String id = request.getParameter("id");
		String pictureHome = request.getParameter("pictureHome");
		String roleDesc = request.getParameter("roleDesc");
		String tree = request.getParameter("tree");
		String tree2 = request.getParameter("tree2");
		String tree3 = request.getParameter("tree3");
		String tree4 = request.getParameter("tree4");
		List<Role> roles = null;
		if (StringUtils.isBlank(id)) {
			roles = roleService.findRoleByName(id, roleName);
		}
		if (roles != null && !roles.isEmpty()) {
			return "haveSameName";
		} else {
			Set<Resource> rSet = new HashSet<>();
			// 前台有选中节点
			if (StringUtils.isNotBlank(tree)) {
				// 获取所选节点
				String[] trees = tree.split(",");
				for (String t : trees) {
					Resource r = new Resource();
					r.setId(t);
					r = resourceService.get(t);
					// zn
					if (!rSet.contains(r)) {
						if (r != null) {
							rSet.add(r);
						}
					}
					if (r.getParentId() != null && r.getParentId() != "") {
						Resource pr = new Resource();
						pr = resourceService.get(resourceService.get(r.getId())
								.getParentId());
						if (!rSet.contains(pr)) {
							if (pr != null) {
								rSet.add(pr);
							}
						}
						if (pr.getParentId() != null && pr.getParentId() != "") {
							Resource ppr = new Resource();
							ppr = resourceService.get(resourceService.get(
									pr.getId()).getParentId());
							if (!rSet.contains(ppr)) {
								if (ppr != null) {
									rSet.add(ppr);
								}
							}
						}
					}
					// zn
				}
			}
			// 后台有选中节点
			if (StringUtils.isNotBlank(tree2)) {
				// 获取所选节点
				String[] trees = tree2.split(",");
				for (String t : trees) {
					Resource r = new Resource();
					r.setId(t);
					r = resourceService.get(t);
					// zn
					if (!rSet.contains(r)) {
						if (r != null) {
							rSet.add(r);
						}
					}
					if (r.getParentId() != null && r.getParentId() != "") {
						Resource pr = new Resource();
						pr = resourceService.get(resourceService.get(r.getId())
								.getParentId());
						if (!rSet.contains(pr)) {
							if (pr != null) {
								rSet.add(pr);
							}
						}
						if (pr.getParentId() != null && pr.getParentId() != "") {
							Resource ppr = new Resource();
							ppr = resourceService.get(resourceService.get(
									pr.getId()).getParentId());
							if (!rSet.contains(ppr)) {
								if (ppr != null) {
									rSet.add(ppr);
								}
							}
						}
					}
					// zn
				}
			}
			// APP有选中节点
			if (StringUtils.isNotBlank(tree3)) {
				// 获取所选节点
				String[] trees = tree3.split(",");
				for (String t : trees) {
					Resource r = new Resource();
					r.setId(t);
					r = resourceService.get(t);
					// zn
					if (!rSet.contains(r)) {
						if (r != null) {
							rSet.add(r);
						}
					}
					if (r.getParentId() != null && r.getParentId() != "") {
						Resource pr = new Resource();
						pr = resourceService.get(resourceService.get(r.getId())
								.getParentId());
						if (!rSet.contains(pr)) {
							if (pr != null) {
								rSet.add(pr);
							}
						}
						if (pr.getParentId() != null && pr.getParentId() != "") {
							Resource ppr = new Resource();
							ppr = resourceService.get(resourceService.get(
									pr.getId()).getParentId());
							if (!rSet.contains(ppr)) {
								if (ppr != null) {
									rSet.add(ppr);
								}
							}
						}
					}
					// zn
				}
			}
			// 页面元素有选中节点
			if (StringUtils.isNotBlank(tree4)) {
				// 获取所选节点
				String[] trees = tree4.split(",");
				for (String t : trees) {
					Resource r = new Resource();
					r.setId(t);
					r = resourceService.get(t);
					// zn
					if (!rSet.contains(r)) {
						if (r != null) {
							rSet.add(r);
						}
					}
					if (r.getParentId() != null && r.getParentId() != "") {
						Resource pr = new Resource();
						pr = resourceService.get(resourceService.get(r.getId())
								.getParentId());
						if (!rSet.contains(pr)) {
							if (pr != null) {
								rSet.add(pr);
							}
						}
						if (pr.getParentId() != null && pr.getParentId() != "") {
							Resource ppr = new Resource();
							ppr = resourceService.get(resourceService.get(
									pr.getId()).getParentId());
							if (!rSet.contains(ppr)) {
								if (ppr != null) {
									rSet.add(ppr);
								}
							}
						}
					}
					// zn
				}
			}
			// 封装角色信息
			User user = sessionInfoHolder.getLoginUser();
			Role role = new Role();
			if (StringUtils.isNotBlank(id)) {
				role = roleService.get(id);
			}
			role.setRoleName(roleName);
			role.setPictureHome(pictureHome);
			role.setRoleDesc(roleDesc);
			role.setResourceSet(rSet);
			role.setOrginfoId(user.getOrginfoId());
			roleService.saveOrUpdate(role);
			return "success";
		}
	}

	/**
	 * 
	 * @describe 角色删除
	 * @author liukai
	 * @param
	 * @date 2013-6-26
	 */
	@ApiOperation(value = "删除")
	@ApiImplicitParam(name = "id", value = "角色主键", required = true, paramType = "query", dataType = "string")
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public void deleteRole(String id) {
		roleService.delete(id);
	}

}
