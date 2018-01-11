window.loc_onload = function() {
	var wd = frameElement.lhgDG;
	wd.addBtn("ok", "导入", function() {
		$.fileUpload({
			fileElementId: "upfile",    //需要上传的文件域的ID，即<input type="file">的ID。
			url : $.ctx + "/api/source/sourceTableInfo/upload",
			dataType: 'json',
			isShowMask : true,
			maskMassage : 'Load...',
			success: function(data) {   //提交成功后自动执行的处理函数，参数data就是服务器返回的数据。
				if(data.status == "success"){
					wd.addSourceList(data.data);
					$.success(data.msg);
					wd.cancel();
				}else{
					$.alert(data.msg);
				}
				
			},
			error: function(data, status, e) {  //提交失败自动执行的处理函数。
			}
		})
	});
	wd.addBtn("cancel", "取消", function() {
		wd.cancel();
	});
}
