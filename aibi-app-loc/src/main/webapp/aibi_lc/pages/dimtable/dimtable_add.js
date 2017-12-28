var model = {
		dimTableName : "",
		codeColType : "",
		dimCodeCol : "",
		dimValueCol :""	
}
window.loc_onload = function() {
	var wd = frameElement.lhgDG;
	var dimId = $.getUrlParam("dimId");
	$("#dimId").val(dimId);
	if (dimId != null && dimId != "" && dimId != undefined) {
		model.dimId = dimId;
		$.commAjax({
			postData : {
				"dimId" : dimId
			},
			url : $.ctx + '/api/dimtable/dimTableInfo/get',
			onSuccess : function(data) {
				model.dimTableName = data.data.dimTableName;
				model.codeColType = data.data.codeColType;
				model.dimCodeCol = data.data.dimCodeCol;
				model.dimValueCol = data.data.dimValueCol;
			}
		})
	}else{
		model.dimTableName = "";
		model.codeColType = "";
		model.dimCodeCol = "";
		model.dimValueCol ="";
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
		var codeColType = $.trim($("#codeColType").val());
		var dimCodeCol = $.trim($("#dimCodeCol").val());
		var dimValueCol = $.trim($("#dimValueCol").val());
		if (dimTableName == "") {
			$.alert("表名不允许为空");
		} else if (codeColType == "请选择主键类型") {
			$.alert("请选择主键类型");
		} else if(dimCodeCol != "" && dimValueCol == ""){$.alert("请输入描述字段名",300,30);}
		else if(dimCodeCol == "" && dimValueCol != ""){$.alert("请输入主键字段名",300,30);}
		else if (dimCodeCol != "" && !reg.test(dimCodeCol)) {
			$.alert("主键字段名只能以英文字母开头,包含数字、字母、下划线",300,30);
		} else if (dimValueCol != "" && !reg.test(dimValueCol)) {
			$.alert("描述字段名只能以英文字母开头,包含数字、字母、下划线",300,30);
		} else {
			$.commAjax({
				url : url_,
				postData : $('#saveDataForm').formToJson(),
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
}