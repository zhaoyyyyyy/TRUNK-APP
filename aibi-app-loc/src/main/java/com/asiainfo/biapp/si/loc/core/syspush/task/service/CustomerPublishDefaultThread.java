/*
 * @(#)CustomerPublishDefaultThread.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.task.service;

import java.beans.IntrospectionException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.extend.SpringContextHolder;
import com.asiainfo.biapp.si.loc.base.utils.Bean2XMLUtils;
import com.asiainfo.biapp.si.loc.base.utils.DESUtil;
import com.asiainfo.biapp.si.loc.base.utils.DateUtil;
import com.asiainfo.biapp.si.loc.base.utils.FileUtil;
import com.asiainfo.biapp.si.loc.base.utils.FtpUtil;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.base.utils.MD5Util;
import com.asiainfo.biapp.si.loc.base.utils.SftpUtil;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.base.utils.model.DES;
import com.asiainfo.biapp.si.loc.base.utils.model.OtherSysXmlBean;
import com.asiainfo.biapp.si.loc.bd.common.service.IBackSqlService;
import com.asiainfo.biapp.si.loc.cache.CocCacheAble;
import com.asiainfo.biapp.si.loc.cache.CocCacheProxy;
import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableData;
import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableDataId;
import com.asiainfo.biapp.si.loc.core.dimtable.service.IDimTableDataService;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTable;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTableColumn;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelInfoService;
import com.asiainfo.biapp.si.loc.core.syspush.common.constant.ServiceConstants;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelAttrRel;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelPushCycle;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelPushReq;
import com.asiainfo.biapp.si.loc.core.syspush.entity.SysInfo;
import com.asiainfo.biapp.si.loc.core.syspush.service.ICustomerPublishCommService;
import com.asiainfo.biapp.si.loc.core.syspush.service.ICustomerPublishService;
import com.asiainfo.biapp.si.loc.core.syspush.service.ILabelAttrRelService;
import com.asiainfo.biapp.si.loc.core.syspush.service.ILabelPushCycleService;
import com.asiainfo.biapp.si.loc.core.syspush.service.ILabelPushReqService;
import com.asiainfo.biapp.si.loc.core.syspush.service.ISysInfoService;
import com.asiainfo.biapp.si.loc.core.syspush.task.ICustomerPublishThread;
import com.asiainfo.biapp.si.loc.core.syspush.vo.LabelAttrRelVo;
import com.asiainfo.biapp.si.loc.core.syspush.vo.LabelPushReqVo;
import com.asiainfo.biapp.si.loc.core.syspush.vo.StandardPushXmlBean;
import com.asiainfo.biapp.si.loc.core.syspush.vo.StandardPushXmlBean.Data;
import com.asiainfo.biapp.si.loc.core.syspush.vo.StandardPushXmlBean.Title;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * Title : CustomerPublishDefaultThread
 * <p/>
 * Description : 客户群推送默认实现线程类
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.7 +
 * <p/>
 * Modification History :
 * <p/>
 * <pre>NO.    Date         Modified By    Why & What is modified</pre>
 * <pre>1    2018年2月26日     hongfb        Created</pre>
 * <p/>
 *
 * @author  hongfb
 * @version 1.0.0.2018年2月26日
 */

@Service
@Scope("prototype")
public class CustomerPublishDefaultThread implements ICustomerPublishThread {
    
    @Autowired
    private IBackSqlService backSqlService;
	
	@Autowired
	private ISysInfoService iSysInfoService;
	
    @Autowired
    private ILabelInfoService iLabelInfoService;
    
    @Autowired
    private ICustomerPublishCommService iCustomerPublishCommService;
    
    @Autowired
    private ILabelAttrRelService iLabelAttrRelService;
    
    @Autowired
    ILabelPushCycleService iLabelPushCycleService;
    
    @Autowired
    ILabelPushReqService iLabelPushReqService;

    @Autowired
    IDimTableDataService iDimTableDataService;
    
    
    //传入参数
    private List<LabelPushCycle> labelPushCycleList;
	private boolean isJobTask;
	private List<Map<String, Object>> reservedParameters;

    private CocCacheAble cacheProxy = null;     //缓存代理类
    
    
    //内部变量
    private SysInfo sysInfo;                //当前的推送平台
    private LabelPushReq labelPushReq;      //当前的推送详情
    private LabelInfo customInfo;           //当前的推送客户群
    private String fileName;                //当前的文件名称
    List<LabelAttrRel> attrRelList = null;	//当前的推送客户群关联的属性列

    private int bufferedRowSize = 10000;    //每次读取数据的条数
    private static final String FILE_PATH = "syspush"; 		//推送的文件的目录名称
    private static final String encode = "UTF-8"; 			//当前的文件的编码
    private static final long CUSTOMER_PUBLISH_PRE_WAIT_TIME = 5000;     //客户群推送线程前置等待时间
    private static final long CUSTOMER_PUBLISH_FTP_WAIT_TIME = 60000;    //客户群推送线程FTP等待时间

    
    /**
     * Description: 传入初始化参数
     * 
     * @param customPushReqList 推送请求列表
     * @param pushCycle 推送周期
     * @param pushCycle 推送周期
     */
    public void initParamter(List<LabelPushCycle> labelPushCycleList, boolean isJobTask, List<Map<String, Object>> reservedParameters) {
        this.labelPushCycleList = labelPushCycleList;
        this.isJobTask = isJobTask;
        this.reservedParameters = reservedParameters;
        
        this.cacheProxy = CocCacheProxy.getCacheProxy();
        String pageSzie = cacheProxy.getSYSConfigInfoByKey("EXPORT_TO_FILE_PAGESIZE"); // 查询每页大小
        if (StringUtil.isNotEmpty(pageSzie)) {
            bufferedRowSize = Integer.valueOf(pageSzie);
        }
	}

	public void run() {
	    LogUtil.info("Enter CustomerPublishDefaultThread.run()");
        LogUtil.debug("客户群推送 "+CUSTOMER_PUBLISH_PRE_WAIT_TIME/1000+" 秒后开始执行。。。");
		
        try {
			Thread.sleep(CUSTOMER_PUBLISH_PRE_WAIT_TIME);//之前的事务提交需要一定时间
		} catch (InterruptedException e) {
		    LogUtil.error("InterruptedException", e);
		}
		
		long start = System.currentTimeMillis();
		
		for(LabelPushCycle labelPushCycle : labelPushCycleList){
            customInfo = cacheProxy.getLabelInfoById(labelPushCycle.getCustomGroupId()); //datedate is null,so it's error
            if (null == customInfo || (null!=customInfo && null==customInfo.getDataDate())) {
                customInfo = iLabelInfoService.get(labelPushCycle.getCustomGroupId());
            }

            //获取属性列
            int attrType = ServiceConstants.LabelAttrRel.ATTR_SETTING_TYPE_PUSH;
            try {
                attrRelList = iCustomerPublishCommService.getLabelAttrRelsByCustom(customInfo, attrType);
            } catch (Exception e) {
                LogUtil.error("查询客户群关联的属性错误！", e);
            }
            
            //在back库里确认一下清单数据是否存在,并查询有多少数据
			String customId = customInfo.getLabelId();
            String customListSql = iCustomerPublishCommService.getCustomListSql(customInfo, attrRelList,true);
			String sql = new StringBuffer("SELECT COUNT(1) FROM (").append(customListSql).append(") tab ").toString();
            LogUtil.debug("客户群("+customId+")的清单数据是否存在sql："+sql);
	        try {
                int no = backSqlService.queryCount(sql);
                LogUtil.debug("客户群("+customId+")的清单数据量："+no);
            } catch (Exception e) {
                LogUtil.warn("查询客户群("+customId+")的清单数据出错，不推送。");
                continue;
            }
	        
	        //保存推送详情
	        String sysId = labelPushCycle.getSysId();
	        labelPushReq = new LabelPushReq();
//			if (isJobTask) {    //自启动推送
//			    labelPushReq = new LabelPushReq();
			    labelPushReq.setDataDate(customInfo.getDataDate());
			    labelPushReq.setPushStatus(ServiceConstants.LabelPushReq.PUSH_STATUS_PUSHING);
			    labelPushReq.setStartTime(new Date(start));
	            labelPushReq.setRecodeId(labelPushCycle.getRecordId());
	            iLabelPushReqService.save(labelPushReq);
//			}
            //推送文件名称（无路径，无后缀）
            //格式：COC_标签创建人_YYYYMMDDHHMMSS_6位随机数,形如:【COC_admin_20180212150301_981235】
            fileName = LabelPushReqVo.REQID_PREFIX + customInfo.getCreateUserId() + "_"
                    + DateUtil.date2String(new Date(),DateUtil.FORMAT_YYYYMMDDHHMMSS) 
                    + this.findRandom();
            
            try {
                this.customPublish(sysId, customInfo.getCreateUserId());
            } catch (Exception e) {
                //跟新实时更新推送状态
                this.updateLabelPushReq(ServiceConstants.LabelPushReq.PUSH_STATUS_FAILED, e);
                LogUtil.error("推送失败", e);
                
                return;
            }
		}
		
        //跟新实时更新推送状态
        this.updateLabelPushReq(ServiceConstants.LabelPushReq.PUSH_STATUS_SUCCESS, null);
        
        LogUtil.debug("Customer("+customInfo.getLabelId()+")Publish end,cost:" + (System.currentTimeMillis() - start)/1000.0 + " s.");
		LogUtil.info("Exist CustomerPublishDefaultThread.run()");
	}
    
    /**
	 * 处理推送接口
	 * @param sysId
	 * @param pushUserId
	 * @version BZ
	 */
	private boolean customPublish(String sysId, String pushUserId){
	    boolean flag = true;
	    
		sysInfo = iSysInfoService.get(sysId);
		if (sysInfo == null) {
            LogUtil.info("推送平台("+sysId+")不存在，不推送。");
			flag = false;
		} else {
	        //系统支持重复客户群推送 或者 客户群不包含重复记录时 进行推送
	        if (sysInfo.getSysId().equals(cacheProxy.getSYSConfigInfoByKey("MCD_SYS_ID"))) {
	            //发布到MCD，北京移动的要求，到其他地方可能要改动
	            flag = false;
	            LogUtil.error("发布到MCD，北京移动的个性化要求，敬请期待。");
	        } else if (sysInfo.getSysId().equals(cacheProxy.getSYSConfigInfoByKey("OLD_CI_SYS_ID"))) {
	            //发布到老的CI
	            flag = false;
	            LogUtil.error("发布到老的CI，敬请期待。");
	        } else {
	            if (StringUtil.isNotEmpty(sysInfo.getPushClassName())) {    //个性化推送
	                String msg = "个性化推送失败：";
	                try {
	                    LogUtil.debug("个性化推送...");
	                    ICustomerPublishService myPush = (ICustomerPublishService) SpringContextHolder.getBean(sysInfo.getPushClassName());
	                    if (null != sysInfo.getPushClassName()) {
							if (null != myPush) {
								flag = myPush.push(labelPushCycleList, isJobTask, reservedParameters);
							} else {
								msg = new StringBuffer().append("没有找到个性化推送接口实现类，").append(msg).toString();
								throw new RuntimeException(msg);
							} 
						} else {
							msg = new StringBuffer().append("SysInfo表没有配置个性化推送接口实现类，").append(msg).toString();
							throw new RuntimeException(msg);
						} 
	                } catch (Exception e) {
	                    flag = false;
	                    LogUtil.error(msg, e);
	                }
	            } else {
	                String needTxtIds = cacheProxy.getSYSConfigInfoByKey("FTP_NEED_TXT_SYS_IDS");
	                if (StringUtil.isNotEmpty(needTxtIds)) {    //是否要推送txt
	                    String[] sysIdsArray = needTxtIds.split(",");
	                    List<String> ids = Arrays.asList(sysIdsArray);
	                    if (ids != null && ids.size() > 0 && ids.contains(sysInfo.getSysId())) {
	                        flag = false;
	                        LogUtil.error("推送为txt，敬请期待。");
	                    }
	                } else {
	                    //查询该客户群清单是否包含属性
	                    List<LabelAttrRel> attrRelList = null;
	                    try {
	                        LabelAttrRelVo labelAttrRelVo = new LabelAttrRelVo();
	                        labelAttrRelVo.setLabelId(customInfo.getLabelId());
	                        attrRelList = iLabelAttrRelService.selectLabelAttrRelList(labelAttrRelVo);
	                    } catch (Exception e) {
	                        flag = false;
	                        LogUtil.error("查询客户群属性表错误！", e);
	                    }
	                    //浙江新需求，无属性的清单推送，生成的表名前缀，
	                    //eg:表名为tabel_name_a,实际表名为table_name_a+系统最新数据日期；为空时走FTP方式;
	                    //日表每天一天表，月表一月一张表(一次性客户群表数据存在月表中)；
	                    if(StringUtil.isNotEmpty(sysInfo.getTableNamePre()) && (attrRelList==null || attrRelList.size() <=1 )){
	                        LogUtil.error("浙江新个性化推送，无属性的清单推送，生成的表名前缀，敬请期待。");
	                        flag = false;
	                    }else{
	                        //标准化推送
                            flag = standardPublish(pushUserId);
	                    }
	                }
	            }
	        }
		}
		
        return flag;
	}

	/**
	 * 标准化推送,判断如果清单状态是成功的，则推送清单、客户群信息，否则推送只是推送客户群信息
	 * @param pushUserId
	 * @param ciCustomPushReq
	 * @return 是否成功
	 */
	public boolean standardPublish(String pushUserId) {
        LogUtil.debug("标准化推送...");
	    
        boolean result = true;
	    
		List<LabelAttrRel> resultAttrs = new ArrayList<LabelAttrRel>();
	    
	    
	    //1.data2file
		//本地缓冲目录
        String localPathTmp = cacheProxy.getSYSConfigInfoByKey("LOC_CONFIG_SYS_TEMP_PATH");  
        if (null != sysInfo.getLocalPath()) {   //以数据库为准
            localPathTmp = sysInfo.getLocalPath();
        } else {   //否则以缓冲目录为准
            if (!localPathTmp.endsWith(File.separator)) {
                localPathTmp += File.separator;
            }
            localPathTmp += FILE_PATH;
        }
        final String localFilePath = localPathTmp + File.separator;
        String csvFile = localFilePath + fileName + ".csv";
        String csvFileTmp = localFilePath + fileName + "_tmp.csv";
        String zipFile = localFilePath + fileName + ".zip";
        String zipFileTmp = localFilePath + fileName + "_tmp.zip";
        String fileTmp = "";
        String desFile ="";
        
        //1.1 获取创建清单文件sql
        String customListSql = iCustomerPublishCommService.getCustomListSql(customInfo, attrRelList,true);
        LogUtil.info("查询清单数据："+customListSql);
        //1.2 创建清单文件
        result = this.getSql2FileUtils().sql2File(customListSql, null, fileName, encode, bufferedRowSize);
        
        
	    //2.determine push file type
        if (result) {
            //2.1 是否有表头
            String title = cacheProxy.getSYSConfigInfoByKey("LOC_CONFIG_APP_RELATED_COLUMN_CN_NAME");
            if(sysInfo.getIsNeedTitle() != null && ServiceConstants.SysInfo.IS_NEED_TITLE_YES == sysInfo.getIsNeedTitle()){
                if (StringUtil.isEmpty(title)) {
                    title = "手机号码";
                }
                if (null != attrRelList && !attrRelList.isEmpty()) {    //有属性列
                		StringBuffer titleStr = new StringBuffer();
                		for (LabelAttrRel labelAttrRel : attrRelList) {
                			titleStr.append(",").append(labelAttrRel.getAttrColName());
					}
                		title += titleStr.toString();
                }
            }
            //2.2 是否有加密描述文件
            if (sysInfo.getIsNeedDes() != null && ServiceConstants.SysInfo.IS_NEED_DES_YES == sysInfo.getIsNeedDes()) {
                result = this.getSql2FileUtils().sql2File(customListSql, title, csvFileTmp, encode, bufferedRowSize);
                fileTmp = csvFileTmp;
                desFile = csvFile;
            }else{
                result = this.getSql2FileUtils().sql2File(customListSql, title, csvFile, encode, bufferedRowSize);
                desFile = csvFile;
            }
            
            boolean hasExists = false;
            if (sysInfo.getIsNeedDes() != null && ServiceConstants.SysInfo.IS_NEED_DES_YES == sysInfo.getIsNeedDes()) {
                hasExists = new File(fileTmp).exists();
            }else{
                hasExists = new File(desFile).exists();
            }
            if (!hasExists) {//如果因为记录数为0而没有产生文件，那么就创建一个空文件
                try {
                    FileUtil.createFile(fileName, "");
                } catch (Exception e) {
                    LogUtil.error("导出清单文件错误" + customListSql + fileTmp + zipFile, e);
                }
            }
          
            //2.3 ZIP file
            if(sysInfo.getIsNeedCompress() != null && ServiceConstants.SysInfo.IS_NEED_COMPRESS_YES == sysInfo.getIsNeedCompress()){
                try {
                    LogUtil.debug("zipfile:" + zipFile);
                    if (sysInfo.getIsNeedDes() != null && 1 == sysInfo.getIsNeedDes()) {
                        FileUtil.zipFileUnPassword(csvFile, zipFileTmp, "UTF-8");
                        fileTmp = zipFileTmp;
                        desFile = zipFile;
                    } else {
                        FileUtil.zipFileUnPassword(csvFile, zipFile, "UTF-8");
                        desFile = zipFile;
                    }
                } catch (Exception e) {
                    String allFileName = null;
                    if (sysInfo.getIsNeedDes() != null && ServiceConstants.SysInfo.IS_NEED_DES_YES == sysInfo.getIsNeedDes()) {
                        allFileName = csvFile + " " + zipFileTmp;
                    } else {
                        allFileName = csvFile + " " + zipFile;
                    }
                    LogUtil.error("压缩文件出错：" + allFileName, e);
                    return false;
                }
            }
            //2.4 加密文件
            if (sysInfo.getIsNeedDes() != null && ServiceConstants.SysInfo.IS_NEED_DES_YES == sysInfo.getIsNeedDes()) {
                try {
                    String key = sysInfo.getDesKey(); //加密密钥
                    LogUtil.debug(key);
                    if (StringUtil.isEmpty(key)) {
                        String errorMsg = "数据库中未定义密钥";
                        LogUtil.error(errorMsg);
                    }
                    LogUtil.debug("key : " + key);
                    DESUtil des = new DESUtil(key);
                    // DES 加密文件
                    des.encryptFile(fileTmp, desFile);
                } catch (Exception e) {
                    LogUtil.error("加密文件出错：" + desFile, e);
                    return false;
                }
                result = new File(fileTmp).delete();
            }
            //2.5 是否需要XML
            String xmlFile = localFilePath + fileName + ".xml";
            if (sysInfo.getIsNeedXml() != null && 
                  ServiceConstants.SysInfo.IS_NEED_XML_YES == sysInfo.getIsNeedXml()) {
                // create XML File
                try {
                    LogUtil.debug(">>create xml " + xmlFile + " start");
                    LogUtil.info("xmlFile>>>>>>>>>>>>>>>>>>>"+xmlFile);
                    LogUtil.info("desFile>>>>>>>>>>>>>>>>>>>"+desFile);
                    this.createOtherSysXmlFile(xmlFile, desFile);
                    LogUtil.debug(">>create xml " + xmlFile + " end");
                } catch (Exception e) {
                    LogUtil.error("创建XML出错：" + xmlFile, e);
                    return false;
                }
            }

            
            //3 download file
            //3.1 下载数据文件
            Integer protocoType = sysInfo.getProtocoType();//协议类型
            String protocoTypeStr = null;
            try {
                if (null == protocoType || ServiceConstants.SysInfo.PROTOCO_TYPE_FTP == protocoType) {   //默认 ftp
                    protocoTypeStr = "ftp";
                } else if (ServiceConstants.SysInfo.PROTOCO_TYPE_SFTP == protocoType) {  //sftp
                    protocoTypeStr = "sftp";
                } else {
                    protocoTypeStr = "表 SYS_INFO 的 PROTOCOTYPE 字段配置错误！";
                    LogUtil.error(protocoTypeStr);
                    result = false;
                    throw new RuntimeException(protocoTypeStr);
                }
                
                LogUtil.debug("推送方式：------"+protocoTypeStr+"------");
                LogUtil.info(protocoTypeStr+" :" + sysInfo.toString());
                LogUtil.debug("desFile :" + desFile);

                if (null == protocoType || ServiceConstants.SysInfo.PROTOCO_TYPE_FTP == protocoType) {   //默认 ftp
                    result = FtpUtil.ftp(sysInfo.getFtpServerIp(), sysInfo.getFtpPort(), sysInfo.getFtpUser(),
                        DES.decrypt(sysInfo.getFtpPwd()), desFile, sysInfo.getFtpPath() + "/");
                } else if (ServiceConstants.SysInfo.PROTOCO_TYPE_SFTP == protocoType) {  //sftp
                    result = SftpUtil.sftp(sysInfo.getFtpServerIp(), sysInfo.getFtpPort(), sysInfo.getFtpUser(),
                        DES.decrypt(sysInfo.getFtpPwd()), desFile, sysInfo.getFtpPath() + "/");
                }
                if (!result) {
                    LogUtil.error(protocoTypeStr+" error");
                }
                LogUtil.debug("客户群推送线程"+protocoTypeStr+"等待时间: "+CUSTOMER_PUBLISH_FTP_WAIT_TIME/1000+" s");
                Thread.sleep(CUSTOMER_PUBLISH_FTP_WAIT_TIME);
                result = new File(desFile).delete();    //FTP后删除本地des文件
                LogUtil.debug(new File(desFile).exists());
                //FTP后删除本地csv文件
                if(sysInfo.getIsNeedCompress() != null && ServiceConstants.SysInfo.IS_NEED_COMPRESS_YES == sysInfo.getIsNeedCompress()){
                    if (result) {
                        result = new File(csvFile).delete();
                    }
                }
            } catch (Exception e) {
                LogUtil.error(protocoTypeStr+"出错" + sysInfo + zipFile, e);
                return false;
            }
            //3.2 下载xml
            if (sysInfo.getIsNeedXml() != null && 1 == sysInfo.getIsNeedXml()) {
                //FTP xml file
                try {
                    LogUtil.debug(protocoTypeStr+" :" + sysInfo.getFtpServerIp() + ":" + sysInfo.getFtpPort() + sysInfo.getFtpPath());

                    if (null == protocoType || ServiceConstants.SysInfo.PROTOCO_TYPE_FTP == protocoType) {   //默认 ftp
                        result = FtpUtil.ftp(sysInfo.getFtpServerIp(), sysInfo.getFtpPort(), sysInfo.getFtpUser(),
                            DES.decrypt(sysInfo.getFtpPwd()), xmlFile, sysInfo.getFtpPath() + "/");
                    } else if (ServiceConstants.SysInfo.PROTOCO_TYPE_SFTP == protocoType) {  //sftp
                        result = SftpUtil.sftp(sysInfo.getFtpServerIp(), sysInfo.getFtpPort(), sysInfo.getFtpUser(),
                            DES.decrypt(sysInfo.getFtpPwd()), xmlFile, sysInfo.getFtpPath() + "/");
                    }
                    
                    if (!result) {
                        LogUtil.error(protocoTypeStr+" error");
                    }
                    result = new File(xmlFile).delete();
                } catch (Exception e) {
                    LogUtil.error("XML "+protocoTypeStr+"出错" + sysInfo + xmlFile, e);
                    return false;
                }
            }

            
            //4 call webService,or do nothing  to ALERT
            String isCallWebService = this.cacheProxy.getSYSConfigInfoByKey("TRANSFER_WEBSERVICE_BEFORE_PUBLISH");
            if (!Boolean.valueOf(isCallWebService) && StringUtil.isNotEmpty(sysInfo.getWebserviceWsdl())
                && StringUtil.isNotEmpty(sysInfo.getWebserviceMethod())) {
                result = this.getCustomPushWebServiceResult(pushUserId, labelPushReq, resultAttrs);
            }
        }
	    
		return result;
	}
	
    /*
     * 更新推送实时状态
     */
    private void updateLabelPushReq(Integer pushStatus, Exception e) {
        labelPushReq = iLabelPushReqService.get(labelPushReq.getReqId());
        labelPushReq.setPushStatus(pushStatus);
        if (null != e) {
            String emsg = e.getMessage();
            if (StringUtil.isNoneBlank(emsg) && emsg.length() > 2048) {
                emsg = emsg.substring(0, 1024);
            }

            labelPushReq.setExceInfo(emsg);
        }
        iLabelPushReqService.update(labelPushReq);
    }

    /**
     * 
     * Description: 调用远程webservice接口
     *
     * @param pushUserId
     * @param ciCustomPushReq
     * @param resultAttrs
     * @return
     */
    private boolean getCustomPushWebServiceResult(String pushUserId, LabelPushReq ciCustomPushReq,
            List<LabelAttrRel> resultAttrs) {
        boolean result = true;
        try {
            Object[] args = null;
            if (StringUtil.isNotEmpty(sysInfo.getWebserviceArgs())) {
                String argNames[] = sysInfo.getWebserviceArgs().split(",");
                //给参数赋值 
                args = getArgs(argNames);
            } else {
                //组装webservice请求参数的xml
                StandardPushXmlBean bean = this.createStandardPushXmlBean(pushUserId, 
                        labelPushReq, resultAttrs);
                String pushXmlBody = Bean2XMLUtils.bean2XmlString(bean);
                LogUtil.debug("StandardPushXml : \n\r" + pushXmlBody);
                args = new Object[1];
                args[0] = pushXmlBody;
            }
            Object[] res = callWebService(sysInfo.getWebserviceWsdl(), 
                sysInfo.getWebserviceTargetnamespace(), sysInfo.getWebserviceMethod(), args);
            result = Boolean.valueOf(String.valueOf(res[0]));
        } catch (Exception e) {
            //跟新实时更新推送状态
            this.updateLabelPushReq(ServiceConstants.LabelPushReq.PUSH_STATUS_FAILED, e);
            LogUtil.error("调用webService出错" + sysInfo, e);
            
            return false;
        }
        return result;
    }

    /**
     * 创建标准推送webservice需要的xml
     * @param userId
     * @param ciCustomPushReq 推送请求对象
     * @param resultAttrList 输出属性List
     * @return
     * @version ZJ
     */
    private StandardPushXmlBean createStandardPushXmlBean(String userId, 
            LabelPushReq ciCustomPushReq, List<LabelAttrRel> resultAttrs) {
        StandardPushXmlBean bean = new StandardPushXmlBean();
        Title title = bean.getTitle();
        title.setTaskDesc(sysInfo.getSysName() + "推送任务");
        title.setSendTime(new Date());

        Data data = bean.getData();
        data.setReqId(ciCustomPushReq.getReqId());
        data.setPlatformCode("COC");
        data.setUserId(userId);
        //创建人名称
        String crtPersnName = customInfo.getCreateUserId();
        data.setCrtPersnName(crtPersnName == null ? "" : crtPersnName);
        if (StringUtil.isNoneBlank(customInfo.getCreateTime())) {
            //创建时间
            Date crtTime = null;
            try {
                crtTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(customInfo.getCreateTime());
            } catch (ParseException e) {
                //跟新实时更新推送状态
                this.updateLabelPushReq(ServiceConstants.LabelPushReq.PUSH_STATUS_FAILED, e);
                LogUtil.warn("客户群创建时间（"+customInfo.getCreateTime()+"）格式化异常");
            } 
            data.setCrtTime(crtTime);
        }
        //生效时间
        Date effective_time = customInfo.getEffecTime();
        data.setEffective_time(effective_time);
        //失效时间
        Date fail_time = customInfo.getFailTime();
        data.setFail_time(fail_time);
        //客户群数量
        Long rowNumber = Long.parseLong(String.valueOf(customInfo.getLabelExtInfo().getCustomNum()));
        data.setRowNumber(rowNumber);
        data.setUploadFileName(fileName + ".zip");//文件名
        data.setUploadFileType("zip"); //上传文件类型
        if(sysInfo.getIsNeedCompress() != null && 
                ServiceConstants.SysInfo.IS_NEED_COMPRESS_YES == sysInfo.getIsNeedCompress()){
            data.setUploadFileName(fileName + ".zip");//文件名
            data.setUploadFileType("zip"); //上传文件类型
        }else{
            data.setUploadFileName(fileName + ".csv");//文件名
            data.setUploadFileType("csv"); //上传文件类型 
        }
        data.setUploadFileDesc("清单文件"); //上传文件描述
        int dataCycle = customInfo.getUpdateCycle();
        data.setDataCycle(dataCycle); //周期类型：1,一次性;2,月周期;3,日周期;
        String dataCycleDesc = "";
        data.setDataCycleDesc(dataCycleDesc); //周期类型描述：1,一次性;2,月周期;3,日周期;
        data.setCustomGroupId(customInfo.getLabelId()); //客户群Id:KHQ + 地市ID + 8位顺序码
        data.setCustomGroupName(customInfo.getLabelName()); //客户群名称
        data.setCustomGroupDesc(customInfo.getPublishDesc()); //客户群描述
        String dataDateStr = customInfo.getDataDate();
        data.setDataDate(dataDateStr); //统计周期：如果是月周期就是yyyyMM，如果是日周期就是yyyyMMdd
        
        //查询推送的清单的所有列
        /*
        String keyColumn = PropertiesUtils.getProperties("RELATED_COLUMN");
        String keyTitle = PropertiesUtils.getProperties("RELATED_COLUMN_CN_NAME");
        */
        List<StandardPushXmlBean.Data.Column> columns = new ArrayList<StandardPushXmlBean.Data.Column>();
        StandardPushXmlBean.Data.Column col = data.newColumn();
        col.setIsPrimaryKey(1);
        columns.add(col);
        
        for (LabelAttrRel rel : resultAttrs) {
            StandardPushXmlBean.Data.Column column = data.newColumn();
            column.setColumnCnName(rel.getAttrColName());
            column.setIsPrimaryKey(0);
            columns.add(column); //列集合
        }

        data.setColumns(columns);
        
        return bean;
    }


	/**
	 * get 参数 of webService
	 * @param argNames
	 * @return
	 */
	private Object[] getArgs(String[] argNames) {
		Object args[] = new Object[argNames.length];
		for (int i = 0; i < argNames.length; i++) {
			String name = argNames[i];
			String value = "";
			try {
			    value = BeanUtils.getProperty(customInfo, name);//优先从"清单表"获取属性的值
			} catch (Exception e) {
				LogUtil.warn("获取属性的值error,name=" + name);
				continue;
			}
			args[i] = value;
		}
		return args;
	}

	/**
	 * 动态调用webService
	 * @param wsdl
	 * @param targetNamespace 不必须,调用KMB和KMV的webService需要提供名称空间http://service.webservice.kmv.biapp.asiainfo.com/
	 * @param methodName
	 * @param args
	 * @return
	 */
	private static Object[] callWebService(String wsdl, String targetNamespace, String methodName, Object args[]) {
		try {
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient(wsdl);
			Object[] res = null;
			if (targetNamespace == null) {
				res = client.invoke(methodName, args);
			} else {
				res = client.invoke(new QName(targetNamespace, methodName), args);
			}
			return res;
		} catch (Exception e) {
			LogUtil.error("动态调用webservice 失败,wsdl:" + wsdl + ",targetNamespace:" + targetNamespace + ",methodName:"
					+ methodName + ",args:" + args, e);
		}
        return args;
	}
	
	/*
	 * 生成文件名最后的随机数
	 */
	private String findRandom() {
		String guid = ""; 
		for (int i = 1; i <= 6; i++){ 
			if(i == 1) {
				guid += "_"; 
			}
			String n = String.valueOf((int)Math.floor(Math.random() * 10));
			guid += n; 
			
		} 
		return guid; 
	}
	

	
	
    /**
     * 创建ftp上传的xml文件
     * @param xmlFile xml文件名
     * @param zipFile zip文件名
     * @return
     * @version ZJ
     */
    private boolean createOtherSysXmlFile(String xmlFile, String zipFile) {
        OtherSysXmlBean bean = new OtherSysXmlBean();
        bean.getTitle().setTaskDesc(sysInfo.getSysName() + "推送任务");
        bean.getTitle().setSendTime(new Date());

        bean.getData().setReqId(labelPushReq.getRecodeId());
        bean.getData().setPlatformCode("COC");
        File zip = new File(zipFile);
        String fileName = zip.getName();
        bean.getData().setUploadFileName(fileName);

        bean.getData().setRowNumber(String.valueOf(customInfo.getLabelExtInfo().getCustomNum()));
        bean.getData().setUserId(customInfo.getCreateUserId());
        bean.getData().setMD5Check(MD5Util.getFileMD5String(zip));
        bean.getData().setCustomName(customInfo.getLabelName());
        bean.getData().setDataTime(customInfo.getDataDate());
        bean.getData().setPublishDesc(customInfo.getPublishDesc());

        String xmlStr = "";
        boolean result = false;
        try {
            xmlStr = Bean2XMLUtils.bean2XmlString(bean);
            LogUtil.debug(xmlStr);
        } catch (IOException e) {
            LogUtil.error("生成xml文件，uploadXmlFile IOException", e);
            return false;
        } catch (SAXException e) {
            LogUtil.error("生成xml文件，uploadXmlFile SAXException", e);
            return false;
        } catch (IntrospectionException e) {
            LogUtil.error("生成xml文件，uploadXmlFile IntrospectionException", e);
            return false;
        } catch (Exception e) {
            LogUtil.error("生成xml文件，uploadXmlFile", e);
            return false;
        }
        try {
            FileUtil.writeByteFile(xmlStr.getBytes("UTF-8"), new File(xmlFile));
        } catch (Exception e) {
            LogUtil.error("生成xml文件，uploadXmlFile", e);
            return false;
        }
        return result;
    }
    
    /**
     * 根据sql得到文件的工具类
     * @return {@link Sql2FileUtils}
     */
    private Sql2FileUtils getSql2FileUtils() {
        return new Sql2FileUtils();
    }
    /**
     * Title : Sql2FileUtils
     * <p/>
     * Description : 此处用内部类避免多进程下的写文件时数据混乱或丢失
     * <p/>
     * CopyRight : CopyRight (c) 2017
     * <p/>
     * Company : 北京亚信智慧数据科技有限公司
     * <p/>
     * JDK Version Used : JDK 7.0 +
     * <p/>
     * Modification History :
     * <p/>
     * <pre>NO.    Date    Modified By    Why & What is modified</pre>
     * <pre>1    2017年12月10日    hongfb        Created</pre>
     * <p/>
     *
     * @author  hongfb
     * @version 1.0.0.2017年12月12日
     */
    class Sql2FileUtils {

        /**
         * sql生成文件（csv or text）
         * @param sql
         * @param jndiName
         * @param params
         * @param title
         * @param columns
         * @param fileName
         * @param encode
         * @param dimCols
         * @param quote
         * @param waterMarkCode
         * @param bufferedRowSize
         * @return
         * @version ZJ
         */
        public boolean sql2File(String sql, final String title, String fileName,
                final String encode, int bufferedRowSize) {
            
            LogUtil.info("sql2File2:sql= " + sql + ";titles=" + title + ";fileName=" + fileName + ";encode=" + encode);

            long t1 = System.currentTimeMillis();
            boolean flag = true;
            try {
                List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
                final int bufferSize = bufferedRowSize;
                final String file = fileName;
                
                int start = 1;
                IBackSqlService backSqlService = (IBackSqlService) SpringContextHolder.getBean("backServiceImpl");
                while (true) {
                    dataList = backSqlService.queryForPage(sql, start, bufferedRowSize);
                    if (dataList.size() >= bufferSize){
                        if (fileName.toLowerCase().endsWith("csv") || fileName.toLowerCase().endsWith("txt")) {//纯文本文本格式
                            flag = this.writeToTextFile1(dataList, title, file, encode);
                        }
                        dataList.clear();
                        start++;
                    }else{
                        if (fileName.toLowerCase().endsWith("csv") || fileName.toLowerCase().endsWith("txt")) {//纯文本文本格式
                            flag = this.writeToTextFile1(dataList, title, file, encode);
                        }
                        break;
                    }
                }
            }catch (Exception e) {
                flag = false;
                LogUtil.error("createFile(" + fileName + ") error:", e);
            } finally {
                LogUtil.info("The cost of sql2File2 is :  " + (System.currentTimeMillis() - t1) + "ms");
            }
            
            return flag;
        }

		/**文件写入
         * 
         * @param datas
         * @param title
         * @param columns
         * @param fileName
         * @param encode
         * @param dimCols
         * @param quote
         * @return
         */
        public boolean writeToTextFile1(List<Map<String, String>> datas, String title, String fileName, String encode) {
            boolean flag = true;
            CSVWriter cw = null;
            Writer osw = null;
            int bufferdsize = 2048;
            try {
                FileUtil.createLocDir(fileName);//创建目录
                boolean hasExists = new File(fileName).exists();
                osw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, true), encode), bufferdsize);
                cw = new CSVWriter(osw);
                if (!hasExists) {
                    if (StringUtil.isNotEmpty(title)) {
                        cw.writeNext(title.replace("\"", "").split(","));
                    }
                }
                List<String> data = new ArrayList<String>();
                for (Map<String, String> m : datas) {
                    if (m.containsKey("rownum")) {  //不把序号写入文件
                        m.remove("rownum");
                    }
                    for (String col : m.keySet()) {
                        data.add(String.valueOf(m.get(col)).replace("\"", ""));
                    }
                    if (data.size() > 0) {
                        cw.writeNext(data.toArray(new String[] {}));
                        data.clear();
                    }
                    cw.flush();
                }
            } catch (Exception e) {
                flag = false;
                LogUtil.error("createFile(" + fileName + ") error:", e);
            } finally {
                try {
                    if (cw != null) {
                        cw.close();
                    }
                } catch (Exception e) {
                    LogUtil.warn("IO关闭异常："+e.getMessage());
                }
            }
            return flag;
        }

    }
    
    
}
