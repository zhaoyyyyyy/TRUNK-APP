var model = {
		labelId : "",
		labelName : "",
		busiLegend : "",
}
window.loc_onload = function() {
	var labelId = $.getUrlParam("labelId");
	var wd = frameElement.lhgDG;
	$.commAjax({
		  url: $.ctx + "/api/label/labelInfo/get",
		  postData:{labelId:labelId},
		  onSuccess: function(returnObj){
			  model.labelId = returnObj.data.labelId;
			  model.labelName = returnObj.data.labelName;
			  model.busiLegend = returnObj.data.busiLegend;
		  }
	});
	
	new Vue({
		el : '#dataD',
		data : model,
	})
	wd.addBtn("ok", "保存", function() {
		if($('#saveDataForm').validateForm()){
			$.commAjax({
				  url: $.ctx + "/api/label/labelInfo/update",
				  postData:$('#saveDataForm').formToJson(),
				  onSuccess: function(returnObj){
					  if(returnObj.data == "success"){
							$.success("修改成功", function() {
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
