package com.asiainfo.biapp.si.coc.jauth.sysmgr.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDao;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.dao.LocTaskExeInfoDao;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.LocTaskExeInfo;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.LocTaskExeInfoService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.LocTaskExeInfoVo;

/**
 * @describe
 * @author zhangnan
 * @date 2017年10月23日 下午1:58:53
 */
@Service
@Transactional
public class LocTaskExeInfoServiceImpl extends BaseServiceImpl<LocTaskExeInfo, String> implements LocTaskExeInfoService {

	@Autowired
	private LocTaskExeInfoDao locTaskExeInfoDao;
	@Override
	protected BaseDao<LocTaskExeInfo, String> getBaseDao() {
		return locTaskExeInfoDao;
	}

	public LocTaskExeInfo findOneByName(String taskExeName) {
		return locTaskExeInfoDao.findOneByName(taskExeName);
	}
	
	public JQGridPage<LocTaskExeInfo> findLocTaskExeInfoList(JQGridPage<LocTaskExeInfo> page, LocTaskExeInfoVo locTaskExeInfoVo){
		return locTaskExeInfoDao.findLocTaskExeInfoList(page, locTaskExeInfoVo);
	}

}
