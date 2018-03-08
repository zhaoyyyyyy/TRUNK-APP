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
	
	$.commAjax({
		url: $.ctx + '/api/back/allUserMsg/queryList',
		onSuccess : function(data){
			model.allUserMsg = data.data;
		}
	})
	
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
				model.dataAccessType0 = data.data.dataAccessType;
				model.sourceName = data.data.sourceName;
				model.sourceEnName = data.data.sourceEnName;
				model.orgId = data.data.orgId;
//				var time = new Date(data.data.invalidTime);
//				var y = time.getFullYear();//年
//				var m = (time.getMonth()+1<10 ? '0'+(time.getMonth()+1):time.getMonth()+1);//月
//				var d = (time.getDate()+1<10 ? '0' +(time.getDate()):time.getDate());//日	
//				$("#invalidTime").val(y+"-"+m+"-"+d);
				model.priKey = data.data.allUserMsg.priKey;
			}
		})
	}else{
		model.configDesc = "";
		model.dataAccessType = "";
		model.sourceName = "";
		model.sourceEnName = "";
		model.orgId = "";
		model.priKey = "";
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
	$.commAjax({
		url : $.ctx + '/api/user/get',
		onSuccess : function(data) {
			if(data.data.orgPrivaliege != null && data.data.orgPrivaliege != undefined){
				var orgobj = data.data.orgPrivaliege;
				for(var i=0; i<4; i++){
					if(orgobj[i]==undefined){
						continue;
					}
					for(var f=0; f<orgobj[i].length; f++){
						var ob = orgobj[i][f];
						orgdata[ob.orgCode]=ob.simpleName;
						if(ob.parentId == "999"){
							model.zqlxList.push(ob);
						}else if(ob.orgType == "2" && ob.parentId == "3"){
							model.hyxList.push(ob);
						}else if(ob.orgType == "1" && ob.parentId == "2"){
							model.cpxList.push(ob);
						}
					}
				}
			}
		}
	});
	wd.addBtn("ok", "保存", function() {
		if($('#saveDataForm').validateForm()){
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
			$.commAjax({
				url : url_,
				isShowMask : true,
				maskMassage : 'Load...',
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
	
		}
	});
	
	wd.addBtn("cancel", "取消", function() {
		wd.cancel();
	});
}
function changeStatus(obj){
	model.showSelect = true;
	if(obj.id == "type1"){
		model.selectName = "业务线";
		$("#org").val("");
	}
	if(obj.id == "type2"){
		model.selectName = "行业线";
		$("#org").val("");
	}
	if(obj.id == "type3"){
		model.selectName = "";
		$("#org").val("");
	}
	if(model.dataAccessType != obj.value){
		model.dataAccessType = obj.value;
		model.orgId = "allnull";
	}
	$(".ui-form-ztree").removeClass("open");
}
function openTtee(tag){
	var install = {
			view: {
				selectedMulti: false,
			},
			data: {
				selectedMulti: false,			
				simpleData: {  
	                enable: true,   //设置是否使用简单数据模式(Array)  	                    
	            },  
	            key: {             	
	            	idKey: "id",    //设置节点唯一标识属性名称  
	                pIdKey: "parentId" ,     //设置父节点唯一标识属性名称  
	                name:"simpleName",//zTree 节点数据保存节点名称的属性名称  
	                title: "simpleName"//zTree 节点数据保存节点提示信息的属性名称        
	            }  
			},
			callback: {
				onClick: zTreeOnClick
			}
		}
	
	model.tagNode=tag;
	var e = document.all ? window.event : arguments[0] ? arguments[0] : event;
	e.stopPropagation?e.stopPropagation():e.cancelBubble=true;
	
	if($(tag).parent(".ui-form-ztree").hasClass("open")){
		$(tag).parent(".ui-form-ztree").removeClass("open")
	}else{
		$(tag).parent(".ui-form-ztree").addClass("open");
		$(tag).parent(".ui-form-ztree").find(".dropdown-menu").css("width",100+"%");
		if(model.dataAccessType == "2"){
			model.ztreeObj=model.hyxList;
		}else if(model.dataAccessType == "1"){
			model.ztreeObj=model.cpxList;
		}
		var obj = model.ztreeObj;
		for(var sort=0;sort<obj.length;sort++){
			obj[sort].children = obj[sort].childList;
		}
    	$.fn.zTree.init($("#pretree"), install, model.ztreeObj);
	}
	
	//展示选中分类下的标签
	function zTreeOnClick(event, treeId, treeNode) {
		model.nodeName=treeNode;
		model.orgId=treeNode.orgCode;
		$("#orgId").val(treeNode.orgCode);
		$(model.tagNode).val(treeNode.simpleName);
		$(".ui-form-ztree").removeClass("open");
	};	
}
function changeSelect(){
	model.selectNum = 1;
}
