/**
 * ------------------------------------------------------------------ 
 * 运行监控明细表格
 * add by shaosq 20180420
 * ------------------------------------------------------------------
 */
function initCustomGenerateTable(monitorDetail){
	$("#customGenerateTable").jqGrid({
		url : $.ctx + "/api/label/listInfo/queryPage",
		postData : {
			"configId" : monitorDetail.configId,
			"dataDate" : monitorDetail.qryDataDate.replace(/-/g, "")
		},
		datatype : "json",
		colNames : [ '客户群名称', '生成状态', '生成时间' ],
		colModel : [ {
			name : 'labelInfo.labelName',
			index : 'labelInfo.labelName',
			width : 160,
			align : "center",
			sortable : false
		},
		{
			name : 'dataStatus',
			index : 'dataStatus',
			width : 80,
			align : "center",
			sortable : false,
			formatter : function(value, opts, data) {
				if(Number(value)===0){
        			return '<span class="state-fail">' +$.getCodeDesc("QTZTZD",value)+ '</span>';
        		}else if(Number(value)===1){
					return '<span class="state-ready">' +$.getCodeDesc("QTZTZD",value)+ '</span>';
				}else if(Number(value)===2){
					return '<span class="state-progress">' +$.getCodeDesc("QTZTZD",value)+ '</span>';
				}else if(Number(value)===3){
					return '<span class="state-success">' +$.getCodeDesc("QTZTZD",value)+ '</span>';
				}else if(Number(value)===4){
					return '<span class="state-unStart">' +$.getCodeDesc("QTZTZD",value)+ '</span>';
				}
	    	}
		},{
			name : 'dataTime',
			index : 'dataTime',
			width : 140,
			sortable : false,
			align : "center",
			formatter:function(value, opts, data){
				return DateFmt.dateFormatter(value, opts, data);
			}
		}],
	   loadComplete:function(){
	    	$("#load_dataPrepareTable").hide();
	    	$("#load_labelGenerateTable").hide();
	    	$("#load_customPushTable").hide();
	    },
		autowidth : true,
		viewrecords : true,
		rowNum : 10,
		rownumbers : true,
		jsonReader : {
			repeatitems : false,
			id : "0"
		},
		height : '100%',
		rowList : [ 10, 20, 30 ],
		pager : "#customGeneratePager",
	});
	$("#customGenerateTable").jqGrid('setLabel', 0, '序号');
}

/**
 * 表格检索
 */
function qryCustomGenerateTableByCond(){
	var labelName = $("#customGenerateName").val();
	var khqscCodesTemp = [];
	//查询准备状态
	$("#khqscAll").parents("span").siblings("span").find("input:checkbox:checked").each(function(i){
		khqscCodesTemp.push($(this).val());
	});
	if(khqscCodesTemp){
		if(khqscCodesTemp.length ===0){
			$("#customGenerateTable").clearGridData();
			return;
		}else if(khqscCodesTemp.length === monitorDetail.khqscList.length){
			$("#khqscAll").prop("checked",true);
		}else{
			$("#khqscAll").prop("checked",false);
		}
	}
	$($("#customGenerateTable")).setGridParam({
		postData:{
			"configId":monitorDetail.configId,
			"dataDate":monitorDetail.qryDataDate.replace(/-/g,""),
			"labelName":labelName,
			"dataStatuses":khqscCodesTemp.join(",")
		},
		dataType : 'json'
	}).trigger("reloadGrid", [{
		page: 1
	}]);
}

/**
 * 状态全选
 */
function selectAllCustomGenerateStatus(e){
	//准备状态
	if($(e).prop("checked")){
		$("#khqscAll").parents("span").siblings("span").find("input:checkbox").each(function(i){
			$(this).prop("checked",true);
		});
		qryCustomGenerateTableByCond();
	}else{
		$("#khqscAll").parents("span").siblings("span").find("input:checkbox").each(function(i){
			$(this).prop("checked",false);
		});
		$("#customGenerateTable").clearGridData();
	}
}