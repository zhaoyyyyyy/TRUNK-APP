/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.sysmgr.service;

import java.util.List;

import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.page.Page;
import com.asiainfo.biapp.si.coc.jauth.frame.service.BaseService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Group;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Organization;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.GroupVo;

/**
 * @describe TODO
 * @author liukai
 * @date 2013-6-27
 */
public interface GroupService extends BaseService<Group,String> {
	/**
	 * 
	 * @describe 分页查询数据范围
	 * @author liukai
	 * @param
	 * @date 2013-6-27
	 */
	public JQGridPage<Group> findGroupList(JQGridPage<Group> page, GroupVo groupVo);
	/**
	 * 
	 * @describe 根据数据范围名称查询数据范围
	 * @author liukai
	 * @param
	 * @date 2013-6-28
	 */
	public List<Group> findGroupByName(String groupName, String id);
	/**
	 * 
	 * @describe 根据Code查找组织
	 * @author liukai
	 * @param
	 * @date 2013-6-28
	 */
	public Organization findOrganizationByCode(String Code);
	
	/**
	 * 
	 * @describe 根据Code查找组织
	 * @author liukai
	 * @param
	 * @date 2013-6-28
	 */
	public Organization findOrganizationById(String Code);
	/**
	 * 
	 * @describe 根据CODE删除数据范围组织关系
	 * @author liukai
	 * @param
	 * @date 2013-8-1
	 */
	public void deleteGroupOrgByOrgCode(String code);
}
