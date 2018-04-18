/**
 * 个性化工具方法
 */
$.extend({
	
	//键值操作-放value
	kvSet : function(key,value){
		var ssg = window.sessionStorage;
		if(ssg){
			ssg.setItem(key,value);
		}
	},
	//键值操作-得到value
	kvGet : function(key){
		var ssg = window.sessionStorage;
		if(ssg && ssg.getItem(key)){
			return ssg.getItem(key);
		}else{
			return false;
		}
	},
	//拿到当前专区编码
	getToken : function(){
		return $.kvGet("token");
	},
	//拿到当前专区编码
	getCurrentConfigId : function(){
		return $.kvGet("CurrentConfigId");
	},
	//根据维度编码，拿到维度描述
	getCodeDesc : function(codeClass, code) {
		var codes = $.getDicData(codeClass);
		if (codes) {
			for (var i = 0 ; i < codes.length; i++) {
				if (codes[i].code == code) {
					return codes[i].dataName;
				}
			}
		}
		return "字典无法对应["+codeClass+"]["+code+"]";
	},
	// 根据状态获取数据
	getDicData : function(code) {
		if($.kvGet(code)){return eval($.kvGet(code));}
		
		var codes;
		$.commAjax({
			url : $.ctx + '/api/dicData/queryList',
			async : false,
			postData : {
				code : code
			},
			onSuccess : function(data) {
				if(data.status == 200){
					codes = data.data;
					$.kvSet(code,JSON.stringify(codes));
				}
			}
		});
		return codes;
	},
	/**
	 * 页面中根据resourceId 属性，判断该用户是否有按钮的权限
	 */
	initWarrentButton : function(userId) {
		//1.隐藏所有受权限控制的按钮
		//2.获取用户有哪些按钮权限
		//3.判断资源中按钮是否展示
		$("*[resourceCode]").hide();
		var resourceList;
		var show = false;
		$.commAjax({
			url: $.ctx+"/api/user/resourceDom/queryDomCodeList",
			async : false,
			postData:{"tocken":$.getCurrentToken()},
			onSuccess:function(result){
				resourceList = result.data;
			}
		});
		$.commAjax({
			  url: $.ctx + "/api/user/getUserId",
			  async : false,
			  onSuccess: function(returnObj){
				  if(userId == returnObj.data){
					  show = true;
				  }
			  }
		});
		if(resourceList && show){
			$("*[resourceCode]").each(function(){
			    if($.inArray($(this).attr("resourceCode"), resourceList)>-1){
			    	$(this).show();
			    }
			});
		}
		return resourceList;
		
	}
})
	
$.fn.extend({
	//专区渲染
	preConfig : function() {
		var start = new Date().getTime();//起始时间
		 var _self = $(this);
		 var html = '<div class="dropdown fright" id="preConfig_list">'
					    +'<a href="javascript:;" class="dropdown-toggle" title="专区管理">'
					    		+'<span class="pre-config-name"></span>'
					    		+'<span class="caret"></span>'
					   +'</a>'
				       +'<ul class="dropdown-menu">'
				       +'</ul>'   
				    +'</div>';
		$("#preConfig_list").remove();
		_self.append(html);
		$.commAjax({
			async : false,
			url: $.ctx+"/api/prefecture/preConfigInfo/queryList",
			postData:{"configStatus":1},
			onSuccess:function(result){
				if(result.data && result.data.length > 0){
				  //已经选择过了
				  var currentConfigId = $.kvGet("CurrentConfigId");
				  if(currentConfigId){
					  $.each(result.data,function(i){
						  if(result.data[i].configId == currentConfigId){
							  $("#preConfig_list > .dropdown-toggle > .pre-config-name").html(result.data[i].sourceName).attr("configId",result.data[i].configId);
							  return false;
						  }
					  });
				  }else{
					  $.kvSet("CurrentConfigId",result.data[0].configId);
					  $("#preConfig_list > .dropdown-toggle > .pre-config-name").html(result.data[0].sourceName).attr("configId",result.data[0].configId);
//					 
				  }
				  $("#preConfig_list > a.dropdown-toggle").on("click",function(ev){
						var e = ev||event ;
						e.stopPropagation?e.stopPropagation():e.cancelBubble=true;
						var $dropToggle = $(this).parents("#preConfig_list");
						if($dropToggle.hasClass("open")){
							$dropToggle.removeClass("open");
						}else{
							$dropToggle.addClass("open");
							var data = result.data;
							var liHtml = '';
							for(var i=0,len=data.length;i<len;i++){
								liHtml+='<li><a href="javascript:;" configId="'+data[i].configId+'">'+data[i].sourceName+'</a></li>';
							}
							$("#preConfig_list > .dropdown-menu").html(liHtml)
							$("#preConfig_list > ul.dropdown-menu li > a").on("click",function(ev){
								var e = ev||event ;
								e.stopPropagation?e.stopPropagation():e.cancelBubble=true;
								var $this = $(this);
								$.kvSet("CurrentConfigId",$this.attr("configId"));
								window.location.reload();
							});
						}
					});
				    //更新缓存当前专区id 20180418 tianxy3
					$.commAjax({
						  url: $.ctx + "/api/prefecture/preConfigInfo/setSession",
						  postData:{object: $.kvGet("CurrentConfigId")},
						  onSuccess: function(returnObj){
							  
						  }
					});
				}
			}
		});
		
		$(document).click(function(ev){
			var e = ev||event ;
			e.stopPropagation?e.stopPropagation():e.cancelBubble=true;
			$("#preConfig_list").removeClass("open");
		});
		var end = new Date().getTime();//接受时间
//		console.log("专区选择列表渲染用时："+(end - start)+"ms");
	}
});

	/**
     * 日期控件
     * 双日期加载
     */
$.extend({
    loaddatepicker:function(option){
    		var defaults={
    			from:"",
    			to:"",
    			minDate:null,
    			dateFormat:"yy-MM-dd",
    			maxDate:null,
    			minDate:null,
    			beforeShow:function(){
    			},
    			onClose:function(){},
    			showButtonPanel: false,
	        closeText:"关闭",
    			isClickFuc:false
    		};
    		option = $.extend(defaults,option);
    		if(option.formValue && option.toValue){
    			$( option.from ).val(option.formValue );
    			$( option.to ).val(option.toValue );
    		}
    		$( option.from ).datepicker( "destroy" );
    		$( option.to ).datepicker( "destroy" );
    		var dateFormat = option.dateFormat,
        from = $( option.from )
	        .datepicker({
	        	changeMonth: true,
	        	changeYear: true,
	        	numberOfMonths:1,
	        	showButtonPanel: option.showButtonPanel,
	        closeText: option.closeText ,
	        	minDate:option.minDate,
	        	"maxDate":getDate( $(option.to)[0] )||option.maxDate,
	        	dateFormat: dateFormat,
	        	beforeShow:option.beforeShow,
	        	onClose:option.onClose
        }).on( "change", function() {
        		to.datepicker( "option", "minDate", getDate( this ) );
        }),
        to = $( option.to ).datepicker({
	        	changeMonth: true,
	        	changeYear: true,
	        	numberOfMonths:1,
	        	showButtonPanel: option.showButtonPanel,
	        closeText: option.closeText ,
	        	"minDate":getDate( $(option.from)[0] )||option.minDate,
	        	maxDate:option.maxDate,
	        	dateFormat: dateFormat,
	        	beforeShow:option.beforeShow,
	        	onClose:option.onClose
        }).on( "change", function() {
        		from.datepicker( "option", "maxDate", getDate( this ) );
        });
			$.datepicker.dpDiv.addClass("ui-datepicker-box");
        function getDate( element ) {
	        	var date;
	        	try {
        			date = DateFmt.parseDate( element.value );
	        	} catch( error ) {
	        		date = null;
	        	}
        		return date;
        }
    },
	
	
	
});
+function ($) {
  'use strict';
  $(window).on('load', function () {
  	$( '[data-dismiss="datepicker"]' ).datepicker({
  		changeMonth: true,
  		changeYear: true,
  		dateFormat:"yy-mm-dd",
  		dayNamesMin: [ "日", "一", "二", "三", "四", "五", "六" ],
  		monthNamesShort: [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" ]
  	});
  	/***
  	 * 双日历
  	 */
  	var  datepickerList = $( '[data-dismiss*="formDatepicker"]' );
  	for(var i=0,len=datepickerList.length;i<len;i++){
  		var flag = $(datepickerList[i]).attr("data-dismiss").replace("formDatepicker","");
  		$( '[data-dismiss="formDatepicker'+flag+'"]' ).datepicker({
	  		changeMonth: true,
	  		changeYear: true,
	  		dateFormat:"yy-mm-dd",
	  		numberOfMonths:1,
	  		dayNamesMin: [ "日", "一", "二", "三", "四", "五", "六" ],
	  		monthNamesShort: [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" ]
	  	}).on( "change", function() {
	  		  var flag = $(this).attr("data-dismiss").replace("formDatepicker","");
	          $( '[data-dismiss="toDatepicker'+flag+'"]' ).datepicker( "option", "minDate", getDate( this ) );
	    });
	  	$( '[data-dismiss="toDatepicker'+flag+'"]' ).datepicker({
	  		changeMonth: true,
	  		changeYear: true,
	  		dateFormat:"yy-mm-dd",
	  		numberOfMonths:1,
	  		dayNamesMin: [ "日", "一", "二", "三", "四", "五", "六" ],
	  		monthNamesShort: [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" ]
	  	}).on( "change", function() {
	  		var flag = $(this).attr("data-dismiss").replace("toDatepicker","");
	        $( '[data-dismiss="formDatepicker'+flag+'"]' ).datepicker( "option", "maxDate", getDate( this ) );
	    });
  	}
  	$.datepicker.dpDiv.addClass("ui-datepicker-box");
  	function getDate( element ) {
      var date;
      try {
        date = $.datepicker.parseDate( "yy-mm-dd", element.value );
      } catch( error ) {
        date = null;
      }
 
      return date;
    }
  });
  
}(jQuery);