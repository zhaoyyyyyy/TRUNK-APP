var model = {
		dimtableInfoList:[],
		sourcetableInfoList:[],
		sourceInfoList:[],
		bqlx : [],
		sjlx : [],
		showdimDetail: [],
		isActive:false, 
		arrs:[],
		labelInfoList:[],
		readCycle : "",
		sourceTableType : "",
		read : "",
		nodeName:"",
		categoryName:"",
		tagNode:"",
		categoryId:"",
		configId:"",
		checked:false,//添加radio属性
}
function changeStatus(elem){
	if($(elem).val()==5){
		$(elem).parents("form").find(".ui-form-hide").show();
	}else{
		$(elem).parents("form").find(".ui-form-hide").hide();
	}
}
window.loc_onload = function() {	
	model.configId =$.getCurrentConfigId();
	var dicBqlx = $.getDicData("BQLXZD");
	for(var i = 0; i<dicBqlx.length; i++){
		if(dicBqlx[i].code!=10&&dicBqlx[i].code!=12&&dicBqlx[i].code!=11&&dicBqlx[i].code!=8){
			model.bqlx.push(dicBqlx[i]);
		}	
	}
	var dicsjlx = $.getDicData("ZDLXZD");
	for(var i =0 ; i<dicsjlx.length; i++){
		model.sjlx.push(dicsjlx[i]);
	}
	new Vue({
		el : '#dataD',
	    data : model ,
	    methods :{
	    	fun_to_dimdetail : function(sourceInfo){
	    		$.commAjax({
					ansyc : false,
				    url : $.ctx + '/api/dimtable/dimTableInfo/get',
				    postData : {
				    	"dimId" : sourceInfo.dimId
				    },
				    onSuccess : function(data){
				    	var dimTableName = data.data.dimTableName;
				    	var win = $.window('维表详情',$.ctx + '/aibi_lc/pages/dimtable/dimtable_detail.html?dimTableName='+dimTableName, 900,
				    			600);
				    }
				});
	    	} 
	    } ,
	    mounted : function () {
		    this.$nextTick(function () {
		    	this.bqlx[0].checked=true;//默认第一个radio选中
			    var r = $(".easyui-validatebox");
	   			if (r.length){
	   				r.validatebox();
	   			}
		    })
		}
	})
	$.commAjax({
		url : $.ctx + '/api/dimtable/dimTableInfo/queryList',
		postData : {
			"configId" : model.configId
		},
		onSuccess : function(data){
			model.dimtableInfoList = data.data
		}
	});
	$.commAjax({
		async : false,
		url : $.ctx + '/api/source/sourceTableInfo/queryList',
		postData : {
			"configId" : model.configId,
			"sourceTableType" : 1
		},
		onSuccess : function(data){
			model.sourcetableInfoList = data.data
		}
	});	
	$('#sourceTableId').change(function(){
		$(".ui-form-hide").hide();
		var sourceTableId = $("#sourceTableId").val();
		model.sourceInfoList = [];
		$.commAjax({
			url : $.ctx + '/api/source/sourceTableInfo/get?sourceTableId='+sourceTableId,
			onSuccess : function(data){
				$(".ui-form-hide").hide();
				model.sourceTableType = data.data.sourceTableType
				model.sourceInfoList = data.data.sourceInfoList;
				model.readCycle=data.data.readCycle;
				model.read = $.getCodeDesc("GXZQZD",data.data.readCycle);
				model.showdimDetail=[];
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
	var flag = "";
	var k = 1;
	$("form[class~=active]").each(function(){
		if($("form[class~=active]").validateForm()){	
			var labelInfo = $(this).formToJson();
			$.commAjax({
			  async : false,
			  url : $.ctx + '/api/label/labelInfo/save',
			  postData : labelInfo,
			  onSuccess : function(data){
				  flag = data.status
			  }  
			});	
			if(k == $("form[class~=active]").size() && flag == 200){	
				$.success("创建成功",function(){
					history.back(-1);
				})
			}
			k++;
		}
	})
}

function getData(tag){	
	if($(tag).parents(".create-main").hasClass("active")){
		$(tag).parents(".create-main").removeClass("active");
	}else{
		formD=$(tag).parents(".create-main").addClass("active");
	}
		
}

function getTime(element){
	console.log(element);
	$(element).datepicker({
  		changeMonth: true,
  		changeYear: true,
  		dateFormat:"yy-mm-dd",
  		dayNamesMin: [ "日", "一", "二", "三", "四", "五", "六" ],
  		monthNamesShort: [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" ]
  	});
}

function openTtee(tag){
	model.tagNode=tag;
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
		var labelId =$.getCurrentConfigId();	
		$.commAjax({			
		    url : $.ctx+'/api/label/categoryInfo/queryList',  		    
		    dataType : 'json', 
		    async:true,
		    postData : {
					"sysId" :labelId,
				},
		    onSuccess: function(data){ 		    			    			    	
			    	var ztreeObj=data.data;
			    	$.fn.zTree.init($(".ztree"), setting, ztreeObj)
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
			model.categoryName=treeNode.categoryName;
			model.categoryId=treeNode.categoryId;
			$(model.tagNode).val(model.categoryName);
			$(model.tagNode).siblings("input").val(model.categoryId);
			$(".ui-form-ztree").removeClass("open");
		};		
	}



