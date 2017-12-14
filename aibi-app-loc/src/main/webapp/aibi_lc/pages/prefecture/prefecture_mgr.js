window.loc_onload = function() {

	$('#formSearch').keyup(function(event){
    	if(event.keyCode == 13){
    		$("#btn_search").click();
    	}
    })
	
    $("#btn_search").click(function() {
		$("#mainGrid").setGridParam({
            postData:$('#formSearch').formToJson()
        }).trigger("reloadGrid", [{
            page: 1
        }]);
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
        url: $.ctx + "/api/prefecture/preConfigInfo/queryPage",
        datatype: "json",
        colNames: ['专区名','专区ID', '专区英文名', '组织名称', '创建时间', '专区类型', '数据状态', '操作'],
        colModel: [{
            name: 'sourceName',
            index: 'sourceName',
            width: 30,
            sortable: true,
            frozen: true,
            align: "center",
            formatter : function(value, opts, data) {
    			return "<a href='###' onclick='fun_to_detail(\"" + data.configId
    			+ "\")' ><font color='blue'>" + data.sourceName
    			+ "</font></a>";
    		}
        },
        {
            name: 'configId',
            index: 'configId',
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
                return $.getCodeDesc('ZZLXZD', v)
            }
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
            width: 60,
            align: "center",
            formatter: function(value, opts, data) {
            	var html = '';
            	if(data.configStatus != 4){
            		if (data.configStatus == 1) {
                        html += '<button onclick="fun_to_over(\''+data.configId+'\')" type="button" class="btn btn-default  ui-table-btn ui-table-btn">停用</button>';
                    } else if(data.configStatus != 3){
                    	html += '<button onclick="fun_to_start(\''+data.configId+'\')" type="button" class="btn btn-default  ui-table-btn ui-table-btn">启用</button>';
                    }
                	if (data.configStatus != 3 && data.configStatus != 0) {
                		html += '<button onclick="fun_to_edit(\''+data.configId+'\')" type="button" class="btn btn-default ui-table-btn ui-table-btn">修改</button>'; 
                	}
//                	else{
//                		html += '<button onclick="fun_to_delete(\''+data.configId+'\')" type="button" class="btn btn-default  ui-table-btn ui-table-btn">删除</button>';
//                	}
                    if (data.configStatus == 2) {
                        html += '<button onclick="fun_to_down(\''+data.configId+'\')" type="button" class="btn btn-default  ui-table-btn ui-table-btn">下线</button>';
                    }
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
function fun_to_edit(id){
	window.location='prefecture_add.html?configId='+id;
}
function fun_to_detail(id){
	var wd = $.window('专区详情', $.ctx
			+ '/aibi_lc/pages/prefecture/prefecture_detail.html?configId=' + id, 500, 500);
	wd.reload = function() {
		$("#mainGrid").setGridParam({
			postData : $("#formSearch").formToJson()
		}).trigger("reloadGrid", [ {
			page : 1
		} ]);
	}
}
function fun_to_over(id){
	$.confirm('确定停用该专区？', function() {
		$.confirm('真的确定要停用该专区？', function() {
			$.commAjax({
				url : $.ctx + '/api/prefecture/preConfigInfo/update',
				postData : {
					"configId" : id,
					"configStatus" : 2
				},
				onSuccess : function(data) {
					$.success('停用成功。', function() {
						$("#mainGrid").setGridParam({
							postData : $("#formSearch").formToJson()
						}).trigger("reloadGrid", [ {
							page : 1
						} ]);
					});
				}
			});
		})
	})
}
function fun_to_start(id){
	$.confirm('确定启用该专区？', function() {
		$.commAjax({
			url : $.ctx + '/api/prefecture/preConfigInfo/update',
			postData : {
				"configId" : id,
				"configStatus" : 1
			},
			onSuccess : function(data) {
				$.success('启用成功。', function() {
					$("#mainGrid").setGridParam({
						postData : $("#formSearch").formToJson()
					}).trigger("reloadGrid", [ {
						page : 1
					} ]);
				});
			}
		});
	})
}
function fun_to_down(id){
	$.confirm('确定要下线该专区吗？', function() {
		$.confirm('真的确定要下线该专区？', function() {
			$.commAjax({
				url : $.ctx + '/api/prefecture/preConfigInfo/update',
				postData : {
					"configId" : id,
					"configStatus" : 3
				},
				onSuccess : function(data) {
					$.success('启用成功。', function() {
						$("#mainGrid").setGridParam({
							postData : $("#formSearch").formToJson()
						}).trigger("reloadGrid", [ {
							page : 1
						} ]);
					});
				}
			});
		})
	})
}
function fun_to_delete(id){
	$.confirm('确定要删除该专区吗？', function() {
		$.confirm('真的确定要删除该专区？', function() {
			$.commAjax({
				url : $.ctx + '/api/prefecture/preConfigInfo/update',
				postData : {
					"configId" : id,
					"configStatus" : 4
				},
				onSuccess : function(data) {
					$.success('启用成功。', function() {
						$("#mainGrid").setGridParam({
							postData : $("#formSearch").formToJson()
						}).trigger("reloadGrid", [ {
							page : 1
						} ]);
					});
				}
			});
		})
	})
}