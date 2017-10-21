window.jauth_onload = function() {
	var id = $.getUrlParam("id")
	$.commAjax({
		url : $.ctx + '/api/user/role/query',
		postData : {
			"id" : id
		},
		type : 'post',
		cache : false,
		async : false,
		onSuccess : function(data) {
			new Vue({
				el : '#formSearch',
				data : data,
				methods : {
					remove : function(id) {
						$('[userId=' + id + ']').remove();
						if ($('span[name=names_span]').each().length < 1) {
							var dg = frameElement.lhgDG;
							dg.cancel();
						}
					}
				}
			});
		}
	});
	// 列表
	var urlShow = $.ctx + '/api/role/rolePage/query';
	var colNames = [ '角色名称', '角色描述', '角色ID' ];
	var colModel = [ {
		name : 'roleName',
		index : 'roleName',
		width : 20,
		align : 'left'
	}, {
		name : 'roleDesc',
		index : 'roleDesc',
		width : 40,
		align : 'center'
	}, {
		name : 'id',
		index : 'id',
		hidden : true,
	} ];
	$("#mainGrid").jqGrid({
		url : urlShow,
		colNames : colNames,
		colModel : colModel,
		multiselect : true,
		rownumbers : true,
		autowidth : true,
		viewrecords : true,
		pager : '#mainGridPager',
		loadComplete : function() {
			var roleId = $("#roleId").val();
			var r = roleId.split(",");
			for (var i = 0; i < r.length; i++) {
				$('#mainGrid').setSelection(r[i]);
			}
		}
	});

	// 查询按钮
	$("#btnSearch").click(function() {
		$("#mainGrid").setGridParam({
			postData : $("#formSearch").formToJson()
		}).trigger("reloadGrid", [ {
			page : 1
		} ]);
	});

	var dg = frameElement.lhgDG;
	dg.removeBtn();

	dg.addBtn("save", "保存", function() {
		var roleId = $('#mainGrid').jqGrid('getGridParam', 'selarrrow');
		var id = "";
		$('input[name=names_input]').each(function() {
			if (id.length > 0) {
				id += ",";
			}
			id += $(this).attr('value');
		});
		var admin = $("#isAdmin").is(':checked');
		if (roleId == '') {
			$.alert("请选择角色");
			return;
		} else {
			$.commAjax({
				url : $.ctx + '/api/user/role/update',
				postData : "id=" + id + "&roleId=" + roleId,
				onSuccess : function(data) {
					if (data == "success") {
						$.success('保存成功。', function() {
							dg.cancel();
							dg.reload();
						})
					}
				}
			})
		}

	});
	dg.addBtn("cancel", "取消", function() {
		dg.cancel();
	});

};
/**
 * 删除节点
 * 
 * @param {}
 *            id
 */
// function fun_delUser(id) {
// alert(id);
// //$('#' + 2).remove();
// $('[userId='+id+']').remove();
// if ($('span[name=names_span]').each().length < 1) {
// var dg = frameElement.lhgDG;
// dg.cancel();
// }
// }
