/*
 * @(#)DimTableDataController.java
 *
 * CopyRight (c) 2018 北京亚信智慧数据科技有限公司 保留所有权利。
 */

package com.asiainfo.biapp.si.loc.core.dimtable.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.loc.base.controller.BaseController;
import com.asiainfo.biapp.si.loc.base.exception.BaseException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.utils.WebResult;
import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableData;
import com.asiainfo.biapp.si.loc.core.dimtable.service.IDimTableDataService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * Title : DimTableDataController
 * <p/>
 * Description : 
 * <p/>
 * CopyRight : CopyRight (c) 2018
 * <p/>
 * Company : 北京亚信智慧数据科技有限公司
 * <p/>
 * JDK Version Used : JDK 8.0 +
 * <p/>
 * Modification History	:
 * <p/>
 * <pre>NO.    Date    Modified By    Why & What is modified</pre>
 * <pre>1    2018年1月3日    hongfb        Created</pre>
 * <p/>
 *
 * @author  hongfb
 * @version 1.0.0.2018年1月3日
 */

@Api(value = "维表数据管理",description="洪福宝")
@RequestMapping("api/dimtabledata")
@RestController
public class DimTableDataController extends BaseController<DimTableData>{
    
    @Autowired
    private IDimTableDataService iDimTableDataService;
    
    
    @ApiOperation(value = "分页查询维表值")
    @RequestMapping(value = "/queryPage", method = RequestMethod.POST)
    public WebResult<Page<DimTableData>> queryPage(@ModelAttribute Page<DimTableData> page, @ModelAttribute DimTableData dimTableData){
        WebResult<Page<DimTableData>> webResult = new WebResult<>();
        Page<DimTableData> dimTableDatas = null;
        try {
            dimTableDatas = iDimTableDataService.selectDimTableDataPageList(page, dimTableData);
        } catch (BaseException e) {
            return webResult.fail(e);
        }
        
        return webResult.success("分页查询维表值成功", dimTableDatas);
    }
    

}
