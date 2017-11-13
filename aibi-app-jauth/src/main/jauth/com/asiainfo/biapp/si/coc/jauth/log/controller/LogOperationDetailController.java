
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
import com.asiainfo.biapp.si.coc.jauth.log.entity.LogOperationDetail;
import com.asiainfo.biapp.si.coc.jauth.log.service.ILogOperationDetailService;
import com.asiainfo.biapp.si.coc.jauth.log.service.impl.LogOperationDetailServiceImpl;
import com.asiainfo.biapp.si.coc.jauth.log.vo.LogOperationDetailVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 * Title : LogOperDetailController
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
 * <pre>1    2017年10月20日    panweiwei        Created</pre>
 * <p/>
 *
 * @author  panweiwei
 * @version 1.0.0.2017年10月20日
 */
//@Api(value="用户操作日志")
@RequestMapping("api/logOperation")
@RestController
public class LogOperationDetailController  extends BaseController<LogOperationDetail>{

	@Autowired
	private ILogOperationDetailService logoperationService;
	
    @Override
    protected BaseService<LogOperationDetail, String> getBaseService() {
        return logoperationService;
    }
    /**
     * 
     * Description: 分页查询
     *
     * @param page
     * @param cols
     */
    @ApiOperation(value="显示用户操作日志列表(分页形式)")
    @ApiImplicitParams({
        @ApiImplicitParam(name="cols",value="列名称",required=true,paramType="query",dataType="string",defaultValue="opTime,userId,sysId,resource.type,resource.resourceName,params,ipAddr"),
        @ApiImplicitParam(name="userId",value="用户名",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="sysId",value="操作",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="opTimeStart",value="操作时间(开始)",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="opTimeEnd",value="操作时间(结束)",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="type",value="资源类型",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="resourceName",value="资源名称",required=false,paramType="query",dataType="string")
    })
    @RequestMapping(value="/logoperPage/query",method=RequestMethod.POST,produces={ MediaType.APPLICATION_JSON_VALUE })
    public String queryPage(@ModelAttribute JQGridPage<LogOperationDetail> page,String cols,
    		@ApiIgnore LogOperationDetailVo logOperationDetailVo){
        JQGridPage<LogOperationDetail> logoperList = logoperationService.findLogOperList(page, logOperationDetailVo);
        return JSONResult.page2Json(logoperList,cols);
    }
    /**
     * 
     * Description: 保存方法
     *
     * @param logOperationDetail
     */
    @ApiOperation(value="保存用户操作日志")
    @ApiImplicitParams({
        @ApiImplicitParam(name="userId",value="用户名",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="sysId",value="操作",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="resourceId",value="资源id",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="opTime",value="操作时间",required=false,paramType="query",dataType="date"),
        @ApiImplicitParam(name="ipAddr",value="ip地址",required=false,paramType="query",dataType="string"),
        @ApiImplicitParam(name="params",value="信息",required=false,paramType="query",dataType="string")
    })
    @RequestMapping(value="/save",method=RequestMethod.POST)
    public String saveLogOperationDetail(LogOperationDetail logOperationDetail){
        logoperationService.save(logOperationDetail);
        return "success";
    }
   
}
