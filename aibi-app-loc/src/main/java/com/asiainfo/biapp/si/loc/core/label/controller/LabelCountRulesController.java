/*
 * @(#)LabelCountRulesController.java
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
import com.asiainfo.biapp.si.loc.core.label.entity.LabelCountRules;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelCountRulesService;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelCountRulesVo;

/**
 * Title : LabelCountRulesController
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
 * 1    2017年11月20日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月20日
 */
@Api(value = "标签规则管理")
@RequestMapping("api/label")
@RestController
public class LabelCountRulesController extends BaseController<LabelCountRules> {

    @Autowired
    private ILabelCountRulesService iLabelCountRulesService;

    private static final String SUCCESS = "success";

    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/labelCountRulesPage/query", method = RequestMethod.POST)
    public Page<LabelCountRules> list(@ModelAttribute Page<LabelCountRules> page,
            @ModelAttribute LabelCountRulesVo labelCountRulesVo) {
        Page<LabelCountRules> labelCountRulesPage = new Page<>();
        try {
            labelCountRulesPage = iLabelCountRulesService.selectLabelCountRulesPageList(page, labelCountRulesVo);
        } catch (BaseException e) {
            labelCountRulesPage.fail(e);
        }
        return labelCountRulesPage;
    }

    @ApiOperation(value = "查询列表")
    @RequestMapping(value = "/labelCountRules/query", method = RequestMethod.POST)
    public WebResult<List<LabelCountRules>> findList(@ModelAttribute LabelCountRulesVo labelCountRulesVo) {
        WebResult<List<LabelCountRules>> webResult = new WebResult<>();
        List<LabelCountRules> labelCountRulesList = new ArrayList<>();
        try {
            labelCountRulesList = iLabelCountRulesService.selectLabelCountRulesList(labelCountRulesVo);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取标签规则成功.", labelCountRulesList);
    }

    @ApiOperation(value = "通过ID得到标签规则")
    @ApiImplicitParam(name = "countRulesCode", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/labelCountRules/get", method = RequestMethod.POST)
    public WebResult<LabelCountRules> findById(String countRulesCode) throws BaseException {
        WebResult<LabelCountRules> webResult = new WebResult<>();
        LabelCountRules labelCountRules = new LabelCountRules();
        try {
            labelCountRules = iLabelCountRulesService.selectLabelCountRulesById(countRulesCode);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取标签信息成功.", labelCountRules);
    }

    @ApiOperation(value = "新增一个标签规则")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dependIndex", value = "规则依赖的指标", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "countRules", value = "具体规则", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "countRulesDesc", value = "规则描述", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "whereSql", value = "过滤条件", required = false, paramType = "query", dataType = "string") })
    @RequestMapping(value = "/labelCountRules/save", method = RequestMethod.POST)
    public WebResult<String> save(@ApiIgnore LabelCountRules labelCountRules) {
        WebResult<String> webResult = new WebResult<>();
        try {
            iLabelCountRulesService.addLabelCountRules(labelCountRules);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("新增标签规则成功", SUCCESS);
    }

    @ApiOperation(value = "修改一个标签规则")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "countRulesCode", value = "ID", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dependIndex", value = "规则依赖的指标", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "countRules", value = "具体规则", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "countRulesDesc", value = "规则描述", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "whereSql", value = "过滤条件", required = false, paramType = "query", dataType = "string") })
    @RequestMapping(value = "/labelCountRules/update", method = RequestMethod.POST)
    public WebResult<String> edit(@ApiIgnore LabelCountRules labelCountRules) {
        WebResult<String> webResult = new WebResult<>();
        LabelCountRules oldLab = new LabelCountRules();
        try {
            oldLab = iLabelCountRulesService.selectLabelCountRulesById(labelCountRules.getCountRulesCode());
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        oldLab = fromToBean(labelCountRules, oldLab);
        try {
            iLabelCountRulesService.modifyLabelCountRules(oldLab);
        } catch (BaseException e1) {
            return webResult.fail(e1);
        }
        return webResult.success("修改标签规则成功", SUCCESS);
    }

    @ApiOperation(value = "删除")
    @ApiImplicitParam(name = "countRulesCode", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/labelCountRules/delete", method = RequestMethod.POST)
    public WebResult<String> del(String countRulesCode) {
        WebResult<String> webResult = new WebResult<>();
        try {
            iLabelCountRulesService.deleteLabelCountRules(countRulesCode);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("删除标签规则成功", SUCCESS);
    }

    public LabelCountRules fromToBean(LabelCountRules lab, LabelCountRules oldLab) {
        if (StringUtil.isNotBlank(lab.getDependIndex())) {
            oldLab.setDependIndex(lab.getDependIndex());
        }
        if (StringUtil.isNotBlank(lab.getCountRules())) {
            oldLab.setCountRules(lab.getCountRules());
        }
        if (StringUtil.isNotBlank(lab.getCountRulesDesc())) {
            oldLab.setCountRulesDesc(lab.getCountRulesDesc());
        }
        if (StringUtil.isNotBlank(lab.getWhereSql())) {
            oldLab.setWhereSql(lab.getWhereSql());
        }
        return oldLab;
    }

}
