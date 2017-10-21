window.jauth_onload = function() {
	var id = $.getUrlParam("id")
	$.commAjax({
		url : $.ctx + '/api/user/group/query',
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
	var urlShow = $.ctx + '/api/group/groupPage/query';
	var colNames = [ '数据范围名称', '数据范围描述', '数据范围ID' ];
	var colModel = [ {
		name : 'groupName',
		index : 'groupName',
		width : 20,
		align : 'left'
	}, {
		name : 'groupDesc',
		index : 'groupDesc',
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
			var groupId = $("#groupId").val();
			var g = groupId.split(",");
			for (var i = 0; i < g.length; i++) {
				$('#mainGrid').setSelection(g[i]);
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
		var groupId = $('#mainGrid').jqGrid('getGridParam', 'selarrrow');
		var id = "";
		$('input[name=names_input]').each(function() {
			if (id.length > 0) {
				id += ",";
			}
			id += $(this).attr('value');
		});
		if (groupId == '') {
			$.alert("请选择数据范围");
			return;
		} else {
			$.commAjax({
				url : $.ctx + '/api/user/group/update',
				postData : "id=" + id + "&groupId=" + groupId,
				onSuccess : function(data) {
					if (data == 'success') {
						$.success('保存成功。', function() {
							dg.cancel();
							dg.reload();
						});
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
// $('#' + id).remove();
// if ($('span[name=names_span]').each().length < 1) {
// var dg = frameElement.lhgDG;
// dg.cancel();
// }
// }
