var $removeIDs = "";;
(function($) {
	/*
	 * jqGrid 3.8.2 - jQuery Grid Copyright (c) 2008, Tony Tomov,
	 * tony@trirand.com Dual licensed under the MIT and GPL licenses
	 * http://www.opensource.org/licenses/mit-license.php
	 * http://www.gnu.org/licenses/gpl-2.0.html Date: 2010-12-14
	 * 
	 * 
	 * ************************************************************************
	 * Date: 2011-04-27 modifire: zhouyingzhao reason:
	 * jquery.extend针对array时，是深度拷贝，设置rowlist有问题。 修改了rowList的设置方式。
	 * 
	 * ************************************************************************
	 * Date: 2011-05-06 modifier: zhouyingzhao reason:
	 * 新加了一个print按钮，使用navAddButton接口不太好统一，需要留的接口太多，所以使用了修改代码的方式。
	 * 
	 * ************************************************************************
	 * Date: 2011-05-12 modifier: zhouyingzhao reason:
	 * 原始的jqgrid文件太大，核心只需要其中的三个部分base、common、formedit； 将这三个部分合并到grid.base中。
	 * 
	 * ************************************************************************
	 * Date: 2011-06-29 modifier: zhouyingzhao reason:
	 * colModel中默认的resizable和sortable修改成true。
	 * 
	 * ************************************************************************
	 * Date: 2011-7-2 modifier: shenlj reason: 修改方法:checkValues : function(val,
	 * valref, rowid, g) 增加参数valrefrow（grid数据所对应的rowid），自定义验证方法时可以用。
	 * 
	 * ************************************************************************
	 * Date: 2011-7-7 modifier: shenlj reason:
	 * 修改setCell方法的样式设置，样式前面有符号"-"时，为单元格删除相应样式。 jqgrid增加增加与删除行方法
	 * jqgrid底部的打印、增加、删除按钮默认为不显示，需要显示时在jsp中把相关属性值设为true
	 * ************************************************************************
	 * Date: 2011-7-27 modifier: yangning reason: 修改方法:checkValues
	 * 为editrules的默认属性增加edtrul.decimalnum属性，用于判断小数位数
	 * ************************************************************************
	 * 
	 * ************************************************************************
	 * Date: 2011-7-28 modifier: 周营昭 reason: 当sortable时，才将表头样式加上手型。
	 * 
	 * ************************************************************************
	 * Date: 2011-8-3 modifier: 周营昭 reason: 给编辑框绑定时间时，传入参数opt，使得编辑框事件可以获得当前行号。 *
	 * *********************************************************************
	 * Date: 2011-11-1 modifier: zhougz reason:增加afterLoad接口，用法同loadComplete *
	 * *********************************************************************
	 * Date: 2011-12-1 modifier: zhougz reason:解决当checkbox disable时，全选也会被勾上bug *
	 * *********************************************************************
	 * Date: 2011-12-1 modifier: zhougz reason:解决当选择一行时连同 diasabled 的
	 * checkbox一起勾选bug * *
	 * *********************************************************************
	 * Date: 2012-02-28 modifier: qiujie reason:新增radio渲染方式 * * *
	 * *********************************************************************
	 * Date: 2012-06-07 modifier: qiujie reason:全选时，如果有checkbox是disable将不再选中 * * * *
	 * ************************************************************* Date:
	 * 2012-07-18 modifier: zhangjb reason:注释掉2531行的时间格式处理
	 */
	$.jgrid = $.jgrid || {};
	$.extend($.jgrid, {
		htmlDecode		: function(value) {
			if (value == '&nbsp;' || value == '&#160;'
			        || (value.length == 1 && value.charCodeAt(0) == 160)) {
				return "";
			}
			return !value ? value : String(value).replace(/&amp;/g, "&")
			        .replace(/&gt;/g, ">").replace(/&lt;/g, "<").replace(
			                /&quot;/g, '"'
			        );
		},
		htmlEncode		: function(value) {
			return !value ? value : String(value).replace(/&/g, "&amp;")
			        .replace(/>/g, "&gt;").replace(/</g, "&lt;").replace(/\"/g,
			                "&quot;"
			        );
		},
		format		 : function(format) { // jqgformat
			var args = $.makeArray(arguments).slice(1);
			if (format === undefined) {
				format = "";
			}
			return format.replace(/\{(\d+)\}/g, function(m, i) {
				return args[i];
			});
		},
		getCellIndex	: function(cell) {
			var c = $(cell);
			if (c.is('tr')) {
				return -1;
			}
			c = (!c.is('td') && !c.is('th') ? c.closest("td,th") : c)[0];
			if ($.browser.msie) {
				return $.inArray(c, c.parentNode.cells);
			}
			return c.cellIndex;
		},
		stripHtml		: function(v) {
			v = v + "";
			var regexp = /<("[^"]*"|'[^']*'|[^'">])*>/gi;
			if (v) {
				v = v.replace(regexp, "");
				return (v && v !== '&nbsp;' && v !== '&#160;') ? v.replace(
				        /\"/g, "'"
				) : "";
			} else {
				return v;
			}
		},
		stringToDoc		: function(xmlString) {
			var xmlDoc;
			if (typeof xmlString !== 'string') {
				return xmlString;
			}
			try {
				var parser = new DOMParser();
				xmlDoc = parser.parseFromString(xmlString, "text/xml");
			} catch (e) {
				xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
				xmlDoc.async = false;
				xmlDoc.loadXML(xmlString);
			}
			return (xmlDoc && xmlDoc.documentElement && xmlDoc.documentElement.tagName != 'parsererror')
			        ? xmlDoc
			        : null;
		},
		parse		 : function(jsonString) {
			var js = jsonString;
			if (js.substr(0, 9) == "while(1);") {
				js = js.substr(9);
			}
			if (js.substr(0, 2) == "/*") {
				js = js.substr(2, js.length - 4);
			}
			if (!js) {
				js = "{}";
			}
			return ($.jgrid.useJSON === true && typeof(JSON) === 'object' && typeof(JSON.parse) === 'function')
			        ? JSON.parse(js)
			        : eval('(' + js + ')');
		},
		parseDate		: function(format, date) {
			var tsp = {
				m	: 1,
				d	: 1,
				y	: 1970,
				h	: 0,
				i	: 0,
				s	: 0
			}, k, hl, dM;
			if (date && date !== null && date !== undefined) {
				date = $.trim(date);
				date = date.split(/[\\\/:_;.\t\T\s-]/);
				format = format.split(/[\\\/:_;.\t\T\s-]/);
				var dfmt = $.jgrid.formatter.date.monthNames;
				var afmt = $.jgrid.formatter.date.AmPm;
				var h12to24 = function(ampm, h) {
					if (ampm === 0) {
						if (h == 12) {
							h = 0;
						}
					} else {
						if (h != 12) {
							h += 12;
						}
					}
					return h;
				};
				for (k = 0, hl = format.length; k < hl; k++) {
					if (format[k] == 'M') {
						dM = $.inArray(date[k], dfmt);
						if (dM !== -1 && dM < 12) {
							date[k] = dM + 1;
						}
					}
					if (format[k] == 'F') {
						dM = $.inArray(date[k], dfmt);
						if (dM !== -1 && dM > 11) {
							date[k] = dM + 1 - 12;
						}
					}
					if (format[k] == 'a') {
						dM = $.inArray(date[k], afmt);
						if (dM !== -1 && dM < 2 && date[k] == afmt[dM]) {
							date[k] = dM;
							tsp.h = h12to24(date[k], tsp.h);
						}
					}
					if (format[k] == 'A') {
						dM = $.inArray(date[k], afmt);
						if (dM !== -1 && dM > 1 && date[k] == afmt[dM]) {
							date[k] = dM - 2;
							tsp.h = h12to24(date[k], tsp.h);
						}
					}
					if (date[k] !== undefined) {
						tsp[format[k].toLowerCase()] = parseInt(date[k], 10);
					}
				}
				tsp.m = parseInt(tsp.m, 10) - 1;
				var ty = tsp.y;
				if (ty >= 70 && ty <= 99) {
					tsp.y = 1900 + tsp.y;
				} else if (ty >= 0 && ty <= 69) {
					tsp.y = 2000 + tsp.y;
				}
			}
			return new Date(tsp.y, tsp.m, tsp.d, tsp.h, tsp.i, tsp.s, 0);
		},
		jqID		 : function(sid) {
			sid = sid + "";
			return sid.replace(/([\.\:\[\]])/g, "\\$1");
		},
		getAccessor		: function(obj, expr) {
			var ret, p, prm, i;
			if (typeof expr === 'function') {
				return expr(obj);
			}
			ret = obj[expr];
			if (ret === undefined) {
				try {
					if (typeof expr === 'string') {
						prm = expr.split('.');
					}
					i = prm.length;
					if (i) {
						ret = obj;
						while (ret && i--) {
							p = prm.shift();
							ret = ret[p];
						}
					}
				} catch (e) {
				}
			}
			return ret;
		},
		ajaxOptions		: {},
		from		 : function(source, initalQuery) {
			// Original Author Hugo Bonacci
			// License MIT http://jlinq.codeplex.com/license
			var queryObject = function(d, q) {
				if (typeof(d) == "string") {
					d = $.data(d);
				}
				var self = this, _data = d, _usecase = true, _trim = false, _query = q, _stripNum = /[\$,%]/g, _lastCommand = null, _lastField = null, _negate = false, _queuedOperator = "", _sorting = [], _useProperties = true;
				if (typeof(d) == "object" && d.push) {
					if (d.length > 0) {
						if (typeof(d[0]) != "object") {
							_useProperties = false;
						} else {
							_useProperties = true;
						}
					}
				} else {
					throw "data provides is not an array";
				}
				this._hasData = function() {
					return _data === null ? false : _data.length === 0
					        ? false
					        : true;
				};
				this._getStr = function(s) {
					var phrase = [];
					if (_trim) {
						phrase.push("jQuery.trim(");
					}
					phrase.push("String(" + s + ")");
					if (_trim) {
						phrase.push(")");
					}
					if (!_usecase) {
						phrase.push(".toLowerCase()");
					}
					return phrase.join("");
				};
				this._strComp = function(val) {
					if (typeof(val) == "string") {
						return ".toString()";
					} else {
						return "";
					}
				};
				this._group = function(f, u) {
					return ({
						field	: f.toString(),
						unique	: u,
						items	: []
					});
				};
				this._toStr = function(phrase) {
					if (_trim) {
						phrase = $.trim(phrase);
					}
					if (!_usecase) {
						phrase = phrase.toLowerCase();
					}
					phrase = phrase.toString().replace(new RegExp('\\"', "g"),
					        '\\"'
					);
					return phrase;
				};
				this._funcLoop = function(func) {
					var results = [];
					$.each(_data, function(i, v) {
						results.push(func(v));
					});
					return results;
				};
				this._append = function(s) {
					if (_query === null) {
						_query = "";
					} else {
						_query += _queuedOperator === ""
						        ? " && "
						        : _queuedOperator;
					}
					if (_negate) {
						_query += "!";
					}
					_query += "(" + s + ")";
					_negate = false;
					_queuedOperator = "";
				};
				this._setCommand = function(f, c) {
					_lastCommand = f;
					_lastField = c;
				};
				this._resetNegate = function() {
					_negate = false;
				};
				this._repeatCommand = function(f, v) {
					if (_lastCommand === null) {
						return self;
					}
					if (f != null && v != null) {
						return _lastCommand(f, v);
					}
					if (_lastField === null) {
						return _lastCommand(f);
					}
					if (!_useProperties) {
						return _lastCommand(f);
					}
					return _lastCommand(_lastField, f);
				};
				this._equals = function(a, b) {
					return (self._compare(a, b, 1) === 0);
				};
				this._compare = function(a, b, d) {
					if (d === undefined) {
						d = 1;
					}
					if (a === undefined) {
						a = null;
					}
					if (b === undefined) {
						b = null;
					}
					if (a === null && b === null) {
						return 0;
					}
					if (a === null && b !== null) {
						return 1;
					}
					if (a !== null && b === null) {
						return -1;
					}
					if (!_usecase && typeof(a) !== "number"
					        && typeof(b) !== "number") {
						a = String(a).toLowerCase();
						b = String(b).toLowerCase();
					}
					if (a < b) {
						return -d;
					}
					if (a > b) {
						return d;
					}
					return 0;
				};
				this._performSort = function() {
					if (_sorting.length === 0) {
						return;
					}
					_data = self._doSort(_data, 0);
				};
				this._doSort = function(d, q) {
					var by = _sorting[q].by, dir = _sorting[q].dir, type = _sorting[q].type, dfmt = _sorting[q].datefmt;
					if (q == _sorting.length - 1) {
						return self._getOrder(d, by, dir, type, dfmt);
					}
					q++;
					var values = self._getGroup(d, by, dir, type, dfmt);
					var results = [];
					for (var i = 0; i < values.length; i++) {
						var sorted = self._doSort(values[i].items, q);
						for (var j = 0; j < sorted.length; j++) {
							results.push(sorted[j]);
						}
					}
					return results;
				};
				this._getOrder = function(data, by, dir, type, dfmt) {
					var sortData = [], _sortData = [], newDir = dir == "a"
					        ? 1
					        : -1, i, ab, j, findSortKey;
					
					if (type === undefined) {
						type = "text";
					}
					if (type == 'float' || type == 'number'
					        || type == 'currency' || type == 'numeric') {
						findSortKey = function($cell, a) {
							var key = parseFloat(String($cell).replace(
							        _stripNum, ''
							));
							
							return isNaN(key) ? 0.00 : key;
						};
					} else if (type == 'int' || type == 'integer') {
						findSortKey = function($cell, a) {
							return $cell ? parseFloat(String($cell).replace(
							        _stripNum, ''
							)) : 0;
						};
					} else if (type == 'date' || type == 'datetime') {
						findSortKey = function($cell, a) {
							return $.jgrid.parseDate(dfmt, $cell).getTime();
						};
					} else if ($.isFunction(type)) {
						findSortKey = type;
					} else {
						findSortKey = function($cell, a) {
							if (!$cell) {
								$cell = "";
							}
							return $.trim(String($cell).toUpperCase());
						};
					}
					$.each(data, function(i, v) {
						ab = by !== "" ? $.jgrid.getAccessor(v, by) : v;
						if (ab === undefined) {
							ab = "";
						}
						ab = findSortKey(ab, v);
						_sortData.push({
							'vSort'	: ab,
							'index'	: i
						});
					});
					
					_sortData.sort(function(a, b) {
						a = a.vSort;
						b = b.vSort;
						return self._compare(a, b, newDir);
					});
					j = 0;
					var nrec = data.length;
					// overhead, but we do not change the original data.
					while (j < nrec) {
						i = _sortData[j].index;
						sortData.push(data[i]);
						j++;
					}
					return sortData;
				};
				this._getGroup = function(data, by, dir, type, dfmt) {
					var results = [], group = null, last = null, val;
					$.each(self._getOrder(data, by, dir, type, dfmt), function(
					        i, v) {
						val = $.jgrid.getAccessor(v, by);
						if (val === undefined) {
							val = "";
						}
						if (!self._equals(last, val)) {
							last = val;
							if (group != null) {
								results.push(group);
							}
							group = self._group(by, val);
						}
						group.items.push(v);
					});
					if (group != null) {
						results.push(group);
					}
					return results;
				};
				this.ignoreCase = function() {
					_usecase = false;
					return self;
				};
				this.useCase = function() {
					_usecase = true;
					return self;
				};
				this.trim = function() {
					_trim = true;
					return self;
				};
				this.noTrim = function() {
					_trim = false;
					return self;
				};
				this.combine = function(f) {
					var q = $.from(_data);
					if (!_usecase) {
						q.ignoreCase();
					}
					if (_trim) {
						q.trim();
					}
					var result = f(q).showQuery();
					self._append(result);
					return self;
				};
				this.execute = function() {
					var match = _query, results = [];
					if (match === null) {
						return self;
					}
					$.each(_data, function() {
						if (eval(match)) {
							results.push(this);
						}
					});
					_data = results;
					return self;
				};
				this.data = function() {
					return _data;
				};
				this.select = function(f) {
					self._performSort();
					if (!self._hasData()) {
						return [];
					}
					self.execute();
					if ($.isFunction(f)) {
						var results = [];
						$.each(_data, function(i, v) {
							results.push(f(v));
						});
						return results;
					}
					return _data;
				};
				this.hasMatch = function(f) {
					if (!self._hasData()) {
						return false;
					}
					self.execute();
					return _data.length > 0;
				};
				this.showQuery = function(cmd) {
					var queryString = _query;
					if (queryString === null) {
						queryString = "no query found";
					}
					if ($.isFunction(cmd)) {
						cmd(queryString);
						return self;
					}
					return queryString;
				};
				this.andNot = function(f, v, x) {
					_negate = !_negate;
					return self.and(f, v, x);
				};
				this.orNot = function(f, v, x) {
					_negate = !_negate;
					return self.or(f, v, x);
				};
				this.not = function(f, v, x) {
					return self.andNot(f, v, x);
				};
				this.and = function(f, v, x) {
					_queuedOperator = " && ";
					if (f === undefined) {
						return self;
					}
					return self._repeatCommand(f, v, x);
				};
				this.or = function(f, v, x) {
					_queuedOperator = " || ";
					if (f === undefined) {
						return self;
					}
					return self._repeatCommand(f, v, x);
				};
				this.isNot = function(f) {
					_negate = !_negate;
					return self.is(f);
				};
				this.is = function(f) {
					self._append('this.' + f);
					self._resetNegate();
					return self;
				};
				this._compareValues = function(func, f, v, how, t) {
					var fld;
					if (_useProperties) {
						fld = 'this.' + f;
					} else {
						fld = 'this';
					}
					if (v === undefined) {
						v = null;
					}
					var val = v === null ? f : v, swst = t.stype === undefined
					        ? "text"
					        : t.stype;
					switch (swst) {
						case 'int' :
						case 'integer' :
							val = isNaN(Number(val)) ? '0' : val; // To be
																	// fixed
																	// with more
																	// inteligent
																	// code
							fld = 'parseInt(' + fld + ',10)';
							val = 'parseInt(' + val + ',10)';
							break;
						case 'float' :
						case 'number' :
						case 'numeric' :
							val = String(val).replace(_stripNum, '');
							val = isNaN(Number(val)) ? '0' : val; // To be
																	// fixed
																	// with more
																	// inteligent
																	// code
							fld = 'parseFloat(' + fld + ')';
							val = 'parseFloat(' + val + ')';
							break;
						case 'date' :
						case 'datetime' :
							val = String($.jgrid.parseDate(t.newfmt || 'Y-m-d',
							        val
							).getTime());
							fld = 'jQuery.jgrid.parseDate("' + t.srcfmt + '",'
							        + fld + ').getTime()';
							break;
						default :
							fld = self._getStr(fld);
							val = self._getStr('"' + self._toStr(val) + '"');
					}
					self._append(fld + ' ' + how + ' ' + val);
					self._setCommand(func, f);
					self._resetNegate();
					return self;
				};
				this.equals = function(f, v, t) {
					return self._compareValues(self.equals, f, v, "==", t);
				};
				this.greater = function(f, v, t) {
					return self._compareValues(self.greater, f, v, ">", t);
				};
				this.less = function(f, v, t) {
					return self._compareValues(self.less, f, v, "<", t);
				};
				this.greaterOrEquals = function(f, v, t) {
					return self._compareValues(self.greaterOrEquals, f, v,
					        ">=", t
					);
				};
				this.lessOrEquals = function(f, v, t) {
					return self
					        ._compareValues(self.lessOrEquals, f, v, "<=", t);
				};
				this.startsWith = function(f, v) {
					var val = (v === undefined || v === null) ? f : v, length = _trim
					        ? $.trim(val.toString()).length
					        : val.toString().length;
					if (_useProperties) {
						self._append(self._getStr('this.' + f) + '.substr(0,'
						        + length + ') == '
						        + self._getStr('"' + self._toStr(v) + '"'));
					} else {
						length = _trim ? $.trim(v.toString()).length : v
						        .toString().length;
						self._append(self._getStr('this') + '.substr(0,'
						        + length + ') == '
						        + self._getStr('"' + self._toStr(f) + '"'));
					}
					self._setCommand(self.startsWith, f);
					self._resetNegate();
					return self;
				};
				this.endsWith = function(f, v) {
					var val = (v === undefined || v === null) ? f : v, length = _trim
					        ? $.trim(val.toString()).length
					        : val.toString().length;
					if (_useProperties) {
						self._append(self._getStr('this.' + f) + '.substr('
						        + self._getStr('this.' + f) + '.length-'
						        + length + ',' + length + ') == "'
						        + self._toStr(v) + '"');
					} else {
						self._append(self._getStr('this') + '.substr('
						        + self._getStr('this') + '.length-"'
						        + self._toStr(f) + '".length,"'
						        + self._toStr(f) + '".length) == "'
						        + self._toStr(f) + '"');
					}
					self._setCommand(self.endsWith, f);
					self._resetNegate();
					return self;
				};
				this.contains = function(f, v) {
					if (_useProperties) {
						self._append(self._getStr('this.' + f) + '.indexOf("'
						        + self._toStr(v) + '",0) > -1');
					} else {
						self._append(self._getStr('this') + '.indexOf("'
						        + self._toStr(f) + '",0) > -1');
					}
					self._setCommand(self.contains, f);
					self._resetNegate();
					return self;
				};
				this.groupBy = function(by, dir, type, datefmt) {
					if (!self._hasData()) {
						return null;
					}
					return self._getGroup(_data, by, dir, type, datefmt);
				};
				this.orderBy = function(by, dir, stype, dfmt) {
					dir = dir === undefined || dir === null ? "a" : $.trim(dir
					        .toString().toLowerCase());
					if (stype === null || stype === undefined) {
						stype = "text";
					}
					if (dfmt === null || dfmt === undefined) {
						dfmt = "Y-m-d";
					}
					if (dir == "desc" || dir == "descending") {
						dir = "d";
					}
					if (dir == "asc" || dir == "ascending") {
						dir = "a";
					}
					_sorting.push({
						by		: by,
						dir		: dir,
						type	: stype,
						datefmt	: dfmt
					});
					return self;
				};
				return self;
			};
			return new queryObject(source, null);
		},
		extend		 : function(methods) {
			$.extend($.fn.jqGrid, methods);
			if (!this.no_legacy_api) {
				$.fn.extend(methods);
			}
		}
	}
	);
	
	$.fn.jqGrid = function(pin) {
		if (typeof pin == 'string') {
			// var fn = $.fn.jqGrid[pin];
			var fn = $.jgrid.getAccessor($.fn.jqGrid, pin);
			if (!fn) {
				throw ("jqGrid - No such method: " + pin);
			}
			var args = $.makeArray(arguments).slice(1);
			return fn.apply(this, args);
		}
		return this.each(function() {
			if (this.grid) {
				return;
			}
			
			var p = $.extend(true, {
				url				  : "",
				height				: 150,
				page				: 1,
				rowNum				: 20,
				rowTotal			: null,
				records				: 0,
				pager				: "",
				pgbuttons			: true,
				pginput				: true,
				colModel			: [],
				rowList				: [],
				colNames			: [],
				sortorder			: "asc",
				sortname			: "",
				datatype			: "xml",
				mtype				: "GET",
				altRows				: false,
				selarrrow			: [],
				savedRow			: [],
				shrinkToFit			: true,
				xmlReader			: {},
				jsonReader			: {},
				subGrid				: false,
				subGridModel		: [],
				reccount			: 0,
				lastpage			: 0,
				lastsort			: 0,
				selrow				: null,
				beforeSelectRow		: null,
				onSelectRow			: null,
				onSortCol			: null,
				ondblClickRow		: null,
				onRightClickRow		: null,
				onPaging			: null,
				onSelectAll			: null,
				loadComplete		: null,
				afterLoad			: null, // add by zhougz 2011-11-1
				gridComplete		: null,
				loadError			: null,
				loadBeforeSend		: null,
				afterInsertRow		: null,
				beforeRequest		: null,
				onHeaderClick		: null,
				viewrecords			: false,
				loadonce			: false,
				multiselect			: false,
				multikey			: false,
				editurl				: null,
				search				: false,
				caption				: "",
				hidegrid			: true,
				hiddengrid			: false,
				postData			: {},
				userData			: {},
				treeGrid			: false,
				treeGridModel		: 'nested',
				treeReader			: {},
				treeANode			: -1,
				ExpandColumn		: null,
				tree_root_level		: 0,
				prmNames			: {
					page		: "page",
					rows		: "rows",
					sort		: "sidx",
					order		: "sord",
					search		: "_search",
					nd			: "nd",
					id			: "id",
					oper		: "oper",
					editoper	: "edit",
					addoper		: "add",
					deloper		: "del",
					subgridid	: "id",
					npage		: null,
					totalrows	: "totalrows"
				},
				forceFit			: false,
				gridstate			: "visible",
				cellEdit			: false,
				cellsubmit			: "remote",
				nv				  : 0,
				loadui				: "enable",
				toolbar				: [
				        false, ""
				],
				scroll				: false,
				multiboxonly		: false,
				deselectAfterSort	: true,
				scrollrows			: false,
				autowidth			: false,
				scrollOffset		: 18,
				cellLayout			: 5,
				subGridWidth		: 20,
				multiselectWidth	: 20,
				gridview			: false,
				rownumWidth			: 25,
				rownumbers			: false,
				pagerpos			: 'center',
				recordpos			: 'right',
				footerrow			: false,
				userDataOnFooter	: false,
				hoverrows			: true,
				altclass			: 'ui-priority-secondary',
				viewsortcols		: [
				        false, 'vertical', true
				],
				resizeclass			: '',
				autoencode			: false,
				remapColumns		: [],
				ajaxGridOptions		: {},
				direction			: "ltr",
				toppager			: false,
				headertitles		: false,
				scrollTimeout		: 40,
				data				: [],
				_index				: {},
				grouping			: false,
				groupingView		: {
					groupField			: [],
					groupOrder			: [],
					groupText			: [],
					groupColumnShow		: [],
					groupSummary		: [],
					showSummaryOnHide	: false,
					sortitems			: [],
					sortnames			: [],
					groupDataSorted		: false,
					summary				: [],
					summaryval			: [],
					plusicon			: 'ui-icon-circlesmall-plus',
					minusicon			: 'ui-icon-circlesmall-minus'
				},
				ignoreCase			: false,
				cmTemplate			: {}
			}, $.jgrid.defaults, pin || {});
			var grid = {
				headers				: [],
				cols				: [],
				footers				: [],
				dragStart			: function(i, x, y) {
					this.resizing = {
						idx		: i,
						startX	: x.clientX,
						sOL		: y[0]
					};
					this.hDiv.style.cursor = "col-resize";
					this.curGbox = $("#rs_m" + p.id, "#gbox_" + p.id);
					this.curGbox.css({
						display	: "block",
						left	: y[0],
						top		: y[1],
						height	: y[2]
					});
					if ($.isFunction(p.resizeStart)) {
						p.resizeStart.call(this, x, i);
					}
					document.onselectstart = function() {
						return false;
					};
				},
				dragMove			: function(x) {
					if (this.resizing) {
						var diff = x.clientX - this.resizing.startX, h = this.headers[this.resizing.idx], newWidth = p.direction === "ltr"
						        ? h.width + diff
						        : h.width - diff, hn, nWn;
						if (newWidth > 33) {
							this.curGbox.css({
								left	: this.resizing.sOL + diff
							});
							if (p.forceFit === true) {
								hn = this.headers[this.resizing.idx + p.nv];
								nWn = p.direction === "ltr"
								        ? hn.width - diff
								        : hn.width + diff;
								if (nWn > 33) {
									h.newWidth = newWidth;
									hn.newWidth = nWn;
								}
							} else {
								this.newWidth = p.direction === "ltr"
								        ? p.tblwidth + diff
								        : p.tblwidth - diff;
								h.newWidth = newWidth;
							}
						}
					}
				},
				dragEnd				: function() {
					this.hDiv.style.cursor = "default";
					if (this.resizing) {
						var idx = this.resizing.idx, nw = this.headers[idx].newWidth
						        || this.headers[idx].width;
						nw = parseInt(nw, 10);
						this.resizing = false;
						$("#rs_m" + p.id).css("display", "none");
						p.colModel[idx].width = nw;
						this.headers[idx].width = nw;
						this.headers[idx].el.style.width = nw + "px";
						this.cols[idx].style.width = nw + "px";
						if (this.footers.length > 0) {
							this.footers[idx].style.width = nw + "px";
						}
						if (p.forceFit === true) {
							nw = this.headers[idx + p.nv].newWidth
							        || this.headers[idx + p.nv].width;
							this.headers[idx + p.nv].width = nw;
							this.headers[idx + p.nv].el.style.width = nw + "px";
							this.cols[idx + p.nv].style.width = nw + "px";
							if (this.footers.length > 0) {
								this.footers[idx + p.nv].style.width = nw
								        + "px";
							}
							p.colModel[idx + p.nv].width = nw;
						} else {
							p.tblwidth = this.newWidth || p.tblwidth;
							$('table:first', this.bDiv).css("width",
							        p.tblwidth + "px"
							);
							$('table:first', this.hDiv).css("width",
							        p.tblwidth + "px"
							);
							this.hDiv.scrollLeft = this.bDiv.scrollLeft;
							if (p.footerrow) {
								$('table:first', this.sDiv).css("width",
								        p.tblwidth + "px"
								);
								this.sDiv.scrollLeft = this.bDiv.scrollLeft;
							}
						}
						if ($.isFunction(p.resizeStop)) {
							p.resizeStop.call(this, nw, idx);
						}
					}
					this.curGbox = null;
					document.onselectstart = function() {
						return true;
					};
				},
				populateVisible		: function() {
					if (grid.timer) {
						clearTimeout(grid.timer);
					}
					grid.timer = null;
					var dh = $(grid.bDiv).height();
					if (!dh) {
						return;
					}
					var table = $("table:first", grid.bDiv);
					var rows = $("> tbody > tr:gt(0):visible:first", table);
					var rh = rows.outerHeight() || grid.prevRowHeight;
					if (!rh) {
						return;
					}
					grid.prevRowHeight = rh;
					var rn = p.rowNum;
					var scrollTop = grid.scrollTop = grid.bDiv.scrollTop;
					var ttop = Math.round(table.position().top) - scrollTop;
					var tbot = ttop + table.height();
					var div = rh * rn;
					var page, npage, empty;
					if (tbot < dh
					        && ttop <= 0
					        && (p.lastpage === undefined || parseInt((tbot
					                        + scrollTop + div - 1)
					                        / div, 10) <= p.lastpage)) {
						npage = parseInt((dh - tbot + div - 1) / div, 10);
						if (tbot >= 0 || npage < 2 || p.scroll === true) {
							page = Math.round((tbot + scrollTop) / div) + 1;
							ttop = -1;
						} else {
							ttop = 1;
						}
					}
					if (ttop > 0) {
						page = parseInt(scrollTop / div, 10) + 1;
						npage = parseInt((scrollTop + dh) / div, 10) + 2 - page;
						empty = true;
					}
					if (npage) {
						if (p.lastpage && page > p.lastpage || p.lastpage == 1) {
							return;
						}
						if (grid.hDiv.loading) {
							grid.timer = setTimeout(grid.populateVisible,
							        p.scrollTimeout
							);
						} else {
							p.page = page;
							if (empty) {
								grid.selectionPreserver(table[0]);
								grid.emptyRows(grid.bDiv, false);
							}
							grid.populate(npage);
						}
					}
				},
				scrollGrid			: function() {
					if (p.scroll) {
						var scrollTop = grid.bDiv.scrollTop;
						if (grid.scrollTop === undefined) {
							grid.scrollTop = 0;
						}
						if (scrollTop != grid.scrollTop) {
							grid.scrollTop = scrollTop;
							if (grid.timer) {
								clearTimeout(grid.timer);
							}
							grid.timer = setTimeout(grid.populateVisible,
							        p.scrollTimeout
							);
						}
					}
					grid.hDiv.scrollLeft = grid.bDiv.scrollLeft;
					if (p.footerrow) {
						grid.sDiv.scrollLeft = grid.bDiv.scrollLeft;
					}
				},
				selectionPreserver	: function(ts) {
					var p = ts.p;
					var sr = p.selrow, sra = p.selarrrow ? $
					        .makeArray(p.selarrrow) : null;
					var left = ts.grid.bDiv.scrollLeft;
					var complete = p.gridComplete;
					p.gridComplete = function() {
						p.selrow = null;
						p.selarrrow = [];
						if (p.multiselect && sra && sra.length > 0) {
							for (var i = 0; i < sra.length; i++) {
								if (sra[i] != sr) {
									$(ts).jqGrid("setSelection", sra[i], false);
								}
							}
						}
						if (sr) {
							$(ts).jqGrid("setSelection", sr, false);
						}
						ts.grid.bDiv.scrollLeft = left;
						p.gridComplete = complete;
						if (p.gridComplete) {
							complete();
						}
					};
				}
			};
			
			/**
			 * add by zhouyingzhao 2011-04-27,
			 * default中设置了rowList之后，自己设置的rowList少的话，不起作用。
			 */
			if (pin.rowList && jQuery.isArray(pin.rowList)) {
				p.rowList = pin.rowList;
			}
			
			if (this.tagName != 'TABLE') {
				alert("Element is not a table");
				return;
			}
			$(this).empty();
			this.p = p;
			var i, dir, ts, clm;
			if (this.p.colNames.length === 0) {
				for (i = 0; i < this.p.colModel.length; i++) {
					this.p.colNames[i] = this.p.colModel[i].label
					        || this.p.colModel[i].name;
				}
			}
			if (this.p.colNames.length !== this.p.colModel.length) {
				return;
			}
			var gv = $("<div class='ui-jqgrid-view'></div>"), ii, isMSIE = $.browser.msie
			        ? true
			        : false, isSafari = $.browser.safari ? true : false;
			ts = this;
			ts.p.direction = $.trim(ts.p.direction.toLowerCase());
			if ($.inArray(ts.p.direction, [
			                "ltr", "rtl"
			        ]) == -1) {
				ts.p.direction = "ltr";
			}
			dir = ts.p.direction;
			
			$(gv).insertBefore(this);
			$(this).appendTo(gv).removeClass("scroll");
			var eg = $("<div class='ui-jqgrid ui-widget ui-widget-content ui-corner-all'></div>");
			$(eg).insertBefore(gv).attr({
				"id"	: "gbox_" + this.id,
				"dir"	: dir
			});
			$(gv).appendTo(eg).attr("id", "gview_" + this.id);
			if (isMSIE && $.browser.version <= 6) {
				ii = '<iframe style="display:block;position:absolute;z-index:-1;filter:Alpha(Opacity=\'0\');" src="javascript:false;"></iframe>';
			} else {
				ii = "";
			}
			$("<div class='ui-widget-overlay jqgrid-overlay' id='lui_"
			        + this.id + "'></div>").append(ii).insertBefore(gv);
			$("<div class='loading ui-state-default ui-state-active' id='load_"
			        + this.id + "'>" + this.p.loadtext + "</div>")
			        .insertBefore(gv);
			$(this).attr({
				cellspacing				: "0",
				cellpadding				: "0",
				border				   : "0",
				"role"				   : "grid",
				"aria-multiselectable"	: !!this.p.multiselect,
				"aria-labelledby"		: "gbox_" + this.id
			});
			var sortkeys = [
			        "shiftKey", "altKey", "ctrlKey"
			], intNum = function(val, defval) {
				val = parseInt(val, 10);
				if (isNaN(val)) {
					return defval ? defval : 0;
				} else {
					return val;
				}
			}, formatCol = function(pos, rowInd, tv) {
				var cm = ts.p.colModel[pos], ral = cm.align, result = "style=\"", clas = cm.classes, nm = cm.name;
				if (ral) {
					result += "text-align:" + ral + ";";
				}
				if (cm.hidden === true) {
					result += "display:none;";
				}
				if (rowInd === 0) {
					result += "width: " + grid.headers[pos].width + "px;";
				}
				// edit by qiujie 20120921 修改操作列显示全部的操作title
				if (tv != undefined) {
					var $tv = tv + '';
					if ($tv.indexOf("<a") != "-1") {
						result += "\""
						        + (clas !== undefined
						                ? (" class=\"" + clas + "\"")
						                : "") + ((cm.title && tv) ? (" ") : "");
					} else {
						result += "\""
						        + (clas !== undefined
						                ? (" class=\"" + clas + "\"")
						                : "")
						        + ((cm.title && tv) ? (" title=\""
						                + $.jgrid.stripHtml(tv) + "\"") : "");
					}
				} else {
					result += "\""
					        + (clas !== undefined
					                ? (" class=\"" + clas + "\"")
					                : "")
					        + ((cm.title && tv) ? (" title=\""
					                + $.jgrid.stripHtml(tv) + "\"") : "");
				}
				
				result += " aria-describedby=\"" + ts.p.id + "_" + nm + "\"";
				return result;
			}, cellVal = function(val) {
				return val === undefined || val === null || val === ""
				        ? "&#160;"
				        : (ts.p.autoencode ? $.jgrid.htmlEncode(val) : val + "");
			}, formatter = function(rowId, cellval, colpos, rwdat, _act) {
				var cm = ts.p.colModel[colpos], v;
				if (typeof cm.formatter !== 'undefined') {
					var opts = {
						rowId		: rowId,
						colModel	: cm,
						gid			: ts.p.id,
						pos			: colpos
					};
					if ($.isFunction(cm.formatter)) {
						v = cm.formatter.call(ts, cellval, opts, rwdat, _act);
					} else if ($.fmatter) {
						v = $.fn.fmatter(cm.formatter, cellval, opts, rwdat,
						        _act
						);
					} else {
						v = cellVal(cellval);
					}
				} else {
					v = cellVal(cellval);
				}
				return v;
			}, addCell = function(rowId, cell, pos, irow, srvr) {
				var v, prp;
				/** 把html标签转义 add by qiujie 2012-06-20 * */
				var celstr = "";
				if (cell != null && cell != undefined
				        && (typeof cell == "string")) {// 只对数据类型为字符串的进行转义
														// update by xiajb
														// 2012-06-21
					for (var i = 0; i < cell.length; i++) {
						if (cell[i] == "<") {
							celstr += "&lt;"
						} else if (cell[i] == ">") {
							celstr += "&gt;"
						} else {
							celstr += cell[i]
						};
					}
					
				} else {
					celstr = cell;
				}
				/** 把html标签转义 add by qiujie 2012-06-20 * */
				v = formatter(rowId, celstr, pos, srvr, 'add');
				
				prp = formatCol(pos, irow, v);
				return "<td role=\"gridcell\" " + prp + ">" + v + "</td>";
			}, addMulti = function(rowid, pos, irow) {
				var v = "<input role=\"checkbox\" type=\"checkbox\""
				        + " id=\"jqg_" + ts.p.id + "_" + rowid
				        + "\" class=\"cbox\" name=\"jqg_" + ts.p.id + "_"
				        + rowid + "\"/>", prp = formatCol(pos, irow, '');
				return "<td role=\"gridcell\" " + prp + ">" + v + "</td>";
			}, addRowNum = function(pos, irow, pG, rN) {
				var v = (parseInt(pG, 10) - 1) * parseInt(rN, 10) + 1 + irow, prp = formatCol(
				        pos, irow, ''
				);
				return "<td role=\"gridcell\" class=\"ui-state-default jqgrid-rownum\" "
				        + prp + ">" + v + "</td>";
			}, reader = function(datatype) {
				var field, f = [], j = 0, i;
				for (i = 0; i < ts.p.colModel.length; i++) {
					field = ts.p.colModel[i];
					if (field.name !== 'cb' && field.name !== 'subgrid'
					        && field.name !== 'rn') {
						if (datatype == "local") {
							f[j] = field.name;
						} else {
							f[j] = (datatype == "xml") ? field.xmlmap
							        || field.name : field.jsonmap || field.name;
						}
						j++;
					}
				}
				return f;
			}, orderedCols = function(offset) {
				var order = ts.p.remapColumns;
				if (!order || !order.length) {
					order = $.map(ts.p.colModel, function(v, i) {
						return i;
					});
				}
				if (offset) {
					order = $.map(order, function(v) {
						return v < offset ? null : v - offset;
					});
				}
				return order;
			}, emptyRows = function(parent, scroll) {
				if (ts.p.deepempty) {
					$("#" + ts.p.id + " tbody:first tr:gt(0)").remove();
				} else {
					var trf = $("#" + ts.p.id + " tbody:first tr:first")[0];
					$("#" + ts.p.id + " tbody:first").empty().append(trf);
				}
				
				if (scroll && ts.p.scroll) {
					$(">div:first", parent).css({
						height	: "auto"
					}).children("div:first").css({
						height	: 0,
						display	: "none"
					});
					parent.scrollTop = 0;
				}
				
			}, refreshIndex = function() {
				var datalen = ts.p.data.length, idname, i, val, ni = ts.p.rownumbers === true
				        ? 1
				        : 0, gi = ts.p.multiselect === true ? 1 : 0, si = ts.p.subGrid === true
				        ? 1
				        : 0;
				
				if (ts.p.keyIndex === false || ts.p.loadonce === true) {
					idname = ts.p.localReader.id;
				} else {
					idname = ts.p.colModel[ts.p.keyIndex + gi + si + ni].name;
				}
				for (i = 0; i < datalen; i++) {
					val = $.jgrid.getAccessor(ts.p.data[i], idname);
					ts.p._index[val] = i;
				}
			}, addXmlData = function(xml, t, rcnt, more, adjust) {
				var startReq = new Date(), locdata = (ts.p.datatype != "local" && ts.p.loadonce)
				        || ts.p.datatype == "xmlstring", xmlid, frd = ts.p.datatype == "local"
				        ? "local"
				        : "xml";
				if (locdata) {
					ts.p.data = [];
					ts.p._index = {};
					ts.p.localReader.id = xmlid = "_id_";
				}
				ts.p.reccount = 0;
				if ($.isXMLDoc(xml)) {
					if (ts.p.treeANode === -1 && !ts.p.scroll) {
						emptyRows(t, false);
						rcnt = 1;
					} else {
						rcnt = rcnt > 1 ? rcnt : 1;
					}
				} else {
					return;
				}
				var i, fpos, ir = 0, v, row, gi = 0, si = 0, ni = 0, idn, getId, f = [], F, rd = {}, xmlr, rid, rowData = [], cn = (ts.p.altRows === true)
				        ? " " + ts.p.altclass
				        : "", cn1;
				if (!ts.p.xmlReader.repeatitems) {
					f = reader(frd);
				}
				if (ts.p.keyIndex === false) {
					idn = ts.p.xmlReader.id;
				} else {
					idn = ts.p.keyIndex;
				}
				if (f.length > 0 && !isNaN(idn)) {
					if (ts.p.remapColumns && ts.p.remapColumns.length) {
						idn = $.inArray(idn, ts.p.remapColumns);
					}
					idn = f[idn];
				}
				if ((idn + "").indexOf("[") === -1) {
					if (f.length) {
						getId = function(trow, k) {
							return $(idn, trow).text() || k;
						};
					} else {
						getId = function(trow, k) {
							return $(ts.p.xmlReader.cell, trow).eq(idn).text()
							        || k;
						};
					}
				} else {
					getId = function(trow, k) {
						return trow.getAttribute(idn.replace(/[\[\]]/g, ""))
						        || k;
					};
				}
				ts.p.userData = {};
				$(ts.p.xmlReader.page, xml).each(function() {
					ts.p.page = this.textContent || this.text || 0;
				});
				$(ts.p.xmlReader.total, xml).each(function() {
					ts.p.lastpage = this.textContent || this.text;
					if (ts.p.lastpage === undefined) {
						ts.p.lastpage = 1;
					}
				});
				$(ts.p.xmlReader.records, xml).each(function() {
					ts.p.records = this.textContent || this.text || 0;
				});
				$(ts.p.xmlReader.userdata, xml).each(function() {
					ts.p.userData[this.getAttribute("name")] = this.textContent
					        || this.text;
				}
				);
				var gxml = $(ts.p.xmlReader.root + " " + ts.p.xmlReader.row,
				        xml
				);
				if (!gxml) {
					gxml = [];
				}
				var gl = gxml.length, j = 0;
				if (gxml && gl) {
					var rn = parseInt(ts.p.rowNum, 10), br = ts.p.scroll
					        ? (parseInt(ts.p.page, 10) - 1) * rn + 1
					        : 1, altr;
					if (adjust) {
						rn *= adjust + 1;
					}
					var afterInsRow = $.isFunction(ts.p.afterInsertRow), grpdata = {}, hiderow = "";
					if (ts.p.grouping
					        && ts.p.groupingView.groupCollapse === true) {
						hiderow = " style=\"display:none;\"";
					}
					while (j < gl) {
						xmlr = gxml[j];
						rid = getId(xmlr, br + j);
						altr = rcnt === 0 ? 0 : rcnt + 1;
						cn1 = (altr + j) % 2 == 1 ? cn : '';
						rowData
						        .push("<tr"
						                + hiderow
						                + " id=\""
						                + rid
						                + "\" role=\"row\" class =\"ui-widget-content jqgrow ui-row-"
						                + ts.p.direction + "" + cn1 + "\">");
						if (ts.p.rownumbers === true) {
							rowData
							        .push(addRowNum(0, j, ts.p.page,
							                ts.p.rowNum
							        ));
							ni = 1;
						}
						if (ts.p.multiselect === true) {
							rowData.push(addMulti(rid, ni, j));
							gi = 1;
						}
						if (ts.p.subGrid === true) {
							rowData.push($(ts).jqGrid("addSubGridCell",
							        gi + ni, j + rcnt
							));
							si = 1;
						}
						if (ts.p.xmlReader.repeatitems) {
							if (!F) {
								F = orderedCols(gi + si + ni);
							}
							var cells = $(ts.p.xmlReader.cell, xmlr);
							$.each(F, function(k) {
								var cell = cells[this];
								if (!cell) {
									return false;
								}
								v = cell.textContent || cell.text;
								rd[ts.p.colModel[k + gi + si + ni].name] = v;
								rowData.push(addCell(rid, v, k + gi + si + ni,
								        j + rcnt, xmlr
								));
							});
						} else {
							for (i = 0; i < f.length; i++) {
								v = $(f[i], xmlr).text();
								rd[ts.p.colModel[i + gi + si + ni].name] = v;
								rowData.push(addCell(rid, v, i + gi + si + ni,
								        j + rcnt, xmlr
								));
							}
						}
						rowData.push("</tr>");
						if (ts.p.grouping) {
							var grlen = ts.p.groupingView.groupField.length, grpitem = [];
							for (var z = 0; z < grlen; z++) {
								grpitem
								        .push(rd[ts.p.groupingView.groupField[z]]);
							}
							grpdata = $(ts).jqGrid('groupingPrepare', rowData,
							        grpitem, grpdata, rd
							);
							rowData = [];
						}
						if (locdata) {
							rd[xmlid] = rid;
							ts.p.data.push(rd);
						}
						if (ts.p.gridview === false) {
							if (ts.p.treeGrid === true) {
								fpos = ts.p.treeANode > -1 ? ts.p.treeANode : 0;
								row = $(rowData.join(''))[0]; // speed
																// overhead
								$(ts.rows[j + fpos]).after(row);
								try {
									$(ts).jqGrid("setTreeNode", rd, row);
								} catch (e) {
								}
							} else {
								$("tbody:first", t).append(rowData.join(''));
							}
							if (ts.p.subGrid === true) {
								try {
									$(ts).jqGrid("addSubGrid",
									        ts.rows[ts.rows.length - 1],
									        gi + ni
									);
								} catch (_) {
								}
							}
							if (afterInsRow) {
								ts.p.afterInsertRow.call(ts, rid, rd, xmlr);
							}
							rowData = [];
						}
						rd = {};
						ir++;
						j++;
						if (ir == rn) {
							break;
						}
					}
				}
				if (ts.p.gridview === true) {
					if (ts.p.grouping) {
						$(ts).jqGrid('groupingRender', grpdata,
						        ts.p.colModel.length
						);
						grpdata = null;
					} else {
						$("tbody:first", t).append(rowData.join(''));
					}
				}
				ts.p.totaltime = new Date() - startReq;
				if (ir > 0) {
					if (ts.p.records === 0) {
						ts.p.records = gl;
					}
				}
				rowData = null;
				if (!ts.p.treeGrid && !ts.p.scroll) {
					ts.grid.bDiv.scrollTop = 0;
				}
				ts.p.reccount = ir;
				ts.p.treeANode = -1;
				if (ts.p.userDataOnFooter) {
					$(ts).jqGrid("footerData", "set", ts.p.userData, true);
				}
				if (locdata) {
					ts.p.records = gl;
					ts.p.lastpage = Math.ceil(gl / rn);
				}
				if (!more) {
					ts.updatepager(false, true);
				}
				if (locdata) {
					while (ir < gl) {
						xmlr = gxml[ir];
						rid = getId(xmlr, ir);
						if (ts.p.xmlReader.repeatitems) {
							if (!F) {
								F = orderedCols(gi + si + ni);
							}
							var cells2 = $(ts.p.xmlReader.cell, xmlr);
							$.each(F, function(k) {
								var cell = cells2[this];
								if (!cell) {
									return false;
								}
								v = cell.textContent || cell.text;
								rd[ts.p.colModel[k + gi + si + ni].name] = v;
							});
						} else {
							for (i = 0; i < f.length; i++) {
								v = $(f[i], xmlr).text();
								rd[ts.p.colModel[i + gi + si + ni].name] = v;
							}
						}
						rd[xmlid] = rid;
						ts.p.data.push(rd);
						rd = {};
						ir++;
					}
					refreshIndex();
				}
			}, addJSONData = function(data, t, rcnt, more, adjust) {
				ts.p.pageNum = data.page;
				var startReq = new Date();
				if (data) {
					if (ts.p.treeANode === -1 && !ts.p.scroll) {
						emptyRows(t, false);
						rcnt = 1;
					} else {
						rcnt = rcnt > 1 ? rcnt : 1;
					}
				} else {
					return;
				}
				
				var dReader, locid, frd, locdata = (ts.p.datatype != "local" && ts.p.loadonce)
				        || ts.p.datatype == "jsonstring";
				if (locdata) {
					ts.p.data = [];
					ts.p._index = {};
					locid = ts.p.localReader.id = "_id_";
				}
				ts.p.reccount = 0;
				if (ts.p.datatype == "local") {
					dReader = ts.p.localReader;
					frd = 'local';
				} else {
					dReader = ts.p.jsonReader;
					frd = 'json';
				}
				var ir = 0, v, i, j, row, f = [], F, cur, gi = 0, si = 0, ni = 0, len, drows, idn, rd = {}, fpos, idr, rowData = [], cn = (ts.p.altRows === true)
				        ? " " + ts.p.altclass
				        : "", cn1, lp;
				ts.p.page = $.jgrid.getAccessor(data, dReader.page) || 0;
				lp = $.jgrid.getAccessor(data, dReader.total);
				ts.p.lastpage = lp === undefined ? 1 : lp;
				ts.p.records = $.jgrid.getAccessor(data, dReader.records) || 0;
				ts.p.userData = $.jgrid.getAccessor(data, dReader.userdata)
				        || {};
				if (!dReader.repeatitems) {
					F = f = reader(frd);
				}
				if (ts.p.keyIndex === false) {
					idn = dReader.id;
				} else {
					idn = ts.p.keyIndex;
				}
				if (f.length > 0 && !isNaN(idn)) {
					if (ts.p.remapColumns && ts.p.remapColumns.length) {
						idn = $.inArray(idn, ts.p.remapColumns);
					}
					idn = f[idn];
				}
				drows = $.jgrid.getAccessor(data, dReader.root);
				if (!drows) {
					drows = [];
				}
				len = drows.length;
				i = 0;
				var rn = parseInt(ts.p.rowNum, 10), br = ts.p.scroll
				        ? (parseInt(ts.p.page, 10) - 1) * rn + 1
				        : 1, altr;
				if (adjust) {
					rn *= adjust + 1;
				}
				var afterInsRow = $.isFunction(ts.p.afterInsertRow), grpdata = {}, hiderow = "";
				if (ts.p.grouping && ts.p.groupingView.groupCollapse === true) {
					hiderow = " style=\"display:none;\"";
				}
				// add by qiujie 修改grid有横向滚动条且没有数据时，标题显示不完整 2012-08-23
				if (len == 0) {
					$("#" + ts.p.id + " tbody:first tr:first").css("height",
					        "1"
					);
					$(".ui-jqgrid tr.jqgfirstrow td").css("border-right-width",
					        "1"
					);
				} else {
					$("#" + ts.p.id + " tbody:first tr:first").css("height",
					        "auto"
					);
					$(".ui-jqgrid tr.jqgfirstrow td").css("border-right-width",
					        "1"
					);
				}
				/* end */
				while (i < len) {
					cur = drows[i];
					idr = $.jgrid.getAccessor(cur, idn);
					if (idr === undefined) {
						idr = br + i;
						if (f.length === 0) {
							if (dReader.cell) {
								var ccur = cur[dReader.cell];
								idr = ccur[idn] || idr;
								ccur = null;
							}
						}
					}
					altr = rcnt === 1 ? 0 : rcnt;
					cn1 = (altr + i) % 2 == 1 ? cn : '';
					rowData
					        .push("<tr"
					                + hiderow
					                + " id=\""
					                + idr
					                + "\" role=\"row\" class= \"ui-widget-content jqgrow ui-row-"
					                + ts.p.direction + "" + cn1 + "\">");
					if (ts.p.rownumbers === true) {
						rowData.push(addRowNum(0, i, ts.p.page, ts.p.rowNum));
						ni = 1;
					}
					if (ts.p.multiselect) {
						rowData.push(addMulti(idr, ni, i));
						gi = 1;
					}
					if (ts.p.subGrid) {
						rowData.push($(ts).jqGrid("addSubGridCell", gi + ni,
						        i + rcnt
						));
						si = 1;
					}
					if (dReader.repeatitems) {
						if (dReader.cell) {
							cur = $.jgrid.getAccessor(cur, dReader.cell);
						}
						if (!F) {
							F = orderedCols(gi + si + ni);
						}
					}
					for (j = 0; j < F.length; j++) {
						v = $.jgrid.getAccessor(cur, F[j]);
						rowData.push(addCell(idr, v, j + gi + si + ni,
						        i + rcnt, cur
						));
						rd[ts.p.colModel[j + gi + si + ni].name] = v;
					}
					rowData.push("</tr>");
					if (ts.p.grouping) {
						var grlen = ts.p.groupingView.groupField.length, grpitem = [];
						for (var z = 0; z < grlen; z++) {
							grpitem.push(rd[ts.p.groupingView.groupField[z]]);
						}
						
						grpdata = $(ts).jqGrid('groupingPrepare', rowData,
						        grpitem, grpdata, rd
						);
						rowData = [];
					}
					if (locdata) {
						rd[locid] = idr;
						ts.p.data.push(rd);
					}
					if (ts.p.gridview === false) {
						if (ts.p.treeGrid === true) {
							fpos = ts.p.treeANode > -1 ? ts.p.treeANode : 0;
							row = $(rowData.join(''))[0];
							$(ts.rows[i + fpos]).after(row);
							try {
								$(ts).jqGrid("setTreeNode", rd, row);
							} catch (e) {
							}
						} else {
							$("#" + ts.p.id + " tbody:first").append(rowData
							        .join(''));
						}
						if (ts.p.subGrid === true) {
							try {
								$(ts).jqGrid("addSubGrid",
								        ts.rows[ts.rows.length - 1], gi + ni
								);
							} catch (_) {
							}
						}
						if (afterInsRow) {
							ts.p.afterInsertRow.call(ts, idr, rd, cur);
						}
						rowData = [];// ari=0;
					}
					rd = {};
					ir++;
					i++;
					if (ir == rn) {
						break;
					}
				}
				if (ts.p.gridview === true) {
					if (ts.p.grouping) {
						$(ts).jqGrid('groupingRender', grpdata,
						        ts.p.colModel.length
						);
						grpdata = null;
					} else {
						$("#" + ts.p.id + " tbody:first").append(rowData
						        .join(''));
					}
				}
				ts.p.totaltime = new Date() - startReq;
				if (ir > 0) {
					if (ts.p.records === 0) {
						ts.p.records = len;
					}
				}
				rowData = null;
				if (!ts.p.treeGrid && !ts.p.scroll) {
					ts.grid.bDiv.scrollTop = 0;
				}
				ts.p.reccount = ir;
				ts.p.treeANode = -1;
				if (ts.p.userDataOnFooter) {
					$(ts).jqGrid("footerData", "set", ts.p.userData, true);
				}
				if (locdata) {
					ts.p.records = len;
					ts.p.lastpage = Math.ceil(len / rn);
				}
				if (!more) {
					ts.updatepager(false, true);
				}
				if (locdata) {
					while (ir < len) {
						cur = drows[ir];
						idr = $.jgrid.getAccessor(cur, idn);
						if (idr === undefined) {
							idr = br + ir;
							if (f.length === 0) {
								if (dReader.cell) {
									var ccur2 = cur[dReader.cell];
									idr = ccur2[idn] || idr;
									ccur2 = null;
								}
							}
						}
						if (cur) {
							if (dReader.repeatitems) {
								if (dReader.cell) {
									cur = $.jgrid
									        .getAccessor(cur, dReader.cell);
								}
								if (!F) {
									F = orderedCols(gi + si + ni);
								}
							}
							
							for (j = 0; j < F.length; j++) {
								v = $.jgrid.getAccessor(cur, F[j]);
								rd[ts.p.colModel[j + gi + si + ni].name] = v;
							}
							rd[locid] = idr;
							ts.p.data.push(rd);
							rd = {};
						}
						ir++;
					}
					refreshIndex();
				}
			}, addLocalData = function() {
				var st, fndsort = false, cmtypes = [], grtypes = [], grindexes = [], srcformat, sorttype, newformat;
				if (!$.isArray(ts.p.data)) {
					return;
				}
				var grpview = ts.p.grouping ? ts.p.groupingView : false;
				$.each(ts.p.colModel, function(i, v) {
					sorttype = this.sorttype || "text";
					if (sorttype == "date" || sorttype == "datetime") {
						if (this.formatter
						        && typeof(this.formatter) === 'string'
						        && this.formatter == 'date') {
							if (this.formatoptions
							        && this.formatoptions.srcformat) {
								srcformat = this.formatoptions.srcformat;
							} else {
								srcformat = $.jgrid.formatter.date.srcformat;
							}
							if (this.formatoptions
							        && this.formatoptions.newformat) {
								newformat = this.formatoptions.newformat;
							} else {
								newformat = $.jgrid.formatter.date.newformat;
							}
						} else {
							srcformat = newformat = this.datefmt || "Y-m-d";
						}
						cmtypes[this.name] = {
							"stype"		: sorttype,
							"srcfmt"	: srcformat,
							"newfmt"	: newformat
						};
					} else {
						cmtypes[this.name] = {
							"stype"		: sorttype,
							"srcfmt"	: '',
							"newfmt"	: ''
						};
					}
					if (ts.p.grouping && this.name == grpview.groupField[0]) {
						var grindex = this.name
						if (typeof this.index != 'undefined') {
							grindex = this.index;
						}
						grtypes[0] = cmtypes[grindex];
						grindexes.push(grindex);
					}
					if (!fndsort
					        && (this.index == ts.p.sortname || this.name == ts.p.sortname)) {
						st = this.name; // ???
						fndsort = true;
					}
				}
				);
				if (ts.p.treeGrid) {
					$(ts).jqGrid("SortTree", st, ts.p.sortorder,
					        cmtypes[st].stype, cmtypes[st].srcfmt
					);
					return;
				}
				var compareFnMap = {
					'eq'	: function(queryObj) {
						return queryObj.equals;
					},
					'ne'	: function(queryObj) {
						return queryObj.not().equals;
					},
					'lt'	: function(queryObj) {
						return queryObj.less;
					},
					'le'	: function(queryObj) {
						return queryObj.lessOrEquals;
					},
					'gt'	: function(queryObj) {
						return queryObj.greater;
					},
					'ge'	: function(queryObj) {
						return queryObj.greaterOrEquals;
					},
					'cn'	: function(queryObj) {
						return queryObj.contains;
					},
					'nc'	: function(queryObj) {
						return queryObj.not().contains;
					},
					'bw'	: function(queryObj) {
						return queryObj.startsWith;
					},
					'bn'	: function(queryObj) {
						return queryObj.not().startsWith;
					},
					'en'	: function(queryObj) {
						return queryObj.not().endsWith;
					},
					'ew'	: function(queryObj) {
						return queryObj.endsWith;
					},
					'ni'	: function(queryObj) {
						return queryObj.not().equals;
					},
					'in'	: function(queryObj) {
						return queryObj.equals;
					}
					
				}, query = $.jgrid.from(ts.p.data);
				if (ts.p.ignoreCase) {
					query = query.ignoreCase();
				}
				if (ts.p.search === true) {
					var srules = ts.p.postData.filters, opr;
					if (srules) {
						if (typeof srules == "string") {
							srules = $.jgrid.parse(srules);
						}
						for (var i = 0, l = srules.rules.length, rule; i < l; i++) {
							rule = srules.rules[i];
							opr = srules.groupOp;
							if (compareFnMap[rule.op] && rule.field
							        && rule.data && opr) {
								if (opr.toUpperCase() == "OR") {
									query = compareFnMap[rule.op](query)(
									        rule.field, rule.data,
									        cmtypes[rule.field]
									).or();
								} else {
									query = compareFnMap[rule.op](query)(
									        rule.field, rule.data,
									        cmtypes[rule.field]
									);
								}
							}
						}
					} else {
						try {
							query = compareFnMap[ts.p.postData.searchOper](query)(
							        ts.p.postData.searchField,
							        ts.p.postData.searchString,
							        cmtypes[ts.p.postData.searchField]
							);
						} catch (se) {
						}
					}
				}
				if (ts.p.grouping) {
					query.orderBy(grindexes, grpview.groupOrder[0],
					        grtypes[0].stype, grtypes[0].srcfmt
					);
					grpview.groupDataSorted = true;
				}
				if (st && ts.p.sortorder && fndsort) {
					if (ts.p.sortorder.toUpperCase() == "DESC") {
						query.orderBy(ts.p.sortname, "d", cmtypes[st].stype,
						        cmtypes[st].srcfmt
						);
					} else {
						query.orderBy(ts.p.sortname, "a", cmtypes[st].stype,
						        cmtypes[st].srcfmt
						);
					}
				}
				var queryResults = query.select(), recordsperpage = parseInt(
				        ts.p.rowNum, 10
				), total = queryResults.length, page = parseInt(ts.p.page, 10), totalpages = Math
				        .ceil(total / recordsperpage), retresult = {};
				queryResults = queryResults.slice((page - 1) * recordsperpage,
				        page * recordsperpage
				);
				query = null;
				cmtypes = null;
				retresult[ts.p.localReader.total] = totalpages;
				retresult[ts.p.localReader.page] = page;
				retresult[ts.p.localReader.records] = total;
				retresult[ts.p.localReader.root] = queryResults;
				queryResults = null;
				return retresult;
			}, updatepager = function(rn, dnd) {
				var cp, last, base, from, to, tot, fmt, pgboxes = "";
				base = parseInt(ts.p.page, 10) - 1;
				if (base < 0) {
					base = 0;
				}
				base = base * parseInt(ts.p.rowNum, 10);
				
				to = base + ts.p.reccount;
				if (ts.p.scroll) {
					var rows = $("tbody:first > tr:gt(0)", ts.grid.bDiv);
					base = to - rows.length;
					ts.p.reccount = rows.length;
					var rh = rows.outerHeight() || ts.grid.prevRowHeight;
					if (rh) {
						var top = base * rh;
						var height = parseInt(ts.p.records, 10) * rh;
						$(">div:first", ts.grid.bDiv).css({
							height	: height
						}).children("div:first").css({
							height	: top,
							display	: top ? "" : "none"
						});
					}
					ts.grid.bDiv.scrollLeft = ts.grid.hDiv.scrollLeft;
				}
				pgboxes = ts.p.pager ? ts.p.pager : "";
				pgboxes += ts.p.toppager ? (pgboxes
				        ? "," + ts.p.toppager
				        : ts.p.toppager) : "";
				if (pgboxes) {
					fmt = $.jgrid.formatter.integer || {};
					cp = intNum(ts.p.page);
					last = intNum(ts.p.lastpage);
					$(".selbox", pgboxes).attr("disabled", false);
					if (ts.p.pginput === true) {
						$('.ui-pg-input', pgboxes).val(ts.p.page);
						$('#sp_1', pgboxes).html($.fmatter
						        ? $.fmatter.util.NumberFormat(ts.p.lastpage,
						                fmt
						        )
						        : ts.p.lastpage);
						
					}
					if (ts.p.viewrecords) {
						if (ts.p.reccount === 0) {
							$(".ui-paging-info", pgboxes)
							        .html(ts.p.emptyrecords);
						} else {
							from = base + 1;
							tot = ts.p.records;
							if ($.fmatter) {
								from = $.fmatter.util.NumberFormat(from, fmt);
								to = $.fmatter.util.NumberFormat(to, fmt);
								tot = $.fmatter.util.NumberFormat(tot, fmt);
							}
							$(".ui-paging-info", pgboxes).html($.jgrid.format(
							        ts.p.recordtext, from, to, tot
							));
						}
					}
					if (ts.p.pgbuttons === true) {
						if (cp <= 0) {
							cp = last = 0;
						}
						if (cp == 1 || cp === 0) {
							
							$("#first, #prev", ts.p.pager)
							        .addClass('ui-state-disabled')
							        .removeClass('ui-state-hover');
							/* 2012-07-9 edit by qiujie 修改翻页条。 */
							$("#first", ts.p.pager).find('span')
							        .addClass('ui-icon-seek-first-disabled')
							        .removeClass('ui-icon-seek-first');
							$("#prev", ts.p.pager).find('span')
							        .addClass('ui-icon-seek-prev-disabled')
							        .removeClass('ui-icon-seek-prev');
							if (ts.p.toppager) {
								$("#first_t, #prev_t", ts.p.toppager)
								        .addClass('ui-state-disabled')
								        .removeClass('ui-state-hover');
							}
						} else {
							$("#first, #prev", ts.p.pager)
							        .removeClass('ui-state-disabled');
							/* 2012-07-9 edit by qiujie 修改翻页条。 */
							$("#first", ts.p.pager).find('span')
							        .addClass('ui-icon-seek-first')
							        .removeClass('ui-icon-seek-first-disabled');
							$("#prev", ts.p.pager).find('span')
							        .addClass('ui-icon-seek-prev')
							        .removeClass('ui-icon-seek-prev-disabled');
							if (ts.p.toppager) {
								$("#first_t, #prev_t", ts.p.toppager)
								        .removeClass('ui-state-disabled');
							}
						}
						if (cp == last || cp === 0) {
							$("#next, #last", ts.p.pager)
							        .addClass('ui-state-disabled')
							        .removeClass('ui-state-hover');
							/* 2012-07-9 edit by qiujie 修改翻页条。 */
							$("#next", ts.p.pager).find('span')
							        .addClass('ui-icon-seek-next-disabled')
							        .removeClass('ui-icon-seek-next');
							$("#last", ts.p.pager).find('span')
							        .addClass('ui-icon-seek-end-disabled')
							        .removeClass('ui-icon-seek-end');
							if (ts.p.toppager) {
								$("#next_t, #last_t", ts.p.toppager)
								        .addClass('ui-state-disabled')
								        .removeClass('ui-state-hover');
							}
						} else {
							$("#next, #last", ts.p.pager)
							        .removeClass('ui-state-disabled');
							/* 2012-07-9 edit by qiujie 修改翻页条。 */
							$("#next", ts.p.pager).find('span')
							        .addClass('ui-icon-seek-next')
							        .removeClass('ui-icon-seek-next-disabled');
							$("#last", ts.p.pager).find('span')
							        .addClass('ui-icon-seek-end')
							        .removeClass('ui-icon-seek-end-disabled');
							if (ts.p.toppager) {
								$("#next_t, #last_t", ts.p.toppager)
								        .removeClass('ui-state-disabled');
							}
						}
					}
				}
				if (rn === true && ts.p.rownumbers === true) {
					$("td.jqgrid-rownum", ts.rows).each(function(i) {
						if (base < 0) { // edit by qiujie 20121108
										// 修改有子列表时删除数据序号不正确
							base = 0;
						}
						$(this).html(base + 1 + i);
					});
					
				}
				if (dnd && ts.p.jqgdnd) {
					$(ts).jqGrid('gridDnD', 'updateDnD');
				}
				if ($.isFunction(ts.p.gridComplete)) {
					ts.p.gridComplete.call(ts);
				}
			}, beginReq = function() {
				ts.grid.hDiv.loading = true;
				if (ts.p.hiddengrid) {
					return;
				}
				switch (ts.p.loadui) {
					case "disable" :
						break;
					case "enable" :
						$("#load_" + ts.p.id).show();
						break;
					case "block" :
						$("#lui_" + ts.p.id).show();
						$("#load_" + ts.p.id).show();
						break;
				}
			}, endReq = function() {
				ts.grid.hDiv.loading = false;
				switch (ts.p.loadui) {
					case "disable" :
						break;
					case "enable" :
						$("#load_" + ts.p.id).hide();
						break;
					case "block" :
						$("#lui_" + ts.p.id).hide();
						$("#load_" + ts.p.id).hide();
						break;
				}
			}, populate = function(npage) {
				if (!ts.grid.hDiv.loading) {
					var pvis = ts.p.scroll && npage === false;
					var prm = {}, dt, dstr, pN = ts.p.prmNames;
					if (ts.p.page <= 0) {
						ts.p.page = 1;
					}
					if (pN.search !== null) {
						prm[pN.search] = ts.p.search;
					}
					if (pN.nd !== null) {
						prm[pN.nd] = new Date().getTime();
					}
					if (pN.rows !== null) {
						prm[pN.rows] = ts.p.rowNum;
					}
					if (pN.page !== null) {
						prm[pN.page] = ts.p.page;
					}
					if (pN.sort !== null) {
						prm[pN.sort] = ts.p.sortname;
					}
					if (pN.order !== null) {
						prm[pN.order] = ts.p.sortorder;
					}
					if (ts.p.rowTotal !== null && pN.totalrows !== null) {
						prm[pN.totalrows] = ts.p.rowTotal;
					}
					var lc = ts.p.loadComplete;
					var lcf = $.isFunction(lc);
					if (!lcf) {
						lc = null;
					}
					var adjust = 0;
					npage = npage || 1;
					if (npage > 1) {
						if (pN.npage !== null) {
							prm[pN.npage] = npage;
							adjust = npage - 1;
							npage = 1;
						} else {
							lc = function(req) {
								ts.p.page++;
								ts.grid.hDiv.loading = false;
								if (lcf) {
									ts.p.loadComplete.call(ts, req);
								}
								populate(npage - 1);
							};
						}
					} else if (pN.npage !== null) {
						delete ts.p.postData[pN.npage];
					}
					
					if (ts.p.grouping) {
						$(ts).jqGrid('groupingSetup');
						if (ts.p.groupingView.groupDataSorted === true) {
							prm[pN.sort] = ts.p.groupingView.groupField[0]
							        + " " + ts.p.groupingView.groupOrder[0]
							        + ", " + prm[pN.sort];
						}
					}
					$.extend(ts.p.postData, prm);
					var rcnt = !ts.p.scroll ? 1 : ts.rows.length - 1;
					if ($.isFunction(ts.p.datatype)) {
						ts.p.datatype
						        .call(ts, ts.p.postData, "load_" + ts.p.id);
						return;
					} else if ($.isFunction(ts.p.beforeRequest)) {
						ts.p.beforeRequest.call(ts);
					}
					dt = ts.p.datatype.toLowerCase();
					switch (dt) {
						case "json" :
						case "jsonp" :
						case "xml" :
						case "script" :
							$.ajax($.extend({
								url			: ts.p.url,
								type		: ts.p.mtype,
								dataType	: dt,
								data		: $
								        .isFunction(ts.p.serializeGridData)
								        ? ts.p.serializeGridData.call(ts,
								                ts.p.postData
								        )
								        : ts.p.postData,
								success		: function(data, st) {
									if (dt === "xml") {
										addXmlData(data, ts.grid.bDiv, rcnt,
										        npage > 1, adjust
										);
									} else {
										addJSONData(data, ts.grid.bDiv, rcnt,
										        npage > 1, adjust
										);
									}
									
									var $mainGridObj = $(ts);
									$mainGridObj.data("gridListData",null);//将列表数据重新获取
									var _gridListData = {};
									var dataIDs = $mainGridObj.getDataIDs();
									if (dataIDs) {
										$.each(dataIDs,function(id, rowId){
											_gridListData[rowId]=$mainGridObj.getRowData(rowId);
										});
									}
									$mainGridObj.data("gridListData",_gridListData);
									
									if (lc) {
										lc.call(ts, data);
									}
									// add by zhougz 2011-11-1 begin
									if (ts.p.afterLoad) {
										ts.p.afterLoad.call(ts, data);
									}
									// add by zhougz 2011-11-1 end
									if (pvis) {
										ts.grid.populateVisible();
									}
									if (ts.p.loadonce || ts.p.treeGrid) {
										ts.p.datatype = "local";
									}
									data = null;
									endReq();
								},
								error		: function(xhr, st, err) {
									if ($.isFunction(ts.p.loadError)) {
										ts.p.loadError.call(ts, xhr, st, err);
									}
									endReq();
									xhr = null;
								},
								beforeSend	: function(xhr) {
									beginReq();
									if ($.isFunction(ts.p.loadBeforeSend)) {
										ts.p.loadBeforeSend.call(ts, xhr);
									}
								}
							}, $.jgrid.ajaxOptions, ts.p.ajaxGridOptions));
							break;
						case "xmlstring" :
							beginReq();
							dstr = $.jgrid.stringToDoc(ts.p.datastr);
							addXmlData(dstr, ts.grid.bDiv);
							if (lcf) {
								ts.p.loadComplete.call(ts, dstr);
							}
							ts.p.datatype = "local";
							ts.p.datastr = null;
							endReq();
							break;
						case "jsonstring" :
							beginReq();
							if (typeof ts.p.datastr == 'string') {
								dstr = $.jgrid.parse(ts.p.datastr);
							} else {
								dstr = ts.p.datastr;
							}
							addJSONData(dstr, ts.grid.bDiv);
							if (lcf) {
								ts.p.loadComplete.call(ts, dstr);
							}
							ts.p.datatype = "local";
							ts.p.datastr = null;
							endReq();
							break;
						case "local" :
						case "clientside" :
							beginReq();
							ts.p.datatype = "local";
							var req = addLocalData();
							addJSONData(req, ts.grid.bDiv, rcnt, npage > 1,
							        adjust
							);
							if (lc) {
								lc.call(ts, req);
							}
							if (pvis) {
								ts.grid.populateVisible();
							}
							endReq();
							break;
					}
				}
			}, setPager = function(pgid, tp) {
				var sep = "<td class='ui-pg-button ui-state-disabled' style='width:4px;'><span class='ui-separator'></span></td>", pginp = "", pgl = "<table cellspacing='0' cellpadding='0' border='0' style='table-layout:auto;' class='ui-pg-table'><tbody><tr>", str = "", pgcnt, lft, cent, rgt, twd, tdw, i, clearVals = function(
				        onpaging) {
					var ret;
					if ($.isFunction(ts.p.onPaging)) {
						ret = ts.p.onPaging.call(ts, onpaging);
					}
					ts.p.selrow = null;
					if (ts.p.multiselect) {
						ts.p.selarrrow = [];
						$('#cb_' + $.jgrid.jqID(ts.p.id), ts.grid.hDiv).attr(
						        "checked", false
						);
					}
					ts.p.savedRow = [];
					if (ret == 'stop') {
						return false;
					}
					return true;
				};
				pgid = pgid.substr(1);
				pgcnt = "pg_" + pgid;
				lft = pgid + "_left";
				cent = pgid + "_center";
				rgt = pgid + "_right";
				$("#" + pgid)
				        .append("<div id='"
				                + pgcnt
				                + "' class='ui-pager-control' role='group'><table cellspacing='0' cellpadding='0' border='0' class='ui-pg-table' style='width:100%;table-layout:fixed;height:100%;' role='row'><tbody><tr><td id='"
				                + lft
				                + "' align='left'></td><td id='"
				                + cent
				                + "' align='center' style='white-space:pre;'></td><td id='"
				                + rgt
				                + "' align='right'></td></tr></tbody></table></div>")
				        .attr("dir", "ltr"); // explicit setting
				if (ts.p.rowList.length > 0) {
					str = "<td dir='" + dir + "'>";
					str += "<select class='ui-pg-selbox' role='listbox'>";
					for (i = 0; i < ts.p.rowList.length; i++) {
						str += "<option role=\"option\" value=\""
						        + ts.p.rowList[i]
						        + "\""
						        + ((ts.p.rowNum == ts.p.rowList[i])
						                ? " selected=\"selected\""
						                : "") + ">" + ts.p.rowList[i]
						        + "</option>";
					}
					str += "</select></td>";
				}
				if (dir == "rtl") {
					pgl += str;
				}
				if (ts.p.pginput === true) {
					pginp = "<td dir='"
					        + dir
					        + "'>"
					        + $.jgrid
					                .format(
					                        ts.p.pgtext || "",
					                        "<input class='ui-pg-input' type='text' size='2' maxlength='7' value='0' role='textbox'/>",
					                        "<span id='sp_1'></span>"
					                ) + "</td>";
				}
				if (ts.p.pgbuttons === true) {
					var po = [
					        "first" + tp, "prev" + tp, "next" + tp, "last" + tp
					];
					if (dir == "rtl") {
						po.reverse();
					}
					pgl += "<td id='"
					        + po[0]
					        + "' class='ui-pg-button ui-corner-all'><span class='ui-icon ui-icon-seek-first'></span></td>";
					pgl += "<td id='"
					        + po[1]
					        + "' class='ui-pg-button ui-corner-all'><span class='ui-icon ui-icon-seek-prev'></span></td>";
					pgl += pginp !== "" ? sep + pginp + sep : "";
					pgl += "<td id='"
					        + po[2]
					        + "' class='ui-pg-button ui-corner-all'><span class='ui-icon ui-icon-seek-next'></span></td>";
					pgl += "<td id='"
					        + po[3]
					        + "' class='ui-pg-button ui-corner-all'><span class='ui-icon ui-icon-seek-end'></span></td>";
				} else if (pginp !== "") {
					pgl += pginp;
				}
				if (dir == "ltr") {
					pgl += str;
				}
				pgl += "</tr></tbody></table>";
				if (ts.p.viewrecords === true) {
					$("td#" + pgid + "_" + ts.p.recordpos, "#" + pgcnt)
					        .append("<div dir='" + dir + "' style='text-align:"
					                + ts.p.recordpos
					                + "' class='ui-paging-info'></div>");
				}
				$("td#" + pgid + "_" + ts.p.pagerpos, "#" + pgcnt).append(pgl);
				tdw = $(".ui-jqgrid").css("font-size") || "11px";
				$(document.body)
				        .append("<div id='testpg' class='ui-jqgrid ui-widget ui-widget-content' style='font-size:"
				                + tdw + ";visibility:hidden;' ></div>");
				twd = $(pgl).clone().appendTo("#testpg").width();
				$("#testpg").remove();
				if (twd > 0) {
					if (pginp != "") {
						twd += 50;
					} // should be param
					$("td#" + pgid + "_" + ts.p.pagerpos, "#" + pgcnt)
					        .width(twd);
				}
				ts.p._nvtd = [];
				ts.p._nvtd[0] = twd ? Math.floor((ts.p.width - twd) / 2) : Math
				        .floor(ts.p.width / 3);
				ts.p._nvtd[1] = 0;
				pgl = null;
				$('.ui-pg-selbox', "#" + pgcnt).bind('change', function() {
					ts.p.page = Math.round(ts.p.rowNum * (ts.p.page - 1)
					        / this.value - 0.5)
					        + 1;
					ts.p.rowNum = this.value;
					if (tp) {
						$('.ui-pg-selbox', ts.p.pager).val(this.value);
					} else if (ts.p.toppager) {
						$('.ui-pg-selbox', ts.p.toppager).val(this.value);
					}
					if (!clearVals('records')) {
						return false;
					}
					populate();
					return false;
				}
				);
				if (ts.p.pgbuttons === true) {
					$(".ui-pg-button", "#" + pgcnt).hover(function(e) {
						if ($(this).hasClass('ui-state-disabled')) {
							this.style.cursor = 'default';
						} else {
							$(this).addClass('ui-state-hover');
							this.style.cursor = 'pointer';
						}
					}, function(e) {
						if ($(this).hasClass('ui-state-disabled')) {
						} else {
							$(this).removeClass('ui-state-hover');
							this.style.cursor = "default";
						}
					});
					$(
					        "#first" + tp + ", #prev" + tp + ", #next" + tp
					                + ", #last" + tp, "#" + pgid
					).click(function(e) {
						var cp = intNum(ts.p.page, 1), last = intNum(
						        ts.p.lastpage, 1
						), selclick = false, fp = true, pp = true, np = true, lp = true;
						if (last === 0 || last === 1) {
							fp = false;
							pp = false;
							np = false;
							lp = false;
						} else if (last > 1 && cp >= 1) {
							if (cp === 1) {
								fp = false;
								pp = false;
							} else if (cp > 1 && cp < last) {
							} else if (cp === last) {
								np = false;
								lp = false;
							}
						} else if (last > 1 && cp === 0) {
							np = false;
							lp = false;
							cp = last - 1;
						}
						if (this.id === 'first' + tp && fp) {
							ts.p.page = 1;
							selclick = true;
						}
						if (this.id === 'prev' + tp && pp) {
							ts.p.page = (cp - 1);
							selclick = true;
						}
						if (this.id === 'next' + tp && np) {
							ts.p.page = (cp + 1);
							selclick = true;
						}
						if (this.id === 'last' + tp && lp) {
							ts.p.page = last;
							selclick = true;
						}
						if (selclick) {
							if (!clearVals(this.id)) {
								return false;
							}
							populate();
						}
						return false;
					}
					);
				}
				if (ts.p.pginput === true) {
					$('input.ui-pg-input', "#" + pgcnt).keypress(function(e) {
						var key = e.charCode ? e.charCode : e.keyCode
						        ? e.keyCode
						        : 0;
						if (key == 13) {
							var _inputpage = $(this).val();
							if (_inputpage > 0 && _inputpage <= ts.p.lastpage&&_inputpage.indexOf(".")==-1) {
								ts.p.page = ($(this).val() > 0)
								        ? $(this).val()
								        : ts.p.page;
								if (!clearVals('user')) {
									return false;
								}
								populate();
							} else { // add by qiujie 2012-08-29
										// 修改当输入不存在页数时进行提示
								if (ts.p.lastpage == 1) {
									alert("跳转页面只能是1的整数！");
								} else {
									alert("跳转页面只能是1至" + ts.p.lastpage + "的整数！");
								}
							}
							return false;
						}
						return this;
					}
					);
				}
			}, sortData = function(index, idxcol, reload, sor) {
				if (!ts.p.colModel[idxcol].sortable) {
					return;
				}
				var so;
				if (ts.p.savedRow.length > 0) {
					return;
				}
				if (!reload) {
					if (ts.p.lastsort == idxcol) {
						if (ts.p.sortorder == 'asc') {
							ts.p.sortorder = 'desc';
						} else if (ts.p.sortorder == 'desc') {
							ts.p.sortorder = 'asc';
						}
					} else {
						ts.p.sortorder = ts.p.colModel[idxcol].firstsortorder
						        || 'asc';
					}
					ts.p.page = 1;
				}
				if (sor) {
					if (ts.p.lastsort == idxcol && ts.p.sortorder == sor
					        && !reload) {
						return;
					} else {
						ts.p.sortorder = sor;
					}
				}
				var thd = $("thead:first", ts.grid.hDiv).get(0);
				$("tr th:eq(" + ts.p.lastsort + ") span.ui-grid-ico-sort", thd)
				        .addClass('ui-state-disabled');
				$("tr th:eq(" + ts.p.lastsort + ")", thd).attr("aria-selected",
				        "false"
				);
				$("tr th:eq(" + idxcol + ") span.ui-icon-" + ts.p.sortorder,
				        thd
				).removeClass('ui-state-disabled');
				$("tr th:eq(" + idxcol + ")", thd)
				        .attr("aria-selected", "true");
				if (!ts.p.viewsortcols[0]) {
					if (ts.p.lastsort != idxcol) {
						$("tr th:eq(" + ts.p.lastsort + ") span.s-ico", thd)
						        .hide();
						$("tr th:eq(" + idxcol + ") span.s-ico", thd).show();
					}
				}
				index = index.substring(5);
				ts.p.sortname = ts.p.colModel[idxcol].index || index;
				so = ts.p.sortorder;
				if ($.isFunction(ts.p.onSortCol)) {
					if (ts.p.onSortCol.call(ts, index, idxcol, so) == 'stop') {
						ts.p.lastsort = idxcol;
						return;
					}
				}
				if (ts.p.datatype == "local") {
					if (ts.p.deselectAfterSort) {
						$(ts).jqGrid("resetSelection");
					}
				} else {
					ts.p.selrow = null;
					if (ts.p.multiselect) {
						$("#cb_" + $.jgrid.jqID(ts.p.id), ts.grid.hDiv).attr(
						        "checked", false
						);
					}
					ts.p.selarrrow = [];
					ts.p.savedRow = [];
				}
				if (ts.p.scroll) {
					var sscroll = ts.grid.bDiv.scrollLeft;
					emptyRows(ts.grid.bDiv, true);
					ts.grid.hDiv.scrollLeft = sscroll;
				}
				if (ts.p.subGrid && ts.p.datatype == 'local') {
					$("td.sgexpanded", "#" + ts.p.id).each(function() {
						$(this).trigger("click");
					});
				}
				populate();
				ts.p.lastsort = idxcol;
				if (ts.p.sortname != index && idxcol) {
					ts.p.lastsort = idxcol;
				}
			}, setColWidth = function() {
				var initwidth = 0, brd = ts.p.cellLayout, vc = 0, lvc, scw = ts.p.scrollOffset, cw, hs = false, aw, tw = 0, gw = 0, cl = 0, cr;
				if (isSafari) {
					brd = 0;
				}
				$.each(ts.p.colModel, function(i) {
					if (typeof this.hidden === 'undefined') {
						this.hidden = false;
					}
					if (this.hidden === false) {
						initwidth += intNum(this.width, 0);
						if (this.fixed) {
							tw += this.width;
							gw += this.width + brd;
						} else {
							vc++;
						}
						cl++;
					}
				});
				if (isNaN(ts.p.width)) {
					ts.p.width = grid.width = initwidth;
				} else {
					grid.width = ts.p.width;
				}
				ts.p.tblwidth = initwidth;
				if (ts.p.shrinkToFit === false && ts.p.forceFit === true) {
					ts.p.forceFit = false;
				}
				if (ts.p.shrinkToFit === true && vc > 0) {
					aw = grid.width - brd * vc - gw;
					if (isNaN(ts.p.height)) {
					} else {
						aw -= scw;
						hs = true;
					}
					initwidth = 0;
					$.each(ts.p.colModel, function(i) {
						if (this.hidden === false && !this.fixed) {
							cw = Math.round(aw * this.width
							        / (ts.p.tblwidth - tw));
							this.width = cw;
							initwidth += cw;
							lvc = i;
						}
					});
					cr = 0;
					if (hs) {
						if (grid.width - gw - (initwidth + brd * vc) !== scw) {
							cr = grid.width - gw - (initwidth + brd * vc) - scw;
						}
					} else if (!hs
					        && Math.abs(grid.width - gw
					                - (initwidth + brd * vc)) !== 1) {
						cr = grid.width - gw - (initwidth + brd * vc);
					}
					ts.p.colModel[lvc].width += cr;
					ts.p.tblwidth = initwidth + cr + tw + cl * brd;
					if (ts.p.tblwidth > ts.p.width) {
						ts.p.colModel[lvc].width -= (ts.p.tblwidth - parseInt(
						        ts.p.width, 10
						));
						ts.p.tblwidth = ts.p.width;
					}
				}
			}, nextVisible = function(iCol) {
				var ret = iCol, j = iCol, i;
				for (i = iCol + 1; i < ts.p.colModel.length; i++) {
					if (ts.p.colModel[i].hidden !== true) {
						j = i;
						break;
					}
				}
				return j - ret;
			}, getOffset = function(iCol) {
				var i, ret = {}, brd1 = isSafari ? 0 : ts.p.cellLayout;
				ret[0] = ret[1] = ret[2] = 0;
				for (i = 0; i <= iCol; i++) {
					if (ts.p.colModel[i].hidden === false) {
						ret[0] += ts.p.colModel[i].width + brd1;
					}
				}
				if (ts.p.direction == "rtl") {
					ret[0] = ts.p.width - ret[0];
				}
				ret[0] = ret[0] - ts.grid.bDiv.scrollLeft;
				if ($(ts.grid.cDiv).is(":visible")) {
					ret[1] += $(ts.grid.cDiv).height()
					        + parseInt($(ts.grid.cDiv).css("padding-top"), 10)
					        + parseInt($(ts.grid.cDiv).css("padding-bottom"),
					                10
					        );
				}
				if (ts.p.toolbar[0] === true
				        && (ts.p.toolbar[1] == 'top' || ts.p.toolbar[1] == 'both')) {
					ret[1] += $(ts.grid.uDiv).height()
					        + parseInt($(ts.grid.uDiv).css("border-top-width"),
					                10
					        )
					        + parseInt($(ts.grid.uDiv)
					                        .css("border-bottom-width"), 10);
				}
				if (ts.p.toppager) {
					ret[1] += $(ts.grid.topDiv).height()
					        + parseInt($(ts.grid.topDiv)
					                        .css("border-bottom-width"), 10);
				}
				ret[2] += $(ts.grid.bDiv).height() + $(ts.grid.hDiv).height();
				return ret;
			};
			this.p.id = this.id;
			if ($.inArray(ts.p.multikey, sortkeys) == -1) {
				ts.p.multikey = false;
			}
			ts.p.keyIndex = false;
			for (i = 0; i < ts.p.colModel.length; i++) {
				clm = ts.p.colModel[i];
				clm = $.extend(clm, ts.p.cmTemplate, clm.template || {});
				if (ts.p.keyIndex === false && ts.p.colModel[i].key === true) {
					ts.p.keyIndex = i;
				}
			}
			ts.p.sortorder = ts.p.sortorder.toLowerCase();
			if (ts.p.grouping === true) {
				ts.p.scroll = false;
				ts.p.rownumbers = false;
				ts.p.subGrid = false;
				ts.p.treeGrid = false;
				ts.p.gridview = true;
			}
			if (this.p.treeGrid === true) {
				try {
					$(this).jqGrid("setTreeGrid");
				} catch (_) {
				}
				if (ts.p.datatype != "local") {
					ts.p.localReader = {
						id	: "_id_"
					};
				}
			}
			if (this.p.subGrid) {
				try {
					$(ts).jqGrid("setSubGrid");
				} catch (_) {
				}
			}
			if (this.p.multiselect) {
				if (this.p.selectOnlyOne === true) {
					this.p.colNames.unshift("&nbsp;");
				} else {
					this.p.colNames.unshift("<input role='checkbox' id='cb_"
					        + this.p.id + "' class='cbox' type='checkbox'/>");
				}
				this.p.colModel.unshift({
					name		: 'cb',
					width		: isSafari ? ts.p.multiselectWidth
					        + ts.p.cellLayout : ts.p.multiselectWidth,
					sortable	: false,
					resizable	: false,
					hidedlg		: true,
					search		: false,
					align		: 'center',
					fixed		: true
				});
			}
			if (this.p.rownumbers) {
				
				this.p.colNames.unshift("");
				this.p.colModel.unshift({
					name		: 'rn',
					width		: ts.p.rownumWidth,
					sortable	: false,
					resizable	: false,
					hidedlg		: true,
					search		: false,
					align		: 'center',
					fixed		: true
				});
			}
			ts.p.xmlReader = $.extend(true, {
				root		: "rows",
				row			: "row",
				page		: "rows>page",
				total		: "rows>total",
				records		: "rows>records",
				repeatitems	: true,
				cell		: "cell",
				id			: "[id]",
				userdata	: "userdata",
				subgrid		: {
					root		: "rows",
					row			: "row",
					repeatitems	: true,
					cell		: "cell"
				}
			}, ts.p.xmlReader);
			ts.p.jsonReader = $.extend(true, {
				root		: "rows",
				page		: "page",
				total		: "total",
				records		: "records",
				repeatitems	: true,
				cell		: "cell",
				id			: "id",
				userdata	: "userdata",
				subgrid		: {
					root		: "rows",
					repeatitems	: true,
					cell		: "cell"
				}
			}, ts.p.jsonReader);
			ts.p.localReader = $.extend(true, {
				root		: "rows",
				page		: "page",
				total		: "total",
				records		: "records",
				repeatitems	: false,
				cell		: "cell",
				id			: "id",
				userdata	: "userdata",
				subgrid		: {
					root		: "rows",
					repeatitems	: true,
					cell		: "cell"
				}
			}, ts.p.localReader);
			if (ts.p.scroll) {
				ts.p.pgbuttons = false;
				ts.p.pginput = false;
				ts.p.rowList = [];
			}
			if (ts.p.data.length) {
				refreshIndex();
			}
			var thead = "<thead><tr class='ui-jqgrid-labels' role='rowheader'>", tdc, idn, w, res, sort, td, ptr, tbody, imgs, iac = "", idc = "";
			if (ts.p.shrinkToFit === true && ts.p.forceFit === true) {
				for (i = ts.p.colModel.length - 1; i >= 0; i--) {
					if (!ts.p.colModel[i].hidden) {
						ts.p.colModel[i].resizable = false;
						break;
					}
				}
			}
			if (ts.p.viewsortcols[1] == 'horizontal') {
				iac = " ui-i-asc";
				idc = " ui-i-desc";
			}
			tdc = isMSIE ? "class='ui-th-div-ie'" : "";
			imgs = "<span class='s-ico' style='display:none'><span sort='asc' class='ui-grid-ico-sort ui-icon-asc"
			        + iac
			        + " ui-state-disabled ui-icon ui-icon-triangle-1-n ui-sort-"
			        + dir + "'></span>";
			imgs += "<span sort='desc' class='ui-grid-ico-sort ui-icon-desc"
			        + idc
			        + " ui-state-disabled ui-icon ui-icon-triangle-1-s ui-sort-"
			        + dir + "'></span></span>";
			for (i = 0; i < this.p.colNames.length; i++) {
				var tooltip = ts.p.headertitles ? (" title=\""
				        + $.jgrid.stripHtml(ts.p.colNames[i]) + "\"") : "";
				thead += "<th id='"
				        + ts.p.id
				        + "_"
				        + ts.p.colModel[i].name
				        + "' role='columnheader' class='ui-state-default ui-th-column ui-th-"
				        + dir + "'" + tooltip + ">";
				idn = ts.p.colModel[i].index || ts.p.colModel[i].name;
				thead += "<div id='jqgh_" + ts.p.colModel[i].name + "' " + tdc
				        + ">" + ts.p.colNames[i];
				if (!ts.p.colModel[i].width) {
					ts.p.colModel[i].width = 150;
				} else {
					ts.p.colModel[i].width = parseInt(ts.p.colModel[i].width,
					        10
					);
				}
				if (typeof(ts.p.colModel[i].title) !== "boolean") {
					ts.p.colModel[i].title = true;
				}
				if (idn == ts.p.sortname) {
					ts.p.lastsort = i;
				}
				thead += imgs + "</div></th>";
			}
			thead += "</tr></thead>";
			imgs = null;
			$(this).append(thead);
			$("thead tr:first th", this).hover(function() {
				$(this).addClass('ui-state-hover');
			}, function() {
				$(this).removeClass('ui-state-hover');
			});
			if (this.p.multiselect) {
				var emp = [], chk;
				$('#cb_' + $.jgrid.jqID(ts.p.id), this).bind('click',
				function() {
					if (this.checked) {
						// add by zhougz 2011201 当checkbox disable时，全选也会被勾上
						// begin
						$("[id^=jqg_" + ts.p.id + "_" + "]").each(function(){
							var thisObj = $(this);
							if(!thisObj.attr("disabled")){
								thisObj.attr("checked", "checked");
							}
						});
						// end
						// $("[id^=jqg_"+ts.p.id+"_"+"]").attr("checked","checked");
						ts.p.selarrrow = []; // add by qiujie 20120728
												// 当全选时先清空该对象
						$(ts.rows).each(function(i) {
							if (i > 0) {
								// modify by zhougz 20120607 撤掉disabled多选框选中行样式
								if (!$(this).hasClass("subgrid")
								        && !$(this).hasClass("jqgroup")
								        && $(':checkbox:disabled', this).length == 0) {
									// end if(!$(this).hasClass("subgrid") &&
									// !$(this).hasClass("jqgroup")){
									$(this).addClass("ui-state-highlight")
									        .attr("aria-selected", "true");
									
									// update by xiajb
									// 20120727,修改掉当主子表展开子表后全选会把子表格的空ID算上的问题
									if (this.id && this.id != "") {
										
										ts.p.selarrrow.push(this.id);
										
									}
									ts.p.selrow = this.id;
								}
							}
						}
						);
						chk = true;
						emp = [];
					} else {
						$("[id^=jqg_" + ts.p.id + "_" + "]")
						        .removeAttr("checked");
						$(ts.rows).each(function(i) {
							if (i > 0) {
								if (!$(this).hasClass("subgrid")) {
									$(this).removeClass("ui-state-highlight")
									        .attr("aria-selected", "false");
									emp.push(this.id);
								}
							}
						}
						);
						ts.p.selarrrow = [];
						ts.p.selrow = null;
						chk = false;
					}
					if ($.isFunction(ts.p.onSelectAll)) {
						ts.p.onSelectAll.call(ts, chk ? ts.p.selarrrow : emp,
						        chk
						);
					}
				}
				);
			}
			
			if (ts.p.autowidth === true) {
				var pw = $(eg).innerWidth();
				ts.p.width = pw > 0 ? pw : 'nw';
			}
			setColWidth();
			$(eg).css("width", grid.width + "px")
			        .append("<div class='ui-jqgrid-resize-mark' id='rs_m"
			                + ts.p.id + "'>&#160;</div>");
			$(gv).css("width", grid.width + "px");
			thead = $("thead:first", ts).get(0);
			var tfoot = "";
			if (ts.p.footerrow) {
				tfoot += "<table role='grid' style='width:"
				        + ts.p.tblwidth
				        + "px;' class='ui-jqgrid-ftable' cellspacing='0' cellpadding='0' border='0'><tbody><tr role='row' class='ui-widget-content footrow footrow-"
				        + dir + "' style='border-left:0px;'>";
			}
			var thr = $("tr:first", thead), firstr = "<tr class='jqgfirstrow' role='row' style='height:auto'>";
			ts.p.disableClick = false;
			$("th", thr).each(function(j) {
				w = ts.p.colModel[j].width;
				if (typeof ts.p.colModel[j].resizable === 'undefined') {
					ts.p.colModel[j].resizable = false;
				}
				if (ts.p.colModel[j].resizable) {
					res = document.createElement("span");
					$(res).html("&#160;")
					        .addClass('ui-jqgrid-resize ui-jqgrid-resize-'
					                + dir);
					if (!$.browser.opera) {
						$(res).css("cursor", "col-resize");
					}
					$(this).addClass(ts.p.resizeclass);
				} else {
					res = "";
				}
				$(this).css("width", w + "px").prepend(res);
				var hdcol = "";
				if (ts.p.colModel[j].hidden) {
					$(this).css("display", "none");
					hdcol = "display:none;";
				}
				firstr += "<td role='gridcell' style='height:0px;width:" + w
				        + "px;" + hdcol + "'></td>";
				grid.headers[j] = {
					width	: w,
					el		: this
				};
				sort = ts.p.colModel[j].sortable;
				if (typeof sort !== 'boolean') {
					ts.p.colModel[j].sortable = false;
					sort = false;
				}
				var nm = ts.p.colModel[j].name;
				if (!(nm == 'cb' || nm == 'subgrid' || nm == 'rn')) {
					if (ts.p.viewsortcols[2] && sort) {
						$("div", this).addClass('ui-jqgrid-sortable');
					}
				}
				if (sort) {
					if (ts.p.viewsortcols[0]) {
						$("div span.s-ico", this).show();
						if (j == ts.p.lastsort) {
							$("div span.ui-icon-" + ts.p.sortorder, this)
							        .removeClass("ui-state-disabled");
						}
					} else if (j == ts.p.lastsort) {
						$("div span.s-ico", this).show();
						$("div span.ui-icon-" + ts.p.sortorder, this)
						        .removeClass("ui-state-disabled");
					}
				}
				if (ts.p.footerrow) {
					tfoot += "<td role='gridcell' " + formatCol(j, 0, '')
					        + ">&#160;</td>";
				}
			}
			).mousedown(function(e) {
				if ($(e.target).closest("th>span.ui-jqgrid-resize").length != 1) {
					return;
				}
				var ci = $.jgrid.getCellIndex(this);
				if (ts.p.forceFit === true) {
					ts.p.nv = nextVisible(ci);
				}
				grid.dragStart(ci, e, getOffset(ci));
				return false;
			}
			).click(function(e) {
				if (ts.p.disableClick) {
					ts.p.disableClick = false;
					return false;
				}
				var s = "th>div.ui-jqgrid-sortable", r, d;
				if (!ts.p.viewsortcols[2]) {
					s = "th>div>span>span.ui-grid-ico-sort";
				}
				var t = $(e.target).closest(s);
				if (t.length != 1) {
					return;
				}
				var ci = $.jgrid.getCellIndex(this);
				if (!ts.p.viewsortcols[2]) {
					r = true;
					d = t.attr("sort");
				}
				sortData($('div', this)[0].id, ci, r, d);
				return false;
			});
			if (ts.p.sortable && $.fn.sortable) {
				try {
					$(ts).jqGrid("sortableColumns", thr);
				} catch (e) {
				}
			}
			if (ts.p.footerrow) {
				tfoot += "</tr></tbody></table>";
			}
			firstr += "</tr>";
			tbody = document.createElement("tbody");
			this.appendChild(tbody);
			$(this).addClass('ui-jqgrid-btable').append(firstr);
			firstr = null;
			var $wid = grid.width;
			if (ts.p.pager != "") {
				if (ts.p.rownumbers || ts.p.multiselect) {
					$wid = grid.width + 2;
				}
			}
			
			// add by qiujie 修改table的宽度
			var hTable = $("<table class='ui-jqgrid-htable' style='width:"
			        + $wid + "px' role='grid' aria-labelledby='gbox_" + this.id
			        + "' cellspacing='0' cellpadding='0' border='0'></table>")
			        .append(thead), hg = (ts.p.caption && ts.p.hiddengrid === true)
			        ? true
			        : false, hb = $("<div class='ui-jqgrid-hbox"
			        + (dir == "rtl" ? "-rtl" : "") + "'></div>");
			thead = null;
			grid.hDiv = document.createElement("div");
			$(grid.hDiv).css({
				width	: grid.width + "px"
			}).addClass("ui-state-default ui-jqgrid-hdiv").append(hb);
			$(hb).append(hTable);
			hTable = null;
			if (hg) {
				$(grid.hDiv).hide();
			}
			if (ts.p.pager) {
				if (typeof ts.p.pager == "string") {
					if (ts.p.pager.substr(0, 1) != "#") {
						ts.p.pager = "#" + ts.p.pager;
					}
				} else {
					ts.p.pager = "#" + $(ts.p.pager).attr("id");
				}
				$(ts.p.pager)
				        .css({
					        width	: grid.width + "px"
				        })
				        .appendTo(eg)
				        .addClass('ui-state-default ui-jqgrid-pager ui-corner-bottom');
				if (hg) {
					$(ts.p.pager).hide();
				}
				setPager(ts.p.pager, '');
			}
			if (ts.p.cellEdit === false && ts.p.hoverrows === true) {
				$(ts).bind('mouseover', function(e) {
					ptr = $(e.target).closest("tr.jqgrow");
					if ($(ptr).attr("class") !== "subgrid") {
						$(ptr).addClass("ui-state-hover");
					}
					return false;
				}).bind('mouseout', function(e) {
					ptr = $(e.target).closest("tr.jqgrow");
					$(ptr).removeClass("ui-state-hover");
					return false;
				});
			}
			var ri, ci;
			$(ts).before(grid.hDiv).click(function(e) {
				td = e.target;
				var scb = $(td).hasClass("cbox");
				ptr = $(td, ts.rows).closest("tr.jqgrow");
				if ($(ptr).length === 0) {
					return this;
				}
				var cSel = true;
				if ($.isFunction(ts.p.beforeSelectRow)) {
					cSel = ts.p.beforeSelectRow.call(ts, ptr[0].id, e);
				}
				if (td.tagName == 'A'
				        || ((td.tagName == 'INPUT' || td.tagName == 'TEXTAREA'
				                || td.tagName == 'OPTION' || td.tagName == 'SELECT') && !scb)) {
					return this;
				}
				if (cSel === true) {
					if (ts.p.cellEdit === true) {
						if (ts.p.multiselect && scb) {
							$(ts).jqGrid("setSelection", ptr[0].id, true);
						} else {
							ri = ptr[0].rowIndex;
							ci = $.jgrid.getCellIndex(td);
							try {
								$(ts).jqGrid("editCell", ri, ci, true);
							} catch (_) {
							}
						}
					} else if (!ts.p.multikey) {
						if (ts.p.multiselect && ts.p.multiboxonly) {
							if (scb) {
								$(ts).jqGrid("setSelection", ptr[0].id, true);
							} else {
								$(ts.p.selarrrow).each(function(i, n) {
									var ind = ts.rows.namedItem(n);
									$(ind).removeClass("ui-state-highlight");
									$("#jqg_" + ts.p.id + "_" + $.jgrid.jqID(n))
									        .attr("checked", false);
								}
								);
								ts.p.selarrrow = [];
								$("#cb_" + $.jgrid.jqID(ts.p.id), ts.grid.hDiv)
								        .attr("checked", false);
								$(ts).jqGrid("setSelection", ptr[0].id, true);
							}
						} else if (ts.p.multiselect) {
							$("#cb_" + $.jgrid.jqID(ts.p.id), ts.grid.hDiv)
							        .attr("checked", false);
							$(ts).jqGrid("setSelection", ptr[0].id, true);
						} else {
							$(ts).jqGrid("setSelection", ptr[0].id, true);
						}
					} else {
						if (e[ts.p.multikey]) {
							$(ts).jqGrid("setSelection", ptr[0].id, true);
						} else if (ts.p.multiselect && scb) {
							scb = $("[id^=jqg_" + ts.p.id + "_" + "]")
							        .attr("checked");
							$("[id^=jqg_" + ts.p.id + "_" + "]").attr(
							        "checked", !scb
							);
						}
					}
					if ($.isFunction(ts.p.onCellSelect)) {
						ri = ptr[0].id;
						ci = $.jgrid.getCellIndex(td);
						ts.p.onCellSelect.call(ts, ri, ci, $(td).html(), e);
					}
					e.stopPropagation();
				} else {
					return this;
				}
			}
			).bind('reloadGrid', function(e, opts) {
				if (ts.p.treeGrid === true) {
					ts.p.datatype = ts.p.treedatatype;
				}
				if (opts && opts.current) {
					ts.grid.selectionPreserver(ts);
				}
				if (ts.p.datatype == "local") {
					$(ts).jqGrid("resetSelection");
					if (ts.p.data.length) {
						refreshIndex();
					}
				} else if (!ts.p.treeGrid) {
					ts.p.selrow = null;
					if (ts.p.multiselect) {
						ts.p.selarrrow = [];
						$('#cb_' + $.jgrid.jqID(ts.p.id), ts.grid.hDiv).attr(
						        "checked", false
						);
					}
					ts.p.savedRow = [];
				}
				if (ts.p.scroll) {
					emptyRows(ts.grid.bDiv, true);
				}
				if (opts && opts.page) {
					var page = opts.page;
					if (page > ts.p.lastpage) {
						page = ts.p.lastpage;
					}
					if (page < 1) {
						page = 1;
					}
					ts.p.page = page;
					if (ts.grid.prevRowHeight) {
						ts.grid.bDiv.scrollTop = (page - 1)
						        * ts.grid.prevRowHeight * ts.p.rowNum;
					} else {
						ts.grid.bDiv.scrollTop = 0;
					}
				}
				if (ts.grid.prevRowHeight && ts.p.scroll) {
					delete ts.p.lastpage;
					ts.grid.populateVisible();
				} else {
					ts.grid.populate();
				}
				return false;
			}
			);
			if ($.isFunction(this.p.ondblClickRow)) {
				$(this).dblclick(function(e) {
					td = e.target;
					ptr = $(td, ts.rows).closest("tr.jqgrow");
					if ($(ptr).length === 0) {
						return false;
					}
					ri = ptr[0].rowIndex;
					ci = $.jgrid.getCellIndex(td);
					ts.p.ondblClickRow.call(ts, $(ptr).attr("id"), ri, ci, e);
					return false;
				});
			}
			if ($.isFunction(this.p.onRightClickRow)) {
				$(this).bind('contextmenu', function(e) {
					td = e.target;
					ptr = $(td, ts.rows).closest("tr.jqgrow");
					if ($(ptr).length === 0) {
						return false;
					}
					if (!ts.p.multiselect) {
						$(ts).jqGrid("setSelection", ptr[0].id, true);
					}
					ri = ptr[0].rowIndex;
					ci = $.jgrid.getCellIndex(td);
					ts.p.onRightClickRow.call(ts, $(ptr).attr("id"), ri, ci, e);
					return false;
				}
				);
			}
			grid.bDiv = document.createElement("div");
			// add by qiujie 2012-08-21 修改grid横向滚动条与竖向滚动条同时出现时操作列会被遮住一部分
			var $gridWid = grid.width;
			if (grid.width < ts.p.tblwidth) {
				var $gridWid = grid.width - 20;
			}
			var $wid = grid.width;
			if (ts.p.pager != "") {
				if (ts.p.rownumbers || ts.p.multiselect) {
					$wid = grid.width + 2;
				}
			}
			$(grid.bDiv).append($('<div style="position:relative;'
			        + (isMSIE && $.browser.version < 8 ? "height:0.01%;" : "")
			        + '"></div>').append('<div></div>').append(this))
			        .addClass("ui-jqgrid-bdiv").css({
				        height	: ts.p.height
				                + (isNaN(ts.p.height) ? "" : "px"),
				        width	: (grid.width) + "px"
			        }
			        ).scroll(grid.scrollGrid);
			$("table:first", grid.bDiv).css({
				width	: $wid + "px"
			});// add by qiujie 修改table的宽度
			if (isMSIE) {
				if ($("tbody", this).size() == 2) {
					$("tbody:gt(0)", this).remove();
				}
				if (ts.p.multikey) {
					$(grid.bDiv).bind("selectstart", function() {
						return false;
					});
				}
			} else {
				if (ts.p.multikey) {
					$(grid.bDiv).bind("mousedown", function() {
						return false;
					});
				}
			}
			if (hg) {
				$(grid.bDiv).hide();
			}
			grid.cDiv = document.createElement("div");
			var arf = ts.p.hidegrid === true
			        ? $("<a role='link' href='javascript:void(0)'/>")
			                .addClass('ui-jqgrid-titlebar-close HeaderButton')
			                .hover(function() {
				                arf.addClass('ui-state-hover');
			                }, function() {
				                arf.removeClass('ui-state-hover');
			                })
			                .append("<span class='ui-icon ui-icon-circle-triangle-n'></span>")
			                .css((dir == "rtl" ? "left" : "right"), "0px")
			        : "";
			$(grid.cDiv)
			        .append(arf)
			        .append("<span class='ui-jqgrid-title"
			                + (dir == "rtl" ? "-rtl" : "") + "'>"
			                + ts.p.caption + "</span>")
			        .addClass("ui-jqgrid-titlebar ui-widget-header ui-corner-top ui-helper-clearfix");
			$(grid.cDiv).insertBefore(grid.hDiv);
			if (ts.p.toolbar[0]) {
				grid.uDiv = document.createElement("div");
				if (ts.p.toolbar[1] == "top") {
					$(grid.uDiv).insertBefore(grid.hDiv);
				} else if (ts.p.toolbar[1] == "bottom") {
					$(grid.uDiv).insertAfter(grid.hDiv);
				}
				if (ts.p.toolbar[1] == "both") {
					grid.ubDiv = document.createElement("div");
					$(grid.uDiv).insertBefore(grid.hDiv)
					        .addClass("ui-userdata ui-state-default").attr(
					                "id", "t_" + this.id
					        );
					$(grid.ubDiv).insertAfter(grid.hDiv)
					        .addClass("ui-userdata ui-state-default").attr(
					                "id", "tb_" + this.id
					        );
					if (hg) {
						$(grid.ubDiv).hide();
					}
				} else {
					$(grid.uDiv).width(grid.width)
					        .addClass("ui-userdata ui-state-default").attr(
					                "id", "t_" + this.id
					        );
				}
				if (hg) {
					$(grid.uDiv).hide();
				}
			}
			if (ts.p.toppager) {
				ts.p.toppager = ts.p.id + "_toppager";
				grid.topDiv = $("<div id='" + ts.p.toppager + "'></div>")[0];
				ts.p.toppager = "#" + ts.p.toppager;
				$(grid.topDiv).insertBefore(grid.hDiv)
				        .addClass('ui-state-default ui-jqgrid-toppager')
				        .width(grid.width);
				setPager(ts.p.toppager, '_t');
			}
			if (ts.p.footerrow) {
				grid.sDiv = $("<div class='ui-jqgrid-sdiv'></div>")[0];
				hb = $("<div class='ui-jqgrid-hbox"
				        + (dir == "rtl" ? "-rtl" : "") + "'></div>");
				$(grid.sDiv).append(hb).insertAfter(grid.hDiv)
				        .width(grid.width);
				$(hb).append(tfoot);
				grid.footers = $(".ui-jqgrid-ftable", grid.sDiv)[0].rows[0].cells;
				if (ts.p.rownumbers) {
					grid.footers[0].className = 'ui-state-default jqgrid-rownum';
				}
				if (hg) {
					$(grid.sDiv).hide();
				}
			}
			hb = null;
			if (ts.p.caption) {
				var tdt = ts.p.datatype;
				if (ts.p.hidegrid === true) {
					$(".ui-jqgrid-titlebar-close", grid.cDiv).click(
					function(e) {
						var onHdCl = $.isFunction(ts.p.onHeaderClick), elems = ".ui-jqgrid-bdiv, .ui-jqgrid-hdiv, .ui-jqgrid-pager, .ui-jqgrid-sdiv", counter, self = this;
						if (ts.p.toolbar[0] === true) {
							if (ts.p.toolbar[1] == 'both') {
								elems += ', #' + $(grid.ubDiv).attr('id');
							}
							elems += ', #' + $(grid.uDiv).attr('id');
						}
						counter = $(elems, "#gview_" + ts.p.id).length;
						
						if (ts.p.gridstate == 'visible') {
							$(elems, "#gbox_" + ts.p.id).slideUp("fast",
							function() {
								counter--;
								if (counter == 0) {
									$("span", self)
									        .removeClass("ui-icon-circle-triangle-n")
									        .addClass("ui-icon-circle-triangle-s");
									ts.p.gridstate = 'hidden';
									if ($("#gbox_" + ts.p.id)
									        .hasClass("ui-resizable")) {
										$(".ui-resizable-handle",
										        "#gbox_" + ts.p.id
										).hide();
									}
									if (onHdCl) {
										if (!hg) {
											ts.p.onHeaderClick.call(ts,
											        ts.p.gridstate, e
											);
										}
									}
								}
							}
							);
						} else if (ts.p.gridstate == 'hidden') {
							$(elems, "#gbox_" + ts.p.id).slideDown("fast",
							function() {
								counter--;
								if (counter == 0) {
									$("span", self)
									        .removeClass("ui-icon-circle-triangle-s")
									        .addClass("ui-icon-circle-triangle-n");
									if (hg) {
										ts.p.datatype = tdt;
										populate();
										hg = false;
									}
									ts.p.gridstate = 'visible';
									if ($("#gbox_" + ts.p.id)
									        .hasClass("ui-resizable")) {
										$(".ui-resizable-handle",
										        "#gbox_" + ts.p.id
										).show();
									}
									if (onHdCl) {
										if (!hg) {
											ts.p.onHeaderClick.call(ts,
											        ts.p.gridstate, e
											);
										}
									}
								}
							}
							);
						}
						return false;
					}
					);
					if (hg) {
						ts.p.datatype = "local";
						$(".ui-jqgrid-titlebar-close", grid.cDiv)
						        .trigger("click");
					}
				}
			} else {
				$(grid.cDiv).hide();
			}
			$(grid.hDiv).after(grid.bDiv).mousemove(function(e) {
				if (grid.resizing) {
					grid.dragMove(e);
					return false;
				}
			});
			$(".ui-jqgrid-labels", grid.hDiv).bind("selectstart", function() {
				return false;
			});
			$(document).mouseup(function(e) {
				if (grid.resizing) {
					grid.dragEnd();
					return false;
				}
				return true;
			});
			ts.formatCol = formatCol;
			ts.sortData = sortData;
			ts.updatepager = updatepager;
			ts.refreshIndex = refreshIndex;
			ts.formatter = function(rowId, cellval, colpos, rwdat, act) {
				return formatter(rowId, cellval, colpos, rwdat, act);
			};
			$.extend(grid, {
				populate	: populate,
				emptyRows	: emptyRows
			});
			this.grid = grid;
			ts.addXmlData = function(d) {
				addXmlData(d, ts.grid.bDiv);
			};
			ts.addJSONData = function(d) {
				addJSONData(d, ts.grid.bDiv);
			};
			this.grid.cols = this.rows[0].cells;
			
			populate();
			ts.p.hiddengrid = false;
			$(window).unload(function() {
				ts = null;
			});
		}
		);
	};
	$.jgrid.extend({
		getGridParam	: function(pName) {
			var $t = this[0];
			if (!$t || !$t.grid) {
				return;
			}
			if (!pName) {
				return $t.p;
			} else {
				return typeof($t.p[pName]) != "undefined" ? $t.p[pName] : null;
			}
		},
		setGridParam	: function(newParams) {
			function dateNextDay(d2) {
				// slice返回一个数组
				var str = d2.slice(5) + "-" + d2.slice(0, 4);
				var str2 = d2.replace(/\-/g, "/");
				var d = new Date(str2);
				var d3 = new Date(d.getFullYear(), d.getMonth(), d.getDate()
				                + 1);
				var month = returnMonth(d3.getMonth());
				var day = d3.getDate();
				day = day < 10 ? "0" + day : day;
				var str2 = d3.getFullYear() + "-" + month + "-" + day;
				return str2;
			}
			
			function returnMonth(num) {
				var str = "";
				switch (num) {
					case 0 :
						str = "01";
						break;
					case 1 :
						str = "02";
						break;
					case 2 :
						str = "03";
						break;
					case 3 :
						str = "04";
						break;
					case 4 :
						str = "05";
						break;
					case 5 :
						str = "06";
						break;
					case 6 :
						str = "07";
						break;
					case 7 :
						str = "08";
						break;
					case 8 :
						str = "09";
						break;
					case 9 :
						str = "10";
						break;
					case 10 :
						str = "11";
						break;
					case 11 :
						str = "12";
						break;
				}
				return str;
			}
			
			// for(var k in newParams.postData){
			// if(/endTime|\$LE\$D$/.test(k)){
			// if(newParams.postData[k] != ""){
			// newParams.postData[k] = dateNextDay(newParams.postData[k]);
			// }
			// }
			// }
			
			return this.each(function() {
				if (this.grid && typeof(newParams) === 'object') {
					$.extend(true, this.p, newParams);
				}
			});
		},
		getDataIDs		: function() {
			var ids = [], i = 0, len, j = 0;
			this.each(function() {
				len = this.rows.length;
				if (len && len > 0) {
					while (i < len) {
						if ($(this.rows[i]).hasClass('jqgrow')) {
							ids[j] = this.rows[i].id;
							j++;
						}
						i++;
					}
				}
			});
			return ids;
		},
		setSelection	: function(selection, onsr) {
			return this.each(function() {
				var $t = this, stat, pt, ner, ia, tpsr;
				if (selection === undefined) {
					return;
				}
				onsr = onsr === false ? false : true;
				pt = $t.rows.namedItem(selection + "");
				if (!pt) {
					return;
				}
				function scrGrid(iR) {
					var ch = $($t.grid.bDiv)[0].clientHeight, st = $($t.grid.bDiv)[0].scrollTop, rpos = $t.rows[iR].offsetTop, rh = $t.rows[iR].clientHeight;
					if (rpos + rh >= ch + st) {
						$($t.grid.bDiv)[0].scrollTop = rpos - (ch + st) + rh
						        + st;
					} else if (rpos < ch + st) {
						if (rpos < st) {
							$($t.grid.bDiv)[0].scrollTop = rpos;
						}
					}
				}
				if ($t.p.scrollrows === true) {
					ner = $t.rows.namedItem(selection).rowIndex;
					if (ner >= 0) {
						scrGrid(ner);
					}
				}
				if (!$t.p.multiselect) {
					if (pt.className !== "ui-subgrid") {
						if ($t.p.selrow) {
							$($t.rows.namedItem($t.p.selrow))
							        .removeClass("ui-state-highlight").attr(
							                "aria-selected", "false"
							        );
						}
						if ($t.p.selrow != pt.id) {
							$t.p.selrow = pt.id;
							$(pt).addClass("ui-state-highlight").attr(
							        "aria-selected", "true"
							);
							if ($t.p.onSelectRow && onsr) {
								$t.p.onSelectRow.call($t, $t.p.selrow, true);
							}
						} else {
							$t.p.selrow = null;
						}
					}
				} else {
					$t.p.selrow = pt.id;
					ia = $.inArray($t.p.selrow, $t.p.selarrrow);
					if (ia === -1) {
						if (pt.className !== "ui-subgrid") {
							$(pt).addClass("ui-state-highlight").attr(
							        "aria-selected", "true"
							);
						}
						stat = true;
						// add by zhougz 解决当选择一行时连同 diasabled 的 checkbox一起勾选bug
						$("#jqg_" + $t.p.id + "_" + $.jgrid.jqID($t.p.selrow)
						        + ":not(:disabled)").attr("checked", stat);
						// end
						// $("#jqg_"+$t.p.id+"_"+$.jgrid.jqID($t.p.selrow)).attr("checked",stat);
						        
						$t.p.selarrrow.push($t.p.selrow);
						// add by qiujie 解决当列表上的全部选择框都勾选后，全选框应该被选中 20130226
						 if($t.p.selarrrow.length==$t.p.rowNum){
						 	$("#cb_"+ $t.p.id).attr("checked", true);
						 }
						if ($t.p.onSelectRow && onsr) {
							$t.p.onSelectRow.call($t, $t.p.selrow, stat);
						}
					} else {
						if (pt.className !== "ui-subgrid") {
							$(pt).removeClass("ui-state-highlight").attr(
							        "aria-selected", "false"
							);
						}
						stat = false;
						$("#jqg_" + $t.p.id + "_" + $.jgrid.jqID($t.p.selrow))
						        .attr("checked", stat);
						$t.p.selarrrow.splice(ia, 1);
						if ($t.p.onSelectRow && onsr) {
							$t.p.onSelectRow.call($t, $t.p.selrow, stat);
						}
						tpsr = $t.p.selarrrow[0];
						$t.p.selrow = (tpsr === undefined) ? null : tpsr;
					}
				}
			}
			);
		},
		resetSelection	: function() {
			return this.each(function() {
				var t = this, ind;
				if (!t.p.multiselect) {
					if (t.p.selrow) {
						$("#" + t.p.id + " tbody:first tr#"
						        + $.jgrid.jqID(t.p.selrow))
						        .removeClass("ui-state-highlight").attr(
						                "aria-selected", "false"
						        );
						t.p.selrow = null;
					}
				} else {
					$(t.p.selarrrow).each(function(i, n) {
						ind = t.rows.namedItem(n);
						$(ind).removeClass("ui-state-highlight").attr(
						        "aria-selected", "false"
						);
						$("#jqg_" + t.p.id + "_" + $.jgrid.jqID(n)).attr(
						        "checked", false
						);
					}
					);
					$("#cb_" + $.jgrid.jqID(t.p.id)).attr("checked", false);
					t.p.selarrrow = [];
				}
				t.p.savedRow = [];
			});
		},
		getRowData		: function(rowid) {
			var _gridListData = this.data("gridListData");
		
			if(this.length>0&&this[0] && this[0].p && this[0].p.rowDataCache&&_gridListData&&_gridListData[rowid]){
				return _gridListData[rowid];
			}
			
			var res = {}, resall, getall = false, len, j = 0;
			this.each(function() {
				var $t = this, nm, ind;
				if (typeof(rowid) == 'undefined') {
					getall = true;
					resall = [];
					len = $t.rows.length;
				} else {
					ind = $t.rows.namedItem(rowid);
					if (!ind) {
						return res;
					}
					len = 2;
				}
				while (j < len) {
					if (getall) {
						ind = $t.rows[j];
					}
					if ($(ind).hasClass('jqgrow')) {
						$('td', ind).each(function(i) {
							nm = $t.p.colModel[i].name;
							if (nm !== 'cb' && nm !== 'subgrid' && nm !== 'rn') {
								if ($t.p.treeGrid === true
								        && nm == $t.p.ExpandColumn) {
									res[nm] = $.jgrid.htmlDecode($(
									        "span:first", this
									).html());
								} else {
									try {
										res[nm] = $.unformat(this, {
											rowId		: ind.id,
											colModel	: $t.p.colModel[i]
										}, i);
									} catch (e) {
										res[nm] = $.jgrid.htmlDecode($(this)
										        .html());
									}
								}
							}
						}
						);
						if (getall) {
							resall.push(res);
							res = {};
						}
					}
					j++;
				}
			}
			);
			return resall ? resall : res;
		},
		delRowData		: function(rowid) {
			var success = false, rowInd, ia, ri;
			this.each(function() {
				var $t = this;
				rowInd = $t.rows.namedItem(rowid);
				if (!rowInd) {
					return false;
				} else {
					ri = rowInd.rowIndex;
					$(rowInd).remove();
					$t.p.records--;
					$t.p.reccount--;
					$t.updatepager(true, false);
					success = true;
					if ($t.p.multiselect) {
						ia = $.inArray(rowid, $t.p.selarrrow);
						if (ia != -1) {
							$t.p.selarrrow.splice(ia, 1);
						}
					}
					if (rowid == $t.p.selrow) {
						$t.p.selrow = null;
					}
				}
				if ($t.p.datatype == 'local') {
					var pos = $t.p._index[rowid];
					if (typeof(pos) != 'undefined') {
						$t.p.data.splice(pos, 1);
						$t.refreshIndex();
					}
				}
				if ($t.p.altRows === true && success) {
					var cn = $t.p.altclass;
					$($t.rows).each(function(i) {
						if (i % 2 == 1) {
							$(this).addClass(cn);
						} else {
							$(this).removeClass(cn);
						}
					});
				}
			});
			/** 删除数据行时表格自动自适应宽高 add by zhaomd */
			if (success) {
				$(window).trigger('resize');
			}
			/** add end */
			return success;
		},
		setRowData		: function(rowid, data, cssp) {
			var nm, success = true, title;
			this.each(function() {
				if (!this.grid) {
					return false;
				}
				var t = this, vl, ind, cp = typeof cssp, lcdata = {};
				ind = t.rows.namedItem(rowid);
				if (!ind) {
					return false;
				}
				if (data) {
					try {
						$(this.p.colModel).each(function(i) {
							nm = this.name;
							if (data[nm] !== undefined) {
								lcdata[nm] = this.formatter
								        && typeof(this.formatter) === 'string'
								        && this.formatter == 'date'
								        ? $.unformat.date(data[nm], this)
								        : data[nm];
								vl = t.formatter(rowid, data[nm], i, data,
								        'edit'
								);
								title = this.title ? {
									"title"	: $.jgrid.stripHtml(vl)
								} : {};
								if (t.p.treeGrid === true
								        && nm == t.p.ExpandColumn) {
									$("td:eq(" + i + ") > span:first", ind)
									        .html(vl).attr(title);
								} else {
									$("td:eq(" + i + ")", ind).html(vl)
									        .attr(title);
								}
							}
						}
						);
						if (t.p.datatype == 'local') {
							var pos = t.p._index[rowid];
							if (typeof(pos) != 'undefined') {
								t.p.data[pos] = $.extend(true, t.p.data[pos],
								        lcdata
								);
							}
							lcdata = null;
						}
					} catch (e) {
						success = false;
					}
				}
				if (success) {
					if (cp === 'string') {
						$(ind).addClass(cssp);
					} else if (cp === 'object') {
						$(ind).css(cssp);
					}
				}
			});
			return success;
		},
		addRowData		: function(rowid, rdata, pos, src) {
			if (!pos) {
				pos = "last";
			}
			var success = false, nm, row, gi, si, ni, sind, i, v, prp = "", aradd, cnm, cn, data, cm;
			if (rdata) {
				if ($.isArray(rdata)) {
					aradd = true;
					pos = "last";
					cnm = rowid;
				} else {
					rdata = [
						rdata
					];
					aradd = false;
				}
				this.each(function() {
					$(".ui-jqgrid tr.jqgfirstrow td").css("border-right-width",
					        "1"
					);
					var t = this, datalen = rdata.length;
					ni = t.p.rownumbers === true ? 1 : 0;
					gi = t.p.multiselect === true ? 1 : 0;
					si = t.p.subGrid === true ? 1 : 0;
					if (!aradd) {
						if (typeof(rowid) != 'undefined') {
							rowid = rowid + "";
						} else {
							rowid = (t.p.records + 1) + "";
							if (t.p.keyIndex !== false) {
								cnm = t.p.colModel[t.p.keyIndex + gi + si + ni].name;
								if (typeof rdata[0][cnm] != "undefined") {
									rowid = rdata[0][cnm];
								}
							}
						}
					}
					cn = t.p.altclass;
					var k = 0, cna = "", lcdata = {}, air = $
					        .isFunction(t.p.afterInsertRow) ? true : false;
					while (k < datalen) {
						data = rdata[k];
						row = "";
						if (aradd) {
							try {
								rowid = data[cnm];
							} catch (e) {
								rowid = (t.p.records + 1) + "";
							}
							cna = t.p.altRows === true ? (t.rows.length - 1)
							        % 2 === 0 ? cn : "" : "";
						}
						if (ni) {
							prp = t.formatCol(0, 1, '');
							row += "<td role=\"gridcell\" aria-describedby=\""
							        + t.p.id
							        + "_rn\" class=\"ui-state-default jqgrid-rownum\" "
							        + prp + ">0</td>";
						}
						if (gi) {
							v = "<input role=\"checkbox\" type=\"checkbox\""
							        + " id=\"jqg_" + t.p.id + "_" + rowid
							        + "\" class=\"cbox\"/>";
							prp = t.formatCol(ni, 1, '');
							row += "<td role=\"gridcell\" aria-describedby=\""
							        + t.p.id + "_cb\" " + prp + ">" + v
							        + "</td>";
						}
						if (si) {
							row += $(t).jqGrid("addSubGridCell", gi + ni, 1);
						}
						for (i = gi + si + ni; i < t.p.colModel.length; i++) {
							cm = t.p.colModel[i];
							nm = cm.name;
							lcdata[nm] = cm.formatter
							        && typeof(cm.formatter) === 'string'
							        && cm.formatter == 'date' ? $.unformat
							        .date(data[nm], cm) : data[nm];
							v = t.formatter(rowid, $.jgrid
							                .getAccessor(data, nm), i, data,
							        'edit'
							);
							prp = t.formatCol(i, 1, v);
							row += "<td role=\"gridcell\" aria-describedby=\""
							        + t.p.id + "_" + nm + "\" " + prp + ">" + v
							        + "</td>";
						}
						row = "<tr id=\""
						        + rowid
						        + "\" role=\"row\" class=\"ui-widget-content jqgrow ui-row-"
						        + t.p.direction + " " + cna + "\">" + row
						        + "</tr>";
						if (t.p.subGrid === true) {
							row = $(row)[0];
							$(t).jqGrid("addSubGrid", row, gi + ni);
						}
						if (t.rows.length === 0) {
							$("table:first", t.grid.bDiv).append(row);
						} else {
							switch (pos) {
								case 'last' :
									$(t.rows[t.rows.length - 1]).after(row);
									break;
								case 'first' :
									$(t.rows[0]).after(row);
									break;
								case 'after' :
									sind = t.rows.namedItem(src);
									if (sind) {
										if ($(t.rows[sind.rowIndex + 1])
										        .hasClass("ui-subgrid")) {
											$(t.rows[sind.rowIndex + 1])
											        .after(row);
										} else {
											$(sind).after(row);
										}
									}
									break;
								case 'before' :
									sind = t.rows.namedItem(src);
									if (sind) {
										$(sind).before(row);
										sind = sind.rowIndex;
									}
									break;
							}
						}
						t.p.records++;
						t.p.reccount++;
						if (air) {
							t.p.afterInsertRow.call(t, rowid, data, data);
						}
						k++;
						if (t.p.datatype == 'local') {
							t.p._index[rowid] = t.p.data.length;
							t.p.data.push(lcdata);
							lcdata = {};
						}
					}
					if (t.p.altRows === true && !aradd) {
						if (pos == "last") {
							if ((t.rows.length - 1) % 2 == 1) {
								$(t.rows[t.rows.length - 1]).addClass(cn);
							}
						} else {
							$(t.rows).each(function(i) {
								if (i % 2 == 1) {
									$(this).addClass(cn);
								} else {
									$(this).removeClass(cn);
								}
							});
						}
					}
					t.updatepager(true, true);
					success = true;
				}
				);
			}
			return success;
		},
		footerData		: function(action, data, format) {
			var nm, success = false, res = {}, title;
			function isEmpty(obj) {
				for (var i in obj) {
					if (obj.hasOwnProperty(i)) {
						return false;
					}
				}
				return true;
			}
			if (typeof(action) == "undefined") {
				action = "get";
			}
			if (typeof(format) != "boolean") {
				format = true;
			}
			action = action.toLowerCase();
			var textAlign = false;
			this.each(function() {
				var t = this, vl;
				if (!t.grid || !t.p.footerrow) {
					return false;
				}
				if (action == "set") {
					if (isEmpty(data)) {
						return false;
					}
				}
				success = true;
				$(this.p.colModel).each(function(i) {
					nm = this.name;
					if (action == "set") {
						/**
						 * 针对页面底部userData数据行，新增colModel配置项userDataAlign，用于配置userData的对齐方式，另userData中无数据的列不显示其右边框，用于实现合并列效果
						 * edit by zhaomd
						 */
						if (data[nm] !== undefined) {
							vl = format ? t.formatter("", data[nm], i, data,
							        'edit'
							) : data[nm];
							title = this.title ? {
								"title"	: $.jgrid.stripHtml(vl)
							} : {};
							textAlign = this.userDataAlign || this.align;
							var $td = $("tr.footrow td:eq(" + i + ")",
							        t.grid.sDiv
							).html(vl).attr(title).css('textAlign', textAlign);
							success = true;
						} else if (nm != 'rn') {
							$("tr.footrow td:eq(" + i + ")", t.grid.sDiv).css(
							        'borderRight', "1px solid transparent"
							);
						}
						/** edit end */
					} else if (action == "get") {
						res[nm] = $("tr.footrow td:eq(" + i + ")", t.grid.sDiv)
						        .html();
					}
				}
				);
				
			});
			return action == "get" ? res : success;
		},
		ShowHideCol		: function(colname, show) {
			return this.each(function() {
				var $t = this, fndh = false;
				if (!$t.grid) {
					return;
				}
				if (typeof colname === 'string') {
					colname = [
						colname
					];
				}
				show = show != "none" ? "" : "none";
				var sw = show === "" ? true : false;
				$(this.p.colModel).each(function(i) {
					if ($.inArray(this.name, colname) !== -1
					        && this.hidden === sw) {
						$("tr", $t.grid.hDiv).each(function() {
							$("th:eq(" + i + ")", this).css("display", show);
						});
						$($t.rows).each(function(j) {
							$("td:eq(" + i + ")", $t.rows[j]).css("display",
							        show
							);
						}
						);
						if ($t.p.footerrow) {
							$("td:eq(" + i + ")", $t.grid.sDiv).css("display",
							        show
							);
						}
						if (show == "none") {
							$t.p.tblwidth -= this.width + $t.p.cellLayout;
						} else {
							$t.p.tblwidth += this.width;
						}
						this.hidden = !sw;
						fndh = true;
					}
				}
				);
				if (fndh === true) {
					$("table:first", $t.grid.hDiv).width($t.p.tblwidth);
					$("table:first", $t.grid.bDiv).width($t.p.tblwidth);
					$t.grid.hDiv.scrollLeft = $t.grid.bDiv.scrollLeft;
					if ($t.p.footerrow) {
						$("table:first", $t.grid.sDiv).width($t.p.tblwidth);
						$t.grid.sDiv.scrollLeft = $t.grid.bDiv.scrollLeft;
					}
					if ($t.p.shrinkToFit === true) {
						$($t).jqGrid("setGridWidth", $t.grid.width - 0.001,
						        true
						);
					}
				}
			});
		},
		hideCol		   : function(colname) {
			return this.each(function() {
				$(this).jqGrid("ShowHideCol", colname, "none");
			});
		},
		showCol		   : function(colname) {
			return this.each(function() {
				$(this).jqGrid("ShowHideCol", colname, "");
			});
		},
		remapColumns	: function(permutation, updateCells, keepHeader) {
			function resortArray(a) {
				var ac;
				if (a.length) {
					ac = $.makeArray(a);
				} else {
					ac = $.extend({}, a);
				}
				$.each(permutation, function(i) {
					a[i] = ac[this];
				});
			}
			var ts = this.get(0);
			function resortRows(parent, clobj) {
				$(">tr" + (clobj || ""), parent).each(function() {
					var row = this;
					var elems = $.makeArray(row.cells);
					$.each(permutation, function() {
						var e = elems[this];
						if (e) {
							row.appendChild(e);
						}
					});
				});
			}
			resortArray(ts.p.colModel);
			resortArray(ts.p.colNames);
			resortArray(ts.grid.headers);
			resortRows($("thead:first", ts.grid.hDiv), keepHeader
			                && ":not(.ui-jqgrid-labels)");
			if (updateCells) {
				resortRows($("#" + ts.p.id + " tbody:first"),
				        ".jqgfirstrow, tr.jqgrow, tr.jqfoot"
				);
			}
			if (ts.p.footerrow) {
				resortRows($("tbody:first", ts.grid.sDiv));
			}
			if (ts.p.remapColumns) {
				if (!ts.p.remapColumns.length) {
					ts.p.remapColumns = $.makeArray(permutation);
				} else {
					resortArray(ts.p.remapColumns);
				}
			}
			ts.p.lastsort = $.inArray(ts.p.lastsort, permutation);
			if (ts.p.treeGrid) {
				ts.p.expColInd = $.inArray(ts.p.expColInd, permutation);
			}
		},
		setGridWidth	: function(nwidth, shrink) {
			return this.each(function() {
				if (!this.grid) {
					return;
				}
				var $t = this, cw, initwidth = 0, brd = $t.p.cellLayout, lvc, vc = 0, hs = false, scw = $t.p.scrollOffset, aw, gw = 0, tw = 0, cl = 0, cr;
				if (typeof shrink != 'boolean') {
					shrink = $t.p.shrinkToFit;
				}
				if (isNaN(nwidth)) {
					return;
				} else {
					nwidth = parseInt(nwidth, 10);
					$t.grid.width = $t.p.width = nwidth;
				}
				$("#gbox_" + $t.p.id).css("width", nwidth + "px");
				$("#gview_" + $t.p.id).css("width", nwidth + "px");
				$($t.grid.bDiv).css("width", nwidth + "px");
				$($t.grid.hDiv).css("width", nwidth + "px");
				if ($t.p.pager) {
					$($t.p.pager).css("width", nwidth + "px");
				}
				if ($t.p.toppager) {
					$($t.p.toppager).css("width", nwidth + "px");
				}
				if ($t.p.toolbar[0] === true) {
					$($t.grid.uDiv).css("width", nwidth + "px");
					if ($t.p.toolbar[1] == "both") {
						$($t.grid.ubDiv).css("width", nwidth + "px");
					}
				}
				if ($t.p.footerrow) {
					$($t.grid.sDiv).css("width", nwidth + "px");
				}
				if (shrink === false && $t.p.forceFit === true) {
					$t.p.forceFit = false;
				}
				if (shrink === true) {
					if ($.browser.safari) {
						brd = 0;
					}
					$.each($t.p.colModel, function(i) {
						if (this.hidden === false) {
							initwidth += parseInt(this.width, 10);
							if (this.fixed) {
								tw += this.width;
								gw += this.width + brd;
							} else {
								vc++;
							}
							cl++;
						}
					});
					if (vc === 0) {
						return;
					}
					$t.p.tblwidth = initwidth;
					aw = nwidth - brd * vc - gw;
					if (!isNaN($t.p.height)) {
						if ($($t.grid.bDiv)[0].clientHeight < $($t.grid.bDiv)[0].scrollHeight
						        || $t.rows.length === 1) {
							hs = true;
							aw -= scw;
						}
					}
					initwidth = 0;
					var cle = $t.grid.cols.length > 0;
					var $hths = $('table:first', $t.grid.hDiv)
					        .find("tr:first>th");
					$.each($t.p.colModel, function(i) {
						if (this.hidden === false && !this.fixed) {
							cw = Math.round(aw * this.width
							        / ($t.p.tblwidth - tw));
							if (cw < 8) {// 当列宽小于一个字符时不再调整该列的列宽 add by zhaomd
								return;
							}
							this.width = cw;
							initwidth += cw;
							$t.grid.headers[i].width = cw;
							$t.grid.headers[i].el.style.width = cw + "px";
							$hths.eq(i).css("width", cw + "px");
							if ($t.p.footerrow) {
								$t.grid.footers[i].style.width = cw + "px";
							}
							if (cle) {
								$t.grid.cols[i].style.width = cw + "px";
							}
							lvc = i;
						}
					});
					if (!lvc) {// 当没有列适合调整列宽时，不再继续调整表格宽度，该为出横向滚动条 add by zhaomd
						return;
					}
					cr = 0;
					if (hs) {
						if (nwidth - gw - (initwidth + brd * vc) !== scw) {
							cr = nwidth - gw - (initwidth + brd * vc) - scw;
						}
					} else if (Math.abs(nwidth - gw - (initwidth + brd * vc)) !== 1) {
						cr = nwidth - gw - (initwidth + brd * vc);
					}
					$t.p.colModel[lvc].width += cr;
					$t.p.tblwidth = initwidth + cr + tw + brd * cl;
					if ($t.p.tblwidth > nwidth) {
						var delta = $t.p.tblwidth - parseInt(nwidth, 10);
						$t.p.tblwidth = nwidth;
						cw = $t.p.colModel[lvc].width = $t.p.colModel[lvc].width
						        - delta;
					} else {
						cw = $t.p.colModel[lvc].width;
					}
					$t.grid.headers[lvc].width = cw;
					$t.grid.headers[lvc].el.style.width = cw + "px";
					$hths.eq(lvc).css("width", cw + "px");
					if (cle) {
						$t.grid.cols[lvc].style.width = cw + "px";
					}
					// alert(ui-jqgrid-bdiv)
					/* 修改gird没有数据时出现的多余宽度 qiujie 2012-09-12/2012-09-20 */
					if ($t.p.rownumbers || $t.p.multiselect) {
						$('table:first', $t.grid.bDiv).css("width",
						        $t.p.tblwidth + "px"
						);
						$('table:first', $t.grid.hDiv).css("width",
						        $t.p.tblwidth + "px"
						);
					} else {
						$('table:first', $t.grid.bDiv).css("width",
						        $t.grid.width + "px"
						);
						$('table:first', $t.grid.hDiv).css("width",
						        $t.grid.width + "px"
						);
					}
					/* end 修改gird没有数据时出现的多余宽度 qiujie 2012-09-12 */
					$t.grid.hDiv.scrollLeft = $t.grid.bDiv.scrollLeft;
					if ($t.p.footerrow) {
						$t.grid.footers[lvc].style.width = cw + "px";
						$('table:first', $t.grid.sDiv).css("width",
						        $t.p.tblwidth + "px"
						);
					}
				}
			}
			);
		},
		setGridHeight	: function(nh) {
			return this.each(function() {
				var $t = this;
				if (!$t.grid) {
					return;
				}
				$($t.grid.bDiv).css({
					height	: nh + (isNaN(nh) ? "" : "px")
				});
				$t.p.height = nh;
				if ($t.p.scroll) {
					$t.grid.populateVisible();
				}
			});
		},
		setCaption		: function(newcap) {
			return this.each(function() {
				this.p.caption = newcap;
				$("span.ui-jqgrid-title", this.grid.cDiv).html(newcap);
				$(this.grid.cDiv).show();
			});
		},
		setLabel		: function(colname, nData, prop, attrp) {
			return this.each(function() {
				var $t = this, pos = -1;
				if (!$t.grid) {
					return;
				}
				if (isNaN(colname)) {
					$($t.p.colModel).each(function(i) {
						if (this.name == colname) {
							pos = i;
							return false;
						}
					});
				} else {
					pos = parseInt(colname, 10);
				}
				if (pos >= 0) {
					var thecol = $("tr.ui-jqgrid-labels th:eq(" + pos + ")",
					        $t.grid.hDiv
					);
					if (nData) {
						var ico = $(".s-ico", thecol);
						$("[id^=jqgh_]", thecol).empty().html(nData)
						        .append(ico);
						$t.p.colNames[pos] = nData;
					}
					if (prop) {
						if (typeof prop === 'string') {
							$(thecol).addClass(prop);
						} else {
							$(thecol).css(prop);
						}
					}
					if (typeof attrp === 'object') {
						$(thecol).attr(attrp);
					}
				}
			});
		},
		setCell		   : function(rowid, colname, nData, cssp, attrp, forceupd) {
			return this.each(function() {
				var $t = this, pos = -1, v, title;
				if (!$t.grid) {
					return;
				}
				if (isNaN(colname)) {
					$($t.p.colModel).each(function(i) {
						if (this.name == colname) {
							pos = i;
							return false;
						}
					});
				} else {
					pos = parseInt(colname, 10);
				}
				if (pos >= 0) {
					var ind = $t.rows.namedItem(rowid);
					if (ind) {
						var tcell = $("td:eq(" + pos + ")", ind);
						if (nData !== "" || forceupd === true) {
							v = $t.formatter(rowid, nData, pos, ind, 'edit');
							title = $t.p.colModel[pos].title ? {
								"title"	: $.jgrid.stripHtml(v)
							} : {};
							if ($t.p.treeGrid
							        && $(".tree-wrap", $(tcell)).length > 0) {
								$("span", $(tcell)).html(v).attr(title);
							} else {
								$(tcell).html(v).attr(title);
							}
							if ($t.p.datatype == "local") {
								var cm = $t.p.colModel[pos], index;
								nData = cm.formatter
								        && typeof(cm.formatter) === 'string'
								        && cm.formatter == 'date' ? $.unformat
								        .date(nData, cm) : nData;
								index = $t.p._index[rowid];
								if (typeof index != "undefined") {
									$t.p.data[index][cm.name] = nData;
								}
							}
						}
						if (typeof cssp === 'string') {
							// 当样式值最前面带有符号"-"时，则删除此样式。用于填报的单元格样式渲染 2011-07-07
							// shenlj
							if (cssp.indexOf("-") > -1) {
								$(tcell).removeClass(cssp.substring(1));
							} else {
								$(tcell).addClass(cssp);
							}
						} else if (cssp) {
							$(tcell).css(cssp);
						}
						if (typeof attrp === 'object') {
							$(tcell).attr(attrp);
						}
					}
				}
			});
		},
		getCell		   : function(rowid, col) {
			var ret = false;
			this.each(function() {
				var $t = this, pos = -1;
				if (!$t.grid) {
					return;
				}
				if (isNaN(col)) {
					$($t.p.colModel).each(function(i) {
						if (this.name === col) {
							pos = i;
							return false;
						}
					});
				} else {
					pos = parseInt(col, 10);
				}
				if (pos >= 0) {
					var ind = $t.rows.namedItem(rowid);
					if (ind) {
						try {
							ret = $.unformat($("td:eq(" + pos + ")", ind), {
								rowId		: ind.id,
								colModel	: $t.p.colModel[pos]
							}, pos);
						} catch (e) {
							ret = $.jgrid.htmlDecode($("td:eq(" + pos + ")",
							        ind
							).html());
						}
					}
				}
			});
			return ret;
		},
		
		/** 周营昭，过滤掉第一个为null的行。 */
		getCol		   : function(col, obj, mathopr) {
			var ret = [], val, sum = 0;
			obj = typeof(obj) != 'boolean' ? false : obj;
			if (typeof mathopr == 'undefined') {
				mathopr = false;
			}
			this.each(function() {
				var $t = this, pos = -1;
				if (!$t.grid) {
					return;
				}
				if (isNaN(col)) {
					$($t.p.colModel).each(function(i) {
						if (this.name === col) {
							pos = i;
							return false;
						}
					});
				} else {
					pos = parseInt(col, 10);
				}
				if (pos >= 0) {
					var ln = $t.rows.length, i = 0, j = 0;
					if (ln && ln > 0) {
						while (i < ln) {
							if ($($t.rows[i]).hasClass('jqgrow')) {
								try {
									val = $.unformat($($t.rows[i].cells[pos]),
									{
										rowId		: $t.rows[i].id,
										colModel	: $t.p.colModel[pos]
									}, pos
									);
								} catch (e) {
									val = $.jgrid
									        .htmlDecode($t.rows[i].cells[pos].innerHTML);
								}
								if (mathopr) {
									sum += parseFloat(val);
								} else if (obj) {
									ret.push({
										id		: $t.rows[j].id,
										value	: val
									});
								} else {
									ret[j] = val;
								}
								j++;
							}
							i++;
						}
						if (mathopr) {
							switch (mathopr.toLowerCase()) {
								case 'sum' :
									ret = sum;
									break;
								case 'avg' :
									ret = sum / ln;
									break;
								case 'count' :
									ret = ln;
									break;
								case 'pureCount' :
									ret = j;
								case 'pureAvg' :
									ret = sum / (j || 1);
							}
						}
					}
				}
			}
			);
			return ret;
		},
		clearGridData	: function(clearfooter) {
			return this.each(function() {
				var $t = this;
				if (!$t.grid) {
					return;
				}
				if (typeof clearfooter != 'boolean') {
					clearfooter = false;
				}
				if ($t.p.deepempty) {
					$("#" + $t.p.id + " tbody:first tr:gt(0)").remove();
				} else {
					var trf = $("#" + $t.p.id + " tbody:first tr:first")[0];
					$("#" + $t.p.id + " tbody:first").empty().append(trf);
				}
				if ($t.p.footerrow && clearfooter) {
					$(".ui-jqgrid-ftable td", $t.grid.sDiv).html("&#160;");
				}
				$t.p.selrow = null;
				$t.p.selarrrow = [];
				$t.p.savedRow = [];
				$t.p.records = 0;
				$t.p.page = 1;
				$t.p.lastpage = 0;
				$t.p.reccount = 0;
				$t.p.data = [];
				$t.p_index = {};
				$t.updatepager(true, false);
			});
		},
		getInd		   : function(rowid, rc) {
			var ret = false, rw;
			this.each(function() {
				rw = this.rows.namedItem(rowid);
				if (rw) {
					ret = rc === true ? rw : rw.rowIndex;
				}
			});
			return ret;
		}
	}
	);
})(jQuery);

;
(function($) {
	/*
	 * jqGrid common function Tony Tomov tony@trirand.com
	 * http://trirand.com/blog/ Dual licensed under the MIT and GPL licenses:
	 * http://www.opensource.org/licenses/mit-license.php
	 * http://www.gnu.org/licenses/gpl-2.0.html
	 */
	$.extend($.jgrid, {
		// Modal functions
		showModal	: function(h) {
			h.w.show();
		},
		closeModal	: function(h) {
			h.w.hide().attr("aria-hidden", "true");
			if (h.o) {
				h.o.remove();
			}
		},
		hideModal	: function(selector, o) {
			o = $.extend({
				jqm	: true,
				gb	: ''
			}, o || {});
			if (o.onClose) {
				var oncret = o.onClose(selector);
				if (typeof oncret == 'boolean' && !oncret) {
					return;
				}
			}
			if ($.fn.jqm && o.jqm === true) {
				$(selector).attr("aria-hidden", "true").jqmHide();
			} else {
				if (o.gb !== '') {
					try {
						$(".jqgrid-overlay:first", o.gb).hide();
					} catch (e) {
					}
				}
				$(selector).hide().attr("aria-hidden", "true");
			}
		},
		// Helper functions
		findPos		: function(obj) {
			var curleft = 0, curtop = 0;
			if (obj.offsetParent) {
				do {
					curleft += obj.offsetLeft;
					curtop += obj.offsetTop;
				} while (obj = obj.offsetParent);
				// do not change obj == obj.offsetParent
			}
			return [
			        curleft, curtop
			];
		},
		createModal	: function(aIDs, content, p, insertSelector, posSelector,
		        appendsel) {
			var mw = document.createElement('div'), rtlsup, self = this;
			rtlsup = $(p.gbox).attr("dir") == "rtl" ? true : false;
			mw.className = "ui-widget ui-widget-content ui-corner-all ui-jqdialog";
			mw.id = aIDs.themodal;
			var mh = document.createElement('div');
			mh.className = "ui-jqdialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix";
			mh.id = aIDs.modalhead;
			$(mh).append("<span class='ui-jqdialog-title'>" + p.caption
			        + "</span>");
			var ahr = $("<a href='javascript:void(0)' class='ui-jqdialog-titlebar-close ui-corner-all'></a>")
			        .hover(function() {
				        ahr.addClass('ui-state-hover');
			        }, function() {
				        ahr.removeClass('ui-state-hover');
			        })
			        .append("<span class='ui-icon ui-icon-closethick'></span>");
			$(mh).append(ahr);
			if (rtlsup) {
				mw.dir = "rtl";
				$(".ui-jqdialog-title", mh).css("float", "right");
				$(".ui-jqdialog-titlebar-close", mh).css("left", 0.3 + "em");
			} else {
				mw.dir = "ltr";
				$(".ui-jqdialog-title", mh).css("float", "left");
				$(".ui-jqdialog-titlebar-close", mh).css("right", 0.3 + "em");
			}
			var mc = document.createElement('div');
			$(mc).addClass("ui-jqdialog-content ui-widget-content").attr("id",
			        aIDs.modalcontent
			);
			$(mc).append(content);
			mw.appendChild(mc);
			$(mw).prepend(mh);
			if (appendsel === true) {
				$('body').append(mw);
			} // append as first child in body -for alert dialog
			else {
				$(mw).insertBefore(insertSelector);
			}
			if (typeof p.jqModal === 'undefined') {
				p.jqModal = true;
			} // internal use
			var coord = {};
			if ($.fn.jqm && p.jqModal === true) {
				if (p.left === 0 && p.top === 0) {
					var pos = [];
					pos = this.findPos(posSelector);
					p.left = pos[0] + 4;
					p.top = pos[1] + 4;
				}
				coord.top = p.top + "px";
				coord.left = p.left;
			} else if (p.left !== 0 || p.top !== 0) {
				coord.left = p.left;
				coord.top = p.top + "px";
			}
			$("a.ui-jqdialog-titlebar-close", mh).click(function(e) {
				var oncm = $("#" + aIDs.themodal).data("onClose") || p.onClose;
				var gboxclose = $("#" + aIDs.themodal).data("gbox") || p.gbox;
				self.hideModal("#" + aIDs.themodal, {
					gb		: gboxclose,
					jqm		: p.jqModal,
					onClose	: oncm
				});
				return false;
			});
			if (p.width === 0 || !p.width) {
				p.width = 300;
			}
			if (p.height === 0 || !p.height) {
				p.height = 200;
			}
			if (!p.zIndex) {
				var parentZ = $(insertSelector).parents("*[role=dialog]")
				        .first().css("z-index")
				if (parentZ)
					p.zIndex = parseInt(parentZ) + 1
				else
					p.zIndex = 950;
			}
			var rtlt = 0;
			if (rtlsup && coord.left && !appendsel) {
				rtlt = $(p.gbox).width()
				        - (!isNaN(p.width) ? parseInt(p.width, 10) : 0) - 8; // to
																				// do
				// just in case
				coord.left = parseInt(coord.left, 10) + parseInt(rtlt, 10);
			}
			if (coord.left) {
				coord.left += "px";
			}
			$(mw).css($.extend({
				width		: isNaN(p.width) ? "auto" : p.width + "px",
				height		: isNaN(p.height) ? "auto" : p.height + "px",
				zIndex		: p.zIndex,
				overflow	: 'hidden'
			}, coord)).attr({
				tabIndex			: "-1",
				"role"				: "dialog",
				"aria-labelledby"	: aIDs.modalhead,
				"aria-hidden"		: "true"
			});
			if (typeof p.drag == 'undefined') {
				p.drag = true;
			}
			if (typeof p.resize == 'undefined') {
				p.resize = true;
			}
			if (p.drag) {
				$(mh).css('cursor', 'move');
				if ($.fn.jqDrag) {
					$(mw).jqDrag(mh);
				} else {
					try {
						$(mw).draggable({
							handle	: $("#" + mh.id)
						});
					} catch (e) {
					}
				}
			}
			if (p.resize) {
				if ($.fn.jqResize) {
					$(mw)
					        .append("<div class='jqResize ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se ui-icon-grip-diagonal-se'></div>");
					$("#" + aIDs.themodal).jqResize(".jqResize",
					        aIDs.scrollelm ? "#" + aIDs.scrollelm : false
					);
				} else {
					try {
						$(mw).resizable({
							handles		: 'se, sw',
							alsoResize	: aIDs.scrollelm
							        ? "#" + aIDs.scrollelm
							        : false
						}
						);
					} catch (e) {
					}
				}
			}
			if (p.closeOnEscape === true) {
				$(mw).keydown(function(e) {
					if (e.which == 27) {
						var cone = $("#" + aIDs.themodal).data("onClose")
						        || p.onClose;
						self.hideModal(this, {
							gb		: p.gbox,
							jqm		: p.jqModal,
							onClose	: cone
						});
					}
				}
				);
			}
		},
		viewModal	: function(selector, o) {
			o = $.extend({
				toTop	: true,
				overlay	: 10,
				modal	: false,
				onShow	: this.showModal,
				onHide	: this.closeModal,
				gbox	: '',
				jqm		: true,
				jqM		: true
			}, o || {});
			if ($.fn.jqm && o.jqm === true) {
				if (o.jqM) {
					$(selector).attr("aria-hidden", "false").jqm(o).jqmShow();
				} else {
					$(selector).attr("aria-hidden", "false").jqmShow();
				}
			} else {
				if (o.gbox !== '') {
					$(".jqgrid-overlay:first", o.gbox).show();
					$(selector).data("gbox", o.gbox);
				}
				$(selector).show().attr("aria-hidden", "false");
				try {
					$(':input:visible', selector)[0].focus();
				} catch (_) {
				}
			}
		},
		
		info_dialog	: function(caption, content, c_b, modalopt) {
			var mopt = {
				width			: 290,
				height			: 'auto',
				dataheight		: 'auto',
				drag			: true,
				resize			: false,
				caption			: "<b>" + caption + "</b>",
				left			: 250,
				top				: 170,
				zIndex			: 1000,
				jqModal			: true,
				modal			: false,
				closeOnEscape	: true,
				align			: 'center',
				buttonalign		: 'center',
				buttons			: []
				// {text:'textbutt', id:"buttid", onClick : function(){...}}
				// if the id is not provided we set it like info_button_+ the
				// index in the array - i.e info_button_0,info_button_1...
			};
			$.extend(mopt, modalopt || {});
			var jm = mopt.jqModal, self = this;
			if ($.fn.jqm && !jm) {
				jm = false;
			}
			// in case there is no jqModal
			var buttstr = "";
			if (mopt.buttons.length > 0) {
				for (var i = 0; i < mopt.buttons.length; i++) {
					if (typeof mopt.buttons[i].id == "undefined") {
						mopt.buttons[i].id = "info_button_" + i;
					}
					buttstr += "<a href='javascript:void(0)' id='"
					        + mopt.buttons[i].id
					        + "' class='fm-button ui-state-default ui-corner-all'>"
					        + mopt.buttons[i].text + "</a>";
				}
			}
			var dh = isNaN(mopt.dataheight) ? mopt.dataheight : mopt.dataheight
			        + "px", cn = "text-align:" + mopt.align + ";";
			var cnt = "<div id='info_id'>";
			cnt += "<div id='infocnt' style='margin:0px;padding-bottom:1em;width:100%;overflow:auto;position:relative;height:"
			        + dh + ";" + cn + "'>" + content + "</div>";
			cnt += c_b
			        ? "<div class='ui-widget-content ui-helper-clearfix' style='text-align:"
			                + mopt.buttonalign
			                + ";padding-bottom:0.8em;padding-top:0.5em;background-image: none;border-width: 1px 0 0 0;'><a href='javascript:void(0)' id='closedialog' class='fm-button ui-state-default ui-corner-all'>"
			                + c_b + "</a>" + buttstr + "</div>"
			        : buttstr !== ""
			                ? "<div class='ui-widget-content ui-helper-clearfix' style='text-align:"
			                        + mopt.buttonalign
			                        + ";padding-bottom:0.8em;padding-top:0.5em;background-image: none;border-width: 1px 0 0 0;'>"
			                        + buttstr + "</div>"
			                : "";
			cnt += "</div>";
			
			try {
				if ($("#info_dialog").attr("aria-hidden") == "false") {
					this.hideModal("#info_dialog", {
						jqm	: jm
					});
				}
				$("#info_dialog").remove();
			} catch (e) {
			}
			this.createModal({
				themodal		: 'info_dialog',
				modalhead		: 'info_head',
				modalcontent	: 'info_content',
				scrollelm		: 'infocnt'
			}, cnt, mopt, '', '', true);
			// attach onclick after inserting into the dom
			if (buttstr) {
				$.each(mopt.buttons, function(i) {
					$("#" + this.id, "#info_id").bind('click', function() {
						mopt.buttons[i].onClick.call($("#info_dialog"));
						return false;
					});
				});
			}
			$("#closedialog", "#info_id").click(function(e) {
				self.hideModal("#info_dialog", {
					jqm	: jm
				});
				return false;
			});
			$(".fm-button", "#info_dialog").hover(function() {
				$(this).addClass('ui-state-hover');
			}, function() {
				$(this).removeClass('ui-state-hover');
			});
			if ($.isFunction(mopt.beforeOpen)) {
				mopt.beforeOpen();
			}
			this.viewModal("#info_dialog", {
				onHide	: function(h) {
					h.w.hide().remove();
					if (h.o) {
						h.o.remove();
					}
				},
				modal	: mopt.modal,
				jqm		: jm
			});
			if ($.isFunction(mopt.afterOpen)) {
				mopt.afterOpen();
			}
			try {
				$("#info_dialog").focus();
			} catch (e) {
			}
		},
		// Form Functions
		createEl	: function(eltype, options, vl, autowidth, ajaxso) {
			var elem = "";
			if (options.defaultValue) {
				delete options.defaultValue;
			}
			function bindEv(el, opt) {
				if ($.isFunction(opt.dataInit)) {
					// datepicker fix
					el.id = opt.id;
					opt.dataInit(el);
					delete opt.id;
					delete opt.dataInit;
				}
				if (opt.dataEvents) {
					$.each(opt.dataEvents, function() {
						if (this.data !== undefined) {
							$(el).bind(this.type, this.data, this.fn);
						} else {
							// modify by zhouyingzhao
							$(el).bind(this.type, opt, this.fn);
						}
					});
					delete opt.dataEvents;
				}
				return opt;
			}
			
			switch (eltype) {
				case "textarea" :
					elem = document.createElement("textarea");
					if (autowidth) {
						if (!options.cols) {
							$(elem).css({
								width	: "98%"
							});
						}
					} else if (!options.cols) {
						options.cols = 20;
					}
					if (!options.rows) {
						options.rows = 2;
					}
					
					// 文本输入框高度 add by zhougz 2011-12-07
					if (options.height) {
						$(elem).css({
							'height'	: options.height
						})
					}
					
					if (vl == '&nbsp;' || vl == '&#160;'
					        || (vl.length == 1 && vl.charCodeAt(0) == 160)) {
						vl = "";
					}
					elem.value = vl;
					options = bindEv(elem, options);
					$(elem).attr(options).attr({
						"role"		: "textbox",
						"multiline"	: "true"
					});
					break;
				case "checkbox" : // what code for simple checkbox
					elem = document.createElement("input");
					elem.type = "checkbox";
					if (!options.value) {
						var vl1 = vl.toLowerCase();
						if (vl1.search(/(false|0|no|off|undefined)/i) < 0
						        && vl1 !== "") {
							elem.checked = true;
							elem.defaultChecked = true;
							elem.value = vl;
						} else {
							elem.value = "on";
						}
						$(elem).attr("offval", "off");
					} else {
						var cbval = options.value.split(":");
						if (vl === cbval[0]) {
							elem.checked = true;
							elem.defaultChecked = true;
						}
						elem.value = cbval[0];
						$(elem).attr("offval", cbval[1]);
						try {
							delete options.value;
						} catch (e) {
						}
					}
					options = bindEv(elem, options);
					$(elem).attr(options).attr("role", "checkbox");
					break;
				case "radio" : // 新增radio渲染
					elem = document.createElement("div");
					var oSv = options.value;
					
					var data = new Date();
					var i = Math.floor(Math.random() * 10000) + data.getSeconds() + data.getMilliseconds();
					for (var key in oSv) {
						if (oSv.hasOwnProperty(key)) {
							ov = document.createElement("input");
							ov.setAttribute("type", "radio");
							ov.setAttribute("name", "rad" + i);
							// ov.setAttribute("id",key+i);
							ov.setAttribute("id", key);
							ov.value = key;
							lb = document.createElement("label");
							// lb.setAttribute("for",key+i);
							lb.innerHTML = oSv[key];
							if ($.trim(key) == $.trim(vl)) {
								ov.checked = "checked";
							}
							elem.appendChild(ov);
							elem.appendChild(lb);
						}
						
					}
					options = bindEv(elem, options);
					
					$(elem).attr(options).attr("value", vl);
					break;
				case "select" :
					elem = document.createElement("select");
					elem.setAttribute("role", "select");
					var msl, ovm = [];
					if (options.multiple === true) {
						msl = true;
						elem.multiple = "multiple";
						$(elem).attr("aria-multiselectable", "true");
					} else {
						msl = false;
					}
					if (typeof(options.dataUrl) != "undefined") {
						$.ajax($.extend({
							url			: options.dataUrl,
							type		: "GET",
							dataType	: "html",
							success		: function(data, status) {
								try {
									delete options.dataUrl;
									delete options.value;
								} catch (e) {
								}
								var a;
								if (typeof(options.buildSelect) != "undefined") {
									var b = options.buildSelect(data);
									a = $(b).html();
									delete options.buildSelect;
								} else {
									a = $(data).html();
								}
								if (a) {
									$(elem).append(a);
									options = bindEv(elem, options);
									if (typeof options.size === 'undefined') {
										options.size = msl ? 3 : 1;
									}
									if (msl) {
										ovm = vl.split(",");
										ovm = $.map(ovm, function(n) {
											return $.trim(n);
										});
									} else {
										ovm[0] = $.trim(vl);
									}
									$(elem).attr(options);
									setTimeout(function() {
										$("option", elem).each(function(i) {
											if (i === 0) {
												this.selected = "";
											}
											$(this).attr("role", "option");
											if ($.inArray($
											                .trim($(this)
											                        .text()),
											        ovm
											) > -1
											        || $.inArray($.trim($(this)
											                        .val()),
											                ovm
											        ) > -1) {
												this.selected = "selected";
												if (!msl) {
													return false;
												}
											}
										}
										);
									}, 0);
								}
							}
						}, ajaxso || {}
						));
					} else if (options.value) {
						var i;
						if (msl) {
							ovm = vl.split(",");
							ovm = $.map(ovm, function(n) {
								return $.trim(n);
							});
							if (typeof options.size === 'undefined') {
								options.size = 3;
							}
						} else {
							options.size = 1;
						}
						if (typeof options.value === 'function') {
							options.value = options.value();
						}
						var so, sv, ov;
						if (typeof options.value === 'string') {
							so = options.value.split(";");
							for (i = 0; i < so.length; i++) {
								sv = so[i].split(":");
								if (sv.length > 2) {
									sv[1] = $.map(sv, function(n, i) {
										if (i > 0) {
											return n;
										}
									}).join(":");
								}
								ov = document.createElement("option");
								ov.setAttribute("role", "option");
								ov.value = sv[0];
								ov.innerHTML = sv[1];
								if (!msl
								        && ($.trim(sv[0]) == $.trim(vl) || $
								                .trim(sv[1]) == $.trim(vl))) {
									ov.selected = "selected";
								}
								if (msl
								        && ($.inArray($.trim(sv[1]), ovm) > -1 || $
								                .inArray($.trim(sv[0]), ovm) > -1)) {
									ov.selected = "selected";
								}
								elem.appendChild(ov);
							}
						} else if (typeof options.value === 'object') {
							var oSv = options.value;
							for (var key in oSv) {
								if (oSv.hasOwnProperty(key)) {
									ov = document.createElement("option");
									ov.setAttribute("role", "option");
									ov.value = key;
									ov.innerHTML = oSv[key];
									if (!msl
									        && ($.trim(key) == $.trim(vl) || $
									                .trim(oSv[key]) == $
									                .trim(vl))) {
										ov.selected = "selected";
									}
									if (msl
									        && ($
									                .inArray($.trim(oSv[key]),
									                        ovm
									                ) > -1 || $.inArray($
									                        .trim(key), ovm) > -1)) {
										ov.selected = "selected";
									}
									elem.appendChild(ov);
								}
							}
						}
						options = bindEv(elem, options);
						try {
							delete options.value;
						} catch (e) {
						}
						$(elem).attr(options);
					}
					break;
				case "text" :
				case "password" :
				case "button" :
					var role;
					if (eltype == "button") {
						role = "button";
					} else {
						role = "textbox";
					}
					elem = document.createElement("input");
					elem.type = eltype;
					elem.value = vl;
					options = bindEv(elem, options);
					if (eltype != "button") {
						if (autowidth) {
							if (!options.size) {
								$(elem).css({
									width	: "98%"
								});
							}
						} else if (!options.size) {
							options.size = 20;
						}
					}
					$(elem).attr(options).attr("role", role);
					break;
				case "image" :
				case "file" :
					elem = document.createElement("input");
					elem.type = eltype;
					options = bindEv(elem, options);
					$(elem).attr(options);
					break;
				case "custom" :
					elem = document.createElement("span");
					try {
						if ($.isFunction(options.custom_element)) {
							var celm = options.custom_element.call(this, vl,
							        options
							);
							if (celm) {
								celm = $(celm).addClass("customelement").attr({
									id		: options.id,
									name	: options.name
								});
								$(elem).empty().append(celm);
							} else {
								throw "e2";
							}
						} else {
							throw "e1";
						}
					} catch (e) {
						if (e == "e1") {
							this.info_dialog($.jgrid.errors.errcap,
							        "function 'custom_element' "
							                + $.jgrid.edit.msg.nodefined,
							        $.jgrid.edit.bClose
							);
						}
						if (e == "e2") {
							this.info_dialog($.jgrid.errors.errcap,
							        "function 'custom_element' "
							                + $.jgrid.edit.msg.novalue,
							        $.jgrid.edit.bClose
							);
						} else {
							this.info_dialog($.jgrid.errors.errcap,
							        typeof(e) === "string" ? e : e.message,
							        $.jgrid.edit.bClose
							);
						}
					}
					break;
			}
			return elem;
		},
		// Date Validation Javascript
		checkDate	: function(format, date) {
			var daysInFebruary = function(year) {
				// February has 29 days in any year evenly divisible by four,
				// EXCEPT for centurial years which are not also divisible by
				// 400.
				return (((year % 4 === 0) && (year % 100 !== 0 || (year % 400 === 0)))
				        ? 29
				        : 28);
			}, DaysArray = function(n) {
				for (var i = 1; i <= n; i++) {
					this[i] = 31;
					if (i == 4 || i == 6 || i == 9 || i == 11) {
						this[i] = 30;
					}
					if (i == 2) {
						this[i] = 29;
					}
				}
				return this;
			};
			
			var tsp = {}, sep;
			format = format.toLowerCase();
			// we search for /,-,. for the date separator
			if (format.indexOf("/") != -1) {
				sep = "/";
			} else if (format.indexOf("-") != -1) {
				sep = "-";
			} else if (format.indexOf(".") != -1) {
				sep = ".";
			} else {
				sep = "/";
			}
			format = format.split(sep);
			date = date.split(sep);
			if (date.length != 3) {
				return false;
			}
			var j = -1, yln, dln = -1, mln = -1;
			for (var i = 0; i < format.length; i++) {
				var dv = isNaN(date[i]) ? 0 : parseInt(date[i], 10);
				tsp[format[i]] = dv;
				yln = format[i];
				if (yln.indexOf("y") != -1) {
					j = i;
				}
				if (yln.indexOf("m") != -1) {
					mln = i;
				}
				if (yln.indexOf("d") != -1) {
					dln = i;
				}
			}
			if (format[j] == "y" || format[j] == "yyyy") {
				yln = 4;
			} else if (format[j] == "yy") {
				yln = 2;
			} else {
				yln = -1;
			}
			var daysInMonth = DaysArray(12), strDate;
			if (j === -1) {
				return false;
			} else {
				strDate = tsp[format[j]].toString();
				if (yln == 2 && strDate.length == 1) {
					yln = 1;
				}
				if (strDate.length != yln
				        || (tsp[format[j]] === 0 && date[j] != "00")) {
					return false;
				}
			}
			if (mln === -1) {
				return false;
			} else {
				strDate = tsp[format[mln]].toString();
				if (strDate.length < 1 || tsp[format[mln]] < 1
				        || tsp[format[mln]] > 12) {
					return false;
				}
			}
			if (dln === -1) {
				return false;
			} else {
				strDate = tsp[format[dln]].toString();
				if (strDate.length < 1
				        || tsp[format[dln]] < 1
				        || tsp[format[dln]] > 31
				        || (tsp[format[mln]] == 2 && tsp[format[dln]] > daysInFebruary(tsp[format[j]]))
				        || tsp[format[dln]] > daysInMonth[tsp[format[mln]]]) {
					return false;
				}
			}
			return true;
		},
		isEmpty		: function(val) {
			if (val.match(/^\s+$/) || val === "") {
				return true;
			} else {
				return false;
			}
		},
		checkTime	: function(time) {
			// checks only hh:ss (and optional am/pm)
			var re = /^(\d{1,2}):(\d{2})([ap]m)?$/, regs;
			if (!this.isEmpty(time)) {
				regs = time.match(re);
				if (regs) {
					if (regs[3]) {
						if (regs[1] < 1 || regs[1] > 12) {
							return false;
						}
					} else {
						if (regs[1] > 23) {
							return false;
						}
					}
					if (regs[2] > 59) {
						return false;
					}
				} else {
					return false;
				}
			}
			return true;
		},
		checkValues	: function(val, valref, rowid, g) {
			var edtrul, i, nm, dft, len;
			if (typeof(valref) == 'string') {
				for (i = 0, len = g.p.colModel.length; i < len; i++) {
					if (g.p.colModel[i].name == valref) {
						edtrul = g.p.colModel[i].editrules;
						valref = i;
						try {
							nm = g.p.colModel[i].formoptions.label;
						} catch (e) {
						}
						break;
					}
				}
			} else if (valref >= 0) {
				edtrul = g.p.colModel[valref].editrules;
			}
			if (edtrul) {
				if (!nm) {
					nm = g.p.colNames[valref];
				}
				
				if (edtrul.required === true) {
					if (this.isEmpty(val)) {
						return [
						        false, nm + ": " + $.jgrid.edit.msg.required,
						        ""
						];
					}
				}
				// force required
				var rqfield = edtrul.required === false ? false : true;
				if (edtrul.number === true) {
					if (!(rqfield === false && this.isEmpty(val))) {
						if (isNaN(val)) {
							return [
							        false, nm + ": " + $.jgrid.edit.msg.number,
							        ""
							];
						}
					}
				}
				if (typeof edtrul.minValue != 'undefined'
				        && !isNaN(edtrul.minValue)) {
					
					if (parseFloat(val) < parseFloat(edtrul.minValue)) {
						return [
						        false,
						        nm + ": " + $.jgrid.edit.msg.minValue + " "
						                + edtrul.minValue, ""
						];
					}
				}
				if (typeof edtrul.maxValue != 'undefined'
				        && !isNaN(edtrul.maxValue)) {
					
					if (parseFloat(val) > parseFloat(edtrul.maxValue)) {
						return [
						        false,
						        nm + ": " + $.jgrid.edit.msg.maxValue + " "
						                + edtrul.maxValue, ""
						];
					}
				}
				if (typeof edtrul.maxLength != 'undefined'
				        && !isNaN(edtrul.maxLength)) { // edit by qiujie
														// 2012-08-06 新增长度校验
					if (parseFloat(val.length2()) > parseFloat(edtrul.maxLength
					        * 2)) {
						return [
						        false,
						        nm
						                + ": 中文不得超过"
						                + Math
						                        .floor(parseFloat(edtrul.maxLength))
						                + "个字符，英文不得超过"
						                + Math
						                        .floor(parseFloat(edtrul.maxLength)
						                                * 2) + "个字符 ", ""
						];
					}
				}
				if (typeof edtrul.onlyFloat != 'undefined'
				        && !isNaN(edtrul.onlyFloat)) { // edit by qiujie
														// 2012-08-07
														// 新增验证小数点后保留两位
					var validator = /^[0-9]+(.[0-9]{2})?$/;
					
					if (!validator.test(val)) {
						return [
						        false, nm + ": 只能是整数或者小数点后保留两位!", ""
						];
					}
				}
				var filter;
				if (edtrul.email === true) {
					if (!(rqfield === false && this.isEmpty(val))) {
						// taken from $ Validate plugin
						filter = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i;
						if (!filter.test(val)) {
							return [
							        false, nm + ": " + $.jgrid.edit.msg.email,
							        ""
							];
						}
					}
				}
				if (edtrul.integer === true) {
					if (!(rqfield === false && this.isEmpty(val))) {
						if (isNaN(val)) {
							return [
							        false,
							        nm + ": " + $.jgrid.edit.msg.integer, ""
							];
						}
						if ((val % 1 !== 0) || (val.indexOf('.') != -1)) {
							return [
							        false,
							        nm + ": " + $.jgrid.edit.msg.integer, ""
							];
						}
					}
				}
				if (edtrul.date === true) {
					if (!(rqfield === false && this.isEmpty(val))) {
						if (g.p.colModel[valref].formatoptions
						        && g.p.colModel[valref].formatoptions.newformat) {
							dft = g.p.colModel[valref].formatoptions.newformat;
						} else {
							dft = g.p.colModel[valref].datefmt || "Y-m-d";
						}
						if (!this.checkDate(dft, val)) {
							return [
							        false,
							        nm + ": " + $.jgrid.edit.msg.date + " - "
							                + dft, ""
							];
						}
					}
				}
				if (edtrul.time === true) {
					if (!(rqfield === false && this.isEmpty(val))) {
						if (!this.checkTime(val)) {
							return [
							        false,
							        nm + ": " + $.jgrid.edit.msg.date
							                + " - hh:mm (am/pm)", ""
							];
						}
					}
				}
				if (edtrul.url === true) {
					if (!(rqfield === false && this.isEmpty(val))) {
						filter = /^(((https?)|(ftp)):\/\/([\-\w]+\.)+\w{2,3}(\/[%\-\w]+(\.\w{2,})?)*(([\w\-\.\?\\\/+@&#;`~=%!]*)(\.\w{2,})?)*\/?)/i;
						if (!filter.test(val)) {
							return [
							        false, nm + ": " + $.jgrid.edit.msg.url, ""
							];
						}
					}
				}
				
				// add by yangning 2011/07/27
				if (edtrul.decimalnum
				        && typeof edtrul.decimalnum != 'undefined'
				        && !isNaN(edtrul.decimalnum)) {
					if (!(rqfield === false && this.isEmpty(val))) {
						if (val
						        && !isNaN(val)
						        && 0 < val.toString().indexOf(".")
						        && edtrul.decimalnum < val.toString()
						                .split(".")[1].length) {
							return [
							        false,
							        "输入的数字中小数位数不应该超过" + edtrul.decimalnum + "位",
							        ""
							]
						}
					}
				}
				
				if (edtrul.custom === true) {
					if (!(rqfield === false && this.isEmpty(val))) {
						if ($.isFunction(edtrul.custom_func)) {
							var ret = edtrul.custom_func
							        .call(g, val, nm, rowid);
							if ($.isArray(ret)) {
								return ret;
							} else {
								return [
								        false, $.jgrid.edit.msg.customarray, ""
								];
							}
						} else {
							return [
							        false, $.jgrid.edit.msg.customfcheck, ""
							];
						}
					}
				}
			}
			return [
			        true, "", ""
			];
		}
	}
	);
})(jQuery);

(function($) {
	/**
	 * jqGrid extension for form editing Grid Data Tony Tomov tony@trirand.com
	 * http://trirand.com/blog/ Dual licensed under the MIT and GPL licenses:
	 * http://www.opensource.org/licenses/mit-license.php
	 * http://www.gnu.org/licenses/gpl-2.0.html
	 */
	var rp_ge = null;
	$.jgrid.extend({
		// 增加：增加行与删除行的功能 2011-07-07 shenlj
		navGrid		 : function(elem, o, pEdit, pAdd, pDel, pSearch, pView) {
			o = $.extend({
				refresh			: false,
				refreshicon		: "ui-icon-refresh",
				refreshstate	: 'firstpage',
				print			: false,
				printicon		: "ui-icon-print",
				
				up				: false,
				upicon			: "ui-icon-arrowthick-1-n",
				down			: false,
				downicon		: "ui-icon-arrowthick-1-s",
				top				: false,
				topicon			: "ui-icon-arrowthickstop-1-n",
				bottom			: false,
				bottomicon		: "ui-icon-arrowthickstop-1-s",
				
				add				: false,
				addicon			: "ui-icon-plus",
				addtext			: "增加",
				del				: false,
				delicon			: "ui-icon-trash",
				deltext			: "删除",
				position		: "left",
				closeOnEscape	: true,
				beforeRefresh	: null,
				afterRefresh	: null,
				cloneToTop		: false
			}, $.jgrid.nav, o || {});
			return this.each(function() {
				var alertIDs = {
					themodal		: 'alertmod',
					modalhead		: 'alerthd',
					modalcontent	: 'alertcnt'
				}, $t = this, vwidth, vheight, twd, tdw;
				if (!$t.grid || typeof elem != 'string') {
					return;
				}
				if ($("#" + alertIDs.themodal).html() === null) {
					if (typeof window.innerWidth != 'undefined') {
						vwidth = window.innerWidth;
						vheight = window.innerHeight;
					} else if (typeof document.documentElement != 'undefined'
					        && typeof document.documentElement.clientWidth != 'undefined'
					        && document.documentElement.clientWidth !== 0) {
						vwidth = document.documentElement.clientWidth;
						vheight = document.documentElement.clientHeight;
					} else {
						vwidth = 1024;
						vheight = 768;
					}
					$.jgrid
					        .createModal(
					        alertIDs,
					        "<div>"
					                + o.alerttext
					                + "</div><span tabindex='0'><span tabindex='-1' id='jqg_alrt'></span></span>",
					        {
						        gbox			: "#gbox_" + $t.p.id,
						        jqModal			: true,
						        drag			: true,
						        resize			: true,
						        caption			: o.alertcap,
						        top				: vheight / 2 - 25,
						        left			: vwidth / 2 - 100,
						        width			: 200,
						        height			: 'auto',
						        closeOnEscape	: o.closeOnEscape
					        }, "", "", true
					        );
				}
				var clone = 1;
				if (o.cloneToTop && $t.p.toppager) {
					clone = 2;
				}
				for (var i = 0; i < clone; i++) {
					var tbd, navtbl = $("<table cellspacing='0' cellpadding='0' border='0' class='ui-pg-table navtable' style='float:left;table-layout:auto;'><tbody><tr></tr></tbody></table>"), sep = "<td class='ui-pg-button ui-state-disabled' style='width:4px;'><span class='ui-separator'></span></td>", pgid, elemids;
					if (i === 0) {
						pgid = elem;
						elemids = $t.p.id;
						if (pgid == $t.p.toppager) {
							elemids += "_top";
							clone = 1;
						}
					} else {
						pgid = $t.p.toppager;
						elemids = $t.p.id + "_top";
					}
					if ($t.p.direction == "rtl") {
						$(navtbl).attr("dir", "rtl").css("float", "right");
					}
					if (o.add) {
						pAdd = pAdd || {};
						tbd = $("<td class='ui-pg-button ui-corner-all'></td>");
						$(tbd)
						        .append("<div class='ui-pg-div'><span class='ui-icon "
						                + o.addicon
						                + "'></span>"
						                + o.addtext
						                + "</div>");
						$("tr", navtbl).append(tbd);
						$(tbd, navtbl).attr({
							"title"	: o.addtitle || "",
							id		: pAdd.id || "add_" + elemids
						}).click(function() {
							if (!$(this).hasClass('ui-state-disabled')) {
								if ($($t).jqGrid('getGridParam', 'cellEdit')) {
									$($t).jqGrid("saveEditingCell");
								} else {
									$("tr[editable='1']", $($t).context).each(
									function(index, obj) {
										$($t).jqGrid("saveRow", obj.id);
									}
									);
								}
								
								var newRowNo = "jqgridnew"
								        + Math.floor(Math.random() * 100) + "."
								        + ((new Date()).getSeconds());
								var rowData = $($t).jqGrid('getRowData',
								        $($t).jqGrid('getGridParam', 'records')
								);
								for (var w in rowData) {
									rowData[w] = "";
								}
								$($t).jqGrid("addRowData", newRowNo, rowData,
								        "last"
								);
							}
							return false;
						}
						).hover(function() {
							if (!$(this).hasClass('ui-state-disabled')) {
								$(this).addClass("ui-state-hover");
							}
						}, function() {
							$(this).removeClass("ui-state-hover");
						});
						tbd = null;
					}
					
					if (o.del) {
						tbd = $("<td class='ui-pg-button ui-corner-all'></td>");
						pDel = pDel || {};
						$(tbd)
						        .append("<div class='ui-pg-div'><span class='ui-icon "
						                + o.delicon
						                + "'></span>"
						                + o.deltext
						                + "</div>");
						$("tr", navtbl).append(tbd);
						$(tbd, navtbl).attr({
							"title"	: o.deltitle || "",
							id		: pDel.id || "del_" + elemids
						}).click(function() {
							if (!$(this).hasClass('ui-state-disabled')) {
								var rowNum = $($t).jqGrid('getGridParam',
								        'selrow'
								);
								if (rowNum == null) {
									$("tr[editable='1']", $($t).context).each(
									function(index, obj) {
										$($t).jqGrid("saveRow", obj.id, false,
										        "clientArray"
										);
										rowNum = obj.id;
									}
									);
								}
								if (rowNum == null) {
									J.alert("请选择要删除的数据！");
									return;
								}
								var rowData = $($t)
								        .jqGrid('getRowData', rowNum);
								if (rowData.id != undefined
								        && $.trim(rowData.id) != "") {
									if ($removeIDs == "") {
										$removeIDs = rowData.id;
									} else {
										$removeIDs = $removeIDs + ","
										        + rowData.id;
									}
								}
								$($t).jqGrid("saveEditingCell");
								$($t).jqGrid("delRowData", rowNum);
							}
							return false;
						}
						).hover(function() {
							if (!$(this).hasClass('ui-state-disabled')) {
								$(this).addClass("ui-state-hover");
							}
						}, function() {
							$(this).removeClass("ui-state-hover");
						});
						tbd = null;
					}
					
					function altGridRows() {
						if ($t.p.altRows === true) {
							var cn = $t.p.altclass;
							var numStart = $t.p.rowNum * ($t.p.pageNum - 1);
							$($t.rows).each(function(i) {
								if (!$(this).is(".jqgfirstrow")) {
									if ($(this).attr("id").indexOf("_blank") != "-1") {
										$(
										        "td[class='ui-state-default jqgrid-rownum']",
										        this
										).text(numStart + (i - 1));// edit by
																	// qiujie
																	// 20121107
																	// 修改行顺序调整时序号不正确
									} else {
										$(
										        "td[class='ui-state-default jqgrid-rownum']",
										        this
										).text(numStart + (i));// edit by
																// qiujie
																// 20121107
																// 修改行顺序调整时序号不正确
									}
								}
								
								if (i % 2 == 1) {
									$(this).addClass(cn);
								} else {
									$(this).removeClass(cn);
								}
							}
							);
						}
					}
					
					/** add by 周营昭，行顺序调整时，触发时间需要增加一个延时。 */
					var rowOrderAdjustDelay = null;
					function sendAdjustRowOrderEvent() {
						if (o.rowOrderAdjust) {
							if (rowOrderAdjustDelay) {
								clearTimeout(rowOrderAdjustDelay);
								rowOrderAdjustDelay = null;
							}
							
							rowOrderAdjustDelay = setTimeout(function() {
								rowOrderAdjustDelay = null;
								o.rowOrderAdjust();
							}, 500);
						}
					}
					
					if (o.top) {
						tbd = $("<td class='ui-pg-button ui-corner-all'></td>");
						$(tbd)
						        .append("<div class='ui-pg-div'><span class='ui-icon "
						                + o.topicon
						                + "'></span>"
						                + (o.toptext || "") + "</div>");
						$("tr", navtbl).append(tbd);
						$(tbd, navtbl).attr({
							"title"	: o.toptitle || "",
							id		: "top_" + elemids
						}).click(function() {
							if (!$(this).hasClass('ui-state-disabled')) {
								
								var sels = $($t).jqGrid('getGridParam',
								        'selarrrow'
								);
								if (sels.length < 1) {
									$.alert("请选择要调整顺序的记录！");
									return;
								}
								
								if ($t.rows.length > 2) { // edit by qiujie
															// 20121101
															// 判断该当前的行数是否大于2
									// 找到一个非选中的 edit by qiujie 20120923
									for (var i = 1; i < $t.rows.length; i++) {
										if ($.inArray($t.rows[i].id, sels) === -1) {
											break;
										}
									}
									var $tobe = $($t.rows[i]);
									
									for (; i < $t.rows.length; i++) {
										if ($.inArray($t.rows[i].id, sels) > -1) {
											$tobe.before($t.rows[i]);
										}
									}
									
									altGridRows();
									sendAdjustRowOrderEvent();
								}
							}
							return false;
						}
						).hover(function() {
							if (!$(this).hasClass('ui-state-disabled')) {
								$(this).addClass("ui-state-hover");
							}
						}, function() {
							$(this).removeClass("ui-state-hover");
						});
						tbd = null;
					}
					
					if (o.up) {
						tbd = $("<td class='ui-pg-button ui-corner-all'></td>");
						$(tbd)
						        .append("<div class='ui-pg-div'><span class='ui-icon "
						                + o.upicon
						                + "'></span>"
						                + (o.uptext || "") + "</div>");
						$("tr", navtbl).append(tbd);
						$(tbd, navtbl).attr({
							"title"	: o.uptitle || "",
							id		: "up_" + elemids
						}).click(function() {
							if (!$(this).hasClass('ui-state-disabled')) {
								var sels = $($t).jqGrid('getGridParam',
								        'selarrrow'
								);
								if (sels.length < 1) {
									$.alert("请选择要调整顺序的记录！");
									return;
								}
								if ($t.rows.length > 2) { // edit by qiujie
															// 20121101
															// 判断该当前的行数是否大于2
									for (var i = 1; i < $t.rows.length; i++) {
										var lastRow = $t.rows[i - 1];
										
										if (i > 1
										        && $.inArray($t.rows[i].id,
										                sels
										        ) > -1
										        && $.inArray(lastRow.id, sels) === -1) {
											$(lastRow).before($t.rows[i]);
										}
									}
									
									altGridRows();
									sendAdjustRowOrderEvent();
								}
							}
							return false;
						}
						).hover(function() {
							if (!$(this).hasClass('ui-state-disabled')) {
								$(this).addClass("ui-state-hover");
							}
						}, function() {
							$(this).removeClass("ui-state-hover");
						});
						tbd = null;
					}
					
					if (o.down) {
						tbd = $("<td class='ui-pg-button ui-corner-all'></td>");
						$(tbd)
						        .append("<div class='ui-pg-div'><span class='ui-icon "
						                + o.downicon
						                + "'></span>"
						                + (o.downtext || "") + "</div>");
						$("tr", navtbl).append(tbd);
						$(tbd, navtbl).attr({
							"title"	: o.downtitle || "",
							id		: "down_" + elemids
						}).click(function() {
							if (!$(this).hasClass('ui-state-disabled')) {
								
								var sels = $($t).jqGrid('getGridParam',
								        'selarrrow'
								);
								if (sels.length < 1) {
									$.alert("请选择要调整顺序的记录！");
									return;
								}
								if ($t.rows.length > 2) { // edit by qiujie
															// 20121101
															// 判断该当前的行数是否大于2
									for (var i = $t.rows.length - 1; i > 0; i--) {
										var nextRow = $t.rows[i + 1];
										if (i < ($t.rows.length - 1)
										        && $.inArray($t.rows[i].id,
										                sels
										        ) > -1
										        && $.inArray(nextRow.id, sels) === -1) {
											$(nextRow).after($t.rows[i]);
										}
									}
									altGridRows();
									sendAdjustRowOrderEvent();
								}
							}
							return false;
						}
						).hover(function() {
							if (!$(this).hasClass('ui-state-disabled')) {
								$(this).addClass("ui-state-hover");
							}
						}, function() {
							$(this).removeClass("ui-state-hover");
						});
						tbd = null;
					}
					
					if (o.bottom) {
						tbd = $("<td class='ui-pg-button ui-corner-all'></td>");
						$(tbd)
						        .append("<div class='ui-pg-div'><span class='ui-icon "
						                + o.bottomicon
						                + "'></span>"
						                + (o.bottomtext || "") + "</div>");
						$("tr", navtbl).append(tbd);
						$(tbd, navtbl).attr({
							"title"	: o.bottomtitle || "",
							id		: "bottom_" + elemids
						}).click(function() {
							if (!$(this).hasClass('ui-state-disabled')) {
								
								var sels = $($t).jqGrid('getGridParam',
								        'selarrrow'
								);
								if (sels.length < 1) {
									$.alert("请选择要调整顺序的记录！");
									return;
								}
								if ($t.rows.length > 2) { // edit by qiujie
															// 20121101
															// 判断该当前的行数是否大于2
									// 找到最后一个非选择的，然后往这个后面插
									for (var i = ($t.rows.length - 1); i > 0; i--) {
										if ($.inArray($t.rows[i].id, sels) === -1) {
											break;
										}
									}
									
									var $tobe = $($t.rows[i]);
									for (; i > 0; i--) {
										if ($.inArray($t.rows[i].id, sels) > -1) {
											$($tobe).after($t.rows[i]);
										}
									}
									altGridRows();
									sendAdjustRowOrderEvent();
								}
							}
							return false;
						}
						).hover(function() {
							if (!$(this).hasClass('ui-state-disabled')) {
								$(this).addClass("ui-state-hover");
							}
						}, function() {
							$(this).removeClass("ui-state-hover");
						});
						tbd = null;
					}
					
					if (o.refresh) {
						tbd = $("<td class='ui-pg-button ui-corner-all'></td>");
						$(tbd)
						        .append("<div class='ui-pg-div'><span class='ui-icon "
						                + o.refreshicon
						                + "'></span>"
						                + o.refreshtext + "</div>");
						$("tr", navtbl).append(tbd);
						$(tbd, navtbl).attr({
							"title"	: o.refreshtitle || "",
							id		: "refresh_" + elemids
						}).click(function() {
							if (!$(this).hasClass('ui-state-disabled')) {
								if ($.isFunction(o.beforeRefresh)) {
									o.beforeRefresh();
								}
								$t.p.search = false;
								try {
									var gID = $t.p.id;
									$("#fbox_" + gID).searchFilter().reset({
										"reload"	: false
									});
									if ($.isFunction($t.clearToolbar)) {
										$t.clearToolbar(false);
									}
								} catch (e) {
								}
								switch (o.refreshstate) {
									case 'firstpage' :
										$($t).trigger("reloadGrid", [
										                {
											                page	: 1
										                }
										        ]);
										break;
									case 'current' :
										$($t).trigger("reloadGrid", [
										                {
											                current	: true
										                }
										        ]);
										break;
								}
								if ($.isFunction(o.afterRefresh)) {
									o.afterRefresh();
								}
							}
							return false;
						}).hover(function() {
							if (!$(this).hasClass('ui-state-disabled')) {
								$(this).addClass("ui-state-hover");
							}
						}, function() {
							$(this).removeClass("ui-state-hover");
						});
						tbd = null;
					}
					
					/** add by zhouyingzhao, modified by weihongru */
					if (o.print) {
						tbd = $("<td class='ui-pg-button ui-corner-all'></td>");
						$(tbd)
						        .append("<div class='ui-pg-div'><span class='ui-icon "
						                + o.printicon
						                + "'></span>"
						                + (o.printtext || "") + "</div>");
						$("tr", navtbl).append(tbd);
						
						$(tbd, navtbl).attr({
							"title"	: o.printtitle || "",
							id		: "print_" + elemids
						}).click(function() {
							// 构造iframe
							var $exportFrame = $("#jeawExportExcelFrame");
							
							if ($exportFrame.length == 0) {
								$exportFrame = $("<iframe style='display: none;width:0px;height:0px;' name='jeawExportExcelFrame' id='jeawExportExcelFrame'></iframe>");
								$exportFrame.appendTo($("body"));
							}
							
							// 构造导出的form
							var $exportForm = $("#jeawExportExcelForm");
							
							if ($exportForm.length == 0) {
								$exportForm = $("<form id='jeawExportExcelForm' name='jeawExportExcelForm' target='jeawExportExcelFrame' method='POST'> </form>");
								$exportForm.attr("action", $t.p.exportUrl);
								$exportForm.appendTo($("body"));
							} else {
								$exportForm.empty();
							}
							// 使用jqgrid中的参数来为form造hidden输入域
							for (var prop in $t.p.postData) {
								if (prop == "cols" && $t.p.exportCols) {
									$("<input type='hidden' />").attr("name",
									        prop
									).attr("value", $t.p.exportCols)
									        .appendTo($exportForm);
								} else {
									$("<input type='hidden' />").attr("name",
									        prop
									).attr("value", $t.p.postData[prop])
									        .appendTo($exportForm);
								}
								
							}
							
							$exportForm.submit();
							
						}
						).hover(function() {
							$(this).hasClass("ui-state-disabled")
							        || $(this).addClass("ui-state-hover")
						}, function() {
							$(this).removeClass("ui-state-hover")
						}
						);
						tbd = null
					}
					
					tdw = $(".ui-jqgrid").css("font-size") || "11px";
					$('body')
					        .append("<div id='testpg2' class='ui-jqgrid ui-widget ui-widget-content' style='font-size:"
					                + tdw + ";visibility:hidden;' ></div>");
					twd = $(navtbl).clone().appendTo("#testpg2").width();
					$("#testpg2").remove();
					$(pgid + "_" + o.position, pgid).append(navtbl);
					if ($t.p._nvtd) {
						if (twd > $t.p._nvtd[0]) {
							$(pgid + "_" + o.position, pgid).width(twd);
							$t.p._nvtd[0] = twd;
						}
						$t.p._nvtd[1] = twd;
					}
					tdw = null;
					twd = null;
					navtbl = null;
				}
			}
			);
		},
		navButtonAdd	: function(elem, p) {
			p = $.extend({
				caption			: "newButton",
				title			: '',
				buttonicon		: 'ui-icon-newwin',
				onClickButton	: null,
				position		: "last",
				cursor			: 'pointer'
			}, p || {});
			return this.each(function() {
				if (!this.grid) {
					return;
				}
				if (elem.indexOf("#") !== 0) {
					elem = "#" + elem;
				}
				var findnav = $(".navtable", elem)[0], $t = this;
				if (findnav) {
					var tbd = $("<td></td>");
					if (p.buttonicon.toString().toUpperCase() == "NONE") {
						$(tbd).addClass('ui-pg-button ui-corner-all')
						        .append("<div class='ui-pg-div'>" + p.caption
						                + "</div>");
					} else {
						$(tbd)
						        .addClass('ui-pg-button ui-corner-all')
						        .append("<div class='ui-pg-div'><span class='ui-icon "
						                + p.buttonicon
						                + "'></span>"
						                + p.caption + "</div>");
					}
					if (p.id) {
						$(tbd).attr("id", p.id);
					}
					if (p.position == 'first') {
						if (findnav.rows[0].cells.length === 0) {
							$("tr", findnav).append(tbd);
						} else {
							$("tr td:eq(0)", findnav).before(tbd);
						}
					} else {
						$("tr", findnav).append(tbd);
					}
					$(tbd, findnav).attr("title", p.title || "").click(
					function(e) {
						if (!$(this).hasClass('ui-state-disabled')) {
							if ($.isFunction(p.onClickButton)) {
								p.onClickButton.call($t, e);
							}
						}
						return false;
					}
					).hover(function() {
						if (!$(this).hasClass('ui-state-disabled')) {
							$(this).addClass('ui-state-hover');
						}
					}, function() {
						$(this).removeClass("ui-state-hover");
					});
				}
			}
			);
		}
	}
	);
})(jQuery);

/** begin of fmatter * */
;
(function($) {
	$.fmatter = {};
	// opts can be id:row id for the row, rowdata:the data for the row,
	// colmodel:the column model for this column
	// example {id:1234,}
	$.extend($.fmatter, {
		isBoolean	: function(o) {
			return typeof o === 'boolean';
		},
		isObject	: function(o) {
			return (o && (typeof o === 'object' || $.isFunction(o))) || false;
		},
		isString	: function(o) {
			return typeof o === 'string';
		},
		isNumber	: function(o) {
			return typeof o === 'number' && isFinite(o);
		},
		isNull		: function(o) {
			return o === null;
		},
		isUndefined	: function(o) {
			return typeof o === 'undefined';
		},
		isValue		: function(o) {
			return (this.isObject(o) || this.isString(o) || this.isNumber(o) || this
			        .isBoolean(o));
		},
		isEmpty		: function(o) {
			if (!this.isString(o) && this.isValue(o)) {
				return false;
			} else if (!this.isValue(o)) {
				return true;
			}
			o = $.trim(o).replace(/\&nbsp\;/ig, '').replace(/\&#160\;/ig, '');
			return o === "";
		}
	}
	);
	$.fn.fmatter = function(formatType, cellval, opts, rwd, act) {
		// build main options before element iteration
		var v = cellval;
		opts = $.extend({}, $.jgrid.formatter, opts);
		
		if ($.fn.fmatter[formatType]) {
			v = $.fn.fmatter[formatType](cellval, opts, rwd, act);
		}
		return v;
	};
	$.fmatter.util = {
		// Taken from YAHOO utils
		NumberFormat	: function(nData, opts) {
			if (!$.fmatter.isNumber(nData)) {
				nData *= 1;
			}
			if ($.fmatter.isNumber(nData)) {
				var bNegative = (nData < 0);
				var sOutput = nData + "";
				var sDecimalSeparator = (opts.decimalSeparator)
				        ? opts.decimalSeparator
				        : ".";
				var nDotIndex;
				if ($.fmatter.isNumber(opts.decimalPlaces)) {
					// Round to the correct decimal place
					var nDecimalPlaces = opts.decimalPlaces;
					var nDecimal = Math.pow(10, nDecimalPlaces);
					sOutput = Math.round(nData * nDecimal) / nDecimal + "";
					nDotIndex = sOutput.lastIndexOf(".");
					if (nDecimalPlaces > 0) {
						// Add the decimal separator
						if (nDotIndex < 0) {
							sOutput += sDecimalSeparator;
							nDotIndex = sOutput.length - 1;
						}
						// Replace the "."
						else if (sDecimalSeparator !== ".") {
							sOutput = sOutput.replace(".", sDecimalSeparator);
						}
						// Add missing zeros
						while ((sOutput.length - 1 - nDotIndex) < nDecimalPlaces) {
							sOutput += "0";
						}
					}
				}
				if (opts.thousandsSeparator) {
					var sThousandsSeparator = opts.thousandsSeparator;
					nDotIndex = sOutput.lastIndexOf(sDecimalSeparator);
					nDotIndex = (nDotIndex > -1) ? nDotIndex : sOutput.length;
					var sNewOutput = sOutput.substring(nDotIndex);
					var nCount = -1;
					for (var i = nDotIndex; i > 0; i--) {
						nCount++;
						if ((nCount % 3 === 0) && (i !== nDotIndex)
						        && (!bNegative || (i > 1))) {
							sNewOutput = sThousandsSeparator + sNewOutput;
						}
						sNewOutput = sOutput.charAt(i - 1) + sNewOutput;
					}
					sOutput = sNewOutput;
				}
				// Prepend prefix
				sOutput = (opts.prefix) ? opts.prefix + sOutput : sOutput;
				// Append suffix
				sOutput = (opts.suffix) ? sOutput + opts.suffix : sOutput;
				return sOutput;
				
			} else {
				return nData;
			}
		},
		// Tony Tomov
		// PHP implementation. Sorry not all options are supported.
		// Feel free to add them if you want
		DateFormat		: function(format, date, newformat, opts) {
			var token = /\\.|[dDjlNSwzWFmMntLoYyaABgGhHisueIOPTZcrU]/g, timezone = /\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g, timezoneClip = /[^-+\dA-Z]/g, msDateRegExp = new RegExp("^/Date\((([-+])?[0-9]+)(([-+])([0-9]{2})([0-9]{2}))?\)/$"), msMatch = date
			        .match(msDateRegExp), pad = function(value, length) {
				value = String(value);
				length = parseInt(length, 10) || 2;
				while (value.length < length) {
					value = '0' + value;
				}
				return value;
			}, ts = {
				m	: 1,
				d	: 1,
				y	: 1970,
				h	: 0,
				i	: 0,
				s	: 0,
				u	: 0
			}, timestamp = 0, dM, k, hl, dateFormat = [
				"i18n"
			];
			// Internationalization strings
			dateFormat.i18n = {
				dayNames	: opts.dayNames,
				monthNames	: opts.monthNames
			};
			if (format in opts.masks) {
				format = opts.masks[format];
			}
			if (date.constructor === Number) {
				timestamp = new Date(date);
			} else if (date.constructor === Date) {
				timestamp = date;
				// Microsoft date format support
			} else if (msMatch !== null) {
				timestamp = new Date(parseInt(msMatch[1], 10));
				if (msMatch[3]) {
					var offset = Number(msMatch[5]) * 60 + Number(msMatch[6]);
					offset *= ((msMatch[4] == '-') ? 1 : -1);
					offset -= timestamp.getTimezoneOffset();
					timestamp.setTime(Number(Number(timestamp)
					        + (offset * 60 * 1000)));
				}
			} else {
				date = date.split(/[\\\/:_;.,\t\T\s-]/);
				format = format.split(/[\\\/:_;.,\t\T\s-]/);
				// parsing for month names
				for (k = 0, hl = format.length; k < hl; k++) {
					if (format[k] == 'M') {
						dM = $.inArray(date[k], dateFormat.i18n.monthNames);
						if (dM !== -1 && dM < 12) {
							date[k] = dM + 1;
						}
					}
					if (format[k] == 'F') {
						dM = $.inArray(date[k], dateFormat.i18n.monthNames);
						if (dM !== -1 && dM > 11) {
							date[k] = dM + 1 - 12;
						}
					}
					if (date[k]) {
						ts[format[k].toLowerCase()] = parseInt(date[k], 10);
					}
				}
				if (ts.f) {
					ts.m = ts.f;
				}
				if (ts.m === 0 && ts.y === 0 && ts.d === 0) {
					return "&#160;";
				}
				ts.m = parseInt(ts.m, 10) - 1;
				var ty = ts.y;
				if (ty >= 70 && ty <= 99) {
					ts.y = 1900 + ts.y;
				} else if (ty >= 0 && ty <= 69) {
					ts.y = 2000 + ts.y;
				}
				timestamp = new Date(ts.y, ts.m, ts.d, ts.h, ts.i, ts.s, ts.u);
			}
			
			if (newformat in opts.masks) {
				newformat = opts.masks[newformat];
			} else if (!newformat) {
				newformat = 'Y-m-d';
			}
			var G = timestamp.getHours(), i = timestamp.getMinutes(), j = timestamp
			        .getDate(), n = timestamp.getMonth() + 1, o = timestamp
			        .getTimezoneOffset(), s = timestamp.getSeconds(), u = timestamp
			        .getMilliseconds(), w = timestamp.getDay(), Y = timestamp
			        .getFullYear(), N = (w + 6) % 7 + 1, z = (new Date(Y,
			        n - 1, j
			) - new Date(Y, 0, 1))
			        / 86400000, flags = {
				// Day
				d	: pad(j),
				D	: dateFormat.i18n.dayNames[w],
				j	: j,
				l	: dateFormat.i18n.dayNames[w + 7],
				N	: N,
				S	: opts.S(j),
				// j < 11 || j > 13 ? ['st', 'nd', 'rd', 'th'][Math.min((j - 1)
				// % 10, 3)] : 'th',
				w	: w,
				z	: z,
				// Week
				W	: N < 5 ? Math.floor((z + N - 1) / 7) + 1 : Math.floor((z
				        + N - 1)
				        / 7)
				        || ((new Date(Y - 1, 0, 1).getDay() + 6) % 7 < 4
				                ? 53
				                : 52),
				// Month
				F	: dateFormat.i18n.monthNames[n - 1 + 12],
				m	: pad(n),
				M	: dateFormat.i18n.monthNames[n - 1],
				n	: n,
				t	: '?',
				// Year
				L	: '?',
				o	: '?',
				Y	: Y,
				y	: String(Y).substring(2),
				// Time
				a	: G < 12 ? opts.AmPm[0] : opts.AmPm[1],
				A	: G < 12 ? opts.AmPm[2] : opts.AmPm[3],
				B	: '?',
				g	: G % 12 || 12,
				G	: G,
				h	: pad(G % 12 || 12),
				H	: pad(G),
				i	: pad(i),
				s	: pad(s),
				u	: u,
				// Timezone
				e	: '?',
				I	: '?',
				O	: (o > 0 ? "-" : "+")
				        + pad(	Math.floor(Math.abs(o) / 60) * 100
				                        + Math.abs(o) % 60, 4),
				P	: '?',
				T	: (String(timestamp).match(timezone) || [
					""
				]).pop().replace(timezoneClip, ""),
				Z	: '?',
				// Full Date/Time
				c	: '?',
				r	: '?',
				U	: Math.floor(timestamp / 1000)
			};
			return newformat.replace(token, function($0) {
				return $0 in flags ? flags[$0] : $0.substring(1);
			});
		}
	};
	$.fn.fmatter.defaultFormat = function(cellval, opts) {
		return ($.fmatter.isValue(cellval) && cellval !== "")
		        ? cellval
		        : opts.defaultValue ? opts.defaultValue : "&#160;";
	};
	$.fn.fmatter.email = function(cellval, opts) {
		if (!$.fmatter.isEmpty(cellval)) {
			return "<a href=\"mailto:" + cellval + "\">" + cellval + "</a>";
		} else {
			return $.fn.fmatter.defaultFormat(cellval, opts);
		}
	};
	$.fn.fmatter.checkbox = function(cval, opts) {
		var op = $.extend({}, opts.checkbox), ds;
		if (!$.fmatter.isUndefined(opts.colModel.formatoptions)) {
			op = $.extend({}, op, opts.colModel.formatoptions);
		}
		if (op.disabled === true) {
			ds = "disabled=\"disabled\"";
		} else {
			ds = "";
		}
		if ($.fmatter.isEmpty(cval) || $.fmatter.isUndefined(cval)) {
			cval = $.fn.fmatter.defaultFormat(cval, op);
		}
		cval = cval + "";
		cval = cval.toLowerCase();
		var bchk = cval.search(/(false|0|no|off)/i) < 0
		        ? " checked='checked' "
		        : "";
		return "<input type=\"checkbox\" " + bchk + " value=\"" + cval
		        + "\" offval=\"no\" " + ds + "/>";
	};
	$.fn.fmatter.link = function(cellval, opts) {
		var op = {
			target	: opts.target
		};
		var target = "";
		if (!$.fmatter.isUndefined(opts.colModel.formatoptions)) {
			op = $.extend({}, op, opts.colModel.formatoptions);
		}
		if (op.target) {
			target = 'target=' + op.target;
		}
		if (!$.fmatter.isEmpty(cellval)) {
			return "<a " + target + " href=\"" + cellval + "\">" + cellval
			        + "</a>";
		} else {
			return $.fn.fmatter.defaultFormat(cellval, opts);
		}
	};
	$.fn.fmatter.showlink = function(cellval, opts) {
		var op = {
			baseLinkUrl	: opts.baseLinkUrl,
			showAction	: opts.showAction,
			addParam	: opts.addParam || "",
			target		: opts.target,
			idName		: opts.idName
		}, target = "", idUrl;
		if (!$.fmatter.isUndefined(opts.colModel.formatoptions)) {
			op = $.extend({}, op, opts.colModel.formatoptions);
		}
		if (op.target) {
			target = 'target=' + op.target;
		}
		idUrl = op.baseLinkUrl + op.showAction + '?' + op.idName + '='
		        + opts.rowId + op.addParam;
		if ($.fmatter.isString(cellval) || $.fmatter.isNumber(cellval)) { // add
																			// this
																			// one
																			// even
																			// if
																			// its
																			// blank
																			// string
			return "<a " + target + " href=\"" + idUrl + "\">" + cellval
			        + "</a>";
		} else {
			return $.fn.fmatter.defaultFormat(cellval, opts);
		}
	};
	$.fn.fmatter.integer = function(cellval, opts) {
		var op = $.extend({}, opts.integer);
		if (!$.fmatter.isUndefined(opts.colModel.formatoptions)) {
			op = $.extend({}, op, opts.colModel.formatoptions);
		}
		if ($.fmatter.isEmpty(cellval)) {
			return op.defaultValue;
		}
		return $.fmatter.util.NumberFormat(cellval, op);
	};
	$.fn.fmatter.number = function(cellval, opts) {
		var op = $.extend({}, opts.number);
		if (!$.fmatter.isUndefined(opts.colModel.formatoptions)) {
			op = $.extend({}, op, opts.colModel.formatoptions);
		}
		if ($.fmatter.isEmpty(cellval)) {
			return op.defaultValue;
		}
		return $.fmatter.util.NumberFormat(cellval, op);
	};
	$.fn.fmatter.currency = function(cellval, opts) {
		var op = $.extend({}, opts.currency);
		if (!$.fmatter.isUndefined(opts.colModel.formatoptions)) {
			op = $.extend({}, op, opts.colModel.formatoptions);
		}
		if ($.fmatter.isEmpty(cellval)) {
			return op.defaultValue;
		}
		return $.fmatter.util.NumberFormat(cellval, op);
	};
	$.fn.fmatter.date = function(cellval, opts, rwd, act) {
		var op = $.extend({}, opts.date);
		if (!$.fmatter.isUndefined(opts.colModel.formatoptions)) {
			op = $.extend({}, op, opts.colModel.formatoptions);
		}
		if (!op.reformatAfterEdit && act == 'edit') {
			return $.fn.fmatter.defaultFormat(cellval, opts);
		} else if (!$.fmatter.isEmpty(cellval)) {
			return $.fmatter.util.DateFormat(op.srcformat, cellval,
			        op.newformat, op
			);
		} else {
			return $.fn.fmatter.defaultFormat(cellval, opts);
		}
	};
	$.fn.fmatter.select = function(cellval, opts, rwd, act) {
		// jqGrid specific
		cellval = cellval + "";
		var oSelect = false, ret = [];
		if (!$.fmatter.isUndefined(opts.colModel.formatoptions)) {
			oSelect = opts.colModel.formatoptions.value;
		} else if (!$.fmatter.isUndefined(opts.colModel.editoptions)) {
			oSelect = opts.colModel.editoptions.value;
		}
		if (oSelect) {
			var msl = opts.colModel.editoptions.multiple === true
			        ? true
			        : false, scell = [], sv;
			if (msl) {
				scell = cellval.split(",");
				scell = $.map(scell, function(n) {
					return $.trim(n);
				});
			}
			if ($.fmatter.isString(oSelect)) {
				// mybe here we can use some caching with care ????
				var so = oSelect.split(";"), j = 0;
				for (var i = 0; i < so.length; i++) {
					sv = so[i].split(":");
					if (sv.length > 2) {
						sv[1] = jQuery.map(sv, function(n, i) {
							if (i > 0) {
								return n;
							}
						}).join(":");
					}
					if (msl) {
						if (jQuery.inArray(sv[0], scell) > -1) {
							ret[j] = sv[1];
							j++;
						}
					} else if ($.trim(sv[0]) == $.trim(cellval)) {
						ret[0] = sv[1];
						break;
					}
				}
			} else if ($.fmatter.isObject(oSelect)) {
				// this is quicker
				if (msl) {
					ret = jQuery.map(scell, function(n, i) {
						return oSelect[n];
					});
				} else {
					ret[0] = oSelect[cellval] || "";
				}
			}
		}
		cellval = ret.join(", ");
		return cellval === ""
		        ? $.fn.fmatter.defaultFormat(cellval, opts)
		        : cellval;
	};
	$.fn.fmatter.rowactions = function(rid, gid, act, pos) {
		var op = {
			keys			: false,
			editbutton		: true,
			delbutton		: true,
			onEdit			: null,
			onSuccess		: null,
			afterSave		: null,
			onError			: null,
			afterRestore	: null,
			extraparam		: {
				oper	: 'edit'
			},
			url			 : null,
			delOptions		: {}
		}, cm = $('#' + gid)[0].p.colModel[pos];
		if (!$.fmatter.isUndefined(cm.formatoptions)) {
			op = $.extend(op, cm.formatoptions);
		}
		var saverow = function(rowid) {
			if (op.afterSave)
				op.afterSave(rowid);
			$(
			        "tr#" + rid + " div.ui-inline-edit, " + "tr#" + rid
			                + " div.ui-inline-del", "#" + gid
			).show();
			$(
			        "tr#" + rid + " div.ui-inline-save, " + "tr#" + rid
			                + " div.ui-inline-cancel", "#" + gid
			).hide();
		}, restorerow = function(rowid) {
			if (op.afterRestore)
				op.afterRestore(rowid);
			$(
			        "tr#" + rid + " div.ui-inline-edit, " + "tr#" + rid
			                + " div.ui-inline-del", "#" + gid
			).show();
			$(
			        "tr#" + rid + " div.ui-inline-save, " + "tr#" + rid
			                + " div.ui-inline-cancel", "#" + gid
			).hide();
		};
		
		switch (act) {
			case 'edit' :
				$('#' + gid).jqGrid('editRow', rid, op.keys, op.onEdit,
				        op.onSuccess, op.url, op.extraparam, saverow,
				        op.onError, restorerow
				);
				$(
				        "tr#" + rid + " div.ui-inline-edit, " + "tr#" + rid
				                + " div.ui-inline-del", "#" + gid
				).hide();
				$(
				        "tr#" + rid + " div.ui-inline-save, " + "tr#" + rid
				                + " div.ui-inline-cancel", "#" + gid
				).show();
				break;
			case 'save' :
				$('#' + gid).jqGrid('saveRow', rid, op.onSuccess, op.url,
				        op.extraparam, saverow, op.onError, restorerow
				);
				$(
				        "tr#" + rid + " div.ui-inline-edit, " + "tr#" + rid
				                + " div.ui-inline-del", "#" + gid
				).show();
				$(
				        "tr#" + rid + " div.ui-inline-save, " + "tr#" + rid
				                + " div.ui-inline-cancel", "#" + gid
				).hide();
				break;
			case 'cancel' :
				$('#' + gid).jqGrid('restoreRow', rid, restorerow);
				$(
				        "tr#" + rid + " div.ui-inline-edit, " + "tr#" + rid
				                + " div.ui-inline-del", "#" + gid
				).show();
				$(
				        "tr#" + rid + " div.ui-inline-save, " + "tr#" + rid
				                + " div.ui-inline-cancel", "#" + gid
				).hide();
				break;
			case 'del' :
				$('#' + gid).jqGrid('delGridRow', rid, op.delOptions);
				break;
		}
	};
	$.fn.fmatter.actions = function(cellval, opts, rwd) {
		var op = {
			keys		: false,
			editbutton	: true,
			delbutton	: true
		};
		if (!$.fmatter.isUndefined(opts.colModel.formatoptions)) {
			op = $.extend(op, opts.colModel.formatoptions);
		}
		var rowid = opts.rowId, str = "", ocl;
		if (typeof(rowid) == 'undefined' || $.fmatter.isEmpty(rowid)) {
			return "";
		}
		if (op.editbutton) {
			ocl = "onclick=$.fn.fmatter.rowactions('" + rowid + "','"
			        + opts.gid + "','edit'," + opts.pos + ");";
			str = str
			        + "<div style='margin-left:8px;'><div title='"
			        + $.jgrid.nav.edittitle
			        + "' style='float:left;cursor:pointer;' class='ui-pg-div ui-inline-edit' "
			        + ocl
			        + "><span class='ui-icon ui-icon-pencil'></span></div>";
		}
		if (op.delbutton) {
			ocl = "onclick=$.fn.fmatter.rowactions('" + rowid + "','"
			        + opts.gid + "','del'," + opts.pos + ");";
			str = str
			        + "<div title='"
			        + $.jgrid.nav.deltitle
			        + "' style='float:left;margin-left:5px;' class='ui-pg-div ui-inline-del' "
			        + ocl
			        + "><span class='ui-icon ui-icon-trash'></span></div>";
		}
		ocl = "onclick=$.fn.fmatter.rowactions('" + rowid + "','" + opts.gid
		        + "','save'," + opts.pos + ");";
		str = str
		        + "<div title='"
		        + $.jgrid.edit.bSubmit
		        + "' style='float:left;display:none' class='ui-pg-div ui-inline-save'><span class='ui-icon ui-icon-disk' "
		        + ocl + "></span></div>";
		ocl = "onclick=$.fn.fmatter.rowactions('" + rowid + "','" + opts.gid
		        + "','cancel'," + opts.pos + ");";
		str = str
		        + "<div title='"
		        + $.jgrid.edit.bCancel
		        + "' style='float:left;display:none;margin-left:5px;' class='ui-pg-div ui-inline-cancel'><span class='ui-icon ui-icon-cancel' "
		        + ocl + "></span></div></div>";
		return str;
	};
	$.unformat = function(cellval, options, pos, cnt) {
		// specific for jqGrid only
		var ret, formatType = options.colModel.formatter, op = options.colModel.formatoptions
		        || {}, sep, re = /([\.\*\_\'\(\)\{\}\+\?\\])/g, unformatFunc = options.colModel.unformat
		        || ($.fn.fmatter[formatType] && $.fn.fmatter[formatType].unformat);
		if (typeof unformatFunc !== 'undefined' && $.isFunction(unformatFunc)) {
			ret = unformatFunc($(cellval).text(), options, cellval);
		} else if (!$.fmatter.isUndefined(formatType)
		        && $.fmatter.isString(formatType)) {
			var opts = $.jgrid.formatter || {}, stripTag;
			switch (formatType) {
				case 'integer' :
					op = $.extend({}, opts.integer, op);
					sep = op.thousandsSeparator.replace(re, "\\$1");
					stripTag = new RegExp(sep, "g");
					ret = $(cellval).text().replace(stripTag, '');
					break;
				case 'number' :
					op = $.extend({}, opts.number, op);
					sep = op.thousandsSeparator.replace(re, "\\$1");
					stripTag = new RegExp(sep, "g");
					ret = $(cellval).text().replace(stripTag, "").replace(
					        op.decimalSeparator, '.'
					);
					break;
				case 'currency' :
					op = $.extend({}, opts.currency, op);
					sep = op.thousandsSeparator.replace(re, "\\$1");
					stripTag = new RegExp(sep, "g");
					ret = $(cellval).text().replace(stripTag, '').replace(
					        op.decimalSeparator, '.'
					).replace(op.prefix, '').replace(op.suffix, '');
					break;
				case 'checkbox' :
					var cbv = (options.colModel.editoptions)
					        ? options.colModel.editoptions.value.split(":")
					        : [
					                "Yes", "No"
					        ];
					ret = $('input', cellval).attr("checked") ? cbv[0] : cbv[1];
					break;
				case 'select' :
					ret = $.unformat.select(cellval, options, pos, cnt);
					break;
				case 'actions' :
					return "";
				default :
					ret = $(cellval).text();
			}
		}
		return ret ? ret : cnt === true ? $(cellval).text() : $.jgrid
		        .htmlDecode($(cellval).html());
	};
	$.unformat.select = function(cellval, options, pos, cnt) {
		// Spacial case when we have local data and perform a sort
		// cnt is set to true only in sortDataArray
		var ret = [];
		var cell = $(cellval).text();
		if (cnt === true) {
			return cell;
		}
		var op = $.extend({}, options.colModel.editoptions);
		if (op.value) {
			var oSelect = op.value, msl = op.multiple === true ? true : false, scell = [], sv;
			if (msl) {
				scell = cell.split(",");
				scell = $.map(scell, function(n) {
					return $.trim(n);
				});
			}
			if ($.fmatter.isString(oSelect)) {
				var so = oSelect.split(";"), j = 0;
				for (var i = 0; i < so.length; i++) {
					sv = so[i].split(":");
					if (sv.length > 2) {
						sv[1] = jQuery.map(sv, function(n, i) {
							if (i > 0) {
								return n;
							}
						}).join(":");
					}
					if (msl) {
						if (jQuery.inArray(sv[1], scell) > -1) {
							ret[j] = sv[0];
							j++;
						}
					} else if ($.trim(sv[1]) == $.trim(cell)) {
						ret[0] = sv[0];
						break;
					}
				}
			} else if ($.fmatter.isObject(oSelect) || $.isArray(oSelect)) {
				if (!msl) {
					scell[0] = cell;
				}
				ret = jQuery.map(scell, function(n) {
					var rv;
					$.each(oSelect, function(i, val) {
						if (val == n) {
							rv = i;
							return false;
						}
					});
					if (typeof(rv) != 'undefined') {
						return rv;
					}
				});
			}
			return ret.join(", ");
		} else {
			return cell || "";
		}
	};
	$.unformat.date = function(cellval, opts) {
		var op = $.jgrid.formatter.date || {};
		if (!$.fmatter.isUndefined(opts.formatoptions)) {
			op = $.extend({}, op, opts.formatoptions);
		}
		if (!$.fmatter.isEmpty(cellval)) {
			return $.fmatter.util.DateFormat(op.newformat, cellval,
			        op.srcformat, op
			);
		} else {
			return $.fn.fmatter.defaultFormat(cellval, opts);
		}
	};
})(jQuery);
/** end of fmatter * */
