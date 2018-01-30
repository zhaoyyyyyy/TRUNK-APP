/* 单点登录重写类 */
var auto_Login = (function (model){
        
		model.active  = "jauth"; //jauth  chinapost
        
        /***************默认开饭版本的实现(基于jauth的) ****************/
        model.jauth = {
        		failLoginUrl: "./aibi_lc/pages/common/login.html",
        		autoLoginFun:function(callback){
        			var guid = model._util.getUrlParam("username");
        			return callback(username);
        		}
        }
        
        /***************中邮版本的实现(基于ocrm的) ****************/
        model.chinapost = {
        		failLoginUrl: "http://crm.chinapost.com:18880/login",
        		autoLoginFun:function(callback){
        			var flag = false;
        			
        			 $.ajax({
        				 url:'http://crm.chinapost.com:8442/api/sso/userinfo',
        				 type: 'get',
        				 xhrFields: {
        			            withCredentials: true
        			     },
     					 async: false,
        				 success: function(data){
        					 flag = callback(data.cnpost.username);
        				 }
        			 })
                	return flag;
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
	//存  #label/label_mgr 则走单点登录逻辑
	if(hash){
		href = hash.split("#")[1];
		var ssg = window.sessionStorage;
		if(ssg){
			var token = ssg.getItem("token");
			if(!token){
				if(auto_Login[auto_Login.active].autoLoginFun(auto_Login._util.login)){
					window.location.href = "./aibi_lc/pages/"+ href+ ".html"
				}else{
					window.location.href = auto_Login[auto_Login.active].failLoginUrl
				}
			}
		}
	}else{
		window.location.href = auto_Login[auto_Login.active].failLoginUrl
	}
})