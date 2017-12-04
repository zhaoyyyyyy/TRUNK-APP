/*
 * @(#)LabelRuleController.java
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
import com.asiainfo.biapp.si.loc.core.label.entity.LabelRule;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelRuleService;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelRuleVo;

/**
 * Title : LabelRuleController
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
 * 1    2017年11月22日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月22日
 */
//@Api(value = "客户群规则管理",description="张楠")
@RequestMapping("api/label")
@RestController
public class LabelRuleController extends BaseController<LabelRule> {

    @Autowired
    private ILabelRuleService iLabelRuleService;

    private static final String SUCCESS = "success";

    @ApiOperation(value = "分页查询客户群规则")
    @RequestMapping(value = "/labelRulePage/query", method = RequestMethod.POST)
    public Page<LabelRule> list(@ModelAttribute Page<LabelRule> page, @ModelAttribute LabelRuleVo labelRuleVo) {
        Page<LabelRule> labelRulePage = new Page<>();
        try {
            labelRulePage = iLabelRuleService.selectLabelRulePageList(page, labelRuleVo);
        } catch (BaseException e) {
            labelRulePage.fail(e);
        }
        return labelRulePage;
    }

    @ApiOperation(value = "不分页查询客户群规则列表")
    @RequestMapping(value = "/labelRule/queryList", method = RequestMethod.POST)
    public WebResult<List<LabelRule>> findList(@ModelAttribute LabelRuleVo labelRuleVo) {
        WebResult<List<LabelRule>> webResult = new WebResult<>();
        List<LabelRule> labelRuleList = new ArrayList<>();
        try {
            labelRuleList = iLabelRuleService.selectLabelRuleList(labelRuleVo);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取客户群规则成功.", labelRuleList);
    }

    @ApiOperation(value = "通过ID得到客户群规则")
    @ApiImplicitParam(name = "ruleId", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/labelRule/get", method = RequestMethod.POST)
    public WebResult<LabelRule> findById(String ruleId) throws BaseException {
        WebResult<LabelRule> webResult = new WebResult<>();
        LabelRule labelRule = new LabelRule();
        try {
            labelRule = iLabelRuleService.selectLabelRuleById(ruleId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取客户群规则成功.", labelRule);
    }

    @ApiOperation(value = "新增客户群规则")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "父规则ID", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "customId", value = "客户群标签ID", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "calcuElement", value = "计算元素", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "minVal", value = "最小值", required = false, paramType = "query", dataType = "double"),
            @ApiImplicitParam(name = "maxVal", value = "最大值", required = false, paramType = "query", dataType = "double"),
            @ApiImplicitParam(name = "sortNum", value = "计算顺序", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "customType", value = "类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "elementType", value = "计算元素类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "labelFlag", value = "是否标识", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "attrVal", value = "属性值", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "startTime", value = "起始时间", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "endTime", value = "截止时间", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "continueMinVal", value = "连续最小值", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "continueMaxVal", value = "连续最大值", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "leftZoneSign", value = "左区间符号", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "rightZoneSign", value = "右区间符号", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "exactValue", value = "精确值", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "darkValue", value = "模糊匹配值", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "tableName", value = "条件对应表名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "isNeedOffset", value = "是否需要偏移", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "virtualLabelName", value = "虚标签名称", required = false, paramType = "query", dataType = "string") })
    @RequestMapping(value = "/labelRule/save", method = RequestMethod.POST)
    public WebResult<String> save(@ApiIgnore LabelRule labelRule) {
        WebResult<String> webResult = new WebResult<>();
        try {
            iLabelRuleService.addLabelRule(labelRule);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("新增客户群规则成功", SUCCESS);
    }

    @ApiOperation(value = "修改客户群规则")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ruleId", value = "ID", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "parentId", value = "父规则ID", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "customId", value = "客户群标签ID", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "calcuElement", value = "计算元素", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "minVal", value = "最小值", required = false, paramType = "query", dataType = "double"),
            @ApiImplicitParam(name = "maxVal", value = "最大值", required = false, paramType = "query", dataType = "double"),
            @ApiImplicitParam(name = "sortNum", value = "计算顺序", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "customType", value = "类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "elementType", value = "计算元素类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "labelFlag", value = "是否标识", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "attrVal", value = "属性值", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "startTime", value = "起始时间", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "endTime", value = "截止时间", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "continueMinVal", value = "连续最小值", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "continueMaxVal", value = "连续最大值", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "leftZoneSign", value = "左区间符号", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "rightZoneSign", value = "右区间符号", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "exactValue", value = "精确值", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "darkValue", value = "模糊匹配值", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "tableName", value = "条件对应表名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "isNeedOffset", value = "是否需要偏移", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "virtualLabelName", value = "虚标签名称", required = false, paramType = "query", dataType = "string") })
    @RequestMapping(value = "/labelRule/update", method = RequestMethod.POST)
    public WebResult<String> edit(@ApiIgnore LabelRule labelRule) {
        WebResult<String> webResult = new WebResult<>();
        LabelRule oldLab = new LabelRule();
        try {
            oldLab = iLabelRuleService.selectLabelRuleById(labelRule.getRuleId());
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        oldLab = fromToBean(labelRule, oldLab);
        try {
            iLabelRuleService.modifyLabelRule(oldLab);
        } catch (BaseException e1) {
            return webResult.fail(e1);
        }
        return webResult.success("修改客户群规则成功", SUCCESS);
    }

    @ApiOperation(value = "删除客户群规则")
    @ApiImplicitParam(name = "ruleId", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/labelRule/delete", method = RequestMethod.POST)
    public WebResult<String> del(String ruleId) {
        WebResult<String> webResult = new WebResult<>();
        try {
            iLabelRuleService.deleteLabelRule(ruleId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("删除客户群规则成功", SUCCESS);
    }

    public LabelRule fromToBean(LabelRule lab, LabelRule oldLab) {
        if (StringUtil.isNotBlank(lab.getParentId())) {
            oldLab.setParentId(lab.getParentId());
        }
        if (StringUtil.isNotBlank(lab.getCustomId())) {
            oldLab.setCustomId(lab.getCustomId());
        }
        if (StringUtil.isNotBlank(lab.getCalcuElement())) {
            oldLab.setCalcuElement(lab.getCalcuElement());
        }
        if (null != lab.getMinVal()) {
            oldLab.setMinVal(lab.getMinVal());
        }
        if (null != lab.getMaxVal()) {
            oldLab.setMaxVal(lab.getMaxVal());
        }
        if (null != lab.getSortNum()) {
            oldLab.setSortNum(lab.getSortNum());
        }
        if (null != lab.getCustomType()) {
            oldLab.setCustomType(lab.getCustomType());
        }
        if (null != lab.getElementType()) {
            oldLab.setElementType(lab.getElementType());
        }
        if (null != lab.getLabelFlag()) {
            oldLab.setLabelFlag(lab.getLabelFlag());
        }
        if (StringUtil.isNotBlank(lab.getAttrVal())) {
            oldLab.setAttrVal(lab.getAttrVal());
        }
        if (StringUtil.isNotBlank(lab.getStartTime())) {
            oldLab.setStartTime(lab.getStartTime());
        }
        if (StringUtil.isNotBlank(lab.getEndTime())) {
            oldLab.setEndTime(lab.getEndTime());
        }
        if (StringUtil.isNotBlank(lab.getContiueMinVal())) {
            oldLab.setContiueMinVal(lab.getContiueMinVal());
        }
        if (StringUtil.isNotBlank(lab.getContiueMaxVal())) {
            oldLab.setContiueMaxVal(lab.getContiueMaxVal());
        }
        if (StringUtil.isNotBlank(lab.getLeftZoneSign())) {
            oldLab.setLeftZoneSign(lab.getLeftZoneSign());
        }
        if (StringUtil.isNotBlank(lab.getRightZoneSign())) {
            oldLab.setRightZoneSign(lab.getRightZoneSign());
        }
        if (StringUtil.isNotBlank(lab.getExactValue())) {
            oldLab.setExactValue(lab.getExactValue());
        }
        if (StringUtil.isNotBlank(lab.getDarkValue())) {
            oldLab.setDarkValue(lab.getDarkValue());
        }
        if (StringUtil.isNotBlank(lab.getTableName())) {
            oldLab.setTableName(lab.getTableName());
        }
        if (null != lab.getIsNeedOffset()) {
            oldLab.setIsNeedOffset(lab.getIsNeedOffset());
        }
        if (StringUtil.isNotBlank(lab.getVirtualLabelName())) {
            oldLab.setVirtualLabelName(lab.getVirtualLabelName());
        }
        return oldLab;
    }
}
