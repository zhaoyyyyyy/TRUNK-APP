/*
 * @(#)LabelRuleServiceImpl.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.core.label.dao.ILabelRuleDao;
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

    public Page<LabelRule> selectLabelRulePageList(Page<LabelRule> page, LabelRuleVo labelRuleVo) throws BaseException {
        return iLabelRuleDao.selectLabelRulePageList(page, labelRuleVo);
    }

    public List<LabelRule> selectLabelRuleList(LabelRuleVo labelRuleVo) throws BaseException {
        return iLabelRuleDao.selectLabelRuleList(labelRuleVo);
    }

    public LabelRule selectLabelRuleById(String ruleId) throws BaseException {
        if (StringUtils.isBlank(ruleId)) {
            throw new ParamRequiredException("ID不能为空");
        }
        return super.get(ruleId);
    }

    public void addLabelRule(LabelRule labelRule) throws BaseException {
        super.saveOrUpdate(labelRule);
    }

    public void modifyLabelRule(LabelRule labelRule) throws BaseException {
        super.saveOrUpdate(labelRule);
    }

    public void deleteLabelRule(String ruleId) throws BaseException {
        if (StringUtils.isBlank(ruleId)) {
            throw new ParamRequiredException("ID不能为空");
        }
        super.delete(ruleId);
    }

}
