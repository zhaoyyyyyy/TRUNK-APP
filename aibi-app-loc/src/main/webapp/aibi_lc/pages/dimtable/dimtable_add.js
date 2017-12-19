var model = {
		dimTablename : "",
		dimCodeColType : "",
		dimCodeCol : "",
		dimValueCol :""	
}
window.loc_onload = function() {
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
				model.dimTablename = data.data.dimTablename;
				model.dimCodeColType = data.data.dimCodeColType;
				model.dimCodeCol = data.data.dimCodeCol;
				model.dimValueCol = data.data.dimValueCol;
			}
		})
	}else{
		model.dimTablename = "";
		model.dimCodeColType = "";
		model.dimCodeCol = "";
		model.dimValueCol ="";
	}
}

function fun_to_save() {
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
	var dimTablename = $.trim($("#dimTablename").val());
	var dimCodeColType = $.trim($("#dimCodeColType").val());
	var dimCodeCol = $.trim($("#dimCodeCol").val());
	var dimValueCol = $.trim($("#dimValueCol").val());
	if (dimTablename == "") {
		$.alert("表名不允许为空");
	} else if (dimCodeColType == "请选择主键类型") {
		$.alert("主键类型不允许为空");
	} else if (dimCodeCol != "" && !reg.test(dimCodeCol)) {
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
						history.back(-1);
					});
				}
			}
		});
	}
}