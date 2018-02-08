/*
 * @(#)SysInfoServiceImpl.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.syspush.dao.ISysInfoDao;
import com.asiainfo.biapp.si.loc.core.syspush.entity.SysInfo;
import com.asiainfo.biapp.si.loc.core.syspush.service.ISysInfoService;
import com.asiainfo.biapp.si.loc.core.syspush.vo.SysInfoVo;

/**
 * Title : SysInfoServiceImpl
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
 * <pre>1    2018年1月17日    wangrd        Created</pre>
 * <p/>
 *
 * @author  wangrd
 * @version 1.0.0.2018年1月17日
 */
@Service
@Transactional
public class SysInfoServiceImpl extends BaseServiceImpl<SysInfo, String> implements ISysInfoService{

    @Autowired
    private ISysInfoDao iSysInfoDao;
    
    @Override
    protected BaseDao<SysInfo, String> getBaseDao() {
        return iSysInfoDao;
    }
    
    public Page<SysInfo> selectSysInfoPageList(Page<SysInfo> page, SysInfoVo sysInfoVo) throws BaseException {
        return iSysInfoDao.selectSysInfoPageList(page, sysInfoVo);
    }

    public List<SysInfo> selectSysInfoList(SysInfoVo sysInfoVo) throws BaseException {
        return iSysInfoDao.selectSysInfoList(sysInfoVo);
    }

    public SysInfo selectSysInfoById(String sysId) throws BaseException {
        if(StringUtil.isBlank(sysId)){
            throw new ParamRequiredException("ID不能为空");
        }
        return super.get(sysId);
    }
    
    public SysInfo selectSysInfoBySysName(String sysName) throws BaseException {
        if(StringUtil.isBlank(sysName)){
            throw new ParamRequiredException("名称不能为空");
        }
        return iSysInfoDao.selectSysInfoBySysName(sysName);
    }

    public void addSysInfo(SysInfo sysInfo) throws BaseException {
    	if(null !=iSysInfoDao.selectSysInfoBySysName(sysInfo.getSysName())){
    		 throw new ParamRequiredException("平台名称已存在");
    	}
        super.saveOrUpdate(sysInfo);
    }

    public void modifySysInfo(SysInfo sysInfo) throws BaseException {
        super.saveOrUpdate(sysInfo);
    }

    public void deleteSysInfoById(String sysId) throws BaseException {
        if(StringUtil.isBlank(sysId)){
            throw new ParamRequiredException("ID不能为空");
        }
        super.delete(sysId);
    }

}
