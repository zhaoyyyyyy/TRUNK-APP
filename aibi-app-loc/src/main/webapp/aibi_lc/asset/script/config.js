/**
 * 前端配置文件
 */
var _tempctx = "@ctx-loc@";
$.ctx = _tempctx.indexOf("@")>-1 || _tempctx == "/" ? "" : _tempctx,   //获取后端服务器上下文（可以是绝对路径也可以是相对路径）
$.forward = "frame.html",//登录成功跳转路径(可以是相对路径也可以是绝对路径)
$.theme =  window.sessionStorage.theme||'default', //主题样式 default red blue
$.runFreqObj = {day:"日",week :"周",month:"月",quarter:"季"},
$.weekObj  =  {0:"周一",1 :"周二",2:"周三",3:"周四",4 :"周五",5:"周六",6:"周日"},
$.xzqh = 3 //行政区划的常亮
$(".themeBtn").find("option[value='"+window.sessionStorage.theme+"']").attr("selected","selected");
$(".themeBtn").change(function(){
	var temp=$(".themeBtn").val();
	window.sessionStorage.selectedTheme=$(".themeBtn").text();
	$(this).find("option[value='"+temp+"']").attr("selected","selected");
	window.sessionStorage.theme=$(this).find("option:selected").val();
	console.log($(this).find("option:selected").val())
	location.reload() 
	
})