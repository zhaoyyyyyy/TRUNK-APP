/**
 * ------------------------------------------------------------------
 * 运行监控明细 add by shaosq 20180420
 * ------------------------------------------------------------------
 */
var monitorDetail;//运营监控明细Vue实例
var sysMonth;//系统设置月周期区间
var sysDay;//系统设置日周期区间
var newDay;//最新日周期日期
var newMonth;//最新月周期日期
window.loc_onload = function() {	
	sysMonth = 1 - $.getSysConfig('LOC_CONFIG_APP_MAX_KEEP_MONTHS');
    sysDay = 1 - $.getSysConfig('LOC_CONFIG_APP_MAX_KEEP_DAYS');
	monitorDetail = new Vue({
		el:"#monitorDetail",
		data:{
			serviceMonitorObj:{},  //运营总览
			qryDataDate:"",//数据日期
		  	chooseDataDay:'',//用户动态选择日周期日期
		  	chooseDataMonth:'',//用户动态选择月周期日期
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
			queryType:1,//数据准备表格初始化查询准备状态
			zbFlag:true, //数据准备表格默认显示准备状态
			isOpen:false,//日期下拉
			isDown:false,//状态下拉
		},
		methods:{
			//加载监控数据
			loadMonitorDetailData:function(){
			    // 加载初始化数据
				this.loadCountData();
			},
			toggle:function(){
				this.isOpen=!this.isOpen;
			},
            getLastestDate:function(fn){
            	var that = this;
            	var iframeDataDate = $.getUrlParam("dataDate");
            	var iframeReadCycle = $.getUrlParam("readCycle");
    			if(iframeDataDate){
    				that.chooseDataDay = DateFmt.Formate(new Date(iframeDataDate),"yyyy-MM-dd");
					that.chooseDataMonth = DateFmt.Formate(new Date(iframeDataDate),"yyyy-MM");
					that.readCycle = Number(iframeReadCycle);
					$("#readCycle").val(that.readCycle);
					if(that.readCycle === 1){
						that.qryDataDate = that.chooseDataDay;
					}else{
						that.qryDataDate = that.chooseDataMonth;
					}
					fn();
    			}else{
    				$.commAjax({
    					url : $.ctx + "/api/source/TargetTableStatus/selectLastestDateByCycle",
    					postData : {
    						readCycle : that.readCycle
    					},
    					onSuccess : function(returnObj) {
    						if(returnObj.data){
    							that.chooseDataDay = DateFmt.Formate(new Date(returnObj.data),"yyyy-MM-dd");
    							that.chooseDataMonth = DateFmt.Formate(new Date(returnObj.data),"yyyy-MM");
    							newDay = DateFmt.Formate(new Date(returnObj.data),"yyyy-MM-dd");
    							newMonth = DateFmt.Formate(new Date(returnObj.data),"yyyy-MM");
    						}else{
    							var now = new Date();
    							that.chooseDataDay = $.dateFormat(new Date(now.getTime() - 2*24*60*60*1000),"yyyy-MM-dd");
    							that.chooseDataMonth = $.dateFormat(new Date(now.getTime() - 2*24*60*60*1000),"yyyy-MM");
    							newDay = $.dateFormat(new Date(now.getTime() - 2*24*60*60*1000),"yyyy-MM-dd");
    							newMonth = $.dateFormat(new Date(now.getTime() - 2*24*60*60*1000),"yyyy-MM");
    						}
    						//初始化查询日期
    						that.qryDataDate = that.chooseDataDay;
    						fn();
    					}
    				});
    			}
            },
            //切换日期刷新监控数据
            changeMonitorByDate:function(){
            	var that = this;
            	$.commAjax({			
        		    url : $.ctx+'/api/monitor/overview/queryMonitorMainByConfig',
        		    isShowMask : true,
					maskMassage : '加载中...',
        		    dataType : 'json', 
        		    async:true,
        		    postData : {
        					"configId" : this.configId,
        					"dataDate" : this.qryDataDate.replace(/-/g,"")
        			},
        		    onSuccess: function(returnOb){
        		    	if(returnOb.data){
        		    		that.serviceMonitorObj = returnOb.data;
        		    	}
        		    },
        	   });
            },
            //加载监控明细数据
			loadCountData:function(){
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
//			// 设置页面刷新频率
//	        refresh:function(event){
//	            this.setTimes($(event.target).val());
//	            this.setTimer();
//	        },
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
	            	that.loadMonitorDetailData();
	            	//刷新数据准备表格
	            	qryDataPrepareTableByCond();
	     			//刷新标签生成表格
	     			qryLabelGenerateTableByCond();
	       			//刷新客户群生成表格
	     			qryCustomGenerateTableByCond();
	      			//刷新客户群推送表格
	     			qryCustomPushTableByCond();
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
            		dateElement.val(this.chooseDataDay);
            		this.qryDataDate=this.chooseDataDay;
		    	}else{
		    		dateElement.val(this.chooseDataMonth);
		    		this.qryDataDate=this.chooseDataMonth;
		    	}
            	that.qryDataDate = dateElement.val();
        	    that.changeMonitorByDate();
				//刷新数据准备表格
			  	qryDataPrepareTableByCond();
				//刷新标签生成表格
				qryLabelGenerateTableByCond();
					//刷新客户群生成表格
				qryCustomGenerateTableByCond();
					//刷新客户群推送表格
				qryCustomPushTableByCond();
            },
            // 日期切换函数
            getTime:function(){
               var that = this;
               sysMonth = 1 - $.getSysConfig('LOC_CONFIG_APP_MAX_KEEP_MONTHS');
   		       sysDay = 1 - $.getSysConfig('LOC_CONFIG_APP_MAX_KEEP_DAYS');
   		       //处理从总览跳转过来的初始化日期
   		       if(!newDay){
   		    	  newDay = that.chooseDataDay;
   		       }
   		       if(!newMonth){
   		    	  newMonth = that.chooseDataMonth;
 		       }
 	           if(Number($(event.currentTarget).parents("div").siblings("select").find("option:selected").val()) === 1){
 	        		var minDay ='#F{$dp.$DV(\''+ newDay +'\',{d:'+sysDay+'});}';
	        		WdatePicker({
	        			isShowClear:false,
	        			dateFmt:'yyyy-MM-dd',
		    			maxDate:newDay,
		    			minDate:minDay,
		    			onpicked: function(dq) {
		    				that.qryDataDate = dq.cal.getNewDateStr();
		    				
		    				that.changeMonitorByDate();
		    				//刷新数据准备表格
		    			  	qryDataPrepareTableByCond();
		    				//刷新标签生成表格
		    				qryLabelGenerateTableByCond();
		    					//刷新客户群生成表格
		    				qryCustomGenerateTableByCond();
		    					//刷新客户群推送表格
		    				qryCustomPushTableByCond();
		    				$(this).blur();
		    			}
		    		});
 	        	}else{
 	        		var minMonth ='#F{$dp.$DV(\''+newMonth+'\',{M:'+sysMonth+'});}';
	        		WdatePicker({
	        			isShowClear:false,
	        			dateFmt:'yyyy-MM',
		    			maxDate:newMonth,
		    			minDate:minMonth,
		    			onpicked: function(dq) {
		    				that.qryDataDate = dq.cal.getNewDateStr();
		    				
		    				that.changeMonitorByDate();
		    				//刷新数据准备表格
		    			  	qryDataPrepareTableByCond();
		    				//刷新标签生成表格
		    				qryLabelGenerateTableByCond();
		    				//刷新客户群生成表格
		    				qryCustomGenerateTableByCond();
		    				//刷新客户群推送表格
		    				qryCustomPushTableByCond();
		    				$(this).blur();
		    			}
		    		});
 	        	}
             }
		},
		mounted: function(){
			var that = this;
			this.configId = $.getCurrentConfigId();

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
				
			    //加载数据准备表格
		    	initDataPrepareTable(that);
		    	
		    	//加载标签生成表格
		    	initLabelGenerateTable(that);
		    	
				//加载客户群生成表格
		    	initCustomGenerateTable(that);

				//加载客户群推送表格
				initCustomPushTable(that);
				
				//从监控总览页面跳转到指定锚点位置
				var detailAnchor = $.getUrlParam("detailAnchor");
				if(detailAnchor){
					$("#returnBtn").show();
					location.href="#"+detailAnchor;
				}else{
					$("#returnBtn").hide();
				}
			});
		}
		
	});
    
	//默认刷新频率
    refresh(monitorDetail.defaultPl);
    
	$(".ui-pre-progress a").each(function(){//锚点定位
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
	
	$(".anchors-list > li.ui-prerecture-item").each(function(){//锚点定位
		var $index = $(this).index();
		$(this).find("span.scroll-anchors").click(function(){
			var scrollTop=$(".scrollBox").eq($index).offset().top-80;
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
	
	$("#refreshId").change(function(){
		refresh($(this).children('option:selected').val());
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
    if(window.pageYOffset!==null){//标准模式
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

//设置页面刷新频率
function refresh(val){
	monitorDetail.setTimes(val);
	monitorDetail.setTimer();
}
window.onscroll =function(){//吸顶导航
    if(scroll().top>=281){
        $(".ui-pre-progress").addClass("active");
    }else{
        $(".ui-pre-progress").removeClass("active");
    }

};
