
//在页面hash改变时加载页面
window.onhashchange = function(){
	var hash = window.location.hash;
	var href = "";
	if(hash){
		href = hash.split("#")[1];
		$('title').text($('#accordion a[href=\''+hash+'\']').text());
		$('iframe').attr('src',"../"+href+".html");
	}
}

//页面初始化加载页面及菜单
window.loc_onload = function(){
	
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
			
			//点击菜单
			$('#accordion a').click(function(){
				
				if(!$(this).parent().parent().is("#accordion")){
					$('#accordion li').removeClass("active");
					$(this).parent().addClass("active");
				}
			});
		}
	});
	
	
}
