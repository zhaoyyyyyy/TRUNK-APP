window.jauth_onload = function() {
	// 列表
	var urlShow = $.ctx + '/api/resource/resourcePage/query';
	var colNames = ['资源名称', '资源编码', '资源值',  '操作'];
	var colModel = [{
				name : 'resourceName',
				index : 'resourceName',
				width : 20,
				align : 'left'
			}, {
				name : 'resourceCode',
				index : 'resourceCode',
				width : 20,
				align : 'center'
			}, {
				name : 'address',
				index : 'address',
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
		var dg = $.dialog('资源新增', $.ctx
						+ '/admin/pages/resource/resource_add.html', 600, 500);
		dg.reload = function() {
			$("#mainGrid").setGridParam({
						postData : $("#formSearch").formToJson()
					}).trigger("reloadGrid", [{
								page : 1
							}]);
			window.location.reload();
		}
	});


	var proscenium = "1"; // 前台
	var app = "2"; //APP
	var backstage = "3"; // 后台
	var roleId = "";
	var mySimpleTree;
	var myTree;
	var appTree;
	var cascadeParentChecked = false;
	var cascadeChildrenChecked = false;
	
	//前台
	var url1 = $.ctx + "/api/resource/renderOrgTree"
	$.commAjax({
		url : url1,
		isShowMask : true,
		postData:{"resourceId":proscenium},
		type : 'POST',
		async:false,
		onSuccess : function(data) {
			$("#sRoot").append(data);
			mySimpleTree = $('#tree').simpleTree({
				autoclose : false,
				showCheckBox : true,
				cascadeParentChecked : cascadeParentChecked,
				cascadeChildrenChecked : cascadeChildrenChecked,
				afterClick: function(node){
					$('#parentId').val(node.attr('id'));
					$("#mainGrid").setGridParam({
						postData : $("#formSearch").formToJson()
					}).trigger("reloadGrid", [{
								page : 1
					}]);
					
				},
				ignoreIndeterminate : false
			});

		},
		onFailure : function() {
		},
		maskMassage : '数据加载中...'
	});
	//后台
	$.commAjax({
		url : url1,
		isShowMask : true,
		postData:{"resourceId":backstage},
		type : 'POST',
		async:false,
		onSuccess : function(data) {
			$("#simpleRoot").append(data);
			myTree = $('#simpleTree').simpleTree({
				autoclose : false,
				showCheckBox : true,
				cascadeParentChecked : cascadeParentChecked,
				cascadeChildrenChecked : cascadeChildrenChecked,
				afterClick: function(node){
					$('#parentId').val(node.attr('id'));
					$("#mainGrid").setGridParam({
						postData : $("#formSearch").formToJson()
					}).trigger("reloadGrid", [{
								page : 1
					}]);
					
				},
				ignoreIndeterminate : false
			});

		},
		onFailure : function() {
		},
		maskMassage : '数据加载中...'
	});
	
	
	//app 
	$.commAjax({
		url : url1,
		isShowMask : true,
		postData:{"resourceId":app},
		type : 'POST',
		async:false,
		onSuccess : function(data) {
			$("#simpleRootAPP").append(data);
			appTree = $('#appTree').simpleTree({
				autoclose : false,
				showCheckBox : true,
				cascadeParentChecked : cascadeParentChecked,
				cascadeChildrenChecked : cascadeChildrenChecked,
				afterClick: function(node){
					$('#parentId').val(node.attr('id'));
					$("#mainGrid").setGridParam({
						postData : $("#formSearch").formToJson()
					}).trigger("reloadGrid", [{
								page : 1
					}]);
					
				},
				ignoreIndeterminate : false
			});
		
		},
		onFailure : function() {
		},
		maskMassage : '数据加载中...'
	});
}
	
	
	
			
// 删除按钮
function fun_del(id) {
	$.confirm('您确定要继续删除么？', function() {
				$.commAjax({
							url : $.ctx + '/api/resource/delete',
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
											window.location.reload();
										});
							}
						});
			});
}
function fun_to_detail(id) {
	var dg = $.dialog('资源编辑', $.ctx + '/admin/pages/resource/resource_add.html?id=' + id, 600, 500);
	dg.reload = function() {
		$("#mainGrid").setGridParam({
					postData : $("#formSearch").formToJson()
				}).trigger("reloadGrid", [{
							page : 1
						}]);
		window.location.reload();
	}
}