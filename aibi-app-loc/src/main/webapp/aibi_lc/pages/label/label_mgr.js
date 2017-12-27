window.loc_onload = function(){
	/*//批量删除标签
	$('#btn_batch_del').click(function(){
		var ids = $('#mainGrid').jqGrid('getGridParam', 'selarrrow');
		if(ids.length<1){
			$.alert("请选择要删除的标签");
			return;
		}	
		$.confirm("您确定要继续删除吗？",function(){
			for(var i=0; i<ids.length; i++){
				$.commAjax({
					url : $.ctx+'/api/label/labelInfo/delete?Ids=',
					postData : {
						"labelId" : ids[i]
					},
				});
				var k=i+1;
				if(k==ids.length){
					$.success('删除成功。',function(){
						$("mainGrid").setGridParam().trigger(
						     		"reloadGrid",[{
						     			page : 1
						      }]);
					});
				}
			}
		});
	});
	*/
	$('#formSearch').keyup(function(event){
    	if(event.keyCode == 13){
    		$("#btn_search").click();
    	}
    })
    
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
	
	//根据标签数据状态筛选标签
	$("#dataStatusId").change(function(){
		$("#mainGrid").setGridParam({
			postData:{
				"dataStatusId":this.value
			}
		}).trigger("reloadGrid",[{
			page:1
		}]);
	})
	//根据标签审批状态筛选标签
	$("#approveStatusId").change(function(){
		$("#mainGrid").setGridParam({
			postData:{
				"approveStatusId":this.value
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
	            {name:'labelName',index:'labelName', width:20, align:"center",
	            	formatter : function(value, opts, data) {
	            		return "<a href='###' onclick='fun_to_detail(\"" + data.labelId
	        			+ "\")' ><font color='blue'>" + data.labelName
	        			+ "</font></a>";
	            	}
	            },
	            {name:'labelTypeId',index:'labelTypeId', width:20, align:"center",
	            	formatter : function(value, opts, data) {
	            		return $.getCodeDesc("BQLXZD",value);
	            	}
	            },
	            {name:'updateCycle',index:'updateCycle', width:20, align:"center",
	            	formatter : function(value, opts, data) {
	            		return $.getCodeDesc("GXZQZD",value);
	            	}
	            },
	            {name:'dataStatusId',index:'dataStatusId', width:20, align:"center",
	            	formatter : function(value, opts, data) {
	            		return $.getCodeDesc("BQZTZD",value);
	            	}
	            },
	            {name:'approveInfo.approveStatusId',index:'approveInfo.approveStatusId', width:20, align:"center",
	            	formatter : function(value, opts, data) {
	            		return $.getCodeDesc("SPZTZD",value);
	            	}
	            },
	            {name:'createTime',index:'createTime', width:20, align:"center"},
	            {name:'labelId',index:'labelId', width:30, text_aling:"left", key:true,
	            	formatter : function(value, opts, data) {
	            		var html = '';
	            		if(data.dataStatusId !=6){
	            			if(data.approveInfo.approveStatusId==1){
	            			    html+= '<button onclick="fun_to_publish(\''+data.labelId+'\')" type="button" class="btn btn-default  ui-table-btn ui-table-btn" >标签审批发布</button>';
	            		    }
	            		    if(data.dataStatusId==2){
	            			    html+= '<button onclick="fun_to_over(\''+data.labelId+'\')" type="button" class="btn btn-default  ui-table-btn ui-table-btn" >停用</button>';
	            		    }else if(data.dataStatusId==4){
	            		    	html+= '<button onclick="fun_to_start(\''+data.labelId+'\')" type="button" class="btn btn-default  ui-table-btn ui-table-btn" >启用</button>'+
	            		    	       '<button onclick="fun_to_offline(\''+data.labelId+'\')" type="button" class="btn btn-default  ui-table-btn ui-table-btn" >下线</button>';
	            		    }
	            		    if(data.dataStatusId !=2){
	            		    	html+= '<button onclick="fun_to_edit(\''+data.labelId+'\')" type="button" class="btn btn-default  ui-table-btn ui-table-btn" >修改</button>';
	            		    }
	            		    if(data.dataStatusId==1){
	            			    html+= '<button onclick="fun_to_del(\''+data.labelId+'\')" type="button" class="btn btn-default  ui-table-btn ui-table-btn" >删除</button>';
	            		    }  
	            		}	
	            		return html;
	            	}
	            },
	        ],
	        rowNum:12,
	        rowList:[10,20,30],
	        viewrecords: true,
//	        multiselect:true,
	        pager: '#mainGridPager'  
	    });
}

function fun_to_detail(id){
	var win = $.window('标签详情',$.ctx+'/aibi_lc/pages/label/label_detail.html?labelId='+id,500,600);
	win.reload = function(){
		$("#mainGrid").setGridParam({
			postData : $("fromSearch").fromToJson()
		}).trigger("reloadGrid",[{
			page : 1
		}]);
	}
}

function fun_to_publish(id){
	$.confirm('确定发布此标签？',function(){
		$.commAjax({
			url : $.ctx + '/api/label/labelInfo/publish',
			postData : {
				"labelId" : id,
				"dataStatusId" : 2,
				"approveInfo.approveStatusId": 2
			},
			onSuccess : function(data){
				$.success('发布成功。',function(){
					$("#mainGrid").setGridParam({
						postData : $("#formSearch").formToJson()
					}).trigger("reloadGrid", [ {
						page : 1
					} ]);
				});
			}
		});
	})
}

function fun_to_over(id){
	$.confirm('您确定停用该标签吗？',function(){
		$.commAjax({
			url : $.ctx + '/api/label/labelInfo/update',
			postData:{
				"labelId" : id,
				"dataStatusId" : 4
			},
			onSuccess : function(data){
				$("#mainGrid").setGridParam({
					postData:{
						data : null
					}
				}).trigger("reloadGrid",[{
					page : 1
				}]);
			}
		});
	});
}

function fun_to_start(id){
	$.confirm('您确定启用该标签吗？',function(){
		$.commAjax({
			url : $.ctx + '/api/label/labelInfo/update',
			postData:{
				"labelId" : id,
				"dataStatusId" : 2
			},
			onSuccess : function(data){
				$("#mainGrid").setGridParam({
					postData:{
						data : null
					}
				}).trigger("reloadGrid",[{
					page : 1
				}]);
			}
		});
	});
}

function fun_to_offline(id){
	$.confirm('您确定下线该标签吗？',function(){
		$.commAjax({
			url : $.ctx + '/api/label/labelInfo/update',
			postData:{
				"labelId" : id,
				"dataStatusId" : 5
			},
			onSuccess : function(data){
				$("#mainGrid").setGridParam({
					postData:{
						data : null
					}
				}).trigger("reloadGrid",[{
					page : 1
				}]);
			}
		});
	});
}

function fun_to_edit(id){
	var win = $.window('标签修改',$.ctx+'/aibi_lc/pages/label/label_edit.html?labelId='+id,500,600);
	win.reload = function(){
		$("#mainGrid").setGridParam({
			postData : $("fromSearch").fromToJson()
		}).trigger("reloadGrid",[{
			page : 1
		}]);
	}
}

function fun_to_del(id){
	$.confirm('您确定要继续删除吗？',function(){
		$.commAjax({
			url : $.ctx+'/api/label/labelInfo/update',
			postData:{
				"labelId":id,
				"dataStatusId" : 6
			},
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