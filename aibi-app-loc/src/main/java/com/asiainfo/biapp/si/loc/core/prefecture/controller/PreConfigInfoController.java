/*
 * @(#)PreConfigInfoController.java
 * 
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.prefecture.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import com.asiainfo.biapp.si.loc.base.utils.WebResult;
import com.asiainfo.biapp.si.loc.core.label.entity.MdaSysTable;
import com.asiainfo.biapp.si.loc.core.prefecture.entity.PreConfigInfo;
import com.asiainfo.biapp.si.loc.core.prefecture.service.IPreConfigInfoService;
import com.asiainfo.biapp.si.loc.core.prefecture.vo.PreConfigInfoVo;

/**
 * Title : PreConfigInfoController
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 1.8 +
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2017年11月7日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2017年11月7日
 */

@Api(value = "专区信息管理",description="张楠")
@RequestMapping("api/prefecture")
@RestController
public class PreConfigInfoController extends BaseController<PreConfigInfo> {

    @Autowired
    private IPreConfigInfoService iPreConfigInfoService;

    private static final String SUCCESS = "success";
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MdaSysTable.class);

    /**
     * 分页查询
     * 
     * @param page
     * @param preConfigInfo
     * @return
     */
    @ApiOperation(value = "分页查询专区信息")
    @RequestMapping(value = "/preConfigInfo/queryPage", method = RequestMethod.POST)
    public Page<PreConfigInfo> queryPage(@ModelAttribute Page<PreConfigInfo> page,
            @ModelAttribute PreConfigInfoVo preConfigInfoVo) {
        Page<PreConfigInfo> preConfigInfoPage = new Page<>();
        try {
            preConfigInfoPage = iPreConfigInfoService.selectPreConfigInfoPageList(page, preConfigInfoVo);
        } catch (BaseException e) {
            preConfigInfoPage.fail(e);
        }
        return preConfigInfoPage;
    }

    /**
     * 查询列表
     * 
     * @param preConfigInfo
     * @return
     */
    @ApiOperation(value = "不分页查询专区信息")
    @RequestMapping(value = "/preConfigInfo/queryList", method = RequestMethod.POST)
    public WebResult<List<PreConfigInfo>> findList(@ModelAttribute PreConfigInfoVo preConfigInfoVo) {
        WebResult<List<PreConfigInfo>> webResult = new WebResult<>();
        List<PreConfigInfo> preConfigInfoList = new ArrayList<>();
        try {
            preConfigInfoList = iPreConfigInfoService.selectPreConfigInfoList(preConfigInfoVo);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取专区成功.", preConfigInfoList);
    }

    /**
     * 通过ID查询专区
     * 
     * @param configId
     * @return
     */
    @ApiOperation(value = "根据ID查询专区信息")
    @ApiImplicitParam(name = "configId", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/preConfigInfo/get", method = RequestMethod.POST)
    public WebResult<PreConfigInfo> getById(String configId) throws BaseException {
        WebResult<PreConfigInfo> webResult = new WebResult<>();
        PreConfigInfo preConfigInfo = new PreConfigInfo();
        try {
            preConfigInfo = iPreConfigInfoService.selectPreConfigInfoById(configId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取专区成功.", preConfigInfo);
    }

    /**
     * 新增
     * 
     * @param preConfigInfo
     * @return
     */
    @ApiOperation(value = "新增专区信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgId", value = "组织编码", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dataAccessType", value = "专区类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "sourceName", value = "专区名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sourceEnName", value = "专区英文名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "contractName", value = "合同名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "configDesc", value = "专区描述", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "invalidTime", value = "失效时间", required = false, paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "configStatus", value = "状态", required = false, paramType = "query", dataType = "int") })
    @RequestMapping(value = "/preConfigInfo/save", method = RequestMethod.POST)
    public WebResult<String> save(@ApiIgnore PreConfigInfo preConfigInfo) {
        WebResult<String> webResult = new WebResult<>();
        PreConfigInfo rePre = new PreConfigInfo();
        User user = new User();
        try {
            rePre = iPreConfigInfoService.selectOneBySourceName(preConfigInfo.getSourceName());
            if (null != rePre) {
                return webResult.fail("专区名称已存在");
            }
            user = this.getLoginUser();
            preConfigInfo.setCreateUserId(user.getUserId());
            iPreConfigInfoService.addPreConfigInfo(preConfigInfo);
        } catch (BaseException e1) {
            return webResult.fail(e1);
        }
        return webResult.success("新增专区成功", SUCCESS);
    }

    /**
     * 修改
     *
     * @param preConfigInfo
     * @return
     */
    @ApiOperation(value = "修改专区信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "configId", value = "专区ID", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "orgId", value = "组织编码", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "dataAccessType", value = "专区类型", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "sourceName", value = "专区名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sourceEnName", value = "专区英文名", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "contractName", value = "合同名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "configDesc", value = "专区描述", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "invalidTime", value = "失效时间", required = false, paramType = "query", dataType = "date"),
            @ApiImplicitParam(name = "configStatus", value = "状态", required = false, paramType = "query", dataType = "int") })
    @RequestMapping(value = "/preConfigInfo/update", method = RequestMethod.POST)
    public WebResult<String> edit(@ApiIgnore PreConfigInfo preConfigInfo) {
        WebResult<String> webResult = new WebResult<>();
        try {
            iPreConfigInfoService.modifyPreConfigInfo(preConfigInfo);
        } catch (BaseException e1) {
            return webResult.fail(e1);
        }
        return webResult.success("修改专区成功", SUCCESS);
    }

    /**
     * 删除
     * 
     * @param id
     */
    @ApiOperation(value = "删除专区信息")
    @ApiImplicitParam(name = "configId", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/preConfigInfo/delete", method = RequestMethod.POST)
    public WebResult<String> delete(String configId) {
        WebResult<String> webResult = new WebResult<>();
        try {
            iPreConfigInfoService.deletePreConfigInfo(configId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("删除专区成功", SUCCESS);
    }

}
