/*
 * @(#)LabelAttrTemplateInfoServiceImpl.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.syspush.dao.ILabelAttrTemplateInfoDao;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelAttrTemplateInfo;
import com.asiainfo.biapp.si.loc.core.syspush.service.ILabelAttrTemplateInfoService;
import com.asiainfo.biapp.si.loc.core.syspush.vo.LabelAttrTemplateInfoVo;

/**
 * Title : LabelAttrTemplateInfoServiceImpl
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2018年1月19日    wangrd        Created</pre>
 * <p/>
 *
 * @author  wangrd
 * @version 1.0.0.2018年1月19日
 */
@Service
@Transactional
public class LabelAttrTemplateInfoServiceImpl extends BaseServiceImpl<LabelAttrTemplateInfo, String> implements ILabelAttrTemplateInfoService{

    @Autowired
    private ILabelAttrTemplateInfoDao iLabelAttrTemplateInfoDao;
    
    @Override
    protected BaseDao<LabelAttrTemplateInfo, String> getBaseDao() {
        return iLabelAttrTemplateInfoDao;
    }
    
    public Page<LabelAttrTemplateInfo> selectLabelAttrTemplateInfoPageList(Page<LabelAttrTemplateInfo> page, LabelAttrTemplateInfoVo labelAttrTemplateInfoVo) throws BaseException {
        return iLabelAttrTemplateInfoDao.selectLabelAttrTemplateInfoPageList(page, labelAttrTemplateInfoVo);
    }

    public List<LabelAttrTemplateInfo> selectLabelAttrTemplateInfoList(LabelAttrTemplateInfoVo labelAttrTemplateInfoVo) throws BaseException {
        return iLabelAttrTemplateInfoDao.selectLabelAttrTemplateInfoList(labelAttrTemplateInfoVo);
    }

    public LabelAttrTemplateInfo selectLabelAttrTemplateInfoById(String templateId) throws BaseException {
        if(StringUtil.isBlank(templateId)){
            throw new ParamRequiredException("ID不能为空");
        }
        return super.get(templateId);
    }

    public void addLabelAttrTemplateInfo(LabelAttrTemplateInfo labelAttrTemplateInfo) throws BaseException {
        super.saveOrUpdate(labelAttrTemplateInfo);
    }

    public void modifyLabelAttrTemplateInfo(LabelAttrTemplateInfo labelAttrTemplateInfo) throws BaseException {
        super.saveOrUpdate(labelAttrTemplateInfo);
    }

    public void deleteLabelAttrTemplateInfoById(String templateId) throws BaseException {
        if(StringUtil.isBlank(templateId)){
            throw new ParamRequiredException("ID不能为空");
        }
        super.delete(templateId);
    }
}
