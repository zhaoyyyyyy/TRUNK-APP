/*
 * @(#)DimTargetTableStatusServiceImpl.java
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
import com.asiainfo.biapp.si.loc.core.label.sourceconfig.dao.IDimTargetTableStatusDao;
import com.asiainfo.biapp.si.loc.core.label.sourceconfig.entity.DimTargetTableStatus;
import com.asiainfo.biapp.si.loc.core.label.sourceconfig.service.IDimTargetTableStatusService;
import com.asiainfo.biapp.si.loc.core.label.sourceconfig.vo.DimTargetTableStatusVo;

/**
 * Title : DimTargetTableStatusServiceImpl
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
public class DimTargetTableStatusServiceImpl extends BaseServiceImpl<DimTargetTableStatus, String> implements
        IDimTargetTableStatusService {

    @Autowired
    private IDimTargetTableStatusDao iDimTargetTableStatusDao;

    @Override
    protected BaseDao<DimTargetTableStatus, String> getBaseDao() {
        return iDimTargetTableStatusDao;
    }

    /**
     * 根据条件分页查询
     *
     * @param page
     * @param dimTargetTableStatusVo
     * @return
     */
    public Page<DimTargetTableStatus> findDimTargetTableStatusPageList(Page<DimTargetTableStatus> page,
            DimTargetTableStatusVo dimTargetTableStatusVo) {
        return iDimTargetTableStatusDao.findDimTargetTableStatusPageList(page, dimTargetTableStatusVo);
    }

    /**
     * 根据条件查询列表
     *
     * @param dimTargetTableStatusVo
     * @return
     */
    public List<DimTargetTableStatus> findDimTargetTableStatusList(DimTargetTableStatusVo dimTargetTableStatusVo) {
        return iDimTargetTableStatusDao.findDimTargetTableStatusList(dimTargetTableStatusVo);
    }

    /**
     * 根据ID得到一个实体
     *
     * @param labelId
     * @return
     * @throws BaseException
     */
    public DimTargetTableStatus getById(String labelId) throws BaseException {
        if (StringUtils.isBlank(labelId)) {
            throw new ParamRequiredException("ID不能为空");
        }
        return super.get(labelId);
    }

    /**
     * 新增一个实体
     *
     * @param dimTargetTableStatus
     * @throws BaseException
     */
    public void saveT(DimTargetTableStatus dimTargetTableStatus) throws BaseException {
        super.saveOrUpdate(dimTargetTableStatus);
    }

    /**
     * 修改一个实体
     *
     * @param dimTargetTableStatus
     * @throws BaseException
     */
    public void updateT(DimTargetTableStatus dimTargetTableStatus) {
        super.saveOrUpdate(dimTargetTableStatus);
    }

    /**
     * 根据ID删除一个实体
     *
     * @param labelId
     * @throws BaseException
     */
    public void deleteById(String labelId) throws BaseException {
        super.delete(labelId);
    }

}
