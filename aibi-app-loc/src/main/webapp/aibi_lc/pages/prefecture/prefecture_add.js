var model = {
		showCpx : false,
		showHyx : false,
		showXzqh : false,
		zqlxList : [],
		hyxList : [],
		cpxList : [],
		xzqhList : [],
		configDesc : "",
		contractName : "",
		dataAccessType : 0,
		sourceName : "",
		sourceEnName : "",
		invalidTime : "",
		configId : "",
		num : 0
}
window.loc_onload = function() {
	var configId = $.getUrlParam("configId");
	if (configId != null && configId != "" && configId != undefined) {
		model.configId = configId;
		$.commAjax({
			url : $.ctx + '/api/prefecture/preConfigInfo/get',
			postData : {
				"configId" : configId
			},
			onSuccess : function(data) {
				model.configDesc = data.data.configDesc;
				model.contractName = data.data.contractName;
				model.dataAccessType = data.data.dataAccessType;
				model.sourceName = data.data.sourceName;
				model.sourceEnName = data.data.sourceEnName;
				var time = new Date(data.data.invalidTime);
				var y = time.getFullYear();//年
				var m = time.getMonth() + 1;//月
				var d = time.getDate();//日
				model.invalidTime = y+"-"+m+"-"+d;
			}
		})
	}else{
		model.configDesc = "";
		model.contractName = "";
		model.dataAccessType = "";
		model.sourceName = "";
		model.sourceEnName = "";
		model.invalidTime = "";
	}
	new Vue({
		el : '#dataD',
		data : model,
		watch : {
			zqlxList : function(){
				this.$nextTick(function(){
					$("#type"+model.dataAccessType).click();
					var val = $('input:radio[name="dataAccessType"]:checked').val();
					if(val != null){
						if(configId != null && configId != "" && configId != undefined && model.contractName != ""){
							$("#contractName").val(model.contractName);
						}
					}
				})
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
			$.commAjax({
				url : $.ctx + '/api/user/privaliegeData/query',
				onSuccess : function(data1) {
					if(data1.data != null && data1.data != undefined){
						var dataobj = data1.data;
						for(var e=0 ; e<4 ; e++){
							if(dataobj[e]==undefined){
								continue;
							}
							for(var l=0 ; l<dataobj[e].length ; l++){
								var od = dataobj[e][l];
								if(od.parentId == "999"){
									model.zqlxList.push(od);
								}else if(od.orgType == "3"){
									model.xzqhList.push(od);
								}
							}
						}
					}
				}
			})
		}
	});
}
function changeStatus(obj){
	if(obj.id == "type1"){
		model.dataAccessType = obj.value;
		model.showCpx = true;
		model.showHyx = false;
		model.showXzqh = false;
	}
	if(obj.id == "type2"){
		model.dataAccessType = obj.value;
		model.showCpx = false;
		model.showHyx = true;
		model.showXzqh = false;
	}
	if(obj.id == "type3"){
		model.dataAccessType = obj.value;
		model.showHyx = false;
		model.showCpx = false;
		model.showXzqh = true;
	}
}
function fun_to_save(){
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
//	if($("#saveDataForm").validateForm){
		$.commAjax({
			url : url_,
			postData : $('#saveDataForm').formToJson(),
			onSuccess : function(data) {
				if(data.data == "success"){
					$.success(msss, function() {
						window.location='prefecture_mgr.html';
					});
				}
				
			}
		})
//	}else{
//		$.alert("表单校验失败");
//	}
	
}