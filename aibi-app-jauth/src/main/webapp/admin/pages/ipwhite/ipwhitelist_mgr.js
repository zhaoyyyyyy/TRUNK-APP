window.jauth_onload = function(){
			// 列表
			var urlShow = $.ctx + '/api/ipWhiteList/whitelist/query';
			var colNames = [ 'IP白名单', '请求地址', '操作'];
			var colModel = [ {
						name : 'ipAddress',
						index : 'ipaddress',
						width : 40,
						align : 'left'
					},{
						name : 'requestAddress',
						index : 'requestddress',
						width : 40,
						align : 'left'
					}, {
						name : 'listId',
						index : 'listId',
						width : 120,
						fixed : true,
						formatter : function(value, opts, data) {
							return "<a onclick='fun_to_detail(\"" + data.listId
									+ "\")' class='s_edit' >编辑</a>"
									+ "<a onclick='fun_del(\"" + data.listId
									+ "\")' class='s_delete' >删除</a>"
						}
					}];
			$("#mainGrid").jqGrid({
						url : urlShow,
						colNames : colNames,
						colModel : colModel,
						rownumbers : true,
						autowidth : true,
						viewrecords : true	
			});
			
//			fn.jqGrid  //表格初始化
//			fn.setGridParam  //表格放置参数 
//			fn.trigger('reloadGrid')   //重新加载数据
//			$("#mainGrid")
			
			
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
				var dg = $.dialog('新增白名单', $.ctx + '/admin/pages/ipwhite/ipwhitelist_edit.html', 500, 300);
				dg.reload = function() {
					$("#mainGrid").setGridParam({
								postData : $("#formSearch").formToJson()
							}).trigger("reloadGrid", [{
										page : 1
									}]);
				}
			});

	}
function fun_del(id) {
	$.confirm('您确定要继续删除么？', function() {
				$.commAjax({
							url : $.ctx + '/api/ipWhiteList/whitelist/delete',
							postData : {"whiteListId" : id},
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
	var dg = $.dialog('ip白名单编辑', $.ctx + '/admin/pages/ipwhite/ipwhitelist_edit.html?whiteListId='+id, 500, 300);
	dg.reload = function() {
		$("#mainGrid").setGridParam({
					postData : $("#formSearch").formToJson()
				}).trigger("reloadGrid", [{
							page : 1
						}]);
	}
}