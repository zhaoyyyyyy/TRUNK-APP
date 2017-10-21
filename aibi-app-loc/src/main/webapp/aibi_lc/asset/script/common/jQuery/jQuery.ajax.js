
/**
 *  ajax 封装处理
 */
(function(){
	$.extend({
		fetch:function(option){
			var token = null;
			  var defaults = {
				  url: " ",
				   data: {},
				   async:true,
				   dataType:"json",
				   success: function(data){
					   
				   },
				   beforeSend:function (XMLHttpRequest) {
					    // this;// 调用本次AJAX请求时传递的options参数
				   },
				   complete:function (XMLHttpRequest, textStatus) {
					    //this; // 调用本次AJAX请求时传递的options参数
				   },
				   error:function (XMLHttpRequest, textStatus, errorThrown) {
					    // 通常 textStatus 和 errorThrown 之中
					    // 只有一个会包含信息
					    //this; // 调用本次AJAX请求时传递的options参数
					   try{
						   alert("请求出错~！");
						   console.log(errorThrown);
					   }catch(error){}
				   }
			  };
			 var ssg = window.sessionStorage;
			if(ssg){
				token = ssg.getItem("token");
				if(token){
					defaults.headers = {
							'X-Authorization': token
						};
				}
			}
			option = $.extend(defaults,option);
			$.ajax(option);
		} ,
		/****
		 * 创建方法
		 */
		AICreate:function(option){
			  var defaults = {
					  url: " ",
					   data: {},
					   async:true,
					   dataType:"json",
					   type:"POST",
					   success: function(data){
						   
					   } 
				  };
			  option = $.extend(defaults,option);
			$.fetch(option);
		} ,
		/****
		 * 删除方法
		 */
		AIDel:function(option){
			var defaults = {
					url: " ",
					data: {},
					async:true,
					dataType:"json",
					type:"DELETE",
					success: function(data){
						
					} 
			};
			option = $.extend(defaults,option);
			$.fetch(option);
		} ,
		/****
		 * 修改方法
		 */
		AIUpdate:function(option){
			var defaults = {
					url: " ",
					data: {},
					async:true,
					dataType:"json",
					type:"PUT",
					success: function(data){
						
					} 
			};
			option = $.extend(defaults,option);
			$.fetch(option);
		},
		/****
		 * 查询方法
		 */
		AISearch:function(option){
			var defaults = {
					url: " ",
					data: {},
					async:true,
					dataType:"json",
					type:"GET",
					success: function(data){
						
					} 
			};
			option = $.extend(defaults,option);
			$.fetch(option);
		}
	  });
})($);