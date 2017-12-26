
package com.asiainfo.biapp.si.loc.bd.common.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asiainfo.biapp.si.loc.bd.common.service.IBackSqlService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(value = "后台数据库查询接口(测试)",description = "直接对数据库进行操作")
@RequestMapping("backSql")
@RestController
public class BackSqlController {
	
		@Autowired
		private IBackSqlService backSqlService;

	    @ApiOperation(value="通过表名拿到库中的表", notes="@param datasource 数据源编码(配置文件中")
	    @ApiImplicitParam(name = "tableName", value = "表名称（支持模糊查询）",paramType = "query", dataType = "string")
	    @PostMapping(value = "/tables")
	    public List<Map<String,String>> queryTable(String tableName){
	        return backSqlService.queryTableLikeTableName(tableName);
	    }



}
