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
            initData:function(){
            	var now = new Date();
            	this.dataDate = $.dateFormat(new Date(now.getTime() - 3*24*60*60*1000),"yyyy-MM-dd");
            	this.initConfigData();
            	this.initMonitorMain();
            },
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
            clickConfigData:function(item){//点击专区，动态刷新监控数据
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
            initDateByCycle:function(dateCycle,item,event){
            	item.isOpen=false;
            	var dateCycle=$(event.currentTarget).text();
            	$(event.currentTarget).parents(".dropdown").find("span.pre-cycle-name").text(dateCycle);
            	this.dateCycle = dateCycle;
            	var now = new Date();
            	var dataDay = $.dateFormat(new Date(now.getTime() - 3*24*60*60*1000),"yyyy-MM-dd");
            	var dataMonth = $.dateFormat(new Date(now.getTime() - 3*24*60*60*1000),"yyyy-MM");
            	if("day" === this.dateCycle){
            		$(this).parents("div.dropdown").siblings("div.control-input").children("input").html(dataDay);
            	}else{
            		$(this).parents("div.dropdown").siblings("div.control-input").children("input").html(dataMonth);
            	}
            },
            showDate:function(item){
            	if(typeof item.isOpen=='undefined'){
            		this.$set(item,"isOpen",true);
            	}else{
            		item.isOpen=!item.isOpen;
            	}
            },
             dateToggle:function(item){
            	/*if(typeof item.isActive=='undefined'){
            		this.$set(item,"isActive",true);
            	}else{
            		item.isActive=!item.isActive;
            	}*/
            	 
            	var nowDate = $.dateFormat(new Date(),"yyyy-MM-dd");
            	var nowMonth = $.dateFormat(new Date(),"yyyy-MM");
            	if("day" === this.dateCycle){
    	    		//数据日期日周期显示范围是前三天 	
    	    		WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',
    	    			maxDate:nowDate,minDate:'#F{$dp.$DV(\''+nowDate+'\',{d:-3});}'});
            	}else{
                	//数据日期月周期显示范围是一年
    	    		WdatePicker({isShowClear:false,dateFmt:'yyyy-MM',
    	    			maxDate:nowMonth,minDate:'#F{$dp.$DV(\''+nowMonth+'\',{y:-12});}'});
            	}
            },
            getData:function(event,item){
            	var cycle=$(event.currentTarget).parents(".ui-prefecture").find("span.pre-cycle-name").text();
            	if(cycle=="日周期"){
            		$(event.currentTarget).datepicker({
				  		changeMonth: true,
				  		changeYear: true,
				  		dateFormat:"yy-mm-dd",
				  		dayNamesMin: [ "日", "一", "二", "三", "四", "五", "六" ],
				  		monthNamesShort: [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" ]
				  	});
            	}
            	
            },
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
            }
        },
        mounted: function() {
            this.initData();
        
  	
        }
    });
	
};
