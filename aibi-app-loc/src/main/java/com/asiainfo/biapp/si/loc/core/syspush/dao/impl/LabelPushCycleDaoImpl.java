/*
 * @(#)LabelPushCycleDaoImpl.java
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
import com.asiainfo.biapp.si.loc.core.syspush.dao.ILabelPushCycleDao;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelPushCycle;
import com.asiainfo.biapp.si.loc.core.syspush.vo.LabelPushCycleVo;

/**
 * Title : LabelPushCycleDaoImpl
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
 * <pre>1    2018年1月17日    wangrd        Created</pre>
 * <p/>
 *
 * @author  wangrd
 * @version 1.0.0.2018年1月17日
 */
@Repository
public class LabelPushCycleDaoImpl extends BaseDaoImpl<LabelPushCycle, String> implements ILabelPushCycleDao{

    /**
     * 根据条件分页查询
     *
     * @param page
     * @param LabelPushCycle
     * @return
     */
    public Page<LabelPushCycle> selectLabelPushCyclePageList(Page<LabelPushCycle> page, LabelPushCycleVo labelPushCycleVo) {
        Map<String, Object> reMap = fromBean(labelPushCycleVo);
        Map<String, Object> params = (Map<String, Object>)reMap.get("params");
        return super.findPageByHql(page, reMap.get("hql").toString(), params);
    }

    /**
     * 根据条件查询列表
     *
     * @param LabelPushCycle
     * @return
     */
    public List<LabelPushCycle> selectLabelPushCycleList(LabelPushCycleVo labelPushCycleVo) {
        Map<String, Object> reMap = fromBean(labelPushCycleVo);
        Map<String, Object> params = (Map<String, Object>)reMap.get("params");
        return super.findListByHql(reMap.get("hql").toString(), params);
    }
    
    public Map<String, Object> fromBean(LabelPushCycleVo labelPushCycleVo){
        Map<String, Object> reMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from LabelPushCycle l where 1=1 ");
        if(StringUtil.isNoneBlank(labelPushCycleVo.getRecordId())){
            hql.append("and l.recordId = :recordId ");
            params.put("recordId", labelPushCycleVo.getRecordId());
        }
        if(StringUtil.isNoneBlank(labelPushCycleVo.getCustomGroupId())){
            hql.append("and l.customGroupId = :customGroupId ");
            params.put("customGroupId", labelPushCycleVo.getCustomGroupId());
        }
        if(StringUtil.isNoneBlank(labelPushCycleVo.getSysId())){
            hql.append("and l.sysId = :sysId ");
            params.put("sysId", labelPushCycleVo.getSysId());
        }
        if(null != labelPushCycleVo.getKeyType()){
            hql.append("and l.keyType = :keyType ");
            params.put("keyType", labelPushCycleVo.getKeyType());
        }
        if(null != labelPushCycleVo.getPushCycle()){
            hql.append("and l.pushCycle = :pushCycle ");
            params.put("pushCycle", labelPushCycleVo.getPushCycle());
        }
        if(StringUtil.isNoneBlank(labelPushCycleVo.getPushUserIds())){
            hql.append("and l.pushUserIds = :pushUserIds ");
            params.put("pushUserIds", labelPushCycleVo.getPushUserIds());
        }
        if(null != labelPushCycleVo.getModifyTime()){
            hql.append("and l.modifyTime = :modifyTime ");
            params.put("modifyTime", labelPushCycleVo.getModifyTime());
        }
        if(null != labelPushCycleVo.getStatus()){
            hql.append("and l.status = :status ");
            params.put("status", labelPushCycleVo.getStatus());
        }
        reMap.put("hql", hql);
        reMap.put("params", params);
        return reMap;
    }

}
