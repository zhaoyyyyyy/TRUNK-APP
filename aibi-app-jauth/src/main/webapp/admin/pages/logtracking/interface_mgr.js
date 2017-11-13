window.jauth_onload = function(){
	// 列表
	var urlShow = $.ctx + '/api/interface/interfacePage/query';
	
	var colNames = ['时间', '用户名', 'IP地址', 
	                '接口名称',
	                '接口路径', 
	                '参数',
	                '输出CODE/MESSAGE'];
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
		name : 'ipAddr',
		index : 'ipAddr',
		width : 20,
		align : 'center'
	}, {
		name : 'interfaceName',
		index : 'interfaceName',
		width : 20,
		align : 'center'
	}, {
		name : 'interfaceUrl',
		index : 'interfaceUrl',
		width : 30,
		align : 'center'
	},{
		name : 'inputParams',
		index : 'inputParams',
		width : 30,
		align : 'center'
	}, {
		name : 'outputParams',
		index : 'outputParams',
		width : 200,
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
