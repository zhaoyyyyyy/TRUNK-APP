/*
 * @(#)LabelInfoDaoImpl.java
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
import com.asiainfo.biapp.si.loc.core.label.dao.ILabelInfoDao;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelInfoVo;

/**
 * Title : LabelInfoDaoImpl
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
 * 1    2017年11月16日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月16日
 */
@Repository
public class LabelInfoDaoImpl extends BaseDaoImpl<LabelInfo, String> implements ILabelInfoDao {

    public Page<LabelInfo> selectLabelInfoPageList(Page<LabelInfo> page, LabelInfoVo labelInfoVo) {
        Map<String, Object> reMap = fromBean(labelInfoVo);
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");
        return super.findPageByHql(page, reMap.get("hql").toString(), params);
    }

    public List<LabelInfo> selectLabelInfoList(LabelInfoVo labelInfoVo) {
        Map<String, Object> reMap = fromBean(labelInfoVo);
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");
        return super.findListByHql(reMap.get("hql").toString(), params);
    }

    public Map<String, Object> fromBean(LabelInfoVo labelInfoVo) {
        Map<String, Object> reMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from LabelInfo l where 1=1 ");
        if (StringUtil.isNotBlank(labelInfoVo.getLabelId())) {
            hql.append("and l.labelId = :labelId ");
            params.put("labelId", labelInfoVo.getLabelId());
        }
        if (null != labelInfoVo.getKeyType()) {
            hql.append("and l.keyType = :keyType ");
            params.put("keyType", labelInfoVo.getKeyType());
        }
        if (StringUtil.isNotBlank(labelInfoVo.getConfigId())) {
            hql.append("and l.configId = :configId ");
            params.put("configId", labelInfoVo.getConfigId());
        }
        if (StringUtil.isNotBlank(labelInfoVo.getOrgId())) {
            hql.append("and l.orgId = :orgId ");
            params.put("orgId", labelInfoVo.getOrgId());
        }
        if (StringUtil.isNotBlank(labelInfoVo.getCountRulesCode())) {
            hql.append("and l.countRulesCode = :countRulesCode ");
            params.put("countRulesCode", labelInfoVo.getCountRulesCode());
        }
        if (StringUtil.isNotBlank(labelInfoVo.getLabelName())) {
            hql.append("and l.labelName = :labelName ");
            params.put("labelName", labelInfoVo.getLabelName());
        }
        if (null != labelInfoVo.getUpdateCycle()) {
            hql.append("and l.updateCycle = :updateCycle ");
            params.put("updateCycle", labelInfoVo.getUpdateCycle());
        }
        if (null != labelInfoVo.getLabelTypeId()) {
            hql.append("and l.labelTypeId = :labelTypeId ");
            params.put("labelTypeId", labelInfoVo.getLabelTypeId());
        }
        if (StringUtil.isNotBlank(labelInfoVo.getCategoryId())) {
            hql.append("and l.categoryId = :categoryId ");
            params.put("categoryId", labelInfoVo.getCategoryId());
        }
        if (null != labelInfoVo.getCreateTypeId()) {
            hql.append("and l.createTypeId = :createTypeId ");
            params.put("createTypeId", labelInfoVo.getCreateTypeId());
        }
        if (null != labelInfoVo.getDataStatusId()) {
            hql.append("and l.dataStatusId = :dataStatusId ");
            params.put("dataStatusId", labelInfoVo.getDataStatusId());
        }
        if (StringUtil.isNotBlank(labelInfoVo.getDataDate())) {
            hql.append("and l.dataDate = :dataDate ");
            params.put("dataDate", labelInfoVo.getDataDate());
        }
        if (StringUtil.isNotBlank(labelInfoVo.getBusiCaliber())) {
            hql.append("and l.busiCaliber = :busiCaliber ");
            params.put("busiCaliber", labelInfoVo.getBusiCaliber());
        }
        if (null != labelInfoVo.getEffecTime()) {
            hql.append("and l.effecTime = :effecTime ");
            params.put("effecTime", labelInfoVo.getEffecTime());
        }
        if (null != labelInfoVo.getFailTime()) {
            hql.append("and l.failTime = :failTime ");
            params.put("failTime", labelInfoVo.getFailTime());
        }
        if (null != labelInfoVo.getPublishTime()) {
            hql.append("and l.publishTime = :publishTime ");
            params.put("publishTime", labelInfoVo.getPublishTime());
        }
        if (StringUtil.isNotBlank(labelInfoVo.getPublishDesc())) {
            hql.append("and l.publishDesc = :publishDesc ");
            params.put("publishDesc", labelInfoVo.getPublishDesc());
        }
        if (StringUtil.isNotBlank(labelInfoVo.getBusiLegend())) {
            hql.append("and l.busiLegend = :busiLegend ");
            params.put("busiLegend", labelInfoVo.getBusiLegend());
        }
        if (StringUtil.isNotBlank(labelInfoVo.getApplySuggest())) {
            hql.append("and l.applySuggest = :applySuggest ");
            params.put("applySuggest", labelInfoVo.getApplySuggest());
        }
        if (StringUtil.isNotBlank(labelInfoVo.getLabelIdLevelDesc())) {
            hql.append("and l.labelIdLevelDesc = :labelIdLevelDesc ");
            params.put("labelIdLevelDesc", labelInfoVo.getLabelIdLevelDesc());
        }
        if (null != labelInfoVo.getIsRegular()) {
            hql.append("and l.isRegular = :isRegular ");
            params.put("isRegular", labelInfoVo.getIsRegular());
        }
        if (null != labelInfoVo.getGroupType()) {
            hql.append("and l.groupType = :groupType ");
            params.put("groupType", labelInfoVo.getGroupType());
        }
        if (null != labelInfoVo.getSortNum()) {
            hql.append("and l.sortNum = :sortNum ");
            params.put("sortNum", labelInfoVo.getSortNum());
        }
        reMap.put("hql", hql);
        reMap.put("params", params);
        return reMap;
    }

}
