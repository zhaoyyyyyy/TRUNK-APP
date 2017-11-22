/*
 * @(#)SourceTableInfoDaoImpl.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.sourceconfig.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.label.sourceconfig.dao.ISourceTableInfoDao;
import com.asiainfo.biapp.si.loc.core.label.sourceconfig.entity.SourceTableInfo;
import com.asiainfo.biapp.si.loc.core.label.sourceconfig.vo.SourceTableInfoVo;

/**
 * Title : SourceTableInfoDaoImpl
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
public class SourceTableInfoDaoImpl extends BaseDaoImpl<SourceTableInfo, String> implements ISourceTableInfoDao {

    /**
     * 根据条件分页查询
     *
     * @param page
     * @param sourceTableInfoVo
     * @return
     */
    public Page<SourceTableInfo> findSourceTableInfoPageList(Page<SourceTableInfo> page,
            SourceTableInfoVo sourceTableInfoVo) {
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from SourceTableInfo s where 1=1 ");
        if (StringUtil.isNotBlank(sourceTableInfoVo.getSourceTableId())) {
            hql.append("and s.sourceTableId = :sourceTableId ");
            params.put("sourceTableId", sourceTableInfoVo.getSourceTableId());
        }
        if (StringUtil.isNotBlank(sourceTableInfoVo.getConfigId())) {
            hql.append("and s.configId = :configId ");
            params.put("configId", sourceTableInfoVo.getConfigId());
        }
        if (StringUtil.isNotBlank(sourceTableInfoVo.getSourceFileName())) {
            hql.append("and s.sourceFileName = :sourceFileName ");
            params.put("sourceFileName", sourceTableInfoVo.getSourceFileName());
        }
        if (StringUtil.isNotBlank(sourceTableInfoVo.getSourceTableName())) {
            hql.append("and s.sourceTableName = :sourceTableName ");
            params.put("sourceTableName", sourceTableInfoVo.getSourceTableName());
        }
        if (StringUtil.isNotBlank(sourceTableInfoVo.getTableSchema())) {
            hql.append("and s.tableSchema = :tableSchema ");
            params.put("tableSchema", sourceTableInfoVo.getTableSchema());
        }
        if (StringUtil.isNotBlank(sourceTableInfoVo.getSourceTableCnName())) {
            hql.append("and s.sourceTableCnName = :sourceTableCnName ");
            params.put("sourceTableCnName", sourceTableInfoVo.getSourceTableCnName());
        }
        if (null != sourceTableInfoVo.getSourceTableType()) {
            hql.append("and s.sourceTableType = :sourceTableType ");
            params.put("sourceTableType", sourceTableInfoVo.getSourceTableType());
        }
        if (StringUtil.isNotBlank(sourceTableInfoVo.getTableSuffix())) {
            hql.append("and s.tableSuffix = :tableSuffix ");
            params.put("tableSuffix", sourceTableInfoVo.getTableSuffix());
        }
        if (null != sourceTableInfoVo.getReadCycle()) {
            hql.append("and s.readCycle = :readCycle ");
            params.put("readCycle", sourceTableInfoVo.getReadCycle());
        }
        if (null != sourceTableInfoVo.getKeyType()) {
            hql.append("and s.keyType = :keyType ");
            params.put("keyType", sourceTableInfoVo.getKeyType());
        }
        if (null != sourceTableInfoVo.getIdType()) {
            hql.append("and s.idType = :idType ");
            params.put("idType", sourceTableInfoVo.getIdType());
        }
        if (StringUtil.isNotBlank(sourceTableInfoVo.getIdColumn())) {
            hql.append("and s.idColumn = :idColumn ");
            params.put("idColumn", sourceTableInfoVo.getIdColumn());
        }
        if (StringUtil.isNotBlank(sourceTableInfoVo.getIdDataType())) {
            hql.append("and s.idDataType = :idDataType ");
            params.put("idDataType", sourceTableInfoVo.getIdDataType());
        }
        if (StringUtil.isNotBlank(sourceTableInfoVo.getColumnCaliber())) {
            hql.append("and s.columnCaliber = :columnCaliber ");
            params.put("columnCaliber", sourceTableInfoVo.getColumnCaliber());
        }
        if (StringUtil.isNotBlank(sourceTableInfoVo.getDataSourceId())) {
            hql.append("and s.dataSourceId = :dataSourceId ");
            params.put("dataSourceId", sourceTableInfoVo.getDataSourceId());
        }
        if (null != sourceTableInfoVo.getDataSourceType()) {
            hql.append("and s.dataSourceType = :dataSourceType ");
            params.put("dataSourceType", sourceTableInfoVo.getDataSourceType());
        }
        if (null != sourceTableInfoVo.getDataExtractionType()) {
            hql.append("and s.dataExtractionType = :dataExtractionType ");
            params.put("dataExtractionType", sourceTableInfoVo.getDataExtractionType());
        }
        if (StringUtil.isNotBlank(sourceTableInfoVo.getDateColumnName())) {
            hql.append("and s.dateColumnName = :dateColumnName ");
            params.put("dateColumnName", sourceTableInfoVo.getDateColumnName());
        }
        if (StringUtil.isNotBlank(sourceTableInfoVo.getWhereSql())) {
            hql.append("and s.whereSql = :whereSql ");
            params.put("whereSql", sourceTableInfoVo.getWhereSql());
        }
        if (null != sourceTableInfoVo.getStatusId()) {
            hql.append("and s.statusId = :statusId ");
            params.put("statusId", sourceTableInfoVo.getStatusId());
        }
        return super.findPageByHql(page, hql.toString(), params);
    }

    /**
     * 根据条件查询列表
     *
     * @param sourceTableInfoVo
     * @return
     */
    public List<SourceTableInfo> findSourceTableInfoList(SourceTableInfoVo sourceTableInfoVo) {
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from SourceTableInfo s where 1=1 ");
        if (StringUtil.isNotBlank(sourceTableInfoVo.getSourceTableId())) {
            hql.append("and s.sourceTableId = :sourceTableId ");
            params.put("sourceTableId", sourceTableInfoVo.getSourceTableId());
        }
        if (StringUtil.isNotBlank(sourceTableInfoVo.getConfigId())) {
            hql.append("and s.configId = :configId ");
            params.put("configId", sourceTableInfoVo.getConfigId());
        }
        if (StringUtil.isNotBlank(sourceTableInfoVo.getSourceFileName())) {
            hql.append("and s.sourceFileName = :sourceFileName ");
            params.put("sourceFileName", sourceTableInfoVo.getSourceFileName());
        }
        if (StringUtil.isNotBlank(sourceTableInfoVo.getSourceTableName())) {
            hql.append("and s.sourceTableName = :sourceTableName ");
            params.put("sourceTableName", sourceTableInfoVo.getSourceTableName());
        }
        if (StringUtil.isNotBlank(sourceTableInfoVo.getTableSchema())) {
            hql.append("and s.tableSchema = :tableSchema ");
            params.put("tableSchema", sourceTableInfoVo.getTableSchema());
        }
        if (StringUtil.isNotBlank(sourceTableInfoVo.getSourceTableCnName())) {
            hql.append("and s.sourceTableCnName = :sourceTableCnName ");
            params.put("sourceTableCnName", sourceTableInfoVo.getSourceTableCnName());
        }
        if (null != sourceTableInfoVo.getSourceTableType()) {
            hql.append("and s.sourceTableType = :sourceTableType ");
            params.put("sourceTableType", sourceTableInfoVo.getSourceTableType());
        }
        if (StringUtil.isNotBlank(sourceTableInfoVo.getTableSuffix())) {
            hql.append("and s.tableSuffix = :tableSuffix ");
            params.put("tableSuffix", sourceTableInfoVo.getTableSuffix());
        }
        if (null != sourceTableInfoVo.getReadCycle()) {
            hql.append("and s.readCycle = :readCycle ");
            params.put("readCycle", sourceTableInfoVo.getReadCycle());
        }
        if (null != sourceTableInfoVo.getKeyType()) {
            hql.append("and s.keyType = :keyType ");
            params.put("keyType", sourceTableInfoVo.getKeyType());
        }
        if (null != sourceTableInfoVo.getIdType()) {
            hql.append("and s.idType = :idType ");
            params.put("idType", sourceTableInfoVo.getIdType());
        }
        if (StringUtil.isNotBlank(sourceTableInfoVo.getIdColumn())) {
            hql.append("and s.idColumn = :idColumn ");
            params.put("idColumn", sourceTableInfoVo.getIdColumn());
        }
        if (StringUtil.isNotBlank(sourceTableInfoVo.getIdDataType())) {
            hql.append("and s.idDataType = :idDataType ");
            params.put("idDataType", sourceTableInfoVo.getIdDataType());
        }
        if (StringUtil.isNotBlank(sourceTableInfoVo.getColumnCaliber())) {
            hql.append("and s.columnCaliber = :columnCaliber ");
            params.put("columnCaliber", sourceTableInfoVo.getColumnCaliber());
        }
        if (StringUtil.isNotBlank(sourceTableInfoVo.getDataSourceId())) {
            hql.append("and s.dataSourceId = :dataSourceId ");
            params.put("dataSourceId", sourceTableInfoVo.getDataSourceId());
        }
        if (null != sourceTableInfoVo.getDataSourceType()) {
            hql.append("and s.dataSourceType = :dataSourceType ");
            params.put("dataSourceType", sourceTableInfoVo.getDataSourceType());
        }
        if (null != sourceTableInfoVo.getDataExtractionType()) {
            hql.append("and s.dataExtractionType = :dataExtractionType ");
            params.put("dataExtractionType", sourceTableInfoVo.getDataExtractionType());
        }
        if (StringUtil.isNotBlank(sourceTableInfoVo.getDateColumnName())) {
            hql.append("and s.dateColumnName = :dateColumnName ");
            params.put("dateColumnName", sourceTableInfoVo.getDateColumnName());
        }
        if (StringUtil.isNotBlank(sourceTableInfoVo.getWhereSql())) {
            hql.append("and s.whereSql = :whereSql ");
            params.put("whereSql", sourceTableInfoVo.getWhereSql());
        }
        if (null != sourceTableInfoVo.getStatusId()) {
            hql.append("and s.statusId = :statusId ");
            params.put("statusId", sourceTableInfoVo.getStatusId());
        }
        return super.findListByHql(hql.toString(), params);
    }

}
