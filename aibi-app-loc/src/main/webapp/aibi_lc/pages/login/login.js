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
	var data = formFmt.formToObj($("#login"));
	$.AIPost({
		  url: $.ctx + "/api/user/login",
		  cache:false,
		  data :data,
//		data:{"username":"admin","password":"test1234"},
		  success: function(returnObj){
			  if(returnObj && returnObj.status == '200'){
				  var data = returnObj.data;
				  var ssg = window.sessionStorage;
				  if(ssg){
					  ssg.setItem("token",data.token);
					  ssg.setItem("refreshToken",data.refreshToken);
				  }
				  location.href = $.context+$.loginURL;
			  }else{
				  alert(returnObj.msg);
			  }
		   },
		   error: function(req){
			   var obj;
				try {
					obj = jQuery.parseJSON(req.responseText);
				} catch (e) {
					obj = req.responseText;
				}
		   }
	  });
}