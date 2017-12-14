window.loc_onload = function() {
	var configId = $.getUrlParam("configId");
	var wd = frameElement.lhgDG;
	$.commAjax({
		url : $.ctx + '/api/prefecture/preConfigInfo/get',
		postData : {
			"configId" : configId
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