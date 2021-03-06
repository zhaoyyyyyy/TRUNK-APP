/*
 * @(#)LabelRuleServiceImpl.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.common.CommonConstants;
import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.base.utils.ExpressionPaser;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.cache.CocCacheProxy;
import com.asiainfo.biapp.si.loc.core.ServiceConstants;
import com.asiainfo.biapp.si.loc.core.label.dao.ILabelRuleDao;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelRule;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelVerticalColumnRel;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTableColumn;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelInfoService;
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
    
    @Autowired
    private ILabelInfoService iLabelInfoService;

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
		List<LabelRule> resultRuleList = new ArrayList<LabelRule>();
		List<LabelRule> childrenRulesList = new ArrayList<LabelRule>();
		// 非子规则和子规则分离
		for (LabelRule r : labelRuleList) {
			if (StringUtil.isEmpty(r.getParentId())) {
				resultRuleList.add(r);
			} else {
				childrenRulesList.add(r);
			}
		}
		for (LabelRule entity : resultRuleList) {
			LabelRuleVo rule=new LabelRuleVo();
			try {
				BeanUtils.copyProperties(rule, entity);
			} catch (Exception e) {LogUtil.info("将值复制给规则失败");}
			if (ServiceConstants.LabelRule.ELEMENT_TYPE_LIST_ID == entity.getElementType()) {
				LabelInfo labelInfo = iLabelInfoService.selectLabelInfoById(entity.getCalcuElement());
				rule.setAttrName(labelInfo.getLabelName());
                rule.setCustomOrLabelName(labelInfo.getLabelName());
			}
			if (ServiceConstants.LabelRule.ELEMENT_TYPE_LABEL_ID == entity.getElementType()) {
				LabelInfo labelInfo = CocCacheProxy.getCacheProxy().getLabelInfoById( entity.getCalcuElement());
				rule.setLabelTypeId(labelInfo.getLabelTypeId());
				rule.setAttrName(labelInfo.getLabelName());
				rule.setCustomOrLabelName(labelInfo.getLabelName());
				rule.setUpdateCycle(labelInfo.getUpdateCycle());
				rule.setDataDate(labelInfo.getDataDate());
				rule.setQueryWay("1");
				if (ServiceConstants.LabelInfo.LABEL_TYPE_ID_VERT == labelInfo.getLabelTypeId()) {
					List<LabelRuleVo> childRuleList = new ArrayList<LabelRuleVo>();
					// 纵表标签列关系
					Map<String, LabelVerticalColumnRel> map = new HashMap<String, LabelVerticalColumnRel>();
					List<LabelVerticalColumnRel> verticalColumnRels = labelInfo.getVerticalColumnRels();
					for (LabelVerticalColumnRel rel : verticalColumnRels) {
						String key = rel.getLabelVerticalColumnRelId().getColumnId();
						map.put(key, rel);
					}
					for (LabelRule childRule : childrenRulesList) {
						if (!rule.getRuleId().equals(childRule.getParentId()))
							continue;
						LabelRuleVo vo=new LabelRuleVo();
						try {
							BeanUtils.copyProperties(vo, childRule);
						} catch (Exception e) {LogUtil.info("将值复制给规失败");}
						LabelVerticalColumnRel rel = map.get(childRule.getCalcuElement());
						MdaSysTableColumn column = rel.getMdaSysTableColumn();
						vo.setLabelTypeId(rel.getLabelTypeId());
						vo.setAttrName(column.getColumnCnName());
						vo.setColumnCnName(column.getColumnCnName());
						vo.setQueryWay("1");
						childRuleList.add(vo);
					}
					rule.setChildLabelRuleList(childRuleList);
				}//end 纵表
			}//end 标签
			list.add(rule);
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
		if (ciLabelRuleListTemp != null && !ciLabelRuleListTemp.isEmpty()) {
			for (int i = 0; i < ciLabelRuleListTemp.size(); i++) {
				LabelRuleVo labelRule = ciLabelRuleListTemp.get(i);
				int elementType = labelRule.getElementType();
				if(elementType == ServiceConstants.LabelRule.ELEMENT_TYPE_BRACKET){
					rule.append(labelRule.getCalcuElement());
				}else if(elementType == ServiceConstants.LabelRule.ELEMENT_TYPE_OPERATOR){
					String operE = "";
					if(ServiceConstants.LabelRule.CALCU_ELEMENT_AND.equals(labelRule.getCalcuElement())){
						operE = "且";
					}else if(ServiceConstants.LabelRule.CALCU_ELEMENT_OR.equals(labelRule.getCalcuElement())){
						operE = "或";
					}else{
						operE = "剔除";
					}
					rule.append(operE);
				}else if(elementType == ServiceConstants.LabelRule.ELEMENT_TYPE_LABEL_ID){
					if(labelRule.getLabelFlag() != ServiceConstants.LABEL_RULE_FLAG_YES && labelRule.getLabelTypeId() !=ServiceConstants.LabelInfo.LABEL_TYPE_ID_SIGN){
						rule.append("(非)");
					}
					rule.append(labelRule.getCustomOrLabelName());
					String attrVal = ruleAttrVal(labelRule);
					if(StringUtil.isNotEmpty(attrVal)){
						rule.append("[");
						rule.append(attrVal);
						rule.append("]");
					}
				}else if(elementType == ServiceConstants.LabelRule.ELEMENT_TYPE_LIST_ID){
					rule.append("客户群：");
					rule.append(labelRule.getCustomOrLabelName());
					if(StringUtil.isNotEmpty(labelRule.getAttrVal())){
						rule.append("[已选清单：");
						rule.append(labelRule.getAttrVal());
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
		if(labelRule.getLabelTypeId() == ServiceConstants.LabelInfo.LABEL_TYPE_ID_SIGN){//01型
			if(labelRule.getLabelFlag() != ServiceConstants.LABEL_RULE_FLAG_YES){
				attrValStr.append("否");
			}else{
				attrValStr.append("是");
			}
		}else if(labelRule.getLabelTypeId() == ServiceConstants.LabelInfo.LABEL_TYPE_ID_KPI){
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
		}else if(labelRule.getLabelTypeId() == ServiceConstants.LabelInfo.LABEL_TYPE_ID_ENUM ){
			if(StringUtil.isNotEmpty(labelRule.getAttrVal())){
				attrValStr.append("已选择条件：");
				attrValStr.append(labelRule.getAttrName());
			}
		}else if(labelRule.getLabelTypeId() == ServiceConstants.LabelInfo.LABEL_TYPE_ID_DATE){
			//
			
		}else if(labelRule.getLabelTypeId() == ServiceConstants.LabelInfo.LABEL_TYPE_ID_TEXT){
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
		}else if(labelRule.getLabelTypeId() == ServiceConstants.LabelInfo.LABEL_TYPE_ID_VERT){
			List<LabelRuleVo> childLabelRuleList = labelRule.getChildLabelRuleList();
			if(childLabelRuleList != null && !childLabelRuleList.isEmpty()){
				for (int i = 0; i < childLabelRuleList.size(); i++) {
					LabelRuleVo rule = childLabelRuleList.get(i);
					if(rule.getLabelTypeId() == ServiceConstants.LabelInfo.LABEL_TYPE_ID_SIGN){//01型
						attrValStr.append("[");
						attrValStr.append(rule.getColumnCnName());
						attrValStr.append("：");
						if(labelRule.getLabelFlag() != ServiceConstants.LABEL_RULE_FLAG_YES){
							attrValStr.append("否");
						}else{
							attrValStr.append("是");
						}
						attrValStr.append("]");
					}else if(rule.getLabelTypeId() == ServiceConstants.LabelInfo.LABEL_TYPE_ID_KPI){
						if("1".equals(rule.getQueryWay())){
							attrValStr.append("[");
							attrValStr.append(rule.getColumnCnName());
							attrValStr.append("：");
							if(StringUtil.isNotEmpty(rule.getContiueMinVal())){
								if(rule.getLeftZoneSign().equals(CommonConstants.GE)){
									attrValStr.append("大于等于");
								}
								if(rule.getLeftZoneSign().equals(CommonConstants.GT)){
									attrValStr.append("大于");
								}
								attrValStr.append(rule.getContiueMinVal());
							}
							if(StringUtil.isNotEmpty(rule.getContiueMaxVal())){
								if(rule.getRightZoneSign().equals(CommonConstants.LE)){
									attrValStr.append("小于等于");
								}
								if(rule.getRightZoneSign().equals(CommonConstants.LT)){
									attrValStr.append("小于");
								}
								attrValStr.append(rule.getContiueMaxVal());
							}
							if(StringUtil.isNotEmpty(rule.getUnit())){
								attrValStr.append("(");
								attrValStr.append(rule.getUnit());
								attrValStr.append(")");
							}
							attrValStr.append("]");
						}else if("2".equals(rule.getQueryWay())){
							attrValStr.append(rule.getColumnCnName());
							attrValStr.append("：");
							if(StringUtil.isNotEmpty(rule.getExactValue())){
								attrValStr.append(rule.getExactValue());
								if(StringUtil.isNotEmpty(rule.getUnit())){
									attrValStr.append("(");
									attrValStr.append(rule.getUnit());
									attrValStr.append(")");
									attrValStr.append("]");
								}
							}
						}
					}else if(rule.getLabelTypeId() == ServiceConstants.LabelInfo.LABEL_TYPE_ID_ENUM){
						if(StringUtil.isNotEmpty(rule.getAttrVal())){
							attrValStr.append("[");
							attrValStr.append(rule.getColumnCnName());
							attrValStr.append("：");
							attrValStr.append(rule.getAttrName());
							attrValStr.append("]");
						}
					}else if(rule.getLabelTypeId() == ServiceConstants.LabelInfo.LABEL_TYPE_ID_DATE){
						LogUtil.info("进来了日期型");
					}else if(rule.getLabelTypeId() == ServiceConstants.LabelInfo.LABEL_TYPE_ID_TEXT){
						if("1".equals(rule.getQueryWay())){
							if(StringUtil.isNotEmpty(rule.getDarkValue())){
								attrValStr.append("[");
								attrValStr.append(rule.getColumnCnName());
								attrValStr.append("：");
								attrValStr.append(rule.getDarkValue());
								attrValStr.append("]");
							}
						}else if("2".equals(rule.getQueryWay())){
							if(StringUtil.isNotEmpty(rule.getExactValue())){
								attrValStr.append("[");
								attrValStr.append(rule.getColumnCnName());
								attrValStr.append("：");
								attrValStr.append(rule.getExactValue());
								attrValStr.append("]");
								
							}
						}
					}
				}
			}
		
			
		}
		return attrValStr.toString();
	}

	@Override
	public List<LabelRuleVo> getNewLabelRuleVoList(List<LabelRuleVo> originalList) {
		List<LabelRuleVo> newList = new ArrayList<LabelRuleVo>();
		try {
			StringBuffer sb = new StringBuffer();
			// 用string保存运算元素，用空格分隔
			for (LabelRuleVo c : originalList) {
				sb.append(c.getCalcuElement()).append(" ");
			}
			String oldStr = sb.substring(0, sb.lastIndexOf(" "));
			// 检查str中是否有"-"来判断是否有“剔除”的情况，如果有返回剔除位置
			int firstExceptPos = oldStr.indexOf("-");
			String resultStr = "";
			if (firstExceptPos > 0) {
				// 有“剔除”需要替换剔除，如B剔除A，即B - A，去除剔除后为B and !A，!A(即非A)，记录为_A
				resultStr = ExpressionPaser
						.getNewString(oldStr, firstExceptPos);
			} else {
				// 不含有“剔除”时直接返回原有rule集合
				return originalList;
			}
			String[] resultArr = resultStr.split(" ");
			// 处理带下划线的转换为元素内取反，即原来取反的（0或2），现在不取反（1或3），原来不去反的（1或3），现在取反（0或2），
			for (int i = 0; i < resultArr.length; i++) {
				// 使用前先克隆，保持原有规则不变
				LabelRuleVo rule = originalList.get(i).clone();
				if (resultArr[i].startsWith("_")) {
					rule.setCalcuElement(resultArr[i].replace("_", ""));
					if (rule.getLabelFlag() != null) {
					    int labelFlag0=0;
					    int labelFlag1=1;
					    int labelFlag2=2;
					    int labelFlag3=3;
						if (rule.getLabelFlag() == labelFlag0) {
							rule.setLabelFlag(labelFlag1);
						} else if (rule.getLabelFlag() == labelFlag1) {
							rule.setLabelFlag(labelFlag0);
						} else if (rule.getLabelFlag() == labelFlag2) {
							rule.setLabelFlag(labelFlag3);
						} else if (rule.getLabelFlag() == labelFlag3) {
							rule.setLabelFlag(labelFlag2);
						}
					} else {
						rule.setLabelFlag(0);
					}
				} else {
					rule.setCalcuElement(resultArr[i]);
				}
				newList.add(rule);
			}
		} catch (Exception e) {
			LogUtil.error("转换CiLabelRule List，替换“剔除”错误。", e);
		}
		return newList;
	}
	

}
