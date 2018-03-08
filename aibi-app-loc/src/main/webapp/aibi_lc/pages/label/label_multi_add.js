var model = {
		dimtableInfoList:[],
		showdimDetail: [],
		sourcetableInfoList:[],
		sourceInfoList:[{
			dependIndexList : [],
			dependIndex : "",
			labelName : "",
			labelTypeId : "",
			isRegular : "",
			countRules : "",
			unit : "",
			busiCaliber : "",
			failTime : "",
		    updateCycle : "",
		    categoryName : ""
		}],
		bqlx : [],
		gxzq : [],
		sjlx : [],
		sourceIdList : [],
		configId : "",
		checked:false,//添加radio属性
}
function changeStatus(elem){
	if($(elem).val()==5){
		$(elem).parents("form").find(".ui-form-hide").show();
	}else{
		$("#dimId").removeAttr("name");
//		$("#dataType").removeAttr("name");
		$(elem).parents("form").find(".ui-form-hide").hide();
		
	}
}
window.loc_onload = function(){
	model.configId = $.getCurrentConfigId();
	var dicBqlx = $.getDicData("BQLXZD");
	for(var i = 0; i<dicBqlx.length; i++){
		if(dicBqlx[i].code!=10&&dicBqlx[i].code!=12&&dicBqlx[i].code!=11&&dicBqlx[i].code!=8){
			model.bqlx.push(dicBqlx[i]);
		}	
//		model.bqlx.push(dicBqlx[i]);
	}
	var dicgxzq = $.getDicData("GXZQZD");
	for(var i =0 ; i<dicgxzq.length; i++){
		if(dicgxzq[i].code!=3){
			model.gxzq.push(dicgxzq[i]);
		}
	}
	var dicsjlx = $.getDicData("ZDLXZD");
	for(var i =0 ; i<dicsjlx.length; i++){
		model.sjlx.push(dicsjlx[i]);
	}
	new Vue({
		el : '#dataD',
	    data : model ,
	    methods : {
	        del_sourceName : function(index,index1){
	        	console.log(model.sourceInfoList)
	        	model.sourceInfoList[index]['dependIndexList'].splice(index1,1);
	        	var dependx="";
	        	for(var i=0; i<model.sourceInfoList[index]['dependIndexList'].length; i++){
	    			dependx += model.sourceInfoList[index]['dependIndexList']+","
	    		}
	        	if($(".label-lists span").length>1){
					$("#dependIndex_"+index).val(dependx.substr(0,dependx.length-1));
				}else{
					$("#dependIndex_"+index).val("");
				}
	        },
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
	    },
	    /*mounted: function () {
		    this.$nextTick(function () {
		    	this.bqlx[0].checked=true;//默认第一个radio选中
			    var r = $(".easyui-validatebox");
	   			if (r.length){
	   				r.validatebox();
	   			}
		    })
		}*/
	});
	$.commAjax({
		url : $.ctx + '/api/dimtable/dimTableInfo/queryList',
		postData : {
			"configId" : model.configId
		},
		onSuccess : function(data){
			model.dimtableInfoList = data.data
		}
	});
	$("#dataStatusId").change(function(){
		$("#mainGrid").setGridParam({
			postData:{
				"dataStatusId":this.value
			}
		}).trigger("reloadGrid",[{
			page:1
		}]);
	});
	$(".ui-lc-mian").delegate(".create-main i.form-del-btn","click",function(){
		$(this).parents("form.create-main").remove();
	});
}
//指标选择
function chooseKpi(obj){
	var readCycle = model.sourceInfoList[obj.id].updateCycle;
	if(readCycle ==undefined ||readCycle==null || readCycle==""){
		$.alert("请选择更新周期")
	}else{
		var win = $.window('指标配置', $.ctx + '/aibi_lc/pages/label/sourceInfo_mgr.html?readCycle='+readCycle, 900, 500);
		win.addKpis = function(chooseKpis) {
			model.sourceIdList = chooseKpis;
			var index = obj.id;
			var sourceName = [];
			var dependIndexList = [];
			var dependx="";
			for(var i=0; i<chooseKpis.length; i++){
				dependIndexList.push(chooseKpis[i]);
			}
			for(var i=0; i<dependIndexList.length; i++){
				dependx += dependIndexList[i]+","
			}
			model.sourceInfoList[index]['dependIndexList'] = dependIndexList;
			$("#dependIndex_"+index).val(dependx.substr(0,dependx.length-1));
		}
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
function fun_to_createRow(){
	model.sourceInfoList.push({
		 dependIndexList : [],
	     dependIndex : "",
	     labelName : "",
	     labelTypeId : "",
	     isRegular : "",
		 countRules : "",
		 unit : "",
		 busiCaliber : "",
	     failTime : "",
	     updateCycle : "",
	     categoryName : ""
	});
}

function fun_del(id){
	$.confirm('您确定要继续删除吗？',function(){
		$.commAjax({
			url:$.ctx+'/api/label/labelInfo/delete',
			postData:{"labelId":id},
			onSuccess:function(data){
				$("#mainGrid").setGridParam({
					postData:{
						data:null
					}
				}).trigger("reloadGrid",[{
					page:1
			   }]);	
			}
		});
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
function fun_to_save(){
	var flag = "";
	var k = 1;
	$("form").each(function(){
		if($("form").validateForm()){
			var labelInfo = $(this).formToJson();
			$.commAjax({
			  async : false,
			  url : $.ctx + '/api/label/labelInfo/save',
			  postData : labelInfo,
			  onSuccess : function(data){
				  flag = data.status
			  }  
			});	
			if(k == $("form").size() && flag == 200){
				$.success("创建成功",function(){
					history.back(-1);
//					frame.html.location.href='label_mgr.html';
				})
			}
			k++;
		}
	})
}