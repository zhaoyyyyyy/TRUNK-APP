window.loc_onload = function() {
	$("#mainGrid").jqGrid({
	        colNames : [ '手机号' ],
	        colModel : [ 
	                     {name : 'id',index : 'id',width : 60,align : "center"},
	                   ],
	      });
	  var mydata = [ 
	                 {id : "123456789"}, 
	                 {id : "223456789"}, 
	                 {id : "323456789"}, 
	                 {id : "423456789"}, 
	                 {id : "523456789"}, 
	                 {id : "623456789"}, 
	                 {id : "723456789"}, 
	                 {id : "823456789"}, 
	                 {id : "923456789"},
	                 {id : "923456789"} 
	               ];
	  for ( var i = 0; i <= mydata.length; i++){
	    $("#mainGrid").jqGrid('addRowData', i + 1, mydata[i]);
	};
	
	var wd = frameElement.lhgDG;
	wd.addBtn("cancel", "确定", function() {
		wd.cancel();
	});
}