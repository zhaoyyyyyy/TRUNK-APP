
package com.asiainfo.biapp.si.coc.jauth.log.dao.impl;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.criteria.From;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.util.DateUtil;
import com.asiainfo.biapp.si.coc.jauth.log.dao.ILogOperationDetailDao;
import com.asiainfo.biapp.si.coc.jauth.log.entity.LogOperationDetail;
import com.asiainfo.biapp.si.coc.jauth.log.vo.LogOperationDetailVo;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.Resource;

@Repository
public class LogOperationDetailDaoImpl extends BaseDaoImpl<LogOperationDetail,String>  implements ILogOperationDetailDao {

	/**
	 * @describe 根据查询条件查询出用户操作日志
	 * @author lilin
	 * @param
	 * @date 2017-11-2
	 */
	@Override
	public JQGridPage<LogOperationDetail> findLogOperList(JQGridPage<LogOperationDetail> page,
			LogOperationDetailVo logOperationDetailVo) {
		//拼装hql及参数
		Map<String, Object> params = new HashMap<>();
		StringBuffer hql = new StringBuffer("from LogOperationDetail l where 1=1");
		//用户Id
		if (StringUtils.isNotBlank(logOperationDetailVo.getUserId())) {
			hql.append(" and l.userId LIKE :userId");
			params.put("userId", "%"+logOperationDetailVo.getUserId()+"%");
		}
		//操作
		if (StringUtils.isNotBlank(logOperationDetailVo.getSysId())) {
			hql.append(" and l.sysId LIKE :sysId");
			params.put("sysId", "%"+logOperationDetailVo.getSysId()+"%");
		}
		//时间
		if (StringUtils.isNotBlank(logOperationDetailVo.getOpTimeStart())) {
			hql.append(" and l.opTime >= :opTimeStart");
			params.put("opTimeStart", DateUtil.parse(logOperationDetailVo.getOpTimeStart(),DateUtil.FMT_DATE_YYYYMMDDHHmmss));
		}
		if (StringUtils.isNotBlank(logOperationDetailVo.getOpTimeEnd())) {
			hql.append(" and l.opTime <= :opTimeEnd");
			params.put("opTimeEnd", DateUtil.parse(logOperationDetailVo.getOpTimeEnd(),DateUtil.FMT_DATE_YYYYMMDDHHmmss));
		}
		//资源类型
		if (StringUtils.isNotBlank(logOperationDetailVo.getType())) {
			hql.append(" and resource.type LIKE :type");
			params.put("type", "%"+logOperationDetailVo.getType()+"%");
		}
		//资源名称
		if (StringUtils.isNotBlank(logOperationDetailVo.getResourceName())) {
			hql.append(" and resource.resourceName LIKE :resourceName");
			params.put("resourceName", "%"+logOperationDetailVo.getResourceName()+"%");
		}
		return (JQGridPage<LogOperationDetail>) super.findPageByHql(page, hql.toString(), params);
	}

}
