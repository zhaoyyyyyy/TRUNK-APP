window.loc_onload = function() {
	var labelId = $.getCurrentConfigId(); //专区ID
	ztreeFunc();
	labeltree();
	//移动标签时所选中的节点ID
	var transToData;
	//移动标签时所选中的标签ID数组
	var transData = new Array();
	var leftTreeCagyId;  //左边树选中的分类ID
	$("#labelLength").html(0);
	$("#dialog").dialog({
	      height:164,
	      width: 300,
	      modal: true,
	      autoOpen: false,
	      open:function(){
	      	ztreeFunc();
	      	$(".ui-form-group ").show();
	      }
  });
	//左边树
	function ztreeFunc(){
		var ztreeObj;
		$.commAjax({			
		    url : $.ctx+'/api/label/categoryInfo/queryList',
		    isShowMask : true,
		    dataType : 'json', 
		    async:true,
		    postData : {
					"sysId" :labelId,
				},
		    onSuccess: function(data){
		    	var ztreeObj = [{"categoryId":null,"sysId":null,"sysType":null,"categoryDesc":null,"categoryName":"根","parentId":null,"categoryPath":null,"isLeaf":null,"statusId":null,"sortNum":null,"levelId":null,"children":[]}]
		    	if(data.data.length != 0){
		    		ztreeObj[0].children = data.data;
		    	}
		    	$.fn.zTree.init($("#ztree"), setting, ztreeObj);
		    },
		    maskMassage : 'Load...'
	   });
		setting = {  
			view: {
				selectedMulti: false,
				addHoverDom: addHoverDom,
				removeHoverDom: removeHoverDom,
			},
//			async: {
//				enable: true,
//				url: $.ctx+'/api/label/categoryInfo/queryList',
//				autoParam: ["id"]
//			},
			data: {
				selectedMulti: false,			
				simpleData: {  
	                enable: true,   //设置是否使用简单数据模式(Array)  	                    
	            },  	
	            key: {             	
	            	idKey: "categoryId",    //设置节点唯一标识属性名称  
	                pIdKey: "parentId" ,     //设置父节点唯一标识属性名称  
	                name:'categoryName',//zTree 节点数据保存节点名称的属性名称  
	                title: "categoryName"//zTree 节点数据保存节点提示信息的属性名称        
	            }  
			},
			callback: {
				onClick: zTreeOnClick,
//				onAsyncSuccess: zTreeOnAsyncSuccess
			}
		}	
//		$.fn.zTree.init($("#ztree"), setting, ztreeObj);
//		function zTreeOnAsyncSuccess(event, treeId, treeNode, msg){
//			console.log(msg);
//		}
		var newCount = 1;				
		function addHoverDom(treeId, treeNode) {
			var sObj = $("#" + treeNode.tId + "_span");
	        if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0)
	            return;
	        var addStr = "<div class='button add label-handles' id='handle_" + treeNode.tId
	                + "'  ><a id='updBtn_" + treeNode.tId+ "'  class='setting' title='修改'></a><a id='delBtn_" + treeNode.tId
	                + "'  class='del' title='删除'></a><a id='addBtn_" + treeNode.tId
	                + "'  class='add' title='添加'></a></div>";
	        sObj.after(addStr);
	        var btnAdd = $("#addBtn_" + treeNode.tId);	
	        //增加节点
	        if (btnAdd) btnAdd.bind("click", function(e){	
	        	var e=arguments.callee.caller.arguments[0]||event; 
			    if (e && e.stopPropagation) {			     
			    e.stopPropagation();
			    } else if (window.event) {
			      window.event.cancelBubble = true;
			    }
	        	 $( "#dialog" ).dialog({
	        	 	title:"新增标签分类",
	        	 	autoOpen: true,
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
		  	  			$(".ui-form-group ").show();
				      	$(".ui-form-group ").find("input").val("");
				    }
	        	});
	        	 $('#add-dialog-btn').click(function(){
	        	 	var Ppname=$( "#dialog" ).find("input").val();
	        	 	if (Ppname == null) {
			            return;
				    } else if (Ppname == "") {
				            $.alert("节点名称不能为空");
				    }else {
				        $.commAjax({						
							url : $.ctx + '/api/label/categoryInfo/save',
							isShowMask : true,
							async:true,
							postData : {
								"sysId" :labelId,
								"categoryName":Ppname,
								"parentId":treeNode.categoryId,
							},
							onSuccess:function(data){							
								ztreeFunc();
								labeltree();
								$.alert("新增标签分类成功");
							},
							maskMassage : '添加中...'
						});
		       		}
	        	})
			});
			//删除节点
			var delBtn = $("#delBtn_" + treeNode.tId);	
			delBtn.click(function(){
				var e=arguments.callee.caller.arguments[0]||event; 
			    if (e && e.stopPropagation) {			     
			    e.stopPropagation();
			    } else if (window.event) {
			      window.event.cancelBubble = true;
			    }
			    if(treeNode.isParent){
			    	var childrenNodes = treeNode.children;
			    	$.alert("该分类下有标签分类,不可以删除");
			    }else if(!treeNode.categoryId){
			    	$.alert("此标签分类不可以删除");
			    }else{
			    	$.commAjax({
						url : $.ctx + '/api/label/labelInfo/queryList',
						isShowMask : true,
						postData : {
							"categoryId" : treeNode.categoryId,	
							"configId" :labelId,								
						},onSuccess:function(data){
							if(!data.data.length){
								$.confirm('确定要删除该标签分类？', function() {
									$.commAjax({
											url : $.ctx + '/api/label/categoryInfo/delete',
											postData : {
											"categoryId" : treeNode.categoryId,	
											"sysId" :labelId,								
											"parentId":treeNode.categoryId,
										},
										onSuccess:function(data){
											var treeObj = $.fn.zTree.getZTreeObj("ztree");
										    var nodes = treeObj.getNodesByParam("tId", treeNode.tId, null);
										    treeObj.removeNode(nodes[0]);
										    labeltree();
										    $.alert("删除标签分类成功");
										},
									});
								});
							}else{
								$.alert("该分类下还有标签，不可删除");
							}
						},
						maskMassage : '删除中...'
			    	})
			    }
			})
			//修改按钮
			var updBtn = $("#updBtn_" + treeNode.tId);
			if (updBtn) updBtn.bind("click", function(){
				var e=arguments.callee.caller.arguments[0]||event; 
			    if (e && e.stopPropagation) {			     
			    e.stopPropagation();
			    } else if (window.event) {
			      window.event.cancelBubble = true;
			    }
			    if(!treeNode.categoryId){
			    	$.alert("此标签分类不可以修改");
			    }else{
		        	 $( "#dialog" ).dialog({
		        	 	title:"修改标签分类",
			        	autoOpen: true,
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
				  	  		$(".ui-form-group ").show();
					      	$(".ui-form-group ").find("input").val(treeNode.categoryName);
					    }
		        	 });
			    }
        	 $('#add-dialog-btn').click(function(){	        	 	
        	 	var Ppname=$( "#dialog" ).find("input").val();	  
        	 	if (Ppname == null) {
		            return;
			    } else if (Ppname == "") {
			            alert("标签分类名称不能为空");
			    }else {
			        $.commAjax({						
						url : $.ctx + '/api/label/categoryInfo/update',
						isShowMask : true,
						async:true,
						postData : {
							"sysId" :labelId,
							"categoryName":Ppname,
							"categoryId":treeNode.categoryId,
						},
						onSuccess:function(data){
							var treeObj = $.fn.zTree.getZTreeObj("ztree");
						    var nodes = treeObj.getNodesByParam("tId", treeNode.tId, null);
							nodes[0].categoryName = Ppname;
							treeObj.updateNode(nodes[0]);
							labeltree();
							$.alert("修改标签分类成功");
						},
						maskMassage : '修改中...'
					});
	       		}
        	})	        	
		});
		};
		function removeHoverDom(treeId, treeNode) {
			$("#handle_" + treeNode.tId).unbind().remove();
		};
		//展示选中分类下的标签
		function zTreeOnClick(event, treeId, treeNode) {
			leftTreeCagyId =treeNode.categoryId;
			showLabelInfo();
		};		
	}
	//标签全部选中
	$("#selectAll").click(function(){				
		$(".label-select-main ul li").find("input").each(function(){
			$(this).prop("checked", true);
			$(this).siblings("label").addClass("active");
		})
	})
	
	$("#labelList").delegate("input","click",function(){
		if($(this).siblings("label").hasClass("active")){
			$(this).siblings("label").removeClass("active");
			$(this).prop("checked", false);
		}else{
			$(this).siblings("label").addClass("active");
			$(this).prop("checked", true);
		}
	})
	//点击移动到事件
	$("#ui-move").click(function(){
		if($(".label-dialog").hasClass("active")){
			$(".label-dialog").removeClass("active");
		}else{
			$(".label-dialog").addClass("active");
		}
	});
	$("#dialog-del").click(function(){
		$(".label-dialog").removeClass("active");
		$("#labelList").find("input[checked]");
	});
	//左边树模糊查询
	$("#btn_serach").click(function(){
		$.commAjax({			
		    url : $.ctx+'/api/label/categoryInfo/queryList',
		    isShowMask : true,
		    dataType : 'json', 
		    async:true,
		    postData : {
					"sysId" :labelId,
				},
		    onSuccess: function(data){
		    	$.fn.zTree.init($("#ztree"), setting, data.data);
		    	var text = $("#exampleInputAmount").val();
				var treeObj = $.fn.zTree.getZTreeObj("ztree");
				var zTreeNodes = treeObj.getNodesByParamFuzzy("categoryName", text, null);
				$.fn.zTree.init($("#ztree"), setting, zTreeNodes);
		    },
		    maskMassage : 'Load...'
	   });
	});
	//移动事件
	$("#dialog-upd").click(function(){
		//获取要移动的标签ID
		transData = [];
		var j=0;
		$("#labelList label[class~=active]").each(function(){
			transData[j]=$(this).attr("data-id");
			j++;
		})
		if(transData.length == 0){
			$.alert("请选择标签");
		}
		for(var i=0;i<transData.length;i++){
			$.commAjax({			
			    url : $.ctx+'/api/label/labelInfo/update',  
			    isShowMask : true,
			    dataType : 'json', 
			    async : false,
			    postData : {
						"labelId" :transData[i],
						"categoryId":transToData,
					},
			    onSuccess: function(data){ 
			    	if(i == transData.length-1){
				    	$.alert("移动标签成功");
				    	showLabelInfo();
				    	$("#dialog-del").click();
			    	}
			    	
				},
				maskMassage : '正在移动...'
			});
		}
	});
	//移动时的树
	function labeltree(){
		var ztreeObj;
		$.commAjax({			
		    url : $.ctx+'/api/label/categoryInfo/queryList',  		    
		    dataType : 'json', 
		    async:true,
		    postData : {
					"sysId" :labelId,
				},
		    onSuccess: function(data){ 		    			    			    	
			    	var ztreeObj=data.data;
			    	$.fn.zTree.init($("#labeltree"), install, ztreeObj)
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
	//获取移动时选中的节点
	function dataClick(event, treeId, treeNode){
		transToData=treeNode.categoryId;
	}
	//标签部分的模糊查询
	$("#btn_serach1").click(function(){
		var text =$("#exampleInputAmount1").val();
		showLabelInfo(text);
		/*if(labelCategory != 1){
			showLabelInfo(text,1);
		}else{
			showLabelInfo(text);
		}*/
	})
	//全部分类当前分类判断
	/*var labelCategory; //1.当前分类     其他为全部分类
	function distIndex(dataId){
		labelCategory = 0;
		if(dataId != 0){
			labelCategory =1; //将分类定义为当前分类
		}
	}
	$("#radioList .radio").each(function(e){
		if($(this).find("label").hasClass('active')){
			distIndex(e);
		}
		$(this).on("click",function(){
			if($(this).find("label").hasClass('active')){
				$(this).find("label").removeClass('active');
			}else{
				$(this).find("label").addClass('active').parents(".radio").siblings(".radio").find("label").removeClass('active');
				distIndex(e);
			}
		})
	})*/
	//右边树的模糊查询
	$("#btn_serach2").click(function(){
		$.commAjax({			
		    url : $.ctx+'/api/label/categoryInfo/queryList',
		    isShowMask : true,
		    dataType : 'json', 
		    async:true,
		    postData : {
					"sysId" :labelId,
				},
		    onSuccess: function(data){
		    	$.fn.zTree.init($("#labeltree"), install, data.data);
		    	var text = $("#exampleInputAmount2").val();
				var treeObj = $.fn.zTree.getZTreeObj("labeltree");
				var zTreeNodes = treeObj.getNodesByParamFuzzy("categoryName", text, null);
				$.fn.zTree.init($("#labeltree"), install, zTreeNodes);
		    },
		    maskMassage : 'Load...'
	   });
	});
	//符合条件的标签的拼写
	function showLabelInfo(labelInfo,number){
		var categoryId = leftTreeCagyId;
		var text = labelInfo;			//模糊查询时的关键字
		if(number == 1){text = labelInfo; categoryId = "";}//全部分类下的标签模糊查询
		$.commAjax({			
		    url : $.ctx+'/api/label/labelInfo/queryList', 
		    isShowMask : true,
		    dataType : 'json', 
		    async:true,
		    postData : {
					"labelName" :text,
					"configId" :labelId,
					"categoryId" :categoryId,
				},
		    onSuccess: function(data){
		    	$("#labelList").html("");
		    	$("#labelLength").html(data.data.length);
		    	for(var i=0;i<data.data.length;i++){
		    		if (! document.getElementById(data.data[i].labelId)){
			    		var html="<li>"+
				    		"<div class='checkbox'>"+
				    		"<input type='checkbox' id='"+data.data[i].labelId+"' class='checkbix'>"+
				    		"<label for='"+data.data[i].labelId+"' aria-label role='checkbox' class='checkbix' data-id='"+data.data[i].labelId+"'>"+
				    		"<span class='large'></span>"+
				    		data.data[i].labelName+
				    		"</label>"+
				    		"</div>"+
				    		"</li>";
				    	$("#labelList").append(html);
		    		}
		    	}
		    },
		    maskMassage : '搜索中...'
	   });
	}
}