window.jauth_onload = function(){
	loadTree();
};
function loadTree(){
	var mySimpleTree;
	var _url = $.ctx+"/api/organization/renderOrgTree?isAsynchron=true";
	$.commAjax({
		url : _url,
		isShowMask : false,
		type : 'POST',
		onSuccess : function(data) {
			$("li.root").append(data);
			mySimpleTree = $('#tree').simpleTree({
				autoclose:false,
				afterClick:function(thi){
					detail($(thi).attr('id'));
				}
			});
		}
	});
}
function detail(orgCode){
	$("#detail").attr("src",$.ctx+"/admin/pages/orgmgr/org_info.html?orgCode=" + orgCode);
}