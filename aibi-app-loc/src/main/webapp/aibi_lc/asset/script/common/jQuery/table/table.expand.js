(function($) {
	$.fn.extend({
		isBoolean : function(o) {
			return typeof o === 'boolean';
		},
		isObject : function(o) {
			return (o && (typeof o === 'object' || $.isFunction(o))) || false;
		},
		isString : function(o) {
			return typeof o === 'string';
		},
		isNumber : function(o) {
			return typeof o === 'number' && isFinite(o);
		},
		isNull : function(o) {
			return o === null;
		},
		isUndefined : function(o) {
			return typeof o === 'undefined';
		},
		isValue : function (o) {
			return (this.isObject(o) || this.isString(o) || this.isNumber(o) || this.isBoolean(o));
		},
		isEmpty : function(o) {
			if(!this.isString(o) && this.isValue(o)) {
				return false;
			}else if (!this.isValue(o)){
				return true;
			}
			o = $.trim(o).replace(/\&nbsp\;/ig,'').replace(/\&#160\;/ig,'');
			return o==="";	
		},
		tableExpand: function(options) {
			var defaults = {
				tableClass : "" ,//表格自定义样式
				url: '',
				ajaxType : "post", //get 、post 两种方式
				datatype: 'json', //ajax 表格返回类型
				isDrag : false, //是否行拖拽，
				isOrder: true,//是否可以排序
//				dragCallBack:function(e, ui) {//拖拽排序后回调函数
//			        $('td.index', ui.item.parent()).each(function (i) {
//			           
//			        });
//			    },
	            /*[{
	                 width: 80,
	                "colspan": "1", //列合并
	                "rowspan": "1" , //行合并
	                "title" :"统计时间" , //显示名称
	                 key:'sn' , //数据返回表头和返回JSON 关联
	                 isOrder :true , //是否排序
	                 orderBy : desc , //排序方式
	                 formatter:""  //自定义操作
	               }]
				*/
				ajaxData : {theader: []}, //get 、post 两种方式
				colNum : 0, //表头列数
				items_per_page:10,  //每页显示记录条数，默认为10条。
				current_page:0,
				num_display_entries:3, //主体页数
				num_edge_entries:1, //边缘页数
				pager: 'pager',
				prev_text:"上一页",
		        next_text:"下一页",
		        prev_show_always:true,
		        next_show_always:true,
		        waterMark:false, //添加水印，
		        waterMarkPicUrl:""
			};
			var opts = $.extend(defaults, options);
			    //分页参数动态拼接到ajax参数中
			    opts.ajaxData = $.extend(opts.ajaxData ,
			    	                     {items_per_page: opts.items_per_page ,
			    	                      current_page:opts.current_page}); 
            var _self  = $(this).empty();
			
		
			if(opts.ajaxData.theader.length > 0 ){
				var $thead = _self.find("> thead");
				if($thead.length == 0){
					$thead = $('<thead></thead>');
				}
				var thTemp = "";
				for (var i = 0, len = opts.ajaxData.theader.length; i < len; i++) {
					var $tr = $("<tr></tr>");
					var trArr = opts.ajaxData.theader[i];
					trArr.unshift({
	                		 key:"numerical",
	                		 title:"序号",
	                		 width:40
	                	 });
						var thTemp = "";
                        trArr.map(function(item){
                             // console.log(item);
                          var thWidth = item.width? item.width : "auto";
                          var colspan = item.colspan? item.colspan : 1;
                          var rowspan = item.rowspan? item.rowspan : 1;
                          var isOrder = item.isOrder? true : false;
                          var key = item.key;
                          var orderBy ;
                            
                              if(i == 0) {
                              	  opts.colNum += parseInt(colspan,10);
                              }
                              if(isOrder){
                              	 orderBy = item.orderBy ? item.orderBy : "desc" ;
                              	 thTemp +="<th width=" + thWidth +" colspan="+colspan
                              	        +"  key="+key +" orderBy="+ orderBy 
                              	        + " class=orderBy rowspan="+rowspan+">" + item.title + '<span class="sort">&nbsp;</span></th>';
                              }else{
                                 thTemp +="<th width=" + thWidth + " colspan="+colspan
                                        +" key="+key+" rowspan="+rowspan+">" + item.title + '</th>';
                              }
                        })
                        $tr.append(thTemp);
                        $thead.append($tr);
				}
				//console.log("总列数 : " + opts.colNum) ;
				_self.append($thead);
				//
				_self.find("thead th").filter(".orderBy").on("click",function(){
					 //console.log(opts);
					 var key = $(this).attr("key");
					 var orderBy = $(this).attr("orderBy");
					     orderBy  = orderBy == 'desc' ? 'asc' : 'desc' ; //修改排序
					     $(this).attr("orderBy" , orderBy);
						 opts.ajaxData.theader = mergeHeader(opts.ajaxData.theader , key , orderBy);
						 opts.ajaxData.current_page = 0;
						// console.log(opts.ajaxData.theader);
						 loadTablePageList({
							url:opts.url,
							ajaxData:opts.ajaxData,
							pager:opts.pager, //分页的ID
							items_per_page: opts.items_per_page,
							num_display_entries:opts.num_display_entries,
							num_edge_entries:opts.num_edge_entries,
							prev_text:opts.prev_text,
					        next_text:opts.next_text,
					        prev_show_always:opts.prev_show_always,
					        next_show_always:opts.next_show_always,
			                colNum : opts.colNum,
							currentGird:_self
						 })
				})
			}else{
				alert("请填写theader！");
				return;
			}
            

			var $tbody = _self.find("> tbody");
			if($tbody.length == 0){
				$tbody = $('<tbody></tbody>');
			}
//            var $tbody = $('<tbody></tbody>');
                _self.append($tbody);
			  
			if (opts.isDrag) { 
			    //判断行是可以拖拽的
				_self.find("tbody").sortable({
					helper: function(e, tr) {
						var $originals = tr.children();
						var $helper = tr.clone();
						$helper.children().each(function(index) {
							$(this).width($originals.eq(index).width())
						});
						return $helper;
					},
					stop: opts.dragCallBack
				}).disableSelection();
			}
			//是否加水印
			if(opts.waterMark){
				_self.addClass('waterMarkTable');
				_self[0].style.backgroundImage = "url("+ opts.waterMarkPicUrl +")";
			}
			
             //加载表格
             loadTablePageList({
				url:opts.url,
				ajaxData:opts.ajaxData,
				pager:opts.pager, //分页的ID
				items_per_page: opts.items_per_page,
				num_display_entries:opts.num_display_entries,
				num_edge_entries:opts.num_edge_entries,
				prev_text:opts.prev_text,
		        next_text:opts.next_text,
		        prev_show_always:opts.prev_show_always,
		        next_show_always:opts.next_show_always,
                colNum : opts.colNum,
				currentGird:_self //,
				//theader : opts.theader
			})
            //根据KEY查找对应的元素
            function findItemFromArr(arr , keyVal ){
            	 	var tempItem = null;
                 for(var i = 0;i< arr.length ;i++){
                    var itemArr = arr[i];
                        itemArr.map(function(item){
                         	if(item.key== keyVal){
                               tempItem = item;
                         	} 
                        });
                 }
                 return tempItem;
            }
            //合并排序参数
            function mergeHeader(arr,keyVal,orderBy){
                var tempItem = null;
	                for(var i = 0;i< arr.length ;i++){
	                    var itemArr = arr[i];
	                        itemArr.map(function(item){
	                         	if(item.key== keyVal){
	                               //tempItem = item;
	                               item.orderBy = orderBy
	                         	}
	                        })
	                 }
	                 return arr ;
            }
			function loadTablePageList (param){
				   /* console.log("ajax 参数 ：");
				    console.log(param);*/
				    var colNum = param.colNum ;
				    var theaderArr = param.ajaxData.theader ;//表头信息
				    //拼装grid内容
					getGrid({
						url:param.url,
						ajaxData:param.ajaxData,
						callback:function(jsonText){
							//console.log(jsonText);
							var jsonData = jsonText;
							if($.isArray(jsonText)){
								jsonData = jsonText[0]
							}
							var trData = jsonData.result;
							if(!trData){
								trData =  jsonData.data ? jsonData.data : [];
							}
						    $tbody.empty();
							     //console.log("总记录数:" + trData.length );
						    if(trData.length == 0){
						    		var colspan = theaderArr[0].length;
						    		 var $tr = 	$('<tr></tr>');
						    		var $td = $('<td  colspan='+colspan+ ' style=\"text-align:center;\">暂无数据</td>');
								$tr.append($td);
								$tbody.append($tr);
						    }
						    var arrTh = theaderArr[0];
							for( var i = 0,len = trData.length; i <len;i++ ){
								 var $tr = 	$('<tr></tr>');
								 var trDataItem  = trData[i];
//								 trDataItem["operation"]="";
								 var attrLen = Object.getOwnPropertyNames(trDataItem).length;
                                 var keyNum = 0;
                                	 for(var j= 0,leng = arrTh.length;j<leng;j++){
                                		var colValue =  trDataItem[arrTh[j].key] ?  trDataItem[arrTh[j].key] :"";
                                		if(arrTh[j].key == "numerical"){
                                			colValue = (i+1);
                                		}
                                		theadItemObj = arrTh[j];
                                		 keyNum++;
//            	                                     theadItemObj = findItemFromArr(theaderArr , key);
	                                     if(theadItemObj){
												var width = theadItemObj.width ? theadItemObj.width : "auto";
												var align = theadItemObj.align ? theadItemObj.align : "left";
												var colspan = theadItemObj.colspan ? theadItemObj.colspan : 1;
//            												var colName = theadItemObj.key ;
												var formatter = theadItemObj.formatter ;
//            						                        var colValue = trDataItem[colName];
					                           if(keyNum == attrLen-1 ){
					                               colspan =  colNum-attrLen +1;
											    }
												if(formatter){
												   colValue = eval("("+formatter+"("+JSON.stringify(trDataItem)+")"+")");
												}
												var $td = $('<td  "colspan='+colspan+ ' width=' + width + ' style=\"text-align:'+align+';\">' + colValue + '</td>');
												$tr.append($td);
                                 	 }
                                 }
                                $tbody.append($tr);
							}
                            //判断是否有分页
							 $("#"+param.pager).parent().remove();
							 if(jsonData.totalSize && jsonData.totalSize !=0){
								 $(param.currentGird).after($("<div class='clearfix'><div class='fleft totalPage'>共"+jsonData.totalSize+"条，"+jsonData.totalPage+"页，当前第"+jsonData.pageNum+"页</div><div id='"+param.pager+"' class='tcdPageCode'></div></div>"));
								 $("#"+param.pager).createPage({
								        pageCount:jsonData.totalPage,
								        current:jsonData.pageNum,
								        backFn:function(current){
								        		param.ajaxData.data.pageNum = current;
								        		loadTablePageList(param);
								        }
								   });
							 }
//							$("#"+param.pager).pagination({
//								url : param.url,
//								ajaxData:param.ajaxData,
//								num_display_entries:param.num_display_entries,
//								items_per_page: param.items_per_page,
//								current_page:param.ajaxData.current_page,
//								num_edge_entries:param.num_edge_entries,
//								maxentries:jsonData.maxentries,
//								prev_text:param.prev_text,
//						        next_text:param.next_text,
//						        prev_show_always:param.prev_show_always,
//						        next_show_always:param.next_show_always,
//						        //theader : param.theader,
//								callback: function(current_page){
//									//console.log("当前页码:" + current_page);
//									param.ajaxData.current_page = current_page;
//								    loadTablePageList(param);
//								}
//							});
						}
					});
		  	}
		}
	});
})(jQuery);
//拼装grid内容
function getGrid(opts){
	$.ajax({
		url:opts.url,
		type:"post",
		data:opts.ajaxData.data,
		dataType:"json",
		success:function(result){
			opts.callback(result);
		},
		error:function(){
			alert("ajax请求异常！！！");
		}
	});
}




