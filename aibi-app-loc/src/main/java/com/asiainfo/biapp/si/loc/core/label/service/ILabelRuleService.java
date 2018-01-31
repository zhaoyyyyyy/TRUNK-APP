/*
 * @(#)ILabelRuleService.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelRule;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelRuleVo;

/**
 * Title : ILabelRuleService
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
public interface ILabelRuleService extends BaseService<LabelRule, String> {

	/**
	 * 
	 * Description: * 按客户群或者模板Id查找所用的标签规则
	 * 
	 * @param customId
	 *            客户群或者模板Id
	 * @param customType
	 *            1、客户群，2、模板
	 * @return
	 * @throws BaseException
	 *
	 * @author tianxy3
	 * @date 2018年1月12日
	 */
	public List<LabelRuleVo> queryCiLabelRuleList(String customId, Integer customType) throws BaseException;


	/**
	 * Description: 新增一个客户群规则
	 *
	 * @param labelRule
	 * @throws BaseException
	 */
	public void addLabelRule(LabelRule labelRule) throws BaseException;


	/**
	 * Description: 删除客户群规则
	 *
	 * @param ruleId
	 * @throws BaseException
	 */
	public void deleteLabelRule(String ruleId) throws BaseException;
	
	/**
	 * 拼session所有规则
	 * @param ciLabelRuleListTemp
	 * @return
	 */
	public String shopCartRule(List<LabelRuleVo> labelRuleVos);
	
	/**
	 * 根据页面提交的LabelRuleVo的list替换掉“剔除”，返回新可解析成sql的list
	 * @param originalList
	 * @return
	 */
	public List<LabelRuleVo> getNewLabelRuleVoList(List<LabelRuleVo> originalList);

}
