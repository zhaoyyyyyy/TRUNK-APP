/*
 * @(#)LabelAttrRelController.java
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

import com.asiainfo.biapp.si.loc.auth.model.User;
import com.asiainfo.biapp.si.loc.base.controller.BaseController;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.base.utils.WebResult;
import com.asiainfo.biapp.si.loc.core.syspush.entity.LabelAttrRel;
import com.asiainfo.biapp.si.loc.core.syspush.service.ILabelAttrRelService;
import com.asiainfo.biapp.si.loc.core.syspush.vo.LabelAttrRelVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Title : LabelAttrRelController
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
@Api(value = "客户群标签与属性对应关系",description="王瑞冬")
@RequestMapping("api/syspush")
@RestController
public class LabelAttrRelController extends BaseController<LabelAttrRel>{

    @Autowired
    private ILabelAttrRelService iLabelAttrRelService;
    
    private static final String SUCCESS = "success";
    
    @ApiOperation(value = "分页查询客户群标签与属性对应关系")
    @RequestMapping(value = "/labelAttrRel/queryPage", method = RequestMethod.POST)
    public Page<LabelAttrRel> list(@ModelAttribute Page<LabelAttrRel> page,@ModelAttribute LabelAttrRelVo labelAttrRelVo){
        Page<LabelAttrRel> labelAttrRelPage = new Page<>();
        try {
            labelAttrRelPage = iLabelAttrRelService.selectLabelAttrRelPageList(page, labelAttrRelVo);
        } catch (BaseException e) {
            labelAttrRelPage.fail(e);
        }
        return labelAttrRelPage;
    }
    
    @ApiOperation(value = "不分页查询客户群标签与属性对应关系")
    @RequestMapping(value = "/labelAttrRel/queryList", method = RequestMethod.POST)
    public WebResult<List<LabelAttrRel>> findList(@ModelAttribute LabelAttrRelVo labelAttrRelVo) {
        WebResult<List<LabelAttrRel>> webResult = new WebResult<>();
        List<LabelAttrRel> labelAttrRelList = new ArrayList<>();
        try {
            labelAttrRelList = iLabelAttrRelService.selectLabelAttrRelList(labelAttrRelVo);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取客户群标签与属性对应关系成功.", labelAttrRelList);
    }
    
    @ApiOperation(value = "根据ID查询客户群标签与属性对应关系")
    @ApiImplicitParam(name = "priKey", value = "主键", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/LabelAttrRel/get",method = RequestMethod.POST)
    public WebResult<LabelAttrRel> findById(String priKey) {
        WebResult<LabelAttrRel> webResult = new WebResult<>();
        LabelAttrRel labelAttrRel = new LabelAttrRel();
        try {
            labelAttrRel = iLabelAttrRelService.selectLabelAttrRelById(priKey);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取客户群标签与属性对应关系成功", labelAttrRel);
    }
    
    @ApiOperation(value = "新增客户群标签与属性对应关系")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "priKey", value = "主键", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "recordId", value = "推送设置记录ID", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "labelId", value = "客户群标签ID", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "attrCol", value = "属性名", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "attrColType", value = "属性字段类型", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "attrColName", value = "属性中文名称", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "attrSource", value = "属性来源", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "labelOrCustomId", value = "来源标签(客户群清单)ID", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "labelOrCustomColumn", value = "来源标签(客户群清单)列名", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "isVerticalAttr", value = "是否纵表属性", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "listTableName", value = "清单表名", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "status", value = "状态", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "attrVal", value = "属性值", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "tableName", value = "条件对应表名", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "sortType", value = "排序类型", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "sortNum", value = "排序优先级", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "attrSettingType", value = "属性设置类型", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "attrCreateUserId", value = "属性创建人", required = false, paramType = "query", dataType = "string")})
    @RequestMapping(value = "/labelAttrRel/save", method = RequestMethod.POST)
    public WebResult<String> save(@ApiIgnore LabelAttrRel labelAttrRel) {
            WebResult<String> webResult = new WebResult<>();
            labelAttrRel.setModifyTime(new Date());
            User user = new User(); 
            try {
            	user = this.getLoginUser();
            	String userName = user.getUserName();
                iLabelAttrRelService.addLabelAttrRelPreview(labelAttrRel,userName);
            } catch (BaseException e) {
                return webResult.fail(e);
            }
            return webResult.success("新增客户群标签与属性对应关系成功", SUCCESS);
    }
    
    @ApiOperation(value = "修改客户群标签与属性对应关系")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "priKey", value = "主键", required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "recordId", value = "推送设置记录ID", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "labelId", value = "客户群标签ID", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "attrCol", value = "属性名", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "attrColType", value = "属性字段类型", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "attrColName", value = "属性中文名称", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "attrSource", value = "属性来源", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "labelOrCustomId", value = "来源标签(客户群清单)ID", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "labelOrCustomColumn", value = "来源标签(客户群清单)列名", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "isVerticalAttr", value = "是否纵表属性", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "listTableName", value = "清单表名", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "status", value = "状态", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "attrVal", value = "属性值", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "tableName", value = "条件对应表名", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "sortType", value = "排序类型", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "sortNum", value = "排序优先级", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "attrSettingType", value = "属性设置类型", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "attrCreateUserId", value = "属性创建人", required = false, paramType = "query", dataType = "string")})
    @RequestMapping(value = "/labelAttrRel/update", method = RequestMethod.POST)
    public WebResult<String> edit(@ApiIgnore LabelAttrRel labelAttrRel) {
        WebResult<String> webResult = new WebResult<>();
        labelAttrRel.setModifyTime(new Date());
        LabelAttrRel oldLab = new LabelAttrRel();
        try {
            oldLab = iLabelAttrRelService.selectLabelAttrRelById(labelAttrRel.getPriKey());
            oldLab = fromToBean(labelAttrRel, oldLab);
            iLabelAttrRelService.modifyLabelAttrRel(oldLab);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("修改客户群标签与属性对应关系成功", SUCCESS);
    }
    
    @ApiOperation(value = "删除客户群标签与属性对应关系")
    @ApiImplicitParam(name = "priKey", value = "主键", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/labelAttrRel/delete", method = RequestMethod.POST)
    public WebResult<String> del(String priKey) {
        WebResult<String> webResult = new WebResult<>();
        try {
            iLabelAttrRelService.deleteLabelAttrRelById(priKey);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("删除客户群标签与属性对应关系成功", SUCCESS);
    }
    
    /**
     * 封装实体信息
     *
     * @param lab
     * @param oldLab
     * @return
     */
    public LabelAttrRel fromToBean(LabelAttrRel lab, LabelAttrRel oldLab){
        if(StringUtil.isNoneBlank(lab.getPriKey())){
            oldLab.setPriKey(lab.getPriKey());
        }
        if(StringUtil.isNoneBlank(lab.getRecordId())){
            oldLab.setRecordId(lab.getRecordId());
        }
        if(StringUtil.isNoneBlank(lab.getLabelId())){
            oldLab.setLabelId(lab.getLabelId());
        }
        if(StringUtil.isNoneBlank(lab.getAttrCol())){
            oldLab.setAttrCol(lab.getAttrCol());
        }
        if(StringUtil.isNoneBlank(lab.getAttrColType())){
            oldLab.setAttrColType(lab.getAttrColType());
        }
        if(StringUtil.isNoneBlank(lab.getAttrColName())){
            oldLab.setAttrColName(lab.getAttrColName());
        }
        if(null != lab.getAttrSource()){
            oldLab.setAttrSource(lab.getAttrSource());
        }
        if(StringUtil.isNoneBlank(lab.getLabelOrCustomId())){
            oldLab.setLabelOrCustomId(lab.getLabelOrCustomId());
        }
        if(StringUtil.isNoneBlank(lab.getLabelOrCustomColumn())){
            oldLab.setLabelOrCustomColumn(lab.getLabelOrCustomColumn());
        }
        if(null != lab.getIsVerticalAttr()){
            oldLab.setIsVerticalAttr(lab.getIsVerticalAttr());
        }
        if(StringUtil.isNoneBlank(lab.getListTableName())){
            oldLab.setListTableName(lab.getListTableName());
        }
        if(null != lab.getStatus()){
            oldLab.setStatus(lab.getStatus());
        }
        if(StringUtil.isNoneBlank(lab.getAttrVal())){
            oldLab.setAttrVal(lab.getAttrVal());
        }
        if(StringUtil.isNoneBlank(lab.getTableName())){
            oldLab.setTableName(lab.getTableName());
        }
        if(StringUtil.isNoneBlank(lab.getSortType())){
            oldLab.setSortType(lab.getSortType());
        }
        if(null != lab.getSortNum()){
            oldLab.setSortNum(lab.getSortNum());
        }
        return oldLab;
    }
}
