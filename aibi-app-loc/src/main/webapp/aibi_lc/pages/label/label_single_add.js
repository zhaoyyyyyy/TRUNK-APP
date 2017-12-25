var model = {
		dimtableInfoList:[],
		sourcetableInfoList:[],
		sourceInfoList:[],
		bqlx : [],
		isbq : [],
		showdimDetail: "",
}

window.loc_onload = function() {		
	var dicBqlx = $.getDicData("BQLXZD");
	for(var i = 0; i<dicBqlx.length; i++){
		model.bqlx.push(dicBqlx[i]); 
	}
	var dicIsbq = $.getDicData("SFZD");
	for(var i = 0; i<dicIsbq.length; i++){
		model.isbq.push(dicIsbq[i]);
	}
	
	new Vue({
		el : '#dataD',
	    data : model 
	})
	$.commAjax({
		url : $.ctx + '/api/dimtable/dimTableInfo/queryList',
		onSuccess : function(data){
			model.dimtableInfoList = data.data
		}
	});
	$.commAjax({
		url : $.ctx + '/api/source/sourceTableInfo/queryList',
		onSuccess : function(data){
			model.sourcetableInfoList = data.data
		}
	});	
	$('#sourceTableId').change(function(){
		var sourceTableId = $("#sourceTableId").val();
		$.commAjax({
			url : $.ctx + '/api/source/sourceTableInfo/get?sourceTableId='+sourceTableId,
			onSuccess : function(data){
				model.sourceInfoList = data.data.sourceInfoList;			
			}
		});
	});

}

function fun_to_save(){
	var labelName = $('#labelName').val();
	var list =$('#saveDataForm').formToJson();
    labellist=JSON.stringify(list);
    labellist = JSON.parse(JSON.stringify(list));
    const formatCode = function (data) {
    	   const formatData = {};
    	   const result = {};
    	   Object.keys(data).forEach(function(key) {
    	     if (key.indexOf('.') === -1) {
    	       result[key] = data[key];
    	       return;
    	     }
    	     const label = key.split('.')[0];
    	     const name = key.split('.')[1];
    	     const index = label.charAt(label.length - 2);
    	     formatData[index] = formatData[index] || {};
    	     formatData[index][name] = data[key];
    	   });
    	  const resultArray = Object.keys(formatData).map(function(item) {
    	    return formatData[item];
    	  })
    	  result.dataArray = resultArray;
    	  return result;
    	};
    var labelInfolist=formatCode(labellist);
    debugger
	if(labelName==""){
		$.alert("标签名称不许为空");
	}else{
		$.commAjax({
			url : $.ctx+'/api/label/labelInfo/save',
			postData:{
				"labelInfoList":JSON.stringify(labelInfolist.dataArray)
			},
			onSuccess:function(data){
				if (data.data == 'success') {
					$.success("创建成功", function() {
						history.back(-1);
					});
				} 
			}
		});
	}	
}
function fun_to_dimdetail(){
	var dimId = $("#dimTableName").val();
	var win = $.window('维表详情',$.ctx + '/aibi_lc/pages/dimtable/dimtable_detail.html?dimId='+dimId, 800,
			600);
	win.reload = function(){
		$("mainGrid").setGridParam({
			postData : $("#formSearch").formToJson()
		}).trigger("reloadGrid",[{
			page : 1
		}]);
	}
}

function changeStatus(obj){
	if(obj.value == 5){
		model.showdimDetail = obj.id;
	}else{
		model.showdimDetail = "false";
	}
}
