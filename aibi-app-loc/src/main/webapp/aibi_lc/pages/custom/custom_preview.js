window.loc_onload = function() {
	 labelId = $.getUrlParam("labelId");
	 $("#mainGrid").jqGrid({
			url : $.ctx + "/api/syspush/labelPushCycle/findGroupList",
			datatype : "json",
			postData :{"labelId": labelId},
			colNames : [ '手机号', ],
			colModel : [
				{
					name : 'productNo',
					index : 'productNo',
					width : 80,
					sortable : false,
					frozen : true,
					align : "center",
				},
				],
		});
		$("#mainGrid").jqGrid('setLabel',0, '序号');
	var wd = frameElement.lhgDG;
	wd.addBtn("cancel", "确定", function() {
		wd.cancel();
	});
}