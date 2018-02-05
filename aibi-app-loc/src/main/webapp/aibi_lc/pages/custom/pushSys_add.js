var model = {
		sysId:"",//推送平台ID
		sysName:"",//推送平台名称
		descTxt:"",//推送平台描述
		tsfszd:"",//推送方式  字典中获取
		pushType:"",//推送方式
		protocoType:"",//协议类型
		ftpServerIp:"",//IP
		ftpUser:"",//用户名
		ftpPwd:"",//密码
		ftpPath:"",//FTP路径
		ftpPort:"",//端口
		localPath:"",//本地路径
		webserviceWsdl:"",//WSDL
		webserviceTargetnamespace:"",//命名空间
		webserviceMethod:"",//方法
		webserviceArgs:"",//参数
		showInPage:"",//是否推送页面显示
		isNeedXml:"",//是否同时上传xml文件
		isNeedDes:"",//是否需要DES加密
		desKey:"",//加密密钥
		isNeedCompress:false,//是否需要压缩
		compressType:"",//压缩类型
		isAllowAttr:"",//是否允许推送属性
		pushClassName:"",//实现类名
		tableNamePre:"",//推送清单表前缀
		customTaskTable:"",//客户群调度信息表
		curentIndex:false,//radio选中
		isChecked:false,
		isActive:false,
		isShow:false,
		checked:false,
		checkedPushType:false,
		showPushType:"",//显示推送方式
		showDesKey:false,//展示DES加密
		showCompressType:false,//展示压缩类型
		
}
window.loc_onload = function() {
	model.tsfszd=$.getDicData("TSFSZD");
	var sysId = $.getUrlParam("sysId");
	var wd = frameElement.lhgDG;
	if (sysId != null && sysId != "" && sysId != undefined) {
		model.sysId = sysId;
		$.commAjax({
			url : $.ctx + '/api/syspush/sysInfo/get',
			postData : {
				"sysId" : sysId
			},
			onSuccess : function(data) {
				model.sysName = data.data.sysName;
				model.descTxt = data.data.descTxt;
				model.protocoType = data.data.protocoType;
				model.ftpServerIp = data.data.ftpServerIp;
				model.pushType = data.data.pushType;
				model.ftpUser = data.data.ftpUser;
				model.ftpPwd = data.data.ftpPwd;
				model.ftpPath = data.data.ftpPath;
				model.ftpPort = data.data.ftpPort;
				model.localPath = data.data.localPath;
				model.webserviceWsdl = data.data.webserviceWsdl;
				model.webserviceTargetnamespace = data.data.webserviceTargetnamespace;
				model.webserviceMethod = data.data.webserviceMethod;
				model.webserviceArgs = data.data.webserviceArgs;
				model.pushClassName = data.data.pushClassName;
				model.desKey = data.data.desKey;
				model.compressType = data.data.compressType;
				model.tableNamePre = data.data.tableNamePre;
				model.customTaskTable = data.data.customTaskTable;
				if(data.data.pushType =='1'){
					model.showPushType ="文件推送";
					var b =document.getElementById("pushType");
					$(b).prop("checked", true);
					model.curentIndex =true;
				}
				if(data.data.pushType =='2'){
					model.showPushType ="表推送";
					$("input[name='pushType']").prop("checked", true);
					model.curentIndex =true;
				}
			}
		})
	}
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
		},
	})
	wd.addBtn("ok", "保存", function() {
		if ($('#saveDataForm').validateForm()){
			var url_ = "";
			var msss = "";
			if (model.sysId != null && model.sysId != undefined
					&& model.sysId != "") {
				url_ = $.ctx + '/api/syspush/sysInfo/update';
				msss = "修改成功";
			} else {
				$("#sysId").removeAttr("name");
				url_ = $.ctx + '/api/syspush/sysInfo/save';
				msss = "保存成功";
			}
			var dataForm =$('#saveDataForm').formToJson();
			if(dataForm.pushType==""){
				$.alert("请选择推送方式");
			}else{
				if(dataForm.showInPage !=1){
				dataForm.showInPage = 0;
				}
				if(dataForm.isNeedXml !="1"){
					dataForm.isNeedXml = 0;
				}
				if(dataForm.isNeedDes !="1"){
					dataForm.isNeedDes = 0;
				}
				if(dataForm.isNeedCompress !="1"){
					dataForm.isNeedCompress = 0;
				}
				if(dataForm.isAllowAttr !="1"){
					dataForm.isAllowAttr = 0;
				}
				$.commAjax({
					url : url_,
					maskMassage : 'Load...',
					postData : dataForm,
					onSuccess : function(data) {
						if (data.data == "success") {
							$.success(msss, function() {
								wd.cancel();
								wd.reload();
							});
						}
					},
				})
			}
		}
	});
	wd.addBtn("cancel", "取消", function() {
		wd.cancel();
	});
	$("input[name='pushType']").on("click",function(){	//选择推送方式来隐藏或者显示下面内容
		var rdoValue = $("#pushType").is(":checked") ? "文件推送":"表推送";
		if(rdoValue == "文件推送"){
			model.showPushType="文件推送";			
		}else{
			model.showPushType="表推送";
		}
	})
}
function isShowDesKey(obj) {
	if (obj.checked) {
		$("#isNeedDes").val("1");
		model.showDesKey = true;
	}else{
		model.showDesKey = false;
	}
}
function isShowCompressType(obj) {
	if (obj.checked) {
		$("#compressType").val("1");
		model.showCompressType = true;
	}else{
		model.showCompressType = false;
	}
}
