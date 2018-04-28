var dataModel = {
	existMonthLabel : true,
	existDayLabel : true,
	labelMonth : '',
	labelDay : '',
	dataPrivaliegeList : [],//用户数据权限
	checkedModelList : [],//数据选中
	isShowPrivaliegeDiv : false,//数据范围是否显示
	nowDate : new Date()
}
window.loc_onload = function() {
	var wd = frameElement.lhgDG;
	dataModel.existMonthLabel = wd.curWin.dataModel.existMonthLabel;
	dataModel.existDayLabel = wd.curWin.dataModel.existDayLabel;
	dataModel.labelMonth = wd.curWin.dataModel.labelMonth;
	dataModel.labelDay = wd.curWin.dataModel.labelDay;
	$('#labelMonth').val(dataModel.labelMonth);
	$('#labelDay').val(dataModel.labelDay);
	dataDateModel.getDataPrivaliege();

	//获取数据rule
	wd.addBtn("ok", "继续", function() {
		var labelMonthTemp = "";
		var labelDayTemp = "";
		if($('#labelDay').val()){
			labelDayTemp = $('#labelDay').val().replace(/-/g,"");
		}
		if($('#labelMonth').val()){
			labelMonthTemp = $('#labelMonth').val().replace(/-/g,"");
		}
		if(dataModel.checkedModelList.length <1){
			$.alert("当前用户拥有多个数据权限，请选择需要使用的数据范围。");
			return ;
		}	
		dataDateModel.checkRuleEffectDate(labelMonthTemp,labelDayTemp,wd);
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
    	data : dataModel,
		mounted: function () {
		    this.$nextTick(function () {
		    	var month = 1 - $.getSysConfig('LOC_CONFIG_APP_MAX_KEEP_MONTHS');
		    	var day = 1 - $.getSysConfig('LOC_CONFIG_APP_MAX_KEEP_DAYS');
		    	if(dataModel.labelMonth){
		    		$("#labelMonth").click(function(){
			    		WdatePicker({isShowClear:false,dateFmt:'yyyy-MM',
			    			maxDate:dataModel.labelMonth,minDate:'#F{$dp.$DV(\''+dataModel.labelMonth+'\',{M:'+month+'});}'});
			    	})
		    	}
		    	if(dataModel.labelDay){
			    	$("#labelDay").click(function(){
			    		WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',
			    			maxDate:dataModel.labelDay,minDate:'#F{$dp.$DV(\''+dataModel.labelDay+'\',{d:'+day+'});}'});
			    	})
		    	}
		    	
		    })
		}
    });
    //在vue渲染之后才可以
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
	model.checkRuleEffectDate = function(labelMonthArg,labelDayArg,wd){
		$.commAjax({
			  url: $.ctx + "/api/shopCart/checkRuleEffectDate",
			  postData:{
				  "newLabelMonthFormat":labelMonthArg,
				  "newLabelDayFormat":labelDayArg
			  },
			  isShowMask : true,
			  maskMassage : '正在校验规则中，请稍等...',
			  onSuccess: function(returnObj){
				    //验证sql
					var checkedModelListStr = dataModel.checkedModelList.join(',');
					if(wd.curWin.calculateCenter.validateSql(labelMonthArg,labelDayArg,checkedModelListStr)){
						wd.curWin.calculateCenter.submitForExplore(labelMonthArg,labelDayArg,checkedModelListStr);
						wd.cancel();
					}
			  },
			  onFailure: function(returnObj){
				  $.alert(returnObj.msg);
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
				  dataModel.dataPrivaliegeList = returnObj.data.dataPrivaliege[$.xzqh];
				  if(dataModel.dataPrivaliegeList && dataModel.dataPrivaliegeList.length > 1){
					  dataModel.isShowPrivaliegeDiv = true;
				  }else if(dataModel.dataPrivaliegeList && dataModel.dataPrivaliegeList.length == 1){
					  dataModel.checkedModelList[0] = dataModel.dataPrivaliegeList[0].id;
				  }
			  }
		});
	}
	return model;
})(window.dataDateModel || {});