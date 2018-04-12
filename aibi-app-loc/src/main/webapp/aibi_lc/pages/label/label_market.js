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
		categoryPath : "",  //总路径
		categoryPath1 : "",  //一级目录
		categoryPath2 : "",  //二级目录
		categoryPath3 : "",  //三级目录
		offset:""//购物车动画偏移量
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
    		select : function(index,event,item){
    			//购物车参数
    			var animatePrar={
    				event:event,
    				item:item,
    				offset:dataModel.offset,
    			}
    			calculateCenter.addToShoppingCar(index,animatePrar);
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
    	},
    	mounted: function () {
		    this.$nextTick(function () {
		       dataModel.offset = $("#end").offset();//加入购物车参数
		    })
		},
		components : {
			'calculate-center' :function (resolve, reject) {
	            $.get("./calculate_center.html").then(function (res) {
	                resolve({
	                    template: res,
	                    props: ['ruleList']
	                })
	            });
	        }
		}
    });
	

	//初始化加载标签体系
	labelMarket.loadLabelCategoryList();
	//更新周期
	labelMarket.loadUpdateCycle();
	//加载标签集市
	labelMarket.loadLabelInfoList();
	//加载购物车
	calculateCenter.refreshShopCart();
	
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
			calculateCenter.refreshShopCart();
			$(".ui-calculate-center").addClass("heightAuto");
		}
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
			obj.pageSize = 18;
			obj.groupType = 0;
			obj.dataStatusId = 2;
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
					obj.title="点击标签按从大到小排序";
				}else if(dataModel.sortOrder == "DESC"){
					dataModel.sortOrder = "ASC";
					$("#sortOrder").val("ASC");
					$("#"+obj.id+"Desc").removeClass("active");
					$("#"+obj.id+"Asc").addClass("active");
					obj.title="点击标签按从小到大排序";
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
				obj.title="点击标签按从大到小排序";
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