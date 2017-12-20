window.loc_onload = function() {
	var frWin = frameElement.lhgDG;
	frWin.removeBtn();
	frWin.addBtn("ok", "创建", function() {
		if ($('#saveDataForm').validateForm()) {
			$.commAjax({
				url : $.ctx+'/api/label/labelInfo/save',
				postData:$('#saveDataForm').formToJson(),
				onSuccess:function(data){
					if (data.data == 'success') {
						$.success('保存成功。', function() {
							frWin.cancel();
							frWin.reload();
						})
					} 
				}
			})
	    }		
	});
	frWin.addBtn("cancel", "取消", function() {
		frWin.cancel();
	});	
			     
}

