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
		calcuElement:[],//客户群规则计算元素
		minVal:[],//客户群规则计算的最小值
		maxVal:[],//客户群规则计算的最大值
		
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
		}
	})
	$.commAjax({
		postData : {
			"customGroupId": labelId,
		},
		url : $.ctx + '/api/label/labelInfo/findCustomRuleById',
		onSuccess : function(data) {
			for(var i=0;i<data.data.length;i++){
				$.commAjax({
					postData : {
						"labelId": data.data[i].calcuElement,
					},
					url : $.ctx + '/api/label/labelInfo/get',
					onSuccess : function(data) {
						model.calcuElement = data.data.labelName;
					}
				})
				model.minVal = data.data[i].minVal;
				model.maxVal = data.data[i].maxVal;
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
		    		width: 450,
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
								//console.log($("#radioList label[class~=active]").siblings("input").val()+$("#checkboxList label[class~=active]"))
								for(var i=0;i<$("#checkboxList label[class~=active]").length;i++){
									var sysId = $("#checkboxList label[class~=active]")[i].htmlFor;
									$.commAjax({			
									    url : $.ctx+'/api/syspush/labelPushCycle/save',
									    dataType : 'json', 
									    postData : {
												"customGroupId" :labelId,
												"sysId" :sysId,
												"pushCycle" :$("#radioList label[class~=active]").siblings("input").val(),
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
}