/*
 * @(#)PreConfigInfoServiceImpl.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.prefecture.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.exception.UserAuthException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.base.utils.DateUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.label.entity.NewestLabelDate;
import com.asiainfo.biapp.si.loc.core.label.service.IMdaSysTableService;
import com.asiainfo.biapp.si.loc.core.label.service.INewestLabelDateService;
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
    
    @Autowired
    private INewestLabelDateService iNewestLabelDateService;

    @Override
    protected BaseDao<PreConfigInfo, String> getBaseDao() {
        return iPreConfigInfoDao;
    }

    public Page<PreConfigInfo> selectPreConfigInfoPageList(Page<PreConfigInfo> page, PreConfigInfoVo preConfigInfoVo,User user)
            throws BaseException {
        if(user.getOrgPrivaliege() == null ||( user.getOrgPrivaliege() != null && user.getOrgPrivaliege().size() == 0)){
            throw new UserAuthException("请给当前用户赋予专区组织权限。");
        }
        return iPreConfigInfoDao.selectPreConfigInfoPageList(page, preConfigInfoVo,user);
    }

    public List<PreConfigInfo> selectPreConfigInfoList(PreConfigInfoVo preConfigInfoVo,User user) throws BaseException {
    	if(user.getOrgPrivaliege() == null ||( user.getOrgPrivaliege() != null && user.getOrgPrivaliege().size() == 0)){
    		throw new UserAuthException("请给当前用户赋予专区组织权限。");
    	}
        return iPreConfigInfoDao.selectPreConfigInfoList(preConfigInfoVo,user);
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
        PreConfigInfo rePre = selectOneBySourceName(preConfigInfo.getSourceName());
        if (null != rePre) {
            throw new ParamRequiredException("专区名称已存在");
        }
        preConfigInfo.setCreateTime(new Date());
        preConfigInfo.setConfigStatus(0);
        super.saveOrUpdate(preConfigInfo);
        //最新数据日期
        NewestLabelDate newestLabelDate = new NewestLabelDate();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sft;
        String monthDate;
        String dayDate;
        calendar.add(Calendar.MONTH, -1);
        sft = new SimpleDateFormat("yyyyMM");
        monthDate = sft.format(calendar.getTime());
        calendar = Calendar.getInstance();;
        sft = new SimpleDateFormat("yyyyMMdd");
        calendar.add(Calendar.DATE,-1);
        dayDate = sft.format(calendar.getTime());
        newestLabelDate.setMonthNewestDate(monthDate);
        newestLabelDate.setDayNewestDate(dayDate);
        newestLabelDate.setConfigId(preConfigInfo.getConfigId());
        iNewestLabelDateService.addNewestLabelDate(newestLabelDate);
        
        iMdaSysTableService.addDWTable(preConfigInfo);
    }

    public void modifyPreConfigInfo(PreConfigInfo preConfigInfo) throws BaseException {
        PreConfigInfo oldPre;
        oldPre = selectPreConfigInfoById(preConfigInfo.getConfigId());
        PreConfigInfo rePre = null;
        if(StringUtil.isNotBlank(preConfigInfo.getSourceName())){
            rePre = selectOneBySourceName(preConfigInfo.getSourceName());
        }
        if (null != rePre &&StringUtil.isNotBlank(preConfigInfo.getSourceName())&& !oldPre.getSourceName().equals(preConfigInfo.getSourceName())) {
            throw new ParamRequiredException("专区名称已存在");
        }
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
        if (null != pre.getAllUserMsg()){
            oldPre.setAllUserMsg(pre.getAllUserMsg());
        }
        return oldPre;
    }
    
    public List<PreConfigInfo> selectEffectivaPreConfigInfo() throws BaseException{
        return iPreConfigInfoDao.selectEffectivaPreConfigInfo();
    }

}
