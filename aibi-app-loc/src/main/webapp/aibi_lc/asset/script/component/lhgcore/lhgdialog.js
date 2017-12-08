﻿/*!
 * lhgcore Dialog Plugin v3.2.1
 * Date : 2011-01-26 14:51:11
 * Copyright (c) 2009 - 2011 By Li Hui Gang
 */

;(function(J){

if( !J.lhgren )
{
    J.browser.ie = J.browser.msie ? true : false;
	J.browser.i7 = (J.browser.msie && J.browser.version >= 7) ? true : false;
}

var top = window, doc, cover, ZIndex, install = true,

getSrc = function()
{
	if( J.browser.ie )
		return ( J.browser.i7 ? '' : "javascript:''" );
	else
		return 'javascript:void(0);';
},

iframeTpl = J.browser.ie && !J.browser.i7 ? '<iframe hideFocus="true" ' + 
	'frameborder="0" src="' + getSrc() + '" style="position:absolute;' +
	'z-index:-1;width:100%;height:100%;top:0px;left:0px;filter:' +
	'progid:DXImageTransform.Microsoft.Alpha(opacity=0);opacity:0"><\/iframe>' : '',

compat = function( d )
{
    d = d || document;
	return d.compatMode === 'CSS1Compat' ? d.documentElement : d.body;
},

getScrSize = function()
{
	if( 'pageXOffset' in top )
	{
	    return {
		    x: top.pageXOffset || 0,
			y: top.pageYOffset || 0
		};
	}
	else
	{
	    var d = compat( doc );
		return {
		    x: d.scrollLeft || 0,
			y: d.scrollTop || 0
		};
	}
},

getDocSize = function()
{
	var d = compat( doc );
	
	return {
	    w: d.clientWidth || 0,
		h: d.clientHeight || 0
	}
},

getZIndex = function()
{
	/**解决弹出窗口中有tab页时，弹出别的窗口时显示层级有问题 edit by zhaomd*/
    if( !ZIndex) ZIndex = window.ZIndex || 999;
    /**edit end*/
	return ++ZIndex;
},

reSizeHdl = function()
{
    var rel = compat( doc );
	
	J(cover).css({
	    width: Math.max( 3000 || 0 ) - 1 + 'px',
		height: Math.max( 3000 || 0 ) - 1 + 'px'
	});
},

getAbsoultePath = function()
{
	var sc = J('script'), bp = '', i = 0, l = sc.length;
	
	for( ; i < l; i++ )
	{
	    if( sc[i].src.indexOf('lhgdialog') >= 0 )
		{
		    bp = !!document.querySelector ?
			    sc[i].src : sc[i].getAttribute('src',4);
			break;
		}
	}
	
	return bp.substr( 0, bp.lastIndexOf('/') + 1 );
};

while( top.parent && top.parent != top )
{
    try{
	    if( top.parent.document.domain != document.domain ) break;
	}catch(e){ break; }
	
	top = top.parent;
}

if( top.document.getElementsByTagName('frameset').length > 0 )
    top = window; 

doc = top.document;

J.fn.fixie6png = function()
{
    var els = J('*',this), iebg, bgIMG;
	
	for( var i = 0, l = els.length; i < l; i++ )
	{
	    bgIMG = J(els[i]).css('backgroundImage');
		
		if( bgIMG.indexOf('.png') !== -1 )
		{
		    iebg = bgIMG.replace(/url\(|"|\)/g,'');
			els[i].style.backgroundImage = 'none';
			els[i].runtimeStyle.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + iebg + "',sizingMethod='scale')";
		}
	}
};

J.fn.dialog = function( opts )
{
    var dialog = false;
	
	if( this[0] )
	    dialog = new J.dialog( opts, this[0] );
	
	return dialog;
};

J.dialog = function( opts, elem )
{
	var self = this,xbtnObj,
	
	r = this.opt = J.extend({
		title: 'lhgdialog \u5F39\u51FA\u7A97\u53E3',
		width: 400,
		height: 300,
		titleBar: true,
		xButton: true,  //去掉右上角的X
		id: 'lhgdlgId',
		SetTopWindow: false,
		btns: true,
		link: false,
		page: '',
		event: 'click',
		fixed: false,
		onXclick: null,
		top: 'center',
		left: 'center',
		drag: true,
		resize: true,
		loadingText: '\u7A97\u53E3\u5185\u5BB9\u52A0\u8F7D\u4E2D\uFF0C\u8BF7\u7A0D\u7B49...'
	}, opts || {} );
	
	if( r.SetTopWindow )
	{
	    top = r.SetTopWindow;
		doc = top.document;
	}
	
	if( !install )
	{
		try{
			doc.execCommand( 'BackgroundImageCache', false, true );
		}catch(e){}
		
		J('head',doc).append( '<link href="' + getAbsoultePath() + 'lhgdialog.css" rel="stylesheet" type="text/css"/>' );
		
		install = true;
	}
	
	this.SetIFramePage = function()
	{
	    var innerDoc, dialogTpl;
		
		if( r.html )
		{
		    if( typeof r.html === 'string' )
				innerDoc = '<div id="lhgdig_inbox" class="lhgdig_inbox" style="display:none">' + r.html + '</div>';
			else
				innerDoc = '<div id="lhgdig_inbox" class="lhgdig_inbox" style="display:none"></div>';
		}
		else if( r.page )
		{
		    innerDoc = ['<iframe frameborder="0" src="', r.page, '" noresize="noresize" ',
				'id="lhgfrm" style="height:0px;width:100%;"><\/iframe>'].join('');
		}
		
		dialogTpl = [
		    '<div id="', r.id, '" class="lhgdig" style="width:', r.width, 'px;height:', r.height, 'px;">',
				'<table border="0" cellspacing="0" cellpadding="0">',
				'<tr>',
					'<td class="lhgdig_leftTop"></td>',
					'<td id="lhgdig_drag" class="lhgdig_top">',
						'<div class="lhgdig_title"><span id="lhgdig_icon" class="lhgdig_icon"></span>', r.title, '</div>'+
							(r.xButton ? ('<a class="lhgdig_xbtn" id="lhgdig_xbtn' + r.id + '" href="###"></a>') : '') + '</div>',
					'</td>',
					'<td class="lhgdig_rightTop"></td>',
				'</tr>',
				'<tr>',
					'<td class="lhgdig_left" id="lhgdigLeft"></td>',
					'<td>',
						'<table border="0" cellspacing="0" cellpadding="0">',
						'<tr>',
							'<td id="lhgdig_content" class="lhgdig_content">',
								innerDoc, '<div id="throbber" class="lhgdig_throbber"><span id="lhgdig_load">', r.loadingText, '</span></div>',
							'</td>',
						'</tr>',
						r.btns ? '<tr><td id="lhgdig_btns" class="lhgdig_btns"><div id="lhgdig_bDiv" class="lhgdig_bDiv buttonArea"></div></td></tr>' : '',
						'</table>',
					'</td>',
					'<td class="lhgdig_right"></td>',
				'</tr>',
				'<tr>',
					'<td class="lhgdig_leftBottom"></td>',
					'<td class="lhgdig_bottom"></td>',
					'<td id="lhgdig_drop" class="lhgdig_rightBottom"></td>',
				'</tr>',
				'</table>', iframeTpl,
			'</div>'
		].join('');
		
		return dialogTpl;
	};
	
	this.ShowDialog = function()
	{
	    if( J('#'+r.id,doc)[0] )
		    return;
		
		if( r.cover )
		    this.ShowCover();
		
		var fixpos = r.fixed && (!J.browser.ie || J.browser.i7) ? 'fixed' : 'absolute',
		    html = this.SetIFramePage();
		
		this.dg = J(html,doc).css({
		    position: fixpos, zIndex: getZIndex()
		}).appendTo(doc.body)[0];
		xbtnObj = J('#lhgdig_xbtn'+r.id,doc)[0];
		this.iPos( this.dg, r.top, r.left, r.fixed );
		
		// edit by qiany
		if (r.fixed && $.browser.msie && $.browser.version <= 6.0) {
			$(window).scroll(function() {
				$(self.dg).css({ top: r.top + document.documentElement.scrollTop + 'px'});
			})
		}
		// end 
		
		this.SetDialog( this.dg );
	
	    if( r.drag )
		    this.initDrag( J('#lhgdig_drag',this.dg)[0] );
		
		if( r.resize )
		    this.initSize( J('#lhgdig_drop',this.dg)[0] );
		
		if( J.browser.ie && !J.browser.i7 )
		{
		    var ie6PngRepair = J('html',doc).css('ie6PngRepair') === 'true' ? true : false;
			if( ie6PngRepair ) J(this.dg).fixie6png();
		}
		
		this.lhgDigxW = J('#lhgdigLeft',this.dg)[0].offsetWidth * 2;

		this.reContentSize( this.dg );
		
		if( r.html && r.cusfn ) r.cusfn();
		
		if( r.html )
		{
		    J('#throbber',this.dg).css('display','none');
			J('#lhgdig_inbox',this.dg)[0].style.display = 'inline-block';
		}
	};
	
	this.ShowCover = function()
	{
	    if( !cover )
		{
			var html = [ '<div style="position:absolute;top:0px;left:0px;',
					'background-color:#ccc;filter:alpha(opacity =50);opacity:0.5">', iframeTpl, '</div>' ].join('');
			cover = J(html,doc).css({'opacity':0.5}).appendTo(doc.body)[0];
		}
		
		J(top).bind( 'resize', reSizeHdl );
		reSizeHdl();
		J(cover).css({ display: '', zIndex: getZIndex() });
	};
	
	this.hideProgress = function() {
		this.dgFrm.style.display = 'block';
		J('#throbber',this.dg)[0].style.display = 'none';
	};
	
	this.iPos = function( dg, tp, lt, fix )
	{
	    var cS = getDocSize(top),
		    sS = getScrSize(top),
			dW = dg.offsetWidth,
			dH = dg.offsetHeight, x, y;
		var lx,rx,cx,ty,by,cy;//取消全局变量的定义，改为局部变量 add by zhaomd
		
		if( fix )
		{
			if( J.browser.ie && !J.browser.i7 )
			{
				J('html',doc).addClass('lhgdig_ie6_fixed');
				J('<div class="lhgdig_warp"></div>',doc).appendTo(doc.body).append(dg).css('zIndex',getZIndex());
			}
			
			lx = 0;
			rx = cS.w - dW;
			cx = ( rx - 20 ) / 2;
			
			ty = 0;
			by = cS.h - dH;
			cy = ( by - 20 ) / 2;
		}
		else
		{
			lx = sS.x;
			cx = sS.x + ( cS.w - dW - 20 ) / 2;
			rx = sS.x + cS.w - dW;
			
			ty = sS.y;
			cy = sS.y + ( cS.h - dH - 20 ) / 2;
			by = sS.y + cS.h - dH;
		}
		
		switch( lt )
		{
		    case 'center':
				x = cx;
				break;
			case 'left':
				x = lx;
				break;
			case 'right':
				x = rx;
				break;
			default:
				// original
			    // if(fix) lt = lt - sS.x;
			    
			    // eidt by qiany
			    if(fix && $.browser.msie && $.browser.version <= 6.0) {
		    		lt = lt + document.documentElement.scrollLeft;
			    }
			    // end
			    
				x = lt; break;
		}
		
		switch( tp )
		{
		    case 'center':
				y = cy;
			    break;
			case 'top':
			    y = ty;
				break;
			case 'bottom':
			    y = by;
				break;
			default:
			    // original 
			    // if(fix) tp = tp - sS.y;
			    
			    // edit by qiany
			    if(fix && $.browser.msie && $.browser.version <= 6.0) {
	    			tp = tp + document.documentElement.scrollTop;
		    	} 
			    // end
			    
				y = tp; break;
		}
		
		J(dg).css({ top: y + 'px', left: x + 'px' });
	};
	
	this.SetDialog = function( dg )
	{
		this.topWin = top;
		this.topDoc = doc;
		
	    this.curWin = window;
		this.curDoc = document;
		
		J(dg).bind('contextmenu',function(ev){
		    ev.preventDefault();
		}).bind( 'mousedown', self.SetIndex );
		
		if( r.html && r.html.nodeType )
		    J('#lhgdig_inbox',dg).append( r.html );
		
		this.regWindow = [ window ];
		
		if( top != window )
		    this.regWindow.push( top );
		
		if( r.page.length > 0 )
		{
		    this.dgFrm = J('#lhgfrm',dg)[0];
			
		    if( !r.link )
			{
			    this.dgWin = this.dgFrm.contentWindow;
				this.dgFrm.lhgDG = this;
			}
			
			J(this.dgFrm).bind('load',function(){
				if( !self.opt.link )
				{
				    var indw = J.browser.ie ?
					    this.contentWindow.document : this.contentWindow;
					
					J(indw).bind( 'mousedown', self.SetIndex );
					
					self.regWindow.push( this.contentWindow );
				    self.dgDoc = this.contentWindow.document;
				}
				
				this.style.height = '100%';
			    J('#throbber',self.dg)[0].style.display = 'none';
			});
		}
		
		if( r.xButton)
		{
		    J(xbtnObj).hover(function(){
			    J(this).addClass('lhgdig_xbtnover');
			},function(){
			    J(this).removeClass('lhgdig_xbtnover');
			}).bind( 'click', r.onXclick);
		}
	};
	
	this.reContentSize = function( dg )
	{
	    var tH = J('#lhgdig_drag',dg)[0].offsetHeight,
		    bH = J('#lhgdig_drop',dg)[0].offsetHeight,
			xW = this.lhgDigxW,
			nH = r.btns ? J('#lhgdig_btns',dg)[0].offsetHeight : 0,
			iW = parseInt( dg.style.width, 10 ) - xW,
			iH = parseInt( dg.style.height, 10 ) - tH - bH - nH;
		
		J('#lhgdig_content',dg).css({
		    width: iW + 'px', height: iH + 'px'
		});
		
		if( r.html )
		{
		    J('#lhgdig_inbox',dg).css({
			    width: iW + 'px', height: iH + 'px'
			});
		}
		
		this.SetLoadLeft();
	};
	
	this.SetLoadLeft = function()
	{
	    var loadL = ( J('#lhgdig_content',this.dg)[0].offsetWidth -
		    J('#lhgdig_load',this.dg)[0].offsetWidth ) / 2;
			
		J('#lhgdig_load',this.dg)[0].style.left = loadL + 'px';
	};
	
	this.reDialogDisplay = function(param){
		if(param!=undefined){
			if(param.display){
				J(this.dg).css({
				'display'   :'block'
				});
				return;
				
			}else{
				J(this.dg).css({
				'display'   :'none'
				});
				return;
			}
		}
	};
	
	this.reDialogSize = function( width, height,left,top1,param )
	{
		if(param!=undefined){
			var wid = top.document.body.clientWidth;
			var hei = top.document.body.clientHeight;
			if(param.qp){
				J(this.dg).css({
			    'width': wid + 'px', 
			    'height': hei + 'px',
			    'top'   :top1+ 'px',
			    'left'  :left+ 'px'
			});
			}
		}else{
			J(this.dg).css({
			    'width': width + 'px', 
			    'height': height + 'px'
			});
		}
		
		this.reContentSize( this.dg );
	};
	
	this.SetIndex = function(ev)
	{
		if( self.opt.fixed && J.browser.ie && !J.browser.i7 )
		{
		    J(self.dg).parent()[0].style.zIndex = parseInt(ZIndex,10) + 1;
			ZIndex = parseInt( J(self.dg).parent()[0].style.zIndex, 10 );
		}
		else
		{
		    self.dg.style.zIndex = parseInt(ZIndex,10) + 1;
			ZIndex = parseInt( self.dg.style.zIndex, 10 );
		}
		
		ev.stopPropagation();
	};
	
	this.initDrag = function( elem )
	{
	    var lacoor, maxX, maxY, curpos, regw = this.regWindow, cS, sS;
		
		function moveHandler(ev)
		{
			var curcoor = { x: ev.screenX, y: ev.screenY };
		    curpos =
		    {
		        x: curpos.x + ( curcoor.x - lacoor.x ),
			    y: curpos.y + ( curcoor.y - lacoor.y )
		    };
			lacoor = curcoor;
			
			if( r.rang )
			{
			    if( curpos.x < sS.x ) curpos.x = sS.x;
				if( curpos.y < sS.y ) curpos.y = sS.y;
				if( curpos.x > maxX ) curpos.x = maxX;
				if( curpos.y > maxY ) curpos.y = maxY;
			}
			
			self.dg.style.top = self.opt.fixed ? curpos.y - sS.y + 'px' : curpos.y + 'px';
			self.dg.style.left = self.opt.fixed ? curpos.x - sS.x + 'px' : curpos.x + 'px';
		};
		
		function upHandler(ev)
		{
			for( var i = 0, l = regw.length; i < l; i++ )
			{
			    J( regw[i].document ).unbind( 'mousemove', moveHandler );
				J( regw[i].document ).unbind( 'mouseup', upHandler );
			}
			
			lacoor = null; elem = null;
			
		    if( J.browser.ie ) self.dg.releaseCapture();
		};
		
		J(elem).bind( 'mousedown', function(ev){
		    if( ev.target.id === 'lhgdig_xbtn' ) return;

			cS = getDocSize(top);
			sS = getScrSize(top);
			
			var lt = self.dg.offsetLeft,
			    tp = self.dg.offsetTop,
			    dW = self.dg.clientWidth,
			    dH = self.dg.clientHeight;
			
			curpos = self.opt.fixed ?
			    { x: lt + sS.x, y: tp + sS.y } : { x: lt, y: tp };
			
			lacoor = { x: ev.screenX, y: ev.screenY };
			
			maxX = cS.w + sS.x - dW;
			maxY = cS.h + sS.y - dH;
			
			self.dg.style.zIndex = parseInt( ZIndex, 10 ) + 1;
			
			for( var i = 0, l = regw.length; i < l; i++ )
			{
				J( regw[i].document ).bind( 'mousemove', moveHandler );
				J( regw[i].document ).bind( 'mouseup', upHandler );
			}
			
			ev.preventDefault();
			
			if( J.browser.ie ) self.dg.setCapture();
		});
	};
	
	this.initSize = function( elem )
	{
	    var lacoor, dH, dW, curpos, regw = this.regWindow, dialog, cS, sS;
		
		function moveHandler(ev)
		{
		    var curcoor = { x : ev.screenX, y : ev.screenY };
			dialog = {
				w: curcoor.x - lacoor.x,
				h: curcoor.y - lacoor.y
			};
			
			if( dialog.w < 200 ) dialog.w = 200;
			if( dialog.h < 100 ) dialog.h = 100;
			
			self.dg.style.top = self.opt.fixed ? curpos.y - sS.y + 'px' : curpos.y + 'px';
			self.dg.style.left = self.opt.fixed ? curpos.x - sS.x + 'px' : curpos.x + 'px';
			
			self.reDialogSize( dialog.w, dialog.h );
		};
		
		function upHandler(ev)
		{
			for( var i = 0, l = regw.length; i < l; i++ )
			{
			    J( regw[i].document ).unbind( 'mousemove', moveHandler );
				J( regw[i].document ).unbind( 'mouseup', upHandler );
			}
			
			lacoor = null; elem = null;
			
		    if( J.browser.ie ) self.dg.releaseCapture();
		};
	
	    J(elem).bind( 'mousedown', function(ev){
			dW = self.dg.clientWidth;
			dH = self.dg.clientHeight;
			
			dialog = { w: dW, h: dH };
			
			cS = getDocSize(top);
			sS = getScrSize(top);
			
			var lt = self.dg.offsetLeft,
			    tp = self.dg.offsetTop;
			
			curpos = self.opt.fixed ?
			    { x: lt + sS.x, y: tp + sS.y } : { x: lt, y: tp };
				
			lacoor = { x: ev.screenX - dW, y: ev.screenY - dH };
			
			self.dg.style.zIndex = parseInt( ZIndex, 10 ) + 1;
			
			for( var i = 0, l = regw.length; i < l; i++ )
			{
			    J( regw[i].document ).bind( 'mousemove', moveHandler );
				J( regw[i].document ).bind( 'mouseup', upHandler );
			}
			
			ev.preventDefault();
			
			if( J.browser.ie ) self.dg.setCapture();
		});
	};
	
	this.addBtn = function( id, txt, fn )
	{
		var c = J('<div id="'+ id + '_lhgbtn" class="left newButton"><div><input class="'
					+ id + '" type="button" value="' + txt + '" /></div></div>' , doc)[0];
		J("input", c).click(fn);
		
		J("#lhgdig_bDiv", this.dg).append(c);
	};
	
	this.removeBtn = function( id )
	{
		if (id) {
	    	J("#" + id + "_lhgbtn", this.dg)[0] && J("#" + id + "_lhgbtn", this.dg).remove();
		} else {
			J("#lhgdig_btns div div.newButton", this.dg).remove();
		}
	};
	
	this.cancel = function()
	{
		self.removeDG();
		
		if( cover )
		{
		    if( self.opt.parent && self.opt.parent.opt.cover )
			{
			    var Index = self.opt.parent.dg.style.zIndex;
				cover.style.zIndex = parseInt(Index,10) - 1;
			}
			else
			    cover.style.display = 'none';
		}
	};
	
	this.removeDG = function()
	{
		var frm = J('#lhgfrm',self.dg)[0];
		if( frm )
		{
			if( !self.opt.link )
				J(frm.contentWindow).unbind( 'load' );
			frm.src = getSrc(); frm = null;
		}
		
		self.regWindow = [];
		
		if( self.opt.fixed && J.browser.ie && !J.browser.i7 )
		{
		    J('html',doc).removeClass('lhgdig_ie6_fixed');
			J(self.dg).parent().remove();
		}
		else
		    J(self.dg).remove();
		
		self.dg = null;
	}
	
	this.cleanDialog = function()
	{
		if( self.dg )
		    self.removeDG();
		
		if( cover )
		{
		    J(cover).remove();
			cover = null;
		}
	};
	r.onXclick = r.onXclick || this.cancel;
	J(window).bind( 'unload', this.cleanDialog );

    if( elem )
	    J(elem).bind( r.event, function(){ self.ShowDialog(); });
};

J(function(){
	var lhgDY = setTimeout(function(){
	    new J.dialog({ id:'reLoadId', html:'lhgdialog', width:100, title:'reLoad', height:100, left:-9000 ,top:-9999}).ShowDialog(); clearTimeout(lhgDY);
	}, 150);
});

})(lhgcore);