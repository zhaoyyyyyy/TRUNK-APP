var dataModel = {
	existMonthLabel : true,
	existDayLabel : true,
	labelMonth : '',
	labelDay : ''
}
window.loc_onload = function() {
	var wd = frameElement.lhgDG;
	dataModel.existMonthLabel = wd.curWin.labelMarket.existMonthLabel;
	dataModel.existDayLabel = wd.curWin.labelMarket.existDayLabel;
	dataModel.labelMonth = wd.curWin.labelMarket.labelMonth;
	dataModel.labelDay = wd.curWin.labelMarket.labelDay;
	$('#labelMonth').val(dataModel.labelMonth);
	$('#labelDay').val(dataModel.labelDay);
	//获取数据rule
	wd.addBtn("ok", "继续", function() {
		var labelMonthTemp = $('#labelMonth').val().replace(/-/g,"");
		var labelDayTemp = "";
		if($('#labelDay').val()){
			labelDayTemp = $('#labelDay').val().replace(/-/g,"");
		}
			
		if(!dataDateModel.checkRuleEffectDate(labelMonthTemp,labelDayTemp)){
			//验证sql
			if(wd.curWin.labelMarket.validateSql(labelMonthTemp,labelDayTemp)){
				wd.curWin.labelMarket.submitForExplore(labelMonthTemp,labelDayTemp);
				wd.cancel();
			}
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
    var app = new Vue({
    	el : '#dataDateSet',
    	data : dataModel
    });
}
/**
 * ------------------------------------------------------------------
 * 有效日期设置
 * ------------------------------------------------------------------
 */
var dataDateModel = (function (model){
	/**************
	 * 判断所选日期是否早于标签生效日期
     * 传入参数：newLabelMonthFormat所选月数据日期；newLabelDayFormat所选日数据日期
     * {flag:true,msg:""} flag:true所选数据日期早于标签生效日期，false没有早于标签生效日期
	 */
	model.checkRuleEffectDate = function(labelMonthArg,labelDayArg){
		var isNewDate = false;
		var msgDate = "";
		$.commAjax({
			  url: $.ctx + "/api/shopCart/checkRuleEffectDate",
			  async	: false,//同步
			  postData:{
				  "newLabelMonthFormat":labelMonthArg,
				  "newLabelDayFormat":labelDayArg
			  },
			  onSuccess: function(returnObj){
				  if(returnObj.status == '200'){
					  isNewDate = false ;
				  }else{
					  isNewDate = true ;
					  msgDate = returnObj.msg;
				  }
				  
			  }
		});
		if(isNewDate){
			$.alert(msgDate);
			
		}
		return isNewDate;
	}
	return model;
})(window.dataDateModel || {});