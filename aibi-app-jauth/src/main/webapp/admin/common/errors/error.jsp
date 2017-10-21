<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>错误提示</title>
<style type="text/css">
html,body,div,h1,p,{margin:0;padding:0;border:0;outline:0;}
.clear {clear: both;display: block;	overflow: hidden;visibility: hidden;width:0;height:0; }
.f_16 { font-size:16px;}
.f_14 { font-size:14px;}
a{ text-decoration:none; color:#005eac;}
a:hover{ text-decoration:underline;}
#box{ width:832px; height:591px; margin:50px auto; background:url(/DJ-ARM/admin/common/errors/error_bg.png) no-repeat;} 
#box h1{ width:auto; height:40px; line-height:40px; padding-left:220px;}
#box p{ line-height:2.0em; padding-left:220px;}
</style>
<script type="text/javascript">
	function hideMask(){
		if(top.hideTopMask){
			top.hideTopMask();
		}
	}
</script>
</head>

<body onload="hideMask()"> 
<div id="box">
	<div class="clear" style="height:166px;"></div>
	<h1 class="f_16">亲爱的用户，你好！ 很抱歉为您带来不便</h1>
    <p class="f_14">
    	造成上述结果的原因可能有以下两种情况：<br />
        (1)您没有访问权限。<br />
        (2)您没有正确访问。<br />
        <a href="javascript:void(0);" onclick="top.location.href='/DJ-ARM/j_spring_security_logout'">请返回登入</a>
    </p>
    
</div>
</body>
</html>
