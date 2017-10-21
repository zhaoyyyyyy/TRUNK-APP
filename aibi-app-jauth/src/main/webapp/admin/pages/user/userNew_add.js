window.jauth_onload = function() {
	var dg = frameElement.lhgDG;
	dg.removeBtn();

	dg.addBtn("save", "保存", function() {
		if ($('#saveDataForm').validateForm()) {
			var user = $("#saveDataForm").serialize();
			$.commAjax({
				url : $.ctx + '/api/user/newUser',
				postData : $("#saveDataForm").formToJson(),
				onSuccess : function(data) {
					if (data == 'success') {
						$.success('保存成功。', function() {
							dg.cancel();
							dg.reload();
						})
					}
				}
			});
		}
	});
	dg.addBtn("cancel", "取消", function() {
		dg.cancel();
	});
}