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
		
}
window.loc_onload = function() {
	model.tsfszd=$.getDicData("TSFSZD");
	var sysId = $.getUrlParam("sysId");
	var wd = frameElement.lhgDG;
	model.sysId = sysId;
	$.commAjax({
		url : $.ctx + '/api/syspush/sysInfo/get',
		postData : {
			"sysId" : sysId
		},
		onSuccess : function(data) {
			model.sysName = data.data.sysName;
			model.descTxt = data.data.descTxt;
			model.protocoType = $.getCodeDesc("XYLXZD",data.data.protocoType);
			model.ftpServerIp = data.data.ftpServerIp;
			model.pushType = $.getCodeDesc("TSFSZD",data.data.pushType);
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
			model.compressType = $.getCodeDesc("YSLXZD",data.data.compressType);
			model.tableNamePre = data.data.tableNamePre;
			model.customTaskTable = data.data.customTaskTable;
			model.showInPage = data.data.showInPage;
			model.isNeedXml = data.data.isNeedXml;
			model.isNeedDes = data.data.isNeedDes;
			model.isNeedCompress = data.data.isNeedCompress;
			model.isAllowAttr = data.data.isAllowAttr;
			if(data.data.pushType=="1"){
				$("#filePush").show();
				$("#labelPush").hide();			
			}else{
				$("#filePush").hide();
				$("#labelPush").show();	
			}
		}
	})
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
	wd.addBtn("cancel", "确定", function() {
		wd.cancel();
	});
}