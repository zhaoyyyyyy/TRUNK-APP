window.loc_onload = function() {
	var labelId = $.getUrlParam("labelId");
	var frwin = frameElement.lhgDG;
	$.commAjax({
		url : $.ctx + '/api/label/labelInfo/get',
		postData : {
			"labelId" : labelId
		},
		onSuccess : function(data) {
			var time = new Date(data.data.invalidTime);
			var y = time.getFullYear();//年
			var m = time.getMonth() + 1;//月
			var d = time.getDate();//日
			data.data.invalidTime = y+"年"+m+"月"+d+"日 ";
			new Vue({
				el : "#dataD",
				data : data
			})
		}
	})
	frwin.addBtn("ok", "保存", function(){
		$.commAjax({
			
		})
	});
	frwin.addBtn("cancel", "取消", function() {
		frwin.cancel();
	});
}

function ssssss(){
	var wd = $.window('专区详情', $.ctx
			+ '/aibi_lc/pages/prefecture/prefecture_detail.html', 800, 800);
	wd.reload = function() {
		$("#mainGrid").setGridParam({
			postData : $("#formSearch").formToJson()
		}).trigger("reloadGrid", [ {
			page : 1
		} ]);
	}
}