/*
 * @(#)LabelStatusDaoImpl.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.label.dao.ILabelStatusDao;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelStatus;

/**
 * Title : LabelStatusDaoImpl
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2017年11月20日     wangrd        Created</pre>
 * <p/>
 *
 * @author   wangrd
 * @version 1.0.0.2017年11月20日
 */
@Repository
public class LabelStatusDaoImpl extends BaseDaoImpl<LabelStatus, String> implements ILabelStatusDao{

    public Page<LabelStatus> selectLabelStatusPageList(Page<LabelStatus> page, LabelStatus labelStatusVo){
        Map<String, Object> reMap = fromBean(labelStatusVo);
        Map<String, Object> params = (Map<String, Object>)reMap.get("params");
        return super.findPageByHql(page, reMap.get("hql").toString(), params);
    }

    public List<LabelStatus> selectLabelStatusList(LabelStatus labelStatusVo){
        Map<String, Object> reMap = fromBean(labelStatusVo);
        Map<String, Object> params = (Map<String, Object>)reMap.get("params");
        return super.findListByHql(reMap.get("hql").toString(), params);
    }

    public Map<String, Object> fromBean(LabelStatus labelStatusVo){
        Map<String, Object> reMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from LabelStatus l where 1=1 ");
        if (StringUtil.isNotBlank(labelStatusVo.getLabelId()) && StringUtil.isNoneBlank(labelStatusVo.getDataDate())){
            hql.append("and l.labelId = :labelId and l.dataDate = :dataDate ");
            params.put("labelId", labelStatusVo.getLabelId());
            params.put("dataDate", labelStatusVo.getDataDate());
        }
        if(null != labelStatusVo.getDataStatus()){
            hql.append("and l.dataStatus = :dataStatus ");
            params.put("dataStatus", labelStatusVo.getDataStatus());
        }
        if(null != labelStatusVo.getDataInsertTime()) {
            hql.append("and l.dataInsertTime = :dataInsertTime ");
            params.put("dataInsertTime", labelStatusVo.getDataInsertTime());
        }
        if(StringUtil.isNoneBlank(labelStatusVo.getExceptionDesc())){
            hql.append("and l.exceptionDesc = :exceptionDesc ");
            params.put("exceptionDesc", labelStatusVo.getExceptionDesc());
        }
        if(null != labelStatusVo.getLabelInfo() && StringUtil.isNoneBlank(labelStatusVo.getLabelInfo().getConfigId())){
            hql.append("and l.labelInfo.configId = :configId ");
            params.put("configId", labelStatusVo.getLabelInfo().getConfigId());
        }
        if(null !=  labelStatusVo.getLabelInfo() && StringUtil.isNotBlank(labelStatusVo.getLabelInfo().getLabelName())){
            hql.append("and l.labelInfo.labelName LIKE :configId ");
            params.put("labelName", "%" +labelStatusVo.getLabelInfo().getLabelName()+ "%");
        }
        reMap.put("hql", hql);
        reMap.put("params",params );
        return reMap;
    }
}
