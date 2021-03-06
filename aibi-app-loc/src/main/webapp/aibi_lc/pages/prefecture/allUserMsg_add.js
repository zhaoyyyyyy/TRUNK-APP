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

	otherColumn : "",// 其他字段

	showPartition : false,// 显示分区字段
}
window.loc_onload = function() {
	var priKey = $.getUrlParam("priKey");
    var isObj = $("#isPartitionCheckedInput");
    isObj.click();
    isObj.attr('checked',true);
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
				if (data.data.isPartition == "0") {
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
	} else {
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
				var rowData = JSON.stringify($("#myjqgrid").jqGrid(
						"getRowData", ids[i]));
				if (rowData.indexOf('input') > 0) {
					return false;
				}
			}
			// 拼接批量信息
			var list = $("#myjqgrid").jqGrid("getRowData");
			var dimOrgLevelStr = "";
			var levelList = [];
			var sortList = [];
			var exitLevel = false;
			var nullNum = 0;
			for (var k = 0; k < list.length; k++) {
				if (!$.isNull(list[k]["dimOrgLevelId.orgColumnName"])) {
					if (!isInArray(levelList, list[k].levelId)) {
						levelList.push(list[k].levelId);
					} else {
						$.alert("层级不能重复");
						for (var i = 1; i <= 5; i++) {
							$('#myjqgrid').jqGrid('editRow', i);
						}
						return false;
					}
					if (!isInArray(sortList, list[k].sortNum)) {
						sortList.push(list[k].sortNum);
					} else {
						$.alert("序号不能重复");
						for (var i = 1; i <= 5; i++) {
							$('#myjqgrid').jqGrid('editRow', i);
						}
						return false;
					}
					dimOrgLevelStr += JSON.stringify(list[k]);
					var l = k + 1;
					if (l != list.length) {
						dimOrgLevelStr += ";";
					}
				} else {
					nullNum++;
					if (nullNum == 5) {
						$.alert("请填写组织字段名称");
						for (var i = 1; i <= 5; i++) {
							$('#myjqgrid').jqGrid('editRow', i);
						}
						return false;
					}
					continue;
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
				},

			})
			for (var i = 1; i <= 5; i++) {
				$('#myjqgrid').jqGrid('editRow', i);
			}
		}
	});

	wd.addBtn("cancel", "取消", function() {
		wd.cancel();
	});

	$('#myjqgrid').jqGrid(
			{
				url : $.ctx + '/api/back/dimOrgLevel/queryPage',
				postData : {
					'dimOrgLevelId.priKey' : priKey
				},
				editurl : 'clientArray',
				datatype : 'json',
				colNames : [ '组织字段名称', '层级', '排序', '隐藏列' ],
				colModel : [ {
					name : 'dimOrgLevelId.orgColumnName',
					index : 'dimOrgLevelId.orgColumnName',
					width : 60,
					sortable : false,
					editable : true,
					frozen : true,
					align : 'center'
				}, {
					name : 'levelId',
					index : 'levelId',
					width : 20,
					sortable : false,
					editable : true,
					frozen : true,
					align : 'center',
					edittype : 'select',
					editoptions : {
						value : number
					},
					formatter : 'select',
				}, {
					name : 'sortNum',
					index : 'sortNum',
					width : 20,
					sortable : false,
					editable : true,
					frozen : true,
					align : 'center',
					edittype : 'select',
					editoptions : {
						value : number
					},
					formatter : 'select',
				}, {
					name : 'hidden',
					index : 'hidden',
					width : 20,
					hidden : true,
				} ],
				cellEdit : true,
				jsonReader : {
					repeatitems : false,
					id : '0'
				},
				afterGridLoad : function() {
					if (priKey == 'selectNull') {
						for (var i = 1; i <= 5; i++) {
							var dataRow = {
								'dimOrgLevelId.orgColumnName' : '',
								'levelId' : i,
								'sortNum' : i,
							}
							$('#myjqgrid').jqGrid('addRowData', i, dataRow,
									'last');
							$('#myjqgrid').jqGrid('editRow', i);
						}
					} else {
						var ids = $('#myjqgrid').jqGrid('getDataIDs');
						for (var i = 0; i < ids.length; i++) {
							$('#myjqgrid').jqGrid('editRow', ids[i]);
						}
						if (ids.length < 5) {
							for (var num = ids.length + 1; num <= 5; num++) {
								var dataRow = {
									'dimOrgLevelId.orgColumnName' : '',
									'levelId' : num,
									'sortNum' : num,
								}
								$('#myjqgrid').jqGrid('addRowData', num,
										dataRow, 'last');
								$('#myjqgrid').jqGrid('editRow', num);
							}
						}
					}
				}
			});
}
function isPartitionChecked(obj) {
	if (obj.checked) {
		$("#isPartition").val("0");
		model.showPartition = true;
	} else {
		$("#isPartition").val("1");
		model.showPartition = false;
	}
}
function isInArray(arr, value) {
	for (var i = 0; i < arr.length; i++) {
		if (value === arr[i]) {
			return true;
		}
	}
	return false;
}
