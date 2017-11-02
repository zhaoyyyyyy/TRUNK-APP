var model = {// 定义Model
	// 前台 timed 定时 delay 延时
	exeType : '2',
	msgColor : 'black',
	message : null,
	showChoose : false,
	timedExecution : 'normal',
	delayExecution : 'normal',
	timedCircle : '◎',
	delayCircle : '○',
	showTimed : true,
	showDelay : false,
	// 后台
	parentExeName : null,
	parentExeId : null,
	taskExeName : null,
	sysId : null,
	taskExeId : null,
	taskId : ''

}
window.jauth_onload = function() {
	new Vue({// 定义vue
		el : '#saveDataForm',
		data : model
	});
	var parentExeId = $.getUrlParam("parentExeId");
	var taskExeId = $.getUrlParam("taskExeId");
	if (parentExeId != "" && parentExeId != null) {// 如果前台树有选中节点或选中节点有父节点
		model.parentExeId = parentExeId;
		model.showChoose = true;
		model.timedExecution = 'bolder';
		$.commAjax({
			url : $.ctx + '/api/schedule/taskExeInfo/get',
			isShowMask : false,
			type : 'POST',
			async : false,
			postData : {
				exeId : parentExeId
			},
			onSuccess : function(data) {
				model.parentExeName = data.locTaskExeInfo.taskExeName;
			}
		})
	} else if (taskExeId != null && taskExeId != "") {// 如果选中调度
		$
				.commAjax({
					url : $.ctx + '/api/schedule/taskExeInfo/get',
					isShowMask : false,
					type : 'POST',
					async : false,
					postData : {
						exeId : taskExeId
					},
					onSuccess : function(data) {
						if (data.locTaskExeInfo.parentExeId != null
								&& data.locTaskExeInfo.parentExeId != "") {
							$
									.commAjax({
										url : $.ctx
												+ '/api/schedule/taskExeInfo/get',
										isShowMask : false,
										type : 'POST',
										async : false,
										postData : {
											exeId : data.locTaskExeInfo.parentExeId
										},
										onSuccess : function(parentData) {
											model.parentExeName = parentData.locTaskExeInfo.taskExeName;
											model.parentExeId = parentData.locTaskExeInfo.taskExeId;
										}
									});
						} else {
							model.parentExeName = '无';
						}
						model.taskExeName = data.locTaskExeInfo.taskExeName;
						model.taskId = data.locTaskExeInfo.taskId;

						var obj = document.getElementById('taskId');
						var $obj = $(obj);
						$obj.treecombo;

						document.getElementById('taskId').value = data.locTaskExeInfo.taskId;
						model.sysId = data.locTaskExeInfo.sysId;
						model.taskExeId = data.locTaskExeInfo.taskExeId;
						document.getElementById('oldName').value = data.locTaskExeInfo.taskExeName;
						if (data.locTaskExeInfo.exeType == 2) {
							model.showChoose = true;
							fun_to_qiehuan(1);
							var taskExeTime = data.locTaskExeInfo.taskExeTime;
							var exeTime = taskExeTime.split(" ");
							for (var i = 0; i < exeTime.length; i++) {
								var id = i + 1;
								document.getElementById('taskExeTime' + id).value = exeTime[i];
							}
							;
						}
						;
						if (data.locTaskExeInfo.exeType == 1) {
							model.showChoose = true;
							fun_to_qiehuan(2);
							document.getElementById('taskExeTime').value = data.locTaskExeInfo.taskExeTime;
						}
						;
						if (data.locTaskExeInfo.exeStatus == 0) {
							model.showTimed = false;
						}
					}
				})
	} else {// 没有在树选择节点也没有在列表选择修改
		model.parentExeId = '1';
		model.parentExeName = '无';
		model.showTimed = false;
	}
	var dg = frameElement.lhgDG;
	dg.removeBtn();
	dg.addBtn("save", "保存", function() {
		$.commAjax({
			url : $.ctx + '/api/schedule/taskExeInfo/save',
			type : 'post',
			postData : $("#saveDataForm").formToJson(),
			onSuccess : function(data) {
				if (data == 'success') {
					$.success('保存成功。', function() {
						dg.cancel();
						dg.reload();
					})
				} else if (data == "failure") {
					$.alert("请确认输入是否正确");
				} else if (data == "notime") {
					$.alert("请选择并输入执行规则");
				}
			}
		})
	});
	dg.addBtn("cancel", "取消", function() {
		dg.cancel();
	});
}
// 校验是否重名
function fun_jiaoyan(obj) {
	var oldvalue = document.getElementById('oldName').value;
	var value = obj.value;
	$.commAjax({
		url : $.ctx + '/api/schedule/taskExeInfo/queryNameExist',
		isShowMask : false,
		type : 'POST',
		async : false,
		postData : {
			taskExeName : value
		},
		onSuccess : function(data) {
			if (data == 'exits' && value != oldvalue) {
				model.msgColor = 'red';
				model.message = '×名称已存在，请重新输入';
			} else if (data == 'success') {
				model.msgColor = 'green';
				model.message = '√可以使用';
			} else if (data == 'empty') {
				model.msgColor = 'red';
				model.message = '*请输入调度名称';
			} else {
				model.msgColor = 'green';
				model.message = ' √可以使用';
			}
		}
	})
}
// 写死的执行时间
function fun_to_change(count) {
	$('.exeTime').val('');
	if (count == 1) {
		document.getElementById('taskExeTime1').value = '0';
		document.getElementById('taskExeTime2').value = '0';
		document.getElementById('taskExeTime3').value = '1/1';
		document.getElementById('taskExeTime4').value = '*';
		document.getElementById('taskExeTime5').value = '*';
		document.getElementById('taskExeTime6').value = '?';
	}
	if (count == 2) {
		document.getElementById('taskExeTime1').value = '0';
		document.getElementById('taskExeTime2').value = '0';
		document.getElementById('taskExeTime3').value = '0';
		document.getElementById('taskExeTime4').value = '8/1';
		document.getElementById('taskExeTime5').value = '*';
		document.getElementById('taskExeTime6').value = '?';
	}
	if (count == 3) {
		document.getElementById('taskExeTime1').value = '0';
		document.getElementById('taskExeTime2').value = '20/1';
		document.getElementById('taskExeTime3').value = '*';
		document.getElementById('taskExeTime4').value = '*';
		document.getElementById('taskExeTime5').value = '*';
		document.getElementById('taskExeTime6').value = '?';
	}

}
// 点击切换按时间或延时
function fun_to_qiehuan(i) {
	if (i == 1) {
		model.timedExecution = 'bolder';
		model.delayExecution = 'normal';
		model.timedCircle = '◎';
		model.delayCircle = '○';
		model.showTimed = true;
		model.showDelay = false;
		model.exeType = '2';
		$('.exeTime').val('');
	}
	if (i == 2) {
		model.timedExecution = 'normal';
		model.delayExecution = 'bolder';
		model.timedCircle = '○';
		model.delayCircle = '◎';
		model.showTimed = false;
		model.showDelay = true;
		model.exeType = '1';
		$('.exeTime').val('');
	}
}
