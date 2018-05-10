window.loc_onload = function() {
		$("#person_notice").click(function(){
			if(($.ctx+'/aibi_lc/pages/home/personNotice.html') != window.location.pathname){
				window.location.href = $.ctx+'/aibi_lc/pages/home/personNotice.html';
			}
		})
		$("#sys_announcement").click(function(){
			if (($.ctx+'/aibi_lc/pages/home/sysAnnouncement.html') != window.location.pathname){
				window.location.href = $.ctx+'/aibi_lc/pages/home/sysAnnouncement.html';
			}
		})
		$("#announcement_mgr").click(function(){
			if (($.ctx+'/aibi_lc/pages/home/sysAnnouncementMgr.html') != window.location.pathname){
				window.location.href = $.ctx+'/aibi_lc/pages/home/sysAnnouncementMgr.html';
			}
		})
		$( "#sortable > .ui-conditionCT,.ui-calc-h3>span>em" ).draggable({
			helper: function( event ) {
				if($(event.target).hasClass("J-drag-bracket")){
	       	 	   return $(event.target).attr("data-attr") == "left"?$( '<span class="ui-bracket left">(</span>' ):$( '<span class="ui-bracket left">)</span>' );
				}
	       	  return $( '<h4 class="ui-conditionCT-h4 ui-conditionCT-h4-helper">2G数据流量<em></em><i></i></h4>' );
	    	},
	    	cursor: "crosshair",
	    	start:function(event,ui){
	      	  $(".ui-chaining").hide();
	      	  var items = $("#sortable > .ui-conditionCT");
	      	  var calc= '<div class="ui-state-highlight ui-sortablr-helper J-helper"></div>';
	  		  $(items).after(calc);
	  		  if($("#sortable > .ui-bracket.left").prev().length == 0){
		      	  $("#sortable > .ui-bracket.left").before(calc);
	  		  }
	      	  $("#sortable > .ui-bracket").after(calc);
	  		  $(".J-helper").droppable({
		  	  	   hoverClass: "ui-drop-highlight",
		  		   greedy:true,
		  		   drop: function( event, ui ) {
		  			   var onDragTag=ui.draggable;
		  			   if(onDragTag.hasClass("J-drag-bracket")){
		  				 onDragTag = onDragTag.attr("data-attr") == "left"?'<span class="ui-bracket left">(</span>':'<span class="ui-bracket right">)<i></i></span>'
		  				 $(this).after(onDragTag);
		  				 return;
		  			   }
		  		        $(this).after(onDragTag);
		  		        var chains = $("#sortable > .ui-chaining");
		  		     	var CTitems = $("#sortable > .ui-conditionCT");
		  		        for(var i =0,len = chains.length;i<len;i++){
		  		        		$(CTitems[i]).after(chains[i]);
		  		        }
		  	       },
		  	       create:function( event, ui){
		  		        console.log(ui);
		  	       }
		  	   });
	        },
	        stop:function(event,ui){
	      	  $(".ui-chaining").show();
	      	  $(".J-helper").remove();
	        }
		});
		$(".ui-shop-cart").click(function(){
			$(".ui-calculate-center").addClass("heightAuto");
		});
		$(".J-min").click(function(){
			$(".ui-calculate-center").removeClass("heightAuto");
		});
		 $("#jsonmap1").jqGrid({
			url:$.ctx+'/api/message/locSysAnnouncement/queryPage',
            datatype: "json",
            postData: {
    			/*"announcementDetail" : $("#txt_name").val(),
    			"announcementName" : $("#txt_name").val()*/
    		},
            colNames:['公告名称','发布时间','公告内容','公告id','阅读状态','名称无格式'],
            colModel:[
                {name:'announcementName',index:'announcementName', width:30, sortable:true,frozen : false ,align:"center",formatter:alarm},//frozen : true固定列 ,formatter:alarm
                {name:'releaseDate',index:'releaseDate', width:60, align:"center",formatter:formatDate},
                {name:'announcementDetail',index:'announcementDetail', align:"center",hidden:true},
                {name:'announcementId',index:'announcementId', align:"center",hidden:true},
                {name:'readStatus',index:'readStatus', align:"center",hidden:true},
                {name:'announcementName',index:'announcementNameNull', align:"center",hidden:true},
            ],
            rowNum:10,
            rowList:[10,20,30],
            pager: '#pjmap1',//分页的id
            sortname: '',//排序的字段名称 不需要的话可置为空  取值取自colModel中的index字段
            viewrecords: true,
            multiselect:false,
            title:false,
            rownumbers:false,//是否展示行号
            sortorder: "desc",//排序方式
            onSelectRow:function(rowid){//选中行时操作
            	var a = $("#jsonmap1").getRowData(rowid);
            	if (a.readStatus == 2) {
            		$.commAjax({
            			type: 'POST',
            			url: $.ctx+'/api/message/locSysAnnouncement/read',
            			postData : {
            				'announcementId': a.announcementId,
            			},
            			onSuccess: function(){
            				$("#unreadSize").text($("#unreadSize").text()-1);
            				//$("#"+rowid).find("span.info-bold").removeAttr("class");//不加粗
            				$("#jsonmap1").setCell(rowid,"readStatus",1);
            				$("#jsonmap1").setCell(rowid,"announcementName",1);//有fomatter在  此处修改的参数无效
            			}
            		});
            	}
            	$("#announcementName_right").text(a.announcementName);
            	$("#releaseDate_right").text(a.releaseDate);
            	$("#announcementDetail_right").text(a.announcementDetail);
            },
            
            jsonReader: {
                repeatitems : false,
                id: "0"
            },
            height: '100%',
            width:"100%",
            autowidth:true,
            gridComplete: function () {
                $(this).closest('.ui-jqgrid-view').find('div.ui-jqgrid-hdiv').hide();
                var a = $("#jsonmap1").getRowData(1);
            	$("#announcementName_right").text(a.announcementName);
            	$("#releaseDate_right").text(a.releaseDate);
            	$("#announcementDetail_right").text(a.announcementDetail);
            	
             },
             loadComplete:function(data){

         		var _self = this;
         		var thisId = $(_self).attr("id");
         		var opts = _self.p;
         		
         		console.log(data)
         		var pageBox = '<div class="clearfix"><div class="fleft totalPage"><span class="fleft">共'+data.totalCount+'条记录，'+data.totalPageCount+'页，当前第'+data.currentPageNo+'页</span></div><div id="'+thisId+'_pager" class="tcdPageCode"></div></div>';
         		$(opts.pager).html(pageBox);
         		$(opts.pager).find("#"+thisId+"_pager").createPage({
         	        pageCount:data.totalPageCount,
         	        current: data.currentPageNo,
         	        gridId:thisId,
         	        backFn:function(current){
         	            $(_self).jqGrid('setGridParam',{    
         	                page:current   
         	            }).trigger("reloadGrid");  
         	        }
         	   });
         		var rowList = opts.rowList;
         		var rowNum = $(_self).jqGrid('getGridParam',"rowNum");
         		for(var i=0,len = rowList.length;i<len;i++){
         			if(rowNum == rowList[i]){
         				$("#"+thisId+"_listbox").append('<option role="option" value="'+rowList[i]+'" selected="selected">'+rowList[i]+'</option>');
         			}else{
         				$("#"+thisId+"_listbox").append('<option role="option" value="'+rowList[i]+'">'+rowList[i]+'</option>');
         			}
         		}
         		$('.grid-nodata-box').remove();
         		//showNoResult为true时调用展示无结果图片
         		if(opts.showNoResult){
         			if(data.status !='200' || (data.status=='200' && data.data.length<=0) || (data.status=='200' && !data.data)){
         				var message = data.message ? data.message : "暂无数据&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
         				$('.grid-nodata-box').remove();
         				$(opts.pager).before('<div class="grid-nodata-box"><p class="no-data-icon"></p><div class="no-data-msg text-center color-666">'+message+'</div></div>');
         			}
         		}
         		opts.afterGridLoad(data);
         		$("#totalSize").text("全部："+data.totalCount)
         		$("#unreadSize").text(data.rows[0].unread);
             }
        });
		 
		function formatDate(cellvalue, options, rowObject){
	            return getSmpFormatDateByLong(rowObject.releaseDate,true);
	        }
        function alarm(cellvalue, options, rowObject){
           // var html='<a><em class="label-store ui-news-alarm"></em>' +rowObject.announcementName+ '</a>';
        	var html = "";
        	if (rowObject.readStatus == 2) {
        		html = '<span class="info-bold">'+rowObject.announcementName+'</span>';
        	} else if (rowObject[4] == 1){
        		html = rowObject[5];
        	} else {
        		html = rowObject.announcementName ; 
        	}
            return html;
        }
	}


//搜索
function search(){
	$("#jsonmap1").setGridParam( //G,P要大写
			{
				url:$.ctx+'/api/message/locSysAnnouncement/queryPage',
				postData: {
					"announcementDetail" : $("#txt_name").val(),
					"announcementName" : $("#txt_name").val()
				},
			}
	) .trigger("reloadGrid");
}


//扩展Date的format方法   
Date.prototype.format = function (format) {  
    var o = {  
        "M+": this.getMonth() + 1,  
        "d+": this.getDate(),  
        "h+": this.getHours(),  
        "m+": this.getMinutes(),  
        "s+": this.getSeconds(),  
        "q+": Math.floor((this.getMonth() + 3) / 3),  
        "S": this.getMilliseconds()  
    }  
    if (/(y+)/.test(format)) {  
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));  
    }  
    for (var k in o) {  
        if (new RegExp("(" + k + ")").test(format)) {  
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));  
        }  
    }  
    return format;  
}  
/**   
*转换日期对象为日期字符串   
* @param date 日期对象   
* @param isFull 是否为完整的日期数据,   
*               为true时, 格式如"2000-03-05 01:05:04"   
*               为false时, 格式如 "2000-03-05"   
* @return 符合要求的日期字符串   
*/    
function getSmpFormatDate(date, isFull) {  
    var pattern = "";  
    if (isFull == true || isFull == undefined) {  
        pattern = "yyyy-MM-dd hh:mm:ss";  
    } else {  
        pattern = "yyyy-MM-dd";  
    }  
    return getFormatDate(date, pattern);  
}  
/**   
*转换当前日期对象为日期字符串   
* @param date 日期对象   
* @param isFull 是否为完整的日期数据,   
*               为true时, 格式如"2000-03-05 01:05:04"   
*               为false时, 格式如 "2000-03-05"   
* @return 符合要求的日期字符串   
*/    

function getSmpFormatNowDate(isFull) {  
    return getSmpFormatDate(new Date(), isFull);  
}  
/**   
*转换long值为日期字符串   
* @param l long值   
* @param isFull 是否为完整的日期数据,   
*               为true时, 格式如"2000-03-05 01:05:04"   
*               为false时, 格式如 "2000-03-05"   
* @return 符合要求的日期字符串   
*/    

function getSmpFormatDateByLong(l, isFull) {  
    return getSmpFormatDate(new Date(l), isFull);  
}  
/**   
*转换long值为日期字符串   
* @param l long值   
* @param pattern 格式字符串,例如：yyyy-MM-dd hh:mm:ss   
* @return 符合要求的日期字符串   
*/    

function getFormatDateByLong(l, pattern) {  
    return getFormatDate(new Date(l), pattern);  
}  
/**   
*转换日期对象为日期字符串   
* @param l long值   
* @param pattern 格式字符串,例如：yyyy-MM-dd hh:mm:ss   
* @return 符合要求的日期字符串   
*/    
function getFormatDate(date, pattern) {  
    if (date == undefined) {  
        date = new Date();  
    }  
    if (pattern == undefined) {  
        pattern = "yyyy-MM-dd hh:mm:ss";  
    }  
    return date.format(pattern);  
}  









