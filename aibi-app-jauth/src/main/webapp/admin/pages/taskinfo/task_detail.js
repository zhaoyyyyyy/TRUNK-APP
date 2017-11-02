window.jauth_onload = function() {
	var taskExeId = $.getUrlParam("taskExeId");
	$.commAjax({
		url : $.ctx + '/api/schedule/taskExeInfo/get',
		isShowMask : false,
		type : 'POST',
		async : false,
		postData : {
			exeId : taskExeId
		},
		onSuccess:function(data){
			new Vue({
				el:'#saveDataDiv',
				data : {
					taskExeName : data.locTaskExeInfo.taskExeName,
					taskExeTime : data.locTaskExeInfo.taskExeTime,
					sysId : data.locTaskExeInfo.sysId
				}
			})
		}
	})
//	var urlShow = $.ctx + '';
//	var colNames = [ ];
//	var colModel = [ ];
//	$("#mainGrid").jqGrid({
//		url : urlShow,
//		colNames : colNames,
//		colModel : colModel,
//		rownumbers : true,
//		autowidth : true,
//		viewrecords : true,
//		pager : '#mainGridPager'
//	});
}