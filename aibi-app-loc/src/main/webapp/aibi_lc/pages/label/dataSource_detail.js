var model = {
	sourceTableName : "",
	sourceTableCnName : "",
	sourceTableType : "",
	dateColumnName : "",
	whereSql : "",
	readCycle : "",
	idType : "",
	idColumn : "",
	idDataType : "",
	tableSchema : "",
	gxzqzd : [],
	bslxzd : [],
	zdlxzd : [],
}
window.loc_onload = function() {
	var id = $.getUrlParam("sourceTableId");
	var wd = frameElement.lhgDG;

	var dicGxzq = $.getDicData("GXZQZD");
	for (var i = 0; i < dicGxzq.length; i++) {
		model.gxzqzd[dicGxzq[i].code]=dicGxzq[i].dataName;
	}
	var dicGxzq = $.getDicData("BSLXZD");
	for (var i = 0; i < dicGxzq.length; i++) {
		model.bslxzd[dicGxzq[i].code]=dicGxzq[i].dataName;
	}
	var dicGxzq = $.getDicData("ZDLXZD");
	for (var i = 0; i < dicGxzq.length; i++) {
		model.zdlxzd[dicGxzq[i].code]=dicGxzq[i].dataName;
	}
	
	
	var cooType = [];
	cooType[1]="integer";
	cooType[2]="varchar";
	
	$.commAjax({
		url : $.ctx + '/api/source/sourceTableInfo/get',
		postData : {
			"sourceTableId" : id
		},
		isShowMask : true,
		maskMassage : 'Load...',
		onSuccess : function(data) {
			model.sourceTableName = data.data.sourceTableName;
			model.sourceTableCnName = data.data.sourceTableCnName;
			model.sourceTableType = data.data.sourceTableType;
			model.dateColumnName = data.data.dateColumnName;
			model.whereSql = data.data.whereSql;
			model.idColumn = data.data.idColumn;
			model.tableSchema = data.data.tableSchema;
			model.readCycle = model.gxzqzd[data.data.readCycle];
			model.idType = model.bslxzd[data.data.idType];
			model.idDataType = model.zdlxzd[data.data.idDataType];
		}
	})
	new Vue({
		el : "#dataD",
		data : model,
	})

	$("#jsonmap").jqGrid({
		url : $.ctx + "/api/source/sourceInfo/queryPage",
		editurl : "clientArray",
		postData : {
			'sourceTableId' : id
		},
		datatype : "json",
		colNames : [ '源字段名称', '字段类型', '指标名称', '描述' ],
		colModel : [ {
			name : 'columnName',
			index : 'columnName',
			width : 110,
			sortable : false,
			frozen : true,
		},
		// frozen : true固定列
		{
			name : 'cooColumnType',
			index : 'cooColumnType',
			width : 50,
			align : "center",
			sortable : false,
			formatter:function(value){
				return cooType[value];
			}
		}, {
			name : 'sourceName',
			index : 'sourceName',
			width : 110,
			sortable : false,
			align : "center",
		}, {
			name : 'columnCaliber',
			index : 'columnCaliber',
			sortable : false,
			width : 120,
			align : "center"
		} ],
		viewrecords : true,
		rowNum : 999,
		rownumbers : true,
		// caption:"标题",
		// 是否展示行号
		sortorder : "desc",
		// 排序方式
		jsonReader : {
			repeatitems : false,
			id : "0"
		},
		height : '100%'
	});
	
	wd.addBtn("cancel", "取消", function() {
		wd.cancel();
	});
}