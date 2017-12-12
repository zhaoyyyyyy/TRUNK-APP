window.loc_onload = function() {

    $("#btn_search").click(function() {
    	var txtValue = $("#txt_name").val();
    	if(txtValue == null){
    		$("#mainGrid").setGridParam({
                postData: {
                	"sourceName": null ,
                    "sourceEnName": null,
                    "configDesc": null
                }
            }).trigger("reloadGrid", [{
                page: 1
            }]);
    	}else{
    		var sourceName_rows = 0;
    		var sourceEnName_rows = 0;
    		for(var i=0;i<3;i++){
    			if(i==0){
    				$("#mainGrid").setGridParam({
    	                postData: {
    	                	"sourceName": txtValue ,
    	                    "sourceEnName": null,
    	                    "configDesc": null
    	                }
    	            }).trigger("reloadGrid", [{
    	                page: 1
    	            }]);
    				sourceName_rows = $("#mainGrid").jqGrid('getGridParam', 'rows');
    			}else if(i==1&&sourceName_rows==null){
    				$("#mainGrid").setGridParam({
                        postData: {
                        	"sourceName": null ,
                            "sourceEnName": txtValue,
                            "configDesc": null
                        }
                    }).trigger("reloadGrid", [{
                        page: 1
                    }]);
                    sourceEnName_rows = $("#mainGrid").jqGrid('getGridParam', 'rows');
    			}else if(i==2&&sourceEnName_rows==null){
    				$("#mainGrid").setGridParam({
                        postData: {
                        	"sourceName": null ,
                            "sourceEnName": null,
                            "configDesc": txtValue
                        }
                    }).trigger("reloadGrid", [{
                        page: 1
                    }]);
    			}
    		}
    	}
    })

    $("#changeStatus").change(function() {
        $("#mainGrid").setGridParam({
            postData: {
                "configStatus": this.value,
            }
        }).trigger("reloadGrid", [{
            page: 1
        }]);
    })

    $("#mainGrid").jqGrid({
        // url:'../../demos/grid.json',
        url: $.ctx + "/api/prefecture/preConfigInfo/queryPage",
        datatype: "json",
        colNames: ['专区名', '专区英文名', '地市名称', '创建时间', '专区类型', '专区描述', '数据状态', '操作'],
        colModel: [{
            name: 'sourceName',
            index: 'sourceName',
            width: 30,
            sortable: true,
            frozen: true,
            align: "center"
        },
        {
            name: 'sourceEnName',
            index: 'sourceEnName',
            width: 30,
            sortable: true,
            frozen: true,
            align: "center"
        },
        {
            name: 'orgId',
            index: 'orgId',
            width: 30,
            sortable: true,
            frozen: true,
            align: "center"
        },
        {
            name: 'createTime',
            index: 'createTime',
            width: 60,
            sortable: true,
            frozen: true,
            align: "center",
        },
        {
            name: 'dataAccessType',
            index: 'dataAccessType',
            width: 30,
            align: "center",
            formatter: function(v) {
                return $.getCodeDesc('ZQLXZD', v)
            }
        },
        {
            name: 'configDesc',
            index: 'configDesc',
            sortable: false,
            width: 60,
            align: "center"
        },
        {
            name: 'configStatus',
            index: 'configStatus',
            width: 30,
            align: "center",
            formatter: function(v) {
                return $.getCodeDesc('ZQZTZD', v)
            }
        },
        {
            name: 'configId',
            index: 'configId',
            sortable: false,
            width: 120,
            align: "center",
            formatter: function(value, opts, data) {
                var html = '<button  type="button" class="btn btn-default  ui-table-btn ui-table-btn">修改</button>' + '<button type="button" class="btn btn-default  ui-table-btn ui-table-btn">修改授权用户</button>' + '<button type="button" class="btn btn-default ui-table-btn">标签授权</button>';
                if (data.configStatus != 2) {
                    html += '<button type="button" class="btn btn-default  ui-table-btn ui-table-btn">停用</button>';
                }
                return html;
            }
        }],
        pager: '#mainGridPager',
        // 分页的id
        viewrecords: true,
    });

}
function setColor(cellvalue, options, rowObject) {
    if (rowObject.total > 700) {
        return '<span class="appMonitor" style="color:#faa918;">草稿</span>';
    }
    return cellvalue;
}