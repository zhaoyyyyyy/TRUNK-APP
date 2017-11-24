/*
 * @(#)LabelStatusServiceImpl.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.labelconfig.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.core.label.labelconfig.dao.ILabelStatusDao;
import com.asiainfo.biapp.si.loc.core.label.labelconfig.entity.LabelStatus;
import com.asiainfo.biapp.si.loc.core.label.labelconfig.service.ILabelStatusService;
import com.asiainfo.biapp.si.loc.core.label.labelconfig.vo.LabelStatusVo;

/**
 * Title : LabelStatusServiceImpl
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
 * <pre>1    2017年11月21日     wangrd        Created</pre>
 * <p/>
 *
 * @author   wangrd
 * @version 1.0.0.2017年11月21日
 */
@Service
@Transactional
public class LabelStatusServiceImpl extends BaseServiceImpl<LabelStatus, String> implements ILabelStatusService{

    @Autowired
    private ILabelStatusDao iLabelStatusDao;
    
    @Override
    protected BaseDao<LabelStatus, String> getBaseDao() {
        return iLabelStatusDao;
    }
    
    public Page<LabelStatus> findLabelStatusPageList(Page<LabelStatus> page, LabelStatusVo labelStatusVo) throws BaseException{
        return iLabelStatusDao.findLabelStatusPageList(page, labelStatusVo);
    }

    public List<LabelStatus> findLabelStatusList(LabelStatusVo labelStatusVo) throws BaseException{
        return iLabelStatusDao.findLabelStatusList(labelStatusVo);
    }

    public LabelStatus getById(String labelId) throws BaseException {
        if(StringUtils.isBlank(labelId)){
            throw new ParamRequiredException("Id不能为空");
        }
        return super.get(labelId);
    }

    public void saveT(LabelStatus labelStatus) throws BaseException {
        super.saveOrUpdate(labelStatus);
        
    }

    public void deleteById(String labelId) throws BaseException {
        super.delete(labelId);
        
    }

    public void updateT(LabelStatus labelStatus) throws BaseException {
        super.saveOrUpdate(labelStatus);

    }

    

}
