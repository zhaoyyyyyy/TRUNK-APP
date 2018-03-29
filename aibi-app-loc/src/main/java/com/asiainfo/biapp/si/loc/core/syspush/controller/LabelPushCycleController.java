/*
 * @(#)LabelPushCycleController.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.base.controller.BaseController;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.utils.DateUtil;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.base.utils.WebResult;
import com.asiainfo.biapp.si.loc.base.utils.model.DES;
import com.asiainfo.biapp.si.loc.cache.CocCacheAble;
import com.asiainfo.biapp.si.loc.cache.CocCacheProxy;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelInfoService;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelInfoVo;
import com.asiainfo.biapp.si.loc.core.syspush.common.constant.ServiceConstants;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelAttrRel;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelPushCycle;
import com.asiainfo.biapp.si.loc.core.syspush.entity.SysInfo;
import com.asiainfo.biapp.si.loc.core.syspush.service.ILabelPushCycleService;
import com.asiainfo.biapp.si.loc.core.syspush.service.ISysInfoService;
import com.asiainfo.biapp.si.loc.core.syspush.vo.LabelPushCycleVo;
import com.asiainfo.biapp.si.loc.core.syspush.vo.LabelPushReqVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Title : LabelPushCycleController
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

@Api(value = "标签推送设置信息表",description="王瑞冬")
@RequestMapping("api/syspush")
@RestController
public class LabelPushCycleController extends BaseController<LabelPushCycle>{

    private static final String FILE_PATH = "syspush" + File.separator +"groupListDownload";  //推送的文件的目录名称
    
    @Autowired
    private ILabelPushCycleService iLabelPushCycleService;
    
    @Autowired
    private ISysInfoService iSysInfoService;
    
    @Autowired
    private ILabelInfoService iLabelInfoService;
    
    private static final String SUCCESS = "success";
    
    @ApiOperation(value = "分页查询推送设置信息")
    @RequestMapping(value = "/labelPushCycle/queryPage", method = RequestMethod.POST)
    public Page<LabelPushCycle> list(@ModelAttribute Page<LabelPushCycle> page,@ModelAttribute LabelPushCycleVo labelPushCycleVo) {
        Page<LabelPushCycle> labelPushCyclePage = new Page<>();
        try {
            labelPushCyclePage = iLabelPushCycleService.selectLabelPushCyclePageList(page, labelPushCycleVo);
        } catch (BaseException e) {
            labelPushCyclePage.fail(e);
        }
        return labelPushCyclePage;
    }
    
    @ApiOperation(value = "不分页查询推送设置信息列表")
    @RequestMapping(value = "/labelPushCycle/queryList", method = RequestMethod.POST)
    public WebResult<List<LabelPushCycle>> findList(@ModelAttribute LabelPushCycleVo labelPushCycleVo) {
        WebResult<List<LabelPushCycle>> webResult = new WebResult<>();
        List<LabelPushCycle> labelPushCycleList = new ArrayList<>();
        try {
            labelPushCycleList = iLabelPushCycleService.selectLabelPushCycleList(labelPushCycleVo);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取推送设置信息成功.", labelPushCycleList);
    }
    
    @ApiOperation(value = "根据ID查询推送设置信息")
    @ApiImplicitParam(name = "recordId", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/labelPushCycle/get",method = RequestMethod.POST)
    public WebResult<LabelPushCycle> findById(String recordId) {
        WebResult<LabelPushCycle> webResult = new WebResult<>();
        LabelPushCycle labelPushCycle = new LabelPushCycle();
        try {
            labelPushCycle = iLabelPushCycleService.selectLabelPushCycleById(recordId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取推送设置信息成功", labelPushCycle);
    }
    
    @ApiOperation(value = "新增推送设置信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "recordId", value = "推送设置记录ID", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "customGroupId", value = "客户群ID", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "sysId", value = "对端系统ID", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "keyType", value = "主键标识类型", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "pushCycle", value = "推送周期", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "pushUserIds", value = "推送目标用户", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "status", value = "状态", required = false, paramType = "query", dataType = "int") })
    @RequestMapping(value = "/labelPushCycle/save", method = RequestMethod.POST)
    public WebResult<String> save(@ApiIgnore LabelPushCycle labelPushCycle) {
            WebResult<String> webResult = new WebResult<>();
            labelPushCycle.setModifyTime(new Date());
            User user = new User(); 
            try {
            	user = this.getLoginUser();
            	String userName = user.getUserName();
                iLabelPushCycleService.addLabelPushCycle(labelPushCycle,userName);
            } catch (BaseException e) {
                return webResult.fail(e);
            }
            return webResult.success("新增推送设置信息成功", SUCCESS);
    }
    
    @ApiOperation(value = "修改推送设置信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "recordId", value = "推送设置记录ID", required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "customGroupId", value = "客户群ID", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "sysId", value = "对端系统ID", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "keyType", value = "主键标识类型", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "pushCycle", value = "推送周期", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "pushUserIds", value = "推送目标用户", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "status", value = "状态", required = false, paramType = "query", dataType = "int") })
    @RequestMapping(value = "/labelPushCycle/update", method = RequestMethod.POST)
    public WebResult<String> edit(@ApiIgnore LabelPushCycle labelPushCycle){
        WebResult<String> webResult = new WebResult<>();
        labelPushCycle.setModifyTime(new Date());
        LabelPushCycle oldLab = new LabelPushCycle();
        try {
            oldLab = iLabelPushCycleService.selectLabelPushCycleById(labelPushCycle.getRecordId());
            oldLab = fromToBean(labelPushCycle, oldLab);
            iLabelPushCycleService.modifyLabelPushCycle(oldLab);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("修改推送设置信息成功", SUCCESS);
    }
    
    @ApiOperation(value = "删除推送设置信息")
    @ApiImplicitParam(name = "recordId", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/labelPushCycle/delete", method = RequestMethod.POST)
    public WebResult<String> del(String recordId) {
        WebResult<String> webResult = new WebResult<>();
        try {
            iLabelPushCycleService.deleteLabelPushCycleById(recordId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("删除推送设置信息成功", SUCCESS);
    }
    
    /**
     * 封装实体信息
     *
     * @param lab
     * @param oldLab
     * @return
     */
    public LabelPushCycle fromToBean(LabelPushCycle lab, LabelPushCycle oldLab){
        if(StringUtil.isNoneBlank(lab.getRecordId())){
            oldLab.setRecordId(lab.getRecordId());
        }
        if(StringUtil.isNoneBlank(lab.getCustomGroupId())){
            oldLab.setCustomGroupId(lab.getCustomGroupId());
        }
        if(StringUtil.isNoneBlank(lab.getSysId())){
            oldLab.setSysId(lab.getSysId());
        }
        if(null != (lab.getKeyType())){
            oldLab.setKeyType(lab.getKeyType());
        }
        if(null != lab.getPushCycle()){
            oldLab.setPushCycle(lab.getPushCycle());
        }
        if(StringUtil.isNoneBlank(lab.getPushUserIds())){
            oldLab.setPushUserIds(lab.getPushUserIds());
        }
        if(null != lab.getModifyTime()){
            oldLab.setModifyTime(lab.getModifyTime());
        }
        if(null != lab.getStatus()){
            oldLab.setStatus(lab.getStatus());
        }
        return oldLab;
    }

    @ApiOperation(value = "清单预览表头")
    @RequestMapping(value = "/labelPushCycle/findGroupListCols", method = RequestMethod.POST)
    public WebResult<List<LabelAttrRel>> findGroupListCols(@ModelAttribute LabelInfoVo customGroup) {
        WebResult<List<LabelAttrRel>> webResult = new WebResult<>();
        List<LabelAttrRel> attrRelList = new ArrayList<>();
        try {
            attrRelList = iLabelPushCycleService.findGroupListCols(customGroup);
        } catch (BaseException e) {
            webResult.fail(e);
        }
        return webResult.success("查询预览清单表头成功！", attrRelList);
    }
    
    @ApiOperation(value = "清单预览数据")
    @RequestMapping(value = "/labelPushCycle/findGroupList", method = RequestMethod.POST)
    public Page<Map<String, String>> findGroupList(@ModelAttribute Page<Map<String, String>> page, @ModelAttribute LabelInfoVo customGroup) {
        try {
            page = iLabelPushCycleService.findGroupList(page,customGroup);
        } catch (BaseException e) {
            page.fail(e);
        }
        return page;
    }
    
    @ApiOperation(value = "清单下载")
    @RequestMapping(value = "/labelPushCycle/downloadGroupList", method = RequestMethod.POST)
    public void downloadGroupList(@ModelAttribute LabelPushCycle labelPushCycle, HttpServletResponse response) {
        //1.走手动推送流程
        //获取属性
        CocCacheAble cacheProxy = CocCacheProxy.getCacheProxy();
        String sftpUsername = cacheProxy.getSYSConfigInfoByKey("LOC_CONFIG_SYS_SFTP_USERNAME");
        String sftpPwd = cacheProxy.getSYSConfigInfoByKey("LOC_CONFIG_SYS_SFTP_PASSWORD");
        String sftpIp = cacheProxy.getSYSConfigInfoByKey("LOC_CONFIG_SYS_SFTP_IP");
        String sftpPort = cacheProxy.getSYSConfigInfoByKey("LOC_CONFIG_SYS_SFTP_PORT");
        String sftpBasePath = cacheProxy.getSYSConfigInfoByKey("LOC_CONFIG_SYS_SFTP_BASE_PATH");
        if (StringUtil.isNotBlank(sftpBasePath)) {
            if (sftpBasePath.endsWith(File.separator)) {
                sftpBasePath = sftpBasePath.replace(File.separator, "");
            } 
        }
        String sysName = "coc默认的ftp服务器";
        
        //保存sysInfo,以便走手动推送流程
        SysInfo sysInfo = null;
        try {//确认数据库中是否存在
            sysInfo = iSysInfoService.selectSysInfoBySysName(sysName);
        } catch (BaseException e) {
            LogUtil.info("根据名称查询平台");
        }
        if (null == sysInfo || StringUtil.isBlank(sysInfo.getSysId())) {//数据库中不存在
            sysInfo = new SysInfo();
            sysInfo.setSysName(sysName);
        }
        sysInfo.setFtpUser(sftpUsername);
        try {
            sysInfo.setFtpPwd(DES.encrypt(sftpPwd));
        } catch (Exception e) {
            LogUtil.error("加密字符串出错！", e);
        }
        sysInfo.setFtpPort(sftpPort);
        sysInfo.setFtpPath(sftpBasePath);
        sysInfo.setFtpServerIp(sftpIp);
        sysInfo.setProtocoType(ServiceConstants.SysInfo.PROTOCO_TYPE_SFTP);
        sysInfo.setShowInPage(ServiceConstants.SysInfo.SHOW_IN_PAGE_NO);
        //本地缓冲目录
        String localPathTmp = cacheProxy.getSYSConfigInfoByKey("LOC_CONFIG_SYS_TEMP_PATH");  
        if (StringUtil.isNotBlank(localPathTmp)) {   //以缓冲目录为准
            if (!localPathTmp.endsWith(File.separator)) {
                localPathTmp += File.separator;
            }
            localPathTmp += FILE_PATH;
        }
        sysInfo.setLocalPath(localPathTmp);
        if (StringUtil.isBlank(sysInfo.getSysId())) {
            iSysInfoService.save(sysInfo);
        } else {
            iSysInfoService.saveOrUpdate(sysInfo);
        }
        
        String sysIds = new StringBuffer(sysInfo.getSysId()).toString();
        labelPushCycle.setSysIds(sysIds);
        LabelInfo customInfo = iLabelInfoService.get(labelPushCycle.getCustomGroupId());
        labelPushCycle.setPushCycle(customInfo.getUpdateCycle());
        WebResult<String> saveRes = this.save(labelPushCycle);

        //2.把推送文件打到浏览器
        //推送文件名称（无路径，无后缀）
        //格式：COC_标签创建人_YYYYMMDDHHMMSS_6位随机数,形如:【COC_admin_20180212150301_981235】
        String fileName = LabelPushReqVo.REQID_PREFIX + customInfo.getCreateUserId() + "_"
                + DateUtil.date2String(new Date(),DateUtil.FORMAT_YYYYMMDD);
        LogUtil.debug("查找文件前缀："+fileName);
        //保存成功
        if (String.valueOf(WebResult.Code.OK).equals(saveRes.getStatus())) {
            //探测并等待文件生成
            int num = 1;
            boolean isSleep = true;
            File dir = new File(localPathTmp);
            /**
             * 获取目录下的所有文件和文件夹
             */
            String[] names = null;
            while (isSleep) {
                try {
                    //等待文件生成
                    Thread.sleep((75+(num*10)*1000));
                    
                    names = dir.list();
                    for (String name : names) {
                        if (name.contains(fileName)) {
                            LogUtil.debug("查找文件完整名称："+name);
                            fileName = name;
                            isSleep = false;
                            Thread.sleep((75+(num*10)*1000));//再睡一会儿，防止文件未写完
                        }
                    }
                    if (num > 20) {//超时失败
                        isSleep = false;
                    }
                } catch (InterruptedException e) {
                    isSleep = false;
                    LogUtil.warn(e);
                }
                num++;
            }
            
            if (!isSleep) {
                //返回文件生成
                localPathTmp += File.separator + fileName;
                BufferedInputStream bis = null;
                response.setContentType("application/octet-stream");
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));  
                
                byte[] buffer = new byte[1024];
                try {
                    String realPath = ResourceUtils.getURL("classpath:").getPath();//获取web项目的路径
                    //在开发测试模式时，得到的地址为：{项目跟目录}/target/classes/
                    //在打包成jar正式发布时，得到的地址为：{发布jar包目录}/
                    if (realPath.endsWith("target/classes/")) {    //次分支只有在dev下运行
                        realPath = realPath.replace("target/classes/", "");
                    }
                    File file = new File(realPath,localPathTmp);
                    if(file.exists()){ //判断文件父目录是否存在
                        bis = new BufferedInputStream(new FileInputStream(file));
                         
                        //输出流
                        OutputStream os = response.getOutputStream();
                        int i = bis.read(buffer);
                        while(i != -1){
                            os.write(buffer, 0, i);
                            os.flush();
                            i = bis.read(buffer);
                        }
                        LogUtil.debug("file download：" + fileName);
                        file.delete();//删除推送的本地文件
                    }
                } catch (IOException e) {
                    LogUtil.error("IO Exception!", e);
                } finally {
                    if (bis != null) {
                        try {
                          bis.close();
                        } catch (IOException e) {
                            LogUtil.error("IO Exception!", e);
                        }
                    }
                }
            }
        }
    }
    
    
}
