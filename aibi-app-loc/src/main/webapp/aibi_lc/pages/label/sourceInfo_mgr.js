/**
 * Created by j on 2017/12/6.
 */
window.loc_onload = function(){
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
	
	$("#jsonmap").jqGrid({
        url:$.ctx + "/api/source/sourceInfo/queryPage",
        datatype: "json",
        colNames:['字段名称','字段类型', '指标中文名', '是否表示列','描述','操作'],
        colModel:[
            {name:'sourceName',index:'sourceName', width:80, sortable:false,frozen : true,editable: true },//frozen : true固定列
            {name:'invdate',index:'invdate', width:70,sortable:false, jsonmap:"invdate",align:"center",editable: true},
            {name:'columnCnName',index:'columnCnName', width:110,align:"center",sortable:false,editable: true},
            {name:'amount',index:'amount', width:50,align:"center",sortable:true,editable: true},
            {name:'tax',index:'tax', width:120,align:"center"	,editable: true,sortable:true},
            {name:'op',index:'op', width:40, sortable:false,formatter:del,align:"center"}
        ],
        cellEdit: true,//单个编辑 去掉行编辑
        onSelectRow: function(id){debugger
            $('#jsonmap').AIGrid('editRow',id,true);
        },
        rowList:[10,20,30],
        //pager: '#pjmap',//分页的id
        sortname: 'invdate',//排序的字段名称 不需要的话可置为空  取值取自colModel中的index字段
        viewrecords: true,
        multiselect:false,
//        caption:"标题",
        rownumbers:false,//是否展示行号
        sortorder: "desc",//排序方式
        jsonReader: {
            repeatitems : false,
            id: "0"
        },
        height: '100%'
    });
}
function setColor(cellvalue, options, rowObject){
    if(rowObject.total > 700){
        return '<span class="appMonitor" style="color:#faa918;">草稿</span>';
    }
    return cellvalue;
}
function del(){
    var html='<button type="button" class="btn btn-default  ui-table-btn ui-table-btn">删除</button>';
    return html;
}