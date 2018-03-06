var model = {
		labelName : "",
		updateCycle:"",
		orgId : "",
		tacticsId : "",
		busiLegend:"",	
		configId : "",
		dataDate:"",
		dayLabelDate : "",
		monthLabelDate : "",
		dataName:"",//推送周期
		sysName:"",//推送系统
		curentIndex:false,//radio选中
		isActive:false,//check选中
		customRule:"",//客户群规则
		haveAttr:false,
		customNum:"",//客户群人数
		calcuElementName:"",//已选属性名称(字符串)
		calcuElementNames:[],//已选属性名称
		AttrbuteId : "",//推送的属性标签ID
		labelOptRuleShow:"",//枚举标签选择的条件
		sortAttrAndType:"",//排序的属性和类型（asc,desc）
		
}
window.loc_onload = function() {
	$("#dialog").dialog({
	    autoOpen: false,
	    title:"推送设置",
	    modal: true,
	    open:function(){
	      	$(".form-horizontal").show();
	      	new Vue({
		      	el : '#dialog',
				data : model,
				methods:{
					radioSelect:function(index){
						model.curentIndex=index;
					},
					checkSelect:function(item){
						if(typeof item.isActive=='undefined'){
							this.$set(item,"isActive",true)
						}else if(typeof item.isActive!='undefined'){
							item.isActive=!item.isActive;
						}
					}
				}
	      	});
	      	model.dataName=$.getDicData("GXZQZD");
	    }
    });
	var labelId = $.getUrlParam("labelId");
	var configId = $.getCurrentConfigId();
	model.configId = configId;
	$.commAjax({
		postData : {
			"configId": configId,
			"labelId": labelId,
		},
		url : $.ctx + '/api/label/labelInfo/get',
		onSuccess : function(data) {
			model.labelName = data.data.labelName;
			model.updateCycle = data.data.updateCycle;
			model.orgId = data.data.orgId;
			model.tacticsId = data.data.tacticsId;
			model.busiLegend = data.data.busiLegend;
			model.dataDate = data.data.dataDate;
			model.dayLabelDate = data.data.dayLabelDate;
			model.monthLabelDate = data.data.monthLabelDate;
			model.customNum = data.data.labelExtInfo.customNum;
			model.labelOptRuleShow = data.data.labelExtInfo.labelOptRuleShow;
		}
	})
	$.commAjax({
		postData : {
			"customGroupId": labelId,
		},
		url : $.ctx + '/api/label/labelInfo/findCustomRuleById',
		onSuccess : function(data) {
			model.customRule=data.data;
			for (var i=0;i<model.customRule.length;i++){
				var html="";
        		if(model.customRule[i].elementType ==2){
        			var leftZoneSign, rightZoneSign;
    					if(model.customRule[i].leftZoneSign == ">="){
    						leftZoneSign = "大于等于";
    					}
    					if(model.customRule[i].leftZoneSign == ">"){
    						leftZoneSign= "大于";
    					}
    					if(model.customRule[i].rightZoneSign == "<="){
    						rightZoneSign= "小于等于";
    					}
    					if(model.customRule[i].rightZoneSign == "<"){
    						rightZoneSign= "小于";
    					}
        			if(model.customRule[i].labelTypeId ==4){
        				if(model.customRule[i].contiueMinVal!="" &&model.customRule[i].contiueMaxVal!=""&&model.customRule[i].contiueMinVal!=null &&model.customRule[i].contiueMaxVal!=null){
            				html = "<div class='ui-custom-item clearfix clearfix' ><a><span> "+model.customRule[i].attrName+"</span></a><a><span>数值范围:"+leftZoneSign+model.customRule[i].contiueMinVal+"且"+rightZoneSign+model.customRule[i].contiueMaxVal+"</span></div>"
        				}
        				if(model.customRule[i].contiueMinVal!="" &&model.customRule[i].contiueMaxVal!=""&&model.customRule[i].contiueMinVal!=null &&model.customRule[i].contiueMaxVal!=null){
            				html = "<div class='ui-custom-item clearfix clearfix' ><a><span> "+model.customRule[i].attrName+"</span></a><a><span>数值范围:"+leftZoneSign+model.customRule[i].contiueMinVal+"且"+rightZoneSign+model.customRule[i].contiueMaxVal+"</span></div>"
            			}
            			if(model.customRule[i].contiueMinVal==null &&model.customRule[i].contiueMaxVal!=null){
            				html = "<div class='ui-custom-item clearfix clearfix' ><a><span> "+model.customRule[i].attrName+"</span></a><a><span>数值范围:"+rightZoneSign+model.customRule[i].contiueMaxVal+"</span></div>"
            			}
            			if(model.customRule[i].contiueMinVal!=null &&model.customRule[i].contiueMaxVal==null){
            				html = "<div class='ui-custom-item clearfix clearfix' ><a><span> "+model.customRule[i].attrName+"</span></a><a><span>数值范围:"+leftZoneSign+model.customRule[i].contiueMinVal+"</span></div>"
            			}
            			if(model.customRule[i].exactValue!= ""){
            				html = "<div class='ui-custom-item clearfix clearfix' ><a><span> "+model.customRule[i].attrName+"</span></a><a><span>精确值:"+model.customRule[i].exactValue+"</span></div>"
            			}
        			}
        			else if(model.customRule[i].labelTypeId== 1){//01型
        				if(model.customRule[i].labelFlag != 1){
        					html = "<div class='ui-custom-item clearfix clearfix' ><a><span> "+model.customRule[i].attrName+"</span></a><a><span>"+"否"+"</span></a></div>"
        				}else{
        					html = "<div class='ui-custom-item clearfix clearfix' ><a><span> "+model.customRule[i].attrName+"</span></a><a><span>"+"是"+"</span></a></div>"
        				}
        			}
        			else if(model.customRule[i].labelTypeId== 5||model.customRule[i].labelTypeId== 9||model.customRule[i].labelTypeId== 12){//枚举
        				html = "<div class='ui-custom-item clearfix clearfix' ><a><span> "+model.customRule[i].attrName+"</span></a><a><span>已选择条件:"
        				if(model.customRule[i].attrVal){
        					var attrVals = model.customRule[i].attrVal.split(",");
        					var dimtableName;
        					$.commAjax({			
							    url : $.ctx+'/api/label/labelInfo/getDimtableName',
							    dataType : 'json', 
							    async : false,
							    postData : {"labelId" : model.customRule[i].calcuElement},
							    onSuccess: function(data){ 
							    	dimtableName =data.data;
						    	}  
						   });
        					for (var j=0;j<attrVals.length;j++){
	        					$.commAjax({			
								    url : $.ctx+'/api/dimtabledata/queryPage',
								    dataType : 'json', 
								    async : false,
								    postData : {
								    	"dimTableName" : dimtableName,
								    	"dimKey" : attrVals[j],
								    	},
								    onSuccess: function(data){ 
								    	if(j ==attrVals.length-1){
								    		html += data.rows[0].dimValue
								    	}else{
								    		html += data.rows[0].dimValue+",";
								    	}
							    	}  
							   });
        					};
        					html += "</span></a></div>"
        				}
        			}
        			else if(model.customRule[i].labelTypeId == 7){//文本型
        				if(model.customRule[i].darkValue){
        					html = "<div class='ui-custom-item clearfix clearfix' ><a><span> "+model.customRule[i].attrName+"</span></a><a><span>模糊值:"+model.customRule[i].darkValue+"</span></div>"
        				}
        				if(model.customRule[i].exactValue){
        					html = "<div class='ui-custom-item clearfix clearfix' ><a><span> "+model.customRule[i].attrName+"</span></a><a><span>精确值:"+model.customRule[i].exactValue+"</span></div>"
        				}
        			}
        			else if(model.customRule[i].labelTypeId == 6){//6=日期型
        				if(model.customRule[i].startTime || model.customRule[i].endTime){
        					html = "<div class='ui-custom-item clearfix clearfix' ><a><span> "+model.customRule[i].attrName+"</span></a><a><span>取值范围:"+leftZoneSign+model.customRule[i].startTime
        					}
        					if(model.customRule[i].endTime){
        						html += "且"+rightZoneSign+model.customRule[i].endTime
        					}
        					if(model.customRule[i].isNeedOffset == 1){
        						html += "（动态偏移更新）"
        					}
        					html +="</span></div>";
        				if(model.customRule[i].exactValue){
        					//attrValStr += "已选择条件：";
        					var exactStr = model.customRule[i].exactValue.split(",");
        					html = "<div class='ui-custom-item clearfix clearfix' ><a><span> "+model.customRule[i].attrName+"</span></a><a><span>精确值:"
        					if(exactStr && exactStr[0] != "-1"){
        						html += exactStr[0];
        						html += "年";
        					}
        					if(exactStr && exactStr[1] != "-1"){
        						html += exactStr[1];
        						html += "月";
        					}
        					if(exactStr && exactStr[2] != "-1"){
        						html += exactStr[2];
        						html += "日";
        					}
        					html+="</span></div>"
        				}
        			}
        		$("#labelDetail").append(html);
        		}
			}
		}
	})
	new Vue({
		el : '#dataD',
		data : model,
		methods:{
			previewDialog:function () {
				var wd = $.window('客户群预览', $.ctx
						+ '/aibi_lc/pages/custom/custom_preview.html?labelId='+$.getUrlParam("labelId"), 900,
						600);
				wd.reload = function() {
					$("#mainGrid").setGridParam({
						postData : $("#formSearch").formToJson()
					}).trigger("reloadGrid", [ {
						page : 1
					} ]);
				}
			},
			showDialog:function(){
				model.sortAttrAndType="";
				$(".selectList").each(function(){
					if(model.sortAttrAndType.indexOf($(this).find(".select-Sort").val()) !=-1 || $(this).find("label[class~=active]").text() ==null ||$(this).find("label[class~=active]").text()==""){
						model.sortAttrAndType=null;
						$.alert("请正确选择排序属性及方式");
						return false;
					}else{
						model.sortAttrAndType+=$(this).find(".select-Sort").val()+","+$(this).find("label[class~=active]").text()+";";
					}
				})
				if(model.sortAttrAndType!=null){
					$("#dialog").dialog({
		    		autoOpen: true,
		    		width: 550,
		    		buttons: [
			    	    {
			    	       text: "取消",
			    	       "class":"ui-btn ui-btn-second",
			    	        click: function() {
			    	        	$( this ).dialog( "close" );
			    	     	}
				  	    },
				  	    {
			    	        text: "确定",
			    	        "id":"add-dialog-btn",
			    	        "class":"ui-btn ui-btn-default",
			    	        click: function() {
			    	        	$( this ).dialog( "close" );
			    	        	if($("#checkboxList label[class~=active]").length ==0){
			    	        		$.alert("请正确选择推送平台");
			    	        	}else{
			    	        		if(!model.haveAttr){
			    	        			model.sortAttrAndType =null;
			    	        		}
			    	        		for(var i=0;i<$("#checkboxList label[class~=active]").length;i++){
										var sysId = $("#checkboxList label[class~=active]")[i].htmlFor;
										$.commAjax({			
										    url : $.ctx+'/api/syspush/labelPushCycle/save',
										    dataType : 'json', 
										    async : false,
										    postData : {
													"customGroupId" :labelId,
													"sysId" :sysId,
													"pushCycle" :$("#radioList label[class~=active]").siblings("input").val(),
													"AttrbuteId" :model.AttrbuteId,
													"sortAttrAndType" :model.sortAttrAndType,
												},
										    maskMassage : '推送中...'
									   });
										if(i == $("#checkboxList label[class~=active]").length-1){
											$.success("推送设置成功",function(){
												//history.back(-1);
											});
										}
									}
			    	        	}
			    	        }
				    	}
			  		]
		    	});
		    	$.commAjax({
		    		url: $.ctx + "/api/syspush/sysInfo/queryList",
		    		onSuccess:function(data){
		    			model.sysName=data.data;
		    		}
		    	})
		    	}
			},
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
	var nameNumber=0;
	$(".selectBox").delegate(".selectList .radio","click",function(){
		var e=arguments.callee.caller.arguments[0]||event; 
	    if (e && e.stopPropagation) {			     
	    e.stopPropagation();
	    } else if (window.event) {
	      window.event.cancelBubble = true;
	    }
		if($(this).find("label").hasClass("active")){				
			$(this).siblings(".radio").find("label").removeClass("active");
			$(this).siblings(".radio").find("input").prop("checked", false);
		}else{
			$(this).find("label").addClass("active");
			$(this).find("input").prop("checked", true);
			$(this).siblings(".radio").find("label").removeClass("active");
			$(this).siblings(".radio").find("input").prop("checked", false);
		}
	})

	$("#fun_to_add").click(function(){
		if($(".selectList").length>2){
			$.alert("只能添加三行");
		}else{
			var html ="<div class='mt20 selectList'> <div class='form-group mr100 optionhtml'> <div class=''><select class='form-control input-pointer select-Sort' name='dataStatusId' ></select></div></div><div class='radio circle success'> <input type='radio' name='radio"+nameNumber+"' id='myr'> <label for='myr'><i class='default'></i>升序</label</div></div><div class='radio circle success'><input type='radio' name='radio"+nameNumber+++"'  id='myr1'> <label  for='myr1'><i class='default'></i>降序</label></div></div> ";
			$("#addALine").append(html);
			$(".optionhtml").find("select").html("");
			for(var i=0;i<model.calcuElementNames.length;i++){
				var option = "<option>"+model.calcuElementNames[i]+"</option>";
				$(".optionhtml").find("select").append(option);
			}
		}
	})
	//标签体系
	labeltree();
	function labeltree(){
		var ztreeObj;
		$.commAjax({			
		    url : $.ctx+'/api/label/categoryInfo/queryList',  		    
		    dataType : 'json', 
		    async:true,
		    postData : {
					"sysId" :configId,
				},
		    onSuccess: function(data){ 		    			    			    	
			    	var ztreeObj=data.data;
			    	$.fn.zTree.init($("#ztree"), install, ztreeObj);
		    	}  
	   });
		install = {
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
				onClick: dataClick
			}
		}
	}
	function dataClick(event, treeId, treeNode){
		$.commAjax({			
		    url : $.ctx + "/api/label/labelInfo/queryPage", 
		    dataType : 'json', 
		    async:true,
		    postData : {
					"configId" :configId,
					"categoryId" :treeNode.categoryId,
					"groupType" :0,
					"dataStatusId":2,
				},
		    onSuccess: function(data){
		    	$("#OptionalLabel").html("");
		    	for(var i=0;i<data.rows.length;i++){
		    		if(model.AttrbuteId =="" || model.AttrbuteId.indexOf(data.rows[i].labelId) == -1){
			    		html="<li>"+
			    		"<div class='checkbox'>"+
			    		"<input type='checkbox' id='"+data.rows[i].labelId+"L' class='checkbix'>"+
			    		"<label for='"+data.rows[i].labelId+"L' aria-label role='checkbox' class='checkbix' data-id='"+data.rows[i].labelId+"L' data-name='"+data.rows[i].labelName+"'>"+
			    		"<span class='large'></span>"+
			    		data.rows[i].labelName+
			    		"</label>"+
			    		"</div>"+
			    		"</li>";
			    		$("#OptionalLabel").append(html);
		    		}
		    	}
		    },
		    maskMassage : '搜索中...'
	   });
	};
	$('#labelName').keyup(function(event){
    	if(event.keyCode == 13){
    		$("#btn_search").click();
    	}
    });
	
    $("#btn_search").click(function() {
    	var txt = $("#labelName").val();
    	$.commAjax({			
		    url : $.ctx+"/api/label/labelInfo/queryPage", 
		    dataType : 'json', 
		    async:true,
		    postData : {
		    		"labelName":txt,
					"configId" :configId,
					"groupType" :0,
					"dataStatusId":2,
				},
		    onSuccess: function(data){
		    	$("#OptionalLabel").html("");
		    	for(var i=0;i<data.rows.length;i++){
		    		html="<li>"+
		    		"<div class='checkbox'>"+
		    		"<input type='checkbox' id='"+data.rows[i].labelId+"L' class='checkbix'>"+
		    		"<label for='"+data.rows[i].labelId+"L' aria-label role='checkbox' class='checkbix' data-id='"+data.rows[i].labelId+"L' data-name='"+data.rows[i].labelName+"'>"+
		    		"<span class='large'></span>"+
		    		data.rows[i].labelName+
		    		"</label>"+
		    		"</div>"+
		    		"</li>";
		    		$("#OptionalLabel").append(html);
		    	}
		    },
		    maskMassage : '搜索中...'
	   });
    });
    $("#selectedLabel").delegate("input","click",function(){//右边
		if($(this).siblings("label").hasClass("active")){
			$(this).siblings("label").removeClass("active");
			$(this).prop("checked", false);
		}else{
			$(this).siblings("label").addClass("active");
			$(this).prop("checked", true);
		}
	})
    $("#custom_left").click(function(){
    	var attrId ="",attrName="";
		$("#selectedLabel label[class~=active]").each(function(){
			$("#addALine").html("");
			attrId = $(this).attr('data-id').substring(0,$(this).attr('data-id').length-1);
			attrName = $(this).attr('data-name');
			if(model.AttrbuteId.indexOf(attrId)!=0){
				model.AttrbuteId = model.AttrbuteId.replace(","+attrId,"");
				model.calcuElementName = model.calcuElementName.replace(","+attrName,"");
			}else{
				if(model.AttrbuteId.indexOf(",")!=-1){
					model.AttrbuteId = model.AttrbuteId.replace(attrId+",","");
					model.calcuElementName = model.calcuElementName.replace(attrName+",","");
				}else{
					model.AttrbuteId = model.AttrbuteId.replace(attrId,"");
					model.calcuElementName = model.calcuElementName.replace(attrName,"");
				}
			}
			model.calcuElementNames=model.calcuElementName.split(",");
			var  html="<li>"+
    		"<div class='checkbox'>"+
    		"<input type='checkbox' id='"+attrId+"L' class='checkbix'>"+
    		"<label for='"+attrId+"L' aria-label role='checkbox' class='checkbix' data-id='"+attrId+"L' data-name='"+attrName+"'>"+
    		"<span class='large'></span>"+
    		attrName+
    		"</label>"+
    		"</div>"+
    		"</li>";
			$("#OptionalLabel").append(html);
			$("#selectedLabel label[class~=active]").parents("li").remove();
		})
		if(attrId== ""){
			$.alert("请选择标签");
		}
    })
    $("#OptionalLabel").delegate("input","click",function(){//左边
		if($(this).siblings("label").hasClass("active")){
			$(this).siblings("label").removeClass("active");
			$(this).prop("checked", false);
		}else{
			$(this).siblings("label").addClass("active");
			$(this).prop("checked", true);
		}
	})
    $("#custom_right").click(function(){
    	var attrId ="",attrName="";
		$("#OptionalLabel label[class~=active]").each(function(){
			$("#addALine").html("");
			attrId = $(this).attr('data-id').substring(0,$(this).attr('data-id').length-1);
			attrName = $(this).attr('data-name');
			if(model.AttrbuteId!=""){
				model.AttrbuteId +=","+attrId;
				model.calcuElementName +=","+attrName;
			}else{
				model.AttrbuteId +=attrId;
				model.calcuElementName +=attrName;
			}
			model.calcuElementNames=model.calcuElementName.split(",");
			var  html="<li>"+
    		"<div class='checkbox'>"+
    		"<input type='checkbox' id='"+attrId+"R' class='checkbix'>"+
    		"<label for='"+attrId+"R' aria-label role='checkbox' class='checkbix' data-id='"+attrId+"R' data-name='"+attrName+"'>"+
    		"<span class='large'></span>"+
    		attrName+
    		"</label>"+
    		"</div>"+
    		"</li>";
			$("#selectedLabel").append(html);
			$("#OptionalLabel label[class~=active]").parents("li").remove();
		})
		if(attrId== ""){
			$.alert("请选择标签");
		}
    })
}