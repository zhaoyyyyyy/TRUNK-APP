
window.loc_onload = function(){
	 
	$("#btn_search").click(function(){
			$("#jsonmap1").setGridParam({
				postData : {"dicCode":"SEX"}
			}).trigger("reloadGrid", [{
				page : 1
			}]);
	})
	
    $("#mainGrid").jqGrid({
    	//url:'../../demos/grid.json',
    	url: $.ctx + "/api/dicData/dicdataPage/query",
        datatype: "json",
        colNames:['序号'],
        colModel:[
                  {name:'dataName',index:'dataName', width:30, sortable:true,frozen : true ,align:"center"}

//            {name:'id',index:'id', width:30, sortable:true,frozen : true ,align:"center"},
//            {name:'invdate',index:'invdate', width:60, jsonmap:"invdate",align:"center"},
//            {name:'name',index:'name asc, invdate',sortable:false, width:60,align:"center"},
//            {name:'amount',index:'amount', width:60,align:"center"},
//            {name:'tax',index:'tax', width:80,align:"center"	},
//            {name:'total',index:'total', width:100,align:"center"},
//            {name:'op',index:'op', width:160, sortable:false,formatter:del,align:"center"}
        ],
        pager: '#mainGridPager',//分页的id
        viewrecords: true,
    });




}
function setColor(cellvalue, options, rowObject){
	if(rowObject.total > 700){
		return '<span class="appMonitor" style="color:#faa918;">草稿</span>';
	}
	return cellvalue;
}
function del(){
	var html='<button type="button" class="btn btn-default  ui-table-btn ui-table-btn">停用</button><button type="button" class="btn btn-default  ui-table-btn ui-table-btn">修改</button><button type="button" class="btn btn-default  ui-table-btn ui-table-btn">修改授权用户</button><button type="button" class="btn btn-default ui-table-btn">标签授权</button>';
	return html;
}
