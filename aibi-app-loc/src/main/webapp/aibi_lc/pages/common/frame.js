
//在页面hash改变时加载页面
window.onhashchange = function(){
	loadPage();
}


function loadPage (){
	var hash = window.location.hash;
	var href = "";
	if(hash){
		href = hash.split("#")[1];
		$('title').text($('#accordion a[href=\''+hash+'\']').text());
		$('iframe').attr('src',"../"+href+".html");
	}
} 

function toggleDown(elem,event){
	var e = e||event ;
	e.stopPropagation?e.stopPropagation():e.cancelBubble=true;
	$(elem).toggleClass("open");
}


/**
 * 退出
 */
function exitLoc(){
	var ssg = window.sessionStorage;
	delete ssg.token;
	delete ssg.refreshToken;
	delete ssg.CurrentConfigId;
	window.location = "login.html";
}
/**
 * 进入后台管理
 */
function toAdminConsole(){
	$.commAjax({
		url:'/api/config/springConfig',
		postData:{'key':'jauth-url'},
		onSuccess:function(obj){
			var ssg = window.sessionStorage;
			window.open(obj.data+"#"+ssg.getItem('token'));    
		}
	});
}

//页面初始化加载页面及菜单
window.loc_onload = function(){
	loadPage();
	$(document).click(function(ev){
		var e = ev||event ;
		e.stopPropagation?e.stopPropagation():e.cancelBubble=true;
		$(".ui-login-header").removeClass("open");
	});
	
	//得到用户所拥有的菜单
	$.commAjax({
		url:'/api/user/get',
		onSuccess:function(obj){
			new Vue({ el:'#accordion', data: {resourceList:obj.data.menuResource} });
			new Vue({ el:'#user_opt_ul', data: {username:obj.data.userName} });
			
			//渲染手风琴菜单并显示
			$("#accordion").accordion({
				heightStyle: "content",
				icons:false
			});
			$("#accordion").show();
			
			
			var $accordin = $('#accordion a');
			//点击菜单
			$accordin.click(function(){
				var self = this;
				if(!$(this).parent().parent().is("#accordion")){
					$('#accordion li').removeClass("active");
					$(this).parent().addClass("active");
				}
				//点击一级分类
				if($(self).attr('href').indexOf("#") > -1){
					window.location.hash = $(self).attr('href');
				}
			});
			//点击菜单
			if($accordin && $accordin.length >0){
				$('#accordion a').eq(0).click();
			}
		}
	});
	
	
}
