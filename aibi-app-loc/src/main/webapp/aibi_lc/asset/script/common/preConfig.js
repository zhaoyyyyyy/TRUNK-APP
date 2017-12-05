/*
专区管理
*/
(function($) {
	$.fn.extend({
		preConfig : function(options) {
			var defaults = {
			 	url:"",
			 	type:"post",
			 	select:function(){
			 		
			 	},
			 	create:function(){
			 		
			 	}
			 };
			 var _self = $(this);
			 var html = '<div class="dropdown fright" id="preConfig_list">'
						    +'<a href="javascript:;" class="dropdown-toggle" title="专区管理">'
						    		+'<span class="pre-config-name"></span>'
						    		+'<span class="caret"></span>'
						   +'</a>'
					       +'<ul class="dropdown-menu">'
	//					       +'<li><a href="#">Action</a></li>'
	//					       +'<li><a href="#">Another action</a></li>'
	//					       +'<li><a href="#">Something else here</a></li>'
					       +'</ul>'   
					    +'</div>';
			$("#preConfig_list").remove();
			_self.append(html);
			$.AIPost({
				type:options.type,
				url:options.url,
				onSuccess:function(result){
					var ssg = window.sessionStorage;
					if(ssg && result.data){
					  ssg.setItem("preConfigData",JSON.stringify(result));
					  $("#preConfig_list > .dropdown-toggle > .pre-config-name").html(result.data[0].sourceName).attr("configId",result.data[0].configId);
				  	  options.create(result.data[0]);
					}
				}
			});
			options = $.extend(defaults,options);
			$("#preConfig_list > a.dropdown-toggle").on("click",function(ev){
				var e = ev||event ;
				e.stopPropagation?e.stopPropagation():e.cancelBubble=true;
				var $dropToggle = $(this).parents("#preConfig_list");
				if($dropToggle.hasClass("open")){
					$dropToggle.removeClass("open");
				}else{
					$dropToggle.addClass("open");
					var ssg = window.sessionStorage;
					if(ssg){
						var result = ssg.getItem("preConfigData");
						var data = JSON.parse(result).data;
						var liHtml = '';
						for(var i=0,len=data.length;i<len;i++){
							liHtml+='<li><a href="javascript:;" configId="'+data[i].configId+'">'+data[i].sourceName+'</a></li>';
						}
						$("#preConfig_list > .dropdown-menu").html(liHtml)
						$("#preConfig_list > ul.dropdown-menu li > a").on("click",function(ev){
							var e = ev||event ;
							e.stopPropagation?e.stopPropagation():e.cancelBubble=true;
							var $this = $(this);
							$("#preConfig_list > .dropdown-toggle > .pre-config-name").html($this.text()).attr("configId",$this.attr("configId"));
							$dropToggle.removeClass("open");
							options.select($this);
						});
				    }
				}
			});
			$(document).click(function(ev){
				var e = ev||event ;
				e.stopPropagation?e.stopPropagation():e.cancelBubble=true;
				$("#preConfig_list").removeClass("open");
			});
			
			
			
			
			
			
			
			
			
			
			
		}
	});
})(jQuery);


















