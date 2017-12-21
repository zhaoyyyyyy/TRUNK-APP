/**
 * Created by j on 2017/12/6.
 */
var model = {
	sourceTableName : "",
	sourceTableCnName : "",
	sourceTableType : "",
	dateColumnName : "",
	whereSql : "",
	readCycle : "",
	idType : "",
	idColumn : "",
	idDataType : ""
}
window.loc_onload = function() {
	var isEdit = $.getUrlParam("isEdit");
	var id = $.getUrlParam("sourceTableId");
	if (id != null && id != "" && id != undefined) {
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
		el : "#dataD",
		data : model
	})
	var url = "";
	var pD = {};
	if (isEdit == 1) {
		url = $.ctx + "/api/source/sourceInfo/queryPage";
		pD = {
			'sourceTableId' : id
		}
	}
	
	var dic = $.getDicData("ZDLXZD");
	var dicCode = "";
	for(var i=0; i<dic.length; i++){
		dicCode += dic[i].code + ":" + dic[i].dataName;
		var j = i+1;
		if(j != dic.length){
			dicCode += ";";
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
	        align: "center",
	        edittype: 'select',
	        formatter: 'select',
	        editable: true,
	        editoptions: {
	            value: dicCode
	        }
	    },
	    {
	        name: 'columnCnName',
	        index: 'columnCnName',
	        width: 110,
	        editable: true,
	        align: "center"
	    },
	    {
	        name: 'columnUnit',
	        index: 'columnUnit',
	        width: 120,
	        editable: true,
	        align: "center"
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
	    afterGridLoad: function() {
	        var rows = $("#jsonmap").jqGrid('getRowData').length;
	        for (var i = 1; i <= rows; i++) {
	            $("#jsonmap").jqGrid('editRow', i);
	        }
	    },
	    // pager: '#pjmap',//分页的id
	    // sortname: 'invdate',//排序的字段名称 不需要的话可置为空
	    // 取值取自colModel中的index字段
	    viewrecords: true,
	    rownumbers: true,
	    multiselect: false,
	    // caption:"标题",
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
		"sourceName" : "",
		"cooColumnType" : "",
		"columnCnName" : "",
		"columnUnit" : "",
		"op" : ""
	}
	$("#btn_addRow").click(function() {
		var rows = $("#jsonmap").jqGrid('getRowData').length;
		$("#jsonmap").jqGrid("addRowData", rows + 1, dataRow, "last");
		$("#jsonmap").jqGrid('editRow', rows + 1);
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
	var list = $("#jsonmap").jqGrid("getRowData");
	var obj=list[0].sourceName;
	debugger;
}