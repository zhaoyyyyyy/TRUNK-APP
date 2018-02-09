/*
 * @(#)LabelRuleDaoImpl.java
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
import com.asiainfo.biapp.si.loc.core.label.dao.ILabelRuleDao;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelRule;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelRuleVo;

/**
 * Title : LabelRuleDaoImpl
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
 * 1    2017年11月22日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月22日
 */
@Repository
public class LabelRuleDaoImpl extends BaseDaoImpl<LabelRule, String> implements ILabelRuleDao {

    public Page<LabelRule> selectLabelRulePageList(Page<LabelRule> page, LabelRuleVo labelRuleVo) {
        Map<String, Object> reMap = fromBean(labelRuleVo);
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");
        return super.findPageByHql(page, reMap.get("hql").toString(), params);
    }

    public List<LabelRule> selectLabelRuleList(LabelRuleVo labelRuleVo) {
        Map<String, Object> reMap = fromBean(labelRuleVo);
        Map<String, Object> params = (Map<String, Object>) reMap.get("params");
        return super.findListByHql(reMap.get("hql").toString(), params);
    }

    public Map<String, Object> fromBean(LabelRuleVo labelRuleVo) {
        Map<String, Object> reMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from LabelRule l where 1=1 ");
        if (StringUtil.isNotBlank(labelRuleVo.getRuleId())) {
            hql.append("and l.ruleId = :ruleId ");
            params.put("ruleId", labelRuleVo.getRuleId());
        }
        if (StringUtil.isNotBlank(labelRuleVo.getParentId())) {
            hql.append("and l.parentId = :parentId ");
            params.put("parentId", labelRuleVo.getParentId());
        }
        if (StringUtil.isNotBlank(labelRuleVo.getCustomId())) {
            hql.append("and l.customId = :customId ");
            params.put("customId", labelRuleVo.getCustomId());
        }
        if (StringUtil.isNotBlank(labelRuleVo.getCalcuElement())) {
            hql.append("and l.calcuElement = :calcuElement ");
            params.put("calcuElement", labelRuleVo.getCalcuElement());
        }
        if (null != labelRuleVo.getMinVal()) {
            hql.append("and l.minVal = :minVal ");
            params.put("minVal", labelRuleVo.getMinVal());
        }
        if (null != labelRuleVo.getMaxVal()) {
            hql.append("and l.maxVal = :maxVal ");
            params.put("maxVal", labelRuleVo.getMaxVal());
        }
        if (null != labelRuleVo.getSortNum()) {
            hql.append("and l.sortNum = :sortNum ");
            params.put("sortNum", labelRuleVo.getSortNum());
        }
        if (null != labelRuleVo.getCustomType()) {
            hql.append("and l.customType = :customType ");
            params.put("customType", labelRuleVo.getCustomType());
        }
        if (null != labelRuleVo.getElementType()) {
            hql.append("and l.elementType = :elementType ");
            params.put("elementType", labelRuleVo.getElementType());
        }
        if (null != labelRuleVo.getLabelFlag()) {
            hql.append("and l.labelFlag = :labelFlag ");
            params.put("labelFlag", labelRuleVo.getLabelFlag());
        }
        if (StringUtil.isNotBlank(labelRuleVo.getAttrVal())) {
            hql.append("and l.attrVal = :attrVal ");
            params.put("attrVal", labelRuleVo.getAttrVal());
        }
        if (StringUtil.isNotBlank(labelRuleVo.getStartTime())) {
            hql.append("and l.startTime LIKE :startTime ");
            params.put("startTime", "%" + labelRuleVo.getStartTime() + "%");
        }
        if (StringUtil.isNotBlank(labelRuleVo.getEndTime())) {
            hql.append("and l.endTime LIKE :endTime ");
            params.put("endTime", "%" + labelRuleVo.getEndTime() + "%");
        }
        if (StringUtil.isNotBlank(labelRuleVo.getContiueMinVal())) {
            hql.append("and l.contiueMinVal = :contiueMinVal ");
            params.put("contiueMinVal", labelRuleVo.getContiueMinVal());
        }
        if (StringUtil.isNotBlank(labelRuleVo.getContiueMaxVal())) {
            hql.append("and l.contiueMaxVal = :contiueMaxVal ");
            params.put("contiueMaxVal", labelRuleVo.getContiueMaxVal());
        }
        if (StringUtil.isNotBlank(labelRuleVo.getLeftZoneSign())) {
            hql.append("and l.leftZoneSign = :leftZoneSign ");
            params.put("leftZoneSign", labelRuleVo.getLeftZoneSign());
        }
        if (StringUtil.isNotBlank(labelRuleVo.getRightZoneSign())) {
            hql.append("and l.rightZoneSign = :rightZoneSign ");
            params.put("rightZoneSign", labelRuleVo.getRightZoneSign());
        }
        if (StringUtil.isNotBlank(labelRuleVo.getExactValue())) {
            hql.append("and l.exactValue = :exactValue ");
            params.put("exactValue", labelRuleVo.getExactValue());
        }
        if (StringUtil.isNotBlank(labelRuleVo.getDarkValue())) {
            hql.append("and l.darkValue = :darkValue ");
            params.put("darkValue", labelRuleVo.getDarkValue());
        }
        if (StringUtil.isNotBlank(labelRuleVo.getTableName())) {
            hql.append("and l.tableName = :tableName ");
            params.put("tableName", labelRuleVo.getTableName());
        }
        if (null != labelRuleVo.getIsNeedOffset()) {
            hql.append("and l.isNeedOffset = :isNeedOffset ");
            params.put("isNeedOffset", labelRuleVo.getIsNeedOffset());
        }
        if (StringUtil.isNotBlank(labelRuleVo.getVirtualLabelName())) {
            hql.append("and l.virtualLabelName = :virtualLabelName ");
            params.put("virtualLabelName", labelRuleVo.getVirtualLabelName());
        }
        reMap.put("hql", hql);
        reMap.put("params", params);
        return reMap;
    }

}
