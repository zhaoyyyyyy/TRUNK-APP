/*
 * @(#)DimTableInfoController.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.dimtable.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.base.controller.BaseController;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.base.utils.WebResult;
import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableInfo;
import com.asiainfo.biapp.si.loc.core.dimtable.service.IDimTableInfoService;
import com.asiainfo.biapp.si.loc.core.dimtable.vo.DimTableInfoVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Title : DimTableInfoController
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2017年11月27日    wangrd        Created</pre>
 * <p/>
 *
 * @author  wangrd
 * @version 1.0.0.2017年11月27日
 */
@Api(value = "维表信息管理",description="王瑞冬")
@RequestMapping("api/dimtable")
@RestController
public class DimTableInfoController extends BaseController<DimTableInfo>{
    
    @Autowired
    private IDimTableInfoService iDimTableInfoService;
    
    private static final String SUCCESS = "success";
    
    @ApiOperation(value = "分页查询维表信息")
    @RequestMapping(value = "/dimTableInfo/queryPage", method = RequestMethod.POST)
    public Page<DimTableInfo> list(@ModelAttribute Page<DimTableInfo> page,@ModelAttribute DimTableInfoVo dimTableInfoVo) {
        Page<DimTableInfo> dimTableInfoPage = new Page<>();
        try {
            dimTableInfoPage = iDimTableInfoService.selectDimTableInfoPageList(page, dimTableInfoVo);
        } catch (BaseException e) {
            dimTableInfoPage.fail(e);
        }
        return dimTableInfoPage;
    }
    
    @ApiOperation(value = "不分页查询维表信息列表")
    @RequestMapping(value = "/dimTableInfo/queryList", method = RequestMethod.POST)
    public WebResult<List<DimTableInfo>> findList(@ModelAttribute DimTableInfoVo dimTableInfoVo) {
        WebResult<List<DimTableInfo>> webResult = new WebResult<>();
        List<DimTableInfo> dimTableInfoList = new ArrayList<>();
        try {
            dimTableInfoList = iDimTableInfoService.selectDimTableInfoList(dimTableInfoVo);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取维表信息成功.", dimTableInfoList);
    }
    
    @ApiOperation(value = "根据ID查询维表信息")
    @ApiImplicitParam(name = "dimId", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/dimTableInfo/get",method = RequestMethod.POST)
    public WebResult<DimTableInfo> findById(String dimId) {
        WebResult<DimTableInfo> webResult = new WebResult<>();
        DimTableInfo dimTableInfo = new DimTableInfo();
        try {
            dimTableInfo = iDimTableInfoService.selectDimTableInfoById(dimId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取维表信息成功", dimTableInfo);
    }
    
    @ApiOperation(value = "新增维表信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "dimId", value = "维表主键", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "dimTableName", value = "维表表名", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "dimComment", value = "维表描述", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "dimCodeCol", value = "维表主键列名", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "dimValueCol", value = "维表值列名", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "configId", value = "所属专区ID", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "codeColType", value = "主键字段类型", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "dimWhere", value = "Where条件", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "createUserId", value = "创建人", required = false, paramType = "query", dataType = "string") })
    @RequestMapping(value = "/dimTableInfo/save", method = RequestMethod.POST)
    public WebResult<String> save(@ApiIgnore DimTableInfo dimTableInfo) {
            WebResult<String> webResult = new WebResult<>();
            dimTableInfo.setCreateTime(new Date());
            User user = new User(); 
            DimTableInfo dimTable = new DimTableInfo();
            try {
            	user = this.getLoginUser();
                dimTableInfo.setCreateUserId(user.getUserName());
                dimTable = iDimTableInfoService.selectOneByDimTableName(dimTableInfo.getDimTableName());
                if (null != dimTable) {
                    return webResult.fail("维表名称已存在");
                }
                iDimTableInfoService.addDimTableInfo(dimTableInfo);
            } catch (BaseException e) {
                return webResult.fail(e);
            }
            return webResult.success("新增维表信息成功", SUCCESS);
    }
    
    @ApiOperation(value = "修改维表信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "dimId", value = "维表主键", required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "dimTableName", value = "维表表名", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "dimComment", value = "维表描述", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "dimCodeCol", value = "维表主键列名", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "dimValueCol", value = "维表值列名", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "configId", value = "所属专区ID", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "codeColType", value = "主键字段类型", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "dimWhere", value = "Where条件", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "createUserId", value = "创建人", required = false, paramType = "query", dataType = "string") })
    @RequestMapping(value = "/dimTableInfo/update", method = RequestMethod.POST)
    public WebResult<String> edit(@ApiIgnore DimTableInfo dimTableInfo){
        WebResult<String> webResult = new WebResult<>();
        DimTableInfo oldDim = new DimTableInfo();
        DimTableInfo dimTable = new DimTableInfo();
        try {
            dimTable = iDimTableInfoService.selectOneByDimTableName(dimTableInfo.getDimTableName());
            oldDim = iDimTableInfoService.selectDimTableInfoById(dimTableInfo.getDimId());
            if(!dimTableInfo.getDimTableName().equals(oldDim.getDimTableName()) && null != dimTable){
                return webResult.fail("维表名称已存在");
            }
            oldDim = fromToBean(dimTableInfo, oldDim);
            iDimTableInfoService.modifyDimTableInfo(oldDim);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        
        return webResult.success("修改维表信息成功", SUCCESS);
    }
    
    @ApiOperation(value = "删除维表信息")
    @ApiImplicitParam(name = "dimId", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/dimTableInfo/delete", method = RequestMethod.POST)
    public WebResult<String> del(String dimId) {
        WebResult<String> webResult = new WebResult<>();
        try {
            iDimTableInfoService.deleteDimTableInfoById(dimId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("删除维表信息成功", SUCCESS);
    }
    
    /**
     * 封装实体信息
     *
     * @param dim
     * @param oldDim
     * @return
     */
    public DimTableInfo fromToBean(DimTableInfo dim, DimTableInfo oldDim){
        if(StringUtil.isNoneBlank(dim.getDimId())){
            oldDim.setDimId(dim.getDimId());
        }
        if(StringUtil.isNoneBlank(dim.getDimTableName())){
            oldDim.setDimTableName(dim.getDimTableName());
        }
        if(StringUtil.isNoneBlank(dim.getDimComment())){
            oldDim.setDimComment(dim.getDimComment());
        }
        if(StringUtil.isNoneBlank(dim.getDimCodeCol())){
            oldDim.setDimCodeCol(dim.getDimCodeCol());
        }
        if(StringUtil.isNoneBlank(dim.getDimValueCol())){
            oldDim.setDimValueCol(dim.getDimValueCol());
        }else{
            oldDim.setDimValueCol("");
        }
        if(StringUtil.isNoneBlank(dim.getConfigId())){
            oldDim.setConfigId(dim.getConfigId());
        }
        if(StringUtil.isNoneBlank(dim.getCodeColType())){
            oldDim.setCodeColType(dim.getCodeColType());
        }
        if(StringUtil.isNoneBlank(dim.getDimWhere())){
            oldDim.setDimWhere(dim.getDimWhere());
        }
        if(null != (dim.getCreateTime())){
            oldDim.setCreateTime(dim.getCreateTime());
        }
        if(StringUtil.isNoneBlank(dim.getCreateUserId())){
            oldDim.setCreateUserId(dim.getCreateUserId());
        }
        return oldDim;
    }

}
