/*
 * @(#)SysInfoDaoImpl.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.asiainfo.biapp.si.loc.base.dao.BaseDaoImpl;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.core.syspush.dao.ISysInfoDao;
import com.asiainfo.biapp.si.loc.core.syspush.entity.SysInfo;
import com.asiainfo.biapp.si.loc.core.syspush.vo.SysInfoVo;

/**
 * Title : SysInfoDaoImpl
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
@Repository
public class SysInfoDaoImpl extends BaseDaoImpl<SysInfo, String> implements ISysInfoDao{
	
	public SysInfo selectSysInfoBySysName(String sysName) {
		 return super.findOneByHql("from SysInfo s where s.sysName = ?0", sysName);
	}

    /**
     * 根据条件分页查询
     *
     * @param page
     * @param SysInfo
     * @return
     */
    public Page<SysInfo> selectSysInfoPageList(Page<SysInfo> page, SysInfoVo sysInfoVo) {
        Map<String, Object> reMap = fromBean(sysInfoVo);
        Map<String, Object> params = (Map<String, Object>)reMap.get("params");
        return super.findPageByHql(page, reMap.get("hql").toString(), params);
    }

    /**
     * 根据条件查询列表
     *
     * @param SysInfo
     * @return
     */
    public List<SysInfo> selectSysInfoList(SysInfoVo sysInfoVo) {
        Map<String, Object> reMap = fromBean(sysInfoVo);
        Map<String, Object> params = (Map<String, Object>)reMap.get("params");
        return super.findListByHql(reMap.get("hql").toString(), params);
    }
    
    public Map<String, Object> fromBean(SysInfoVo sysInfoVo){
        Map<String, Object> reMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        StringBuffer hql = new StringBuffer("from SysInfo s where 1=1 ");
        if(StringUtil.isNoneBlank(sysInfoVo.getSysId())){
            hql.append("and s.sysId = :sysId ");
            params.put("sysId", sysInfoVo.getSysId());
        }
        if(StringUtil.isNoneBlank(sysInfoVo.getSysName())){
            hql.append("and s.sysName LIKE :sysName ");
            params.put("sysName","%" + sysInfoVo.getSysName()+"%");
        }
        if(StringUtil.isNoneBlank(sysInfoVo.getFtpServerIp())){
            hql.append("and s.ftpServerIp = :ftpServerIp ");
            params.put("ftpServerIp", sysInfoVo.getFtpServerIp());
        }
        if(StringUtil.isNoneBlank(sysInfoVo.getFtpUser())){
            hql.append("and s.ftpUser = :ftpUser ");
            params.put("ftpUser", sysInfoVo.getFtpUser());
        }
        if(StringUtil.isNoneBlank(sysInfoVo.getFtpPwd())){
            hql.append("and s.ftpPwd = :ftpPwd ");
            params.put("ftpPwd", sysInfoVo.getFtpPwd());
        }
        if(StringUtil.isNoneBlank(sysInfoVo.getFtpPath())){
            hql.append("and s.ftpPath = :ftpPath ");
            params.put("ftpPath", sysInfoVo.getFtpPath());
        }
        if(StringUtil.isNoneBlank(sysInfoVo.getFtpPort())){
            hql.append("and s.ftpPort = :ftpPort ");
            params.put("ftpPort", sysInfoVo.getFtpPort());
        }
        if(StringUtil.isNoneBlank(sysInfoVo.getLocalPath())){
            hql.append("and s.localPath = :localPath ");
            params.put("localPath", sysInfoVo.getLocalPath());
        }
        if(StringUtil.isNoneBlank(sysInfoVo.getWebserviceWsdl())){
            hql.append("and s.webserviceWsdl = :webserviceWsdl ");
            params.put("webserviceWsdl", sysInfoVo.getWebserviceWsdl());
        }
        if(StringUtil.isNoneBlank(sysInfoVo.getWebserviceTargetnamespace())){
            hql.append("and s.webserviceTargetnamespace = :webserviceTargetnamespace ");
            params.put("webserviceTargetnamespace", sysInfoVo.getWebserviceTargetnamespace());
        }
        if(StringUtil.isNoneBlank(sysInfoVo.getWebserviceMethod())){
            hql.append("and s.webserviceMethod = :webserviceMethod ");
            params.put("webserviceMethod", sysInfoVo.getWebserviceMethod());
        }
        if(StringUtil.isNoneBlank(sysInfoVo.getWebserviceArgs())){
            hql.append("and s.webserviceArgs = :webserviceArgs ");
            params.put("webserviceArgs", sysInfoVo.getWebserviceArgs());
        }
        if(StringUtil.isNoneBlank(sysInfoVo.getDescTxt())){
            hql.append("and s.descTxt = :descTxt ");
            params.put("descTxt", sysInfoVo.getDescTxt());
        }
        if (null != sysInfoVo.getPushType()){
            hql.append("and s.pushType = :pushType ");
            params.put("pushType", sysInfoVo.getPushType());
        }
        if (null != sysInfoVo.getShowInPage()){
            hql.append("and s.showInPage = :showInPage ");
            params.put("showInPage", sysInfoVo.getShowInPage());
        }
        if (null != sysInfoVo.getIsNeedXml()){
            hql.append("and s.isNeedXml = :isNeedXml ");
            params.put("isNeedXml", sysInfoVo.getIsNeedXml());
        }
        if (null != sysInfoVo.getIsNeedDes()){
            hql.append("and s.isNeedDes = :isNeedDes ");
            params.put("isNeedDes", sysInfoVo.getIsNeedDes());
        }
        if(StringUtil.isNoneBlank(sysInfoVo.getDesKey())){
            hql.append("and s.desKey = :desKey ");
            params.put("desKey", sysInfoVo.getDesKey());
        }
        if (null != sysInfoVo.getIsNeedCycle()){
            hql.append("and s.isNeedCycle = :isNeedCycle ");
            params.put("isNeedCycle", sysInfoVo.getIsNeedCycle());
        }
        if(StringUtil.isNoneBlank(sysInfoVo.getPushClassName())){
            hql.append("and s.pushClassName = :pushClassName ");
            params.put("pushClassName", sysInfoVo.getPushClassName());
        }
        if (null != sysInfoVo.getIsNeedCompress()){
            hql.append("and s.isNeedCompress = :isNeedCompress ");
            params.put("isNeedCompress", sysInfoVo.getIsNeedCompress());
        }
        if (null != sysInfoVo.getCompressType()){
            hql.append("and s.compressType = :compressType ");
            params.put("compressType", sysInfoVo.getCompressType());
        }
        if (null != sysInfoVo.getIsNeedTitle()){
            hql.append("and s.isNeedTitle = :isNeedTitle ");
            params.put("isNeedTitle", sysInfoVo.getIsNeedTitle());
        }
        if(StringUtil.isNoneBlank(sysInfoVo.getFileType())){
            hql.append("and s.fileType = :fileType ");
            params.put("fileType", sysInfoVo.getFileType());
        }
        if (null != sysInfoVo.getIsDuplicateNum()){
            hql.append("and s.isDuplicateNum = :isDuplicateNum ");
            params.put("isDuplicateNum", sysInfoVo.getIsDuplicateNum());
        }
        if (null != sysInfoVo.getProtocoType()){
            hql.append("and s.protocoType = :protocoType ");
            params.put("protocoType", sysInfoVo.getProtocoType());
        }
        if (null != sysInfoVo.getIsAllowNolist()){
            hql.append("and s.isAllowNolist = :isAllowNolist ");
            params.put("isAllowNolist", sysInfoVo.getIsAllowNolist());
        }
        if (null != sysInfoVo.getIsAllowAttr()){
            hql.append("and s.isAllowAttr = :isAllowAttr ");
            params.put("isAllowAttr", sysInfoVo.getIsAllowAttr());
        }
        if(StringUtil.isNoneBlank(sysInfoVo.getTableNamePre())){
            hql.append("and s.tableNamePre = :tableNamePre ");
            params.put("tableNamePre", sysInfoVo.getTableNamePre());
        }
        if(StringUtil.isNoneBlank(sysInfoVo.getCustomTaskTable())){
            hql.append("and s.customTaskTable = :customTaskTable ");
            params.put("customTaskTable", sysInfoVo.getCustomTaskTable());
        }
        reMap.put("hql", hql);
        reMap.put("params", params);
        return reMap;
    }

}
