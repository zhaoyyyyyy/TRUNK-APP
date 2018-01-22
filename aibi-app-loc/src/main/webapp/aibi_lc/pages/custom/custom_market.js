/*!
 *标签集市
 * Date: 2017-12-04 
 */
/**初始化*/
var dataModel = {
		zqlxList:[],
		xzqhList:[],
		gxzqList:[],
		page:[],
		categoryInfoList : [],
		labelInfoList : [],//标签
		ruleListCount : 0,
		ruleList : [],//规则
		currentAddLabel : {},
		configId : '-1',
		categoryId:"",
		orgId:"",
		publishTime:"",
		updateCycle:"",
		sortOrder:"ASC",
		sortCol:"customNum",
		isShow:false,
		bdList:[],
		exploreCustomNum:'--', //探索结果
		_EnumCount : 100,//枚举值的最大个数
		_maxLabelNum : 100 ,//选择的标签最大个数
		existMonthLabel : true,//规则中是否包含月标签
		existDayLabel : true,//规则中是否包含月标签
		labelMonth : '',//规则中月日期
		labelDay : ''//规则中日日期
}
window.loc_onload = function() {
	//初始化参数
	dataModel.configId = $.getCurrentConfigId();
	var ulListId;
	
	/**
     * ------------------------------------------------------------------
     * 标签集市
     * ------------------------------------------------------------------
     */

    var labelSysApp = new Vue({
    	el : '#labelInfoListApp',
    	data : dataModel,
    	methods : {
    		/**
    		 * 选择标签
    		 */
    		select : function(index){
    			labelMarket.addToShoppingCar(index);
    		},
    		toggleDropdown : function(item){
				if(typeof item.isOpen=='undefined'){
					this.$set(item,"isOpen",true)
				}else{
					item.isOpen=!item.isOpen;
				}
			}
    	}
    });

	
	//初始化加载标签体系
	labelMarket.loadLabelCategoryList();
	//初始化计算中心事件
	labelMarket.setClacCenter();
	//初始化地市
	labelMarket.loadOrg();
	
	labelMarket.loadUpdateCycle();
	//加载标签集市
	labelMarket.loadLabelInfoList();
	//加载购物车
	labelMarket.refreshShopCart();
	//搜索框回车
	$('#labelNameText').keyup(function(event){
    	if(event.keyCode == 13){
    		$("#btn_search").click();
    	}
    })
	
	$(".ui-label-system > li").each(function(e){
		$(this).delegate(".labelItems","click",function(){
			$(this).siblings("a").removeClass("all-active");
			if($(this).hasClass('active')){
				$(this).removeClass('active');
				$(".ui-label-sec").hide();
				$(".ui-label-sec").find("a").removeClass("active");
			}else{
				$(this).addClass('active').siblings(".labelItems").removeClass("active");
				var sysId=$(this).attr("sysid");
				$(this).attr("data-id",sysId+ulListId);
				$(".ui-label-sec").attr("data-id",sysId+ulListId);
				if($(this).attr("data-id")==$(".ui-label-sec").attr("data-id")){
					$(".ui-label-sec").show();
					$(".ui-label-sec").find("a").removeClass("active");
				}
			}
		})
	})

	
	//计算中心弹出/收起（下面）
	$(".ui-shop-cart").click(function(){
		labelMarket.refreshShopCart();
		$(".ui-calculate-center").addClass("heightAuto");
	});
	$(".J-min").click(function(){
		$(".ui-calculate-center").removeClass("heightAuto");
	});
	
}






/**
 * ------------------------------------------------------------------
 * 标签集市
 * ------------------------------------------------------------------
 */
var labelMarket = (function (model){
        //开发版本号
        model.version = "1.0.0";
        model.author  = "wangsen3";
        model.email   = "wangsen3@asiainfo.com";

        
        /**
         * @description 获取标签体系
         * @param  option
         * @return  
         * ------------------------------------------------------------------
         */
        model.customer_portrait_dialog = function(option) {
        	var wd = $.window("群体用户画像", $.ctx + '/aibi_lc/pages/custom/custom_protrait.html', 1000, 700);
        };
        
        /**
         * @description 获取标签体系
         * @param  option
         * @return  
         * ------------------------------------------------------------------
         */
        model.loadLabelCategoryList = function(option) {
        	$.commAjax({
			  url: $.ctx + "/api/label/categoryInfo/queryList",
			  postData:{
				  sysId : dataModel.configId
			  },
			  onSuccess: function(returnObj){
				  dataModel.categoryInfoList = returnObj.data;
			  }
			});
        };
        
        /**
         * @description 获取标签数据
         * @param  option
         * @return  
         * ------------------------------------------------------------------
         */
		model.loadLabelInfoList = function(){
			// $("#configId").val($.getCurrentConfigId());
			var obj = $("#formSearch").formToJson();
			obj.pageSize = 20;
			obj.groupType = 0;
			$.commAjax({
				url: $.ctx + "/api/label/labelInfo/queryPage",
				postData:obj,
				isShowMask : true,
				maskMassage : '正在查找...',
				onSuccess: function(data){
					dataModel.page.currentPageNo=data.currentPageNo;
					dataModel.page.totalCount=data.totalCount;
					dataModel.page.totalPageCount=data.totalPageCount;
					for(var i=0 ; i<data.rows.length; i++){
						if(data.rows[i].labelExtInfo!=undefined&&data.rows[i].labelExtInfo!=null){
							data.rows[i].customNum = data.rows[i].labelExtInfo.customNum;
						}else{
							data.rows[i].customNum = "0";
						}
						data.rows[i].customRuleShow = data.rows[i].labelExtInfo.labelOptRuleShow;
						data.rows[i].customComment = data.rows[i].labelExtInfo.techCaliber;
						console.log(data.rows[i].labelExtInfo.labelId);
						console.log(data.rows[i].labelExtInfo.labelOptRuleShow);
						console.log(data.rows[i].labelExtInfo)
					}
					dataModel.labelInfoList = data.rows;
				}
			});
		};
		
		//获取地市
		model.loadOrg = function(){
    		$.commAjax({
    			url: $.ctx + "/api/user/privaliegeData/query",
    			onSuccess: function(data){
    				if(data.data != null && data.data != undefined){
    					var dataobj = data.data;
						for(var e=0 ; e<4 ; e++){
							if(dataobj[e]==undefined){
								continue;
							}
							for(var l=0 ; l<dataobj[e].length ; l++){
								var od = dataobj[e][l];
								if(od.parentId == "999"){
									dataModel.zqlxList.push(od);
								}else if(od.orgType == "3"){
									dataModel.xzqhList.push(od);
								}
							}
						}
    				}
    			}
    		});
	    };
	    
	    
	    //获取更新周期
	    model.loadUpdateCycle = function(){
	    	var gxzqList = [];
	    	var dicGxzq = $.getDicData("GXZQZD");
	    	for(var i=0; i<dicGxzq.length; i++){
	    		gxzqList.push(dicGxzq[i]);
	    	}
	    	dataModel.gxzqList = gxzqList;
	    };
		
		
		//更改更新周期条件
		model.ucSearch = function(obj){
			if(obj.id == "allUc"){
				$("#updateCycle"+dataModel.updateCycle).removeClass("all-active");
				dataModel.updateCycle = "";
				$("#updateCycle").val("");
				$("#allUc").addClass("all-active");
			}else{
				$("#updateCycle"+dataModel.updateCycle).removeClass("all-active");
				dataModel.updateCycle = obj.name;
				$("#updateCycle").val(obj.name);
				$("#allUc").removeClass("all-active");
				$("#updateCycle"+obj.name).addClass("all-active");
			}
			model.loadLabelInfoList();
		}
		
		//更改排序
		model.changeSortCol = function(obj){
			if(obj.name == dataModel.sortCol){
				$("#sortCol").val(obj.name);
				if(dataModel.sortOrder == "ASC"){
					dataModel.sortOrder = "DESC";
					$("#sortOrder").val("DESC");
				}else if(dataModel.sortOrder == "DESC"){
					dataModel.sortOrder = "ASC";
					$("#sortOrder").val("ASC");
				}
			}else{
				if(obj.id == "customNum"){
					$("#sortPublishTime").removeClass("active");
					$("#customNum").addClass("active");
				}else{
					$("#customNum").removeClass("active");
					$("#sortPublishTime").addClass("active");
				}
				dataModel.sortCol = obj.name;
				$("#sortCol").val(obj.name);
				$("#sortOrder").val("ASC");
			}
			model.loadLabelInfoList();
		}
		
		//更改发布时间
		model.changePublishTime = function(obj){
			var now = new Date();
			if(obj.id == "allDate"){
				$("#publishTimeStart").val("");
				$("#allDate").addClass("all-active");
				$("#oneDay").removeClass("all-active");
				$("#oneMonth").removeClass("all-active");
				$("#threeMonth").removeClass("all-active");
			}
			if(obj.id == "oneDay"){
				$("#publishTimeStart").val($.dateFormat(new Date(now.getTime() - 24*60*60*1000),"yyyy-MM-dd HH:mm:ss"));
				$("#allDate").removeClass("all-active");
				$("#oneDay").addClass("all-active");
				$("#oneMonth").removeClass("all-active");
				$("#threeMonth").removeClass("all-active");
			}
			if(obj.id == "oneMonth"){
				$("#publishTimeStart").val($.dateFormat(new Date(now.getTime() - 30*24*60*60*1000),"yyyy-MM-dd HH:mm:ss"));
				$("#allDate").removeClass("all-active");
				$("#oneDay").removeClass("all-active");
				$("#oneMonth").addClass("all-active");
				$("#threeMonth").removeClass("all-active");
			}
			if(obj.id == "threeMonth"){
				$("#publishTimeStart").val($.dateFormat(new Date(now.getTime() - 90*24*60*60*1000),"yyyy-MM-dd HH:mm:ss"));
				$("#allDate").removeClass("all-active");
				$("#oneDay").removeClass("all-active");
				$("#oneMonth").removeClass("all-active");
				$("#threeMonth").addClass("all-active");
			}
			model.loadLabelInfoList();
		}
		
		model.btn_search = function(){
			$("#labelName").val($("#labelNameText").val());
			model.loadLabelInfoList();
		}
		
		//查找我创建的客户群
		model.myCreateCustom = function(obj){
			if(obj.checked){
				$.commAjax({
					url: $.ctx + "/api/user/get",
					onSuccess: function(data){
						$("#createUserId").val(data.data.userId);
						model.loadLabelInfoList();
					}
				});
			}else{
				$("#createUserId").val("");
				model.loadLabelInfoList();
			}
		}
        
		/**
         * @description 标签添加缓存，购物车动画效果===true ,组装rule
         * @param  
         * @return  
         * ------------------------------------------------------------------
         */
		model.addToShoppingCar = function(index){
        	var labelInfo = dataModel.labelInfoList[index];
        	model.addShopCart(labelInfo.labelId,1,'',0);
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
			if(typeId == 1) {
				//校验标签有效性
				$.commAjax({
					  url: $.ctx + "/api/shopCart/findLabelValidate",
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
			}
			if(typeId == 2) {
				//待开发
				/*
				$.commAjax({
					  url: $.ctx + "/api/label/labelInfo/findCusotmValidate",
					  postData:{
						  labelId : {'labelId' : $.trim(id)}
					  },
					  onSuccess: function(returnObj){
					  	  //1.如果验证失败，需要返回 2.需要提示
						  
						  flag = true;
					  }
				});
				*/
				if(flag){
					$.alert("添加标签失败");
					return;
				}
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
    			
    		}else if(labelType == "5" || labelType == "9"){  //条件选择
    			//样例弹出页面
    			var wd = $.window(name + "-条件设置", $.ctx + '/aibi_lc/pages/labelDialog/enumItemSet.html?index='+index, 800, 500);
		    	wd.reload = function() {
		    		model.refreshShopCart();
		    	}
    		}else if(labelType == "11"){  //条件选择
    			
            }else if(labelType == "6"){//日期类型标签
    			var startTime = "" ;
    			var endTime ="" ;
    			var leftZoneSign ="" ;
    			var rightZoneSign = "" ;
    			var isNeedOffset = mainObj.attr('isNeedOffset');
    			startTime = mainObj.attr("startTime");
    			endTime = mainObj.attr("endTime");
    			leftZoneSign = mainObj.attr("leftZoneSign");
    			rightZoneSign = mainObj.attr("rightZoneSign");
    			queryWay = mainObj.attr("queryWay");
    			exactValue = mainObj.attr("exactValue");
    			
    			var para = "?startTime="+startTime+"&endTime="+endTime+"&leftZoneSign="+leftZoneSign
    						+"&rightZoneSign="+rightZoneSign+"&queryWay="+queryWay
    						+"&exactValue="+exactValue + "&isNeedOffset=" + isNeedOffset;
    			var ifmUrl ="${ctx}/aibi_ci/dialog/dateSettingsDialog.jsp"+para;
    			dialogUtil.create_dialog("dateSettings", {
    				"title" 		: name + "-条件设置",
    				"height"		: "auto",
    				"width" 		: 570,
    				"frameSrc" 		: ifmUrl,
    				"frameHeight"	: 290,
    				"position" 		: ['center','center'] 
    			});			
    		}else if(labelType == "7"){
    			var darkValue = "" ;
    			darkValue = mainObj.attr("darkValue");
    			darkValue=encodeURIComponent(encodeURIComponent(darkValue));

    			var queryWay = mainObj.attr("queryWay"); 
    			var exactValue = mainObj.attr("exactValue"); 
    			exactValue = encodeURIComponent(encodeURIComponent(exactValue));
    			
    			var para = "?darkValue="+darkValue+"&exactValue="+exactValue+"&queryWay="+queryWay;
    			var ifmUrl ="${ctx}/aibi_ci/dialog/darkValueSetDialog.jsp"+para;
    			
    			/* dialogUtil.create_dialog("darkValueSet", {
    				"title" 		: name + "-条件设置",
    				"height"		: "auto",
    				"width" 		: 570,
    				"frameSrc" 		: ifmUrl,
    				"frameHeight"	: 290,
    				"position" 		: ['center','center'] 
    			}); */
    			$(dialogId).dialog("option","title", name+"-条件设置");
    			var ifmUrl ="${ctx}/aibi_ci/dialog/darkValueSetDialog.jsp"+para;
    			$("#darkValueSetFrame").attr("src", ifmUrl).load(function(){ });
    			$(dialogId).dialog("open");
    			
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
		 * 删除匹配的括号【与条件直接关联的括号】,待测试
		 */
		model.deleteCurlyBraces = function(index){
			var pre = dataModel.ruleList[index-1];
			var nex = dataModel.ruleList[index+1];
			if(pre && nex && pre.elementType == 3 && nex.elementType == 3){
				dataModel.ruleList.splice(index+1,1);
				dataModel.ruleList.splice(index-1,1);
				index = index-1;
				deleteCurlyBraces(index);//递归删除
			}
			return index;
		}
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
		 */
		model.validateSql = function(labelMonth,labelDay){
			var flag = false;				
			//验证sql
			var actionUrl = $.ctx + '/api/shopCart/validateSql';
			$.commAjax({
				  url: actionUrl,
				  async	: false,//同步
				  postData:{
					  "monthLabelDate":labelMonth,
					  "dayLabelDate":labelDay
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
								model.existMonthLabel = result.existMonthLabel;
								model.existDayLabel = result.existDayLabel;
								model.labelMonth = result.monthDate;
								model.labelDay = result.dayDate;
								if(!model.existMonthLabel && !model.existDayLabel){//不含标签时直接探索
									//验证sql
									if(model.validateSql(model.labelMonth.replace(/-/g,""),model.labelDay.replace(/-/g,""))){
										model.submitForExplore(model.labelMonth.replace(/-/g,""),model.labelDay.replace(/-/g,""));
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
		model.submitForExplore = function(labelMonth,labelDay){
			var actionUrl = "";
			var param = {
				"dataDate":labelMonth,
				"monthLabelDate":labelMonth,
				"dayLabelDate":labelDay
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
			
		};
        /**
         * @description 计算中心
         * @param  
         * @return  
         * ------------------------------------------------------------------
         */
        model.setClacCenter= function(){
        		$( "#sortable > .ui-conditionCT,.ui-calc-h3>span>em" ).draggable({
				helper: function( event ) {
					if($(event.target).hasClass("J-drag-bracket")){
		       	 	   return $(event.target).attr("data-attr") == "left"?$( '<span class="ui-bracket left">(</span>' ):$( '<span class="ui-bracket left">)</span>' );
					}
		       	  return $( '<h4 class="ui-conditionCT-h4 ui-conditionCT-h4-helper">2G数据流量<em></em><i></i></h4>' );
		    	},
		    	cursor: "crosshair",
		    	start:function(event,ui){
		      	  $(".ui-chaining").hide();
		      	  var items = $("#sortable > .ui-conditionCT");
		      	  var calc= '<div class="ui-state-highlight ui-sortablr-helper J-helper"></div>';
		  		  $(items).after(calc);
		  		  if($("#sortable > .ui-bracket.left").prev().length == 0){
			      	  $("#sortable > .ui-bracket.left").before(calc);
		  		  }
		      	  $("#sortable > .ui-bracket").after(calc);
		  		  $(".J-helper").droppable({
			  	  	   hoverClass: "ui-drop-highlight",
			  		   greedy:true,
			  		   drop: function( event, ui ) {
			  			   var onDragTag=ui.draggable;
			  			   if(onDragTag.hasClass("J-drag-bracket")){
			  				 onDragTag = onDragTag.attr("data-attr") == "left"?'<span class="ui-bracket left">(</span>':'<span class="ui-bracket right">)<i></i></span>'
			  				 $(this).after(onDragTag);
			  				 return;
			  			   }
			  		        $(this).after(onDragTag);
			  		        var chains = $("#sortable > .ui-chaining");
			  		     	var CTitems = $("#sortable > .ui-conditionCT");
			  		        for(var i =0,len = chains.length;i<len;i++){
			  		        		$(CTitems[i]).after(chains[i]);
			  		        }
			  	       },
			  	       create:function( event, ui){
			  		        console.log(ui);
			  	       }
			  	   });
		        },
		        stop:function(event,ui){
		      	  $(".ui-chaining").show();
		      	  $(".J-helper").remove();
		        }
			});
        };
        return model;
   })(window.labelMarket || {});

