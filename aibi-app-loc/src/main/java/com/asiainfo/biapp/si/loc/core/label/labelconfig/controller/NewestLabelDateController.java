/*
 * @(#)NewestLabelDateController.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.labelconfig.controller;

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
import com.asiainfo.biapp.si.loc.core.label.labelconfig.entity.NewestLabelDate;
import com.asiainfo.biapp.si.loc.core.label.labelconfig.service.INewestLabelDateService;
import com.asiainfo.biapp.si.loc.core.label.labelconfig.vo.NewestLabelDateVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Title : NewestLabelDateController
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
 * <pre>1    2017年11月22日    wangrd        Created</pre>
 * <p/>
 *
 * @author  wangrd
 * @version 1.0.0.2017年11月22日
 */
@Api(value = "最新标签数据时间管理")
@RequestMapping("api/label")
@RestController
public class NewestLabelDateController extends BaseController<NewestLabelDate>{

    @Autowired
    private INewestLabelDateService iNewestLabelDateService;
    
    private static final String SUCCESS = "success";
    
    @ApiOperation(value ="分页查询")
    @RequestMapping(value = "/newestLabelDatePage/query", method = RequestMethod.POST)
    public Page<NewestLabelDate> queryPage(@ModelAttribute Page<NewestLabelDate> page,@ModelAttribute NewestLabelDateVo newestLabelDateVo) throws BaseException{
        Page<NewestLabelDate> newestLabelDatePage = new Page<>();
        try {
            newestLabelDatePage = iNewestLabelDateService.findNewestLabelDatePageList(page, newestLabelDateVo);
        } catch (BaseException e) {
            newestLabelDatePage.fail(e);
        }
        return newestLabelDatePage;
    }
    
    @ApiOperation(value = "查询列表")
    @RequestMapping(value = "/newestLabelDate/queryList", method = RequestMethod.POST)
    public WebResult<List<NewestLabelDate>> queryList(@ModelAttribute NewestLabelDateVo newestLabelDateVo) throws BaseException{
        WebResult<List<NewestLabelDate>> webRequest = new WebResult<>();
        List<NewestLabelDate> newestLabelDateList = new ArrayList<>();
        try {
            newestLabelDateList = iNewestLabelDateService.findNewestLabelDateList(newestLabelDateVo);
        } catch (BaseException e) {
           return webRequest.fail(e);
        }
        return webRequest.success("查询最新标签数据时间成功", newestLabelDateList);
    }
    
    @ApiOperation(value = "根据最新日数据日期查询")
    @ApiImplicitParam(name = "dayNewestDate", value = "最新日数据日期", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/newestLabelDate/get", method = RequestMethod.POST)
    public WebResult<NewestLabelDate> getByDayNewestDate(String dayNewestDate) throws BaseException{
        WebResult<NewestLabelDate> webResult = new WebResult<>();
        NewestLabelDate newestLabelDate = new NewestLabelDate();
        try {
            newestLabelDate = iNewestLabelDateService.getByDayNewestDate(dayNewestDate);
        } catch (BaseException e) {
            webResult.fail(e);
        }
        return webResult.success("获取最新标签数据时间成功", newestLabelDate);
    }
    
    @ApiOperation(value = "新增")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "dayNewestDate", value = "最新日数据日期", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "monthNewestDate", value = "最新月数据月份", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "dayNewestStatus", value = "是否统计过日数据", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "monthNewestStatus", value = "是否统计过月数据", required = false, paramType = "query", dataType = "int") })
    @RequestMapping(value = "/newestLabelDate/save", method = RequestMethod.POST)
    public WebResult<String> save(@ApiIgnore NewestLabelDate newestLabelDate) throws BaseException{
        WebResult<String> webResult = new WebResult<>();
        try {
            iNewestLabelDateService.saveT(newestLabelDate);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("新增最新标签数据时间成功", SUCCESS);
    }

    @ApiOperation(value = "修改")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "dayNewestDate", value = "最新日数据日期", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "monthNewestDate", value = "最新月数据月份", required = false, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "dayNewestStatus", value = "是否统计过日数据", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "monthNewestStatus", value = "是否统计过月数据", required = false, paramType = "query", dataType = "int") })
    @RequestMapping(value = "/newestLabelDate/update", method = RequestMethod.POST)
    public WebResult<String> update(@ApiIgnore NewestLabelDate newestLabelDate) throws BaseException{
        WebResult<String> webResult = new WebResult<>();
        NewestLabelDate oldnew = new NewestLabelDate();
        try {
            oldnew = iNewestLabelDateService.getByDayNewestDate(newestLabelDate.getDayNewestDate());
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        oldnew = fromToBean(newestLabelDate, oldnew);
        iNewestLabelDateService.update(oldnew);
        return webResult.success("修改最新标签数据时间成功", SUCCESS);
    }
    
    @ApiOperation(value = "删除")
    @ApiImplicitParam(name = "dayNewestDate", value = "最新日数据日期", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/newestLabelDate/delete", method = RequestMethod.POST)
    public WebResult<String> delete(String dayNewestDate) throws BaseException{
        WebResult<String> webResult = new WebResult<>();
        try {
            iNewestLabelDateService.deleteByDayNewestDate(dayNewestDate);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("删除最新标签数据时间成功", SUCCESS);
    }
    
    /**
     * 封装实体信息
     *
     * @param newest
     * @param oldnew
     * @return
     */
    public NewestLabelDate fromToBean(NewestLabelDate newest, NewestLabelDate oldNew){
        if(StringUtil.isNotBlank(newest.getDayNewestDate())){
            oldNew.setDayNewestDate(newest.getDayNewestDate());
        }
        if(StringUtil.isNoneBlank(newest.getMonthNewestDate())){
            oldNew.setMonthNewestDate(newest.getMonthNewestDate());
        }
        if(null != newest.getDayNewestStatus()){
            oldNew.setDayNewestStatus(newest.getDayNewestStatus());
        }
        if(null != newest.getMonthNewestStatus()){
            oldNew.setMonthNewestStatus(newest.getMonthNewestStatus());
        }
        return oldNew;
    }
}

