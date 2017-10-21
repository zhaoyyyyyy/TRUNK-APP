String.prototype.length2 = function() {
	var cArr = this.match(/[^\x00-\xff]/ig);
	return this.length + (cArr == null ? 0 : cArr.length);
};

//(function($) {
//	$.parser = {
//		parse	: function(context) {
//			if ($.parser.defaults.auto) {
//				var r = $(".easyui-validatebox", context);
//				if (r.length)
//					r.validatebox();
//			}
//		}
//	};
//	
//	$.parser.defaults = {
//		auto	: true
//	};
//	
//	$(function() {
//		$.parser.parse();
//	});
//})(jQuery);

/**
 * validatebox - jQuery EasyUI
 * 
 * Licensed under the GPL: http://www.gnu.org/licenses/gpl.txt
 * 
 * Copyright 2010 stworthy [ stworthy@gmail.com ]
 * 
 */
(function($) {
	
	function init(target) {
		$(target).addClass('validatebox-text');
	}
	
	/**
	 * destroy the box, including it's tip object.
	 */
	function destroyBox(target) {
		var tip = $.data(target, 'validatebox').tip;
		if (tip) {
			tip.remove();
		}
		$(target).removeClass('validatebox-invalid').unbind('.validatebox');// add
																			// by
																			// zhaomd
																			// 用于销毁校验时取消其事件的绑定
	}
	
	function bindEvents(target) {
		var box = $(target);
		// var tip = $.data(target, 'validatebox') && $.data(target,
		// 'validatebox').tip;
		
		var time = null;
		box.unbind('.validatebox').bind(
		'focus.validatebox validate.validatebox', function() {
			if (time) {
				clearInterval(time);
			}
			time = setInterval(function() {
				if (target.type != "textarea") {
					validate(target);
				}
			}, 800);
		}
		).bind('blur.validatebox', function() {
			clearInterval(time);
			time = null;
			hideTip(target);
			if (target.type == "textarea") {
				validate(target);
			}
		}).bind('mouseover.validatebox', function() {
			if (box.hasClass('validatebox-invalid')) {
				showTip(target);
			}
			
		}).bind('mouseout.validatebox', function() {
			hideTip(target);
		});
	}
	
	/**
	 * show tip message.
	 */
	function showTip(target) {
		var box = $(target);
		var msg = $.data(target, 'validatebox').message;
		var tip = $.data(target, 'validatebox').tip;
		if (!tip) {
			tip = $('<div class="validatebox-tip">'
			        + '<span class="validatebox-tip-content">' + '</span>'
			        + '<span class="validatebox-tip-pointer">' + '</span>'
			        + '</div>').appendTo('body');
			$.data(target, 'validatebox').tip = tip;
		}
		tip.find('.validatebox-tip-content').html(msg);
		
		var left = box.offset().left + box.outerWidth();
		var $body = $("body").outerWidth();
		var $tip = tip.outerWidth();
		// alert($tip);
		// alert(tip.outerWidth());
		// 当窗体宽度小于输入框加输入框据左边的距离时，提示信息在左边 add by qj 2012-08-22
		if ($body < (left + $tip)) {
			left = box.offset().left - $tip;
			
			tip.children(".validatebox-tip-content").css({
				
				right	: 10
			})
			tip.children().next().addClass("validatebox-tip-pointer1");
			tip.children().next().removeClass("validatebox-tip-pointer");
			
		} else {
			tip.children(".validatebox-tip-content").css({
				left	: 10
				
			})
		}
		// alert(tip.width());
		tip.css({
			display	: 'block',
			left	: left,
			top		: box.offset().top
		})
		
	}
	
	/**
	 * hide tip message.
	 */
	function hideTip(target) {
		var tip = $.data(target, 'validatebox')
		        && $.data(target, 'validatebox').tip;
		if (tip) {
			tip.remove();
			$.data(target, 'validatebox').tip = null;
		}
	}
	
	/**
	 * do validate action
	 */
	function validate(target) {
		if ($(target).hasClass("easyui-validatebox")) {
			var box = $.data(target, 'validatebox');
			if (!box) {
				return;
			}
			try{
                box.removeClass('validatebox-invalid');
            }catch(e){
            	//$(box).removeClass('validatebox-invalid');
                $(target).removeClass('validatebox-invalid');
            }

			hideTip(target);
			
			var opts = box.options;
			var tip = box.tip;
			var box = $(target);
			var value = box.val();
			
			function setTipMessage(msg) {
				
				$.data(target, 'validatebox').message = msg;
			}
			
			// if the box is disabled, skip validate action.
			var disabled = box.attr('disabled');
			if (disabled == true || disabled == 'true') {
				return true;
			}
			
			if (opts.required) {
				if (value == '' || $.trim(value) == '') {
					box.addClass('validatebox-invalid');
					setTipMessage(opts.missingMessage);
					showTip(target);
					return false;
				}
			}
			
			
			
			if (opts.maxlength) {
				if (opts.maxlength == -1) {
					return;
				}
				/** 新增长度验证 add by qiuj 2012-06-26 end * */
				box.removeAttr("maxlength");
				var rule = opts.rules["maxlength"];
				var param = eval(opts.maxlength);
				if (!rule['validator'](value, param)) {
					box.addClass('validatebox-invalid');
					var message = rule['message'];
					message = message.replace(
					        new RegExp("\\{" + 0 + "\\}", "g"), param
					);
					message = message.replace(
					        new RegExp("\\{" + 1 + "\\}", "g"), Math
					                .floor(param * 2)
					);
					
					setTipMessage(opts.invalidMessage || message);
					showTip(target);
					return false;
				}
			}
			if (opts.maxlen) {
				/** 新增长度验证 add by qiuj 2012-06-26 end * */
				box.removeAttr("maxlen");
				var rule = opts.rules["maxlength"];
				var param = eval(opts.maxlen);
				if (!rule['validator'](value, param)) {
					box.addClass('validatebox-invalid');
					var message = rule['message'];
					message = message.replace(
					        new RegExp("\\{" + 0 + "\\}", "g"), param
					);
					message = message.replace(
					        new RegExp("\\{" + 1 + "\\}", "g"), Math
					                .floor(param * 2)
					);
					
					setTipMessage(opts.invalidMessage || message);
					showTip(target);
					return false;
				}
			}
			if (opts.validType) {
				var validaTypes = opts.validType.split(";");
				for (var i = 0; i < validaTypes.length; i++) {
					var result = /([a-zA-Z_]+)(.*)/.exec(validaTypes[i]);
					var rule = opts.rules[result[1]];
					if ((value || result[1] == 'confirm') && rule) {
						var param = eval(result[2]);
						if (!rule['validator'](value, param)) {
							box.addClass('validatebox-invalid');
							var message = rule['message'];
							if (param) {
								for (var i = 0; i < param.length; i++) {
									message = message.replace(new RegExp("\\{"
									                        + i + "\\}", "g"),
									        param[i]
									);
								}
							}
							setTipMessage(opts.invalidMessage || message);
							showTip(target);
							return false;
						}
					}
				}
			}
			
			if(box.is('.validatebox-invalid')){
				box.removeClass('validatebox-invalid');
			}
			hideTip(target);
			return true;
		}
	}
	
	$.fn.validatebox = function(options) {
		if (typeof options == 'string') {
			switch (options) {
				case 'destroy' :
					return this.each(function() {
						destroyBox(this);
					});
				case 'validate' :
					return this.each(function() {
						validate(this);
						// $(this).blur();
				}
					);
				case 'isValid' :
					return validate(this[0]);
					
				case 'bindevents' :
					return bindEvents(this[0]);
			}
		}
		
		options = options || {};
		return this.each(function() {
			var state = $.data(this, 'validatebox');
			if (state) {
				$.extend(state.options, options);
			} else {
				init(this);
				var t = $(this);
				state = $.data(this, 'validatebox', {
					options	: $.extend({}, $.fn.validatebox.defaults, {
						required		: (t.attr('required') ? (t
						        .attr('required') == 'true' || t
						        .attr('required') == true) : undefined),
						validType		: (t.attr('validType') || undefined),
						maxlength		: (t.attr('maxlength') || undefined),
/** 新增长度验证add by qiuj 2012-06-26 end * */
						maxlen			: (t.attr('maxlen') || undefined),
/** 新增长度验证add by qiuj 2012-06-26 end * */
						missingMessage	: (t.attr('missingMessage') || undefined),
						invalidMessage	: (t.attr('invalidMessage') || undefined)
					}, options
					)
				}
				);
			}
			
			bindEvents(this);
		}
		);
	};
	
	$.fn.validatebox.defaults = {
		required		: false,
		validType		: null,
		maxlength		: null,
		missingMessage	: '必填项.',
		invalidMessage	: null,
		
		rules		   : {
			email			  : {
				validator	: function(value) {
					return /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i
					        .test(value);
				},
				message		: '请您输入正确的邮箱地址.'
			},
			url			      : {
				validator	: function(value) {
					return /^(https|http|ftp|rtsp|mms):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i
					        .test(value);
				},
				message		: '请您输入正确的访问地址.'
			},
			passwd			  : {
				validator	: function(value, param) {
					var len = $.strLen(value);
					return len == 0 || (len >= param[0] && len <= param[1]);
				},
				message		: '请输入长度在{0}和{1}之间的密码'
			},
			confirm			  : {
				validator	: function(value, param) {
					return $('#' + param[0]).val() == value;
				},
				message		: 'Value is not matching'
			},
			cardNum			  : {// 身份证号
				validator	: function(idcard) {
					var area = {
						11	: "北京",
						12	: "天津",
						13	: "河北",
						14	: "山西",
						15	: "内蒙古",
						21	: "辽宁",
						22	: "吉林",
						23	: "黑龙江",
						31	: "上海",
						32	: "江苏",
						33	: "浙江",
						34	: "安徽",
						35	: "福建",
						36	: "江西",
						37	: "山东",
						41	: "河南",
						42	: "湖北",
						43	: "湖南",
						44	: "广东",
						45	: "广西",
						46	: "海南",
						50	: "重庆",
						51	: "四川",
						52	: "贵州",
						53	: "云南",
						54	: "西藏",
						61	: "陕西",
						62	: "甘肃",
						63	: "青海",
						64	: "宁夏",
						65	: "xinjiang",
						71	: "台湾",
						81	: "香港",
						82	: "澳门",
						91	: "国外"
					}
					var idcard, Y, JYM;
					var S, M;
					var idcard_array = new Array();
					idcard_array = idcard.split("");
					if (area[parseInt(idcard.substr(0, 2))] == null)
						return false;
					switch (idcard.length) {
						
						case 18 :
							if (parseInt(idcard.substr(6, 4)) % 4 == 0
							        || (parseInt(idcard.substr(6, 4)) % 100 == 0 && parseInt(idcard
							                .substr(6, 4))
							                % 4 == 0)) {
								ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;// 闰年出生日期的合法性正则表达式
							} else {
								ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;// 平年出生日期的合法性正则表达式
							}
							if (ereg.test(idcard)) {
								S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10]))
								        * 7
								        + (parseInt(idcard_array[1]) + parseInt(idcard_array[11]))
								        * 9
								        + (parseInt(idcard_array[2]) + parseInt(idcard_array[12]))
								        * 10
								        + (parseInt(idcard_array[3]) + parseInt(idcard_array[13]))
								        * 5
								        + (parseInt(idcard_array[4]) + parseInt(idcard_array[14]))
								        * 8
								        + (parseInt(idcard_array[5]) + parseInt(idcard_array[15]))
								        * 4
								        + (parseInt(idcard_array[6]) + parseInt(idcard_array[16]))
								        * 2
								        + parseInt(idcard_array[7])
								        * 1
								        + parseInt(idcard_array[8])
								        * 6
								        + parseInt(idcard_array[9]) * 3;
								Y = S % 11;
								M = "F";
								JYM = "10X98765432";
								M = JYM.substr(Y, 1);
								if (M == idcard_array[17])
									return true;
								else
									return false;
							} else
								return false;
							break;
						default :
							return false;
							break;
					}
					
				},
				message		: "请您输入正确的身份证号码."
			},
			postCode			: {// 邮政编码
				validator	: function(value) {
					return /^(\d{6})$/i.test(value);
				},
				message		: "请您正确的输入邮政编码."
			},
			officePhone			: {// 办公电话（[区号]+电话号码+[分机号]）
				validator	: function(value) {
					return /(^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/i
					        .test(value);
				},
				message		: "请您正确的输入办公电话（[区号-]+电话号码+[-分机号]）."
			},
			homePhone			: {// 家庭电话（[区号]+电话号码）
				validator	: function(value) {
					return /(^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8}))$)/i
					        .test(value);
				},
				message		: "请您正确的输入家庭电话（[区号]+电话号码）."
			},
			mobilePhone			: {// 手机
				validator	: function(value) {
					return /^(\d{11})$/i.test(value);
				},
				message		: "请您正确的输入手机号码."
			},
			lxfs			  : {// 联系方式
				validator	: function(value) {
					return /(^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8}))$|^(\d{11})$)/i
					        .test(value);
				},
				message		: "请您正确的输入联系方式."
			},
			specialCharacter	: {
				validator	: function(value) {
					return !/[^\u4e00-\u9fa5\da-zA-Z\-\_]+/.test(value);
				},
				message		: "非法字符。"
			},
			minLength			: {
				validator	: function(value, param) {
					return value.length2() >= param[0];
				},
				message		: '至少要输入{0}个字符'
			},
			/** 新增长度验证add by qiuj 2012-06-26 end * */
			maxlength			: {
				validator	: function(value, param) {
					
					return value.length2() <= param * 2;
				},
				message		: '中文不得超过{0}个字符，英文或数字不得超过{1}个字符'
			},
			/** 数字长度验证 * */
			maxNumLength			: {
				validator	: function(value, param) {
					
					return value.length() <= param;
				},
				message		: '数字不能超过{0}位'
			},
			maxDPLength			: {
				validator	: function(value, param) {
					var pointIndex = value.indexOf(".");
					if (pointIndex == -1) {
						return true;
					}
					var dp = value.substr(pointIndex + 1);
					return dp.length <= param[0];
				},
				message		: '小数点后最多输入{0}位数字'
			},
			alpha			  : {
				validator	: function(value) {
					if (value) {
						return /^(^[A-Za-z]+$)*$/.test(value);
					} else {
						return true;
					}
				},
				message		: '只能输入英文字符.'
			},
			alphanum			: {
				validator	: function(value, param) {
					if (value) {
						return /^(^[A-Za-z0-9]+$)*$/.test(value);
					} else {
						return true;
					}
				},
				message		: '只能输入字母和数字.'
			},
			positiveint			: {
				validator	: function(value, param) {
					if (value) {
						return /^(^[0-9]*[1-9][0-9]*$)*$/.test(value);
					} else {
						return true;
					}
				},
				message		: '只能输入正整数.'
			},
			alphanumUnderline			: {
				validator	: function(value, param) {
					if (value) {
						return /^(^[A-Za-z0-9_]+$)*$/.test(value);
					} else {
						return true;
					}
				},
				message		: '只能输入字母、数字和下划线。'
			},
			zeroPositiveint			: {
				validator	: function(value, param) {
					if (value) {
						return /^(^[0-9]*[0-9][0-9]*$)*$/.test(value);
					} else {
						return true;
					}
				},
				message		: '只能输入0和正整数.'
			},
			numeric			  : {
				validator	: function(value, param) {
					if (value) {
						return /^[0-9]*(\.[0-9]+)?$/.test(value);
					} else {
						return true;
					}
				},
				message		: '只能输入数字.'
			},
			chinese			  : {
				validator	: function(value, param) {
					if (value) {
						return /^[\u4E00-\u9FA5]+$/.test(value); // edit by
																	// qiujie
																	// 只能输入中文
					} else {
						return true;
					}
				},
				message		: '只能输入中文'
			},
			blankVid			: {
				validator	: function(value) {
					if (value) {
						var flag = true;
						var valueMid = "";
						for (var i = 0; i < value.length; i++) {
							if (value.charAt(i) != " ") {
								valueMid = valueMid + value.charAt(i);
							}
						}
						if (valueMid.length == 0) {
							flag = false;
						}
						return flag;
					} else {
						return true;
					}
				},
				message		: '内容不能为空'
			},
			specialCharacterB	: {
				validator	: function(value) {
					return /^[^\|"'<>\[\]\+\{\}\*\-\_\\\\$\?\^#@!&`~·;\/]*$/
					        .test(value);
				},
				message		: "非法字符。"
			},
			// 后台唯一性校验
			uniquenessCheck		: {
				validator	: function(value, param) {
					var values = param[0].split("*");
					var url = values[0];
					var inputObjId = values[1];
					var flag = false;
					$.ajax({
						type	: 'post',
						async	: false,
						url		: jQuery.ctx + url,
						data	: {
							boardName	: function() {
								return $('#' + inputObjId).val();
							}
						},
						success	: function(val) {
							if (val == "false") {
								flag = true;
							}
						},
						error	: function() {
							
						}
					});
					return flag;
				},
				message		: "已存在"
			},
			// 输入框只能输入n位小数m位整数，参数为js对象validType="onlyFloat[8,2]"
			onlyFloat			: {
				validator	: function(value, params) {
					var reg = new RegExp("(^0|(^[1-9][0-9]{0,"
					        + (params[0] - 1) + "}))(\\.\\d{0," + params[1]
					        + "})?$");
					return reg.test(value);
				},
				message		: "输入的值最多为{0}位整数，{1}位小数的正数。"
			},
			// 检测分数是否在范围之内 validType="numeric;intervalNum[0,60]"
			intervalNum			: {
				validator	: function(value, interval) {
					return Number(value) <= Number(interval[1])
					        && Number(interval[0]) <= Number(value)
					        ? true
					        : false;
				},
				message		: "输入的值应该在区间[{0},{1}]之内。"
			},
			// 检测分数是否在范围之内 validtType="inNum[0,60]"
			inNum			  : {
				validator	: function(value, interval) {
					if (/^\d+$/i.test(value) && /^\d+$/i.test(interval[1])) {
						return value <= interval[1] && interval[0] <= value
						        ? true
						        : false;
					} else {
						return false;
					}
				},
				message		: "请输入{0}至{1}之内的整数。"
			}
			
		}
	};
	
})(jQuery);

