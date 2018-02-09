/*
 * @(#)ILabelCountRulesDao.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.dao;

import java.util.List;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelCountRules;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelCountRulesVo;

/**
 * Title : ILabelCountRulesDao
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
public interface ILabelCountRulesDao extends BaseDao<LabelCountRules, String> {

    /**
     * Description: 分页查询
     *
     * @param page
     * @param labelCountRulesVo
     * @return
     */
    public Page<LabelCountRules> selectLabelCountRulesPageList(Page<LabelCountRules> page,
            LabelCountRulesVo labelCountRulesVo);

    /**
     * Description: 查询列表
     *
     * @param labelCountRulesVo
     * @return
     */
    public List<LabelCountRules> selectLabelCountRulesList(LabelCountRulesVo labelCountRulesVo);

}
