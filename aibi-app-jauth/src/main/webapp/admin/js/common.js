 function coc_common_init(){
	
	
	
	
	/* 操作按钮悬浮 */
	$(".buttonArea .newButton").mouseover(function() {
		$(this).removeClass("newButton");
		$(this).addClass("newButton_hover");
	}).mouseout(function() {
		$(this).removeClass("newButton_hover");
		$(this).addClass("newButton");
	});
	
	/* 查询按钮悬浮 */
	$(".v_button").mouseover(function() {
		$(this).removeClass("v_button");
		$(this).addClass("v_button_hover");
	}).mouseout(function() {
		$(this).removeClass("v_button_hover");
		$(this).addClass("v_button");
	});
	
	/* 重置按钮悬浮 */
	$(".r_button").mouseover(function() {
		$(this).removeClass("r_button");
		$(this).addClass("r_button_hover");
	}).mouseout(function() {
		$(this).removeClass("r_button_hover");
		$(this).addClass("r_button");
	});
	$(".gj_button").mouseover(function() {
		$(this).removeClass("gj_button");
		$(this).addClass("gj_button_hover");
	}).mouseout(function() {
		$(this).removeClass("gj_button_hover");
		$(this).addClass("gj_button");
	});
	
	
//	$.parser = {
//		defaults:{
//			auto:true
//		},
//		parse	: function(context) {
//			if ($.parser.defaults.auto) {
//				var r = $(".easyui-validatebox", context);
//				if (r.length)
//					r.validatebox();
//			}
//		}
//	};
//	
//	$.parser.parse();
		

	if(window.top.getTitleHtml){
		var _title = $('#_title');
		if(_title.length > 0 && _title.is("[hasReturn=true]")){
			_title.html(window.top.getTitleHtml({title:_title.text(),url:location.href},true));
		}else{
			_title.html(window.top.getTitleHtml({title:_title.text(),url:location.href}));
		}
	}
	
	$('#formSearch').keyup(function(event){
	  if(event.keyCode == 13){
	  	$("#btnSearch").click();
	  } 
	});
	
	
	//初始化提示框
	$('input[innerTip]').each(function(){
		$(this).val($(this).attr('innerTip')).css('color','gray');
		$(this).bind('focusin',function(){
			$(this).val('').css('color','black');
		}).bind('focusout',function(){
			$(this).val($(this).attr('innerTip')).css('color','gray');
		});
	});
	
	/**
	 * 初始化只能输入数字
	 */
	$("input:text[numberbox]").each(function() {
		var $self = $(this);
		$self.numberbox();
	});
	
	if(document.getElementById('TabbedPanels')){
		new Spry.Widget.TabbedPanels("TabbedPanels");
	}
	
	/* 操作按钮悬浮 */
	$(".Wdate").bind('focus click',function() {
		if($(this).parent().find('.Wdate').length ==2 && $(this).next().is('input.Wdate')){
			if($(this).next().attr('id')){
				WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\''+$(this).next().attr('id')+'\')}'});
			}else{
				$(this).next().attr('id','searchTimeStart');
				WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'searchTimeStart\')}'});
			}
		}else if($(this).parent().find('.Wdate').length ==2 && $(this).prev().is('input.Wdate')){
			if($(this).prev().attr('id')){
				WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\''+$(this).prev().attr('id')+'\')}'});
			}else{
				$(this).prev().attr('id','searchTimeEnd');
				WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'searchTimeEnd\')}'});
			}
		}else{
			WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});
		}
	});
	
	
	// add by xiajb 2012-03-31，查询区所有文本输入域增加最大长度限制
	var newQueryMaxlen = 1000;// 查询区文本输入最大长度
	$(".searchTable :text").each(function() {
		var thisInput = $(this);
		if (thisInput.attr("maxlength")) {
			// 如果文本输入框有最大长度限制，则判断该长度限制是否超过newQueryMaxlen所指定的长度限制，如果超过则重新设置最大长度为newQueryMaxlen
			var oldQueryMaxlen = Number(thisInput.attr("maxlength"));
			if (oldQueryMaxlen > newQueryMaxlen) {
				thisInput.attr("maxlength", newQueryMaxlen);
			}
		} else {
			// 如果没有设置过最大长度限制则设置最大长度为newQueryMaxlen
			thisInput.attr("maxlength", newQueryMaxlen);
		}
	});
	
	
	//下拉框手动重置
	$("form").bind("reset",function(){
		var $disInput = $('input[id$=mcdropdown]',this);
		if($disInput.length > 0){
			$disInput.each(function(){
				var $self = $(this);
				$('#'+$self.attr('id').split('_')[0]).val($self.attr('defValue'));
			});
		}
	});
	
	
	/**
	 * vue组件
	 */
	Vue.component('v-input', {
	    props: ['defvalue'],
	    template: '<input v-bind:dataDic="dataDic" v-bind:name="name" v-bind:defvalue="defvalue" v-bind:type="type" />',
	    data: function() {
	        var returndate = {};
	        returndate.dataDic = this.dataDic;
	        returndate.name = this.name;
	        returndate.defvalue = this.defvalue;
	        returndate.type = this.type || "text";

	        return returndate;
	    },
	    mounted : function() {

	        var parentData = this.$parent._data;
	        var $defObj = $(this.$el);

	        //下拉框渲染
	        var dataDic = $defObj.attr('dataDic');
	        var defValue = $defObj.attr('defvalue');
	        var bb = defValue.split(".");
	        $.each(bb,
	        function(i) {
	            parentData = parentData[bb[i]];
	        })

	        if (dataDic && dataDic != '') {
	            $defObj.attr("defvalue", parentData);
	            $.initCodeComponents();
	        } else {
	            $defObj.val(parentData);
	        }
	        $.parser = {
	            defaults: {
	                auto: true
	            },
	            parse: function(context) {
	                if ($.parser.defaults.auto) {
	                    var r = $(".easyui-validatebox", context);
	                    if (r.length) r.validatebox();
	                }
	            }
	        };
	        $.parser.parse();
	    }

	})
		
		
	$.initCodeComponents();
	  $.parser = {
				defaults:{
					auto:true
				},
				parse	: function(context) {
					if ($.parser.defaults.auto) {
						var r = $(".easyui-validatebox", context);
						if (r.length)
							r.validatebox();
					}
				}
			};
	  $.parser.parse();
	
}



	