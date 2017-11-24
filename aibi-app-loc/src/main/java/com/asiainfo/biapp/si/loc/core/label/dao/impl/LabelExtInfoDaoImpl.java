/*
 * @(#)LabelExtInfoDaoImpl.java
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
import com.asiainfo.biapp.si.loc.core.label.dao.ILabelExtInfoDao;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelExtInfo;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelExtInfoVo;

/**
 * Title : LabelExtInfoDaoImpl
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
 * 1    2017年11月21日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月21日
 */
@Repository
public class LabelExtInfoDaoImpl extends BaseDaoImpl<LabelExtInfo, String> implements ILabelExtInfoDao {

    public Page<LabelExtInfo> findLabelExtInfoPageList(Page<LabelExtInfo> page, LabelExtInfoVo labelExtInfoVo) {
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from LabelExtInfo l where 1=1 ");
        if (StringUtil.isNotBlank(labelExtInfoVo.getLabelId())) {
            hql.append("and l.labelId = :labelId ");
            params.put("labelId", labelExtInfoVo.getLabelId());
        }
        if (null != labelExtInfoVo.getOriginalTableType()) {
            hql.append("and l.originalTableType = :originalTableType ");
            params.put("originalTableType", labelExtInfoVo.getOriginalTableType());
        }
        if (StringUtil.isNotBlank(labelExtInfoVo.getLabelOptRuleShow())) {
            hql.append("and l.labelOptRuleShow = :labelOptRuleShow ");
            params.put("labelOptRuleShow", labelExtInfoVo.getLabelOptRuleShow());
        }
        if (StringUtil.isNotBlank(labelExtInfoVo.getMonthLabelDate())) {
            hql.append("and l.monthLabelDate = :monthLabelDate ");
            params.put("monthLabelDate", labelExtInfoVo.getMonthLabelDate());
        }
        if (StringUtil.isNotBlank(labelExtInfoVo.getDayLabelDate())) {
            hql.append("and l.dayLabelDate = :dayLabelDate ");
            params.put("dayLabelDate", labelExtInfoVo.getDayLabelDate());
        }
        if (StringUtil.isNotBlank(labelExtInfoVo.getTacticsId())) {
            hql.append("and l.tacticsId = :tacticsId ");
            params.put("tacticsId", labelExtInfoVo.getTacticsId());
        }
        if (StringUtil.isNotBlank(labelExtInfoVo.getServerId())) {
            hql.append("and l.serverId = :serverId ");
            params.put("serverId", labelExtInfoVo.getServerId());
        }
        if (StringUtil.isNotBlank(labelExtInfoVo.getOffsetDate())) {
            hql.append("and l.offsetDate = :offsetDate ");
            params.put("offsetDate", labelExtInfoVo.getOffsetDate());
        }
        if (null != labelExtInfoVo.getListMaxNum()) {
            hql.append("and l.listMaxNum = :listMaxNum ");
            params.put("listMaxNum", labelExtInfoVo.getListMaxNum());
        }
        if (null != labelExtInfoVo.getPriority()) {
            hql.append("and l.priority = :priority ");
            params.put("priority", labelExtInfoVo.getPriority());
        }
        if (null != labelExtInfoVo.getLabelLevel()) {
            hql.append("and l.labelLevel = :labelLevel ");
            params.put("labelLevel", labelExtInfoVo.getLabelLevel());
        }
        if (StringUtil.isNotBlank(labelExtInfoVo.getTechCaliber())) {
            hql.append("and l.techCaliber = :techCaliber ");
            params.put("techCaliber", labelExtInfoVo.getTechCaliber());
        }
        if (null != labelExtInfoVo.getCustomNum()) {
            hql.append("and l.customNum = :customNum ");
            params.put("customNum", labelExtInfoVo.getCustomNum());
        }
        if (null != labelExtInfoVo.getLabelPrecision()) {
            hql.append("and l.labelPrecision = :labelPrecision ");
            params.put("labelPrecision", labelExtInfoVo.getLabelPrecision());
        }
        if (null != labelExtInfoVo.getLabelCoverate()) {
            hql.append("and l.labelCoverate = :labelCoverate ");
            params.put("labelCoverate", labelExtInfoVo.getLabelCoverate());
        }
        if (null != labelExtInfoVo.getAvgScore()) {
            hql.append("and l.avgScore = :avgScore ");
            params.put("avgScore", labelExtInfoVo.getAvgScore());
        }
        return super.findPageByHql(page, hql.toString(), params);
    }

    public List<LabelExtInfo> findLabelExtInfoList(LabelExtInfoVo labelExtInfoVo) {
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from LabelExtInfo l where 1=1 ");
        if (StringUtil.isNotBlank(labelExtInfoVo.getLabelId())) {
            hql.append("and l.labelId = :labelId ");
            params.put("labelId", labelExtInfoVo.getLabelId());
        }
        if (null != labelExtInfoVo.getOriginalTableType()) {
            hql.append("and l.originalTableType = :originalTableType ");
            params.put("originalTableType", labelExtInfoVo.getOriginalTableType());
        }
        if (StringUtil.isNotBlank(labelExtInfoVo.getLabelOptRuleShow())) {
            hql.append("and l.labelOptRuleShow = :labelOptRuleShow ");
            params.put("labelOptRuleShow", labelExtInfoVo.getLabelOptRuleShow());
        }
        if (StringUtil.isNotBlank(labelExtInfoVo.getMonthLabelDate())) {
            hql.append("and l.monthLabelDate = :monthLabelDate ");
            params.put("monthLabelDate", labelExtInfoVo.getMonthLabelDate());
        }
        if (StringUtil.isNotBlank(labelExtInfoVo.getDayLabelDate())) {
            hql.append("and l.dayLabelDate = :dayLabelDate ");
            params.put("dayLabelDate", labelExtInfoVo.getDayLabelDate());
        }
        if (StringUtil.isNotBlank(labelExtInfoVo.getTacticsId())) {
            hql.append("and l.tacticsId = :tacticsId ");
            params.put("tacticsId", labelExtInfoVo.getTacticsId());
        }
        if (StringUtil.isNotBlank(labelExtInfoVo.getServerId())) {
            hql.append("and l.serverId = :serverId ");
            params.put("serverId", labelExtInfoVo.getServerId());
        }
        if (StringUtil.isNotBlank(labelExtInfoVo.getOffsetDate())) {
            hql.append("and l.offsetDate = :offsetDate ");
            params.put("offsetDate", labelExtInfoVo.getOffsetDate());
        }
        if (null != labelExtInfoVo.getListMaxNum()) {
            hql.append("and l.listMaxNum = :listMaxNum ");
            params.put("listMaxNum", labelExtInfoVo.getListMaxNum());
        }
        if (null != labelExtInfoVo.getPriority()) {
            hql.append("and l.priority = :priority ");
            params.put("priority", labelExtInfoVo.getPriority());
        }
        if (null != labelExtInfoVo.getLabelLevel()) {
            hql.append("and l.labelLevel = :labelLevel ");
            params.put("labelLevel", labelExtInfoVo.getLabelLevel());
        }
        if (StringUtil.isNotBlank(labelExtInfoVo.getTechCaliber())) {
            hql.append("and l.techCaliber = :techCaliber ");
            params.put("techCaliber", labelExtInfoVo.getTechCaliber());
        }
        if (null != labelExtInfoVo.getCustomNum()) {
            hql.append("and l.customNum = :customNum ");
            params.put("customNum", labelExtInfoVo.getCustomNum());
        }
        if (null != labelExtInfoVo.getLabelPrecision()) {
            hql.append("and l.labelPrecision = :labelPrecision ");
            params.put("labelPrecision", labelExtInfoVo.getLabelPrecision());
        }
        if (null != labelExtInfoVo.getLabelCoverate()) {
            hql.append("and l.labelCoverate = :labelCoverate ");
            params.put("labelCoverate", labelExtInfoVo.getLabelCoverate());
        }
        if (null != labelExtInfoVo.getAvgScore()) {
            hql.append("and l.avgScore = :avgScore ");
            params.put("avgScore", labelExtInfoVo.getAvgScore());
        }
        return super.findListByHql(hql.toString(), params);
    }
}
