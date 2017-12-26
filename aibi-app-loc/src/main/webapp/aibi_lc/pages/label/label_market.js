/*!
 *标签集市
 * Date: 2017-12-04 
 */
/**初始化*/
var dataModel = {
		zqlxList:[],
		xzqhList:[],
		gxzqList:[],
		labelList:[],
		page:[],
		categoryInfoList : [],
		labelInfoList : [],
		ruleListSize : 0,
		ruleList : [],
		configId : '-1'

}

window.loc_onload = function() {
	//初始化参数
	dataModel.configId = $.getCurrentConfigId();
	
	/**
     * ------------------------------------------------------------------
     * 标签集市
     * ------------------------------------------------------------------
     */

    var labelSysApp = new Vue({
    	el : '#labelInfoListApp',
    	data : dataModel
    });
	
	//初始化加载标签体系
	labelMarket.loadLabelCategoryList();
	//初始化计算中心事件
	labelMarket.setClacCenter();
	//初始化地市
	labelMarket.loadOrg();
	
	labelMarket.loadGxzq();
	//加载标签集市
	labelMarket.loadLabelInfoList();
	
	
	//计算中心弹出/收起（下面）
	$(".ui-shop-cart").click(function(){
		$(".ui-calculate-center").addClass("heightAuto");
	});
	$(".J-min").click(function(){
		$(".ui-calculate-center").removeClass("heightAuto");
	});
	
	
	//样例弹出页面
	$(".ui-dialog").dialog({
	      height: 515,
	      width: 560,
	      modal: true,
	      title:"新建/修改",
	      open:function(){
	      	ztreeFunc();
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
         * @description 获取标签体系
         * @param  option
         * @return  
         * ------------------------------------------------------------------
         */
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
										//dataModel.zqlxList.push(od);
									}else if(od.orgType == "3"){
										//dataModel.xzqhList.push(od);
									}
								}
							}
        				}
        			}
        		});
        };
        
        
        model.loadGxzq = function(){
        	var gxzqList = [];
        	var dicGxzq = $.getDicData("GXZQZD");
        	for(var i=0; i<dicGxzq.length; i++){
        		gxzqList.push(dicGxzq[i]);
        	}
        	dataModel.gxzqList = gxzqList;
        };
        /**
         * @description 获取标签数据
         * @param  option
         * @return  
         * ------------------------------------------------------------------
         */
		model.loadLabelInfoList = function(){
			$.commAjax({
				url: $.ctx + "/api/label/labelInfo/queryPage",
				postData:{
					configId : ""
				},
				onSuccess: function(data){
					dataModel.page.currentPageNo=data.currentPageNo;
					dataModel.page.totalCount=data.totalCount;
					dataModel.page.totalPageCount=data.totalPageCount;
					for(var i=0 ; i<data.rows.length; i++){
						if(data.rows[i].labelExtInfo!=undefined&&data.rows[i].labelExtInfo!=null){
							data.rows[i].customNum = data.rows[i].labelExtInfo.customNum;
						}else{
							data.rows[i].customNum = "无";
						}
					}
					dataModel.labelInfoList = data.rows;
				}
			});
		};
        
		/**
         * @description 标签添加缓存，购物车动画效果===true ,组装rule
         * @param  
         * @return  
         * ------------------------------------------------------------------
         */
		model.addToShoppingCar = function(index){
        	var labelInfo = dataModel.labelInfoList[index];
        	modeladdShopCart(labelInfo.labelId,1,'',0);
        	//shopChartMain.addToShoppingCar(ev,ths,1);
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
				//待开发
				/*
				$.commAjax({
					  url: $.ctx + "/api/label/labelInfo/findLabelValidate",
					  postData:{
						  labelId : {'labelId' : $.trim(id)}
					  },
					  onSuccess: function(returnObj){
					  	  //1.如果验证失败，需要返回 2.需要提示
						  
						  flag = true;
					  }
				});*/
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
					  	var success = returnObj.success;
					  	
						if (success){
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
  					  sysId : configId
  				  },
  				  onSuccess: function(returnObj){
  					dataModel.ruleList = [];
  					dataModel.ruleListSize = 0;
  					if(returnObj.data){
  						//1.更新已选择标签数据
	  					//2.更新计算中心的页面样式
  						dataModel.ruleList = returnObj.data;
  						dataModel.ruleListSize = _this.ruleLis.size;
  					}
  					   
  				  }
  			});
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