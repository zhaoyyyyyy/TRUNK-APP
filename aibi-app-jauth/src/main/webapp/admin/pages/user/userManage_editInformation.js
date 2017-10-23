window.jauth_onload = function() {
	var id = $.getUrlParam("id")
	$.commAjax({
		url:$.ctx+'/api/user/userInfo/query',
		postData:{"id":id},
		type:'post',
		cache:false,
		async:false,
		onSuccess:function(data){
			new Vue({ el:'#saveDataForm', data: data });
		}
	});
	// 编辑用户角色
	$('#edit_user_role').click(function() {
		var id = document.getElementById("userid").value;
		var dg = $.dialog('分配角色', $.ctx + '/admin/pages/user/userManage_role.html?id=' + id,
				600, 500);
		dg.reload = function() {
			location.reload();
		}
	});
	// 编辑数据范围
	$('#edit_user_group').click(function() {
		var id = document.getElementById("userid").value;
		var dg = $.dialog('分配数据范围', $.ctx
						+ '/admin/pages/user/userManage_group.html?id=' + id,
				600, 500);
		dg.reload = function() {
			location.reload();
		}
	});

	var dg = frameElement.lhgDG;
	dg.removeBtn();
	
	dg.addBtn("save", "保存", function() {
				if ($('#saveDataForm').validateForm()) {
					var user = $("#saveDataForm").serialize();
					$.commAjax({
								url : $.ctx + '/api/user/userInfo/update',
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