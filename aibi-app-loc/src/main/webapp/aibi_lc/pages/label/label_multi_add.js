var model = {
		dimtableInfoList:[],
		showdimDetail: [],
		sourcetableInfoList:[],
		sourceInfoList:[{
			sourceNameList :[],
			dependIndex : "",
			labelName : "",
			labelTypeId : "",
			isRegular : "",
			countRules : "",
			unit : "",
			busiCaliber : "",
			failTime : "",
		    updateCycle : "",
		    dataType : "",
		    categoryName : ""
		}],
		bqlx : [],
		isbq : [],
		gxzq : [],
		sourceIdList : [],
		configId : "",
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
window.loc_onload = function(){
	model.configId = $.getCurrentConfigId();
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
		if(dicgxzq[i].code!=1){
			model.gxzq.push(dicgxzq[i]);
		}
	}
	new Vue({
		el : '#dataD',
	    data : model ,
	    methods : {
	    	del_form : function(index){
	    		model.sourceInfoList.splice(index,1);
	    	}
	    },
	    mounted: function () {
		    this.$nextTick(function () {
			    var r = $(".easyui-validatebox");
	   			if (r.length){
	   				r.validatebox();
	   			}
		    })
		}
	})
	$("#dataStatusId").change(function(){
		$("#mainGrid").setGridParam({
			postData:{
				"dataStatusId":this.value
			}
		}).trigger("reloadGrid",[{
			page:1
		}]);
	});
	$(".ui-lc-mian").delegate(".create-main i.del-btn-icon","click",function(){
		$(this).parents("form.create-main").remove();
	});
}
//指标选择
function chooseKpi(obj){
	var win = $.window('指标配置', $.ctx + '/aibi_lc/pages/label/sourceInfo_mgr.html', 900, 600);
	win.addKpis = function(chooseKpis) {
		model.sourceIdList = chooseKpis;
		var index;
		var sourceName = [];
		var dependx="";
		for(var i=0; i<chooseKpis.length; i++){
			$.commAjax({
				async : false,
				url : $.ctx + '/api/source/sourceInfo/get',
				postData : {
					"sourceId" : chooseKpis[i]
				},
				onSuccess : function(data){
					sourceName.push(data.data.sourceName);
					dependx += data.data.sourceId+",";
				}
			});
		}
		index = obj.id;
		model.sourceInfoList[index]['sourceNameList']= sourceName;
		model.sourceInfoList[index]['dependIndex']= dependx.substr(0,dependx.length-1);
	}
}

function fun_to_dimdetail(){
	var dimId = $("#dimId").val();
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
		 sourceNameList : [],
	     dependIndex : "",
	     labelName : "",
	     labelTypeId : "",
	     isRegular : "",
		 countRules : "",
		 unit : "",
		 busiCaliber : "",
	     failTime : "",
	     updateCycle : "",
	     dataType : "",
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
				  flag = data.data
			  }  
			});	
			if(k == $("form").size() && flag == "success"){	
				$.success("创建成功",function(){
					history.back(-1);
				})
			}
			k++;
		}
	})
}