var model = {
	bqlx : [],
	gxzq : [],
	spzt : [],
//	isbq : [],
	sjlx : [],
	dimtableInfoList : [],
	sourceIdList : [],
	sourceNameList : [],
	dependIndexList : [],
	categoryId : "",
	labelName : "",
	failTime : "",
	labelId : "",
	updateCycle : "",
	labelTypeId : "",
	approveStatusId : "",
	dependIndex : "",
	configId : "",
	dataName : "",
	dimId : "",
	unit : "",
	isRegular : "",
	categoryName : "",
	countRules : "",
	busiCaliber : "",
	isemmu : false,
}

window.loc_onload = function() {
	model.configId = $.getCurrentConfigId();
	var labelId = $.getUrlParam("labelId");
	if(labelId!=""&&labelId!=null&&labelId!=undefined){
		model.labelId = labelId;
		$.commAjax({
			ansyc : false,
			isShowMask : true,
			url : $.ctx + '/api/label/labelInfo/get',
			postData : {
				"labelId" : labelId
			},
			onSuccess : function(data) {
				var time = new Date(data.data.failTime);
				var y = time.getFullYear()+'-';//年
				var m = (time.getMonth()+1<10 ? '0'+(time.getMonth()+1):time.getMonth()+1)+'-';//月
				var d = (time.getDate()+1<10 ? '0' +(time.getDate()):time.getDate());//日		
				$("#failTime").val(y+m+d);
				model.labelName = data.data.labelName;
				model.updateCycle = data.data.updateCycle;
				model.labelTypeId = data.data.labelTypeId;
				model.isRegular = data.data.isRegular;
				model.busiCaliber = data.data.busiCaliber;
				if(model.isRegular==1){
					$("#type"+model.isRegular).click()
				}else if(model.isRegular==2){
					$("#type"+model.isRegular).click()
				}
				var categoryId = data.data.categoryId;
				$.commAjax({
					ansyc : false,
					url : $.ctx + '/api/label/categoryInfo/get',
					postData : {
						"categoryId" : categoryId
					},
				    onSuccess : function(data2){
				    	model.categoryName = data2.data.categoryName
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
						model.countRules = data4.data.countRules;
						var echodependIndex = data4.data.dependIndex;
						$("#dependIndex").val(echodependIndex);
						var dependList = echodependIndex.split(",");
						for(var i=0; i<dependList.length ; i++){
							model.dependIndexList.push(dependList[i])
						}
					}	
				});
				$.commAjax({
					ansyc : false,
					url : $.ctx + '/api/label/mdaSysTableCol/queryList',
					postData : {
						"labelId" : labelId
					},
					onSuccess : function(data1){
						var list = data1.data;
						if(list.length!=0){
							if(model.labelTypeId==5){
								model.isemmu=true;
								model.dataName = list[0].dataType;
								model.dimId = list[0].dimTransId;
							}
							model.unit = list[0].unit
						};
					}
				});
				model.approveStatusId = data.data.approveInfo.approveStatusId;
			},
			maskMassage : 'load...'
		});
	}
	new Vue({
			el : "#dataD",
			data : model,
			methods : {
				del_sourceName : function(index){
//					model.sourceNameList.splice(index,1);
					model.dependIndexList.splice(index,1);
					var dependx = "";
					for(var i=0; i<model.dependIndexList.length; i++){
						dependx += model.dependIndexList[i]+","
					}
					if($("#label-lists span").length>1){
						$("#dependIndex").val(dependx.substr(0,dependx.length-1));
					}else{
						$("#dependIndex").val("");
					}
					
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

	var dicBqlx = $.getDicData("BQLXZD");
	for(var i = 0; i<dicBqlx.length; i++){
		if(dicBqlx[i].code!=10&&dicBqlx[i].code!=12&&dicBqlx[i].code!=11){
			model.bqlx.push(dicBqlx[i]);
		}	
//		model.bqlx.push(dicBqlx[i]);
	}
	
	var dicspzt = $.getDicData("SPZTZD");
	for(var i = 0; i<dicspzt.length; i++){
		if(dicspzt[i].code!=3){
			model.spzt.push(dicspzt[i]); 
		}
	}	
	$.commAjax({
		url : $.ctx + '/api/dimtable/dimTableInfo/queryList',
		postData : {
			"configId" : model.configId
		},
		onSuccess : function(data){
			model.dimtableInfoList = data.data
		}
	});
	
	//指标选择
	$('#btn_index_select').click(function() {
		if(model.updateCycle==""){
			$.alert("请先选择更新周期")
		}else {
			var win = $.window('指标配置', $.ctx + '/aibi_lc/pages/label/sourceInfo_mgr.html?readCycle='+model.updateCycle, 900, 600);
			win.addKpis = function(chooseKpis) {
				model.sourceIdList = chooseKpis;
				var dependx="";
				model.dependIndexList=[];
				for(var i=0; i<chooseKpis.length; i++){
					model.dependIndexList.push(chooseKpis[i])
				}
				for(var i=0; i<model.dependIndexList.length; i++){
					dependx += model.dependIndexList[i]+","
				}
				model.dependIndex= dependx.substr(0,dependx.length-1);
				$("#dependIndex").val(dependx.substr(0,dependx.length-1));
			}
		}	
	});
	
}

function changebq(obj){
	if(obj==5){
		model.isemmu=true;
	}else{
		model.isemmu=false;
	}
}

function fun_to_save(){
	if($("#saveDataForm").validateForm()){
		var url_ = "";
		var msg = "";
		if(model.labelId!=null && model.labelId!="" && model.labelId!=undefined){
			url_ = $.ctx + '/api/label/labelInfo/update',
			msg = "修改成功";
		}else{
			$("#labelId").removeAttr("name"),
			url_ = $.ctx + '/api/label/labelInfo/save',
			msg = "保存成功";
		}
		$.commAjax({
			url : url_,
			postData : $('#saveDataForm').formToJson(),
			onSuccess : function(data){
				if(data.data == "success"){
					$.success(msg, function() {
						history.back(-1);
					});
				}
			}
		});
	}else{
		$.alert("表单校验失败");
	}
}

function fun_to_dimdetail(){
	var dimId = $("#dimId").val();
	$.commAjax({
		ansyc : false,
	    url : $.ctx + '/api/dimtable/dimTableInfo/get',
	    postData : {
	    	"dimId" : dimId
	    },
	    onSuccess : function(data){
	    	var dimTableName = data.data.dimTableName;
	    	var win = $.window('维表详情',$.ctx + '/aibi_lc/pages/dimtable/dimtable_detail.html?dimTableName='+dimTableName, 900,
	    			600);
	    }
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
