/*
 * @(#)TargetTableStatusDaoImp.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.source.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.core.source.dao.ITargetTableStatusDao;
import com.asiainfo.biapp.si.loc.core.source.entity.TargetTableStatus;
import com.asiainfo.biapp.si.loc.core.source.vo.TargetTableStatusVo;

/**
 * Title : TargetTableStatusDaoImp
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2017年11月15日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月15日
 */
@Repository
public class TargetTableStatusDaoImpl extends BaseDaoImpl<TargetTableStatus, String> implements ITargetTableStatusDao {

    public Page<TargetTableStatus> selectTargetTableStatusPageList(Page<TargetTableStatus> page,
            TargetTableStatusVo targetTableStatusVo) {
        Map<String, Object> reMap = fromBean(targetTableStatusVo);
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");
        return super.findPageByHql(page, reMap.get("hql").toString(), params);
    }

    public List<TargetTableStatus> selectTargetTableStatusList(TargetTableStatusVo targetTableStatusVo) {
        Map<String, Object> reMap = fromBean(targetTableStatusVo);
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");
        return super.findListByHql(reMap.get("hql").toString(), params);
    }

    public Map<String, Object> fromBean(TargetTableStatus targetTableStatusVo) {
        Map<String, Object> reMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from TargetTableStatus d where 1=1 ");
        if (StringUtils.isNotBlank(targetTableStatusVo.getTableId())) {
            hql.append("and d.tableId = :tableId ");
            params.put("tableId", targetTableStatusVo.getTableId());
        }
        if (StringUtils.isNotBlank(targetTableStatusVo.getCooTableName())) {
            hql.append("and d.cooTableName = :cooTableName ");
            params.put("cooTableName", targetTableStatusVo.getCooTableName());
        }
        if (null != targetTableStatusVo.getCooTableType()) {
            hql.append("and d.cooTableType = :cooTableType ");
            params.put("cooTableType", targetTableStatusVo.getCooTableType());
        }
        if (null != targetTableStatusVo.getManualExecution()) {
            hql.append("and d.manualExecution = :manualExecution ");
            params.put("manualExecution", targetTableStatusVo.getManualExecution());
        }
        if (null != targetTableStatusVo.getIsDoing()) {
            hql.append("and d.isDoing = :isDoing ");
            params.put("isDoing", targetTableStatusVo.getIsDoing());
        }
        if (StringUtils.isNotBlank(targetTableStatusVo.getDataDate())) {
            hql.append("and d.dataDate = :dataDate ");
            params.put("dataDate", targetTableStatusVo.getDataDate());
        }
        if (null != targetTableStatusVo.getDataStatus()) {
            hql.append("and d.dataStatus = :dataStatus ");
            params.put("dataStatus", targetTableStatusVo.getDataStatus());
        }
        if (null != targetTableStatusVo.getDataBatch()) {
            hql.append("and d.dataBatch = :dataBatch ");
            params.put("dataBatch", targetTableStatusVo.getDataBatch());
        }
        if (StringUtils.isNotBlank(targetTableStatusVo.getExceptionDesc())) {
            hql.append("and d.exceptionDesc LIKE :exceptionDesc ");
            params.put("exceptionDesc", "%" + targetTableStatusVo.getExceptionDesc() + "%");
        }
        reMap.put("hql", hql);
        reMap.put("params", params);
        return reMap;
    }
}