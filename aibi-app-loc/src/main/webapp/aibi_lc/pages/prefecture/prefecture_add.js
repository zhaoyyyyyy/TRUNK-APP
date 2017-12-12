window.loc_onload = function() {
	
}
function fun_to_save(){
		$.commAjax({
			url : $.ctx + '/api/prefecture/preConfigInfo/save',
			postData : $('#saveDataForm').formToJson(),
			onSuccess : function(data) {
				if (data == 'success') {
					$.alert("保存成功");
				} else if (data == 'haveCode') {
					$.alert("编码重复");
				}
			}
		})
}