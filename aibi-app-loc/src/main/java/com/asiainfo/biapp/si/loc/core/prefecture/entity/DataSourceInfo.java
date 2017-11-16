/*
 * @(#)DataSourceInfo.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.prefecture.entity;

import io.swagger.annotations.ApiParam;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.asiainfo.biapp.si.loc.base.entity.BaseEntity;

/**
 * Title : DataSourceInfo
 * <p/>
 * Description : 数据源信息
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8 +
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2017年11月7日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月7日
 */

@Entity
@Table(name = "LOC_DATA_SOURCE_INFO")
public class DataSourceInfo extends BaseEntity {

    private static final long serialVersionUID = 2035013017939483936L;

    /**
     * 主键 数据源ID
     */
    @Id
    @Column(name = "DATA_SOURCE_ID")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @ApiParam(value = "数据源ID")
    private String dataSourceId;

    /**
     * 专区ID
     */
    @Column(name = "CONFIG_ID")
    @ApiParam(value = "专区ID")
    private String configId;

    /**
     * 数据源名称
     */
    @Column(name = "DATA_SOURCE_NAME")
    @ApiParam(value = "数据源名称")
    private String dataSourceName;

    /**
     * 数据源类型 0:数据库接口 1:文件接口
     */
    @Column(name = "DATA_SOURCE_TYPE")
    @ApiParam(value = "数据源类型")
    private Integer dataSourceType;

    /**
     * 数据库类型
     */
    @Column(name = "DB_TYPE")
    @ApiParam(value = "数据库类型")
    private String dbType;

    /**
     * 数据库驱动
     */
    @Column(name = "DB_DRIVER")
    @ApiParam(value = "数据库驱动")
    private String dbDriver;

    /**
     * 数据库链接
     */
    @Column(name = "DB_URL")
    @ApiParam(value = "数据库链接")
    private String dbUrl;

    /**
     * 用户名
     */
    @Column(name = "DB_USERNAME")
    @ApiParam(value = "用户名")
    private String dbUsername;

    /**
     * 密码
     */
    @Column(name = "DB_PASSWORD")
    @ApiParam(value = "密码")
    private String dbPassword;

    /**
     * 是否本地仓库 0：第三方数据库 1：本地后台仓库
     */
    @Column(name = "IS_LOCAL")
    @ApiParam(value = "是否本地仓库")
    private Integer isLocal;

    /**
     * FTP地址
     */
    @Column(name = "FTP_ADD")
    @ApiParam(value = "FTP地址")
    private String ftpAdd;

    /**
     * FTP端口
     */
    @Column(name = "FTP_POINT")
    @ApiParam(value = "FTP端口")
    private String ftpPoint;

    /**
     * FTP服务器用户名
     */
    @Column(name = "FTP_USER")
    @ApiParam(value = "FTP服务器用户名")
    private String ftpUser;

    /**
     * FTP服务器密码
     */
    @Column(name = "FTP_PWD")
    @ApiParam(value = "FTP服务器密码")
    private String ftpPwd;

    /**
     * FTP服务器目录
     */
    @Column(name = "FTP_DIR")
    @ApiParam(value = "FTP服务器目录")
    private String ftpDir;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    @ApiParam(value = "创建时间")
    private Date createTime;

    /**
     * 失效时间
     */
    @Column(name = "INVALID_TIME")
    @ApiParam(value = "失效时间")
    private Date invalidTime;

    /**
     * 排序
     */
    @Column(name = "SORT_NUM")
    @ApiParam(value = "排序")
    private Integer sortNum;

    /**
     * 状态 0.未生效 1.已启用 2.已停用 3.已下线 4.已删除
     */
    @Column(name = "CONFIG_STATUS")
    @ApiParam(value = "状态")
    private Integer configStatus;

    public String getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(String dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public Integer getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(Integer dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public void setDbDriver(String dbDriver) {
        this.dbDriver = dbDriver;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public Integer getIsLocal() {
        return isLocal;
    }

    public void setIsLocal(Integer isLocal) {
        this.isLocal = isLocal;
    }

    public String getFtpAdd() {
        return ftpAdd;
    }

    public void setFtpAdd(String ftpAdd) {
        this.ftpAdd = ftpAdd;
    }

    public String getFtpPoint() {
        return ftpPoint;
    }

    public void setFtpPoint(String ftpPoint) {
        this.ftpPoint = ftpPoint;
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

    public String getFtpDir() {
        return ftpDir;
    }

    public void setFtpDir(String ftpDir) {
        this.ftpDir = ftpDir;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Integer getConfigStatus() {
        return configStatus;
    }

    public void setConfigStatus(Integer configStatus) {
        this.configStatus = configStatus;
    }

}
