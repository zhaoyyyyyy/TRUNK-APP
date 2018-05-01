/*
 * @(#)LabelPushReqDaoImpl.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.syspush.dao.ILabelPushReqDao;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelPushReq;
import com.asiainfo.biapp.si.loc.core.syspush.vo.LabelPushReqVo;

/**
 * Title : LabelPushReqDaoImpl
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2018年1月18日    wangrd        Created</pre>
 * <p/>
 *
 * @author  wangrd
 * @version 1.0.0.2018年1月18日
 */
@Repository
public class LabelPushReqDaoImpl extends BaseDaoImpl<LabelPushReq, String> implements ILabelPushReqDao{

    /**
     * 根据条件分页查询
     *
     * @param page
     * @param LabelPushReq
     * @return
     */
    @SuppressWarnings("unchecked")
    public Page<LabelPushReq> selectLabelPushReqPageList(Page<LabelPushReq> page, LabelPushReqVo labelPushReqVo) {
        Map<String, Object> reMap = fromBean(labelPushReqVo);
        Map<String, Object> params = (Map<String, Object>)reMap.get("params");
        return super.findPageByHql(page, reMap.get("hql").toString(), params);
    }

    /**
     * 根据条件查询列表
     *
     * @param LabelPushReq
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<LabelPushReq> selectLabelPushReqList(LabelPushReqVo labelPushReqVo) {
        Map<String, Object> reMap = fromBean(labelPushReqVo);
        Map<String, Object> params = (Map<String, Object>)reMap.get("params");
        return super.findListByHql(reMap.get("hql").toString(), params);
    }
    
    public Map<String, Object> fromBean(LabelPushReqVo labelPushReqVo){
        Map<String, Object> reMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from LabelPushReq l where 1=1 ");
        if(StringUtil.isNoneBlank(labelPushReqVo.getReqId())){
            hql.append("and l.reqId = :reqId ");
            params.put("reqId", labelPushReqVo.getReqId());
        }
        if(StringUtil.isNoneBlank(labelPushReqVo.getRecodeId())){
            hql.append("and l.recodeId = :recodeId ");
            params.put("recodeId", labelPushReqVo.getRecodeId());
        }
        if(StringUtil.isNoneBlank(labelPushReqVo.getDataDate())){
            hql.append("and l.dataDate = :dataDate ");
            params.put("dataDate", labelPushReqVo.getDataDate());
        }
        if (null != labelPushReqVo.getPushStatus()){
            hql.append("and l.pushStatus = :pushStatus ");
            params.put("pushStatus", labelPushReqVo.getPushStatus());
        }
        if (null != labelPushReqVo.getIsHasList()){
            hql.append("and l.isHasList = :isHasList ");
            params.put("isHasList", labelPushReqVo.getIsHasList());
        }
        if (null != labelPushReqVo.getStartTime()){
            hql.append("and l.startTime = :startTime ");
            params.put("startTime", labelPushReqVo.getStartTime());
        }
        if (null != labelPushReqVo.getEndTime()){
            hql.append("and l.endTime = :endTime ");
            params.put("endTime", labelPushReqVo.getEndTime());
        }
        if(StringUtil.isNoneBlank(labelPushReqVo.getListTableName())){
            hql.append("and l.listTableName = :listTableName ");
            params.put("listTableName", labelPushReqVo.getListTableName());
        }
        if(StringUtil.isNoneBlank(labelPushReqVo.getExceInfo())){
            hql.append("and l.exceInfo = :exceInfo ");
            params.put("exceInfo", labelPushReqVo.getExceInfo());
        }
        if(null != labelPushReqVo.getLabelPushCycle().getLabelInfo() && StringUtil.isNoneBlank(labelPushReqVo.getLabelPushCycle().getLabelInfo().getConfigId())){
            hql.append("and l.labelPushCycle.labelInfo.configId = :configId ");
            params.put("configId", labelPushReqVo.getLabelPushCycle().getLabelInfo().getConfigId());
        }
        reMap.put("hql", hql);
        reMap.put("params", params);
        return reMap;
    }

}
