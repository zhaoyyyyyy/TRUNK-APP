var model = {
		dimTableName : "",
		dimValueCol:"",
		codeColType : "",
		dimCodeCol : "",
		dimComment :""	
}
window.loc_onload = function() {
	var wd = frameElement.lhgDG;
	var dimId = $.getUrlParam("dimId");
	var configId = $.getUrlParam("configId");
	$("#dimId").val(dimId);
	if (dimId != null && dimId != "" && dimId != undefined) {
		model.dimId = dimId;
		$.commAjax({
			postData : {
				"dimId" : dimId,
				"configId": configId
			},
			url : $.ctx + '/api/dimtable/dimTableInfo/get',
			onSuccess : function(data) {
				model.dimTableName = data.data.dimTableName;
				model.dimValueCol = data.data.dimValueCol;
				model.codeColType = data.data.codeColType;
				model.dimCodeCol = data.data.dimCodeCol;
				model.dimComment = data.data.dimComment;
			}
		})
	}else{
		model.dimTableName = "";
		model.dimValueCol = "";
		model.codeColType = "";
		model.dimCodeCol = "";
		model.dimComment ="";
	}
	new Vue({
		el : '#dataD',
		data : model,
	})
	wd.addBtn("ok", "保存", function() {
		var url_ = "";
		var msss = "";
		if(model.dimId!=null && model.dimId!=undefined && model.dimId!= ""){
			url_ = $.ctx + '/api/dimtable/dimTableInfo/update';
			msss = "修改成功";
			
		}else{
			$("#dimId").removeAttr("name");;
			url_ = $.ctx + '/api/dimtable/dimTableInfo/save';
			msss = "保存成功";
		}
		var reg = /^[a-zA-Z][a-zA-Z0-9_]*$/;
		var dimTableName = $.trim($("#dimTableName").val());
		var dimValueCol = $.trim($("#dimValueCol").val());
		var codeColType = $.trim($("#codeColType").val());
		var dimCodeCol = $.trim($("#dimCodeCol").val());
		var dimComment = $.trim($("#dimComment").val());
		var result = true;
		if (dimTableName == "") {
			$.alert("表名不允许为空");
			result = false;
		} 
		if (codeColType == "") {
			$.alert("请选择主键类型");
			result = false;
		}
		var rdoValue = $("#yes").is(":checked") ? "是":"否";
		if(rdoValue == "否"){
			if(dimComment == ""){
				$.alert("请输入描述字段名",300,30);
				result = false;
			}else if(dimCodeCol == ""){
				$.alert("请输入主键字段名",300,30);
				result = false;
			}else if (dimCodeCol != "" && !reg.test(dimCodeCol)) {
				$.alert("主键字段名只能以英文字母开头,包含数字、字母、下划线",300,30);
				result = false;
			} else if (dimComment != "" && !reg.test(dimComment)) {
				$.alert("描述字段名只能以英文字母开头,包含数字、字母、下划线",300,30);
				result = false;
			}
		}else {
			dimCodeCol = "DIM_ID";
			dimComment = "DIM_VALUE";
			$("#dimCodeCol").val(dimCodeCol);
			$("#dimComment").val(dimComment);
		}
		if(result){
			var data1 =$('#saveDataForm').formToJson();
			data1.configId = configId;
			$.commAjax({
				url : url_,
				postData :data1,
				onSuccess : function(data) {
					if(data.data == "success"){
						$.success(msss, function() {
							wd.cancel();
							wd.reload();
						});
					}
				}
			});
		}
	});
	wd.addBtn("cancel", "取消", function() {
		wd.cancel();
	});
	//是否使用默认字段
	$("input[name='useDefaultCol']").on("click",function(){	
		var rdoValue = $("#yes").is(":checked") ? "是":"否";
		if(rdoValue == "否"){
			$("#dimCodeCol1").show();
			$("#dimComment1").show();			
		}else{
			$("#dimCodeCol1").hide();
			$("#dimComment1").hide();
		}
	})
}