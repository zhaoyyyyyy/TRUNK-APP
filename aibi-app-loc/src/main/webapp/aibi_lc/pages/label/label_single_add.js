var model = {
		dimtableInfoList:[],
		sourcetableInfoList:[],
		sourceInfoList:[],
		bqlx : [],
		isbq : [],
		showdimDetail: "",
		isActive:false,
	 
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
	    data : model ,
//	    methods: {
//	    getData: function (event) {
//	    	console.log(event)
//	        model.isActive=true;	      
//	    }
//	  }
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
	
	$( '[data-dismiss="Datepicker"]' ).datepicker({
  		changeMonth: true,
  		changeYear: true,
  		dateFormat:"yy-mm-dd",
  		dayNamesMin: [ "日", "一", "二", "三", "四", "五", "六" ],
  		monthNamesShort: [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" ]
  	});
	
}

	


function fun_to_save(){

$("form[class~=active]").each(function(){
	console.log($(this))
})


	
	
	model.sourceInfoList.del
	//console.log(model.sourceInfoList)
	var labelName = $('#labelName').val();
	var countRulesCode = $('#countRulesCode').val();
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
	}else if(countRulesCode==""){
	   	$.alert("抽取规则不许为空");
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

function fun_to_createRow(){
	model.sourceInfoList.push({
		columnCaliber: "",
	    columnCnName : "",
        columnLength : "",
        columnName : "",
        columnNum : "",
        columnUnit : "",
        cooColumnType : "",
        sourceColumnRule : "",
        sourceId : "",
        sourceName : "",
        sourceTableId : ""
	});
}

function changeStatus(obj){
	if(obj.value == 5){
		model.showdimDetail = obj.id;
	}else{
		model.showdimDetail = "false";
	}
}
function getData(tag){	
	if($(tag).parents(".create-main").hasClass("active")){
		$(tag).parents(".create-main").removeClass("active");
	}else{
		formD=$(tag).parents(".create-main").addClass("active");
	}
		
}



