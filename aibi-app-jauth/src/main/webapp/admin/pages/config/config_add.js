window.jauth_onload = function() {
	var html='';
	var dg = frameElement.lhgDG;
	var paramsObj = dg.getParams();
	var coKey = paramsObj.coKey;
	var isEdit = paramsObj.isEdit;
	var obj = paramsObj.configFields;
	$.commAjax({
		url : $.ctx + '/api/config/get',
		postData : {
			"coKey" : coKey
		},
		type : 'post',
		cache : false,
		onSuccess : function(data) {
			if(0==isEdit){
				new Vue({
					el : '#saveDataForm',
					data : {
						configName : null,
						configKey : null,
						configVal : null,
						configDesc : null,
						parentKey:data.config.configKey
					}
				});
			}else if(1==isEdit){
				new Vue({
					el : '#saveDataForm',
					data : {
						configName : data.config.configName,
						configKey : data.config.configKey,
						configVal : data.config.configVal,
						configDesc : data.config.configDesc,
						parentKey : data.config.parentKey
					}
				});
			}
		}
	});
	if(1==isEdit){
		html+='<input id="isEdit" name="isEdit" value="'+isEdit+'" type="hidden"/>';
	}
	if(true){//开始写HTML
		
		if(1==obj.length){//如果fields只有1个
			
			html+='<tr><td colspan="6"></td></tr><tr>';
			html+='<th v-show="\'undefined\'!=\''+obj[0].name+'\'">配置名称<span class="req">*</span></th><td v-show="\'undefined\'!=\''+obj[0].name+'\'"><v-input name="configName" defValue="configName" type="text" class="inputStyle easyui-validatebox" required="true"></v-input>';
			html+='<th v-show="\'undefined\'!=\''+obj[0].code+'\'">配置编码<span class="req">*</span></th>';
			if('0'==isEdit){
				html+='<td width="33%" v-show="\'undefined\'!=\''+obj[0].code+'\'">{{parentKey}}_<v-input name="configKey" defValue="configKey" type="text"class="inputStyle easyui-validatebox" required="true" ></v-input></td>'
			}
			if('1'==isEdit){
				html+='<td v-show="\'undefined\'!=\''+obj[0].code+'\'"><v-input name="configKey" defValue="configKey" type="text"class="read_inputStyle easyui-validatebox" required="true" readonly="readonly"></v-input></td>';
			}	
			html+=
				  '<th v-show="\'undefined\'!=\''+obj[0].type+'\'">配置值<span class="req">*</span></th><td v-show="\'undefined\'!=\''+obj[0].type+'\'">'
				+'<p v-if="\'enum\'==\''+obj[0].type+'\'"><v-input id="configVal" name="configVal" defValue="configVal" type="text" class="inputStyle easyui-validatebox" required="true" datadic="'+obj[0].dimCode+'" ></v-input><input name="configValType" type="hidden" value="4" /></p>'
				+'<p v-if="\'text\'==\''+obj[0].type+'\'"><v-input id="configVal" name="configVal" defValue="configVal" type="text" class="inputStyle easyui-validatebox"  required="true" ></v-input><input name="configValType" type="hidden" value="2" /></p>'
				+'<p v-if="\'boolean\'==\''+obj[0].type+'\'"><v-input id="configVal" name="configVal" defValue="configVal" type="text" class="inputStyle easyui-validatebox"  required="true" datadic="TOF"></v-input><input name="configValType" type="hidden" value="1" /></p>'
				+'</td>';
			html+='</tr><tr>';
			html+='<th v-show="\'undefined\'!=\''+obj[0].desc+'\'">配置描述</th><td colspan="5"  v-show="\'undefined\'!=\''+obj[0].desc+'\'"><textarea id="configDesc"name="configDesc" v-model="configDesc"class="input_textarea"></textarea></td>'
			html+='</tr>';
		}else{//如果fileds有多个
			
			for(var i=0;i<obj.length;i++){
				html+='<tr><td colspan="6"></td></tr><tr>';
				html+='<th v-show="\'undefined\'!=\''+obj[i].name+'\'">配置名称<span class="req">*</span></th><td v-show="\'undefined\'!=\''+obj[i].name+'\'"><v-input name="configName" defValue="configName" type="text"class="inputStyle easyui-validatebox" required="true"></v-input>'
				html+='<th v-show="\'undefined\'!=\''+obj[i].code+'\'">配置编码<span class="req">*</span></th><td width="33%" v-show="\'undefined\'!=\''+obj[i].code+'\'">{{parentKey}}_<v-input name="configKey" defValue="configKey"type="text"class="inputStyle easyui-validatebox" required="true"></v-input></td>';
				html+=
					  '<th v-show="\'undefined\'!=\''+obj[i].type+'\'">配置值<span class="req">*</span></th><td v-show="\'undefined\'!=\''+obj[i].type+'\'">'
					+'<p v-if="\'enum\'==\''+obj[i].type+'\'"><v-input id="configVal"name="configVal" defValue="configVal" type="text" class="inputStyle easyui-validatebox" required="true" datadic="'+obj[i].dimCode+'"></v-input><input name="configValTypes" type="hidden" value="4" /></p>'
					+'<p v-if="\'text\'==\''+obj[i].type+'\'"><v-input id="configVal"name="configVal" defValue="configVal" type="text" class="inputStyle easyui-validatebox" required="true"></v-input><input name="configValTypes" type="hidden" value="2" /></p>'
					+'<p v-if="\'boolean\'==\''+obj[i].type+'\'"><v-input id="configVal"name="configVal" defValue="configVal" type="text" class="inputStyle easyui-validatebox"  required="true" datadic="TOF"></v-input><input name="configValTypes" type="hidden" value="1" /></p>'
					+'</td>';
				html+='</tr><tr>';
				html+='<th v-show="\'undefined\'!=\''+obj[0].desc+'\'">配置描述</th><td colspan="5"  v-show="\'undefined\'!=\''+obj[0].desc+'\'"><textarea id="configDesc"name="configDesc" v-model="configDesc"class="input_textarea"></textarea></td>'
				html+='</tr>';
			}
			
		}
		new Vue({
			el : '#show',
			data : {
				list : html
			}
		});
	};
	
	dg.removeBtn();
	dg.addBtn("save", "保存", function() {
		if ($('#saveDataForm').validateForm()) {
			$.commAjax({
				url : $.ctx + '/api/config/save',
				postData : $("#saveDataForm").formToJson(),
				onSuccess : function(data) {
					if (data == 'success') {
						$.success('保存成功。', function() {
							dg.cancel();
							dg.reload();
						})
					}else {
						$.alert(data);
					}
				}
			})
		}
	});
	dg.addBtn("cancel", "取消", function() {
		dg.cancel();
	});

}