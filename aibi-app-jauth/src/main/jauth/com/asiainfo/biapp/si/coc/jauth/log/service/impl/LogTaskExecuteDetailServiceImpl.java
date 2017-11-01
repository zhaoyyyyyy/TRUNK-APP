
package com.asiainfo.biapp.si.coc.jauth.log.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDao;
import com.asiainfo.biapp.si.coc.jauth.frame.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.coc.jauth.log.dao.ILogTaskExecuteDetailDao;
import com.asiainfo.biapp.si.coc.jauth.log.entity.LogTaskExecuteDetail;
import com.asiainfo.biapp.si.coc.jauth.log.service.ILogTaskExecuteDetailService;

@Service
@Transactional
public class LogTaskExecuteDetailServiceImpl extends BaseServiceImpl<LogTaskExecuteDetail,String> implements ILogTaskExecuteDetailService {

	@Autowired
	private ILogTaskExecuteDetailDao iLogTaskExecuteDetailDao;

	@Override
	protected BaseDao<LogTaskExecuteDetail, String> getBaseDao() {
		return iLogTaskExecuteDetailDao;
	}


}
