/*
 * @(#)LabelExeInfoServiceImpl.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */
package com.asiainfo.biapp.si.loc.core.label.service.impl;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.core.label.dao.ILabelExeInfoDao;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelExeInfo;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelExeInfoService;

/**
 * 
 * Title : LabelExeInfoServiceImpl
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
 * <pre>1    2018年5月14日    admin        Created</pre>
 * <p/>
 *
 * @author  shaosq
 * @version 1.0.0.2018年5月14日
 */
@Service
@Transactional
public class LabelExeInfoServiceImpl extends BaseServiceImpl<LabelExeInfo, String>  implements ILabelExeInfoService {

    @Autowired
    private ILabelExeInfoDao iLabelExeInfoDao;

    @Override
    protected BaseDao<LabelExeInfo, String> getBaseDao() {
        return iLabelExeInfoDao;
    }
    
    @Override
    public LabelExeInfo selectLabelExeInfoByLabelId(String labelId,String dataDate) throws BaseException {
        if (StringUtils.isBlank(labelId)) {
            throw new ParamRequiredException("客户群标签ID不能为空！");
        }
        if (StringUtils.isBlank(dataDate)) {
            throw new ParamRequiredException("数据日期不能为空！");
        }
        return iLabelExeInfoDao.selectLabelExeInfoByLabelId(labelId,dataDate);
    }

}
