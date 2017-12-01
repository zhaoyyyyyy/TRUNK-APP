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
        super.saveOrUpdate(preConfigInfo);
    }

    public void modifyPreConfigInfo(PreConfigInfo preConfigInfo) throws BaseException {
        super.saveOrUpdate(preConfigInfo);
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

}
