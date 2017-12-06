/**
 * Created by j on 2017/12/6.
 */
$(function(){
    function del(){
        var html='<button type="button" class="btn btn-default  ui-table-btn ui-table-btn">删除</button><button type="button" class="btn btn-default ui-table-btn">修改</button>';
        return html;
    }
    $("#jsonmap1").AIGrid({
        //url:'../../demos/grid.json',
        url:'http://10.1.48.18:8441/api/prefecture/dataSourceInfo/queryList',
        datatype: "json",
        colNames:['元数据名称','创建时间', '标签类型','操作'],
        colModel:[
            {name:'id',index:'id', width:20, sortable:false,frozen : true,align:"center"},//frozen : true固定列
            {name:'invdate',index:'invdate', width:80, jsonmap:"invdate",align:"center"},
            {name:'name',index:'name asc, invdate', width:100,align:"center"},
            {name:'op',index:'op', width:40, sortable:false,formatter:del,align:"right"}
        ],
        rowNum:10,
        rowList:[10,20,30],
        pager: '#pjmap1',//分页的id
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
});