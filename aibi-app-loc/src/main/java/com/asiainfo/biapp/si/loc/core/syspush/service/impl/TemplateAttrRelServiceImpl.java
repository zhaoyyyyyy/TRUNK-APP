/*
 * @(#)TemplateAttrRelServiceImpl.java
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
import com.asiainfo.biapp.si.loc.core.syspush.dao.ISysInfoDao;
import com.asiainfo.biapp.si.loc.core.syspush.dao.ITemplateAttrRelDao;
import com.asiainfo.biapp.si.loc.core.syspush.entity.SysInfo;
import com.asiainfo.biapp.si.loc.core.syspush.entity.TemplateAttrRel;
import com.asiainfo.biapp.si.loc.core.syspush.service.ISysInfoService;
import com.asiainfo.biapp.si.loc.core.syspush.service.ITemplateAttrRelService;
import com.asiainfo.biapp.si.loc.core.syspush.vo.SysInfoVo;
import com.asiainfo.biapp.si.loc.core.syspush.vo.TemplateAttrRelVo;

/**
 * Title : TemplateAttrRelServiceImpl
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
public class TemplateAttrRelServiceImpl extends BaseServiceImpl<TemplateAttrRel, String> implements ITemplateAttrRelService{

    @Autowired
    private ITemplateAttrRelDao iTemplateAttrRelDao;
    
    @Override
    protected BaseDao<TemplateAttrRel, String> getBaseDao() {
        return iTemplateAttrRelDao;
    }
    
    public Page<TemplateAttrRel> selectTemplateAttrRelPageList(Page<TemplateAttrRel> page, TemplateAttrRelVo templateAttrRelVo) throws BaseException {
        return iTemplateAttrRelDao.selectTemplateAttrRelPageList(page, templateAttrRelVo);
    }

    public List<TemplateAttrRel> selectTemplateAttrRelList(TemplateAttrRelVo templateAttrRelVo) throws BaseException {
        return iTemplateAttrRelDao.selectTemplateAttrRelList(templateAttrRelVo);
    }

    public TemplateAttrRel selectTemplateAttrRelById(String templateId, String labelId, String labelColumnId) throws BaseException {
        if(StringUtil.isBlank(templateId)){
            throw new ParamRequiredException("模板编码不能为空");
        }
        if(StringUtil.isBlank(labelId)){
            throw new ParamRequiredException("标签ID不能为空");
        }
        if(StringUtil.isBlank(labelColumnId)){
            throw new ParamRequiredException("标签列名不能为空");
        }
        return super.get(templateId);
    }

    public void addTemplateAttrRel(TemplateAttrRel templateAttrRel) throws BaseException {
        super.saveOrUpdate(templateAttrRel);
    }

    public void modifyTemplateAttrRel(TemplateAttrRel templateAttrRel) throws BaseException {
        super.saveOrUpdate(templateAttrRel);
    }

    public void deleteTemplateAttrRelById(String templateId, String labelId, String labelColumnId) throws BaseException {
        if(StringUtil.isBlank(templateId)){
            throw new ParamRequiredException("模板编码不能为空");
        }
        if(StringUtil.isBlank(labelId)){
            throw new ParamRequiredException("标签ID不能为空");
        }
        if(StringUtil.isBlank(labelColumnId)){
            throw new ParamRequiredException("标签列名不能为空");
        }
        super.delete(templateId);
    }

}
