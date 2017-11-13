
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
import com.asiainfo.biapp.si.coc.jauth.log.entity.LogMonitorDetail;
import com.asiainfo.biapp.si.coc.jauth.log.service.ILogMonitorDetailService;
import com.asiainfo.biapp.si.coc.jauth.log.vo.LogMonitorDetailVo;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;
/**
 * 
 * Title : LogMonitorDetailController
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
 * <pre>1    2017年10月24日    panweiwei        Created</pre>
 * <p/>
 *
 * @author  panweiwei
 * @version 1.0.0.2017年10月24日
 */
//@Api(value="后台监控日志")
@RequestMapping("api/monitor")
@RestController
public class LogMonitorDetailController  extends BaseController<LogMonitorDetail>{

    @Autowired
    private ILogMonitorDetailService logmonitorService;
    
    @Override
    protected BaseService<LogMonitorDetail, String> getBaseService() {
        return logmonitorService;
    }
    /**
     * 
     * Description: 查询方法
     *
     * @param page
     * @param cols
     */
    @ApiOperation(value = "显示后台监控日志的列表（分页形式）")
    @ApiImplicitParams({
        @ApiImplicitParam(name="cols",value="列名称",required=true,paramType="query",dataType="string",defaultValue="opTime,userId,ipAddr,levelId,threadName,nodeName,interfaceUrl,errorMsg"),
        @ApiImplicitParam(name="userId",value="用户名",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="opTimeStart",value="调用时间(开始)",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="opTimeEnd",value="调用时间(结束)",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="levelId",value="状态",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="errorMsg",value="信息",required=false,paramType="query",dataType="string")
    })
    @RequestMapping(value="/monitorPage/query",method=RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
    public String queryPageByParams(@ModelAttribute JQGridPage<LogMonitorDetail> page,String cols,
            @ApiIgnore LogMonitorDetailVo logMonitorDetailVo){
       JQGridPage<LogMonitorDetail> logmonitorList=logmonitorService.findLogMonitorList(page, logMonitorDetailVo);
       return JSONResult.page2Json(logmonitorList,cols);
    }
    
    @ApiOperation(value="保存")
    @ApiImplicitParams({
        @ApiImplicitParam(name="userId",value="用户名",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="sysId",value="系统进程",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="threadName",value="线程名称",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="opTime",value="调用时间",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="interfaceUrl",value="接口名称",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="ipAddr",value="ip地址",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="errorMsg",value="信息",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="levelId",value="状态",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="nodeName",value="节点名称",required=false,paramType="query",dataType="string")
    })
    @RequestMapping(value="/save",method=RequestMethod.POST)
    public String saveLogMonitorDetail(LogMonitorDetail logMonitorDetail){
        logmonitorService.save(logMonitorDetail);
        return "success";
    }
}
