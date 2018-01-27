/*
 * @(#)LabelRuleServiceImpl.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.common.CommonConstants;
import com.asiainfo.biapp.si.loc.base.common.LabelInfoContants;
import com.asiainfo.biapp.si.loc.base.common.LabelRuleContants;
import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.cache.CocCacheProxy;
import com.asiainfo.biapp.si.loc.core.label.dao.ILabelRuleDao;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelRule;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelRuleService;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelRuleVo;

/**
 * Title : LabelRuleServiceImpl
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
@Service
@Transactional
public class LabelRuleServiceImpl extends BaseServiceImpl<LabelRule, String> implements ILabelRuleService {

    @Autowired
    private ILabelRuleDao iLabelRuleDao;

    @Override
    protected BaseDao<LabelRule, String> getBaseDao() {
        return iLabelRuleDao;
    }

	@Override
	public List<LabelRuleVo> queryCiLabelRuleList(String customId, Integer customType) throws BaseException {
		List<LabelRuleVo> list=new ArrayList<>();
		LabelRuleVo param=new LabelRuleVo();
		param.setCustomId(customId);
		param.setCustomType(customType);
		List<LabelRule> labelRuleList = iLabelRuleDao.selectLabelRuleList(param);
		for (LabelRule entity : labelRuleList) {
			LabelRuleVo labelRuleVo=new LabelRuleVo();
			try {
				BeanUtils.copyProperties(labelRuleVo, entity);
			} catch (Exception e) {}
			if (LabelRuleContants.ELEMENT_TYPE_LABEL_ID == entity.getElementType()) {
				String labelIdStr = entity.getCalcuElement();
				LabelInfo labelInfo = CocCacheProxy.getCacheProxy().getLabelInfoById(labelIdStr);
				labelRuleVo.setLabelTypeId(labelInfo.getLabelTypeId());
				labelRuleVo.setAttrName(labelInfo.getLabelName());
				//TODO 设置其他参数
			}
			list.add(labelRuleVo);
		}
		return list;
	}

    private LabelRule selectLabelRuleById(String ruleId) throws BaseException {
        if (StringUtils.isBlank(ruleId)) {
            throw new ParamRequiredException("ID不能为空");
        }
        return super.get(ruleId);
    }

    public void addLabelRule(LabelRule labelRule) throws BaseException {
        super.saveOrUpdate(labelRule);
    }

    public void deleteLabelRule(String ruleId) throws BaseException {
        if (StringUtils.isBlank(ruleId)) {
            throw new ParamRequiredException("ID不能为空");
        }
        if (selectLabelRuleById(ruleId)==null){
            throw new ParamRequiredException("ID不存在");
        }
        super.delete(ruleId);
    }

	@Override
	public String shopCartRule(List<LabelRuleVo> ciLabelRuleListTemp) {
		StringBuffer rule=new StringBuffer();
		if (ciLabelRuleListTemp != null && ciLabelRuleListTemp.size() > 0) {
			for (int i = 0; i < ciLabelRuleListTemp.size(); i++) {
				LabelRuleVo labelRule = ciLabelRuleListTemp.get(i);
				int elementType = labelRule.getElementType();
				if(elementType == LabelRuleContants.ELEMENT_TYPE_BRACKET){
					rule.append(labelRule.getCalcuElement());
				}else if(elementType == LabelRuleContants.ELEMENT_TYPE_OPERATOR){
					String operE = "";
					if(LabelRuleContants.CALCULATE_ELEMENT_TYPE_OPT_AND.equals(labelRule.getCalcuElement())){
						operE = "且";
					}else if(LabelRuleContants.CALCULATE_ELEMENT_TYPE_OPT_OR.equals(labelRule.getCalcuElement())){
						operE = "或";
					}else{
						operE = "剔除";
					}
					rule.append(operE);
				}else if(elementType == LabelRuleContants.ELEMENT_TYPE_LABEL_ID){
					if(labelRule.getLabelFlag() != LabelRuleContants.LABEL_RULE_FLAG_YES && labelRule.getLabelTypeId() != LabelInfoContants.LABEL_TYPE_SIGN){
						rule.append("(非)");
					}
					rule.append(labelRule.getCustomOrLabelName());
					String attrVal = ruleAttrVal(labelRule);
					if(StringUtil.isNotEmpty(attrVal)){
						rule.append("[");
						rule.append(attrVal);
						rule.append("]");
					}
				}
			}
				
		}
		return rule.toString();
	}
	
	/**
	 * 标签规则展开
	 * @param labelRule
	 * @return
	 */
	public String ruleAttrVal(LabelRuleVo labelRule){
		StringBuffer attrValStr = new StringBuffer();
		if(labelRule.getLabelTypeId() == LabelInfoContants.LABEL_TYPE_SIGN){//01型
			if(labelRule.getLabelFlag() != LabelRuleContants.LABEL_RULE_FLAG_YES){
				attrValStr.append("否");
			}else{
				attrValStr.append("是");
			}
		}else if(labelRule.getLabelTypeId() == LabelInfoContants.LABEL_TYPE_KPI){
			if("1".equals(labelRule.getQueryWay())){
				attrValStr.append("数值范围：");
				if(StringUtil.isNotEmpty(labelRule.getContiueMinVal())){
					if(labelRule.getLeftZoneSign().equals(CommonConstants.GE)){
						attrValStr.append("大于等于");
					}
					if(labelRule.getLeftZoneSign().equals(CommonConstants.GT)){
						attrValStr.append("大于");
					}
					attrValStr.append(labelRule.getContiueMinVal());
				}
				if(StringUtil.isNotEmpty(labelRule.getContiueMaxVal())){
					if(labelRule.getRightZoneSign().equals(CommonConstants.LE)){
						attrValStr.append("小于等于");
					}
					if(labelRule.getRightZoneSign().equals(CommonConstants.LT)){
						attrValStr.append("小于");
					}
					attrValStr.append(labelRule.getContiueMaxVal());
				}
				if(StringUtil.isNotEmpty(labelRule.getUnit())){
					attrValStr.append("(");
					attrValStr.append(labelRule.getUnit());
					attrValStr.append(")");
				}
			}else if("2".equals(labelRule.getQueryWay())){
				if(StringUtil.isNotEmpty(labelRule.getExactValue())){
					attrValStr.append("数值范围：");
					attrValStr.append(labelRule.getExactValue());
					if(StringUtil.isNotEmpty(labelRule.getUnit())){
						attrValStr.append("(");
						attrValStr.append(labelRule.getUnit());
						attrValStr.append(")");
					}
				}
			}
		}else if(labelRule.getLabelTypeId() == LabelInfoContants.LABEL_TYPE_ENUM ){
			if(StringUtil.isNotEmpty(labelRule.getAttrVal())){
				attrValStr.append("已选择条件：");
				attrValStr.append(labelRule.getAttrName());
			}
		}else if(labelRule.getLabelTypeId() == LabelInfoContants.LABEL_TYPE_DATE){
			//
			
		}else if(labelRule.getLabelTypeId() == LabelInfoContants.LABEL_TYPE_TEXT){
			if("1".equals(labelRule.getQueryWay())){
				if(StringUtil.isNotEmpty(labelRule.getDarkValue())){
					attrValStr.append("模糊值：");
					attrValStr.append(labelRule.getDarkValue());
				}
			}else if("2".equals(labelRule.getQueryWay())){
				if(StringUtil.isNotEmpty(labelRule.getExactValue())){
					attrValStr.append("精确值：");
					attrValStr.append(labelRule.getExactValue());
				}
			}
		// 增加组合标签类型，处理方式与纵表一致（已做）
		}
		return attrValStr.toString();
	}
	

}
