$(function(){
	var result = {
			data :[
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
	 			        	        	               }
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
			 ]
	}
	$('#commonFilterBox').filterChoice({
		"data":result.data,//
		"selectedUl":"#selectedUlDemo",//存放已选择条件的ul对象-默认id应设置为'selectedUl',但用户可根据需要更改成自己的id(如'selectedUlDemo')
		"filterBox":"#filterBox",//存放筛选条件的div对象-默认id应设置为它,但用户可根据需要更改成自己的id
	});
})