window.loc_onload = function() {
	ztreeFunc();
	labeltree();
	
	function ztreeFunc(){
		var ztreeObj,zTreeNodes;	 		
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
	                	
	                	idKey: "id",    //设置节点唯一标识属性名称  
	                    pIdKey: "parentId" ,     //设置父节点唯一标识属性名称  
	                    name:'categoryName',//zTree 节点数据保存节点名称的属性名称  
	                    title: "categoryName"//zTree 节点数据保存节点提示信息的属性名称        
	                }  
				}
			}
		
		var obj = $("#preConfig_list").find("span");
		var labelId =obj.attr("configId");		
		 $.commAjax({  
		    url : $.ctx+'/api/label/categoryInfo/queryList',  		    
		    dataType : 'json', 
		    async:false,
		    postData : {
					"sysId" :labelId,
				},
		    onSuccess: function(data){ 		    	
		    	
		    	zTreeNodes = data.data;	 
		    	
		    	}  
		    });
		 
		 ztreeObj=$.fn.zTree.init($("#ztree"), setting, zTreeNodes);
			
			function addHoverDom(treeId, treeNode) {
				
				var sObj = $("#" + treeNode.tId + "_span");
		        if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0)
		            return;
		        var addStr = "<div class='button add label-handles' id='addBtn_" + treeNode.tId
		                + "' title='add node'><a class='setting'></a><a class='del'></a><a class='add'></a></div>";
		        sObj.after(addStr);
		        var btn = $("#addBtn_" + treeNode.tId).find(".add");				
				if (btn) btn.bind("click", function(){
 				var Ppname = prompt("请输入新节点名称");
 				
                if (Ppname == null) {
                    return;
                } else if (Ppname == "") {
                    alert("节点名称不能为空");
                } else {
                	
                    var param ="&sysId="+ labelId + "&name=" + Ppname; 
                    
                    console.log(param)
                    var zTree = $.fn.zTree.getZTreeObj("#ztree");
                    $.commAjax({
							url : $.ctx + '/api/label/categoryInfo/queryList',
							postData : {
								"sysId" :labelId,
							},
							onSuccess : function(data) {
								$.success('启用成功。', function() {
									if ($.trim(data) != null) {
                                    var treenode = $.trim(data);
                                    ztreeObj.addNodes(treeNode, {
                                        categoryId : labelId,
                                        name : Ppname
                                    }, true);
                                }
								});
							}
						});                                     
                	}	

				});

			};
			
			
			var addCount = 1;
			function addNodes() {
//				hideRMenu();
				var newNode = { name:"增加" + (addCount++)};
				if (zTree.getSelectedNodes()[0]) {
					newNode.checked = zTree.getSelectedNodes()[0].checked;
					zTree.addNodes(zTree.getSelectedNodes()[0], newNode);
				} else {
					zTree.addNodes(null, newNode);
				}
			}
			
			function removeHoverDom(treeId, treeNode) {
				 $("#addBtn_" + treeNode.tId).unbind().remove();
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
}