window.jauth_onload = function() {
	var id = $.getUrlParam("id");
	dg = frameElement.lhgDG;
	dg.removeBtn();
	// 编辑用户角色
	dg.addBtn("save", "保存", function() {
		if ($('#formSearch').validateForm()) {
//			var password = $("#password").val();
			var password = document.getElementById("password").value;
			$.commAjax({
						url : $.ctx + '/api/user/password/update',
						postData : "password=" + password + "&id=" + id,
						onSuccess : function(data) {
							if (data == 'success') {
								$.success('保存成功。', function() {
									dg.cancel();
								})
							} else {
								$.alert("密码修改失败");
							}
						}
					});
		}
	});
	dg.addBtn("cancel", "取消", function() {
				dg.cancel();
	});
}