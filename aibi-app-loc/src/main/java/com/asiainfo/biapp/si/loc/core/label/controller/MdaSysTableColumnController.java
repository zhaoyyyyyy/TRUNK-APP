/*
 * @(#)MdaSysTableColController.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTableColumn;
import com.asiainfo.biapp.si.loc.core.label.service.IMdaSysTableColService;
import com.asiainfo.biapp.si.loc.core.label.vo.MdaSysTableColumnVo;

/**
 * Title : MdaSysTableColController
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2017
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
 * 1    2017年11月21日    lilin7        Created
 * </pre>
 * <p/>
 *
 * @author lilin7
 * @version 1.0.0.2017年11月21日
 */
//@Api(value = "元数据表列管理")
@RequestMapping("api/label")
@RestController
public class MdaSysTableColumnController extends BaseController<MdaSysTableColumn> {

    private static final String SUCCESS = "success";

    @Autowired
    private IMdaSysTableColService iMdaSysTableColService;

    @ApiOperation(value = "元数据表列分页查询")
    @RequestMapping(value = "/mdaSysTableCol/queryPage", method = RequestMethod.POST)
    public Page<MdaSysTableColumn> listPage(@ModelAttribute Page<MdaSysTableColumn> page,
            @ModelAttribute MdaSysTableColumnVo mdaSysTableColumnVo) {
        Page<MdaSysTableColumn> mdaSysTableColPage = new Page<>();
        try {
            mdaSysTableColPage = iMdaSysTableColService.selectMdaSysTableColPageList(page, mdaSysTableColumnVo);
        } catch (BaseException e) {
            mdaSysTableColPage.fail(e);
        }
        return mdaSysTableColPage;
    }

    @ApiOperation(value = "元数据列查询(不分页)")
    @RequestMapping(value = "/mdaSysTableCol/queryList", method = RequestMethod.POST)
    public WebResult<List<MdaSysTableColumn>> findList(@ModelAttribute MdaSysTableColumnVo mdaSysTableColumnVo) {
        WebResult<List<MdaSysTableColumn>> webResult = new WebResult<>();
        List<MdaSysTableColumn> mdaSysTableColList = new ArrayList<>();
        try {
            mdaSysTableColList = iMdaSysTableColService.selectMdaSysTableColList(mdaSysTableColumnVo);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取元数据表列信息成功", mdaSysTableColList);
    }

    @ApiOperation(value = "根据Id获取元数据列信息")
    @ApiImplicitParam(name = "columnId", value = "元数据列id", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/mdaSysTableCol/get", method = RequestMethod.POST)
    public WebResult<MdaSysTableColumn> findById(String columnId) {
        WebResult<MdaSysTableColumn> webResult = new WebResult<>();
        MdaSysTableColumn mdaSysTableColumn = new MdaSysTableColumn();
        try {
            mdaSysTableColumn = iMdaSysTableColService.selectMdaSysTableColumnById(columnId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取元数据列信息成功", mdaSysTableColumn);
    }

    @ApiOperation(value = "新增")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "labelId", value = "标签Id", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "tableId", value = "所属表Id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "columnName", value = "列名", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "columnCnName", value = "列中文名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "columnDataTypeId", value = "列数据类型Id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "dimTransId", value = "对应维表表名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "unit", value = "单位", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dataType", value = "数据类型", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "columnStatus", value = "列状态", required = false, paramType = "query", dataType = "int") })
    @RequestMapping(value = "/mdaSysTableCol/save", method = RequestMethod.POST)
    public WebResult<MdaSysTableColumn> save(@ApiIgnore MdaSysTableColumn mdaSysTableColumn) {
        WebResult<MdaSysTableColumn> webResult = new WebResult<>();
        try {
            iMdaSysTableColService.addMdaSysTableColumn(mdaSysTableColumn);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("保存元数据表列成功", mdaSysTableColumn);
    }

    @ApiOperation(value = "修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "columnId", value = "列Id", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "labelId", value = "标签Id", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "tableId", value = "所属表Id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "columnName", value = "列名", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "columnCnName", value = "列中文名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "columnDataTypeId", value = "列数据类型Id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "dimTransId", value = "对应维表表名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "unit", value = "单位", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dataType", value = "数据类型", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "columnStatus", value = "列状态", required = false, paramType = "query", dataType = "int") })
    @RequestMapping(value = "/mdaSysTableCol/update", method = RequestMethod.POST)
    public WebResult<String> edit(@ApiIgnore MdaSysTableColumn mdaSysTableColumn) {
        WebResult<String> webResult = new WebResult<>();
        MdaSysTableColumn oldmdaSysTableCol = new MdaSysTableColumn();
        try {
            oldmdaSysTableCol = iMdaSysTableColService.selectMdaSysTableColumnById(mdaSysTableColumn.getColumnId());
        } catch (BaseException e) {
            webResult.fail(e);
        }
        oldmdaSysTableCol = fromToBean(mdaSysTableColumn, oldmdaSysTableCol);
        try {
            iMdaSysTableColService.modifyMdaSysTableColumn(oldmdaSysTableCol);
        } catch (BaseException e1) {
            webResult.fail(e1);
        }
        return webResult.success("修改信息成功", SUCCESS);
    }

    @ApiOperation(value = "列Id")
    @ApiImplicitParam(name = "columnId", value = "列Id", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/mdaSysTableCol/delete", method = RequestMethod.POST)
    public WebResult<String> del(String columnId) {
        WebResult<String> webResult = new WebResult<>();
        try {
            iMdaSysTableColService.deleteMdaSysTableColumnById(columnId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("删除元数据列成功", SUCCESS);
    }

    /**
     * Description:封装实体信息
     *
     * @param mda
     * @param oldmda
     * @return
     */
    private MdaSysTableColumn fromToBean(MdaSysTableColumn mda, MdaSysTableColumn oldmda) {
        if (StringUtils.isNotBlank(mda.getLabelId())) {
            oldmda.setLabelId(mda.getLabelId());
        }
        if (StringUtils.isNotBlank(mda.getTableId())) {
            oldmda.setTableId(mda.getTableId());
        }
        if (StringUtils.isNotBlank(mda.getColumnName())) {
            oldmda.setColumnName(mda.getColumnName());
        }
        if (StringUtils.isNotBlank(mda.getColumnCnName())) {
            oldmda.setColumnCnName(mda.getColumnCnName());
        }
        if (null != mda.getColumnDataTypeId()) {
            oldmda.setColumnDataTypeId(mda.getColumnDataTypeId());
        }
        if (StringUtils.isNotBlank(mda.getDimTransId())) {
            oldmda.setDimTransId(mda.getDimTransId());
        }
        if (StringUtils.isNotBlank(mda.getUnit())) {
            oldmda.setUnit(mda.getUnit());
        }
        if (StringUtils.isNotBlank(mda.getDataType())) {
            oldmda.setDataType(mda.getDataType());
        }
        if (null != mda.getColumnStatus()) {
            oldmda.setColumnStatus(mda.getColumnStatus());
        }
        if (StringUtil.isNotBlank(mda.getCountRulesCode())) {
            oldmda.setCountRulesCode(mda.getCountRulesCode());
        }
        return oldmda;
    }

}
