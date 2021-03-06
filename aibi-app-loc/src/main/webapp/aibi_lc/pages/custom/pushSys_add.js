var model = {
		sysId:"",//推送平台ID
		sysName:"",//推送平台名称
		descTxt:"",//推送平台描述
		tsfszd:"",//推送方式  字典中获取
		pushType:1,//推送方式
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
		isNeedTitle:"",//是否允许推送属性
		pushClassName:"",//实现类名
		tableNamePre:"",//推送清单表前缀
		customTaskTable:"",//客户群调度信息表
		curentIndex:0,//radio选中
	//	isChecked:false,
	//	isActive:false,
	//  isShow:false,
		checked:false,
	//  checkedPushType:false,
	//	showPushType:"",//显示推送方式
		showDesKey:false,//展示DES加密
		showCompressType:false,//展示压缩类型
		pushFile:false,//上传xml文件
		pushTitle:false,//推送属性
		pushPage:true,//展开推送页面显示
		
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
				if(data.data.pushType ==1){
//					var b =document.getElementById("pushType");
//					$(b).prop("checked", true);
					model.curentIndex =0;
					if(model.webserviceWsdl){
						model.checked=true;
					}else{
						model.checked=false;
					}
					if(data.data.isNeedDes==1){
						model.showDesKey=true;
					}else{
						model.showDesKey=false;
					}
					if(data.data.isNeedCompress==1){
						model.showCompressType=true;
					}else{
						model.showCompressType=false;
					}
					if(data.data.isNeedXml==1){
						model.pushFile=true;
					}else{
						model.pushFile=false;
					}
					if(data.data.isNeedTitle==1){
						model.pushTitle=true;
					}else{
						model.pushTitle=false;
					}
					if(data.data.showInPage==1){
						model.pushPage=true;
					}else{
						model.pushPage=false;
					}
				}
				if(data.data.pushType ==2){
//					$("input[name='pushType']").prop("checked", true);
					model.curentIndex =1;
				}
			}
		})
	}
	new Vue({
		el : '#dataD',
		data : model,
		methods:{
			select : function(index){
				model.curentIndex=index;
    			if(index==0){
    				model.pushType=1;
    			}else{
    				model.pushType=2;
    			}
    		}
		},
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
				if(dataForm.isNeedXml !=1){
					dataForm.isNeedXml = 0;
				}
				if(dataForm.isNeedDes !=1){
					dataForm.isNeedDes = 0;
				} else {
					var desKeyTmp = String(dataForm.desKey).trim();
					if(desKeyTmp){
						if(desKeyTmp.length < 8){
							$.alert("加密密钥最小长度是8");
							return;
						}else if(desKeyTmp.length > 128){
							$.alert("加密密钥最大长度是128");
							return;
						}
					} else {
						$.alert("请输入加密密钥");
						return;
					}
					dataForm.desKey = desKeyTmp;
				}
				if(dataForm.isNeedCompress !=1){
					dataForm.isNeedCompress = 0;
				}
				if(dataForm.isNeedTitle !=1){
					dataForm.isNeedTitle = 0;
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
}

