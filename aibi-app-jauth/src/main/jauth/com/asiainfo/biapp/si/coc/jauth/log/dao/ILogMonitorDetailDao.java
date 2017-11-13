
package com.asiainfo.biapp.si.coc.jauth.log.dao;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDao;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.log.entity.LogMonitorDetail;
import com.asiainfo.biapp.si.coc.jauth.log.vo.LogMonitorDetailVo;


public interface ILogMonitorDetailDao extends BaseDao<LogMonitorDetail,String> {
    /**
     * @describe 根据条件查询出后台监控日志
     * @author lilin
     * @param
     * @date 2017-11-1
     */
  public JQGridPage<LogMonitorDetail> findLogMonitorList(JQGridPage<LogMonitorDetail> page,
          LogMonitorDetailVo logMonitorDetailVo);
}
