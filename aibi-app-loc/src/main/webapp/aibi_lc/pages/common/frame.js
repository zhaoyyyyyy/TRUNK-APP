
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

function toggleDown(elem){
	$(elem).toggleClass("open");
}

function exitLoc(){
	var ssg = window.sessionStorage;
	delete ssg.token;
	delete ssg.refreshToken;
	delete ssg.CurrentConfigId;
	window.location = "login.html";
}

//页面初始化加载页面及菜单
window.loc_onload = function(){
	loadPage();
	
	
	//得到用户所拥有的菜单
	$.commAjax({
		url:'/api/user/resourceMenu/query',
		onSuccess:function(obj){
			new Vue({ el:'#accordion', data: {resourceList:obj.data} });
			
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
