/*
 * @(#)SourceTableInfoServiceImpl.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.source.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiainfo.biapp.si.loc.base.dao.BaseDao;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.service.impl.BaseServiceImpl;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
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
        if(StringUtils.isEmpty(sourceTableInfo.getSourceTableName())){
            throw new ParamRequiredException("表名称不能为空");
        }
        if(StringUtils.isEmpty(sourceTableInfo.getSourceTableCnName())){
            throw new ParamRequiredException("表中文名称不能为空");
        }
        if(StringUtils.isEmpty(sourceTableInfo.getIdColumn())){
            throw new ParamRequiredException("主键名称不能为空");
        }
        List<String> nameList = new ArrayList<>();
        for(SourceInfo s : sourceTableInfo.getSourceInfoList()){
            if(!nameList.contains(s.getColumnName())){
                nameList.add(s.getColumnName());
            }else{
                throw new ParamRequiredException("字段名称不能重复");
            }
        }
        SourceTableInfoVo sourceTableInfoVo = new SourceTableInfoVo();
        sourceTableInfoVo.setSourceTableName(sourceTableInfo.getSourceTableName());
        List<SourceTableInfo> sourceTableInfoList = selectSourceTableInfoList(sourceTableInfoVo);
        if (!sourceTableInfoList.isEmpty()&&StringUtil.isNotBlank(sourceTableInfoVo.getSourceTableName())) {
            throw new ParamRequiredException("表名称已存在");
        }
        sourceTableInfo.setKeyType(sourceTableInfo.getIdType());
        sourceTableInfo.setCreateTime(new Date());
        sourceTableInfo.setDataExtractionType(0);
        sourceTableInfo.setSourceTableType(1);
        sourceTableInfo.setStatusId(1);
        sourceTableInfo.setDataStore(1);
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
        if(StringUtils.isEmpty(sourceTableInfo.getSourceTableName())){
            throw new ParamRequiredException("表名称不能为空");
        }
        if(StringUtils.isEmpty(sourceTableInfo.getSourceTableCnName())){
            throw new ParamRequiredException("表中文名称不能为空");
        }
        if(StringUtils.isEmpty(sourceTableInfo.getIdColumn())){
            throw new ParamRequiredException("主键名称不能为空");
        }
        List<String> nameList = new ArrayList<>();
        for(SourceInfo s : sourceTableInfo.getSourceInfoList()){
            if(!nameList.contains(s.getColumnName())){
                nameList.add(s.getColumnName());
            }else{
                throw new ParamRequiredException("字段名称不能重复");
            }
        }
        
        SourceTableInfo oldSou = selectSourceTableInfoById(sourceTableInfo.getSourceTableId());
        
        //保存指标源表
        super.saveOrUpdate(fromToBean(sourceTableInfo, oldSou));
        
        //修改状态表
        TargetTableStatus targetTableStatus = iTargetTableStatusService.selectTargertTableStatusById(sourceTableInfo.getSourceTableId());
        if(targetTableStatus != null){
            targetTableStatus.setSourceTableName(sourceTableInfo.getSourceTableName());
            iTargetTableStatusService.modifyTargertTableStatus(targetTableStatus);
        }else{
            throw new ParamRequiredException("指标源表状态表数据错误");
        }
        
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
                    olds.setColumnName(s.getColumnName());
                    olds.setCooColumnType(s.getCooColumnType());
                    olds.setSourceName(s.getSourceName());
                    olds.setColumnCaliber(s.getColumnCaliber());
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
    
    /**
     * 封装实体信息
     *
     * @param sou
     * @param oldSou
     * @return
     */
    public SourceTableInfo fromToBean(SourceTableInfo sou, SourceTableInfo oldSou) {
        if (StringUtil.isNotBlank(sou.getConfigId())) {
            oldSou.setConfigId(sou.getConfigId());
        }
        if (StringUtil.isNotBlank(sou.getSourceFileName())) {
            oldSou.setSourceFileName(sou.getSourceFileName());
        }
        if (StringUtil.isNotBlank(sou.getSourceTableName())) {
            oldSou.setSourceTableName(sou.getSourceTableName());
        }
        if (StringUtil.isNotBlank(sou.getTableSchema())) {
            oldSou.setTableSchema(sou.getTableSchema());
        }
        if (StringUtil.isNotBlank(sou.getSourceTableCnName())) {
            oldSou.setSourceTableCnName(sou.getSourceTableCnName());
        }
        if (null != sou.getSourceTableType()) {
            oldSou.setSourceTableType(sou.getSourceTableType());
        }
        if (StringUtil.isNotBlank(sou.getTableSuffix())) {
            oldSou.setTableSuffix(sou.getTableSuffix());
        }
        if (null != sou.getReadCycle()) {
            oldSou.setReadCycle(sou.getReadCycle());
        }
        if (null != sou.getIdType()) {
            oldSou.setKeyType(sou.getIdType());
            oldSou.setIdType(sou.getIdType());
        }
        if (StringUtil.isNotBlank(sou.getIdColumn())) {
            oldSou.setIdColumn(sou.getIdColumn());
        }
        if (StringUtil.isNotBlank(sou.getIdDataType())) {
            oldSou.setIdDataType(sou.getIdDataType());
        }
        if (StringUtil.isNotBlank(sou.getColumnCaliber())) {
            oldSou.setColumnCaliber(sou.getColumnCaliber());
        }
        if (StringUtil.isNotBlank(sou.getDataSourceId())) {
            oldSou.setDataSourceId(sou.getDataSourceId());
        }
        if (null != sou.getDataSourceType()) {
            oldSou.setDataSourceType(sou.getDataSourceType());
        }
        if (null != sou.getDataExtractionType()) {
            oldSou.setDataExtractionType(sou.getDataExtractionType());
        }
        if (StringUtil.isNotBlank(sou.getDateColumnName())) {
            oldSou.setDateColumnName(sou.getDateColumnName());
        }
        if (StringUtil.isNotBlank(sou.getWhereSql())) {
            oldSou.setWhereSql(sou.getWhereSql());
        }
        if (null != sou.getStatusId()) {
            oldSou.setStatusId(sou.getStatusId());
        }
        if (null != sou.getSourceInfoList()) {
            oldSou.setSourceInfoList(sou.getSourceInfoList());
        }
        return oldSou;
    }
    
//    public List<SourceInfo> parseColumnInfoFile(InputStream inputStream, String fileName) throws Exception{
//        String filePath = null;
//        try {
//            filePath = FileUtil.uploadTargetUserFile(inputStream, fileName);
//        } catch (Exception e1) {
//            LogUtil.error("获取文件目录信息报错", e1);
//        }
//        
//        //解析模版文件
//        List<SourceInfo> sourceInfoList = new ArrayList<SourceInfo>();
//        CSVReader reader = null;
//        // 当前记录条数
//        int currentRowNum = 0;
//        //列数
//        int columnSize = 4;
//
//        InputStream in = new FileInputStream(new File(filePath));
//        Charset charset = FileUtil.getInputStreamCharset(in);
//        reader = new CSVReader(new InputStreamReader(new FileInputStream(
//                new File(filePath)), charset));
//        String[] nextLine;
//        
//        try {
//            while ((nextLine = reader.readNext()) != null) {
//                currentRowNum++;
//                //跳过注释行
//                if(nextLine[0].startsWith("#")) {
//                    continue;
//                }
//                if(nextLine.length < columnSize){
//                    throw new Exception("导入文件错误");
//                }
//                SourceInfo sourceInfo = new SourceInfo();
//                sourceInfo.setColumnName(nextLine[0]);
//                sourceInfo.setCooColumnType(nextLine[1]);
//                sourceInfo.setSourceName(nextLine[2]);
//                sourceInfo.setColumnCaliber(nextLine[3]);
//                
//                sourceInfoList.add(sourceInfo);
//            }
//        } catch (Exception e) {
//            LogUtil.error("parseColumnInfoFile 行号："+currentRowNum+",error:", e);
//            currentRowNum = -1;
//            throw new Exception("导入报错"+e.getMessage(),e);
//        } finally {
//            if(in != null){
//                in.close();
//            }
//            if (reader != null) {
//                reader.close();
//                reader = null;
//            }
//        }
//        LogUtil.info("end parseColumnInfoFile ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
//        return sourceInfoList;
//    }

}
