var ruleDataModel = {
		rule : {},
		ruleIndex : -1,
		leftClosed : false,
		rightClosed : false,
		exactValueDateYear : '',
		exactValueDateMonth : '',
		exactValueDateDay : '',
		dynamicUpdate : false
}
window.loc_onload = function() {
	var wd = frameElement.lhgDG;
	var ruleIndex = $.getUrlParam("index");
	ruleDataModel.rule = wd.curWin.calculateCenter.getDialogRuleValue(ruleIndex);
	ruleDataModel.ruleIndex = ruleIndex;
	//获取数据rule
	wd.addBtn("ok", "确定", function() {
		if(dateRule.validateForm()){
			dateRule.setDateValue();
		}
		
	});
	
	wd.addBtn("cancel", "取消", function() {
		wd.cancel();
	});
	/**
     * ------------------------------------------------------------------
     * 数值标签修改
     * ------------------------------------------------------------------
     */
    var ruleApp = new Vue({
    	el : '#dateValueSet',
    	data : ruleDataModel,
		mounted: function () {
		    this.$nextTick(function () {
			    var r = $(".easyui-validatebox");
	   			if (r.length){
	   				r.validatebox();
	   			}
	   			//初始化数据
	   			dateRule.init();
	   			$("input[name=queryWay]").click(function(){
	   		    	if($(this).val()==1){
	   		    		$("input[id^='exactValueDate']").attr("disabled","disabled");
	   					$("#startTime").removeAttr("disabled");
	   					$("#endTime").removeAttr("disabled");
	   					$("#dynamicUpdate").removeAttr("disabled");
	   					$("#leftClosed").removeAttr("disabled");
	   					$("#rightClosed").removeAttr("disabled");
	   		    	}else{
	   		    		$("#startTime").attr("disabled","disabled");
	   					$("#endTime").attr("disabled","disabled");
	   					$("#dynamicUpdate").attr("disabled","disabled");
	   					$("#leftClosed").attr("disabled","disabled");
	   					$("#rightClosed").attr("disabled","disabled");
	   					$("input[id^='exactValueDate']").removeAttr("disabled");
	   		    	}
	   		    	
	   			});
		    })
		}
    });
}

/**
 * ------------------------------------------------------------------
 * 指标标签
 * ------------------------------------------------------------------
 */
var dateRule = (function (model){

	//开发版本号
    model.version = "1.0.0";
    /**
     * 初始化数据
     */
    model.init = function(){
    	//初始化参数
    	if(!ruleDataModel.rule.queryWay){
    		ruleDataModel.rule.queryWay = 1;
    	}
    	if(ruleDataModel.rule.leftZoneSign){
    		if(ruleDataModel.rule.leftZoneSign.indexOf("=")>=0){
    			ruleDataModel.leftClosed = true;
    		}
    	}
    	if(ruleDataModel.rule.rightZoneSign){
    		if(ruleDataModel.rule.rightZoneSign.indexOf("=")>=0){
    			ruleDataModel.rightClosed = true;
    		}
    	}
    	if(ruleDataModel.rule.isNeedOffset == 1) {
    		ruleDataModel.dynamicUpdate = 1;
		}
    	if(ruleDataModel.rule.startTime) {
	    		$('#startTime').val(ruleDataModel.rule.startTime);
			}
	    	if(ruleDataModel.rule.endTime) {
	    		$('#endTime').val(ruleDataModel.rule.endTime);
			}
    	//精确值
		if(ruleDataModel.rule.exactValue){
			var itemValueArr = ruleDataModel.rule.exactValue.split(",");
			if(itemValueArr.length == 3){
				var exactValueDateYear = itemValueArr[0];
				var exactValueDateMonth = itemValueArr[1];
				var exactValueDateDay =itemValueArr[2];
				if(exactValueDateYear && exactValueDateYear != "-1"){
					ruleDataModel.exactValueDateYear = exactValueDateYear;
				}
				if(exactValueDateMonth && exactValueDateMonth != "-1"){
					ruleDataModel.exactValueDateMonth = exactValueDateMonth;
				}
				if(exactValueDateDay && exactValueDateDay != "-1"){
					ruleDataModel.exactValueDateDay = exactValueDateDay;
				}
			}
		}
		if(ruleDataModel.rule.queryWay == "1"){
			$("input[id^='exactValueDate']").attr("disabled","disabled");
			$("#startTime").removeAttr("disabled");
			$("#endTime").removeAttr("disabled");
			$("#leftClosed").removeAttr("disabled");
			$("#rightClosed").removeAttr("disabled");
		}else{
			$("#startTime").attr("disabled","disabled");
			$("#endTime").attr("disabled","disabled");
			$("#dynamicUpdate").attr("disabled","disabled");
			$("#leftClosed").attr("disabled","disabled");
			$("#rightClosed").attr("disabled","disabled");
			$("input[id^='exactValueDate']").removeAttr("disabled");
		}
		
    }
    /**
     * 校验表单
     */
	model.validateForm = function(){
		var queryWay= ruleDataModel.rule.queryWay;
		if(queryWay == "1"){
			var startTime = $.trim($("#startTime").val());
			var endTime= $.trim($("#endTime").val());
			var timeStr=startTime+endTime;
			if($.trim(timeStr) == ""){
				$.alert("时间段至少一个不能为空");
				return false;
			}
		}else {
			var exactValueDateYear = $.trim(ruleDataModel.exactValueDateYear);
			var exactValueDateMonth = $.trim(ruleDataModel.exactValueDateMonth);
			var exactValueDateDay = $.trim(ruleDataModel.exactValueDateDay);
			var exactValueDate = exactValueDateYear+exactValueDateMonth+exactValueDateDay;
			
			if(exactValueDate == ""){
				$.alert("精确日期至少一个不能为空");
				return false;
			}
			//年
			if(exactValueDateYear !=""){
				if(isNaN(exactValueDateYear)){
					$.alert("年份格式不符合要求！");
					return false;
				}
			}else{
				exactValueDateYear = 1990;//给定年固定值
			}
			//月
			if(exactValueDateMonth !=""){
				if(isNaN(exactValueDateMonth)){
					$.alert("月份格式不符合要求！");
					return false;
				}
			}else{
				exactValueDateMonth = 7;//给定月固定值
			}
			//日
			if(exactValueDateDay !=""){
				if(isNaN(exactValueDateDay)){
					$.alert("日格式不符合要求！");
					return false;
				}
			} else {
				exactValueDateDay = 20;//给定日固定值
			}
			var year = parseInt(exactValueDateYear);   
			var month = parseInt(exactValueDateMonth, 10)-1;              
			var day = parseInt(exactValueDateDay, 10); 
			var date = new Date(year, month, day);       
			var y = date.getFullYear();              
			var m = date.getMonth();              
			var d = date.getDate();       
			if ((y != year) || (m != month) || (d != day)) {                  
				$.alert("请输入合法的日期！");
				return false;
			}  
		}
		
		return true;
	};
	/**
	 * 设置参数
	 */
	model.setDateValue = function(){
			var leftZoneSign="";
			var rightZoneSign="";
			var isNeedOffset = 0;
			var exactValueDate="";
			
			if(ruleDataModel.rule.queryWay == "1"){
				var isNeedOffsetVal = ruleDataModel.dynamicUpdate;
				if(isNeedOffsetVal) {
					isNeedOffset = 1;	
				}
				var leftChecked=ruleDataModel.leftClosed;
				if(leftChecked){
					leftZoneSign=">=";
				}else{
					leftZoneSign=">";
				}
				var rightChecked=ruleDataModel.rightClosed;
				if(rightChecked){
					rightZoneSign="<=";
				}else{
					rightZoneSign="<";
				}
				var startTime = $.trim($("#startTime").val());
				var endTime= $.trim($("#endTime").val());
				ruleDataModel.rule.startTime = startTime;
				ruleDataModel.rule.endTime = endTime;
				ruleDataModel.rule.leftZoneSign = leftZoneSign;
				ruleDataModel.rule.rightZoneSign = rightZoneSign;
				ruleDataModel.rule.isNeedOffset = isNeedOffset;
				ruleDataModel.rule.exactValue = '';
			}else{
				ruleDataModel.rule.startTime = '';
				ruleDataModel.rule.endTime = '';
				ruleDataModel.rule.leftZoneSign = '';
				ruleDataModel.rule.rightZoneSign = '';
				ruleDataModel.rule.isNeedOffsetVal = '';
				var exactValueDateYear = $.trim(ruleDataModel.exactValueDateYear);
				var exactValueDateMonth = $.trim(ruleDataModel.exactValueDateMonth);
				var exactValueDateDay = $.trim(ruleDataModel.exactValueDateDay);
				if(exactValueDateYear && exactValueDateYear !=""){
					exactValueDate=exactValueDateYear;
				}else{
					exactValueDate="-1";
				}
				if(exactValueDateMonth && exactValueDateMonth !=""){
					exactValueDate += ","+exactValueDateMonth;
				}else{
					exactValueDate += ",-1";
				}
				if(exactValueDateDay && exactValueDateDay !=""){
					exactValueDate += ","+exactValueDateDay;
				}else{
					exactValueDate += ",-1";
				}
				ruleDataModel.rule.exactValue = exactValueDate;
			}
			var wd = frameElement.lhgDG;
			//提交变量
			wd.curWin.calculateCenter.setDialogRuleValue(ruleDataModel.ruleIndex,ruleDataModel.rule);
			wd.cancel();
	}
    return model;
})(window.dateRule || {});