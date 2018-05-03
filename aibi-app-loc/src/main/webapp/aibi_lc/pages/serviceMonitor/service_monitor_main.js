/**
 * ------------------------------------------------------------------
 * 运行监控总览 add by shaosq 20180420
 * ------------------------------------------------------------------
 */
var monitorMain;////运营监控总览Vue实例
window.loc_onload = function() {
    monitorMain = new Vue({
        el:"#monitorMain",
        data:{
        	configId:'',//专区ID
        	readCycle:1,//周期默认为日周期
        	firstDataDay:'',//最新日周期日期
        	firstDataMonth:'',//最新月周期日期
//        	sysDay:'',//系统设置日周期范围
//        	sysMonth:'',//系统设置月周期范围
        	configData:[],//专区信息
        	monitorData:[], //运营监控总览数据
        	isChecked:true, //专区全部默认选中
        	count : 0,//复选框选中个数
        },
        methods:{
        	//页面初始化函数
            initData:function(){
            	var that = this;
            	//获取最新数据日期：取指标源表状态表里最新数据日期
            	that.getLastestDate(function(){
					that.initConfigData();
					that.initMonitorMain();
				});
            },
            //初始化日期
            getLastestDate:function(fn){
            	var that = this;
            	$.commAjax({
					url : $.ctx + "/api/source/TargetTableStatus/selectLastestDateByCycle",
					onSuccess : function(returnObj) {
						if(returnObj.data){
							that.firstDataDay = DateFmt.Formate(new Date(returnObj.data),"yyyy-MM-dd");
							that.firstDataMonth = DateFmt.Formate(new Date(returnObj.data),"yyyy-MM");
						}else{
							var now = new Date();
							that.firstDataDay = $.dateFormat(new Date(now.getTime() - 2*24*60*60*1000),"yyyy-MM-dd");
							that.firstDataMonth = $.dateFormat(new Date(now.getTime() - 2*24*60*60*1000),"yyyy-MM");
						}
						fn();
					}
            	});
            },
            //加载专区
            initConfigData:function(){
            	var that = this;
            	$.commAjax({
        			async : false,
        			url: $.ctx+"/api/prefecture/preConfigInfo/queryList",
        			postData:{"configStatus":1},
        			onSuccess:function(result){
        				if(result.data && result.data.length > 0){
        				    for(var i=0;i<result.data.length;i++){
        				   		result.data[i].isChecked=true;
        				    }
        				    that.configData = result.data;
        				}
        			}
        		});
            },
            //点击专区，动态刷新监控数据
            clickConfigData:function(item,event){
				var configId = item.configId;
				if(typeof item.isChecked=='undefined'){
					this.$set(item,"isChecked",true);
				}else{
					item.isChecked=!item.isChecked;
				}
				if(!item.isChecked){
					$("#"+configId+"monitor").hide();
					$("#allChecked").prop('checked',false)
				}else{
					monitorMain.count++;
					console.log($(event.target))
					if(monitorMain.count==monitorMain.configData.length){
						$("#allChecked").prop('checked',true)
					}
					$("#"+configId+"monitor").show();
				}
            },
            //加载监控数据
            initMonitorMain:function(){
            	var that = this;
            	$.commAjax({
                    url: $.ctx + "/api/monitor/overview/queryData",
                    postData : {
    					"dataDate" :that.firstDataDay.replace(/-/g,"")
                    },
                    onSuccess: function(returnObj){
                    	that.monitorData = returnObj.data;
                    }
                });
            },
            //周期切换
            getCycle:function(event){
            	var configId = $(event.target).attr("data-cycleId");
            	var that = this;
            	var dateElement=$(event.currentTarget).siblings(".control-input").find("input");
            	if(Number($(event.currentTarget).find("option:selected").val())=== 1){
            		dateElement.val(this.firstDataDay);
		    	}else{
		    		dateElement.val(this.firstDataMonth);
		    	}
        	  that.changeMonitorByDate(dateElement.val(),configId);
            },
           // 日期切换函数
           getTime:function(e){//初始化日期
        	    var sysMonth = 1 - $.getSysConfig('LOC_CONFIG_APP_MAX_KEEP_MONTHS');
		        var sysDay = 1 - $.getSysConfig('LOC_CONFIG_APP_MAX_KEEP_DAYS');
        	    var that = this;
	        	var configId = $(e.target).attr("data-cycleId");
	        	if(Number($(event.currentTarget).parents("div").siblings("select").find("option:selected").val()) === 1){
	        		var minDay ='#F{$dp.$DV(\''+ that.firstDataDay+'\',{d:'+sysDay+'});}';
	        		WdatePicker({
	        			isShowClear:false,
	        			dateFmt:'yyyy-MM-dd',
		    			maxDate:that.firstDataDay,
		    			minDate:minDay,
		    			onpicked: function(dq) {
		    				that.changeMonitorByDate(dq.cal.getNewDateStr(),configId);
		    			}
		    		});
	        	}else{
	        		var minMonth ='#F{$dp.$DV(\''+that.firstDataMonth+'\',{M:'+sysMonth+'});}';
	        		WdatePicker({
	        			isShowClear:false,
	        			dateFmt:'yyyy-MM',
		    			maxDate:that.firstDataMonth,
		    			minDate:minMonth,
		    			onpicked: function(dq) {
		    				that.changeMonitorByDate(dq.cal.getNewDateStr(),configId);
		    			}
		    		});
	        	}
            },
            //点击日期，刷新监控数据
            changeMonitorByDate:function(dataTime,configId){
            	var that = this;
            	$.commAjax({			
        		    url : $.ctx+'/api/monitor/overview/queryMonitorMainByConfig',
        		    isShowMask : true,
					maskMassage : '加载中...',
        		    dataType : 'json', 
        		    async:true,
        		    postData : {
        					"configId" :configId,
        					"dataDate" :dataTime.replace(/-/g,"")
        			},
        		    onSuccess: function(val){        		    	
        		    	var deleteIndex;
        		    	var deleteVal;
        		    	for(var i=0;i<that.monitorData.length;i++){
        		    		var value=that.monitorData[i];
        		    		var key=i;
        		    		if(value.configId ===  configId){
        		    			deleteIndex=key;
        		    			deleteVal=val.data;
        		    			break;
        		    		}
        		    	}
        		    	
        		    	if(typeof(deleteIndex) != "undefined"){
        		    		that.monitorData.splice(deleteIndex, 1,deleteVal);
        		    	}
        		    },
        	   });
            },
            //跳转到监控明细页面锚点位置
            jumpPageToDetail:function(detailAnchor,event){
            	var dataDate = $(event.target).parents("ul").siblings('div').find('input').val();
            	var selectConfigId = $(event.target).parents("ul").siblings('div').find('select').attr("data-cycleid");
            	var locationHref='service_monitor_detail.html?detailAnchor='+detailAnchor+'&configId='+selectConfigId+'&dataDate='+dataDate;
            	parent.toggleMenu("#serviceMonitor/service_monitor_detail",locationHref);
            },
            //选中与取消选中全部专区
            selectAllConfigs:function(e){
            	if($(event.target).prop('checked')){
            		$.each(this.configData,function(key,value){
                		$("#"+value.configId+"monitor").show();  
                		for(var i=0;i<monitorMain.configData.length;i++){
                			monitorMain.configData[i].isChecked=true;
                		}
                		
            		});
            	}else{
            		$.each(this.configData,function(key,value){
            			$("#"+value.configId+"monitor").hide();
            			for(var i=0;i<monitorMain.configData.length;i++){
                			monitorMain.configData[i].isChecked=false;
                		}
            		});
            	}
            }
        },
        mounted: function() {
            this.initData();
        }
    });

//    monitorMain.$nextTick(function () {
//    	monitorMain.sysDay = 1 - $.getSysConfig('LOC_CONFIG_APP_MAX_KEEP_DAYS');
//    	monitorMain.sysMonth = 1 - $.getSysConfig('LOC_CONFIG_APP_MAX_KEEP_MONTHS');
//    });
};


