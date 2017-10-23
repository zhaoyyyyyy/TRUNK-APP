window.jauth_onload = function() {
	/**
	 * 列表
	 */
	var dicCode = $.getUrlParam("dicCode");
	var urlShow = $.ctx + '/api/datadic/dicdataPage/query?dicCode='+dicCode;
	var colNames = [ '编码', '名称', '备注', '操作' ];
	var colModel = [
			{
				name : 'code',
				index : 'code',
				width : 30,
				align : 'left'
			},
			{
				name : 'dataName',
				index : 'dataName',
				width : 30,
				align : 'left'
			},
			{
				name : 'note',
				index : 'note',
				width : 40,
				align : 'left'
			},
			{
				name : 'id',
				index : 'id',
				width : 120,
				fixed : true,
				formatter : function(value, opts, data) {
					// 用编号查看，用ID删除
					return "<a onclick='fun_detail(\"" + data.id
							+ "\")' class='s_ls' >编辑</a>"
							+ "<a onclick='fun_del(\"" + data.id
							+ "\")' class='s_delete' >删除</a>";
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
	$('#btn_add').click(function() {
		var dg = $.dialog('数据字典内容新增',$.ctx+'/admin/pages/datadic/datadic_detail.html?dicCode='+dicCode, 600, 300);
		dg.reload = function() {
			$("#mainGrid").setGridParam({
				postData : $("#formSearch").formToJson() 
			}).trigger("reloadGrid", [ {
				page : 1 
			} ]);
		}
	});

}

/**
 * 详情
 */
function fun_detail(id) {
	var dg = $.dialog('数据字典内容编辑', $.ctx
			+ '/admin/pages/datadic/datadic_detail.html?id=' + id
			+ '&dicCode=' + $('#dicCode').val(), 600, 300);
	dg.reload = function() {
		$("#mainGrid").setGridParam({
			postData : $("#formSearch").formToJson()
		}).trigger("reloadGrid", [ {
			page : 1
		} ]);
	}
}

/**
 * 删除
 */
function fun_del(id) {
	$.confirm('您确定要继续删除么？', function() {
		$.commAjax({
			url : $.ctx + '/api/datadic/delete',
			postData : {
				id : id
			},
			onSuccess : function() {
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