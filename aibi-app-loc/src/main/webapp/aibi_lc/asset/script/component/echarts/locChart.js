var locChart = {
	// 饼图数值和百分比的显示
	itemStyle: {
        normal:{ 
          label:{ 
            show: false, 
            formatter: '{b} : {c} ({d}%)',
            position:'inside',
            textStyle:{color:'#000000'}
          }, 
          labelLine :{show:false} 
        } 
    },
	// 统计图图例样式
	legenStyle : { 
		itemGap:40,
		borderColor:"#000000",
		itemHeight:10,
		itemWidth:10,
		textStyle:{
			color:"#000000",
			fontWeight:'normal',
			fontSize:12
		}
	},
	color : [
	                "#00CD00",
	                "#FFA500",
	                "#6959CD",
	                "#FF6347",
	                "#1874CD",
	                "#00688B"
	            ],
	// 统计图布局样式
	grid: {
        left: "3%",
        right: "4%",
        bottom: "12%",
        top: "8%",
        containLabel: true
    },
	// 轴样式
	axisStyle : {
		color:"#000000",
		fontSize:12
	},
	chartMap:{},
	elementMap:{},
	e:{},
	init:function(element,chartId) { // optSetting：初始化option
		var chart = echarts.init(element);
		e = chart;
		 
		if(chartId){ //如果传入图表ID，则缓存起来供之后调用
			locChart.chartMap[chartId] = e;
			locChart.elementMap[chartId] = element;
		}
	   	return this;
	},
	//add by zhougz 轮循
	reloadChart : function(chartId){
		try{
			var ele = locChart.elementMap[chartId];
			var o = locChart.chartMap[chartId].getOption();
			var ne = echarts.init(ele);
			ne.setOption(o);
			return true;
		}catch(e){
			return false;
		}
	},
	setOption:function(option) {
		if(typeof e != "undefined") {
			
			option.color = locChart.color;
			
			
			// 终端定制，折线图折点放大。
			if(option.series && option.series[0].type == "line"){
				for(var i = 0; i < option.series.length; i++) {
					option.series[i].symbolSize = 15;
				}
				option.series[0].itemStyle = locChart.itemStyle;
			}
			
			// x轴样式
			if(option.xAxis){ 
				option.xAxis[0].axisLabel = {'textStyle':locChart.axisStyle,'interval':'0','rotate':25};
				option.xAxis[0].splitLine = {show:false};
				option.xAxis[0].axisLine = {lineStyle:{color:'#3CB371',width:1}};
				// 终端使用，横轴为时间段则自适应宽度
				if(option.xAxis[0].data != undefined && 
						option.xAxis[0].data.length != 0 && 
						option.xAxis[0].data[0].length == 10 &&
						option.xAxis[0].data[0].indexOf('-') == 4 && 
						option.xAxis[0].data[0].lastIndexOf('-') == 7) {
					option.xAxis[0].axisLabel.interval = 'auto';
					option.xAxis[0].axisLabel.rotate = 0;
					for(var i = 0; i < option.xAxis[0].data.length; i++) {
						option.xAxis[0].data[i] = option.xAxis[0].data[i].substring(5);
					}
				}
			}
			// y轴样式
			if(option.yAxis){ 
				for(var i = 0; i < option.yAxis.length; i++) {
					option.yAxis[i].axisLabel = {'textStyle':locChart.axisStyle};
				}
			}
			// 终端定制，去掉指定的legend
			if(option.legendShow != undefined) {
				delete option.legend;
				option.series[0].radius = '40%';
			}
			// 图例样式 
			if(option.legend){ 
				option.legend.textStyle = locChart.legenStyle.textStyle;
				option.legend.itemWidth = locChart.legenStyle.itemWidth;
				option.legend.itemHeight = locChart.legenStyle.itemHeight;
			}
			
			e.setOption(option);
		}
	},
	getOption:function(){
		return e.getOption();
	},
};






/**
 * 对外提供接口
 * @param chartId 图表ID
 * @param options 维度参数{
 * 	DIMA:'',
 *  DIMB:''
 * }
 * @date 20160604
 */
$.fn.locChart = function(options){
		var _self = this;
	   var $self = $(_self);
	   $self.html("<div style='text-align:center;color:rgba(123,123,123,.5);'>暂无数据</div>");
	   var echart = locChart.init($self[0],"TODOchartId");
	   echart.setOption(options);
	   return echart;
}
