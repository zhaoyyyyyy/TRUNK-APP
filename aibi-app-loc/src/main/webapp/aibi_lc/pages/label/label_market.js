/*!
 *标签集市
 * Date: 2017-12-04 
 */
/**初始化*/
var dataModel = {
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
		labelDay : '',//规则中日日期
		updateCycleList : [] ,//更新周期
		labelTypeIdList : [] ,//创建类型
		labelInfoViewObj : {},
		createdLeftPar : new Array(), //左括号id
		categoryPath : "",  //总路径
		categoryPath1 : "",  //一级目录
		categoryPath2 : "",  //二级目录
		categoryPath3 : "",  //三级目录
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
    	filters: {
			 formatDate : function (time) {
				var d = new Date(time);
			    var year = d.getFullYear();
			    var month = d.getMonth() + 1;
			    month = month <10 ? '0' + month : '' + month;
			    var day = d.getDate() <10 ? '0' + d.getDate() : '' + d.getDate();
				return year+ '-' + month + '-' + day;
			 }
		},
    	methods : {
    		/**
    		 * 选择标签
    		 */
    		select : function(index){
    			labelMarket.addToShoppingCar(index);
    		},
    		toggle:function(categoryId,index,categoryName){
    			$("#categoryId").val(categoryId);
    			labelMarket.loadLabelInfoList();
    			dataModel.categoryPath1=categoryName;
    			dataModel.categoryPath = dataModel.categoryPath1;
    			ulListId=index;
    			$.commAjax({
				    url: $.ctx + "/api/label/categoryInfo/queryList",
				    isShowMask : true,
					maskMassage : '加载分类...',
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
	calculateDragSort.dragParenthesis();
	
	labelMarket.loadUpdateCycle();
	//加载标签集市
	labelMarket.loadLabelInfoList();
	//加载购物车
	labelMarket.refreshShopCart();
	
	//标签创建周期
	dataModel.updateCycleList = $.getDicData("GXZQZD");
	//标签创建类型
	dataModel.labelTypeIdList = $.getDicData("BQLXZD");
	//搜索框回车
	$('#labelNameText').keyup(function(event){
    	if(event.keyCode == 13){
    		$("#btn_search").click();
    	}
    })
	
	$(".ui-label-system > li").each(function(e){
		$(this).delegate(".labelItems","click",function(){
			
			$(this).addClass('active').siblings(".labelItems").removeClass("active");
			var sysId=$(this).attr("sysid");
			$(this).attr("data-id",sysId+ulListId);
			$(".ui-label-sec").attr("data-id",sysId+ulListId);
			if($(this).attr("data-id")==$(".ui-label-sec").attr("data-id")){
				$(".ui-label-sec").show();
				$(".ui-label-sec").find("a").removeClass("active");
			}
			
			$(this).siblings("a").removeClass("all-active");
//			if($(this).hasClass('active')){
//				$(this).removeClass('active');
//				$(".ui-label-sec").hide();
//				$(".ui-label-sec").find("a").removeClass("active");
//			}else{
//				$(this).addClass('active').siblings(".labelItems").removeClass("active");
//				var sysId=$(this).attr("sysid");
//				$(this).attr("data-id",sysId+ulListId);
//				$(".ui-label-sec").attr("data-id",sysId+ulListId);
//				if($(this).attr("data-id")==$(".ui-label-sec").attr("data-id")){
//					$(".ui-label-sec").show();
//					$(".ui-label-sec").find("a").removeClass("active");
//				}
//			}
		})
	})
	

	
	
	$( '[data-dismiss*="Datepicker"]' ).datepicker({
		dateFormat: "yy-mm-dd",
		onClose: function(dateText, inst) {// 关闭事件  
			if($(this).hasClass("publishStar")){
				$("#publishTimeStart").val($(this).val());
				labelMarket.loadLabelInfoList();
			}else if($(this).hasClass("publishEnd")){
				$("#publishTimeEnd").val($( this).val());
				labelMarket.loadLabelInfoList();
			}
		}
	})
	
	//计算中心弹出/收起（下面）
	$(".ui-shop-cart").click(function(){
		if($(".ui-calculate-center").hasClass("heightAuto")){
			$(".ui-calculate-center").removeClass("heightAuto");
		}else{
			labelMarket.refreshShopCart();
			$(".ui-calculate-center").addClass("heightAuto");
		}
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
  
	  $(".ui-calc-content").delegate(".ui-bracket.right","mouseover",function(){
	  	 $(this).find("span").after($(".ui-helper-box"));
	  	 $(this).find(".ui-helper-box").show();
	  })
	   $(".ui-calc-content").delegate(".ui-bracket.right","mouseleave",function(){
	  	 $(this).find(".ui-helper-box").hide();
	  	 $(".ui-helper-box").hide();
	  	 $(".ui-helper-box").find("ul").hide();
	  })
	  $(".ui-helper-box").find("i").click(function(){
	  	$(this).siblings("ul").stop().toggle();
	  })
	
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
			$("#configId").val($.getCurrentConfigId());
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
					}
					dataModel.labelInfoList = data.rows;
					$("#jsonmap1_pager").createPage({
						pageCount:data.totalPageCount,  
  	   					backFn : function(pageIndex){
  	   						obj.pageStart = pageIndex;
  	   						$.commAjax({
  	   							url: $.ctx + "/api/label/labelInfo/queryPage",
								postData:obj,
								isShowMask : true,
								maskMassage : '正在查找...',
								onSuccess: function(res){
									dataModel.page.currentPageNo=pageIndex;
									dataModel.labelInfoList = res.rows;
								}
  	   						})
  	   					}
   					});
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
			dataModel.categoryPath="";
		}
		model.selectByCategoryId = function(obj){
			$("#categoryId").val(obj.id);
			$.commAjax({
				url: $.ctx + "/api/label/categoryInfo/get",
				async:false,
				postData:{"categoryId":obj.id},
				onSuccess: function(data){
					$.commAjax({
						url: $.ctx + "/api/label/categoryInfo/get",
						postData:{"categoryId":data.data.parentId},
						onSuccess: function(data1){
							if(data1.data.categoryName!=dataModel.categoryPath1){
								dataModel.categoryPath=dataModel.categoryPath1+">"+data1.data.categoryName+">"+data.data.categoryName;
							}else{
								dataModel.categoryPath=dataModel.categoryPath1+">"+data.data.categoryName;
							}
						}
					});
				}
			});
			//二级三级选中状态切换
			$(obj).addClass("active").siblings("a").removeClass("active");
			$(obj).parent("div").siblings("label").find('a').removeClass("active");
			$(obj).parent("label").siblings("div").find('a').removeClass("active");
			$(obj).parents("li").siblings("li").find("label a").removeClass("active");
			$(obj).parents("li").siblings("li").find("div a").removeClass("active");
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
					$("#"+obj.id+"Asc").removeClass("active");
					$("#"+obj.id+"Desc").addClass("active");
				}else if(dataModel.sortOrder == "DESC"){
					dataModel.sortOrder = "ASC";
					$("#sortOrder").val("ASC");
					$("#"+obj.id+"Desc").removeClass("active");
					$("#"+obj.id+"Asc").addClass("active");
				}
			}else{
				if(obj.id == "customNum"){
					$("#sortPublishTime").removeClass("active");
					$("#sortPublishTimeAsc").removeClass("active");
					$("#sortPublishTimeDesc").removeClass("active");
					$("#customNum").addClass("active");
					$("#customNumAsc").addClass("active");
					$("#customNumDesc").removeClass("active");
				}else{
					$("#customNum").removeClass("active");
					$("#customNumAsc").removeClass("active");
					$("#customNumDesc").removeClass("active");
					$("#sortPublishTime").addClass("active");
					$("#sortPublishTimeAsc").addClass("active");
					$("#sortPublishTimeDesc").removeClass("active");
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
  						calculateDragSort.sortLabels();
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
		 * 展示标签信息
		 */
		model.showLabelInfo = function(elem,event){
			var index = $(elem).parent().parent().attr("index");
			var rule = dataModel.ruleList[index];
			if($(elem).parents(".ui-conditionCT").find(".ui-conditionBox").length==0){
				$(elem).parents(".ui-conditionCT").append($(".ui-conditionBox"));
				$(elem).parents(".ui-conditionCT").find(".ui-conditionBox").show();
				if(rule){
					$.commAjax({
						url : $.ctx + "/api/label/labelInfo/get",
						postData:{
							labelId : rule.calcuElement
		  				},
						onSuccess:function(returnObj){
							var status = returnObj.status;
							if (status == '200'){
								$(elem).parents(".ui-conditionCT").find(".ui-conditionBox").show();
								dataModel.labelInfoViewObj = returnObj.data;
							}else{
								$.alert(returnObj.msg);
							}
						},
					});
				}
			}else{
				$(elem).parents(".ui-conditionCT").find(".ui-conditionBox").hide();
				$(elem).parents(".ui-conditionCT").find(".ui-conditionBox").remove($(".ui-conditionBox"));
			}
			
			
			
			
//			
//			if($(".ui-conditionBox[index="+index+"]").css("display")=="none"){//如果原来隐藏，则显示
//				$(".ui-conditionBox[index="+index+"]").show();
//				if(rule){
//					$.commAjax({
//						url : $.ctx + "/api/label/labelInfo/get",
//						postData:{
//							labelId : rule.calcuElement
//		  				},
//						onSuccess:function(returnObj){
//							var status = returnObj.status;
//							if (status == '200'){
//								$(elem).parents(".ui-conditionCT").find(".ui-conditionBox").show();
//								dataModel.labelInfoViewObj = returnObj.data;
//							}else{
//								$.alert(returnObj.msg);
//							}
//						},
//					});
//				}
//			}else{
//				$(".ui-conditionBox").hide();
//			}		
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
		//删除连接符号
		function deleteConnectFlag(obj){
			var rule = dataModel.ruleList[obj.attr('index')-2];
			if(rule.elementType ==1){
				//obj.prev().prev().remove();
				dataModel.ruleList.splice(index-2,1);
			}
			if(obj.prevAll(".ui-conditionCT").length == 0){ 
				var index = obj.next(".ui-chaining").attr('index');
				dataModel.ruleList.splice(index,1);
			}
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
			var creat=$("body .onDelPar").parent().parent().attr("creat");
			if($(".onDelPar").parent().parent().prev().attr("creat") && $(".onDelPar").parent().parent().prev().attr("creat") == creat){
				//删除前面或后面的连接符
				model.deleteConnectFlag($(".onDelPar").parent().parent());
			}
			var leftBrackets = $(".onDelPar").parent().parent().siblings("[creat='"+creat+"']");
			var rightBrackets = $(".onDelPar").parent().parent();
			
			//leftBrackets.remove();
			//rightBrackets.remove();
			//$("#delPar").hide();
			dataModel.ruleList.splice(leftBrackets.attr('index'),1);
			dataModel.ruleList.splice(rightBrackets.attr('index'),1);
			model.submitRules();
		}
		/**
		 * 删除括号及整个块的处理函数
		 */
		model.delThisParsAndCT = function (){
			var creat=$("body .onDelPar").parent().parent().attr("creat");
			//遍历删除括号内部所有的块
			var ary=$(".onDelPar").parent().parent().siblings("[creat='"+creat+"']").nextAll();
			for(var i=0;i<ary.length;i++){
				if(ary.eq(i).attr('creat') == creat ) {
					break;
				} else {
					ary.eq(i).remove();
				}
			}
			var nextObj = $(".onDelPar").parent().parent().next();
			//删除前面或后面的连接符
			model.deleteConnectFlag($(".onDelPar").parent().parent());
			//调用删除括号的方法
			model.onlyDelThisPars();

			//处理剩余空括号
			if( nextObj.attr('creat') && nextObj.attr('creat') == nextObj.prev().attr('creat')){
				model.delEmptyParsAndCF(nextObj);
			}
			model.submitRules();
		}
		/**
		 * 循环删除空括号和前面的连接符
		 */
		model.delEmptyParsAndCF = function(obj){
			//删除前面或后面的连接符
			deleteConnectFlag(obj);
			
			var nextObj = obj.next();
			
			//删除自己和与自己配对的括号
			obj.prev().remove();
			obj.remove();
			
			//判断如果后面是括号，则循环执行删除
			if(nextObj.attr('creat') && nextObj.attr('creat') == nextObj.prev().attr('creat')){
				model.delEmptyParsAndCF(nextObj);
			}else{
				return;
			}
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