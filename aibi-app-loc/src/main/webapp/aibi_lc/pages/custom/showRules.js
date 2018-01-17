var dataModel = {
		shopCartRules :[],
		showCartRulesCount	: false
}
window.loc_onload = function() {
	//初始化参数
	dataModel.configId = $.getCurrentConfigId();
	new Vue({
		el : '#dataD',
		data : dataModel
	})
	
}

/**
 * ------------------------------------------------------------------
 * 标签集市
 * ------------------------------------------------------------------
 */
var showRulesModel = (function (model){
	/**
     * @description 查询对象
     * @param  option
     * @return  
     * ------------------------------------------------------------------
     */
    model.findCustomRule = function(option) {
    	$.commAjax({
		  url: $.ctx + "/api/shopCart/findCustomRule",
		  postData:{
			  customGroupId : dataModel.customGroupId
		  },
		  onSuccess: function(returnObj){
			  dataModel.categoryInfoList = returnObj.data;
		  }
		});
    };
   
	return model;
})(window.showRulesModel || {})