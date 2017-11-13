
package com.asiainfo.biapp.si.coc.jauth.log.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.coc.jauth.frame.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.util.DateUtil;
import com.asiainfo.biapp.si.coc.jauth.log.dao.ILogMonitorDetailDao;
import com.asiainfo.biapp.si.coc.jauth.log.entity.LogMonitorDetail;
import com.asiainfo.biapp.si.coc.jauth.log.vo.LogMonitorDetailVo;

@Repository
public class LogMonitorDetailDaoImpl extends BaseDaoImpl<LogMonitorDetail,String> implements ILogMonitorDetailDao {
    /**
     * @describe 根据条件查询出用户操作日志
     * @author lilin
     * @param
     * @date 2017-11-1
     */
    @Override
    public JQGridPage<LogMonitorDetail> findLogMonitorList(JQGridPage<LogMonitorDetail> page,
            LogMonitorDetailVo logMonitorDetailVo) {
        //拼装hql及参数
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from LogMonitorDetail l where 1=1");
        //用户名
        if (StringUtils.isNotBlank(logMonitorDetailVo.getUserId())) {
            hql.append(" and l.userId like :userId");
            params.put("userId", "%"+logMonitorDetailVo.getUserId()+"%");
        }
        //时间
        if (StringUtils.isNotBlank(logMonitorDetailVo.opTimeStart)) {
            hql.append(" and l.opTime >= :opTimeStart");
            params.put("opTimeStart", DateUtil.parse(logMonitorDetailVo.getOpTimeStart(),DateUtil.FMT_DATE_YYYYMMDDHHmmss));
        }
        if (StringUtils.isNotBlank(logMonitorDetailVo.opTimeEnd)) {
            hql.append(" and l.opTime <= :opTimeEnd");
            params.put("opTimeEnd", DateUtil.parse(logMonitorDetailVo.getOpTimeEnd(),DateUtil.FMT_DATE_YYYYMMDDHHmmss));
        }
        //level
        if (StringUtils.isNotBlank(logMonitorDetailVo.getLevelId())) {
            hql.append(" and l.levelId = :levelId");
            params.put("levelId", logMonitorDetailVo.getLevelId());
        }
        //message
        if (StringUtils.isNotBlank(logMonitorDetailVo.getErrorMsg())) {
            hql.append(" and l.errorMsg like :errorMsg");
            params.put("errorMsg", "%"+logMonitorDetailVo.getErrorMsg()+"%");
        }
        return (JQGridPage<LogMonitorDetail>) super.findPageByHql(page, hql.toString(), params);
    }

}
