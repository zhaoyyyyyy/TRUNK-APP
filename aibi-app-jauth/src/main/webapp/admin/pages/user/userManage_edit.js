var dg;
window.jauth_onload = function(){
	
	var id = $.getUrlParam("id")
	$.commAjax({
		url:$.ctx+'/api/user/userInfo/query',
		postData:{"id":id},
		type:'post',
		cache:false,
		async:false,
		onSuccess:function(data){
			data.user.sex = $.getCodeDesc('SEX', data.user.sex);
			new Vue({ el:'#saveDataForm', data:data });
		}
	});
	
	dg = frameElement.lhgDG;
	dg.removeBtn();
	dg.addBtn("cancel", "取消", function() {
				dg.reload();
				dg.cancel();
			});

	// 编辑基础信息
	$('#btn_edit_user_information').click(function() {
		var dgT = $.dialog( '编辑用户基础信息', $.ctx + '/admin/pages/user/userManage_editInformation.html?id='+ id, 700, 360);
		dgT.reload = function() {
			dg.reload();
			window.location.reload();
		}
	});
	// 修改密码
	$('#btn_edit_user_password').click(function() {
		var dgT = $.dialog('修改密码',$.ctx+ '/admin/pages/user/userManage_editPassword.html?id='+ id, 400, 120);
		dgT.reload = function() {
			dg.reload();
		}
	});

}
