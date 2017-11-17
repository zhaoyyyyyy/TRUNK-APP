/*
 * @(#)PreConfigInfoServiceImpl.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.prefecture.service.impl;

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
import com.asiainfo.biapp.si.loc.core.prefecture.dao.IPreConfigInfoDao;
import com.asiainfo.biapp.si.loc.core.prefecture.entity.PreConfigInfo;
import com.asiainfo.biapp.si.loc.core.prefecture.service.IPreConfigInfoService;
import com.asiainfo.biapp.si.loc.core.prefecture.vo.PreConfigInfoVo;

/**
 * Title : PreConfigInfoServiceImpl
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8 +
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2017年11月7日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月7日
 */
@Service
@Transactional
public class PreConfigInfoServiceImpl extends BaseServiceImpl<PreConfigInfo, String> implements IPreConfigInfoService {

    @Autowired
    private IPreConfigInfoDao iPreConfigInfoDao;

    @Override
    protected BaseDao<PreConfigInfo, String> getBaseDao() {
        return iPreConfigInfoDao;
    }

    /**
     * 根据条件分页查询专区
     * 
     * @param page
     * @param preConfigInfo
     * @return
     */
    public Page<PreConfigInfo> findPreConfigInfoPageList(Page<PreConfigInfo> page, PreConfigInfoVo preConfigInfoVo) {
        return iPreConfigInfoDao.findPreConfigInfoPageList(page, preConfigInfoVo);
    }

    /**
     * 根据条件查询专区列表
     * 
     * @param page
     * @param dataSourceInfo
     * @return
     */
    public List<PreConfigInfo> findPreConfigInfoList(PreConfigInfoVo preConfigInfoVo) {
        return iPreConfigInfoDao.findPreConfigInfoList(preConfigInfoVo);
    }

    /**
     * 根据数据源名称查询一个数据源
     * 
     * @param dataSourceName
     * @return
     */
    public PreConfigInfo findOneBySourceName(String sourceName) {
        return iPreConfigInfoDao.findOneBySourceName(sourceName);
    }

    /**
     * 通过主键得到实体
     * 
     * @param id
     * @return
     */
    public PreConfigInfo getById(String configId) throws BaseException {
        if (StringUtils.isBlank(configId)) {
            throw new ParamRequiredException("ID不能为空");
        }
        return super.get(configId);
    }

    /**
     * 新增专区
     *
     * @param preConfigInfo
     * @return
     * @throws BaseException
     */
    public void saveT(PreConfigInfo preConfigInfo) {
        super.saveOrUpdate(preConfigInfo);
    }

    /**
     * 修改专区
     *
     * @param preConfigInfo
     * @return
     * @throws BaseException
     */
    public void updateT(PreConfigInfo preConfigInfo) {
        super.saveOrUpdate(preConfigInfo);
    }

    /**
     * 删除专区
     *
     * @param preConfigInfo
     * @throws BaseException
     */
    public void deleteById(String configId) {
        super.delete(configId);
    }

}
