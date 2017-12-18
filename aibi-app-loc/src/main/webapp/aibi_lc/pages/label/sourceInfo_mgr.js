/**
 * Created by j on 2017/12/6.
 */
window.loc_onload = function(){
	var frWin = frameElement.lhgDG;
	frWin.removeBtn();
	frWin.addBtn("ok", "确定", function() {
		var id = $('#mainGrid').jqGrid('getGridParam', 'selarrrow');
		$.commAjax({
			url: $.ctx + '/api/source/sourceInfo/get?sourceId='+id,
			onSuccess:function(data){
				var sourceName = data.data.sourceName;
				console.log(sourceName)
				$("<span></span>").test(sourceName).attr("class","del-btn");
				console.log($("<span></span>").test(sourceName).attr("class","del-btn"))
			}
		})
	});
	
	frWin.addBtn("cancel", "取消", function() {
		frWin.cancel();
	});	
	
	$("#btn_search").click(function(){
		var txtValue = $("#form_search").val();
		if(txtValue == null){
			$("#mainGrid").setGridParam({
				postData : {
					data : null
				}
			}).trigger("reloadGrid",[{
				page : 1
			}]);
		}else{
			$("#mainGrid").setGridParam({
				postData : {
					"sourceName":txtValue
				}
			}).trigger("reloadGrid",[{
				page : 1
			}])
		}
	})
	
	
	
	
    $("#mainGrid").jqGrid({
        url: $.ctx + "/api/source/sourceInfo/queryPage",
        datatype: "json",
        colNames:['指标名称','源数据表字段', '源数据表名称','创建时间'],
        colModel:[
            {name:'sourceName',index:'sourceName', width:20, sortable:false,frozen : true,align:"center"},//frozen : true固定列
            {name:'columnName',index:'columnName', width:80, sortable:false,frozen : true,align:"center"},
            {name:'sourceTableInfo.sourceTableName',index:'sourceTableInfo.sourceTableName', width:100,align:"center"},
            {name:'sourceTableInfo.createTime',index:'sourceTableInfo.createTime', width:40, sortable:false,align:"right"}
        ],
        rowNum:10,
        rowList:[10,20,30],
        pager: '#mainGridPager',//分页的id
        sortname: '',//排序的字段名称 不需要的话可置为空  取值取自colModel中的index字段
        viewrecords: true,
        multiselect:true,
        rownumbers:false,//是否展示行号
        sortorder: "desc",//排序方式
        jsonReader: {
            repeatitems : false,
            id: "0"
        },
        height: '100%'
    });  
    function del(){
        var html='<button type="button" class="btn btn-default  ui-table-btn ui-table-btn">删除</button><button type="button" class="btn btn-default ui-table-btn">修改</button>';
        return html;
    }
}