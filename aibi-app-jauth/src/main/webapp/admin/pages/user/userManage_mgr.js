window.jauth_onload = function(){
	// 列表
	var urlShow = $.ctx + '/api/user/userPage/query';
	
	var colNames = ['用户名称', '姓名', '性别', 
//	                '所在组织',
	                '角色', 
//	                '数据范围',
	                '状态',
	                '更新时间', '操作'];
	var colModel = [{
		name : 'userName',
		index : 'userName',
		width : 20,
		align : 'center',
		formatter : function(value, opts, data) {
			return "<a href='###' onclick='fun_to_information(\"" + data.id
			+ "\")' ><font color='blue'>" + data.userName
			+ "</font></a>";
		}
	}, {
		name : 'realName',
		index : 'realName',
		width : 12,
		align : 'center'
	}, {
		name : 'sex',
		index : 'sex',
		width : 10,
		align : 'center',
		formatter : function(value, opts, data) {
			return  $.getCodeDesc('SEX', data.sex) ;
		}
	}, 
//	{
//		name : 'orgNames',
//		index : 'orgNames',
//		width : 20,
//		align : 'center'
//			
//	},
	{
		name : 'roleNames',
		index : 'roleNames',
		width : 20,
		align : 'center'
	},
//	{
//		name : 'groupNames',
//		index : 'groupNames',
//		width : 15,
//		align : 'center'
//	},
	{
		name : 'status',
		index : 'status',
		width : 10,
		align : 'center',
		formatter : function(value, opts, data) {
			if (data.status == 1) {
				return "<a href='###' onclick='fun_to_status(\"" + data.id
				+ "\",\"" + data.status + "\")' ><font color='green'>"
				+ $.getCodeDesc('YHZT', data.status) + "</font></a>";
			} else {
				return "<a href='###' onclick='fun_to_status(\"" + data.id
				+ "\",\"" + data.status + "\")' ><font color='red'>"
				+ $.getCodeDesc('YHZT', data.status) + "</font></a>";
			}
		}
	}, 
	{
		name : 'createTime',
		index : 'createTime',
		width : 25,
		align : 'center',
		formatter : function(cellvalue) {  
			 return cellvalue.substr(0,19);
		}
	}, {
		name : 'id',
		index : 'id',
		width : 260,
		fixed : true,
		align : 'center',
		formatter : function(value, opts, data) {
			return "<a onclick='fun_to_detail(\"" + data.id
			+ "\")' class='s_edit' >编辑</a>" 
			+ "<a onclick='fun_to_role(\"" + data.id
			+ "\")' class='s_ls' >分配角色</a>"
			+ "<a onclick='fun_to_group(\"" + data.id
			+ "\")' class='s_ls' >分配数据范围</a>"
		}
	}];
	$("#mainGrid").jqGrid({
		url : urlShow,
		colNames : colNames,
		colModel : colModel,
		rownumbers : true,
		multiselect : true,
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
	//新建用户
	$('#btn_batch_new').click(function(){
		var dg = $.dialog('新建用户', $.ctx + '/admin/pages/user/userNew_add.html',
				600, 500);
		dg.reload = function() {
			$("#mainGrid").setGridParam({
				postData : $("#formSearch").formToJson()
			}).trigger("reloadGrid", [{
				page : 1
			}]);
		}
	});
	
	// 批量分配角色
	$('#btn_batch_role').click(function() {
		var id = $('#mainGrid').jqGrid('getGridParam', 'selarrrow');
		if (id.length < 1) {
			$.alert("请选择用户");
			return;
		}
		var dg = $.dialog('分配角色', $.ctx + '/admin/pages/user/userManage_role.html?id=' + id,
				600, 500);
		dg.reload = function() {
			$("#mainGrid").setGridParam({
				postData : $("#formSearch").formToJson()
			}).trigger("reloadGrid", [{
				page : 1
			}]);
		}
	});
	// 批量分配数据范围
	$('#btn_batch_group').click(function() {
		var id = $('#mainGrid').jqGrid('getGridParam', 'selarrrow');
		if (id.length < 1) {
			$.alert("请选择用户");
			return;
		}
		var dg = $.dialog('分配数据范围', $.ctx+ '/admin/pages/user/userManage_group.html?id=' + id,
				600, 500);
		dg.reload = function() {
			$("#mainGrid").setGridParam({
				postData : $("#formSearch").formToJson()
			}).trigger("reloadGrid", [{
				page : 1
			}]);
		}
	})
	// 批量删除用户
	$('#btn_batch_delete').click(function() {
		var id = $('#mainGrid').jqGrid('getGridParam', 'selarrrow');
		if (id.length < 1) {
			$.alert("请选择用户");
			return;
		}
		$.confirm('您确定要继续删除么？', function() {
			$.commAjax({
				url : $.ctx + '/api/user/delete?id=' + id,
				onSuccess : function(data) {
					$.success('删除成功。', function() {
						$("#mainGrid").setGridParam().trigger(
								"reloadGrid", [{
									page : 1
								}]);
					});
				}
			});
		});
	});
	
	// 导出数据
	$('#btn_export_data').click(function() {
		$('#formSearch').submit();
	})
	
	
}
/**
 * 删除用户
 * 
 * @param {}
 * id
 */
function fun_del(id) {
	$.confirm('您确定要继续删除么？', function() {
		$.commAjax({
				url : $.ctx + '/api/user/delete',
				postData : {
					id : id
				},
				onSuccess : function(data) {
					$.success('删除成功。', function() {
						$("#mainGrid").setGridParam({
							postData : $("#formSearch").formToJson()
						}).trigger("reloadGrid", [{
							page : 1
						}]);
					});
				}
			});
	});
}
/**
 * 编辑用户
 * 
 * @param {}
 *            id
 */
function fun_to_detail(id) {
	var dg = $.dialog('用户编辑', $.ctx + '/admin/pages/user/userManage_edit.html?id=' + id, 850, 500);
	dg.reload = function() {
		$("#mainGrid").setGridParam({
			postData : $("#formSearch").formToJson()
		}).trigger("reloadGrid", [{
			page : 1
		}]);
	}
}
/**
 * 变更用户状态
 */
function fun_to_status(id, status) {
	$.commAjax({
				url : $.ctx + '/api/user/changeStatus',
				postData : {
					id : id,
					status : status
				},
				onSuccess : function(data) {
					$.success('修改用户状态成功。', function() {
						$("#mainGrid").setGridParam({
							postData : $("#formSearch").formToJson()
						}).trigger("reloadGrid", [{
									page : 1
						}]);
					});
				}
			});
}
/**
 * 分配角色
 * 
 * @param {}
 *            id
 */
function fun_to_role(id) {
	var dg = $.dialog('分配角色', $.ctx+ '/admin/pages/user/userManage_role.html?id=' + id, 600,500);
	dg.reload = function() {
		$("#mainGrid").setGridParam({
			postData : $("#formSearch").formToJson()
		}).trigger("reloadGrid", [{
			page : 1
		}]);
	}
}
/**
 * 分配数据范围
 * 
 * @param {}
 *            id
 */
function fun_to_group(id) {
	var dg = $.dialog('分配数据范围', $.ctx + '/admin/pages/user/userManage_group.html?id=' + id, 600,500);
	dg.reload = function() {
		$("#mainGrid").setGridParam({
			postData : $("#formSearch").formToJson()
		}).trigger("reloadGrid", [{
			page : 1
		}]);
	}
}
/**
 * 简历预览
 */
function fun_to_information(id) {
	var dg = $.dialog('用户编辑', $.ctx + '/admin/pages/user/userManage_edit.html?id=' + id, 850, 500);
	dg.reload = function() {
		$("#mainGrid").setGridParam({
			postData : $("#formSearch").formToJson()
		}).trigger("reloadGrid", [{
			page : 1
		}]);
	}
}