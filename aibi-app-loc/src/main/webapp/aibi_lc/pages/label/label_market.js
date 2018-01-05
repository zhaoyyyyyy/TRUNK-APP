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
    		
    		toggle:function(categoryId,index){
    			$("#categoryId").val(categoryId);
    			labelMarket.loadLabelInfoList();
    			ulListId=index;
    			$.commAjax({
				    url: $.ctx + "/api/label/categoryInfo/queryList",
				    postData:{
				    	"sysId" :dataModel.configId,
					    parentId :categoryId
				    },
					onSuccess: function(data){
						dataModel.bdList = data.data;
				    }
				});
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
	
	//样例弹出页面
	$("#ztreeDiv").dialog({
	      height: 515,
	      width: 560,
	      modal: true,
	      title:"新建/修改",
	      autoOpen: false,
	      open:function(){
	      	ztreeFunc();
	      	$(".ui-enum-content").css("display","block")
	      },
	      buttons: [
	    	    {
	    	      text: "取消",
	    	      "class":"ui-btn ui-btn-second",
	    	      click: function() {
	    	        $( this ).dialog( "close" );
	    	      }
  	        },{
	    	      text: "确定",
	    	      "class":"ui-btn ui-btn-default",
	    	      click: function() {
	    	        $( this ).dialog( "close" );
	    	      }
	    	}
  	  ]
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
			$.commAjax({
				url: $.ctx + "/api/label/labelInfo/queryPage",
				postData:obj,
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
	    
	    //取消标签体系选择
		model.selectAllCategoryId = function(elem){
			$("#categoryId").val("");
			labelMarket.loadLabelInfoList();
			$(elem).addClass("all-active");
			$(elem).siblings("a.labelItems").removeClass("active");
			$(".ui-label-sec").hide();
		}
		model.selectByCategoryId = function(obj){
			$("#categoryId").val(obj.id);
			if($(obj).hasClass("active")){
				$(obj).removeClass("active");
			}else{
				//二级三级选中状态切换
				$(obj).addClass("active").siblings("a").removeClass("active");
				$(obj).parent("div").siblings("label").find('a').removeClass("active");
				$(obj).parent("label").siblings("div").find('a').removeClass("active");
				$(obj).parents("li").siblings("li").find("label a").removeClass("active");
				$(obj).parents("li").siblings("li").find("div a").removeClass("active");
			}
			labelMarket.loadLabelInfoList();
		}
	    
	    //获取更新周期
	    model.loadUpdateCycle = function(){
	    	var gxzqList = [];
	    	var dicGxzq = $.getDicData("GXZQZD");
	    	for(var i=0; i<dicGxzq.length; i++){
	    		gxzqList.push(dicGxzq[i]);
	    	}
	    	dataModel.gxzqList = gxzqList;
	    };
		
		//更改地市条件
		model.orgSearch = function(obj){
			if(obj.id == "allOrg"){
				$("#org"+dataModel.orgId).removeClass("all-active");
				dataModel.orgId = "";
				$("#orgId").val("");
				$("#allOrg").addClass("all-active");
			}else{
				$("#org"+dataModel.orgId).removeClass("all-active");
				dataModel.orgId = obj.name;
				$("#orgId").val(obj.name);
				$("#allOrg").removeClass("all-active");
				$("#org"+obj.name).addClass("all-active");
			}
			model.loadLabelInfoList();
		}
		
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
			if(typeId == 1) {
				//校验标签有效性
				$.commAjax({
					  url: $.ctx + "/api/shopCart/findLabelValidate",
					  postData:{labelId :  $.trim(id)},
					  onSuccess: function(returnObj){
					  	  //1.如果验证失败，需要返回 2.需要提示
						  flag = true;
					  }
				});
				if(flag){
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
    			/**
                var attrVal = mainObj.attr("attrVal");
                var attrName = mainObj.attr("attrName");
                var calcuElement = mainObj.attr("calcuElement");
                var para="?attrVal="+attrVal+"&attrName="+attrName+"&calcuElement="+calcuElement;
                $(dialogId).dialog("option","title", name+"-条件设置");
                var ifmUrl = '${ctx}/aibi_ci/dialog/genKpiChooseDialog.jsp';
                var form = '<form id="postData_form" action="' + ifmUrl + '" method="post" target="_self">' + 
                    '<input name="attrVal" type="hidden" value="' + attrVal + '"/>' +
                    '<input name="attrName" type="hidden" value="' + attrName + '"/>' +
                    '<input name="calcuElement" type="hidden" value="' + calcuElement + '"/>' +
                    '<input name="labelType" type="hidden" value="' + labelType + '"/>' +
                    '</form>';
                document.getElementById('genKpiChooseFrame').contentWindow.document.write(form);
                document.getElementById('genKpiChooseFrame').contentWindow.document.getElementById('postData_form').submit();
                $(dialogId).dialog("open");
                **/
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
			var index = $(t).parent().attr("index");
			var rule = dataModel.ruleList[index];
			if(rule.labelFlag == 1){
				dataModel.ruleList[index].labelFlag = 0 ;
			}else{
				dataModel.ruleList[index].labelFlag = 1 ;
			}
			
    		model.submitRules();
		};
		/**
		 * 删除规则
		 */
		model.deleteRule = function(t){
			var index = $(t).parent().parent().parent().attr("index");
			var rule = dataModel.ruleList[index];
			dataModel.ruleList.pop(rule);
    		model.submitRules();
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



function ztreeFunc(){
	var zTreeObj,
	setting = {
		view: {
			selectedMulti: false
		}
	},
	zTreeNodes = [
		{"name":"网站导航", open:true, children: [
			{ "name":"google", "url":"http://g.cn", "target":"_blank"},
			{ "name":"baidu", "url":"http://baidu.com", "target":"_blank"},
			{ "name":"sina", "url":"http://www.sina.com.cn", "target":"_blank"}
			]
		}
	];
	zTreeObj = $.fn.zTree.init($("#ztree"), setting, zTreeNodes);
}