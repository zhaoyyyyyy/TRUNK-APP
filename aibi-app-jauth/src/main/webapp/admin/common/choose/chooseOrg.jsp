<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
		<jsp:include page="/admin/common/includes.jsp"></jsp:include>
		<title>组织机构</title>
		<script src="OrgType.js" type="text/javascript"></script>
		<script type="text/javascript">
		
 		$(function(){
			var mySimpleTree;
			
			var dg = frameElement.lhgDG;
			dg.removeBtn();
			
			var rootOrgCode = ORG_CODE_ROOT;
			if (dg.params && dg.params.orgCode) {
				rootOrgCode = dg.params.orgCode;
			}
			
			var treeType = null;
			if (dg.params && dg.params.treeType) {
				treeType = dg.params.treeType;
			}
			
			var _url = $.ctx+"/system/organization!renderOrgTree.action?orgCode=" + rootOrgCode +(treeType?'&treeType='+treeType:'');
			if (dg.params && dg.params.type) {
				_url += "&type=" + dg.params.type;
			}
			if (dg.params && !$.isNull(dg.params.selectable)) {
				_url += "&selectable=" + dg.params.selectable;
			}
			if (dg.params && dg.params.isNeedAll) {
				_url += "&isNeedAll=true";
			}
			
			var cascadeParentChecked = true;
			var cascadeChildrenChecked = true;
			if (dg.params && dg.params.cascadeParentChecked=="false") {
				cascadeParentChecked = false;
			}
			if (dg.params && dg.params.cascadeChildrenChecked=="false") {
				cascadeChildrenChecked = false;
			}
			var filterOrgType = null;
			if (dg.params && dg.params.filterOrgType) {
				filterOrgType = {};
				$.each(dg.params.filterOrgType.split(","),function(){
					filterOrgType[this]=true;
				});
			}
			
			$.commAjax({
	    		url			:	_url+"&isAsynchron=true",
	    		isShowMask	:	false,
	    		type		: 	'POST',
				onSuccess	: function(data){
					$("li.root").append(data);
					mySimpleTree = $('#tree').simpleTree({
						autoclose: true,
						nodeCheckBox : dg.params && dg.params.selectable,
						cascadeParentChecked : cascadeParentChecked,
						cascadeChildrenChecked : cascadeChildrenChecked
					});
				},
				onFailure   :function(){},
				maskMassage	:'数据加载中...'
	    	});
			
			//确定按钮
		    dg.addBtn("confirm", "确定", function() {
		    	var valObj = mySimpleTree[0].getSelectionsValue(function(obj){
		    		return !filterOrgType||filterOrgType[$(obj).attr("orgType")];
		    	});
		    	
		    	//add by xiajb 20120801，给回调函数增加当前节点和父节点参数
		    	var callbackParams = {
		    		sel:valObj,
		    		parent:mySimpleTree[0].getSelectedParent()
		    	};
		    	
		    	if($.isNull(valObj.value)){
		    		$.alert("请先选择单位。");
		    	}else if (dg.callback) {
					dg.callback(valObj.value, valObj.text,callbackParams);
					dg.cancel();
		    	}
		    });
		    
		    //取消按钮
		    dg.addBtn("cancel", "取消", function() {
		    	dg.cancel();
		    });
		});
		
 	</script>
	</head>
	<body style="height: 415px; overflow: auto;">
		<ul class="simpleTree" id="tree" style="margin-left: 5px; margin-top: 5px;" >	
			<li class="root">
				<span class='text' id="root">资源平台</span>
			</li>
		</ul>
		
	
	</body>
</html>