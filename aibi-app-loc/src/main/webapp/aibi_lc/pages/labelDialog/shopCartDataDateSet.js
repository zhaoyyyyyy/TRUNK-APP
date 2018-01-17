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
	//dataModel.existMonthLabel = wd.curWin.labelMarket.existMonthLabel;
	//dataModel.existDayLabel = wd.curWin.labelMarket.existDayLabel;
	dataModel.labelMonth = wd.curWin.labelMarket.labelMonth;
	dataModel.labelMonth = '2017-03';
	dataModel.labelDay = wd.curWin.labelMarket.labelDay;
	$('#labelMonth').val(dataModel.labelMonth);
	$('#labelDay').val(dataModel.labelDay);
	dataDateModel.getDataPrivaliege();

	//获取数据rule
	wd.addBtn("ok", "继续", function() {
		var labelMonthTemp = $('#labelMonth').val().replace(/-/g,"");
		var labelDayTemp = "";
		if($('#labelDay').val()){
			labelDayTemp = $('#labelDay').val().replace(/-/g,"");
		}
		if(dataModel.checkedModelList.length <1){
			$.alert("当前没有数据权限，请联系管理员。");
			return ;
		}	
		if(!dataDateModel.checkRuleEffectDate(labelMonthTemp,labelDayTemp)){
			//验证sql
			var checkedModelListStr = dataModel.checkedModelList.join(',');
			if(wd.curWin.labelMarket.validateSql(labelMonthTemp,labelDayTemp,checkedModelListStr)){
				wd.curWin.labelMarket.submitForExplore(labelMonthTemp,labelDayTemp,checkedModelListStr);
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
    	data : dataModel,
		mounted: function () {
		    this.$nextTick(function () {
		    	
		    	$("#labelMonth").datepicker({
		    		monthNamesShort: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],  // 区域化月名为中文  
			        changeYear: true,          // 年下拉菜单  
			        changeMonth: true,             // 月下拉菜单  
			        showButtonPanel: true,         // 显示按钮面板  
			        showMonthAfterYear: true,  // 月份显示在年后面  
			        currentText: "本月",         // 当前日期按钮提示文字  
			        closeText: "关闭",           // 关闭按钮提示文字  
			        dateFormat: "yy-mm",       // 日期格式  
			        onChangeMonthYear: function(dateText, inst) {
					    var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();  
					    var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();  
					    $(this).datepicker('setDate', new Date(year, month, 1)); 
						$(this).datepicker( "hide" );
					}
			    });
		    	$("#labelDay").click(function(){
		    		if($(".ui-datepicker-div").css("display")=="none"){
		    			$('.ui-datepicker-calendar').hide();
		    		}else{
		    			$('.ui-datepicker-calendar').show();
		    		}
		    	})
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
	}
	return model;
})(window.dataDateModel || {});