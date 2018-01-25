/*
 * @(#)DimOrgLevelController.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.back.controller;

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
import com.asiainfo.biapp.si.loc.base.utils.WebResult;
import com.asiainfo.biapp.si.loc.core.back.entity.DimOrgLevel;
import com.asiainfo.biapp.si.loc.core.back.entity.DimOrgLevelId;
import com.asiainfo.biapp.si.loc.core.back.service.IDimOrgLevelService;
import com.asiainfo.biapp.si.loc.core.back.vo.DimOrgLevelVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Title : DimOrgLevelController
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2018年1月25日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2018年1月25日
 */
@Api(value = "数据权限组织层级管理", description = "张楠")
@RequestMapping("api/back")
@RestController
public class DimOrgLevelController extends BaseController<DimOrgLevel> {

    @Autowired
    private IDimOrgLevelService iDimOrgLevelService;

    private static final String SUCCESS = "success";

    @ApiOperation(value = "分页查询数据权限组织层级信息")
    @RequestMapping(value = "/dimOrgLevel/queryPage", method = RequestMethod.POST)
    public Page<DimOrgLevel> queryPage(@ModelAttribute Page<DimOrgLevel> page,
            @ModelAttribute DimOrgLevelVo DimOrgLevelVo) {
        Page<DimOrgLevel> dimOrgLevelPage = new Page<>();
        try {
            dimOrgLevelPage = iDimOrgLevelService.selectDimOrgLevelPageList(page, DimOrgLevelVo);
        } catch (BaseException e) {
            dimOrgLevelPage.fail(e);
        }
        return dimOrgLevelPage;
    }

    @ApiOperation(value = "不分页查询数据权限组织层级信息")
    @RequestMapping(value = "/dimOrgLevel/queryList", method = RequestMethod.POST)
    public WebResult<List<DimOrgLevel>> findList(@ModelAttribute DimOrgLevelVo DimOrgLevelVo) {
        WebResult<List<DimOrgLevel>> webResult = new WebResult<>();
        List<DimOrgLevel> DimOrgLevelList = new ArrayList<>();
        try {
            DimOrgLevelList = iDimOrgLevelService.selectDimOrgLevelList(DimOrgLevelVo);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取数据权限组织层级成功.", DimOrgLevelList);
    }

    @ApiOperation(value = "根据ID查询数据权限组织层级信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "priKey", value = "主键ID", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "orgColumnName", value = "组织字段名称", required = true, paramType = "query", dataType = "string") })
    @RequestMapping(value = "/dimOrgLevel/get", method = RequestMethod.POST)
    public WebResult<DimOrgLevel> getById(DimOrgLevelId dimOrgLevelId) throws BaseException {
        WebResult<DimOrgLevel> webResult = new WebResult<>();
        DimOrgLevel DimOrgLevel = new DimOrgLevel();
        try {
            DimOrgLevel = iDimOrgLevelService.selectDimOrgLevelById(dimOrgLevelId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取数据权限组织层级成功.", DimOrgLevel);
    }

    @ApiOperation(value = "新增数据权限组织层级信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "configStatus", value = "状态", required = false, paramType = "query", dataType = "int")

    })
    @RequestMapping(value = "/dimOrgLevel/save", method = RequestMethod.POST)
    public WebResult<String> save(@ApiIgnore DimOrgLevel DimOrgLevel) {
        WebResult<String> webResult = new WebResult<>();
        try {
            iDimOrgLevelService.addDimOrgLevel(DimOrgLevel);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("新增数据权限组织层级成功", SUCCESS);
    }

    @ApiOperation(value = "修改数据权限组织层级信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "configStatus", value = "状态", required = false, paramType = "query", dataType = "int")

    })
    @RequestMapping(value = "/dimOrgLevel/update", method = RequestMethod.POST)
    public WebResult<String> edit(@ApiIgnore DimOrgLevel DimOrgLevel) {
        WebResult<String> webResult = new WebResult<>();
        try {
            iDimOrgLevelService.modifyDimOrgLevel(DimOrgLevel);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("修改数据权限组织层级成功", SUCCESS);
    }

    @ApiOperation(value = "删除数据权限组织层级信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "priKey", value = "主键ID", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "orgColumnName", value = "组织字段名称", required = true, paramType = "query", dataType = "string") })
    @RequestMapping(value = "/dimOrgLevel/delete", method = RequestMethod.POST)
    public WebResult<String> delete(DimOrgLevelId dimOrgLevelId) {
        WebResult<String> webResult = new WebResult<>();
        try {
            iDimOrgLevelService.deleteDimOrgLevel(dimOrgLevelId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("删除数据权限组织层级成功", SUCCESS);
    }

}
