window.loc_onload = function() {
	
	 
	 var wd = frameElement.lhgDG;
	 wd.addBtn('close', '关闭', wd.cancel);
	 
	 //TODO 这块要放在方法里
	var option = {
	    legend: {
	        data:['男女人数']
	    },
	    xAxis: [
	        {
	            type: 'category',
	            data: ['男','女','未知'],
	            axisPointer: {
	                type: 'shadow'
	            }
	        }
	    ],
	    yAxis: [
	        {
	            type: 'value',
	            name: '万人',
	            min: 0,
	            max: 250,
	            interval: 50,
	            axisLabel: {
	                formatter: '{value} 万人'
	            }
	        }
	    ],
	    series: [{
	            name:'性别',
	            type:'bar',
	            data:[103, 80, 20]
	     }]
	};

	
	$('#chart_1').locChart(option);
	$('#chart_2').locChart(option);
	$('#chart_3').locChart(option);
	$('#chart_4').locChart(option);
	$('#chart_5').locChart(option);
	$('#chart_6').locChart(option);
}