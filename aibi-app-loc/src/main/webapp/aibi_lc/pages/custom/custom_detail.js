var model = {
		labelName : "",
		updateCycle:"",
		orgId : "",
		tacticsId : "",
		busiLegend:"",	
		configId : "",
		dataDate:"",
		dayLabelDate : "",
		monthLabelDate : "",
		dataName:"",//推送周期
		sysName:"",//推送系统
		curentIndex:false,//radio选中
		isActive:false,//check选中
		customRule:"",//客户群规则
		haveAttr:false,
		customNum:"",//客户群人数
		calcuElementName:[],//已选属性名称
		AttrbuteId : "",//推送的属性标签ID
		
}
window.loc_onload = function() {
	$("#dialog").dialog({
	    autoOpen: false,
	    title:"推送设置",
	    modal: true,
	    open:function(){
	      	$(".form-horizontal").show();
	      	new Vue({
		      	el : '#dialog',
				data : model,
				methods:{
					radioSelect:function(index){
						model.curentIndex=index;
					},
					checkSelect:function(item){
						if(typeof item.isActive=='undefined'){
							this.$set(item,"isActive",true)
						}else if(typeof item.isActive!='undefined'){
							item.isActive=!item.isActive;
						}
					}
				}
	      	});
	      	model.dataName=$.getDicData("GXZQZD");
	    }
    });
	var labelId = $.getUrlParam("labelId");
	var configId = $.getCurrentConfigId();
	model.configId = configId;
	$.commAjax({
		postData : {
			"configId": configId,
			"labelId": labelId,
		},
		url : $.ctx + '/api/label/labelInfo/get',
		onSuccess : function(data) {
			model.labelName = data.data.labelName;
			model.updateCycle = data.data.updateCycle;
			model.orgId = data.data.orgId;
			model.tacticsId = data.data.tacticsId;
			model.busiLegend = data.data.busiLegend;
			model.dataDate = data.data.dataDate;
			model.dayLabelDate = data.data.dayLabelDate;
			model.monthLabelDate = data.data.monthLabelDate;
			model.customNum = data.data.labelExtInfo.customNum;
		}
	})
	$.commAjax({
		postData : {
			"customGroupId": labelId,
		},
		url : $.ctx + '/api/label/labelInfo/findCustomRuleById',
		onSuccess : function(data) {
			model.customRule=data.data;
			for (var i=0;i<model.customRule.length;i++){
				var html="";
        		if(model.customRule[i].elementType ==2){
        			if(model.AttrbuteId==""){
	        			model.AttrbuteId = model.customRule[i].calcuElement;
	        		}else{
	        			model.AttrbuteId +=","+model.customRule[i].calcuElement;
	        		}
        			html="<li>"+
		    		"<div class='checkbox'>"+
		    		"<input type='checkbox' id='"+model.customRule[i].calcuElement+"R' class='checkbix'>"+
		    		"<label for='"+model.customRule[i].calcuElement+"R' aria-label role='checkbox' class='checkbix' data-id='"+model.customRule[i].calcuElement+"R' data-name='"+model.customRule[i].attrName+"'>"+
		    		"<span class='large'></span>"+
		    		model.customRule[i].attrName+
		    		"</label>"+
		    		"</div>"+
		    		"</li>";
        			$("#selectedLabel").append(html);
        			if(model.customRule[i].labelTypeId ==4 &&model.customRule[i].contiueMinVal!="" &&model.customRule[i].contiueMaxVal!=""&&model.customRule[i].contiueMinVal!=null &&model.customRule[i].contiueMaxVal!=null){
        				html = "<div class='ui-custom-item clearfix clearfix' ><a><span> "+model.customRule[i].attrName+"</span></a><a><span>"+model.customRule[i].leftZoneSign+model.customRule[i].contiueMinVal+"且"+model.customRule[i].rightZoneSign+model.customRule[i].contiueMaxVal+"</span></div>"
        				$("#labelDetail").append(html);
        			}
        			if(model.customRule[i].labelTypeId ==4 &&model.customRule[i].contiueMinVal==null &&model.customRule[i].contiueMaxVal!=null){
        				html = "<div class='ui-custom-item clearfix clearfix' ><a><span> "+model.customRule[i].attrName+"</span></a><a><span>"+model.customRule[i].rightZoneSign+model.customRule[i].contiueMaxVal+"</span></div>"
        				$("#labelDetail").append(html);
        			}
        			if(model.customRule[i].labelTypeId ==4 &&model.customRule[i].contiueMinVal!=null &&model.customRule[i].contiueMaxVal==null){
        				html = "<div class='ui-custom-item clearfix clearfix' ><a><span> "+model.customRule[i].attrName+"</span></a><a><span>"+model.customRule[i].leftZoneSign+model.customRule[i].contiueMinVal+"</span></div>"
        				$("#labelDetail").append(html);
        			}
        			if(model.customRule[i].labelTypeId ==4 &&model.customRule[i].exactValue!= ""){
        				html = "<div class='ui-custom-item clearfix clearfix' ><a><span> "+model.customRule[i].attrName+"</span></a><a><span>精确值:"+model.customRule[i].exactValue+"</span></div>"
            			$("#labelDetail").append(html);
        			}
        			if(model.customRule[i].labelTypeId== 1){//01型
        				if(model.customRule[i].labelFlag != 1){
        					html = "<div class='ui-custom-item clearfix clearfix' ><a><span> "+model.customRule[i].attrName+"</span></a><a><span>"+"否"+"</span></a></div>"
            				$("#labelDetail").append(html);
        				}else{
        					html = "<div class='ui-custom-item clearfix clearfix' ><a><span> "+model.customRule[i].attrName+"</span></a><a><span>"+"是"+"</span></a></div>"
            				$("#labelDetail").append(html);
        				}
        			}
        			if(model.customRule[i].labelTypeId== 5||model.customRule[i].labelTypeId== 9||model.customRule[i].labelTypeId== 12){//枚举
        				if(model.customRule[i].attrVal){
        					html = "<div class='ui-custom-item clearfix clearfix' ><a><span> "+model.customRule[i].attrName+"</span></a></div>"
            				$("#labelDetail").append(html);
        				}
        			}
        		}
			}
		}
	})
	new Vue({
		el : '#dataD',
		data : model,
		methods:{
			showDialog:function(){
				$("#dialog").dialog({
		    		autoOpen: true,
		    		width: 550,
		    		buttons: [
			    	    {
			    	       text: "取消",
			    	       "class":"ui-btn ui-btn-second",
			    	        click: function() {
			    	        	$( this ).dialog( "close" );
			    	     	}
				  	    },
				  	    {
			    	        text: "确定",
			    	        "id":"add-dialog-btn",
			    	        "class":"ui-btn ui-btn-default",
			    	        click: function() {
			    	        	$( this ).dialog( "close" );
			    	        	if(model.AttrbuteId ==""){
			    	        		$.alert("请选择推送的属性");
			    	        	}else{
									//console.log($("#radioList label[class~=active]").siblings("input").val()+$("#checkboxList label[class~=active]"))
									for(var i=0;i<$("#checkboxList label[class~=active]").length;i++){
										var sysId = $("#checkboxList label[class~=active]")[i].htmlFor;
										$.commAjax({			
										    url : $.ctx+'/api/syspush/labelPushCycle/save',
										    dataType : 'json', 
										    async : false,
										    postData : {
													"customGroupId" :labelId,
													"sysId" :sysId,
													"pushCycle" :$("#radioList label[class~=active]").siblings("input").val(),
													"AttrbuteId" :model.AttrbuteId,
												},
										    maskMassage : '推送中...'
									   });
										if(i == $("#checkboxList label[class~=active]").length-1){
											$.success("推送成功",function(){
												//history.back(-1);
											});
										}
									}
			    	        	}
			    	        }
				    	}
			  		]
		    	});
		    	$.commAjax({
		    		url: $.ctx + "/api/syspush/sysInfo/queryList",
		    		onSuccess:function(data){
		    			model.sysName=data.data;
		    		}
		    	})
			}
		},
		mounted: function () {
		    this.$nextTick(function () {
			    var r = $(".easyui-validatebox");
	   			if (r.length){
	   				r.validatebox();
	   			}
		    })
		}
	})
	//标签体系
	labeltree();
//	function custom_left(){
//    	console.log(1);
//    }
	function labeltree(){
		var ztreeObj;
		$.commAjax({			
		    url : $.ctx+'/api/label/categoryInfo/queryList',  		    
		    dataType : 'json', 
		    async:true,
		    postData : {
					"sysId" :configId,
				},
		    onSuccess: function(data){ 		    			    			    	
			    	var ztreeObj=data.data;
			    	$.fn.zTree.init($("#ztree"), install, ztreeObj);
		    	}  
	   });
		install = {
			view: {
				selectedMulti: false,
			},
			data: {
				selectedMulti: false,			
				simpleData: {  
	                enable: true,   //设置是否使用简单数据模式(Array)  	                    
	            },  
	            key: {             	
	            	idKey: "sysId",    //设置节点唯一标识属性名称  
	                pIdKey: "parentId" ,     //设置父节点唯一标识属性名称  
	                name:'categoryName',//zTree 节点数据保存节点名称的属性名称  
	                title: "categoryName"//zTree 节点数据保存节点提示信息的属性名称        
	            }  
			},
			callback: {
				onClick: dataClick
			}
		}
	}
	function dataClick(event, treeId, treeNode){
		$.commAjax({			
		    url : $.ctx+'/api/label/labelInfo/queryListEffective', 
		    dataType : 'json', 
		    async:true,
		    postData : {
					"configId" :configId,
					"categoryId" :treeNode.categoryId,
					"groupType" :0,
				},
		    onSuccess: function(data){
		    	$("#OptionalLabel").html("");
		    	for(var i=0;i<data.data.length;i++){
		    		html="<li>"+
		    		"<div class='checkbox'>"+
		    		"<input type='checkbox' id='"+data.data[i].labelId+"L' class='checkbix'>"+
		    		"<label for='"+data.data[i].labelId+"L' aria-label role='checkbox' class='checkbix' data-id='"+data.data[i].labelId+"L' data-name='"+data.data[i].labelName+"'>"+
		    		"<span class='large'></span>"+
		    		data.data[i].labelName+
		    		"</label>"+
		    		"</div>"+
		    		"</li>";
		    		$("#OptionalLabel").append(html);
		    	}
		    },
		    maskMassage : '搜索中...'
	   });
	};
    $("#btn_search").click(function() {
    	var txt = $("#labelName").val();
    	console.log(txt);
    	$.commAjax({			
		    url : $.ctx+'/api/label/labelInfo/queryListEffective', 
		    dataType : 'json', 
		    async:true,
		    postData : {
		    		"labelName":txt,
					"configId" :configId,
					//"categoryId" :treeNode.categoryId,
					"groupType" :0,
				},
		    onSuccess: function(data){
		    	$("#OptionalLabel").html("");
		    	for(var i=0;i<data.data.length;i++){
		    		html="<li>"+
		    		"<div class='checkbox'>"+
		    		"<input type='checkbox' id='"+data.data[i].labelId+"L' class='checkbix'>"+
		    		"<label for='"+data.data[i].labelId+"L' aria-label role='checkbox' class='checkbix' data-id='"+data.data[i].labelId+"L' data-name='"+data.data[i].labelName+"'>"+
		    		"<span class='large'></span>"+
		    		data.data[i].labelName+
		    		"</label>"+
		    		"</div>"+
		    		"</li>";
		    		$("#OptionalLabel").append(html);
		    	}
		    },
		    maskMassage : '搜索中...'
	   });
    });
    $("#selectedLabel").delegate("input","click",function(){//右边
		if($(this).siblings("label").hasClass("active")){
			$(this).siblings("label").removeClass("active");
			$(this).prop("checked", false);
		}else{
			$(this).siblings("label").addClass("active");
			$(this).prop("checked", true);
		}
	})
    $("#custom_left").click(function(){
    	var attrId ="",attrName="";
		$("#selectedLabel label[class~=active]").each(function(){
			attrId = $(this).attr('data-id').substring(0,$(this).attr('data-id').length-1);
			attrName = $(this).attr('data-name');
			if(model.AttrbuteId.length>10){
				model.AttrbuteId = model.AttrbuteId.replace(","+attrId,"")
			}else{
				model.AttrbuteId = model.AttrbuteId.replace(attrId,"")
			}
			var  html="<li>"+
    		"<div class='checkbox'>"+
    		"<input type='checkbox' id='"+attrId+"L' class='checkbix'>"+
    		"<label for='"+attrId+"L' aria-label role='checkbox' class='checkbix' data-id='"+attrId+"L' data-name='"+attrName+"'>"+
    		"<span class='large'></span>"+
    		attrName+
    		"</label>"+
    		"</div>"+
    		"</li>";
			$("#OptionalLabel").append(html);
			$("#selectedLabel label[class~=active]").parents("li").remove();
		})
		if(attrId== ""){
			$.alert("请选择标签");
		}
    })
    $("#OptionalLabel").delegate("input","click",function(){//左边
		if($(this).siblings("label").hasClass("active")){
			$(this).siblings("label").removeClass("active");
			$(this).prop("checked", false);
		}else{
			$(this).siblings("label").addClass("active");
			$(this).prop("checked", true);
		}
	})
    $("#custom_right").click(function(){
    	var attrId ="",attrName="";
		$("#OptionalLabel label[class~=active]").each(function(){
			attrId = $(this).attr('data-id').substring(0,$(this).attr('data-id').length-1);
			attrName = $(this).attr('data-name');
			if(model.AttrbuteId!=""){
				model.AttrbuteId +=","+attrId;
			}else{
				model.AttrbuteId +=attrId;
			}
			var  html="<li>"+
    		"<div class='checkbox'>"+
    		"<input type='checkbox' id='"+attrId+"R' class='checkbix'>"+
    		"<label for='"+attrId+"R' aria-label role='checkbox' class='checkbix' data-id='"+attrId+"R' data-name='"+attrName+"'>"+
    		"<span class='large'></span>"+
    		attrName+
    		"</label>"+
    		"</div>"+
    		"</li>";
			$("#selectedLabel").append(html);
			$("#OptionalLabel label[class~=active]").parents("li").remove();
		})
		if(attrId== ""){
			$.alert("请选择标签");
		}
    })
}