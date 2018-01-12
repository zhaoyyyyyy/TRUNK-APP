/*
 * @(#)PreConfigInfoServiceImpl.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.prefecture.service.impl;

import java.util.Date;
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
import com.asiainfo.biapp.si.loc.core.label.service.IMdaSysTableService;
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
    
    @Autowired
    private IMdaSysTableService iMdaSysTableService;

    @Override
    protected BaseDao<PreConfigInfo, String> getBaseDao() {
        return iPreConfigInfoDao;
    }

    public Page<PreConfigInfo> selectPreConfigInfoPageList(Page<PreConfigInfo> page, PreConfigInfoVo preConfigInfoVo)
            throws BaseException {
        return iPreConfigInfoDao.selectPreConfigInfoPageList(page, preConfigInfoVo);
    }

    public List<PreConfigInfo> selectPreConfigInfoList(PreConfigInfoVo preConfigInfoVo) throws BaseException {
        return iPreConfigInfoDao.selectPreConfigInfoList(preConfigInfoVo);
    }

    public PreConfigInfo selectOneBySourceName(String sourceName) throws BaseException {
        if (StringUtils.isBlank(sourceName)) {
            throw new ParamRequiredException("名称不能为空");
        }
        return iPreConfigInfoDao.selectOneBySourceName(sourceName);
    }

    public PreConfigInfo selectPreConfigInfoById(String configId) throws BaseException {
        if (StringUtils.isBlank(configId)) {
            throw new ParamRequiredException("ID不能为空");
        }
        return super.get(configId);
    }

    public void addPreConfigInfo(PreConfigInfo preConfigInfo) throws BaseException {
        preConfigInfo.setCreateTime(new Date());
        preConfigInfo.setConfigStatus(0);
        super.saveOrUpdate(preConfigInfo);
        iMdaSysTableService.addDWTable(preConfigInfo);
    }

    public void modifyPreConfigInfo(PreConfigInfo preConfigInfo) throws BaseException {
        PreConfigInfo oldPre;
        oldPre = selectPreConfigInfoById(preConfigInfo.getConfigId());
        super.saveOrUpdate(fromToBean(preConfigInfo, oldPre));
    }

    public void deletePreConfigInfo(String configId) throws BaseException {
        if (StringUtils.isBlank(configId)) {
            throw new ParamRequiredException("ID不能为空");
        }
        if (selectPreConfigInfoById(configId)==null){
            throw new ParamRequiredException("ID不存在");
        }
        super.delete(configId);
    }
    
    /**
     * 封装专区信息
     * 
     * @param pre
     * @param oldPre
     * @return
     */
    private PreConfigInfo fromToBean(PreConfigInfo pre, PreConfigInfo oldPre) {
        if (StringUtils.isNotBlank(pre.getOrgId())) {
            oldPre.setOrgId(pre.getOrgId());
        }
        if (null != pre.getDataAccessType()) {
            oldPre.setDataAccessType(pre.getDataAccessType());
        }
        if (StringUtils.isNotBlank(pre.getSourceName())) {
            oldPre.setSourceName(pre.getSourceName());
        }
        if (StringUtils.isNotBlank(pre.getSourceEnName())) {
            oldPre.setSourceEnName(pre.getSourceEnName());
        }
        if (StringUtils.isNotBlank(pre.getContractName())) {
            oldPre.setContractName(pre.getContractName());
        }
        if (StringUtils.isNotBlank(pre.getConfigDesc())) {
            oldPre.setConfigDesc(pre.getConfigDesc());
        }
        if (null != pre.getInvalidTime()) {
            oldPre.setInvalidTime(pre.getInvalidTime());
        }
        if (null != pre.getConfigStatus()) {
            oldPre.setConfigStatus(pre.getConfigStatus());
        }
        return oldPre;
    }

}
