var _parentKey = '';

var createConfigurationTemplate = [ {
	name : '开关配置',
	fields : [ {
		name : '请输入配置项名称',
		code : '{PARENT_CODE}_请输入',
		type : 'boolean',
		value : '01',
	} ]
}, {
	name : '文本配置',
	fields : [ {
		name : '请输入名称',
		code : '请输入编码',
		type : 'text',
		value : '请输入配置项的值',
		desc : ''
	} ]
}, {
	name : '数据库配置',
	fields : [ {
		name : '驱动',
		code : '{PARENT_CODE}_DRIVERCLASS',
		type : 'enum',
		dimCode : 'driverClass',
		value : 'http://xxx.xxx.xxx.xxx'
	}, {
		name : '地址',
		code : '{PARENT_CODE}_URL',
		type : 'text',
		value : 'root',
	}, {
		name : '用户名',
		code : '{PARENT_CODE}_NAME',
		type : 'text',
		value : '请输入配置项的值'
	}, {
		name : '密码',
		code : '{PARENT_CODE}_PW',
		type : 'text',
		value : '请输入配置项的值'
	} ]
}, {
	name : '枚举配置',
	fields : [ {
		name : '驱动',
		code : '{PARENT_CODE}_DRIVERCLASS',
		type : 'enum',
		dimCode : 'driverClass',
		value : 'http://xxx.xxx.xxx.xxx'
	} ]
} ]

function fun_add(i, configKey) {
	var isEdit = 0;
	var dg;
	if (null != configKey) {
		i = i - 1;
		isEdit = 1;
		dg = $.dialog('编辑  [  ' + configKey + '  ]', $.ctx
				+ '/admin/pages/config/config_add.html', 800, 500);
		dg.getParams = function() {
			return {
				'configFields' : createConfigurationTemplate[i].fields,
				'coKey' : configKey,
				'isEdit' : isEdit
			}
		}
	} else {
		dg = $.dialog('新增  [  ' + createConfigurationTemplate[i].name + '  ]',
				$.ctx + '/admin/pages/config/config_add.html', 800, 500);
		dg.getParams = function() {
			return {
				'configFields' : createConfigurationTemplate[i].fields,
				'coKey' : _parentKey,
				'isEdit' : isEdit
			}
		}
	}
	dg.reload = function() {
		$("#mainGrid").setGridParam({
			postData : $("#formSearch").formToJson()
		}).trigger("reloadGrid", [ {
			page : 1
		} ]);
		// window.location.reload();
	}
}

window.jauth_onload = function() {
	$.commAjax({
		url : $.ctx + '/api/config/tree',
		isShowMask : false,
		type : 'POST',
		async : false,
		onSuccess : function(data) {
			$("li.root").append(data);
			mySimpleTree = $('#tree').simpleTree(
				{
					autoclose : false,
					afterClick : function(thi) {
						_parentKey = $(thi).attr('id');
						$("#mainGrid").setGridParam({
							postData : {
								parentKey : _parentKey
							}
						}).trigger("reloadGrid", [ {
							page : 1
						} ]);
						var btnAreaHtml = '';
						$.each(
							createConfigurationTemplate,
							function(i) {
								var btnObj = createConfigurationTemplate[i]
								var btn = '<div class="left newButton"><div>'
										+ '<input type="button" class="add" value="'
										+ btnObj.name
										+ '" onclick="fun_add('
										+ i
										+ ')" /></div></div>';
								btnAreaHtml = btnAreaHtml
										+ btn;
						})
						btnAreaHtml = btnAreaHtml
								+ '<div class="clear"></div>';
						new Vue({
							el : '#buttonA',
							data : {
								btn : btnAreaHtml
							}
						})
					}
				})
		}
	});

	var colNames = [ '名称', '编码', '值', '描述', '类型', '操作' ];
	var colModel = [
			{
				name : 'configName',
				index : 'configName',
				width : 20,
				align : 'left'
			},
			{
				name : 'configKey',
				index : 'configKey',
				width : 25,
				align : 'left'
			},
			{
				name : 'configVal',
				index : 'configVal',
				width : 20,
				align : 'left'
			},
			{
				name : 'configDesc',
				index : 'configDesc',
				width : 40,
				align : 'left'
			},
			{
				name : 'configValType',
				index : 'configValType',
				width : 20,
				align : 'center',
				formatter : function(value, opts, data) {
					return $.getCodeDesc('PZLX', data.configValType);
				}
			},
			{
				name : 'configKey',
				index : 'configKey',
				width : 120,
				fixed : true,
				formatter : function(value, opts, data) {
					return "<a onclick='fun_add(\"" + data.configValType
							+ "\",\"" + data.configKey
							+ "\")' class='s_edit' >编辑</a>"
							+ "<a onclick='fun_del(\"" + data.configKey
							+ "\")' class='s_delete' >删除</a>"
				}
			} ];
	$("#mainGrid").jqGrid({
		url : $.ctx + '/api/config/configPage/query',
		colNames : colNames,
		colModel : colModel,
		rownumbers : true,
		autowidth : true,
		viewrecords : true,
		pager : '#mainGridPager'
	});
	// 查询按钮
	$("#btnSearch").click(function() {
		$("#mainGrid").setGridParam({
			postData : $("#formSearch").formToJson()
		}).trigger("reloadGrid", [ {
			page : 1
		} ]);
	});
}

function fun_del(configKey) {
	$.confirm('此操作会删除本节点及叶子节点数据，且不可恢复，是否继续？', function() {
		$.commAjax({
			url : $.ctx + '/api/config/delete',
			postData : {
				key : configKey
			},
			onSuccess : function(data) {
				$.success('删除成功。', function() {
					$("#mainGrid").setGridParam({
						postData : $("#formSearch").formToJson()
					}).trigger("reloadGrid", [ {
						page : 1
					} ]);
				});
				// window.location.reload();
			}
		});
	});
}