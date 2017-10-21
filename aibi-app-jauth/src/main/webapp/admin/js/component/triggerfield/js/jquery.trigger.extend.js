	/***************************************************************************
	 * 功能说明：初始化选择组件（带清除功能，可选）
	 * 
	 * @author zhaomd
	 * 
	 * @param selector 组件选择器
	 * @param busiType 选择页面类型，详见$.initTrigger.config
	 * @param fn 选择页面操作的回调函数
	 * @param params 其他配置项 
	 *        {beforeTrigger：false//弹出窗口前处置函数，function，return false时不再弹出其选择页面}
	 **************************************************************************/
	$.fn.initTrigger = function(busiType, fn, params) {
		function popup() {
			var id =getDgId();  //id唯一标示
			var _popup, _popupOpt = $.fn.initTrigger.config[busiType];
			_popupOpt = $.extend({cover : true,id:id,rang:true}, _popupOpt);
			//解决弹出窗口中的tab页里再弹出时层级错误问题--add by wangs
			if(parent && parent.$("div.TabbedPanels").size()!=0 && parent.frameElement && parent.frameElement.lhgDG){
				//若条件成立，则为tab页并且在弹出窗口中
				frameElement.lhgDG = parent.frameElement.lhgDG;
			}
			if (frameElement && frameElement.lhgDG) {
				_popupOpt.SetTopWindow = top.window;
				_popupOpt.parent = frameElement.lhgDG;
				
				_popup = new frameElement.lhgDG.curWin.J.dialog(_popupOpt);
			} else {
				
				_popup = new J.dialog(_popupOpt);
			}
			_popup.ShowDialog();
			
			return _popup;
		}
		var $self = $(this);
//		if(typeof selector=="string"){
//			$self = $(selector);
//		}else{
//			$self = selector;
//		}
        
		if ($self.is(":text")) {
			var $trigger = $self.triggerfield({
				// btnIcon : 'date_triggerIcon',
				showClear	: true,
				open		: function() {
					if(params && params.beforeTrigger && !params.beforeTrigger()){
						return;
					}
					var $this = $(this);
					var _popup = popup();
					_popup.params = params;
					_popup.callback = function(value, text, appendObj) {
						$this.triggerfield('setValue', {
							"value"	: value,
							"text"	: text
						});
						if (fn) {
							fn(value, text, appendObj);
						}
					};
					
					//有设置少得参数就取得设置少得参数，没有设置好的参数去，初始化参数
					_popup.getParams = function(){
						return paramsObj||params;
					};
					
					_popup.getValue = function() {
					
						if(params){
							
							if(params.data!=null){
								if(params.data[2]==undefined){
									b = [params.data[0],params.data[1],"yyyy-MM-dd"];
									return b;
								}else{
									return params.data;
								}
							}
							//弹出选择框  传参数{name："value"}
							if(params.name!=null){
								return params.name;
							}
							
								
							return params;
							
							
						}
						return $self.triggerfield('getValue');
					
					}
				}
			});
			
			//设置参数
			var paramsObj;
			$trigger.setParams = function(obj){
				paramsObj = obj;
			}
					
					
			// 是否冻结
			if ($self.attr('disable')) {
				$trigger.triggerfield('disable');
			}
            
            // 如果组织机构属性的初始化值，则初始化组织机构代码 add by zhangll
	       /** $.ajax({
	            url         : $.ctx + "/system5!getOrgChche.action",
	            async       : false,
	            dataType    : "json",
	            success     : function(result) {
	                top.$.orgCache = result || {};
	            }
	        });**/
	        
	        if ($self.attr("orgCode")) {
	            $self.triggerfield('setValue', {
	                "value" : $self.attr("orgCode") || "",
	                "text"  : top.$.orgCache[$self.attr("orgCode")] || ""
	            });
	        }
	        
	        //设置初始值，defValue属性为初始code，defText为初始text add by xiajb
	        var defVal = $self.attr("defValue");
	        if (defVal) {
	        	var defTxt = $self.attr("defText");
	            $self.triggerfield('setValue', {
	                "value" : defVal|| "",
	                "text"  : defTxt||""
	            });
	        }
        
            
			return $trigger;
		} else {
			$self.click(function() {
				if(params && params.beforeTrigger && !params.beforeTrigger()){
					return;
				}
				var _popup = popup();
				_popup.params = params;
				if (!$self.is(":text")) {
				_popup.getValue = function() {
						if(params){
						
							if(params.data!=null){
								return params.data;
							}else{
								return params;
							}
							
						}else{
							return "yyyy-MM-dd";
						}
						return $self.triggerfield('getValue');
					
					}
				
				}
				_popup.callback = function(value, text, appendObj) {
					if (fn) {
						return fn(value, text, appendObj);
					}
				};
			});
			
		}
	};
	
	$.fn.initTrigger.config = {
		// 选择范围 全部  
		"org"		: {
			title	: "组织机构树",
			page	: $.ctx + '/admin/common/choose/chooseOrg.jsp',
			width	: 300,
			height	: 500
		}
	};
	
	/* 
 * function: 生成一个新的窗口id
 * arguments: id首单词
 *
 */
 
function getDgId(initial) {
	
	var id;
	initial = initial || 'dialog';
	
	do {
		id = initial + Math.floor(Math.random() * 10000);
	} while ($('#' + id, parent.document).length > 0);
	
	return id;
}