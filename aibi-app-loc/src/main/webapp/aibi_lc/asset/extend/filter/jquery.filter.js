/**
 * 条件过滤筛选 如$('#demo').filterChoice(option)
 */
(function(){
	var childrenList = [];
	$.fn.extend({
		returnDefaults:function(){
			var defaults = {
					data:[],//条件数据list
					selectedUl:"#selectedUl",//存放已选择条件的ul对象-默认id应设置为它,但用户可根据需要更改成自己的id
					filterBox:"#filterBox",//存放筛选条件的div对象-默认id应设置为它,但用户可根据需要更改成自己的id
			};
			return defaults;
		},
		filterChoice:function(option){
			var defaults = this.returnDefaults();
			if(defaults[option] && typeof option === 'string' ){
				return defaults[option].apply(this, Array.prototype.slice.call(arguments, 1));
			}else if (typeof option === 'object'){
				option = $.extend(defaults,option);
			}
			console.log(option)
			//加载列表
			this.loadFliterList(option);
			//加载下拉列表数据
			this.loadChildrenList(childrenList);
			//点击事件
			this.filterClick(option);
			//有子级的li鼠标经过和离开事件
			this.filterHover();
		},
		returnRowStr:function(item,childrenStr,rootId){
			var filterRowStr = '<div class="filter-row-box clearfix" id="'+item.id+'-box">'+
							    	'<div class="fleft align_right filter-row-left" rootId='+rootId+' id='+item.id+' title='+item.name+' data-id='+item.pId+' isMultiSelect='+item.isMultiSelect+'><a class="hidden-important" hre="javascript:;">'+item.name+'</a>'+item.name+':</div>'+
							    	'<div class="fleft filter-row-right">'+childrenStr+'</div>'+
							   '</div>';
			return filterRowStr;
	   },
	   loadFliterList:function(option){
		    var data =  option.data;
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
				str+=this.returnRowStr(item,childrenStr);
			}
			$(this.returnDefaults().filterBox).append(str);
	},
	loadChildrenList:function(data){
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
				var childrenListStr = this.returnRowStr(childrenArr[j],chilrenStr,rootId);
				$('li.hasChildren#'+childrenArr[j].pId).find('.childrenBox').append(childrenListStr);
			}
		}
	},
	filterClick:function(option){
		var _this = this;
		var selectedUl = option.selectedUl ? option.selectedUl : _this.returnDefaults().selectedUl;
		var filterBox =  option.filterBox ? option.filterBox : _this.returnDefaults().filterBox;
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
			if(thisName=="全部"){
				$(this).addClass('active').siblings('li').removeClass('active');
				$(selectedUl).find('li[rootId='+rootId+']').remove(); 
			}else{
				if(isMultiSelect == '1'){
					$(this).addClass('active');
					$(filterBox).find('li#'+rootId+'-all').removeClass('active');
					$(selectedUl).append(selectedStr);
				}
				if(isMultiSelect == '0'){
					$(filterBox).find('li[rootId='+rootId+']').removeClass('active');
					$(this).addClass('active')
					$(selectedUl).find('li[rootId='+rootId+']').remove(); 
					$(selectedUl).append(selectedStr);
					
				}
			}
			_this.selectedDel(option);//删除已选中
		});
	},
	filterHover:function(){
		$('.filter-row-box li.hasChildren').on('mouseover',function(){
			$('.filter-row-box li.hasChildren').removeClass('hover');
			$(this).addClass('hover');
			$('.childrenBox').hide();
			$(this).find('.childrenBox').show();
		}).on('mouseout',function(){
			$(this).removeClass('hover');
			$(this).find('.childrenBox').hide();
		})
	},
	selectedDel:function(option){
		var selectedUl = option.selectedUl ? option.selectedUl : _this.returnDefaults().selectedUl;
		var filterBox =  option.filterBox ? option.filterBox : _this.returnDefaults().filterBox;
		$(selectedUl).find('.item-del-btn').on('click',function(){
			var thisId = $(this).parent('li').attr('data-id');
			var rootId = $(this).parent('li').attr('rootId');
			$(this).parent('li').remove();
			$(filterBox).find('li#'+thisId).removeClass('active');
			var pLength = $(selectedUl).find('li[rootId='+rootId+']').length;
			//如果某一个类别删除完 恢复到默认的选择全部
			if(pLength <= 0){
				$('li#'+rootId+'-all').addClass('active');
			}
		})
	}
	})
})($);
