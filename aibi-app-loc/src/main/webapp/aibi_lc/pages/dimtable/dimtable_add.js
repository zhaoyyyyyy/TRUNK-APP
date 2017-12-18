function fun_to_save(){
	$.commAjax({
		url : $.ctx + '/api/dimtable/dimTableInfo/save',
    	postData : $('#saveDataForm').formToJson(),
		onSuccess : function(data) {
			if (data.data == 'success') {
				$.alert("保存成功");
				window.location="dimtable_mgr.html";
			} else if (data.data == 'haveSameCode') {
				$.alert("已存在的维表管理");
			}
		}
	});
}