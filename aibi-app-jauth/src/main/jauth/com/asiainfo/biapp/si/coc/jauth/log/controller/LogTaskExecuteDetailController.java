
package com.asiainfo.biapp.si.coc.jauth.log.controller;

import org.apache.commons.collections.functors.FalsePredicate;
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
import com.asiainfo.biapp.si.coc.jauth.log.entity.LogTaskExecuteDetail;
import com.asiainfo.biapp.si.coc.jauth.log.service.ILogTaskExecuteDetailService;
import com.asiainfo.biapp.si.coc.jauth.log.vo.LogTaskExecuteDetailVo;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.entity.LocTaskExeInfo;
import com.asiainfo.biapp.si.coc.jauth.sysmgr.service.LocTaskExeInfoService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;
/**
 * 
 * Title : LogTaskExecuteDetailController
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
@RequestMapping("api/taskexecute")
@RestController
public class LogTaskExecuteDetailController  extends BaseController<LogTaskExecuteDetail>{

    @Autowired
    private LocTaskExeInfoService locTaskExeInfoService;
    
	@Autowired
	private ILogTaskExecuteDetailService logtaskexecuteService;
	
    @Override
    protected BaseService<LogTaskExecuteDetail, String> getBaseService() {
        return logtaskexecuteService;
    }
    /**
     * 
     * Description: 分页查询方法
     *
     * @param page
     * @param cols
     */
    @ApiOperation(value="显示系统调度日志(分页形式)")
    @ApiImplicitParams({
        @ApiImplicitParam(name="cols",value="列名称",required=true,paramType="query",dataType="string",defaultValue="startTime,exeType,status,returnMsg,userId"),
        @ApiImplicitParam(name="stratTimeStart",value="调度时间(开始)",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="startTimeEnd",value="调度时间(结束)",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="status",value="响应状态",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="userId",value="执行人",required=false,paramType="query",dataType="string")
    })
    @RequestMapping(value="/taskexecutePage/query",method=RequestMethod.POST,produces={ MediaType.APPLICATION_JSON_VALUE })
    public String queryPageByParams(@ModelAttribute JQGridPage<LogTaskExecuteDetail> page,String cols,
            @ApiIgnore LogTaskExecuteDetailVo logTaskExecuteDetailVo){
        JQGridPage<LogTaskExecuteDetail> taskexecuteList = logtaskexecuteService.findTaskExeList(page, logTaskExecuteDetailVo);
        return JSONResult.page2Json(taskexecuteList,cols); 
    }
    /**
     * 
     * Description: 保存方法
     *
     * @param logTaskExecuteDetail
     */
    @ApiOperation(value="保存")
    @ApiImplicitParams({
        @ApiImplicitParam(name="userId",value="执行人",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="sysId",value="系统进程",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="taskExtId",value="调度id",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="startTime",value="调度时间",required=false,paramType="query",dataType="date"),
        @ApiImplicitParam(name="status",value="响应状态",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="resultMsg",value="响应信息",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="nodeName",value="节点名称",required=false,paramType="query",dataType="string")
    })
    @RequestMapping(value="/save",method=RequestMethod.POST)
    public String saveLogTaskExecuteDetail(LogTaskExecuteDetail logTaskExecuteDetail){
        LocTaskExeInfo locTaskExeInfo = locTaskExeInfoService.get(logTaskExecuteDetail.getTaskExtId());
        logTaskExecuteDetail.setExeParams(locTaskExeInfo.getSysId());  
        logtaskexecuteService.save(logTaskExecuteDetail);
        return "success";
    }

}
