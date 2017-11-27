/*
 * @(#)LabelCountRulesServiceImpl.java
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
import com.asiainfo.biapp.si.loc.core.label.dao.ILabelCountRulesDao;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelCountRules;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelCountRulesService;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelCountRulesVo;

/**
 * Title : LabelCountRulesServiceImpl
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
@Service
@Transactional
public class LabelCountRulesServiceImpl extends BaseServiceImpl<LabelCountRules, String> implements
        ILabelCountRulesService {

    @Autowired
    private ILabelCountRulesDao iLabelCountRulesDao;

    @Override
    protected BaseDao<LabelCountRules, String> getBaseDao() {
        return iLabelCountRulesDao;
    }

    public Page<LabelCountRules> selectLabelCountRulesPageList(Page<LabelCountRules> page,
            LabelCountRulesVo labelCountRulesVo) {
        return iLabelCountRulesDao.selectLabelCountRulesPageList(page, labelCountRulesVo);
    }

    public List<LabelCountRules> selectLabelCountRulesList(LabelCountRulesVo labelCountRulesVo) {
        return iLabelCountRulesDao.selectLabelCountRulesList(labelCountRulesVo);
    }

    public LabelCountRules selectLabelCountRulesById(String countRulesCode) throws BaseException {
        if (StringUtils.isBlank(countRulesCode)) {
            throw new ParamRequiredException("ID不能为空");
        }
        return super.get(countRulesCode);
    }

    public void addLabelCountRules(LabelCountRules labelCountRules) throws BaseException {
        super.saveOrUpdate(labelCountRules);
    }

    public void modifyLabelCountRules(LabelCountRules labelCountRules) throws BaseException {
        super.saveOrUpdate(labelCountRules);
    }

    public void deleteLabelCountRules(String countRulesCode) throws BaseException {
        if (StringUtils.isBlank(countRulesCode)) {
            throw new ParamRequiredException("ID不能为空");
        }
        super.delete(countRulesCode);
    }

}
