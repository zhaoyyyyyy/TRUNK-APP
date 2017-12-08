//加载页面
loc_loadPage = function(){
	var hash = window.location.hash; 
	if(hash){
		$('iframe').attr('src',"../"+hash.split("#")[1]+".html");
	}
}

//在页面hash改变时加载页面
window.onhashchange = function(){
	loc_loadPage();
}

//页面初始化加载页面及菜单
window.loc_onload = function(){
	loc_loadPage();
	$("#accordion").accordion({
		heightStyle: "content",
		icons:false
	});
	
	$('#accordion a').click(function(){
		var href = $(this).attr("href");
		if(href.indexOf("#") > -1){
			window.location.href = href;
		}
		
		if(!$(this).parent().parent().is("#accordion")){
			$('#accordion li').removeClass("active");
			$(this).parent().addClass("active");
		}
	})
}
