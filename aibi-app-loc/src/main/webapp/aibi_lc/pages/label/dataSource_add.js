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
}
window.loc_onload = function() {
	var obj = $("#preConfig_list").find("span");
	$("#configId").val(obj.attr("configId"));
	
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
	    colNames: ['字段名称', '字段类型', '指标中文名', '描述', '操作'],
	    colModel: [{
	        name: 'sourceName',
	        index: 'sourceName',
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
	        name: 'columnCnName',
	        index: 'columnCnName',
	        width: 110,
	        editable: true,
	        align: "center",
	        editrules: {
	        	required: true,
	        	custom: true,
	        	custom_func: fun_cnName
	        }
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
	        formatter: function(value, opts, data) {
	            return '<button onclick="fun_to_del('+opts.rowId+')" type="button" class="btn btn-default  ui-table-btn ui-table-btn">删除</button>';
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
function fun_to_del(id) {
	$("#jsonmap").jqGrid("delRowData", id);
}
function fun_to_save() {
	
	$.alert($("#configId").val());
	
	//取消所有编辑
	var ids = $("#jsonmap").jqGrid('getDataIDs');
    for (var i = 0; i < ids.length; i++) {
        $("#jsonmap").jqGrid("saveRow", ids[i]);
    }
	
    //拼接批量信息
	var list = $("#jsonmap").jqGrid("getRowData");
	var sourceInfoList = "sourceInfoList{";
	for(var k = 0; k<list.length; k++){
		delete list[k].op;
		sourceInfoList += JSON.stringify(list[k]); 
		var l = k+1;
		if(l!=list.length){
			sourceInfoList += ",";
		}else{
			sourceInfoList += "}";
		}
	}
	$("#sourceInfoList").val(sourceInfoList);
	
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
		postData : $('#formData').formToJson(),
		onSuccess : function(data) {
			if(data.data == "success"){
				$.success(msss, function() {
					history.back(-1);
				});
			}
		}
	})
}
function fun_cnName(value,colName){
	var patrn=/[^\u4e00-\u9fa5]/;
	if (patrn.test(value)) { 
		return [false,"请在["+colName+"]列输入中文"];
	} else { 
		return [true,""]; 
	} 
}