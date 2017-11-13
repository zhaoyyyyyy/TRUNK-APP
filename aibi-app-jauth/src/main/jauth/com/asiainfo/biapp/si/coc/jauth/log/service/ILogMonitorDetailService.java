
package com.asiainfo.biapp.si.coc.jauth.log.service;

import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.service.BaseService;
import com.asiainfo.biapp.si.coc.jauth.log.entity.LogMonitorDetail;
import com.asiainfo.biapp.si.coc.jauth.log.vo.LogMonitorDetailVo;

public interface ILogMonitorDetailService extends BaseService<LogMonitorDetail,String>{
    /**
     * Description: 根据查询条件查询后台监控日志
     * @author lilin
     * @params
     * @date:2017-11-06
     */
    public JQGridPage<LogMonitorDetail> findLogMonitorList(JQGridPage<LogMonitorDetail> page,
            LogMonitorDetailVo logMonitorDetailVo);
}
