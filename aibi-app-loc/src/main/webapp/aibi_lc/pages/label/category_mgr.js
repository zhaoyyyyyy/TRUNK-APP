window.loc_onload = function() {
	ztreeFunc();
	labeltree();
	$("#dialog").dialog({
	      height:164,
	      width: 300,
	      modal: true,
	      autoOpen: false,
	      title:"新增标签",
	      open:function(){
	      	ztreeFunc();
	      },
	      buttons: [
	    	    {
	    	      text: "取消",
	    	      "class":"ui-btn ui-btn-second",
	    	      click: function() {
	    	        $( this ).dialog( "close" );
	    	      }
  	        },{
	    	      text: "确定",
	    	      "id":"add-dialog-btn",
	    	      "class":"ui-btn ui-btn-default",
	    	      click: function() {
	    	        $( this ).dialog( "close" );
	    	      }
	    	}
  	  ]
  });
	function ztreeFunc(){
		var ztreeObj;
		var obj = $("#preConfig_list").find("span");
		var labelId =obj.attr("configId");				
		$.commAjax({			
		    url : $.ctx+'/api/label/categoryInfo/queryList',  		    
		    dataType : 'json', 
		    async:true,
		    postData : {
					"sysId" :labelId,
				},
		    onSuccess: function(data){ 		    			    			    	
			    	var ztreeObj=data.data;
			    	$.fn.zTree.init($("#ztree"), setting, ztreeObj)
		    	}  
	   });
		setting = {
			view: {
				selectedMulti: false,
				addHoverDom: addHoverDom,
				removeHoverDom: removeHoverDom,
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
				onClick: zTreeOnClick
			}
		}		
		var newCount = 1;				
		function addHoverDom(treeId, treeNode) {
			var sObj = $("#" + treeNode.tId + "_span");
	        if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0)
	            return;
	        var addStr = "<div class='button add label-handles' id='handle_" + treeNode.tId
	                + "'  title='add node'><a id='updBtn_" + treeNode.tId+ "'  class='setting'></a><a id='delBtn_" + treeNode.tId
	                + "'  class='del'></a><a id='addBtn_" + treeNode.tId
	                + "'  class='add'></a></div>";
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
	        	 $( "#dialog" ).dialog( "open" );
	        	 $('#add-dialog-btn').click(function(){
	        	 	var Ppname=$( "#dialog" ).find("input").val();
	        	 	if (Ppname == null) {
			            return;
				    } else if (Ppname == "") {
				            alert("节点名称不能为空");
				    }else {
				        $.commAjax({						
							url : $.ctx + '/api/label/categoryInfo/save',
							async:true,
							postData : {
								"sysId" :labelId,
								"categoryName":Ppname,
								"parentId":treeNode.categoryId,
							},
							onSuccess:function(data){							
								ztreeFunc();
							}
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
				$.confirm('确定要删除该标签？', function() {
					$.commAjax({
							url : $.ctx + '/api/label/categoryInfo/delete',
							postData : {
								"categoryId" : treeNode.categoryId,	
								"sysId" :labelId,								
								"parentId":treeNode.categoryId,
							},
							onSuccess:function(data){
								
								ztreeFunc();
							}
						});
				})
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
        	 $( "#dialog" ).dialog( "open" );	        	
        	 $('#add-dialog-btn').click(function(){	        	 	
        	 	 var Ppname=$( "#dialog" ).find("input").val();	        	 	 
        	 	if (Ppname == null) {
		            return;
			    } else if (Ppname == "") {
			            alert("节点名称不能为空");
			    }else {
			        $.commAjax({						
						url : $.ctx + '/api/label/categoryInfo/update',
						async:true,
						postData : {
							"sysId" :labelId,
							"categoryName":Ppname,
							"categoryId":treeNode.categoryId,
						},
						onSuccess:function(data){														
							ztreeFunc();
						}
					});
	       		}
        	})	        	
		});
	
		};
		function removeHoverDom(treeId, treeNode) {
			$("#handle_" + treeNode.tId).unbind().remove();
		};
		function zTreeOnClick(event, treeId, treeNode) {			
		    $.commAjax({			
			    url : $.ctx+'/api/label/labelInfo/queryList',  		    
			    dataType : 'json', 
			    async:true,
			    postData : {
						"categoryId" :treeNode.categoryId,
					},
			    onSuccess: function(data){ 		    			    			    	
				    	var labelList=data.data;
				    	for(var i=0;i<labelList.length;i++){
				    		var html="<li>"+
				    		"<div class='checkbox'>"+
				    		"<input type='checkbox' id='checkbox_"+i+"' class='checkbix'>"+
				    		"<label for='checkbox_"+i+"' aria-label role='checkbox' class='checkbix'>"+
				    		"<span class='large'></span>"+
				    		labelList[i].labelName+
				    		"</label>"+
				    		"</div>"+
				    		"</li>";				    					    		
				    		$("#labelList").append(html)
				    	}
			    	}  
	   		});
		    
		};		
	}
	
	
	function labeltree(){
		var ztreeObj;
		var obj = $("#preConfig_list").find("span");
		var labelId =obj.attr("configId");
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
			}
		}
	}




		
	$("#selectAll").click(function(){				
		$(".label-select-main ul li").find("input").each(function(){
			$(this).attr("checked",true);					
		})
	})
		
	$("#ui-move").click(function(){
		$(".label-dialog").addClass("active");
	});
	$("#dialog-del").click(function(){
		$(".label-dialog").removeClass("active");
	});
	var txt;
	$("#btn_serach").click(function(){
		var text = $("#exampleInputAmount").val();	
		if(txt != text){
			var treeObj = $.fn.zTree.getZTreeObj("ztree");
			var zTreeNodes = treeObj.getNodesByParamFuzzy("categoryName", text, null);
			$.fn.zTree.init($("#ztree"), setting, zTreeNodes);
			txt =text;
		}
	});

	
}