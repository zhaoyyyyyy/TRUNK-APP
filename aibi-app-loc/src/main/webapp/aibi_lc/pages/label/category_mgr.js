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
	                	
	                	idKey: "categoryId",    //设置节点唯一标识属性名称  
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
			
			function addHoverDom(categoryId, treeNode) {				
				var aObj = $("#" + treeNode.tId + "_a");
				
				if ($("#diyBtn_space_"+treeNode.id).length>0) return;
				var editStr = "<div class='label-handles' id='diyBtn_space_" +treeNode.id+ "' ><a class='setting'></a><a class='del'></a><a class='add' onclick='addTreeNode()'></a></div>";
				aObj.append(editStr);
				var btn = $("#diyBtn_space_"+treeNode.id);
//				if (btn) btn.bind("click", function(){
//					
//					alert("diy Button for " + treeNode.tId);
//					
////					var wd = $.window('新增标签', $.ctx
////							+ '/aibi_lc/pages/label/category_add.html', 500, 500);
////				    	wd.reload = function() {
////							
////				    	}
//
//					
//				});
				
				
//				$("#label-delBox").click(function(){
//					$(".label-addBox").hide();
//				})
			};
			
			
			function removeHoverDom(treeId, treeNode) {
				$("#diyBtn_space_" +treeNode.id).unbind().remove();
			};
		
	}
	function addTreeNode(){
		alert(1)
		//$(".label-addBox").show();
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