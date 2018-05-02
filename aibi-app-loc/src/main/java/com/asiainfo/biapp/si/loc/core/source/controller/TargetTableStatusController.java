/*
 * @(#)TargetTableStatusController.java
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
import com.asiainfo.biapp.si.loc.core.source.entity.TargetTableStatus;
import com.asiainfo.biapp.si.loc.core.source.service.ITargetTableStatusService;
import com.asiainfo.biapp.si.loc.core.source.vo.TargetTableStatusVo;

/**
 * Title : TargetTableStatusController
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
@Api(value = "006.03->-指标源表状态管理",description="张楠")
@RequestMapping("api/source")
@RestController
public class TargetTableStatusController extends BaseController<TargetTableStatus> {

    @Autowired
    private ITargetTableStatusService iTargetTableStatusService;

    private static final String SUCCESS = "success";

    @ApiOperation(value = "分页查询指标源表状态")
    @RequestMapping(value = "/TargetTableStatus/queryPage", method = RequestMethod.POST)
    public Page<TargetTableStatus> list(@ModelAttribute Page<TargetTableStatus> page,
            @ModelAttribute TargetTableStatusVo targetTableStatusVo) {
        Page<TargetTableStatus> TargetTableStatusPage = new Page<>();
        try {
            TargetTableStatusPage = iTargetTableStatusService.selectTargetTableStatusPageList(page, targetTableStatusVo);
        } catch (BaseException e) {
            TargetTableStatusPage.fail(e);
        }
        return TargetTableStatusPage;
    }

    @ApiOperation(value = "不分页查询指标源表状态")
    @RequestMapping(value = "/TargetTableStatus/queryList", method = RequestMethod.POST)
    public WebResult<List<TargetTableStatus>> findList(@ModelAttribute TargetTableStatusVo targetTableStatusVo) {
        WebResult<List<TargetTableStatus>> webResult = new WebResult<>();
        List<TargetTableStatus> TargetTableStatusList = new ArrayList<>();
        try {
            TargetTableStatusList = iTargetTableStatusService.selectTargetTableStatusList(targetTableStatusVo);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取指标源表状态成功.", TargetTableStatusList);
    }

    @ApiOperation(value = "根据ID查询指标源表状态")
    @ApiImplicitParam(name = "tableId", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/TargetTableStatus/get", method = RequestMethod.POST)
    public WebResult<TargetTableStatus> findById(String tableId) throws BaseException {
        WebResult<TargetTableStatus> webResult = new WebResult<>();
        TargetTableStatus targetTableStatus = new TargetTableStatus();
        try {
            targetTableStatus = iTargetTableStatusService.selectTargertTableStatusById(tableId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取指标源表状态成功.", targetTableStatus);
    }

    @ApiOperation(value = "新增指标源表状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cooTableName", value = "数据表名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "cooTableType", value = "指标源表类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "manualExecution", value = "是否手动执行", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "isDoing", value = "是否正在执行", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "dataDate", value = "数据日期", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dataStatus", value = "数据状态", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "dataBatch", value = "批次", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "exceptionDesc", value = "错误信息描述", required = false, paramType = "query", dataType = "string") })
    @RequestMapping(value = "/TargetTableStatus/save", method = RequestMethod.POST)
    public WebResult<String> save(@ApiIgnore TargetTableStatus targetTableStatus) {
        WebResult<String> webResult = new WebResult<>();
        try {
            iTargetTableStatusService.addTargertTableStatus(targetTableStatus);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("新增指标源表状态成功", SUCCESS);
    }

    @ApiOperation(value = "修改指标源表状态")
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
    @RequestMapping(value = "/TargetTableStatus/update", method = RequestMethod.POST)
    public WebResult<String> edit(@ApiIgnore TargetTableStatus targetTableStatus) {
        WebResult<String> webResult = new WebResult<>();
        TargetTableStatus oldTar = new TargetTableStatus();
        try {
            oldTar = iTargetTableStatusService.selectTargertTableStatusById(targetTableStatus.getSourceTableId());
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        oldTar = fromToBean(targetTableStatus, oldTar);
        try {
            iTargetTableStatusService.modifyTargertTableStatus(oldTar);
        } catch (BaseException e1) {
            return webResult.fail(e1);
        }
        return webResult.success("修改指标源表状态成功", SUCCESS);
    }

    /**
     * 删除
     * 
     * @param id
     */
    @ApiOperation(value = "删除指标源表状态")
    @ApiImplicitParam(name = "tableId", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/TargetTableStatus/delete", method = RequestMethod.POST)
    public WebResult<String> del(String tableId) {
        WebResult<String> webResult = new WebResult<>();
        try {
            iTargetTableStatusService.deleteTargertTableStatus(tableId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("删除指标源表状态成功", SUCCESS);
    }

    /**
     * 封装实体信息
     *
     * @param tar
     * @param oldTar
     * @return
     */
    public TargetTableStatus fromToBean(TargetTableStatus tar, TargetTableStatus oldTar) {
        if (StringUtil.isNotBlank(tar.getSourceTableName())) {
            oldTar.setSourceTableName(tar.getSourceTableName());
        }
        if (null != tar.getSourceTableType()) {
            oldTar.setSourceTableType(tar.getSourceTableType());
        }
        if (null != tar.getManualExecution()) {
            oldTar.setManualExecution(tar.getManualExecution());
        }
        if (null != tar.getIsDoing()) {
            oldTar.setIsDoing(tar.getIsDoing());
        }
        if (StringUtil.isNotBlank(tar.getDataDate())) {
            oldTar.setDataDate(tar.getDataDate());
        }
        if (null != tar.getDataStatus()) {
            oldTar.setDataStatus(tar.getDataStatus());
        }
        if (null != tar.getDataBatch()) {
            oldTar.setDataBatch(tar.getDataBatch());
        }
        if (StringUtil.isNotBlank(tar.getExceptionDesc())) {
            oldTar.setExceptionDesc(tar.getExceptionDesc());
        }
        return oldTar;
    }
    
    @ApiOperation(value = "根据读取周期查询指标源表最新日期")
    @RequestMapping(value = "/TargetTableStatus/selectLastestDateByCycle", method = RequestMethod.POST)
    public WebResult<String> selectLastestDateByCycle() throws BaseException {
        WebResult<String> webResult = new WebResult<>();
        String lastestDate = ""; 
        try {
            lastestDate = iTargetTableStatusService.selectLastestDateByCycle();
        } catch (BaseException e) {
            return webResult.fail("根据读取周期查询指标源表最新日期失败:",e);
        }
        return webResult.success("根据读取周期查询指标源表最新日期成功。", lastestDate);
    }

}
