var dataModel = {
		rule : {},
		totalSizeNum : 0
}
window.loc_onload = function() {
	var index = $.getUrlParam("index");
	var wd = frameElement.lhgDG;
	var pa = wd.parent;
	var rule = wd.curWin.dataModel.ruleList[index];
	dataModel.rule = rule;
	

	wd.addBtn("ok", "确定", function() {
		var url_ = "";
		var msss = "";
//		if($("#saveDataForm").validateForm){
		
//		}else{
//			$.alert("表单校验失败");
//		}
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
    	data : dataModel
    });
}
/**
 * ------------------------------------------------------------------
 * 枚举标签
 * ------------------------------------------------------------------
 */
var enumRule = (function (model){
	/**************
	 * 分页查询尾标
	 */
	model.queryPage = function(){
		
	}
	/****
	 * 鼠标选中事件
	 */
	model.setItemStatus = function(flag , divBoxId){
		
	}
});