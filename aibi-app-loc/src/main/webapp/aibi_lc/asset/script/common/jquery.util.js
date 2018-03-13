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
	getCookie : function(name){
	    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	    if(arr=document.cookie.match(reg))
	        return unescape(arr[2]); 
	    else 
	        return null; 
	},
	setCurrentToken : function(token,refreshToken){
		if($.getCookie("cnpost") && $.getCookie("cnpost")!=""){
			var Days = 30;   //cookie 将被保存30天
			var exp  = new Date();  //获得当前时间
			exp.setTime(exp.getTime() + Days*24*60*60*1000);  //换成毫秒
			document.cookie = "token="+ token + ";expires=" + exp.toGMTString();
			document.cookie = "cpcnpost="+ encodeURIComponent($.getCookie("cnpost"))+ ";expires=" + exp.toGMTString();
		}else{
			var ssg = window.sessionStorage;
			if(ssg){
				ssg.setItem("token",token);
				ssg.setItem("refreshToken",refreshToken);
			}
		}
		
	},
	isExistsToken : function(){
		var ssg = window.sessionStorage;
		var sstoken = ssg.getItem("token");
		
		var cktoken=$.getCookie("token");
		
		var tokenStr;
		if(sstoken){
			tokenStr = sstoken;
		}else if(cktoken){
			tokenStr = cktoken;
		}
		if(!tokenStr){
			return false;
		}else{
			return true;
		}
	},
	getCurrentToken : function(){
		var ssg = window.sessionStorage;
		var sstoken = ssg.getItem("token");
		
		var cktoken=$.getCookie("token");
		
		var tokenStr;
		if(sstoken){
			tokenStr = sstoken;
		}else if(cktoken){
			tokenStr = cktoken;
		}
		return tokenStr;
	},
	
	//通用异步请求
	commAjax	: function(options, el) {
		options = $.extend({
			url			: '',
			//isShowMask	: true,
			type		: 'POST',
			postData	: {},
			beforeSend	: false,
			onSuccess	: false,
			onFailure	: false,
			timeout		: 1800000,
			async		: true,
			checkSubmitted:false,
			//maskMassage	: '数据加载中' // 等待提示信息
		}, options);
		
		if(options.checkSubmitted){
			//判断当前是否已经有提交动作，如果有则不提交
			if($.checkRun()){
				return;
			}
		}
		
		if (!el) {
			el = $('body');
		}
		if (options.isShowMask && el.length > 0) {
			el.mask({
				top		: el.offset().top,
				left	: el.offset().left,
				width	: el.width(),
				height	: el.height(),
				message	: options.maskMassage
			});
		}
		
		var tokenStr=$.getCurrentToken();
		
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
//							$.message(obj.msg);
							$.alert(obj.msg);
							try {console.log(obj.execption);} catch (e) {}
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
							 location.href = $.ctx ? $.ctx : "/";
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
	}
});


// 第二部分。
$.fn.extend({
	mask : function(options) {
		var $self = $(this), $mask, $maskText;
		options = $.extend({
			autoShow	: true,
			id			: 'massk',
			left		: $self.offset().left
			        + parseInt(($self.css('padding-left') || 0).replace(
			                'px', ''
			        )),
			top			: $self.offset().top
			        + parseInt(($self.css('padding-top') || 0).replace(
			                'px', ''
			        )),
			width		: $self.width() + 2, // 宽度
			height		: $self.height() + 2, // 高度
			message		: '数据加载中', // 提示内容
			showMessage	: true
			// 提示内容
		}, options);
		
		this.init = function() {
			$mask = $('<div class="window-mask"></div>').attr('id',
			        options.id
			).appendTo(this);
			$mask.css({
				top				: options.top,
				left			: options.left,
				zIndex			: 99998,
				width			: options.width,
				height			: options.height,
				'line-height'	: options.height + 'px',
				display			: 'none'
			});
			if (options.showMessage) {
				$maskText = $('<div class="window-text"></div>').attr('id',
				        options.id + '_text'
				).appendTo(this);
				$maskText.css({
					top		: (options.top + options.height / 2 - 60 / 2),
					left	: options.left + options.width / 2 - 340 / 2,
					zIndex	: 99999,
					display	: 'none'
				});
			}
		};
		this.show = function() {
			$mask.show();
			if ($maskText)
				$maskText.show();
		};
		this.hide = function() {
			$mask.hide();
			if ($maskText)
				$maskText.hide();
		};
		this.remove = function() {
			$mask.remove();
			if ($maskText)
				$maskText.remove();
		};
		this.changeText = function(text) {
			if ($maskText)
				$maskText.html(text + '...');
		};
//		$(top).bind('resize.mask', function() {
//			$mask.css({
//				width	: $self.width(),
//				height	: $self.height()
//			});
//		});
		this.init();
		if (options.autoShow)
			this.show();
		this.changeText(options.message);
		return this;
	},
	unmask	: function(id) {
		var unmaskId = id || 'massk';
		$('#' + unmaskId, this).remove();
		$('#' + unmaskId + '_text', this).remove();
//		$(top).unbind('resize.mask');
	},
	validateForm : function() {
		var form = $(this);
		var r = $(".easyui-validatebox", form);
		if (r.length) {
			r.validatebox();
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
				if (returnStr != '') {
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