
package com.asiainfo.biapp.si.coc.jauth.log.dao;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDao;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.log.entity.LogOperationDetail;
import com.asiainfo.biapp.si.coc.jauth.log.vo.LogOperationDetailVo;

public interface ILogOperationDetailDao extends BaseDao<LogOperationDetail, String> {
	/**
	 * @describe 根据条件查询出用户操作日志
	 * @author lilin
	 * @param
	 * @date 2017-11-1
	 */
	public JQGridPage<LogOperationDetail> findLogOperList(JQGridPage<LogOperationDetail> page,
			LogOperationDetailVo logOperationDetailVo);

}
