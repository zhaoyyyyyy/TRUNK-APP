window.jauth_onload = function() {
	var parentOrgCode = $.getUrlParam("parentOrgCode");
	$.commAjax({
		url:$.ctx+'/api/organization/parentOrg/get',
		postData:{"orgCode":parentOrgCode},
		type:'post',
		cache:false,
		async:false,
		onSuccess:function(data){
			new Vue({ el:'#vie', data: data });
		}
	});
	//保存按钮
	$("#btn_save").click(function(){
		if ($('#saveDataForm').validateForm()){
			$.commAjax({
				url : $.ctx+'/api/organization/add?poc='+parentOrgCode,
				postData:$('#saveDataForm').formToJson(),
				onSuccess:function(data){
					if(data == "success"){
						$.success('保存成功。',function(){
							parent.window.location.reload();
						});
					}else if(data == "orgCodeExist"){
						$.err('组织编码已存在，请重新输入！',function(){
							$("orgCode").focus();
						});
					}
				}
			});
		}
  	});
  	$("#btn_rtn").click(function(){
  		location.href = $.ctx+'/admin/pages/orgmgr/org_info.html?orgCode=' + parentOrgCode;
  	});
};