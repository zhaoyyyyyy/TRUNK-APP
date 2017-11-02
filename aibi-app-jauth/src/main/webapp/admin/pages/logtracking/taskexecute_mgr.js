var model = {
	taskExeName : '请从左侧树中选择节点',
	taskExeTime : '',
	sysId : ''
}
window.jauth_onload = function() {
	new Vue({
		el : '#saveDataDiv',
		data : model
	})
	$.commAjax({
		url : $.ctx + '/api/schedule/tree',
		isShowMask : false,
		type : 'POST',
		async : false,
		onSuccess : function(data) {
			$("li.root").append(data);
			mySimpleTree = $('#tree').simpleTree({
				autoclose : false,
				afterClick : function(thi) {
					$.commAjax({
						url : $.ctx + '/api/schedule/taskExeInfo/get',
						isShowMask : false,
						type : 'POST',
						async : false,
						postData : {
							exeId : $(thi).attr('id')
						},
						onSuccess:function(data){
							model.taskExeName = data.locTaskExeInfo.taskExeName,
							model.taskExeTime = data.locTaskExeInfo.taskExeTime,
							model.sysId = data.locTaskExeInfo.sysId
						}
					})
				}
			})
		}
	});
	// var urlShow = $.ctx + '';
	// var colNames = [ ];
	// var colModel = [ ];
	// $("#mainGrid").jqGrid({
	// url : urlShow,
	// colNames : colNames,
	// colModel : colModel,
	// rownumbers : true,
	// autowidth : true,
	// viewrecords : true,
	// pager : '#mainGridPager'
	// });
}