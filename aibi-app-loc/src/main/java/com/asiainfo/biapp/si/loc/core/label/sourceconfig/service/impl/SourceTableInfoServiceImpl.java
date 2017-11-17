/*
 * @(#)SourceTableInfoServiceImpl.java
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
import com.asiainfo.biapp.si.loc.core.label.sourceconfig.dao.ISourceTableInfoDao;
import com.asiainfo.biapp.si.loc.core.label.sourceconfig.entity.SourceTableInfo;
import com.asiainfo.biapp.si.loc.core.label.sourceconfig.service.ISourceTableInfoService;
import com.asiainfo.biapp.si.loc.core.label.sourceconfig.vo.SourceTableInfoVo;

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

    /**
     * 根据条件分页查询
     *
     * @param page
     * @param sourceTableInfoVo
     * @return
     */
    public Page<SourceTableInfo> findSourceTableInfoPageList(Page<SourceTableInfo> page,
            SourceTableInfoVo sourceTableInfoVo) {
        return iSourceTableInfoDao.findSourceTableInfoPageList(page, sourceTableInfoVo);
    }

    /**
     * 根据条件查询列表
     *
     * @param sourceTableInfoVo
     * @return
     */
    public List<SourceTableInfo> findSourceTableInfoList(SourceTableInfoVo sourceTableInfoVo) {
        return iSourceTableInfoDao.findSourceTableInfoList(sourceTableInfoVo);
    }

    /**
     * 通过ID得到一个实体
     *
     * @param sourceTableId
     * @return
     * @throws BaseException
     */
    public SourceTableInfo getById(String sourceTableId) throws BaseException {
        if (StringUtils.isBlank(sourceTableId)) {
            throw new ParamRequiredException("ID不能为空");
        }
        return super.get(sourceTableId);
    }

    /**
     * 新增一个实体
     *
     * @param sourceTableInfo
     * @throws BaseException
     */
    public void saveT(SourceTableInfo sourceTableInfo) throws BaseException {
        super.saveOrUpdate(sourceTableInfo);
    }

    /**
     * 修改一个实体
     *
     * @param sourceTableInfo
     * @throws BaseException
     */
    public void updateT(SourceTableInfo sourceTableInfo) {
        super.saveOrUpdate(sourceTableInfo);
    }

    /**
     * 通过ID删除一个实体
     *
     * @param sourceTableId
     * @throws BaseException
     */
    public void deleteById(String sourceTableId) throws BaseException {
        super.delete(sourceTableId);
    }

}
