/*
 * @(#)LabelInfoDaoImpl.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.base.utils.DateUtil;
import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
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
@Repository("labelInfoDaoImpl")
public class LabelInfoDaoImpl extends BaseDaoImpl<LabelInfo, String> implements ILabelInfoDao {

    public Page<LabelInfo> selectLabelInfoPageList(Page<LabelInfo> page, LabelInfoVo labelInfoVo) {
        Map<String, Object> reMap = fromBean(labelInfoVo,page);
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");
        String sql = reMap.get("hql").toString();
        return super.findPageByHql(page, reMap.get("hql").toString(), params);
    }

    public List<LabelInfo> selectLabelInfoList(LabelInfoVo labelInfoVo) {
        Map<String, Object> reMap = fromBean(labelInfoVo,new Page<LabelInfo>());
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");
        return super.findListByHql(reMap.get("hql").toString(), params);
    }

    public Map<String, Object> fromBean(LabelInfoVo labelInfoVo,Page<LabelInfo> page) {
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
        if (StringUtil.isNotBlank(labelInfoVo.getLabelName())) {
            hql.append("and l.labelName Like :labelName ");
            params.put("labelName", "%"+labelInfoVo.getLabelName()+"%");
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
            hql.append("and l.categoryId in (:categoryIdSet) ");
            params.put("categoryIdSet", labelInfoVo.getCategoryIdSet());
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
        //modify by zhougz3 此配置异步生成，暂时不访问数据库 20180119
//        if (StringUtil.isNotBlank(labelInfoVo.getLabelIdLevelDesc())) {
//            hql.append("and l.labelIdLevelDesc = :labelIdLevelDesc ");
//            params.put("labelIdLevelDesc", labelInfoVo.getLabelIdLevelDesc());
//        }
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
        if (StringUtil.isNotBlank(labelInfoVo.getApproveStatusId())) {
            hql.append("and l.approveInfo.approveStatusId = :approveStatusId ");
            params.put("approveStatusId", labelInfoVo.getApproveStatusId());
        }
        if (StringUtils.isNotBlank(labelInfoVo.getpublishTimeStart())) {
            hql.append("and l.publishTime >= :publishTimeStart ");
            params.put("publishTimeStart", DateUtil.string2Date(labelInfoVo.getpublishTimeStart(), DateUtil.FORMAT_YYYY_MM_DD));
        }
        if (StringUtils.isNotBlank(labelInfoVo.getpublishTimeEnd())) {
            hql.append("and l.publishTime <= :publishTimeEnd ");
            params.put("publishTimeEnd", DateUtil.string2Date(labelInfoVo.getpublishTimeEnd(), DateUtil.FORMAT_YYYY_MM_DD));
        }
        hql.append("and l.dataStatusId != 6");
        if(StringUtil.isNotBlank(page.getSortCol())){
            hql.append(" order by l."+page.getSortCol()+" "+page.getSortOrder());
        }
        reMap.put("hql", hql);
        reMap.put("params", params);
        return reMap;
    }

    public List<LabelInfo> selectEffectiveCiLabelInfo(){
        List<LabelInfo> labelInfoList = new ArrayList<>();
        try {
            labelInfoList = super.findListByHql("from LabelInfo l where l.dataStatusId = ?0", 2);
        } catch (BaseException e) {
            LogUtil.error(e);
        }
        return labelInfoList;
    }
    
    public List<LabelInfo> selectLabelAllEffectiveInfoList(LabelInfoVo labelInfoVo) {
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
        if (StringUtil.isNotBlank(labelInfoVo.getLabelName())) {
            hql.append("and l.labelName Like :labelName ");
            params.put("labelName", "%"+labelInfoVo.getLabelName()+"%");
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
      //modify by zhougz3 此配置异步生成，暂时不访问数据库 20180119
//        if (StringUtil.isNotBlank(labelInfoVo.getLabelIdLevelDesc())) {
//            hql.append("and l.labelIdLevelDesc = :labelIdLevelDesc ");
//            params.put("labelIdLevelDesc", labelInfoVo.getLabelIdLevelDesc());
//        }
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
        if (StringUtil.isNotBlank(labelInfoVo.getApproveStatusId())) {
            hql.append("and approveInfo.approveStatusId LIKE :approveStatusId");
            params.put("approveStatusId", "%"+labelInfoVo.getApproveStatusId()+"%");
        }
        if (StringUtils.isNotBlank(labelInfoVo.getpublishTimeStart())) {
            hql.append("and l.publishTime >= :publishTimeStart ");
            params.put("publishTimeStart", DateUtil.string2Date(labelInfoVo.getpublishTimeStart(), DateUtil.DATETIME_FORMAT));
        }
        if (StringUtils.isNotBlank(labelInfoVo.getpublishTimeEnd())) {
            hql.append("and l.publishTime <= :publishTimeEnd ");
            params.put("publishTimeEnd", DateUtil.string2Date(labelInfoVo.getpublishTimeEnd(), DateUtil.DATETIME_FORMAT));
        }
        hql.append("and (l.dataStatusId= 1 or l.dataStatusId= 2 or l.dataStatusId= 4) ");
        return super.findListByHql(hql.toString(), params);
    }

}
