/*
 * @(#)SourceTableInfoServiceImpl.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.source.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.core.source.dao.ISourceTableInfoDao;
import com.asiainfo.biapp.si.loc.core.source.entity.SourceInfo;
import com.asiainfo.biapp.si.loc.core.source.entity.SourceTableInfo;
import com.asiainfo.biapp.si.loc.core.source.entity.TargetTableStatus;
import com.asiainfo.biapp.si.loc.core.source.service.ISourceInfoService;
import com.asiainfo.biapp.si.loc.core.source.service.ISourceTableInfoService;
import com.asiainfo.biapp.si.loc.core.source.service.ITargetTableStatusService;
import com.asiainfo.biapp.si.loc.core.source.vo.SourceInfoVo;
import com.asiainfo.biapp.si.loc.core.source.vo.SourceTableInfoVo;

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
    
    @Autowired
    private ISourceInfoService iSourceInfoService;
    
    @Autowired
    private ITargetTableStatusService iTargetTableStatusService;

    @Override
    protected BaseDao<SourceTableInfo, String> getBaseDao() {
        return iSourceTableInfoDao;
    }

    public Page<SourceTableInfo> selectSourceTableInfoPageList(Page<SourceTableInfo> page,
            SourceTableInfoVo sourceTableInfoVo) throws BaseException {
        return iSourceTableInfoDao.selectSourceTableInfoPageList(page, sourceTableInfoVo);
    }

    public List<SourceTableInfo> selectSourceTableInfoList(SourceTableInfoVo sourceTableInfoVo) throws BaseException {
        return iSourceTableInfoDao.selectSourceTableInfoList(sourceTableInfoVo);
    }

    public SourceTableInfo selectSourceTableInfoById(String sourceTableId) throws BaseException {
        if (StringUtils.isBlank(sourceTableId)) {
            throw new ParamRequiredException("ID不能为空");
        }
        return super.get(sourceTableId);
    }

    public void addSourceTableInfo(SourceTableInfo sourceTableInfo) throws BaseException {
        if(!sourceTableInfo.getSourceInfoList().isEmpty()&&"please-format-sourceInfoList".equals(sourceTableInfo.getSourceInfoList().get(0).getSourceTableId())){
            throw new ParamRequiredException("指标信息列表格式不正确");
        }
        if(StringUtil.isEmpty(sourceTableInfo.getSourceTableName())){
            throw new ParamRequiredException("表名称不能为空");
        }
        if(StringUtil.isEmpty(sourceTableInfo.getSourceTableCnName())){
            throw new ParamRequiredException("表中文名称不能为空");
        }
        if(StringUtil.isEmpty(sourceTableInfo.getIdColumn())){
            throw new ParamRequiredException("主键名称不能为空");
        }
        super.saveOrUpdate(sourceTableInfo);
        
        //添加指标源表状态
        TargetTableStatus targetTableStatus = new TargetTableStatus();
        targetTableStatus.setSourceTableId(sourceTableInfo.getSourceTableId());
        targetTableStatus.setSourceTableName(sourceTableInfo.getSourceTableName());
        targetTableStatus.setSourceTableType(sourceTableInfo.getSourceTableType());
        targetTableStatus.setManualExecution(0);
        targetTableStatus.setIsDoing(0);
        targetTableStatus.setDataStatus(1);
        iTargetTableStatusService.addTargertTableStatus(targetTableStatus);
        
        //添加指标信息列
        if(!sourceTableInfo.getSourceInfoList().isEmpty()){
            for(SourceInfo s : sourceTableInfo.getSourceInfoList()){
                if(sourceTableInfo.getIdColumn().equals(s.getSourceName())){
                    continue;
                }
                s.setSourceColumnRule(s.getColumnName());
                s.setSourceTableId(sourceTableInfo.getSourceTableId());
                iSourceInfoService.addSourceInfo(s);
                SourceInfo newS = iSourceInfoService.selectSourceInfoById(s.getSourceId());
                newS.setDepositColumn("L"+newS.getSourceId());
                iSourceInfoService.modifySourceInfo(newS);
            }
        }
    }

    public void modifySourceTableInfo(SourceTableInfo sourceTableInfo) throws BaseException {
        if(!sourceTableInfo.getSourceInfoList().isEmpty()&&"please-format-sourceInfoList".equals(sourceTableInfo.getSourceInfoList().get(0).getSourceTableId())){
            throw new ParamRequiredException("指标信息列表格式不正确");
        }
        if(StringUtil.isEmpty(sourceTableInfo.getSourceTableName())){
            throw new ParamRequiredException("表名称不能为空");
        }
        if(StringUtil.isEmpty(sourceTableInfo.getSourceTableCnName())){
            throw new ParamRequiredException("表中文名称不能为空");
        }
        if(StringUtil.isEmpty(sourceTableInfo.getIdColumn())){
            throw new ParamRequiredException("主键名称不能为空");
        }
        List<String> nameList = new ArrayList<>();
        for(SourceInfo s : sourceTableInfo.getSourceInfoList()){
            if(!nameList.contains(s.getSourceName())){
                nameList.add(s.getSourceName());
            }else{
                throw new ParamRequiredException("字段名称不能重复");
            }
        }
        
        //保存指标源表
        super.saveOrUpdate(sourceTableInfo);
        
        //修改状态表
        TargetTableStatus targetTableStatus = iTargetTableStatusService.selectTargertTableStatusById(sourceTableInfo.getSourceTableId());
        targetTableStatus.setSourceTableName(sourceTableInfo.getSourceTableName());
        iTargetTableStatusService.modifyTargertTableStatus(targetTableStatus);
        
        //修改指标信息列
        List<String> newIds = new ArrayList<>();
        if(!sourceTableInfo.getSourceInfoList().isEmpty()){
            for(SourceInfo s : sourceTableInfo.getSourceInfoList()){
                if(sourceTableInfo.getIdColumn().equals(s.getSourceName())){
                    continue;
                }
                s.setSourceColumnRule(s.getColumnName());
                s.setSourceTableId(sourceTableInfo.getSourceTableId());
                if(StringUtils.isNotBlank(s.getSourceId())){
                    SourceInfo olds = iSourceInfoService.selectSourceInfoById(s.getSourceId());
                    olds.setSourceName(s.getSourceName());
                    olds.setCooColumnType(s.getCooColumnType());
                    olds.setColumnCnName(s.getColumnCnName());
                    olds.setColumnUnit(s.getColumnUnit());
                    iSourceInfoService.modifySourceInfo(olds);
                    newIds.add(olds.getSourceId());
                }else{
                    iSourceInfoService.addSourceInfo(s);
                    newIds.add(s.getSourceId());
                }
            }
        }
        SourceInfoVo sourceInfoVo = new SourceInfoVo();
        sourceInfoVo.setSourceTableId(sourceTableInfo.getSourceTableId());
        List<SourceInfo> oldList = iSourceInfoService.selectSourceInfoList(sourceInfoVo);
        for(SourceInfo os : oldList){
            if(newIds.contains(os.getSourceId())){
                continue;
            }else{
                iSourceInfoService.deleteSourceInfo(os.getSourceId());
            }
        }
    }

    public void deleteSourceTableInfo(String sourceTableId) throws BaseException {
        if (StringUtils.isBlank(sourceTableId)) {
            throw new ParamRequiredException("ID不能为空");
        }
        if(selectSourceTableInfoById(sourceTableId)==null){
            throw new ParamRequiredException("ID不存在");
        }
        SourceInfoVo sourceInfoVo = new SourceInfoVo();
        sourceInfoVo.setSourceTableId(sourceTableId);
        List<SourceInfo> delList = iSourceInfoService.selectSourceInfoList(sourceInfoVo);
        for(SourceInfo s : delList){
            iSourceInfoService.deleteSourceInfo(s.getSourceId());
        }
        super.delete(sourceTableId);
        iTargetTableStatusService.deleteTargertTableStatus(sourceTableId);
    }

}
