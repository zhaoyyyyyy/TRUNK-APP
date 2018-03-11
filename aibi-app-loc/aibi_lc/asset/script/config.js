/**
 * 前端配置文件
 */
var _tempctx = "/loc";
$.ctx = _tempctx.indexOf("@")>-1 || _tempctx == "/" ? "" : _tempctx,   //获取后端服务器上下文（可以是绝对路径也可以是相对路径）
$.forward = "frame.html",//登录成功跳转路径(可以是相对路径也可以是绝对路径)
$.theme =  "default", //主题样式 default red
$.runFreqObj = {day:"日",week :"周",month:"月",quarter:"季"},
$.weekObj  =  {0:"周一",1 :"周二",2:"周三",3:"周四",4 :"周五",5:"周六",6:"周日"},
$.xzqh = 3 //行政区划的常亮
