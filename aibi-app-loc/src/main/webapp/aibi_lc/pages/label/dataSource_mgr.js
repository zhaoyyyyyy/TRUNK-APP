/**
 * Created by j on 2017/12/6.
 */
window.loc_onload = function() {
    function del() {
        var html = '<button type="button" class="btn btn-default  ui-table-btn ui-table-btn">删除</button><button type="button" class="btn btn-default ui-table-btn">修改</button>';
        return html;
    }
    $("#jsonmap1").jqGrid({
        url: $.ctx + "/api/source/sourceTableInfo/queryPage",
        datatype: "json",
        colNames: ['数据源表名称', '创建时间', '标签类型', '操作'],
        colModel: [{
            name: 'sourceTableName',
            index: 'sourceTableName',
            width: 20,
            sortable: false,
            frozen: true,
            align: "center"
        },
        // frozen:true固定列
        {
            name: 'createTime',
            index: 'createTime',
            width: 80,
            align: "center"
        },
        {
            name: 'sourceTableType',
            index: 'sourceTableType',
            width: 100,
            align: "center"
        },
        {
            name: 'sourceTableId',
            index: 'sourceTableId',
            width: 40,
            sortable: false,
            align: "center",
            formatter: function(value, opts, data) {
                var html = '<button type="button" class="btn btn-default ui-table-btn">修改</button>' 
                	+ '<button type="button" class="btn btn-default  ui-table-btn ui-table-btn">删除</button>';
                return html;
            }
        }],
        rowNum: 10,
        rowList: [10, 20, 30],
        pager: '#pjmap1',
        // 分页的id
        sortname: '',
        // 排序的字段名称 不需要的话可置为空
        // 取值取自colModel中的index字段
        viewrecords: true,
        multiselect: true,
        rownumbers: false,
        // 是否展示行号
        sortorder: "desc",
        // 排序方式
        jsonReader: {
            repeatitems: false,
            id: "0"
        },
        height: '100%'
    });
}