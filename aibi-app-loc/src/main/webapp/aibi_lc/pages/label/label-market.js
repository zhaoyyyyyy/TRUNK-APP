/*!
 *标签集市
 * Date: 2017-12-04 
 */
/**初始化*/
$(function(){
		
	//专区
	$("#labelMktHeader").preConfig({
		url: $.ctx + "/api/prefecture/preConfigInfo/queryList",
	 	type:"post",
	 	create:function(data){
	 		labelMarket.loadLabelCategoryList({
				sysId:data.configId
			});//获取标签体系
	 	},
	 	select:function(ui){
	 		labelMarket.loadLabelCategoryList({
				sysId:ui.attr("configId")
			});//获取标签体系
	 	}
	});
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	$( "#sortable > .ui-conditionCT,.ui-calc-h3>span>em" ).draggable({
		helper: function( event ) {
			if($(event.target).hasClass("J-drag-bracket")){
       	 	   return $(event.target).attr("data-attr") == "left"?$( '<span class="ui-bracket left">(</span>' ):$( '<span class="ui-bracket left">)</span>' );
			}
       	  return $( '<h4 class="ui-conditionCT-h4 ui-conditionCT-h4-helper">2G数据流量<em></em><i></i></h4>' );
    	},
    	cursor: "crosshair",
    	start:function(event,ui){
      	  $(".ui-chaining").hide();
      	  var items = $("#sortable > .ui-conditionCT");
      	  var calc= '<div class="ui-state-highlight ui-sortablr-helper J-helper"></div>';
  		  $(items).after(calc);
  		  if($("#sortable > .ui-bracket.left").prev().length == 0){
	      	  $("#sortable > .ui-bracket.left").before(calc);
  		  }
      	  $("#sortable > .ui-bracket").after(calc);
  		  $(".J-helper").droppable({
	  	  	   hoverClass: "ui-drop-highlight",
	  		   greedy:true,
	  		   drop: function( event, ui ) {
	  			   var onDragTag=ui.draggable;
	  			   if(onDragTag.hasClass("J-drag-bracket")){
	  				 onDragTag = onDragTag.attr("data-attr") == "left"?'<span class="ui-bracket left">(</span>':'<span class="ui-bracket right">)<i></i></span>'
	  				 $(this).after(onDragTag);
	  				 return;
	  			   }
	  		        $(this).after(onDragTag);
	  		        var chains = $("#sortable > .ui-chaining");
	  		     	var CTitems = $("#sortable > .ui-conditionCT");
	  		        for(var i =0,len = chains.length;i<len;i++){
	  		        		$(CTitems[i]).after(chains[i]);
	  		        }
	  	       },
	  	       create:function( event, ui){
	  		        console.log(ui);
	  	       }
	  	   });
        },
        stop:function(event,ui){
      	  $(".ui-chaining").show();
      	  $(".J-helper").remove();
        }
	});
	$(".ui-shop-cart").click(function(){
		$(".ui-calculate-center").addClass("heightAuto");
	});
	$(".J-min").click(function(){
		$(".ui-calculate-center").removeClass("heightAuto");
	});
	//弹出框
	/*$(".ui-dialog").dialog({
	      height: 515,
	      width: 560,
	      modal: true,
	      title:"新建/修改",
	      buttons: [
	    	    {
	    	      text: "取消",
	    	      "class":"ui-btn ui-btn-second",
	    	      click: function() {
	    	        $( this ).dialog( "close" );
	    	      }
    	        },
    	        {
	    	      text: "确定",
	    	      "class":"ui-btn ui-btn-default",
	    	      click: function() {
	    	        $( this ).dialog( "close" );
	    	      }
    	 
	    	      // Uncommenting the following line would hide the text,
	    	      // resulting in the label being used as a tooltip
	    	      //showText: false
	    	    }
    	  ]
    });*/
	
	
	
});














/**
 * ------------------------------------------------------------------
 * 标签集市
 * ------------------------------------------------------------------
 */
var labelMarket = (function (model){
        //开发版本号
        model.version = "1.0.0";
        model.author  = "wangsen3";
        model.email   = "wangsen3@asiainfo.com";

        /**
         * @description 获取标签体系
         * @param  option
         * @return  
         * ------------------------------------------------------------------
         */
        model.loadLabelCategoryList = function(option) { 
        		$.AIPost({
			  url: $.ctx + "/api/label/categoryInfo/queryList",
			  cache:false,
			  data:option,
			  success: function(returnObj){
			  	console.log(returnObj);
			  }
			});
        	
        	
        	
        }
        return model;
        

   })(window.labelMarket || {});