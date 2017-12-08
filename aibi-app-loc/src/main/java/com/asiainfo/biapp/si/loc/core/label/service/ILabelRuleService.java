/*
 * @(#)ILabelRuleService.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
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
     * Description: 分页查询客户群规则
     *
     * @param page
     * @param labelRuleVo
     * @return
     * @throws BaseException
     */
    public Page<LabelRule> selectLabelRulePageList(Page<LabelRule> page, LabelRuleVo labelRuleVo) throws BaseException;

    /**
     * Description: 查询客户群规则列表
     *
     * @param labelRuleVo
     * @return
     * @throws BaseException
     */
    public List<LabelRule> selectLabelRuleList(LabelRuleVo labelRuleVo) throws BaseException;

    /**
     * Description: 根据ID拿到客户群规则
     *
     * @param ruleId
     * @return
     * @throws BaseException
     */
    public LabelRule selectLabelRuleById(String ruleId) throws BaseException;

    /**
     * Description: 新增一个客户群规则
     *
     * @param labelRule
     * @throws BaseException
     */
    public void addLabelRule(LabelRule labelRule) throws BaseException;

    /**
     * Description: 修改客户群规则
     *
     * @param labelRule
     * @throws BaseException
     */
    public void modifyLabelRule(LabelRule labelRule) throws BaseException;

    /**
     * Description: 删除客户群规则
     *
     * @param ruleId
     * @throws BaseException
     */
    public void deleteLabelRule(String ruleId) throws BaseException;

}
