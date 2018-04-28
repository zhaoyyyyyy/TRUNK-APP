function autoLogin(){
	applyToken("sfadmin");
	window.location.href = 'http://10.1.235.181:8441/aibi_lc/pages/common/frame.html#label/label_market';
}

function applyToken(username){
		var flag = true;
		$.ajax({
			  url: $.ctx + "/api/user/applyToken",
			  type: 'post',
			  async: false,
			  data :{"username":username},
			  success: function(returnObj){
				  if(returnObj && returnObj.status == '200'){
					  var data = returnObj.data;
					  $.setCurrentToken(data.token,data.refreshToken);
				  }else{
					  flag = false;
					  alert(returnObj.msg);
				  }
			   }
		});
		return flag;
}