//加载页面方法
function loc_loadPage(){
	var hash = window.location.hash;
	var href = "";
	if(hash){
		href = hash.split("#")[1];
	}else{
		href = $('#accordion a:eq(0)').attr('href').split("#")[1];
	}
	$('iframe').attr('src',"../"+href+".html");
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
	$("#accordion").show();
	
	
	//点击菜单
	$('#accordion a').click(function(){
		var href = $(this).attr("href");
		if(href.indexOf("#") > -1){
			window.location.href = href;
		}
		
		if(!$(this).parent().parent().is("#accordion")){
			$('#accordion li').removeClass("active");
			$(this).parent().addClass("active");
		}
	});
	
	
}
