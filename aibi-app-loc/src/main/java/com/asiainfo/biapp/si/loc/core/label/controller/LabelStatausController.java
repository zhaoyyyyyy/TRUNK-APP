/*
 * @(#)LabelStatausController.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.controller;

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
import com.asiainfo.biapp.si.loc.core.label.entity.LabelStatus;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelStatusService;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelStatusVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Title : LabelStatausController
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
 * <pre>1    2017年11月21日     wangrd     Created</pre>
 * <p/>
 *
 * @author   wangrd
 * @version 1.0.0.2017年11月21日
 */
@Api("标签状态管理")
@RequestMapping("api/label")
@RestController
public class LabelStatausController extends BaseController<LabelStatus>{
    
    @Autowired
    private ILabelStatusService iLabelStatusService;
    
    private static final String SUCCESS = "success";
    
    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/labelStatusPage/query", method = RequestMethod.POST)
    public Page<LabelStatus> list(@ModelAttribute Page<LabelStatus> page,@ModelAttribute LabelStatusVo labelStatusVo) throws BaseException{
        Page<LabelStatus> labelStatusPage = new Page <>();
        try {
            labelStatusPage=iLabelStatusService.selectLabelStatusPageList(page, labelStatusVo);
        } catch (BaseException e) {
            labelStatusPage.fail(e);
        }
        return labelStatusPage;
    }
    
    @ApiOperation(value = "查询列表")
    @RequestMapping(value = "/labelStatus/queryList", method = RequestMethod.POST)
    public WebResult<List<LabelStatus>> find(@ModelAttribute LabelStatusVo labelStatusVo) throws BaseException{
        WebResult<List<LabelStatus>> webResult = new WebResult<>();
        List<LabelStatus> labelStatusList = new ArrayList<>();
        try {
            labelStatusList = iLabelStatusService.selectLabelStatusList(labelStatusVo);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取标签状态成功", labelStatusList);
    }
    
    @ApiOperation(value = "根据ID查询")
    @ApiImplicitParam(name = "labelId", value="ID",required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/labelStatus/get", method = RequestMethod.POST)
    public WebResult<LabelStatus> findById(String labelId) throws BaseException{
        WebResult<LabelStatus> webResult = new WebResult<>();
        LabelStatus labelStatus = new LabelStatus();
        try {
           labelStatus = iLabelStatusService.selectLabelStatusById(labelId); 
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取标签状态成功", labelStatus);
    }
    
    @ApiOperation(value = "新增")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "labelId", value = "标签编码",required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "dataDate", value = "数据日期",required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "dataStatus", value = "数据生成状态",required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "exceptionDesc", value = "错误信息描述",required = false, paramType = "query", dataType = "string") })
    @RequestMapping(value = "/labelStatus/save", method = RequestMethod.POST)
    public WebResult<String> save(@ApiIgnore LabelStatus labelStatus) throws BaseException{
        WebResult<String> webResult = new WebResult<>();
        labelStatus.setDataInsertTime(new Date());
        try {
            iLabelStatusService.addLabelStatus(labelStatus);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("新增标签状态成功", SUCCESS);
    }
    
    @ApiOperation(value = "修改")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "labelId", value = "标签编码",required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "dataDate", value = "数据日期",required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "dataStatus", value = "数据生成状态",required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "exceptionDesc", value = "错误信息描述",required = false, paramType = "query", dataType = "string") 
    })
    @RequestMapping(value = "/labelStatus/update", method = RequestMethod.POST)
    public WebResult<String> edit(@ApiIgnore LabelStatus labelStatus) throws BaseException{
        WebResult<String> webResult = new WebResult<>();
        LabelStatus oldLab = new LabelStatus();
        try {
            oldLab = iLabelStatusService.selectLabelStatusById(labelStatus.getLabelId());
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        oldLab = fromToBean(labelStatus, oldLab);
        iLabelStatusService.update(oldLab);
        return webResult.success("修改标签状态成功", SUCCESS);
    }
    
    @ApiOperation(value = "删除")
    @ApiImplicitParam(name = "labelId", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/labelStatus/delete", method = RequestMethod.POST)
    public WebResult<String> del(String labelId) throws BaseException{
        WebResult<String> webResult = new WebResult<>();
        try {
            iLabelStatusService.deleteLabelStatusById(labelId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("删除标签状态成功", SUCCESS);
    } 
    
    /**
     * 封装实体信息
     *
     * @param lab
     * @param oldLab
     * @return
     */
    public LabelStatus fromToBean(LabelStatus lab, LabelStatus oldLab){
        if (StringUtil.isNotBlank(lab.getLabelId())){
            oldLab.setLabelId(lab.getLabelId());
        }
        if (StringUtil.isNoneBlank(lab.getDataDate())){
           oldLab.setDataDate(lab.getDataDate());
        }
        if(null != lab.getDataStatus()){
            oldLab.setDataStatus(lab.getDataStatus());
        }
        if(StringUtil.isNoneBlank(lab.getExceptionDesc())){
            oldLab.setExceptionDesc(lab.getExceptionDesc());
        }
        return oldLab;
    }
    
}
















