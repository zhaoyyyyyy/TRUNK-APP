var model = {
		dimTableName : "",
		dimComment:"",
		codeColType : "",
		dimCodeCol : "",
		dimValueCol:"",
		dimId:""
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
				console.log(data.data);
				model.dimTableName = data.data.dimTableName;
				model.dimComment = data.data.dimComment;
				model.codeColType = data.data.codeColType;
				model.dimCodeCol = data.data.dimCodeCol;
				model.dimValueCol = data.data.dimValueCol;
				if(data.data.dimCodeCol!=("DIM_CODE") && data.data.dimValueCol!=("DIM_VALUE")){
					var useDefaultCol = document.getElementById("no");
					useDefaultCol.checked = true;
					$("#dimCodeCol1").show();
					$("#dimValueCol1").show();			
				}else{
					$("#dimCodeCol1").hide();
					$("#dimValueCol1").hide();
				}
			}
		})
	}else{
		model.dimTableName = "";
		model.dimComment = "";
		model.codeColType = "";
		model.dimCodeCol = "";
		model.dimValueCol ="";
	}
	new Vue({
		el : '#dataD',
		data : model,
		mounted: function () {
		    this.$nextTick(function () {
			    var r = $(".easyui-validatebox");
	   			if (r.length){
	   				r.validatebox();
	   			}
		    })
		}
	})
	wd.addBtn("ok", "保存", function() {
		if($('#saveDataForm').validateForm()){
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
			var dimComment = $.trim($("#dimComment").val());
			var codeColType = $.trim($("#codeColType").val());
			var dimCodeCol = $.trim($("#dimCodeCol").val());
			var dimValueCol = $.trim($("#dimValueCol").val());
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
				if(dimValueCol == ""){
					$.alert("请输入描述字段名",300,30);
					result = false;
				}else if(dimCodeCol == ""){
					$.alert("请输入主键字段名",300,30);
					result = false;
				}else if (dimCodeCol != "" && !reg.test(dimCodeCol)) {
					$.alert("主键字段名只能以英文字母开头,包含数字、字母、下划线",300,30);
					result = false;
				} else if (dimValueCol != "" && !reg.test(dimValueCol)) {
					$.alert("描述字段名只能以英文字母开头,包含数字、字母、下划线",300,30);
					result = false;
				}
			}else {
				dimCodeCol = "DIM_CODE";
				dimValueCol = "DIM_VALUE";
				$("#dimCodeCol").val(dimCodeCol);
				$("#dimValueCol").val(dimValueCol);
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
			$("#dimValueCol1").show();			
		}else{
			$("#dimCodeCol1").hide();
			$("#dimValueCol1").hide();
		}
	})
}