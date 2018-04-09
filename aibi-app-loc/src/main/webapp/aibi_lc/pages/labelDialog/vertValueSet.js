var ruleDataModel = {
		rule : {},
		labelVerticalColumnRelList : [],
		ruleIndex : -1,
		showDivColumnId : '' //默认展示的div序列号
}
/**
 * 需要注意：
 * 1.纵表列和选中的子规则并不对应
 */
window.loc_onload = function() {
	var wd = frameElement.lhgDG;
	var ruleIndex = $.getUrlParam("index");
	ruleDataModel.rule = wd.curWin.calculateCenter.getDialogRuleValue(ruleIndex);
	ruleDataModel.ruleIndex = ruleIndex;
	//获取数据rule
	wd.addBtn("ok", "确定", function() {
		if(vertValueRule.validateForm()){
			vertValueRule.setVertValue();
		}
		
	});
	
	wd.addBtn("cancel", "取消", function() {
		wd.cancel();
	});
	//初始化数据
	vertValueRule.initData(); 
	/**
     * ------------------------------------------------------------------
     * 数值标签修改
     * ------------------------------------------------------------------
     */
    var ruleApp = new Vue({
    	el : '#vertValueSet',
    	data : ruleDataModel,
		mounted: function () {
			//初始化数据
		    this.$nextTick(function () {
			    var r = $(".easyui-validatebox");
	   			if (r.length){
	   				r.validatebox();
	   			}
	   			vertValueRule.initMethod();
		    })
		}
    });
    
    
}

/**
 * ------------------------------------------------------------------
 * 指标标签
 * ------------------------------------------------------------------
 */
var vertValueRule = (function (model){

	//开发版本号
    model.version = "1.0.0";
    /**
     * 初始化数据
     * 默认需要vue监控的属性，给予默认值，否则初始化后，再加入不受vue控制，改变数据视图不会同步更新
     */
    model.initData = function(){
    	//舒适化纵表标签
    	$.commAjax({
			  url: $.ctx + "/api/shopCart/findVerticalLabel",
			  async	: false,//同步
			  postData:{
				  labelId : ruleDataModel.rule.calcuElement
			  },
			  onSuccess: function(returnObj){
				  ruleDataModel.labelVerticalColumnRelList = returnObj.data.ciLabelVerticalColumnRelList;
			  }
		});
    	//初始化数据,事件
		for(var i=0 ; i< ruleDataModel.labelVerticalColumnRelList.length; i++ ){
			var item = ruleDataModel.labelVerticalColumnRelList[i];
			var labelTypeId 		= item.labelTypeId;
			var dimTransId 				= item.mdaSysTableColumn.dimTransId;
			var childCalcuElementId = item.labelVerticalColumnRelId.columnId;
			var unit 				= item.unit;
			var childCalcEl			= model.getChildLabel(childCalcuElementId);//获取设置的值
			var isExist 			= false;
			if(childCalcEl){
				isExist = true ;
				item.rule = childCalcEl;
			}else{
				item.rule = {};
			}
			
			if(labelTypeId == 1){
				
			}else if(labelTypeId == 4) {
				var queryWay = 1;
				if(isExist) {//有可能没有设置值
					queryWay = item.rule.queryWay ;
					if(queryWay == 1) {
						if(item.rule.leftZoneSign){
							if(item.rule.leftZoneSign.indexOf("=")>=0){
								item.leftClosed = true;
							}
						}
						if(item.rule.rightZoneSign){
							if(item.rule.rightZoneSign.indexOf("=")>=0){
								item.rightClosed = true;
							}
						}
					}
				}
				//初始化参数
				if(!item.rule.queryWay){
					item.rule.queryWay = 1;
				}
			} else if(labelTypeId == 5) {
				item.itemChooseListSrc = [];//默认增加属性，提供vue监控
				item.itemChooseList = [];//默认增加属性，提供vue监控
				if(isExist) {
					var attrVal 	= childCalcEl.attrVal;
					var attrName 	= childCalcEl.attrName;
					var itemKeyArr 	= [];
					var itemNameArr = [];
					if(attrVal) {
						itemKeyArr = attrVal.split(',');
						itemNameArr = attrName.split(',');
						for ( var i = 0, len; i < itemKeyArr.length; i++) {
							model.addEnumItem(item,itemKeyArr[i],itemNameArr[i]);
						}
					}
				}
			} else if(labelTypeId == 6) {
				//初始化参数
		    	if(!item.rule.queryWay){
		    		item.rule.queryWay = 1;
		    	}
				if(isExist) {
					if(item.rule.queryWay == "1"){
						if(item.rule.leftZoneSign){
				    		if(item.rule.leftZoneSign.indexOf("=")>=0){
				    			item.leftClosed = true;
				    		}
				    	}
				    	if(item.rule.rightZoneSign){
				    		if(item.rule.rightZoneSign.indexOf("=")>=0){
				    			item.rightClosed = true;
				    		}
				    	}
				    	if(item.rule.isNeedOffset == 1) {
				    		item.dynamicUpdate = 1;
						}
				    	
					}else{
						//精确值
						if(item.rule.exactValue){
							var itemValueArr = item.rule.exactValue.split(",");
							if(itemValueArr.length == 3){
								var exactValueDateYear = itemValueArr[0];
								var exactValueDateMonth = itemValueArr[1];
								var exactValueDateDay =itemValueArr[2];
								if(exactValueDateYear && exactValueDateYear != "-1"){
									item.exactValueDateYear = exactValueDateYear;
								}
								if(exactValueDateMonth && exactValueDateMonth != "-1"){
									item.exactValueDateMonth = exactValueDateMonth;
								}
								if(exactValueDateDay && exactValueDateDay != "-1"){
									item.exactValueDateDay = exactValueDateDay;
								}
							}
						}
					}
				}
				
				
			} else if(labelTypeId == 7) {
				
				//初始化参数
				if(!item.rule.queryWay){
					item.rule.queryWay = 1;
				}
			}
		}
		
    }
    /***
     * 初始化事件，事件必须在vue和数据创建之后
     */
    model.initMethod = function(){
    		//默认展示
    		model.showTabDiv(ruleDataModel.labelVerticalColumnRelList[0],0) ;
	    	//初始化事件
			for(var i=0 ; i< ruleDataModel.labelVerticalColumnRelList.length; i++ ){
				var item = ruleDataModel.labelVerticalColumnRelList[i];
				var labelTypeId 		= item.labelTypeId;
				var dimTransId 				= item.mdaSysTableColumn.dimTransId;
				var childCalcuElementId = item.labelVerticalColumnRelId.columnId;
				if(labelTypeId == 5){//初始化分页grid
					vertValueRule.initDimtabledataPage(childCalcuElementId,dimTransId);
				}else if(labelTypeId == 6){
					if(item.rule.startTime) {
				    	$('#startTime'+childCalcuElementId).val(item.rule.startTime);
					}
			    	if(item.rule.endTime) {
			    		$('#endTime'+childCalcuElementId).val(item.rule.endTime);
					}
			    	if(item.rule.queryWay == "1"){
						
						$("#exactValueDateYear"+ childCalcuElementId).attr("disabled","disabled");
						$("#exactValueDateMonth"+ childCalcuElementId).attr("disabled","disabled");
						$("#exactValueDateDay"+ childCalcuElementId).attr("disabled","disabled");
						
						$("#startTime"+ childCalcuElementId).removeAttr("disabled");
						$("#endTime"+ childCalcuElementId).removeAttr("disabled");
						$("#dynamicUpdate"+ childCalcuElementId).removeAttr("disabled");
						$("#leftClosed"+ childCalcuElementId).removeAttr("disabled");
						$("#rightClosed"+ childCalcuElementId).removeAttr("disabled");
					}else{
						$("#startTime"+ childCalcuElementId).attr("disabled","disabled");
						$("#endTime"+ childCalcuElementId).attr("disabled","disabled");
						$("#dynamicUpdate"+ childCalcuElementId).attr("disabled","disabled");
						$("#leftClosed"+ childCalcuElementId).attr("disabled","disabled");
						$("#rightClosed"+ childCalcuElementId).attr("disabled","disabled");
						
						$("#exactValueDateYear"+ childCalcuElementId).removeAttr("disabled");
						$("#exactValueDateMonth"+ childCalcuElementId).removeAttr("disabled");
						$("#exactValueDateDay"+ childCalcuElementId).removeAttr("disabled");
					}
				}
				
			}
			$("input[name^=queryWay6]").click(function(){
				var name = this.name ;
				var childCalcuElementId = this.name.substring('queryWay6'.length,name.length);
   		    	if($(this).val()==1){
   		    		$("#exactValueDateYear"+ childCalcuElementId).attr("disabled","disabled");
					$("#exactValueDateMonth"+ childCalcuElementId).attr("disabled","disabled");
					$("#exactValueDateDay"+ childCalcuElementId).attr("disabled","disabled");
   					$("#startTime"+childCalcuElementId).removeAttr("disabled");
   					$("#endTime"+childCalcuElementId).removeAttr("disabled");
   					$("#dynamicUpdate"+childCalcuElementId).removeAttr("disabled");
   					$("#leftClosed"+childCalcuElementId).removeAttr("disabled");
   					$("#rightClosed"+childCalcuElementId).removeAttr("disabled");
   		    	}else{
   		    		$("#startTime"+childCalcuElementId).attr("disabled","disabled");
   					$("#endTime"+childCalcuElementId).attr("disabled","disabled");
   					$("#dynamicUpdate"+childCalcuElementId).attr("disabled","disabled");
   					$("#leftClosed"+childCalcuElementId).attr("disabled","disabled");
   					$("#rightClosed"+childCalcuElementId).attr("disabled","disabled");
   					$("#exactValueDateYear"+ childCalcuElementId).removeAttr("disabled");
					$("#exactValueDateMonth"+ childCalcuElementId).removeAttr("disabled");
					$("#exactValueDateDay"+ childCalcuElementId).removeAttr("disabled");
   		    	}
   			});
			//导航切换选择方式
	    	$("input[name^=queryWay7]").click(function(){
		    	if($(this).val()==1){
		    		$('#darkValue'+childCalcuElementId).removeAttr('disabled');
		    		$('#exactValue'+childCalcuElementId).attr('disabled',true);
		    	}else{
		    		$('#exactValue'+childCalcuElementId).removeAttr('disabled');
		    		$('#darkValue'+childCalcuElementId).attr('disabled',true);
		    	}
		    	
			});
    	
    }
    /**
     * 判断是否已经存在对应的子标签，如果存在则对div属性进行重新修改，如果不存在则新加div
     * 获取计算中心中当前标签的子标签
     */
    model.getChildLabel = function(childLabelId){
    	var result;
    	var childLabelRuleList = ruleDataModel.rule.childLabelRuleList;
    	if(childLabelRuleList){
    		for(var i=0; i<childLabelRuleList.length; i++) {
    			var el = childLabelRuleList[i];
    			if(el.calcuElement == childLabelId) {
    				result = el;
    				break;
    			}
    		}
    	}
		return result;
    }
    /**
	 * 展示标签设置tab
	 */
	model.showTabDiv = function (item,index){
		var id = item.labelTypeId + item.labelVerticalColumnRelId.columnId ;
		ruleDataModel.showDivColumnId = 'labelElement' + id ;
		$('#switchImportWayBox  li').removeClass('current');
		$('#switchImportWayBox  li[index='+index+']').addClass('current');
	}
    /**************
	 * 分页查询枚举
	 */
	model.initDimtabledataPage = function(gridId,dimTransId){
		$("#mainGrid"+gridId).jqGrid({
	        url: $.ctx + "/api/dimtabledata/queryPageById",
	        postData:{
				  "dimKey": '',
	              "dimValue": '',
	              "dimTransId" : dimTransId
			},
	        datatype: "json",
	        colNames : [ '','选项'],
	        colModel: [{
	            name: 'dimKey',
	            index: 'dimKey',
	            width: 2,
	            hidden: true
	        },{
	        	name: 'dimValue',
	            index: 'dimValue',
	            sortable: false,
	            width: 310,
	            align: "center"
	        }],
	        rowList: [10, 20, 30],
	        pager: '#mainGridPager'+gridId,
	        // 分页的id
	        viewrecords: true,
	        width :'100%',
	        autowidth:true,
	    	/**
	    	 * 添加
	    	 */
	        onSelectRow: function(ids) { //单击选择行  
	        	var id = this.id ;
	        	var columnId = id.substring("mainGrid".length,id.length);
                var rodData= $("#"+id).jqGrid("getRowData",ids);
                var id=rodData.dimKey;
                var name=rodData.dimValue;
                var item = {};
              //初始化数据,事件
        		for(var i=0 ; i< ruleDataModel.labelVerticalColumnRelList.length; i++ ){
        			var temp = ruleDataModel.labelVerticalColumnRelList[i];
        			if(columnId == ruleDataModel.labelVerticalColumnRelList[i].labelVerticalColumnRelId.columnId){
        				item = ruleDataModel.labelVerticalColumnRelList[i];
        			}
        		}
        		if(item){
        			model.addEnumItem(item,id,name);
        		}
            }
	        
	    });
		$("#mainGrid"+gridId).jqGrid('setLabel',0, '序号');
	};
	/**************
	 * 分页查询枚举
	 */
	model.searchGrid = function(item){
		var columnId = item.labelVerticalColumnRelId.columnId;
		var dimTransId = item.mdaSysTableColumn.dimTransId;
		
		$("#mainGrid"+columnId).setGridParam({
            postData: {
                "dimKey": '',
                "dimValue": item.dimValueSearch,
                "dimTransId" : dimTransId
            },
            dataType : 'json'
        }).trigger("reloadGrid", [{
            page: 1
        }]);
	}
	/**
	 * 查询已经选择的条件
	 * item.itemChooseListSrc,添加后的结果集
	 * item.itemChooseList ，代表展示在页面上的结果
	 */
	model.searchChooseEnum = function(item,index){
		//该种赋值避免引用相同,将原数据赋值给临时值
		var temp = JSON.parse(JSON.stringify(item.itemChooseListSrc));
		for(var i=0;i<temp.length ;i++){
			//不包含则删除
			if(temp[i].dimValue.indexOf(item.itemChooseSearch)<0){
				temp.splice(i,1);
				i--;//下边恢复上一个，重新判断删除后的数组
			}
		}
		//该种赋值避免引用相同
		item.itemChooseList = JSON.parse(JSON.stringify(temp));
	}
	/**
	 * 枚举标签：添加
	 */ 
	model.addEnumItem = function(objModel,id,name){
		var flag = false;//不存在
		if(objModel.itemChooseListSrc){
			for(var i=0;i<objModel.itemChooseListSrc.length;i++){
				if(id == objModel.itemChooseListSrc[i].dimKey){
					flag = true;
				}
			}
		}else{
			objModel.itemChooseListSrc = [];
			objModel.itemChooseList = [];
		}
		if(!flag){
			var obj = {
					dimKey : id,
					dimValue : name
			}
			objModel.itemChooseListSrc[objModel.itemChooseListSrc.length] = obj;
			objModel.itemChooseCount = objModel.itemChooseListSrc.length;
			//该种赋值避免引用相同
			objModel.itemChooseList = JSON.parse(JSON.stringify(objModel.itemChooseListSrc));//赋值显示数据
			objModel.itemChooseSearch = '';//清空查询条件
		}
    }
	/**
	 * 全选添加
	 */ 
	model.addAllEnumItem = function(objModel,index){
		var indexNumber = Number(index);
		$.commAjax({
			  url: $.ctx + "/api/dimtabledata/queryPageById",
			  postData:{
				  "dimKey": '',
	              "dimValue": '',
	              "dimTransId" : objModel.mdaSysTableColumn.dimTransId
			  },
			  onSuccess: function(returnObj){
				  objModel.itemChooseListSrc = returnObj.rows ; 
				  objModel.itemChooseList = returnObj.rows;//赋值显示数据
				  objModel.itemChooseCount = objModel.itemChooseListSrc.length;
				  objModel.itemChooseSearch = '';//清空查询条件
				  ruleDataModel.labelVerticalColumnRelList[indexNumber] = objModel;
			  }
		});
	}
	
	/**
	 * 删除已选择的
	 */ 
	model.delEnumItem = function(item,indexDim){
		var itemDim = item.itemChooseList[indexDim];
		item.itemChooseList.splice(indexDim,1);
		for(var i=0;i<item.itemChooseListSrc.length ;i++){
			if(item.itemChooseListSrc[i].dimKey == itemDim.dimKey){
				item.itemChooseListSrc.splice(i,1);
				break;
			}
		}
		item.itemChooseCount = item.itemChooseListSrc.length;
	}
	/**
	 * 全选删除已选择的
	 */ 
	model.delEnumAllItem = function(objModel){
		objModel.itemChooseList = [];
		objModel.itemChooseListSrc = [];
		objModel.itemChooseCount = 0;
	}
	
    /**
     * 校验表单
     */
	model.validateForm = function(){
		var flag = true ;
		for(var i= 0 ; i < ruleDataModel.labelVerticalColumnRelList.length ;i++){
			var item = ruleDataModel.labelVerticalColumnRelList[i] ;
			if(item.labelTypeId == 1){
				var isMustColumn = item.isMustColumn;
				if(isMustColumn == 1 && item.rule.labelFlag == null){
					flag = false ;
					model.showTabDiv(item,i);
					break;
				}
			}if(item.labelTypeId == 4){
				if(!model.validateNumberForm(item)){
					flag = false ;
					model.showTabDiv(item,i);
					break;
				}
			}else if(item.labelTypeId == 5){
				if(!model.validateEnumForm(item)){
					flag = false ;
					model.showTabDiv(item,i);
					break;
				}
			}else if(item.labelTypeId == 6){
				if(!model.validateDateForm(item)){
					flag = false ;
					model.showTabDiv(item,i);
					break;
				}
			}else if(item.labelTypeId == 7){
				if(!model.validateTextForm(item)){
					flag = false ;
					model.showTabDiv(item,i);
					break;
				}
			}else{
				//
			}
		}
		return flag; 
	}
	/**
     * 校验表单
     */
	model.validateNumberForm = function(item){
		var queryWay= item.rule.queryWay;
		var isMustColumn = item.isMustColumn;
		if(queryWay == "1"){
			var contiueMinVal=$.trim(item.rule.contiueMinVal);
			var contiueMaxVal=$.trim(item.rule.contiueMaxVal);
			if(contiueMinVal == "输入下限"){
				contiueMinVal="";
			}
			if(contiueMaxVal == "输入上限") {
				contiueMaxVal="";
			}
			var contiueVal=contiueMinVal+contiueMaxVal;
			if(isMustColumn == 1 && $.trim(contiueVal) == ""){
				$.alert("请输入数值！");
				return false;
			}
			//var reg = /^(-?([1-9]\d*)|0)(\.\d+)?$/;
			var reg = /^(-?([1-9]\d{0,11})|0)(\.\d{1,4})?$/
			if(contiueMinVal !=""){
				if(!contiueMinVal.match(reg)){
					$.alert("请输入合法的数值！");
					return false;
				}
			}
			if(contiueMaxVal !=""){
				if(!contiueMaxVal.match(reg)){
					$.alert("请输入合法的数值！");
					return false;
				}
			}
			if(contiueMinVal !="" && contiueMaxVal !=""){
				if(Number(contiueMaxVal) < Number(contiueMinVal)){
					$.alert("数值范围上限不能小于下限！");
					return false;
				}
			}
		}else {
			var exactValue = $.trim(item.rule.exactValue);
			if(isMustColumn == 1 && exactValue == ""){
				$.alert("精确值不能为空！");
				return false;
			}
			//var reg = /^(-?([1-9]\d*)|0)(\.\d+)?(\,(-?([1-9]\d*)|0)(\.\d+)?)*$/;
			var reg = /^(-?([1-9]\d{0,11})|0)(\.\d{1,4})?(\,(-?([1-9]\d{0,11})|0)(\.\d{1,4})?)*$/;
			if(exactValue !=""){
				if(!exactValue.match(reg)){
					$.alert("请输入合法的数值！");
					return false;
				}
			}
		}
		
		return true;
	};
	/**
	 * 校验标签 枚举5,暂时没有校验
	 */
	model.validateEnumForm = function(item){
        return true;
    }
    /**
     * 校验标签 日期 6
     */
	model.validateDateForm = function(item){
		var queryWay= item.rule.queryWay;
		var isMustColumn = item.isMustColumn;
		var id = item.labelVerticalColumnRelId.columnId ; 
		if(queryWay == "1"){
			var startTime = $.trim($("#startTime"+id).val());
			var endTime= $.trim($("#endTime"+id).val());
			var timeStr=startTime+endTime;
			if(isMustColumn == 1 && $.trim(timeStr) == ""){
				$.alert("时间段至少一个不能为空");
				return false;
			}
		}else {
			var exactValueDateYear = $.trim(item.exactValueDateYear);
			var exactValueDateMonth = $.trim(item.exactValueDateMonth);
			var exactValueDateDay = $.trim(item.exactValueDateDay);
			var exactValueDate = exactValueDateYear+exactValueDateMonth+exactValueDateDay;
			
			if(exactValueDate == ""){
				$.alert("精确日期至少一个不能为空");
				return false;
			}
			//年
			if(exactValueDateYear !=""){
				if(isNaN(exactValueDateYear)){
					$.alert("年份格式不符合要求！");
					return false;
				}
			}else{
				exactValueDateYear = 1990;//给定年固定值
			}
			//月
			if(exactValueDateMonth !=""){
				if(isNaN(exactValueDateMonth)){
					$.alert("月份格式不符合要求！");
					return false;
				}
			}else{
				exactValueDateMonth = 7;//给定月固定值
			}
			//日
			if(exactValueDateDay !=""){
				if(isNaN(exactValueDateDay)){
					$.alert("日格式不符合要求！");
					return false;
				}
			} else {
				exactValueDateDay = 20;//给定日固定值
			}
			var year = parseInt(exactValueDateYear);   
			var month = parseInt(exactValueDateMonth, 10)-1;              
			var day = parseInt(exactValueDateDay, 10); 
			var date = new Date(year, month, day);       
			var y = date.getFullYear();              
			var m = date.getMonth();              
			var d = date.getDate();       
			if ((y != year) || (m != month) || (d != day)) {                  
				$.alert("请输入合法的日期！");
				return false;
			}  
		}
		
		return true;
	};
	/**
     * 校验文本类型的7
     */
	model.validateTextForm = function(item){
		var queryWay= item.rule.queryWay;
		var isMustColumn = item.isMustColumn;
		if(queryWay == "1"){
			var darkValue=$.trim(item.rule.darkValue);
			if(isMustColumn == 1 && $.trim(darkValue) == ""){
				$.alert("模糊值不能为空！");
				return false;
			}
		}else {
			var exactValue = $.trim(item.rule.exactValue);
			if(exactValue == ""){
				$.alert("精确值不能为空！");
				return false;
			}
			if(exactValue.indexOf(",") == 0 || exactValue.lastIndexOf(",") == exactValue.length-1 || exactValue.indexOf(",,") !=-1){
				$.alert("请输入合法的值！");
				return false;
			}
		}
		
		return true;
	};
	/**
	 * 设置指标标签 4
	 */
	model.setNumberValue = function(item){
			var contiueMinVal = "";
			var contiueMaxVal = "";
			var leftZoneSign="";
			var rightZoneSign="";
			var rule = item.rule;
			
			if(item.rule.queryWay == "1"){
				contiueMinVal=$.trim(rule.contiueMinVal);
				contiueMaxVal=$.trim(rule.contiueMaxVal);
				if(contiueMinVal == "输入下限"){
					contiueMinVal="";
				}
				if(contiueMaxVal == "输入上限") {
					contiueMaxVal="";
				}
				var leftChecked = item.leftClosed;
				if(leftChecked){
					leftZoneSign=">=";
				}else{
					leftZoneSign=">";
				}
				var rightChecked = item.rightClosed;
				if(rightChecked){
					rightZoneSign="<=";
				}else{
					rightZoneSign="<";
				}
				rule.contiueMinVal = contiueMinVal;
				rule.contiueMaxVal = contiueMaxVal;
				rule.leftZoneSign = leftZoneSign;
				rule.rightZoneSign = rightZoneSign;
				rule.exactValue = '';
			}else{
				rule.contiueMinVal = '';
				rule.contiueMaxVal = '';
				rule.leftZoneSign = '';
				rule.rightZoneSign = '';
			}
			return rule ;
	}
	/**
	 * 设置参数 5
	 */
	model.setEnumValue = function(item){
		var attrVals = "";
		var attrNames ="";
		var rule = item.rule;
		if(item.itemChooseListSrc){
			for(var i=0;i<item.itemChooseListSrc.length ;i++){
				if(i==0){
					attrVals +=item.itemChooseListSrc[i].dimKey;
					attrNames +=item.itemChooseListSrc[i].dimValue;
				}else{
					attrVals +=","+item.itemChooseListSrc[i].dimKey;
					attrNames +=","+item.itemChooseListSrc[i].dimValue;
				}
				
			}
			rule.attrVal = attrVals;
			rule.attrName = attrNames;
		}else{
			rule = null ;
		}
		
		return rule ;
	}
	/**
	 * 设置参数日期值，6
	 */
	model.setDateValue = function(item){
			var leftZoneSign="";
			var rightZoneSign="";
			var isNeedOffset = 0;
			var exactValueDate="";
			var rule = item.rule;
			var columnId = item.labelVerticalColumnRelId.columnId ; 
			if(rule.queryWay == "1"){
				var isNeedOffsetVal = item.dynamicUpdate;
				if(isNeedOffsetVal) {
					isNeedOffset = 1;	
				}
				var leftChecked = item.leftClosed;
				if(leftChecked){
					leftZoneSign=">=";
				}else{
					leftZoneSign=">";
				}
				var rightChecked= item.rightClosed;
				if(rightChecked){
					rightZoneSign="<=";
				}else{
					rightZoneSign="<";
				}
				var startTime = $.trim($("#startTime"+columnId).val());
				var endTime= $.trim($("#endTime"+columnId).val());
				rule.startTime = startTime;
				rule.endTime = endTime;
				rule.leftZoneSign = leftZoneSign;
				rule.rightZoneSign = rightZoneSign;
				rule.isNeedOffset = isNeedOffset;
				rule.exactValue = '';
			}else{
				rule.startTime = '';
				rule.endTime = '';
				rule.leftZoneSign = '';
				rule.rightZoneSign = '';
				rule.isNeedOffsetVal = '';
				var exactValueDateYear = $.trim(item.exactValueDateYear);
				var exactValueDateMonth = $.trim(item.exactValueDateMonth);
				var exactValueDateDay = $.trim(item.exactValueDateDay);
				if(exactValueDateYear && exactValueDateYear !=""){
					exactValueDate=exactValueDateYear;
				}else{
					exactValueDate="-1";
				}
				if(exactValueDateMonth && exactValueDateMonth !=""){
					exactValueDate += ","+exactValueDateMonth;
				}else{
					exactValueDate += ",-1";
				}
				if(exactValueDateDay && exactValueDateDay !=""){
					exactValueDate += ","+exactValueDateDay;
				}else{
					exactValueDate += ",-1";
				}
				rule.exactValue = exactValueDate;
			}
			return rule ;
	}
	/**
	 * 设置参数
	 */
	model.setVertValue = function(){
			var childrenRule = new Array();
			for(var i= 0 ; i < ruleDataModel.labelVerticalColumnRelList.length ;i++){
				var item = ruleDataModel.labelVerticalColumnRelList[i] ;
				var rule = ruleDataModel.labelVerticalColumnRelList[i].rule ;
				rule.calcuElement = item.labelVerticalColumnRelId.columnId ;
				rule.labelTypeId = item.labelTypeId ;
				rule.columnCnName = item.mdaSysTableColumn.columnCnName ;
				rule.elementType = 2 ;//标签,默认必须
				if(rule.labelFlag == null || rule.labelFlag == ''){//标签,labelFlag必须
					rule.labelFlag = 1 ;
					rule.maxVal = 1	;
				}
				if(item.labelTypeId == 1){
					if(rule.labelFlag == 0 ){
						rule.minVal = 0 ;
					}else if(rule.labelFlag == 1 ){
						rule.maxVal = 1	;
					}
				}else if(item.labelTypeId == 4){
					rule = model.setNumberValue(item);
				}else if(item.labelTypeId == 5){
					rule = model.setEnumValue(item);
				}else if(item.labelTypeId == 6){
					rule = model.setDateValue(item);
				}else if(item.labelTypeId == 7){
					if(rule.queryWay == "1"){
						rule.exactValue = '';
					}else{
						rule.darkValue = '';
					}
				}else{
					//
				}
				if(rule){
					childrenRule[childrenRule.length] = rule ;
				}
			}
			ruleDataModel.rule.childLabelRuleList = childrenRule ;
			var wd = frameElement.lhgDG;
			//提交变量
			wd.curWin.calculateCenter.setDialogRuleValue(ruleDataModel.ruleIndex,ruleDataModel.rule);
			wd.cancel();
	}
    return model;
})(window.dateRule || {});