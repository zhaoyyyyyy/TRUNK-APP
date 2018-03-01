/*!
 *标签集市
 * Date: 2017-12-04 
 */
/**初始化*/
var dataModel = {
		zqlxList:[],
		xzqhList:[],
		gxzqList:[],
		dataStatusList:[],
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
    		toggleDropdown : function(item){
				if(typeof item.isOpen=='undefined'){
					this.$set(item,"isOpen",true)
				}else{
					item.isOpen=!item.isOpen;
				}
			}
    	},
    	mounted: function () {
		    this.$nextTick(function () {
		       dataModel.offset = $("#end").offset();//加入购物车参数
		    })
		}
    });

	
	//初始化加载标签体系
	labelMarket.loadLabelCategoryList();
	//初始化计算中心事件
	calculateDragSort.dragParenthesis();
	//初始化地市
	labelMarket.loadOrg();
	
	labelMarket.loadUpdateCycle();
	
	labelMarket.loadDataStatus();
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
			$("#configId").val($.getCurrentConfigId());
			var obj = $("#formSearch").formToJson();
			obj.pageSize = 20;
			obj.groupType = 1;
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
						data.rows[i].customComment = data.rows[i].busiLegend;
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
		
		model.loadDataStatus = function(){
	    	var dataStatusList = [];
	    	var dicDs = $.getDicData("QTZTZD");
	    	for(var i=0; i<dicDs.length; i++){
	    		dataStatusList.push(dicDs[i]);
	    	}
	    	dataModel.dataStatusList = dataStatusList;
	    };
		
		//更改数据状态
		model.dataStatusSearch = function(obj){
			if(obj.id == "allDs"){
				$("#dataStatus"+dataModel.dataStatus).removeClass("all-active");
				dataModel.dataStatus = "";
				$("#dataStatusId").val("");
				$("#allDs").addClass("all-active");
			}else{
				$("#dataStatus"+dataModel.dataStatus).removeClass("all-active");
				dataModel.dataStatus = obj.name;
				$("#dataStatusId").val(obj.name);
				$("#allDs").removeClass("all-active");
				$("#dataStatus"+obj.name).addClass("all-active");
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
		
		//查找我创建的客户群
		model.myCreateCustom = function(obj){
			if(obj.checked){
				$.commAjax({
					url: $.ctx + "/api/user/get",
					onSuccess: function(data){
						$("#createUserId").val(data.data.userName);
						model.loadLabelInfoList();
					}
				});
			}else{
				$("#createUserId").val("");
				model.loadLabelInfoList();
			}
		}
        
        return model;
   })(window.labelMarket || {});

