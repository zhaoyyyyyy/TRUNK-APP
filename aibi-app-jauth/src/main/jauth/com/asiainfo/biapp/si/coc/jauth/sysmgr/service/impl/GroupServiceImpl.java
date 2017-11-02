/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.sysmgr.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDao;
import com.asiainfo.biapp.si.coc.jauth.frame.dao.UUIDHexGenerator;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.page.Page;
import com.asiainfo.biapp.si.coc.jauth.frame.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.GroupDao;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Group;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Organization;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.GroupService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.OrganizationService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.GroupVo;

/**
 * @author liukai
 * @date 2013-6-27
 */
@Service
@Transactional
public class GroupServiceImpl extends BaseServiceImpl<Group,String> implements
		GroupService {
	@Autowired
	private GroupDao groupDao;
	@Autowired
	private OrganizationService orgService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aibi.lsp.common.service.impl.BaseServiceImpl#getBaseDao()
	 */
	@Override
	protected BaseDao<Group,String> getBaseDao() {
		return groupDao;
	}
	/**
	 * 
	 * @describe 根据CODE删除数据范围组织关系
	 * @author liukai
	 * @param
	 * @date 2013-8-1
	 */
	public void deleteGroupOrgByOrgCode(String code){
		groupDao.deleteGroupOrgByOrgCode(code);
	}
	/**
	 * 
	 * @describe 分页查询数据范围
	 * @author liukai
	 * @param
	 * @date 2013-6-27
	 */
	public JQGridPage<Group> findGroupList(JQGridPage<Group> page, GroupVo groupVo) {
		return groupDao.findGroupList(page, groupVo);
	}

	/**
	 * 
	 * @describe 删除数据范围
	 * @author liukai
	 * @param
	 * @date 2013-6-27
	 */
	public void delete(String id) {
		groupDao.delete(id);
	}

	/**
	 * 
	 * @author liukai
	 * @param
	 * @date 2013-6-28
	 */
	public Group get(String id) {
		return groupDao.get(id);
	}

	/**
	 * 
	 * @describe 根据数据范围名称查询数据范围
	 * @author liukai
	 * @param
	 * @date 2013-6-28
	 */
	public List<Group> findGroupByName(String groupName, String id) {
		return groupDao.findGroupByName(groupName, id);
	}

	/**
	 * 保存或更新数据范围
	 */
	public void saveOrUpdate(Group group) {
		// 有ID增加创建时间 没有则增加变更时间
		if (StringUtils.isBlank(group.getId())) {
			group.setCreateTime(getSystemDate());
		} else {
			group.setUpdateTime(getSystemDate());
			group.setGroupCode(UUIDHexGenerator.getInstance().generate());
		}
		groupDao.saveOrUpdate(group);
	}

	/**
	 * 
	 * @describe 根据Code查找组织
	 * @author liukai
	 * @param
	 * @date 2013-6-28
	 */
	public Organization findOrganizationByCode(String Code) {
		return orgService.getOrgByOrgCode(Code);
	}
	
	/**
	 * 
	 * @describe 根据Code查找组织
	 * @author liukai
	 * @param
	 * @date 2013-6-28
	 */
	public Organization findOrganizationById(String Code) {
		return orgService.getOrgByOrgId(Code);
	}

}
