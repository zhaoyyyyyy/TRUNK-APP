/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.sysmgr.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

import com.asiainfo.biapp.si.coc.jauth.frame.Constants;
import com.asiainfo.biapp.si.coc.jauth.frame.controller.BaseController;
import com.asiainfo.biapp.si.coc.jauth.frame.json.JSONResult;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.service.BaseService;
import com.asiainfo.biapp.si.coc.jauth.frame.util.DateUtil;
import com.asiainfo.biapp.si.coc.jauth.security.auth.ajax.AjaxLoginProcessingFilter;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Resource;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Role;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.User;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.ResourceService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.UserService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.utils.SessionInfoHolder;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.ResourceVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @describe
 * @author liukai
 * @date 2013-6-25
 */
@Api(value = "资源管理")
@RequestMapping("api/resource")
@RestController
public class ResourceController extends BaseController<Resource> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ResourceController.class);

	@Autowired
	private ResourceService resourceService;
	@Autowired
	private SessionInfoHolder sessionInfoHolder;
	@Autowired
	private UserService userServer;

	@Override
	protected BaseService<Resource, String> getBaseService() {
		return resourceService;
	}

	/**
	 * 查询资源列表
	 * 
	 * @author zhougz
	 * @param
	 * @return
	 * @date 2016-6-1
	 */
	// api/resource/resourcePage/query
	@ApiOperation(value = "显示资源的列表（分页形式）")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "cols", value = "列名称", required = true, paramType = "query", dataType = "string", defaultValue = "resourceName,resourceCode,address,id"),
			@ApiImplicitParam(name = "resourceName", value = "菜单名称", required = false, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "address", value = "地址", required = false, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "resourceCode", value = "编码", required = false, paramType = "query", dataType = "string") })
	@RequestMapping(value = "/resourcePage/query", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public String list(@ModelAttribute JQGridPage<Resource> page,
			@ApiIgnore ResourceVo resourceVo, String cols) {
		// zn
		User user = sessionInfoHolder.getLoginUser();
		if (user.getIsAdmin() == 1) {
			JQGridPage<Resource> resourceList = resourceService
					.findResourceList(page, resourceVo);
			return JSONResult.page2Json(resourceList, cols);
		} else {
			Set<Resource> list = new HashSet<>();
			for (Role role : user.getRoleSet()) {
				for (Resource r : role.getResourceSet()) {
					list.add(r);
				}
			}
			resourceVo.setRoleSet(user.getRoleSet());
			JQGridPage<Resource> resourceList = resourceService
					.findResourceList(page, resourceVo);
			return JSONResult.page2Json(resourceList, cols);
		}
		// zn
	}

	/**
	 * 
	 * @describe 删除资源
	 * @author zhougz
	 * @param
	 * @date 2016-6-1
	 */
	// api/resource/delete
	@ApiOperation(value = "删除资源")
	@ApiImplicitParam(name = "id", value = "角色主键", required = true, paramType = "query", dataType = "string")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void deleteResource(String id) {
		resourceService.delete(id);
	}

	/**
	 * 保存信息
	 * 
	 * @return
	 */
	@ApiOperation(value = "保存")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "资源主键", required = false, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "parentId", value = "父资源ID", required = false, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "resourceName", value = "资源名称", required = false, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "resourceCode", value = "资源编码", required = false, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "address", value = "地址", required = false, paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "dispOrder", value = "序号", required = false, paramType = "query", dataType = "string"), })
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Resource resource) {
		User user = sessionInfoHolder.getLoginUser();
		String id = request.getParameter("resourceId");
		resource.setId(id);
		if (StringUtils.isNotBlank(id)) {
			Resource oldResource = resourceService.get(id);
			Resource resourceBy = resourceService.getOrgByOrgCode(resource
					.getResourceCode());
			if (resourceBy != null
					&& !oldResource.getResourceCode().equals(
							resource.getResourceCode())) {
				return "haveSameCode";
			}
			resourceService.saveOrUpdate(resource);
			return "success";
		}
		Resource resourceBy = resourceService.getOrgByOrgCode(resource
				.getResourceCode());
		if (resourceBy != null) {
			return "haveSameCode";
		}
		resource.setOrginfoId(user.getOrginfoId());
		try {
			resourceService.saveOrUpdate(resource);
		} catch (Exception e) {
			LOGGER.info("context", e);
		}
		return "success";
	}

	/**
	 * 
	 * @describe 进入变更资源页
	 * @author zhougz
	 * @param
	 * @date 2016-6-1
	 */
	@ApiOperation(value = "通过id得到详细信息")
	@ApiImplicitParam(name = "id", value = "资源主键", required = true, paramType = "query", dataType = "string")
	@RequestMapping(value = "/get", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Map<String, Object> initEditResource(String id) {
		Resource resource = resourceService.get(id);
		Map<String, Object> map = new HashMap<>();
		map.put("resource", resource);
		return map;
	}

	@ApiOperation(value = "通过id得到父节点信息")
	@ApiImplicitParam(name = "id", value = "资源主键", required = true, paramType = "query", dataType = "string")
	@RequestMapping(value = "/parentResource/get", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Map<String, Object> queryParent(String id) {
		Resource resource = resourceService.get(id);
		Map<String, Object> pmap = new HashMap<>();
		String ptid = resource.getParentId();
		if (null == ptid) {
			return null;
		}
		Resource parent = resourceService.get(ptid);
		pmap.put("parent", parent);
		return pmap;
	}

	/**
	 * 
	 * @describe 进入后台
	 * @author liukai
	 * @param
	 * @date 2013-7-30
	 */
	@ApiOperation(value = "进入后台")
	@RequestMapping(value = "/initBackground", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public Map<String, Object> initBackground() {
		Map<String, Object> map = new HashMap<>();
		User user = sessionInfoHolder.getLoginUser();
		String backgroundId = Constants.BACKGROUND_ID;
		List<Resource> resourceList = resourceService.getParentResourceByRole(
				user, backgroundId);
		for (int i = 0; i < resourceList.size(); i++) {
			resourceList.get(i).getChildren().clear();
			List<Resource> re1 = resourceService.getParentResourceByRole(user,
					resourceList.get(i).getId());
			for (Resource re2 : re1) {
				resourceList.get(i).getChildren().add(re2);
			}
		}
		// 删掉多余没有权限的功能
		map.put("resourceList", resourceList);
		map.put("user", user);
		map.put("date", DateUtil.formatDate(new Date(),
				DateUtil.FMT_DATE_YYYY_MM_DD_EEEE));
		return map;
	}

	/**
	 * 
	 * @describe 构建资源树
	 * @author liukai
	 * @param
	 * @return
	 * @date 2013-6-25
	 */
	@ApiOperation(value = "构建资源树")
	@ApiImplicitParam(name = "resourceId", value = "资源主键", required = true, paramType = "query", dataType = "string")
	@RequestMapping(value = "/renderOrgTree", method = RequestMethod.POST, produces = { MediaType.ALL_VALUE })
	public String renderOrgTree() {
		String resource = this.getRequest().getParameter("resourceId");
		String roleId = this.getRequest().getParameter("roleId");
		User user = userServer.get(sessionInfoHolder.getLoginId());
		List<String> list = new ArrayList<>();
		// 如果是管理员能显示所有菜单
		if (user.getIsAdmin() == 1) {
			String orgId = null;
			List<Resource> resourceList = resourceService
					.findResourceList(orgId);
			for (Resource resourc : resourceList) {
				list.add(resourc.getId());
			}
		} else {
			for (Role role : user.getRoleSet()) {
				for (Resource r : role.getResourceSet()) {
					list.add(r.getId());
				}
			}
		}
		Resource organization = resourceService.getOrgByOrgCode(resource);
		StringBuffer sb = new StringBuffer();
		return this.getTree(organization, "true", sb, list);
	}

	/**
	 * @describe 取得叶子节点
	 * @author zhougz
	 * @param
	 * 
	 * @date 2013-6-19
	 */
	private String getTree(Resource common, String selectable,
			StringBuffer htmlCon, List<String> list) {
		if (!list.isEmpty() && list.contains(common.getId())) {
			String orgType = common.getType();
			htmlCon.append("<li id='").append(common.getId())
					.append("' name='").append(common.getResourceCode())
					.append("' selectable='").append(selectable);
			if (orgType != null) {
				htmlCon.append("' orgType='").append(orgType);
			}
			htmlCon.append("'>");
			htmlCon.append("<span class='text'>");
			htmlCon.append(common.getResourceName());
			htmlCon.append("</span><ul>");
			getSubTree(common.getChildren(), selectable, htmlCon, list);
			htmlCon.append("</ul></li>");
		}
		return htmlCon.toString();
	}

	/**
	 * @describe 取得叶子节点
	 * @author zhougz
	 * @param
	 * @date 2013-6-19
	 */
	private String getSubTree(Set<Resource> set, String selectable,
			StringBuffer htmlCon, List<String> list) {
		for (Resource common : set) {
			if (common.getChildren() != null && !common.getChildren().isEmpty()) {
				getTree(common, selectable, htmlCon, list);
			} else {
				getLeaf(common, htmlCon, list);
			}
		}
		return htmlCon.toString();
	}

	private String getLeaf(Resource common, StringBuffer htmlCon,
			List<String> list) {
		if (list.contains(common.getId())) {
			String orgType = common.getType();
			htmlCon.append("<li id='").append(common.getId())
					.append("' name='").append(common.getResourceCode())
					.append("' selectable='true'");
			if (orgType != null) {
				htmlCon.append(" orgType='").append(orgType).append("'");
			}
			htmlCon.append("'>");
			htmlCon.append("<span class='text'>");
			htmlCon.append(common.getResourceName());
			htmlCon.append("</span>");
			htmlCon.append("</li>");
		}
		return htmlCon.toString();
	}

}
