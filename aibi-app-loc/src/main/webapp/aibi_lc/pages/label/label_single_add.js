var model = {
		dimtableInfoList:[],
		sourcetableInfoList:[],
		sourceInfoList:[],
		bqlx : [],
		isbq : [],
		gxzq : [],
		showdimDetail: [],
		isActive:false, 
		arrs:[],
		labelInfoList:[],
		readCycle : "",
		read : "",
		nodeName:"",
}

function changeStatus(obj){
	if(obj.value==="5"){//枚举型标签字典value为5
		const exit = model.showdimDetail.some(function(item){
			return Object.keys(item)[0] === ('showdim'+obj.id);
		});
		if(!exit){
			var a = {};
			a['showdim'+obj.id] = true;
			model.showdimDetail.push(a);
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
}
window.loc_onload = function() {		
	var dicBqlx = $.getDicData("BQLXZD");
	for(var i = 0; i<dicBqlx.length; i++){
		if(dicBqlx[i].code!=10&&dicBqlx[i].code!=12&&dicBqlx[i].code!=8){
			model.bqlx.push(dicBqlx[i]);
		}		 
	}
	var dicIsbq = $.getDicData("SFZD");
	for(var i = 0; i<dicIsbq.length; i++){
		model.isbq.push(dicIsbq[i]);
	}
	var dicgxzq = $.getDicData("GXZQZD");
	for(var i =0 ; i<dicgxzq.length; i++){
		model.gxzq.push(dicgxzq[i]);
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
				model.readCycle=data.data.readCycle;
				model.read = data.data.readCycle;
				if(model.read=1){
					model.read="一次性"
				}else if(model.read=2){
					model.read="月周期"
				}else if(model.read=3){
					model.read="月周期"
				}
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
	if($("form[class~=active]").size()==0){
		$.alert("请选择要保存的标签");
	}
	var k = 1;
	$("form[class~=active]").each(function(){
		var labelInfo = $(this).formToJson();
		var labelName = $('#labelName').val();
		if(labelName==""){
			$.alert("标签名称不许为空");
		}else{
			$.commAjax({
			  url : $.ctx + '/api/label/labelInfo/save',
			  postData : labelInfo,
			});	
			if(k == $("form[class~=active]").size()){
				$.success("创建成功",function(){
					history.back(-1);
				})
			}
		}
		k++;
	})
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
        sourceTableId : "",
        dependIndex: false,
        countRules : "",
        updateCycle : ""
	});
}
function getData(tag){	
	if($(tag).parents(".create-main").hasClass("active")){
		$(tag).parents(".create-main").removeClass("active");
	}else{
		formD=$(tag).parents(".create-main").addClass("active");
	}
		
}


function openTtee(tag){
	var e = document.all ? window.event : arguments[0] ? arguments[0] : event;
	e.stopPropagation?e.stopPropagation():e.cancelBubble=true;
	if($(tag).parent(".ui-form-ztree").hasClass("open")){
		$(tag).parent(".ui-form-ztree").removeClass("open")
	}else{
		$(tag).parent(".ui-form-ztree").addClass("open");
		$(tag).parent(".ui-form-ztree").find(".dropdown-menu").css("width",100+"%");
		ztreeFunc();
		
	}
}


function ztreeFunc(){
		var ztreeObj;
		var obj = $("#preConfig_list").find("span");
		var labelId =obj.attr("configId");				
		$.commAjax({			
		    url : $.ctx+'/api/label/categoryInfo/queryList',  		    
		    dataType : 'json', 
		    async:true,
		    postData : {
					"sysId" :labelId,
				},
		    onSuccess: function(data){ 		    			    			    	
			    	var ztreeObj=data.data;
			    	$.fn.zTree.init($("#ztree"), setting, ztreeObj)
		    	}  
	   });
		setting = {
			view: {
				selectedMulti: false,
			},
			data: {
				selectedMulti: false,			
				simpleData: {  
	                enable: true,   //设置是否使用简单数据模式(Array)  	                    
	            },  
	            key: {             	
	            	idKey: "sysId",    //设置节点唯一标识属性名称  
	                pIdKey: "parentId" ,     //设置父节点唯一标识属性名称  
	                name:'categoryName',//zTree 节点数据保存节点名称的属性名称  
	                title: "categoryName"//zTree 节点数据保存节点提示信息的属性名称        
	            }  
			},
			callback: {
				onClick: zTreeOnClick
			}
		}		
		
		//展示选中分类下的标签
		function zTreeOnClick(event, treeId, treeNode) {
			model.nodeName=treeNode;
			$(".ui-form-ztree").removeClass("open");
		};		
	}



