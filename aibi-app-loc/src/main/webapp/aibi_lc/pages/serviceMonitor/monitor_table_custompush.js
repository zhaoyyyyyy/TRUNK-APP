/**
 * ------------------------------------------------------------------ 
 * 运行监控明细表格
 * add by shaosq 20180420
 * ------------------------------------------------------------------
 */

function initCustomPushTable(){
	$("#customPushTable").jqGrid({
		url : $.ctx + "/api/syspush/labelPushReq/queryPage",
		postData : {
			"configId" : monitorDetail.configId,
			"dataDate" : monitorDetail.qryDataDate.replace(/-/g, "")
		},
		datatype : "json",
		colNames : [ '客户群名称', '推送平台', '推送时间','推送状态' ],
		colModel : [ {
    		name : 'labelPushCycle.labelInfo.labelName',
    		index : 'labelPushCycle.labelInfo.labelName',
    		width : 110,
    		sortable : false,
    		align : "center",
    		frozen : true
    	},
    	 {
    		name : 'labelPushCycle.sysInfo.sysName',
    		index : 'labelPushCycle.sysInfo.sysName',
    		width : 110,
    		sortable : false,
    		frozen : true,
    		align : "center"
    	},{
    		name : 'startTime',
    		index : 'startTime',
    		width : 110,
    		sortable : false,
    		align : "center",
    		formatter:function(value, opts, data){
    			return DateFmt.dateFormatter(value, opts, data);
    		}
    	},
    	{
    		name : 'pushStatus',
    		index : 'pushStatus',
    		width : 50,
    		align : "center",
    		sortable : false,
    		cellattr: setColColor,
    		formatter : function(value, opts, data) {
        		return $.getCodeDesc("KHQTSZT",value);
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
		pager : "#customPushPager"
	});
	$("#customPushTable").jqGrid('setLabel', 0, '序号');
}


/**
 * 表格检索
 */
function qryCustomPushTableByCond(){
	var labelName = $("#customPushName").val();
	var khqtsCodesTemp = [];
	//查询准备状态
	$("#khqtsAll").parents("span").siblings("span").find("input:checkbox:checked").each(function(i){
		khqtsCodesTemp.push($(this).val());
	});
	if(khqtsCodesTemp){
		if(khqtsCodesTemp.length ===0){
			$("#customPushTable").clearGridData();
			return;
		}else if(khqtsCodesTemp.length === monitorDetail.khqscList.length){
			$("#khqtsAll").prop("checked",true);
		}else{
			$("#khqtsAll").prop("checked",false);
		}
	}
	$("#customPushTable").setGridParam({
		postData:{
			"configId":monitorDetail.configId,
			"dataDate":monitorDetail.qryDataDate.replace(/-/g,""),
			"labelName":labelName,
			"pushStatuses":khqtsCodesTemp.join(",")
		},
		dataType : 'json'
	}).trigger("reloadGrid", [{
		page: 1
	}]);
}

/**
 * 状态全选
 */
function selectAllCustomPushStatus(e){
	//准备状态
	if($(e).prop("checked")){
		$("#khqtsAll").parents("span").siblings("span").find("input:checkbox").each(function(i){
			$(this).prop("checked",true);
		});
		qryCustomPushTableByCond();
	}else{
		$("#khqtsAll").parents("span").siblings("span").find("input:checkbox").each(function(i){
			$(this).prop("checked",false);
		});
		$("#customPushTable").clearGridData();
	}
}