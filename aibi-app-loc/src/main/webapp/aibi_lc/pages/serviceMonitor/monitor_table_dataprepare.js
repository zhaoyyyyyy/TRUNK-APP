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
	var zbCodesTemp = [];
	//查询准备状态
	$("#zbList span").find("input:checkbox[name=zbbox]:checked").each(function(i){
		 zbCodesTemp.push($(this).val());
	});
	$("#dataPrepareTable").jqGrid({
		url: $.ctx + "/api/source/sourceTableInfo/queryPageForMonitor",
		isShowMask : true,
		postData : {
			"configId" : monitorDetail.configId,
			"dataDate" : monitorDetail.qryDataDate.replace(/-/g, ""),
			"dataStatuses":zbCodesTemp.join(",")
		},
		datatype : "json",
		colNames : [ '表名', '准备状态', '抽取状态', '准备完成时间', '抽取完成时间','表ID','异常信息','准备状态编码','抽取状态编码' ],
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
			formatter : function(value) {
				if(value === null){
					//未准备
					return '<span class="state-unStart">' +$.getCodeDesc("SJZBZT",0)+ '</span>';
				}else{
					//准备完成
					return '<span class="state-success">' +$.getCodeDesc("SJZBZT",1)+ '</span>';
				}
			}
		}, {
			name : 'isDoing',
			index : 'isDoing',
			sortable : false,
			width : 80,
			align : "center",
			formatter : function(value,col, rowData) {
				if(typeof value === 'undefined' || value === null ){
					return "";
				}else{
					var dataStatus = Number(rowData.dataStatus);
					if(Number(value)===1 && dataStatus ===1){//抽取中
	        			return '<span class="state-progress">' +$.getCodeDesc("SJCQZT",1)+ '</span>';
	        		}else if(Number(value)===0 && dataStatus ===0){//抽取完成
						return '<span class="state-success">' +$.getCodeDesc("SJCQZT",0)+ '</span>';
					}else if(Number(value)===0 && dataStatus ===2){//抽取失败
						return '<span class="state-fail">' +$.getCodeDesc("SJCQZT",2)+ '</span>';
					}else if(Number(value)===0 && dataStatus ===1){//未抽取
						return '<span class="state-unStart">' +$.getCodeDesc("SJCQZT",3)+ '</span>';
					}else{
						return "";
					}
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
		},{
    		name : 'sourceTableId',
    		index : 'sourceTableId',
    		hidden:true
    	},{
    		name : 'exceptionDesc',
    		index : 'exceptionDesc',
    		hidden:true
    	} ,{
    		name : 'dataStatus',
    		index : 'dataStatus',
    		hidden:true
    	},{
    		name : 'isDoing',
    		index : 'isDoing',
    		hidden:true
    	}
    	],
		autowidth : true,
		viewrecords : true,
		rowNum : 10,
		rownumbers : true,
		jsonReader : {
			repeatitems : false,
			id : "0"
		},
		onSelectRow: function (id) {
			setDataPrepareLineChecked(id);
            var rowData = $("#dataPrepareTable").jqGrid("getRowData", id);
            var dataStatus = rowData.dataStatus;
            var isDoing = rowData.isDoing;
            if(isDoing && dataStatus && (Number(isDoing) ===0 && Number(dataStatus) ===2)){//抽取失败
            	$("#dataPrepareError").css("display","");
            	$("#dataPrepareError span").text(rowData.exceptionDesc);
            }else{
            	$("#dataPrepareError").css("display","none");
            }
		},
		ajaxGridOptions:{
			beforeSend :function(){
				$("#dataPrepareAnchor").find(".loading").show();
			}
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
function dataSearchKey(event){
	if(event.keyCode === 13){
		qryDataPrepareTableByCond();
	}
}
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


/**
 * 设置选中行样式
 * 
 * @param rowId
 */
function setDataPrepareLineChecked(rowId) {
	var ids = $("#dataPrepareTable").jqGrid("getDataIDs");
	if (ids) {
		for (var i = 0; i < ids.length; i++) {
			if (rowId === ids[i]) {
				$('#dataPrepareTable ' + '#' + ids[i]).find("td").addClass("ui-state-highlight");
			} else {
				$('#dataPrepareTable ' + '#' + ids[i]).find("td").removeClass("ui-state-highlight");
			}
		}
	}
}