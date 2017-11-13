
package com.asiainfo.biapp.si.coc.jauth.log.dao.impl;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.criteria.From;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.util.DateUtil;
import com.asiainfo.biapp.si.coc.jauth.log.dao.ILogTaskExecuteDetailDao;
import com.asiainfo.biapp.si.coc.jauth.log.entity.LogTaskExecuteDetail;
import com.asiainfo.biapp.si.coc.jauth.log.vo.LogTaskExecuteDetailVo;

@Repository
public class LogTaskExecuteDetailDaoImpl extends BaseDaoImpl<LogTaskExecuteDetail,String> implements ILogTaskExecuteDetailDao {

    /**
     * Description 根据条件查询系统调度日志
     * @author lilin
     * @param
     * @date 2017-11-07
     */
    @Override
    public JQGridPage<LogTaskExecuteDetail> findTaskExeList(JQGridPage<LogTaskExecuteDetail> page,
            LogTaskExecuteDetailVo logTaskExecuteDetailVo) {
        //拼装hql及参数
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from LogTaskExecuteDetail l where 1=1");
        if (StringUtils.isNotBlank(logTaskExecuteDetailVo.getTaskExtId())) {
            hql.append(" and l.taskExtId = :taskExtId");
            params.put("taskExtId",logTaskExecuteDetailVo.getTaskExtId());
        }
        //执行时间
        if (StringUtils.isNotBlank(logTaskExecuteDetailVo.getStartTimeStart())) {
            hql.append(" and l.startTimeStart >= :startTimeStart");
            params.put("startTimeStart", DateUtil.parse(logTaskExecuteDetailVo.getStartTimeStart(),DateUtil.FMT_DATE_YYYYMMDDHHmmss));
        }
        if (StringUtils.isNotBlank(logTaskExecuteDetailVo.getStartTimeEnd())) {
            hql.append(" and l.startTimeEnd <= :startTimeEnd");
            params.put("startTimeEnd", DateUtil.parse(logTaskExecuteDetailVo.getStartTimeEnd(),DateUtil.FMT_DATE_YYYYMMDDHHmmss));
        }
        //执行状态
        if (StringUtils.isNotBlank(logTaskExecuteDetailVo.getStatus())) {
            hql.append(" and l.status = :status");
            params.put("status", logTaskExecuteDetailVo.getStatus());
        }
        //执行人
        if (StringUtils.isNotBlank(logTaskExecuteDetailVo.getUserId())) {
            hql.append(" and l.userId LIKE :userId");
            params.put("userId", "%"+logTaskExecuteDetailVo.getUserId()+"%");
        }
        return (JQGridPage<LogTaskExecuteDetail>) super.findPageByHql(page, hql.toString(), params);
    }

}
