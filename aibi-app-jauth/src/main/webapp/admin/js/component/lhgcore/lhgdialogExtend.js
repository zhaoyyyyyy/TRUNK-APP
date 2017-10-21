/*
 * dialog, confirm, alert author: qiany date: 2011.04.26
 * 
 */

// var alertDG;
$.fn.extend({
	
	/*
	 * function: 确认窗口 parameter: 确认信息文本， 确认后执行的方法， 取消后执行的方法，窗口宽度， 窗口高度，
	 * 触发确认窗口的事件
	 * 
	 */

	confirm	: function(content, callback, cancel, width, height, event, cfg) {
		
		// 设置参数
		content = content || '确定继续吗？'; // 确认信息文本
		callback = callback || $.noop; // 确认后执行的方法
		cancel = cancel || $.noop; // 取消后执行的方法
		width = width || 300; // 窗口宽度
		height = getHeight(content, height); // 窗口高度
		event = event || 'click'; // 触发确认窗口的事件
		
		cfg = $.extend({
			sureText	: "确定",
			cancelText	: "取消"
		}, cfg);
		
		// 绑定弹出窗口事件
		$(this).live(event, function() {
			var obj = this;
			obj.callback = callback;
			obj.cancel = cancel;
			
			var dg = frameElement;
		if (parent.frameElement != null && frameElement.lhgDG == undefined) {
			dg = parent.frameElement.lhgDG;
		} else {
			dg = frameElement ? frameElement.lhgDG : null;
		}
			var confirmDG;
			var id = getDgId('confirm');
			
			if (dg) {
				confirmDG = new dg.curWin.J.dialog({
					        id		: id,
					        title	: '确认信息',
					        width	: width,
					        height	: height,
					        html	: '<div class="alert-question">' + content
					                + '</div>',
					        resize	: false,
					        cover	: true,
					        rang	: true,
					        btns	: true,
					        parent	: dg
				        });
			} else {
				confirmDG = new J.dialog({
					        id		: 'confirmDG',
					        title	: '确认信息',
					        width	: width,
					        height	: height,
					        html	: '<div class="alert-question">' + content
					                + '</div>',
					        resize	: false,
					        cover	: true,
					        rang	: true,
					        btns	: true
				        });
			}
			
			confirmDG.ShowDialog();
			confirmDG.addBtn('confirm', cfg.sureText, function() {
				confirmDG.cancel();
				obj.callback();
			});
			confirmDG.addBtn('cancel', cfg.cancelText, function() {
				confirmDG.cancel();
				obj.cancel();
			});
			
			// 取消链接的跳转事件
			return false;
		}
		);
	}
});

jQuery.extend({
	
	/*
	 * function: 弹出窗口 parameter: 窗口标题， 窗口内容页面url， 窗口宽度， 窗口高度
	 * 
	 */

	dialog	: function(title, url, width, height, otherParams, manualOpenDialog) {
		var dg = frameElement;
		if (parent.frameElement != null && frameElement.lhgDG == undefined) {
			dg = parent.frameElement.lhgDG;
		} else {
			dg = frameElement ? frameElement.lhgDG : null;
		}
		var dialogDG;
		var id = getDgId('dialog');
		
		title = title || '标题'; // 窗口标题
		url = url || ''; // 窗口内容页面url
		width = width || 800; // 窗口宽度
		height = height || 500; // 窗口高度
		
		otherParams = otherParams ? otherParams : {};
		otherParams = $.extend({
			id			 : id,
			fixed			: false,
			left			: 'center',
			top			 : 'center',
			rang			: true,
			resize			: false,
			drag			: true,
			cover			: true,
			btns			: true,
			xButton			: true,
			onXclick		: null,
			SetTopWindow	: top.window
			
		}, otherParams);
		
		if (dg) {
			dialogDG = new dg.curWin.J.dialog({
				        id				: otherParams.id,
				        title			: title,
				        width			: width,
				        height			: height,
				        page			: url,
				        fixed			: otherParams.fixed,
				        left			: otherParams.left,
				        top				: otherParams.top,
				        rang			: otherParams.rang,
				        resize			: otherParams.resize,
				        drag			: otherParams.drag,
				        cover			: otherParams.cover,
				        btns			: otherParams.btns,
				        xButton			: otherParams.xButton,
				        SetTopWindow	: otherParams.SetTopWindow,
				        onXclick		: otherParams.onXclick,
				        parent			: dg
			        });
		} else {
			dialogDG = new J.dialog({
				        id				: otherParams.id,
				        title			: title,
				        width			: width,
				        height			: height,
				        page			: url,
				        fixed			: otherParams.fixed,
				        left			: otherParams.left,
				        top				: otherParams.top,
				        rang			: otherParams.rang,
				        resize			: otherParams.resize,
				        drag			: otherParams.drag,
				        cover			: otherParams.cover,
				        xButton			: otherParams.xButton,
				        onXclick		: otherParams.onXclick,
				        SetTopWindow	: otherParams.SetTopWindow,
				        btns			: otherParams.btns
			        });
		}
		
		if (!manualOpenDialog) {
			dialogDG.ShowDialog();
		}
		
		return dialogDG; // todo: 由于返回时，内容页尚未加载完毕，dialogDG.dgDoc的值不正确。
	},
	
	/*
	 * function: 确认窗口 parameter: 确认信息文本，确认后执行的方法，取消后执行的方法，窗口宽度，窗口高度
	 * 
	 */

	confirm	: function(content, callback, cancel, width, height, cfg) {
		$("body").focus();
		var dg = frameElement;
		if (parent.frameElement != null && frameElement.lhgDG == undefined) {
			dg = parent.frameElement.lhgDG;
		} else {
			dg = frameElement ? frameElement.lhgDG : null;
		}
		var confirmDG;
		var id = getDgId('confirm');
		
		content = content || ''; // 确认信息文本
		callback = callback || $.noop; // 确认后执行的方法
		cancel = cancel || $.noop; // 取消后执行的方法
		width = width || 300; // 窗口宽度
		height = getHeight(content, height); // 窗口高度
		
		cfg = $.extend({
			sureText	: "确定",
			cancelText	: "取消"
		}, cfg);
		
		if (dg) {
			confirmDG = new dg.curWin.J.dialog({
				        id				: id,
				        title			: '确认信息',
				        width			: width,
				        height			: height,
				        html			: '<div class="alert-question">'
				                + content + '</div>',
				        resize			: false,
				        cover			: true,
				        rang			: true,
				        btns			: true,
				        xButton			: false,
				        SetTopWindow	: top.window,
				        parent			: dg
			        });
		} else {
			confirmDG = new J.dialog({
				        id				: id,
				        title			: '确认信息',
				        width			: width,
				        height			: height,
				        html			: '<div class="alert-question">'
				                + content + '</div>',
				        resize			: false,
				        cover			: true,
				        rang			: true,
				        xButton			: false,
				        SetTopWindow	: top.window,
				        btns			: true
			        });
		}
		
		confirmDG.ShowDialog();
		
		confirmDG.addBtn('confirm', cfg.sureText, function() {
			confirmDG.cancel();
			callback();
		});
		confirmDG.addBtn('cancel', cfg.cancelText, function() {
			confirmDG.cancel();
			cancel();
		});
	},
	
	/*
	 * function: 提示窗口 parameter: 提示信息文本， 窗口宽度， 窗口高度， 确认后执行的方法, 提示类型
	 * 
	 */

	alert	: function(content, width, height, callback, type) {
		$("body").focus();
		var dg = frameElement;
		if (parent.frameElement != null && frameElement.lhgDG == undefined) {
			dg = parent.frameElement.lhgDG;
		} else {
			dg = frameElement ? frameElement.lhgDG : null;
		}
		
		var alertDG;
		var id = getDgId('alert');
		
		// 如果没有content、width和height参数
		if (typeof(content) == 'function') {
			callback = content;
			content = '';
		}
		
		// 如果没有width和height参数
		if (typeof(width) == 'function') {
			callback = width;
			width = 0;
		}
		
		// 如果没有height参数
		if (typeof(height) == 'function') {
			callback = height;
			height = 0;
		}
		
		content = content || ''; // 提示信息文本
		width = width || 300; // 窗口宽度
		height = getHeight(content, height); // 窗口高度
		callback = callback || $.noop; // 确认后执行的方法
		type = type || 'alert-alarm';
		
		if (dg) {
			alertDG = new dg.curWin.J.dialog({
				        id				: id,
				        title			: '提示信息',
				        width			: width,
				        height			: height,
				        html			: '<div class="' + type + '"></div>',
				        resize			: false,
				        cover			: true,
				        rang			: true,
				        btns			: true,
				        xButton			: false,
				        SetTopWindow	: top.window,
				        parent			: dg
			        });
		} else {
			alertDG = new J.dialog({
				        id				: id,
				        title			: '提示信息',
				        width			: width,
				        height			: height,
				        html			: '<div class="' + type + '"></div>',
				        resize			: false,
				        cover			: true,
				        rang			: true,
				        xButton			: false,
				        SetTopWindow	: top.window,
				        btns			: true
			        });
		}
		
		alertDG.ShowDialog();
		$('.' + type, alertDG.topDoc).html(content);
		
		alertDG.addBtn('confirm', '确定', function() {
			alertDG.cancel();
			callback();
		});
		
		// 关闭按钮也执行回调函数
		$('#' + id, top.document).find('#lhgdig_xbtn').click(callback);
	},
	message	: function(title, content, width, height, callback, type) {
		var dg = frameElement;
		if (parent.frameElement != null && frameElement.lhgDG == undefined) {
			dg = parent.frameElement.lhgDG;
		} else {
			dg = frameElement ? frameElement.lhgDG : null;
		}
		var alertDG;
		var id = getDgId('message');
		
		// 如果没有content、width和height参数
		if (typeof(content) == 'function') {
			callback = content;
			content = '';
		}
		
		// 如果没有width和height参数
		if (typeof(width) == 'function') {
			callback = width;
			width = 0;
		}
		
		// 如果没有height参数
		if (typeof(height) == 'function') {
			callback = height;
			height = 0;
		}
		
		content = content || ''; // 提示信息文本
		width = width || 300; // 窗口宽度
		height = getHeight(content, height); // 窗口高度
		callback = callback || $.noop; // 确认后执行的方法
		type = type || 'alert-message';
		title = title || '提示信息'; // 窗口标题
		
		if (dg) {
			alertDG = new dg.curWin.J.dialog({
				        id				: id,
				        title			: title,
				        width			: width,
				        height			: height,
				        html			: '<div class="' + type + '">adf</div>',
				        resize			: false,
				        cover			: true,
				        rang			: true,
				        btns			: true,
				        xButton			: false,
				        SetTopWindow	: top.window,
				        parent			: dg
			        }
			);
		} else {
			alertDG = new J.dialog({
				        id				: id,
				        title			: title,
				        width			: width,
				        height			: height,
				        html			: '<div class="' + type
				                + '">adsf</div>',
				        resize			: false,
				        cover			: true,
				        rang			: true,
				        xButton			: false,
				        SetTopWindow	: top.window,
				        btns			: true
			        });
		}
		
		alertDG.ShowDialog();
		$('.' + type, alertDG.topDoc).html(content);
		
		alertDG.addBtn('confirm', '确定', function() {
			alertDG.cancel();
			callback();
		});
	},
	
	/* 错误提示窗 */
	err	    : function(content, width, height, callback) {
		$("body").focus();
		$.alert(content, width, height, callback, 'alert-error');
	},
	
	/* 成功提示窗 */
	success	: function(content, width, height, callback) {
		$("body").focus();
		$.alert(content, width, height, callback, 'alert-success');
	}
	
}
);

/*
 * function: 生成一个新的窗口id arguments: id首单词
 * 
 */

function getDgId(initial) {
	
	var id;
	initial = initial || 'dialog';
	
	do {
		id = initial + Math.floor(Math.random() * 10000);
	} while ($('#' + id, parent.document).length > 0);
	
	return id;
}

function getParent(obj) {
	var tempobj = parent.obj;
	// if(tempobj.lhgDG == undefined){
	// tempobj = getParent(tempobj);
	// }
	return tempobj;
}

function cancleKeyDown(evt) {
	evt = (evt) ? evt : ((window.event) ? window.event : "");
	var keyCode = evt.keyCode ? evt.keyCode : evt.which;
	
	var obj = frameElement;
	if (obj.lhgDG == undefined) {
		obj = getParent(frameElement);
	}
	
	var closeFlg = true;
	try {
		var calendar = obj.lhgDG.curWin.J.cache;
		
		for (var i = 0; i < calendar.guid; i++) {
			
			if (calendar[i] != undefined) {
				if (calendar[i].elem.innerText == '提示信息'
				        || calendar[i].elem.innerText == '确认信息') {
					closeFlg = false;
					
					break;
				}
			}
		}
	} catch (e) {
	}
	if (keyCode == 27 && closeFlg) {
		// obj.lhgDG.cancel();
	}
}

/*
 * 弹出框自适应高度
 */
function getHeight(content, defHeight) {

	if (defHeight == undefined || defHeight == null || defHeight == '') {
		var chineseLen = content.replace(/[^\u4e00-\u9fa5]/gi, "").length;
		var height = ((content.length - chineseLen) / 2 + chineseLen) / 20;
		if (height > parseInt(height)) {
			height = parseInt(height) + 1;
		}
		height = 110 + height * 21;

		height = height > 160 ? height : 160;
		if (height > 500)
			height = 500;
		return height;
	} else {
		return defHeight;
	}
}

/*
 * function: 处理弹出窗口的min-width arguments: 无 date: 2011/6/20
 * 
 */

$(function() {
	
	var win = window;
	var $frame;
	var level = 0;
	
	while (win != top) {
		$frame = $(win.frameElement);
		level += 1;
		if ($frame.attr('id') == 'lhgfrm') {
			break;
		}
		win = win.parent;
	}
	
	if ($frame && $frame.attr('id') == 'lhgfrm') {
		$('body').css('min-width', $frame.width() - 50 * level);
		
//		$("html").live("keydown", function(event) {
//			cancleKeyDown(event);
//		});
		$("body").focus();
	}
	
});