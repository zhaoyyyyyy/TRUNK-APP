
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
		
		url:$.ctx+'/api/user/get',
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
