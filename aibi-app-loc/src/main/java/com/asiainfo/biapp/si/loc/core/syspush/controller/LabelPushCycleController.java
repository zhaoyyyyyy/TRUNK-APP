/*
 * @(#)LabelPushCycleController.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.loc.base.controller.BaseController;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.base.utils.WebResult;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelPushCycle;
import com.asiainfo.biapp.si.loc.core.syspush.service.ILabelPushCycleService;
import com.asiainfo.biapp.si.loc.core.syspush.vo.LabelPushCycleVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Title : LabelPushCycleController
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2018年1月17日    wangrd        Created</pre>
 * <p/>
 *
 * @author  wangrd
 * @version 1.0.0.2018年1月17日
 */
@Api(value = "标签推送设置信息表",description="王瑞冬")
@RequestMapping("api/syspush")
@RestController
public class LabelPushCycleController extends BaseController<LabelPushCycle>{

    @Autowired
    private ILabelPushCycleService iLabelPushCycleService;
    
    private static final String SUCCESS = "success";
    
    @ApiOperation(value = "分页查询推送设置信息")
    @RequestMapping(value = "/labelPushCycle/queryPage", method = RequestMethod.POST)
    public Page<LabelPushCycle> list(@ModelAttribute Page<LabelPushCycle> page,@ModelAttribute LabelPushCycleVo labelPushCycleVo) throws BaseException{
        Page<LabelPushCycle> labelPushCyclePage = new Page<>();
        try {
            labelPushCyclePage = iLabelPushCycleService.selectLabelPushCyclePageList(page, labelPushCycleVo);
        } catch (BaseException e) {
            labelPushCyclePage.fail(e);
        }
        return labelPushCyclePage;
    }
    
    @ApiOperation(value = "不分页查询推送设置信息列表")
    @RequestMapping(value = "/labelPushCycle/queryList", method = RequestMethod.POST)
    public WebResult<List<LabelPushCycle>> findList(@ModelAttribute LabelPushCycleVo labelPushCycleVo) throws BaseException{
        WebResult<List<LabelPushCycle>> webResult = new WebResult<>();
        List<LabelPushCycle> labelPushCycleList = new ArrayList<>();
        try {
            labelPushCycleList = iLabelPushCycleService.selectLabelPushCycleList(labelPushCycleVo);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取推送设置信息成功.", labelPushCycleList);
    }
    
    @ApiOperation(value = "根据ID查询推送设置信息")
    @ApiImplicitParam(name = "recordId", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/labelPushCycle/get",method = RequestMethod.POST)
    public WebResult<LabelPushCycle> findById(String recordId) throws BaseException{
        WebResult<LabelPushCycle> webResult = new WebResult<>();
        LabelPushCycle labelPushCycle = new LabelPushCycle();
        try {
            labelPushCycle = iLabelPushCycleService.selectLabelPushCycleById(recordId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取推送设置信息成功", labelPushCycle);
    }
    
    @ApiOperation(value = "新增推送设置信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "recordId", value = "推送设置记录ID", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "customGroupId", value = "客户群ID", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "sysId", value = "对端系统ID", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "keyType", value = "主键标识类型", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "pushCycle", value = "推送周期", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "pushUserIds", value = "推送目标用户", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "status", value = "状态", required = false, paramType = "query", dataType = "int") })
    @RequestMapping(value = "/labelPushCycle/save", method = RequestMethod.POST)
    public WebResult<String> save(@ApiIgnore LabelPushCycle labelPushCycle) throws BaseException{
            WebResult<String> webResult = new WebResult<>();
            labelPushCycle.setModifyTime(new Date());
            try {
                iLabelPushCycleService.addLabelPushCycle(labelPushCycle);
            } catch (BaseException e) {
                return webResult.fail(e);
            }
            return webResult.success("新增推送设置信息成功", SUCCESS);
    }
    
    @ApiOperation(value = "修改推送设置信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "recordId", value = "推送设置记录ID", required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "customGroupId", value = "客户群ID", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "sysId", value = "对端系统ID", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "keyType", value = "主键标识类型", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "pushCycle", value = "推送周期", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "pushUserIds", value = "推送目标用户", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "status", value = "状态", required = false, paramType = "query", dataType = "int") })
    @RequestMapping(value = "/labelPushCycle/update", method = RequestMethod.POST)
    public WebResult<String> edit(@ApiIgnore LabelPushCycle labelPushCycle) throws BaseException{
        WebResult<String> webResult = new WebResult<>();
        labelPushCycle.setModifyTime(new Date());
        LabelPushCycle oldLab = new LabelPushCycle();
        try {
            oldLab = iLabelPushCycleService.selectLabelPushCycleById(labelPushCycle.getRecordId());
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        oldLab = fromToBean(labelPushCycle, oldLab);
        iLabelPushCycleService.update(oldLab);
        return webResult.success("修改推送设置信息成功", SUCCESS);
    }
    
    @ApiOperation(value = "删除推送设置信息")
    @ApiImplicitParam(name = "recordId", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/labelPushCycle/delete", method = RequestMethod.POST)
    public WebResult<String> del(String recordId) throws BaseException{
        WebResult<String> webResult = new WebResult<>();
        try {
            iLabelPushCycleService.deleteLabelPushCycleById(recordId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("删除推送设置信息成功", SUCCESS);
    }
    
    /**
     * 封装实体信息
     *
     * @param lab
     * @param oldLab
     * @return
     */
    public LabelPushCycle fromToBean(LabelPushCycle lab, LabelPushCycle oldLab){
        if(StringUtil.isNoneBlank(lab.getRecordId())){
            oldLab.setRecordId(lab.getRecordId());
        }
        if(StringUtil.isNoneBlank(lab.getCustomGroupId())){
            oldLab.setCustomGroupId(lab.getCustomGroupId());
        }
        if(StringUtil.isNoneBlank(lab.getSysId())){
            oldLab.setSysId(lab.getSysId());
        }
        if(null != (lab.getKeyType())){
            oldLab.setKeyType(lab.getKeyType());
        }
        if(null != lab.getPushCycle()){
            oldLab.setPushCycle(lab.getPushCycle());
        }
        if(StringUtil.isNoneBlank(lab.getPushUserIds())){
            oldLab.setPushUserIds(lab.getPushUserIds());
        }
        if(null != lab.getModifyTime()){
            oldLab.setModifyTime(lab.getModifyTime());
        }
        if(null != lab.getStatus()){
            oldLab.setStatus(lab.getStatus());
        }
        return oldLab;
    }
}
