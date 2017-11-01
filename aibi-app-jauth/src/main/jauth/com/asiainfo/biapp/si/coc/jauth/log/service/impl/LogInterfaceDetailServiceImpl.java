
package com.asiainfo.biapp.si.coc.jauth.log.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDao;
import com.asiainfo.biapp.si.coc.jauth.frame.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.coc.jauth.log.dao.ILogInterfaceDetailDao;
import com.asiainfo.biapp.si.coc.jauth.log.entity.LogInterfaceDetail;
import com.asiainfo.biapp.si.coc.jauth.log.service.ILogInterfaceDetailService;

@Service
@Transactional
public class LogInterfaceDetailServiceImpl extends BaseServiceImpl<LogInterfaceDetail,String> implements ILogInterfaceDetailService {

	@Autowired
	private ILogInterfaceDetailDao iLogInterfaceDetailDao;
	
    @Override
    protected BaseDao<LogInterfaceDetail, String> getBaseDao() {
        return iLogInterfaceDetailDao;
    }

}
