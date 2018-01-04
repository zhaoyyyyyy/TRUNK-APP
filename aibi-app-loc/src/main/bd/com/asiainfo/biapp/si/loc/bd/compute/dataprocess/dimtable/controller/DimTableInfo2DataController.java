/*
 * @(#)DimTableInfo2DataController.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.bd.compute.dataprocess.dimtable.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.loc.base.controller.BaseController;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.utils.WebResult;
import com.asiainfo.biapp.si.loc.bd.compute.dataprocess.dimtable.task.service.IDimTableInfo2DataService;
import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * Title : DimTableInfo2DataServiceImpl
 * <p/>
 * Description : 本接口是dim_table的跑数接口，数据流是：DimTableInfo ——> DimTableData
 * <p/>
 * CopyRight : CopyRight (c) 2017
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 8.0 +
 * <p/>
 * Modification History :
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2018年1月3日    hongfb        Created</pre>
 * <p/>
 *
 * @author  hongfb
 * @version 1.0.0.2018年1月3日
 */

@Api(value = "维表信息跑数",description="洪福宝")
@RequestMapping("api/dimtableinfo2data")
@RestController
public class DimTableInfo2DataController extends BaseController<DimTableInfo> {
    
    private static final String SUCCESS = "success";
    
    @Autowired
    private IDimTableInfo2DataService iDimTableInfo2DataService;
    
    @ApiOperation(value = "维表信息跑数")
    @RequestMapping(value = "/execute", method = RequestMethod.POST)
    @ApiImplicitParam(name = "tableName", value = "表名称(可选，不传是全量跑)",paramType = "query", dataType = "string")
    public WebResult<String> execute(String tableName) {
        WebResult<String> webResult = new WebResult<>();
        try {
            iDimTableInfo2DataService.dimTableInfo2Data(tableName);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        return webResult.success("维表信息跑数成功", SUCCESS);
        
    }
    
    
}
