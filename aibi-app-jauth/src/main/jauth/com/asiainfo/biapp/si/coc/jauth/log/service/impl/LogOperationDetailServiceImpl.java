package com.asiainfo.biapp.si.coc.jauth.log.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDao;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.coc.jauth.log.dao.ILogOperationDetailDao;
import com.asiainfo.biapp.si.coc.jauth.log.entity.LogOperationDetail;
import com.asiainfo.biapp.si.coc.jauth.log.service.ILogOperationDetailService;
import com.asiainfo.biapp.si.coc.jauth.log.vo.LogOperationDetailVo;

@Service
@Transactional
public class LogOperationDetailServiceImpl extends BaseServiceImpl<LogOperationDetail, String>
		implements ILogOperationDetailService {

	@Autowired
	private ILogOperationDetailDao iLogOperationDetailDao;

	@Override
	protected BaseDao<LogOperationDetail, String> getBaseDao() {
		return iLogOperationDetailDao;
	}

	@Override
	public JQGridPage<LogOperationDetail> findLogOperList(JQGridPage<LogOperationDetail> page,
			LogOperationDetailVo logOperationDetailVo) {
		return iLogOperationDetailDao.findLogOperList(page, logOperationDetailVo);
	}

}
