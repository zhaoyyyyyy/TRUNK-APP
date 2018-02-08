var ruleDataModel = {
		rule : {},
		ruleIndex : -1
}
window.loc_onload = function() {
	var wd = frameElement.lhgDG;
	var ruleIndex = $.getUrlParam("index");
	ruleDataModel.rule = wd.curWin.calculateCenter.getDialogRuleValue(ruleIndex);
	ruleDataModel.ruleIndex = ruleIndex;
	//初始化参数
	if(!ruleDataModel.rule.queryWay){
		ruleDataModel.rule.queryWay = 1;
	}
	//获取数据rule
	wd.addBtn("ok", "确定", function() {
		if(textRule.validateForm()){
			textRule.setTextValue();
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
    	el : '#textValueSet',
    	data : ruleDataModel,
		mounted: function () {
		    this.$nextTick(function () {
			    var r = $(".easyui-validatebox");
	   			if (r.length){
	   				r.validatebox();
	   			}
	   			$("input[name=queryWay]").click(function(){
	   		    	if($(this).val()==1){
	   		    		$('#darkValue').removeAttr('disabled');
	   		    		$('#exactValue').attr('disabled',true);
	   		    	}else{
	   		    		$('#exactValue').removeAttr('disabled');
	   		    		$('#darkValue').attr('disabled',true);
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
var textRule = (function (model){

	//开发版本号
    model.version = "1.0.0";
    /**
     * 校验表单
     */
	model.validateForm = function(){
		var queryWay= ruleDataModel.rule.queryWay;
		if(queryWay == "1"){
			var darkValue=$.trim(ruleDataModel.rule.darkValue);
			if($.trim(darkValue) == ""){
				$.alert("模糊值不能为空！");
				return false;
			}
		}else {
			var exactValue = $.trim(ruleDataModel.rule.exactValue);
			if(exactValue == ""){
				$.alert("精确值不能为空！");
				return false;
			}
			if(exactValue.indexOf(",") == 0 || exactValue.lastIndexOf(",") == exactValue.length-1 || exactValue.indexOf(",,") !=-1){
				$.alert("请输入合法的值！");
				return false;
			}
		}
		
		return true;
	};
	/**
	 * 设置参数
	 */
	model.setTextValue = function(){
			
			if(ruleDataModel.rule.queryWay == "1"){
				ruleDataModel.rule.exactValue = '';
			}else{
				ruleDataModel.rule.darkValue = '';
			}
			var wd = frameElement.lhgDG;
			//提交变量
			wd.curWin.calculateCenter.setDialogRuleValue(ruleDataModel.ruleIndex,ruleDataModel.rule);
			wd.cancel();
	}
    return model;
})(window.textRule || {});