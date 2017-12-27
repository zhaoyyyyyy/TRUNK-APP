window.loc_onload = function() {
	ztreeFunc();
	labeltree();
	//移动标签时所选中的节点ID
	var transToData;
	//移动标签时所选中的标签ID数组
	var transData = new Array();
	$("#dialog").dialog({
	      height:164,
	      width: 300,
	      modal: true,
	      autoOpen: false,
	      open:function(){
	      	ztreeFunc();
	      }
	      
  });
	//左边树
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
	        	 $( "#dialog" ).dialog({
	        	 	title:"新增标签",
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
		  	  		]
	        	});
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
								labeltree();
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
								labeltree();
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
        	 $( "#dialog" ).dialog({
        	 	title:"修改标签",
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
		  	  	]
	        	
        	 });
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
							labeltree();
						}
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
			var flag=false;
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
				    		checkID=treeNode.categoryId+i
				    		if (! document.getElementById(checkID)){
					    		var html="<li>"+
						    		"<div class='checkbox'>"+
						    		"<input type='checkbox' id='"+treeNode.categoryId+i+"' class='checkbix'>"+
						    		"<label for='"+treeNode.categoryId+i+"' aria-label role='checkbox' class='checkbix' data-id='"+labelList[i].labelId+"'>"+
						    		"<span class='large'></span>"+
						    		labelList[i].labelName+
						    		"</label>"+
						    		"</div>"+
						    		"</li>";
						    	$("#labelList").append(html);
				    		}
					    }
			    	}  
	   		});
		    
		};		
	}
	//标签全部选中
	$("#selectAll").click(function(){				
		$(".label-select-main ul li").find("input").each(function(){
			$(this).attr("checked",true);
			$(this).siblings("label").addClass("active");
		})
	})
	$("#labelList").delegate("label","click",function(){
		if($(this).hasClass("active")){
			$(this).removeClass("active")
		}else{
			$(this).addClass("active")
		}
	})
	
	//获取选中标签ID
	$("#ui-move").click(function(){
		$(".label-dialog").addClass("active");
		var i=0;
		$("#labelList label[class~=active]").each(function(){
			transData[i]=$(this).attr("data-id");
			i++;
		})
	});
	$("#dialog-del").click(function(){
		$(".label-dialog").removeClass("active");
		$("#labelList").find("input[checked]");
		console.log($("#labelList").find("input[checked]"))
	});
	//左边树模糊查询
	var leftTreeInput ="";
	$("#btn_serach").click(function(){
		var text;
		leftTreeInput = $("#exampleInputAmount").val();
		if(leftTreeInput == ""){ztreeFunc(); return }
		if(leftTreeInput !=text ){
			var treeObj = $.fn.zTree.getZTreeObj("ztree");
			var zTreeNodes = treeObj.getNodesByParamFuzzy("categoryName", leftTreeInput, null);
			$.fn.zTree.init($("#ztree"), setting, zTreeNodes);
			text =leftTreeInput;
		}
	});
	//移动事件
	$("#dialog-upd").click(function(){
		var flag =false;
		for(var i=0;i<transData.length;i++){
			$.commAjax({			
			    url : $.ctx+'/api/label/labelInfo/update',  		    
			    dataType : 'json', 
			    postData : {
						"labelId" :transData[i],
						"categoryId":transToData,
					},
			    onSuccess: function(data){ 
			    	if(!flag){
				    	$.alert("移动标签成功");
				    	$("#labelList").html("");
				    	ztreeFunc();
				    	labeltree();
				    	flag =true;
			    	}
				}
			});
		}
	});
	//移动时的树
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
		$.alert("暂未实现")
	})
	//右边树的模糊查询
	var rightTreeInput ="";
	$("#btn_serach2").click(function(){
		var text;
		rightTreeInput = $("#exampleInputAmount2").val();
		if(rightTreeInput == ""){labeltree(); return }
		if(rightTreeInput !=text ){
			var treeObj = $.fn.zTree.getZTreeObj("labeltree");
			var zTreeNodes = treeObj.getNodesByParamFuzzy("categoryName", rightTreeInput, null);
			$.fn.zTree.init($("#labeltree"), install, zTreeNodes);
			text =rightTreeInput;
		}
	});
}