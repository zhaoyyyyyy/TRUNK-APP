var model = {
	bqlx : [],
	fhbqlx : [],
	gxzq : [],
	spzt : [],
	sjlx : [],
	sfzd : [],
	dimtableInfoList : [],
	sourceIdList : [],
	dependIndexList : [],
	sourcetableInfoList : [],  //纵表集合
	sourceInfoList : [], //纵表对应的列集合
	sourceTableId : "",
	
	categoryId : "",
	labelName : "",
	failTime : "",
	labelId : "",
	updateCycle : "",
	labelTypeId : "",
	approveStatusId : "",
	dependIndex : "",
	configId : "",
//	dataName : "",
	dimId : "",
	unit : "",
	isRegular : "",
	categoryName : "",
	countRules : "",
	busiCaliber : "",
	isemmu : false,
	isfhbq : false, //复合标签判断相应的显隐
	showdim : false,
	ismodify: false,
	isAdd: true,
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
				model.busiCaliber = data.data.busiCaliber;
				var categoryId = data.data.categoryId;
				$.commAjax({
					ansyc : false,
					isShowMask : true,
					url : $.ctx + '/api/label/categoryInfo/get',
					postData : {
						"categoryId" : categoryId
					},
				    onSuccess : function(data2){
				    	model.categoryName = data2.data.categoryName
				    },
					maskMassage : 'load...'
				});
				$.commAjax({
					ansyc : false,
					isShowMask : true,
					url : $.ctx + '/api/label/mdaSysTableCol/queryList',
					postData : {
						"labelId" : labelId
					},
					onSuccess : function(data1){
						var list = data1.data;
						if(list.length!=0){
							if(model.labelTypeId==5){
								model.isemmu=true;
								model.dimId = list[0].dimTransId;
							}else if(model.labelTypeId==8){
								model.isfhbq = true;
								model.ismodify =true;
								model.isAdd= false;
								model.isemmu = false;
								model.sourceInfoList = list;
								for(var i=0; i<model.sourceInfoList.length; i++){
									if(model.sourceInfoList[i].labelTypeId==5){
										model.sourceInfoList[i]["showdim"] = true
									}
								}
								$.commAjax({
									async : false,
									url : $.ctx+'/api/label/mdaSysTable/get',
									postData : {
										"tableId" : list[0].tableId
									},
								    onSuccess : function(data){
								    	var sourcetablename = data.data.tableName;
								    	var index = sourcetablename.indexOf('_',sourcetablename.indexOf('_')+1)
								    	sourcetablename = sourcetablename.substr(index+1,sourcetablename.length);
								    	$.commAjax({
								    		url : $.ctx + '/api/source/sourceTableInfo/queryList',
								    		postData : {
								    			"sourceTableName" : sourcetablename,
								    			"sourceTableType" : 2
								    		},
								    	    onSuccess : function(data){
								    	    	var sourcetablelist = data.data
								    	    	model.sourceTableId = sourcetablelist[0].sourceTableId
								    	    }
								    	});
								    }
								});	
								$.commAjax({
									async : false,
									url : $.ctx + '/api/source/sourceTableInfo/queryList',
									postData : {
										"configId" : model.configId,
										"readCycle" : model.updateCycle,
										"sourceTableType" : 2
									},
									onSuccess : function(data){
										model.sourcetableInfoList = data.data
									}
								});
							}
							model.unit = list[0].unit
						};
						var countRulesCode = list[0].countRulesCode;
						if(countRulesCode !=null){
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
						}	
					},
					maskMassage : 'load...'
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
				},
				changebq : function(event){
					if(event.target.value==5){
						model.isemmu = true;
						model.isfhbq = false;
					}else if(event.target.value==8){
						model.isfhbq = true;
						model.isemmu = false;
//						model.sourceInfoList=[];
					}else{
						model.isemmu = false;
						model.isfhbq = false;
					}
				},
				//选择不同的标签类型
				changeStatus : function(index,event){
					Vue.set(model.sourceInfoList[index],'showdim',false);
					if(event.target.value == 5){
						model.sourceInfoList[index].showdim = true;
					}else{
						model.sourceInfoList[index].showdim = false;
					}
				},
				//不同周期获取不同的纵表
				change_updateCycle : function(){
					model.sourceTableId = "";
					model.sourceInfoList = [];
					$.commAjax({
						async : false,
						url : $.ctx + '/api/source/sourceTableInfo/queryList',
						postData : {
							"configId" : model.configId,
							"readCycle" : model.updateCycle,
							"sourceTableType" : 2
						},
						onSuccess : function(data){
							model.sourcetableInfoList = data.data
						}
					});
				},		
				//选择不同的纵表，获取对应的列信息
				change_sourceId : function(event){
					var sourceTableId = event.target.value;
					$.commAjax({
						url : $.ctx + '/api/source/sourceTableInfo/get?sourceTableId='+sourceTableId,
						onSuccess : function(data){			
							model.sourceInfoList = data.data.sourceInfoList
						}
					});
					/*if(!model.updateCycle){
						$.alert("请选择周期")
					}else{
						var sourceTableId = event.target.value;
						$.commAjax({
							url : $.ctx + '/api/source/sourceTableInfo/get?sourceTableId='+sourceTableId,
							onSuccess : function(data){			
								model.sourceInfoList = data.data.sourceInfoList
							}
						});
					}*/
				},
				//删除列
				del_form : function(index){
					model.sourceInfoList.splice(index,1)
				},
				//显示维表详情
				showdimdetail : function(sourceInfo){
					$.commAjax({
						ansyc : false,
					    url : $.ctx + '/api/dimtable/dimTableInfo/get',
					    postData : {
					    	"dimId" : sourceInfo.dimTransId
					    },
					    onSuccess : function(data){
					    	var dimTableName = data.data.dimTableName;
					    	var win = $.window('维表详情',$.ctx + '/aibi_lc/pages/dimtable/dimtable_detail.html?dimTableName='+dimTableName, 900,
					    			600);
					    }
					});
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
	}
	
	//复合标签列数据类型
	for(var i = 0; i<dicBqlx.length; i++){
		if(dicBqlx[i].code!=10&&dicBqlx[i].code!=12&&dicBqlx[i].code!=11&&dicBqlx[i].code!=8){
			model.fhbqlx.push(dicBqlx[i]);
		}	
	}
	
	var dicspzt = $.getDicData("SPZTZD");
	for(var i = 0; i<dicspzt.length; i++){
		if(dicspzt[i].code!=3){
			model.spzt.push(dicspzt[i]); 
		}
	}	
	var dicsfzd = $.getDicData("SFZD");
	for(var i = 0; i<dicsfzd.length; i++){
		model.sfzd.push(dicsfzd[i]);
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
			var win = $.window('指标配置', $.ctx + '/aibi_lc/pages/label/sourceInfo_mgr.html?readCycle='+model.updateCycle, 900, 500);
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



function fun_to_save(){
	if($("form").validateForm()){
		var url_ = "";
		var url_fh = "";
		var msg = "";
		var savemda = "";
		if(model.labelId!=null && model.labelId!="" && model.labelId!=undefined){
			url_ = $.ctx + '/api/label/labelInfo/update';
			if(model.labelTypeId == 8){
				url_fh = $.ctx + '/api/label/mdaSysTableCol/update';
			}
			msg = "修改成功";
		}else{
			$("#labelId").removeAttr("name"),
			url_ = $.ctx + '/api/label/labelInfo/save';
			if(model.labelTypeId == 8){
				url_fh = $.ctx + '/api/label/mdaSysTableCol/save';
			}
			msg = "保存成功";
		}
		$.commAjax({
			url : url_,
			postData : $('#saveDataForm').formToJson(),
			onSuccess : function(data){
				var labelId = data.data.labelId;
				if(url_fh !="" && labelId!=""){
					var k=0;
					$("form[class~=create-main-col]").each(function(){
						var mdaSysTableColumn = $(this).formToJson();
						/*if(mdaSysTableColumn['columnId']==""){
							delete mdaSysTableColumn['columnId']
						}*/
						mdaSysTableColumn["labelId"]=labelId;
						$.commAjax({
							ansyc : false,
							url : url_fh,
							postData : mdaSysTableColumn,
							onSuccess : function(data){
								savemda = data.status
								if(k==$("form[class~=create-main-col]").size() && savemda== 200){
									$.success(msg,function(){
										history.back(-1);
									});
								};
							}
						});
						k++;
					})
				}else{
					$.success(msg,function(){
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
