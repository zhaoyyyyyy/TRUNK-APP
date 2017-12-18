window.loc_onload = function() {
	var dimId = $.getUrlParam("dimId");
	var wd = frameElement.lhgDG;
	$.commAjax({
		url : $.ctx + '/api/dimtable/dimTableInfo/get',
		postData : {
			"dimId" : dimId
		},
		onSuccess : function(data) {
			new Vue({
				el : "#dataD",
				data : data
			})
		}
	})
	wd.addBtn("cancel", "取消", function() {
		wd.cancel();
	});
}