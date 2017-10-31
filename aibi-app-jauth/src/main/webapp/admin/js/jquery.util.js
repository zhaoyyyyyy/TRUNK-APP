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
	
	//根据维度编码，拿到维度描述
	getCodeDesc : function(codeClass, code) {
		var codes = $.getStatusByCode(codeClass);
		if (codes) {
			for (var i = 0, i_len = codes.length; i < i_len; i++) {
				if (codes[i].code == code) {
					return codes[i].dataName;
				}
			}
		}
		
		return "dic["+codeClass+"]no["+code+"]";
	},
	
	// 根据状态获取数据
	getStatusByCode : function(codeClass) {
		var codes = '';
		
		$.commAjax({
			url : $.ctx + '/api/datadic/dicdatas/query',
			async : false,
			postData : {
				dicCode : codeClass
			},
			onSuccess : function(data) {
				codes = data;
			}
		});
		
		console.log(codes);
		var returnVal = [];
		if (codes) {
			for (var i = 0, i_len = codes.length; i < i_len; i++) {
				//if (codes[i].status == 1) {
					returnVal.push(codes[i]);
				//}
			}
		}
		return returnVal;
	},
	
	//通用异步请求
	commAjax	: function(options, el) {
		options = $.extend({
			url			: '',
			isShowMask	: false,
			type		: 'POST',
			postData	: {},
			beforeSend	: false,
			onSuccess	: false,
			onFailure	: false,
			timeout		: 1800000,
			async		: true,
			checkSubmitted:false,
			maskMassage	: '数据加载中' // 等待提示信息
		}, options);
		
		if(options.checkSubmitted){
			//判断当前是否已经有提交动作，如果有则不提交
			if($.checkRun()){
				return;
			}
			//设置当前提交动作为true
			$.setRun(true);
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
		
		var ssg = window.sessionStorage;
		var tokenStr = "token_null";
		if(ssg){
			var token = ssg.getItem("token");
			if(token){
				tokenStr = token;
			}
		}
		$.ajax({
			headers : {'X-Authorization': tokenStr},
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
				if (/**st == "success" && **/(req.status == 200 || req.status == 0)) { //TODO
					var obj;
					try {
						obj = jQuery.parseJSON(req.responseText);
					} catch (e) {
						obj = req.responseText;
					}
					
					if (obj && obj.success == false) {
						if ($.isFunction(options.onFailure)) {
							try {
								options.onFailure.call(this, obj);
							} catch (e) {
								$.alert("系统无法响应请求，请联系管理员");
							}
						} else if (obj.msg) {
							$.alert(obj.msg);
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
				
				if(options.checkSubmitted){
					//设置当前提交动作标志为false
					$.setRun(false);
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
	
	realPosition : function() {
		var _ownerWin = window;
		var pos = {'left' : 0, 'top' : 0, 'bottom' : 9999999};
		while (_ownerWin != top) {
			var ifs = _ownerWin.parent.document.getElementsByTagName("iframe");
			for (var i = 0; i < ifs.length; i++) {
				try{
					if (ifs[i].contentWindow == _ownerWin) {
						var _pos = ifs[i].getBoundingClientRect();
						pos.left += _pos.left;
						pos.top += _pos.top;
						if(_pos.bottom < pos.bottom){
							pos.bottom = _pos.bottom;
						}
						break;
					}
				}catch(e){}
			}
			_ownerWin = _ownerWin.parent;
		}
		return pos;
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
			// $.getJSON("../data/code.json", function(JSONData) {
			// $.getJSON($.ctx + "/system!listCode.action?codeClasses="
			// + _codeClasses.substring(1), function(data) {
			initFunc(target, $.getStatusByCode(data));
			// });
		} else {
			if (data.indexOf("?") != -1) {
				data += "&unusefulTimestamp=" + (new Date()).getTime();
			} else {
				data += "?unusefulTimestamp=" + (new Date()).getTime();
			}
			
			$.getJSON(data, function(JSONData) {
				initFunc(target, JSONData);
			});
		}
	},
	//初始化多选框
	initCodeComponents : function() {
		$("input[dataDic]").each(function() {
			var $self = $(this);
			var codeClass = $self.attr("dataDic");
			$self.removeAttr("dataDic");
			
			var _codes = [];
			var parentCode = $self.attr("parentCode");
			if (parentCode || parentCode == "") {
				_codes = $.getCodesByParentCode(codeClass, parentCode);
			} else {
				_codes = $.getStatusByCode(codeClass);
				
			}
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
				$el.before("<label for='" + id + "_" + index + "'>&nbsp;"
				        + value + "&nbsp;&nbsp;</label>");
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
				$el.before("<label for='" + id + "_" + index + "'>&nbsp;"
				        + value + "&nbsp;&nbsp;</label>");
			});
			// 删除原来的。
			$el.remove();
		}
	}
});


// 第二部分。
$.fn.extend({
	// 在当前组件上生成蒙板。
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
		$(top).bind('resize.mask', function() {
			$mask.css({
				width	: $self.width(),
				height	: $self.height()
			});
		});
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
		$(top).unbind('resize.mask');
	},
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
				var dg;
				if (frameElement != null) {
					dg = frameElement.lhgDG;
				}
				if (returnStr != '') {
					try {
						$dp.hide();// 日期控件全局变量 将日期控件隐藏
					} catch (e) {
					}
					
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
	},
	getElFitHeight: function(exceptEl){
		var $self = $(this);
		var winPos = {'left' : 0, 'top' : 0, 'bottom' : 9999999};
		var ifs = parent.document.getElementsByTagName("iframe");
		for (var i = 0; i < ifs.length; i++) {
			try{
				if (ifs[i].contentWindow == window) {
					winPos = ifs[i].getBoundingClientRect();
					break;
				}
			}catch(e){}
		}
		var elTop = $self.offset().top;
		var elMarginTop = parseInt($self.css('marginTop')) || parseInt($self.css('paddingTop'));
		if(elMarginTop<0){
			elTop += elMarginTop;
		}else{
			elTop -= elMarginTop;
		}
		
		var expElHeight = 0;
		if(exceptEl){
			var $exceptEl = $("#"+exceptEl);
			expElHeight = $exceptEl.outerHeight();
			var expElMarginTop = parseInt($exceptEl.css('marginTop')) || parseInt($exceptEl.css('paddingTop'));
			if(expElMarginTop<0){
				expElHeight += expElMarginTop;
			}else{
				expElHeight -= expElMarginTop;
			}
		}
		return winPos.bottom-winPos.top-elTop-expElHeight;
	},
	/**
	 * 选择域
	 * @param {} options
	 * @return {}
	 */
	chooseScope : function(options){
        options = $.extend({
             defaultClass :'negative',
             beforeDelete:null,
             afterDelete:null
        },options);
        var thi = this;
     	var values = [];
     	var $scope = $('<div class="buttons" style="border:1px solid #C0C0C0;padding-bottom:5px;"><input id="chooseScope_input"  type="text" style="width:150px;border:0px;font-size:15px;" /></div>');
     	$(thi).append($scope);
	    thi.add = function(id,text,type){
	   		if($.inArray(id,values) == -1){
	   		  var $_btn_span = $('<a href="#" id="chooseScope_tag_a_'+id+'" class="'+options.defaultClass+'">'+text+'<img src="'+$.ctx+'/admin/css/blue/images/cross.png" title="删除" alt=""/></a>');
	   		   $_btn_span.insertBefore($('input',$scope));
	           values.push(id);
	   		  $('img',$_btn_span).click(function(){
	   		  		if(options.beforeDelete){
	   		  			if(options.beforeDelete.call(thi,id,text)==false){
	   		  				return false;
	   		  			};
	   		  		}
	   		  		$_btn_span.remove();
	   		  		values.splice($.inArray(id,values),1);
	   		  		if(options.afterDelete){
	   		  			options.afterDelete.call(thi,id,text);
	   		  		}
	   		  });
	   		}
	     }
	     thi.getValues = function(){
	     	return values;
	     }
	     thi.deleteValue = function(id){
	     	values.splice($.inArray(id,values),1);
	     	$('#chooseScope_tag_a_'+id).remove();
	     	return values;
	     }
	     return thi;
     }
});