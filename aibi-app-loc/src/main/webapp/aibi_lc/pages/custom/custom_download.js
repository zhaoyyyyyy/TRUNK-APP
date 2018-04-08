var VueModel ={
	customDownloadRecord:{
	    /**
	     * 数据状态：1:未生成，2：生成中，3：已生成，4：失败
	     */
	    DATA_STATUS_WAIT:1,
	    DATA_STATUS_DOING:2,
	    DATA_STATUS_SUCCESS:3,
	    DATA_STATUS_FAILED:4,
	},
	customGroupId:'',
	AttrbuteId:'',
	sortAttrAndType:'',
	dataDate:'',
	downloadColNames:[ '文件名', '数据日期', '下载次数','数据状态', '操作' ],
	downloadColModel:[{
		name : 'fileName',
		index : 'fileName',
		width : 60,
		align : 'left'
	},{
		name : 'dataDate',
		index : 'dataDate',
		width : 20,
		align : 'center'
	},{
		name : 'downloadNum',
		index : 'downloadNum',
		width : 20,
		align : 'center'
	},{
		name : 'dataStatus',
		index : 'dataStatus',
		hidden:true
	},{
		name : 'recordId',
		index : 'recordId',
		width : 100,
		fixed : true,
		align : 'center',
		sortable : false,
		formatter : function(value, opts, data) {
			var res = "";
			if (data.dataStatus == VueModel.customDownloadRecord.DATA_STATUS_WAIT) {//未生成
				res = "<a href='javascript:void(0);' class='s_edit' onclick='preDownloadGroupList(this);' style='color:#00f;'>生成文件</a>";
			} else if (data.dataStatus == VueModel.customDownloadRecord.DATA_STATUS_DOING) {//生成中
				res = "生成中";
			} else if (data.dataStatus == VueModel.customDownloadRecord.DATA_STATUS_SUCCESS) {//已生成
				res = "<a href='javascript:downloadGroupList(\"" + data.recordId + "\")' class='s_export' style='color:#00f;'>下载</a>";
			} else if (data.dataStatus == VueModel.customDownloadRecord.DATA_STATUS_FAILED) {//失败
				res = "<span class='s_delete' >生成文件失败</span>";
			}
			
			return res;
		}
	}],
	loadGrid:function(RowData){
		$("#downloadGrid").jqGrid({
			postData : {
				'customId':VueModel.customGroupId
			},
			url : $.ctx + '/api/syspush/customDownloadRecord/queryPage',
			colNames : VueModel.downloadColNames,
			colModel : VueModel.downloadColModel,
			rownumbers : true,
			autowidth : true,
			viewrecords : true,
			pager : '#downloadGridPager',
            loadComplete:function(){  
	            	var grid = $("#downloadGrid");
		        var rows = grid.jqGrid('getRowData'); //获取当前显示的记录
            		//若此客户群未生成，则显示
		        if (rows.length == 0) {
		        		grid.jqGrid("addRowData",0,RowData);
		        }
			}
		});
    	 	$("#downloadGrid").jqGrid('setLabel',0, '序号');
	}
};
window.loc_onload = function() {
	var wd = frameElement.lhgDG;
	VueModel.customGroupId=$.getUrlParam("customGroupId");
	VueModel.AttrbuteId=$.getUrlParam("AttrbuteId");
	VueModel.sortAttrAndType=$.getUrlParam("sortAttrAndType");
	VueModel.dataDate=$.getUrlParam("dataDate");
	
	$("#alertDialog").dialog({
	    autoOpen: false,
	    title:"推送设置",
	    modal: true,
	    open:function(){
	      	$(".form-horizontal").show();
	    }
    });
	
	new Vue({
	  	el : '#downloadDialog',
	  	data:VueModel,
		mounted: function () {
		    this.$nextTick(function () {
		    		this.initDownloadView();
		    });
		},
		methods:{
			initDownloadView:function(){
				VueModel.loadGrid({
					fileName:'未知',
					dataDate:VueModel.dataDate,
					downloadNum:'',
					dataStatus:VueModel.customDownloadRecord.DATA_STATUS_WAIT,
					recordId:VueModel.customDownloadRecord.DATA_STATUS_WAIT
                });
			}
		}
	});
	
	/* 清单生成*/
	preDownloadGroupList = function(el){
		$(el).parent().attr("title","生成中").append("<span>生成中</span>");
		$(el).remove();
		
		$.commAjax({			
		    url : $.ctx+'/api/syspush/labelPushCycle/preDownloadGroupList',
		    postData : {
				"customGroupId":VueModel.customGroupId,
				"AttrbuteId":VueModel.AttrbuteId,
				"sortAttrAndType":VueModel.sortAttrAndType,
			},
		    maskMassage : '生成中...',
		    onSuccess: function(dataJson){
				if (dataJson.status==200) {
					$("#alertDialog").dialog("close");
					if ($("#alertDialog")) $("#alertDialog").hide();
					VueModel.localPathFile = dataJson.data;

			    		//刷表格
			    		$("#downloadGrid").setGridParam({
			    			postData:{
							'customId':VueModel.customGroupId
			    			}
			    		}).trigger("reloadGrid", [{page:1}]);
				}
		    }
		});
	}
	/* 清单下载*/
	downloadGroupList = function(id){
	    	$.commAjax({
	    		url:$.ctx + '/api/syspush/customDownloadRecord/updateDownloadNum',
	    		postData:{
	    			'recordId':id
	    		},
			isShowMask	: true,
		    maskMassage : '下载中...',
	    		onSuccess:function(dataJson){
				if (dataJson.status==200) {
			    		//刷表格
			    		$("#downloadGrid").setGridParam({
			    			postData:{
							'customId':VueModel.customGroupId
			    			}
			    		}).trigger("reloadGrid", [{page:1}]);
					
		    			//模拟form提交下载文件
		    			var url = $.ctx+'/api/syspush/customDownloadRecord/downloadGroupList';
		    			var form = $("<form></form>").attr("action",url).attr("method", "post");
		    			form.append($("<input></input>").attr("type","hidden").attr("name","token").attr("value",$.getToken()));
		    			form.append($("<input></input>").attr("type","hidden").attr("name","localPathFile").attr("value",VueModel.localPathFile));
		    			form.append($("<input></input>").attr("type","hidden").attr("name","recordId").attr("value",id));
		    			form.appendTo('body').submit().remove();
				}
	    		}
		},$('#customDownloadWin'));
	}
};