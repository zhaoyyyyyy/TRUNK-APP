
package com.asiainfo.biapp.si.coc.jauth.log.service;

import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.service.BaseService;
import com.asiainfo.biapp.si.coc.jauth.log.entity.LogOperationDetail;
import com.asiainfo.biapp.si.coc.jauth.log.vo.LogOperationDetailVo;

public interface ILogOperationDetailService extends BaseService<LogOperationDetail,String>{
	/**
	 * 
	 * @describe 根据查询条件查询出用户操作日志
	 * @author lilin
	 * @param
	 * @date 2017-11-2
	 */
	public JQGridPage<LogOperationDetail> findLogOperList(JQGridPage<LogOperationDetail> page,
			LogOperationDetailVo logOperationDetailVo);
}
