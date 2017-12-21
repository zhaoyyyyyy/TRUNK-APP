var token = "";
try{
	token = $.getToken();
}catch(e){}
jQuery.extend(jQuery.jgrid.defaults, {        
   	url:'./grid.json',
	datatype: "json",
	mtype:"POST",
   	colNames:[ ],
   	colModel:[ ],
   	rowNum:10,
   	rowList:[],
   	pager: '#pager_'+Math.random()*100,
   	sortname: 'id',
    //viewrecords: true,
    sortorder: "desc",
	jsonReader: {
		repeatitems : false,
		id: "0"
	},
	prmNames: {
		page:"pageStart",
		rows:"pageSize", 
		sort: "sortOrder",
		order: "sortOrder", 
		search:"_search", 
		nd:"nd", 
		id:"id",
		oper:"oper",
		editoper:"edit",
		addoper:"add",
		deloper:"del", 
		subgridid:"id", 
		npage: null, 
		totalrows:"totalCount"
	},
	ajaxGridOptions:{
		headers:{
			'X-Authorization':token,
			'Access-Control-Allow-Origin': '*',
			"Content-Type": "application/x-www-form-urlencoded;charset=utf-8"
		},
		beforeSend :function(){
			$("#load_"+$(this).attr("id")).show();
		}
    },
	height: '100%',
	multiselect:false,
	setGroupHeaders:false,
	autowidth:true,
	rownumbers:true,
	showNoResult:false,//是否展示无数据时的样式
	afterGridLoad:function(){
		
	},
	loadError:function(xhr,status,error){
		 if(xhr.status == 401){
			   alert("会话已过期，请重新登录！");
		   }
	},
	loadBeforeSend:function(){
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
	},
	gridComplete:function(){
		
		var _self = this;
		var thisId = $(_self).attr("id");
		var opts = _self.p;
//		var pgselbox = $(opts.pager).find(".ui-pg-selbox");
//		$("#"+thisId+"_listbox").html(pgselbox);
		if($(".frozen-bdiv.ui-jqgrid-bdiv").css("top")){
			$(".frozen-bdiv.ui-jqgrid-bdiv").css("top",$(".frozen-bdiv.ui-jqgrid-bdiv").css("top").replace(/px/,"")-1);
		}
		$(_self).find(".ui-delay-icon").parent().parent().addClass("ui-btn-delay-row");
		$(opts.pager).on("change","#"+thisId+"_listbox",function(){
			var rowNum = $(this).val();
			$(_self).jqGrid('setGridParam',{ 
				"rowNum": rowNum,
            }).trigger("reloadGrid");
        });
		//固定操作列
		var colModel = $(_self).jqGrid('getGridParam',"colModel");
		var op = colModel[colModel.length-1];
		if(op && op.frozen){
			var rows = $(_self).jqGrid('getGridParam',"rowNum");
			$(".right-frozen-bdiv").remove();
			var height = rows * 40;
			var fDiv = '<div style="position: absolute; right: 0px; top: 40px; overflow-y: hidden;width:'+op.width+'px; height: '+height+'px;" class="frozen-bdiv right-frozen-bdiv ui-jqgrid-bdiv" ><table class="table table-striped ui-jqgrid-btable ui-common-table" tabindex="0" role="presentation" aria-multiselectable="false" aria-labelledby="gbox_jsonmap" style="width: 1px;"></table></div>';
			$(_self).parents(".ui-jqgrid-bdiv").after(fDiv);
			var tdArr = $(_self).find('tr').find("td:last").clone();
			for(var i =0;i< tdArr.length;i++){
				var tr=$('<tr role="row"id="'+i+'_f" tabindex="-1" class="jqgrow ui-row-ltr ui-widget-content" style="height: 42px;"></tr>')
				if(i == 0){
					tr=$('<tr role="row"id="'+i+'_f" tabindex="-1" class="jqgfirstrow ui-row-ltr ui-widget-content" style="height: 0px;"></tr>')
				}
				$(tr).append(tdArr[i]);
				$(".right-frozen-bdiv > table").append(tr);
			}
			var thBox ='<div style="position: absolute; right: 0; top: 0px; height: 40px;" class="frozen-div right-frozen-div ui-jqgrid-hdiv ui-state-default"><table class="ui-jqgrid-htable ui-common-table "style="width: 1px; height: 100%;" role="presentation"aria-labelledby="gbox_jsonmap"><thead><tr class="ui-jqgrid-labels" role="row" style="height: 50px;"><th id="'+op.name+'_th" role="columnheader"class="ui-th-column ui-th-ltr ui-state-default" style="width: '+op.width+'px;"><span class="ui-jqgrid-resize ui-jqgrid-resize-ltr" style="cursor: col-resize;">&nbsp;</span><div class="ui-th-div ui-jqgrid-sortable" id="jqgh_'+op.name+'_th">操作 </div></th></tr></thead></table> </div>';
			$(_self).parents(".ui-jqgrid-bdiv").after(thBox);
		}
		 $(window).resize(function(){
			 var width = $(_self).parents(".ui-grid-box").width(); 
			 $(_self).setGridWidth(width);
		 });
	},
	getSelectedJsonArray : function(){
		var _self = this;
		var rowIds = $(_self).jqGrid('getGridParam','selarrrow');
		var array=[];
		if(!rowIds){
			return array;
		}
		for(var i = 0,len = rowIds.length;i<len;i++){
			var obj=$(_self).jqGrid('getRowData',rowIds[i]);   
			array.push(obj);
		}
		return array;
	},
	getSelectedIdsArray : function(param){
	   var jsonRaaderdefaults={
				"key":"appId"   
		   };
		   param = $.extend(jsonRaaderdefaults,param)
		   var rowIds = $(_self).jqGrid('getGridParam','selarrrow');
			var array=[];
			if(!rowIds){
				return array;
			}
			for(var i = 0,len = rowIds.length;i<len;i++){
				var obj=$(_self).jqGrid('getRowData',rowIds[i]);   
				array.push(obj[param.key]);
			}
			return array;
	   }
})

