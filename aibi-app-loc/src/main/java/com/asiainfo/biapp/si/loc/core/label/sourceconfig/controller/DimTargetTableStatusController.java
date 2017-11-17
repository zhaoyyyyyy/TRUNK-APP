/*
 * @(#)DimTargetTableStatusController.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.sourceconfig.controller;

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
import com.asiainfo.biapp.si.loc.core.label.sourceconfig.entity.DimTargetTableStatus;
import com.asiainfo.biapp.si.loc.core.label.sourceconfig.service.IDimTargetTableStatusService;
import com.asiainfo.biapp.si.loc.core.label.sourceconfig.vo.DimTargetTableStatusVo;

/**
 * Title : DimTargetTableStatusController
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
@Api(value = "指标源表状态管理")
@RequestMapping("api/source")
@RestController
public class DimTargetTableStatusController extends BaseController<DimTargetTableStatus> {

    @Autowired
    private IDimTargetTableStatusService iDimTargetTableStatusService;

    private static final String SUCCESS = "success";

    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/dimTargetTableStatusPage/query", method = RequestMethod.POST)
    public Page<DimTargetTableStatus> queryPage(@ModelAttribute Page<DimTargetTableStatus> page,
            @ModelAttribute DimTargetTableStatusVo dimTargetTableStatusVo) {
        Page<DimTargetTableStatus> dimTargetTableStatusPage = new Page<>();
        try {
            dimTargetTableStatusPage = iDimTargetTableStatusService.findDimTargetTableStatusPageList(page,
                dimTargetTableStatusVo);
        } catch (BaseException e) {
            dimTargetTableStatusPage.fail(e);
        }
        return dimTargetTableStatusPage;
    }

    @ApiOperation(value = "查询列表")
    @RequestMapping(value = "/dimTargetTableStatus/queryList", method = RequestMethod.POST)
    public WebResult<List<DimTargetTableStatus>> queryList(@ModelAttribute DimTargetTableStatusVo dimTargetTableStatusVo) {
        WebResult<List<DimTargetTableStatus>> webResult = new WebResult<>();
        List<DimTargetTableStatus> dimTargetTableStatusList = new ArrayList<>();
        try {
            dimTargetTableStatusList = iDimTargetTableStatusService
                .findDimTargetTableStatusList(dimTargetTableStatusVo);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取指标源表状态成功.", dimTargetTableStatusList);
    }

    @ApiOperation(value = "根据ID查询")
    @ApiImplicitParam(name = "labelId", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/dimTargetTableStatus/get", method = RequestMethod.POST)
    public WebResult<DimTargetTableStatus> getById(String labelId) throws BaseException {
        WebResult<DimTargetTableStatus> webResult = new WebResult<>();
        DimTargetTableStatus dimTargetTableStatus = new DimTargetTableStatus();
        try {
            dimTargetTableStatus = iDimTargetTableStatusService.getById(labelId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取指标源表状态成功.", dimTargetTableStatus);
    }

    @ApiOperation(value = "新增")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cooTableName", value = "数据表名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "cooTableType", value = "指标源表类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "manualExecution", value = "是否手动执行", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "isDoing", value = "是否正在执行", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "dataDate", value = "数据日期", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dataStatus", value = "数据状态", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "dataBatch", value = "批次", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "exceptionDesc", value = "错误信息描述", required = false, paramType = "query", dataType = "string") })
    @RequestMapping(value = "/dimTargetTableStatus/save", method = RequestMethod.POST)
    public WebResult<String> save(@ApiIgnore DimTargetTableStatus dimTargetTableStatus) {
        WebResult<String> webResult = new WebResult<>();
        try {
            iDimTargetTableStatusService.saveT(dimTargetTableStatus);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("新增指标源表状态成功", SUCCESS);
    }

    @ApiOperation(value = "修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "labelId", value = "ID", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "cooTableName", value = "数据表名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "cooTableType", value = "指标源表类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "manualExecution", value = "是否手动执行", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "isDoing", value = "是否正在执行", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "dataDate", value = "数据日期", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dataStatus", value = "数据状态", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "dataBatch", value = "批次", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "exceptionDesc", value = "错误信息描述", required = false, paramType = "query", dataType = "string") })
    @RequestMapping(value = "/dimTargetTableStatus/update", method = RequestMethod.POST)
    public WebResult<String> update(@ApiIgnore DimTargetTableStatus dimTargetTableStatus) {
        WebResult<String> webResult = new WebResult<>();
        DimTargetTableStatus oldDim = new DimTargetTableStatus();
        try {
            oldDim = iDimTargetTableStatusService.getById(dimTargetTableStatus.getLabelId());
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        oldDim = fromToBean(dimTargetTableStatus, oldDim);
        iDimTargetTableStatusService.updateT(oldDim);
        return webResult.success("修改指标源表状态成功", SUCCESS);
    }

    /**
     * 删除
     * 
     * @param id
     */
    @ApiOperation(value = "删除")
    @ApiImplicitParam(name = "labelId", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/dimTargetTableStatus/delete", method = RequestMethod.POST)
    public WebResult<String> delete(String labelId) {
        WebResult<String> webResult = new WebResult<>();
        try {
            iDimTargetTableStatusService.deleteById(labelId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("删除指标源表状态成功", SUCCESS);
    }
    
    /**
     * 封装实体信息
     *
     * @param dim
     * @param oldDim
     * @return
     */
    public DimTargetTableStatus fromToBean(DimTargetTableStatus dim, DimTargetTableStatus oldDim) {
        if (StringUtil.isNotBlank(dim.getCooTableName())) {
            oldDim.setCooTableName(dim.getCooTableName());
        }
        if (null != dim.getCooTableType()) {
            oldDim.setCooTableType(dim.getCooTableType());
        }
        if (null != dim.getManualExecution()) {
            oldDim.setManualExecution(dim.getManualExecution());
        }
        if (null != dim.getIsDoing()) {
            oldDim.setIsDoing(dim.getIsDoing());
        }
        if (StringUtil.isNotBlank(dim.getDataDate())) {
            oldDim.setDataDate(dim.getDataDate());
        }
        if (null != dim.getDataStatus()) {
            oldDim.setDataStatus(dim.getDataStatus());
        }
        if (null != dim.getDataBatch()) {
            oldDim.setDataBatch(dim.getDataBatch());
        }
        if (StringUtil.isNotBlank(dim.getExceptionDesc())) {
            oldDim.setExceptionDesc(dim.getExceptionDesc());
        }
        return oldDim;
    }

}
