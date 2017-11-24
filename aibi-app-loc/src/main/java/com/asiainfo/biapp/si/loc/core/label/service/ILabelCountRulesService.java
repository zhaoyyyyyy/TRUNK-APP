/*
 * @(#)ILabelCountRulesService.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.service;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.BaseService;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelCountRules;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelCountRulesVo;

/**
 * Title : ILabelCountRulesService
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
 * 1    2017年11月20日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月20日
 */
public interface ILabelCountRulesService extends BaseService<LabelCountRules, String> {

    /**
     * Description: 分页查询标签规则
     *
     * @param page
     * @param labelCountRulesVo
     * @return
     * @throws BaseException
     */
    public Page<LabelCountRules> findLabelCountRulesPageList(Page<LabelCountRules> page,
            LabelCountRulesVo labelCountRulesVo) throws BaseException;

    /**
     * Description: 查询标签规则列表
     *
     * @param labelCountRulesVo
     * @return
     * @throws BaseException
     */
    public List<LabelCountRules> findLabelCountRulesList(LabelCountRulesVo labelCountRulesVo) throws BaseException;

    /**
     * Description: 通过ID拿到标签规则
     *
     * @param countRulesCode
     * @return
     * @throws BaseException
     */
    public LabelCountRules getById(String countRulesCode) throws BaseException;

    /**
     * Description: 新增一个标签规则
     *
     * @param labelCountRules
     * @throws BaseException
     */
    public void saveT(LabelCountRules labelCountRules) throws BaseException;

    /**
     * Description: 修改标签规则
     *
     * @param labelCountRules
     * @throws BaseException
     */
    public void updateT(LabelCountRules labelCountRules) throws BaseException;

    /**
     * Description: 删除标签规则
     *
     * @param countRulesCode
     * @throws BaseException
     */
    public void deleteById(String countRulesCode) throws BaseException;

}
