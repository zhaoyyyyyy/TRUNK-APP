var Scripts = {
	//原生javascript加载js文件
	loadScript : function(option) {
		var defaults={
				src:"../../asset/script/component/jquery-1.12.4.js",
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
					src:"../../asset/css/common/reset.css",
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
	Scripts.loadScript({
		src:["../../asset/script/component/jquery-1.12.4.js","../../asset/script/component/vue/vue.min.js","../../asset/script/config.js"],
		callback:function(){
			var theme = $.theme;
			Scripts.loadStyle({src:"../../asset/css/common/reset.css"});
			Scripts.loadStyle({src:"../../asset/css/jQueryUI/jquery-ui-1.12.1.min.css"});
//			Scripts.loadStyle({src:"../../asset/script/component/jQueryUI/gird/page/page.css"});
			Scripts.loadStyle({src:"../../asset/script/component/tree/css/zTreeStyle/zTreeStyle.css"});
			Scripts.loadStyle({src:"../../asset/script/component/jQueryUI/gird/css/ui.jqgrid.css" });
//			Scripts.loadStyle({src:"../../asset/script/component/jQueryUI/gird/css/ui.jqgrid.owner.css"});
			Scripts.loadStyle({src:"../../asset/script/component/validate/css/jquery.easyui.css"});
			Scripts.loadStyle({src:"../../asset/css/lhgcore/lhgdialog.css"});
			Scripts.loadStyle({src:"../../asset/css/lhgcore/alertWindow.css"});
			Scripts.loadStyle({src:"../../asset/css/theme/"+theme+"/button.css"});
			Scripts.loadStyle({src:"../../asset/css/theme/"+theme+"/page.css"});
			Scripts.loadStyle({src:"../../asset/css/theme/"+theme+"/ui.jqgrid.owner.css"});
			Scripts.loadStyle({src:"../../asset/css/theme/"+theme+"/form.css"});
			Scripts.loadStyle({src:"../../asset/css/theme/"+theme+"/tab.css"});
			Scripts.loadStyle({src:"../../asset/css/theme/"+theme+"/coc_frame.css"});
			Scripts.loadStyle({src:"../../asset/css/theme/"+theme+"/main.css"});
			
			Scripts.loadScript({src:[
			        //图表组件            
	  				"../../asset/script/component/echarts/echarts.min.js",
	  				"../../asset/script/component/echarts/locChart.js",
	 
			        "../../asset/script/common/jquery.customs.js",
			        "../../asset/script/common/jquery.util.js",
			        "../../asset/script/component/form/jquery.form.js",
			        "../../asset/script/component/validate/js/jquery.easyui.js",
                    "../../asset/script/component/jQueryUI/jquery-ui-1.12.1.min.js",
 			        "../../asset/script/component/lhgcore/lhgcore.min.js","../../asset/script/component/lhgcore/lhgdialog.js","../../asset/script/component/lhgcore/lhgdialogExtend.js",
 			        "../../asset/script/component/jQueryUI/gird/page/jquery.page.js",
 			        "../../asset/script/component/jQueryUI/gird/js/jquery.jqGrid.min.js",
 			        "../../asset/script/component/jQueryUI/gird/js/i18n/grid.locale-cn.js",
 			        "../../asset/script/component/jQueryUI/gird/jquery.jqGrid.custom.js",
 			        "../../asset/script/component/tree/jquery.ztree.all.min.js",
 			        "../../asset/script/component/fileUpload/ajaxfileupload.js",
			        "../../asset/script/component/fileUpload/jQuery.fileUpload.js",
			        "../../asset/script/component/My97DatePicker/WdatePicker.js",
			        ],
			        callback:function(){
			        	Scripts.loadScript({src:[
    	     			        "../../asset/script/common/init.js"
    	     			        ],
    	     			        callback:function(){
    	     						loc_common_init();
    	     						if(window.loc_onload){
    	     							window.loc_onload();
    	     						}
    	     					}
    	     			});
					}
			});
			
			
		}
	});
})(Scripts);