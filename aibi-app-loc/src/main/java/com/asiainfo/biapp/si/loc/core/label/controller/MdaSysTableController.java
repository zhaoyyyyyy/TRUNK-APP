/*
 * @(#)MdaSysTableController.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import com.asiainfo.biapp.si.loc.base.utils.WebResult;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTable;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTableModels;
import com.asiainfo.biapp.si.loc.core.label.service.IMdaSysTableService;
import com.asiainfo.biapp.si.loc.core.label.vo.MdaSysTableVo;

/**
 * Title : MdaSysTableController
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
 * 1    2017年11月20日    lilin7        Created
 * </pre>
 * <p/>
 *
 * @author lilin7
 * @version 1.0.0.2017年11月20日
 */
//@Api(value = "元数据表管理")
@RequestMapping("api/label")
@RestController
public class MdaSysTableController extends BaseController<MdaSysTable> {

    private static final String SUCCESS = "success";

    private static final Logger LOGGER = LoggerFactory.getLogger(MdaSysTable.class);

    @Autowired
    private IMdaSysTableService iMdaSysTableService;

    @ApiOperation(value = "元数据表分页查询")
    @RequestMapping(value = "/mdaSysTable/queryPage", method = RequestMethod.POST)
    public Page<MdaSysTable> listPage(@ModelAttribute Page<MdaSysTable> page,
            @ModelAttribute MdaSysTableVo mdaSysTableVo) {
        Page<MdaSysTable> mdaSysTablePage = new Page<>();
        try {
            mdaSysTablePage = iMdaSysTableService.selectMdaSysTablePageList(page, mdaSysTableVo);
        } catch (BaseException e) {
            mdaSysTablePage.fail(e);
        }
        return mdaSysTablePage;
    }

    @ApiOperation(value = "元数据表查询(不分页)")
    @RequestMapping(value = "/mdaSysTable/queryList", method = RequestMethod.POST)
    public WebResult<List<MdaSysTable>> findList(@ModelAttribute MdaSysTableVo mdaSysTableVo) {
        WebResult<List<MdaSysTable>> webResult = new WebResult<>();
        List<MdaSysTable> mdaSysTableList = new ArrayList<>();
        try {
            mdaSysTableList = iMdaSysTableService.selectMdaSysTableList(mdaSysTableVo);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取元数据表信息成功", mdaSysTableList);
    }

    @ApiOperation(value = "根据id查找元数据")
    @ApiImplicitParam(name = "tableId", value = "表Id", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/mdaSysTable/get", method = RequestMethod.POST)
    public WebResult<MdaSysTable> findById(String tableId) {
        WebResult<MdaSysTable> webResult = new WebResult<>();
        MdaSysTable mdaSysTable = new MdaSysTable();
        try {
            mdaSysTable = iMdaSysTableService.selectMdaSysTableById(tableId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取元数据信息成功", mdaSysTable);
    };

    @ApiOperation(value = "新增")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "configId", value = "数据源Id", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "tableName", value = "表名", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "tableCnName", value = "表中文名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "tableDesc", value = "表描述", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "tablePostfix", value = "表后缀", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "tableSchema", value = "表所属schema", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "tableType", value = "宽表类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "updateCycle", value = "更新周期", required = false, paramType = "query", dataType = "int") })
    @RequestMapping(value = "/mdaSysTable/save", method = RequestMethod.POST)
    public WebResult<MdaSysTable> save(@ApiIgnore MdaSysTable mdaSysTable) {
        WebResult<MdaSysTable> webResult = new WebResult<>();
        mdaSysTable.setCreateTime(new Date());
        User user = new User();
        try {
            user = this.getLoginUser();
        } catch (BaseException e) {
            LOGGER.info("context", e);
        }
        mdaSysTable.setCreateUserId(user.getUserId());
        try {
            iMdaSysTableService.addMdaSysTable(mdaSysTable);
        } catch (BaseException e1) {
            return webResult.fail(e1);
        }
        return webResult.success("保存元数据信息成功", mdaSysTable);
    }

    @ApiOperation(value = "批量新增")
    @RequestMapping(value = "/mdaSysTable/saveAll", method = RequestMethod.POST)
    public WebResult<String> saveAll(MdaSysTableModels mdaSyaTableModels) {
        WebResult<String> webResult = new WebResult<>();
        List<MdaSysTable> mdaSysTableList = mdaSyaTableModels.getMdaSysTables();
        for (MdaSysTable mdaSysTable : mdaSysTableList) {
            try {
                iMdaSysTableService.addMdaSysTable(mdaSysTable);
            } catch (BaseException e) {
                webResult.fail(e);
            }
        }
        return webResult.success("批量新增成功", SUCCESS);
    }

    @ApiOperation(value = "修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableId", value = "表Id", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "configId", value = "数据源Id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "tableName", value = "表名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "tableCnName", value = "表中文名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "tableDesc", value = "表描述", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "tablePostfix", value = "表后缀", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "tableSchema", value = "表所属schema", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "tableType", value = "宽表类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "updateCycle", value = "更新周期", required = false, paramType = "query", dataType = "int") })
    @RequestMapping(value = "/mdaSysTable/update", method = RequestMethod.POST)
    public WebResult<String> edit(@ApiIgnore MdaSysTable mdaSysTable) {
        WebResult<String> webResult = new WebResult<>();
        MdaSysTable oldMdaSysTable = new MdaSysTable();
        try {
            oldMdaSysTable = iMdaSysTableService.selectMdaSysTableById(mdaSysTable.getTableId());
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        oldMdaSysTable = fromToBean(mdaSysTable, oldMdaSysTable);
        try {
            iMdaSysTableService.modifyMdaSysTable(oldMdaSysTable);
        } catch (BaseException e1) {
            return webResult.fail(e1);
        }
        return webResult.success("修改数据信息成功", SUCCESS);
    }

    @ApiOperation(value = "根据表Id删除元数据")
    @ApiImplicitParam(name = "tableId", value = "表Id", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/mdaSysTable/delete", method = RequestMethod.POST)
    public WebResult<String> del(String tableId) {
        WebResult<String> webResult = new WebResult<>();
        try {
            iMdaSysTableService.deleteMdaSysTableById(tableId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("删除元数据成功", SUCCESS);
    }

    /**
     * Description:封装实体信息
     *
     * @param mda
     * @param oldMda
     * @return
     */
    private MdaSysTable fromToBean(MdaSysTable mda, MdaSysTable oldMda) {
        if (StringUtils.isNotBlank(mda.getConfigId())) {
            oldMda.setConfigId(mda.getConfigId());
        }
        if (StringUtils.isNotBlank(mda.getTableName())) {
            oldMda.setTableName(mda.getTableName());
        }
        if (StringUtils.isNotBlank(mda.getTableCnName())) {
            oldMda.setTableCnName(mda.getTableCnName());
        }
        if (StringUtils.isNotBlank(mda.getTableDesc())) {
            oldMda.setTableDesc(mda.getTableDesc());
        }
        if (StringUtils.isNotBlank(mda.getTablePostfix())) {
            oldMda.setTablePostfix(mda.getTablePostfix());
        }
        if (StringUtils.isNotBlank(mda.getTableSchema())) {
            oldMda.setTableSchema(mda.getTableSchema());
        }
        if (null != mda.getTableType()) {
            oldMda.setTableType(mda.getTableType());
        }
        if (null != mda.getUpdateCycle()) {
            oldMda.setUpdateCycle(mda.getUpdateCycle());
        }
        return oldMda;
    }
}
