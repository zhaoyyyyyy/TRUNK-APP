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
	idDataType : "",
	sortNum : 0,
	gxzq : [],
	sourceTableId : "",
	configId : ""
}
window.loc_onload = function() {
	model.configId = $.getCurrentConfigId();
	
	var dicGxzq = $.getDicData("GXZQZD");
	for(var i=0; i<dicGxzq.length; i++){
		model.gxzq.push(dicGxzq[i]);
	}
	var isEdit = $.getUrlParam("isEdit");
	var id = $.getUrlParam("sourceTableId");
	if (id != null && id != "" && id != undefined) {
		model.sourceTableId = id;
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
				$("#code"+data.data.readCycle).click();
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
	for(var k=0; k<dic.length; k++){
		dicCode += dic[k].code + ":" + dic[k].dataName;
		var j = k+1;
		if(j != dic.length){
			dicCode += ";";
		}
	}
	
	$("#jsonmap").jqGrid({
	    url: url,
	    editurl: $.ctx +"/api/source/sourceInfo/save",
	    postData: pD,
	    datatype: "json",
	    colNames: ['源字段名称', '字段类型', '指标名称', '描述','指标编码', '操作'],
	    colModel: [{
	        name: 'columnName',
	        index: 'columnName',
	        width: 80,
	        sortable: false,
	        frozen: true,
	        editable: true,
	        editrules: {
	        	required: true,
	        }
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
	        },
	        editrules: {
	        	required: true,
	        }
	    },
	    {
	        name: 'sourceName',
	        index: 'sourceName',
	        width: 110,
	        editable: true,
	        align: "center",
	        editrules: {
	        	required: true,
	        }
	    },
	    {
	        name: 'columnCaliber',
	        index: 'columnCaliber',
	        width: 120,
	        editable: true,
	        align: "center"
	    },
	    {
	        name: 'sourceId',
	        index: 'sourceId',
	        align: "center",
	        hidden: true
	    },
	    {
	        name: 'op',
	        index: 'op',
	        width: 40,
	        sortable: false,
	        align: "center",
	        formatter: function(value, opts, data) {
	            return '<button onclick="fun_to_del(\''+opts.rowId+'\',\''+data.sourceId+'\')" type="button" class="btn btn-default  ui-table-btn ui-table-btn">删除</button>';
	        }
	    }],
	    cellEdit: true,
	    afterGridLoad: function() {
	        var rows = $("#jsonmap").jqGrid('getRowData').length;
	        for (var i = 1; i <= rows; i++) {
	            $("#jsonmap").jqGrid('editRow', i);
	        }
	        model.sortNum += rows;
	    },
	    // pager: '#pjmap',//分页的id
	    // sortname: 'invdate',//排序的字段名称 不需要的话可置为空
	    // 取值取自colModel中的index字段
	    viewrecords: true,
	    rowNum:999,
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
		"columnName" : "",
		"cooColumnType" : "",
		"sourceName" : "",
		"columnCaliber" : "",
		"sourceId" : "",
		"op" : ""
	}
	$("#btn_addRow").click(function() {
		model.sortNum += 1;
		$("#jsonmap").jqGrid("addRowData", model.sortNum, dataRow, "last");
		$("#jsonmap").jqGrid("editRow", model.sortNum);
	})
}
function setColor(cellvalue, options, rowObject) {
	if (rowObject.total > 700) {
		return '<span class="appMonitor" style="color:#faa918;">草稿</span>';
	}
	return cellvalue;
}
function fun_to_del(id,sourceId) {
	$.commAjax({
		url:$.ctx+"/api/label/labelCountRules/queryList",
		postData:{"dependIndex":sourceId},
		onSuccess:function(data){
			if(data.data.length==0){
				$("#jsonmap").jqGrid("delRowData", id);
			}else{
				$.alert("该指标已经注册");
			}
		}
	})
}
function fun_to_save() {
	
	//取消所有编辑
	var ids = $("#jsonmap").jqGrid('getDataIDs');
    for (var i = 0; i < ids.length; i++) {
        $("#jsonmap").jqGrid("saveRow", ids[i]);
    }
	
    //拼接批量信息
	var list = $("#jsonmap").jqGrid("getRowData");
	var sourceInfoList = "sourceInfoList{";
	var idColumn = $("#idColumn").val();
	var colNum = "";
	for(var k = 0; k<list.length; k++){
		delete list[k].op;
		if(list[k].columnName == idColumn){
			colNum = "error";
			$.alert("第["+(k+1)+"]行字段名称与主键名称重复");
			break;
		}
		sourceInfoList += JSON.stringify(list[k]); 
		var l = k+1;
		if(l!=list.length){
			sourceInfoList += ",";
		}else{
			sourceInfoList += "}";
		}
	}
	$("#sourceInfoList").val(sourceInfoList);
	if(colNum == "error"){
		for (var q = 0; q < ids.length; q++) {
	        $("#jsonmap").jqGrid("editRow", ids[q]);
	    }
		return false;
	}else{
		$.alert("go");
		//开始进行保存
		var url_ = "";
		var msss = "";
		if(model.sourceTableId!=null && model.sourceTableId!=undefined && model.sourceTableId!= ""){
			url_ = $.ctx + '/api/source/sourceTableInfo/update';
			msss = "修改成功";
		}else{
			$("#sourceTableId").removeAttr("name");
			url_ = $.ctx + '/api/source/sourceTableInfo/save';
			msss = "保存成功";
		}
		$.commAjax({
			url : url_,
			async : false,
			postData : $('#formData').formToJson(),
			onSuccess : function(data) {
				if(data.data == "success"){
					$.success(msss, function() {
						history.back(-1);
					});
				}
			}
		});
		for (var p = 0; p < ids.length; p++) {
	        $("#jsonmap").jqGrid("editRow", ids[p]);
	    }
	}
}
function analysis(){
	var tableName = $("#sourceTableName").val();
	$.commAjax({
		url:$.ctx + "/backSql/columns",
		postData:{"tableName" : tableName},
		onSuccess:function(data){
			$("#jsonmap").jqGrid("clearGridData");
			for(var i=0;i < data.data.length;i++){
				if(data.data[i].data_type == "string"){
					data.data[i].data_type = "2";
				}else if(data.data[i].data_type == "integer"){
					data.data[i].data_type = "1";
				}
				var dataRow = {
					"columnName" : data.data[i].col_name,
					"cooColumnType" : data.data[i].data_type,
					"sourceName" : "",
					"columnCaliber" : data.data[i].comment,
					"sourceId" : "",
					"op" : ""
				}
				model.sortNum += 1;
				$("#jsonmap").jqGrid("addRowData", model.sortNum, dataRow, "last");
				$("#jsonmap").jqGrid("editRow", model.sortNum);
			}
		}
	})
}
function fun_to_import(){
	var wd = $.window('导入列信息', $.ctx
			+ '/aibi_lc/pages/label/dataSource_import.html', 500, 200);
    	wd.reload = function() {
    		//TODO
    	}
}