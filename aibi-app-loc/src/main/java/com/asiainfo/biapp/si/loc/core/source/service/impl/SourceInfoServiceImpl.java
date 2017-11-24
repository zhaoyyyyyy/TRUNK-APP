/*
 * @(#)SourceInfoServiceImpl.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.source.service.impl;

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
import com.asiainfo.biapp.si.loc.core.source.dao.ISourceInfoDao;
import com.asiainfo.biapp.si.loc.core.source.entity.SourceInfo;
import com.asiainfo.biapp.si.loc.core.source.service.ISourceInfoService;
import com.asiainfo.biapp.si.loc.core.source.vo.SourceInfoVo;

/**
 * Title : SourceInfoServiceImpl
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
 * 1    2017年11月15日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月15日
 */
@Service
@Transactional
public class SourceInfoServiceImpl extends BaseServiceImpl<SourceInfo, String> implements ISourceInfoService {

    @Autowired
    private ISourceInfoDao iSourceInfoDao;

    @Override
    protected BaseDao<SourceInfo, String> getBaseDao() {
        return iSourceInfoDao;
    }

    public Page<SourceInfo> findSourceInfoPageList(Page<SourceInfo> page, SourceInfoVo sourceInfoVo)
            throws BaseException {
        return iSourceInfoDao.findSourceInfoPageList(page, sourceInfoVo);
    }

    public List<SourceInfo> findSourceInfoList(SourceInfoVo sourceInfoVo) throws BaseException {
        return iSourceInfoDao.findSourceInfoList(sourceInfoVo);
    }

    public SourceInfo getById(String sourceId) throws BaseException {
        if (StringUtils.isBlank(sourceId)) {
            throw new ParamRequiredException("ID不能为空");
        }
        return super.get(sourceId);
    }

    public void saveT(SourceInfo sourceInfo) throws BaseException {
        super.saveOrUpdate(sourceInfo);
    }

    public void updateT(SourceInfo sourceInfo) throws BaseException {
        super.saveOrUpdate(sourceInfo);
    }

    public void deleteById(String sourceId) throws BaseException {
        if (StringUtils.isBlank(sourceId)) {
            throw new ParamRequiredException("ID不能为空");
        }
        super.delete(sourceId);
    }

}
