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
			qryDataDate:"",//数据日期
		  	firstDataDay:'',//最新日周期日期
        	firstDataMonth:'',//最新月周期日期
			yyjkList:[],  //运营监控枚举
			sjzbList:[],  //数据准备状态
			sjzbBox:[],//数据状态复选框数组
			sjcqList:[],  //数据抽取状态
			sjcqBox:[],//抽取状态复选框数组
			bqscList:[],  //标签生成状态
			bqscBox:[],//数据生成复选框数组
			khqscList:[],  //客户群生成生成状态
			khqscBox:[],//客户群生成复选框数组
			khqtsList:[],  //客户群推送生成状态
			khqtsBox:[],//客户群推送复选框数组
			sjsxplList:[], //数据刷新频率
			defaultPl:"",//默认刷新频率
			defaultTimes:5000, // 默认刷新频率
			timerId:"",   // 页面刷新定时器
			tableUrl:"",  //表格Url
			postData:"",  //表格参数
			colNames:"",  //表格表头
			colModel:"",  //表格模型
			pager:"" ,  //表格分页id
			readCycle:1,//日期周期
//			sysDay:"",//系统设置日周期范围
//			sysMonth:"",//系统设置月周期范围
			zbFlag:true, //数据准备表格默认显示准备状态
			isOpen:false,//日期下拉
			isDown:false,//状态下拉
		},
		methods:{
			//加载监控数据
			loadMonitorDetailData:function(){
			    // 加载初始化数据
				this.loadCountData();
				
				 //加载数据准备表格
				this.initDataPrepareTable();

				//加载标签生成表格
				this.initLabelGenerateTable();
				
	  			//加载客户群生成表格
				this.initCustomGenerateTable();
				
	 			//加载客户群推送表格
				this.initCustomPushTable();
			},
			toggle:function(){
				this.isOpen=!this.isOpen;
			},
            getLastestDate:function(fn){
            	var that = this;
            	var iframeDataDate = $.getUrlParam("dataDate");
    			if(iframeDataDate){
    				that.firstDataDay = DateFmt.Formate(new Date(iframeDataDate),"yyyy-MM-dd");
					that.firstDataMonth = DateFmt.Formate(new Date(iframeDataDate),"yyyy-MM");
					that.qryDataDate = that.firstDataDay;
    			}else{
    				$.commAjax({
    					url : $.ctx + "/api/source/TargetTableStatus/selectLastestDateByCycle",
    					postData : {
    						readCycle : that.readCycle
    					},
    					onSuccess : function(returnObj) {
    						if(returnObj.data){
    							that.firstDataDay = DateFmt.Formate(new Date(returnObj.data),"yyyy-MM-dd");
    							that.firstDataMonth = DateFmt.Formate(new Date(returnObj.data),"yyyy-MM");
    						}else{
    							var now = new Date();
    							that.firstDataDay = $.dateFormat(new Date(now.getTime() - 2*24*60*60*1000),"yyyy-MM-dd");
    							that.firstDataMonth = $.dateFormat(new Date(now.getTime() - 2*24*60*60*1000),"yyyy-MM");
    						}
    						that.qryDataDate = that.firstDataDay;
    						fn();
    					}
    				});
    			}
            },
            //切换日期刷新监控数据
            changeMonitorByDate:function(dateTime){
            	var that = this;
            	$.commAjax({			
        		    url : $.ctx+'/api/monitor/overview/queryMonitorMainByConfig',
        		    isShowMask : true,
					maskMassage : '加载中...',
        		    dataType : 'json', 
        		    async:true,
        		    postData : {
        					"configId" : this.configId,
        					"dataDate" : dateTime.replace(/-/g,"")
        			},
        		    onSuccess: function(returnOb){
        		    	if(returnOb.data){
        		    		that.serviceMonitorObj = returnOb.data;
        		    	}
        		    },
        	   });
            },
            //加载监控明细数据
			loadCountData:function(callback){
				var that = this;
				$.commAjax({
					url : $.ctx + "/api/monitor/overview/queryMonitorMainByConfig",
					postData : {
						configId : that.configId,
						dataDate : that.qryDataDate.replace(/-/g,""),
					},
					onSuccess : function(returnObj) {
						if(returnObj.data){
        		    		that.serviceMonitorObj = returnObj.data;
        		    	}
					}
				});
			},
			//表格模糊查询方法
			qryTableByCond:function(e){
				var $el=$(e.target);
				var tableId = $el.attr("data-tableId");
				var conds = [];
				var formCond = $("#"+$el.attr("data-formSearchId")).formToJson;
				if("dataPrepareTable" === tableId){
					formCond.sourceTableInfo.configId = this.configId;
					formCond.dataDate = that.qryDataDate.replace(/-/g,"");
				}else if("labelGenerateTable" === tableId){
					formCond.labelInfo.configId = this.configId;
					formCond.dataDate = this.qryDataDate.replace(/-/g,"");
				}else if("customGenerateTable" === tableId){
					formCond.labelInfo.configId = this.configId;
					formCond.dataDate = this.qryDataDate.replace(/-/g,"");
				}else if("customPushTable" === tableId){
					formCond.labelPushCycle.labelInfo.configId = this.configId;
					formCond.dataDate = this.qryDataDate.replace(/-/g,"");
				}
				conds.push(formCond);
				$($("#"+tableId)).setGridParam({
					postData:JSON.stringify(conds),
					dataType : 'json'
				}).trigger("reloadGrid", [{
					page: 1
				}]);
			},
			//表格点击状态过滤 
		    qryTableByStatus:function(e,tableId){
		    	var $el=$(e.target);
		    	var postConds = [];
		    	var ztListId = $el.parents("div.ui-status").attr("id");
		    	//数据准备表格
		    	if("sjzbList" === ztListId){
		    		if($el.prop('checked')){
		    			this.sjzbBox.push($el.val());
		    			if(this.sjzbBox.length === this.sjzbList.length){
		    				$("#sjzbAll").prop("checked",true);
		    			}else{
		    				$("#sjzbAll").prop("checked",false);
		    			}
		    		}else{
		    			for(var i=0;i<this.sjzbBox.length;i++){
		    				if($el.val() === this.sjzbBox[i]){
		    					 this.sjzbBox.splice(i,1);
		    				}
		    			}
		    			$("#sjzbAll").prop("checked",false);
		    		}
		    		var sjzbCodes = [];
		    		$.each(this.sjzbBox,function(key,value){
		    			sjzbCodes.push(value);
		    		});
		    		postObj = {};
		    		postObj.qryDataStatus = sjzbCodes;
		    		postObj.sourceTableInfo.configId = this.configId;
		    		postObj.dataDate = this.qryDataDate.replace(/-/g,"");
		    	}else if("sjcqList" === ztListId){
		    		if($el.prop('checked')){
		    			this.sjcqBox.push($el.val());
		    			if(this.sjcqBox.length === this.sjcqList.length){
		    				$("#sjzbAll").prop("checked",true);
		    			}else{
		    				$("#sjzbAll").prop("checked",false);
		    			}
		    		}else{
		    			for(var i=0;i<this.sjcqBox.length;i++){
		    				if($el.val() === this.sjcqBox[i]){
		    					 this.sjcqBox.splice(i,1);
		    				}
		    			}
		    			$("#sjzbAll").prop("checked",false);
		    		}
		    		var sjcqCodes = [];
		    		$.each(this.sjcqBox,function(key,value){
		    			sjcqCodes.push(value);
		    		});
		    		postObj = {};
		    		postObj.qryIsDoing = sjcqCodes;
		    		postObj.sourceTableInfo.configId = this.configId ;
		    		postObj.dataDate = this.qryDataDate.replace(/-/g,"");
		    	}else if("bqscList" === ztListId){
		    		if($el.prop('checked')){
		    			this.bqscBox.push($el.val());
		    			if(this.sjcqBox.length === this.sjcqList.length){
		    				$("#bqscAll").prop("checked",true);
		    			}else{
		    				$("#bqscAll").prop("checked",false);
		    			}
		    		}else{
		    			for(var i=0;i<this.bqscBox.length;i++){
		    				if($el.val() === this.bqscBox[i]){
		    					 this.bqscBox.splice(i,1);
		    				}
		    			}
		    			$("#bqscAll").prop("checked",false);
		    		}
		    		var bqscCodes = [];
		    		$.each(this.sjcqBox,function(key,value){
		    			bqscCodes.push(value);
		    		});
		    		postObj = {};
		    		postObj.qryDataStatus = bqscCodes;
		    		postObj.labelInfo.configId = this.configId;
		    		postObj.dataDate = this.qryDataDate.replace(/-/g,"");
		    	}else if("customGenerateTable" === tableId){
		    		if($el.prop('checked')){
		    			this.khqscBox.push($el.val());
		    			if(this.khqscBox.length === this.khqscList.length){
		    				$("#khqscAll").prop("checked",true);
		    			}else{
		    				$("#khqscAll").prop("checked",false);
		    			}
		    		}else{
		    			for(var i=0;i<this.khqscBox.length;i++){
		    				if($el.val() === this.khqscBox[i]){
		    					 this.khqscBox.splice(i,1);
		    				}
		    			}
		    			$("#khqscAll").prop("checked",false);
		    		}
		    		var khqscCodes = [];
		    		$.each(this.khqscBox,function(key,value){
		    			khqscCodes.push(value);
		    		});
		    		postObj = {};
		    		postObj.qryDataStatus = khqscCodes;
		    		postObj.labelInfo.configId = this.configId;
		    		postObj.dataDate = this.qryDataDate.replace(/-/g,"");
		    	}else if("customPushTable" === tableId){
		    		if($el.prop('checked')){
		    			this.khqtsBox.push($el.val());
		    			if(this.khqtsBox.length === this.khqtsList.length){
		    				$("#khqtsAll").prop("checked",true);
		    			}else{
		    				$("#khqtsAll").prop("checked",false);
		    			}
		    		}else{
		    			for(var i=0;i<this.khqtsBox.length;i++){
		    				if($el.val() === this.khqtsBox[i]){
		    					 this.khqtsBox.splice(i,1);
		    				}
		    			}
		    			$("#khqtsAll").prop("checked",false);
		    		}
		    		var khqtsCodes = [];
		    		$.each(this.khqtsBox,function(key,value){
		    			khqtsCodes.push(Number(value));
		    		});
		    		postObj = {};
		    		postObj.qryPushStatus = khqtsCodes;
		    		postObj.labelPushCycle.labelInfo.configId = this.configId;
		    		postObj.dataDate = this.qryDataDate.replace(/-/g,"");
		    	}
		    	postConds.push(postObj);
				$("#"+tableId).setGridParam({
					postData: JSON.stringify(postConds),
					dataType : 'json'
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
	        	var that = this;
	            clearTimeout(that.timerId);   // 关闭定时器
	            that.timerId = setTimeout(function(){
	            	that.loadMonitorDetailData(that.configId);
	            	//刷新数据准备表格
	     			that.reloadTable("#dataPrepareTable",that.zbFlag);
	     			//刷新标签生成表格
	     			that.reloadTable("#labelGenerateTable");
//	       			//刷新客户群生成表格
	     			that.reloadTable("#customGenerateTable");
	      			//刷新客户群推送表格
	     			that.reloadTable("#customPushTable");
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
		    		this.sjzbBox.push(dicSjzb[i].code);
		    		sjzbList.push(dicSjzb[i]);
		    	}
		    	this.sjzbList = sjzbList;
		    },
		    //获取数据抽取状态字典
		    loadDicSjcq :function(){
		    	var sjcqList = [];
		    	var dicSjcq = $.getDicData("SJCQZT");
		    	for(var i=0; i<dicSjcq.length; i++){
		    		this.sjcqBox.push(dicSjcq[i].code);
		    		sjcqList.push(dicSjcq[i]);
		    	}
		    	this.sjcqList = sjcqList;
		    },
		    //获取标签生成状态字典
		    loadDicBqsc :function(){
		    	var bqscList = [];
		    	var dicBqsc = $.getDicData("BQSCZT");
		    	for(var i=0; i<dicBqsc.length; i++){
		    		this.bqscBox.push(dicBqsc[i].code);
		    		bqscList.push(dicBqsc[i]);
		    	}
		    	this.bqscList = bqscList;
		    },
		    //获取客户群生成状态字典
		    loadDicKhqsc :function(){
		    	var khqscList = [];
		    	var dicKhqsc = $.getDicData("QTZTZD");
		    	for(var i=0; i<dicKhqsc.length; i++){
		    		this.khqscBox.push(dicKhqsc[i].code);
		    		khqscList.push(dicKhqsc[i]);
		    	}
		    	this.khqscList = khqscList;
		    },
		    //获取客户群推送状态字典
		    loadDicKhqts :function(){
		    	var khqtsList = [];
		    	var dicKhqts = $.getDicData("KHQTSZT");
		    	for(var i=0; i<dicKhqts.length; i++){
		    		this.khqtsBox.push(dicKhqts[i].code);
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
		    getCycle:function(event){//日期切换
            	var that = this;
            	var dateElement=$(event.currentTarget).siblings(".control-input").find("input");
            	this.readCycle =$(event.currentTarget).find("option:selected").val();
            	if(Number(this.readCycle) === 1){
            		dateElement.val(this.firstDataDay);
            		this.qryDataDate=this.firstDataDay;
		    	}else{
		    		dateElement.val(this.firstDataMonth);
		    		this.qryDataDate=this.firstDataMonth;
		    	}
        	  that.changeMonitorByDate(dateElement.val());
            },
            // 日期切换函数
            getTime:function(e){//初始化日期
         	   var that = this;
         	   var sysMonth = 1 - $.getSysConfig('LOC_CONFIG_APP_MAX_KEEP_MONTHS');
		       var sysDay = 1 - $.getSysConfig('LOC_CONFIG_APP_MAX_KEEP_DAYS');
 	           if(Number($(event.currentTarget).parents("div").siblings("select").find("option:selected").val()) === 1){
 	        		var minDay ='#F{$dp.$DV(\''+that.qryDataDate+'\',{d:'+sysDay+'});}';
	        		WdatePicker({
	        			isShowClear:false,
	        			dateFmt:'yyyy-MM-dd',
		    			maxDate:that.qryDataDate,
		    			minDate:minDay,
		    			onpicked: function(dq) {
		    				that.changeMonitorByDate(dq.cal.getNewDateStr());
		    			}
		    		});
 	        	}else{
 	        		var minMonth ='#F{$dp.$DV(\''+that.qryDataDate+'\',{M:'+sysMonth+'});}';
	        		WdatePicker({
	        			isShowClear:false,
	        			dateFmt:'yyyy-MM',
		    			maxDate:that.qryDataDate,
		    			minDate:minMonth,
		    			onpicked: function(dq) {
		    				that.changeMonitorByDate(dq.cal.getNewDateStr());
		    			}
		    		});
 	        	}
             },
            changeDataPrepareTableByStatus:function(event){//状态切换
            	this.isDown=false;
		    	if(Number($(event.currentTarget).find("option:selected").val())===1){
		    		this.zbFlag = true;
		    	}else{
		    		this.zbFlag = false;
		    	}
		    	this.reloadTable("#dataPrepareTable");
		    },
		    //初始化数据准备表格
		    initDataPrepareTable:function(){
		    	this.isDown=false;
		    	var tableId = "#dataPrepareTable";
		    	this.tableUrl = $.ctx + "/api/source/TargetTableStatus/queryPage";
		    	this.postData = {"sourceTableInfo.configId": this.configId,"dataDate":this.qryDataDate.replace(/-/g,"")};
		    	this.pager = "#dataPreparePager";
		    	this.colNames = [ '表名', '准备状态', '抽取状态','准备完成时间','抽取完成时间' ];
		    	this.colModel = [ {
	    			name : 'sourceTableName',
	    			index : 'sourceTableName',
	    			width : 150,
	    			sortable : false,
	    			align: "center",
	    			frozen : true,
	    		},
	    		{
	    			name : 'dataStatus',
	    			index : 'dataStatus',
	    			width : 60,
	    			align : "center",
	    			sortable : false,
	    			cellattr: setColColor,
	    			formatter : function(value, opts, data) {
	            		return $.getCodeDesc("SJZBZT",value);
	            	}
	    		},{
	    			name : 'isDoing',
	    			index : 'isDoing',
	    			sortable : false,
	    			width : 60,
	    			align : "center",
	    			formatter : function(value, opts, data) {
	            		return $.getCodeDesc("SJCQZT",value);
	            	}
	    		},{
	    			name : 'startTime',
	    			index : 'startTime',
	    			width : 120,
	    			sortable : false,
	    			align : "center",
	    			formatter : function(cellvalue) { 
	    				if(cellvalue){
	   					    return cellvalue.substr(0,19);
	    				}
	    				return "";
					}
	    		},{
	    			name : 'endTime',
	    			index : 'endTime',
	    			width : 120,
	    			sortable : false,
	    			align : "center",
	    			formatter : function(cellvalue) {  
	    				if(cellvalue){
	   					    return cellvalue.substr(0,19);
	    				}
	    				return "";
					}
	    		}];
		    	this.initTable(tableId);
		    },
		    //初始化标签生成表格
		    initLabelGenerateTable:function(){
		    	var tableId = "#labelGenerateTable";
		    	this.tableUrl = $.ctx + "/api/label/labelStatus/queryPage";
		    	this.postData = {'labelInfo.configId' : this.configId,"dataDate":this.qryDataDate.replace(/-/g,"")};
		    	this.colNames = [ '标签名称', '生成状态', '目标表列名', '表名','表类型','错误信息描述' ];
		    	this.colModel = [ {
		    		name : 'labelInfo.labelName',
		    		index : 'labelInfo.labelName',
		    		width : 110,
		    		align : "center",
		    		sortable : false,
		    		frozen : true,
		    	},
		    	{
		    		name : 'dataStatus',
		    		index : 'dataStatus',
		    		width : 50,
		    		align : "center",
		    		sortable : false,
		    		cellattr: setColColor,
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
		    	this.initTable(tableId);
		    },
		    //初始化客户群生成
		    initCustomGenerateTable:function(){
		    	var tableId = "#customGenerateTable";
		    	this.tableUrl = $.ctx + "/api/label/listInfo/queryPage";
		    	this.postData = {"labelInfo.configId" :this.configId,"dataDate":this.qryDataDate.replace(/-/g,"") };
		    	this.colNames =[ '客户群名称', '生成状态', '生成时间' ];
		    	this.colModel = [ {
		    		name : 'labelInfo.labelName',
		    		index : 'labelInfo.labelName',
		    		width : 110,
		    		align : "center",
		    		sortable : false,
		    		frozen : true
		    	},
		    	{
		    		name : 'dataStatus',
		    		index : 'dataStatus',
		    		width : 50,
		    		align : "center",
		    		sortable : false,
		    		cellattr: setColColor,
		    		formatter : function(value, opts, data) {
		        		return $.getCodeDesc("KHQSCZT",value);
		        	}
		    	},{
		    		name : 'dataTime',
		    		index : 'dataTime',
		    		width : 110,
		    		sortable : false,
		    		align : "center",
		    		formatter:function(value, opts, data){
		    			return DateFmt.dateFormatter(value, opts, data);
		    		}
		    	}];
		    	this.pager = "#customGeneratePager" ;
		    	this.initTable(tableId);
		    },
		    //初始化客户群推送表格
		    initCustomPushTable:function(){
		    	var tableId = "#customPushTable";
		    	this.tableUrl =  $.ctx + "/api/syspush/labelPushReq/queryPage";
		    	this.postData = {'labelPushCycle.labelInfo.configId' : this.configId,"dataDate":this.qryDataDate.replace(/-/g,"")};
		    	this.colNames =[ '客户群名称', '推送平台', '推送时间','推送状态' ];
		    	this.colModel = [ {
		    		name : 'labelPushCycle.labelInfo.labelName',
		    		index : 'labelPushCycle.labelInfo.labelName',
		    		width : 110,
		    		sortable : false,
		    		align : "center",
		    		frozen : true
		    	},
		    	 {
		    		name : 'labelPushCycle.sysInfo.sysName',
		    		index : 'labelPushCycle.sysInfo.sysName',
		    		width : 110,
		    		sortable : false,
		    		frozen : true,
		    		align : "center"
		    	},{
		    		name : 'startTime',
		    		index : 'startTime',
		    		width : 110,
		    		sortable : false,
		    		align : "center",
		    		formatter:function(value, opts, data){
		    			return DateFmt.dateFormatter(value, opts, data);
		    		}
		    	},
		    	{
		    		name : 'pushStatus',
		    		index : 'pushStatus',
		    		width : 50,
		    		align : "center",
		    		sortable : false,
		    		cellattr: setColColor,
		    		formatter : function(value, opts, data) {
		        		return $.getCodeDesc("KHQTSZT",value);
		        	}
		    	}];
		    	this.pager= "#customPushPager"; 
		    	this.initTable(tableId);
		    },
		    //初始化表格公共方法
		    initTable:function(tableId){
		    	var that = this;
		    	$(tableId).jqGrid({
		    		url :this.tableUrl,
		    		postData : this.postData,
		    		datatype : "json",
		    		colNames : this.colNames,
		    		colModel : this.colModel,
		    		autowidth: true,
		    		viewrecords : true,
		    		rowNum : 10,
		    		rownumbers : true,
		    		jsonReader : {
		    			repeatitems : false,
		    			id : "0"
		    		},
		    		height : '100%',
		    	    rowList:[10,20,30],
		    	    loadComplete:function(){
		    	    	if("#dataPrepareTable" === tableId ){
		    	    		if(that.zbFlag){
		    	    			$(tableId).jqGrid('hideCol',["isDoing"]);
		    	    			$(tableId).jqGrid('showCol',["dataStatus"]);
		    	    		}else{
		    	    			$(tableId).jqGrid('hideCol',["dataStatus"]);
		    	    			$(tableId).jqGrid('showCol',["isDoing"]);
		    	    		}
		    	    	}
		    	    },
		            pager: this.pager  
		    	});
		   	   $(tableId).jqGrid('setLabel',0, '序号');
		    },
		    reloadTable:function(tableId){
		    	$(tableId)
				.trigger("reloadGrid", [{
					page: 1
				}]);
		    },
		    selectAllStatus:function(e){
		    	var $el=$(e.target);
		    	var children  =$el.parents("div.ui-status").find("input").not(this);
		    	
		    	if($el.prop("checked")){
		    		$.each(children,function(key,value){
		        		$(this).prop("checked",true);
		        	});
		    	}else{
		    		$.each(children,function(key,value){
		        		$(this).prop("checked",false);
		        	});
		    	}
		    }
		},
		mounted: function(){
			var that = this;
			var iframeConfigId = $.getUrlParam("configId");
			if(iframeConfigId){
				this.configId = iframeConfigId;
				$("#preConfig_list").find("span").attr("configId",iframeConfigId);
			}else{
				this.configId = configId;
			}

			//数据字典赋值
			this.loadDicYyjk();
			this.loadDicSjzb();
			this.loadDicSjcq();
			this.loadDicBqsc();
			this.loadDicKhqsc();
			this.loadDicKhqts();
			this.loadDicSjsxpl();
			
			//获取最新数据日期：取指标源表状态表里最新数据日期
			this.getLastestDate(function(){
				//加载统计数据
				that.loadMonitorDetailData();
			});
		
			//默认刷新频率
//			this.refresh(this.defaultPl);
			
			//从监控总览页面跳转到指定锚点位置
			var detailAnchor = $.getUrlParam("detailAnchor");
			if(detailAnchor){
				//TODO iframe 锚点跳转
				$("#"+detailAnchor).trigger("click");
//				window.parent.open("#"+detailAnchor,"monitorDetail");
//				location.href="#"+detailAnchor;
//				location.target="monitorDetail"
			}
		}
		
	});
//	
//    monitorDetail.$nextTick(function () {
//    	monitorDetail.sysDay = 1 - $.getSysConfig('LOC_CONFIG_APP_MAX_KEEP_DAYS');
//    	monitorDetail.sysMonth = 1 - $.getSysConfig('LOC_CONFIG_APP_MAX_KEEP_MONTHS');
//    });
    
	$(".ui-pre-progress a").each(function(e){//锚点定位
		var anchors=$(this);
		$(this).click(function(){
            setTimeout(function(){
                anchors.addClass("active").siblings("a").removeClass("active");
            },500);
            var $index = $(this).index();
			var scrollTop=$(".scrollBox").eq($index).offset().top-50;
			$("html,body").stop().animate({"scrollTop":scrollTop});
		});
	});
	$(window).scroll(function(){
        var $stop=$(this).scrollTop();
        $(".scrollBox").each(function(e){
            var $hei_v=$(this).position().top-50;
            if($stop>=$hei_v){
                $('.ui-pre-progress a').eq(e).addClass('active').siblings("a").removeClass("active");
            }
        });
    });
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
        };
    }else if(document.compatMode=="CSS1Compat"){//不是怪异模式
        return{
            left:document.documentElement.scrollLeft,
            top:document.documentElement.scrollTop
        };
    }else{//怪异模式
        return{
            left:document.body.scrollLeft,
            top:document.body.scrollTop
        };
    }
}


//给列表状态设置不同的颜色
function setColColor(rowId, val, rawObject, datatype, cm, rdata, name) {
	if (val == '1') {
		return "style='color:#009933;'";
	}
	if (val == '2') {
		return "style='color:#FF9900;'";
	}
	if (val == '3') {
		return "style='color:#FF0000;'";
	}
}

window.onscroll =function(){//吸顶导航
    if(scroll().top>=281){
        $(".ui-pre-progress").addClass("active");
    }else{
        $(".ui-pre-progress").removeClass("active");
    }

};
