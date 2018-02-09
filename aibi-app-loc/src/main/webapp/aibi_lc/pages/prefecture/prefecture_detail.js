window.loc_onload = function() {
	var configId = $.getUrlParam("configId");
	var wd = frameElement.lhgDG;
	$.commAjax({
		url : $.ctx + '/api/prefecture/preConfigInfo/get',
		postData : {
			"configId" : configId
		},
		isShowMask : true,
		maskMassage : 'Load...',
		onSuccess : function(data) {
			var time = new Date(data.data.invalidTime);
			var y = time.getFullYear();//年
			var m = (time.getMonth()+1<10 ? '0'+(time.getMonth()+1):time.getMonth()+1);//月
			var d = (time.getDate()+1<10 ? '0' +(time.getDate()):time.getDate());//日	
			data.data.invalidTime = y+"年"+m+"月"+d+"日 ";
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
