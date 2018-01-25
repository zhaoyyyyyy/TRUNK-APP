var model = {
	priKey : "",// 主键标识
	tableDesc : "",// 全量表描述
	isPartition : "",// 是否分区

	dayTableName : "",// 日表名
	dayMainColumn : "",// 日表主键字段
	dayPartitionColumn : "",// 日表分区字段

	monthTableName : "",// 月表名
	monthMainColumn : "",// 月表主键字段
	monthPartitionColumn : "",// 月表分区字段

	otherColumn : ""// 其他字段
}
window.loc_onload = function() {
	var priKey = $.getUrlParam("priKey");
	var wd = frameElement.lhgDG;
	var number = "1:1;2:2;3:3;4:4;5:5";
	if (priKey != null && priKey != "" && priKey != undefined) {
		model.priKey = priKey;
		$.commAjax({
			url : $.ctx + '/api/back/allUserMsg/get',
			postData : {
				"priKey" : priKey
			},
			onSuccess : function(data) {
				model.tableDesc = data.data.tableDesc;
				if (data.data.isPartition == "是") {
					$("#isPartitionCheckedInput").click();
				}
				model.dayTableName = data.data.dayTableName;
				model.dayMainColumn = data.data.dayMainColumn;
				model.dayPartitionColumn = data.data.dayPartitionColumn;

				model.monthTableName = data.data.monthTableName;
				model.monthMainColumn = data.data.monthMainColumn;
				model.monthPartitionColumn = data.data.monthPartitionColumn;

				model.otherColumn = data.data.otherColumn;
			}
		})
	}else{
		priKey = "selectNull";
	}

	new Vue({
		el : '#dataD',
		data : model,
		mounted : function() {
			this.$nextTick(function() {
				var r = $(".easyui-validatebox");
				if (r.length) {
					r.validatebox();
				}
			})
		}
	})

	wd.addBtn("ok", "保存", function() {
		if ($('#saveDataForm').validateForm()) {
			var ids = $("#myjqgrid").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				$("#myjqgrid").jqGrid("saveRow", ids[i]);
				var rowData = JSON.stringify($("#myjqgrid").jqGrid("getRowData",
						ids[i]));
				if (rowData.indexOf('input') > 0) {
					return false;
				}
			}
			//拼接批量信息
			var list = $("#myjqgrid").jqGrid("getRowData");
			var dimOrgLevelStr = "";
			for(var k = 0; k<list.length; k++){
				if(!$.isNull(list[k]["dimOrgLevelId.orgColumnName"])){
					dimOrgLevelStr += JSON.stringify(list[k]); 
					var l = k+1;
					if(l!=list.length){
						dimOrgLevelStr += ";";
					}
				}else{
					break;
				}
			}
			$("#dimOrgLevelStr").val(dimOrgLevelStr);
			var url_ = "";
			var msss = "";
			if (model.priKey != null && model.priKey != undefined
					&& model.priKey != "") {
				url_ = $.ctx + '/api/back/allUserMsg/update';
				msss = "修改成功";
			} else {
				$("#priKey").removeAttr("name");
				;
				url_ = $.ctx + '/api/back/allUserMsg/save';
				msss = "保存成功";
			}
			$.commAjax({
				url : url_,
				isShowMask : true,
				maskMassage : 'Load...',
				postData : $('#saveDataForm').formToJson(),
				onSuccess : function(data) {
					if (data.data == "success") {
						$.success(msss, function() {
							wd.cancel();
							wd.reload();
						});
					}

				}
			})
		}
	});

	wd.addBtn("cancel", "取消", function() {
		wd.cancel();
	});

	$("#myjqgrid").jqGrid({
		url : $.ctx + '/api/back/dimOrgLevel/queryPage',
		postData : {"dimOrgLevelId.priKey":priKey},
		editurl : "clientArray",
		datatype : "json",
		colNames : [ '组织字段名称', '层级', '排序' ],
		colModel : [ {
			name : 'dimOrgLevelId.orgColumnName',
			index : 'dimOrgLevelId.orgColumnName',
			width : 60,
			sortable : false,
			editable: true,
			frozen : true,
			align : "center"
		}, {
			name : 'levelId',
			index : 'levelId',
			width : 40,
			sortable : false,
			editable: true,
			frozen : true,
			align : "center",
			edittype: 'select',
	        formatter: 'select',
			editoptions: {
	            value: number
	        },
		}, {
			name : 'sortNum',
			index : 'sortNum',
			width : 40,
			sortable : false,
			editable: true,
			frozen : true,
			align : "center",
			edittype: 'select',
	        formatter: 'select',
			editoptions: {
	            value: number
	        },
		} ],
		cellEdit : true,
		jsonReader: {
	        repeatitems: false,
	        id: "0"
	    },
		afterGridLoad : function() {
			if (priKey == "selectNull") {
				var dataRow = {
					"dimOrgLevelId.orgColumnName" : "",
					"levelId" : "",
					"sortNum" : "",
				}
				for (var i = 1; i <= 5; i++) {
					$("#myjqgrid").jqGrid("addRowData", i, dataRow, "last");
					$("#myjqgrid").jqGrid("editRow", i);
				}
			}else{
				var ids = $("#myjqgrid").jqGrid('getDataIDs');
			    for (var i = 0; i < ids.length; i++) {
			    	$("#myjqgrid").jqGrid("editRow", ids[i]);
			    }
			}
		}
	});
}
function isPartitionChecked(obj) {
	if (obj.checked) {
		$("#isPartition").val("是");
	} else {
		$("#isPartition").val("否");
	}
}
