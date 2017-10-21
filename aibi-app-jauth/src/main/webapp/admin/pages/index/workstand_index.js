window.jauth_onload = function(){
	$('a[url]').each(function(){
		var $self = $(this);
		$(this).click(function(){
			$.ajax({
				url:$self.attr('url'),
				success:function(){
					$.alert("缓存刷新成功。");
				}
			});		
		});
	});
}