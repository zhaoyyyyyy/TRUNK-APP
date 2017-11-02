package com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.LocTaskExeInfoDao;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.LocTaskExeInfo;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.LocTaskExeInfoVo;

/**
 * @describe
 * @author zhangnan
 * @date 2017年10月23日 上午11:39:27
 */
@Repository
public class LocTaskExeInfoDaoImpl extends BaseDaoImpl<LocTaskExeInfo, String> implements LocTaskExeInfoDao {
	
	public JQGridPage<LocTaskExeInfo> findLocTaskExeInfoList(JQGridPage<LocTaskExeInfo> page, LocTaskExeInfoVo locTaskExeInfoVo){
		Map<String, Object> params = new HashMap<>();
		StringBuffer hql = new StringBuffer("from LocTaskExeInfo a where 1=1 ");
		if (StringUtils.isNotBlank(locTaskExeInfoVo.getParentExeId())) {
			hql.append("and a.parentExeId = :parentExeId ");
			params.put("parentExeId", locTaskExeInfoVo.getParentExeId());
		}
		if (StringUtils.isNotBlank(locTaskExeInfoVo.getTaskExeName())) {
			hql.append("and a.taskExeName LIKE:taskExeName ");
			params.put("taskExeName", "%"+locTaskExeInfoVo.getTaskExeName()+"%");
		}
		if (StringUtils.isNotBlank(locTaskExeInfoVo.getExeStatus())) {
			hql.append("and a.exeStatus = :exeStatus ");
			params.put("exeStatus", locTaskExeInfoVo.getExeStatus());
		}
		if(StringUtils.isNotBlank(page.getSortCol())){
			hql.append(" order by a."+page.getSortCol()+" "+page.getSortOrder());
		}else{
			hql.append(" order by a.taskExeId desc");
		}
		return (JQGridPage<LocTaskExeInfo>) super.findPageByHql(page, hql.toString(), params);
	}
	
	public LocTaskExeInfo findOneByName(String taskExeName) {
		return this.findOneByHql("from LocTaskExeInfo a where a.taskExeName = ?0 ", taskExeName);
	}

}
