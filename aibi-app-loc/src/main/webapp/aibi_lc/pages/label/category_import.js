window.loc_onload = function() {
	var wd = frameElement.lhgDG;
	var sortNum = 0;

	// 统一浏览器对FILE 文件获取上传路径的差异
	$("#multipartFile").change(function() {
		var file = $(this).val();
		var fileName = file.substr(file.lastIndexOf("."));
		fileName = fileName.toUpperCase();
		if (fileName != ".csv" && fileName != ".CSV") {
			$("#fnameTip").empty();
			$("#fnameTip").show().append("请选择合法格式文件！");
		} else {
			$("#fnameTip").empty();
			$("#fnameTip").hide();
		}
	});

	wd.addBtn("ok", "导入", function() {
		if (validateForm()) {
			$("#jsonmap1").jqGrid("clearGridData");
			$.fileUpload({
				fileElementId : "multipartFile",
				type : "post",
				url : $.ctx + "/api/label/categoryInfo/upload",
				data : {
					"configId" : $.getCurrentConfigId
				},
				dataType : "json",
				isShowMask : true,
				maskMassage : 'Load...',
				success : function(data) {
					if (data.status == "200") {
						wd.success();
						$.success(data.msg);
						wd.cancel();
					} else {
						$("#fnameTip").empty();
						$("#fnameTip").show().append("导入数据有误,请修改后重新导入");
						var msg = data.msg.split(";");
						for(var i=0;i<msg.length;i++){
							sortNum ++ ;
							if(msg[i]!=""&&msg[i]!=null){
								var dataRow = {'datamsg' : msg[i]}
								$("#jsonmap1").jqGrid("addRowData", sortNum,dataRow, "last");
							}
						}
					}
				}
			});
		}
	});
	wd.addBtn("cancel", "取消", function() {
		wd.cancel();
	});

	$("#jsonmap1").jqGrid({
		url : "clientArray",
		datatype : "json",
		colNames : [ '错误信息' ],
		colModel : [ {
			name : 'datamsg',
			index : 'datamsg',
			align : "center",
		} ],
		autowidth : true,
		viewrecords : true,
		rownumbers : true,
		// 是否展示行号
		sortorder : "desc",
		// 排序方式
		height : '100%'
	});
}

// 表单验证
function validateForm() {
	var file = $("#multipartFile").val();
	var fileName = file.substr(file.indexOf("."));
	if (fileName == "") {
		$("#fnameTip").empty();
		$("#fnameTip").show().append("请选择文件");
		return false;
	}
	if (fileName != ".csv" && fileName != ".CSV") {
		$("#fnameTip").empty();
		$("#fnameTip").show().append("请选择合法格式文件！");
		return false;
	}
	$("#fnameTip").hide();
	return true;
}
