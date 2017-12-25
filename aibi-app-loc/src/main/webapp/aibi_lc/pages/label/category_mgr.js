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
		var ssg=window.sessionStorage;
		ssg.getItem("token")
		//console.log(ssg.getItem("token"));
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
//			async: {
//				enable: true,
//				url:$.ctx+'/api/label/categoryInfo/queryList',
//				//autoParam:["categoryId"],
//				contentType: "application/json",
//				dataType:'json',
//				//otherParam:{"X-Authorization" :ssg.getItem("token")}
//								
////				dataFilter: filter
//			},
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
				onAsyncError: zTreeOnAsyncError,
				onAsyncSuccess: zTreeOnAsyncSuccess
				
			}
}
		$.fn.zTree.init($("#ztree"), setting)
		
		var newCount = 1;
		
		function zTreeOnAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
    		console.log(XMLHttpRequest);
		};
		function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
    		console.log(msg);
		};
		function addHoverDom(treeId, treeNode) {
			var sObj = $("#" + treeNode.tId + "_span");
	        if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0)
	            return;
	        var addStr = "<div class='button add label-handles' id='handle_" + treeNode.tId
	                + "'  title='add node'><a id='updBtn_" + treeNode.tId
	                + "'  class='setting'></a><a id='delBtn_" + treeNode.tId
	                + "'  class='del'></a><a id='addBtn_" + treeNode.tId
	                + "'  class='add'></a></div>";
	        sObj.after(addStr);
	        var btnAdd = $("#addBtn_" + treeNode.tId);	
	        //增加节点
	        if (btnAdd) btnAdd.bind("click", function(){
	        	var Ppname = prompt("请输入新节点名称");
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
							var zTree = $.fn.zTree.getZTreeObj("ztree");
							zTree.addNodes(treeNode, {categoryName:treeNode.id, categoryName:Ppname});
							//获取新增加的节点信息
							$.commAjax({						
								url : $.ctx + '/api/label/categoryInfo/queryList',
								dataType : 'json', 
								async:true,
								postData : {
									"sysId" :labelId,
									"categoryName":Ppname,
									"parentId":treeNode.categoryId
								},
								onSuccess:function(data){
									ztreeFunc();
								}
							});
						}
					});
		       	}
//	       	})     
	});

			//删除节点
			var delBtn = $("#delBtn_" + treeNode.tId);	
			delBtn.click(function(){
			
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
//				var updBtn = $("#updBtn_" + treeNode.tId);	
//				updBtn.click(function(){
//					$.commAjax({
//								url : $.ctx + '/api/label/categoryInfo/update',
//								postData : {
//									"categoryId" : treeNode.categoryId,									
//								}								
//						});
//				})
//				
		};
		

		
		function removeHoverDom(treeId, treeNode) {
			$("#handle_" + treeNode.tId).unbind().remove();
		};
	}


	
	function labeltree(){
		var zTreeObj,
		setting = {
			view: {
				selectedMulti: false
			}
		},
		zTreeNodes = [
			{"name":"在网用户状态", open:false, children: [
				{ "name":"google", "url":"http://g.cn", "target":"_blank"},
				{ "name":"baidu", "url":"http://baidu.com", "target":"_blank"},
				{ "name":"sina", "url":"http://www.sina.com.cn", "target":"_blank"}
				]
			},
			{"name":"在网状态", open:true, children: [
				{ "name":"停开机状态", open:true,children:[
					{ "name":"催停类型", "url":"http://g.cn", "target":"_blank"},
					{ "name":"测试", "url":"http://baidu.com", "target":"_blank"}
				]
				},
				]
			}
		];
		zTreeObj = $.fn.zTree.init($("#labeltree"), setting, zTreeNodes);
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
	$("#btn_serach").click(function(){
		var text = $("#exampleInputAmount").val();
		var zTreeNodes = ztreeObj.getNodesByParamFuzzy("categoryName", text, null);
		$.fn.zTree.init($("#ztree"), setting, zTreeNodes);
	});
}