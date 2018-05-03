/*
 * @(#)SysInfoController.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.loc.base.controller.BaseController;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.base.utils.WebResult;
import com.asiainfo.biapp.si.loc.base.utils.model.DES;
import com.asiainfo.biapp.si.loc.core.syspush.entity.SysInfo;
import com.asiainfo.biapp.si.loc.core.syspush.service.ISysInfoService;
import com.asiainfo.biapp.si.loc.core.syspush.vo.SysInfoVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Title : SysInfoController
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
@Api(value = "009.01->-系统信息，记录推送到的系统的ftp等信息",description="王瑞冬")
@RequestMapping("api/syspush")
@RestController
public class SysInfoController extends BaseController<SysInfo>{
    
    @Autowired
    private ISysInfoService iSysInfoService;
    
    private static final String SUCCESS = "success";
    
    @ApiOperation(value = "分页查询推送信息")
    @RequestMapping(value = "/sysInfo/queryPage", method = RequestMethod.POST)
    public Page<SysInfo> list(@ModelAttribute Page<SysInfo> page,@ModelAttribute SysInfoVo sysInfoVo) {
        Page<SysInfo> sysInfoPage = new Page<>();
        try {
            sysInfoPage = iSysInfoService.selectSysInfoPageList(page, sysInfoVo);
        } catch (BaseException e) {
            sysInfoPage.fail(e);
        }
        return sysInfoPage;
    }
    
    @ApiOperation(value = "不分页查询推送信息列表")
    @RequestMapping(value = "/sysInfo/queryList", method = RequestMethod.POST)
    public WebResult<List<SysInfo>> findList(@ModelAttribute SysInfoVo sysInfoVo) {
        WebResult<List<SysInfo>> webResult = new WebResult<>();
        List<SysInfo> sysInfoList = new ArrayList<>();
        try {
            sysInfoList = iSysInfoService.selectSysInfoList(sysInfoVo);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取推送信息成功.", sysInfoList);
    }
    
    @ApiOperation(value = "根据ID查询推送信息")
    @ApiImplicitParam(name = "sysId", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/sysInfo/get",method = RequestMethod.POST)
    public WebResult<SysInfo> findById(String sysId) {
        WebResult<SysInfo> webResult = new WebResult<>();
        SysInfo sysInfo = new SysInfo();
        try {
            sysInfo = iSysInfoService.selectSysInfoById(sysId);
        	String str= DES.decrypt(sysInfo.getFtpPwd());
        	sysInfo.setFtpPwd(str);
        } catch (Exception e) {
        	LogUtil.error("解密出现错误", e);
        }
        return webResult.success("获取推送信息成功", sysInfo);
    }
    
    @ApiOperation(value = "新增推送信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "sysId", value = "系统平台ID", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "sysName", value = "系统平台名称", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "ftpServerIp", value = "FTP IP", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "ftpUser", value = "FTP用户名", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "ftpPwd", value = "FTP密码", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "ftpPath", value = "FTP路径", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "ftpPort", value = "FTP端口", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "localPath", value = "本地路径", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "webserviceWsdl", value = "WEBSERVICE_WSDL", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "webserviceTargetnamespace", value = "WEBSERVICE命名空间", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "webserviceMethod", value = "WEBSERVICE方法", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "webserviceArgs", value = "WEBSERVICE参数", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "descTxt", value = "描述", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "showInPage", value = "是否显示在页面上", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "isNeedXml", value = "是否需要同时上传xml文件", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "isNeedDes", value = "是否需要DES加密", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "desKey", value = "DES加解密密钥", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "isNeedCycle", value = "是否需要周期推送", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "pushClassName", value = "推送功能实现类名", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "isNeedCompress", value = "是否需要压缩", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "compressType", value = "压缩类型", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "isNeedTitle", value = "是否需要表头", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "fileType", value = "文件类型", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "isDuplicateNum", value = "是否推送重复记录数", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "protocoType", value = "协议类型", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "isAllowNolist", value = "是否允许推送无清单", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "isAllowAttr", value = "是否允许推送属性", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "tableNamePre", value = "推送清单表前缀", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "pushType", value = "推送类型", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "customTaskTable", value = "客户群调度信息表", required = false, paramType = "query", dataType = "string")})
    @RequestMapping(value = "/sysInfo/save", method = RequestMethod.POST)
    public WebResult<String> save(@ApiIgnore SysInfo sysInfo) {
            WebResult<String> webResult = new WebResult<>();
            try {
                iSysInfoService.addSysInfo(sysInfo);
            } catch (BaseException e) {
                return webResult.fail(e);
            }
            return webResult.success("新增推送信息成功", SUCCESS);
    }
    
    @ApiOperation(value = "修改推送信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "sysId", value = "系统平台ID", required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "sysName", value = "系统平台名称", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "ftpServerIp", value = "FTP IP", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "ftpUser", value = "FTP用户名", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "ftpPwd", value = "FTP密码", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "ftpPath", value = "FTP路径", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "ftpPort", value = "FTP端口", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "localPath", value = "本地路径", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "webserviceWsdl", value = "WEBSERVICE_WSDL", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "webserviceTargetnamespace", value = "WEBSERVICE命名空间", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "webserviceMethod", value = "WEBSERVICE方法", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "webserviceArgs", value = "WEBSERVICE参数", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "descTxt", value = "描述", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "showInPage", value = "是否显示在页面上", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "isNeedXml", value = "是否需要同时上传xml文件", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "isNeedDes", value = "是否需要DES加密", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "desKey", value = "DES加解密密钥", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "isNeedCycle", value = "是否需要周期推送", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "pushClassName", value = "推送功能实现类名", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "isNeedCompress", value = "是否需要压缩", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "compressType", value = "压缩类型", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "isNeedTitle", value = "是否需要表头", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "fileType", value = "文件类型", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "isDuplicateNum", value = "是否推送重复记录数", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "protocoType", value = "协议类型", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "isAllowNolist", value = "是否允许推送无清单", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "isAllowAttr", value = "是否允许推送属性", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "tableNamePre", value = "推送清单表前缀", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "pushType", value = "推送类型", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "customTaskTable", value = "客户群调度信息表", required = false, paramType = "query", dataType = "string") })
    @RequestMapping(value = "/sysInfo/update", method = RequestMethod.POST)
    public WebResult<String> edit(@ApiIgnore SysInfo sysInfo) {
        WebResult<String> webResult = new WebResult<>();
        SysInfo oldSys = new SysInfo();
        SysInfo sys =new SysInfo();
        try {
        	sys =iSysInfoService.selectSysInfoBySysName(sysInfo.getSysName());
            oldSys = iSysInfoService.selectSysInfoById(sysInfo.getSysId());
            if(!sysInfo.getSysName().equals(oldSys.getSysName()) && null != sys){
                return webResult.fail("平台名称已存在");
            }
            oldSys = fromToBean(sysInfo, oldSys);
            iSysInfoService.modifySysInfo(oldSys);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("修改推送信息成功", SUCCESS);
    }
    
    @ApiOperation(value = "删除推送信息")
    @ApiImplicitParam(name = "sysId", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/sysInfo/delete", method = RequestMethod.POST)
    public WebResult<String> del(String sysId) {
        WebResult<String> webResult = new WebResult<>();
        try {
            iSysInfoService.deleteSysInfoById(sysId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("删除推送信息成功", SUCCESS);
    }
    
    /**
     * 封装实体信息
     *
     * @param sys
     * @param oldSys
     * @return
     */
    public SysInfo fromToBean(SysInfo sys, SysInfo oldSys){
        if(StringUtil.isNoneBlank(sys.getSysId())){
            oldSys.setSysId(sys.getSysId());
        }
        if(StringUtil.isNoneBlank(sys.getSysName())){
            oldSys.setSysName(sys.getSysName());
        }
        if(StringUtil.isNoneBlank(sys.getFtpServerIp())){
            oldSys.setFtpServerIp(sys.getFtpServerIp());
        }
        if(StringUtil.isNoneBlank(sys.getFtpUser())){
            oldSys.setFtpUser(sys.getFtpUser());
        }
        if(StringUtil.isNoneBlank(sys.getFtpPwd())){
            oldSys.setFtpPwd(sys.getFtpPwd());
        }
        if(StringUtil.isNoneBlank(sys.getFtpPath())){
            oldSys.setFtpPath(sys.getFtpPath());
        }
        if(StringUtil.isNoneBlank(sys.getFtpPort())){
            oldSys.setFtpPort(sys.getFtpPort());
        }
        if(StringUtil.isNoneBlank(sys.getLocalPath())){
            oldSys.setLocalPath(sys.getLocalPath());
        }
        if(StringUtil.isNoneBlank(sys.getDescTxt())){
            oldSys.setDescTxt(sys.getDescTxt());
        }
        if(null != sys.getShowInPage()){
            oldSys.setShowInPage(sys.getShowInPage());
        }
        if(null != sys.getPushType()){
            oldSys.setPushType(sys.getPushType());
        }
        if(null != sys.getIsNeedXml()){
            oldSys.setIsNeedXml(sys.getIsNeedXml());
        }
        if(null != sys.getIsNeedDes()){
            oldSys.setIsNeedDes(sys.getIsNeedDes());
        }
        if(StringUtil.isNoneBlank(sys.getDesKey())){
            oldSys.setDesKey(sys.getDesKey());
        }
        if(null != sys.getIsNeedCycle()){
            oldSys.setIsNeedCycle(sys.getIsNeedCycle());
        }
        if(StringUtil.isNoneBlank(sys.getPushClassName())){
            oldSys.setPushClassName(sys.getPushClassName());
        }else{
        	oldSys.setPushClassName(null);
        }
        if(null != sys.getIsNeedCompress()){
            oldSys.setIsNeedCompress(sys.getIsNeedCompress());
        }
        if(null != sys.getCompressType()){
            oldSys.setCompressType(sys.getCompressType());
        }
        if(null != sys.getIsNeedTitle()){
            oldSys.setIsNeedTitle(sys.getIsNeedTitle());
        }
        if(StringUtil.isNoneBlank(sys.getFileType())){
            oldSys.setFileType(sys.getFileType());
        }
        if(null != sys.getIsDuplicateNum()){
            oldSys.setIsDuplicateNum(sys.getIsDuplicateNum());
        }
        if(null != sys.getProtocoType()){
            oldSys.setProtocoType(sys.getProtocoType());
        }
        if(null != sys.getIsAllowNolist()){
            oldSys.setIsAllowNolist(sys.getIsAllowNolist());
        }
        if(null != sys.getIsAllowAttr()){
            oldSys.setIsAllowAttr(sys.getIsAllowAttr());
        }
        if(StringUtil.isNoneBlank(sys.getTableNamePre())){
            oldSys.setTableNamePre(sys.getTableNamePre());
        }
        if(StringUtil.isNoneBlank(sys.getCustomTaskTable())){
            oldSys.setCustomTaskTable(sys.getCustomTaskTable());
        }
        return oldSys;
    }

}
