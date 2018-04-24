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
        	dataDate:'',//日期
        	configData:[],//专区信息
        	monitorData:[] //运营监控总览数据
        },
        methods:{
            initData:function(){
//            	var now = new Date();
//            	this.dataDate = $.dateFormat(new Date(now.getTime() - 3*24*60*60*1000),"yyyy-MM-dd");
            	this.dataDate = "2018-03-27";
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
        				}
        			}
        		});
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
            initDateByCycle:function(e){
            	 //数据日期日周期显示范围是前三天
            	//数据日期月周期显示范围是一年
            },
            showDate:function(item){
            	if(typeof item.isOpen=='undefined'){
            		this.$set(item,"isOpen",true)
            	}else{
            		item.isOpen=!item.isOpen;
            	}
            },
             dateToggle:function(item){
            	if(typeof item.isActive=='undefined'){
            		this.$set(item,"isActive",true)
            	}else{
            		item.isActive=!item.isActive;
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
        		    	$.each(that.monitorData,function(key,value){
        		    		//TODO 先删除
        		    		debugger
        		    		console.log(value);
        		    	});
//        		    	that.monitorData.push(data.serviceMonitorObj);
        		    },
        	   });
            }
        },
        mounted: function() {
            this.initData();
        }
    });
  //监测v-for动态生成模板之后,获取dom
	 monitorMain.$nextTick(function() {
//		if(monitorDetail.labelMonth){
//			$("#labelMonth").click(function(){
//	    		WdatePicker({isShowClear:false,dateFmt:'yyyy-MM',
//	    			maxDate:monitorDetail.labelMonth,minDate:'#F{$dp.$DV(\''+monitorDetail.labelMonth+'\',{y:-1});}'});
//	    	});
//		}
//		if(monitorDetail.labelDay){
//	    	$("#labelDay").click(function(){
//	    		WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd',
//	    			maxDate:monitorDetail.labelDay,minDate:'#F{$dp.$DV(\''+monitorDetail.labelDay+'\',{d:-2});}'});
//	    	});
//		}
	 });
   
};