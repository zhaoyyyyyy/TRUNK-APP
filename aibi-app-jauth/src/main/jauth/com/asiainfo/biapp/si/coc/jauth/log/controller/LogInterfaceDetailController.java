
package com.asiainfo.biapp.si.coc.jauth.log.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.coc.jauth.frame.controller.BaseController;
import com.asiainfo.biapp.si.coc.jauth.frame.json.JSONResult;
import com.asiainfo.biapp.si.coc.jauth.frame.page.JQGridPage;
import com.asiainfo.biapp.si.coc.jauth.frame.service.BaseService;
import com.asiainfo.biapp.si.coc.jauth.log.entity.LogInterfaceDetail;
import com.asiainfo.biapp.si.coc.jauth.log.service.ILogInterfaceDetailService;
import com.asiainfo.biapp.si.coc.jauth.log.vo.LogInterfaceDetailVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

//@Api(value="接口调用日志")
@RequestMapping("api/interface")
@RestController
public class LogInterfaceDetailController extends BaseController<LogInterfaceDetail>{
	
	@Autowired
	private ILogInterfaceDetailService loginterfaceService;
	
    @Override
    protected BaseService<LogInterfaceDetail, String> getBaseService() {
        return loginterfaceService;
    }

    @ApiOperation(value = "显示接口调用日志列表（分页形式）")
    @ApiImplicitParams({
        @ApiImplicitParam(name="cols",value="列名称",required=true,paramType="query",dataType="string",defaultValue="opTime,userId,ipAddr,interfaceName,interfaceUrl,inputParams,outputParams"),
        @ApiImplicitParam(name="opTimeStart",value="调用时间(开始)",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="opTimeEnd",value="调用时间(结束)",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="userId",value="用户名",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="interfaceName",value="接口名称",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="interfaceUrl",value="接口路径",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="inputParams",value="输入参数",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="outputParams",value="输出参数",required=false,paramType="query",dataType="string")
    })
    @RequestMapping(value="/interfacePage/query",method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
    public String queryPageByParams(@ModelAttribute JQGridPage<LogInterfaceDetail> page,String cols,
    		@ApiIgnore LogInterfaceDetailVo logInterfaceDetailVo){
        JQGridPage<LogInterfaceDetail> loginterfaceList =loginterfaceService.findLogInterList(page, logInterfaceDetailVo);
        return JSONResult.page2Json(loginterfaceList,cols);
    }

    @ApiOperation(value="保存")
    @ApiImplicitParams({
        @ApiImplicitParam(name="userId",value="用户名",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="sysId",value="系统进程",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="interfaceName",value="接口名称",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="interfaceUrl",value="接口路径",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="opTime",value="调用时间",required=false,paramType="query",dataType="date"),
        @ApiImplicitParam(name="ipAddr",value="ip地址",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="inputParams",value="输入参数",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="outputParams",value="输出参数",required=false,paramType="query",dataType="string")
    })
    @RequestMapping(value="/save",method = RequestMethod.POST)
    public String save(LogInterfaceDetail logInterfaceDetail){
        loginterfaceService.save(logInterfaceDetail);
        return "success";
    }

}
