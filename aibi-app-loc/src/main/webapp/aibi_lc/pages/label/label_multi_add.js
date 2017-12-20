window.loc_onload = function(){
	//指标选择
	$('#btn_index_select').click(function() {
		var win = $.window('指标配置', $.ctx + '/aibi_lc/pages/label/sourceInfo_mgr.html', 1100, 600);
		win.reload = function() {
			$("#mainGrid").setGridParam({
				postData : $("#formSearch").formToJson()
					}).trigger("reloadGrid", [{
						page : 1
			}]);
		}
	});
		
	//批量删除标签
	$('#btn_batch_del').click(function(){
		var ids = $('#mainGrid').jqGrid('getGridParam', 'selarrrow');
		if(ids.length<1){
			$.alert("请选择要删除的标签");
			return;
		}
		$.confirm("您确定要继续删除吗？",function(){
			$.commAjax({
				url : $.ctx+'/api/label/labelInfo/batchdelete?Ids='+ids,
				onSuccess:function(data){
					$.success('删除成功。',function(){
						$("#mainGrid").setGridParam().trigger(
								"reloadGrid", [{
									page : 1
								}]);
					});
				}
			});
		});
	});
	
	//根据标签名模糊查询
	$("#btn_search").click(function(){
		var txtValue = $("#form_search").val();
		if(txtValue == null){
			$("#mainGrid").setGridParam({
				postData : {
					data : null
				}
			}).trigger("reloadGrid",[{
				page : 1
			}]);
		}else{
			$("#mainGrid").setGridParam({
				postData : {
					"labelName":txtValue
				}
			}).trigger("reloadGrid",[{
				page : 1
			}])
		}
	})
	
	$("#dataStatusId").change(function(){
		$("#mainGrid").setGridParam({
			postData:{
				"dataStatusId":this.value
			}
		}).trigger("reloadGrid",[{
			page:1
		}]);
	})
	
	 //带着专区查不出来数据
	 //var params = $.extend($("#formSearch").formToJson(),{configId:$.getCurrentConfigId()});
	
	
	 var params = $("#formSearch").formToJson();
	 
	 $("#mainGrid").jqGrid({
	    	url: $.ctx + "/api/label/labelInfo/queryPage",
	        datatype: "json",
	        postData : params,
	        colNames:['标签名称','标签类型','更新周期','数据状态','标签审批状态','创建时间','操作'],
	        colModel:[
	            {name:'labelName',index:'labelName', width:30, align:"center"},
	            {name:'labelTypeId',index:'labelTypeId', width:30, align:"center",
	            	formatter : function(value, opts, data) {
	            		return $.getCodeDesc("BQLXZD",value);
	            	}
	            },
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
	            {name:'approveInfo.approveStatusId',index:'approveInfo.approveStatusId', width:30, align:"center",
	            	formatter : function(value, opts, data) {
	            		return $.getCodeDesc("SPZTZD",value);
	            	}
	            },
	            {name:'createTime',index:'createTime', width:30, align:"center"},
	            {name:'labelId',index:'labelId', width:30, align:"center",
	            	formatter : function(value, opts, data) {
	            		return "<a onclick='fun_to_information(\"" + data.labelId+ "\")' >发布</a>"
	            		      +"<a onclick='fun_update(\"" + data.labelId+ "\")' >修改</a>"
	            		      +"<a onclick='fun_del(\"" + data.labelId+ "\")' >删除</a>";
	            	}
	            },
	        ],
	        rowNum:12,
	        rowList:[10,20,30],
	        viewrecords: true,
	        multiselect:true,
	        pager: '#mainGridPager'  
	    });
}
function fun_del(id){
	$.confirm('您确定要继续删除吗？',function(){
		$.commAjax({
			url:$.ctx+'/api/label/labelInfo/delete',
			postData:{"labelId":id},
			onSuccess:function(data){
				$("#mainGrid").setGridParam({
					postData:{
						data:null
					}
				}).trigger("reloadGrid",[{
					page:1
			   }]);	
			}
		});
	});
}