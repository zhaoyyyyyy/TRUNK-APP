/**
 * Created by j on 2017/12/6.
 */
var model = {
    sourceTableName: "",
    sourceTableCnName: "",
    sourceTableType: "",
    dateColumnName: "",
    whereSql: "",
    readCycle: "",
    idType: "",
    idColumn: "",
    idDataType: "",
    sortNum: 0,
    gxzq: [],
    tableType:[],
    sourceTableId: "",
    configId: "",
    tableSchema: "",
    isEdit: 0,
}
window.loc_onload = function() {
    model.configId = $.getCurrentConfigId();
    
    var tableTypeCode = [];

    var dicGxzq = $.getDicData("GXZQZD");
    for (var i = 0; i < dicGxzq.length; i++) {
        model.gxzq.push(dicGxzq[i]);
    };
    var dicYblx = $.getDicData("SJYBLX");
    for (var i = 0; i < dicYblx.length; i++) {
        model.tableType.push(dicYblx[i]);
        tableTypeCode.push(dicYblx[i].code);
    };
    var isEdit = $.getUrlParam("isEdit");
    var id = $.getUrlParam("sourceTableId");
    var num = $.getUrlParam("num");
    if (id != null && id != "" && id != undefined) {
        model.sourceTableId = id;
        $.commAjax({
            url: $.ctx + '/api/source/sourceTableInfo/get',
            postData: {
                "sourceTableId": id
            },
            isShowMask: true,
            maskMassage: 'Load...',
            onSuccess: function(data) {
                model.sourceTableName = data.data.sourceTableName;
                model.sourceTableCnName = data.data.sourceTableCnName;
                model.sourceTableType = data.data.sourceTableType;
                model.dateColumnName = data.data.dateColumnName;
                model.whereSql = data.data.whereSql;
                model.readCycle = data.data.readCycle;
                model.idType = data.data.idType;
                model.idColumn = data.data.idColumn;
                model.idDataType = data.data.idDataType;
                model.tableSchema = data.data.tableSchema;
                $("#code" + data.data.readCycle).click();
                $("#type" + data.data.sourceTableType).click();
                if(num == 1){
                	for(var i=0;i<tableTypeCode.length;i++){
                		var obj = $("#type"+tableTypeCode[i]);
                		obj[0].disabled = true;
                    	debugger;
                    }
                }
            }
        })
    };
    new Vue({
        el: "#dataD",
        data: model,
        mounted: function() {
            this.$nextTick(function() {
                var r = $(".easyui-validatebox");
                if (r.length) {
                    r.validatebox();
                }
            })
        }
    });
    $("#code1").click();
    $("#type1").click();
    var url = $.ctx + "/api/source/sourceInfo/queryPage";
    var pD = {};
    if (isEdit == 1) {
        pD = {
            'sourceTableId': id
        }
        $("#sourceTableName").attr("disabled", true);
        $("#tableSchema").attr("disabled", true);
        model.isEdit = isEdit;
    }else{
		pD = {
	        'sourceTableId': 'neverexit'
		}
    };

    var dic = $.getDicData("ZDLXZD");
    var dicCode = "";
    for (var k = 0; k < dic.length; k++) {
        dicCode += dic[k].code + ":" + dic[k].dataName;
        var j = k + 1;
        if (j != dic.length) {
            dicCode += ";";
        }
    };

    $("#jsonmap").jqGrid({
        url: url,
        editurl: "clientArray",
        postData: pD,
        datatype: "json",
        colNames: ['源字段名称', '字段类型', '指标名称', '描述', '指标编码', '操作'],
        colModel: [{
            name: 'columnName',
            index: 'columnName',
            width: 80,
            sortable: false,
            frozen: true,
            editable: true,
            editrules: {
                required: true,
            }
        },
        // frozen : true固定列
        {
            name: 'cooColumnType',
            index: 'cooColumnType',
            width: 70,
            align: "center",
            edittype: 'select',
            formatter: 'select',
            editable: true,
            sortable: false,
            editoptions: {
                value: dicCode
            },
            editrules: {
                required: true,
            }
        },
        {
            name: 'sourceName',
            index: 'sourceName',
            width: 110,
            editable: true,
            sortable: false,
            align: "center",
            editrules: {
                required: true,
            }
        },
        {
            name: 'columnCaliber',
            index: 'columnCaliber',
            sortable: false,
            width: 120,
            editable: true,
            align: "center"
        },
        {
            name: 'sourceId',
            index: 'sourceId',
            sortable: false,
            align: "center",
            hidden: true
        },
        {
            name: 'op',
            index: 'op',
            width: 40,
            sortable: false,
            align: "center",
            formatter: function(value, opts, data) {
                return '<button onclick="fun_to_del(\'' + opts.rowId + '\',\'' + data.sourceId + '\')" type="button" class="btn btn-default  ui-table-btn ui-table-btn">删除</button>';
            }
        }],
        cellEdit: true,
        afterGridLoad: function() {
            var rows = $("#jsonmap").jqGrid('getRowData').length;
            for (var i = 1; i <= rows; i++) {
                $("#jsonmap").jqGrid('editRow', i);
            }
            model.sortNum += rows;
        },
        // pager: '#pjmap',//分页的id
        // sortname: 'invdate',//排序的字段名称 不需要的话可置为空
        // 取值取自colModel中的index字段
        viewrecords: true,
        rowNum: 999,
        rownumbers: true,
        multiselect: false,
        // caption:"标题",
        // 是否展示行号
        sortorder: "desc",
        // 排序方式
        jsonReader: {
            repeatitems: false,
            id: "0"
        },
        height: '100%'
    });
    var dataRow = {
        "columnName": "",
        "cooColumnType": "",
        "sourceName": "",
        "columnCaliber": "",
        "sourceId": "",
        "op": ""
    }
    $("#btn_addRow").click(function() {
        model.sortNum += 1;
        $("#jsonmap").jqGrid("addRowData", model.sortNum, dataRow, "last");
        $("#jsonmap").jqGrid("editRow", model.sortNum);
    })
}
function setColor(cellvalue, options, rowObject) {
    if (rowObject.total > 700) {
        return '<span class="appMonitor" style="color:#faa918;">草稿</span>';
    }
    return cellvalue;
}
function fun_to_del(id, sourceId) {
    if (sourceId != "" && sourceId != null) {
        $.commAjax({
            url: $.ctx + "/api/label/labelCountRules/queryList",
            postData: {
                "dependIndex": sourceId
            },
            isShowMask: true,
            maskMassage: 'Load...',
            onSuccess: function(data) {
                if (data.data.length == 0) {
                    $("#jsonmap").jqGrid("delRowData", id);
                } else {
                    $.alert("该指标已经被使用，不能删除！");
                }
            }
        })
    } else {
        $("#jsonmap").jqGrid("delRowData", id);
    }
}
function fun_to_save() {
    if ($('#formData').validateForm()) {
        var tableName = "";
        if (!$("#sourceTableName").val()) {
            $.alert("请输入表名");
            return false;
        }
        var tableSchema = $("#tableSchema").val();
        if (tableSchema != "" && tableSchema != null) {
            tableName = tableSchema + "." + $("#sourceTableName").val();
        } else {
            tableName = $("#sourceTableName").val();
        }
        var colnames = [];
        var exe = true;
        $.commAjax({
            url: $.ctx + "/backSql/columns",
            async: false,
            isShowMask: true,
            maskMassage: 'Load...',
            postData: {
                "tableName": tableName
            },
            onSuccess: function(data) {

                if (data.data != null) {
                    for (var u = 0; u < data.data.length; u++) {
                        colnames.push(data.data[u].COLUMN_NAME);
                    }
                }
            }
        })
        // 取消所有编辑
        var ids = $("#jsonmap").jqGrid('getDataIDs');
        for (var i = 0; i < ids.length; i++) {
            $("#jsonmap").jqGrid("saveRow", ids[i]);
            var rowData = JSON.stringify($("#jsonmap").jqGrid("getRowData", ids[i]));
            if (rowData.indexOf('input') > 0) {
                return false;
            }
        }
        // 拼接批量信息
        var list = $("#jsonmap").jqGrid("getRowData");
        var sourceInfoList = "sourceInfoList{";
        var idColumn = $("#idColumn").val();
        var dateColumnName = $("#dateColumnName").val();
        var colNum = "";
        var idAndDate = 0;
        if (list.length == 0) {
            $.alert("请填写指标信息列");
            return false;
        }
        for (var k = 0; k < list.length; k++) {
            delete list[k].op;
            if (list[k].columnName == idColumn) {
                idAndDate++;
                continue;
            }
            if (list[k].columnName == dateColumnName) {
                idAndDate++;
                continue;
            }
            if (!isInArray(colnames, list[k].columnName)) {
                exe = false;
            }
            sourceInfoList += JSON.stringify(list[k]);
            var l = k + 1;
            if (l != list.length) {
                sourceInfoList += ",";
            } else {
                sourceInfoList += "}";
            }
        }
        if (idAndDate == list.length) {
            $.alert("请增加除[主键名称]与[分区字段]以外的指标信息列");
            for (var p = 0; p < ids.length; p++) {
                $("#jsonmap").jqGrid("editRow", ids[p]);
            }
            return false;
        }
        $("#sourceInfoList").val(sourceInfoList);
        // 开始进行保存
        var url_ = "";
        var msss = "";
        if (model.sourceTableId != null && model.sourceTableId != undefined && model.sourceTableId != "") {
            url_ = $.ctx + '/api/source/sourceTableInfo/update';
            msss = "修改成功";
        } else {
            $("#sourceTableId").removeAttr("name");
            url_ = $.ctx + '/api/source/sourceTableInfo/save';
            msss = "保存成功";
        }
        var boolean1 = isInArray(colnames, idColumn);
        var boolean2 = isInArray(colnames, dateColumnName);
        if (boolean1 && boolean2 && exe) {
            ajax_to_save(url_, msss);
        } else {
            $.confirm('表不存在或者表结构异常，确认保存？',
            function() {
                ajax_to_save(url_, msss);
            })
        }
        for (var p = 0; p < ids.length; p++) {
            $("#jsonmap").jqGrid("editRow", ids[p]);
        }
    }
}
function analysis() {
    var tableName = "";
    if (!$("#sourceTableName").val()) {
        $.alert("请输入表名");
        return false;
    }
    var tableSchema = $("#tableSchema").val();
    if (tableSchema != "" && tableSchema != null) {
        tableName = tableSchema + "." + $("#sourceTableName").val();
    } else {
        tableName = $("#sourceTableName").val();
    }
    $.commAjax({
        url: $.ctx + "/backSql/columns",
        postData: {
            "tableName": tableName
        },
        isShowMask: true,
        onSuccess: function(data) {
            if (data.data.length == 0) {
                $.alert("无法获得列信息，请确认表名");
                return false;
            }
            if (model.isEdit == 0) {
                $("#jsonmap").jqGrid("clearGridData");
                model.sortNum = 0;
            }
            var ids = $("#jsonmap").jqGrid('getDataIDs');
            for (var isi = 0; isi < ids.length; isi++) { // 让单元格可以获取内容
                $("#jsonmap").jqGrid("saveRow", ids[isi]);
            }
            var exitColnames = [];
            var list = $("#jsonmap").jqGrid("getRowData");
            for (var num = 0; num < list.length; num++) { // 获取已存在的行
                exitColnames.push(list[num].columnName);
            }
            for (var i = 0; i < data.data.length; i++) {
                if (isInArray(exitColnames, data.data[i].COLUMN_NAME)) { // 判断当前行是否已存在
                    continue;
                } else {
                    if (data.data[i].DATA_TYPE.toLowerCase().indexOf("int") != -1) {
                        data.data[i].DATA_TYPE = "1";
                    } else {
                    	data.data[i].DATA_TYPE = "2";
                    }
                    var dataRow = {}
                    if (data.data[i].COLUMN_COMMENT != "" && data.data[i].COLUMN_COMMENT != null) {
                        dataRow = {
                            "columnName": data.data[i].COLUMN_NAME,
                            "cooColumnType": data.data[i].DATA_TYPE,
                            "sourceName": data.data[i].COLUMN_COMMENT,
                            "columnCaliber": data.data[i].COLUMN_COMMENT,
                            "sourceId": "",
                            "op": ""
                        }
                    } else {
                        dataRow = {
                            "columnName": data.data[i].COLUMN_NAME,
                            "cooColumnType": data.data[i].DATA_TYPE,
                            "sourceName": data.data[i].COLUMN_NAME,
                            "columnCaliber": data.data[i].COLUMN_NAME,
                            "sourceId": "",
                            "op": ""
                        }
                    }
                    model.sortNum += 1;
                    $("#jsonmap").jqGrid("addRowData", model.sortNum, dataRow, "last");
                    $("#jsonmap").jqGrid("editRow", model.sortNum);
                }
            }
            for (var p = 0; p < ids.length; p++) {
                $("#jsonmap").jqGrid("editRow", ids[p]);
            }
        },
        maskMassage: '正在解析...'
    })
}
function fun_to_import() {
    var wd = $.window('导入列信息', $.ctx + '/aibi_lc/pages/label/dataSource_import.html', 300, 200);
    wd.addSourceList = function(sourceList) {
        for (var i = 0; i < sourceList.length; i++) {
            if (sourceList[i].cooColumnType.toLowerCase().indexOf("int") != -1) {
                sourceList[i].cooColumnType = "1";
            } else {
                sourceList[i].cooColumnType = "2";
            }
            var dataRow = {
                "columnName": sourceList[i].columnName,
                "cooColumnType": sourceList[i].cooColumnType,
                "sourceName": sourceList[i].sourceName,
                "columnCaliber": sourceList[i].columnCaliber,
                "sourceId": "",
                "op": ""
            }
            model.sortNum += 1;
            $("#jsonmap").jqGrid("addRowData", model.sortNum, dataRow, "last");
            $("#jsonmap").jqGrid("editRow", model.sortNum);
        }
    }
}
function ajax_to_save(url, mesg) {
    $.commAjax({
        url: url,
        isShowMask: true,
        maskMassage: 'Load...',
        postData: $('#formData').formToJson(),
        onSuccess: function(data) {
            if (data.data == "success") {
                $.success(mesg,
                function() {
                    history.back( - 1);
                });
            }
        }
    });
}
function isInArray(arr, value) {
    for (var i = 0; i < arr.length; i++) {
        if (value === arr[i]) {
            return true;
        }
    }
    return false;
}