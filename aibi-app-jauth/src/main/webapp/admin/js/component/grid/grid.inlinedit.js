/**
 * ************************************************************************
 * Date: 2011-7-28
 * modifier: 周营昭
 * 添加getEditing方法，返回数组，正在编辑的行号。如果没有编辑的行，则返回false。
 * **********************************************************
 * ************************************************************************
 * Date: 2011-8-3
 * modifier: 周营昭
 * reason: 给编辑框绑定时间时，传入参数opt，使得编辑框事件可以获得当前行号。
 * 
 * ************************************************************************
 * Date: 2011-8-4
 * modifier: 周营昭
 * reason: select saveRow时，tmp2设置为当前的value。
 * ************************************************************************
 * Date: 2011-8-20
 * modifier: 周营昭
 * reason: 屏蔽回车键事件，不让它提交了。
 * ************************************************************************
 * Date: 2012-2-28
 * modifier: qiujie
 * reason: radio  saveRow时，tmp设置当前的value.
 * ************************************************************************
 * Date: 2012-6-27
 * modifier: zhougz
 * reason: triggerfield  149 tmp设置为隐藏值
 * 
 */

;(function($){
/**
 * jqGrid extension for manipulating Grid Data
 * Tony Tomov tony@trirand.com
 * http://trirand.com/blog/ 
 * Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl-2.0.html
**/ 
$.jgrid.extend({
	getEditing : function() {
	  var editing = [];
	  
	  var ids = $(this).jqGrid("getDataIDs");
	  for (var i = 0; i < ids.length; i++) {
		  var ind = $(this).jqGrid("getInd",ids[i],true);
		  if( ind === false ) {continue;}
		  
		  if ($(ind).attr("editable") == "1") {
			  editing.push(ids[i]);
		  }
	  }
	  
	  if (editing.length > 0) {
		return editing;  
	  } else {
		return false;  
	  }
    }, 
    // 用于重新设定列属性值
    setColProp : function(f, d) {
      return this.each(function() {
            if (this.grid)
              if (d)
                for (var b = this.p.colModel, m = 0; m < b.length; m++)
                  if (b[m].name == f) {
                    $.extend(this.p.colModel[m], d);
                    break
                  }
          })
    },
//Editing
	editRow : function(rowid,keys,oneditfunc,succesfunc, url, extraparam, aftersavefunc,errorfunc, afterrestorefunc) {
		return this.each(function(){
			var $t = this, nm, tmp, editable, cnt=0, focus=null, svr={}, ind,cm;
			if (!$t.grid ) { return; }
			ind = $($t).jqGrid("getInd",rowid,true);
			if( ind === false ) {return;}
			editable = $(ind).attr("editable") || "0";
			if (editable == "0" && !$(ind).hasClass("not-editable-row")) {
				cm = $t.p.colModel;
				$('td',ind).each( function(i) {
					nm = cm[i].name;
					var treeg = $t.p.treeGrid===true && nm == $t.p.ExpandColumn;
					if(treeg) { tmp = $("span:first",this).html();}
					else {
						try {
							tmp =  $.unformat(this,{rowId:rowid, colModel:cm[i]},i);
						} catch (_) {
							tmp = $(this).html();
						}
					}
					if ( nm != 'cb' && nm != 'subgrid' && nm != 'rn') {
						if($t.p.autoencode) { tmp = $.jgrid.htmlDecode(tmp); }
						svr[nm]=tmp;
						if(cm[i].editable===true) {
							if(focus===null) { focus = i; }
							if (treeg) { $("span:first",this).html(""); }
							else { $(this).html(""); }
							var opt = $.extend({"rowId": rowid},cm[i].editoptions || {},{id:rowid+"_"+nm,name:nm});
							if(!cm[i].edittype) { cm[i].edittype = "text"; }
							var elc = $.jgrid.createEl(cm[i].edittype,opt,tmp,true,$.extend({},$.jgrid.ajaxOptions,$t.p.ajaxSelectOptions || {}));
							$(elc).addClass("editable");
							if(treeg) { $("span:first",this).append(elc); }
							else { $(this).append(elc); }
							//Again IE
							if(cm[i].edittype == "select" && cm[i].editoptions.multiple===true && $.browser.msie) {
								$(elc).width($(elc).width());
							}
							
							cnt++;
						}
					}
				});
				if(cnt > 0) {
					svr.id = rowid; $t.p.savedRow.push(svr);
					$(ind).attr("editable","1");
					$("td:eq("+focus+") input",ind).focus();
					if(keys===true) {
						$(ind).bind("keydown",function(e) {
							if (e.keyCode === 27) {$($t).jqGrid("restoreRow",rowid, afterrestorefunc);}
							e.stopPropagation();
						});
					}
					if( $.isFunction(oneditfunc)) { oneditfunc.call($t, rowid); }
				}
			}
		});
	},
	saveRow : function(rowid, succesfunc, url, extraparam, aftersavefunc,errorfunc, afterrestorefunc) {
		return this.each(function(){
		var $t = this, nm, tmp={}, tmp2={}, editable, fr, cv, ind;
		if (!$t.grid ) { return; }
		ind = $($t).jqGrid("getInd",rowid,true);
		if(ind === false) {return;}
		editable = $(ind).attr("editable");
		url = url ? url : $t.p.editurl;
		if (editable==="1") {
			var cm;
			$("td",ind).each(function(i) {
				cm = $t.p.colModel[i];
				nm = cm.name;
				if ( nm != 'cb' && nm != 'subgrid' && cm.editable===true && nm != 'rn') {
					switch (cm.edittype) {
						case "checkbox":
							var cbv = ["Yes","No"];
							if(cm.editoptions ) {
								cbv = cm.editoptions.value.split(":");
							}
							tmp[nm]=  $("input",this).attr("checked") ? cbv[0] : cbv[1]; 
							break;
						case 'text':
							if($('input:hidden',this).length == 1){
								tmp[nm] = $('input:hidden',this).val();
							}else{
								tmp[nm] = $('input:visible',this).val();
							}
							break;
						case 'password':
						case 'textarea':
						case "button" :
							tmp[nm]=$("input, textarea",this).val();
							break;
						case 'select':
							if(!cm.editoptions.multiple) {
								tmp[nm] = $("select>option:selected",this).val();
								tmp2[nm] = $("select>option:selected", this).val();
							} else {
								var sel = $("select",this), selectedText = [];
								tmp[nm] = $(sel).val();
								if(tmp[nm]) { tmp[nm]= tmp[nm].join(","); } else { tmp[nm] =""; }
								$("select > option:selected",this).each(
									function(i,selected){
										selectedText[i] = $(selected).text();
									}
								);
								tmp2[nm] = selectedText.join(",");
							}
							if(cm.formatter && cm.formatter == 'select') { tmp2={}; }
							break;
						case 'radio':
							var isCheck = false;
							
							$("input[type=radio]",this).each(function(i){
								if( $(this).attr("checked")){
									tmp[nm]= $(this).attr("value");
								}
							})
						
							tmp[nm]= tmp[nm] || "";
							break;
						case 'custom' :
							try {
								if(cm.editoptions && $.isFunction(cm.editoptions.custom_value)) {
									tmp[nm] = cm.editoptions.custom_value.call($t, $(".customelement",this),'get');
									if (tmp[nm] === undefined) { throw "e2"; }
								} else { throw "e1"; }
							} catch (e) {
								if (e=="e1") { J.alert("开发错误， function 'custom_value'未找到", 250, 190); }
								if (e=="e2") {J.alert("开发错误， function 'custom_value' 返回值错误", 250, 190); }
								else { J.alert(e.message, 250, 190); }
							}
							break;
					}
					cv = $.jgrid.checkValues(tmp[nm],i,rowid,$t);
					if(cv[0] === false) {
						cv[1] = tmp[nm] + " " + cv[1];
						return false;
					}
					if($t.p.autoencode) { tmp[nm] = $.jgrid.htmlEncode(tmp[nm]); }
				}
			});
			if (cv[0] === false){
				try {
					var positions = $.jgrid.findPos($("#"+$.jgrid.jqID(rowid), $t.grid.bDiv)[0]);
					J.alert(cv[1], 250, 190);
				} catch (e) {
					alert(cv[1]);
				}
				return;
			}
			if(tmp) {
				var idname, opers, oper;
				opers = $t.p.prmNames;
				oper = opers.oper;
				idname = opers.id;
				tmp[oper] = opers.editoper;
				//update by xiajb 2012-03-09，行编辑时往后台传主键数据是用每行指定的id列的值
				//tmp[idname] = rowid;
				if(typeof($t.p.inlineData) == 'undefined') { $t.p.inlineData ={}; }
				if(typeof(extraparam) == 'undefined') { extraparam ={}; }
				tmp = $.extend({},tmp,$t.p.inlineData,extraparam);
			}
			if (url == 'clientArray') {
				tmp = $.extend({},tmp, tmp2);
				if($t.p.autoencode) {
					$.each(tmp,function(n,v){
						tmp[n] = $.jgrid.htmlDecode(v);
					});
				}
				var resp = $($t).jqGrid("setRowData",rowid,tmp);
				$(ind).attr("editable","0");
				for( var k=0;k<$t.p.savedRow.length;k++) {
					if( $t.p.savedRow[k].id == rowid) {fr = k; break;}
				}
				if(fr >= 0) { $t.p.savedRow.splice(fr,1); }
				if( $.isFunction(aftersavefunc) ) { aftersavefunc.call($t, rowid,resp); }
			} else {
				$("#lui_"+$t.p.id).show();
				$.ajax($.extend({
					url:url,
					data: $.isFunction($t.p.serializeRowData) ? $t.p.serializeRowData.call($t, tmp) : tmp,
					type: "POST",
					complete: function(res,stat){
						$("#lui_"+$t.p.id).hide();
						if (stat === "success"){
							var ret;
							if( $.isFunction(succesfunc)) { ret = succesfunc.call($t, res);}
							else { ret = true; }
							if (ret===true) {
								if($t.p.autoencode) {
									$.each(tmp,function(n,v){
										tmp[n] = $.jgrid.htmlDecode(v);
									});
								}
								tmp = $.extend({},tmp, tmp2);
								$($t).jqGrid("setRowData",rowid,tmp);
								$(ind).attr("editable","0");
								for( var k=0;k<$t.p.savedRow.length;k++) {
									if( $t.p.savedRow[k].id == rowid) {fr = k; break;}
								}
								if(fr >= 0) { $t.p.savedRow.splice(fr,1); }
								if( $.isFunction(aftersavefunc) ) { aftersavefunc.call($t, rowid,res); }
							} else {
								if($.isFunction(errorfunc) ) {
									errorfunc.call($t, rowid, res, stat);
								}
								$($t).jqGrid("restoreRow",rowid, afterrestorefunc);
							}
						}
					},
					error:function(res,stat){
						$("#lui_"+$t.p.id).hide();
						if($.isFunction(errorfunc) ) {
							errorfunc.call($t, rowid, res, stat);
						} else {
							alert("Error Row: "+rowid+" Result: " +res.status+":"+res.statusText+" Status: "+stat);
						}
						$($t).jqGrid("restoreRow",rowid, afterrestorefunc);
					}
				}, $.jgrid.ajaxOptions, $t.p.ajaxRowOptions || {}));
			}
			$(ind).unbind("keydown");
		}
		});
	},
	restoreRow : function(rowid, afterrestorefunc) {
		return this.each(function(){
			var $t= this, fr, ind, ares={};
			if (!$t.grid ) { return; }
			ind = $($t).jqGrid("getInd",rowid,true);
			if(ind === false) {return;}
			for( var k=0;k<$t.p.savedRow.length;k++) {
				if( $t.p.savedRow[k].id == rowid) {fr = k; break;}
			}
			if(fr >= 0) {
				if($.isFunction($.fn.datepicker)) {
					try {
						$("input.hasDatepicker","#"+$.jgrid.jqID(ind.id)).datepicker('hide');
					} catch (e) {}
				}
				$.each($t.p.colModel, function(i,n){
					if(this.editable === true && this.name in $t.p.savedRow[fr]) {
						ares[this.name] = $t.p.savedRow[fr][this.name];
					}
				});
				$($t).jqGrid("setRowData",rowid,ares);
				$(ind).attr("editable","0").unbind("keydown");
				$t.p.savedRow.splice(fr,1);
			}
			if ($.isFunction(afterrestorefunc))
			{
				afterrestorefunc.call($t, rowid);
			}
		});
	}
//end inline edit
});
})(jQuery);
