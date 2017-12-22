var model = {
		dimtableInfoList:[],
		sourcetableInfoList:[],
		sourceInfoList:[],
		showdimDetail: false
}

window.loc_onload = function() {	
	//创建并发布单指标
	$('#btn_save').click(function(){
		var labelName = $.trim($("#labelName").val());
		if(labelName==""){
			$.alert("标签名称不许为空");
		}else{
			$.commAjax({
				url : $.ctx+'/api/label/labelInfo/save',
				postData:$('#saveDataForm').formToJson(),
				onSuccess:function(data){
					if (data.data == 'success') {
						$.success("创建成功", function() {
							history.back(-1);
						});
					} 
				}
			});
		}	
	});
	
	new Vue({
		el : '#dataD',
	    data : model 
	})
	$.commAjax({
		url : $.ctx + '/api/dimtable/dimTableInfo/queryList',
		onSuccess : function(data){
			model.dimtableInfoList = data.data
		}
	});
	$.commAjax({
		url : $.ctx + '/api/source/sourceTableInfo/queryList',
		onSuccess : function(data){
			model.sourcetableInfoList = data.data
		}
	});	
	//显示维表详情
	$('#btn_dimdetail').click(function(){
		var dimId = $("#dimTableName").val();
		console.log(dimId)
		var win = $.window('维表详情',$.ctx + '/aibi_lc/pages/dimtable/dimtable_detail.html?dimId='+dimId, 800,
				600);
		win.reload = function(){
			$("mainGrid").setGridParam({
				postData : $("#formSearch").formToJson()
			}).trigger("reloadGrid",[{
				page : 1
			}]);
		}
	});
	
	$('#sourceTableName').change(function(){
		var sourceTableId = $("#sourceTableName").val();
		$.commAjax({
			url : $.ctx + '/api/source/sourceTableInfo/get?sourceTableId='+sourceTableId,
			onSuccess : function(data){
				model.sourceInfoList = data.data.sourceInfoList;			
			}
		});
	});

}
function changeStatus(obj){
	if(obj.value == 5){
		model.showdimDetail = true;
	}else{
		model.showdimDetail = false;
	}
}

