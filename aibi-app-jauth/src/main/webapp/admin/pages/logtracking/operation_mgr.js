window.jauth_onload = function(){
	// 列表
	var urlShow = $.ctx + '/api/logOperation/logoperPage/query';
	
	var colNames = ['时间', '用户名', '操作', 
	                '资源类型',
	                '资源名称', 
	                '信息',
	                'IP地址'];
	var colModel = [{
		name : 'opTime',
		index : 'opTime',
		width : 20,
		align : 'center',
		formatter : function(cellvalue) {  
			 return cellvalue.substr(0,19);
		}
	},{
		name : 'userId',
		index : 'userId',
		width : 10,
		align : 'center',
	}, {
		name : 'sysId',
		index : 'sysId',
		width : 20,
		align : 'center'
	}, {
		name : 'resource.type',
		index : 'resource.type',
		width : 10,
		align : 'center',
		formatter:function(v){return $.getCodeDesc('RESOURCETYPE',v);}
	}, {
		name : 'resource.resourceName',
		index : 'resource.resourceName',
		width : 20,
		align : 'center'
	},{
		name : 'params',
		index : 'params',
		width : 20,
		align : 'center'
	}, {
		name : 'ipAddr',
		index : 'ipAddr',
		width : 160,
		fixed : true,
		align : 'center'
	}];
	$("#mainGrid").jqGrid({
		url : urlShow,
		colNames : colNames,
		colModel : colModel,
		rownumbers : true,
		autowidth : true,
		viewrecords : true,
		pager : '#mainGridPager'
	});
	
	// 查询按钮
	$("#btnSearch").click(function() {
		$("#mainGrid").setGridParam({
			postData : $("#formSearch").formToJson()
		}).trigger("reloadGrid", [{
			page : 1
		}]);
	});
}
