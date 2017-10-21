window.jauth_onload = function() {
	var id = $.getUrlParam("id");
	if(id!=null){
		$.commAjax({
			url:$.ctx+'/api/group/query',
			postData:{"id":id},
			type:'post',
			cache:false,
			async:false,
			onSuccess:function(data){
				new Vue({ el:'#saveDataForm', data: data });
			}
		});
	}
	var rootOrgCode = "1";
	var selectTrees = $("#selectTrees").val()
	var mySimpleTree;
	var myTree;
	var dg = frameElement.lhgDG;
	dg.removeBtn();
	var _url = $.ctx + "/api/organization/renderOrgTree?orgCode="
			+ rootOrgCode + "&groupId=" + id;
	var cascadeParentChecked = true;
	var cascadeChildrenChecked = true;
	var filterOrgType = null;
	$.commAjax({
				url : $.ctx + "/api/organization/renderOrgTree",
				isShowMask : false,
				type : 'POST',
				postData:{
					"orgCode":rootOrgCode,
					"groupId":id,
				},
				onSuccess : function(data) {
					$("#sRoot").append(data);
					mySimpleTree = $('#tree').simpleTree({
								autoclose : false,
								nodeCheckBox : true,
								ignoreIndeterminate : false,
    							selectValues : selectTrees.split(',')
							});

				},
				onFailure : function() {
				},
				maskMassage : '数据加载中...'
			});

	var dg = frameElement.lhgDG;
	dg.removeBtn();

	var isSave = false;
	dg.addBtn("save", "保存", function() {
				if ($('#saveDataForm').validateForm()) {
					var id = document.getElementById("group.id").value;
					var groupName = document.getElementById("group.groupName").value;
					var groupDesc = document.getElementById("group.groupDesc").value;
					var valObj = mySimpleTree[0].getSelectionsValue(function(
									obj) {
								return !filterOrgType
										|| filterOrgType[$(obj).attr("orgType")];
							});
					if ($.isNull(valObj.value)) {
						$.alert("请先选择数据范围");
						return;
					} else {
						if(isSave){
							return ;
						}
						isSave = true;
						$.commAjax({
									url : $.ctx + '/api/group/save',
									postData :{
										"id":id,
										"groupName":groupName,
										"groupDesc":groupDesc,
										"tree":valObj.value
									},
									onSuccess : function(data) {
										if (data == 'success') {
											$.success('保存成功。', function() {
														dg.cancel();
														dg.reload();
													})
										} else if (data == 'haveSameName') {
											$.alert("数据范围名称重复");
											isSave =false;
										}
									}
								})
					}
				}
			});
	dg.addBtn("cancel", "取消", function() {
				dg.cancel();
			});
}
