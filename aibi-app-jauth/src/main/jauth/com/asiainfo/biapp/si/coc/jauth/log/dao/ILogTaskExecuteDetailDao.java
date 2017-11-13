
package com.asiainfo.biapp.si.coc.jauth.log.dao;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDao;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.log.entity.LogTaskExecuteDetail;
import com.asiainfo.biapp.si.coc.jauth.log.vo.LogTaskExecuteDetailVo;

public interface ILogTaskExecuteDetailDao extends BaseDao<LogTaskExecuteDetail,String> {
    /**
     * 
     * Description: 根据条件查询出系统调度日志
     * @author lilin
     * @params
     * @date 2007-11-07
     */
    public JQGridPage<LogTaskExecuteDetail> findTaskExeList(JQGridPage<LogTaskExecuteDetail> page,
            LogTaskExecuteDetailVo logTaskExecuteDetailVo);

}
