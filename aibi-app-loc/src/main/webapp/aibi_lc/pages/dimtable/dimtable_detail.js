window.loc_onload = function() {
	var dimTableName = $.getUrlParam("dimTableName");
	$("#mainGrid").jqGrid({
		url : $.ctx + "/api/dimtabledata/queryPage",
		datatype : "json",
		postData :{"dimTableName": dimTableName},
		colNames : ['编号','名称' ],
		colModel : [
				{
					name : 'dimKey',
					index : 'dimKey',
					width : 50,
					sortable : true,
					frozen : true,
					align : "center"
				},
				{
					name : 'dimValue',
					index : 'dimValue',
					width : 50,
					sortable : true,
					align : "center"
				} ],
		rowList: [10, 20, 30],
		pager: '#mainGridPager',
		viewrecords: true,
	});
	
	var wd = frameElement.lhgDG;
	wd.addBtn("cancel", "确定", function() {
		wd.cancel();
	});
}