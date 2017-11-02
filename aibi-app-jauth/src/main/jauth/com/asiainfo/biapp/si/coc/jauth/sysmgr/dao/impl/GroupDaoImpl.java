/**
 * 
 */
package com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.util.DateUtil;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.GroupDao;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Group;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.GroupVo;

/**
 * @author liukai
 * @date 2013-6-27
 */
@Repository
public class GroupDaoImpl extends BaseDaoImpl<Group,String> implements GroupDao {

	/**
	 * 
	 * @describe 分页查询数据范围
	 * @author liukai
	 * @param
	 * @date 2013-6-27
	 */
	public JQGridPage<Group> findGroupList(JQGridPage<Group> page, GroupVo groupVo) {
		Map<String, Object> params = new HashMap<>();
		StringBuffer hql = new StringBuffer("from Group g where 1=1");
		// 组织
		if (StringUtils.isNotBlank(groupVo.getOrginfoId())) {
			hql.append(" and g.orginfoId = :orginfoId");
			params.put("orginfoId", groupVo.getOrginfoId());
		}
		// 数据范围名称
		if (StringUtils.isNotBlank(groupVo.getGroupName())) {
			hql.append(" and g.groupName LIKE :groupName");
			params.put("groupName", "%" + groupVo.getGroupName() + "%");
		}
		// 创建时间
		if (StringUtils.isNotBlank(groupVo.getCreateTimeStart())) {
			hql.append(" and g.createTime >= :createTimeStart");
			params.put("createTimeStart", DateUtil.parse(groupVo.getCreateTimeStart(), DateUtil.FMT_DATE_YYYYMMDDHHmmss));
		}
		if (StringUtils.isNotBlank(groupVo.getCreateTimeEnd())) {
			params.put("createTimeEnd", DateUtil.parse(groupVo.getCreateTimeEnd(), DateUtil.FMT_DATE_YYYYMMDDHHmmss));
			hql.append(" and g.createTime <= :createTimeEnd ");
		}
		if(StringUtils.isNotBlank(page.getSortCol())){
			hql.append(" order by g."+page.getSortCol()+" "+page.getSortOrder());
		}else{
			hql.append(" order by g.createTime desc");
		}
		return (JQGridPage<Group>) super.findPageByHql(page, hql.toString(),
				params);
	}

	/**
	 * 
	 * @describe 删除数据范围
	 * @author liukai
	 * @param
	 * @date 2013-6-27
	 */
	public void delete(String id) {
		super.delete(id);
	}

	/**
	 * 
	 * @describe 根据CODE删除数据范围组织关系
	 * @author liukai
	 * @param
	 * @date 2013-8-1
	 */
	public void deleteGroupOrgByOrgCode(String code) {
		String sql = "DELETE FROM T_SYS_GROUPORG G WHERE G.ORG_CODE=?";
		super.excuteSql(sql, code);
	}

	/**
	 * 
	 * @describe 获取数据范围
	 * @author liukai
	 * @param
	 * @date 2013-6-28
	 */
	public Group get(String id) {
		return super.get(id);
	}

	/**
	 * 
	 * @describe 根据数据范围名称查询数据范围
	 * @author liukai
	 * @param
	 * @date 2013-6-28
	 */
	public List<Group> findGroupByName(String groupName, String id) {
		// 拼装hql 及参数
		Map<String, Object> params = new HashMap<>();
		StringBuffer hql = new StringBuffer(
				"from Group g where groupName = :groupName");
		params.put("groupName", groupName);
		// 有ID则是编辑
		if (StringUtils.isNotBlank(id)) {
			hql.append(" and id != :id");
			params.put("id", id);
		}
		return super.findListByHql(hql.toString(), params);
	}

	/**
	 * 保存或更新数据范围
	 */
	public void saveOrUpdate(Group group) {
		super.saveOrUpdate(group);
	}
}
