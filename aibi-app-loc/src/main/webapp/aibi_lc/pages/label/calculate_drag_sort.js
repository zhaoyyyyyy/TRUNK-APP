/**
 * ------------------------------------------------------------------
 * 标签集市的拖拽功能
 * ------------------------------------------------------------------
 */
var calculateDragSort = (function (model){
	
	/**
	 * 标签排序
	 */
	model.sortLabels = function(){
		var labels=$("#sortable .ui-conditionCT");
		labels.draggable({
			revert: "invalid",
			cursor:'pointer',
			cursorAt:{left:2,top:2},
			helper:function(){
				var _clone=$(this).clone();
				_clone.find(".ui-conditionCT-content").remove();
				var overlay=$('<div class="dragOverlay">&nbsp;</div>');
				_clone.append(overlay);
				_clone.appendTo('body');
				$(this).css("visibility","hidden");
				return _clone;
			},
			start:function(){
				$("#sortable .ui-chaining").hide();
				var tarAry=$("#sortable .ui-conditionCT,#sortable .right,#sortable .left").not($(this));
				var dragTempCT='<div class="ui-state-highlight ui-sortablr-helper J-helper"></div>';//定义空白
				tarAry.each(function(){
					$(this).after(dragTempCT);
				});
				$("#sortable").prepend(dragTempCT);//开始和结束添加空白
				$(this).css("visibility","visible").hide().addClass("onSortingLabel");//本元素隐藏，增加属性
				model.labels_possibility_position($(this));
			},
			drag:function(){},
			stop:function(source){
				$("#sortable .ui-chaining").show();
				$("#sortable .J-helper").remove();
				$("#sortable .onSortingLabel").removeClass("onSortingLabel").show();
				labelMarket.submitRules();
			}
		});
	}

	//排序标签开始时找出可合理drop标签的位置
	model.labels_possibility_position = function(_t){
		$("#sortable .J-helper").droppable({
			greedy:true,
			tolerance:"pointer",
			activeClass: "ui-state-active",
			hoverClass: "dragEnter",
			drop: function( event, ui ) {
				var conditionCT=_t.clone();
				var indexDrag = Number(conditionCT.attr('index'));
				var ruleDrag = dataModel.ruleList[indexDrag];
				var dragTempCTIndex=$(this).parent().find(".J-helper").index($(this)[0]);
				var conditionCTIndex=_t.parent().find(".ui-conditionCT").index(_t[0]);
				var isDraggingTheLastOne=conditionCTIndex==(_t.parent().find(".ui-conditionCT").length-1)&&_t.nextAll(".right").length==0?true:false;
				var isDraggingTheFirstOne=conditionCTIndex==0&&$(this).prevAll(".left").length==0&&_t.prevAll(".left").length==0?true:false;
				var isDragToTheLast=dragTempCTIndex==($(this).parent().find(".J-helper").length-1)?true:false;
				var isDragToTheFirst=dragTempCTIndex==0?true:false;
				//目标区域是否在括号内的第一个位置
				var isInParFirst=$(this).prev().hasClass("left")?true:false;
				//起始位置是否在括号内的第一个位置
				var isStartFromParenthesisFirst=_t.prev().prev().hasClass("left")?true:false;
				//起始位置是否在括号内最后位置
				var isStartFromParenthesisLast=_t.next().hasClass("right")?true:false;
				//括号里只有一个标签
				var isOnlyoneInParenthesis=_t.prev().prev().hasClass("left")&&_t.next().hasClass("right")?true:false;
				//目标区域在空括号里
				var isToEmptyParenthesis=$(this).prev().hasClass("left")&&($(this).next().hasClass("right")||$(this).next().hasClass("onSortingLabel"))?true:false;
				//如果拖动的是最后一个，并且释放到是最后一个，等于没有拖动；
				if(isDraggingTheLastOne==true){
					if(isDragToTheLast==true) return;
				//如果拖动的是第一个，并且释放到第一个，等于没有拖动；
				}else if(isDraggingTheFirstOne==true){
					if(isDragToTheFirst==true) return;
				}
				if(dragTempCTIndex==0||isInParFirst==true){
					//如果是第一的位置，向后添加连接符；或者目标区域在左括号的右侧1号位，也可认为是第一的位置;
					//但是括号里只有一个元素，则不需要添连接符
					var index = Number($(this).next().attr('index'));
					if(isToEmptyParenthesis!=true){
						var rule = model.getChainingObj(index);
						dataModel.ruleList.splice(index,0,rule);
						//$(this).after(chaining);
						if(index<=indexDrag){//向前添加
							indexDrag = indexDrag+1 ;
						}
					}
					dataModel.ruleList.splice(index,0,ruleDrag);
					if(index<=indexDrag){//向前添加
						indexDrag = indexDrag+1 ;
					}
					//$(this).after(conditionCT);
				}else{
					//向前添加连接符
					var rule = model.getChainingObj(indexDrag);
					var preIndex = Number($(this).prev().attr('index'));
					dataModel.ruleList.splice(preIndex+1,0,rule);//插入最后一个
					dataModel.ruleList.splice(preIndex+2,0,ruleDrag);
					if(preIndex<=indexDrag){//向前添加
						indexDrag = indexDrag+2 ;
					}
					//$(this).before(chaining);
					//$(this).before(conditionCT);
				}
				
				//如果括号里只有一个标签，那么拖拽它时不用删除旁边的符号
				if(isOnlyoneInParenthesis!=true){
					//如果不是拖拽的最后一个并且不是在括号最后位置，或者目标区域在括号的第一个位置并且不在括号内最后位置，或者在括号第一位置，这时删除后面紧跟的连接符；否则删除前面紧挨的连接符；
					if((!isDraggingTheLastOne&&!isStartFromParenthesisLast)||(isInParFirst==true&&isStartFromParenthesisLast==false&&!isDraggingTheLastOne)||isStartFromParenthesisFirst==true){
						//_t.nextAll(".ui-chaining").eq(0).remove();
						//var index = _t.nextAll(".ui-chaining").eq(0).attr('index');
						var firstChaining = -1;//删除后面连接符
						for(var i = indexDrag ;i < dataModel.ruleList.length;i++){
							var item = dataModel.ruleList[i];
							if(item.elementType == 1){
								firstChaining = i;
								break;
							}
						}
						dataModel.ruleList.splice(firstChaining,1);
					}
					else{
						//_t.prevAll(".ui-chaining").eq(0).remove();
						var firstChaining = -1;//删除前面连接符
						for(var i = indexDrag-1 ;i>=0;i--){
							var item = dataModel.ruleList[i];
							if(item.elementType == 1){
								firstChaining = i;
								break;
							}
						}
						dataModel.ruleList.splice(firstChaining,1);
						indexDrag = indexDrag -1;
					}
				}else{
					//括号里只有一个标签
				}
				
				//删除原标签;在此句以后，不要再使用_t了；
				//_t.remove();
				dataModel.ruleList.splice(indexDrag,1);
				$("#sortable,body").css("cursor","default");
				
				//有时间重写，单独的_t.sortLabels(),不用所有的都绑定；
				model.sortLabels();
				
				//最后删除内部无内容的空括号和它后面的连接符
				for(var i = 0 ;i < dataModel.ruleList.length;i++){
					var item = dataModel.ruleList[i];
					if(item.calcuElement == '('){//遍历所有左括号
						var nextItem = dataModel.ruleList[i+1];
						if(item.createBrackets && item.createBrackets == nextItem.createBrackets){//()
							var preItem = dataModel.ruleList[i-1];
							var nextNextItem = dataModel.ruleList[i+2];
							var leftHas = preItem && preItem.elementType == 1 ? true : false ;//如果前一个是连接符 true
							var rightHas = nextNextItem && nextNextItem.elementType == 1 ? true : false ;//如果后一个后一个是连接符 true
							var rightParenthesisHas = nextNextItem && nextNextItem.calcuElement == ')' ? true : false;//如果后一个后一个元素是)
							var rightConditionHas = false;//判断当前左括号右侧是否存在规则，默认没有元素
							for(var j= i+1;j<dataModel.ruleList.length;j++){
								if(item.elementType == 2){//有元素
									rightConditionHas = true;
								}
							}
							//三步,1：空括号左侧右侧都有符号，且右侧无label，删除右侧符号
							//2:空括号左侧有符号且右侧无label，删除左侧符号
							//3:空括号右侧有符号且左侧无label，也删除右侧符号（合并到1）
							var indexTemp = i;
							if(rightHas==true || leftHas==true&&rightHas==true){
								dataModel.ruleList.splice(i+2,1);//删除)右侧符号
							}else if( (leftHas==true&& rightConditionHas == false) || (leftHas==true&& rightParenthesisHas ) ){
								//_next.prevAll(".ui-chaining").eq(0).remove();???
								dataModel.ruleList.splice(i-1,1);//删除左括号的前一个连接符
								indexTemp = i-1;
							}
							//var prev=$(this).prev().prev();
							dataModel.ruleList.splice(indexTemp+1,1);
							dataModel.ruleList.splice(indexTemp,1);
							//_next.remove();
							//$(this).remove();
							/**
							if(prev.hasClass("left")){
								arguments.callee.apply(prev[0],arguments);
							}
							**/
						}
					}
				}
			}
		})
	};
	/**
     * @description 计算中心
     * @param  
     * @return  
     * ------------------------------------------------------------------
     */
    model.dragParenthesis= function(){
    		$( "#sortable > .ui-conditionCT,.ui-calc-h3>span>em" ).draggable({
			helper: function( event ) {
				if($(event.target).hasClass("J-drag-bracket")){
	       	 	   return $(event.target).attr("data-attr") == "left"?$( '<span class="ui-bracket left">(</span>' ):$( '<span class="ui-bracket right">)</span>' );
				}
	       	  return $( '<h4 class="ui-conditionCT-h4 ui-conditionCT-h4-helper">2G数据流量<em></em><i></i></h4>' );
	    	},
	    	cursor: "crosshair",
	    	start:function(event,ui){
	    		model.parenthesis_possibility_position($(this));
	        },
	        stop:function(event,ui){
	      	  $(".J-helper").remove();
	        }
		});
    };
    /**
     * 括号位置
     */
    model.parenthesis_possibility_position = function(_t){
    	if(!_t.parent().hasClass("opened")){
			  var items = $("#sortable > .ui-conditionCT,#sortable > .ui-bracket.left");
	      	  var calc= '<div class="ui-state-highlight ui-sortablr-helper J-helper"></div>';
	  		  $(items).before(calc);
	  		  $(".J-helper").droppable({
		  	  	   hoverClass: "ui-drop-highlight",
		  		   greedy:true,
		  		   drop: function( event, ui ) {
		  			   var onDragTag=ui.draggable;
		  			   var calcuElement = "";
		  			   if(onDragTag.hasClass("J-drag-bracket")){//如果移动的是括号
		  				 if(onDragTag.attr("data-attr") == "left"){
		  					calcuElement = "(";
		  				 }else{
		  					calcuElement = ")";
		  				 }
		  				 var index = $(this).next().attr('index');
		  				 var uuid = model.newGuid();
		  				 var rule = model.getCurlyBraceHTML(calcuElement,1,uuid);
		  				 rule.sortNum = index;
		  				 dataModel.ruleList.splice(index,0,rule);
		  				_t.parent().addClass("opened");
		  				 return;
		  			   }else{//页面规则
			  		        $(this).after(onDragTag);
			  		        var chains = $("#sortable > .ui-chaining");
			  		     	var CTitems = $("#sortable > .ui-conditionCT");
			  		        for(var i =0,len = chains.length;i<len;i++){
			  		        		$(CTitems[i]).after(chains[i]);
			  		        }
		  			   }
		  			   
		  	       },
		  	       create:function( event, ui){
		  		        console.log(ui);
		  	       }
		  	   });
		}else{
			var wait=$("#sortable > .waitClose");//所有左括号
			var totalAry=[];//所有右边元素（规则、括号）
			if(wait.prev().hasClass("left")){//前一个元素是否是左括号
				var creat=wait.prev().attr("creat");
				var stopAry=wait.nextAll(".ui-conditionCT,.ui-bracket");
				for(var k=0;k<stopAry.length;k++){
					if($(stopAry[k]).attr("creat")==creat) break;
					totalAry.push(stopAry[k]);
				}
			}
			else{
				var stopAry=wait.nextAll(".ui-conditionCT,.ui-bracket");
				for(var k=0;k<stopAry.length;k++){
					if($(stopAry[k]).hasClass("ui-bracket") && stopAry.filter("[creat='"+$(stopAry[k]).attr("creat")+"']").length<2) break;
					totalAry.push(stopAry[k]);
				}
			}
			var tarAry=[];
			$(totalAry).each(function(){
				var ary=[];
				var ary2=$(this).prevAll(".ui-conditionCT,.ui-bracket").andSelf();
				for(var i=ary2.length-1;i>=0;i--){	
					if($(ary2[i]).hasClass("waitClose")) break;
					else{
						ary.push($(ary2[i]));
					}
				}
				var left=[]
				for(var i=ary.length-1;i>=0;i--){
					if($(ary[i]).hasClass("left")){
						left.push($(ary[i]));
					}
					else if($(ary[i]).hasClass("right")){
						for(var j=0;j<left.length;j++){
							if( $(left[j]).attr("creat")==$(ary[i]).attr("creat") ){
								left=left.slice(0,j).concat(left.slice(j+1,left.length));
								break;
							}
						}
					}
					else{}
				}
			
				if(left.length==0 &&( $(this).hasClass("ui-conditionCT")||$(this).hasClass("right")) ){
					tarAry.push($(this));
				}
			})
			$(tarAry).each(function(){
				$(this).after('<div class="ui-state-highlight ui-sortablr-helper J-helper"></div>');
				$(this).next().droppable({
					greedy:true,
					hoverClass: "ui-drop-highlight",
					drop: function( event, ui ) {
						var index = $(this).prev().attr('index');
						var rule = model.getCurlyBraceHTML(')',1,$("#sortable .waitClose").attr("creat"));
						rule.sortNum = Number(index)+1;//不转换变成字符串拼接了
		  				dataModel.ruleList.splice(index+1,0,rule);
		  				$("#sortable .waitClose").removeClass("waitClose");
						_t.parent().removeClass("opened");
						//右边括号
						labelMarket.submitRules();
					}
				})
			})
		}
    }
	/**
	 * 获得连接符对象
	 */
	model.getChainingObj = function(sort){
		var rule={
    			elementType : 1,
    			calcuElement : 'and',
    			sortNum : sort
    	};
		return rule ;
	}
	/**
     * 获得括号元素
     */
    model.getCurlyBraceHTML=function(flag,count,uuidObj,needDelete){
    	//封装括号HTML
    	var rule = {};
    	if(flag=="("){
    		rule={
		    			elementType : 3,
		    			calcuElement : flag,
		    			waitClose : true,
		    			createBrackets : uuidObj,
		    			del :false
		    	};
    	}
    	else{
    		if(needDelete == true || needDelete==null) {
    			rule={
  		    			elementType : 3,
  		    			calcuElement : flag,
  		    			waitClose : false,
  		    			createBrackets : uuidObj,
  		    			del :true
  		    	};
    		} else {
    			rule={
  		    			elementType : 3,
  		    			calcuElement : flag,
  		    			waitClose : false,
  		    			createBrackets : uuidObj,
  		    			del :false
  		    	};
    		}
    	}
    	if(!uuidObj){
    		for(var i=0;i<count;i++){
    			if(flag=="("){
    				var uuid = model.newGuid();
    				rule.createBrackets = uuid;
    				rule.waitClose = ''; 
    				dataModel.createdLeftPar.push(uuid);
    			}
    			else{
    				rule.createBrackets = dataModel.createdLeftPar.pop();
    			}
    		}	
    	}
    	return rule;
    }
    //生成唯一标识
    model.newGuid = function(){ 
    	var guid = ""; 
    	for (var i = 1; i <= 32; i++){ 
    		var n = Math.floor(Math.random()*16.0).toString(16); 
    		guid += n; 
    		if((i==8)||(i==12)||(i==16)||(i==20)) 
    			guid += "-"; 
    	} 
    	return guid; 
    } 
	return model ;
})(window.marketDragSort || {});