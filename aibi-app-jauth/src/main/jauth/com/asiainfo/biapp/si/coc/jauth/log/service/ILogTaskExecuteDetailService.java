
package com.asiainfo.biapp.si.coc.jauth.log.service;

import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.service.BaseService;
import com.asiainfo.biapp.si.coc.jauth.log.entity.LogTaskExecuteDetail;
import com.asiainfo.biapp.si.coc.jauth.log.vo.LogTaskExecuteDetailVo;

public interface ILogTaskExecuteDetailService extends BaseService<LogTaskExecuteDetail, String> {
    /**
     * @describe 根据查询条件查询出用户操作日志
     * @author lilin
     * @param
     * @date 2017-11-2
     */
    public JQGridPage<LogTaskExecuteDetail> findTaskExeList(JQGridPage<LogTaskExecuteDetail> page,
            LogTaskExecuteDetailVo logTaskExecuteDetailVo);
}
