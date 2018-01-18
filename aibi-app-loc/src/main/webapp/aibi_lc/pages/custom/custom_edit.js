var dataModel = {
		labelId : "",
		labelInfo : {},
		updateCycles :[],
		updateCycle : 1,
		dataPrivaliegeList : [],//用户数据权限
		checkedModelList : [] ,//数据选中
		isShowPrivaliegeDiv :false,
		isShowLabelMonthDiv : false,
		isShowLabelDayDiv : false,
		labelMonth : '',
		labelDay : '',
		newMonthDate :'',
		newDayDate : '',
		configId : '',
		_submitFlag	: false
}
window.loc_onload = function() {
	//初始化参数
	dataModel.configId = $.getCurrentConfigId();
	
	var dicUpdateCycles = $.getDicData("GXZQZD");
	for(var i =0 ; i<dicUpdateCycles.length; i++){
		dataModel.updateCycles.push(dicUpdateCycles[i]);
	}
	labelInfoModel.getDataPrivaliege();//加载数据权限
	labelInfoModel.findEaliestDataDate();
	
	new Vue({
		el : '#dataD',
		data : dataModel,
		mounted: function () {
		    this.$nextTick(function () {
			    var r = $(".easyui-validatebox");
	   			if (r.length){
	   				r.validatebox();
	   			}
	   			$("#failTime").click(function(){
	   				if($(".ui-datepicker-div").css("display")=="none"){
	   					$('.ui-datepicker-calendar').hide();
	   				}else{
	   					$('.ui-datepicker-calendar').show();
	   				}
	   			})
		    })
		}
	})
	
}

/**
 * ------------------------------------------------------------------
 * 标签集市
 * ------------------------------------------------------------------
 */
var labelInfoModel = (function (model){
	/**
     * @description 查询对象
     * @param  option
     * @return  
     * ------------------------------------------------------------------
     */
    model.queryCustomerGroup = function(option) {
    	$.commAjax({
		  url: $.ctx + "/api/label/categoryInfo/queryList",
		  postData:{
			  customGroupId : dataModel.customGroupId
		  },
		  onSuccess: function(returnObj){
			  dataModel.categoryInfoList = returnObj.data;
		  }
		});
    };
    /**
     * @description 查询最早的数据日期
     * @param  option
     * @return  
     * ------------------------------------------------------------------
     */
    model.findEaliestDataDate = function() {
    	$.commAjax({
			  url: $.ctx + "/api/shopCart/findEaliestDataDate",
			  onSuccess: function(returnObj){
				  var status = returnObj.status;
				  	var result = returnObj.data;
					if (status == '200'){
						dataModel.isShowLabelMonthDiv = result.existMonthLabel;
						dataModel.isShowLabelDayDiv = result.existDayLabel;
						dataModel.labelMonth = result.monthDate;
						dataModel.labelDay = result.dayDate;
						dataModel.newMonthDate = result.newMonthDate;
						dataModel.newDayDate = result.newDayDate;
						
					}
			  }
		});
    };
    /**
     * 校验
     */
    model.validateForm = function(){
    	if(!dataModel.labelInfo.labelName){
    		$.alert("请填写客户群名称。");
    		return false;
    	}
    	if(!dataModel.labelInfo.tacticsId){
    		$.alert("请选择生成策略。");
    		return false;
    	}
    	if(dataModel.updateCycle == ""){
    		$.alert("请选择生成周期。");
    		return false;
    	}
    	if($('#failTime').val()  == ""){
    		$.alert("请选择失效日期。");
    		return false;
    	}
    	if(dataModel.checkedModelList.length <= 0 ){
    		$.alert("当前用户拥有多个数据权限，请选择需要使用的数据范围。");
    		return false;
    	}
    	return true;
    };
    /**
     * @description 保存对象
     * @param  option
     * @return  
     * ------------------------------------------------------------------
     */
    model.save = function(option) {
		 if(dataModel._submitFlag){
			 return ;
		 }
    	 var url = $.ctx + "/api/label/labelInfo/saveCustomerLabelInfo";
    	 if(dataModel.customGroupId){
    		 url = $.ctx + "/labelInfo/updateCustomerLabelInfo";
    	 }
    	 if(model.validateForm()){
    		 var data = dataModel.labelInfo;
    		 data.updateCycle = dataModel.updateCycle ;
    		 data.orgId = dataModel.checkedModelList.join(',');
    		 data.configId = dataModel.configId ;
    		 data.failTime = $('#failTime').val() ;
    		 if($('#labelDay').val()){
    			 dataModel.labelInfo.dayLabelDate = $('#labelDay').val().replace(/-/g,""); 
    		 }
    		 if($('#labelMonth').val()){
    			 dataModel.labelInfo.monthLabelDate = $('#labelMonth').val().replace(/-/g,"");
    			 dataModel.labelInfo.dataDate = $('#labelMonth').val().replace(/-/g,"");
    		 }
			 
    		 dataModel._submitFlag = true;
    		 $.commAjax({
    			  url: url,
    			  postData:data,
    			  onSuccess: function(returnObj){
    				  if(returnObj.status == '200'){
    					  $.alert(returnObj.msg);
    					  model.back();
    				  }else{
    					  $.alert(returnObj.msg);
    				  }
    			  }
    		  });
    	 }
    	
    };
    /**
	 * 查询用户数据权限
	 */
	model.getDataPrivaliege = function(){
		$.commAjax({
			  url: $.ctx + "/api/user/get",
			  onSuccess: function(returnObj){
				  if(returnObj.status == '200'){
					  dataModel.dataPrivaliegeList = returnObj.data.dataPrivaliege[2];
					  if(dataModel.dataPrivaliegeList && dataModel.dataPrivaliegeList.length > 1){
						  dataModel.isShowPrivaliegeDiv = true;
					  }else if(dataModel.dataPrivaliegeList && dataModel.dataPrivaliegeList.length == 1){
						  dataModel.checkedModelList[0] = dataModel.dataPrivaliegeList[0].id;
					  }
				  }
			  }
		});
	};
	/**
	 * 返回
	 */
	model.back = function(){
		var from = $.getUrlParam("from");
		var returnUrl ="";
		if(from == 'labelmarket'){//跳转到客户群集市
			returnUrl = "../label/label_market.html";
		}
		window.location.href = returnUrl;
	};
	/**
	 * 修改策略
	 */
	model.changeTacticsId = function(obj){
		if(obj.selectedOptions.length >0 ){
			var selVal = obj.selectedOptions[0].value;
			//通过修改策略来联动变化数据日期
			if(selVal == 1) {//预约策略，使用最新数据日期
				dataModel.labelInfo.dayLabelDate = dataModel.newDayDate;
				dataModel.labelInfo.monthLabelDate = dataModel.newMonthDate; 
				dataModel.labelInfo.dataDate = dataModel.newMonthDate; 
				$('#labelDay').attr('disabled', true);
				$('#labelMonth').attr('disabled', true);
			} else if(selVal == 2){//保守策略，使用最早数据日期
				dataModel.labelInfo.dayLabelDate = dataModel.dayDate;
				dataModel.labelInfo.monthLabelDate = dataModel.monthLabelDate;
				dataModel.labelInfo.dataDate = dataModel.monthDate;
				$('#labelDay').attr('disabled', false);
				$('#labelMonth').attr('disabled', false);
			}
		}
		
		return false;
	};
	/**
	 * 修改月份
	 */
	model.changeLabelMonth = function(){
		if(dataModel.labelMonth){
    		var labelMonths = dataModel.labelMonth.split('-');
	    	$("#labelMonth").datepicker({
	    		monthNamesShort: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],  // 区域化月名为中文  
		        changeYear: true,          // 年下拉菜单  
		        changeMonth: true,             // 月下拉菜单  
		        showButtonPanel: true,         // 显示按钮面板  
		        showMonthAfterYear: true,  // 月份显示在年后面 
		        currentText: "本月",         // 当前日期按钮提示文字  
		        closeText: "关闭",           // 关闭按钮提示文字  
		        dateFormat: "yy-mm",       // 日期格式  
	            maxDate: new Date(labelMonths[0], labelMonths[1] - 1, 1),//最大日期
	            minDate: new Date(labelMonths[0]-1, labelMonths[1] - 1, 1),//最大日期
		        onChangeMonthYear: function(year, month) {
				    $(this).datepicker('setDate', new Date(year, month-1, 1)); 
					$(this).datepicker( "hide" );
				}
					
		    });
    	}
	}
	/**
	 * 修改月份
	 */
	model.changeLabelDay = function(){
		if(dataModel.labelDay){
    		var labelDays = dataModel.labelDay.split('-');
	    	if($(".ui-datepicker-div").css("display")=="none"){
    			$('.ui-datepicker-calendar').hide();
    		}else{
    			$('.ui-datepicker-calendar').show();
    		}
	    	$("#labelDay" ).datepicker({
		  		changeMonth: true,
		  		changeYear: true,
		  		dateFormat:"yy-mm-dd",
		  		numberOfMonths:1,
		  		showMonthAfterYear: true,  // 月份显示在年后面 
		  		dayNamesMin: [ "日", "一", "二", "三", "四", "五", "六" ],
		  		monthNamesShort: [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" ],
		    	maxDate: new Date(labelDays[0], labelDays[1] - 1, 1),//最大日期
	            minDate: new Date(labelDays[0]-1, labelDays[1] - 1, 1)//最大日期
	    	}).on( "change", function() {
	    		$('.ui-datepicker-calendar').show();
		    });
    	}
	}
	/**
	 * 展示规则
	 */
	model.showRules = function(){
		var wd = $.window(name + "-条件设置", $.ctx + '/aibi_lc/pages/custom/showRules.html', 600, 500);
	}
	return model;
})(window.labelInfoModel || {})