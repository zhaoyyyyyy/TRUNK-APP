(function($) {
	$.fn.extend({
		isBoolean : function(o) {
			return typeof o === 'boolean';
		},
		isObject : function(o) {
			return (o && (typeof o === 'object' || $.isFunction(o))) || false;
		},
		isString : function(o) {
			return typeof o === 'string';
		},
		isNumber : function(o) {
			return typeof o === 'number' && isFinite(o);
		},
		isNull : function(o) {
			return o === null;
		},
		isUndefined : function(o) {
			return typeof o === 'undefined';
		},
		isValue : function (o) {
			return (this.isObject(o) || this.isString(o) || this.isNumber(o) || this.isBoolean(o));
		},
		isEmpty : function(o) {
			if(!this.isString(o) && this.isValue(o)) {
				return false;
			}else if (!this.isValue(o)){
				return true;
			}
			o = $.trim(o).replace(/\&nbsp\;/ig,'').replace(/\&#160\;/ig,'');
			return o==="";	
		},
		tableExpand: function(options) {
			var defaults = {
				listClass : "" ,//表格自定义样式
				url: '',
				ajaxType : "post", //get 、post 两种方式
				datatype: 'json', //ajax 表格返回类型
				ajaxData : {}, //get 、post 两种方式
				gridData:[{
					"key":"",
					"description":"",//无实际意义，仅是补充说明
				}],
				items_per_page:10,  //每页显示记录条数，默认为10条。
				current_page:0,
				pager: 'pager',
				prev_text:"上一页",
		        next_text:"下一页",
		        waterMark:false, //添加水印，
		        waterMarkPicUrl:""
			};
			var _self  = $(this).empty();
			var opts = $.extend(defaults, options);
			    //分页参数动态拼接到ajax参数中
			//是否加水印
			if(opts.waterMark){
				_self.addClass('waterMarkTable');
				_self[0].style.backgroundImage = "url("+ opts.waterMarkPicUrl +")";
			}
		}
	});
})(jQuery);
//拼装grid内容
function getGrid(opts){
	$.ajax({
		url:opts.url,
		type:"post",
		data:opts.ajaxData.data,
		dataType:"json",
		success:function(result){
			opts.callback(result);
		},
		error:function(){
			alert("ajax请求异常！！！");
		}
	});
}




