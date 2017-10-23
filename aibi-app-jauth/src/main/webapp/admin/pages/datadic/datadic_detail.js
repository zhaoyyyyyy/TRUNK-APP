window.jauth_onload = function() {
	var dg = frameElement.lhgDG;
	dg.removeBtn();
	var dicCode = $.getUrlParam("dicCode");
	var id = $.getUrlParam("id");
	if (id != null) {
		$.commAjax({
			url : $.ctx + '/api/datadic/get',
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
						dicCode : data.dicData.dicCode,
						id : data.dicData.id,
						code : data.dicData.code,
						dataName : data.dicData.dataName,
						note : data.dicData.note
					}
				});
			}
		});
	} else {
		new Vue({
			el : '#saveDataForm',
			data : {
				dicCode : dicCode,
				id : null,
				code : null,
				dataName : null,
				note : null
			}
		});
	}

	dg.addBtn("save", "保存", function() {
		if ($('#saveDataForm').validateForm()) {
			$.commAjax({
				url : $.ctx + '/api/datadic/save',
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
