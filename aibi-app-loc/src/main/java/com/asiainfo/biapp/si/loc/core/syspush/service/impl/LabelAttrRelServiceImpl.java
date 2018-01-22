/*
 * @(#)LabelAttrRelServiceImpl.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.service.impl;

import java.util.Date;
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
import com.asiainfo.biapp.si.loc.core.syspush.dao.ILabelAttrRelDao;
import com.asiainfo.biapp.si.loc.core.syspush.dao.ISysInfoDao;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelAttrRel;
import com.asiainfo.biapp.si.loc.core.syspush.entity.SysInfo;
import com.asiainfo.biapp.si.loc.core.syspush.service.ILabelAttrRelService;
import com.asiainfo.biapp.si.loc.core.syspush.service.ISysInfoService;
import com.asiainfo.biapp.si.loc.core.syspush.vo.LabelAttrRelVo;
import com.asiainfo.biapp.si.loc.core.syspush.vo.SysInfoVo;

/**
 * Title : LabelAttrRelServiceImpl
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
public class LabelAttrRelServiceImpl extends BaseServiceImpl<LabelAttrRel, String> implements ILabelAttrRelService{

    @Autowired
    private ILabelAttrRelDao iLabelAttrRelDao;
    
    @Override
    protected BaseDao<LabelAttrRel, String> getBaseDao() {
        return iLabelAttrRelDao;
    }
    
    public Page<LabelAttrRel> selectLabelAttrRelPageList(Page<LabelAttrRel> page, LabelAttrRelVo labelAttrRelVo) throws BaseException {
        return iLabelAttrRelDao.selectLabelAttrRelPageList(page, labelAttrRelVo);
    }

    public List<LabelAttrRel> selectLabelAttrRelList(LabelAttrRelVo labelAttrRelVo) throws BaseException {
        return iLabelAttrRelDao.selectLabelAttrRelList(labelAttrRelVo);
    }

    public LabelAttrRel selectLabelAttrRelById(String recordId,String labelId, String attrCol, Date modifyTime) throws BaseException {
        if(StringUtil.isBlank(recordId)){
            throw new ParamRequiredException("推送设计记录ID为空");
        }
        if(StringUtil.isBlank(labelId)){
            throw new ParamRequiredException("客户群标签ID为空");
        }
        if(StringUtil.isBlank(attrCol)){
            throw new ParamRequiredException("属性名为空");
        }
        if(null == modifyTime){
            throw new ParamRequiredException("修改时间错误");
        }
        return super.get(recordId);
    }

    public void addLabelAttrRel(LabelAttrRel labelAttrRel) throws BaseException {
        super.saveOrUpdate(labelAttrRel);
    }

    public void modifyLabelAttrRel(LabelAttrRel labelAttrRel) throws BaseException {
        super.saveOrUpdate(labelAttrRel);
    }

    public void deleteLabelAttrRelById(String recordId,String labelId, String attrCol, Date modifyTime) throws BaseException {
        if(StringUtil.isBlank(recordId)){
            throw new ParamRequiredException("推送设计记录ID为空");
        }
        if(StringUtil.isBlank(labelId)){
            throw new ParamRequiredException("客户群标签ID为空");
        }
        if(StringUtil.isBlank(attrCol)){
            throw new ParamRequiredException("属性名为空");
        }
        if(null == modifyTime){
            throw new ParamRequiredException("修改时间错误");
        }
        super.delete(recordId);
    }
}
