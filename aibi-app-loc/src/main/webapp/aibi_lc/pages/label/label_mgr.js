window.loc_onload = function(){
	
	
	 //带着专区查不出来数据
	 //var params = $.extend($("#formSearch").formToJson(),{configId:$.getCurrentConfigId()});
	
	
	 var params = $("#formSearch").formToJson();
	 
	 $("#mainGrid").jqGrid({
	    	url: $.ctx + "/api/label/labelInfo/queryPage",
	        datatype: "json",
	        postData : params,
	        colNames:['标签名称','标签类型','创建时间','更新周期','数据状态','标签审批状态','操作'],
	        colModel:[
	            {name:'labelName',index:'labelName', width:30, align:"center"},
	            {name:'labelTypeId',index:'labelTypeId', width:30, align:"center"},
	            {name:'createTime',index:'createTime', width:30, align:"center"},
	            {name:'updateCycle',index:'updateCycle', width:30, align:"center",
	            	formatter : function(value, opts, data) {
	            		return $.getCodeDesc("GXZQZD",value);
	            	}
	            },
	            {name:'dataStatusId',index:'dataStatusId', width:30, align:"center",
	            	formatter : function(value, opts, data) {
	            		return $.getCodeDesc("BQZTZD",value);
	            	}
	            },
	            {name:'labelName',index:'labelName', width:30, align:"center"},
	            {name:'opt',index:'opt', width:30, align:"center",
	            	formatter : function(value, opts, data) {
	            		return "<a href='javascript:void(0);' onclick='fun_to_information(\"" + data.id+ "\")' >发布</a>";
	            	}
	            },
	        ],
	        pager: '#mainGridPager'
	    });
	
}