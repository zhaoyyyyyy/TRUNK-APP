/*
 * @(#)LabelPushReqServiceImpl.java
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
import com.asiainfo.biapp.si.loc.core.syspush.dao.ILabelPushReqDao;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelPushReq;
import com.asiainfo.biapp.si.loc.core.syspush.service.ILabelPushReqService;
import com.asiainfo.biapp.si.loc.core.syspush.vo.LabelPushReqVo;

/**
 * Title : LabelPushReqServiceImpl
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
 * <pre>1    2018年1月18日    wangrd        Created</pre>
 * <p/>
 *
 * @author  wangrd
 * @version 1.0.0.2018年1月18日
 */
@Service
@Transactional
public class LabelPushReqServiceImpl extends BaseServiceImpl<LabelPushReq, String> implements ILabelPushReqService{

    @Autowired
    private ILabelPushReqDao iLabelPushReqDao;
    
    @Override
    protected BaseDao<LabelPushReq, String> getBaseDao() {
        return iLabelPushReqDao;
    }
    
    public Page<LabelPushReq> selectLabelPushReqPageList(Page<LabelPushReq> page, LabelPushReqVo labelPushReqVo)
            throws BaseException {
        return iLabelPushReqDao.selectLabelPushReqPageList(page, labelPushReqVo);
    }

    public List<LabelPushReq> selectLabelPushReqList(LabelPushReqVo labelPushReqVo) throws BaseException {
        return iLabelPushReqDao.selectLabelPushReqList(labelPushReqVo);
    }

    public LabelPushReq selectLabelPushReqById(String reqId) throws BaseException {
        if(StringUtil.isBlank(reqId)){
            throw new ParamRequiredException("ID不能为空");
        }
        return super.get(reqId);
    }

    public void addLabelPushReq(LabelPushReq labelPushReq) throws BaseException {
        super.saveOrUpdate(labelPushReq);
    }

    public void modifyLabelPushReq(LabelPushReq labelPushReq) throws BaseException {
        super.saveOrUpdate(labelPushReq);
    }

    public void deleteLabelPushReqById(String reqId) throws BaseException {
        if(StringUtil.isBlank(reqId)){
            throw new ParamRequiredException("ID不能为空");
        }
        super.delete(reqId);
    }
}
