window.jauth_onload = function() {
	// 列表
	var urlShow = $.ctx + '/api/role/rolePage/query';
	var colNames = [ '角色名称', '创建人', '创建时间', '角色描述', '操作' ];
	var colModel = [
			{
				name : 'roleName',
				index : 'roleName',
				width : 20,
				align : 'left'
			},
			{
				name : 'createUserId',
				index : 'createUserId',
				width : 20,
				align : 'left'
			},
			{
				name : 'createTime',
				index : 'createTime',
				width : 40,
				align : 'center',
				formatter : function(cellvalue) {
					return cellvalue.substr(0, 19);
				}
			},
			{
				name : 'roleDesc',
				index : 'roleDesc',
				width : 40,
				align : 'left'
			},
			{
				name : 'id',
				index : 'id',
				width : 120,
				fixed : true,
				formatter : function(value, opts, data) {
					return "<a onclick='fun_to_detail(\"" + data.id
							+ "\")' class='s_edit' >编辑</a>"
							+ "<a onclick='fun_del(\"" + data.id
							+ "\")' class='s_delete' >删除</a>"
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

	// 查询按钮
	$("#btnSearch").click(function() {
		$("#mainGrid").setGridParam({
			postData : $("#formSearch").formToJson()
		}).trigger("reloadGrid", [ {
			page : 1
		} ]);
	});
	// 添加按钮
	$('#btn_add').click(
			function() {
				var dg = $.dialog('角色新增', $.ctx
						+ '/admin/pages/role/role_add.html', 800, 500);
				dg.reload = function() {
					$("#mainGrid").setGridParam({
						postData : $("#formSearch").formToJson()
					}).trigger("reloadGrid", [ {
						page : 1
					} ]);
				}
			});

}
function fun_del(id) {
	$.confirm('您确定要继续删除么？', function() {
		$.commAjax({
			url : $.ctx + '/api/role/delete',
			postData : {
				"id" : id
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
	});
}
function fun_to_detail(id) {
	var dg = $.dialog('角色编辑', $.ctx + '/admin/pages/role/role_add.html?id='
			+ id, 800, 500);
	dg.reload = function() {
		$("#mainGrid").setGridParam({
			postData : $("#formSearch").formToJson()
		}).trigger("reloadGrid", [ {
			page : 1
		} ]);
	}
}