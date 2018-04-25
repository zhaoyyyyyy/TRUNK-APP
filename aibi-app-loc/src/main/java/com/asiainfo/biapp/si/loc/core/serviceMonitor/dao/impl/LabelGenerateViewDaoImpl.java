/*
 * @(#)LabelGenerateViewDaoImpl.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */
package com.asiainfo.biapp.si.loc.core.serviceMonitor.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.dao.ILabelGenerateViewDao;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.entity.LabelGenerateView;
import com.asiainfo.biapp.si.loc.core.serviceMonitor.vo.LabelGenerateViewVo;

/**
 * 
 * Title : LabelGenerateViewDaoImpl
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
 * <pre>1    2018年4月24日    shaosq        Created</pre>
 * <p/>
 *
 * @author  shaosq
 * @version 1.0.0.2018年4月24日
 */
@Repository("labelGenerateViewDaoImpl")
public class LabelGenerateViewDaoImpl extends BaseDaoImpl<LabelGenerateView, String> implements ILabelGenerateViewDao{

    @SuppressWarnings("unchecked")
    @Override
    public Page<LabelGenerateView> queryLabelGenerateViewPage(Page<LabelGenerateView> page,LabelGenerateViewVo labelGenerateViewVo, String configId) {
        Map<String, Object> reMap = fromBean(labelGenerateViewVo);
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");
        String hqls =reMap.get("hql").toString();
        hqls += "and d.labelId in(select a.labelId from LabelInfo a where a.configId= :configId) ";
        hqls += "order by d.labelName asc,d.dataDate desc ";
        params.put("configId", configId);
        return super.findPageByHql(page, hqls.toString(), params);
    }
    

    /**
     * 
     * 模糊查询对象转化
     *
     * @param labelGenerateViewVo
     * @return
     */
    public Map<String, Object> fromBean(LabelGenerateView labelGenerateViewVo) {
        Map<String, Object> reMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from LabelGenerateView d where 1=1 ");
        if (StringUtils.isNotBlank(labelGenerateViewVo.getLabelId())) {
            hql.append("and d.labelId = :labelId ");
            params.put("tableId", labelGenerateViewVo.getLabelId());
        }
        if (StringUtils.isNotBlank(labelGenerateViewVo.getLabelName())) {
            hql.append("and d.labelName LIKE :labelName ");
            params.put("labelName", "%" + labelGenerateViewVo.getLabelName() + "%");
        }
        if (null != labelGenerateViewVo.getDataStatus()) {
            hql.append("and d.dataStatus = :dataStatus ");
            params.put("dataStatus", labelGenerateViewVo.getDataStatus());
        }
        if (null != labelGenerateViewVo.getColumnName()) {
            hql.append("and d.columnName = :columnName ");
            params.put("columnName", labelGenerateViewVo.getColumnName());
        }
        if (null != labelGenerateViewVo.getTableName()) {
            hql.append("and d.tableName = :tableName ");
            params.put("tableName", labelGenerateViewVo.getTableName());
        }
        if (StringUtils.isNotBlank(labelGenerateViewVo.getDataDate())) {
            hql.append("and d.dataDate = :dataDate ");
            params.put("dataDate", labelGenerateViewVo.getDataDate());
        }
      
        if (null != labelGenerateViewVo.getTableType()) {
            hql.append("and d.tableType = :tableType ");
            params.put("tableType", labelGenerateViewVo.getTableType());
        }
        if (StringUtils.isNotBlank(labelGenerateViewVo.getExceptionDesc())) {
            hql.append("and d.exceptionDesc LIKE :exceptionDesc ");
            params.put("exceptionDesc", "%" + labelGenerateViewVo.getExceptionDesc() + "%");
        }
        reMap.put("hql", hql);
        reMap.put("params", params);
        return reMap;
    }
}
