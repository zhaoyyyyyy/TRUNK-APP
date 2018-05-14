/*
 * @(#)SourceTableInfoDaoImpl.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.source.dao.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.ServiceConstants;
import com.asiainfo.biapp.si.loc.core.source.dao.ISourceTableInfoDao;
import com.asiainfo.biapp.si.loc.core.source.entity.SourceTableInfo;
import com.asiainfo.biapp.si.loc.core.source.vo.SourceTableInfoVo;

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

    public Page<SourceTableInfo> selectSourceTableInfoPageList(Page<SourceTableInfo> page,
            SourceTableInfoVo sourceTableInfoVo) {
        Map<String, Object> reMap = fromBean(sourceTableInfoVo);
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");
        return super.findPageByHql(page, reMap.get("hql").toString(), params);
    }
    

    /**
     * 查询未准备好的数据
     * @param configId
     * @return
     */
    public List<SourceTableInfo> selectNotPrepareData(SourceTableInfoVo sourceTableInfoVo) {
        StringBuilder hql =new StringBuilder();
        hql.append("from  SourceTableInfo l where 1=1 ");
                
        Map<String, Object> params = new HashMap<>();
        if (StringUtil.isNotEmpty(sourceTableInfoVo.getConfigId())) {
            if(sourceTableInfoVo.getConfigId().indexOf(",") != -1){
                Set<String> configIdSet = new HashSet<String>();
                for(String configId : sourceTableInfoVo.getConfigId().split(",")){
                    configIdSet.add(configId);
                }
                hql.append("and l.configId in (:configIdSet) "); 
                params.put("configIdSet", configIdSet);
            }else{
                hql.append("and configId = :configId ");
                params.put("configId", sourceTableInfoVo.getConfigId());
            }
        }
        hql.append("and l.sourceTableId not in ");
        hql.append("( select a.sourceTableId from TargetTableStatus a where a.sourceTableId != 0 and a.dataDate = :dataDate)");
        params.put("dataDate",sourceTableInfoVo.getDataDate());
        return super.findListByHql(hql.toString(), params);
    }
    
    /**
     * 运营监控明细页面数据准备表格分页
     * @param page
     * @param sourceTableInfoVo
     */
    public Page<SourceTableInfo> selectSourceTableInfoMonitorPageList(Page<SourceTableInfo> page,
            SourceTableInfoVo sourceTableInfoVo) {
        if(StringUtil.isNotEmpty(sourceTableInfoVo.getDataStatuses()) && "0".equals(sourceTableInfoVo.getDataStatuses())){
            return null;
        }
        Map<String, Object> reMap = fromBeanForMonitor(sourceTableInfoVo);
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");
        return super.findPageByHql(page, reMap.get("hql").toString(), params);
    }
    
    public List<SourceTableInfo> selectSourceTableInfoList(SourceTableInfoVo sourceTableInfoVo) {
        Map<String, Object> reMap = fromBean(sourceTableInfoVo);
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");
        return super.findListByHql(reMap.get("hql").toString(), params);
    }

    public Map<String, Object> fromBean(SourceTableInfoVo sourceTableInfoVo) {
        Map<String, Object> reMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from SourceTableInfo s where 1=1  ");
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
            if(sourceTableInfoVo.getSourceTableName().contains("=")){
                hql.append("and s.sourceTableName =:sourceTableName ");
                params.put("sourceTableName",sourceTableInfoVo.getSourceTableName().replace("=", ""));
            }else{
                hql.append("and s.sourceTableName LIKE:sourceTableName ");
                params.put("sourceTableName","%"+sourceTableInfoVo.getSourceTableName()+"%");
            }
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
        reMap.put("hql", hql);
        reMap.put("params", params);
        return reMap;
    }
    
    /**
     * 运营监控明细页面表格过滤条件：
     * @param sourceTableInfoVo
     * VO需要参数：专区ID：configId（必须）,
     * 数据日期:dataDate（必须）,
     * 指标源表表名(英文非必须):sourceTableName,
     * 查询准备状态列表:dataStatuses 0：未准备；1：准备完成（支持多选）,
     * 查询抽取状态列表:isDoings(支持多选)
     * @return
     */
    public Map<String, Object> fromBeanForMonitor(SourceTableInfoVo sourceTableInfoVo) {
        Map<String, Object> reMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("select new SourceTableInfo(s.sourceTableId,s.sourceTableName,t.dataStatus,t.isDoing,t.startTime,t.endTime,t.exceptionDesc) "
                + "from SourceTableInfo s left join s.targetTableStatusList t where 1=1  ");
        if (StringUtil.isNotBlank(sourceTableInfoVo.getConfigId())) {
            hql.append("and s.configId = :configId ");
            params.put("configId", sourceTableInfoVo.getConfigId());
        }
        if (StringUtils.isNotBlank(sourceTableInfoVo.getDataDate())) {
            hql.append("and t.dataDate = :dataDate ");
            params.put("dataDate", sourceTableInfoVo.getDataDate());
        }
  
        if (StringUtil.isNotBlank(sourceTableInfoVo.getSourceTableName())) {
            if(sourceTableInfoVo.getSourceTableName().contains("=")){
                hql.append("and s.sourceTableName =:sourceTableName ");
                params.put("sourceTableName",sourceTableInfoVo.getSourceTableName().replace("=", ""));
            }else{
                hql.append("and s.sourceTableName LIKE:sourceTableName ");
                params.put("sourceTableName","%"+sourceTableInfoVo.getSourceTableName()+"%");
            }
        }
        //查询准备完成状态列表 
        if (StringUtil.isNotEmpty(sourceTableInfoVo.getDataStatuses())) {
            Set<Integer> statusSet = new HashSet<Integer>();
            for(String status : sourceTableInfoVo.getDataStatuses().split(",")){
                statusSet.add(Integer.parseInt(status));
            }
            if(statusSet.contains(ServiceConstants.TargetTableStatus.TARGET_TABLE_PREPARED)){
                //查询准备完成的数据：dataStatus !=2 && sourceTableId !=0
                hql.append("and (t.dataStatus !=2 and t.sourceTableId != 0 )  ");
            }
        }
        //查询抽取状态列表
        if (StringUtil.isNotEmpty(sourceTableInfoVo.getIsDoings())) {
            Set<Integer> isdoingSet = new HashSet<Integer>();
            for(String isDoing : sourceTableInfoVo.getIsDoings().split(",")){
                isdoingSet.add(Integer.parseInt(isDoing));
            }
            hql.append(formatIsdoingCondion(isdoingSet));
        }
       
        reMap.put("hql", hql);
        reMap.put("params", params);
        return reMap;
    }

    /**
     * 运营监控明细页面，数据抽取列表过滤条件：
     * @param isdoingSet
     * @return
     */
    public StringBuilder formatIsdoingCondion(Set<Integer> isdoingSet){
        StringBuilder hql = new StringBuilder();
        for(Integer isDoing : isdoingSet){
            if(ServiceConstants.TargetTableStatus.TARGET_TABLE_EXTRACT_SUCCESS == isDoing){
                // 抽取完成：isDoing=0 && dataStatus=0
                hql.append("or (t.isDoing = 0 and t.dataStatus =0 ) ");
            }
            if(ServiceConstants.TargetTableStatus.TARGET_TABLE_EXTRACTING == isDoing){
                // 抽取中：isDoing=1 && dataStatus=1
                hql.append("or (t.isDoing = 1 and t.dataStatus =1 ) ");
            }
            if(ServiceConstants.TargetTableStatus.TARGET_TABLE_EXTRACT_FAIL == isDoing){
                // 抽取失败：isDoing=0 && dataStatus=2
                hql.append("or (t.isDoing = 0 and t.dataStatus =2 ) ");
            }
            if(ServiceConstants.TargetTableStatus.TARGET_TABLE_NOTEXTRACT == isDoing){
                // 未抽取：isDoing=0 && dataStatus=1
                hql.append("or (t.isDoing = 0 and t.dataStatus =1 ) ");
            }
        }
        hql.replace(0, 2, "and (").append(") ");
        return hql;
    }
}
