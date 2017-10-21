window.jauth_onload=function() {
	var dg = frameElement.lhgDG;
	dg.removeBtn();
	var id = $.getUrlParam("id");
	if(id!=null){
		$.commAjax({
			url:$.ctx+'/api/datadic/dic/get',
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
		
//			if(!$('#saveDataForm').validateForm() ){
//				return ;
//				
//			}
//		if($.trim($('[name=entity.id]').val()) != ''){
//			saveOrUpdate();
//			return false;
//		}
//		
//		function saveOrUpdate() {
//		
//			
//			$.commAjax({
//						url : $.ctx+'/sysmgr/datadic/dic!commSave.action',
//						postData:$('#saveDataForm').formToJson(),
//						onSuccess:function(){
//							$.success('保存成功。',function(){
//								dg.cancel();
//								dg.reload();
//							})
//						}
//					})
//		}
//		
//		
//		
//	 	$.commAjax({
//			url : $.ctx+'/sysmgr/datadic/dic!verifyCode.action',
//			postData:{"dic":$("#dic").val()},
//			type: "POST",
//			onSuccess:function(data){
//				if(data==false){
//					saveOrUpdate();
//				}else{
//					$.alert("编码不能重复。");
//				}
//			}
//		 })
		if ($('#saveDataForm').validateForm()) {
			$.commAjax({
			url : $.ctx+'/api/datadic/dic/save',
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

