/*
 * @(#)ApproveInfoServiceImpl.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.label.dao.IApproveInfoDao;
import com.asiainfo.biapp.si.loc.core.label.entity.ApproveInfo;
import com.asiainfo.biapp.si.loc.core.label.service.IApproveInfoService;

/**
 * Title : ApproveInfoServiceImpl
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2017年12月15日    lilin7        Created</pre>
 * <p/>
 *
 * @author  lilin7
 * @version 1.0.0.2017年12月15日
 */
@Service
@Transactional
public class ApproveInfoServiceImpl extends BaseServiceImpl<ApproveInfo, String> implements IApproveInfoService{

    @Autowired
    private IApproveInfoDao iApproveInfoDao;
    
    @Override
    protected BaseDao<ApproveInfo, String> getBaseDao() {
        return iApproveInfoDao;
    }

    @Override
    public ApproveInfo selectApproveInfo(String resourceId) throws BaseException {
        if (StringUtil.isBlank(resourceId)) {
            throw new ParamRequiredException("id不能为空");
        }
        return iApproveInfoDao.selectApproveInfo(resourceId);
    }
    
    
    public void deleteApproveInfo(String resourceId) throws BaseException{
        if (StringUtil.isBlank(resourceId)) {
            throw new ParamRequiredException("id不能为空");
        }
        ApproveInfo approveInfo = iApproveInfoDao.selectApproveInfo(resourceId);
        iApproveInfoDao.deleteObject(approveInfo);
    }
    
    @Override
    public void addApproveInfo(ApproveInfo approveInfo) {
       super.saveOrUpdate(approveInfo);
    }

}
