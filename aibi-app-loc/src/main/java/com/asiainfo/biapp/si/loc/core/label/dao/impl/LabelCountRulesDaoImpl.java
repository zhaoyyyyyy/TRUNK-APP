/*
 * @(#)LabelCountRulesDaoImpl.java
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
import com.asiainfo.biapp.si.loc.core.label.dao.ILabelCountRulesDao;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelCountRules;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelCountRulesVo;

/**
 * Title : LabelCountRulesDaoImpl
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
@Repository
public class LabelCountRulesDaoImpl extends BaseDaoImpl<LabelCountRules, String> implements ILabelCountRulesDao {

    public Page<LabelCountRules> findLabelCountRulesPageList(Page<LabelCountRules> page,
            LabelCountRulesVo labelCountRulesVo) {
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from LabelCountRules l where 1=1 ");
        if (StringUtil.isNotBlank(labelCountRulesVo.getCountRulesCode())) {
            hql.append("and l.countRulesCode = :countRulesCode ");
            params.put("countRulesCode", labelCountRulesVo.getCountRulesCode());
        }
        if (null != labelCountRulesVo.getDependIndex()) {
            hql.append("and l.dependIndex = :dependIndex ");
            params.put("dependIndex", labelCountRulesVo.getDependIndex());
        }
        if (null != labelCountRulesVo.getCountRules()) {
            hql.append("and l.countRules = :countRules ");
            params.put("countRules", labelCountRulesVo.getCountRules());
        }
        if (null != labelCountRulesVo.getCountRulesDesc()) {
            hql.append("and l.countRulesDesc = :countRulesDesc ");
            params.put("countRulesDesc", labelCountRulesVo.getCountRulesDesc());
        }
        if (null != labelCountRulesVo.getWhereSql()) {
            hql.append("and l.whereSql = :whereSql ");
            params.put("whereSql", labelCountRulesVo.getWhereSql());
        }
        return super.findPageByHql(page, hql.toString(), params);
    }

    public List<LabelCountRules> findLabelCountRulesList(LabelCountRulesVo labelCountRulesVo) {
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from LabelCountRules l where 1=1 ");
        if (StringUtil.isNotBlank(labelCountRulesVo.getCountRulesCode())) {
            hql.append("and l.countRulesCode = :countRulesCode ");
            params.put("countRulesCode", labelCountRulesVo.getCountRulesCode());
        }
        if (null != labelCountRulesVo.getDependIndex()) {
            hql.append("and l.dependIndex = :dependIndex ");
            params.put("dependIndex", labelCountRulesVo.getDependIndex());
        }
        if (null != labelCountRulesVo.getCountRules()) {
            hql.append("and l.countRules = :countRules ");
            params.put("countRules", labelCountRulesVo.getCountRules());
        }
        if (null != labelCountRulesVo.getCountRulesDesc()) {
            hql.append("and l.countRulesDesc = :countRulesDesc ");
            params.put("countRulesDesc", labelCountRulesVo.getCountRulesDesc());
        }
        if (null != labelCountRulesVo.getWhereSql()) {
            hql.append("and l.whereSql = :whereSql ");
            params.put("whereSql", labelCountRulesVo.getWhereSql());
        }
        return super.findListByHql(hql.toString(), params);
    }

}
