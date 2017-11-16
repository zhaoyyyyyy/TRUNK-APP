/*
 * @(#)DataSourceInfoController.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.prefecture.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

import com.asiainfo.biapp.si.loc.base.controller.BaseController;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.utils.WebResult;
import com.asiainfo.biapp.si.loc.core.prefecture.entity.DataSourceInfo;
import com.asiainfo.biapp.si.loc.core.prefecture.service.IDataSourceInfoService;
import com.asiainfo.biapp.si.loc.core.prefecture.vo.DataSourceInfoVo;

/**
 * Title : DataSourceInfoController
 * <p/>
 * :
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

@Api(value = "专区数据源信息管理")
@RequestMapping("api/prefecture")
@RestController
public class DataSourceInfoController extends BaseController<DataSourceInfo> {

    @Autowired
    private IDataSourceInfoService iDataSourceInfoService;

    private static final String SUCCESS = "success";

    /**
     * 查询列表
     * 
     * @param dataSourceInfo
     * @return
     */
    @ApiOperation(value = "查询列表")
    @RequestMapping(value = "/dataSourceInfo/queryList", method = RequestMethod.POST)
    public WebResult<List<DataSourceInfo>> queryList(@ModelAttribute DataSourceInfoVo dataSourceInfoVo) {
        WebResult<List<DataSourceInfo>> webResult = new WebResult<>();
        List<DataSourceInfo> dataSourceInfoList = new ArrayList<>();
        try {
            dataSourceInfoList = iDataSourceInfoService.findDataSourceInfoList(dataSourceInfoVo);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取数据源成功.", dataSourceInfoList);
    }

    /**
     * 根据ID得到数据源信息
     * 
     * @param dataSourceId
     * @return
     */
    @ApiOperation(value = "根据ID查询")
    @ApiImplicitParam(name = "dataSourceId", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/dataSourceInfo/get", method = RequestMethod.POST)
    public WebResult<DataSourceInfo> getById(String dataSourceId) throws BaseException {
        WebResult<DataSourceInfo> webResult = new WebResult<>();
        DataSourceInfo dataSourceInfo = new DataSourceInfo();
        try {
            dataSourceInfo = iDataSourceInfoService.getById(dataSourceId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取数据源成功.", dataSourceInfo);
    }

    /**
     * 新增或修改
     * 
     * @param dataSourceInfo
     * @return
     */
    @ApiOperation(value = "新增")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "configId", value = "专区ID", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dataSourceName", value = "数据源名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dataSourceType", value = "数据源类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "dbType", value = "数据库类型", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dbDriver", value = "数据库驱动", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dbUrl", value = "数据库连接", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dbUsername", value = "数据库用户名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dbPassword", value = "数据库密码", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "isLocal", value = "是否本地仓库", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "ftpAdd", value = "FTP地址", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "ftpPoint", value = "FTP端口", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "ftpUser", value = "FTP服务器用户名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "ftpPwd", value = "FTP服务器密码", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "ftpDir", value = "FTP服务器目录", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "invalidTime", value = "失效时间", required = false, paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "sortNum", value = "排序序号", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "configStatus", value = "状态", required = false, paramType = "query", dataType = "int") })
    @RequestMapping(value = "/dataSourceInfo/save", method = RequestMethod.POST)
    public WebResult<String> save(@ApiIgnore DataSourceInfo dataSourceInfo) {
        WebResult<String> webResult = new WebResult<>();
        DataSourceInfo reData = iDataSourceInfoService.findOneByDataSourceName(dataSourceInfo.getDataSourceName());
        if (null != reData) {
            return webResult.fail("数据源名称已存在");
        }
        dataSourceInfo.setCreateTime(new Date());
        try {
            iDataSourceInfoService.saveT(dataSourceInfo);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("新增数据源成功", SUCCESS);
    }

    /**
     * 修改
     *
     * @param dataSourceInfo
     * @return
     */
    @ApiOperation(value = "修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dataSourceId", value = "数据源ID", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "configId", value = "专区ID", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dataSourceName", value = "数据源名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dataSourceType", value = "数据源类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "dbType", value = "数据库类型", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dbDriver", value = "数据库驱动", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dbUrl", value = "数据库连接", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dbUsername", value = "数据库用户名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dbPassword", value = "数据库密码", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "isLocal", value = "是否本地仓库", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "ftpAdd", value = "FTP地址", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "ftpPoint", value = "FTP端口", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "ftpUser", value = "FTP服务器用户名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "ftpPwd", value = "FTP服务器密码", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "ftpDir", value = "FTP服务器目录", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "invalidTime", value = "失效时间", required = false, paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "sortNum", value = "排序序号", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "configStatus", value = "状态", required = false, paramType = "query", dataType = "int") })
    @RequestMapping(value = "/dataSourceInfo/update", method = RequestMethod.POST)
    public WebResult<String> update(@ApiIgnore DataSourceInfo dataSourceInfo) {
        WebResult<String> webResult = new WebResult<>();
        DataSourceInfo oldData;
        try {
            oldData = iDataSourceInfoService.getById(dataSourceInfo.getDataSourceId());
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        oldData = fromToBean(dataSourceInfo, oldData);
        iDataSourceInfoService.updateT(oldData);
        return webResult.success("修改数据源成功", SUCCESS);
    }

    /**
     * 删除
     * 
     * @param id
     */
    @ApiOperation(value = "删除")
    @ApiImplicitParam(name = "dataSourceId", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/dataSourceInfo/delete", method = RequestMethod.POST)
    public WebResult<String> delete(String dataSourceId) {
        WebResult<String> webResult = new WebResult<>();
        try {
            iDataSourceInfoService.deleteById(dataSourceId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("删除数据源成功", SUCCESS);
    }

    /**
     * 封装数据源信息
     * 
     * @param data
     * @param oldData
     * @return
     */
    private DataSourceInfo fromToBean(DataSourceInfo data, DataSourceInfo oldData) {
        if (StringUtils.isNotBlank(data.getConfigId())) {
            oldData.setConfigId(data.getConfigId());
        }
        if (StringUtils.isNotBlank(data.getDataSourceName())) {
            oldData.setDataSourceName(data.getDataSourceName());
        }
        if (null != data.getDataSourceType()) {
            oldData.setDataSourceType(data.getDataSourceType());
        }
        if (StringUtils.isNotBlank(data.getDbType())) {
            oldData.setDbType(data.getDbType());
        }
        if (StringUtils.isNotBlank(data.getDbDriver())) {
            oldData.setDbDriver(data.getDbDriver());
        }
        if (StringUtils.isNotBlank(data.getDbUrl())) {
            oldData.setDbUrl(data.getDbUrl());
        }
        if (StringUtils.isNotBlank(data.getDbUsername())) {
            oldData.setDbUsername(data.getDbUsername());
        }
        if (StringUtils.isNotBlank(data.getDbPassword())) {
            oldData.setDbPassword(data.getDbPassword());
        }
        if (null != data.getIsLocal()) {
            oldData.setIsLocal(data.getIsLocal());
        }
        if (StringUtils.isNotBlank(data.getFtpAdd())) {
            oldData.setFtpAdd(data.getFtpAdd());
        }
        if (StringUtils.isNotBlank(data.getFtpPoint())) {
            oldData.setFtpPoint(data.getFtpPoint());
        }
        if (StringUtils.isNotBlank(data.getFtpUser())) {
            oldData.setFtpUser(data.getFtpUser());
        }
        if (StringUtils.isNotBlank(data.getFtpPwd())) {
            oldData.setFtpPwd(data.getFtpPwd());
        }
        if (StringUtils.isNotBlank(data.getFtpDir())) {
            oldData.setFtpDir(data.getFtpDir());
        }
        if (null != data.getInvalidTime()) {
            oldData.setInvalidTime(data.getInvalidTime());
        }
        if (null != data.getSortNum()) {
            oldData.setSortNum(data.getSortNum());
        }
        if (null != data.getConfigStatus()) {
            oldData.setConfigStatus(data.getConfigStatus());
        }
        return oldData;
    }

}
