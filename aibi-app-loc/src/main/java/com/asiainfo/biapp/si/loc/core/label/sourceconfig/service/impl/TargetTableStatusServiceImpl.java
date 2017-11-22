/*
 * @(#)TargetTableStatusServiceImpl.java
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
import com.asiainfo.biapp.si.loc.core.label.sourceconfig.dao.ITargetTableStatusDao;
import com.asiainfo.biapp.si.loc.core.label.sourceconfig.entity.TargetTableStatus;
import com.asiainfo.biapp.si.loc.core.label.sourceconfig.service.ITargetTableStatusService;
import com.asiainfo.biapp.si.loc.core.label.sourceconfig.vo.TargetTableStatusVo;

/**
 * Title : TargetTableStatusServiceImpl
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
public class TargetTableStatusServiceImpl extends BaseServiceImpl<TargetTableStatus, String> implements
        ITargetTableStatusService {

    @Autowired
    private ITargetTableStatusDao iTargetTableStatusDao;

    @Override
    protected BaseDao<TargetTableStatus, String> getBaseDao() {
        return iTargetTableStatusDao;
    }

    /**
     * 根据条件分页查询
     *
     * @param page
     * @param targetTableStatusVo
     * @return
     */
    public Page<TargetTableStatus> findTargetTableStatusPageList(Page<TargetTableStatus> page,
            TargetTableStatusVo targetTableStatusVo) {
        return iTargetTableStatusDao.findTargetTableStatusPageList(page, targetTableStatusVo);
    }

    /**
     * 根据条件查询列表
     *
     * @param targetTableStatusVo
     * @return
     */
    public List<TargetTableStatus> findTargetTableStatusList(TargetTableStatusVo targetTableStatusVo) {
        return iTargetTableStatusDao.findTargetTableStatusList(targetTableStatusVo);
    }

    /**
     * 根据ID得到一个实体
     *
     * @param labelId
     * @return
     * @throws BaseException
     */
    public TargetTableStatus getById(String labelId) throws BaseException {
        if (StringUtils.isBlank(labelId)) {
            throw new ParamRequiredException("ID不能为空");
        }
        return super.get(labelId);
    }

    /**
     * 新增一个实体
     *
     * @param targetTableStatus
     * @throws BaseException
     */
    public void saveT(TargetTableStatus targetTableStatus) throws BaseException {
        super.saveOrUpdate(targetTableStatus);
    }

    /**
     * 修改一个实体
     *
     * @param targetTableStatus
     * @throws BaseException
     */
    public void updateT(TargetTableStatus targetTableStatus) {
        super.saveOrUpdate(targetTableStatus);
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
