window.loc_onload = function() {
	
	$("#btn_to_add").click(function(){
    	var wd = $.window('新增全量表', $.ctx
			+ '/aibi_lc/pages/prefecture/allUserMsg_add.html', 500, 500);
    	wd.reload = function() {
			$("#mainGrid").setGridParam({
				postData : $("#formSearch").formToJson()
			}).trigger("reloadGrid", [ {
				page : 1
			} ]);
    	}
    })
    
    var isPdata = [];
	isPdata[0] = "是";
	isPdata[1] = "否";
	
    $("#mainGrid").jqGrid({
        url: $.ctx + "/api/back/allUserMsg/queryPage",
        datatype: "json",
        colNames: ['表描述信息','是否分区', '日表表名', '日表主键字段', '日表分区字段', '月表表名', '月表主键字段','月表分区字段','操作'],
        colModel: [{
            name: 'tableDesc',
            index: 'tableDesc',
            width: 60,
            sortable: false,
            frozen: true,
            align: "center"
        },
        {
            name: 'isPartition',
            index: 'isPartition',
            width: 40,
            sortable: false,
            frozen: true,
            align: "center",
            formatter : function(value){
            	return isPdata[value];
            }
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
            name: 'dayMainColumn',
            index: 'dayMainColumn',
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
            name: 'monthTableName',
            index: 'monthTableName',
            width: 40,
            sortable: false,
            frozen: true,
            align: "center",
        },
        {
            name: 'monthMainColumn',
            index: 'monthMainColumn',
            width: 40,
            align: "center",
            sortable: false,
        },
        {
            name: 'monthPartitionColumn',
            index: 'monthPartitionColumn',
            width: 40,
            align: "center",
            sortable: false,
        },
        {
            name: 'priKey',
            index: 'priKey',
            sortable: false,
            width: 50,
            align: "left",
            title:false,
            formatter: function(value, opts, data) {
            	var html = '';
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
}
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
function fun_to_delete(id){
	$.commAjax({
		url : $.ctx + '/api/back/allUserMsg/get',
		postData : {
			"priKey" : id,
		},
		onSuccess : function(data) {
			if(data.data.preConfigInfoSet.length==0){
				$.confirm('确定要进行[删除]吗？该操作可能会影响所有专区的数据生成', function() {
					$.commAjax({
						url : $.ctx + '/api/back/allUserMsg/delete',
						postData : {
							"priKey" : id,
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
			}else{
				$.alert("有正在使用该全量表的专区，无法进行删除");
			}
		}
	});
}