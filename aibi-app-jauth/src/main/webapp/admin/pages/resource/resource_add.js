window.jauth_onload = function() {
	var id = $.getUrlParam("id");
	if(id!=null){
		$.commAjax({
			url:$.ctx+'/api/resource/get',
			postData:{"id":id},
			type:'post',
			cache:false,
			async:false,
			onSuccess:function(data){
				$.commAjax({
						url:$.ctx+'/api/resource/parentResource/get',
						postData:{"id":id},
						type:'post',
						cache:false,
						async:false,
						onSuccess:function(data1){
							if(data.resource.parentId != null && data.resource.parentId != ""){
								data.resource.parentName = data1.parent.resourceName;
							}
							new Vue({ el:'#saveDataForm', data: data });
						}
				});
			}
		});
	}
	var proscenium = "1"; // 前台
	var app = "2"; //APP
	var backstage = "3"; // 后台
	var roleId = "";
	var mySimpleTree;
	var myTree;
	var appTree;
	// 所选节点
	var selectTrees = $("#selectTrees").val();
	var dg = frameElement.lhgDG;
	dg.removeBtn();
	
	var cascadeParentChecked = true;
	var cascadeChildrenChecked = true;
	var filterOrgType = null;
	
	
	//前台
	var url1 = $.ctx + '/api/resource/renderOrgTree';
	$.commAjax({
		url : url1,
		postData:{"resourceId":proscenium},
		isShowMask : true,
		type : 'POST',
		async:false,
		onSuccess : function(data) {
			$("#sRoot").append(data);
			mySimpleTree = $('#tree').simpleTree({
				autoclose : false,
				cascadeParentChecked : cascadeParentChecked,
				cascadeChildrenChecked : cascadeChildrenChecked,
				afterClick: function(node){
					$('#parentName').val($('span:first', node).text());
					$('#parentId').val(node.attr('id'));
				},
				ignoreIndeterminate : false
			});

		},
		onFailure : function() {
		},
		maskMassage : '数据加载中...'
	});
	//后台
	$.commAjax({
		url : url1,
		postData:{"resourceId":backstage},
		isShowMask : true,
		type : 'POST',
		async:false,
		onSuccess : function(data) {
			$("#simpleRoot").append(data);
			myTree = $('#simpleTree').simpleTree({
				autoclose : false,
				cascadeParentChecked : cascadeParentChecked,
				cascadeChildrenChecked : cascadeChildrenChecked,
				afterClick: function(node){
					$('#parentName').val($('span:first', node).text());
					$('#parentId').val(node.attr('id'));
				},
				ignoreIndeterminate : false
			});

		},
		onFailure : function() {
		},
		maskMassage : '数据加载中...'
	});
	
	
	//app 
	$.commAjax({
		url : url1,
		postData:{"resourceId":app},
		isShowMask : true,
		type : 'POST',
		async:false,
		onSuccess : function(data) {
			$("#simpleRootAPP").append(data);
			appTree = $('#appTree').simpleTree({
				autoclose : true,
				cascadeParentChecked : cascadeParentChecked,
				cascadeChildrenChecked : cascadeChildrenChecked,
				afterClick: function(node){
					$('#parentName').val($('span:first', node).text());
					$('#parentId').val(node.attr('id'));
				},
				ignoreIndeterminate : false
			});
		
		},
		onFailure : function() {
		},
		maskMassage : '数据加载中...'
	});


	var dg = frameElement.lhgDG;
	dg.removeBtn();

	dg.addBtn("save", "保存",
					function() {
						if ($('#saveDataForm').validateForm()) {
								$.commAjax({
									url : $.ctx + '/api/resource/save',
									postData :  $("#saveDataForm").formToJson(),
									onSuccess : function(data) {
										if (data == 'success') {
											$.success('保存成功。', function() {
												dg.cancel();
												dg.reload();
											})
										} else if (data == 'haveSameCode') {
											$.alert("已存在的菜单编码");
										}
									}
								});
						}
					});
	dg.addBtn("cancel", "取消", function() {
		dg.cancel();
	});
}
