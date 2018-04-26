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
        	dateCycle:"day",//周期默认为日周期
        	dataDate:'',//日期
        	configData:[],//专区信息
        	monitorData:[], //运营监控总览数据
        	isChecked:true,
        },
        methods:{
        	//页面初始化函数
            initData:function(){
            	var now = new Date();
            	this.dataDate = $.dateFormat(new Date(now.getTime() - 3*24*60*60*1000),"yyyy-MM-dd");
            	this.initConfigData();
            	this.initMonitorMain();
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
        				   //默认全部专区选中状态
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
    					"dataDate" :that.dataDate.replace(/-/g,"")
                    },
                    onSuccess: function(returnObj){
                    	that.monitorData = returnObj.data;
                    }
                });
            },
            //点击周期，初始化日期
//          initDateByCycle:function(dateCycle,item,event){
//          	item.isOpen=false;
//          	var dateCycle=$(event.currentTarget).text();
//          	$(event.currentTarget).parents(".dropdown").find("span.pre-cycle-name").text(dateCycle);
//          	this.dateCycle = dateCycle;
//          	var now = new Date();
//          	var dataDay = $.dateFormat(new Date(now.getTime() - 3*24*60*60*1000),"yyyy-MM-dd");
//          	var dataMonth = $.dateFormat(new Date(now.getTime() - 3*24*60*60*1000),"yyyy-MM");
//          	if("day" === this.dateCycle){
//          		$(this).parents("div.dropdown").siblings("div.control-input").children("input").html(dataDay);
//          	}else{
//          		$(this).parents("div.dropdown").siblings("div.control-input").children("input").html(dataMonth);
//          	}
//          },
//          dateToggle:function(item){
//          	var nowDate = $.dateFormat(new Date(),"yyyy-MM-dd");
//          	var nowMonth = $.dateFormat(new Date(),"yyyy-MM");
//          	if("day" === this.dateCycle){
//  	    		//数据日期日周期显示范围是前三天 	
//  	    		WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',
//  	    			maxDate:nowDate,minDate:'#F{$dp.$DV(\''+nowDate+'\',{d:-3});}'});
//          	}else{
//              	//数据日期月周期显示范围是一年
//  	    		WdatePicker({isShowClear:false,dateFmt:'yyyy-MM',
//  	    			maxDate:nowMonth,minDate:'#F{$dp.$DV(\''+nowMonth+'\',{y:-12});}'});
//          	}
//          },
            getCycle:function(event){
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
            //点击日期，刷新监控数据
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
        					"configId" :configId,
        					"dataDate" :dataDate.replace(/-/g,"")
        			},
        		    onSuccess: function(data){
        		    	$.each(that.monitorData, function (key, value) {
        		    		if (value && (value.configId ==  configId )) {
        		    			that.monitorData.splice(key, 1);
        		    			return;
        		    		}
        		    	});
        		    	that.monitorData.push(data);
        		    },
        	   });
            },
            //跳转到监控明细页面锚点位置
            jumpPageToDetail:function(detailAnchor){
            	parent.toggleMenu("#serviceMonitor/service_monitor_detail");
            	window.location='service_monitor_detail.html?detailAnchor='+detailAnchor;
            }
        },
        mounted: function() {
            this.initData();
        }
    });
};
function getTime(element){
	$(element).datepicker();
}