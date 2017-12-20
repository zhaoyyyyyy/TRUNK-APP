
window.loc_onload = function() {
	/*var frWin = frameElement.lhgDG;
	frWin.removeBtn();
	frWin.addBtn("ok", "创建", function() {
		if ($('#saveDataForm').validateForm()) {
			var labelName = $.trim($("#labelName").val());
			if(labelName == ""){
				$.alert("标签名称不许为空");
			}else{
				$.commAjax({
					url : $.ctx+'/api/label/labelInfo/save',
					postData:$('#saveDataForm').formToJson(),
					onSuccess:function(data){
						if (data.data == 'success') {
							$.success('保存成功。', function() {
								frWin.cancel();
								frWin.reload();
							});
						} 
					}
				});
			}			
	    }		
	});
	frWin.addBtn("cancel", "取消", function() {
		frWin.cancel();
	});	*/
	
	//创建并发布单指标
	$('#btn_save').click(function(){
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
	});
	
	//获取维表信息
	$.commAjax({
		url : $.ctx + '/api/dimtable/dimTableInfo/queryList',
		onSuccess: function(data){
			new Vue({
				el : '#dataD',
				data : {
					"dimtableInfoList":data.data
				}
			})
		}
	});
	//获取指标信息
	$.commAjax({
		url : $.ctx + '/api/source/sourceTableInfo/queryList',
		onSuccess: function(data){
			new Vue({
				el : '#dataSource',
				data : {
					"sourceInfolist" : data.data
				}
			})
		}
	});
	
	
	$('#btn_dimdetail').click(function(){
		var dimtableName = $("dimtableName").val();
		var win = $.window('维表详情',$.ctx + '/aibi_lc/pages/dimtable/dimtable_detail.html', 800,
				600);
		win.reload = function(){
			$("mainGrid").setGridParam({
				postData : $("#formSearch").formToJson()
			}).trigger("reloadGrid",[{
				page : 1
			}]);
		}
	});

}

