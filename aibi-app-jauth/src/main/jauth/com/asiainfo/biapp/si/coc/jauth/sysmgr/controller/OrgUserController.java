package com.asiainfo.biapp.si.coc.jauth.sysmgr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.coc.jauth.frame.Constants;
import com.asiainfo.biapp.si.coc.jauth.frame.controller.BaseController;
import com.asiainfo.biapp.si.coc.jauth.frame.json.JSONResult;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.service.BaseService;
import com.asiainfo.biapp.si.coc.jauth.frame.util.StringUtil;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.OrgUser;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Organization;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.User;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.OrgUserService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.OrganizationService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.UserService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.OrgUserVo;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.UserVo;

import io.swagger.annotations.Api;

/**
 * @describe 组织成员管理
 * @author ljs
 * @date 2013-6-28
 */

@Api(value = "组织成员接口")
@RequestMapping("api/orguser")
@RestController
public class OrgUserController extends BaseController<OrgUser>{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private OrgUserService orgUserService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private UserService userService;
	private OrgUser orgUser;
	
	private List<OrgUser> listOrgUser;
	
	private Organization organization;
	
	private Integer count;
	
	@Override
	protected BaseService<OrgUser,String> getBaseService() {
		return orgUserService;
	}
	
	/** 注入 OrgUserVo */
	private OrgUserVo orgUserVo;
	public OrgUserVo getOrgUserVo() {
		return orgUserVo;
	}
	public void setOrgUserVo(OrgUserVo orgUserVo) {
		this.orgUserVo = orgUserVo;
	}

	/**
	 * 注入用户条件
	 */
	private UserVo userVo;
	public UserVo getUserVo() {
		return userVo;
	}
	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}
	/***
	 * @describe 查询组织成员列表
	 * @author XieGaosong
	 * @date 2013-6-28
	 */
	public void findUserPageByParams(){
		this.renderText(JSONResult.page2Json(orgUserService.findUserPageByParams(this.page, orgUserVo), this.cols));
	}
	
	/**
	 * @describe Excel表格导出
	 * @author XieGaosong
	 * @param
	 * @date 2013-7-4
	 */
	public void Out_Excel(){
		orgUserService.saveToExcel(orgUserVo);
	}
	
	/**
	 * @describe to 班级成员批量预入
	 * @author XieGaosong
	 * @date 2013-7-4
	 */
	public String toBatchAdvance(){
		return "batch_advance";
	}
	
	/**
	 * 转到组织用户管理页面
	 * @return
	 */
	public String toUserMgr(){
		String orgCode = this.getRequest().getParameter("orgCode");
		if(!StringUtil.isEmpty(orgCode)){
			this.getRequest().setAttribute("orgCode", orgCode);
		}
		return "circle";
	}
	
	/**
	 * 
	 * @describe 转到组织用户管理页面
	 * @author ljs
	 * @date 2013-9-30
	 * @return
	 */
	public String toOrgUserMgr(){
		String orgCode = this.getRequest().getParameter("orgCode");
		if(!StringUtil.isEmpty(orgCode)){
			this.getRequest().setAttribute("orgCode", orgCode);
		}
		return "org_user_mgr";
	}
	
	/***
	 * @describe 查询班级预入成员
	 * @author XieGaosong
	 * @return list
	 * @date 2013-6-28
	 */
	public String toMemberAdvance(){
		organization=organizationService.getOrgByOrgCode(orgUserVo.getOrgCode());
		listOrgUser=this.orgUserService.findOrgUserList(orgUserVo);
		return "class_member_advance";
	}
	
	public void updateAdvance(){
		if(StringUtil.isEmpty(orgUser.getId())){
			this.orgUserService.save(orgUser);
		}else{
			this.orgUserService.updateAdvance(orgUser);
		}
	}
	
	/***
	 * @describe 跳转到成员管理页面组织成员
	 * @author XieGaosong
	 * @date 2013-7-1
	 */
	public String toMemberManage(){
		return "class_member_manage";
	}
	
	
	/***
	 * @describe 查询正式成员
	 * @author XieGaosong
	 * @date 2013-7-1
	 */
	public void findOfficialMemberPageByParams(){
		JQGridPage<OrgUser> page =orgUserService.findOfficialMemberPageByParams(this.page, orgUserVo);
		this.renderText(JSONResult.page2Json(page, this.cols));
	}
	
	/**
	 * @describe 跳转到嘉宾页面
	 * @author XieGaosong
	 * @param
	 * @date 2013-7-8
	 */
	public String toHonoredManage(){
		return "class_honored_manage";
	}
	
	/**
	 * @describe 删除组织成员
	 * @author XieGaosong
	 * @param
	 * @date 2013-7-3
	 */
	public void deleteAdvance(){
		this.orgUserService.delete(orgUser.getId());
		
	}
	
	
	/**
	 * @describe 将人员T出
	 * @author XieGaosong
	 * @param
	 * @date 2013-7-3
	 */
	public void To_Kicked_Out(){
		String id = this.getRequest().getParameter("id");
		this.orgUserService.updateKickedOut(id);
		this.renderText("");
	}
	
	/**
	 * @describe 踢出
	 * @author ljs
	 * @date 2013-8-26
	 */
	public void shotOff(){
		String id = this.getRequest().getParameter("id");
		orgUserService.deleteShotOff(id);
	}
	
	public OrgUser getOrgUser() {
		return orgUser;
	}
	public void setOrgUser(OrgUser orgUser) {
		this.orgUser = orgUser;
	}
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	public List<OrgUser> getListOrgUser() {
		return listOrgUser;
	}
	public void setListOrgUser(List<OrgUser> listOrgUser) {
		this.listOrgUser = listOrgUser;
	}
	
	/**
	 * 改变角色
	 */
	public void updateType(){
		String id = this.getRequest().getParameter("id");
		String orgType = this.getRequest().getParameter("orgType");
		String type = orgUserService.updateType(id).toString();
		this.renderText(type);
	}
	
	/**
	 * 改变角色
	 */
	public void updateRole(){
		String id = this.getRequest().getParameter("id");
		orgUser=orgUserService.get(id);
		if(Constants.ADMIN.equals(orgUser.getIsAdmin())){
			orgUser.setIsAdmin("");
		}else{
			orgUser.setIsAdmin(Constants.ADMIN);
		}
		orgUserService.update(orgUser);
	}
	
	
	
	
	/**
	 * 改变状态
	 * @return
	 */
	public void updateStatus(){
		String id = this.getRequest().getParameter("id");
		String status = orgUserService.updateStatus(id).toString();
		this.renderText(status);
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	/**
	 * 
	 * @describe 转到增加成员页面
	 * @author ljs
	 * @date 2013-9-30
	 * @return
	 */
	public String toAddUser(){
		String orgCode = this.getRequest().getParameter("orgCode");
		this.getRequest().setAttribute("orgCode", orgCode);
		return "user_add";
	}
	
	/**
	 * 
	 * @describe 根据条件增加成员
	 * @author ljs
	 * @date 2013-9-30
	 */
	public void addUserByParams(){
		String orgCode = this.getRequest().getParameter("orgCode");
		List<User> users = userService.listUser(userVo);
		for (User user : users) {
			orgUserService.createOrgUser(user.getId(), orgCode, Constants.ORGUSER_TYPE.USER);
		}
	}
	
	/**
	 * 
	 * @describe 根据选择增加组织成员
	 * @author ljs
	 * @date 2013-9-30
	 */
	public void addUserByList(){
		String orgCode = this.getRequest().getParameter("orgCode");
		String idsString = this.getRequest().getParameter("ids");
		String[] ids = idsString.split(",");
		for (String id : ids) {
			orgUserService.createOrgUser(id, orgCode, Constants.ORGUSER_TYPE.USER);
		}
	}
	
	/***
	 * @describe 查询班级预入成员
	 * @author XieGaosong
	 * @return list
	 * @date 2013-6-28
	 */
	public String findMemberPhoto(){
		organization=organizationService.getOrgByOrgCode(orgUserVo.getOrgCode());
		listOrgUser=this.orgUserService.findOrgUserList(orgUserVo);
		return "class_member_photo_index";
	}
	
}
