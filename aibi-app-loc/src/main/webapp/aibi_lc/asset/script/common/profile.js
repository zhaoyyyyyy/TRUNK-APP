/**
 * 前端配置文件
 */
(function(){
	$.extend({ 
		ctx:"http://10.1.48.18:8441",//获取后端服务器上下文和端口号等
		forward:"http://127.0.0.1:8020/aibi_lc/pages/label/label-market.html",//登录成功跳转路径
		loginURL:"http://127.0.0.1:8020/aibi_lc/pages/login/login.html",
		context:"http://127.0.0.1:8020",//前端服务器
		runFreqObj:{day:"日",week :"周",month:"月",quarter:"季"},
		weekObj : {0:"周一",1 :"周二",2:"周三",3:"周四",4 :"周五",5:"周六",6:"周日"}
	  });
})($);