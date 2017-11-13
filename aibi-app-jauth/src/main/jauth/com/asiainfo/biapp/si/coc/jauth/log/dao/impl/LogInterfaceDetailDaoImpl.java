
package com.asiainfo.biapp.si.coc.jauth.log.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.util.DateUtil;
import com.asiainfo.biapp.si.coc.jauth.log.dao.ILogInterfaceDetailDao;
import com.asiainfo.biapp.si.coc.jauth.log.entity.LogInterfaceDetail;
import com.asiainfo.biapp.si.coc.jauth.log.vo.LogInterfaceDetailVo;

@Repository
public class LogInterfaceDetailDaoImpl extends BaseDaoImpl<LogInterfaceDetail,String> implements ILogInterfaceDetailDao {

	/**
	 * @describe 根据查询条件查询接口调用日志
	 * @author lilin
	 * @param
	 * @date 2017-11-2
	 */
	@Override
	public JQGridPage<LogInterfaceDetail> findLogInterList(JQGridPage<LogInterfaceDetail> page,
			LogInterfaceDetailVo logInterfaceDetailVo) {
		//拼装hql及参数
		Map<String, Object> params = new HashMap<>();
		StringBuffer hql=new StringBuffer("from LogInterfaceDetail l where 1=1");
		//用户Id
		if (StringUtils.isNotBlank(logInterfaceDetailVo.getUserId())) {
			hql.append(" and l.userId LIKE :userId");
			params.put("userId", "%"+logInterfaceDetailVo.getUserId()+"%");
		}
		//时间
		if (StringUtils.isNotBlank(logInterfaceDetailVo.getOpTimeStart())) {
			hql.append(" and l.opTime >= :opTimeStart");
			params.put("opTimeStart", DateUtil.parse(logInterfaceDetailVo.getOpTimeStart(),DateUtil.FMT_DATE_YYYYMMDDHHmmss));
		}
		if (StringUtils.isNotBlank(logInterfaceDetailVo.getOpTimeEnd())) {
			hql.append(" and l.opTime <= :opTimeEnd");
			params.put("opTimeEnd", DateUtil.parse(logInterfaceDetailVo.getOpTimeEnd(),DateUtil.FMT_DATE_YYYYMMDDHHmmss));
		}
		//接口名称
		if (StringUtils.isNotBlank(logInterfaceDetailVo.getInterfaceName())) {
			hql.append(" and l.interfaceName LIKE :interfaceName");
			params.put("interfaceName", "%"+logInterfaceDetailVo.getInterfaceName()+"%");
		}
		//接口路径
		if (StringUtils.isNotBlank(logInterfaceDetailVo.getInterfaceUrl())) {
			hql.append(" and l.interfaceUrl LIKE :interfaceUrl");
			params.put("interfaceUrl", "%"+logInterfaceDetailVo.getInterfaceUrl()+"%");
		}
		//输入参数
		if (StringUtils.isNotBlank(logInterfaceDetailVo.getInputParams())) {
            hql.append(" and l.inputParams LIKE :inputParams");
            params.put("inputParams", "%"+logInterfaceDetailVo.getInputParams()+"%");
        }
		//输出参数
		if (StringUtils.isNotBlank(logInterfaceDetailVo.getOutputParams())) {
            hql.append(" and l.outputParams LIKE :outputParams");
            params.put("outputParams", "%"+logInterfaceDetailVo.getOutputParams()+"%");
        }
		return (JQGridPage<LogInterfaceDetail>) super.findPageByHql(page, hql.toString(), params);
	}

}
