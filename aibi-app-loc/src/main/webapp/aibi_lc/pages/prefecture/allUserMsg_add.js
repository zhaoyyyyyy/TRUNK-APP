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
			var ids = $("#jsonmap").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				$("#jsonmap").jqGrid("saveRow", ids[i]);
				var rowData = JSON.stringify($("#jsonmap").jqGrid("getRowData",
						ids[i]));
				if (rowData.indexOf('input') > 0) {
					return false;
				}
			}
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
		postData : {"priKey":priKey},
		editurl : "clientArray",
		datatype : "json",
		colNames : [ '组织字段名称', '层级', '排序' ],
		colModel : [ {
			name : 'orgColumnName',
			index : 'orgColumnName',
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
		}, {
			name : 'sortNum',
			index : 'sortNum',
			width : 40,
			sortable : false,
			editable: true,
			frozen : true,
			align : "center",
		} ],
		cellEdit : true,
		jsonReader: {
	        repeatitems: false,
	        id: "0"
	    },
		afterGridLoad : function() {
			if ($.isNull(priKey)) {
				var dataRow = {
					"orgColumnName" : "",
					"levelId" : "",
					"sortNum" : "",
				}
				for (var i = 1; i <= 5; i++) {
					$("#myjqgrid").jqGrid("addRowData", i, dataRow, "last");
					$("#myjqgrid").jqGrid("editRow", i);
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
