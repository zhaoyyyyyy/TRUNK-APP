/*
 * @(#)DimTargetTableStatusDaoImp.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.sourceconfig.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.core.label.sourceconfig.dao.IDimTargetTableStatusDao;
import com.asiainfo.biapp.si.loc.core.label.sourceconfig.entity.DimTargetTableStatus;
import com.asiainfo.biapp.si.loc.core.label.sourceconfig.vo.DimTargetTableStatusVo;

/**
 * Title : DimTargetTableStatusDaoImp
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
public class DimTargetTableStatusDaoImpl extends BaseDaoImpl<DimTargetTableStatus, String> implements
        IDimTargetTableStatusDao {

    /**
     * 根据条件分页查询
     *
     * @param page
     * @param dimTargetTableStatusVo
     * @return
     */
    public Page<DimTargetTableStatus> findDimTargetTableStatusPageList(Page<DimTargetTableStatus> page,
            DimTargetTableStatusVo dimTargetTableStatusVo) {
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from DimTargetTableStatus d where 1=1 ");
        if (StringUtils.isNotBlank(dimTargetTableStatusVo.getLabelId())) {
            hql.append("and d.labelId = :labelId ");
            params.put("labelId", dimTargetTableStatusVo.getLabelId());
        }
        if (StringUtils.isNotBlank(dimTargetTableStatusVo.getCooTableName())) {
            hql.append("and d.cooTableName = :cooTableName ");
            params.put("cooTableName", dimTargetTableStatusVo.getCooTableName());
        }
        if (null != dimTargetTableStatusVo.getCooTableType()) {
            hql.append("and d.cooTableType = :cooTableType ");
            params.put("cooTableType", dimTargetTableStatusVo.getCooTableType());
        }
        if (null != dimTargetTableStatusVo.getManualExecution()) {
            hql.append("and d.manualExecution = :manualExecution ");
            params.put("manualExecution", dimTargetTableStatusVo.getManualExecution());
        }
        if (null != dimTargetTableStatusVo.getIsDoing()) {
            hql.append("and d.isDoing = :isDoing ");
            params.put("isDoing", dimTargetTableStatusVo.getIsDoing());
        }
        if (StringUtils.isNotBlank(dimTargetTableStatusVo.getDataDate())) {
            hql.append("and d.dataDate = :dataDate ");
            params.put("dataDate", dimTargetTableStatusVo.getDataDate());
        }
        if (null != dimTargetTableStatusVo.getDataStatus()) {
            hql.append("and d.dataStatus = :dataStatus ");
            params.put("dataStatus", dimTargetTableStatusVo.getDataStatus());
        }
        if (null != dimTargetTableStatusVo.getDataBatch()) {
            hql.append("and d.dataBatch = :dataBatch ");
            params.put("dataBatch", dimTargetTableStatusVo.getDataBatch());
        }
        if (StringUtils.isNotBlank(dimTargetTableStatusVo.getExceptionDesc())) {
            hql.append("and d.exceptionDesc LIKE :exceptionDesc ");
            params.put("exceptionDesc", "%" + dimTargetTableStatusVo.getExceptionDesc() + "%");
        }
        return super.findPageByHql(page, hql.toString(), params);
    }

    /**
     * 根据条件查询列表
     *
     * @param dimTargetTableStatusVo
     * @return
     */
    public List<DimTargetTableStatus> findDimTargetTableStatusList(DimTargetTableStatusVo dimTargetTableStatusVo) {
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from DimTargetTableStatus d where 1=1 ");
        if (StringUtils.isNotBlank(dimTargetTableStatusVo.getLabelId())) {
            hql.append("and d.labelId = :labelId ");
            params.put("labelId", dimTargetTableStatusVo.getLabelId());
        }
        if (StringUtils.isNotBlank(dimTargetTableStatusVo.getCooTableName())) {
            hql.append("and d.cooTableName = :cooTableName ");
            params.put("cooTableName", dimTargetTableStatusVo.getCooTableName());
        }
        if (null != dimTargetTableStatusVo.getCooTableType()) {
            hql.append("and d.cooTableType = :cooTableType ");
            params.put("cooTableType", dimTargetTableStatusVo.getCooTableType());
        }
        if (null != dimTargetTableStatusVo.getManualExecution()) {
            hql.append("and d.manualExecution = :manualExecution ");
            params.put("manualExecution", dimTargetTableStatusVo.getManualExecution());
        }
        if (null != dimTargetTableStatusVo.getIsDoing()) {
            hql.append("and d.isDoing = :isDoing ");
            params.put("isDoing", dimTargetTableStatusVo.getIsDoing());
        }
        if (StringUtils.isNotBlank(dimTargetTableStatusVo.getDataDate())) {
            hql.append("and d.dataDate = :dataDate ");
            params.put("dataDate", dimTargetTableStatusVo.getDataDate());
        }
        if (null != dimTargetTableStatusVo.getDataStatus()) {
            hql.append("and d.dataStatus = :dataStatus ");
            params.put("dataStatus", dimTargetTableStatusVo.getDataStatus());
        }
        if (null != dimTargetTableStatusVo.getDataBatch()) {
            hql.append("and d.dataBatch = :dataBatch ");
            params.put("dataBatch", dimTargetTableStatusVo.getDataBatch());
        }
        if (StringUtils.isNotBlank(dimTargetTableStatusVo.getExceptionDesc())) {
            hql.append("and d.exceptionDesc LIKE :exceptionDesc ");
            params.put("exceptionDesc", "%" + dimTargetTableStatusVo.getExceptionDesc() + "%");
        }
        return super.findListByHql(hql.toString(), params);
    }
}
