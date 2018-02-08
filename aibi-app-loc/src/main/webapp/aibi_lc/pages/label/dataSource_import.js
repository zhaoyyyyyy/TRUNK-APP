window.loc_onload = function() {
	var wd = frameElement.lhgDG;
	
	//统一浏览器对FILE 文件获取上传路径的差异 
	$("#multipartFile").change(function(){
	  	var file = $(this).val();
		var fileName = file.substr(file.lastIndexOf("."));   
		fileName = fileName.toUpperCase();
		if(fileName != ".csv" && fileName != ".CSV"){
	    	$("#fnameTip").empty();
			$("#fnameTip").show().append("请选择合法格式文件！");
		}else{
			$("#fnameTip").empty();
			$("#fnameTip").hide();
		}
	});
	
	wd.addBtn("ok", "导入", function() {
		if(validateForm()){
			$.fileUpload({
				fileElementId: "multipartFile",
				type: "post",
		        url:$.ctx + "/api/source/sourceTableInfo/upload",
		        dataType:"json",
		        isShowMask : true,
				maskMassage : 'Load...',
		        success: function(data){
		        	if(data.status == "200"){
						wd.addSourceList(data.data);
						$.success(data.msg);
						wd.cancel();
					}else{
						$.alert(data.msg);
					}
		        } 
			});	
		}
	});
	wd.addBtn("cancel", "取消", function() {
		wd.cancel();
	});
}

//表单验证
function validateForm(){
	var file = $("#multipartFile").val();
	var fileName = file.substr(file.indexOf("."));   
	if(fileName == ""){
		$("#fnameTip").empty();
		$("#fnameTip").show().append("请选择文件");
		return false;
	}
	if(fileName != ".csv" && fileName != ".CSV"){
    	$("#fnameTip").empty();
		$("#fnameTip").show().append("请选择合法格式文件！");
		return false;
    }
    $("#fnameTip").hide();
    return true;
}
