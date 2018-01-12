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
import io.swagger.annotations.ApiParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import springfox.documentation.annotations.ApiIgnore;

import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.base.controller.BaseController;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.utils.LogUtil;
import com.asiainfo.biapp.si.loc.base.utils.WebResult;
import com.asiainfo.biapp.si.loc.core.source.entity.SourceInfo;
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
@Api(value = "指标数据源信息配置管理", description = "张楠")
@RequestMapping("api/source")
@RestController
public class SourceTableInfoController extends BaseController<SourceTableInfo> {

    @Autowired
    private ISourceTableInfoService iSourceTableInfoService;

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
            sourceTableInfo.setCreateUserId(user.getUserId());
            iSourceTableInfoService.addSourceTableInfo(sourceTableInfo);
        } catch (BaseException e) {
            return webResult.fail(e);
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
        try {
            iSourceTableInfoService.modifySourceTableInfo(sourceTableInfo);
        } catch (BaseException e) {
            return webResult.fail(e);
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


    @ApiOperation(value = "导入列信息")
    @RequestMapping(value = "/sourceTableInfo/upload", consumes = "multipart/*", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
    public WebResult<List<SourceInfo>> upload(@ApiParam(value = "文件上传", required = true) MultipartFile multipartFile) throws IOException{
        WebResult<List<SourceInfo>> webResult = new WebResult<>();
        List<SourceInfo> list = new ArrayList<>();
        if (multipartFile != null && !multipartFile.isEmpty()) {
             String fileFileName = multipartFile.getOriginalFilename();
             try {
//                list = iSourceTableInfoService.parseColumnInfoFile(multipartFile.getInputStream(), fileFileName);
            } catch (Exception e) {
                LogUtil.error(e);
            }
        }
        return webResult.success("读取指标信息列成功", list);
    }

}
