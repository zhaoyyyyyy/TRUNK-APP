/*
 * @(#)ApproveInfoController.java
 *
 * CopyRight (c) 2017 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.label.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.loc.base.controller.BaseController;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.utils.WebResult;
import com.asiainfo.biapp.si.loc.core.label.entity.ApproveInfo;
import com.asiainfo.biapp.si.loc.core.label.service.IApproveInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * Title : ApproveInfoController
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
 * <pre>1    2017年12月15日    lilin7        Created</pre>
 * <p/>
 *
 * @author  lilin7
 * @version 1.0.0.2017年12月15日
 */
@Api(value = "审批状态信息")
@RequestMapping("api/label")
@RestController
public class ApproveInfoController extends BaseController<ApproveInfo>{
    
    @Autowired
    private IApproveInfoService iApproveInfoService;
    
    private static final String SUCCESS = "success";

    @ApiOperation(value = "根据资源id得到标签审批信息")
    @ApiImplicitParam(name="resourceId",value="资源Id",required=false,paramType="query",dataType="string")
    @RequestMapping(value = "ApproveInfo/",method = RequestMethod.POST)
    public WebResult<ApproveInfo> findByresourceId(String resourceId){
        WebResult<ApproveInfo> webResult = new WebResult<>();
        ApproveInfo approveInfo = new ApproveInfo();
        try {
            approveInfo = iApproveInfoService.selectApproveInfo(resourceId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("获取标签信息成功", approveInfo);
    }
    
    @ApiOperation(value = "根据资源Id删除标签审批信息")
    @ApiImplicitParam(name="resourceId",value="资源Id",required=false,paramType="query",dataType="string")
    @RequestMapping(value = "ApproveInfo/delete",method = RequestMethod.POST)
    public WebResult<String> del(String resourceId){
        WebResult<String> webResult = new WebResult<>();
        try {
            iApproveInfoService.deleteApproveInfo(resourceId);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("删除审批信息成功",SUCCESS);
    }
    
}
