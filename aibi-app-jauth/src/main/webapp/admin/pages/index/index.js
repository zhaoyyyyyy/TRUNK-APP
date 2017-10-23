//$(function() {
//	bindMenuEvent();
//	bindTitleEvent();
//	
//	$('.tit_xker_com:eq(0)').click();
//});

window.jauth_onload = function(){
		
		//$('.tit_xker_com:eq(0)').click();
		
	
		$.commAjax({
			url:$.ctx+'/api/resource/initBackground',
			type:'post',
			cache:false,
			onSuccess:function(data){
				console.log(data);
				new Vue({ el:'#container', data: data });
				bindMenuEvent();
				bindTitleEvent();
			}
		})
		
		
}


/**
 * 菜单事件
 * zhougz
 */
function bindMenuEvent(){
	var $nextXker = null;
		$('.tit_xker_com').click(function(){
			var $clickXker = $(this);
			if($nextXker){
				$nextXker.slideUp('fast',function(){
					$nextXker = $clickXker.next();
					$nextXker.slideDown('fast');
				});
			}else{
				$nextXker = $clickXker.next();
				$nextXker.slideDown('fast');
			}
	});
}

/**
 * 导航事件
 * zhougz
 */
function bindTitleEvent(){
	$('.menuson a').click(function(){
		var $thi = $(this);
		var t1 = $thi.parents('.menuson').prev().text();
		var t2 = $(this).text();
		top.titleUrlArray = {};
		top.titleUrlArray[t2] = $(this).attr('href');
		top.titleUrlArray[t1] = '';
	});
}

/**
 * 页面设置导航
 * zhougz
 */
window.top.titleUrlArray = {};
window.top.getTitleHtml = function(titleObj,hasreturn){
	top.titleUrlArray[titleObj.title] = titleObj.url;
	var html = '';
	$.each(top.titleUrlArray,function(k,v){
		k=k.replace(/\s+/g,"");
		v=v.replace(/\s+/g,"");
		if( k!="" && v!="~~~~~"){
			if(v != ""){
				html +='<li><a href="'+v+'" >'+k+'</a></li>';
			}else{
				html ='<li><a >'+k+'</a></li>'+html;
			}
		}
	});
	
	var returnhtml = ' <div class="place"> <span>位置：</span> <ul class="placeul"><li><a href="#">首页</a></li>'+html;
	if(hasreturn){
  		 returnhtml += '<li><a href="javascript:history.back(-1);top.titleUrlArray[\''+titleObj.title+'\']=\'~~~~~\';">返回</a></li>';
	}  
	 returnhtml +=' </ul> </div>';
	 
	 return returnhtml;
}

