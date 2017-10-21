 /*
   **
   *************************************************************************
   * Date: 2011-7-7
   * modifier: shenlj
   * reason: 增加方法：saveAllData : function(url, succesfunc, postData)保存jqgrid发生变化的数据，用于jqgrid填报提交
   *         增加方法：arrayToJson(o)，字符串或object数组转成json串
   **************************************************************************
   * Date: 2011-11-1
   * modifier: zhougz
   * reason: 
   * a. 调用接口由loadComplete变为afterLoad
   * b. afterLoad中增加模糊匹配功能 
   ************************************************************************** 
   * Date: 2011-12-02
   * modifier: zhougz
   * reason: 
   * 初始化时修改多选框状态 add by zhougz 2011-12-02
   * ************************************************************************** 
   * Date: 2012-06-07
   * modifier: zhougz
   * reason: 
   * 点击行时。如果多选框是disable则没有点击效果 add by zhougz 2012-06-07
  **/
  
jQuery.extend(jQuery.jgrid.defaults, {
      prmNames : {
        page : "pageStart",
        rows : "pageSize",
        sort : "sortCol",
        order : "sortOrder",
        search : "_search",
        nd : "nd",
        id : "id",
        oper : "oper",
        editoper : "edit",
        addoper : "add",
        deloper : "del",
        subgridid : "id",
        npage : null,
        totalrows : "totalrows"
      },
      altRows : true,
      datatype : "json",
      
      //批量 add by zhougz begin
      batchExport : false,
	  batchExportOpt : {
	  	dataRange :['page'],    //导出范围([all]全部导出、[page]当前页导出、[choose]所选导出)
		dataItem :['displayItem'],    //导出数据项([displayItem]当前显示列导出，[template]模板导出，[defined]自定义导出)
		url : $.ctx + '',
		exportData : null,
		beforeExport : null,
		afterExport : null
	  },
	  
      //add by zhougz 模糊匹配
      fuzzyMatch : false,      //是否开启模糊匹配
      fuzzyMatchOpt:[{
      		matchList : [], 
      		tip    : '',       //提示 姓名/学号        
      		maxHeight : 'auto',//最大高度
      		callback : false,  //回调函数 function(id_val, a_val , b_val){}
      		inputObj : ''      //输入框对象
      }],
      
      mtype : "POST",
      rowNum : 10,
      rowList : [10,20,50,100,150],
      height: '-1',
      expectHeightEl : false,
      userDataClass : false,//userData数据行table样式，
	  rowDataCache	: false,//表格行数据是否缓存，如果是行编辑表格的不要设置该属性
      serializeGridData : function(postData) {
        postData = postData || {};
        if (postData.cols) {
          return postData;
        }

        var cols = "";
        for (var i = 0; i < this.p.colModel.length; i++) {
          var _colName = this.p.colModel[i].name;
          if (_colName !== "cb" && _colName !== "subgrid"
              && _colName !== "rn") {
            cols += ',' + _colName;
          }
        }
        if (this.p.appendDataCol) {
          cols = this.p.appendDataCol + cols;
        } else {
          cols = cols.substring(1);
        }
        postData['cols'] = cols;
        return postData;
      },
      beforeSelectRow : function(a,e){
      	//如果点击这行存在  disabled的 checkbox那么将不能被选中
      	if($('td[aria-describedby$=_cb] :checkbox:disabled',$(e.target).parent('tr')).length >0){
      		return false;
      	}else{
      		return true;
      	}
      },
      onSelectRow : function(id, status) {
        // 当多选被激活，同时配置要求单选，那么在当前行被选中时，把其他选择的给去掉。
        if (this.p.multiselect === true
            && this.p.selectOnlyOne === true && status === true) {

          var _arrSelected = $(this).getGridParam("selarrrow");
          for (var i = _arrSelected.length - 1; i >= 0; i--) {
            if (_arrSelected[i] !== id) {
              $(this).jqGrid('setSelection', _arrSelected[i],
                  false);
            }
          }
        }
        if (this.p.afterSelectRow) {
          this.p.afterSelectRow.call(this, id, status);
        }
      },

      afterLoad: function(jsonData) {
      	/** add by 周营昭， 当json数据报错时，需要给出提示。 begin */
      	if (jsonData.success === false) {
      		$.alert(jsonData.msg, false, false, function() {
      			// 配合sessionError.jsp页面给出的数据，这个可以确定是session访问过期造成的错误。
      			if(jsonData.error == "session-expire") {
      				top.location.href = $.ctx + "/login.jsp";
      			}
      		});
      		return;
      	}
      	/** add by 周营昭， 当json数据报错时，需要给出提示。 end */
      
      	var self = this;
      	
      	
      	/** 批量导出 add by zhougz 2012-02-29 begin**/
      	if(self.p.batchExport){
      		var beOpt = self.p.batchExportOpt;
      		
      		//预定义变量
      		var exportParm = {
				'all' : '全部导出',
				'page' : '当前页导出',
				'choose' : '所选导出',
				'displayItem' : '当前显示列导出',
				'template' : '模板导出',
				'defined' : '自定义导出'
			};
			
			var $exportScope = $('.navtable tr:last',self.p.pager);
			
			//定义 【数据范围】
			var dataRangeSel  =  "<td class='ui-corner-all ui-state-disabled' style='color:#377DAD;'>数据范围</td><td dir='ltr'><select name='dataRange' class='ui-pg-selbox'>";
			$.each(beOpt.dataRange,function(i,o){
				dataRangeSel += "<option value='"+o+"'>"+exportParm[o]+"</option>"
			});
			dataRangeSel += "</select ></td>";
			
			//定义 【导出数据项】
			var dataItemSel = "<td class='ui-corner-all ui-state-disabled' style='color:#377DAD;'>导出数据项</td><td dir='ltr'><select name='dataItem'  class='ui-pg-selbox'>";
			$.each(beOpt.dataItem,function(i,o){
				dataItemSel += "<option value='"+o+"'>"+exportParm[o]+"</option>"
			});
      		dataItemSel += "</select></td>";
      		
      		//定义导出按钮 
      		var $exportBtn = $("<td class='ui-pg-button ui-corner-all ui-state-disabled'>导出</td>").click(function(){
      			
      			var dataRangeVal = $exportScope.find('select[name=dataRange]').val();
      			var dataItemVal =  $exportScope.find('select[name=dataItem]').val();
      			
      			//执行导出之前函数
      			if(beOpt.beforeExport){
					beOpt.beforeExport.call(self,dataRangeVal,dataItemVal);
				}
      			$.commAjax({
					url : beOpt.url,
					isShowMask : false,
					type : 'POST',
					postData: {dataRange : dataRangeVal,dataItem:dataItemVal,exportData:beOpt.postData},
					onSuccess : function(data) {
						//执行导出之后函数
						if(beOpt.afterExport){
							beOpt.afterExport.call(data);
						}
					}
				});
      		});
       		$exportScope.append('<td style="width:70px;"><td>').append(dataRangeSel).append(dataItemSel).append($exportBtn);
      	}
      	
      	/** 模糊匹配 add by zhougz 2011-10-28 begin**/
      	if(this.p.fuzzyMatch){
	      	$.each(this.p.fuzzyMatchOpt, function(_num , _opt){
      			var fieldArray = $.turnJsonToArr(jsonData.rows,_opt.matchList);
	    	   	$('body').append('<div id="suggest_open'+_num+'"></div>');
	      		$(_opt.inputObj).suggest(fieldArray,{
		 			hot_list : fieldArray,
		 			attachObject : '#suggest_open'+_num,
		 			maxHeight : _opt.maxHeight,
		 			maxWidth :  _opt.maxWidth,
		 			onSelect : function(id_val, a_val , b_val){
		 				$(self).jqGrid('ensureVisible',id_val);
		 				if($.isFunction(_opt.callback)){
		 					_opt.callback(id_val, a_val , b_val);
		 				}
		 			}
		 		});
			});
      	};
      	
      	//modify by jlw at 2013-03-15 修改多个jqgrid分组展示 id重复问题
      	$.each(this.p.colModel, function(_num , _opt){
      		var col = self.p.colModel[_num];
      		if(col.fuzzyMatchable){
      			var $fuzzyBtn=$("#fuzzyBtn_"+self.p.id+_num);
      			if($fuzzyBtn.length==0){
	      			$fuzzyBtn = $("<div></div>");
	      			$fuzzyBtn.addClass('fuzzy_btn');
	      			$fuzzyBtn.attr('id', 'fuzzyBtn_'+self.p.id+_num);
      			}
      			var $head = $($('#'+self.p.id+'_'+col.name)).find('div[id="jqgh_'+col.name+'"]');
      			$head.prepend($fuzzyBtn);
      			
      			var colsArray = [self.p.prmNames.id,col.name,''];
      			if(col.fuzzyMatchOpt && col.fuzzyMatchOpt.matchList.length>2 ){
      				colsArray = col.fuzzyMatchOpt.matchList;
      			}
  				var fieldArray = $.turnJsonToArr(jsonData.rows,colsArray);
	    	   	$('body').append('<div id="suggest_'+self.p.id+_num+'"></div>');
	    	   	var $suggest_input = $('<input style="display:none;position:absolute;width:160px;" id="suggest_input_'+self.p.id+_num+'" />');
	    	   	$('body').append($suggest_input);
				var flag = true;
				$fuzzyBtn.bind('click',function(){
					var resetPosition = {
				      		top : ($head.offset().top+ $head.height()) +20+ 'px',
				      		left: $head.offset().left + 'px'
				      };
      				$suggest_input.css({
				      		top : ($head.offset().top+ $head.height()) + 'px',
				      		left: $head.offset().left + 'px'
				      }).slideDown("fast",function(){
							$suggest_input.suggest(fieldArray,{
					 			hot_list : fieldArray,
					 			attachObject : '#suggest_'+self.p.id+_num,
					 			lostFocus : function (e){
					 				if(!$(e).is('[id='+'fuzzyBtn_'+self.p.id+_num+']')){
					 					$suggest_input.slideUp("fast");
					 				}
					 			},
					 			maxHeight : '200px',
					 			tip    : $head.text(),
					 			maxWidth :  '160px',
					 			resetPosition : resetPosition,
					 			suggestFlag :flag,
					 			onSelect : function(id_val, a_val , b_val){
					 				$(self).jqGrid('ensureVisible',id_val);
					 				$suggest_input.hide();
					 				if($.isFunction(_opt.callback)){
					 					_opt.callback(id_val, a_val , b_val);
					 				}
					 			}
					 		});	
				 		$suggest_input.focus().click();
				 		flag = false;
      				});
      			})
      		}
		});
      	/** 模糊匹配 add by zhougz 2011-10-28 end**/
		
		/** 初始化时修改多选框状态 add by zhougz 2011-12-02 begin **/
		$.each(this.p.colModel, function(_num,_o){
      		var col = self.p.colModel[_num];
      		if($.isFunction(col.inlineCheckbox)){
      			$.each(jsonData.rows,function(i,obj){
      				var checkboxObj = $("#jqg_"+$(self).attr('id')+"_"+obj.id)[0];
      				col.inlineCheckbox.call(checkboxObj,obj[_o.index],obj)
      			});
      		}
		});
		/** 初始化时修改多选框状态 add by zhougz 2011-12-02 end **/
		
		
      	var $grid = $(this);
      	/**新增设置userdata数据行table样式配置，add by zhaomd*/
		if(this.p.userDataClass){
			$grid.parents(".ui-jqgrid").find(".ui-jqgrid-sdiv table:first").addClass(this.p.userDataClass);
		}
      	/**add end*/
		
      	var $gridDiv = $grid.parents(".ui-jqgrid-bdiv");
      	if(this.p.height == -1 && $.isFunction($gridDiv.getElFitHeight)){//表格高度自适应 add by zhaomd
      		var pageHeight = 18;
      		if(!$.isNull(this.p.pager)){
      			pageHeight += $(this.p.pager).outerHeight();
      		}
      		 var currentTask = null;
      		function onResize(){
      			if($grid.is(":hidden")){
      				return;
      			}
      			var height =  $gridDiv.getElFitHeight($grid[0].p.expectHeightEl)-$gridDiv.nextAll(".ui-jqgrid-sdiv").height()-pageHeight;
      		
      			if($grid.outerHeight()>height){
      				/*当高度超过grid的高度是滚动条出现 add by qiuj 2012-08-22 end */
      				$gridDiv.css("overflow-y","auto"); 
      				$gridDiv.height(height);
      			}else{
      				$gridDiv.height('100%');
      				/** 出现多余滚动条 add by qiuj 2012-06-26 end **/
      				$gridDiv.css("overflow-y","hidden");
      			}
      		}
      		onResize();
      		$(window).resize(function(){
				onResize(); 
      			resizeGridWidth();
      		});
      	}else{
      		window.onresize = resizeGridWidth;
      	}
      
       if(isNaN(this.p.height) && this.p.hscroll === true) {
          $gridDiv.height($grid.height() + 18);
        }

        if(this.p.autowidth) {
          	$grid.jqGrid('setGridWidth', $grid.parents(".ui-jqgrid").parent().width() );
        }
        
        /**if ($.isFunction(JT_show)) {
			if (currentTask != null) {
					clearTimeout(currentTask);
	         		currentTask = null;
				}
				
	          
        	$("a.jTip", this.grid.bDiv)
    		   .hover(function(){  
			    var target = this;
			 currentTask = setTimeout(function() {
	            	JT_show(target,target.rel,target.name)
	            }, 300)},function(){$('#JT').remove();
				if (currentTask != null) {
	         		clearTimeout(currentTask);
	         		currentTask = null;
	         	}});
    		$("a.jTip", this.grid.hDiv)
    		   .hover(function(){ 
			 var target = this;
			   currentTask = setTimeout(function() {
	            	JT_show(target,target.rel,target.name)
	            }, 300)},function(){
					if (currentTask != null) {
	         		clearTimeout(currentTask);
	         		currentTask = null;
	         	}$('#JT').remove()});
        }**/
      },

      loadError: function(xhr, status, error) {
    	  alert(xhr.status + ":" + xhr.statusText + error);
      },

      jsonReader : {
        repeatitems : false
      },
      
      ondblClickRow: function(id){ // 加入双击的默认事件 add by zhangjb 2012-02-22
	      $.rowEdit(this, id);
	    }
    });
/**
 * 把原来的行编辑共同方法提成一个函数，以方便前台在双击编辑前要做一些别的操作(shenlj)
 * @param {} obj
 * @param {} id
 */
$.rowEdit = function(obj, id){
  var gridTab = $(obj);
  var gridId = obj.id;
  if(!gridTab.getEditing){//update by xiajb 2012-06-25，当不是行编辑时不需要调用相应方法
  	return;
  }
  var lastEditing = gridTab.getEditing();
  if (lastEditing === false) {
    gridTab.jqGrid('editRow',id, true, function(){
      gridTab.setCell(id,"_operation",gridTab.getCell(id,"operation"));
      gridTab.setCell(id, "operation", "", {}, {innerHTML:"<a onclick='saveRow(\"" + id + "\",\""+ gridId +"\" )' class='s_save' title=''>保存</a>" +
       "&nbsp<a onclick='cancelEdit(\"" + id + "\",\""+ gridId +"\" )' class='s_cancle' title=''>取消</a>"});
    });       
  }else if (lastEditing[0] === id) {
    return;
  }else {
    $.alert($messageUtil("COMMON.SAVE_DATA_FIRST"));
  }
}
//update by xiajb 2012-03-30 修改公共处理列表中有多个码表值显示需要转换问题
jQuery.extend($.fn.fmatter, {

  translateCode : function(cellvalue, options, rowdata) {
    if (options.colModel.formatoptions
        && options.colModel.formatoptions.value) {
      var codes = options.colModel.formatoptions.value;
      var codeMap = $("body").data("codesMap")?$("body").data("codesMap")[options.colModel.formatoptions.codeClass]:null;
      var texts = [];
      var clscfg = options.colModel.formatoptions.clscfg;
      
      if(!codeMap){
      	  codeMap = {};
	      $.each(codes,function(){
	      	codeMap[this.code]=this.codeDesc;
	      });
      }
      if(cellvalue&&cellvalue!=""){
      	$.each(cellvalue.split(","),function(){
      		texts.push(codeMap[this]);
      	});
      }
      if (clscfg && clscfg[cellvalue]) {
	  	return "<span orig='"+ cellvalue + "' class='" + clscfg[cellvalue] +"'>" + texts.join("，") + "</span>";
	  } else {
		return "<span orig='"+ cellvalue + "'>" + texts.join("，") + "</span>";
	  }
      return  cellvalue || "";
    } else {
      return "没有设置values";
    }
  },

  translateOrg : function(cellvalue, options, rowdata) {
    if (top.$.orgCache == undefined) {
      var _url = $.ctx + "/system4!getOrgChche.action";
      var _data = $.ajax({
            url : _url,
            async : false,
            dataType : "json",
            success : function(result) {
              top.$.orgCache = result||{};
            }
          });
    }
    return top.$.orgCache[cellvalue] || "";
  }
});

//$.fn.fmatter.translateCode.unformat = function(text, options, cellval) {
//   return $("span[orig]", cellval).attr("orig");
//};

$.setColumnAdjustAbility = function(gridId, pagerId) {
	var $t = jQuery("#" + gridId);
	
	$t.navGrid("#" + pagerId).navButtonAdd("#" + pagerId,{
		buttonicon : "ui-icon-gear",
		caption: "",
		title : "调整显示列",
		onClickButton : function() {
			var _popup, _popupOpt = {
				title	: "选择列表显示列",
				page	: $.ctx+"/app/choose/grid-column-adjust.jsp",
				width	: 200,
				height	: 400
			};
				
			if (frameElement && frameElement.lhgDG) {
				_popupOpt.parent = frameElement.lhgDG;
				_popup = new frameElement.lhgDG.curWin.J.dialog(_popupOpt);
			} else {
				_popup = new J.dialog(_popupOpt);
			}
			
			_popup.getGrid = function() {
				return $t;
			};
			
			_popup.ShowDialog();
		}
	});
};

/***************************************************************************
 * 功能说明：自定义查询
 * 
 * @author xiajb
 * 
 * @param options 配置项
 * 			{
 * 				grid:grid元素选择器
 * 				pager:工具按钮条选择器
 * 				anchor:触发自定义查询元素选择器
 * 				url:可以指定自定义查询的请求连接，如果不指定，则跟查询区用的请求路径一致，只是方法名改为“customQuery”
 * 				type:自定义查询类型，HQL：按HQL查询，SQL：按SQL查询
 * 				sql:查询SQL或HQL在sql配置文件中的key，需要依据type指定合适语句，如"from Cdxx where delFlag<>'D' and ${where} order by cdmc"，
 * 					其中${where}为固定写法，是需要被替换的自定义查询条件
 * 			}
 * 
 * 示例：$.setCustomQuery({
	  		grid	: '#mainGrid',
	  		pager	: '#mainGridPager',
	  		anchor	: '#btnCustomQuery',
	  		type	: 'HQL',
	  		sql		: "hql_demo_customQuery"
	  	});
 **************************************************************************/
$.setCustomQuery = function(options) {
	options = options||{};
	//grid元素选择器
	var gridSelector = options.grid;
	//工具按钮条选择器
	var pager = options.pager;
	//触发自定义查询元素选择器
	var anchor = options.anchor;
	//自定义查询SQL
	var sql = options.sql;
	var url = options.url;
	if(!gridSelector){
		$.alert("自定义查询没有指定grid，请指定");
		return;
	}
	if(!sql||sql==""){
		$.alert("自定义查询没有指定SQL或HQL，请指定");
		return;
	}
	var $t = jQuery(gridSelector);
	
	//弹出自定义查询页面的处理函数
	var popupFunc = function(){
		var _popup, _popupOpt = {
			title	: "自定义查询",
			page	: $.ctx+"/app/choose/grid-custom-query.jsp",
			width	: 750,
			height	: 550
		};
			
		if (frameElement && frameElement.lhgDG) {
			_popupOpt.parent = frameElement.lhgDG;
			_popup = new frameElement.lhgDG.curWin.J.dialog(_popupOpt);
		} else {
			_popup = new J.dialog(_popupOpt);
		}
		
		_popup.callback = function(param){
			param["$custom_type"] = options.type||"hql";
			param["$custom_sql"] = options.sql;
			if(!url){
				url = $t.getGridParam("url");
				var queryMethod = url.substring(url.indexOf("!")+1,url.indexOf(".action"));
				url = url.replace(queryMethod,"customQuery");
			}
			$t.setGridParam({
				url: url,
		      	postData : param
		    }).trigger("reloadGrid",[{page:1}]);
		};
		
		_popup.getGrid = function() {
			return $t;
		};
		
		_popup.ShowDialog();
	}
	
	if(pager){
		$t.navGrid(pager).navButtonAdd(pager,{
			buttonicon : "ui-icon-search",
			caption: "",
			title : "自定义查询",
			onClickButton : function() {
				popupFunc();
			}
		});
	}
	
	if(anchor){
		$(anchor).click(function(){
			popupFunc();
		});
	}
};

// 新开发的自定义查询
$.setCustomQueryNew = function(options) {
	options = options||{};
	//grid元素选择器
	var gridSelector = options.grid;
	//工具按钮条选择器
	var pager = options.pager;
	//触发自定义查询元素选择器
	var anchor = options.anchor;
	//自定义查询SQL
	var sql = options.sql;
	var url = options.url;
	var busiType = options.busiType;
	if(!gridSelector){
		$.alert("自定义查询没有指定grid，请指定");
		return;
	}
	if(!sql||sql==""){
		$.alert("自定义查询没有指定SQL或HQL，请指定");
		return;
	}
	if(!busiType){
		$.alert("自定义查询没有指定busiType，请指定");
		return;
	}
	var $t = jQuery(gridSelector);
	
	//弹出自定义查询页面的处理函数
	var _popup;
	var popupFunc = function(){
		if(_popup==undefined){
		
		 _popup, _popupOpt = {
			title	: "自定义查询",
			page	: $.ctx+"/app/choose/zdycx.jsp?busiType=" +busiType+"&sql="+sql,
			width	: 750,
			height	: 400,
			resize	: false,
			rang: true,
			onXclick:function(){
        		_popup.reDialogDisplay({"dislay":false});
        	}
		};
			
		if (frameElement && frameElement.lhgDG) {
			_popupOpt.parent = frameElement.lhgDG;
			_popup = new frameElement.lhgDG.curWin.J.dialog(_popupOpt);
		} else {
			_popup = new J.dialog(_popupOpt);
		}
		_popup.querySql=sql;
		_popup.callback = function(param) {
			if (!url) {
				url = $t.getGridParam("url");
				var queryMethod = url.substring(url.indexOf("!") + 1, url
								.indexOf(".action"));
				url = url.replace(queryMethod, "listCustom");
			}
			$t.setGridParam({
						url : url,
						postData : {'where':param['queryStr'],'sql':sql}
					}).trigger("reloadGrid", [{
								page : 1
							}]);
		};
		_popup.getGrid = function() {
			return $t;
		};
		
		_popup.ShowDialog();
		}else{
			_popup.reDialogDisplay({"display":true});
		}
	}
	
	if(pager){
		$t.navGrid(pager).navButtonAdd(pager,{
			buttonicon : "ui-icon-search",
			caption: "",
			title : "自定义查询",
			onClickButton : function() {
				popupFunc();
			}
		});
	}
	
	if(anchor){
		$(anchor).click(function(){
			popupFunc();
		});
	}
};

  ;(function($){
	$.jgrid.extend({
	  /**
	   * 保存jqgrid发生变化的数据
	   * url：为要提交的处理方法地址
	   * succesfunc：提交完成后调用的回调函数
	   * postData：列表之外需要保存的数据
	   * 2011-07-07 shenlj
	   */
	  saveAllData : function(url, succesfunc, postData) {
	      return this.each(function (){
	          var $t = this;
	          var rowdatas = $($t).jqGrid("getChangedCells","all");
	          
	          postData = postData || {};
	          var rowDataJson = arrayToJson(rowdatas);
	          postData.removeIDs =  $removeIDs;
	          if(rowDataJson != ""){
	        	  postData.rowDataJson = rowDataJson;  
	          }
	          
	          $.commAjax({
	            url: url,
	            postData: postData,
	            onSuccess: function(rtnInfo){
	                succesfunc.call($t,rtnInfo);
	            }
	          });
	          
	      });
	  },
	  
	  /**
	   * 保存jqgrid发生变化的数据
	   * url：为要提交的处理方法地址
	   * succesfunc：提交完成后调用的回调函数
	   * postData：列表之外需要保存的数据
	   * 2011-07-07 shenlj
	   */
	  saveAllDataInGrid : function(url, succesfunc, postData) {
	      return this.each(function (){
	          var $t = this;
	          var rowdatas = $($t).jqGrid("getAllCells","all");
	          
	          postData = postData || {};
	          var rowDataJson = arrayToJson(rowdatas);
	          postData.removeIDs =  $removeIDs;
	          if(rowDataJson != "") {
	        	  postData.rowDataJson = rowDataJson;  
	          }
	          
	          $.commAjax({
	            url: url,
	            postData: postData,
	            onSuccess: function(rtnInfo){
	                succesfunc.call($t,rtnInfo);
	            }
	          });
	          
	      });
	  },
	  
	  ensureVisible : function(rowid) {
		   return this.each(function (){
	  	var $t = this;
		var $mainGrid = $($t)[0].grid.bDiv
		var _row=$("tr#" +rowid, $t);
		_row.click();
		var _h1=$mainGrid.scrollTop;
		var _h2=$mainGrid.clientHeight;
		var _h=_row[0].offsetTop;
		var _trHeight=27;
		if(_h-_h1<0){
			
			$mainGrid.scrollTop=_h;
		}else if((_h-_h1)>(_h2-_trHeight)){
			$mainGrid.scrollTop=_h-_h2+_trHeight;
			
		}else if(_h-_h2<_h1){
			return;
		}
		});
	  }
	})
  })(jQuery);

/**
 * 字符串或object数组转成json串
 * 2011-07-07 shenlj
 */
function arrayToJson(o) {
  if(o == undefined || o == null){
    return "";
  }else{
    var r = [];
    if (typeof o == "string")
      return "\"" + o.replace(/([\'\"\\])/g, "\\$1").replace(/(\n)/g, "\\n")
              .replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t") + "\"";
    if (typeof o == "object") {
      if (!o.sort) {
        for (var i in o)
          r.push(i + ":" + arrayToJson(o[i]));
        if (!!document.all
            && !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/
                .test(o.toString)) {
          r.push("toString:" + o.toString.toString());
        }
        r = "{" + r.join() + "}";
      } else {
        for (var i = 0; i < o.length; i++) {
          r.push(arrayToJson(o[i]));
        }
        r = "[" + r.join() + "]";
      }
      return r;
    }
    return o.toString();
  }
}

function resizeGridWidth(){
	jQuery(".ui-jqgrid").each(function(){
	    var id = $(this).attr("id").substring(5);
	    var w = $(this).parent().width();
	    var aw = jQuery("#" + id).jqGrid('getGridParam', 'autowidth');
	    
	    if (w > 0 && aw === true) {
	    	jQuery("#" + id).jqGrid('setGridWidth', w );
	    }
	});
}


/*
*  jQuery tui tablespan plugin 0.2
*
*  Copyright (c) 2010 china yewf
*
* Dual licensed under the MIT and GPL licenses:
*   http://www.opensource.org/licenses/mit-license.php
*   http://www.gnu.org/licenses/gpl.html
*
* Create: 2010-09-16 10:34:51 yewf $
* Revision: $Id: tui.tablespan.js  2010-09-21 10:08:36 yewf $ 
*
* Table rows or cols span
*/


/* 行合并。索引从0开始，包含隐藏列，注意jqgrid的自动序号列也是一列。
使用方法：
$("#jqGridId").tuiTableRowSpan("3, 4, 8");
该方法暂时由王硕维护，若存在不满足需求的情况或使用上的问题请找王硕解决
*/
jQuery.fn.tuiTableRowSpan = function(colIndexs) {
	
	//去掉鼠标浮动样式、点击样式、各行样式。 add by zhougz 20111219 begin
	$(this).unbind('mouseover').bind('click.unbindClick',function(e){
		$(e.target).closest("tr.ui-state-highlight").removeClass('ui-state-highlight');
	}).find('tr.ui-priority-secondary').removeClass('ui-priority-secondary');
	// end
	
    return this.each(function() {
        var indexs = eval("([" + colIndexs + "])");
        for (var i = 0; i < indexs.length; i++) {
            var colIdx = indexs[i];
            var that;
            //var $preThat;
            $('tbody tr', this).each(function() {
            	var row = this;
                $('td:eq(' + colIdx + ')', this).filter(':visible').not('[merge=true]').each(function() {
                    /**修改合并行规则为后一列的合并依赖于前一列的合并情况 edit by zhaomd*/
                    if (that != null && $(this).html() == $(that).html() 
                    	&& (i==0 || $('td:eq(' + indexs[i-1] + ')', row).attr("merge"))) {
                        //alert($(this).html()+",index["+i+"]="+indexs[i]);
                        rowspan = $(that).attr("rowSpan");
                        if (rowspan == undefined) {

                            $(that).attr("rowSpan", 1);
                            rowspan = $(that).attr("rowSpan");
                        }
                        rowspan = Number(rowspan) + 1;
                        $(that).attr("rowSpan", rowspan); // do your action for the colSpan cell here
                        $(this).hide(); //原为remove，修改为hide，解决合并行后getRowData取值错位问题 --edit by wangs
                    	$(this).attr("merge",true);
                    } else {
                        that = this;
                        //$preThat = $('td:eq(' + indexs[i-1] + ')', row);
                    }
                    /**edit end*/
                    // that = (that == null) ? this : that; // set the that if not already set
                });

            });
        }
    });
};

/* 列表头合并。
索引从0开始，包含隐藏列，注意jqgrid的自动序号列也是一列。
   
使用方法：
$("#jqGridId").tuiJqgridColSpan({ 
    cols: [
        { indexes: "3, 4", title: "合并后的大标题" },
        { indexes: "6, 7", title: "合并后的大标题" },
        { indexes: "11, 12, 13", title: "合并后的大标题" }
    ]
});
注意事项： 
1.不支持列宽的resize，最好将shrinkToFit设置为false；    
2.jqgrid的table表头必须有aria-labelledby='gbox_tableid' 这样的属性；
3.只适用于jqgrid；
*/
var tuiJqgridColSpanInit_kkccddqq = false;
jQuery.fn.tuiJqgridColSpan = function(options) {

    options = $.extend({}, { UnbindDragEvent: true, cols: null }, options);

    if (tuiJqgridColSpanInit_kkccddqq) {
        return;
    }

    // 验证参数
    if (options.cols == null || options.cols.length == 0) {
        alert("cols参数必须设置");
        return;
    }

    // 传入的列参数必须是顺序列，由小到大排列，如3,4,5
    var error = false;
    for (var i = 0; i < options.cols.length; i++) {
        var colIndexs = eval("([" + options.cols[i].indexes + "])");

        for (var j = 0; j < colIndexs.length; j++) {
            if (j == colIndexs.length - 1) break;

            if (colIndexs[j] != colIndexs[j + 1] - 1) {
                error = true;
                break;
            }
        }

        if (error) break;
    }

    if (error) {
        alert("传入的列参数必须是顺序列，如：3,4,5");
        return;
    }

    // 下面是对jqgrid的表头进行改造
    var oldTr,    // 被合并的第一行
        oldThs;   // 被合并的第一行的th
    var tableId = $(this).attr("id");
    // thead
    var jqHead = $("table[aria-labelledby='gbox_" + tableId + "']");
    var jqDiv = $("div#gbox_" + tableId);

    oldTr = $("thead tr:first", jqHead);
    if (oldTr.height() < 5) {
            oldTr = $("thead tr:eq(1)", jqHead);
            oldThs = $("th", oldTr);
    } else {
            // 增加第一行
            oldThs = $("th", oldTr);
            var ftr = $("<tr/>").css("height", "auto").addClass("ui-jqgrid-labels").attr("role", "rowheader").insertBefore(oldTr);
            oldThs.each(function(index) {
            var cth = $(this);
            var cH = cth.css("height"), cW = cth.css("width"),
            fth = $("<th/>").css("height", 0);
            // 在IE8或firefox下面，会出现多一条边线，因此要去掉。
            if (($.browser.msie && $.browser.version == "8.0") || $.browser.mozilla) {
                fth.css({ "border-top": "solid 0px #fff", "border-bottom": "solid 0px #fff" });
            }

            if (cth.css("display") == "none") {
                fth.css({ "display": "none", "white-space": "nowrap", "width": 0 });
            }
            else {
                fth.css("width", cW);
            }
            // 增加第一行
            fth.addClass(cth.attr("class")).attr("role", "columnheader").appendTo(ftr);
        });
    }
    
    var ntr = $("<tr/>").addClass("ui-jqgrid-labels").attr("role", "rowheader").insertAfter(oldTr);
    oldThs.each(function(index) {
        var cth = $(this);
        var cH = cth.css("height"), cW = cth.css("width"),
        nth = $("<th/>").css("height", cH);

        if (cth.css("display") == "none") {
            nth.css({ "display": "none", "white-space": "nowrap", "width": 0 });
        }
        else {
            nth.css("width", cW);
        }
        
        // 增加第三行
        if (cth.children().length > 0) {
        	cth.children().clone().appendTo(nth);
        } else {
        	nth.text(cth.text());
        }
        
        nth.addClass(cth.attr("class"))
           .attr("role", "columnheader")
           .attr("rowSpan", cth.attr("rowSpan"))
           .attr("colSpan", cth.attr("colSpan"))
           .appendTo(ntr);
        
        if (cth.css("border-bottom-style") == "solid") {
        	nth.css("border-bottom", "1px solid #AED0EA");
        }
        
        cth.attr("rowSpan", 1);
    });

    // 列合并。注意：这里不放在上面的循环中处理，因为每个遍历都要执行下面的操作。
    var toDelete = [];
    for (var i = 0; i < options.cols.length; i++) {
        var colIndexs = eval("([" + options.cols[i].indexes + "])");
        var colTitle = options.cols[i].title;
        //传入html begin add by zhougz 2011-12-07
		var colTitleHTML = options.cols[i].titleHTML || false;
		
        var isrowSpan = false;
        var mergedTH;
        
        for (var j = 0; j < colIndexs.length; j++) {
            // 把被合并的列隐藏，不能remove，这样jqgrid的排序功能会错位。
            if (j != 0) {
                var curColspan = mergedTH.attr("colSpan") || 1;
                var appendColspan = oldThs.eq(colIndexs[j]).attr("colSpan") || 1;
                mergedTH.attr("colSpan", 0 + curColspan + appendColspan) ;
                
                oldThs.eq(colIndexs[j]).attr("colSpan", "1").hide();
                toDelete.push(oldThs.eq(colIndexs[j]));
            } else {
                    mergedTH = oldThs.eq(colIndexs[j]);
                    mergedTH.text(colTitle);
                    //传入html begin add by zhougz 2011-12-07
                    if(colTitleHTML) mergedTH.html(colTitleHTML);
                    
                    mergedTH.css("border-bottom", "1px solid #AED0EA");
            }

            // 标记删除clone后多余的th
            $("th", ntr).eq(colIndexs[j]).attr("tuidel", "false");
        }
    }
    
    for (var i = 0; i < toDelete.length; i++) {
        toDelete[i].remove();
    }
    
    $("th", ntr).each(function(index) {
        var $th = $(this);
            
        if ($th.attr("tuidel") != 'false') {
            oldThs.eq(index).attr("rowSpan", 0 + (oldThs.eq(index).attr("rowSpan") || 1) + ($th.attr("rowSpan") || 1));
            $th.remove();
        } 
    });

	$(window).trigger('resize');
};
$.fn.setRownumberTitle = function(title) {
	var tableId = $(this).attr("id");
    var $jqHead = $("table[aria-labelledby='gbox_" + tableId + "']");
	$('#jqgh_rn',$jqHead).text(title);
}