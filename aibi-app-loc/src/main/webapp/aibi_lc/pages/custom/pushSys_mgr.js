window.loc_onload = function() {
	
	$('#formSearch').keyup(function(event){
    	if(event.keyCode == 13){
    		$("#btn_search").click();
    	}
    })
     //根据系统平台名模糊查询
	$("#btn_search").click(function(){
		var txtValue = $("#sysName").val();
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
					"sysName":txtValue
				}
			}).trigger("reloadGrid",[{
				page : 1
			}])
		}
	})
	$("#pushTypeStatus").change(function(){//根据推送方式筛选
		$("#mainGrid").setGridParam({
			postData:{
				"pushType":this.value
			}
		}).trigger("reloadGrid",[{
			page:1
		}]);
	})
    $("#mainGrid").jqGrid({
        url: $.ctx + "/api/syspush/sysInfo/queryPage",
        datatype: "json",
        colNames: ['系统平台名称','推送方式', 'IP地址',  '描述','操作'],
        colModel: [{
            name: 'sysName',
            index: 'sysName',
            width: 60,
            sortable: false,
            frozen: true,
            align: "center"
        },
        {
            name: 'pushType',
            index: 'pushType',
            width: 40,
            sortable: false,
            frozen: true,
            align: "center",
            formatter : function(value, opts, data) {
        		return $.getCodeDesc("TSFSZD",value);
        	}
        },
        {
            name: 'ftpServerIp',
            index: 'ftpServerIp',
            width: 40,
            align: "center",
            sortable: false,
        },
        /*{
            name: 'tableNamePre',
            index: 'tableNamePre',
            width: 40,
            align: "center",
            sortable: false,
        },*/
        {
            name: 'descTxt',
            index: 'descTxt',
            width: 40,
            sortable: false,
            frozen: true,
            align: "center",
        },
        {
            name: 'sysId',
            index: 'sysId',
            sortable: false,
            width: 50,
            align: "center",
            title:false,
            formatter: function(value, opts, data) {
            	var html = '';
            	html += '<button onclick="fun_to_detail(\''+value+'\')" type="button" class="btn btn-default ui-table-btn ui-table-btn">查看</button>';
        		html += '<button onclick="fun_to_edit(\''+value+'\')" type="button" class="btn btn-default ui-table-btn ui-table-btn">修改</button>'; 
        		html += '<button onclick="fun_to_delete(\''+value+'\')" type="button" class="btn btn-default ui-table-btn ui-table-btn">删除</button>'; 
                return html;
            }
        }],
        rowList: [10, 20, 30],
        pager: '#mainGridPager',
        // 分页的id
        viewrecords: true,
        
    });
    $("#mainGrid").jqGrid('setLabel',0, '序号');
    $("#btn_to_add").click(function(){
    	var wd = $.window('新增推送平台', $.ctx
    		+ '/aibi_lc/pages/custom/pushSys_add.html', 600, 500);
    	wd.reload = function() {
    		$("#mainGrid").setGridParam({
    			postData : $("#formSearch").formToJson()
    		}).trigger("reloadGrid", [ {
    			page : 1
    		} ]);
    	}
    })
}
function fun_to_delete(id){
	$.confirm('确认删除此平台吗', function() {
		$.commAjax({
			url : $.ctx + '/api/syspush/sysInfo/delete',
			postData : {
				"sysId" : id,
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
	});
}
function fun_to_edit(id){
	var wd = $.window('编辑推送平台', $.ctx
			+ '/aibi_lc/pages/custom/pushSys_add.html?sysId=' + id, 600, 500);
	wd.reload = function() {
		$("#mainGrid").setGridParam({
			postData : $("#formSearch").formToJson()
		}).trigger("reloadGrid", [ {
			page : 1
		} ]);
	}
}
function fun_to_detail(id){
	var wd = $.window('查看推送平台', $.ctx
			+ '/aibi_lc/pages/custom/pushSys_detail.html?sysId=' + id, 550, 500);
	wd.reload = function() {
		$("#mainGrid").setGridParam({
			postData : $("#formSearch").formToJson()
		}).trigger("reloadGrid", [ {
			page : 1
		} ]);
	}
}
