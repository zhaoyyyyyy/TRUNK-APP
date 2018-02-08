/*
 * @(#)TemplateAttrRelController.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.syspush.controller;

import java.util.ArrayList;
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
import com.asiainfo.biapp.si.loc.core.syspush.entity.TemplateAttrRel;
import com.asiainfo.biapp.si.loc.core.syspush.service.ITemplateAttrRelService;
import com.asiainfo.biapp.si.loc.core.syspush.vo.TemplateAttrRelVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Title : TemplateAttrRelController
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
@Api(value = "模板与带出属性关系",description="王瑞冬")
@RequestMapping("api/syspush")
@RestController
public class TemplateAttrRelController extends BaseController<TemplateAttrRel>{
    
    @Autowired
    private ITemplateAttrRelService iTemplateAttrRelService;
    
    private static final String SUCCESS = "success";
    
    @ApiOperation(value = "分页查询模板与带出属性关系")
    @RequestMapping(value = "/templateAttrRel/queryPage", method = RequestMethod.POST)
    public Page<TemplateAttrRel> list(@ModelAttribute Page<TemplateAttrRel> page,@ModelAttribute TemplateAttrRelVo templateAttrRelVo) {
        Page<TemplateAttrRel> templateAttrRelPage = new Page<>();
        try {
            templateAttrRelPage = iTemplateAttrRelService.selectTemplateAttrRelPageList(page, templateAttrRelVo);
        } catch (BaseException e) {
            templateAttrRelPage.fail(e);
        }
        return templateAttrRelPage;
    }
    
    @ApiOperation(value = "不分页查询模板与带出属性关系列表")
    @RequestMapping(value = "/templateAttrRel/queryList", method = RequestMethod.POST)
    public WebResult<List<TemplateAttrRel>> findList(@ModelAttribute TemplateAttrRelVo templateAttrRelVo) {
        WebResult<List<TemplateAttrRel>> webResult = new WebResult<>();
        List<TemplateAttrRel> templateAttrRelList = new ArrayList<>();
        try {
            templateAttrRelList = iTemplateAttrRelService.selectTemplateAttrRelList(templateAttrRelVo);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取模板与带出属性关系成功.", templateAttrRelList);
    }
    
    @ApiOperation(value = "根据ID查询模板与带出属性关系")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "templateId", value = "模板编码", required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "labelId", value = "标签ID", required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "labelColumnId", value = "标签列名", required = true, paramType = "query", dataType = "string") })
    @RequestMapping(value = "/templateAttrRel/get",method = RequestMethod.POST)
    public WebResult<TemplateAttrRel> findById(String templateId, String labelId, String labelColumnId) {
        WebResult<TemplateAttrRel> webResult = new WebResult<>();
        TemplateAttrRel templateAttrRel = new TemplateAttrRel();
        try {
            templateAttrRel = iTemplateAttrRelService.selectTemplateAttrRelById(templateId,labelId,labelColumnId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取模板与带出属性关系成功", templateAttrRel);
    }
    
    @ApiOperation(value = "新增模板与带出属性关系")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "labelId", value = "标签ID", required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "labelColumnId", value = "标签列名", required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "isVerticalAttr", value = "是否纵表属性", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "sortNum", value = "排序", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "status", value = "状态", required = false, paramType = "query", dataType = "int") })    
    @RequestMapping(value = "/templateAttrRel/save", method = RequestMethod.POST)
    public WebResult<String> save(@ApiIgnore TemplateAttrRel templateAttrRel) {
            WebResult<String> webResult = new WebResult<>();
            try {
                iTemplateAttrRelService.addTemplateAttrRel(templateAttrRel);
            } catch (BaseException e) {
                return webResult.fail(e);
            }
            return webResult.success("新增模板与带出属性关系成功", SUCCESS);
    }
    
    @ApiOperation(value = "修改模板与带出属性关系")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "templateId", value = "模板编码", required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "labelId", value = "标签ID", required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "labelColumnId", value = "标签列名", required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "isVerticalAttr", value = "是否纵表属性", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "sortNum", value = "排序", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "status", value = "状态", required = false, paramType = "query", dataType = "int") })
    @RequestMapping(value = "/templateAttrRel/update", method = RequestMethod.POST)
    public WebResult<String> edit(@ApiIgnore TemplateAttrRel templateAttrRel) {
        WebResult<String> webResult = new WebResult<>();
        TemplateAttrRel oldTem = new TemplateAttrRel();
        try {
            oldTem = iTemplateAttrRelService.selectTemplateAttrRelById(templateAttrRel.getTemplateId(),templateAttrRel.getLabelId(),templateAttrRel.getLabelColumnId());
            oldTem = fromToBean(templateAttrRel, oldTem);
            iTemplateAttrRelService.modifyTemplateAttrRel(oldTem);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("修改模板与带出属性关系成功", SUCCESS);
    }
    
    @ApiOperation(value = "删除模板与带出属性关系")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "templateId", value = "模板编码", required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "labelId", value = "标签ID", required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "labelColumnId", value = "标签列名", required = true, paramType = "query", dataType = "string") })
    @RequestMapping(value = "/templateAttrRel/delete", method = RequestMethod.POST)
    public WebResult<String> del(String templateId, String labelId, String labelColumnId) {
        WebResult<String> webResult = new WebResult<>();
        try {
            iTemplateAttrRelService.deleteTemplateAttrRelById(templateId,labelId,labelColumnId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("删除模板与带出属性关系成功", SUCCESS);
    }
    
    /**
     * 封装实体信息
     *
     * @param tem
     * @param oldTem
     * @return
     */
    public TemplateAttrRel fromToBean(TemplateAttrRel tem, TemplateAttrRel oldTem){
        if(StringUtil.isNoneBlank(tem.getTemplateId())){
            oldTem.setTemplateId(tem.getTemplateId());
        }
        if(StringUtil.isNoneBlank(tem.getLabelId())){
            oldTem.setLabelId(tem.getLabelId());
        }
        if(StringUtil.isNoneBlank(tem.getLabelColumnId())){
            oldTem.setLabelColumnId(tem.getLabelColumnId());
        }
        if(null != tem.getIsVerticalAttr()){
            oldTem.setIsVerticalAttr(tem.getIsVerticalAttr());
        }
        if(null != tem.getSortNum()){
            oldTem.setSortNum(tem.getSortNum());
        }
        if(null != tem.getStatus()){
            oldTem.setStatus(tem.getStatus());
        }
        return oldTem;
    }

}
