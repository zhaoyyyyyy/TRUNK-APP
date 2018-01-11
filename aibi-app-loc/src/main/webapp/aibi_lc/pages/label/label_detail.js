var model={
		labelName : "",
		busiCaliber : "",
		failTime : "",
		updateCycle : "",
		applySuggest : "",
		busiLegend : "",
		labelTypeId : "",
}
window.loc_onload = function() {
	var labelId = $.getUrlParam("labelId");
	var frwin = frameElement.lhgDG;
	$.commAjax({
		url : $.ctx + '/api/label/labelInfo/get',
		postData : {
			"labelId" : labelId
		},
		onSuccess : function(data) {
			var time = new Date(data.data.failTime);
			var y = time.getFullYear();//年
			var m = time.getMonth() + 1;//月
			var d = time.getDate();//日
			data.data.failTime = y+"年"+m+"月"+d+"日 ";
			model.labelName = data.data.labelName;
			model.busiCaliber = data.data.busiCaliber;
			model.failTime = data.data.failTime;
			model.applySuggest = data.data.applySuggest;
			model.busiLegend = data.data.busiLegend;
			model.labelTypeId = $.getCodeDesc("BQLXZD",data.data.labelTypeId);
			model.updateCycle = $.getCodeDesc("GXZQZD",data.data.updateCycle);
		}
	})
	frwin.addBtn("cancel", "确定", function() {
		frwin.cancel();
	});
	new Vue({
		el : "#dataD",
		data : model
	})
}