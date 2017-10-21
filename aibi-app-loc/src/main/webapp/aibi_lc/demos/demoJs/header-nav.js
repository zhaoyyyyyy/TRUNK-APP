$(function(){
	/* 导航地图-鼠标经过-离开 */
	$('.J_navMapBtn,.J_navMapShow').mouseover(function(){
		$('.J_navMapShow').stop().slideDown('fast');
		$('.J_navMapBtn').addClass('active');
	});
	$('.J_navMapBtn,.J_navMapShow').mouseout(function(){
		$('.J_navMapShow').stop().slideUp('fast');
		$('.J_navMapBtn').removeClass('active');
	});
	/* 我的消息 */
	$('.J_messageBtn,.J_messageShow').mouseover(function(){
		$('.J_messageShow').stop().slideDown('fast');
		$('.J_messageBtn').addClass('active');
	});
	$('.J_messageBtn,.J_messageShow').mouseout(function(){
		$('.J_messageShow').stop().slideUp('fast');
		$('.J_messageBtn').removeClass('active');
	});
	/* 我的收藏 */
	$('.J_mycollectBtn,.J_mycollectShow').mouseover(function(){
		$('.J_mycollectShow').stop().slideDown('fast');
		$('.J_mycollectBtn').addClass('active');
	});
	$('.J_mycollectBtn,.J_mycollectShow').mouseout(function(){
		$('.J_mycollectShow').stop().slideUp('fast');
		$('.J_mycollectBtn').removeClass('active');
	});
	/* 切换专区*/
	$('.J_changeareaBtn,.J_changeareaShow').mouseover(function(){
		$('.J_changeareaShow').stop().slideDown('fast');
		$('.J_changeareaBtn').addClass('active');
	});
	$('.J_changeareaBtn,.J_changeareaShow').mouseout(function(){
		$('.J_changeareaShow').stop().slideUp('fast');
		$('.J_changeareaBtn').removeClass('active');
	});
	/* 浮动搜索框点击切换*/
	$('.J_fixedSearchItemBtn').click(function(){
		var hasActive = $(this).hasClass('active');
		if(hasActive){
			$('.J_fixedSearchItemShow').stop().slideUp('fast');
			$('.J_fixedSearchItemBtn').removeClass('active');
		}else{
			$('.J_fixedSearchItemShow').stop().slideDown('fast');
			$('.J_fixedSearchItemBtn').addClass('active');
		}
	});
	//显示或隐藏浮动搜索框
	$(window).scroll(function(){ 
		 var h=$(this).scrollTop();//获得滚动条距top的高度
		 if(h>50){
		     $(".J_fixedSearchBox").removeClass('hidden');
		}else{
		    $(".J_fixedSearchBox").addClass('hidden');
		}
	}) 
})
