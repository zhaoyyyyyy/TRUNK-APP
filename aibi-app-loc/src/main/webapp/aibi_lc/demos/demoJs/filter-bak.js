$(function(){
	var result = [
	     			{
	 			        	 "name":"品牌",
	 			        	 "id":"brand",
	 			        	 "children":[
	 			        	             	{"name":"全部","id":"brand-all",isMultiSelect:"1",children:[],pId:'brand'},
	 			        	             	{"name":"apple","id":"brand1",isMultiSelect:"1",children:[],pId:'brand'},
	 			        	             	{"name":"三星","id":"brand2",isMultiSelect:"1",children:[],pId:'brand'}
	 			        	             ] , 
	 			        	 isMultiSelect:"1",//是否可多选 1-可多选 0-不可多选
	 			        	 pId:'root'
	 			     },
	 			     {
	 			        	 "name":"储存",
	 			        	 id:"store",
	 			        	 children:[
	 			        	           {"name":"全部","id":"store-all",isMultiSelect:"0",children:[],pId:'store'},
	 			        	           {"name":"16G","id":"store1",isMultiSelect:"0",
	 			        	        	   "children":[
	 			        	        	               {
	 			        	        	            	   "name":"16G二级目录名称1","id":"store1-1",isMultiSelect:"0",
	 			        	        	            	   "children":[
	 			        	        	            	               {"name":"16G二级目录名称1-三级目录1","id":"store1-1-1",children:[],isMultiSelect:"0", pId:'store1-1'},
	 			        	        	            	               {"name":"16G二级目录名称1-三级目录2","id":"store1-1-2",children:[],isMultiSelect:"0", pId:'store1-1'}
	 			        	        	            	   			  ],
	 			        	        	            	   pId:'store1'		  
	 			        	        	               },
	 			        	        	   ],
	 			        	        	   pId:'store'
	 			        	           },
	 			        	           {"name":"32G","id":"store2",isMultiSelect:"0",children:[],pId:'store'},
	 			        	           {"name":"128G","id":"store3",isMultiSelect:"0",
	 			        	        	   "children":[
	 			        	        	               {
	 			        	        	            	   "name":"128二级目录名称1","id":"store3-1",isMultiSelect:"0",
	 			        	        	            	   "children":[
	 			        	        	            	               {"name":"128二级目录名称1-128三级目录1","id":"store3-1-1",children:[],isMultiSelect:"0", pId:'store3-1'},
	 			        	        	            	               {"name":"128二级目录名称1-128三级目录2","id":"store3-1-2",children:[],isMultiSelect:"0", pId:'store3-1'}
	 			        	        	            	   			  ],
	 			        	        	            	   pId:'store3'		  
	 			        	        	               },
	 			        	        	              {
	 			        	        	            	   "name":"128二级目录名称2","id":"store3-2",isMultiSelect:"0",
	 			        	        	            	   "children":[
	 			        	        	            	               {"name":"128二级目录名称2-128三级目录11111","id":"store3-2-1",children:[],isMultiSelect:"0", pId:'store3-2'},
	 			        	        	            	               {"name":"128二级目录名称2-128三级目录22222","id":"store3-2-2",children:[],isMultiSelect:"0", pId:'store3-2'}
	 			        	        	            	   			  ],
	 			        	        	            	   pId:'store3'		  
	 			        	        	               },
	 			        	        	   ],
	 			        	        	   pId:'store'
	 			        	           },
	 			        	 ] , 
	 			        	isMultiSelect:"0",
	 			        	pId:'root'
	 			     }
 			 ];
	
	
	function returnRowStr(item,childrenStr,rootId){
		var filterRowStr = '<div class="filter-row-box clearfix" id="'+item.id+'-box">'+
							    '<div class="fleft align_right filter-row-left" rootId='+rootId+' id='+item.id+' title='+item.name+' data-id='+item.pId+' isMultiSelect='+item.isMultiSelect+'><a class="hidden-important" hre="javascript:;">'+item.name+'</a>'+item.name+':</div>'+
								'<div class="fleft filter-row-right">'+childrenStr+'</div>'+
							'</div>';
		return filterRowStr;
	};
	var childrenList = [];//存放所有的下拉数据信息
	function loadFliterList(data){
		var str = "";
		for(var i=0;i<data.length;i++){
			var item = data[i];
			var children = data[i].children;
			var childrenStr = '<ul class="clearfix">';
			for(var j=0;j<children.length;j++){
				var childrenItem = children[j];
				var downList = children[j].children;
				var rootId = children[j].pId;
				if(downList && downList.length>0){
					downList.push(rootId);
					childrenList.push(downList);
				}
				var hasChildrenClass = downList.length>0 ? 'hasChildren' : '';
				var childrenBoxStr =  downList.length>0 ? '<div class="childrenBox"></div>' : '';
				childrenStr+='<li rootId='+rootId+' class="'+hasChildrenClass+'" id="'+childrenItem.id+'" data-id='+childrenItem.pId+' isMultiSelect='+childrenItem.isMultiSelect+'>'+
								'<a href="javascript:void(0);" title="'+childrenItem.name+'" ><span>'+childrenItem.name+'</span><i></i>'+
								'</a>'+childrenBoxStr+
						   '</li>';
			}
			childrenStr+='</ul>';
			str+=returnRowStr(item,childrenStr);
		}
		$('#demoBox').append(str);
	};
	function loadFliterList2(data){
		for(var i in data){
			var childrenArr = data[i];
			var rootId = childrenArr[childrenArr.length-1];
			for(var j in childrenArr){
				var array = childrenArr[j].children;
				var chilrenStr = '<ul class="clearfix">';
				for(var k in array){
					var childrenItem = array[k];
					chilrenStr+='<li rootId='+rootId+' id="'+childrenItem.id+'" data-id='+childrenItem.pId+' isMultiSelect='+childrenItem.isMultiSelect+'>'+
										'<a title="'+childrenItem.name+'" href="javascript:void(0);">'+childrenItem.name+'</a>'+
								   '</li>';
				}
				$('li.hasChildren#'+childrenArr[j].pId).find('.childrenBox').append(returnRowStr(childrenArr[j],chilrenStr,rootId))
			}
		}
	};
	//点击事件
	function clickFun(){
		$('.filter-row-box li').on('click',function(event){
			event.stopPropagation();//阻止事件冒泡
			var hasActive = $(this).hasClass('active');
			var thisName = $(this).find(' > a').text();
			var thisId = $(this).attr('id');
			var thisPid = $(this).attr('data-id');
			var rootId = $(this).attr('rootId');
			var isMultiSelect = $(this).attr('isMultiSelect');
			var selectedStr = '<li class="fleft selected-item" rootId='+rootId+' pId='+thisPid+' data-id="'+thisId+'">'+
								'<div class="fleft item-name" title="'+thisName+'">'+thisName+'</div>'+
								'<i class="fleft cursor item-del-btn">&times;</i>'+
							 '</li>';
			debugger
			if(thisName=="全部"){
				$(this).addClass('active').siblings('li').removeClass('active');
				$('#demoSelected li[rootId='+rootId+']').remove(); 
			}else{
				if(isMultiSelect == '1'){
					$(this).addClass('active');
					$('li#'+rootId+'-all').removeClass('active');
					$('#demoSelected').append(selectedStr);
				}
				if(isMultiSelect == '0'){
					$('#demoBox').find('li[rootId='+rootId+']').removeClass('active');
					$(this).addClass('active')
					$('#demoSelected').find('li[rootId='+rootId+']').remove(); 
					$('#demoSelected').append(selectedStr);
				}
			}
			selectedDel();//删除已选 条件
		});
	};
	//有下拉列表的鼠标经过和离开事件事件
	function filterHover(){
		$('.filter-row-box li.hasChildren').on('mouseover',function(){
			$('.filter-row-box li.hasChildren').removeClass('hover');
			$(this).addClass('hover');
			$('.childrenBox').hide();
			$(this).find('.childrenBox').show();
		}).on('mouseout',function(){
			$(this).removeClass('hover');
			$(this).find('.childrenBox').hide();
		})
	};
	//删除已选条件
	function selectedDel(){
		$('#demoSelected .item-del-btn').on('click',function(){
			var thisId = $(this).parent('li').attr('data-id');
			var rootId = $(this).parent('li').attr('rootId');
			$(this).parent('li').remove();
			$('#demoBox li#'+thisId).removeClass('active');
			var pLength = $('#demoSelected').find('li[rootId='+rootId+']').length;
			//如果某一个类别删除完 恢复到默认的选择全部
			if(pLength <= 0){
				$('li#'+rootId+'-all').addClass('active');
			}
		})
	};
	//加载
	loadFliterList(result);
	loadFliterList2(childrenList);
	clickFun();//一级点击选中事件
	filterHover();
	
})
