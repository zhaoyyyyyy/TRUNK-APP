/*
 * @(#)AllUserMsgServiceImpl.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
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
import com.asiainfo.biapp.si.loc.bd.common.service.impl.BackServiceImpl;
import com.asiainfo.biapp.si.loc.core.prefecture.dao.IAllUserMsgDao;
import com.asiainfo.biapp.si.loc.core.prefecture.entity.AllUserMsg;
import com.asiainfo.biapp.si.loc.core.prefecture.entity.DimOrgLevel;
import com.asiainfo.biapp.si.loc.core.prefecture.entity.DimOrgLevelId;
import com.asiainfo.biapp.si.loc.core.prefecture.service.IAllUserMsgService;
import com.asiainfo.biapp.si.loc.core.prefecture.service.IDimOrgLevelService;
import com.asiainfo.biapp.si.loc.core.prefecture.vo.AllUserMsgVo;
import com.asiainfo.biapp.si.loc.core.prefecture.vo.DimOrgLevelVo;

import net.sf.json.JSONObject;

/**
 * Title : AllUserMsgServiceImpl
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2018年1月24日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2018年1月24日
 */
@Service
@Transactional
public class AllUserMsgServiceImpl extends BaseServiceImpl<AllUserMsg, String>implements IAllUserMsgService {

    @Autowired
    private IAllUserMsgDao iAllUserMsgDao;
    
    @Autowired
    private IDimOrgLevelService iDimOrgLevelService;
    
    @Autowired
    private BackServiceImpl backServiceImpl;

    @Override
    protected BaseDao<AllUserMsg, String> getBaseDao() {
        return iAllUserMsgDao;
    }

    public Page<AllUserMsg> selectAllUserMsgPageList(Page<AllUserMsg> page, AllUserMsgVo allUserMsgVo)
            throws BaseException {
        return iAllUserMsgDao.selectAllUserMsgPageList(page, allUserMsgVo);
    }

    public List<AllUserMsg> selectAllUserMsgList(AllUserMsgVo allUserMsgVo) throws BaseException {
        return iAllUserMsgDao.selectAllUserMsgList(allUserMsgVo);
    }

    public AllUserMsg selectAllUserMsgById(String priKey) throws BaseException {
        if (StringUtils.isBlank(priKey)) {
            throw new ParamRequiredException("ID不能为空");
        }
        return super.get(priKey);
    }

    public void addAllUserMsg(AllUserMsg allUserMsg) throws BaseException {
        String[] dimOrgStr = allUserMsg.getDimOrgLevelStr().split(";");
        AllUserMsgVo allUserMsgVo = new AllUserMsgVo();
        allUserMsgVo.setTableDesc(allUserMsg.getTableDesc());
        List<AllUserMsg> allUserMsgList = selectAllUserMsgList(allUserMsgVo);
        if(!allUserMsgList.isEmpty()){
            throw new ParamRequiredException("全量表名称已存在");
        }
        //去后台查看表是否存在
        boolean isExitDay = backServiceImpl.isExistsTable(allUserMsg.getDayTableName());
        if(!isExitDay){
            throw new ParamRequiredException("日表不存在");
        }
        boolean isExitMonth = backServiceImpl.isExistsTable(allUserMsg.getMonthTableName());
        if(!isExitMonth){
            throw new ParamRequiredException("月表不存在");
        }
        
        super.saveOrUpdate(allUserMsg);
        for(String d : dimOrgStr){
            JSONObject obj = JSONObject.fromObject(d);
            DimOrgLevel dimOrgLevel = new DimOrgLevel();
            DimOrgLevelId newDimOrgLevelId = new DimOrgLevelId();
            newDimOrgLevelId.setOrgColumnName(obj.getString("dimOrgLevelId.orgColumnName"));
            newDimOrgLevelId.setPriKey(allUserMsg.getPriKey());
            dimOrgLevel.setDimOrgLevelId(newDimOrgLevelId);
            dimOrgLevel.setLevelId(Integer.parseInt(obj.getString("levelId")));
            dimOrgLevel.setSortNum(Integer.parseInt(obj.getString("sortNum")));
            iDimOrgLevelService.addDimOrgLevel(dimOrgLevel);
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public void modifyAllUserMsg(AllUserMsg allUserMsg) throws BaseException {
        AllUserMsg oldAll = super.get(allUserMsg.getPriKey());
        AllUserMsgVo allUserMsgVo = new AllUserMsgVo();
        allUserMsgVo.setTableDesc(allUserMsg.getTableDesc());
        List<AllUserMsg> allUserMsgList = selectAllUserMsgList(allUserMsgVo);
        if(!allUserMsgList.isEmpty()&&!oldAll.getTableDesc().equals(allUserMsg.getTableDesc())){
            throw new ParamRequiredException("全量表名称已存在");
        }
        oldAll = fromToBean(allUserMsg,oldAll);
        DimOrgLevelId dimOrgLevelId = new DimOrgLevelId();
        dimOrgLevelId.setPriKey(oldAll.getPriKey());
        DimOrgLevelVo dimOrgLevelVo = new DimOrgLevelVo();
        dimOrgLevelVo.setDimOrgLevelId(dimOrgLevelId);
        List<DimOrgLevel> dimOrgLevelList = iDimOrgLevelService.selectDimOrgLevelList(dimOrgLevelVo);
        for(DimOrgLevel d : dimOrgLevelList){
            iDimOrgLevelService.delete(d.getDimOrgLevelId());
        }
        String[] dimOrgStr = allUserMsg.getDimOrgLevelStr().split(";");
        for(String d : dimOrgStr){
            JSONObject obj = JSONObject.fromObject(d);
            DimOrgLevel dimOrgLevel = new DimOrgLevel();
            DimOrgLevelId newDimOrgLevelId = new DimOrgLevelId();
            newDimOrgLevelId.setOrgColumnName(obj.getString("dimOrgLevelId.orgColumnName"));
            newDimOrgLevelId.setPriKey(allUserMsg.getPriKey());
            dimOrgLevel.setDimOrgLevelId(newDimOrgLevelId);
            dimOrgLevel.setLevelId(Integer.parseInt(obj.getString("levelId")));
            dimOrgLevel.setSortNum(Integer.parseInt(obj.getString("sortNum")));
            iDimOrgLevelService.addDimOrgLevel(dimOrgLevel);
        }
        super.saveOrUpdate(oldAll);
    }

    public void deleteAllUserMsg(String priKey) throws BaseException {
        if (StringUtils.isBlank(priKey)) {
            throw new ParamRequiredException("ID不能为空");
        }
        super.delete(priKey);
        DimOrgLevelId dimOrgLevelId = new DimOrgLevelId();
        dimOrgLevelId.setPriKey(priKey);
        DimOrgLevelVo dimOrgLevelVo = new DimOrgLevelVo();
        dimOrgLevelVo.setDimOrgLevelId(dimOrgLevelId);
        List<DimOrgLevel> dimOrgLevelList = iDimOrgLevelService.selectDimOrgLevelList(dimOrgLevelVo);
        for(DimOrgLevel d : dimOrgLevelList){
            iDimOrgLevelService.delete(d.getDimOrgLevelId());
        }
    }
    
    private AllUserMsg fromToBean(AllUserMsg all, AllUserMsg oldAll) {
        if (StringUtils.isNotBlank(all.getTableDesc())) {
            oldAll.setTableDesc(all.getTableDesc());
        }
        if (StringUtils.isNotBlank(all.getDayTableName())) {
            oldAll.setDayTableName(all.getDayTableName());
        }
        if (StringUtils.isNotBlank(all.getMonthTableName())) {
            oldAll.setMonthTableName(all.getMonthTableName());
        }
        if (StringUtils.isNotBlank(all.getDayMainColumn())) {
            oldAll.setDayMainColumn(all.getDayMainColumn());
        }
        if (StringUtils.isNotBlank(all.getMonthMainColumn())) {
            oldAll.setMonthMainColumn(all.getMonthMainColumn());
        }
        if (StringUtils.isNotBlank(all.getIsPartition())) {
            oldAll.setIsPartition(all.getIsPartition());
        }
        if (StringUtils.isNotBlank(all.getDayPartitionColumn())) {
            oldAll.setDayPartitionColumn(all.getDayPartitionColumn());
        }
        if (StringUtils.isNotBlank(all.getMonthPartitionColumn())) {
            oldAll.setMonthPartitionColumn(all.getMonthPartitionColumn());
        }
        oldAll.setOtherColumn(all.getOtherColumn());
        return oldAll;
    }

}
