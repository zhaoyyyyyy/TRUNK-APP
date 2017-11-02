window.jauth_onload = function() {
	var parentExeId = '';
	$.commAjax({
		url : $.ctx + '/api/schedule/tree',
		isShowMask : false,
		type : 'POST',
		async : false,
		onSuccess : function(data) {
			$("li.root").append(data);
			mySimpleTree = $('#tree').simpleTree(
					{
						autoclose : false,
						afterClick : function(thi) {
							parentExeId = $(thi).attr('id'), $("#mainGrid")
									.setGridParam({
										postData : {
											parentExeId : parentExeId
										}
									}).trigger("reloadGrid", [ {
										page : 1
									} ]);
						}
					})
		}
	});

	var urlShow = $.ctx + '/api/schedule/taskExeInfo/query';
	var colNames = [ '名称', '参数', 'QUARZT表达式', '任务状态', '执行类型', '操作' ];
	var colModel = [
			{
				name : 'taskExeName',
				index : 'taskExeName',
				width : 20,
				align : 'left'
			},
			{
				name : 'sysId',
				index : 'sysId',
				width : 15,
				align : 'left'
			},
			{
				name : 'taskExeTime',
				index : 'taskExeTime',
				width : 20,
				align : 'left'
			},
			{
				name : 'exeStatus',
				index : 'exeStatus',
				width : 12,
				align : 'center',
				formatter : function(value, opts, data) {
					var action;
					if (data.exeStatus == 1) {
						action = '停止';
						return "<a href='###' onclick='fun_to_status(\""
								+ data.taskExeId + "\",\"" + data.status
								+ "\",\"" + data.taskExeName + "\",\"" + action
								+ "\")' ><font color='green'>"
								+ $.getCodeDesc('RWZT', data.exeStatus)
								+ "</font></a>";
					} else if (data.exeStatus == 0) {
						return $.getCodeDesc('RWZT', data.exeStatus);
					} else {
						action = '启动';
						return "<a href='###' onclick='fun_to_status(\""
								+ data.taskExeId + "\",\"" + data.status
								+ "\",\"" + data.taskExeName + "\",\"" + action
								+ "\")' ><font color='red'>"
								+ $.getCodeDesc('RWZT', data.exeStatus)
								+ "</font></a>";
					}
				}
			},
			{
				name : 'exeType',
				index : 'exeType',
				width : 20,
				align : 'center',
				formatter : function(value, opts, data) {
					return $.getCodeDesc('ZXLX', data.exeType);
				}
			},
			{
				name : 'taskExeId',
				index : 'taskExeId',
				width : 40,
				align : 'left',
				formatter : function(value, opts, data) {
					var html = '';
					if (data.exeStatus == 0) {
						return "<a onclick='fun_to_update(\"" + data.taskExeId
								+ "\")' class='s_edit'>修改</a>"
								+ "<a onclick='fun_to_delete(\""
								+ data.taskExeId
								+ "\")' class='s_delete'>删除</a>";
					}
					if (data.exeStatus == 2) {
						html += "<a onclick='fun_to_update(\"" + data.taskExeId
								+ "\")' class='s_edit'>修改</a>";
					}
					html += "<a onclick='fun_to_up(\"" + data.taskExeId
							+ "\")' class='s_edit'>手动吊起</a>"
							+ "<a onclick='fun_to_detail(\"" + data.taskExeId
							+ "\")' class='s_edit'>调用明细</a>"
					if (data.exeStatus == 2) {
						html += "<a onclick='fun_to_delete(\"" + data.taskExeId
								+ "\")' class='s_delete'>删除</a>";
					}
					return html;
				}
			} ];
	$("#mainGrid").jqGrid({
		url : urlShow,
		colNames : colNames,
		colModel : colModel,
		rownumbers : true,
		autowidth : true,
		viewrecords : true,
		pager : '#mainGridPager'
	});
	// 新建
	$('#btn_add').click(
			function() {
				var dg = $.dialog('新增调度', $.ctx
						+ '/admin/pages/taskinfo/task_add.html?parentExeId='
						+ parentExeId, 800, 500);
				dg.reload = function() {
					$("#mainGrid").setGridParam({
						postData : $("#formSearch").formToJson()
					}).trigger("reloadGrid", [ {
						page : 1
					} ]);
				}
			});
	// 查询
	$("#btnSearch").click(function() {
		$("#mainGrid").setGridParam({
			postData : $("#formSearch").formToJson()
		}).trigger("reloadGrid", [ {
			page : 1
		} ]);
	});

}

function fun_to_status(id, status, name, action) {
	$.confirm('你确认  ' + action + '  任务:[  ' + name + '  ]吗？', function() {
		$.commAjax({
			url : $.ctx + '/api/schedule/taskExeInfo/updateStatus',
			postData : {
				taskExeId : id,
			},
			onSuccess : function(data) {
				$.success('修改任务状态成功。', function() {
					$("#mainGrid").setGridParam({
						postData : $("#formSearch").formToJson()
					}).trigger("reloadGrid", [ {
						page : 1
					} ]);
				});
			}
		});
	});
}
function fun_to_update(id) {
	var dg = $.dialog('修改调度', $.ctx
			+ '/admin/pages/taskinfo/task_add.html?taskExeId=' + id, 800, 500);
	dg.reload = function() {
		$("#mainGrid").setGridParam({
			postData : $("#formSearch").formToJson()
		}).trigger("reloadGrid", [ {
			page : 1
		} ]);
	}
}
function fun_to_up(id) {
	$.commAjax({
		url : $.ctx + '/api/schedule/taskExeInfo/start',
		postData : {
			"taskExeId" : id
		},
		onSuccess : function(data) {
			$.alert("吊起成功");
		}
	})
}
function fun_to_detail(id) {
	var dg = $.dialog('调用明细',
			$.ctx + '/admin/pages/taskinfo/task_detail.html?taskExeId='+id, 800, 500);
	dg.reload = function() {
		$("#mainGrid").setGridParam({
			postData : $("#formSearch").formToJson()
		}).trigger("reloadGrid", [ {
			page : 1
		} ]);
	}
}
function fun_to_delete(id) {
	$.confirm('该操作会删除所有该节点下的子调度，您确定要继续吗？', function() {
		$.commAjax({
			url : $.ctx + '/api/schedule/taskExeInfo/delete',
			postData : {
				"taskExeId" : id
			},
			onSuccess : function(data) {
				$.success('删除成功。', function() {
					$("#mainGrid").setGridParam({
						postData : $("#formSearch").formToJson()
					}).trigger("reloadGrid", [ {
						page : 1
					} ]);
				});
			}
		});
	})
}