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
        	firstDataDate:'',//最新日期
        	sysDay:'',//系统设置日周期范围
        	sysMonth:'',//系统设置月周期范围
        	configData:[],//专区信息
        	monitorData:[], //运营监控总览数据
        	isChecked:true //专区全部默认选中
        },
        methods:{
        	//页面初始化函数
            initData:function(){
            	var that = this;
            	this.sysDay = 1 - $.getSysConfig('LOC_CONFIG_APP_MAX_KEEP_DAYS');
        	    this.sysMonth = 1 - $.getSysConfig('LOC_CONFIG_APP_MAX_KEEP_MONTHS');
            	//获取最新数据日期：取指标源表状态表里最新数据日期
				this.getLastestDate(function(){
					that.initConfigData();
					that.initMonitorMain();
				});
            },
            //初始化日期
            getLastestDate:function(fn){
            	var that = this;
            	$.commAjax({
					url : $.ctx + "/api/source/TargetTableStatus/selectLastestDateByCycle",
					postData : {
						readCycle : that.readCycle
					},
					onSuccess : function(returnObj) {
						if(returnObj.data){
							that.firstDataDate = returnObj.data;
						}else{
							var now = new Date();
							that.firstDataDate = $.dateFormat(new Date(now.getTime() - 2*24*60*60*1000),"yyyy-MM-dd");
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
            clickConfigData:function(item){
				var configId = item.configId;
				if(typeof item.isChecked=='undefined'){
					this.$set(item,"isChecked",true);
				}else{
					item.isChecked=!item.isChecked;
				}
				if(!item.isChecked){
					$("#"+configId+"monitor").hide();
				}else{
					$("#"+configId+"monitor").show();
				}
            },
            //加载监控数据
            initMonitorMain:function(){
            	var that = this;
            	$.commAjax({
                    url: $.ctx + "/api/monitor/overview/queryData",
                    postData : {
    					"dataDate" :that.firstDataDate.replace(/-/g,"")
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
            	if($(event.currentTarget).find("option:selected").val()=== 1){
		    		dateElement.val(DateFmt.Formate(new Date(),"yyyy-MM-dd")).datepicker( "destroy" ).datepicker({
		    			dateFormat: "yy-mm-dd",
						showButtonPanel: false,
						changeMonth: false,
      					changeYear: false,
      					defaultDate:+1,
      					minDate:DateFmt.DateCalc(new Date(),"d",this.sysDay),
		    			maxDate:DateFmt.DateCalc(new Date(),"d",0),
						beforeShow :function(){
			    			$("#ui-datepicker-div").removeClass("ui-hide-calendar");
			    		}
		    		}).off("click");
		    	}else{
		    		dateElement.val(DateFmt.Formate(new Date(),"yyyy-MM")).datepicker( "destroy" ).datepicker({
            			dateFormat: "yyyy-MM",
						showButtonPanel: true,
						closeText:"确定" ,
						changeMonth: true,
      					changeYear: true,
      					minDate:DateFmt.DateCalc(new Date(),"M",this.sysMonth),
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
        	  that.changeMonitorByDate(dateElement.val(),configId);
            },
           // 日期切换函数
           getTime:function(e){//初始化日期
        	   var that = this;
	        	var configId = $(e.target).attr("data-cycleId");
	        	if(Number($(event.currentTarget).parents("div").siblings("select").find("option:selected").val()) === 1){
	        		$(e.target).datepicker({
		    			dateFormat: "yy-mm-dd",
						showButtonPanel: false,
						changeMonth: false,
      					changeYear: false,
      					defaultDate:+1,
      					minDate:DateFmt.DateCalc(new Date(),"d",that.sysDay),
		    			maxDate:DateFmt.DateCalc(new Date(),"d",0),
						beforeShow :function(){
			    			$("#ui-datepicker-div").removeClass("ui-hide-calendar");
			    		},
			    		onClose:function(dateText, inst){
			    			that.changeMonitorByDate(dateText,configId);
			    		}
		    		});
	        	}else{
	        		$(e.target).datepicker({
	        			isShowClear:false,
            			dateFormat: "yyyy-MM",
						showButtonPanel: true,
						closeText:"确定" ,
						changeMonth: true,
      					changeYear: true,
      					minDate:DateFmt.DateCalc(new Date(),"M",that.sysMonth),
		    			maxDate:DateFmt.DateCalc(new Date(),"M",0),
						beforeShow :function(){
			    			$("#ui-datepicker-div").addClass("ui-hide-calendar");
			    		},
			    		onClose: function(dateText, inst) {
			    		    that.changeMonitorByDate(dateText,configId);
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
        		    	
        		    	if(deleteIndex){
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
            }
        },
        mounted: function() {
            this.initData();
        }
    });

    //全部选中
	$("#selectAllConfigs").click(function(){	
		if($(this).hasClass("active")){
			$(this).removeClass("active");
			$(".ui-lc-mian mt10 ul li").find("input").each(function(){
				$(this).prop("checked", false);
				$(this).siblings("label").removeClass("active");
			});
		}else{
			$(this).addClass("active");
			$(".ui-lc-mian mt10 ul li").find("input").each(function(){
				$(this).prop("checked", true);
				$(this).siblings("label").addClass("active");
			});
		}
		
	});
};
