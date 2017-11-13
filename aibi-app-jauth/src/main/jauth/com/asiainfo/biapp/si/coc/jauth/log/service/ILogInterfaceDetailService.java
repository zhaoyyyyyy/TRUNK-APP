
package com.asiainfo.biapp.si.coc.jauth.log.service;

import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.service.BaseService;
import com.asiainfo.biapp.si.coc.jauth.log.entity.LogInterfaceDetail;
import com.asiainfo.biapp.si.coc.jauth.log.vo.LogInterfaceDetailVo;

public interface ILogInterfaceDetailService extends BaseService<LogInterfaceDetail,String>{
    /**
     * @describe 根据条件查询接口调用日志
     * @author lilin
     * @param
     * @date 2017-11-2
     */
	public JQGridPage<LogInterfaceDetail> findLogInterList(JQGridPage<LogInterfaceDetail> page,
			LogInterfaceDetailVo logInterfaceDetailVo);
}
