/**
 * ------------------------------------------------------------------ 
 * 标签生成表格
 * add by shaosq 20180420
 * ------------------------------------------------------------------
 */
function initLabelGenerateTable(){
	$("#labelGenerateTable").jqGrid({
		url :$.ctx + "/api/label/labelStatus/queryPage",
		postData : {
			"configId" : monitorDetail.configId,
			"dataDate" : monitorDetail.qryDataDate.replace(/-/g, "")
		},
		datatype : "json",
		colNames : [ '标签名称', '生成状态', '目标表列名', '表名','表类型','错误信息描述' ],
		colModel : [ {
    		name : 'labelInfo.labelName',
    		index : 'labelInfo.labelName',
    		width : 110,
    		align : "center",
    		sortable : false,
    		frozen : true,
    	},
    	{
    		name : 'dataStatus',
    		index : 'dataStatus',
    		width : 50,
    		align : "center",
    		sortable : false,
    		cellattr: setColColor,
    		formatter: function(cellvalue, options, rowObject) {
	            return $.getCodeDesc("BQSCZT",cellvalue);
	        }
    	}, {
    		name : 'mdaSysTableColumnNames',
    		index : 'mdaSysTableColumnNames',
    		width : 110,
    		sortable : false,
    		align : "center",
    	}, {
    		name : 'mdaSysTable.tableName',
    		index : 'mdaSysTable.tableName',
    		sortable : false,
    		width : 120,
    		align : "center"
    			
    	} ,{
    		name : 'mdaSysTable.tableType',
    		index : 'mdaSysTable.tableType',
    		width : 110,
    		sortable : false,
    		align : "center",
    		formatter : function(value, opts, data) {
        		return $.getCodeDesc("BQKBLX",value);
        	}
    	},{
    		name : 'exceptionDesc',
    		index : 'exceptionDesc',
    		hidden:true
    	}],
		autowidth : true,
		viewrecords : true,
		rowNum : 10,
		rownumbers : true,
		jsonReader : {
			repeatitems : false,
			id : "0"
		},
		onSelectRow: function (id) {
			if (window.event.button == 0) // 判断是否为鼠标左键点击
			{
				setThisLineColor(id);
                var rowData = $("#labelGenerateTable").jqGrid("getRowData", id);
                var dataStatus = rowData.dataStatus;
                if(dataStatus && Number(dataStatus) == 0){
                	$("#catchError").css("display","");
                	var rowData = $(tableId).jqGrid("getRowData", rowId);
                	$("#catchError span").text(rowData.exceptionDesc);
                }else{
                	$("#catchError").css("display","none");
                }
			}
		},
		height : '100%',
		rowList : [ 10, 20, 30 ],
		pager : "#labelGeneratePager"
	});
	$("#labelGenerateTable").jqGrid('setLabel', 0, '序号');
}


/**
 * 表格检索
 */
function qryLabelGenerateTableByCond(){
	var labelName = $("#labelGenerateName").val();
	var zqscCodesTemp = [];
	//查询准备状态
	$("#bqscAll").parents("span").siblings("span").find("input:checkbox:checked").each(function(i){
		zqscCodesTemp.push($(this).val());
	});
	if(zqscCodesTemp){
		if(zqscCodesTemp.length ===0){
			$("#labelGenerateTable").clearGridData();
			return;
		}else if(zqscCodesTemp.length === monitorDetail.bqscList.length){
			$("#bqscAll").prop("checked",true);
		}else{
			$("#bqscAll").prop("checked",false);
		}
	}
	$($("#labelGenerateTable")).setGridParam({
		postData:{
			"configId":monitorDetail.configId,
			"dataDate":monitorDetail.qryDataDate.replace(/-/g,""),
			"labelName":labelName,
			"dataStatuses":zqscCodesTemp.join(",")
		},
		dataType : 'json'
	}).trigger("reloadGrid", [{
		page: 1
	}]);
}

/**
 * 状态全选
 */
function selectAllLabelGenerateStatus(e){
	//准备状态
	if($(e).prop("checked")){
		$("#bqscAll").parents("span").siblings("span").find("input:checkbox").each(function(i){
			$(this).prop("checked",true);
		});
		qryLabelGenerateTableByCond();
	}else{
		$("#bqscAll").parents("span").siblings("span").find("input:checkboxaaa").each(function(i){
			$(this).prop("checked",false);
		});
		$("#labelGenerateTable").clearGridData();
	}
}

/**
 * 设置选中行样式
 * 
 * @param rowId
 */
function setThisLineColor(rowId) {
	var ids = $("#labelGenerateTable").jqGrid("getDataIDs");
	if (ids) {
		for (var i = 0; i < ids.length; i++) {
			if (rowId == ids[i]) {
				$('#labelGenerateTable ' + '#' + ids[i]).find("td").addClass("ui-state-highlight");
			} else {
				$('#labelGenerateTable ' + '#' + ids[i]).find("td").removeClass("ui-state-highlight");
			}
		}
	}
}
