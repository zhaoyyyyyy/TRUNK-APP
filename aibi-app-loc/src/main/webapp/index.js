/* 单点登录重写类 */
var auto_Login = (function (model){
        
		model.active  = "jauth"; //jauth  cpyw
        
        /***************默认开饭版本的实现(基于jauth的) ****************/
        model.jauth = {
        		failLoginUrl: "./aibi_lc/pages/common/login.html",
        		autoLoginFun:function(callback){
        			var guid = model._util.getUrlParam("username");
        			return callback(username);
        		}
        }
        
        /***************中邮(yw)版本的实现 ****************/
        model.cpywdev = {
        		failLoginUrl: "http://crm.chinapost.com:18880/login",
        		autoLoginFun:function(callback){
        			var flag = false;
        			
        			//必须与OCRM同域获取spring的acrmUrl
        			//var acrmUrl = auto_Login._util.getSpringConfig("acrm-url");
        			//测试环境没有DNS 前后端先分开写 TODO
        			var acrmUrl = "http://crm.chinapost.com:18880/acrm";
        			
        			 $.ajax({
        				 url: acrmUrl+'/api/sso/userinfo',
        				 type: 'get',
        				 xhrFields: {
        			            withCredentials: true
        			     },
     					 async: false,
        				 success: function(data){
        					 if(data && data.cnpost && data.cnpost.id){
        						 flag = callback(data.cnpost.id);
        					 }
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
        		applyToken : function(username){
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
//  							  var ssg = window.sessionStorage;
//  							  if(ssg){
//  								  ssg.setItem("token",data.token);
//  								  ssg.setItem("refreshToken",data.refreshToken);
//  							  }
  						  }else{
  							  flag = false;
  							  alert(returnObj.msg);
  						  }
  					   }
        			});
        			return flag;
        		},
        		getSpringConfig : function(key){
        			var value = "";
        			$.ajax({
  					  url: $.ctx + "/api/config/springConfig",
  					  type: 'post',
  					  async: false,
  					  data :{"key":key},
  					  success: function(returnObj){
  						 if(returnObj && returnObj.status == '200'){
  							value = returnObj.data;
 						  }else{
 							  alert(returnObj.msg);
 						  }
  					   }
        			});
        			return value;
        		}
        }
        return model;
})(window.autoLogin || {});



/**
 * 如果没有
 */
$(function(){
	var hash = window.location.hash;
	
	if(hash){//存  #label/label_mgr 则走单点登录逻辑
		//获取spring的active配置
		var springActive = auto_Login._util.getSpringConfig("spring.profiles.active");
		if(springActive && springActive != ""){
			auto_Login.active = springActive.replace(/\-/g,"");
		}
		var href = hash.split("#")[1];
//		var ssg = window.sessionStorage;
//		if(ssg){
//			var token = ssg.getItem("token");
			var token = $.getCurrentToken();
			if(!token){
				if(auto_Login[auto_Login.active].autoLoginFun(auto_Login._util.applyToken)){
					window.location.href = "./aibi_lc/pages/"+ href+ ".html"
				}else{
					window.location.href = auto_Login[auto_Login.active].failLoginUrl
				}
			}else{
				window.location.href = "./aibi_lc/pages/"+ href+ ".html"
			}
//		}
	}else{
		window.location.href = auto_Login[auto_Login.active].failLoginUrl
	}
})