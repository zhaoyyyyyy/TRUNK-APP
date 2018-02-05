/*
 * @(#)SysInfo.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;

import io.swagger.annotations.ApiParam;

/**
 * Title : SysInfo
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
@Entity
@Table(name = "LOC_SYS_INFO")
public class SysInfo extends BaseEntity{

    private static final long serialVersionUID = 1L;

    /**
     * 系统平台ID
     */
    @Id
    @Column(name = "SYS_ID")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @ApiParam(value = "系统平台ID")
    private String sysId;
    
    /**
     * 系统平台名称
     */
    @Column(name = "SYS_NAME")
    @ApiParam(value = "系统平台名称")
    private String sysName;
    
    /**
     * FTP IP
     */
    @Column(name = "FTP_SERVER_IP")
    @ApiParam(value = "FTP IP")
    private String ftpServerIp;
    
    /**
     * FTP用户名
     */
    @Column(name = "FTP_USER")
    @ApiParam(value = "FTP用户名")
    private String ftpUser;
    
    /**
     * FTP密码
     */
    @Column(name = "FTP_PWD")
    @ApiParam(value = "FTP密码")
    private String ftpPwd;
    
    /**
     * FTP路径
     */
    @Column(name = "FTP_PATH")
    @ApiParam(value = "FTP路径")
    private String ftpPath;
    
    /**
     * FTP端口
     */
    @Column(name = "FTP_PORT")
    @ApiParam(value = "FTP端口")
    private String ftpPost;
    
    /**
     * 本地路径
     */
    @Column(name = "LOCAL_PATH")
    @ApiParam(value = "本地路径")
    private String localPath;
    
    /**
     * WEBSERVICE_WSDL
     */
    @Column(name = "WEBSERVICE_WSDL")
    @ApiParam(value = "WEBSERVICE_WSDL")
    private String webserviceWsdl;
    
    /**
     * WEBSERVICE命名空间
     */
    @Column(name = "WEBSERVICE_TARGETNAMESPACE")
    @ApiParam(value = "WEBSERVICE命名空间")
    private String webserviceTargetnamespace;
    
    /**
     * WEBSERVICE方法
     */
    @Column(name = "WEBSERVICE_METHOD")
    @ApiParam(value = "WEBSERVICE方法")
    private String webserviceMethod;
    
    /**
     * WEBSERVICE参数
     */
    @Column(name = "WEBSERVICE_ARGS")
    @ApiParam(value = "WEBSERVICE参数")
    private String webserviceArgs;
    
    /**
     * 描述
     */
    @Column(name = "DESC_TXT")
    @ApiParam(value = "描述")
    private String descTxt;
    
    /**
     * 是否显示在页面上
     */
    @Column(name = "SHOW_IN_PAGE")
    @ApiParam(value = "是否显示在页面上")
    private Integer showInPage;
    
    /**
     * 是否需要同时上传xml文件
     */
    @Column(name = "IS_NEED_XML")
    @ApiParam(value = "是否需要同时上传xml文件")
    private Integer isNeedXml;
    
    /**
     * 是否需要DES加密
     */
    @Column(name = "IS_NEED_DES")
     @ApiParam(value = "是否需要DES加密")
     private Integer isNeedDes;
    
    /**
     * DES加解密密钥
     */
    @Column(name = "DES_KEY")
    @ApiParam(value = "DES加解密密钥")
    private String desKey;
    
    /**
     * 是否需要周期推送
     */
    @Column(name = "IS_NEED_CYCLE")
    @ApiParam(value = "是否需要周期推送")
    private Integer isNeedCycle;
    
    /**
     * 推送功能实现类名
     */
    @Column(name = "PUSH_CLASS_NAME")
    @ApiParam(value = "推送功能实现类名")
    private String pushClassName;
    
    /**
     * 是否需要压缩
     */
    @Column(name = "IS_NEED_COMPRESS")
    @ApiParam(value = "是否需要压缩")
    private Integer isNeedCompress;
    
    /**
     * 压缩类型
     */
    @Column(name = "COMPRESS_TYPE")
    @ApiParam(value = "压缩类型")
    private Integer compressType;
    
    /**
     * 是否需要表头
     */
    @Column(name = "IS_NEED_TITLE")
    @ApiParam(value = "是否需要表头")
    private Integer isNeedTitle;
    
    /**
     * 文件类型
     */
    @Column(name = "FILE_TYPE")
    @ApiParam(value = "文件类型")
    private String fileType;
    
    /**
     * 是否推送重复记录数
     */
    @Column(name = "IS_DUPLICATE_NUM")
    @ApiParam(value = "是否推送重复记录数")
    private Integer isDuplicateNum;
    
    /**
     * 协议类型
     */
    @Column(name = "PROTOCO_TYPE")
    @ApiParam(value = "协议类型")
    private Integer protocoType;
    
    /**
     * 是否允许推送无清单的
     */
    @Column(name = "IS_ALLOW_NOLIST")
    @ApiParam(value = "是否允许推送无清单")
    private Integer isAllowNolist;
    
    /**
     * 是否允许推送属性
     */
    @Column(name = "IS_ALLOW_ATTR")
    @ApiParam(value = "是否允许推送属性")
    private Integer isAllowAttr;
    
    /**
     * 推送清单表前缀
     */
    @Column(name = "TABLE_NAME_PRE")
    @ApiParam(value = "推送清单表前缀")
    private String tableNamePre;
    
    /**
     * 客户群调度信息表
     */
    @Column(name = "CUSTOM_TASK_TABLE")
    @ApiParam(value = "客户群调度信息表")
    private String customTaskTable;

    /**
     * 推送类型
     */
    @Column(name = "PUSH_TYPE")
    @ApiParam(value = "推送类型")
    private Integer pushType;
    
    
    public Integer getPushType() {
		return pushType;
	}


	public void setPushType(Integer pushType) {
		this.pushType = pushType;
	}


	public String getSysId() {
        return sysId;
    }

    
    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    
    public String getSysName() {
        return sysName;
    }

    
    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    
    public String getFtpServerIp() {
        return ftpServerIp;
    }

    
    public void setFtpServerIp(String ftpServerIp) {
        this.ftpServerIp = ftpServerIp;
    }

    
    public String getFtpUser() {
        return ftpUser;
    }

    
    public void setFtpUser(String ftpUser) {
        this.ftpUser = ftpUser;
    }

    
    public String getFtpPwd() {
        return ftpPwd;
    }

    
    public void setFtpPwd(String ftpPwd) {
        this.ftpPwd = ftpPwd;
    }

    
    public String getFtpPath() {
        return ftpPath;
    }

    
    public void setFtpPath(String ftpPath) {
        this.ftpPath = ftpPath;
    }

    
    public String getFtpPost() {
        return ftpPost;
    }

    
    public void setFtpPost(String ftpPost) {
        this.ftpPost = ftpPost;
    }

    
    public String getLocalPath() {
        return localPath;
    }

    
    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    
    public String getWebserviceWsdl() {
        return webserviceWsdl;
    }

    
    public void setWebserviceWsdl(String webserviceWsdl) {
        this.webserviceWsdl = webserviceWsdl;
    }

    
    public String getWebserviceTargetnamespace() {
        return webserviceTargetnamespace;
    }

    
    public void setWebserviceTargetnamespace(String webserviceTargetnamespace) {
        this.webserviceTargetnamespace = webserviceTargetnamespace;
    }

    
    public String getWebserviceMethod() {
        return webserviceMethod;
    }

    
    public void setWebserviceMethod(String webserviceMethod) {
        this.webserviceMethod = webserviceMethod;
    }

    
    public String getWebserviceArgs() {
        return webserviceArgs;
    }

    
    public void setWebserviceArgs(String webserviceArgs) {
        this.webserviceArgs = webserviceArgs;
    }

    
    public String getDescTxt() {
        return descTxt;
    }

    
    public void setDescTxt(String descTxt) {
        this.descTxt = descTxt;
    }

    
    public Integer getShowInPage() {
        return showInPage;
    }

    
    public void setShowInPage(Integer showInPage) {
        this.showInPage = showInPage;
    }

    
    public Integer getIsNeedXml() {
        return isNeedXml;
    }

    
    public void setIsNeedXml(Integer isNeedXml) {
        this.isNeedXml = isNeedXml;
    }

    
    public Integer getIsNeedDes() {
        return isNeedDes;
    }

    
    public void setIsNeedDes(Integer isNeedDes) {
        this.isNeedDes = isNeedDes;
    }

    
    public String getDesKey() {
        return desKey;
    }

    
    public void setDesKey(String desKey) {
        this.desKey = desKey;
    }

    
    public Integer getIsNeedCycle() {
        return isNeedCycle;
    }

    
    public void setIsNeedCycle(Integer isNeedCycle) {
        this.isNeedCycle = isNeedCycle;
    }

    
    public String getPushClassName() {
        return pushClassName;
    }

    
    public void setPushClassName(String pushClassName) {
        this.pushClassName = pushClassName;
    }

    
    public Integer getIsNeedCompress() {
        return isNeedCompress;
    }

    
    public void setIsNeedCompress(Integer isNeedCompress) {
        this.isNeedCompress = isNeedCompress;
    }

    
    public Integer getCompressType() {
        return compressType;
    }

    
    public void setCompressType(Integer compressType) {
        this.compressType = compressType;
    }

    
    public Integer getIsNeedTitle() {
        return isNeedTitle;
    }

    
    public void setIsNeedTitle(Integer isNeedTitle) {
        this.isNeedTitle = isNeedTitle;
    }

    
    public String getFileType() {
        return fileType;
    }

    
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    
    public Integer getIsDuplicateNum() {
        return isDuplicateNum;
    }

    
    public void setIsDuplicateNum(Integer isDuplicateNum) {
        this.isDuplicateNum = isDuplicateNum;
    }

    
    public Integer getProtocoType() {
        return protocoType;
    }

    
    public void setProtocoType(Integer protocoType) {
        this.protocoType = protocoType;
    }

    
    public Integer getIsAllowNolist() {
        return isAllowNolist;
    }

    
    public void setIsAllowNolist(Integer isAllowNolist) {
        this.isAllowNolist = isAllowNolist;
    }

    
    public Integer getIsAllowAttr() {
        return isAllowAttr;
    }

    
    public void setIsAllowAttr(Integer isAllowAttr) {
        this.isAllowAttr = isAllowAttr;
    }

    
    public String getTableNamePre() {
        return tableNamePre;
    }

    
    public void setTableNamePre(String tableNamePre) {
        this.tableNamePre = tableNamePre;
    }

    
    public String getCustomTaskTable() {
        return customTaskTable;
    }

    
    public void setCustomTaskTable(String customTaskTable) {
        this.customTaskTable = customTaskTable;
    }
    
    
}


