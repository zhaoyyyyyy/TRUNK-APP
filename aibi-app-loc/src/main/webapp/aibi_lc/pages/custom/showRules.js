var dataModel = {
		shopCartRules :[],
		showCartRulesCount	: false,
		customGroupId : '',
		ruleHtml : '',
		tableHtml : ''
		
}
window.loc_onload = function() {
	//初始化参数
	dataModel.configId = $.getCurrentConfigId();
	new Vue({
		el : '#dataD',
		data : dataModel
	})
	var wd = frameElement.lhgDG;
	wd.addBtn("cancel", "关闭", function() {
		wd.cancel();
	});
	showRulesModel.findCustomRule();
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
    	var url = $.ctx + "/api/shopCart/findShopCart";
    	if(dataModel.customGroupId){
    		url = $.ctx + "/api/shopCart/findShopCart";
    	}
    	$.commAjax({
		  url: url,
		  postData:{
			  customGroupId : dataModel.customGroupId
		  },
		  onSuccess: function(returnObj){
			  dataModel.showCartRulesCount = returnObj.data.showCartRulesCount;
			  dataModel.shopCartRules = returnObj.data.shopCartRules;		
			  var $ul = $("#customer-rules-ul");
			  var $table = $("#customer-rules-table");
			  //初始化客户群规则
			  model.shopCartRule(dataModel.shopCartRules);
			  $ul.append(dataModel.ruleHtml);
			  $table.append(dataModel.tableHtml).find("tbody tr:odd").addClass("oddBg");
		  }
		});
    };
    //拼接所有规则
    model.shopCartRule = function(labelRuleList){
    	var ruleHtml = "";
    	var tableHtml = "";
		for(var i = 0; i < labelRuleList.length; i++){
			var labelRule = labelRuleList[i];
			if(labelRule.elementType == 3){//括号
				ruleHtml += '<li class="tag-item-symbol fleft">'+ labelRule.calcuElement +'</li>';
			}else if(labelRule.elementType == 1){//运算符
				var operE = "";
				if(labelRule.calcuElement == "and"){
					operE = "且";
				}else if(labelRule.calcuElement == "or"){
					operE = "或";
				}else{
					operE = "剔除";
				}
				ruleHtml += '<li class="tag-item-symbol fleft">'+ operE +'</li>';
			}else if(labelRule.elementType == 2){//标签
				tableHtml += '<tr>';
				if(labelRule.labelFlag != 1 && labelRule.labelTypeId != 1){//非01型
					ruleHtml += '<li class="tag-item fleft">(非)'+ labelRule.customOrLabelName +'</li>'; 
					tableHtml += '<td  ><p class="ruleTitle" title="(非)'+labelRule.customOrLabelName+'">(非)'+ labelRule.customOrLabelName +'</p></td>';
				}else{
					ruleHtml += '<li class="tag-item fleft">'+ labelRule.customOrLabelName +'</li>';
					tableHtml += '<td  ><p class="ruleTitle" title="'+labelRule.customOrLabelName+'">'+ labelRule.customOrLabelName +'</p></td>';
				}
				var ruleStr = model.ruleAttrVal(labelRule);						
				tableHtml += '<td   > <p class="ruleContent"  title="'+ruleStr+'">'+ ruleStr +'</p></td>';
				tableHtml += '</tr>';
			}else if(labelRule.elementType == 5){//客户群清单
				ruleHtml += '<li class="tag-item fleft">客户群：'+ labelRule.customOrLabelName +'</li>';
				tableHtml += '<tr>';
				tableHtml += '<td ><p class="ruleTitle" title="'+labelRule.customOrLabelName+'">'+ labelRule.customOrLabelName +'</p></td>';
				tableHtml += '<td > <p class="ruleContent"  title="'+labelRule.attrVal+'">清单:'+ labelRule.attrVal +'</p></td>';
				tableHtml += '</tr>';
			}else if(labelRule.elementType == 6){//客户群规则
				ruleHtml += '<li class="tag-item-symbol fleft">(</li>';	
				var childLabelRuleList = labelRule.childLabelRuleList;
				var temp = shopCartRule(childLabelRuleList,param);
				ruleStr += temp;
				ruleHtml += '<li class="tag-item-symbol fleft">)</li>';
			}			
		}
		dataModel.ruleHtml = ruleHtml;
		dataModel.tableHtml = tableHtml;
	}
	
	//标签规则展开
    model.ruleAttrVal = function(labelRule){
		var attrValStr = "";
		if(labelRule.labelTypeId == 1){//01型
			if(labelRule.labelFlag != 1){
				attrValStr += "否";
			}else{
				attrValStr += "是";
			}
		}else if(labelRule.labelTypeId == 4){//指标型
			if("1" == labelRule.queryWay){
				attrValStr += "数值范围：";
				if(labelRule.contiueMinVal){
					if(labelRule.leftZoneSign == ">="){
						attrValStr += "大于等于";
					}
					if(labelRule.leftZoneSign == ">"){
						attrValStr += "大于";
					}
					attrValStr += labelRule.contiueMinVal;
				}
				if(labelRule.contiueMaxVal){
					if(labelRule.rightZoneSign == "<="){
						attrValStr += "小于等于";
					}
					if(labelRule.rightZoneSign == "<"){
						attrValStr += "小于";
					}
					attrValStr += labelRule.contiueMaxVal;
				}
				if(labelRule.unit){
					attrValStr += "(";
					attrValStr += labelRule.unit;
					attrValStr += ")";
				}
			}else if("2" == labelRule.queryWay){
				if(labelRule.exactValue){
					attrValStr += "精确值：";
					attrValStr += labelRule.exactValue;
					if(labelRule.unit){
						attrValStr += "(";
						attrValStr += labelRule.unit;
						attrValStr += ")";
					}
				}
			}
		}else if(labelRule.labelTypeId == 5 || labelRule.labelTypeId == 9 || labelRule.labelTypeId == 12){//5=枚举型
			if(labelRule.attrVal){
				//attrValStr += "已选择条件：";
				attrValStr += labelRule.attrName;
			}
		}else if(labelRule.labelTypeId == 6){//6=日期型
			if("1" == labelRule.queryWay){
				if(labelRule.startTime || labelRule.endTime){
					//attrValStr += "已选择条件：";
					if(labelRule.startTime){
						if(labelRule.leftZoneSign == ">="){
							attrValStr += "大于等于";
						}
						if(labelRule.leftZoneSign == ">"){
							attrValStr += "大于";
						}
						attrValStr += labelRule.startTime;
					}
					if(labelRule.endTime){
						if(labelRule.rightZoneSign == "<="){
							attrValStr += "小于等于";
						}
						if(labelRule.rightZoneSign == "<"){
							attrValStr += "小于";
						}
						attrValStr += labelRule.endTime;
					}
					if(labelRule.isNeedOffset == 1){
						attrValStr += "（动态偏移更新）";
					}
				}
			}else if("2" == labelRule.queryWay){
				if(labelRule.exactValue){
					//attrValStr += "已选择条件：";
					var exactStr = labelRule.exactValue.split(",");
					if(exactStr && exactStr[0] != "-1"){
						attrValStr += exactStr[0];
						attrValStr += "年";
					}
					if(exactStr && exactStr[1] != "-1"){
						attrValStr += exactStr[1];
						attrValStr += "月";
					}
					if(exactStr && exactStr[2] != "-1"){
						attrValStr += exactStr[2];
						attrValStr += "日";
					}
				}
			}
		}else if(labelRule.labelTypeId == 7){//7=模糊型，存字符串
			if("1" == labelRule.queryWay){
				if(labelRule.darkValue){
					attrValStr += "模糊值：";
					attrValStr += labelRule.darkValue;
				}
			}else if("2" == labelRule.queryWay){
				if(labelRule.exactValue){
					attrValStr += "精确值：";
					attrValStr += labelRule.exactValue;
				}
			}
		}else if(labelRule.labelTypeId == 13){//13=虚标签
			var virtualRule = "";
			if(labelRule.childLabelRuleList != null && labelRule.childLabelRuleList.length > 0){
				virtualRule += "(";
				for (var i = 0; i < labelRule.childLabelRuleList.length; i++){
					var rule = labelRule.childLabelRuleList[i];
					if(rule.elementType == 2){
						virtualRule += rule.customOrLabelName;
					}else{
						virtualRule += rule.calcuElement;
					}
				}
				virtualRule += ") ";
			}
			if("1" == labelRule.queryWay){
				attrValStr += "数值范围：";
				attrValStr += virtualRule;
				if(labelRule.contiueMinVal){
					if(labelRule.leftZoneSign == ">="){
						attrValStr += "大于等于";
					}
					if(labelRule.leftZoneSign == ">"){
						attrValStr += "大于";
					}
					attrValStr += labelRule.contiueMinVal;
				}
				if(labelRule.contiueMaxVal){
					if(labelRule.rightZoneSign == "<="){
						attrValStr += "小于等于";
					}
					if(labelRule.rightZoneSign == "<"){
						attrValStr += "小于";
					}
					attrValStr += labelRule.contiueMaxVal;
				}
				if(labelRule.unit){
					attrValStr += "(";
					attrValStr += labelRule.unit;
					attrValStr += ")";
				}
			}else if("2" == labelRule.queryWay){
				if(labelRule.exactValue){
					attrValStr += "精确值：";
					attrValStr += virtualRule;
					attrValStr += "=";
					attrValStr += labelRule.exactValue;
					if(labelRule.unit){
						attrValStr += "(";
						attrValStr += labelRule.unit;
						attrValStr += ")";
					}
				}
			}
		}else if(labelRule.labelTypeId == 8 || labelRule.labelTypeId == 10){//8=纵表标签 10=组合型
			if(labelRule.childLabelRuleList != null && labelRule.childLabelRuleList.length > 0){
				for (var i = 0; i < labelRule.childLabelRuleList.length; i++) {
					var rule = labelRule.childLabelRuleList[i];
					if(rule.labelTypeId == 1){//01型
						attrValStr += "[";
						attrValStr += rule.columnCnName;
						attrValStr += "：";
						if(rule.labelFlag != 1){
							attrValStr += "否";
						}else{
							attrValStr += "是";
						}
						attrValStr += "]";
					}else if(rule.labelTypeId == 4){
						if("1" == rule.queryWay){
							attrValStr += "[";
							attrValStr += rule.columnCnName;
							attrValStr += "：";
							if(rule.contiueMinVal){
								if(rule.leftZoneSign == ">="){
									attrValStr += "大于等于";
								}
								if(rule.leftZoneSign == ">"){
									attrValStr += "大于";
								}
								attrValStr += rule.contiueMinVal;
							}
							if(rule.contiueMaxVal){
								if(rule.rightZoneSign == "<="){
									attrValStr += "小于等于";
								}
								if(rule.rightZoneSign == "<"){
									attrValStr += "小于";
								}
								attrValStr += rule.contiueMaxVal;
							}
							if(rule.unit){
								attrValStr += "(";
								attrValStr += rule.unit;
								attrValStr += ")";
							}
							attrValStr += "]";
						}else if("2" == rule.queryWay){
							attrValStr += "[";
							attrValStr += rule.columnCnName;
							attrValStr += "：";
							if(rule.exactValue){
								attrValStr += rule.exactValue;
								if(rule.unit){
									attrValStr += "(";
									attrValStr += rule.unit;
									attrValStr += ")";
								}
							}
							attrValStr += "]";
						}
					}else if(rule.labelTypeId == 5 || rule.labelTypeId == 9){
						if(rule.attrVal){
							attrValStr += "[";
							attrValStr += rule.columnCnName;
							attrValStr += ":";
							attrValStr += rule.attrName;
							attrValStr += "]";
						}
					}else if(rule.labelTypeId == 6){
						if("1" == rule.queryWay){
							if(rule.startTime || rule.endTime){
								attrValStr += "[";
								attrValStr += rule.columnCnName;
								attrValStr += ":";
								if(rule.startTime){
									if(rule.leftZoneSign == ">="){
										attrValStr += "大于等于";
									}
									if(rule.leftZoneSign == ">"){
										attrValStr += "大于";
									}
									attrValStr += rule.startTime;
								}
								if(rule.endTime){
									if(rule.rightZoneSign == "<="){
										attrValStr += "小于等于";
									}
									if(rule.rightZoneSign == "<"){
										attrValStr += "小于";
									}
									attrValStr += rule.endTime;
								}
								if(rule.isNeedOffset == 1){
									attrValStr += "（动态偏移更新）";
								}
								attrValStr += "]";
							}
						}else if("2" == rule.queryWay){
							if(rule.exactValue){
								attrValStr += "[";
								attrValStr += rule.columnCnName;
								attrValStr += ":";
								var exactStr = rule.exactValue.split(",");
								if(exactStr && exactStr[0] != "-1"){
									attrValStr += exactStr[0];
									attrValStr += "年";
								}
								if(exactStr && exactStr[1] != "-1"){
									attrValStr += exactStr[1];
									attrValStr += "月";
								}
								if(exactStr && exactStr[2] != "-1"){
									attrValStr += exactStr[2];
									attrValStr += "日";
								}
								attrValStr += "]";
							}
						}
						
					}else if(rule.labelTypeId == 7){
						if("1" === rule.queryWay){
							if(rule.darkValue){
								attrValStr += "[";
								attrValStr += rule.columnCnName;
								attrValStr += ":";
								attrValStr += rule.darkValue;
								attrValStr += "]";
							}
						}else if("2" === rule.queryWay){
							if(rule.exactValue){
								attrValStr += "[";
								attrValStr += rule.columnCnName;
								attrValStr += ":";
								attrValStr += rule.exactValue;
								attrValStr += "]";
							}
						}
					}
				}
			}
		}
		return attrValStr;
	}
	return model;
})(window.showRulesModel || {})