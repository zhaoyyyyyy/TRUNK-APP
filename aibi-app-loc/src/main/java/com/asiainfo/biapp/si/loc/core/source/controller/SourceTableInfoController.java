/*
 * @(#)SourceTableInfoController.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.source.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.base.controller.BaseController;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.base.utils.WebResult;
import com.asiainfo.biapp.si.loc.core.source.entity.SourceTableInfo;
import com.asiainfo.biapp.si.loc.core.source.service.ISourceTableInfoService;
import com.asiainfo.biapp.si.loc.core.source.vo.SourceTableInfoVo;

/**
 * Title : SourceTableInfoController
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
@Api(value = "指标数据源信息配置管理",description="张楠")
@RequestMapping("api/source")
@RestController
public class SourceTableInfoController extends BaseController<SourceTableInfo> {

    @Autowired
    private ISourceTableInfoService iSourceTableInfoService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SourceTableInfoController.class);

    private static final String SUCCESS = "success";

    @ApiOperation(value = "分页查询指标数据源信息配置")
    @RequestMapping(value = "/sourceTableInfo/queryPage", method = RequestMethod.POST)
    public Page<SourceTableInfo> list(@ModelAttribute Page<SourceTableInfo> page,
            @ModelAttribute SourceTableInfoVo sourceTableInfoVo) {
        Page<SourceTableInfo> sourceTableInfoPage = new Page<>();
        try {
            sourceTableInfoPage = iSourceTableInfoService.selectSourceTableInfoPageList(page, sourceTableInfoVo);
        } catch (BaseException e) {
            sourceTableInfoPage.fail(e);
        }
        return sourceTableInfoPage;
    }

    @ApiOperation(value = "不分页查询指标数据源信息配置")
    @RequestMapping(value = "/sourceTableInfo/queryList", method = RequestMethod.POST)
    public WebResult<List<SourceTableInfo>> findList(@ModelAttribute SourceTableInfoVo sourceTableInfoVo) {
        WebResult<List<SourceTableInfo>> webResult = new WebResult<>();
        List<SourceTableInfo> sourceTableInfoList = new ArrayList<>();
        try {
            sourceTableInfoList = iSourceTableInfoService.selectSourceTableInfoList(sourceTableInfoVo);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取指标数据源信息配置成功.", sourceTableInfoList);
    }

    @ApiOperation(value = "根据ID查询指标数据源信息配置")
    @ApiImplicitParam(name = "sourceTableId", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/sourceTableInfo/get", method = RequestMethod.POST)
    public WebResult<SourceTableInfo> findById(String sourceTableId) throws BaseException {
        WebResult<SourceTableInfo> webResult = new WebResult<>();
        SourceTableInfo sourceTableInfo = new SourceTableInfo();
        try {
            sourceTableInfo = iSourceTableInfoService.selectSourceTableInfoById(sourceTableId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取指标数据源信息配置成功.", sourceTableInfo);
    }

    @ApiOperation(value = "新增指标数据源信息配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "configId", value = "专区ID", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sourceFileName", value = "指标源表文件名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sourceTableName", value = "指标源表表名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "tableSchema", value = "表名SCHEMA", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sourceTableCnName", value = "指标源表中文名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sourceTableType", value = "指标源表类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "tableSuffix", value = "表/文件后缀日期", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "readCycle", value = "读取周期", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "idType", value = "来源主键标识类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "idColumn", value = "来源主键字段名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "idDataType", value = "来源主键数据类型", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "columnCaliber", value = "业务口径", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dataSourceId", value = "数据源ID", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dataSourceType", value = "数据源类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "dateColumnName", value = "日期分区字段", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "whereSql", value = "过滤条件", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "statusId", value = "状态", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "sourceInfoList", value = "指标信息列表", required = false, paramType = "query", dataType = "string") })
    @RequestMapping(value = "/sourceTableInfo/save", method = RequestMethod.POST)
    public WebResult<String> save(@ApiIgnore SourceTableInfo sourceTableInfo) {
        WebResult<String> webResult = new WebResult<>();
        User user = new User();
        try {
            user = this.getLoginUser();
        } catch (BaseException e) {
            LOGGER.info("context", e);
        }
        sourceTableInfo.setCreateTime(new Date());
        sourceTableInfo.setCreateUserId(user.getUserId());
        sourceTableInfo.setKeyType(sourceTableInfo.getIdType());
        sourceTableInfo.setDataExtractionType(1);
        try {
            iSourceTableInfoService.addSourceTableInfo(sourceTableInfo);
        } catch (BaseException e1) {
            return webResult.fail(e1);
        }
        return webResult.success("新增指标数据源信息配置成功", SUCCESS);
    }

    @ApiOperation(value = "修改指标数据源信息配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceTableId", value = "ID", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "configId", value = "专区ID", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sourceFileName", value = "指标源表文件名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sourceTableName", value = "指标源表表名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "tableSchema", value = "表名SCHEMA", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sourceTableCnName", value = "指标源表中文名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sourceTableType", value = "指标源表类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "tableSuffix", value = "表/文件后缀日期", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "readCycle", value = "读取周期", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "idType", value = "来源主键标识类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "idColumn", value = "来源主键字段名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "idDataType", value = "来源主键数据类型", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "columnCaliber", value = "业务口径", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dataSourceId", value = "数据源ID", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dataSourceType", value = "数据源类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "dataExtractionType", value = "数据抽取方式", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "dateColumnName", value = "日期分区字段", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "whereSql", value = "过滤条件", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "statusId", value = "状态", required = false, paramType = "query", dataType = "int") })
    @RequestMapping(value = "/sourceTableInfo/update", method = RequestMethod.POST)
    public WebResult<String> edit(@ApiIgnore SourceTableInfo sourceTableInfo) {
        WebResult<String> webResult = new WebResult<>();
        SourceTableInfo oldSou = new SourceTableInfo();
        try {
            oldSou = iSourceTableInfoService.selectSourceTableInfoById(sourceTableInfo.getSourceTableId());
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        oldSou = fromToBean(sourceTableInfo, oldSou);
        try {
            iSourceTableInfoService.modifySourceTableInfo(oldSou);
        } catch (BaseException e1) {
            return webResult.fail(e1);
        }
        return webResult.success("修改指标数据源信息配置成功", SUCCESS);
    }

    /**
     * 删除
     * 
     * @param id
     */
    @ApiOperation(value = "删除指标数据源信息配置")
    @ApiImplicitParam(name = "sourceTableId", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/sourceTableInfo/delete", method = RequestMethod.POST)
    public WebResult<String> del(String sourceTableId) {
        WebResult<String> webResult = new WebResult<>();
        try {
            iSourceTableInfoService.deleteSourceTableInfo(sourceTableId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("删除指标数据源信息配置成功", SUCCESS);
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
        return oldSou;
    }

}