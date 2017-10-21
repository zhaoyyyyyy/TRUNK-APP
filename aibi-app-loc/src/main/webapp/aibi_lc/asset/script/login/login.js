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
		$("#rememb").prop("checked",true);
	}
	
}
function COCLogin(){debugger
	var data = formFmt.formToObj($("#login"))
	$.AICreate({
		  url: $.ctx+"/api/auth/login",
		   data: data,
		   success: function(data){
			   
		   } 
	  });
}