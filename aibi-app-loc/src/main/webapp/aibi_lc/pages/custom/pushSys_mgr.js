window.loc_onload = function() {
    $("#mainGrid").jqGrid({
        url: $.ctx + "/api/syspush/sysInfo/queryPage",
        datatype: "json",
        colNames: ['系统平台名称','推送方式', 'FTP/SFTP地址', '表前缀', '描述','操作'],
        colModel: [{
            name: 'sysName',
            index: 'sysName',
            width: 60,
            sortable: false,
            frozen: true,
            align: "center"
        },
        {
            name: 'dayTableName',
            index: 'dayTableName',
            width: 40,
            sortable: false,
            frozen: true,
            align: "center",
        },
        {
            name: 'ftpPost',
            index: 'ftpPost',
            width: 40,
            align: "center",
            sortable: false,
        },
        {
            name: 'dayPartitionColumn',
            index: 'dayPartitionColumn',
            width: 40,
            align: "center",
            sortable: false,
        },
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
            align: "left",
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
    $("#btn_to_add").click(function(){
    	var wd = $.window('新增推送平台', $.ctx
    		+ '/aibi_lc/pages/custom/pushSys_add.html', 500, 500);
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


/*


function fun_to_edit(id){
	$.confirm('确定要进行[编辑]吗？该操作可能会影响所有专区的数据生成', function() {
		var wd = $.window('编辑全量表', $.ctx
				+ '/aibi_lc/pages/prefecture/allUserMsg_add.html?priKey=' + id, 500, 500);
		wd.reload = function() {
			$("#mainGrid").setGridParam({
				postData : $("#formSearch").formToJson()
			}).trigger("reloadGrid", [ {
				page : 1
			} ]);
		}
	})
}
*/