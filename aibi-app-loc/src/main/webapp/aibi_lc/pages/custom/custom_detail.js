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
}

window.loc_onload = function() {
	$("#dialog").dialog({
	    width: 450,
	    modal: true,
	    autoOpen: false,
	    title:"推送设置",
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
    	        }
	    	}
  		],
	    open:function(){
	      	$(".form-horizontal").show();
	      	new Vue({
		      	el : '#dialog',
				data : model,
	      	});
	      	model.dataName=$.getDicData("GXZQZD");
	    }
    });
	var labelId = $.getUrlParam("labelId");
	var configId = $.getCurrentConfigId();
//	var gxzqzd=$.getDicData("GXZQZD");
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
		}
	})
	new Vue({
		el : '#dataD',
		data : model,
		methods:{
			showDialog:function(){
				$("#dialog").dialog({
		    		autoOpen: true,
		    	});
		    	$.commAjax({
		    		url: $.ctx + "/api/syspush/sysInfo/queryList",
		    		onSuccess:function(data){
		    			model.sysName=data.data;
//		    			for(var i=0;i<data.data.length;i++){
//		    				var html="<li class='checkbox fleft'>"+
//					    		"<input type='checkbox' id='"+data.data[i].sysId+"' class='checkbix'>"+
//					    		"<label for='"+data.data[i].sysId+"' aria-label role='checkbox' class='checkbix' data-id='"+data.data[i].sysId+"'>"+
//					    		"<span class='large'></span>"+
//					    		data.data[i].sysName+
//					    		"</label>"+
//					    		"</li>";
//					    	$("#checkboxList").append(html);
//			    		}
//		    			for(var i=0;i<gxzqzd.length;i++){
//		    				var html="<li class='radio circle success'>"+
//				    		"<input name='radio19' type='radio' id='"+gxzqzd[i].id+"' >"+
//				    		"<label for='"+gxzqzd[i].id+"'   data-id='"+gxzqzd[i].id+"'>"+
//				    		"<i class='default'></i>"+
//				    		gxzqzd[i].dataName+
//				    		"</label>"+
//				    		"</li>";
//		    				$("#radioList").append(html);
//		    			}
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
			    	$.fn.zTree.init($("#basicInformation"), install, ztreeObj);
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
				},
		    onSuccess: function(data){
		    	$("#OptionalLabel").html("");
		    	for(var i=0;i<data.data.length;i++){
		    		var html="<li><a>"+data.data[i].labelName+"</a></li>";
		    		$("#OptionalLabel").append(html);
		    	}
		    },
		    maskMassage : '搜索中...'
	   });
	};
	
//	$("#radioList .radio").each(function(){
//		$(this).find("input").click(function(){
//			$(this).siblings("label").addClass("active");
//			$(this).parent(".radio").siblings(".radio").find("label").removeClass("active");
//			$(this).parent(".radio").siblings(".radio").find("input").prop("checked", false);
//			$(this).prop("checked", true);
//		})
//	});
	$("#radioList").delegate(".radio input","click",function(){
		$(this).siblings("label").addClass("active");
		$(this).parent(".radio").siblings(".radio").find("label").removeClass("active");
		$(this).parent(".radio").siblings(".radio").find("input").prop("checked", false);
		$(this).prop("checked", true);
	})
	$("#checkboxList").delegate("input","click",function(){
		if($(this).siblings("label").hasClass("active")){
			$(this).siblings("label").removeClass("active");
			$(this).prop("checked", false);
		}else{
			$(this).siblings("label").addClass("active");
			$(this).prop("checked", true);
		}
	})
	$("#add-dialog-btn").click(function(){
		for(var i=0;i<$("#checkboxList label[class~=active]").length;i++){
			var sysId = $("#checkboxList label[class~=active]")[i].htmlFor;
//			$.commAjax({			
//			    url : $.ctx+'/api/syspush/labelPushCycle/save',
//			    dataType : 'json', 
//			    async:true,
//			    postData : {
//						"customGroupId" :labelId,
//						"sysId" :sysId,
//					},
//			    onSuccess: function(data){
//			    	$.alert("推送完成");
//			    },
//			    maskMassage : '推送中...'
//		   });
		}
		console.log($("#radioList label[class~=active]").text(),$("#checkboxList label[class~=active]").text())
	})
}