/*
 * @(#)TargetTableStatusServiceImpl.java
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
import com.asiainfo.biapp.si.loc.core.source.dao.ITargetTableStatusDao;
import com.asiainfo.biapp.si.loc.core.source.entity.TargetTableStatus;
import com.asiainfo.biapp.si.loc.core.source.service.ITargetTableStatusService;
import com.asiainfo.biapp.si.loc.core.source.vo.TargetTableStatusVo;

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

    public Page<TargetTableStatus> selectTargetTableStatusPageList(Page<TargetTableStatus> page,
            TargetTableStatusVo targetTableStatusVo) throws BaseException {
        return iTargetTableStatusDao.selectTargetTableStatusPageList(page, targetTableStatusVo);
    }

    public List<TargetTableStatus> selectTargetTableStatusList(TargetTableStatusVo targetTableStatusVo)
            throws BaseException {
        return iTargetTableStatusDao.selectTargetTableStatusList(targetTableStatusVo);
    }

    public TargetTableStatus selectTargertTableStatusById(String tableId) throws BaseException {
        if (StringUtils.isBlank(tableId)) {
            throw new ParamRequiredException("ID不能为空");
        }
        return super.get(tableId);
    }

    public void addTargertTableStatus(TargetTableStatus targetTableStatus) throws BaseException {
        super.saveOrUpdate(targetTableStatus);
    }

    public void modifyTargertTableStatus(TargetTableStatus targetTableStatus) throws BaseException {
        super.saveOrUpdate(targetTableStatus);
    }

    public void deleteTargertTableStatus(String tableId) throws BaseException {
        if (StringUtils.isBlank(tableId)) {
            throw new ParamRequiredException("ID不能为空");
        }
        if (selectTargertTableStatusById(tableId) == null) {
            throw new ParamRequiredException("ID不存在");
        }
        super.delete(tableId);
    }

    public String selectLastestDateByCycle(Integer readCycle) throws BaseException {
        if (readCycle == null) {
            throw new ParamRequiredException("读取周期不能为空");
        }
//        TargetTableStatus targetTableStatus = iTargetTableStatusDao.selectLastestDateByCycle(readCycle.intValue());
//        List<TargetTableStatus> targetTableStatuses = iTargetTableStatusDao.selectTargetTableStatusList(new TargetTableStatusVo());
//        if(targetTableStatuses.size() >0 ){
//            
//        }
//        if(targetTableStatus != null && StringUtils.isNotBlank(targetTableStatus.getDataDate())){
//            return targetTableStatus.getDataDate();
//        }
        if(readCycle.intValue() == 1){
            return "2018-04-27";
        }else{
            return "2018-04";
        }
    }
    
}
