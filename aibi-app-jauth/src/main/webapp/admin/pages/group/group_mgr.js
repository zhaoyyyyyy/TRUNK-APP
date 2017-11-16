window.jauth_onload = function() {
			// 列表
			var urlShow = $.ctx + '/api/group/groupPage/query';
			var colNames = ['数据权限名称', '创建人', '创建时间', '数据权限描述', '操作'];
			var colModel = [{
						name : 'groupName',
						index : 'groupName',
						width : 20,
						align : 'left'
					}, {
						name : 'createUserId',
						index : 'createUserId',
						width : 20,
						align : 'center'
					}, {
						name : 'createTime',
						index : 'createTime',
						width : 40,
						align : 'center',
						formatter : function(cellvalue) {  
							 return cellvalue.substr(0,19);
						}
					}, {
						name : 'groupDesc',
						index : 'groupDesc',
						width : 40,
						align : 'left'
					}, {
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

			// 添加按钮
			$('#btn_add').click(function() {
				var dg = $.dialog('数据权限新增', $.ctx
								+ '/admin/pages/group/group_add.html', 600, 500);
				dg.reload = function() {
					$("#mainGrid").setGridParam({
								postData : $("#formSearch").formToJson()
							}).trigger("reloadGrid", [{
										page : 1
									}]);
				}
			});

		}
// 删除按钮
function fun_del(id) {
	$.confirm('您确定要继续删除么？', function() {
				$.commAjax({
							url : $.ctx + '/api/group/delete',
							postData : {
								id : id
							},
							onSuccess : function(data) {
								$.success('删除成功。', function() {
											$("#mainGrid").setGridParam({
												postData : $("#formSearch")
														.formToJson()
											}).trigger("reloadGrid", [{
																page : 1
															}]);
										});

							}
						});
			});
}
function fun_to_detail(id) {
	var dg = $.dialog('数据权限编辑', $.ctx + '/admin/pages/group/group_add.html?id='
					+ id, 600, 500);
	dg.reload = function() {
		$("#mainGrid").setGridParam({
					postData : $("#formSearch").formToJson()
				}).trigger("reloadGrid", [{
							page : 1
						}]);
	}
}