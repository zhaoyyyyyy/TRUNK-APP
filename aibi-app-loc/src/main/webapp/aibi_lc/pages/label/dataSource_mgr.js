/**
 * Created by j on 2017/12/6.
 */
window.loc_onload = function() {

	$('#formSearch').keyup(function(event) {
		if (event.keyCode == 13) {
			$("#btn_search").click();
		}
	})

	$("#btn_search").click(function() {
		$("#jsonmap1").setGridParam({
			postData: $('#formSearch').formToJson()
		}).trigger("reloadGrid", [{
			page: 1
		}]);
	})

	$("#jsonmap1").jqGrid({
		url: $.ctx + "/api/source/sourceTableInfo/queryPage",
		datatype: "json",
		colNames: ['数据源表名称', '创建时间', '数据源表类型', '操作'],
		colModel: [{
			name: 'sourceTableName',
			index: 'sourceTableName',
			width: 60,
			sortable: false,
			frozen: true,
			align: "center"
		},
		// frozen:true固定列
		{
			name: 'createTime',
			index: 'createTime',
			sortable: false,
			width: 60,
			align: "center"
		},
		{
			name: 'sourceTableType',
			index: 'sourceTableType',
			width: 60,
			sortable: false,
			align: "center",
			formatter: function(v) {
                return $.getCodeDesc('SJYBLX', v)
            }
		},
		{
			name: 'sourceTableId',
			index: 'sourceTableId',
			width: 40,
			sortable: false,
			align: "left",
			key:true,
			formatter: function(v) {
				var html = '<button onclick="fun_to_up(\'' + v + '\')" type="button" class="btn btn-default ui-table-btn">修改</button>' + '<button onclick="fun_to_del(\'' + v + '\')" type="button" class="btn btn-default  ui-table-btn ui-table-btn">删除</button>';
				return html;
			}
		}],
		rowList: [10, 20, 30],
		autowidth: true,
		pager: '#pjmap1',
		// 分页的id
		sortname: '',
		// 排序的字段名称
		// 不需要的话可置为空,取值取自colModel中的index字段
		viewrecords: true,
		multiselect: true,
		rownumbers: true,
		// 是否展示行号
		sortorder: "desc",
		// 排序方式
		jsonReader: {
			repeatitems: false,
			id: "0"
		},
		height: '100%'
	});
}
function fun_to_up(id) {
	window.location = 'dataSource_add.html?isEdit=1&sourceTableId=' + id;
}
function fun_to_del(id) {
	var msg = "";
	$.commAjax({
		url : $.ctx + '/api/source/sourceInfo/queryList',
		postData : {
			"sourceTableId" : id
		},
		onSuccess : function(data) {
			if (data.data.length != 0) {
				msg = "删除该数据源表会同时删除该数据源的指标信息列，确定删除吗？";
			} else {
				msg = "确定删除该数据源表吗？";
			}
			$.confirm(msg, function() {
				$.commAjax({
					url : $.ctx + '/api/source/sourceTableInfo/delete',
					postData : {
						"sourceTableId" : id
					},
					onSuccess : function(data1) {
						if (data1.data == "success") {
							$.success("删除成功", function() {
								$("#jsonmap1").setGridParam({
									postData : $("#formSearch").formToJson()
								}).trigger("reloadGrid", [ {
									page : 1
								} ]);
							})
						}
					}
				})
			})
		}
	})
}
function fun_to_delAll() {
	var ids = $('#jsonmap1').jqGrid('getGridParam', 'selarrrow');
	if (!ids.length > 0) {
		$.alert("请选择要删除的数据源表");
		return;
	}
	var sourceTableIds = ids.join(",");
	$.confirm("您确定要继续删除吗？该操作会同时删除数据源表下的指标信息列", function() {
		$.commAjax({
			url : $.ctx + '/api/source/sourceTableInfo/delete',
			postData : {
				"sourceTableId" : sourceTableIds
			},
			onSuccess : function(data) {
				if (data.data == "success") {
					$.success("删除成功", function() {
						$("#jsonmap1").setGridParam({
							postData : $("#formSearch").formToJson()
						}).trigger("reloadGrid", [ {
							page : 1
						} ]);
					})
				}
			}
		});
	});
}