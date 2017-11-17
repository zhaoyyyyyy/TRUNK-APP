/*
 * @(#)SourceInfoServiceImpl.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.sourceconfig.service.impl;

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
import com.asiainfo.biapp.si.loc.core.label.sourceconfig.dao.ISourceInfoDao;
import com.asiainfo.biapp.si.loc.core.label.sourceconfig.entity.SourceInfo;
import com.asiainfo.biapp.si.loc.core.label.sourceconfig.service.ISourceInfoService;
import com.asiainfo.biapp.si.loc.core.label.sourceconfig.vo.SourceInfoVo;

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

    /**
     * 根据条件分页查询
     *
     * @param page
     * @param sourceInfoVo
     * @return
     */
    public Page<SourceInfo> findSourceInfoPageList(Page<SourceInfo> page, SourceInfoVo sourceInfoVo) {
        return iSourceInfoDao.findSourceInfoPageList(page, sourceInfoVo);
    }

    /**
     * 根据条件查询列表
     *
     * @param sourceInfoVo
     * @return
     */
    public List<SourceInfo> findSourceInfoList(SourceInfoVo sourceInfoVo) {
        return iSourceInfoDao.findSourceInfoList(sourceInfoVo);
    }

    /**
     * 通过ID得到一个实体
     *
     * @param sourceId
     * @return
     * @throws BaseException
     */
    public SourceInfo getById(String sourceId) throws BaseException {
        if (StringUtils.isBlank(sourceId)) {
            throw new ParamRequiredException("ID不能为空");
        }
        return super.get(sourceId);
    }

    /**
     * 新增一个实体
     *
     * @param sourceInfo
     * @throws BaseException
     */
    public void saveT(SourceInfo sourceInfo) throws BaseException {
        super.saveOrUpdate(sourceInfo);
    }

    /**
     * 修改一个实体
     *
     * @param sourceInfo
     * @throws BaseException
     */
    public void updateT(SourceInfo sourceInfo) {
        super.saveOrUpdate(sourceInfo);
    }

    /**
     * 通过ID删除一个实体
     *
     * @param sourceId
     * @throws BaseException
     */
    public void deleteById(String sourceId) throws BaseException {
        super.delete(sourceId);
    }

}
