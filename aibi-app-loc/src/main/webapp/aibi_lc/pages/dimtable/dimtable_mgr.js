window.loc_onload = function() {
	var obj = $("#preConfig_list").find("span");
	var configId =obj.attr("configId");
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
		var data =$("#formSearch").formToJson();
		data.configId =configId;
    	var wd = $.window('新增维表', $.ctx
			+ '/aibi_lc/pages/dimtable/dimtable_add.html?configId='+configId, 700, 450);
    	wd.reload = function() {
			$("#mainGrid").setGridParam({
				postData : data
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
						postData :{"configId": configId},
						colNames : [ '维表表名',"维表中文名", '操作' ],
						colModel : [
								{
									name : 'dimTableName',
									index : 'dimTableName',
									width : 80,
									sortable : false,
									frozen : true,
									align : "center",
									formatter : function(value, opts, data) {
										return "<font class='detail-text' >"
												+ data.dimTableName
												+ "</font></a> ";
									}
								},
								{
									name : 'dimComment',
									index : 'dimComment',
									width : 50,
									sortable : false,
									frozen : true,
									align : "center"
								},
								{
									name : 'dimId',
									index : 'dimId',
									width : 80,
									sortable : false,
									align : "center",
									title:false,
									formatter : function(value, opts, data) {
										return "<button type='button' class='btn btn-default  ui-table-btn ui-table-btn' onclick='fun_to_edit(\""
												+ value
												+ "\")'>修改</button> <button type='button' class='btn btn-default ui-table-btn' onclick='fun_to_detail( \""
												+ data.dimTableName + "\" )'>查看</button>"
												+ "<button type='button' class='btn btn-default ui-table-btn' onclick='fun_to_del( \""
												+ value + "\" )'>删除</button>"
									}
								} ],
								rowList: [10, 20, 30],
						        pager: '#mainGridPager',
						        viewrecords: true,
					});
}
function fun_to_del(dimId) {
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
function fun_to_detail(dimTableName) {
	var wd = $.window('维值详情', $.ctx
			+ '/aibi_lc/pages/dimtable/dimtable_detail.html?dimTableName=' + dimTableName, 900,
			600);
	wd.reload = function() {
		$("#mainGrid").setGridParam({
			postData : $("#formSearch").formToJson()
		}).trigger("reloadGrid", [ {
			page : 1
		} ]);
	}
}
function fun_to_edit(dimId){
	var wd = $.window('编辑维表', $.ctx
			+ '/aibi_lc/pages/dimtable/dimtable_add.html?dimId=' + dimId, 700, 450);
	wd.reload = function() {
		$("#mainGrid").setGridParam({
			postData : $("#formSearch").formToJson()
		}).trigger("reloadGrid", [ {
			page : 1
		} ]);
	}
}