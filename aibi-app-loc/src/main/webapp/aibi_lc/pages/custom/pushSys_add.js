var model = {
		sysId:"",//推送平台ID
		tsfszd:"",//推送方式  字典中获取
		curentIndex:false,//radio选中
		descTxt:"",//推送平台描述
		
}
window.loc_onload = function() {
	model.tsfszd=$.getDicData("TSFSZD");
	var sysId = $.getUrlParam("sysId");
	var wd = frameElement.lhgDG;
	new Vue({
		el : '#dataD',
		data : model,
		mounted : function() {
			this.$nextTick(function() {
				var r = $(".easyui-validatebox");
				if (r.length) {
					r.validatebox();
				}
			})
		}
	})

	wd.addBtn("ok", "保存", function() {
		
	});
	wd.addBtn("cancel", "取消", function() {
		wd.cancel();
	});
}
