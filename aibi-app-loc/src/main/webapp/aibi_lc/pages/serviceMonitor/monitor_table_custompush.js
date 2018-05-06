/**
 * ------------------------------------------------------------------ 
 * 运行监控明细表格
 * add by shaosq 20180420
 * ------------------------------------------------------------------
 */

function initCustomPushTable(monitorDetail){
	$("#customPushTable").jqGrid({
		url : $.ctx + "/api/syspush/labelPushReq/queryPage",
		postData : {
			"configId" : monitorDetail.configId,
			"dataDate" : monitorDetail.qryDataDate.replace(/-/g, "")
		},
		datatype : "json",
		colNames : [ '客户群名称', '推送平台', '推送时间','推送状态','异常信息','状态编码' ],
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
    		formatter : function(value, opts, data) {
        		if(Number(value)===1){
        			return '<span class="state-ready">' +$.getCodeDesc("KHQTSZT",value)+ '</span>';
        		}else if(Number(value)===2){
					return '<span class="state-progress">' +$.getCodeDesc("KHQTSZT",value)+ '</span>';
				}else if(Number(value)===3){
					return '<span class="state-success">' +$.getCodeDesc("KHQTSZT",value)+ '</span>';
				}else if(Number(value)===0){
					return '<span class="state-fail">' +$.getCodeDesc("KHQTSZT",value)+ '</span>';
				}
        		
        	}
    	},{
    		name : 'exceInfo',
    		index : 'exceInfo',
    		hidden:true
    	}
    	,{
    		name : 'pushStatus',
    		index : 'pushStatus',
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
				setThisLineChecked(id);
                var rowData = $("#customPushTable").jqGrid("getRowData", id);
                var pushStatus = rowData.pushStatus;
                if(pushStatus && Number(pushStatus) === 0){
                	$("#customPushError").css("display","");
                	$("#customPushError span").text(rowData.exceInfo);
                }else{
                	$("#customPushError").css("display","none");
                }
			}
		},
	    loadComplete:function(){
	    	$("#load_dataPrepareTable").hide();
	    	$("#load_labelGenerateTable").hide();
	    	$("#load_customGenerateTable").hide();
	    },
		rowNum : 10,
		rownumbers : true,
		jsonReader : {
			repeatitems : false,
			id : "0"
		},
		height : '100%',
		rowList : [ 10, 20, 30 ],
		pager : "#customPushPager",
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
/**
 * 设置选中行样式
 * 
 * @param rowId
 */
function setThisLineChecked(rowId) {
	var ids = $("#customPushTable").jqGrid("getDataIDs");
	if (ids) {
		for (var i = 0; i < ids.length; i++) {
			if (rowId == ids[i]) {
				$('#customPushTable ' + '#' + ids[i]).find("td").addClass("ui-state-highlight");
			} else {
				$('#customPushTable ' + '#' + ids[i]).find("td").removeClass("ui-state-highlight");
			}
		}
	}
}