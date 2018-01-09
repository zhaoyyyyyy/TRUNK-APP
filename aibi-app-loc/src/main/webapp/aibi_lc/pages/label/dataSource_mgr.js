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

	var obj = $("#preConfig_list").find("span");
	
	$("#jsonmap1").jqGrid({
		url: $.ctx + "/api/source/sourceTableInfo/queryPage",
		postData: {
			"configId" : obj.attr("configId")
		},
		datatype: "json",
		colNames: ['指标表名称', '创建时间', '指标表类型', '操作'],
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
			align: "center",
			autowidth:false,
			key:true,
			formatter: function(value, opts, data) {
				var html = '<button onclick="fun_to_up(\'' + value + '\')" type="button" class="btn btn-default ui-table-btn">修改</button>';
				$.commAjax({
					url:$.ctx+"/api/source/sourceInfo/queryList",
					async:false,
					postData:{"sourceTableId":value},
					onSuccess:function(data){
						var is = "";
						for(var num = 0 ;num <data.data.length; num++){
							$.commAjax({
								url:$.ctx+"/api/label/labelCountRules/queryList",
								async:false,
								postData:{"dependIndex":data.data[num].sourceId},
								onSuccess:function(data){
									if(data.data.length!=0){
										is="have";
									}
								}
							})
						}
						if(is != "have"){
							html += '<button onclick="fun_to_del(\'' + value + '\')" type="button" class="btn btn-default  ui-table-btn ui-table-btn">删除</button>';
						}
					}
				})
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
	$.confirm("确定删除该指标源表吗？", function() {
		$.commAjax({
			url : $.ctx + '/api/source/sourceTableInfo/delete',
			postData : {
				"sourceTableId" : id
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
		})
	})
}
function fun_to_delAll() {
	var ids = $('#jsonmap1').jqGrid('getGridParam', 'selarrrow');
	var is = "";
	if (!ids.length > 0) {
		$.alert("请选择要删除的指标源表");
		return;
	}
	$.confirm("您确定要继续删除吗？该操作会同时删除指标源表下的指标信息列", function() {
		for(var i=0;i<ids.length;i++){//判断选中的是否有被注册的
			$.commAjax({
				url:$.ctx+"/api/source/sourceInfo/queryList",
				async:false,
				postData:{"sourceTableId":ids[i]},
				onSuccess:function(data){
					for(var num = 0 ;num <data.data.length; num++){
						$.commAjax({
							url:$.ctx+"/api/label/labelCountRules/queryList",
							async:false,
							postData:{"dependIndex":data.data[num].sourceId},
							onSuccess:function(data){
								if(data.data.length!=0){
									is="have";
								}
							}
						})
					}
				}
			})
		}
		if(is!="have"){
			for(var j=0;j<ids.length;j++){
				var k = j + 1;
				$.commAjax({
					url : $.ctx + '/api/source/sourceTableInfo/delete',
					async : false,
					postData : {
						"sourceTableId" : ids[j]
					},
					onSuccess:function(data){
						if(k == ids.length){
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
			}
		}else{
			$.alert("选择的指标源表中有被注册的指标，请重新选择");
		}
	});
}