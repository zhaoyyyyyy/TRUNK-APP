var dataModel = {
		labelId : "",//客户群主键
		labelInfo : {},//客户群信息
		labelRuleList : [],//客户群规则
		updateCycles :[],//维表：
		dataPrivaliegeList : [],//维表：用户数据权限
		checkedModelList : [] ,//数据选中
		labelMonth : '',//选择框 月日期
		labelDay : '', //选择框 日日期
		newMonthDate :'',
		newDayDate : '',
		configId : '',
		isShowPrivaliegeDiv :false,
		isShowLabelMonthDiv : false, //是否显示月日期选择框
		isShowLabelDayDiv : false,//是否显示日日期选择框
		isShowTacticsIdDiv : true, //默认显示策略选择框
		isShowFailTimeDayDiv : false, //失效日期的div的显示，true显示
		isShowFailTimeMonthDiv : false, //失效日期的div的显示，true显示
		isAllNewDate : false,//数据日期是否小于最新数据日期
		_submitFlag	: false ,//避免重复提交
		nowDate : new Date(),
		initDimListTacticsValue : '' //初始化策略
}
window.loc_onload = function() {
	//初始化参数
	dataModel.configId = $.getCurrentConfigId();
	dataModel.nowDate = labelInfoModel.formatDate(new Date());
	
	var dicUpdateCycles = $.getDicData("QTGXZQ");
	for(var i =0 ; i<dicUpdateCycles.length; i++){
		dataModel.updateCycles.push(dicUpdateCycles[i]);
	}
	
	labelInfoModel.getDataPrivaliege();//加载数据权限
	labelInfoModel.findEaliestDataDate();//1同步操作
	
	new Vue({
		el : '#dataD',
		data : dataModel,
		mounted: function () {
		    this.$nextTick(function () {
			    var r = $(".easyui-validatebox");
	   			if (r.length){
	   				r.validatebox();
	   			}
	   			labelInfoModel.queryCustomerGroup();//2同步操作查询数据
	   			labelInfoModel.findCustomRule();//3同步操作
	   			labelInfoModel.initDimListTactics();//4同步操作
	   			//生成周期
	   			$("#tacticsId").change(function(){
	   				labelInfoModel.changeTacticsId(this);
	   			})
	   			$("#updateCycleDiv input:radio").change(function(){
	   				labelInfoModel.changeUpdateCycle();
	   			})
	   			$("#endDateByMonth").click(function(){
   					var minDate = '#F{$dp.$D(\'effecTime\',{M:1})}';	
   					//月周期客户群有效结束日期最大往后延长周期为6个月
   					var maxDate = '#F{$dp.$D(\'effecTime\',{M:6})}';
   					WdatePicker({el:this,dateFmt:'yyyy-MM', minDate:minDate,maxDate:maxDate,isShowClear:false})
	   				
		    	})
		    	$("#endDateByDay").click(function(){
   					var minDate = '#F{$dp.$D(\'effecTime\',{d:1})}';
   					var maxDate = '#F{$dp.$D(\'effecTime\',{M:3})}';
   					WdatePicker({el:this,dateFmt:'yyyy-MM-dd', minDate: minDate,maxDate:maxDate,isShowClear:false})
	   				
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
     * @description 查询客户群
     * @param  option
     * @return  
     * ------------------------------------------------------------------
     */
    model.queryCustomerGroup = function(option) {
    	if(dataModel.labelId){//修改
    		$.commAjax({
    			  url: $.ctx + "/api/label/labelInfo/get",
    			  async	: false,//同步
    			  postData:{
    				  labelId : dataModel.labelId
    			  },
    			  onSuccess: function(returnObj){
    				  dataModel.labelInfo = returnObj.data;
    				  model.resetForm();
    			  }
    		});
    	}else{//新增
    		//初始化数据
    		$('#labelMonth').val(dataModel.newMonthDate);//默认最新的数据日期
    		$('#labelDay').val(dataModel.newDayDate);//默认最新的数据日期
    		dataModel.labelInfo.effecTime = dataModel.nowDate ;
    		dataModel.labelInfo.updateCycle = 3 ;
    	}
    };
    
    /**
     * 根据查询的客户群信息，回显信息，待完善
     * 
     */
    model.resetForm = function(){
    	if(dataModel.labelInfo.updateCycle == 2) {//月周期
    		
			
		} else if(updateCycle == 3) {//日周期
			
			
		}
    }
    
    /**
     * @description 查询最早的数据日期
     * @param  option
     * @return  
     * ------------------------------------------------------------------
     */
    model.findEaliestDataDate = function() {
    	$.commAjax({
			  url: $.ctx + "/api/shopCart/findEaliestDataDate",
			  async	: false,//同步
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
						dataModel.isAllNewDate = result.isAllNewDate;//购物车中的标签数据日期是否小于最新数据日期
						//隐藏数据日期、策略
						if(!result.existMonthLabel && !result.existDayLabel){
							dataModel.isShowTacticsIdDiv = false;
						}
					}
			  }
		});
    };
    /**
     * @description 默认支持分批次,初始化保存策略
     * 1.修改客户群,日、月、策略隐藏 2.
     * @param  option
     * @return  
     * ------------------------------------------------------------------
     */
    model.initDimListTactics = function(){
		//将文本框中的日期赋值给结果
    	if($('#labelDay').val()){
  			 dataModel.labelInfo.dayLabelDate = $('#labelDay').val().replace(/-/g,""); 
  		 }
  		 if($('#labelMonth').val()){
  			 dataModel.labelInfo.monthLabelDate = $('#labelMonth').val().replace(/-/g,"");
  		 }
    	
		if(dataModel.labelId){//修改客户群,日、月、策略隐藏
			dataModel.isShowLabelMonthDiv = false;
			dataModel.isShowLabelDayDiv = false;
			dataModel.isShowTacticsIdDiv = false;
		}else{//新建 客户群				
			
	 		if(!model.checkDataDate()){//标签的数据日期小于最新数据日期，则改为预约数据策略
	 			//改为预约数据策略
	 			dataModel.labelInfo.tacticsId = 1;
	 			$("#labelDay").attr('disabled', true);
	 			$("#labelMonth").attr('disabled', true);
	 			$('#tacticsIdTip').show();
	 			//隐藏保守策略
	 			//$("#tacticsId_2").hide();
	 		}else{//改为保守策略
	 			dataModel.labelInfo.tacticsId = 2;
	 			//隐藏预约策略
	 			$("#tacticsId option[value='1']").hide();
	 			$("#labelDay").attr('disabled', false);
	 			$("#labelMonth").attr('disabled', false);
	 		}
	 		dataModel.initDimListTacticsValue = dataModel.labelInfo.tacticsId ;
		}
	}
    /**
     * 校验数据日期
     * 判断购物车的规则中标签的日期是否小于最新的数据日期
     * 小于返回false,大于等于返回true
     */
    model.checkDataDate = function(){
    	var result = true;
		var labelRuleList = model.resolveCustomRule(dataModel.labelRuleList);		
		var newLabelDay = dataModel.labelInfo.dayLabelDate;
		var newLabelMonth = dataModel.labelInfo.monthLabelDate;
		for(var i = 0; i < labelRuleList.length; i++){
			var labelRule = labelRuleList[i];
			if(labelRule.elementType == 2 && (labelRule.labelTypeId != 10 && labelRule.labelTypeId != 13)){
				var updateCycle = labelRule.updateCycle;
				var dataDate = labelRule.dataDate;
				if(updateCycle == 1) {//日周期
					if(newLabelDay > dataDate) {
						result = false;
					}
				} else if(updateCycle == 2) {//月周期
					if(newLabelMonth > dataDate) {
						result = false;
					}
				}
			}else if(labelRule.elementType == 2 &&  (labelRule.labelTypeId == 10||labelRule.labelTypeId == 13)){//组合标签
				var childCiLabelRuleList = labelRule.childCiLabelRuleList;
				for(var j = 0; j < childCiLabelRuleList.length; j++){
					var rule = childCiLabelRuleList[j];
					if(rule.elementType == 2){
						var updateCycle = rule.updateCycle;
						var dataDate = rule.dataDate;
						//生效日期
						var effectDate = rule.effectDate;
						if(updateCycle == 1) {//日周期
							if(newLabelDay > dataDate) {
								result = false;
							}
						} else if(updateCycle == 2) {//月周期
							if(newLabelMonth > dataDate) {
								result = false;
							}
						}
					}
				}
			}
		}
		return result;
    }
    /**
     * 拆分elementType为6的客户群,获取所有的标签
     */
    model.resolveCustomRule = function(labelRuleList){
		var newRuleList = [];
		for(var i = 0; i < labelRuleList.length; i++){
			var labelRule = labelRuleList[i];
			if(labelRule.elementType == 6){//客户规则
				var childCiLabelRuleList = labelRule.childCiLabelRuleList;
				for(var j = 0; j < childCiLabelRuleList.length; j++){
					var rule = childCiLabelRuleList[j];
					newRuleList.push(rule);
				}
			}else{
				newRuleList.push(labelRule);
			}
		}
		return newRuleList;
	}
    /**
     * @description 查询对象规则
     * @param  option
     * @return  
     * ------------------------------------------------------------------
     */
    model.findCustomRule = function(option) {
    	var url = $.ctx + "/api/shopCart/findShopCart";
    	if(dataModel.customGroupId){//修改
    		url = $.ctx + "/api/shopCart/findShopCart";
    	}
    	$.commAjax({
		  url: url,
		  async:false,//同步
		  postData:{
			  customGroupId : dataModel.customGroupId
		  },
		  onSuccess: function(returnObj){
			  dataModel.labelRuleList = returnObj.data.shopCartRules;	
		  }
		});
    };
    /**
	 * 查询用户数据权限
	 */
	model.getDataPrivaliege = function(){
		$.commAjax({
			  url: $.ctx + "/api/user/get",
			  onSuccess: function(returnObj){
				  dataModel.dataPrivaliegeList = returnObj.data.dataPrivaliege[3];
				  if(dataModel.dataPrivaliegeList && dataModel.dataPrivaliegeList.length > 1){
					  dataModel.isShowPrivaliegeDiv = true;
				  }else if(dataModel.dataPrivaliegeList && dataModel.dataPrivaliegeList.length == 1){
					  dataModel.checkedModelList[0] = dataModel.dataPrivaliegeList[0].id;
				  }
			  }
		});
	};
	
	/**
	 * 页面动作：修改策略
	 */
	model.changeTacticsId = function(obj){
		if(obj.selectedOptions.length >0 ){
			var selVal = obj.selectedOptions[0].value;
			//通过修改策略来联动变化数据日期
			if(selVal == 1) {//预约策略，使用最新数据日期
				$("#labelDay").val(dataModel.newDayDate);
	 			$("#labelMonth").val(dataModel.newMonthDate);
				$('#labelDay').attr('disabled', true);
				$('#labelMonth').attr('disabled', true);
				model.monthOrDayDatePicked();
			} else if(selVal == 2){//保守策略，使用最早数据日期，日期可以选择
				$("#labelDay").val(dataModel.labelDay);
	 			$("#labelMonth").val(dataModel.labelMonth);
	 			
				$('#labelDay').attr('disabled', false);
				$('#labelMonth').attr('disabled', false);
				$('#labelDay').attr('onclick', "WdatePicker({onpicked:labelInfoModel.monthOrDayDatePicked,readOnly:true, dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'" + dataModel.labelDay 
						+ "',minDate:'#F{$dp.$DV(\\'" + dataModel.labelDay + "\\',{d:-2});}'})");
				$('#labelMonth').attr('onclick', "WdatePicker({onpicked:labelInfoModel.monthOrDayDatePicked,readOnly:true,dateFmt:'yyyy-MM', isShowClear:false,maxDate:'" + dataModel.labelMonth 
						+ "', minDate:'#F{$dp.$DV(\\'" + dataModel.labelMonth + "\\',{y:-1});}'})");
				model.monthOrDayDatePicked();
			}
		}
		
		return false;
	};
	/**
	 * 统计日期选择后的操作
	 * 1.根据数据日期，设置生效日期、失效日期
	 * 2.检验规则中的生效日期
	 * 3.如果是预约策略，再重新检测数据日期
	 */
	model.monthOrDayDatePicked = function(){
		//设置周期性客户群有效开始日期
		var cycle = dataModel.labelInfo.updateCycle;
		var monthTemp = $("#labelMonth").val();
		var dayTemp = $("#labelDay").val();
		var month = monthTemp.replace(/-/g,"");
		var day = dayTemp.replace(/-/g,"");
		if(cycle == 2){//月周期客户群
			dataModel.labelInfo.effecTime = monthTemp + "-01" ;
			//结束时间小于开始时间,则清空结束时间
			var endDateByMonth = $('#endDateByMonth').val().replace(/-/g,"");
			if(endDateByMonth && endDateByMonth <= month){
				$("#endDateByMonth").val("");
				dataModel.labelInfo.failTime = "";
			}		
		}else if(cycle == 3){//日周期客户群
			dataModel.labelInfo.effecTime = dayTemp + "-01" ;
			//结束时间小于开始时间,则清空结束时间
			var endDateByDay = $('#endDateByDay').val().replace(/-/g,"");
			if(endDateByDay && endDateByDay <= day){
				$("#endDateByDay").val("");
				dataModel.labelInfo.failTime = "";
			}
		}
		//设置标签数据日期
		dataModel.labelInfo.dayLabelDate = day;
		dataModel.labelInfo.monthLabelDate = month;
 		//检验生效日期  0:通过 1：日不满足 2：月不满足 3：日、月都不满足
 		var result = model.checkEffectDate();
		$("#labelMonthTip").hide();
 		$("#labelDayTip").hide(); 		
		if(result == 0){
			
 		}else if(result == 1){
 			$("#tacticsIdTip").hide();
 			$("#labelDayTip").show();
 			return false;
 		}else if(result == 2){
 			$("#tacticsIdTip").hide();
 			$("#labelMonthTip").show();
 			return false;
 		}else if(result == 3){
 			$("#tacticsIdTip").hide();
 			$("#labelDayTip").show();
 			$("#labelMonthTip").show();
 			return false;
 		}
		//检验数据日期
		var dimListTactics = dataModel.labelInfo.tacticsId; //1:预约策略 2:保守策略
		var initDimListTacticsValue = dataModel.initDimListTacticsValue; //初始策略 1:预约策略 2:保守策略
		if(initDimListTacticsValue == 1){
			if(model.checkDataDate()){
				//切换到保守策略
				$("#tacticsId option[value='2']").show();
				//$("#tacticsId_2").click();
				//$("#appointmentTip").hide();
	 			//隐藏预约策略
	 			//$("#tacticsId_1").hide();
			}else{
				//切换到预约策略
				$("#tacticsId option[value='1']").show();
				//$("#tacticsId_1").click();
				$("#tacticsIdTip").show();
	 			//隐藏保守策略
	 			//$("#tacticsId_2").hide();
			}

		}else if(initDimListTacticsValue == 2){
			//初始是保守策略,历史数据肯定有,不再做数据校验
		}

	}
	/**
	 * 校验规则中标签的生效日期
	 * 如果生效日期小于选择的数据日期，则返回false ，说明选择的标签不符合要求
	 */
	model.checkEffectDate = function(){
		var dayResult = true;
		var monthResult = true;
		var labelRuleList = model.resolveCustomRule(dataModel.labelRuleList);;		
		var newLabelDay = dataModel.labelInfo.dayLabelDate;
		var newLabelMonth = dataModel.labelInfo.monthLabelDate;
		for(var i = 0; i < labelRuleList.length; i++){
			var labelRule = labelRuleList[i];
			if(labelRule.elementType == 2 && (labelRule.labelTypeId != 10 && labelRule.labelTypeId != 13)){
				var updateCycle = labelRule.updateCycle;
				//生效日期
				var effectDate = labelRule.effectDate;
				if(updateCycle == 1) {//日周期
					if(newLabelDay < effectDate) {
						dayResult = false;
					}
				} else if(updateCycle == 2) {//月周期
					if(newLabelMonth < effectDate) {
						monthResult = false;
					}
				}
			}else if(labelRule.elementType == 2 &&  (labelRule.labelTypeId == 10 || labelRule.labelTypeId == 13)){//组合标签
				var childCiLabelRuleList = labelRule.childCiLabelRuleList;
				for(var j = 0; j < childCiLabelRuleList.length; j++){
					var rule = childCiLabelRuleList[j];
					if(rule.elementType == 2){
						var updateCycle = rule.updateCycle;
						//生效日期
						var effectDate = rule.effectDate;
						if(updateCycle == 1) {//日周期
							if(newLabelDay < effectDate) {
								dayResult = false;
							}
						} else if(updateCycle == 2) {//月周期
							if(newLabelMonth < effectDate) {
								monthResult = false;
							}
						}
					}
				}
			}
		}
		//0:通过 1：日不满足 2：月不满足 3：日、月都不满足
		if(dayResult && monthResult){
			return 0;
		}else if(!dayResult){
			return 1;
		}else if(!monthResult){
			return 2;
		}else {
			return 3;
		}
	}
	/**
	 * 修改月份，初始化方法
	 */
	model.changeLabelMonth = function(){
		if(dataModel.labelMonth){
    		$("#labelMonth").click(function(){
	    		WdatePicker({isShowClear:false,dateFmt:'yyyy-MM',
	    			maxDate:dataModel.labelMonth,minDate:'#F{$dp.$DV(\''+dataModel.labelMonth+'\',{y:-1});}'});
	    	})
    	}
	}
	/**
	 * 修改日，初始化方法
	 */
	model.changeLabelDay = function(){
		if(dataModel.labelDay){
    		$("#labelDay").click(function(){
	    		WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',
	    			maxDate:dataModel.labelDay,minDate:'#F{$dp.$DV(\''+dataModel.labelDay+'\',{d:-2});}'});
	    	})
	    	
    	}
	}
	/**
	 * 页面动作：修改客户群周期
	 * 根据选择的周期，赋值给生效日期和失效日期
	 */
	model.changeUpdateCycle = function(){
		var customGroupId = dataModel.labelId;
		var cycle = dataModel.labelInfo.updateCycle ;
		dataModel.labelInfo.updateCycle = cycle;
		//切换分割线
		if(cycle == 1){//日
			dataModel.isShowFailTimeDayDiv = true;
			dataModel.isShowFailTimeMonthDiv = false;
			var d = dataModel.labelInfo.dayLabelDate;
			if(d != null && d!= ""){
				var _year = d.substring(0,4);
				var _month = d.substring(4,6);
				var _day = d.substring(6,8);
				dataModel.labelInfo.effecTime = _year+"-"+_month+"-"+_day ;
			}
			$("#endDateByDay").val("");
			dataModel.labelInfo.failTime = "";
			
			if(customGroupId == null || customGroupId == ""){
				//日客户群
				dataModel.labelInfo.dataDate = dataModel.labelInfo.dayLabelDate ;
			}
		}else if(cycle == 2){//月
			dataModel.isShowFailTimeDayDiv = false;
			dataModel.isShowFailTimeMonthDiv = true;
			var d = dataModel.labelInfo.monthLabelDate;
			if(d != null && d!= ""){
				var _year = d.substring(0,4);
				var _month = d.substring(4,6);
				dataModel.labelInfo.effecTime = _year+"-"+_month+"-01";
			}
			$("#endDateByMonth").val("");
			dataModel.labelInfo.failTime = "";
			if(customGroupId == null || customGroupId == ""){
				//月客户群
				dataModel.labelInfo.dataDate = dataModel.labelInfo.monthLabelDate;
			}
		}else if(cycle == 3){//一次性
			dataModel.isShowFailTimeDayDiv = false;
			dataModel.isShowFailTimeMonthDiv = false;
			if(customGroupId == null || customGroupId == ""){
				//一次性客户群
				dataModel.labelInfo.dataDate = dataModel.labelInfo.monthLabelDate;
			}
		}
	}
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
    	if(dataModel.labelInfo.updateCycle == ""){
    		$.alert("请选择生成周期。");
    		return false;
    	}
    	if(dataModel.labelInfo.updateCycle ==1 && $('#endDateByDay').val()  == ""
    		||dataModel.labelInfo.updateCycle ==2 && $('#endDateByMonth').val()  == ""){
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
     * 格式化日期
     */
    model.formatDate = function (d) {
	    var year = d.getFullYear();
	    var month = d.getMonth() + 1;
	    month = month <10 ? '0' + month : '' + month;
	    var day = d.getDate() <10 ? '0' + d.getDate() : '' + d.getDate();
		return year+ '-' + month + '-' + day;
	 }
    /**
     * 获得指定年月最后一天日期
     */
	model.getLastDay = function(year,month){
		var new_year = year;    //取当前的年份         
		var new_month = month++;//取下一个月的第一天，方便计算（最后一天不固定）         
		if(month>12){            //如果当前大于12月，则年份转到下一年         
			new_month -=12;        //月份减         
			new_year++;            //年份增         
		}        
		var new_date = new Date(new_year,new_month,1);                //取当年当月中的第一天         
		return (new Date(new_date.getTime()-1000*60*60*24)).getDate();//获取当月最后一天日期         
	}
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
    	if(model.validateForm()){
    		$.confirm('确认要保存客户群吗？', function() {
        		model.saveSubmit();
        	});
    	}
    };
    /**
     * 保存提交
     */
    model.saveSubmit = function(){
	   	 var url = $.ctx + "/api/label/labelInfo/saveCustomerLabelInfo";
	   	 if(dataModel.customGroupId){
	   		 url = $.ctx + "/labelInfo/updateCustomerLabelInfo";
	   	 }
	   	 
   		 var data = dataModel.labelInfo;
   		 data.orgId = dataModel.checkedModelList.join(',');
   		 data.configId = dataModel.configId ;
   		 if(data.updateCycle == 2){
   			var ym = $("#endDateByMonth").val();
			var year = ym.substring(0,4);
	        var month = ym.substring(5,7);
		    if(month.length == 2 && month.substring(0,1)=='0'){
		        month = month.substring(1);
		    }
		    var d = model.getLastDay(year,month);
   			data.failTime = $('#endDateByMonth').val()+"-"+d ;
   		 }else{
   			data.failTime = $('#endDateByDay').val() ;
   		 }
   		 if($('#labelDay').val()){
   			 data.dayLabelDate = $('#labelDay').val().replace(/-/g,""); 
   		 }
   		 if($('#labelMonth').val()){
   			 data.monthLabelDate = $('#labelMonth').val().replace(/-/g,"");
   		 }
   		 
   		 if(data.updateCycle == 2){
   			 data.dataDate = dataModel.newMonthDate.replace(/-/g,""); 
   		 }else{
   			 data.dataDate = dataModel.newDayDate.replace(/-/g,""); 
   		 }
   		 dataModel._submitFlag = true;
   		 $.commAjax({
   			  url: url,
   			  postData:data,
   			  onSuccess: function(returnObj){
   				  $.alert(returnObj.msg, function(){
   					  //保存成功后跳转，标签集市和客户群集市都跳转到客户群集市页面
   					  top.toggleMenu("#custom/custom_market");
   				  });
					
   			  },
   			  onFailure:function(returnObj){
				  dataModel._submitFlag = false; 
				  $.alert(returnObj.msg);
   			  }
   		  });
    }
    
	/**
	 * 返回
	 */
	model.back = function(){
		var from = $.getUrlParam("from");
		var returnUrl ="";
		if(from == 'labelmarket'){//跳转到标签集市
			returnUrl = "../label/label_market.html";
		}
		if(from == 'custommarket'){//跳转到客户群集市
			returnUrl = "./custom_market.html";
		}
		window.location.href = returnUrl;
	};
	
	/**
	 * 展示规则
	 */
	model.showRules = function(){
		var wd = $.window("客户群规则详细", $.ctx + '/aibi_lc/pages/custom/showRules.html', 600, 500);
	}
	return model;
})(window.labelInfoModel || {})