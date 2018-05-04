/**
 * ------------------------------------------------------------------ 
 * 运行监控明细表格
 * add by shaosq 20180420
 * ------------------------------------------------------------------
 */
function initCustomGenerateTable(){
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
			sortable : false,
			frozen : true
		},
		{
			name : 'dataStatus',
			index : 'dataStatus',
			width : 80,
			align : "center",
			sortable : false,
			cellattr: setColColor,
			formatter : function(value, opts, data) {
	    		return $.getCodeDesc("KHQSCZT",value);
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
		pager : "#customGeneratePager"
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
			$("#bqscAll").prop("checked",true);
		}else{
			$("#bqscAll").prop("checked",false);
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