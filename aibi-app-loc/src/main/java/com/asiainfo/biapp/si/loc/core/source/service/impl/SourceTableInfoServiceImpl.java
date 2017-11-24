/*
 * @(#)SourceTableInfoServiceImpl.java
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
import com.asiainfo.biapp.si.loc.core.source.dao.ISourceTableInfoDao;
import com.asiainfo.biapp.si.loc.core.source.entity.SourceTableInfo;
import com.asiainfo.biapp.si.loc.core.source.service.ISourceTableInfoService;
import com.asiainfo.biapp.si.loc.core.source.vo.SourceTableInfoVo;

/**
 * Title : SourceTableInfoServiceImpl
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
public class SourceTableInfoServiceImpl extends BaseServiceImpl<SourceTableInfo, String> implements
        ISourceTableInfoService {

    @Autowired
    private ISourceTableInfoDao iSourceTableInfoDao;

    @Override
    protected BaseDao<SourceTableInfo, String> getBaseDao() {
        return iSourceTableInfoDao;
    }

    public Page<SourceTableInfo> findSourceTableInfoPageList(Page<SourceTableInfo> page,
            SourceTableInfoVo sourceTableInfoVo) throws BaseException {
        return iSourceTableInfoDao.findSourceTableInfoPageList(page, sourceTableInfoVo);
    }

    public List<SourceTableInfo> findSourceTableInfoList(SourceTableInfoVo sourceTableInfoVo) throws BaseException {
        return iSourceTableInfoDao.findSourceTableInfoList(sourceTableInfoVo);
    }

    public SourceTableInfo getById(String sourceTableId) throws BaseException {
        if (StringUtils.isBlank(sourceTableId)) {
            throw new ParamRequiredException("ID不能为空");
        }
        return super.get(sourceTableId);
    }

    public void saveT(SourceTableInfo sourceTableInfo) throws BaseException {
        super.saveOrUpdate(sourceTableInfo);
    }

    public void updateT(SourceTableInfo sourceTableInfo) throws BaseException {
        super.saveOrUpdate(sourceTableInfo);
    }

    public void deleteById(String sourceTableId) throws BaseException {
        if (StringUtils.isBlank(sourceTableId)) {
            throw new ParamRequiredException("ID不能为空");
        }
        super.delete(sourceTableId);
    }

}
