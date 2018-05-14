var model = {
		showCpx : false,
		showHyx : false,
		showXzqh : false,
		showSelect : false,
		selectName : "",
		zqlxList : [],
		hyxList : [],
		cpxList : [],
		configDesc : "",
		dataAccessType : 0,
		dataAccessType0 : 0,
		sourceName : "",
		sourceEnName : "",
//		invalidTime : "",
		configId : "",
		orgId : "",
		ztreeObj : [],
		allUserMsg : [],
		priKey:"",
		selectNum:0,
		click:0
}
window.loc_onload = function() {
	var orgdata = [];
	orgdata["allnull"] = "";
	var configId = $.getUrlParam("configId");
	var wd = frameElement.lhgDG;
	
	
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
		},
		updated : function(){
			if(configId != null && configId != "" && configId != undefined){
				$("#type"+model.dataAccessType).click();
				$("#org").val(orgdata[model.orgId]);
				if(model.selectNum==0){
					$("#priKey").val(model.priKey);
				}
			}else{
				var orgValue = $("#org").val();
				this.$nextTick(function(){
					if($("#type1")&&model.click!=1&&(orgValue==""||orgValue==null||orgValue==undefined)){
						$("#type1").click();
						model.click=1;
					}else if($("#type2")&&model.click!=1&&(orgValue==""||orgValue==null||orgValue==undefined)){
						$("#type2").click();
						model.click=1;
					}else if($("#type3")&&model.click!=1&&(orgValue==""||orgValue==null||orgValue==undefined)){
						$("#type3").click();
						model.click=1;
					}
				})
				
			}
				
		}
	})
	
	wd.addBtn("ok", "保存", function() {
		if($('#saveDataForm').validateForm()){
			var url_ = $.ctx+'/api/message/locSysAnnouncement/save';
			var msss = "新增成功";
			$.commAjax({
				url : url_,
				postData : $('#saveDataForm').formToJson(),
				onSuccess : function(data) {
					if(data.data == "success"){
						$("#jsonmap1").trigger("reloadGrid", [ {
							page : 1
						} ]);
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
}
