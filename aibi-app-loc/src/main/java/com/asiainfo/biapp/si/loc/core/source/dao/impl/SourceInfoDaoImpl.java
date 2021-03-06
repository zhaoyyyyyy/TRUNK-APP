/*
 * @(#)SourceInfoDaoImpl.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.source.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.source.dao.ISourceInfoDao;
import com.asiainfo.biapp.si.loc.core.source.entity.SourceInfo;
import com.asiainfo.biapp.si.loc.core.source.vo.SourceInfoVo;

/**
 * Title : SourceInfoDaoImpl
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
public class SourceInfoDaoImpl extends BaseDaoImpl<SourceInfo, String> implements ISourceInfoDao {

    public Page<SourceInfo> selectSourceInfoPageList(Page<SourceInfo> page, SourceInfoVo sourceInfoVo) {
        Map<String, Object> reMap = fromBean(sourceInfoVo);
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");
        return super.findPageByHql(page, reMap.get("hql").toString(), params);
    }

    public List<SourceInfo> selectSourceInfoList(SourceInfoVo sourceInfoVo) {
        Map<String, Object> reMap = fromBean(sourceInfoVo);
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");
        return super.findListByHql(reMap.get("hql").toString(), params);
    }

    public Map<String, Object> fromBean(SourceInfoVo sourceInfoVo) {
        Map<String, Object> reMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from SourceInfo s where 1=1 ");
        if (StringUtil.isNotBlank(sourceInfoVo.getSourceId())) {
            hql.append("and s.sourceId = :sourceId ");
            params.put("sourceId", sourceInfoVo.getSourceId());
        }
        if (StringUtil.isNotBlank(sourceInfoVo.getSourceName())) {
            hql.append("and s.sourceName LIKE :sourceName ");
            params.put("sourceName", "%"+sourceInfoVo.getSourceName()+"%");
        }
        if (StringUtil.isNotBlank(sourceInfoVo.getSourceTableId())) {
            hql.append("and s.sourceTableId = :sourceTableId ");
            params.put("sourceTableId", sourceInfoVo.getSourceTableId());
        }
        if (StringUtil.isNotBlank(sourceInfoVo.getColumnName())) {
            hql.append("and s.columnName = :columnName ");
            params.put("columnName", sourceInfoVo.getColumnName());
        }
        if (StringUtil.isNotBlank(sourceInfoVo.getSourceColumnRule())) {
            hql.append("and s.sourceColumnRule = :sourceColumnRule ");
            params.put("sourceColumnRule", sourceInfoVo.getSourceColumnRule());
        }
        if (StringUtil.isNotBlank(sourceInfoVo.getColumnCnName())) {
            hql.append("and s.columnCnName = :columnCnName ");
            params.put("columnCnName", sourceInfoVo.getColumnCnName());
        }
        if (StringUtil.isNotBlank(sourceInfoVo.getCooColumnType())) {
            hql.append("and s.cooColumnType = :cooColumnType ");
            params.put("cooColumnType", sourceInfoVo.getCooColumnType());
        }
        if (null != sourceInfoVo.getColumnLength()) {
            hql.append("and s.columnLength = :columnLength ");
            params.put("columnLength", sourceInfoVo.getColumnLength());
        }
        if (StringUtil.isNotBlank(sourceInfoVo.getColumnUnit())) {
            hql.append("and s.columnUnit = :columnUnit ");
            params.put("columnUnit", sourceInfoVo.getColumnUnit());
        }
        if (StringUtil.isNotBlank(sourceInfoVo.getColumnCaliber())) {
            hql.append("and s.columnCaliber = :columnCaliber ");
            params.put("columnCaliber", sourceInfoVo.getColumnCaliber());
        }
        if (null != sourceInfoVo.getColumnNum()) {
            hql.append("and s.columnNum = :columnNum ");
            params.put("columnNum", sourceInfoVo.getColumnNum());
        }
        reMap.put("hql", hql);
        reMap.put("params", params);
        return reMap;
    }

    @Override
    public Page<SourceInfo> selectSourceInfoListByConfigId(Page<SourceInfo> page, String configId,int readCycle,String sourceName) {
        Map<String, Object> params = new HashMap<>();
        StringBuffer hqls = new StringBuffer("from SourceInfo s where s.sourceTableId in(select a.sourceTableId from SourceTableInfo a where a.sourceTableType = 1 and a.configId= :configId and a.readCycle= :readCycle)");
        params.put("configId", configId);
        params.put("readCycle", readCycle);
        if (StringUtil.isNoneBlank(sourceName)) {
            hqls.append(" and s.sourceName LIKE :sourceName");
            params.put("sourceName", "%"+sourceName+"%");
        }
        return super.findPageByHql(page, hqls.toString(), params);
    }
}
