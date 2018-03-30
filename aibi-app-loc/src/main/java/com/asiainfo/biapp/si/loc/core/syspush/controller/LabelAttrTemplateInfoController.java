/*
 * @(#)LabelAttrTemplateInfoController.java
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
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelAttrTemplateInfo;
import com.asiainfo.biapp.si.loc.core.syspush.service.ILabelAttrTemplateInfoService;
import com.asiainfo.biapp.si.loc.core.syspush.vo.LabelAttrTemplateInfoVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Title : LabelAttrTemplateInfoController
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
 * <pre>1    2018年1月19日    wangrd        Created</pre>
 * <p/>
 *
 * @author  wangrd
 * @version 1.0.0.2018年1月19日
 */
@Api(value = "009.05->-客户群标签属性模板信息表",description="王瑞冬")
@RequestMapping("api/syspush")
@RestController
public class LabelAttrTemplateInfoController extends BaseController<LabelAttrTemplateInfo>{

    @Autowired
    private ILabelAttrTemplateInfoService iLabelAttrTemplateInfoService;
    
    private static final String SUCCESS = "success";
    
    @ApiOperation(value = "分页查询客户群标签属性模板信息")
    @RequestMapping(value = "/labelAttrTemplateInfo/queryPage", method = RequestMethod.POST)
    public Page<LabelAttrTemplateInfo> list(@ModelAttribute Page<LabelAttrTemplateInfo> page,@ModelAttribute LabelAttrTemplateInfoVo labelAttrTemplateInfoVo) {
        Page<LabelAttrTemplateInfo> labelAttrTemplateInfoPage = new Page<>();
        try {
            labelAttrTemplateInfoPage = iLabelAttrTemplateInfoService.selectLabelAttrTemplateInfoPageList(page, labelAttrTemplateInfoVo);
        } catch (BaseException e) {
            labelAttrTemplateInfoPage.fail(e);
        }
        return labelAttrTemplateInfoPage;
    }
    
    @ApiOperation(value = "不分页查询客户群标签属性模板信息")
    @RequestMapping(value = "/labelAttrTemplateInfo/queryList", method = RequestMethod.POST)
    public WebResult<List<LabelAttrTemplateInfo>> findList(@ModelAttribute LabelAttrTemplateInfoVo labelAttrTemplateInfoVo) {
        WebResult<List<LabelAttrTemplateInfo>> webResult = new WebResult<>();
        List<LabelAttrTemplateInfo> labelAttrTemplateInfoList = new ArrayList<>();
        try {
            labelAttrTemplateInfoList = iLabelAttrTemplateInfoService.selectLabelAttrTemplateInfoList(labelAttrTemplateInfoVo);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取客户群标签属性模板信息成功.", labelAttrTemplateInfoList);
    }
    
    @ApiOperation(value = "根据ID查询客户群标签属性模板信息")
        @ApiImplicitParam(name = "templateId", value = "模板编码", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/labelAttrTemplateInfo/get",method = RequestMethod.POST)
    public WebResult<LabelAttrTemplateInfo> findById(String templateId) {
        WebResult<LabelAttrTemplateInfo> webResult = new WebResult<>();
        LabelAttrTemplateInfo labelAttrTemplateInfo = new LabelAttrTemplateInfo();
        try {
            labelAttrTemplateInfo = iLabelAttrTemplateInfoService.selectLabelAttrTemplateInfoById(templateId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取客户群标签属性模板信息成功", labelAttrTemplateInfo);
    }
    
    @ApiOperation(value = "新增客户群标签属性模板信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "templateId", value = "模板编码", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "templateName", value = "模板名称", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "configId", value = "专区ID", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "createUserId", value = "创建用户", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "status", value = "状态", required = false, paramType = "query", dataType = "int")})    @RequestMapping(value = "/labelAttrTemplateInfo/save", method = RequestMethod.POST)
    public WebResult<String> save(@ApiIgnore LabelAttrTemplateInfo labelAttrTemplateInfo) {
            WebResult<String> webResult = new WebResult<>();
            labelAttrTemplateInfo.setCreateTime(new Date());
            try {
                iLabelAttrTemplateInfoService.addLabelAttrTemplateInfo(labelAttrTemplateInfo);
            } catch (BaseException e) {
                return webResult.fail(e);
            }
            return webResult.success("新增客户群标签属性模板信息成功", SUCCESS);
    }
    
    @ApiOperation(value = "修改客户群标签属性模板信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "templateId", value = "模板编码", required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "templateName", value = "模板名称", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "configId", value = "专区ID", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "createUserId", value = "创建用户", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "status", value = "状态", required = false, paramType = "query", dataType = "int")})
    @RequestMapping(value = "/labelAttrTemplateInfo/update", method = RequestMethod.POST)
    public WebResult<String> edit(@ApiIgnore LabelAttrTemplateInfo labelAttrTemplateInfo) {
        WebResult<String> webResult = new WebResult<>();
        LabelAttrTemplateInfo oldLab = new LabelAttrTemplateInfo();
        try {
            oldLab = iLabelAttrTemplateInfoService.selectLabelAttrTemplateInfoById(labelAttrTemplateInfo.getTemplateId());
            oldLab = fromToBean(labelAttrTemplateInfo, oldLab);
            iLabelAttrTemplateInfoService.modifyLabelAttrTemplateInfo(oldLab);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("修改客户群标签属性模板信息成功", SUCCESS);
    }
    
    @ApiOperation(value = "删除客户群标签属性模板信息")
        @ApiImplicitParam(name = "templateId", value = "模板编码", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/labelAttrTemplateInfo/delete", method = RequestMethod.POST)
    public WebResult<String> del(String templateId) {
        WebResult<String> webResult = new WebResult<>();
        try {
            iLabelAttrTemplateInfoService.deleteLabelAttrTemplateInfoById(templateId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("删除客户群标签属性模板信息成功", SUCCESS);
    }
    
    /**
     * 封装实体信息
     *
     * @param lab
     * @param oldLab
     * @return
     */
    public LabelAttrTemplateInfo fromToBean(LabelAttrTemplateInfo lab, LabelAttrTemplateInfo oldLab){
        if(StringUtil.isNoneBlank(lab.getTemplateId())){
            oldLab.setTemplateId(lab.getTemplateId());
        }
        if(StringUtil.isNoneBlank(lab.getTemplateName())){
            oldLab.setTemplateName(lab.getTemplateName());
        }
        if(null != lab.getConfigId()){
            oldLab.setConfigId(lab.getConfigId());
        }
        if(StringUtil.isNoneBlank(lab.getCreateUserId())){
            oldLab.setCreateUserId(lab.getCreateUserId());
        }
        if(null != lab.getCreateTime()){
            oldLab.setCreateTime(lab.getCreateTime());
        }
        if(null != lab.getStatus()){
            oldLab.setStatus(lab.getStatus());
        }
        return oldLab;
    }
}
