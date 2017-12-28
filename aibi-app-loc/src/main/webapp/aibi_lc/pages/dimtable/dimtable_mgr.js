window.loc_onload = function() {
	$('#form_search').keyup(function(event){
    	if(event.keyCode == 13){
    		$("#btn_search").click();
    	}
    })
	$("#btn_search").click(function() {
		var txtValue = $("#txt_name").val();
		if (txtValue == null) {
			$("#mainGrid").setGridParam({
				postData : {
					data : null
				}
			}).trigger("reloadGrid", [ {
				page : 1
			} ]);
		} else {
			$("#mainGrid").setGridParam({
				postData : {
					"dimTableName" : txtValue
				}
			}).trigger("reloadGrid", [ {
				page : 1
			} ]);
		}
	})
	$("#btn_to_add").click(function(){
    	var wd = $.window('新增维表', $.ctx
			+ '/aibi_lc/pages/dimtable/dimtable_add.html', 700, 400);
    	wd.reload = function() {
			$("#mainGrid").setGridParam({
				postData : $("#formSearch").formToJson()
			}).trigger("reloadGrid", [ {
				page : 1
			} ]);
    	}
    })
	$("#mainGrid")
			.jqGrid(
					{
						url : $.ctx + "/api/dimtable/dimTableInfo/queryPage",
						datatype : "json",
						colNames : [ '序号', '维表表名', '操作' ],
						colModel : [
								{
									name : 'dataName',
									index : 'dataName',
									width : 50,
									sortable : true,
									frozen : true,
									align : "right"
								},
								{
									name : 'dimTableName',
									index : 'dimTableName',
									width : 80,
									sortable : true,
									frozen : true,
									align : "center",
									formatter : function(value, opts, data) {
										return "<a href='###' onclick='fun_to_detail(\""
												+ data.dimId
												+ "\")' ><font class='detail-text'>"
												+ data.dimTableName
												+ "</font></a>";
									}

								},
								{
									name : 'dimId',
									index : 'dimId',
									width : 80,
									sortable : false,
									align : "center",
									formatter : function(v) {
										return "<button type='button' class='btn btn-default  ui-table-btn ui-table-btn' onclick='fun_to_edit(\""
												+ v
												+ "\")'>修改</button> "
												+ "<button type='button' class='btn btn-default ui-table-btn' onclick='del( \""
												+ v + "\" )'>删除</button>"
									}
								} ],
								rowList: [10, 20, 30],
						        pager: '#mainGridPager',
						        viewrecords: true,
					});
}
function del(dimId) {
	$.confirm('确认删除此维表吗', function() {
		$.commAjax({
			url : $.ctx + '/api/dimtable/dimTableInfo/delete',
			postData : {
				dimId : dimId
			},
			onSuccess : function(dimId) {
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
	var wd = $.window('维表详情', $.ctx
			+ '/aibi_lc/pages/dimtable/dimtable_detail.html?dimId=' + id, 500,
			300);
	wd.reload = function() {
		$("#mainGrid").setGridParam({
			postData : $("#formSearch").formToJson()
		}).trigger("reloadGrid", [ {
			page : 1
		} ]);
	}
}
function fun_to_edit(dimId){
	var wd = $.window('编辑专区', $.ctx
			+ '/aibi_lc/pages/dimtable/dimtable_add.html?dimId=' + dimId, 700, 400);
	wd.reload = function() {
		$("#mainGrid").setGridParam({
			postData : $("#formSearch").formToJson()
		}).trigger("reloadGrid", [ {
			page : 1
		} ]);
	}
}