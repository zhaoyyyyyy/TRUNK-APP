/*
 * @(#)LabelAttrRelController.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.loc.base.controller.BaseController;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.base.utils.SftpUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.base.utils.WebResult;
import com.asiainfo.biapp.si.loc.base.utils.model.DES;
import com.asiainfo.biapp.si.loc.cache.CocCacheAble;
import com.asiainfo.biapp.si.loc.cache.CocCacheProxy;
import com.asiainfo.biapp.si.loc.core.syspush.entity.CustomDownloadRecord;
import com.asiainfo.biapp.si.loc.core.syspush.entity.SysInfo;
import com.asiainfo.biapp.si.loc.core.syspush.service.ICustomDownloadRecordService;
import com.asiainfo.biapp.si.loc.core.syspush.service.ISysInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Title : LabelAttrRelController
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
 * <pre>1    2018年1月19日    wangrd        Created</pre>
 * <p/>
 *
 * @author  wangrd
 * @version 1.0.0.2018年1月19日
 */

@Api(value = "客户群下载记录",description="会有权限控制")
@RequestMapping("api/syspush")
@RestController
public class CustomDownloadRecordController extends BaseController<CustomDownloadRecord>{

    private static final String SYS_NAME = "coc默认的ftp服务器";

    @Autowired
    private ICustomDownloadRecordService iCustomDownloadRecordService;
    
    @Autowired
    private ISysInfoService iSysInfoService;
    
    @ApiOperation(value = "分页查询客户群下载记录表")
    @RequestMapping(value = "/customDownloadRecord/queryPage", method = RequestMethod.POST)
    public Page<CustomDownloadRecord> list(@ModelAttribute Page<CustomDownloadRecord> page,@ModelAttribute CustomDownloadRecord customDownloadRecord){
        try {
            page = iCustomDownloadRecordService.selectCustomDownloadRecordPageList(page, customDownloadRecord);
        } catch (BaseException e) {
            page.fail(e);
        }
        return page;
    }

    @ApiOperation(value = "更新下载次数")
    @RequestMapping(value = "/customDownloadRecord/updateDownloadNum", method = RequestMethod.POST)
    public WebResult<String> updateDownloadNum(String recordId) throws BaseException{
        CustomDownloadRecord customDownloadRecord = iCustomDownloadRecordService.get(recordId);
        customDownloadRecord.setDownloadNum(String.valueOf(Integer.parseInt(customDownloadRecord.getDownloadNum())+1));
        iCustomDownloadRecordService.update(customDownloadRecord);
        
        return new WebResult<String>().success("成功", "true");
    }
    /**
     * @param response
     * @param localPathTmp
     * @param fileName
     */
    @ApiOperation(value = "清单下载")
    @RequestMapping(value = "/customDownloadRecord/downloadGroupList", method = RequestMethod.POST)
    public void downloadlist(HttpServletResponse response,String localPathFile,@ModelAttribute CustomDownloadRecord customDownloadRecord) {
        customDownloadRecord = iCustomDownloadRecordService.get(customDownloadRecord.getRecordId());
        if (StringUtil.isBlank(localPathFile)) {
            //本地缓冲目录
            CocCacheAble cacheProxy = CocCacheProxy.getCacheProxy();
            localPathFile = cacheProxy.getSYSConfigInfoByKey("LOC_CONFIG_SYS_TEMP_PATH");  
            if (StringUtil.isNotBlank(localPathFile)) {   //以缓冲目录为准
                if (!localPathFile.endsWith(File.separator)) {
                    localPathFile += File.separator;
                }
                localPathFile += customDownloadRecord.getFileName();
            }
        } else {
            String[] localPathFileArr = localPathFile.split(File.separator);
            if (!localPathFileArr[localPathFileArr.length-1].contains("\\.")) {
                if (!localPathFile.endsWith(File.separator)) {
                    localPathFile += File.separator;
                }
                localPathFile += customDownloadRecord.getFileName();
            }
        }
        
        String[] paths = localPathFile.split(File.separator);
        String fileName = paths[paths.length - 1];
        
        //返回文件生成
        BufferedInputStream bis = null;
        FileInputStream fis = null;
        OutputStream os = null;
        
        response.setContentType("application/force-download");//强制设置浏览器不打开下载
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));  
        
        byte[] buffer = new byte[1024];
        try {
        		File file = null; 
            String realPath = ResourceUtils.getURL("classpath:").getPath();//获取web项目的路径
            //在开发测试模式时，得到的地址为：{项目跟目录}/target/classes/
            //在打包成jar正式发布时，得到的地址为：{发布jar包目录}/
            if (realPath.endsWith("target/classes/")) {    //此分支只有在dev下运行
                realPath = realPath.replace("target/classes/", "");
                file = new File(realPath, localPathFile);
            } else {
	            	file = new File(localPathFile);
            }

            boolean isDownload = false;
            if(!file.exists() || file.length()==0 || file.isDirectory()){ //本地文件不存在或者是空文件，从ftp服务器下载
                SysInfo sysInfo = null;
                try {
                    sysInfo = iSysInfoService.selectSysInfoBySysName(SYS_NAME);
                } catch (BaseException e) {
                    LogUtil.error("根据名称查询平台出错！", e);
                }
                if (null != sysInfo) {
                    isDownload = SftpUtil.sftpDownload(sysInfo.getFtpServerIp(), sysInfo.getFtpPort(), sysInfo.getFtpUser(), 
                        DES.decrypt(sysInfo.getFtpPwd()), localPathFile, sysInfo.getFtpPath(), fileName,false,true);
                }
            } else {
                isDownload = true;
            }
            if (isDownload) {
            		fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
                //输出流
                os = response.getOutputStream();
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer, 0, i);
                    os.flush();
                    i = bis.read(buffer);
                }
                LogUtil.debug("file download：" + fileName);
                file.delete();//删除本地文件
            }
        } catch (Exception e) {
            LogUtil.error("Download Exception!", e);
        } finally {
            if (fis != null) {
                try {
                		fis.close();
                } catch (IOException e) {
                    LogUtil.error("IO Exception!", e);
                }
            }
            if (bis != null) {
                try {
                		bis.close();
                } catch (IOException e) {
                    LogUtil.error("IO Exception!", e);
                }
            }
            if (os != null) {
                try {
                		os.close();
                } catch (IOException e) {
                    LogUtil.error("IO Exception!", e);
                }
            }
        }
    }
    
    
}
