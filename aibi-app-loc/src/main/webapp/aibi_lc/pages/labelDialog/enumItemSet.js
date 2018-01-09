var ruleDataModel = {
		rule : {},
		totalSizeNum : 0,
		dimTableName : '',
		itemChooseList : [],//添加展示数据
		itemChooseListSrc : [],//添加结果数据
		itemChooseCount : 0,
		dimValueSearch : '' ,//左侧查询条件
		itemChooseSearch : '' //右侧查询条件
}
window.loc_onload = function() {
	var wd = frameElement.lhgDG;
	var ruleIndex = $.getUrlParam("index");
	ruleDataModel.rule = wd.curWin.labelMarket.getDialogRuleValue(ruleIndex);
	ruleDataModel.ruleIndex = ruleIndex;
	

	wd.addBtn("ok", "确定", function() {
		if(enumRule.validateForm()){
			enumRule.setNumberValue();
		}else{
			$.alert("表单校验失败");
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
    	el : '#enumItemSet',
    	data : ruleDataModel
    });
    
    enumRule.getDimtableName();
   
}
/**
 * ------------------------------------------------------------------
 * 枚举标签
 * ------------------------------------------------------------------
 */
var enumRule = (function (model){
	/**************
	 * 根据标签查询维表表名
	 */
	model.getDimtableName = function(){
		$.commAjax({
			  url: $.ctx + "/api/label/labelInfo/getDimtableName",
			  postData:{
				  labelId : ruleDataModel.rule.calcuElement
			  },
			  onSuccess: function(returnObj){
				  ruleDataModel.dimTableName = returnObj.data;
				  enumRule.initDimtabledataPage();
			  }
		});
	}
	/**************
	 * 分页查询枚举
	 */
	model.initDimtabledataPage = function(){
		$("#mainGrid").jqGrid({
	        url: $.ctx + "/api/dimtabledata/queryPage",
	        datatype: "json",
	        postData: {
                "dimKey": '',
                "dimValue": ruleDataModel.dimValueSearch,
                "dimTableName" : ruleDataModel.dimTableName
            },
	        colNames : [ '','选项'],
	        colModel: [{
	            name: 'dimKey',
	            index: 'dimKey',
	            width: 2,
	            hidden: true
	        },{
	        	name: 'dimValue',
	            index: 'dimValue',
	            sortable: false,
	            width: 60,
	            align: "center",
	            formatter : function(value, opts, data) {
	    			return "<a href='javascript:void(0)' onclick='enumRule.addItem(\"" + data.dimKey+ "\",\"" + value+ "\")' >"+value+"</a>";
	    		}
	        }],
	        rowList: [10, 20, 30],
	        pager: '#mainGridPager',
	        // 分页的id
	        viewrecords: true,
	        width : 200
	        
	    })
	};
	/**************
	 * 分页查询枚举
	 */
	model.search = function(){
		$("#mainGrid").setGridParam({
            postData: {
                "dimKey": '',
                "dimValue": ruleDataModel.dimValueSearch,
                "dimTableName" : ruleDataModel.dimTableName
            },
            dataType : 'json'
        }).trigger("reloadGrid", [{
            page: 1
        }]);
	}
	/**
	 * 查询已经选择的条件
	 */
	model.searchChoose = function(){
		//该种赋值避免引用相同,将原数据赋值给临时值
		var temp = JSON.parse(JSON.stringify(ruleDataModel.itemChooseListSrc));
		for(var i=0;i<temp.length ;i++){
			//不包含则删除
			if(temp[i].dimValue.indexOf(ruleDataModel.itemChooseSearch)<0){
				temp.splice(i,1);
				i--;//下边恢复上一个，重新判断删除后的数组
			}
		}
		//该种赋值避免引用相同
		ruleDataModel.itemChooseList = JSON.parse(JSON.stringify(temp));
	}
	/**
	 * 添加
	 */ 
	model.addItem = function(id,name){
		var flag = false;//不存在
		for(var i=0;i<ruleDataModel.itemChooseListSrc.length;i++){
			if(id == ruleDataModel.itemChooseListSrc[i].dimKey){
				flag = true;
			}
		}
		if(!flag){
			var obj = {
					dimKey : id,
					dimValue : name
			}
			ruleDataModel.itemChooseListSrc[ruleDataModel.itemChooseListSrc.length] = obj;
			ruleDataModel.itemChooseCount = ruleDataModel.itemChooseListSrc.length;
			//该种赋值避免引用相同
			ruleDataModel.itemChooseList = JSON.parse(JSON.stringify(ruleDataModel.itemChooseListSrc));//赋值显示数据
			ruleDataModel.itemChooseSearch = '';//清空查询条件
		}
		
    }
	/**
	 * 全选添加
	 */ 
	model.addAllItem = function(){
		$.commAjax({
			  url: $.ctx + "/api/dimtabledata/queryPage",
			  postData:{
				  "dimKey": '',
	              "dimValue": ruleDataModel.dimValue,
	              "dimTableName" : ruleDataModel.dimTableName
			  },
			  onSuccess: function(returnObj){
				  ruleDataModel.itemChooseListSrc = returnObj.rows ; 
				  ruleDataModel.itemChooseList = returnObj.rows;//赋值显示数据
				  ruleDataModel.itemChooseCount = ruleDataModel.itemChooseListSrc.length;
				  ruleDataModel.itemChooseSearch = '';//清空查询条件
			  }
		});
	}
	/**
	 * 删除已选择的
	 */ 
	model.delItem = function(t){
		var index = $(t).parent().parent().attr("index");
		var item = ruleDataModel.itemChooseList[index];
		ruleDataModel.itemChooseList.splice(index,1);
		for(var i=0;i<ruleDataModel.itemChooseListSrc.length ;i++){
			if(ruleDataModel.itemChooseListSrc[i].dimKey == item.dimKey){
				ruleDataModel.itemChooseListSrc.splice(i,1);
				break;
			}
		}
		ruleDataModel.itemChooseCount = ruleDataModel.itemChooseListSrc.length;
	}
	/**
	 * 全选删除已选择的
	 */ 
	model.delAllItem = function(){
		ruleDataModel.itemChooseList = [];
		ruleDataModel.itemChooseListSrc = [];
		ruleDataModel.itemChooseCount = 0;
	}
	/**
	 * 校验
	 */
	model.validateForm = function(){
        var selectItemList= $("#addItemDetailBox").find("li");
        if(selectItemList.length <= 0){
        	$.alert("选项不能为空，请重新选择");
            return false;
        }
        /*
        if(selectItemList.length>$._maxItemNum){
            showAlert("最多选择" + $._maxItemNum +"个指标值，请重新选择","failed");
            return false;
        }*/
        return true;
    }
	/**
	 * 设置参数
	 */
	model.setNumberValue = function(){
		var wd = frameElement.lhgDG;
		var attrVals = "";
		var attrNames ="";
		for(var i=0;i<ruleDataModel.itemChooseListSrc.length ;i++){
			if(i==0){
				attrVals +=ruleDataModel.itemChooseListSrc[i].dimKey;
				attrNames +=ruleDataModel.itemChooseListSrc[i].dimValue;
			}else{
				attrVals +=","+ruleDataModel.itemChooseListSrc[i].dimKey;
				attrNames +=","+ruleDataModel.itemChooseListSrc[i].dimValue;
			}
			
		}
		ruleDataModel.rule.attrVal = attrVals;
		ruleDataModel.rule.attrName = attrNames;
		//提交变量
		wd.curWin.labelMarket.setDialogRuleValue(ruleDataModel.ruleIndex,ruleDataModel.rule);
		wd.cancel();
	}
    return model;
})(window.enumRule || {});