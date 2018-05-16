window.loc_onload = function() {
		$("#del_btn").click(function(){
			var idArr = $("#jsonmap1").jqGrid('getGridParam','selarrrow');
			if (idArr.length>0){
				var ids = "";
				for(i in idArr){
					var a = $("#jsonmap1").getRowData(idArr[i]);
					if (i == idArr.length-1){
						ids += ('\''+a.announcementId+'\'');
					} else {
						ids += ('\''+a.announcementId+'\',');
					}
				}
				$.confirm("确定删除所选公告吗？", function() {
					$.commAjax({
						type: 'POST',
						url: $.ctx+'/api/message/locSysAnnouncement/delete',
						postData : {
							'ids': ids,
						},
						onSuccess: function(data){
							if (data.data == "success") {
								$("#jsonmap1").trigger("reloadGrid", [ {
									page : 1
								} ]);
							}
						}
					});
				});
			}
		})
		$("#add_btn").click(function(){
	    	var wd = $.window('新增系统公告', $.ctx
				+ '/aibi_lc/pages/home/sysAnnouncement_add.html', 800, 600);
	    	wd.reload = function() {
	    		$("#jsonmap1").trigger("reloadGrid", [ {
					page : 1
				} ]);
	    	}
		})
		
		
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
            colNames:['公告id','标题','内容', '类型', '发布日期','有效截止日期','优先级','公告状态','操作'],
            colModel:[
                {name:'announcementId',index:'announcementId', align:"center",hidden:true},
                {name:'announcementName',index:'announcementName', width:100, sortable:true,frozen : true ,align:"center",formatter:alarm},//frozen : true固定列
                {name:'announcementDetail',index:'announcementDetail', width:100,align:"center"},
                {name:'typeId',index:'typeId',sortable:false, width:60,align:"center",
                	formatter: function(v) {
                		return $.getCodeDesc('GGLX', v)
                	}
                },
                {name:'releaseDate',index:'releaseDate', width:80,align:"center",formatter:formatReleaseDate},
                {name:'effectiveTime',index:'effectiveTime', width:80,align:"center",formatter:formatEffectiveDate},
                {name:'priorityId',index:'priorityId', width:50,align:"center",
                	formatter: function(v) {
                		return $.getCodeDesc('GGYXJ', v)
                	}
                },
                {name:'status',index:'status', width:50,align:"center",
                	formatter: function(v) {
                		return $.getCodeDesc('GGZT', v)
                	}
                },
                {name:'op',index:'op', width:50, sortable:false,formatter:del,align:"center"}
            ],
            postData: {
				"announcementName" : $("#txt_name").val()
			},
            multiselect: true,
            multiboxonly: true,
            rowNum:10,
            rowList:[10,20,30],
            pager: '#pjmap1',//分页的id
            sortname: '',//排序的字段名称 不需要的话可置为空  取值取自colModel中的index字段
            viewrecords: true,
            rownumbers:false,//是否展示行号
            sortorder: "desc",//排序方式
            jsonReader: {
                repeatitems : false,
                id: "0"
            },
            height: '100%',
            width:"100%",
            autowidth:true,
        });
		function formatReleaseDate(cellvalue, options, rowObject){
	            return getSmpFormatDateByLong(rowObject.releaseDate,true);
        }
		function formatEffectiveDate(cellvalue, options, rowObject){
            return getSmpFormatDateByLong(rowObject.effectiveTime,true);
    }
		function alarm(cellvalue, options, rowObject){
			var html="";
			if (rowObject.priorityId == 0){
				html='<a><em class="label-store ui-news-alarm"></em>' +rowObject.announcementName+ '</a>';
			} else {
				html = rowObject.announcementName;
			}
            return html;
        }
        function del(cellvalue, options, rowObject){
            var html='<button onclick="deleteSysAnouncement(\''+rowObject.announcementId+'\')" type="button" class="btn btn-default  ui-table-btn ui-table-btn"><em  class="label-store ui-news-del"></em>删除</button>';
            return html;
        }
	};
	
	//搜索
	function search(){
		$("#jsonmap1").setGridParam( //G,P要大写
				{
					url:$.ctx+'/api/message/locSysAnnouncement/queryPage',
					postData: {
						"announcementName" : $("#txt_name").val()
					},
				}
		) .trigger("reloadGrid", [ {
			page : 1
		} ]);
	}
	
	//删除公告	
	function deleteSysAnouncement(obj){
		$.confirm("确定删除该公告吗？", function() {
			$.commAjax({
				type: 'POST',
				url: $.ctx+'/api/message/locSysAnnouncement/delete',
				postData : {
					'announcementId': obj,
				},
				onSuccess: function(data){
					if (data.data == "success") {
						$("#jsonmap1").trigger("reloadGrid", [ {
							page : 1
						} ]);
					}
				}
			});
		});
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