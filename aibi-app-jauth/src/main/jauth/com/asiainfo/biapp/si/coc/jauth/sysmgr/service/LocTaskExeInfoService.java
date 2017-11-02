package com.asiainfo.biapp.si.coc.jauth.sysmgr.service;

import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.service.BaseService;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.LocTaskExeInfo;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.vo.LocTaskExeInfoVo;

/**
 * @describe
 * @author zhangnan
 * @date 2017年10月23日 下午1:52:27
 */
public interface LocTaskExeInfoService extends BaseService<LocTaskExeInfo, String> {

	public LocTaskExeInfo findOneByName(String taskExeName);
	
	public JQGridPage<LocTaskExeInfo> findLocTaskExeInfoList(JQGridPage<LocTaskExeInfo> page, LocTaskExeInfoVo locTaskExeInfoVo);

}
