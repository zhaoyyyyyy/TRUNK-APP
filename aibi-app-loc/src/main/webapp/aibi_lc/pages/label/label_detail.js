var model={
		labelName : "",
		busiCaliber : "",
		failTime : "",
		updateCycle : "",
		applySuggest : "",
		busiLegend : "",
		labelTypeId : "",
		isRegular : "",
		categoryName : "",
		dimTableName : "",
		dataType : "",
		dependIndex : "",
		sourceName : "",
		countRules : "",
		sourceNameList : []
}
window.loc_onload = function() {
	var labelId = $.getUrlParam("labelId");
	var frwin = frameElement.lhgDG;
	$.commAjax({
		isShowMask : true,
		url : $.ctx + '/api/label/labelInfo/get',
		postData : {
			"labelId" : labelId
		},
		onSuccess : function(data) {
			var time = new Date(data.data.failTime);
			var y = time.getFullYear();//年
			var m = time.getMonth() + 1;//月
			var d = time.getDate();//日
			data.data.failTime = y+"年"+m+"月"+d+"日 ";
			model.labelName = data.data.labelName;
			model.busiCaliber = data.data.busiCaliber;
			model.failTime = data.data.failTime;
			model.applySuggest = data.data.applySuggest;
			model.busiLegend = data.data.busiLegend;
			model.labelTypeId = $.getCodeDesc("BQLXZD",data.data.labelTypeId);
			model.updateCycle = $.getCodeDesc("GXZQZD",data.data.updateCycle);
			model.isRegular = $.getCodeDesc("SFZD",data.data.isRegular);
			var labelId = data.data.labelId;
			if(model.labelTypeId=="枚举型"){
				$.commAjax({
					ansyc : false,
					url : $.ctx + '/api/label/mdaSysTableCol/queryList',
					postData : {
						"labelId" : labelId
					},
					onSuccess : function(data2){
						var list = data2.data;
						model.dataType = list[0].dataType;
						var dimId = list[0].dimTransId;
						$.commAjax({
							ansyc : false,
							url : $.ctx + '/api/dimtable/dimTableInfo/get',
							postData : {
								"dimId" : dimId
							},
						    onSuccess : function(data3){
						    	model.dimTableName = data3.data.dimTableName;
						    }
						});
					}
				});
			}
			var categoryId = data.data.categoryId;
			$.commAjax({
				url : $.ctx + '/api/label/categoryInfo/get',
				postData : {
					"categoryId" : categoryId
				},
			    onSuccess : function(data1){
			    	model.categoryName = data1.data.categoryName
			    }
			});
			var countRulesCode = data.data.countRulesCode;
			$.commAjax({
				ansyc : false,
				url : $.ctx + '/api/label/labelCountRules/get',
				postData : {
					"countRulesCode" : countRulesCode
				},
				onSuccess : function(data4){
					model.dependIndex = data4.data.dependIndex;
					model.countRules = data4.data.countRules;
					var dependList = model.dependIndex.split(",");
					for(var i=0; i<dependList.length ; i++){
						/*$.commAjax({
							ansyc : false,
							url : $.ctx + '/api/source/sourceInfo/get',
							postData : {
								"sourceId" : dependList[i]
							},
							onSuccess : function(data5){
								model.sourceName += data5.data.sourceName+""
							}
						});*/
						model.sourceName += dependList[i]+","
					}
					model.sourceName = model.sourceName.substr(0,model.sourceName.length-1);
				}	
			}); 	
		},
		maskMassage : 'load...'
	})
	frwin.addBtn("cancel", "确定", function() {
		frwin.cancel();
	});
	new Vue({
		el : "#dataD",
		data : model
	})
}