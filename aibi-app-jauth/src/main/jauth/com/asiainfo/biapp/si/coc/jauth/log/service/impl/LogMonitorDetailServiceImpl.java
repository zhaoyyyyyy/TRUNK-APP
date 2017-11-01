
package com.asiainfo.biapp.si.coc.jauth.log.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDao;
import com.asiainfo.biapp.si.coc.jauth.frame.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.coc.jauth.log.dao.ILogMonitorDetailDao;
import com.asiainfo.biapp.si.coc.jauth.log.entity.LogMonitorDetail;
import com.asiainfo.biapp.si.coc.jauth.log.service.ILogMonitorDetailService;

@Service
@Transactional
public class LogMonitorDetailServiceImpl extends BaseServiceImpl<LogMonitorDetail,String> implements ILogMonitorDetailService {

	@Autowired
	private ILogMonitorDetailDao iLogMonitorDetailDao;
	
    @Override
    protected BaseDao<LogMonitorDetail, String> getBaseDao() {
        return iLogMonitorDetailDao;
    }

}
