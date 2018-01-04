var model = {
		showCpx : false,
		showHyx : false,
		showXzqh : false,
		zqlxList : [],
		hyxList : [],
		cpxList : [],
		configDesc : "",
		dataAccessType : 0,
		sourceName : "",
		sourceEnName : "",
		invalidTime : "",
		configId : "",
		orgId : ""
}
window.loc_onload = function() {
	var configId = $.getUrlParam("configId");
	var wd = frameElement.lhgDG;
	if (configId != null && configId != "" && configId != undefined) {
		model.configId = configId;
		$.commAjax({
			url : $.ctx + '/api/prefecture/preConfigInfo/get',
			postData : {
				"configId" : configId
			},
			onSuccess : function(data) {
				model.configDesc = data.data.configDesc;
				model.dataAccessType = data.data.dataAccessType;
				model.sourceName = data.data.sourceName;
				model.sourceEnName = data.data.sourceEnName;
				model.orgId = data.data.orgId;
				var time = new Date(data.data.invalidTime);
				var y = time.getFullYear();//年
				var m = time.getMonth() + 1;//月
				var d = time.getDate();//日
				model.invalidTime = y+"-"+m+"-"+d;
			}
		})
	}else{
		model.configDesc = "";
		model.dataAccessType = "";
		model.sourceName = "";
		model.sourceEnName = "";
		model.orgId = "";
		model.invalidTime = "";
	}
	new Vue({
		el : '#dataD',
		data : model,
		updated : function(){
			var value1 = $("#orgId").val();
			if(value1 == "1" || configId != ""){
				$("#type"+model.dataAccessType).click();
				if(value1 == undefined && model.orgId == ""){
					$("#orgId"+model.dataAccessType).val(1);
					model.orgId = "1";
				}else if(model.orgId != "1"){
					$("#orgId"+model.dataAccessType).val(model.orgId);
				}
			}
		}
	})
	$.commAjax({
		url : $.ctx + '/api/user/privaliegeOrg/query',
		onSuccess : function(data) {
			if(data.data != null && data.data != undefined){
				var orgobj = data.data;
				for(var i=0; i<4; i++){
					if(orgobj[i]==undefined){
						continue;
					}
					for(var f=0; f<orgobj[i].length; f++){
						var ob = orgobj[i][f];
						if(ob.parentId == "999"){
							model.zqlxList.push(ob);
						}else if(ob.orgType == "2"){
							model.hyxList.push(ob);
						}else if(ob.orgType == "1"){
							model.cpxList.push(ob);
						}
					}
				}
			}
		}
	});
	
	wd.addBtn("ok", "保存", function() {
		var url_ = "";
		var msss = "";
		if(model.configId!=null && model.configId!=undefined && model.configId!= ""){
			url_ = $.ctx + '/api/prefecture/preConfigInfo/update';
			msss = "修改成功";
		}else{
			$("#configId").removeAttr("name");;
			url_ = $.ctx + '/api/prefecture/preConfigInfo/save';
			msss = "保存成功";
		}
//		if($("#saveDataForm").validateForm){
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
			})
//		}else{
//			$.alert("表单校验失败");
//		}
	});
	
	wd.addBtn("cancel", "取消", function() {
		wd.cancel();
	});
}
function changeStatus(obj){
	if(obj.id == "type1"){
		model.showCpx = true;
		model.showHyx = false;
		model.showXzqh = false;
	}
	if(obj.id == "type2"){
		model.showCpx = false;
		model.showHyx = true;
		model.showXzqh = false;
	}
	if(obj.id == "type3"){
		model.showHyx = false;
		model.showCpx = false;
		model.showXzqh = true;
	}
	if(model.dataAccessType != obj.value){
		model.dataAccessType = obj.value;
		model.orgId = "";
	}
}
