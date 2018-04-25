/*
 * @(#)AllUserMsgController.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.prefecture.controller;

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
import com.asiainfo.biapp.si.loc.base.utils.WebResult;
import com.asiainfo.biapp.si.loc.core.prefecture.entity.AllUserMsg;
import com.asiainfo.biapp.si.loc.core.prefecture.service.IAllUserMsgService;
import com.asiainfo.biapp.si.loc.core.prefecture.vo.AllUserMsgVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Title : AllUserMsgController
 * <p/>
 * Description :
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 5.0 +
 * <p/>
 * Modification History :
 * <p/>
 * 
 * <pre>
 * NO.    Date    Modified By    Why & What is modified
 * </pre>
 * 
 * <pre>
 * 1    2018年1月24日    zhangnan7        Created
 * </pre>
 * <p/>
 *
 * @author zhangnan7
 * @version 1.0.0.2018年1月24日
 */
@Api(value = "004.01->-全量表管理", description = "张楠")
@RequestMapping("api/back")
@RestController
public class AllUserMsgController extends BaseController<AllUserMsg> {

    @Autowired
    private IAllUserMsgService iAllUserMsgService;
    
    private static final String SUCCESS = "success";

    @ApiOperation(value = "分页查询全量表信息")
    @RequestMapping(value = "/allUserMsg/queryPage", method = RequestMethod.POST)
    public Page<AllUserMsg> queryPage(@ModelAttribute Page<AllUserMsg> page,
            @ModelAttribute AllUserMsgVo allUserMsgVo) {
        Page<AllUserMsg> allUserMsgPage = new Page<>();
        try {
            allUserMsgPage = iAllUserMsgService.selectAllUserMsgPageList(page, allUserMsgVo);
        } catch (BaseException e) {
            allUserMsgPage.fail(e);
        }
        return allUserMsgPage;
    }

    @ApiOperation(value = "不分页查询全量表信息")
    @RequestMapping(value = "/allUserMsg/queryList", method = RequestMethod.POST)
    public WebResult<List<AllUserMsg>> findList(@ModelAttribute AllUserMsgVo allUserMsgVo) {
        WebResult<List<AllUserMsg>> webResult = new WebResult<>();
        List<AllUserMsg> allUserMsgList = new ArrayList<>();
        try {
            allUserMsgList = iAllUserMsgService.selectAllUserMsgList(allUserMsgVo);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取全量表成功.", allUserMsgList);
    }
    
    @ApiOperation(value = "根据ID查询全量表信息")
    @ApiImplicitParam(name = "priKey", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/allUserMsg/get", method = RequestMethod.POST)
    public WebResult<AllUserMsg> getById(String priKey) throws BaseException {
        WebResult<AllUserMsg> webResult = new WebResult<>();
        AllUserMsg allUserMsg = new AllUserMsg();
        try {
            allUserMsg = iAllUserMsgService.selectAllUserMsgById(priKey);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取全量表成功.", allUserMsg);
    }
    
    @ApiOperation(value = "新增全量表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "configStatus", value = "状态", required = false, paramType = "query", dataType = "int")
            
    })
    @RequestMapping(value = "/allUserMsg/save", method = RequestMethod.POST)
    public WebResult<String> save(@ApiIgnore AllUserMsg allUserMsg) {
        WebResult<String> webResult = new WebResult<>();
        try {
            iAllUserMsgService.addAllUserMsg(allUserMsg);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("新增全量表成功", SUCCESS);
    }
    
    @ApiOperation(value = "修改全量表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "configStatus", value = "状态", required = false, paramType = "query", dataType = "int") 
            
    })
    @RequestMapping(value = "/allUserMsg/update", method = RequestMethod.POST)
    public WebResult<String> edit(@ApiIgnore AllUserMsg allUserMsg) {
        WebResult<String> webResult = new WebResult<>();
        try {
            iAllUserMsgService.modifyAllUserMsg(allUserMsg);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("修改全量表成功", SUCCESS);
    }
    
    @ApiOperation(value = "删除全量表信息")
    @ApiImplicitParam(name = "priKey", value = "ID", required = true, paramType = "query", dataType = "string")
    @RequestMapping(value = "/allUserMsg/delete", method = RequestMethod.POST)
    public WebResult<String> delete(String priKey) {
        WebResult<String> webResult = new WebResult<>();
        try {
            iAllUserMsgService.deleteAllUserMsg(priKey);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("删除全量表成功", SUCCESS);
    }

}
