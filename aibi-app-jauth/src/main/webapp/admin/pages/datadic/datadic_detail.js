window.jauth_onload=function() {
	var dg = frameElement.lhgDG;
	dg.removeBtn();
	var dicCode=$.getUrlParam("dicCode");
	var id = $.getUrlParam("id");
	if(id!=null){
		$.commAjax({
			url:$.ctx+'/api/datadic/dicdata/get',
			postData:{"id":id},
			type:'post',
			cache:false,
			async:false,
			onSuccess:function(data){
				new Vue({ el:'#saveDataForm', data: data });
			}
		});
	}
	
	
	dg.addBtn("save", "保存", function() {
//	//全局验证编码
//		if ($('#saveDataForm').validateForm()) {
//	 		$.commAjax({
//				url : $.ctx+'/sysmgr/datadic/dicData!verifyCode.action',
//				postData:{"code":$("#code").val()},
//				type: "POST",
//				onSuccess:function(data){
//					if(data==false){
//							$.commAjax({
//								url : $.ctx+'/sysmgr/datadic/dicData!commSave.action',
//								postData:$('#saveDataForm').formToJson(),
//								onSuccess:function(){
//									$.success('保存成功。',function(){
//										dg.cancel();
//										dg.reload();
//									})
//								}
//							})	
//					}else{
//						$.alert("编码不能重复。");
//					}
//				}
//			})
//		}
		if ($('#saveDataForm').validateForm()) {
				$.commAjax({
				url : $.ctx+'/api/datadic/dicdata/save?dicCode='+dicCode,
				postData:$('#saveDataForm').formToJson(),
				onSuccess:function(data){
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

