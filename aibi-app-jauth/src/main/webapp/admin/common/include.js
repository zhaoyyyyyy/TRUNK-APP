var Scripts = {
	//原生javascript加载js文件
	loadScript : function(option) {
		var defaults={
				src:"../../js/jquery-1.5.2.js",
				callback:function(){
					
				}
		};
		
		 option = Scripts.extend(defaults,option);
		 if(option.src instanceof Array){
			 Scripts.loadScript({
				 src: option.src[0],
				 callback:function(){
					 if(option.src.length >1){
						 Scripts.loadScript({
							 src: option.src.slice(1,option.src.length),
							 callback :option.callback
						 });
					 }else if(option.src.length == 1 ) {
						 Scripts.loadScript({
							 src: option.src[0],
							 callback :option.callback
						 });
					 }
				 }
			 });
		 }else{
			 var script = document.createElement("script");
			 script.type = "text/javascript";
			 if(typeof(option.callback) != "undefined"){
				 if (script.readyState) {
					 script.onreadystatechange = function () {
						 if (script.readyState == "loaded" || script.readyState == "complete") {
							 script.onreadystatechange = null;
							 option.callback();
						 }
					 };
				 } else {
					 script.onload = function () {
						 option.callback();
					 };
				 }
			 }
			 script.src = option.src;
			 document.head.appendChild(script);
		 }
		 
		 
		 
		},
		//原生javascript加载CSS文件
		loadStyle : function(option) {
			var defaults={
					src:"../../../assets/styles/base.css",
			};
			option = Scripts.extend(defaults,option);
			var link = document.createElement("link");
			link.rel="stylesheet";
			link.rev = "stylesheet";
			link.type = "text/css";
			link.href = option.src;
			document.head.appendChild(link);
		},
		/*
		 * @param {Object} target 目标对象。
		 *  @param {Object} source 源对象。 
		 *  @param{boolean} deep 是否复制(继承)对象中的对象。 
		 *   returns {Object} 返回继承了source对象属性的新对象。
		 */ 
		extend :  function(target, /* optional */source, /* optional */deep) { 
			target = target || {}; 
			var sType = typeof source, i = 1, options; 
			if( sType === 'undefined' || sType === 'boolean' ) { 
				deep = sType === 'boolean' ? source : false; 
				source = target; 
				target = this; 
			} 
			if( typeof source !== 'object' && Object.prototype.toString.call(source) !== '[object Function]' ) 
			source = {}; 
			while(i <= 2) { 
				options = i === 1 ? target : source; 
				if( options != null ) { 
					for( var name in options ) { 
						var src = target[name], copy = options[name]; 
						if(target === copy) 
						continue; 
						if(deep && copy && typeof copy === 'object' && !copy.nodeType) 
						target[name] = this.extend(src || (copy.length != null ? [] : {}), copy, deep); 
						else if(copy !== undefined) 
						target[name] = copy; 
					} 
				} 
				i++; 
			} 
			return target; 
		}
	
};
/****
 * 根据js加载进度 加载页面  重写window.onload
 */
var  mainPage={
	load:function(){
		var oldLoadFunc = window.onload;
		var mainObj = $("#mainPage");
		if(mainObj.length > 0){
			this.load();
		}
	}
};





	
	
/*******************************************************************************
 * 加载公用js、css v0.1
 * @author  wangsen3
 */
(function(){
	Scripts.loadStyle({src:"../../css/blue/ht_css.css"});
	Scripts.loadStyle({src:"../../css/blue/button.css"});
	Scripts.loadStyle({src:"../../css/blue/s_button.css"});
	Scripts.loadStyle({src:"../../css/blue/common.css" });
	Scripts.loadStyle({src:"../../js/component/my97DatePicker/skin/WdatePicker.css"});
	Scripts.loadStyle({src:"../../css/blue/component/lhgcore/lhgdialog.css"});
	Scripts.loadStyle({src:"../../css/blue/component/lhgcore/alertWindow.css"});
	Scripts.loadStyle({src:"../../css/blue/component/SpryTabbedPanels/SpryTabbedPanels.css"});
	Scripts.loadStyle({src:"../../css/blue/component/simpletree/css/jquery.simple.tree.css"});
	Scripts.loadStyle({src:"../../css/blue/component/triggerfield/css/jquery.triggerfield.css"});
	Scripts.loadStyle({src:"../../js/component/easyui/css/jquery.easyui.css"});
	Scripts.loadStyle({src:"../../css/blue/component/grid/ui.jqgrid.css"});
	Scripts.loadScript({
		src:["../../js/jquery-1.5.2.js"],
		callback:function(){
			Scripts.loadScript({src:[
                    "../../js/component/vue/vue.min.js",
 			        "../../js/component/my97DatePicker/WdatePicker.js",
 			        "../../js/component/form/jquery.form.js",
 			        "../../js/component/lhgcore/lhgcore.min.js","../../js/component/lhgcore/lhgdialog.js","../../js/component/lhgcore/lhgdialogExtend.js",
 			        "../../js/component/SpryTabbedPanels/SpryTabbedPanels.js",
			        "../../js/component/simpletree/js/jquery.simple.tree.js",
			        "../../js/component/triggerfield/js/jquery.triggerfield.js",
			        "../../js/component/triggerfield/js/jquery.trigger.extend.js",
 			        "../../js/component/combobox/js/jquery.combobox.js",
 			        "../../common/choose/OrgType.js",
 			        "../../js/component/easyui/js/jquery.easyui.js",
			        "../../js/component/numberbox/jquery.numberbox.js",
			        "../../js/component/grid/grid.locale-cn.js","../../js/component/grid/grid.base.js","../../js/component/grid/jqgrid.custom.js","../../common/config.js"
			        ],
			        callback:function(){
			        	Scripts.loadScript({src:[
    	     			        "../../js/jquery.util.js",
    	     			        "../../js/common.js"
    	     			        ],
    	     			        callback:function(){
    	     						coc_common_init();
    	     						if(window.jauth_onload){
    	     							window.jauth_onload();
    	     						}
    	     					}
    	     			});
					}
			});
			
			
		}
	});
})(Scripts);