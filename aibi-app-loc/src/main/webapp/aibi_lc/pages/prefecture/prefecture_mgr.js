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
    
    $("#btn_to_add").click(function(){
    	var wd = $.window('新增专区', $.ctx
			+ '/aibi_lc/pages/prefecture/prefecture_add.html', 500, 500);
    	wd.reload = function() {
			$("#mainGrid").setGridParam({
				postData : $("#formSearch").formToJson()
			}).trigger("reloadGrid", [ {
				page : 1
			} ]);
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
    
    var orgdata = [];
    
    $.commAjax({
		url : $.ctx + '/api/user/get',
		isShowMask : true,
		maskMassage : 'Load...',
		async : false,
		onSuccess : function(data) {
			if(data.data.orgPrivaliege != null && data.data.orgPrivaliege != undefined){
				var orgobj = data.data.orgPrivaliege;
				for(var i=0; i<4; i++){
					if(orgobj[i]==undefined){
						continue;
					}
					for(var f=0; f<orgobj[i].length; f++){
						var ob = orgobj[i][f];
						orgdata[ob.orgCode]=ob.simpleName;
					}
				}
			}
			
		}
	});

    $("#mainGrid").jqGrid({
        url: $.ctx + "/api/prefecture/preConfigInfo/queryPage",
        datatype: "json",
        colNames: ['专区名','专区ID', '组织名称', '创建时间', '专区类型', '数据状态', '操作'],
        colModel: [{
            name: 'sourceName',
            index: 'sourceName',
            width: 30,
            sortable: false,
            frozen: true,
            align: "center",
            formatter : function(value, opts, data) {
    			return "<a href='###' onclick='fun_to_detail(\"" + data.configId
    			+ "\")' ><font class='ui-table-fontColor'>" + data.sourceName
    			+ "</font></a>";
    		}
        },
        {
            name: 'configId',
            index: 'configId',
            width: 30,
            sortable: false,
            frozen: true,
            align: "center"
        },
        {
            name: 'orgId',
            index: 'orgId',
            width: 30,
            sortable: false,
            frozen: true,
            align: "center",
            formatter : function(value){
            	return orgdata[value];
            }
        },
        {
            name: 'createTime',
            index: 'createTime',
            width: 60,
            sortable: false,
            frozen: true,
            align: "center",
        },
        {
            name: 'dataAccessType',
            index: 'dataAccessType',
            width: 30,
            align: "center",
            sortable: false,
            formatter: function(v) {
                return $.getCodeDesc('ZQLXZD', v);
            }
        },
        {
            name: 'configStatus',
            index: 'configStatus',
            width: 30,
            align: "center",
            sortable: false,
            formatter: function(v) {
                return $.getCodeDesc('ZQZTZD', v);
            }
        },
        {
            name: 'configId',
            index: 'configId',
            sortable: false,
            width: 60,
            align: "left",
            title:false,
            formatter: function(value, opts, data) {
            	var html = '';
            	if(data.configStatus != 3){
            		if (data.configStatus == 1) {
                        html += '<button onclick="fun_to_over(\''+data.configId+'\')" type="button" class="btn btn-default  ui-table-btn ui-table-btn">停用</button>';
                    } else if(data.configStatus != 3){
                    	html += '<button onclick="fun_to_start(\''+data.configId+'\')" type="button" class="btn btn-default  ui-table-btn ui-table-btn">启用</button>';
                    }
            		html += '<button onclick="fun_to_edit(\''+data.configId+'\')" type="button" class="btn btn-default ui-table-btn ui-table-btn">修改</button>'; 
                    if (data.configStatus == 2) {
                        html += '<button onclick="fun_to_down(\''+data.configId+'\')" type="button" class="btn btn-default  ui-table-btn ui-table-btn">下线</button>';
                    }
            	}
                return html;
            }
        }],
        rowList: [10, 20, 30],
        pager: '#mainGridPager',
        // 分页的id
        viewrecords: true,
        
    });
	$("#mainGrid").jqGrid('setLabel',0, '序号');
}
function setColor(cellvalue, options, rowObject) {
    if (rowObject.total > 700) {
        return '<span class="appMonitor" style="color:#faa918;">草稿</span>';
    }
    return cellvalue;
}
function fun_to_edit(id){
	var wd = $.window('编辑专区', $.ctx
			+ '/aibi_lc/pages/prefecture/prefecture_add.html?configId=' + id, 500, 500);
	wd.reload = function() {
		$("#mainGrid").setGridParam({
			postData : $("#formSearch").formToJson()
		}).trigger("reloadGrid", [ {
			page : 1
		} ]);
	}
}
function fun_to_detail(id){
	var wd = $.window('专区详情', $.ctx
			+ '/aibi_lc/pages/prefecture/prefecture_detail.html?configId=' + id, 500, 400);
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
}
function fun_to_start(id){
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
}
function fun_to_down(id){
	$.confirm('确定要下线该专区吗？', function() {
		$.commAjax({
			url : $.ctx + '/api/prefecture/preConfigInfo/update',
			postData : {
				"configId" : id,
				"configStatus" : 3
			},
			onSuccess : function(data) {
				$.success('下线成功。', function() {
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
function fun_to_delete(id){
	$.confirm('确定要删除该专区吗？', function() {
		$.commAjax({
			url : $.ctx + '/api/prefecture/preConfigInfo/update',
			postData : {
				"configId" : id,
				"configStatus" : 4
			},
			onSuccess : function(data) {
				$.success('删除成功。', function() {
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