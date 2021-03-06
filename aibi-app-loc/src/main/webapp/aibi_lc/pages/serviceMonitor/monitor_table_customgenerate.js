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
		colNames : [ '客户群名称', '生成状态','数据日期', '生成时间','清单ID','生成状态编码','异常信息' ],
		colModel : [ {
			name : 'labelInfo.labelName',
			index : 'labelInfo.labelName',
			width : 130,
			align : "center",
			sortable : false
		},
		{
			name : 'dataStatus',
			index : 'dataStatus',
			width : 80,
			align : "center",
			sortable : false,
			formatter : function(value) {
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
    		name : 'listInfoId.dataDate',
    		index : 'listInfoId.dataDate',
    		width : 60,
    		sortable : false,
    		align : "center"
    	},{
			name : 'dataTime',
			index : 'dataTime',
			width : 110,
			sortable : false,
			align : "center",
			formatter:function(value, opts, data){
				return DateFmt.dateFormatter(value, opts, data);
			}
		},{
    		name : 'listInfoId.customGroupId',
    		index : 'listInfoId.customGroupId',
    		hidden:true
    	},{
    		name : 'dataStatus',
    		index : 'dataStatus',
    		hidden:true
    	},{
    		name : 'exceptionDesc',
    		index : 'exceptionDesc',
    		hidden:true
    	}],
    	onSelectRow: function (id) {
			setCustomGenerateLineColor(id);
            var rowData = $("#customGenerateTable").jqGrid("getRowData", id);
            var dataStatus = rowData.dataStatus;
            if(dataStatus && Number(dataStatus) === 0){
            	$("#customGenerateError").css("display","");
            	$("#customGenerateError span").text(rowData.exceptionDesc);
            }else{
            	$("#customGenerateError").css("display","none");
            }
		},
	    ajaxGridOptions:{
			beforeSend :function(){
				$("#customGenerateAnchor").find(".loading").show();
			}
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
 * 表格检索：回车
 */
function customSearchKey(event){
	if(event.keyCode === 13){
		qryCustomGenerateTableByCond();
	}
}

/**
 * 表格检索:点击
 */
function qryCustomGenerateTableByCond(){
	$("#customGenerateError").css("display","none");
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

/**
 * 设置选中行样式
 * 
 * @param rowId
 */
function setCustomGenerateLineColor(rowId) {
	var ids = $("#customGenerateTable").jqGrid("getDataIDs");
	if (ids) {
		for (var i = 0; i < ids.length; i++) {
			if (rowId === ids[i]) {
				$('#customGenerateTable ' + '#' + ids[i]).find("td").addClass("ui-state-highlight");
			} else {
				$('#customGenerateTable ' + '#' + ids[i]).find("td").removeClass("ui-state-highlight");
			}
		}
	}
}