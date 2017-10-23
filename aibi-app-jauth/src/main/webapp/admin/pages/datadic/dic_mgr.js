window.jauth_onload=function() {
	
	//列表
	var urlShow = $.ctx+'/api/datadic/dic/dicPage/query';
	var colNames = ['字典名称','字典代码','字典类型','备注','操作'];
	var colModel = [
		{name:'dicName', index:'dicName', width:20, align:'left'},
		{name:'dicCode', index:'dicCode', width:20,  align:'left'},
		{name:'dicType',index:'dicType', width:20, align:'left',
			formatter:function(v){return $.getCodeDesc('ZDLX',v);}
		},
		{name:'note', index:'note', width:40,  align:'left'},
		{name:'id', index:'id', width:120, fixed:true,  align:'center',
			formatter:function(value, opts, data) {
		      	return "<a onclick='fun_to_edit(\""+data.id+"\")' class='s_edit' >编辑</a>" +"<a onclick='fun_to_detail(\""+data.dicCode+"\")' class='s_ls' >详情</a>" 
			}
		}
	];
	$("#mainGrid").jqGrid({
		url: urlShow,
		colNames: colNames,
		colModel:colModel,
		rownumbers  : true,
		autowidth	: true,
		viewrecords	: true,
		pager: '#mainGridPager'
	});
	
	//查询按钮
	$("#btnSearch").click(function(){
  		$("#mainGrid").setGridParam({
	      	postData : $("#formSearch").formToJson()
	    }).trigger("reloadGrid",[{page:1}]);
  	});
	
	//添加按钮
	$('#btn_add').click(function(){
		var dg = $.dialog('字典索引新增',$.ctx+'/admin/pages/datadic/dic_edit.html',600,300);
		dg.reload = function(){
			$("#mainGrid").setGridParam({
		      	postData : $("#formSearch").formToJson()
		    }).trigger("reloadGrid",[{page:1}]);
		}
	});
			     
}

//修改
function fun_to_edit(dicId){
		var dg = $.dialog('字典索引修改',$.ctx+'/admin/pages/datadic/dic_edit.html?id='+dicId,600,300);
		dg.reload = function(){
			$("#mainGrid").setGridParam({
		      	postData : $("#formSearch").formToJson()
		    }).trigger("reloadGrid",[{page:1}]);
		}
}
function saveFile(){
	$.commAjax({
			url : $.ctx+'/api/datadic/dic/save',
			isShowMask : true,
			type : 'POST',
			postData: $.extend(
			             $('#saveDataForm').formToJson(),
				         {'$uploadFiles':$getUploadFileIds(),'$deleteFiles':$getDeleteFileIds()}
				       ),
			onSuccess : function() {
				$.success("保存成功");
			},
			onFailure : function() {
				$.error(data['msg']);
			},
			maskMassage : '数据处理中'
		});
}

/**
 * 去详情页面
 * @param {} dicno
 */
function fun_to_detail(dicCode){
	location.href=$.ctx+'/admin/pages/datadic/datadic_mgr.html?dicCode='+dicCode;
}


