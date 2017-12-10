window.loc_common_init = function (){
	
	//默认专区初始化
	$("h3:eq(0)").preConfig();
	
	
	//初始化下拉框
	$('select[dicCode]').each(function(){
		$self = $(this);
		var dicData = $.getDicData($self.attr('dicCode'));
		
		$self.append('<option value="">--全部--</option>');
		$.each(dicData,function(i,e){
			$self.append('<option value="'+e.code+'">'+e.dataName+'</option>')
		});
	})
	
	//初始化校验组件
	$.parser = {
			defaults:{
				auto:true
			},
			parse	: function(context) {
				if ($.parser.defaults.auto) {
					var r = $(".easyui-validatebox", context);
					if (r.length)
						r.validatebox();
				}
			}
		};
	$.parser.parse();
}