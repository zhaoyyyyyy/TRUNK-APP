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
        	showConfigIds:[]//页面选中要显示的专区数据
        	
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
        				   that.configData = result.data;
        				   //默认全部专区选中状态
        				   $.each(result.data,function(key,value){
        					   that.showConfigIds.push(value.configId);
        				   });
        				   console.log(that.showConfigIds);
        				}
        			}
        		});
            },
            clickConfigData:function(item){//点击专区，动态刷新监控数据
				var configId = item.configId;
            	if($(this).is(":checked")){
            		$("#"+configId).hide();
            	}else{
            		$("#"+configId).show();
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
            initDateByCycle:function(dateCycle){
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