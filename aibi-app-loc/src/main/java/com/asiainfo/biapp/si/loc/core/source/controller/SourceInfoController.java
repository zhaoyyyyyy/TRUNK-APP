/*
 * @(#)SourceInfoController.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.source.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

import com.asiainfo.biapp.si.loc.base.controller.BaseController;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.base.utils.WebResult;
import com.asiainfo.biapp.si.loc.core.source.entity.SourceInfo;
import com.asiainfo.biapp.si.loc.core.source.service.ISourceInfoService;
import com.asiainfo.biapp.si.loc.core.source.vo.SourceInfoVo;

/**
 * Title : SourceInfoController
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
@Api(value = "指标信息管理",description="张楠")
@RequestMapping("api/source")
@RestController
public class SourceInfoController extends BaseController<SourceInfo> {

    @Autowired
    private ISourceInfoService iSourceInfoService;

    private static final String SUCCESS = "success";

    @ApiOperation(value = "分页查询指标信息")
    @RequestMapping(value = "/sourceInfo/queryPage", method = RequestMethod.POST)
    public Page<SourceInfo> list(@ModelAttribute Page<SourceInfo> page, @ModelAttribute SourceInfoVo sourceInfoVo) {
        Page<SourceInfo> sourceInfoPage = new Page<>();
        try {
            sourceInfoPage = iSourceInfoService.selectSourceInfoPageList(page, sourceInfoVo);
        } catch (BaseException e) {
            sourceInfoPage.fail(e);
        }
        return sourceInfoPage;
    }

    @ApiOperation(value = "不分页查询指标信息")
    @RequestMapping(value = "/sourceInfo/queryList", method = RequestMethod.POST)
    public WebResult<List<SourceInfo>> findList(@ModelAttribute SourceInfoVo sourceInfoVo) {
        WebResult<List<SourceInfo>> webResult = new WebResult<>();
        List<SourceInfo> sourceInfoList = new ArrayList<>();
        try {
            sourceInfoList = iSourceInfoService.selectSourceInfoList(sourceInfoVo);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取指标信息成功.", sourceInfoList);
    }

    @ApiOperation(value = "根据ID查询指标信息")
    @ApiImplicitParam(name = "sourceId", value = "sourceId", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/sourceInfo/get", method = RequestMethod.POST)
    public WebResult<List<SourceInfo>> findById(String sourceId) {
        sourceId = request.getParameter("sourceId");
        WebResult<List<SourceInfo>> webResult = new WebResult<>();
        String[] sourceIds = sourceId.split(",");
        List<SourceInfo> sourceInfolist = new ArrayList<>();
        SourceInfo sourceInfo = new SourceInfo();
        for (String sId : sourceIds) {
            try {
                sourceInfo = iSourceInfoService.selectSourceInfoById(sId);
                sourceInfolist.add(sourceInfo);
            } catch (BaseException e) {
                return webResult.fail(e);
            }
        }
        return webResult.success("获取指标信息成功", sourceInfolist);
    }

    @ApiOperation(value = "新增指标信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceId", value = "ID", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sourceName", value = "指标名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "cooTableId", value = "数据ID", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "columnName", value = "列名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sourceColumnRule", value = "列运算规则", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "columnCnName", value = "列中文名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "cooColumnType", value = "列数据类型", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "columnLength", value = "列数据长度", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "columnUnit", value = "单位", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "columnCaliber", value = "业务口径", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "columnNum", value = "列序", required = false, paramType = "query", dataType = "int") })
    @RequestMapping(value = "/sourceInfo/save", method = RequestMethod.POST)
    public WebResult<String> save(@ApiIgnore SourceInfo sourceInfo) {
        WebResult<String> webResult = new WebResult<>();
        try {
            sourceInfo.setSourceColumnRule(sourceInfo.getColumnName());
            iSourceInfoService.addSourceInfo(sourceInfo);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("新增指标信息成功", SUCCESS);
    }

    @ApiOperation(value = "修改指标信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceId", value = "ID", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sourceName", value = "指标名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sourceTableId", value = "数据ID", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "columnName", value = "列名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sourceColumnRule", value = "列运算规则", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "columnCnName", value = "列中文名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "cooColumnType", value = "列数据类型", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "columnLength", value = "列数据长度", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "columnUnit", value = "单位", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "columnCaliber", value = "业务口径", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "columnNum", value = "列序", required = false, paramType = "query", dataType = "int") })
    @RequestMapping(value = "/sourceInfo/update", method = RequestMethod.POST)
    public WebResult<String> edit(@ApiIgnore SourceInfo sourceInfo) {
        WebResult<String> webResult = new WebResult<>();
        SourceInfo oldSou = new SourceInfo();
        try {
            oldSou = iSourceInfoService.selectSourceInfoById(sourceInfo.getSourceId());
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        oldSou = fromToBean(sourceInfo, oldSou);
        try {
            iSourceInfoService.modifySourceInfo(oldSou);
        } catch (BaseException e1) {
            return webResult.fail(e1);
        }
        return webResult.success("修改指标信息成功", SUCCESS);
    }

    /**
     * 删除
     * 
     * @param id
     */
    @ApiOperation(value = "删除指标信息")
    @ApiImplicitParam(name = "sourceId", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/sourceInfo/delete", method = RequestMethod.POST)
    public WebResult<String> del(String sourceId) {
        WebResult<String> webResult = new WebResult<>();
        try {
            iSourceInfoService.deleteSourceInfo(sourceId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("删除指标信息成功", SUCCESS);
    }

    /**
     * 封装实体信息
     *
     * @param sou
     * @param oldSou
     * @return
     */
    public SourceInfo fromToBean(SourceInfo sou, SourceInfo oldSou) {
        if (StringUtil.isNotBlank(sou.getSourceName())) {
            oldSou.setSourceName(sou.getSourceName());
        }
        if (StringUtil.isNotBlank(sou.getSourceTableId())) {
            oldSou.setSourceTableId(sou.getSourceTableId());
        }
        if (StringUtil.isNotBlank(sou.getColumnName())) {
            oldSou.setColumnName(sou.getColumnName());
        }
        if (StringUtil.isNotBlank(sou.getSourceColumnRule())) {
            oldSou.setSourceColumnRule(sou.getSourceColumnRule());
        }
        if (StringUtil.isNotBlank(sou.getColumnCnName())) {
            oldSou.setColumnCnName(sou.getColumnCnName());
        }
        if (StringUtil.isNotBlank(sou.getCooColumnType())) {
            oldSou.setCooColumnType(sou.getCooColumnType());
        }
        if (null != sou.getColumnLength()) {
            oldSou.setColumnLength(sou.getColumnLength());
        }
        if (StringUtil.isNotBlank(sou.getColumnUnit())) {
            oldSou.setColumnUnit(sou.getColumnUnit());
        }
        if (StringUtil.isNotBlank(sou.getColumnCaliber())) {
            oldSou.setColumnCaliber(sou.getColumnCaliber());
        }
        if (null != sou.getColumnNum()) {
            oldSou.setColumnNum(sou.getColumnNum());
        }
        return oldSou;
    }

}
