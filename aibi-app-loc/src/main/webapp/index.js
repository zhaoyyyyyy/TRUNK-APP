/* 单点登录重写类 */
var auto_Login = (function (model){
        
		model.active  = "jauth"; //jauth  chinapost
        
        /***************默认开饭版本的实现(基于jauth的) ****************/
        model.jauth = {
        		failLoginUrl: "./aibi_lc/pages/common/login.html",
        		autoLoginFun:function(){
        			var guid = model._util.getUrlParam("guid");
        			return model._util.login(guid);
        		}
        }
        
        /***************中邮版本的实现(基于ocrm的) ****************/
        model.chinapost = {
        		failLoginUrl: "./aibi_lc/pages/common/login.html",
        		autoLoginFun:function(){ 
        			 //如果sessionStroy 里面存在token，则直接返回
        			 //model._util.setCookie("sign","455f029d6cd214cd2a5de2c974b2b804");
        			 //model._util.setCookie("cnpost","%7B%22username%22%3A%2210ADMIN%22%2C%22id%22%3A%222000001%22%2C%22responseMsg%22%3A%22success%22%2C%22orgId%22%3A%2210006404%22%2C%22orgName%22%3A%22%E4%B8%AD%E5%9B%BD%E9%82%AE%E6%94%BF%E9%9B%86%E5%9B%A2%E5%85%AC%E5%8F%B8%22%2C%22distinctId%22%3A%22110000%22%2C%22distinctTypeId%22%3A%2210%22%2C%22serviceCode%22%3A%2200%22%2C%22avatar%22%3A%22%22%2C%22success%22%3Atrue%7D");
        			 
        			 var cookie_cnpost = model._util.getCookie("cnpost");
        			 if(cookie_cnpost != null){
        				var cnpost = decodeURIComponent(cookie_cnpost);
        				var username = JSON.parse(cnpost).username; //拿到用户名
        				return model._util.login(username);
        			 }
                	return false;
        		}
        }
        
        
        //私有的工具类
        model._util = {
        		getUrlParam : function(){ 
        			  var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        			  var r = window.location.search.substr(1).match(reg);
        			  if(r!=null)return  unescape(r[2]); return null;
        		},
        		setCookie : function(name,value){
        			  var Days = 30; 
        			    var exp = new Date(); 
        			    exp.setTime(exp.getTime() + Days*24*60*60*1000); 
        			    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString(); 
        		},
        		getCookie : function(name){
        		    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
        		    if(arr=document.cookie.match(reg))
        		        return unescape(arr[2]); 
        		    else 
        		        return null; 
        		},
        		login : function(username){
        			var flag = true;
        			$.ajax({
  					  url: $.ctx + "/api/user/autoLogin",
  					  type: 'post',
  					  async: false,
  					  data :{"username":username},
  					  success: function(returnObj){
  						  if(returnObj && returnObj.status == '200'){
  							  var data = returnObj.data;
  							  var ssg = window.sessionStorage;
  							  if(ssg){
  								  ssg.setItem("token",data.token);
  								  ssg.setItem("refreshToken",data.refreshToken);
  							  }
  						  }else{
  							  flag = false;
  							  alert(returnObj.msg);
  						  }
  					   }
        			});
        			return flag;
        		}
        }
        return model;
})(window.autoLogin || {});



/**
 * 如果没有
 */
$(function(){
	var hash = window.location.hash;
	var href = "";
	if(hash){//存  #label/label_mgr 则走单点登录逻辑
		href = hash.split("#")[1];
		if(auto_Login[auto_Login.active].autoLoginFun()){
			window.location.href = "./aibi_lc/pages/"+ href+ ".html"
		}else{
			window.location.href = auto_Login[auto_Login.active].failLoginUrl
		}
	}else{
		window.location.href = auto_Login[auto_Login.active].failLoginUrl
	}
})