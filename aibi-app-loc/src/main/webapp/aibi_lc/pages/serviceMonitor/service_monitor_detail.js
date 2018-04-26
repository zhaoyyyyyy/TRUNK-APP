/**
 * ------------------------------------------------------------------
 * 运行监控明细 add by shaosq 20180420
 * ------------------------------------------------------------------
 */
var monitorDetail;//运营监控明细Vue实例
window.loc_onload = function() {	
	var configId = $("#preConfig_list").find("span").attr("configId");
	monitorDetail = new Vue({
		el:"#monitorDetail",
		data:{
			configId:"",  //从运营总览传入的专区id
			serviceMonitorObj:{},  //运营总览
			dataDate:"",//数据日期
			yyjkList:[],  //运营监控枚举
			sjzbList:[],  //数据准备状态
			sjcqList:[],  //数据抽取状态
			bqscList:[],  //标签生成状态
			khqscList:[],  //客户群生成生成状态
			khqtsList:[],  //客户群推送生成状态
			sjsxplList:[], //数据刷新频率
			defaultPl:"",//默认刷新频率
			defaultTimes:5000, // 默认刷新频率
			timerId:"",   // 页面刷新定时器
			tableId:"",  //表格Id
			tableUrl:"",  //表格Url
			postData:"",  //表格参数
			colNames:"",  //表格表头
			colModel:"",  //表格模型
			pager:"" ,  //表格分页id
			zbFlag:true, //数据准备表格默认显示准备状态
			isOpen:false,//日期下拉
			isDown:false//状态下拉
		},
		methods:{
			//加载监控数据
			loadMonitorDetailData:function(){
				var now = new Date();
				this.dataDate = $.dateFormat(new Date(now.getTime() - 3*24*60*60*1000),"yyyy-MM-dd");
				//数据字典赋值
				this.loadDicYyjk();
				this.loadDicSjzb();
				this.loadDicSjcq();
				this.loadDicBqsc();
				this.loadDicKhqsc();
				this.loadDicKhqts();
				this.loadDicSjsxpl();
				//加载统计数据
				this.loadCountData();
			},
			toggle:function(){
				this.isOpen=!this.isOpen;
			},
//			toggleStatus:function(){
//				this.isDown=!this.isDown;
//			},
			//点击周期初始化时间
			initDateByCycle:function(e){
				this.dateCycle = dateCycle;
            	var now = new Date();
            	var dataDay = $.dateFormat(new Date(now.getTime() - 3*24*60*60*1000),"yyyy-MM-dd");
            	var dataMonth = $.dateFormat(new Date(now.getTime() - 3*24*60*60*1000),"yyyy-MM");
            	if("day" === this.dateCycle){
            		$(this).parents("div.dropdown").siblings("div").children("a").html(dataDay);
            	}else{
            		$(this).parents("div.dropdown").siblings("div").children("a").html(dataMonth);
            	}
            },
            //切换日期刷新监控数据
            changeMonitorByDate:function(e){
            	var $el=$(e.target);
				var configId = $el.attr("data-configId");
				var dataDate = e.target.innerText;
            	var that = this;
            	$.commAjax({			
        		    url : $.ctx+'/api/monitor/overview/queryMonitorMainByConfig',
        		    isShowMask : true,
					maskMassage : '加载中...',
        		    dataType : 'json', 
        		    async:true,
        		    postData : {
        					"configId" : configId,
        					"dataDate" : dataDate.replace(/-/g,"")
        			},
        		    onSuccess: function(data){
        		    	if(data){
        		    		that.serviceMonitorObj = data;
        		    	}
        		    },
        	   });
            },
            //加载监控数据
			loadCountData:function(){
				var that = this;
				$.commAjax({
					url : $.ctx + "/api/monitor/overview/queryMonitorMainByConfig",
					postData : {
						configId : this.configId,
						dataDate : this.dataDate.replace(/-/g,""),
					},
					onSuccess : function(returnObj) {
						that.serviceMonitorObj = returnObj.data;
					}
				});
			},
			//表格查询方法
			qryTableByCond:function(e){
				var $el=$(e.target);
				var $tableId = $("#"+$el.attr("data-tableId"));
				var $formSearchId = $("#"+$el.attr("data-formSearchId"));
				$($tableId).setGridParam({
					postData: $formSearchId.formToJson()
				}).trigger("reloadGrid", [{
					page: 1
				}]);
			},
			// 设置页面刷新频率
	        refresh:function(value){
	            this.setTimes(value);
	            this.setTimer();
	        },
	        // 设置页面频率
	        setTimes:function (value) {
	            var val = Number(value);
	            this.defaultTimes = val*1000;
	        },
	        // 设置定时器
	        setTimer: function () {
	            clearTimeout(this.timerId);   // 关闭定时器
	            this.timerId = setTimeout(function(){
	            	 this.loadMonitorDetailData(this.configId);
	     		    //加载数据准备表格
//	     			this.initDataPrepareTable(this.zbFlag);
	     			//加载标签生成表格
//	     			this.initLabelGenerateTable();
//	       			//加载客户群生成表格
//	     			this.initCustomGenerateTable();
	      			//加载客户群推送表格
//	     			this.initCustomPushTable();
	            }.bind(this),this.defaultTimes);
	        },
	        //获取运营监控状态字典
		    loadDicYyjk :function(){
		    	var yyjkList = [];
		    	var dicYyjk = $.getDicData("YYJKZT");
		    	for(var i=0; i<dicYyjk.length; i++){
		    		yyjkList.push(dicYyjk[i]);
		    	}
		    	this.yyjkList = yyjkList;
		    },
	        //获取数据准备状态字典
		    loadDicSjzb :function(){
		    	var sjzbList = [];
		    	var dicSjzb = $.getDicData("SJZBZT");
		    	for(var i=0; i<dicSjzb.length; i++){
		    		sjzbList.push(dicSjzb[i]);
		    	}
		    	this.sjzbList = sjzbList;
		    },
		    //获取数据抽取状态字典
		    loadDicSjcq :function(){
		    	var sjcqList = [];
		    	var dicSjcq = $.getDicData("SJCQZT");
		    	for(var i=0; i<dicSjcq.length; i++){
		    		sjcqList.push(dicSjcq[i]);
		    	}
		    	this.sjcqList = sjcqList;
		    },
		    //获取标签生成状态字典
		    loadDicBqsc :function(){
		    	var bqscList = [];
		    	var dicBqsc = $.getDicData("BQSCZT");
		    	for(var i=0; i<dicBqsc.length; i++){
		    		bqscList.push(dicBqsc[i]);
		    	}
		    	this.bqscList = bqscList;
		    },
		    //获取客户群生成状态字典
		    loadDicKhqsc :function(){
		    	var khqscList = [];
		    	var dicKhqsc = $.getDicData("KHQSCZT");
		    	for(var i=0; i<dicKhqsc.length; i++){
		    		khqscList.push(dicKhqsc[i]);
		    	}
		    	this.khqscList = khqscList;
		    },
		    //获取客户群推送状态字典
		    loadDicKhqts :function(){
		    	var khqtsList = [];
		    	var dicKhqts = $.getDicData("KHQTSZT");
		    	for(var i=0; i<dicKhqts.length; i++){
		    		khqtsList.push(dicKhqts[i]);
		    	}
		    	this.khqtsList = khqtsList;
		    },
		    //获取刷新频率字典
		    loadDicSjsxpl:function(){
		    	var sjsxplList = [];
		    	var dicSjsxpl = $.getDicData("SJSXPL");
		    	for(var i=0; i<dicSjsxpl.length; i++){
		    		sjsxplList.push(dicSjsxpl[i]);
		    	}
		    	this.sjsxplList = sjsxplList;
		    	this.defaultPl = sjsxplList[0].code;
		    },
		    getCycle:function(event){//日期切花
            	var dateElement=$(event.currentTarget).siblings(".control-input").find("input");
            	if($(event.currentTarget).find("option:selected").val()=="day"){
		    		dateElement.val(DateFmt.Formate(new Date(),"yyyy-MM-dd")).datepicker( "destroy" ).datepicker({
		    			dateFormat: "yy-mm-dd",
						showButtonPanel: false,
						changeMonth: false,
      					changeYear: false,
      					defaultDate:+1,
      					minDate:DateFmt.DateCalc(new Date(),"d",-2),
		    			maxDate:DateFmt.DateCalc(new Date(),"d",0),
						beforeShow :function(){
			    			$("#ui-datepicker-div").removeClass("ui-hide-calendar");
			    		},
		    		}).off("click");
		    	}else{
		    		dateElement.val(DateFmt.Formate(new Date(),"yyyy-MM")).datepicker( "destroy" ).datepicker({
            			dateFormat: "yyyy-MM",
						showButtonPanel: true,
						closeText:"确定" ,
						changeMonth: true,
      					changeYear: true,
      					minDate:DateFmt.DateCalc(new Date(),"M",-12),
		    			maxDate:DateFmt.DateCalc(new Date(),"M",0),
						beforeShow :function(){
			    			$("#ui-datepicker-div").addClass("ui-hide-calendar");
			    		},
			    		 onClose: function(dateText, inst) {
    			            var month = $("#ui-datepicker-div .ui-datepicker-month option:selected").val();//得到选中的月份值
    			            var year = $("#ui-datepicker-div .ui-datepicker-year option:selected").val();//得到选中的年份值
    			            var dateStr = DateFmt.Formate(DateFmt.parseDate(year+'-'+(parseInt(month)+1)),"yyyy-MM");
    			            dateElement.val(dateStr);//给input赋值，其中要对月值加1才是实际的月份
    			        }
            		}).off("click");
		    	}
            },
		    getStatus:function(event){//状态切换
		    	if($(event.currentTarget).find("option:selected").val()=="1"){
		    		this.initDataPrepareTable(true);
		    	}else{
		    		this.initDataPrepareTable(false);
		    	}
		    },
		    changeDataPrepareTable:function(status){

		    	console.log(status)
		    	this.isDown=false;
		    	this.zbFlag = status;
		    	this.tableId = "#dataPrepareTable";
		    	this.tableUrl = $.ctx + "/api/source/TargetTableStatus/queryPage";
		    	this.postData = {'configId' : this.configId};
		    	this.pager = "#dataPreparePager";
		    	if(status){
		    		$("#sjzbList").css("display","");
		    		$("#sjcqList").css("display","none");
		    		this.colNames = [ '表名', '准备状态', '准备完成时间','抽取完成时间' ];
		    		this.colModel = [ {
		    			name : 'sourceTableName',
		    			index : 'sourceTableName',
		    			width : 110,
		    			sortable : false,
		    			frozen : true,
		    		},
		    		{
		    			name : 'dataStatus',
		    			index : 'dataStatus',
		    			width : 50,
		    			align : "center",
		    			sortable : false,
		    			formatter : function(value, opts, data) {
		            		return $.getCodeDesc("SJZBZT",value);
		            	}
		    		}, {
		    			name : 'startTime',
		    			index : 'startTime',
		    			width : 110,
		    			sortable : false,
		    			align : "center",
		    		},{
		    			name : 'endTime',
		    			index : 'endTime',
		    			width : 110,
		    			sortable : false,
		    			align : "center",
		    		}];
		    	}else{
		    		$("#sjzbList").css("display","none");
		    		$("#sjcqList").css("display","");
		    		this.colNames = [ '表名', '抽取状态', '准备完成时间','抽取完成时间' ];
		    		this.colModel = [ {
		    			name : 'sourceTableName',
		    			index : 'sourceTableName',
		    			width : 110,
		    			sortable : false,
		    			frozen : true,
		    		}, {
		    			name : 'isDoing',
		    			index : 'isDoing',
		    			sortable : false,
		    			width : 120,
		    			align : "center",
		    			formatter : function(value, opts, data) {
		            		return $.getCodeDesc("SJCQZT",value);
		            	}
		    				
		    		}, {
		    			name : 'dataDate',
		    			index : 'dataDate',
		    			width : 110,
		    			sortable : false,
		    			align : "center",
		    		} ,{
		    			name : 'dataDate',
		    			index : 'dataDate',
		    			width : 110,
		    			sortable : false,
		    			align : "center",
		    		}];
		    	}
		    	this.reloadTable();
		    },
		    //初始化数据准备表格
		    initDataPrepareTable:function(status){
		    	console.log(status)
		    	this.isDown=false;
		    	this.zbFlag = status;
		    	this.tableId = "#dataPrepareTable";
		    	this.tableUrl = $.ctx + "/api/source/TargetTableStatus/queryPage";
		    	this.postData = {'configId' : this.configId};
		    	this.pager = "#dataPreparePager";
		    	this.colNames = [ '表名', '准备状态', '准备完成时间','抽取完成时间' ];
		    	this.colModel = [ {
	    			name : 'sourceTableName',
	    			index : 'sourceTableName',
	    			width : 110,
	    			sortable : false,
	    			frozen : true,
	    		},
	    		{
	    			name : 'dataStatus',
	    			index : 'dataStatus',
	    			width : 50,
	    			align : "center",
	    			sortable : false,
	    			formatter : function(value, opts, data) {
	            		return $.getCodeDesc("SJZBZT",value);
	            	}
	    		}, {
	    			name : 'startTime',
	    			index : 'startTime',
	    			width : 110,
	    			sortable : false,
	    			align : "center",
	    		},{
	    			name : 'endTime',
	    			index : 'endTime',
	    			width : 110,
	    			sortable : false,
	    			align : "center",
	    		}];
		    	if(status){
		    		$("#sjzbList").css("display","");
		    		$("#sjcqList").css("display","none");
		    		
		    	}else{
		    		$("#sjzbList").css("display","none");
		    		$("#sjcqList").css("display","");
		    		this.colNames = [ '表名', '抽取状态', '准备完成时间','抽取完成时间' ];
//		    		this.colModel = [ {
//		    			name : 'sourceTableName',
//		    			index : 'sourceTableName',
//		    			width : 110,
//		    			sortable : false,
//		    			frozen : true,
//		    		}, {
//		    			name : 'isDoing',
//		    			index : 'isDoing',
//		    			sortable : false,
//		    			width : 120,
//		    			align : "center",
//		    			formatter : function(value, opts, data) {
//		            		return $.getCodeDesc("SJCQZT",value);
//		            	}
//		    				
//		    		}, {
//		    			name : 'dataDate',
//		    			index : 'dataDate',
//		    			width : 110,
//		    			sortable : false,
//		    			align : "center",
//		    		} ,{
//		    			name : 'dataDate',
//		    			index : 'dataDate',
//		    			width : 110,
//		    			sortable : false,
//		    			align : "center",
//		    		}];
		    	}
		    	this.initTable();
		    },
		    //初始化标签生成表格
		    initLabelGenerateTable:function(){
		    	this.tableId = "#labelGenerateTable";
		    	this.tableUrl = $.ctx + "/api/label/labelStatus/queryPage";
		    	this.postData = {'configId' : this.configId};
		    	this.colNames = [ '标签名称', '生成状态', '目标表列名', '表名','表类型','错误信息描述' ];
		    	this.colModel = [ {
		    		name : 'labelInfo.labelName',
		    		index : 'labelInfo.labelName',
		    		width : 110,
		    		sortable : false,
		    		frozen : true,
		    	},
		    	{
		    		name : 'dataStatus',
		    		index : 'dataStatus',
		    		width : 50,
		    		align : "center",
		    		sortable : false,
			        formatter: function(cellvalue, options, rowObject) {
			            var action ="showErrorInfo(" +  "'" + options.rowId + "','#labelGenerateTable')";
			            return "<a style='color: #89B9FF;font-size: 14px;text-decoration: underline;' onclick=" + action + " >"+$.getCodeDesc("BQSCZT",cellvalue)+"</a>";
			        }
		    	}, {
		    		name : 'mdaSysTableColumnNames',
		    		index : 'mdaSysTableColumnNames',
		    		width : 110,
		    		sortable : false,
		    		align : "center",
		    	}, {
		    		name : 'mdaSysTable.tableName',
		    		index : 'mdaSysTable.tableName',
		    		sortable : false,
		    		width : 120,
		    		align : "center"
		    			
		    	} ,{
		    		name : 'mdaSysTable.tableType',
		    		index : 'mdaSysTable.tableType',
		    		width : 110,
		    		sortable : false,
		    		align : "center",
		    		formatter : function(value, opts, data) {
		        		return $.getCodeDesc("BQKBLX",value);
		        	}
		    	},{
		    		name : 'exceptionDesc',
		    		index : 'exceptionDesc',
		    		hidden:true
		    	}];
		    	this.pager = "#labelGeneratePager";
		    	this.initTable();
		    },
		    //初始化客户群生成
		    initCustomGenerateTable:function(){
		    	this.tableId = "#customGenerateTable";
		    	this.tableUrl = $.ctx + "/api/label/listInfo/queryPage";
		    	this.postData = {"configId" :this.configId};
		    	this.colNames =[ '客户群名称', '生成状态', '生成时间' ];
		    	this.colModel = [ {
		    		name : 'label.labelName',
		    		index : 'label.labelName',
		    		width : 110,
		    		sortable : false,
		    		frozen : true,
		    	},
		    	{
		    		name : 'dataStatus',
		    		index : 'dataStatus',
		    		width : 50,
		    		align : "center",
		    		sortable : false,
		    		formatter : function(value, opts, data) {
		        		return $.getCodeDesc("KHQSCZT",value);
		        	}
		    	},{
		    		name : 'dataDate',
		    		index : 'dataDate',
		    		width : 110,
		    		sortable : false,
		    		align : "center",
		    	}];
		    	this.pager = "#customGeneratePager" ;
		    	this.initTable();
		    },
		    //初始化客户群推送表格
		    initCustomPushTable:function(){
		    	this.tableId = "#customPushTable";
		    	this.tableUrl =  $.ctx + "/api/syspush/labelPushReq/queryPage";
		    	this.postData = {'configId' : this.configId};
		    	this.colNames =[ '客户群名称', '推送平台', '推送时间','推送状态' ];
		    	this.colModel = [ {
		    		name : 'labelInfo.labelName',
		    		index : 'labelInfo.labelName',
		    		width : 110,
		    		sortable : false,
		    		frozen : true,
		    	},
		    	 {
		    		name : 'sysName',
		    		index : 'sysName',
		    		width : 110,
		    		sortable : false,
		    		frozen : true,
		    	},{
		    		name : 'startTime',
		    		index : 'startTime',
		    		width : 110,
		    		sortable : false,
		    		align : "center",
		    	},
		    	{
		    		name : 'pushStatus',
		    		index : 'pushStatus',
		    		width : 50,
		    		align : "center",
		    		sortable : false,
		    		formatter : function(value, opts, data) {
		        		return $.getCodeDesc("KHQTSZT",value);
		        	}
		    	}];
		    	this.pager= "#customPushPager"; 
		    	this.initTable();
		    },
		    //初始化表格公共方法
		    initTable:function(){
		    	$(this.tableId).jqGrid({
		    		url :this.tableUrl,
		    		postData : this.postData,
		    		datatype : "json",
		    		colNames : this.colNames,
		    		colModel : this.colModel,
		    		viewrecords : true,
		    		rowNum : 10,
		    		rownumbers : true,
		    		jsonReader : {
		    			repeatitems : false,
		    			id : "0"
		    		},
		    		height : '100%',
		    	    rowList:[10,20,30],
		            pager: this.pager  
		    	});
		    },
		    reloadTable:function(tableId){
		    	$(tableId)
//		    	.setGridParam({
//					postData: $('#formSearch').formToJson()
//				})
				.trigger("reloadGrid", [{
					page: 1
				}]);
		    }
		},
		mounted: function(){
			this.configId = $.getUrlParam("configId");
		    if(!this.configId){
		    	this.configId = configId;
		    }
		    // 加载初始化数据
		    this.loadMonitorDetailData(this.configId);
		    
		    //加载数据准备表格
			this.initDataPrepareTable();
			
			//加载标签生成表格
			this.initLabelGenerateTable();
			
  			//加载客户群生成表格
			this.initCustomGenerateTable();
			
 			//加载客户群推送表格
			this.initCustomPushTable();
			
			//默认刷新频率
//			this.refresh(this.defaultPl);
			
			//从监控总览页面跳转到指定锚点位置
			var detailAnchor = $.getUrlParam("detailAnchor");
			if(detailAnchor){
				location.href="#"+detailAnchor;
			}
		}
		
	});
	$(".ui-pre-progress a").each(function(e){//锚点定位
		var anchors=$(this);
		$(this).click(function(){
            setTimeout(function(){
                anchors.addClass("active").siblings("a").removeClass("active");
            },500);
            var $index = $(this).index();
			var scrollTop=$(".scrollBox").eq($index).offset().top-50;
			$("html,body").stop().animate({"scrollTop":scrollTop})
		})
	})
	$(window).scroll(function(){
        var $stop=$(this).scrollTop();
        $(".scrollBox").each(function(e){
            var $hei_v=$(this).position().top-50;
            if($stop>=$hei_v){
                $('.ui-pre-progress a').eq(e).addClass('active').siblings("a").removeClass("active");
            }
        });
    })
};

/**
 * 显示异常信息
 * @param rowId
 */
function showErrorInfo(rowId,tableId){
	$("#catchError").css("display","");
	var rowData = $(tableId).jqGrid("getRowData", rowId);
	$("#catchError span").text(rowData.exceptionDesc);
}
function scroll(){
    if(window.pageYOffset!=null){//标准模式
        return{
            left:window.pageXOffset,
            top:window.pageYOffset
        }
    }else if(document.compatMode=="CSS1Compat"){//不是怪异模式
        return{
            left:document.documentElement.scrollLeft,
            top:document.documentElement.scrollTop
        }
    }else{//怪异模式
        return{
            left:document.body.scrollLeft,
            top:document.body.scrollTop
        }
    }
}
window.onscroll =function(){//吸顶导航
    if(scroll().top>=281){
        $(".ui-pre-progress").addClass("active");
    }else{
        $(".ui-pre-progress").removeClass("active");
    }

}
function getTime(element){//初始化日期
	$(element).datepicker({
		dateFormat: "yy-mm-dd",
		minDate:DateFmt.DateCalc(new Date(),"d",-2),
		maxDate:DateFmt.DateCalc(new Date(),"d",0),
	});
}