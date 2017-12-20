/**
 * Created by j on 2017/12/6.
 */
var model = {
		sourceTableName : "" ,
		sourceTableCnName : "" ,
		sourceTableType : null ,
		dateColumnName : "" ,
		whereSql : "" ,
		readCycle : null ,
		idType : null ,
		idColumn : "" ,
		idDataType : ""
}
window.loc_onload = function() {
	var isEdit = $.getUrlParam("isEdit");
	var id = $.getUrlParam("sourceTableId");
	if(id != null && id != "" && id != undefined){
		$.commAjax({
			url : $.ctx + '/api/source/sourceTableInfo/get',
			postData : {
				"sourceTableId" : id
			},
			onSuccess : function(data) {
				model.sourceTableName = data.data.sourceTableName;
				model.sourceTableCnName = data.data.sourceTableCnName;
				model.sourceTableType = data.data.sourceTableType;
				model.dateColumnName = data.data.dateColumnName;
				model.whereSql = data.data.whereSql;
				model.readCycle = data.data.readCycle;
				model.idType = data.data.idType;
				model.idColumn = data.data.idColumn;
				model.idDataType = data.data.idDataType;
			}
		})
	}
	new Vue({
		el:"#dataD",
		data:model
	})
	var url = "";
	var pD = {};
	if (isEdit == 1) {
		url = $.ctx + "/api/source/sourceInfo/queryPage";
		pD = {
			'sourceTableId': id
		}
	}
	$("#jsonmap").jqGrid({
		url: url,
		postData: pD,
		datatype: "json",
		colNames: ['字段名称', '字段类型', '指标中文名', '描述', '操作'],
		colModel: [{
			name: 'sourceName',
			index: 'sourceName',
			width: 80,
			sortable: false,
			frozen: true,
			editable: true
		},
		// frozen : true固定列
		{
			name: 'cooColumnType',
			index: 'cooColumnType',
			width: 70,
			sortable: false,
			align: "center",
			editable: true,
			formatter: function() {
				return '<select name="columnCnName" dicCode="ZDLXZD" type="text" class="form-control input-pointer"></select>';
			}
		},
		{
			name: 'columnCnName',
			index: 'columnCnName',
			width: 110,
			align: "center",
			sortable: false,
			editable: true
		},
		{
			name: 'columnUnit',
			index: 'columnUnit',
			width: 120,
			align: "center",
			editable: true,
			sortable: true
		},
		{
			name: 'op',
			index: 'op',
			width: 40,
			sortable: false,
			align: "center",
			formatter: function() {
				return '<button onclick="fun_to_del()" type="button" class="btn btn-default  ui-table-btn ui-table-btn">删除</button>';
			}
		}],
		cellEdit: true,
		// 单个编辑 去掉行编辑
		onSelectRow: function(id) {
			$('#jsonmap').jqGrid('editRow', id, true);
		},
		rowList: [10, 20, 30],
		// pager: '#pjmap',//分页的id
		// sortname: 'invdate',//排序的字段名称 不需要的话可置为空
		// 取值取自colModel中的index字段
		viewrecords: true,
		multiselect: false,
		// caption:"标题",
		rownumbers: false,
		// 是否展示行号
		sortorder: "desc",
		// 排序方式
		jsonReader: {
			repeatitems: false,
			id: "0"
		},
		height: '100%'
	});
	var dataRow = {
		"sourceName": null,
		"invdate": null,
		"columnCnName": null,
		"tax": null,
		"op": null
	}
	$("#btn_addRow").click(function() {
		$("#jsonmap").jqGrid("addRowData", 5, dataRow, "last");
	})
}
function setColor(cellvalue, options, rowObject) {
	if (rowObject.total > 700) {
		return '<span class="appMonitor" style="color:#faa918;">草稿</span>';
	}
	return cellvalue;
}
function fun_to_del() {
	var id = $('#jsonmap').jqGrid('getGridParam', 'selrow');
	if (id == null || id == "" || id == undefined) {
		$.alert("请选中要删除的行");
	}
	$("#jsonmap").jqGrid("delRowData", id);
}
function fun_to_save() {

}