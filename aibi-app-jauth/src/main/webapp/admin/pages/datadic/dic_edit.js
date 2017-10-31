window.jauth_onload = function() {
	var dg = frameElement.lhgDG;
	dg.removeBtn();
	var id = $.getUrlParam("id");
	if (id != null) {
		$.commAjax({
			url : $.ctx + '/api/datadic/dic/get',
			postData : {
				"id" : id
			},
			type : 'post',
			cache : false,
			async : false,
			onSuccess : function(data) {
				new Vue({
					el : '#saveDataForm',
					data : {
						id : data.dic.id,
						dicType : data.dic.dicType,
						dicCode : data.dic.dicCode,
						dicName : data.dic.dicName,
						note : data.dic.note
					}
				});
			}
		});
	} else {
		new Vue({
			el : '#saveDataForm',
			data : {
				id : null,
				dicType : null,
				dicCode : null,
				dicName : null,
				note : null
			}
		});
	}
	dg.addBtn("save", "保存", function() {
		if ($('#saveDataForm').validateForm()) {
			$.commAjax({
				url : $.ctx + '/api/datadic/dic/save',
				postData : $('#saveDataForm').formToJson(),
				onSuccess : function(data) {
					if (data == 'success') {
						$.success('保存成功。', function() {
							dg.cancel();
							dg.reload();
						})
					} else if (data == 'haveCode') {
						$.alert("编码重复");
					}
				}
			})
		}

	});
	dg.addBtn("cancel", "取消", function() {
		dg.cancel();
	});

}
