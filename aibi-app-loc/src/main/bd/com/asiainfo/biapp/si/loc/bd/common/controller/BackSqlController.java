
package com.asiainfo.biapp.si.loc.bd.common.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.loc.base.exception.SqlRunException;
import com.asiainfo.biapp.si.loc.base.utils.WebResult;
import com.asiainfo.biapp.si.loc.bd.common.service.IBackSqlService;
import com.asiainfo.biapp.si.loc.core.label.entity.LabelStatus;

@Api(value = "后台数据库查询接口(测试)",description = "直接对数据库进行操作")
@RequestMapping("backSql")
@RestController
public class BackSqlController {
	
		@Autowired
		private IBackSqlService backSqlService;

	    @ApiOperation(value="通过表名拿到库中的表", notes="@param datasource 数据源编码(配置文件中")
	    @ApiImplicitParam(name = "tableName", value = "表名称（支持模糊查询）",paramType = "query", dataType = "string")
	    @PostMapping(value = "/tables")
	    public WebResult<List<Map<String, String>>> queryTable(String tableName){
            WebResult<List<Map<String,String>>> webResult = new WebResult<>();
            List<Map<String, String>> list = null;
            try {
                list = backSqlService.queryTableLikeTableName(tableName);
            } catch (SqlRunException e) {
                return webResult.fail(e);
            }
            return webResult.success("获取列成功", list);
	    }
	    
	    @ApiOperation(value="通过表名拿到表中的列", notes="@param datasource 数据源编码(配置文件中")
        @ApiImplicitParam(name = "tableName", value = "表名称",paramType = "query", dataType = "string")
        @PostMapping(value = "/columns")
        public WebResult<List<Map<String,String>>> queryColumn(String tableName){
	        WebResult<List<Map<String,String>>> webResult = new WebResult<>();
	        List<Map<String, String>> list = null;
            try {
                list = backSqlService.queryTableColumn(tableName);
            } catch (SqlRunException e) {
//                int num = e.getMessage().indexOf(":");.substring(num+1).split(";")[0]
                return webResult.fail(e.getMessage());
            }
            return webResult.success("获取列成功", list);
        }



}
