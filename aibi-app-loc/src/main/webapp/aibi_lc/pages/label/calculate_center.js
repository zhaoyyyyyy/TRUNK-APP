/**
 * ------------------------------------------------------------------
 * 计算中心功能
 * ------------------------------------------------------------------
 */
var calculateCenter = (function (model){
	
	/**
     * @description 标签添加缓存，购物车动画效果===true ,组装rule
     * @param  
     * @return  
     * ------------------------------------------------------------------
     */
	model.addToShoppingCar = function(index){
    	var labelInfo = dataModel.labelInfoList[index];
    	if(labelInfo.groupType == 0 ){
    		model.addShopCart(labelInfo.labelId,1,'',0);//标签
    	}else{
    		model.addShopCart(labelInfo.labelId,2,'',0);//客户群
    	}
    	
    };
    /**
	 * 加入购物车缓存
	 * @id 标签、用户群或者模板的ID
	 * @typeId 类型：1、标签，2、用户群，3、模板
	 * @defaultOp 操作类型:and,or,- ;没有值为为空字符串，为空时后台默认是and
	 * @isEditCustomFlag 是否是修改操作，1、修改操作，0、保存操作
	 */
    model.addShopCart = function(id,typeId,isEditCustomFlag,defaultOp){
    	var flag = false;
    	var msg = "";
    	var url = "";
		if(typeId == 1) {
			//校验标签有效性
			url = $.ctx + "/api/shopCart/findLabelValidate";
		}
		if(typeId == 2) {
			url = $.ctx + "/api/shopCart/findCusotmValidate";
		}
		//校验标签有效性
		$.commAjax({
			  url: url,
			  async	: false,//同步
			  postData:{labelId :  $.trim(id)},
			  onSuccess: function(returnObj){
			  	  //1.如果验证失败，需要返回 2.需要提示
				  if(returnObj.status == '201'){
					  flag = true; 
					  msg = returnObj.msg ;
				  }
				  
			  }
		});
		if(flag){
			$.alert(msg);
			return;
		}
		var para={
				"calculationsId"	: $.trim(id),
				"typeId"			: typeId,
				"isEditCustomFlag"	: isEditCustomFlag,
				"defaultOp"        : defaultOp
		};
		$.commAjax({
			  url: $.ctx + "/api/shopCart/saveShopSession",
			  postData:para,
			  onSuccess: function(returnObj){
				  	var status = returnObj.status;
					if (status == '200'){
						model.refreshShopCart();
					}else{
						$.alert("添加标签失败");
					}
			  }
		});
    };
    /**
     * @description 刷新缓存
     * @param  
     * @return  
     * ------------------------------------------------------------------
     */
    model.refreshShopCart = function(){
    	var _this = this;
    	$.commAjax({
				  url: $.ctx + "/api/shopCart/findShopCart",
				  postData:{
					  sysId : dataModel.configId
				  },
				  onSuccess: function(returnObj){
					dataModel.ruleList = [];
					dataModel.ruleListCount = 0;
					if(returnObj.data){
						//1.更新已选择标签数据
  					//2.更新计算中心的页面样式
						dataModel.ruleList = returnObj.data.shopCartRules;
						dataModel.ruleListCount = returnObj.data.showCartRulesCount;
					}
					calculateDragSort.sortLabels();
				 }
			});
    };
    /***
	 * 弹窗设置标签规则，避免双向绑定引用相同的对象
	 */
	model.getDialogRuleValue = function(ruleIndex){
		var rule = dataModel.ruleList[ruleIndex];
		var obj = JSON.parse(JSON.stringify(rule));
		return obj ;
		
	};
    /***
	 * 弹窗设置标签规则
	 */
	model.setDialogRuleValue = function(index,rule){
		dataModel.ruleList[index] = rule;
		model.submitRules();
		
	};
    /**
	 * 每执行一步操作对session缓存进行重置
	 */
	model.submitRules = function(sort) {
		var para = JSON.stringify( dataModel.ruleList );
		$.commAjax({
			  url: $.ctx + "/api/shopCart/updateShopSession",
			  postData:{labelRuleStr : para},
			  onSuccess: function(returnObj){
				  	var status = returnObj.status;
					if (status == '200'){
						model.refreshShopCart();
					}else{
						$.alert("添加标签失败");
					}
			  }
		});
	};
	
    /**
     * @description 标签弹出层处理
     * @param  
     * @return  
     * ------------------------------------------------------------------
     */
    model.setLabelAttr = function(t){
    	var index = $(t).parent().attr("index");
    	var rule = dataModel.ruleList[index];
    	var labelType = rule.labelTypeId;
    	var name = rule.customOrLabelName;
		if(labelType == "4"){ // 指标型，存具体的指标值
			//样例弹出页面
			var wd = $.window(name + "-条件设置", $.ctx
					+ '/aibi_lc/pages/labelDialog/numberValueSet.html?index='+index, 600, 500);
	    	wd.reload = function() {
	    		model.refreshShopCart();
	    	}
			
		}else if(labelType == "5" || labelType == "9"){  //枚举标签 条件选择
			//样例弹出页面
			var wd = $.window(name + "-条件设置", $.ctx + '/aibi_lc/pages/labelDialog/enumItemSet.html?index='+index, 800, 500);
	    	wd.reload = function() {
	    		model.refreshShopCart();
	    	}
		}else if(labelType == "11"){  //条件选择
			
        }else if(labelType == "6"){//日期类型标签
        	var wd = $.window(name + "-条件设置", $.ctx + '/aibi_lc/pages/labelDialog/dateValueSet.html?index='+index, 550, 420);
	    	wd.reload = function() {
	    		model.refreshShopCart();
	    	}	
		}else if(labelType == "7"){//文本类型
			var wd = $.window(name + "-条件设置", $.ctx + '/aibi_lc/pages/labelDialog/textValueSet.html?index='+index, 600, 500);
	    	wd.reload = function() {
	    		model.refreshShopCart();
	    	}
			
		} else {
			//showAlert("计算元素类型错误！", "failed");
		}
	};
	
	/**
	 * 集市开关的展开合并
	 */
	model.switchConnectorShow = function(t,e){
		//如果已经打开了就关闭，否则打开
		if($(t).parent().parent().hasClass("open")){
			$(t).parent().parent().removeClass('open');
		}else{
			$(t).parent().parent().addClass('open');
		}
	};
	/**
	 * 切换连接符号
	 */
	model.switchConnector = function(t,calcuElement){
		var index = $(t).parent().parent().parent().attr("index");
		$(t).parent().parent().removeClass('open');
		dataModel.ruleList[index].calcuElement = calcuElement;
		model.submitRules();
	};
	/**
	 * 更改标志型标签值
	 */
	model.againstLabel = function(t){
		var index = $(t).parent().parent().attr("index");
		var rule = dataModel.ruleList[index];
		if(rule){
			if(rule.labelFlag == 1){
				dataModel.ruleList[index].labelFlag = 0 ;
			}else{
				dataModel.ruleList[index].labelFlag = 1 ;
			}
			
    		model.submitRules();
		}
	};
	/**
	 * 展示标签信息
	 */
	model.showLabelInfo = function(elem,event){

		var e=event||window.event;
		e.stopPropagation?e.stopPropagation():e.cancelBubble=true;
		var X = $(elem).parents(".ui-conditionCT").position().top;
		var Y = $(elem).parents(".ui-conditionCT").position().left;
		$(".ui-conditionBox").css({"left":Y+3,"top":X+90});
		var index = $(elem).parent().parent().attr("index");
		var rule = dataModel.ruleList[index];
		$(document).click(function(){$(".ui-conditionBox").hide()});
		if($(".ui-conditionBox").attr("index") == '' || $(".ui-conditionBox").attr("index") !=index){
			if(rule){
				$.commAjax({
					url : $.ctx + "/api/label/labelInfo/get",
					postData:{
						labelId : rule.calcuElement
	  				},
					onSuccess:function(returnObj){
						var status = returnObj.status;
						if (status == '200'){
							$(".ui-conditionBox").attr("index",index);	
							$(".ui-conditionBox").show();
							dataModel.labelInfoViewObj = returnObj.data;
						}else{
							$.alert(returnObj.msg);
						}
					},
				});
			}
		}else{
			$(".ui-conditionBox").hide();
		}
	}
	/**
	 * 删除规则
	 */
	model.deleteRule = function(t){
		var index = Number($(t).parent().parent().attr("index"));
		index = model.deleteCurlyBraces(index);
		//删除关联的连接符
		index = model.deleteConnectFlags(index);
		dataModel.ruleList.splice(index,1);
		model.submitRules();
	};
	
	/**
	 * 删除连接符
	 */
	model.deleteConnectFlags = function(index){
		var pre = dataModel.ruleList[index-1];
		if(pre && pre.elementType == 1){
			dataModel.ruleList.splice(index-1,1);
			index = index -1;
		}else{
			var nex = dataModel.ruleList[index+1];
			if(nex && nex.elementType == 1){
				dataModel.ruleList.splice(index+1,1);
			}
		}
		return index;
		
	}
	/**
	 * 先删除当前元素前面的连接符，如果前面没有元素。则删除后面的连接符()
	 * start 为(的位置
	 */
	model.deleteConnectFlag = function(start){
		var rule = dataModel.ruleList[start-1];
		if(rule && rule.elementType ==1){
			dataModel.ruleList.splice(start-1,1);
			start = start-1;
		}else{
			var nextRule = dataModel.ruleList[start+2]
			if(nextRule && nextRule.elementType ==1){
				dataModel.ruleList.splice(start+2,1);
			}
		}
		return start;
	}
	/**
	 * 删除匹配的括号【与条件直接关联的括号】,待测试
	 */
	model.deleteCurlyBraces = function(index){
		var pre = dataModel.ruleList[index-1];
		var nex = dataModel.ruleList[index+1];
		if(pre && nex && pre.elementType == 3 && nex.elementType == 3){
			dataModel.ruleList.splice(index+1,1);
			dataModel.ruleList.splice(index-1,1);
			index = index-1;
			model.deleteCurlyBraces(index);//递归删除
		}
		return index;
	}
	/**
	 * 删除括号，删除按钮触发事件处理函数
	 */
	model.deletePar = function(t,evt){
		var e=evt||window.event;
		e.stopPropagation?e.stopPropagation():e.cancelBubble=true;
		$(".onDelPar").removeClass("onDelPar");
		$(t).addClass("onDelPar");
		var posX=$(t).offset().left+9;
		var posY=$(t).offset().top+7;
		if($("body > #delPar").length==0){
			var _ul=$('<ul class="ui-bracket-list" id="delPar"><li><a href="javascript:void(0)">删除括号</a></li><li><a href="javascript:void(0)">删除括号与内容</a></li></ul>');
			_ul.appendTo("body");
			$(document).click(function(){$("#delPar").hide()});
			$("#delPar li").eq(0).bind("click",model.delThisPars);
			$("#delPar li").eq(1).bind("click",model.delThisParsAndCT);
		}
		var tar=$("#delPar"),winW=$(window).width(),tarW=tar.width();
		if(posX+tarW>winW) posX=winW-tarW-6;
		tar.css({"left":posX+"px","top":posY+"px"}).slideDown("fast");
	}
	/**
	 * 删除括号的处理函数
	 */
	model.delThisPars = function(){
		var rightBrackets = $(".onDelPar").parent().parent();
		var creat= rightBrackets.attr("creat");
		var leftBrackets = $(".onDelPar").parent().parent().siblings("[creat='"+creat+"']");
		dataModel.ruleList.splice(rightBrackets.attr('index'),1);
		if(leftBrackets){
			dataModel.ruleList.splice(leftBrackets.attr('index'),1);
		}
		model.submitRules();
		$("#delPar").hide();
	}
	/**
	 * 删除括号及整个块的处理函数
	 * 1)删除括号包含的内容
	 * 2)删除连接符
	 * 3)删除多余的括号(())
	 */
	model.delThisParsAndCT = function (){
		var delObj = $("body .onDelPar").parent().parent();
		var delIndex = Number(delObj.attr('index'));
		var creat = delObj.attr("creat");
		//遍历删除括号内部所有的块
		var start=Number($(".onDelPar").parent().parent().siblings("[creat='"+creat+"']").attr('index'));
		dataModel.ruleList.splice(start+1,delIndex-start-1);//删除括号内的元素包含括号
		//删除前面或后面的连接符
		start = model.deleteConnectFlag(start);
		//处理剩余空括号
		model.delEmptyParsAndCF(start);
		//调用删除括号的方法
		$("#delPar").hide();
		model.submitRules();
	}
	/**
	 * 循环删除空括号和前面的连接符

	 */
	model.delEmptyParsAndCF = function(start){
		var pre = dataModel.ruleList[start];
		var nex = dataModel.ruleList[start+1];
		if(pre && nex && pre.elementType == 3 && nex.elementType == 3){
			dataModel.ruleList.splice(start+1,1);
			dataModel.ruleList.splice(start,1);
			start = start-1;
			model.delEmptyParsAndCF(start);//递归删除
		}
		return start;
	}
	/**
	 * 清空规则
	 */
	model.clearShopRules = function(){
		$.confirm('确定要清空？', function() {
			$('.ui-calc-h3>span').remove
			$.commAjax({
				url : $.ctx + "/api/shopCart/delShopSession",
				onSuccess:function(returnObj){
					var status = returnObj.status;
					if (status == '200'){
						model.refreshShopCart();
					}else{
						$.alert(returnObj.msg);
					}
				},
			});
		});
	};
	/**
	 * 枚举或者文本类精确匹配时需要验证个数不能大于100个（oracle数据库超过1000个报错，而且过多影响数据探索，没有实际意义）
	 */
	model.validateEnumCount = function(){
		var resultFlag = true;
		for(var i=0 ; i<dataModel.ruleList.length;i++){
			if(dataModel.ruleList[i].elementType == 2 && dataModel.ruleList[i].labelTypeId == 5){
				var str = dataModel.ruleList[i].attrVal;
				if(str!=null&&str!=""&&str.split(",").length>dataModel._EnumCount){
					resultFlag = false;
				}
				if(!resultFlag){
					return resultFlag;
				}
			}
		}
		if(!resultFlag){
			return false;
		}
	};
	/**
	 * 校验
	 */
	model.validateForm  = function(){
		//判断是否存在规则
		if($('#sortable').children().length <1){
			$.alert("没有规则无法计算！");
			return false;
		}
		if(model.validateEnumCount() == false){
			$.alert("配置的条件中值超过"+dataModel._EnumCount+"个，无法计算，可以保存客户群后查看客户数！");
			return false;
		}
		if(dataModel.ruleListCount>dataModel._maxLabelNum){
			$.alert("计算元素不能超过" + dataModel._maxLabelNum + "个！","failed");
			return false;
		}

		if(!model.isSetProperties()){
			$.alert("存在没有设置属性的标签");
			return false;
		}
		return true;
	}
	/**
	 * 存在没有设置属性的标签
	 */
	model.isSetProperties = function(){
		var flag=true;
		if($(".ui-nothing-icon").length>0){
			flag = false;
		}
		return flag;
	}
	/**
	 * 遍历购物车标签是否存在无效标签,购物车中不是所有规则都是标签，还有客户群
	 */
	model.labelsValite	= function() {
		var resultFlag = true;
		for(var i=0 ; i<dataModel.ruleList.length;i++){
			if(dataModel.ruleList[i].elementType == 2 && dataModel.ruleList[i].labelTypeId != 13){
				$.commAjax({
					  url: $.ctx + "/api/shopCart/findLabelValidate",
					  postData:{labelId : dataModel.ruleList[i].calcuElement},
					  async	: false,//同步
					  onSuccess: function(returnObj){
						  	var status = returnObj.status;
							if (status != '200'){
								resultFlag = false;
								$.alert("购物车中存在无效标签，请删除！");
							}
					  }
				});
			}
		}
		return resultFlag;
	};
	/**
	 * sql验证
	 * dataPrivaliege : 逗号分隔
	 */
	model.validateSql = function(labelMonth,labelDay,dataPrivaliege){
		var flag = false;				
		//验证sql
		var actionUrl = $.ctx + '/api/shopCart/validateSql';
		$.commAjax({
			  url: actionUrl,
			  async	: false,//同步
			  postData:{
				  "monthLabelDate":labelMonth,
				  "dayLabelDate":labelDay,
				  "dataPrivaliege" : dataPrivaliege
			  },
			  onSuccess: function(returnObj){
				  	var status = returnObj.status;
				  	var msg = returnObj.msg;
					if (status == '200'){
						//删除失效的标签
						flag = true;
					}else{
						$.alert(msg);
					}
			  }
		});
		return flag;
	}
	/**
	 * 探索标签
	 */
	model.explore = function(){
		var e=arguments.callee.caller.arguments[0]||event;
		    if (e && e.stopPropagation) {
		       e.stopPropagation();
		    } else if (window.event) {
		       window.event.cancelBubble = true;
		}
		if(model.validateForm()){
			var existLabel = true;
			//只有清单时,不弹出对话框,直接探索
			$.commAjax({
				  url: $.ctx + "/api/shopCart/findEaliestDataDate",
				  async	: false,//同步
				  onSuccess: function(returnObj){
					  	var status = returnObj.status;
					  	var result = returnObj.data;
						if (status == '200'){
							dataModel.existMonthLabel = result.existMonthLabel;
							dataModel.existDayLabel = result.existDayLabel;
							dataModel.labelMonth = result.monthDate;
							dataModel.labelDay = result.dayDate;
							if(!dataModel.existMonthLabel && !dataModel.existDayLabel){//不含标签时直接探索
								//验证sql
								if(model.validateSql(dataModel.labelMonth.replace(/-/g,""),dataModel.labelDay.replace(/-/g,""))){
									model.submitForExplore(dataModel.labelMonth.replace(/-/g,""),dataModel.labelDay.replace(/-/g,""));
								}
								existLabel = false;
							}
						}
				  }
			});
			if(!existLabel){
				return false;
			}
			//设置标签数据日期
			var wd = $.window("标签数据日期", $.ctx + '/aibi_lc/pages/labelDialog/shopCartDataDateSet.html', 600, 500);
	    	wd.reload = function() {
	    		model.refreshShopCart();
	    	}
		}else{
			dataModel.exploreCustomNum = "--";
		}
		
	};
	/**
	 * 提交探索
	 */
	model.submitForExplore = function(labelMonth,labelDay,dataPrivaliege){
		var actionUrl = "";
		var param = {
			"dataDate":labelMonth,
			"monthLabelDate":labelMonth,
			"dayLabelDate":labelDay,
			"dataPrivaliege":dataPrivaliege
		};
		$.commAjax({
			  url: $.ctx + "/api/shopCart/explore",
			  postData:param,
			  onSuccess: function(returnObj){
				dataModel.exploreCustomNum = 0;
				if(returnObj.status == '200'){
					dataModel.exploreCustomNum = returnObj.data;
				}else{
					$.alert("探索失败");
				}
			 }
		});
	};
	/**
	 * 进入客户群编辑界面
	 */
	model.gotoSaveCustomer = function(e){
		var e=arguments.callee.caller.arguments[0]||event;
		    if (e && e.stopPropagation) {
		       e.stopPropagation();
		    } else if (window.event) {
		       window.event.cancelBubble = true;
		}
		
		//修改客户群不验证数据是否存在
		if(model.validateForm()){
			var existLabel = true;
			//只有清单时,不弹出对话框,直接探索
			$.commAjax({
				  url: $.ctx + "/api/shopCart/findEaliestDataDate",
				  async	: true,//同步
				  onSuccess: function(returnObj){
					  	var status = returnObj.status;
					  	var result = returnObj.data;
						if (status == '200'){
							dataModel.labelMonth = result.monthDate;
							dataModel.labelDay = result.dayDate;
						}else{
							existLabel = false;
						}
						if(existLabel){
							if(model.validateSql(dataModel.labelMonth.replace(/-/g,""),dataModel.labelDay.replace(/-/g,""))){
								window.location='../custom/custom_edit.html?from=labelmarket';
							}
						}
						
				  }
			});
			
		}
		
	};
	return model ;
})(window.calculateCenter || {});