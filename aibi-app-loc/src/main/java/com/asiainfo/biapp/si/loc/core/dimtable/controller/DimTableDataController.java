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
import com.asiainfo.biapp.si.loc.base.exception.ParamRequiredException;
import com.asiainfo.biapp.si.loc.base.page.Page;
import com.asiainfo.biapp.si.loc.base.utils.StringUtil;
import com.asiainfo.biapp.si.loc.base.utils.WebResult;
import com.asiainfo.biapp.si.loc.core.dimtable.dao.IDimTableInfoDao;
import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableData;
import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableDataId;
import com.asiainfo.biapp.si.loc.core.dimtable.entity.DimTableInfo;
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

@Api(value = "008.02->-维表数据管理",description="洪福宝")
@RequestMapping("api/dimtabledata")
@RestController
public class DimTableDataController extends BaseController<DimTableData>{
    
    private static final String SUCCESS = "success";
    
    public static volatile boolean dimTableDataIfRunning = false;
    
    @Autowired
    private IDimTableDataService iDimTableDataService;
    
    @Autowired
    private IDimTableInfoDao dimTableInfoDao;  
    
    /**
     * @Description:分页查询维表值
     * @param page 分页器
     * @param dimTableName  维表名称(必填)
     * @param dimKey    维表key(选填)
     * @param dimValue  维表值(选填，支持模糊搜索)
     * @return Page<DimTableData>
     */
    @ApiOperation(value = "分页查询维表值")
    @RequestMapping(value = "/queryPage", method = RequestMethod.POST)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "dimTableName", value = "维表名称(必填)",paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "dimKey", value = "维表key(选填)",paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "dimValue", value = "维表值(选填，支持模糊搜索)",paramType = "query", dataType = "string")
    })
    public Page<DimTableData> queryPage(@ModelAttribute Page<DimTableData> page,
            String dimTableName, String dimKey, String dimValue){
        Page<DimTableData> dimTableDatas = new Page<>();
        //组装参数
        DimTableData dimTableData = new DimTableData(new DimTableDataId(dimTableName));
        if (StringUtil.isNoneBlank(dimKey)) {
        	dimTableData.setDimKey(dimKey);
        }
        if (StringUtil.isNoneBlank(dimValue)) {
            dimTableData.setDimValue(dimValue);
        }
        try {
            dimTableDatas = iDimTableDataService.selectDimTableDataPageList(page, dimTableData);
        } catch (BaseException e) {
            dimTableDatas.fail(e);
        }
        
        return dimTableDatas;
    }
    
    /**根据维表id分页查询维表值*/
    @ApiOperation(value = "根据维表id分页查询维表值")
    @RequestMapping(value = "/queryPageById", method = RequestMethod.POST)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "dimTransId", value = "维表id(必填)",paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "dimKey", value = "维表key(选填)",paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "dimValue", value = "维表值(选填，支持模糊搜索)",paramType = "query", dataType = "string")
    })
    public Page<DimTableData> queryPageById(@ModelAttribute Page<DimTableData> page,
            String dimTransId, String dimKey, String dimValue){
        Page<DimTableData> dimTableDatas = new Page<>();
        //组装参数
        DimTableInfo dimTableInfo = dimTableInfoDao.get(dimTransId);
        String dimTableName = dimTableInfo.getDimTableName();
        if (StringUtil.isBlank(dimTableName)) {
            throw new RuntimeException("找不到对应的维表名");
        }
        DimTableData dimTableData = new DimTableData(new DimTableDataId(dimTableName));
        if (StringUtil.isNoneBlank(dimKey)) {
        	dimTableData.setDimKey(dimKey);
        }
        if (StringUtil.isNoneBlank(dimValue)) {
            dimTableData.setDimValue(dimValue);
        }
        try {
            dimTableDatas = iDimTableDataService.selectDimTableDataPageList(page, dimTableData);
        } catch (BaseException e) {
            dimTableDatas.fail(e);
        }
        return dimTableDatas;
    }
    
    
    
    @ApiOperation(value = "维表信息跑数")
    @RequestMapping(value = "/dimtableinfo2data", method = RequestMethod.POST)
    @ApiImplicitParam(name = "tableName", value = "表名称(可变参数，可选，不传是全量跑)",paramType = "query", dataType = "string")
    public WebResult<String> execute(String ...tableName) {
    	WebResult<String> webResult = new WebResult<>();
    	if(!dimTableDataIfRunning){
    		dimTableDataIfRunning = true;
            try {
                iDimTableDataService.dimTableInfo2Data(tableName);
            } catch (BaseException e) {
                return webResult.fail(e);
            }
            dimTableDataIfRunning = false;
    	}else{
    		return webResult.success("维表信息同步正在执行", SUCCESS);
    	}
        
        return webResult.success("维表信息跑数成功", SUCCESS);
    }

}
