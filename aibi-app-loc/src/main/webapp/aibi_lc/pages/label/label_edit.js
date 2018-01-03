var model = {
	bqlx : [],
	gxzq : [],
	spzt : [],
	dimtableInfoList : [],
	labelName : "",
	failTime : "",
	labelId :"",
	updateCycle:"",
	labelTypeId:"",
	approveStatusId:"",
	isemmu : false,
}

window.loc_onload = function() {
	var labelId = $.getUrlParam("labelId");
	var frwin = frameElement.lhgDG;
	$.commAjax({
		url : $.ctx + '/api/label/labelInfo/get',
		postData : {
			"labelId" : labelId
		},
		onSuccess : function(data) {
			console.log(data)
			var time = new Date(data.data.failTime);
			var y = time.getFullYear();//年
			var m = time.getMonth() + 1;//月
			var d = time.getDate();//日		
			model.failTime = y+"年"+m+"月"+d+"日 ";
			model.labelName = data.data.labelName;
			model.labelId = data.data.labelId;
			model.updateCycle = data.data.updateCycle;
			model.labelTypeId = data.data.labelTypeId;
			model.approveStatusId = data.data.approveInfo.approveStatusId;
		}
	})
	new Vue({
			el : "#dataD",
			data : model,
			})
	var dicgxzq = $.getDicData("GXZQZD");
	for(var i =0 ; i<dicgxzq.length; i++){
		model.gxzq.push(dicgxzq[i]);
	}
	
	var dicBqlx = $.getDicData("BQLXZD");
	for(var i = 0; i<dicBqlx.length; i++){
		if(dicBqlx[i].code!=10&&dicBqlx[i].code!=12&&dicBqlx[i].code!=8){
			model.bqlx.push(dicBqlx[i]);
		}		 
	}
	
	var dicspzt = $.getDicData("SPZTZD");
	for(var i = 0; i<dicspzt.length; i++){
		if(dicspzt[i].code!=3){
			model.spzt.push(dicspzt[i]); 
		}
	}
	
	$.commAjax({
		url : $.ctx + '/api/dimtable/dimTableInfo/queryList',
		onSuccess : function(data){
			model.dimtableInfoList = data.data
		}
	});
	frwin.addBtn("ok","保存",function() {
		debugger
		$.commAjax({
			url : $.ctx + '/api/label/labelInfo/update',
			postData : $('#saveDataForm').formToJson(),
			onSuccess : function(data){
				if(data.data == "success"){
					$.success(msss, function() {
						history.back(-1);
					});
				}
			}
		});
	});
	frwin.addBtn("cancel", "取消", function() {
		frwin.cancel();
	});
}

function changebq(obj){
	if(obj==5){
		model.isemmu=true;
	}else{
		model.isemmu=false;
	}
}

function fun_to_dimdetail(){
	var dimId = $("#dimTableName").val();
	var win = $.window('维表详情',$.ctx + '/aibi_lc/pages/dimtable/dimtable_detail.html?dimId='+dimId, 500,
			300);
	win.reload = function(){
		$("mainGrid").setGridParam({
			postData : $("#formSearch").formToJson()
		}).trigger("reloadGrid",[{
			page : 1
		}]);
	}
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
	var labelId =$.getUrlParam("configId");				
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
