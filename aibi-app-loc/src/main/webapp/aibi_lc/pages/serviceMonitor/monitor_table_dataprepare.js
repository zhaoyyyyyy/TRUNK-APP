/**
 * ------------------------------------------------------------------ 
 * 数据准备表格
 * add by shaosq 20180420
 * ------------------------------------------------------------------
 */
/**
 * 初始化数据准备表格
 */
function initDataPrepareTable(monitorDetail){
	$("#dataPrepareTable").jqGrid({
		url : $.ctx + "/api/source/TargetTableStatus/queryPage",
		isShowMask : true,
		postData : {
			"configId" : monitorDetail.configId,
			"dataDate" : monitorDetail.qryDataDate.replace(/-/g, "")
		},
		datatype : "json",
		colNames : [ '表名', '准备状态', '抽取状态', '准备完成时间', '抽取完成时间' ],
		colModel : [ {
			name : 'sourceTableName',
			index : 'sourceTableName',
			width : 200,
			sortable : false,
			align : "center",
			frozen : true,
		}, {
			name : 'dataStatus',
			index : 'dataStatus',
			width : 80,
			align : "center",
			sortable : false,
			formatter : function(value, opts, data) {
				if(value==0){
        			return '<span class="state-unStart">' +$.getCodeDesc("SJZBZT",value)+ '</span>';
        		}else if(value==1){
					return '<span class="state-success">' +$.getCodeDesc("SJZBZT",value)+ '</span>';
				}
			}
		}, {
			name : 'isDoing',
			index : 'isDoing',
			sortable : false,
			width : 80,
			align : "center",
			formatter : function(value, opts, data) {
				if(Number(value)===0){
        			return '<span class="state-progress">' +$.getCodeDesc("SJCQZT",value)+ '</span>';
        		}else if(Number(value)===1){
					return '<span class="state-success">' +$.getCodeDesc("SJCQZT",value)+ '</span>';
				}else if(Number(value)===2){
					return '<span class="state-fail">' +$.getCodeDesc("SJCQZT",value)+ '</span>';
				}
			}
		}, {
			name : 'startTime',
			index : 'startTime',
			width : 120,
			sortable : false,
			align : "center",
			formatter : function(cellvalue) {
				if (cellvalue) {
					return cellvalue.substr(0, 19);
				}
				return "";
			}
		}, {
			name : 'endTime',
			index : 'endTime',
			width : 120,
			sortable : false,
			align : "center",
			formatter : function(cellvalue) {
				if (cellvalue) {
					return cellvalue.substr(0, 19);
				}
				return "";
			}
		} ],
		autowidth : true,
		viewrecords : true,
		rowNum : 10,
		rownumbers : true,
		jsonReader : {
			repeatitems : false,
			id : "0"
		},
	    loadComplete:function(){
	    	$("#load_labelGenerateTable").hide();
	    	$("#load_customGenerateTable").hide();
	    	$("#load_customPushTable").hide();
	    },
		height : '100%',
		rowList : [ 10, 20, 30 ],
		pager : "#dataPreparePager"
	});
	$("#dataPrepareTable").jqGrid('setLabel', 0, '序号');
	$("#zbList").show();
	$("#cqList").hide();
}

/**
 * 表格检索
 */
function qryDataPrepareTableByCond(){
	var sourceTableName = $("#sourceTableName").val();
	var zbCodesTemp = [];
	var cqCodesTemp = [];
	if (monitorDetail.zbFlag) {
		//查询准备状态
		$("#zbList span").find("input:checkbox[name=zbbox]:checked").each(function(i){
			 zbCodesTemp.push($(this).val());
		});
		if(zbCodesTemp){
			if(zbCodesTemp.length ===0){
				$("#dataPrepareTable").clearGridData();
				return;
			}else if(zbCodesTemp.length === monitorDetail.sjzbList.length){
				$("#sjzbAll").prop("checked",true);
			}else{
				$("#sjzbAll").prop("checked",false);
			}
		}
	}else{
		//查询选中的抽取状态
		$("#cqList span").find("input:checkbox[name=cqbox]:checked").each(function(i){
			cqCodesTemp.push($(this).val());
		});
		if(cqCodesTemp ){
			if(cqCodesTemp.length  === 0){
				$("#dataPrepareTable").clearGridData();
				return;
			}else if(cqCodesTemp.length === monitorDetail.sjcqList.length){
				$("#sjzbAll").prop("checked",true);
			}else{
				$("#sjzbAll").prop("checked",false);
			}
		}
	}
	
	$($("#dataPrepareTable")).setGridParam({
		postData:{
			"configId":monitorDetail.configId,
			"dataDate":monitorDetail.qryDataDate.replace(/-/g,""),
			"sourceTableName":sourceTableName,
			"dataStatuses":zbCodesTemp.join(","),
			"isDoings":cqCodesTemp.join(",")
		},
		dataType : 'json'
	}).trigger("reloadGrid", [{
		page: 1
	}]);
}

/**
 * 状态全选
 */
function selectAllDataPrepareStatus(e){
	//准备状态
	if (monitorDetail.zbFlag) {
		if($(e).prop("checked")){
			$("#zbList span").find("input:checkbox[name=zbbox]").each(function(i){
				$(this).prop("checked",true);
			});
			qryDataPrepareTableByCond();
		}else{
			$("#zbList span").find("input:checkbox[name=zbbox]").each(function(i){
				$(this).prop("checked",false);
			});
			$("#dataPrepareTable").clearGridData();
		}
	}else{//抽取状态
		if($(e).prop("checked")){
			$("#cqList span").find("input:checkbox[name=cqbox]").each(function(i){
				$(this).prop("checked",true);
			});
			qryDataPrepareTableByCond();
		}else{
			$("#cqList span").find("input:checkbox[name=cqbox]").each(function(i){
				$(this).prop("checked",false);
			});
			$("#dataPrepareTable").clearGridData();
		}
	}
}

/**
 * 表格状态切换
 */
function changeDataPrepareTableByStatus(e){
	monitorDetail.isDown=false;
	if(Number($(e).find("option:selected").val())===1){
		monitorDetail.zbFlag = true;
		$("#zbList").show();
		$("#cqList").hide();
		$("#zbList span").find("input:checkbox").each(function(i){
			$(this).prop("checked",true);
		});
	}else{
		monitorDetail.zbFlag = false;
		$("#zbList").hide();
		$("#cqList").show();
		$("#cqList span").find("input:checkbox").each(function(i){
			$(this).prop("checked",true);
		});
	}
	
	qryDataPrepareTableByCond();
}