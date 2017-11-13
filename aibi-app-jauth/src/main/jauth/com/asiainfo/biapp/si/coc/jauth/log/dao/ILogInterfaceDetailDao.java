
package com.asiainfo.biapp.si.coc.jauth.log.dao;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDao;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.log.entity.LogInterfaceDetail;
import com.asiainfo.biapp.si.coc.jauth.log.vo.LogInterfaceDetailVo;


public interface ILogInterfaceDetailDao extends BaseDao<LogInterfaceDetail,String> {
    /**
     * @describe 根据条件查询出接口调用日志
     * @author lilin
     * @param
     * @date 2017-11-1
     */
	public JQGridPage<LogInterfaceDetail> findLogInterList(JQGridPage<LogInterfaceDetail> page,
			LogInterfaceDetailVo logInterfaceDetailVo);

}
