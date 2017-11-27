/*
 * @(#)LabelInfoController.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.base.utils.WebResult;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelInfo;
import com.asiainfo.biapp.si.loc.core.label.service.ILabelInfoService;
import com.asiainfo.biapp.si.loc.core.label.vo.LabelInfoVo;

/**
 * Title : LabelInfoController
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
 * 1    2017年11月16日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月16日
 */
@Api(value = "标签信息管理")
@RequestMapping("api/label")
@RestController
public class LabelInfoController extends BaseController<LabelInfo> {

    @Autowired
    private ILabelInfoService iLabelInfoService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LabelInfoController.class);

    private static final String SUCCESS = "success";

    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/labelInfoPage/query", method = RequestMethod.POST)
    public Page<LabelInfo> list(@ModelAttribute Page<LabelInfo> page, @ModelAttribute LabelInfoVo labelInfoVo) {
        Page<LabelInfo> labelInfoPage = new Page<>();
        try {
            labelInfoPage = iLabelInfoService.selectLabelInfoPageList(page, labelInfoVo);
        } catch (BaseException e) {
            labelInfoPage.fail(e);
        }
        return labelInfoPage;
    }

    @ApiOperation(value = "查询列表")
    @RequestMapping(value = "/labelInfo/query", method = RequestMethod.POST)
    public WebResult<List<LabelInfo>> queryList(@ModelAttribute LabelInfoVo labelInfoVo) {
        WebResult<List<LabelInfo>> webResult = new WebResult<>();
        List<LabelInfo> labelInfoList = new ArrayList<>();
        try {
            labelInfoList = iLabelInfoService.selectLabelInfoList(labelInfoVo);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        System.out.println(labelInfoList.get(0).getEffecTime());
        return webResult.success("获取标签信息成功.", labelInfoList);
    }

    @ApiOperation(value = "通过ID得到标签信息")
    @ApiImplicitParam(name = "labelId", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/labelInfo/get", method = RequestMethod.POST)
    public WebResult<LabelInfo> findById(String labelId) throws BaseException {
        WebResult<LabelInfo> webResult = new WebResult<>();
        LabelInfo labelInfo = new LabelInfo();
        try {
            labelInfo = iLabelInfoService.selectLabelInfoById(labelId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取标签信息成功.", labelInfo);
    }

    @ApiOperation(value = "新增一个标签信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyType", value = "主键标识类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "configId", value = "专区ID", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "orgId", value = "组织ID", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "countRulesCode", value = "规则编码", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "labelName", value = "标签名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "updateCycle", value = "更新周期", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "labelTypeId", value = "标签类型ID", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "categoryId", value = "标签分类ID", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "createTypeId", value = "创建方式ID", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "dataStatusId", value = "数据状态ID", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "dataDate", value = "最新数据时间", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "busiCaliber", value = "业务口径说明", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "effecTime", value = "生效时间", required = false, paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "failTime", value = "失效时间", required = false, paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "publishTime", value = "发布时间", required = false, paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "publishDesc", value = "发布描述", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "busiLegend", value = "业务说明", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "applySuggest", value = "应用建议", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "labelIdLevelDesc", value = "标签ID层级描述", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "isRegular", value = "是否正式标签", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "groupType", value = "群类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "sortNum", value = "排序字段", required = false, paramType = "query", dataType = "int") })
    @RequestMapping(value = "/labelInfo/save", method = RequestMethod.POST)
    public WebResult<String> save(@ApiIgnore LabelInfo labelInfo) {
        WebResult<String> webResult = new WebResult<>();
        labelInfo.setCreateTime(new Date());
        User user = new User();
        try {
            user = this.getLoginUser();
        } catch (BaseException e) {
            LOGGER.info("context", e);
        }
        labelInfo.setCreateUserId(user.getUserId());
        try {
            iLabelInfoService.addLabelInfo(labelInfo);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("新增标签信息成功", SUCCESS);
    }

    @ApiOperation(value = "修改一个标签信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "labelId", value = "ID", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "keyType", value = "主键标识类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "configId", value = "专区ID", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "orgId", value = "组织ID", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "countRulesCode", value = "规则编码", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "labelName", value = "标签名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "updateCycle", value = "更新周期", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "labelTypeId", value = "标签类型ID", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "categoryId", value = "标签分类ID", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "createTypeId", value = "创建方式ID", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "dataStatusId", value = "数据状态ID", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "dataDate", value = "最新数据时间", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "busiCaliber", value = "业务口径说明", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "effecTime", value = "生效时间", required = false, paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "failTime", value = "失效时间", required = false, paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "publishTime", value = "发布时间", required = false, paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "publishDesc", value = "发布描述", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "busiLegend", value = "业务说明", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "applySuggest", value = "应用建议", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "labelIdLevelDesc", value = "标签ID层级描述", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "isRegular", value = "是否正式标签", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "groupType", value = "群类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "sortNum", value = "排序字段", required = false, paramType = "query", dataType = "int") })
    @RequestMapping(value = "/labelInfo/update", method = RequestMethod.POST)
    public WebResult<String> edit(@ApiIgnore LabelInfo labelInfo) {
        WebResult<String> webResult = new WebResult<>();
        LabelInfo oldLab = new LabelInfo();
        try {
            oldLab = iLabelInfoService.selectLabelInfoById(labelInfo.getLabelId());
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        oldLab = fromToBean(labelInfo, oldLab);
        try {
            iLabelInfoService.modifyLabelInfo(oldLab);
        } catch (BaseException e1) {
            return webResult.fail(e1);
        }
        return webResult.success("修改标签信息成功", SUCCESS);
    }

    @ApiOperation(value = "删除")
    @ApiImplicitParam(name = "labelId", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/labelInfo/delete", method = RequestMethod.POST)
    public WebResult<String> del(String labelId) {
        WebResult<String> webResult = new WebResult<>();
        try {
            iLabelInfoService.deleteLabelInfo(labelId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("删除标签信息成功", SUCCESS);
    }

    public LabelInfo fromToBean(LabelInfo lab, LabelInfo oldLab) {
        if (null != lab.getKeyType()) {
            oldLab.setKeyType(lab.getKeyType());
        }
        if (StringUtil.isNotBlank(lab.getConfigId())) {
            oldLab.setConfigId(lab.getConfigId());
        }
        if (StringUtil.isNotBlank(lab.getOrgId())) {
            oldLab.setOrgId(lab.getOrgId());
        }
        if (StringUtil.isNotBlank(lab.getCountRulesCode())) {
            oldLab.setCountRulesCode(lab.getCountRulesCode());
        }
        if (StringUtil.isNotBlank(lab.getLabelName())) {
            oldLab.setLabelName(lab.getLabelName());
        }
        if (null != lab.getUpdateCycle()) {
            oldLab.setUpdateCycle(lab.getUpdateCycle());
        }
        if (null != lab.getLabelTypeId()) {
            oldLab.setLabelTypeId(lab.getLabelTypeId());
        }
        if (StringUtil.isNotBlank(lab.getCategoryId())) {
            oldLab.setCategoryId(lab.getCategoryId());
        }
        if (null != lab.getCreateTypeId()) {
            oldLab.setCreateTypeId(lab.getCreateTypeId());
        }
        if (null != lab.getDataStatusId()) {
            oldLab.setDataStatusId(lab.getDataStatusId());
        }
        if (StringUtil.isNotBlank(lab.getDataDate())) {
            oldLab.setDataDate(lab.getDataDate());
        }
        if (StringUtil.isNotBlank(lab.getBusiCaliber())) {
            oldLab.setBusiCaliber(lab.getBusiCaliber());
        }
        if (null != lab.getEffecTime()) {
            oldLab.setEffecTime(lab.getEffecTime());
        }
        if (null != lab.getFailTime()) {
            oldLab.setFailTime(lab.getFailTime());
        }
        if (null != lab.getPublishTime()) {
            oldLab.setPublishTime(lab.getPublishTime());
        }
        if (StringUtil.isNotBlank(lab.getPublishDesc())) {
            oldLab.setPublishDesc(lab.getPublishDesc());
        }
        if (StringUtil.isNotBlank(lab.getBusiLegend())) {
            oldLab.setBusiLegend(lab.getBusiLegend());
        }
        if (StringUtil.isNotBlank(lab.getApplySuggest())) {
            oldLab.setApplySuggest(lab.getApplySuggest());
        }
        if (StringUtil.isNotBlank(lab.getLabelIdLevelDesc())) {
            oldLab.setLabelIdLevelDesc(lab.getLabelIdLevelDesc());
        }
        if (null != lab.getIsRegular()) {
            oldLab.setIsRegular(lab.getIsRegular());
        }
        if (null != lab.getGroupType()) {
            oldLab.setGroupType(lab.getGroupType());
        }
        if (null != lab.getSortNum()) {
            oldLab.setSortNum(lab.getSortNum());
        }
        return oldLab;
    }

}
