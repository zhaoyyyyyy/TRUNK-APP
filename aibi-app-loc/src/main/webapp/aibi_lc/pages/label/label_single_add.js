var model = {
		dimtableInfoList:[],
		sourcetableInfoList:[],
		sourceInfoList:[],
		bqlx : [],
		isbq : [],
		showdimDetail: [],
		isActive:false, 
		arrs:[]
}

function changeStatus(obj){
	if(obj.value==="5"){//枚举型标签字典value为5
		const exit = model.showdimDetail.some(function(item){
			return Object.keys(item)[0] === ('showdim'+obj.id);
		});
		if(!exit){
			model.showdimDetail.push({[('showdim'+obj.id)]:true});
		}else{
			model.showdimDetail.forEach(function(item){
				if(Object.keys(item)[0]===('showdim'+obj.id)){
					item[('showdim'+obj.id)]=true
				};
			});
		}
	}else{
		model.showdimDetail.some(function(item){
			if(item[('showdim'+obj.id)]){
				item[('showdim'+obj.id)]=false;
				return true
			}
			return false
		});
	}
	console.log(model.showdimDetail);
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
		var json = $(this).formToJson();
		model.arrs.push(json);
	})
	var labelInfoList = JSON.stringify(model.arrs);
	var labelName = $('#labelName').val();
	if(labelName==""){
		$.alert("标签名称不许为空");
	}else if(countRulesCode==""){
	   	$.alert("抽取规则不许为空");
	}else{
		$.commAjax({
			url : $.ctx+'/api/label/labelInfo/save',
			postData:{
				"labelInfoList": labelInfoList
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
function getData(tag){	
	if($(tag).parents(".create-main").hasClass("active")){
		$(tag).parents(".create-main").removeClass("active");
	}else{
		formD=$(tag).parents(".create-main").addClass("active");
	}
		
}



