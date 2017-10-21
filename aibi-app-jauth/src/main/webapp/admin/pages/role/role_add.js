window.jauth_onload = function() {
	var id = $.getUrlParam("id");
	if(id!=null){
		$.commAjax({
			url:$.ctx+'/api/role/resource/query',
			postData:{"id":id},
			type:'post',
			cache:false,
			async:false,
			onSuccess:function(data){
				new Vue({ el:'#saveDataForm', data: data });
			}
		});
	}
	var proscenium = "1"; // 前台
	var app = "2"; //APP
	var backstage = "3"; // 后台
	var resourceId = "";
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
	//var url_ = $.ctx + '/api/resource/renderOrgTree?resourceId=' + proscenium + "roleId" + id;
	var url1 = $.ctx + '/api/resource/renderOrgTree';
	$.commAjax({
		url : url1,
		postData:{"resourceId":proscenium},//,"roleId":id
		isShowMask : true,
		type : 'post',
		async:false,
		onSuccess :function(data) {
			$("#sRoot").append(data);
			mySimpleTree = $('#tree').simpleTree({
				autoclose : false,
				nodeCheckBox: true,
				cascadeParentChecked : cascadeParentChecked,
				cascadeChildrenChecked : cascadeChildrenChecked,
				ignoreIndeterminate : false,
				selectValues : selectTrees.split(',')
			});

		},
		onFailure : function() {
		},
		maskMassage : '数据加载中...'
	});
	//后台
	$.commAjax({
		url :url1,
		postData:{"resourceId":backstage},
		isShowMask : true,
		type : 'post',
		async:false,
		onSuccess :function(data) {
			$("#simpleRoot").append(data);
			myTree = $('#simpleTree').simpleTree({
				autoclose : false,
				nodeCheckBox: true,
				cascadeParentChecked : cascadeParentChecked,
				cascadeChildrenChecked : cascadeChildrenChecked,
				ignoreIndeterminate : false,
				selectValues : selectTrees.split(',') 
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
		type : 'post',
		async:false,
		onSuccess : function(data) {
			$("#simpleRootAPP").append(data);
			appTree = $('#appTree').simpleTree({
				autoclose : false,
				nodeCheckBox: true,
				cascadeParentChecked : cascadeParentChecked,
				cascadeChildrenChecked : cascadeChildrenChecked,
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

	dg.addBtn("save", "保存",function() {
		
		if ($('#saveDataForm').validateForm()) {
			var id = document.getElementById("id").value;
			var roleName = document.getElementById("roleName").value;
			var pictureHome = document.getElementById("pictureHome").value;
			var roleDesc = document.getElementById("roleDesc").value;
			var valObj = mySimpleTree[0].getSelectionsValue(function(obj) {
						return !filterOrgType|| filterOrgType[$(obj).attr("orgType")];
			});
			var valObj1 = myTree[0].getSelectionsValue(function(obj) {
				
						return !filterOrgType|| filterOrgType[$(obj).attr("orgType")];
			});
			var valObj2 = appTree[0].getSelectionsValue(function(obj) {
				return !filterOrgType || filterOrgType[$(obj).attr("orgType")];
			});
			if ($.isNull(valObj.value) && $.isNull(valObj1.value) && $.isNull(valObj2.value)) {//
				$.alert("请先选择资源");
				return;
			} else {
					$.commAjax({
					url : $.ctx + '/api/role/save',
					postData : 
							{
								"id":id,
								"roleName":roleName,
								"pictureHome":pictureHome,
								"roleDesc":roleDesc,
								"tree":valObj.value,
								"tree2":valObj1.value,
								"tree3":valObj2.value
							},
					onSuccess : function(data) {
						if (data == 'success') {
							$.success('保存成功。', function() {
								dg.cancel();
								dg.reload();
							})
						} else if (data == 'haveSameName') {
							$.alert("角色名称重复");
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
