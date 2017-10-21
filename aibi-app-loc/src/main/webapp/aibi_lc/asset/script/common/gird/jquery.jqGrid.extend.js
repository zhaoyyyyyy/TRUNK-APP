(function($) {
	$.fn.extend({
		AIGrid : function(options) {
		 var _self  = $(this).empty();
		 var defaults={        
			   	url:'./grid.json',
				datatype: "json",
			   	colNames:[ ],
			   	colModel:[ ],
			   	rowNum:10,
			   	rowList:[],
			   	pager: '#pager_'+Math.random()*100,
			   	sortname: 'id',
			    viewrecords: true,
			    sortorder: "desc",
				jsonReader: {
					repeatitems : false,
					id: "0"
				},
				height: '100%',
				loadComplete:function(data){
					var pageBox = '<div class="clearfix"><div class="fleft totalPage"><span class="fleft">共'+data.records+'条，'+data.total+'页，当前第'+data.page+'页</span><select class="fleft ui-pg-selbox ui-widget-content ui-corner-all" id="listbox" role="listbox" title="每页记录数"></select></div><div id="pager" class="tcdPageCode"></div></div>';
					$(opts.pager).html(pageBox);
					var rowList = opts.rowList;
					for(var i=0,len = rowList.length;i<len;i++){
						$("#listbox").append('<option role="option" value="'+rowList[i]+'">'+rowList[i]+'</option>');
					}
					$(opts.pager).find("#pager").createPage({
				        pageCount:data.total,
				        current:data.page,
				        backFn:function(current){
				        	$(_self).jqGrid('setGridParam',{ 
				                url:opts.url, 
				                page:current 
				            }).trigger("reloadGrid");
				        }
				   });
				},
				gridComplete:function(){
					$("#load_"+_self.attr("id")).hide();
					$(opts.pager).on("change","#listbox",function(){
						var rowNum = $(this).val();
						$(_self).jqGrid('setGridParam',{ 
			                url:opts.url, 
			                rowNum: rowNum
			            }).trigger("reloadGrid");
			        });
				}
			};
		 var opts = $.extend(defaults, options);
		 return $(_self).jqGrid(opts);
	   }
	});
})(jQuery); 




