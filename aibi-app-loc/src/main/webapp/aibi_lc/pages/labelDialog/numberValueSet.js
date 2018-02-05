var ruleDataModel = {
		rule : {},
		ruleIndex : -1,
		leftClosed : false,
		rightClosed : false
}
window.loc_onload = function() {
	var wd = frameElement.lhgDG;
	var ruleIndex = $.getUrlParam("index");
	ruleDataModel.rule = wd.curWin.labelMarket.getDialogRuleValue(ruleIndex);
	ruleDataModel.ruleIndex = ruleIndex;
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
	//获取数据rule
	wd.addBtn("ok", "确定", function() {
		if(numberRule.validateForm()){
			numberRule.setNumberValue();
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
    	el : '#numberValueSet',
    	data : ruleDataModel
    });
}

/**
 * ------------------------------------------------------------------
 * 指标标签
 * ------------------------------------------------------------------
 */
var numberRule = (function (model){

	//开发版本号
    model.version = "1.0.0";
    /**
     * 校验表单
     */
	model.validateForm = function(){
		var queryWay= ruleDataModel.rule.queryWay;
		if(queryWay == "1"){
			var contiueMinVal=$.trim(ruleDataModel.rule.contiueMinVal);
			var contiueMaxVal=$.trim(ruleDataModel.rule.contiueMaxVal);
			if(contiueMinVal == "输入下限"){
				contiueMinVal="";
			}
			if(contiueMaxVal == "输入上限") {
				contiueMaxVal="";
			}
			var contiueVal=contiueMinVal+contiueMaxVal;
			if($.trim(contiueVal) == ""){
				$.alert("请输入数值！");
				return false;
			}
			//var reg = /^(-?([1-9]\d*)|0)(\.\d+)?$/;
			var reg = /^(-?([1-9]\d{0,11})|0)(\.\d{1,4})?$/
			if(contiueMinVal !=""){
				if(!contiueMinVal.match(reg)){
					$.alert("请输入合法的数值！");
					return false;
				}
			}
			if(contiueMaxVal !=""){
				if(!contiueMaxVal.match(reg)){
					$.alert("请输入合法的数值！");
					return false;
				}
			}
			if(contiueMinVal !="" && contiueMaxVal !=""){
				if(Number(contiueMaxVal) < Number(contiueMinVal)){
					$.alert("数值范围上限不能小于下限！");
					return false;
				}
			}
		}else {
			var exactValue = $.trim(ruleDataModel.rule.exactValue);
			if(exactValue == ""){
				$.alert("精确值不能为空！");
				return false;
			}
			//var reg = /^(-?([1-9]\d*)|0)(\.\d+)?(\,(-?([1-9]\d*)|0)(\.\d+)?)*$/;
			var reg = /^(-?([1-9]\d{0,11})|0)(\.\d{1,4})?(\,(-?([1-9]\d{0,11})|0)(\.\d{1,4})?)*$/;
			if(exactValue !=""){
				if(!exactValue.match(reg)){
					$.alert("请输入合法的数值！");
					return false;
				}
			}
		}
		
		return true;
	};
	/**
	 * 设置参数
	 */
	model.setNumberValue = function(){
			var contiueMinVal = "";
			var contiueMaxVal = "";
			var leftZoneSign="";
			var rightZoneSign="";
			
			if(ruleDataModel.rule.queryWay == "1"){
				contiueMinVal=$.trim(ruleDataModel.rule.contiueMinVal);
				contiueMaxVal=$.trim(ruleDataModel.rule.contiueMaxVal);
				if(contiueMinVal == "输入下限"){
					contiueMinVal="";
				}
				if(contiueMaxVal == "输入上限") {
					contiueMaxVal="";
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
				ruleDataModel.rule.leftZoneSign = leftZoneSign;
				ruleDataModel.rule.rightZoneSign = rightZoneSign;
				ruleDataModel.rule.exactValue = '';
			}else{
				ruleDataModel.rule.contiueMinVal = '';
				ruleDataModel.rule.contiueMaxVal = '';
				ruleDataModel.rule.leftZoneSign = '';
				ruleDataModel.rule.rightZoneSign = '';
			}
			var wd = frameElement.lhgDG;
			//提交变量
			wd.curWin.labelMarket.setDialogRuleValue(ruleDataModel.ruleIndex,ruleDataModel.rule);
			wd.cancel();
	}
    return model;
})(window.numberRule || {});