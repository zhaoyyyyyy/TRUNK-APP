var ruleDataModel = {
		rule : {},
		labelVerticalColumnRelList : [],
		childLabelRuleList : [],
		ruleIndex : -1,
		dimValueSearch : '',
		dimTableName : ''
}
/**
 * 需要注意：
 * 1.纵表列和选中的子规则并不对应
 */
window.loc_onload = function() {
	var wd = frameElement.lhgDG;
	var ruleIndex = $.getUrlParam("index");
	ruleDataModel.rule = wd.curWin.calculateCenter.getDialogRuleValue(ruleIndex);
	ruleDataModel.ruleIndex = ruleIndex;
	//获取数据rule
	wd.addBtn("ok", "确定", function() {
		if(dateRule.validateForm()){
			dateRule.setVertValue();
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
    	el : '#vertValueSet',
    	data : ruleDataModel,
		mounted: function () {
			//初始化数据
   			
		    this.$nextTick(function () {
			    var r = $(".easyui-validatebox");
	   			if (r.length){
	   				r.validatebox();
	   			}
	   			//导航切换选择方式
	   	    	$("div [id^='labelElement']").hide();
	   	    	$("div [id^='labelElement']")[0].show();
	   			
		    })
		}
    });
    vertValueRule.init(); 
}

/**
 * ------------------------------------------------------------------
 * 指标标签
 * ------------------------------------------------------------------
 */
var vertValueRule = (function (model){

	//开发版本号
    model.version = "1.0.0";
    /**
     * 初始化数据
     */
    model.init = function(){
    	//舒适化纵表标签
    	$.commAjax({
			  url: $.ctx + "/api/shopCart/findVerticalLabel",
			  async	: false,//同步
			  postData:{
				  labelId : ruleDataModel.rule.calcuElement
			  },
			  onSuccess: function(returnObj){
				  ruleDataModel.labelVerticalColumnRelList = returnObj.data.ciLabelVerticalColumnRelList;
			  }
		});
    	
    	//初始化数据,事件
		for(var i=0 ; i< ruleDataModel.labelVerticalColumnRelList.length; i++ ){
			var item = ruleDataModel.labelVerticalColumnRelList[i];
			var labelTypeId 		= item.labelTypeId;
			var childCalcuElementId = item.labelVerticalColumnRelId.columnId;
			var unit 				= item.unit;
			var childCalcEl			= model.getChildLabel(childCalcuElementId);//获取设置的值
			var isExist 			= false;
			if(childCalcEl){
				isExist = true ;
				item.rule = childCalcEl;
			}else{
				item.rule = {};
			}
			if(labelTypeId == 4) {
				var queryWay = 1;
				$(".numberUnit" + childCalcuElementId).html(unit);//初始化所有的单位
				if(isExist) {//有可能没有设置值
					queryWay = item.rule.queryWay ;
					if(queryWay == 1) {
						var leftClosed		= item.rule.leftZoneSign;
						var rightClosed		= item.rule.rightZoneSign;
						if(leftClosed){
							if(leftClosed.indexOf("=")>=0){
								$('#leftClosed'  + childCalcuElementId).attr("checked" ,true);
							}
						}
						if(rightClosed){
							if(rightClosed.indexOf("=")>=0){
								$('#rightClosed' + childCalcuElementId).attr("checked" ,true);
							}
						} 
					}
				}
				
				$('.queryMethodControl' + childCalcuElementId).click(function() {
					$('.queryMethod').removeClass('current');
					$(this).addClass('current');
					$('.queryMethod').parent().siblings('dd').find('input').attr('disabled', 'disabled');
					$('.current').parent().siblings('dd').find('input').removeAttr('disabled');
				});
			} else if(labelTypeId == 5) {
				if(isExist) {
					var attrVal 	= childCalcEl.attrVal;
					var attrName 	= childCalcEl.attrName;
					var itemKeyArr 	= [];
					var itemNameArr = [];
					if(attrVal) {
						itemKeyArr = attrVal.split(',');
						itemNameArr = attrName.split(',');
						for ( var i = 0, len; i < itemKeyArr.length; i++) {
							model.addEnumItem(item,itemKeyArr[i],itemNameArr[i]);
						}
					}
				}
				
				$('#calcuElement' + childCalcuElementId).val(childCalcuElementId);
				// 添加和删除操作
				$('#addItemAction' + childCalcuElementId).click(function() {
					model.addEnumItem(childCalcuElementId);
				});
				$('#removeItemAction' + childCalcuElementId).click(function() {
					$('#addItemDetailBox' + childCalcuElementId).find('.itemLiNextAHover').parent().remove();
					calcEnumChooseItemNum(childCalcuElementId);
				});
				// 添加和删除操作
				$('#addAllItemAction' + childCalcuElementId).click(function() {
					$("#_loading"+childCalcuElementId).show();
					var dimName = $("#dimName" + childCalcuElementId).val();
					var calcuElement = $("#calcuElement" + childCalcuElementId).val();
					var enumCategoryId=$("#enumCategoryId"+childCalcuElementId).val();
					var para = {
						"columnId" : calcuElement,
						"dimName" : dimName,
						"enumCategoryId" : enumCategoryId
					};
					var actionUrl = '${ctx}/ciLabelInfo/findAllLabelDimValueByColumnId';
					$.ajax({
						url:actionUrl,
						type:"POST",
						dataType: "json",
						data: para,
						success:function(result){
							if(result.success){
								var dimValueList = result.dimValueList;
								var $liTemp = '';
								$.each(dimValueList, function (n, value) {  
									$('#addItemDetailBox' + childCalcuElementId).find('a[id="'+value.V_KEY+'"]').parent().remove();
									$liTemp += '<li><a href="javascript:void(0);" ondblclick="delEnumItemByDbClick(\'' 
											+ childCalcuElementId + '\',this);" ' 
											+ 'onclick="aAddOrRemoveClass(this);" id="'
											+ value.V_KEY
											+ '" data="'
											+ value.V_KEY
											+ '" >'
											+ value.V_NAME + '</a></li>';
					          	}); 
								$('#addItemDetailBox' + childCalcuElementId).append($liTemp);
								calcEnumChooseItemNum(childCalcuElementId);
								$("#_loading"+childCalcuElementId).hide();
							}else{
								$("#_loading"+childCalcuElementId).hide();
								$.fn.weakTip({"tipTxt":result.msg});
							}
						},
						error:function(){
							$("#_loading"+childCalcuElementId).hide();
							$.fn.weakTip({"tipTxt":'获取所有枚举类型标签的维值失败!'});
						}
					});
				});
				$('#removeAllItemAction' + childCalcuElementId).click(function() {
					$('#addItemDetailBox' + childCalcuElementId).empty();
					$('#selectedItemSize' + childCalcuElementId).html(0);
				});
				//添加选中效果
				$('#addItemDetailBox' + childCalcuElementId + ' a, #itemChooseDetailBox' + childCalcuElementId + ' a').click(function() {
					if ($(this).hasClass('itemLiNextAHover')) {
						$(this).removeClass('itemLiNextAHover');
					} else {
						$(this).addClass('itemLiNextAHover');
					}
				});
				/**
				serchItemList(childCalcuElementId);
				
				initEnumCategorys(childCalcuElementId);
				var tableChild = '';
				var items = $('#itemChooseDetailBox'  + childCalcuElementId).children('li');
				if(items) {
					for(var i=0; i<items.length; i++) {
						var item = $(items[i]);
						tableChild += '<tr><td>' + item.children('a').attr('data') + '</td><td>' + item.text() + '</td></tr>';
					}
				}**/
			} else if(labelTypeId == 6) {
				//初始化参数
		    	if(!item.rule.queryWay){
		    		item.rule.queryWay = 1;
		    	}
				if(isExist) {
					var queryWay = item.rule.queryWay ;
					if(queryWay == "1"){
						if(item.rule.leftZoneSign){
				    		if(item.rule.leftZoneSign.indexOf("=")>=0){
				    			item.leftClosed = true;
				    		}
				    	}
				    	if(item.rule.rightZoneSign){
				    		if(item.rule.rightZoneSign.indexOf("=")>=0){
				    			item.rightClosed = true;
				    		}
				    	}
				    	if(item.rule.isNeedOffset == 1) {
				    		item.dynamicUpdate = 1;
						}
				    	if(item.rule.startTime) {
					    	('#startTime'+childCalcuElementId).val(item.rule.startTime);
						}
				    	if(item.rule.endTime) {
				    		$('#endTime'+childCalcuElementId).val(item.rule.endTime);
						}
					}else{
						//精确值
						if(item.rule.exactValue){
							var itemValueArr = item.rule.exactValue.split(",");
							if(itemValueArr.length == 3){
								var exactValueDateYear = itemValueArr[0];
								var exactValueDateMonth = itemValueArr[1];
								var exactValueDateDay =itemValueArr[2];
								if(exactValueDateYear && exactValueDateYear != "-1"){
									item.exactValueDateYear = exactValueDateYear;
								}
								if(exactValueDateMonth && exactValueDateMonth != "-1"){
									item.exactValueDateMonth = exactValueDateMonth;
								}
								if(exactValueDateDay && exactValueDateDay != "-1"){
									item.exactValueDateDay = exactValueDateDay;
								}
							}
						}
					}
				}
				if(queryWay == "1"){
					
					$("#exactValueDateYear"+ childCalcuElementId).attr("disabled","disabled");
					$("#exactValueDateMonth"+ childCalcuElementId).attr("disabled","disabled");
					$("#exactValueDateDay"+ childCalcuElementId).attr("disabled","disabled");
					
					$("#startTime"+ childCalcuElementId).removeAttr("disabled");
					$("#endTime"+ childCalcuElementId).removeAttr("disabled");
					$("#dynamicUpdate"+ childCalcuElementId).removeAttr("disabled");
					$("#leftClosed"+ childCalcuElementId).removeAttr("disabled");
					$("#rightClosed"+ childCalcuElementId).removeAttr("disabled");
				}else{
					$("#startTime"+ childCalcuElementId).attr("disabled","disabled");
					$("#endTime"+ childCalcuElementId).attr("disabled","disabled");
					$("#dynamicUpdate"+ childCalcuElementId).attr("disabled","disabled");
					$("#leftClosed"+ childCalcuElementId).attr("disabled","disabled");
					$("#rightClosed"+ childCalcuElementId).attr("disabled","disabled");
					
					$("#exactValueDateYear"+ childCalcuElementId).removeAttr("disabled");
					$("#exactValueDateMonth"+ childCalcuElementId).removeAttr("disabled");
					$("#exactValueDateDay"+ childCalcuElementId).removeAttr("disabled");
				}
				
				$("input[name=queryWay6"+childCalcuElementId+"]").click(function(){
	   		    	if($(this).val()==1){
	   		    		$("#exactValueDateYear"+ childCalcuElementId).attr("disabled","disabled");
						$("#exactValueDateMonth"+ childCalcuElementId).attr("disabled","disabled");
						$("#exactValueDateDay"+ childCalcuElementId).attr("disabled","disabled");
	   					$("#startTime"+childCalcuElementId).removeAttr("disabled");
	   					$("#endTime"+childCalcuElementId).removeAttr("disabled");
	   					$("#dynamicUpdate"+childCalcuElementId).removeAttr("disabled");
	   					$("#leftClosed"+childCalcuElementId).removeAttr("disabled");
	   					$("#rightClosed"+childCalcuElementId).removeAttr("disabled");
	   		    	}else{
	   		    		$("#startTime"+childCalcuElementId).attr("disabled","disabled");
	   					$("#endTime"+childCalcuElementId).attr("disabled","disabled");
	   					$("#dynamicUpdate"+childCalcuElementId).attr("disabled","disabled");
	   					$("#leftClosed"+childCalcuElementId).attr("disabled","disabled");
	   					$("#rightClosed"+childCalcuElementId).attr("disabled","disabled");
	   					$("#exactValueDateYear"+ childCalcuElementId).removeAttr("disabled");
						$("#exactValueDateMonth"+ childCalcuElementId).removeAttr("disabled");
						$("#exactValueDateDay"+ childCalcuElementId).removeAttr("disabled");
	   		    	}
	   		    	$("#dateSnameTip" + childCalcuElementId).hide();
	   			});
				
			} else if(labelTypeId == 7) {
				
				//初始化参数
				if(!item.rule.queryWay){
					item.rule.queryWay = 1;
				}
				$("input[name=queryWay7"+childCalcuElementId+"]").click(function(){
	   		    	if($(this).val()==1){
	   		    		$('#darkValue'+childCalcuElementId).removeAttr('disabled');
	   		    		$('#exactValue'+childCalcuElementId).attr('disabled',true);
	   		    	}else{
	   		    		$('#exactValue'+childCalcuElementId).removeAttr('disabled');
	   		    		$('#darkValue'+childCalcuElementId).attr('disabled',true);
	   		    	}
	   		    	
	   			});
			}
		}
		
    }
    /**
     * 判断是否已经存在对应的子标签，如果存在则对div属性进行重新修改，如果不存在则新加div
     * 获取计算中心中当前标签的子标签
     */
    model.getChildLabel = function(childLabelId){
    	var result;
    	var childLabelRuleList = ruleDataModel.rule.childLabelRuleList;
    	if(childLabelRuleList){
    		for(var i=0; i<childLabelRuleList.length; i++) {
    			var el = childLabelRuleList[i];
    			if(el.calcuElement == childLabelId) {
    				result = el;
    				break;
    			}
    		}
    	}
		return result;
    }
    /**
     * 切换标签
     */
    model.changeTabFun = function(objTab){
    	$("div [id^='labelElement']").hide();
    	$('#switchImportWayBox li').removeClass('current');
		$(this).addClass('current');
		var index  = Number($(objTab).attr('index'));
		var labelTypeId = ruleDataModel.labelVerticalColumnRelList[index].labelTypeId;
		var columnId = ruleDataModel.labelVerticalColumnRelList[index].labelVerticalColumnRelId.columnId;
		$('#labelElement' + labelTypeId + '' + columnId).show();
    }
    /**************
	 * 分页查询枚举
	 */
	model.initDimtabledataPage = function(gridId){
		$("#mainGrid"+gridId).jqGrid({
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
	            align: "center"
	        }],
	        rowList: [10, 20, 30],
	        pager: '#mainGridPager'+gridId,
	        // 分页的id
	        viewrecords: true,
	        width : 200,
	    	/**
	    	 * 添加
	    	 */
	        onSelectRow: function(ids) { //单击选择行  
                var rodData= $("#mainGrid").jqGrid("getRowData",ids);//
                var id=rodData.dimKey;
                var name=rodData.dimValue;
            },  
	        
	    });
		$("#mainGrid"+gridId).jqGrid('setLabel',0, '序号');
	};
	/**
	 * 枚举标签：添加
	 */ 
	model.addEnumItem = function(objModel,id,name){
		var flag = false;//不存在
		for(var i=0;i<objModel.itemChooseListSrc.length;i++){
			if(id == objModel.itemChooseListSrc[i].dimKey){
				flag = true;
			}
		}
		if(!flag){
			var obj = {
					dimKey : id,
					dimValue : name
			}
			objModel.itemChooseListSrc[objModel.itemChooseListSrc.length] = obj;
			objModel.itemChooseCount = objModel.itemChooseListSrc.length;
			//该种赋值避免引用相同
			objModel.itemChooseList = JSON.parse(JSON.stringify(objModel.itemChooseListSrc));//赋值显示数据
			objModel.itemChooseSearch = '';//清空查询条件
		}
		
    }
	/**
	 * 全选添加
	 */ 
	model.addAllEnumItem = function(objModel){
		$.commAjax({
			  url: $.ctx + "/api/dimtabledata/queryPage",
			  postData:{
				  "dimKey": '',
	              "dimValue": ruleDataModel.dimValue,
	              "dimTableName" : ruleDataModel.dimTableName
			  },
			  onSuccess: function(returnObj){
				  objModel.itemChooseListSrc = returnObj.rows ; 
				  objModel.itemChooseList = returnObj.rows;//赋值显示数据
				  objModel.itemChooseCount = objModel.itemChooseListSrc.length;
				  objModel.itemChooseSearch = '';//清空查询条件
			  }
		});
	}
	
	/**
	 * 删除已选择的
	 */ 
	model.delEnumItem = function(t){
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
	model.delEnumAllItem = function(objModel){
		objModel.itemChooseList = [];
		objModel.itemChooseListSrc = [];
		objModel.itemChooseCount = 0;
	}
    /**
     * 校验表单
     */
	model.validateForm = function(){
		var flag = true ;
		for(var i= 0 ; i < ruleDataModel.labelVerticalColumnRelList ;i++){
			var item = ruleDataModel.labelVerticalColumnRelList[i] ;
			if(item.labelTypeId = 4){
				if(model.validateNumberForm()){
					flag = false ;
					break;
				}
			}else if(item.labelTypeId = 5){
				if(model.validateEnumForm()){
					flag = false ;
					break;
				}
			}else if(item.labelTypeId = 6){
				if(model.validateDateForm()){
					flag = false ;
					break;
				}
			}else if(item.labelTypeId = 7){
				if(model.validateTextForm()){
					flag = false ;
					break;
				}
			}else{
				//
			}
		}
		return flag; 
	}
	/**
     * 校验表单
     */
	model.validateNumberForm = function(item){
		var queryWay= item.rule.queryWay;
		if(queryWay == "1"){
			var contiueMinVal=$.trim(item.rule.contiueMinVal);
			var contiueMaxVal=$.trim(item.rule.contiueMaxVal);
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
			var exactValue = $.trim(item.rule.exactValue);
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
	 * 校验标签 枚举5
	 */
	model.validateEnumForm = function(item){
		var id = item.labelVerticalColumnRelId.columnId ; 
        var selectItemList= $("#addItemDetailBox"+id).find("li");
        if(selectItemList.length <= 0){
        	$.alert("选项不能为空，请重新选择");
            return false;
        }
        return true;
    }
    /**
     * 校验标签 日期 6
     */
	model.validateDateForm = function(item){
		var queryWay= item.rule.queryWay;
		var id = item.labelVerticalColumnRelId.columnId ; 
		if(queryWay == "1"){
			var startTime = $.trim($("#startTime"+id).val());
			var endTime= $.trim($("#endTime"+id).val());
			var timeStr=startTime+endTime;
			if($.trim(timeStr) == ""){
				$.alert("时间段至少一个不能为空");
				return false;
			}
		}else {
			var exactValueDateYear = $.trim(item.exactValueDateYear);
			var exactValueDateMonth = $.trim(item.exactValueDateMonth);
			var exactValueDateDay = $.trim(item.exactValueDateDay);
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
     * 校验文本类型的7
     */
	model.validateTextForm = function(item){
		var queryWay= item.rule.queryWay;
		if(queryWay == "1"){
			var darkValue=$.trim(item.rule.darkValue);
			if($.trim(darkValue) == ""){
				$.alert("模糊值不能为空！");
				return false;
			}
		}else {
			var exactValue = $.trim(item.rule.exactValue);
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
	model.setVertValue = function(){
			var childrenRule = [];
			for(var i= 0 ; i < ruleDataModel.labelVerticalColumnRelList ;i++){
				var item = ruleDataModel.labelVerticalColumnRelList[i] ;
				if(item.labelTypeId = 4){
					
				}else if(item.labelTypeId = 5){
					
				}else if(item.labelTypeId = 6){
					
				}else if(item.labelTypeId = 7){
					
				}else{
					//
				}
			}
			ruleDataModel.rule.childrenRule = childrenRule ;
			var wd = frameElement.lhgDG;
			//提交变量
			wd.curWin.calculateCenter.setDialogRuleValue(ruleDataModel.ruleIndex,ruleDataModel.rule);
			wd.cancel();
	}
    return model;
})(window.dateRule || {});