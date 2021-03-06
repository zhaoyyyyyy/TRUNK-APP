
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
 *  导航切换,根据传过来的地址自动跳转
 */
function toggleMenu(toggleUrl,locationHref){
	var $accordin = $('#accordion a');
	// 标签集市和客户群集市返回到客户群集市
	if($accordin && $accordin.length >0){
		$.each($accordin,function(i,val){
			var href = $(this).attr("href");
			if(href === toggleUrl){
				$(this).click(function(){
					if(locationHref){
						window.location =locationHref;
					}
				});
				$(this).trigger("click");
				return false;
			}
			
		});
	}
}

/**
 * 退出
 */
function exitLoc(){
	var ssg = window.sessionStorage;
	delete ssg.token;
	delete ssg.refreshToken;
	delete ssg.CurrentConfigId;
	delete ssg.customId;
	
	var exp  = new Date();  //获得当前时间
	exp.setTime(exp.getTime()-(24*60*60*1000));  //换成毫秒
	if($.ctx){
		document.cookie = "token= " + ";expires=" + exp.toGMTString()+";path="+$.ctx;
	}else{
		document.cookie = "token= " + ";expires=" + exp.toGMTString()+";path=/";
	}
	
	window.location = "login.html";
}
/**
 * 进入后台管理
 */
function toAdminConsole(){
	$.commAjax({
		url: $.ctx+ '/api/config/springConfig',
		postData:{'key':'jauth-url'},
		onSuccess:function(obj){
			var token = $.getCurrentToken();
			window.open(obj.data+"#"+token);    
		}
	});
}

//页面初始化加载页面及菜单
window.loc_onload = function(){
	
	$(document).click(function(ev){
		var e = ev||event ;
		e.stopPropagation?e.stopPropagation():e.cancelBubble=true;
		$(".ui-login-header").removeClass("open");
	});
	
	//得到用户所拥有的菜单
	$.commAjax({
		
		url:$.ctx+'/api/user/get',
		onSuccess:function(obj){
			new Vue({ el:'#accordion', data: {resourceList:obj.data.menuResource} });
			new Vue({ el:'#user_opt_ul', data: {username:obj.data.userName} });
			
			
			var $accordion = $("#accordion");
			//渲染手风琴菜单并显示
			$accordion.accordion({
				heightStyle: "content",
				icons:false
			});
			$accordion.show();
			
			
			var $accordionA = $('a',$accordion);
			//点击菜单
			$accordionA.click(function(){
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
			
			//当单点登录直接跳转到某个页面的时候，回显菜单  frame.html#serviceMonitor/service_monitor_main
			var hash = window.location.hash;
			if(hash){
				$accordionA.eq(0).click();
				var $activeMenuAs = $accordionA.filter('[href=\''+hash+'\']').eq(0);
				if($activeMenuAs.length > 0){
					var $activeMenuA = $activeMenuAs.eq(0);
					var $activeMenuUl = $activeMenuA.parent().parent();
					if(!$activeMenuUl.is("#accordion")){
						$activeMenuUl.parent().prev().click();
					}
					$activeMenuA.click();
				}
			}else if($accordionA && $accordionA.length >0){//点击菜单
				$accordionA.eq(0).click();
			}
		}
	});
	
	
}
