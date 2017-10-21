$(function(){
	$("#loginSubmit").click(function(){
		COCLogin();
		return false;
	});
	$("#loginSubmit,#pwd,#username").keyup(function(e){
		if(e.keyCode == 13){
			COCLogin();
		}
		return false;
	});
	rememb();
	$("#rememb").change(function(){
		if(this.checked){
			var localStorage = window.localStorage;
			if(localStorage){
				localStorage.setItem("username",$("#username").val())
				localStorage.setItem("pwd",$("#pwd").val())
			}
		}else{
			localStorage.removeItem("username");
			localStorage.removeItem("pwd");
		}
	});
});

function rememb(){
	var localStorage = window.localStorage;
	if(localStorage){
		$("#username").val(localStorage.getItem("username"));
		$("#pwd").val(localStorage.getItem("pwd"));
	}
	
}
function COCLogin(){
	//svlada@gmail.com
	var node = JSON.stringify({
		    "username": $("#username").val(),
		    "password": $("#pwd").val()
	});
	$.ajax({
		  url: jQuery.ctx + "/api/auth/login",
		  type:'post',
		  cache:false,
		  contentType:'application/json',
		  dataType:'json',
		  data:node,
		  success: function(data){
			  if(data && data.token){
				  var ssg = window.sessionStorage;
				  ssg.setItem("token",data.token);
				  ssg.setItem("refreshToken",data.refreshToken);
				  location.href = jQuery.ctx+"/admin/pages/index/index.html";
			  }else{
				  alert('用户名/密码错误');
			  }
		   },
		   error: function(req){
			   var obj;
				try {
					obj = jQuery.parseJSON(req.responseText);
				} catch (e) {
					obj = req.responseText;
				}
				alert(obj.message)
		   }
	  });
}