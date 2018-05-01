/*
 * @(#)LabelPushReqController.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.controller;

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
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelPushReq;
import com.asiainfo.biapp.si.loc.core.syspush.service.ILabelPushReqService;
import com.asiainfo.biapp.si.loc.core.syspush.vo.LabelPushReqVo;

/**
 * Title : LabelPushReqController
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
 * <pre>1    2018年1月18日    wangrd        Created</pre>
 * <p/>
 *
 * @author  wangrd
 * @version 1.0.0.2018年1月18日
 */
@Api(value = "009.02->-推送请求信息表",description="王瑞冬")
@RequestMapping("api/syspush")
@RestController
public class LabelPushReqController extends BaseController<LabelPushReq>{

    @Autowired
    private ILabelPushReqService iLabelPushReqService;
    
    private static final String SUCCESS = "success";
    
    @ApiOperation(value = "分页查询推送请求信息")
    @RequestMapping(value = "/labelPushReq/queryPage", method = RequestMethod.POST)
    public Page<LabelPushReq> list(@ModelAttribute Page<LabelPushReq> page,@ModelAttribute LabelPushReqVo labelPushReqVo) {
        Page<LabelPushReq> labelPushReqPage = new Page<>();
        try {
            labelPushReqPage = iLabelPushReqService.selectLabelPushReqPageList(page, labelPushReqVo);
            if(labelPushReqPage.getData()!=null && labelPushReqPage.getData().size() >0){
                List<LabelPushReq> labelPushReqList = labelPushReqPage.getData();
                for(LabelPushReq labelPushReq : labelPushReqList){
                    labelPushReq.setLabelPushCycle(labelPushReq.getLabelPushCycle());
                    labelPushReq.getLabelPushCycle().setLabelInfo(labelPushReq.getLabelPushCycle().getLabelInfo());
                    labelPushReq.getLabelPushCycle().setSysInfo(labelPushReq.getLabelPushCycle().getSysInfo());
                }
            }
        } catch (BaseException e) {
            labelPushReqPage.fail(e);
        }
        return labelPushReqPage;
    }
    
    @ApiOperation(value = "不分页查询推送请求信息列表")
    @RequestMapping(value = "/labelPushReq/queryList", method = RequestMethod.POST)
    public WebResult<List<LabelPushReq>> findList(@ModelAttribute LabelPushReqVo labelPushReqVo) {
        WebResult<List<LabelPushReq>> webResult = new WebResult<>();
        List<LabelPushReq> labelPushReqList = new ArrayList<>();
        try {
            labelPushReqList = iLabelPushReqService.selectLabelPushReqList(labelPushReqVo);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取推送请求信息成功.", labelPushReqList);
    }
    
    @ApiOperation(value = "根据ID查询推送请求信息")
    @ApiImplicitParam(name = "reqId", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/labelPushReq/get",method = RequestMethod.POST)
    public WebResult<LabelPushReq> findById(String reqId) {
        WebResult<LabelPushReq> webResult = new WebResult<>();
        LabelPushReq labelPushReq = new LabelPushReq();
        try {
            labelPushReq = iLabelPushReqService.selectLabelPushReqById(reqId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取推送请求信息成功", labelPushReq);
    }
    
    @ApiOperation(value = "新增推送请求信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "reqId", value = "推送请求ID", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "recodeId", value = "推送设置记录ID", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "dataDate", value = "数据日期", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "pushStatus", value = "推送状态", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "isHasList", value = "是否带清单", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, paramType = "query", dataType = "date"),
        @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, paramType = "query", dataType = "date"),
        @ApiImplicitParam(name = "listTableName", value = "清单表名", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "exceInfo", value = "失败异常信息", required = false, paramType = "query", dataType = "string")})
    @RequestMapping(value = "/labelPushReq/save", method = RequestMethod.POST)
    public WebResult<String> save(@ApiIgnore LabelPushReq labelPushReq) {
            WebResult<String> webResult = new WebResult<>();
            try {
                iLabelPushReqService.addLabelPushReq(labelPushReq);
            } catch (BaseException e) {
                return webResult.fail(e);
            }
            return webResult.success("新增推送请求信息成功", SUCCESS);
    }
    
    @ApiOperation(value = "修改推送请求信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "reqId", value = "系统平台ID", required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "recodeId", value = "系统平台名称", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "dataDate", value = "FTP IP", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "pushStatus", value = "FTP用户名", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "isHasList", value = "是否需要压缩", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "startTime", value = "压缩类型", required = false, paramType = "query", dataType = "date"),
        @ApiImplicitParam(name = "endTime", value = "是否需要表头", required = false, paramType = "query", dataType = "date"),
        @ApiImplicitParam(name = "listTableName", value = "文件类型", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "exceInfo", value = "是否推送重复记录数", required = false, paramType = "query", dataType = "string")})
    @RequestMapping(value = "/labelPushReq/update", method = RequestMethod.POST)
    public WebResult<String> edit(@ApiIgnore LabelPushReq labelPushReq) {
        WebResult<String> webResult = new WebResult<>();
        LabelPushReq oldLab = new LabelPushReq();
        try {
            oldLab = iLabelPushReqService.selectLabelPushReqById(labelPushReq.getReqId());
            oldLab = fromToBean(labelPushReq, oldLab);
            iLabelPushReqService.modifyLabelPushReq(oldLab);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("修改推送请求信息成功", SUCCESS);
    }
    
    @ApiOperation(value = "删除推送请求信息")
    @ApiImplicitParam(name = "reqId", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/labelPushReq/delete", method = RequestMethod.POST)
    public WebResult<String> del(String reqId) {
        WebResult<String> webResult = new WebResult<>();
        try {
            iLabelPushReqService.deleteLabelPushReqById(reqId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("删除推送请求信息成功", SUCCESS);
    }
    
    /**
     * 封装实体信息
     *
     * @param lab
     * @param oldLab
     * @return
     */
    public LabelPushReq fromToBean(LabelPushReq lab, LabelPushReq oldLab){
        if(StringUtil.isNoneBlank(lab.getReqId())){
            oldLab.setReqId(lab.getReqId());
        }
        if(StringUtil.isNoneBlank(lab.getRecodeId())){
            oldLab.setRecodeId(lab.getRecodeId());
        }
        if(StringUtil.isNoneBlank(lab.getDataDate())){
            oldLab.setDataDate(lab.getDataDate());
        }
        if(null != lab.getPushStatus()){
            oldLab.setPushStatus(lab.getPushStatus());
        }
        if(null != lab.getIsHasList()){
            oldLab.setIsHasList(lab.getIsHasList());
        }
        if(null != lab.getStartTime()){
            oldLab.setStartTime(lab.getStartTime());
        }
        if(null != lab.getEndTime()){
            oldLab.setEndTime(lab.getEndTime());
        }
        if(StringUtil.isNoneBlank(lab.getListTableName())){
            oldLab.setListTableName(lab.getListTableName());
        }
        if(StringUtil.isNoneBlank(lab.getExceInfo())){
            oldLab.setExceInfo(lab.getExceInfo());
        }
        return oldLab;
    }
}
