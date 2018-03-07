window.loc_onload = function() {
	 labelId = $.getUrlParam("labelId");
	 var colNamesArr =[];
 	var colModelArr =[];
	 $.commAjax({			
		    url : $.ctx+'/api/syspush/labelPushCycle/findGroupListCols',
		    dataType : 'json', 
		    postData : {
		    	"labelId" : labelId,
		    	},
		    onSuccess: function(data){ 
		    	for(var i=0;i<data.data.length;i++){
		    		colNamesArr.push(data.data[i].attrColName);
		    		colModelArr.push({
						name : data.data[i].attrCol,
						index : data.data[i].attrCol,
						width : 80,
						sortable : false,
						frozen : true,
						align : "center",
					})
		    	}
		    	colNamesArr.push("操作111");
		    	colModelArr.push({
					hidden:true,
				})
		    	 $("#mainGrid").jqGrid({
		 			url : $.ctx + "/api/syspush/labelPushCycle/findGroupList",
		 			datatype : "json",
		 			postData :{"labelId": labelId},
		 			colNames : colNamesArr,
		 			colModel : colModelArr,
		 		});
		    	 $("#mainGrid").jqGrid('setLabel',0, '序号');
	    	}  
	   });
	var wd = frameElement.lhgDG;
	wd.addBtn("cancel", "确定", function() {
		wd.cancel();
	});
}