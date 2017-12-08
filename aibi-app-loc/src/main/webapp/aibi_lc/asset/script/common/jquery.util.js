/**
 * 对jquery能力的扩展。
 * 分成两个部分：
 * 1. 对jquery本身能力的扩展，主要提供一些jquery的函数；使用 $.extend({...});
 * 2. 写一些小组件。使用$.fn.extend({...})扩展。
 */
$.browser={};
$.browser.msie = false;
$.browser.version = '9.0';
// 第一部分。
$.extend({
	
	//拿到地址栏里面的参数
	getUrlParam : function(name){
	     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	     var r = window.location.search.substr(1).match(reg);
	     if(r!=null)return  unescape(r[2]); return null;
	},
	
	//通用异步请求
	commAjax	: function(options, el) {
		options = $.extend({
			url			: '',
			type		: 'POST',
			postData	: {},
			beforeSend	: false,
			onSuccess	: false,
			onFailure	: false,
			timeout		: 1800000,
			async		: true,
			checkSubmitted:false
		}, options);
		
		if(options.checkSubmitted){
			//判断当前是否已经有提交动作，如果有则不提交
			if($.checkRun()){
				return;
			}
		}
		
		var ssg = window.sessionStorage;
		var tokenStr = "token_null";
		if(ssg){
			var token = ssg.getItem("token");
			if(token){
				tokenStr = token;
			}
		}
		$.ajax({
			headers 	: {'X-Authorization': tokenStr},
			url			: options.url,
			type		: options.type,
			data		: options.postData,
			beforeSend	: options.beforeSend,
			timeout		: options.timeout,
			async		: options.async,
			cache		: false,
			complete	: function(req, st) {
				if (options.isShowMask) {
					el.unmask();
				}
				// status：200为服务中成功的状态，0为本地打开时的成功状态
				if ((req.status == 200 || req.status == 0)) { //TODO
					var obj;
					try {
						obj = jQuery.parseJSON(req.responseText);
					} catch (e) {
						obj = req.responseText;
					}
					if (obj && obj.status != 200) {
						if ($.isFunction(options.onFailure)) {
							try {
								options.onFailure.call(this, obj);
							} catch (e) {
								$.alert("系统无法响应请求，请联系管理员");
							}
						} else if (obj.msg) {
							//$.alert(obj.msg);
							alert(obj.msg);
						}
					} else {
						if ($.isFunction(options.onSuccess))
							options.onSuccess.call(this, obj);
					}
				} else if (st == 'error') {
					if(req.status == "404"){
						$.alert('未找到对应请求。');
					}else if(req.status == "401"){
						$.alert('登录超时，点击确认重新登录。',function(){
							 location.href = jQuery.ctx;
						});
					}
				} else if (st == 'timeout') {
					$.alert('连接超时，请刷新后重试。');
				} else {
					$.alert('连接失败，请检查网络。');
				}
			}
		});
	},
	strLen		: function(str) {// 判断字符长度(汉字算三个长度)
		str = $.trim(str);
		var len = 0;
		for (var i = 0; i < str.length; i++) {
			if (str.charCodeAt(i) > 256) {
				len += 3;
			} else {
				len++;
			}
		}
		return len;
	},
	isNull	: function(obj) {
		if (obj == null || (typeof(obj) == 'string' && $.trim(obj) == '')
		        || (typeof(obj) == 'object' && $.isEmptyObject(obj))) {
			return true;
		}
		return false;
	},
	dateFormat : function(date, format){
		if(!format){
			format = 'yyyy-MM-dd';
		}
		var o = {   
	      "M+" : date.getMonth()+1, //month  
	      "d+" : date.getDate(),    //day  
	      "H+" : date.getHours(),   //hour  
	      "m+" : date.getMinutes(), //minute  
	      "s+" : date.getSeconds(), //second  ‘
		  //quarter  
	      "q+" : Math.floor((date.getMonth()+3)/3), 
	      "S" : date.getMilliseconds() //millisecond  
	    }   
	    if(/(y+)/.test(format)) format=format.replace(RegExp.$1,(date.getFullYear()+"").substr(4 - RegExp.$1.length));      
	    for(var k in o){
	   	   if(new RegExp("("+ k +")").test(format)){   
	      	   format = format.replace(RegExp.$1,   
	        	 RegExp.$1.length==1 ? o[k] :    
	          	("00"+ o[k]).substr((""+ o[k]).length));  
	       }
	    } 
	    return format;  
	},
	//气泡提示
	tooltip: function() {
		var defaults = {
			placement : "top" ,//提示占位位置
			title: '默认提示信息',
		}
		 //分页参数动态拼接到ajax参数中
		return this.each(function(){
			var _self = $(this),
		        _placement = (_self.attr("placement") == "undefined" ? "top" :_self.attr("placement")),
		        _title =  (_self.attr("title") == "undefined" ? "" :_self.attr("title")) ,
		        offset = _self.offset(),
                left =  offset.left,
                top = offset.top ,
                selfWid = _self.outerWidth(),
                selfHei = _self.outerHeight(),
                timer = 0;

                var opts = $.extend(defaults, {placement:_placement,title: _title});
		       // console.log(opts.placement);

			var _tooltip = "<div class=\"tooltip "+opts.placement+"\" role=\"tooltip\"> ";
		        _tooltip+= "<div class=\"tooltip-arrow\"></div>";
		        _tooltip+= "<div class=\"tooltip-inner\">"+opts.title+"</div></div>";
			var $tooltip = $(_tooltip);
		        $(document.body).append($tooltip);
			var  tipHei = $tooltip.outerHeight();
			var  tipWid = $tooltip.outerWidth();
                //提示框定位
                if(opts.placement == "top"){
                     $tooltip.css({"left":left+((selfWid-tipWid)/2),"top":top-tipHei});
                }else if(opts.placement == "bottom"){
                     $tooltip.css({"left":left+((selfWid-tipWid)/2),"top":top+selfHei});
                }else if(opts.placement == "left"){
                	 $tooltip.css({"left":(left-tipWid),"top":top+(selfHei-tipHei)/2});
                }else if(opts.placement == "right"){
                	 $tooltip.css({"left":(left+selfWid),"top":top+(selfHei-tipHei)/2});
                }                  
			  $(this).hover(function(){
			       clearTimeout(timer);
			       $tooltip.addClass("in");
			  },function(){
                   timer = setTimeout(function(){
                         $tooltip.removeClass('in');
			       },300)
			  })
		})
	},
	popoverTip:function(option){
		var defaults = {
				placement : "bottom" ,//提示占位位置
				title: false,//'默认提示信息',
				content:"气泡内容",
				left:"-237px",
				top:24
		};
		 var opts = $.extend(defaults, option);
		var _self = $(this).empty(),timer = 0,thisId=$(this).attr("id");
		var tipIcon =$('<i class="ui-info-icon"></i>'); 
		_self.append(tipIcon);
		var popoverHtml = '<div class="popover bottom ui-input-popover"id="'+thisId+'_popover"><div class="arrow"></div><div class="popover-content"><p>'+opts.content+'</p></div></div>'
		_self.append(popoverHtml);
		if(opts.title){
			_self.find(".arrow").after('<h3 class="popover-title">'+opts.title+'</h3>');
		} 
		$(tipIcon).hover(function(){
		       clearTimeout(timer);
		       $("#"+thisId+"_popover").css({
		    	  		left:opts.left,
		    	  		top:opts.top
		      }).show();
		  },function(){
               timer = setTimeout(function(){
	   			   		$("#"+thisId+"_popover").hide();
		       },500)
		  });
	},
	//初始化自定义下拉框
	initCodeComboCustom : function(target, data, type, options) {
		if (!target || !data) {
			return;
		}
		
		var initFunc = function(target, data) {
			var codeHtml = "";
			if ($.isArray(data)) {
				codeHtml = $.parseHTMLForTree(data);
			} else {
				codeHtml = data;
			}
			
			var $self = target;
			var _showIcon = $self.attr("showIcon");
			_showIcon = _showIcon == "true" ? true : false;
			
			var _showClear = $self.attr("showClear");
			_showClear = _showClear == "false" ? false : true;
			
			options = options || {};
			var opts = $.extend({
				multiSelected	: $self.attr('multiSelect') || false,
				treeHtml		: codeHtml,
				async			: false,
				showIcon		: _showIcon,
				showClear		: _showClear,
				ignoreValues	: $self.attr('ignoreValues') || false
			}, options);
			
			var $treeCombo = $self.treecombo(opts);
			
			// 是否冻结
			if ($self.attr('disable')) {
				$treeCombo.triggerfield('disable');
			}
			
			$('body').trigger('initComboOver');
		};
		
		if (type == "local") {
			initFunc(target, data);
		} else if (type == "code") {
			initFunc(target, $.getDicData(data));
		} 
	},
	//初始化多选框
	initCodeComponents : function() {
		$("input[dataDic]").each(function() {
			var $self = $(this);
			var codeClass = $self.attr("dataDic");
			$self.removeAttr("dataDic");
			
			var _codes = [];
			_codes = $.getDicData(codeClass);
			if (!_codes) {
				$.alert(codeClass + "未指定");
				return;
			}
			
			if ($self.is(":text")) {
				initText($self, _codes);
			} else if ($self.is(":radio")) {
				initRadio($self, _codes);
			} else if ($self.is(":checkbox")) {
				initCheckbox($self, _codes);
			}
			
		});
		
		$('body').trigger('initComboOver');
		
		// 初始text输入框
		function initText($el, codes) {
			var codeHtml = $.parseHTMLForTree(codes);
			
			var _showClear = $el.attr("showClear");
			_showClear = _showClear == "false" ? false : true;
			$el.removeAttr("dataDic");
			
			var $treeCombo = $el.treecombo({
				multiSelected	: $el.attr('multiSelect') || false,
				treeHtml		: codeHtml,
				async			: false,
				showClear		: _showClear,
				ignoreValues	: $el.attr('ignoreValues') || false
			});
			// 是否冻结
			if ($el.attr('disable')) {
				$treeCombo.triggerfield('disable');
			}
			if($el.attr('checkBoxDisable')){
				var disVal=$el.attr('checkBoxDisable').split(",")
				for(var i=0;i<disVal.length;i++){
					$("#"+disVal[i]).attr("disabled","true");
				}
			}
		}
		
		// 初始radio输入框
		function initRadio($el, codes) {
			var _name = $el.attr("name");
			var defValue = $el.attr("defValue");
			var id = $el.attr("id");
			var ignoreValues = $el.attr("ignoreValues");
			ignoreValues = ',' + ignoreValues + ',';
			
			$.each(codes, function(index, key_value) {
				var key = key_value.code;
				var value = key_value.dataName;
				if (ignoreValues.indexOf(',' + key + ',') > -1)
					return true;
				
				// clone一个新的checkbox模板
				var _newInput = $el.clone();
				_newInput.attr("name", _name);
				_newInput.attr("value", key);
				_newInput.attr("id", id + "_" + index);
				if (key == defValue) {
					_newInput.attr("checked", true);
				}
				$el.before(_newInput);
				$el.before("<label for='" + id + "_" + index + "'>&nbsp;"  + value + "&nbsp;&nbsp;</label>");
			});
			
			// 删除原来的。
			$el.remove();
		}
		
		// 初始checkbox输入框
		function initCheckbox($el, codes) {
			var _name = $el.attr("name");
			var defValue = "," + $el.attr("defValue") + ",";
			var id = $el.id;
			
			$.each(codes, function(index, key_value) {
				var key = key_value.code;
				var value = key_value.dataName;
				// clone一个新的checkbox模板
				var _newInput = $el.clone();
				_newInput.attr("name", _name);
				_newInput.attr("value", key);
				_newInput.attr("id", id + "_" + index);
				if (defValue.indexOf("," + key + ",") > -1) {
					_newInput.attr("checked", true);
				}
				$el.before(_newInput);
				$el.before("<label for='" + id + "_" + index + "'>&nbsp;" + value + "&nbsp;&nbsp;</label>");
			});
			// 删除原来的。
			$el.remove();
		}
	}
});


// 第二部分。
$.fn.extend({
	validateForm : function() {
		var form = $(this);
		var r = $(".easyui-validatebox", form);
		if (r.length) {
			r.validatebox('validate');
			if ($('.validatebox-invalid:visible', form).length != 0) {
				var returnStr = '';
				$('.validatebox-invalid', form).each(function() {
					var disabled = $(this).attr('disabled');
					if (disabled == true || disabled == 'true') {
						console.log("sss");
					} else {
						// 获取该输入框的中文输入项为什么
						var thmess = $(this).attr("titleText");
						if (!thmess) {
							thmess = $(this).parent().prev("th").text();
						}
						if (!thmess) {
							thmess = $(this).parent().parent().parent().prev("th").text();
						}
						if (!thmess) {
							thmess = $(this).parent().parent().prev("th").text();
						}
						var msg = thmess?thmess  + ':'+ $.data(this, 'validatebox').message:'请按提示输入' + ' : '
						        + $.data(this, 'validatebox').message;
						if (returnStr.indexOf(msg) == -1) {
							returnStr = returnStr + msg + '<br>';
						}
					}
				}
				);
				try {
					var uploadingFileNum1 = uploadingFileNum;
				} catch (e) {
					uploadingFileNum1 = 0;
				}
				if (uploadingFileNum1 > 0) {
					returnStr = returnStr + "当前有正在上传的文件，请稍后提交。" + '<br>';
				}
//				var dg;
//				if (frameElement != null) {
//					dg = frameElement.lhgDG;
//				}
				if (returnStr != '') {
//					try {
//						$dp.hide();// 日期控件全局变量 将日期控件隐藏
//					} catch (e) {}
					if (dg == undefined) {
						parent.$.alert(returnStr, function() {
							if ($('.validatebox-invalid', form).first()[0]
							        .getBoundingClientRect().bottom > 0) {
								$('.validatebox-invalid', form).first().focus();
							}
						});
					} else {
						$.alert(returnStr, function() {
							if ($('.validatebox-invalid', form).first()[0]
							        .getBoundingClientRect().bottom > 0) {
								$('.validatebox-invalid', form).first().focus();
							}
						});
					}
					return false;
				}
			}
		}
		try {
			uploadingFileNum1 = uploadingFileNum;
		} catch (e) {
			uploadingFileNum1 = 0;
		}
		if (uploadingFileNum1 > 0) {
			$.alert("当前有正在上传的文件，请稍后提交。");
			return false;
		}
		return true;
	}
});