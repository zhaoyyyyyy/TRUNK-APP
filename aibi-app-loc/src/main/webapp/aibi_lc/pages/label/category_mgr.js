window.loc_onload = function() {
	ztreeFunc();
	labeltree();
	
	function ztreeFunc(){
		var zTreeObj,
		setting = {
			view: {
				selectedMulti: false,
				addHoverDom: addHoverDom,
				removeHoverDom: removeHoverDom,
			}
		}
		var obj = $("#preConfig_list").find("span");
		var labelId =obj.attr("configId");		
		 $.commAjax({  
		    url : $.ctx+'/api/label/categoryInfo/queryList',  		    
		    dataType : 'json', 
		    postData : {
					"sysId" :labelId,
				},
		    onSuccess: function(res){  
		        zTreeNodes = res;
		        console.log(res)
		        //$.fn.zTree.init($("#tree"), setting, treeNodes);  
		        //zTreeObj = $.fn.zTree.init($("#ztree"), setting, zTreeNodes);
		    }  
		    });  
//		zTreeNodes = [
//			{"name":"在网用户状态", open:false, children: [
//				{ "name":"google", "url":"http://g.cn", "target":"_blank"},
//				{ "name":"baidu", "url":"http://baidu.com", "target":"_blank"},
//				{ "name":"sina", "url":"http://www.sina.com.cn", "target":"_blank"}
//				]
//			},
//			{"name":"在网状态", open:true, children: [
//				{ "name":"停开机状态", open:true,children:[
//					{ "name":"催停类型", "url":"http://g.cn", "target":"_blank"},
//					{ "name":"测试", "url":"http://baidu.com", "target":"_blank"}
//				]
//				},
//				]
//			}
//		];
//		zTreeObj = $.fn.zTree.init($("#ztree"), setting, zTreeNodes);
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


	function addHoverDom(treeId, treeNode) {
		var aObj = $("#" + treeNode.tId + "_a");
		if ($("#diyBtn_space_"+treeNode.id).length>0) return;
		var editStr = "<div class='label-handles' id='diyBtn_space_" +treeNode.id+ "' ><a class='setting'></a><a class='del'></a><a class='add'></a></div>";
		aObj.append(editStr);
		var btn = $("#diyBtn_space_"+treeNode.id);
		if (btn) btn.bind("click", function(){alert("diy Button for " + treeNode.name);});
	};
	function removeHoverDom(treeId, treeNode) {
		$("#diyBtn_space_" +treeNode.id).unbind().remove();
	};
		
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