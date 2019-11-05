<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="fineui-icon" href="images/sailiicon.png">
	<link rel="shortcut icon" href="images/sailiicon.png" type="images/x-icon" />
	<link rel="icon" href="images/sailiicon.png" type="images/x-icon" />
<!--360浏览器优先以webkit内核解析-->
<title>Jeecg 微云快速开发平台</title>
<link rel="shortcut icon" href="images/sailiicon.png">
<link href="plug-in/hplus/css/bootstrap.min.css?v=3.3.6"
	rel="stylesheet">
<link href="plug-in/hplus/css/font-awesome.css?v=4.4.0" rel="stylesheet">
<link href="plug-in/hplus/css/animate.css" rel="stylesheet">
<link href="plug-in/hplus/css/style.css?v=4.1.0" rel="stylesheet">
<link rel="stylesheet" href="plug-in/themes/fineui/main/iconfont.css">
<script src="plug-in/laydate/laydate.js"></script>
<style type="text/css">
.gray-bg {
	background-color: #e9ecf3;
}

.col-sm-2 {
	width: 10%;
	padding-left: 5px;
	padding-right: 5px;
	float: left;
}

.p-lg {
	padding: 0px 0px 10px 0px;
}

.widget {
	margin-top: 0px;
}

.iconfont {
	font-size: 30px;
	color: white;
}

h2 {
	font-size: 19px;
}

.echart_div {
	height: 240px;
	width: 100%;
}

.ibtn {
	cursor: pointer;
}

.flot-chart {
	height: 400px;
}
/*  .top-navigation .wrapper.wrapper-content{padding:20px 5px !important;}
	.container {
    	 width:99% !important; margin:10px;
    	 padding-right: 1px !important;
    	 padding-left: 1px !important;
	}
	.color_red{color:#e55555;}
	.col-cs-2 {
	    width: 10%;
		padding-left: 5px;
		padding-right: 5px;
		float: left;
	}*/
@media ( min-width : 992px) {
	.col-cs-2 {
		width: 11.11%;
		padding-left: 5px;
		padding-right: 5px;
		float: left;
	}
}
/*比如说这段css样式*/
.TheStatistics {
	   float: left;
	   position: relative;
	   width: 28vw;
	   height: 58vh;
	   margin: 8vh 0 0 1vw;
	   -webkit-box-sizing: border-box;
	   -moz-box-sizing: border-box;
	   box-sizing: border-box;     /*最主要的是这两行,上面的都是打酱油的*/    background :
	url('../img/icon_boder.png') no-repeat center !important;
	/*注意：最高权重必须要给，不然样式会被覆盖掉*/
	   background-size: 100% 100% !important; /*注意：背景图大小宽高都给到100%,且加上最高权重*/
}

#allmap {
	width: 100%;
	height: 100%;
	overflow: hidden;
	margin: 0;
	font-family: "微软雅黑";
}

.BMap_cpyCtrl {
	display: none;
}

.anchorBL {
	display: none;
}
</style>

</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">


		<div class="row  border-bottom white-bg dashboard-header">
			<div class="col-sm-12">
				<div style="line-height: 24px;">
					<div style="padding: 2px; font-size: 15px;">
						截止本月， <span id="home_info_00"
							style="font-size: 18px; color: #0094ff; font-weight: bolder;"></span>
						共有河长制河流 <span id="home_info_01"
							style="font-size: 18px; color: #0094ff; font-weight: bolder;"></span>
						条，其中5公里以上河流 <span id="home_info_02"
							style="font-size: 18px; color: #0094ff; font-weight: bolder;"></span>
						条， 湖泊 <span id="home_info_03"
							style="font-size: 18px; color: #0094ff; font-weight: bolder;"></span>
						个， 水库 <span id="home_info_04"
							style="font-size: 18px; color: #0094ff; font-weight: bolder;"></span>
						座， 各级河长 <span id="home_info_05"
							style="font-size: 18px; color: darkorange; font-weight: bolder;"></span>
						人，河湖总体水质情况良好；
					</div>
					<br>
					<div style="padding: 2px; font-size: 15px;">
						全市水功能区共 <span id="home_info_10"
							style="font-size: 18px; color: #0094ff; font-weight: bolder;"></span>
						个，其中一级水功能区 <span id="home_info_11"
							style="font-size: 18px; color: #0094ff; font-weight: bolder;"></span>
						个，二级水功能区 <span id="home_info_12"
							style="font-size: 18px; color: #0094ff; font-weight: bolder;"></span>
						个；
					</div>
					<br>
					<div style="padding: 2px; font-size: 15px;">
						市级监测断面 <span id="home_info_20"
							style="font-size: 18px; color: #0094ff; font-weight: bolder;"></span>
						个，水质达标率为 <span id="home_info_21"
							style="font-size: 18px; color: #0094ff; font-weight: bolder;"></span>
						%；
					</div>
					<br>
					<div style="padding: 2px; font-size: 15px;">
						<span id="home_info_30"
							style="font-size: 18px; color: #0094ff; font-weight: bolder;"></span>
						起至今，全 <span id="home_info_31"
							style="font-size: 18px; color: #0094ff; font-weight: bolder;"></span>
						各级河长累计使用app巡河共 <span id="home_info_32"
							style="font-size: 18px; color: #0094ff; font-weight: bolder;"></span>
						次,累计发现各类问题 <span id="home_info_33"
							style="font-size: 18px; color: #0094ff; font-weight: bolder;"></span>
						个， 已处理 <span id="home_info_34"
							style="font-size: 18px; color: #0094ff; font-weight: bolder;"></span>
						件， 处理率为 <span id="home_info_35"
							style="font-size: 18px; color: #0094ff; font-weight: bolder;"></span>
						%；96322受理投诉 <span id="home_info_36"
							style="font-size: 18px; color: #0094ff; font-weight: bolder;"></span>
						起，已处理 <span id="home_info_37"
							style="font-size: 18px; color: #0094ff; font-weight: bolder;"></span>
						起，处理率为 <span id="home_info_38"
							style="font-size: 18px; color: #0094ff; font-weight: bolder;"></span>
						%；
					</div>
				</div>
			</div>
			<div class="col-sm-3"></div>
			<div class="col-sm-5"></div>
			<div class="col-sm-4"></div>

		</div>
		<div class="row">

			<div class="col-sm-4">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>水质</h5>
					</div>
					<div class="ibox-content">
						<div class="row">
							<div class="col-sm-12">
								<div class="flot-chart TheStatistics" style="height: 315px;">
									<div class="flot-chart-content" id="chart00"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>巡查率</h5>
					</div>
					<div class="ibox-content">
						<div class="row">
							<div class="col-sm-12">
								<div class="flot-chart" style="height: 315px;">
									<div class="flot-chart-content" id="chart01"></div>
									<div id="contextMenu"
										style="position: absolute; background: #000; opacity: 0.8; cursor: pointer; border-radius: 2px; display: none; color: #fff; font-size: 14px;">返回上一级</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>


			<div class="col-sm-4">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>河长巡河完成情况图</h5>
					</div>
					<div class="ibox-content">
						<div class="row">
							<div class="col-sm-12">
								<div class="flot-chart" style="height: 315px;">
									<div class="flot-chart-content" id="chart02"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-sm-4">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>水质分布情况</h5>
					</div>
					<div class="ibox-content">
						<div class="row">
							<div class="col-sm-12">
								<div class="flot-chart">
									<div class="flot-chart-content" id="chart10"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>河道水质达标情况</h5>
					</div>
					<div class="ibox-content">
						<div class="row">
							<div class="col-sm-12">
								<div class="flot-chart">
									<div class="flot-chart-content" id="chart11"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>河湖问题完成情况图</h5>
					</div>
					<div class="ibox-content">
						<div class="row">
							<div class="col-sm-12">
								<div class="flot-chart">
									<div class="flot-chart-content" id="chart12"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 全局js -->
		<script src="plug-in/hplus/js/jquery.min.js?v=2.1.4"></script>
		<script src="plug-in/hplus/js/bootstrap.min.js?v=3.3.6"></script>
		<!-- 自定义js -->
		<script src="plug-in/hplus/js/content.js"></script>
		<script type="text/javascript" src="plug-in/echart/echarts.min.js"></script>
		<script type="text/javascript"
			src="https://api.map.baidu.com/api?v=2.0&ak=7aLuDD5cNosFGfxoyzrWlKZwbA6oAzFK&__ec_v__=20190126"></script>
		<script type="text/javascript"
			src="http://echarts.baidu.com/gallery/vendors/echarts/extension/bmap.min.js"></script>
		<!-- 全国344个市、区、州对应的数字编号 -->
		<script type="text/javascript"
			src="plug-in/echart/echart_bak/citymap.js"></script>

		<script type="text/javascript"
			src="plug-in/jquery-plugs/i18n/jquery.i18n.properties.js"></script>
		<t:base type="tools"></t:base>
		<script type="text/javascript" src="plug-in/login/js/getMsgs.js"></script>
		<script>

var chart00 = echarts.init(document.getElementById('chart00'));
var chart01 = echarts.init(document.getElementById('chart01'));
var chart02 = echarts.init(document.getElementById('chart02'));
var chart10 = echarts.init(document.getElementById('chart10'));
var chart11 = echarts.init(document.getElementById('chart11'));
var chart12 = echarts.init(document.getElementById('chart12'));

var geoCoordMap00;
var convertData00 = function (data) {
    var res = [];
    for (var i = 0; i < data.length; i++) {
        var geoCoord = geoCoordMap00[data[i].name];
        if (geoCoord) {
            res.push({
                name: data[i].name,
                value: geoCoord.concat(data[i].value)
            });
        }
    }
    return res;
};

var mapvalue;
var mapdata = [];
var mapname='江苏';
var mapurl='plug-in/echart/echart_bak/map/province/jiangsu.json';
//var mapname='china';
//var mapurl='plug-in/echart/echart_bak/map/china.json';
//初始化绘制全国地图配置
var option01 = {
	title : {
        text: '巡查率',
        subtext: '',
        link: '',
        left: 'left'
    },
    tooltip: {
        trigger: 'item',
        formatter: '{b}:{c}%'
    },
    toolbox: {
        show: true,
        orient: 'vertical',
        left: 'right',
        top: 'center',
        feature: {
            dataView: {readOnly: false},
            restore: {},
            saveAsImage: {}
        },
        iconStyle:{
        	normal:{
        		color:'#fff'
        	}
        }
    },
    visualMap: {
        min: 0,
        max: 100,
        text:['最高','最低'],
        realtime: false,
        calculable: true,
        inRange: {
            color: ['lightskyblue','yellow', 'orangered']
        }
    },
    animationDuration:1000,
	animationEasing:'cubicOut',
	animationDurationUpdate:1000
     
};
//地图点击事件
chart01.on('click', function (params) {
	console.log( params );
	if( params.name in provinces ){
		//如果点击的是34个省、市、自治区，绘制选中地区的二级地图
		$.getJSON('plug-in/echart/echart_bak/map/province/'+ provinces[params.name] +'.json', function(data){
			echarts.registerMap( params.name, data);
			var d = [];
			for( var i=0;i<data.features.length;i++ ){
				var value11=mapvalue[data.features[i].properties.name];
				if (typeof(value11) == "undefined") value11=0; 
				d.push({
					name:data.features[i].properties.name,
					value:value11
				})
			}
			renderMap(params.name,d);
		});
	}else if( params.seriesName in provinces ){
		//如果是【直辖市/特别行政区】只有二级下钻
		if(  special.indexOf( params.seriesName ) >=0  ){
			renderMap(mapname,mapdata);
		}else{
			//显示县级地图
			$.getJSON('plug-in/echart/echart_bak/map/city/'+ cityMap[params.name] +'.json', function(data){
				echarts.registerMap( params.name, data);
				var d = [];
				for( var i=0;i<data.features.length;i++ ){
					var value11=mapvalue[data.features[i].properties.name];
					if (typeof(value11) == "undefined") value11=0; 
					d.push({
						name:data.features[i].properties.name,
						value:value11
					})
				}
				renderMap(params.name,d);
			});	
		}	
	}else{
		renderMap(mapname,mapdata);
	}
});
//初始化绘制全国地图配置
function renderMap(map,data){
	option01.title.subtext = map;
    option01.series = [ 
		{
            name: map,
            type: 'map',
            mapType: map,
            roam: false,
            nameMap:{
			    'china':'中国'
			},
            label: {
	            normal:{
					show:true,
					textStyle:{
						color:'#999',
						fontSize:13
					}  
	            },
	            emphasis: {
	                show: true,
	                textStyle:{
						color:'#fff',
						fontSize:13
					}
	            }
	        },
	        itemStyle:{
                   normal:{label:{show:true}},
                   emphasis:{label:{show:true}}
               },
            data:data
        }	
    ];
    //渲染地图
    chart01.setOption(option01);
}

$(document).ready(function() {
	$(window).resize(chart00.resize);
	$(window).resize(chart01.resize);
	$(window).resize(chart02.resize);
	$(window).resize(chart10.resize);
	$(window).resize(chart11.resize);
	$(window).resize(chart12.resize);	
	
	jQuery.ajax({  
		type: 'POST',  
		dataType : "json",  
		url: 'loginController.do?getHomeInfo',
		success:function(data){
			//layer.msg("已经到达最上一级地图了!");
			if ("成功"==data.message){
				var typename='';
				typename=data.data.home_info_00[0].typename;
				$("#home_info_00").html(typename);
				typename=data.data.home_info_00[1].typename;
				$("#home_info_01").html(typename);
				typename=data.data.home_info_00[2].typename;
				$("#home_info_02").html(typename);
				typename=data.data.home_info_00[3].typename;
				$("#home_info_03").html(typename);
				typename=data.data.home_info_00[4].typename;
				$("#home_info_04").html(typename);
				typename=data.data.home_info_00[5].typename;
				$("#home_info_05").html(typename);
				
				typename=data.data.home_info_00[6].typename;
				$("#home_info_10").html(typename);
				typename=data.data.home_info_00[7].typename;
				$("#home_info_11").html(typename);
				typename=data.data.home_info_00[8].typename;
				$("#home_info_12").html(typename);
				
				typename=data.data.home_info_00[9].typename;
				$("#home_info_20").html(typename);
				typename=data.data.home_info_00[10].typename;
				$("#home_info_21").html(typename);
				
				typename=data.data.home_info_00[11].typename;
				$("#home_info_30").html(typename);
				typename=data.data.home_info_00[12].typename;
				$("#home_info_31").html(typename);
				typename=data.data.home_info_00[13].typename;
				$("#home_info_32").html(typename);
				typename=data.data.home_info_00[14].typename;
				$("#home_info_33").html(typename);
				typename=data.data.home_info_00[15].typename;
				$("#home_info_34").html(typename);
				typename=data.data.home_info_00[16].typename;
				$("#home_info_35").html(typename);
				typename=data.data.home_info_00[17].typename;
				$("#home_info_36").html(typename);
				typename=data.data.home_info_00[18].typename;
				$("#home_info_37").html(typename);
				typename=data.data.home_info_00[19].typename;
				$("#home_info_38").html(typename);
				
				var jsonstr=data.data.home_info_01[0].typename;
				var data00=JSON.parse(jsonstr);
				jsonstr=data.data.home_info_01[1].typename;
				geoCoordMap00=JSON.parse(jsonstr);
				jsonstr=data.data.home_info_01[2].typename;
				var center00=JSON.parse(jsonstr);
				jsonstr=data.data.home_info_01[3].typename;
				var zoom00=jsonstr;
				chart00.setOption(option = {
					    tooltip : {
					        trigger: 'item'
					    },
					    bmap: {
					        center: center00,
					        zoom: zoom00,
					        roam: true,
					        mapStyle: {
					            styleJson: [{
					                'featureType': 'water',
					                'elementType': 'all',
					                'stylers': {
					                    'color': '#d1d1d1'
					                }
					            }, {
					                'featureType': 'land',
					                'elementType': 'all',
					                'stylers': {
					                    'color': '#f3f3f3'
					                }
					            }, {
					                'featureType': 'railway',
					                'elementType': 'all',
					                'stylers': {
					                    'visibility': 'off'
					                }
					            }, {
					                'featureType': 'highway',
					                'elementType': 'all',
					                'stylers': {
					                    'color': '#fdfdfd'
					                }
					            }, {
					                'featureType': 'highway',
					                'elementType': 'labels',
					                'stylers': {
					                    'visibility': 'off'
					                }
					            }, {
					                'featureType': 'arterial',
					                'elementType': 'geometry',
					                'stylers': {
					                    'color': '#fefefe'
					                }
					            }, {
					                'featureType': 'arterial',
					                'elementType': 'geometry.fill',
					                'stylers': {
					                    'color': '#fefefe'
					                }
					            }, {
					                'featureType': 'poi',
					                'elementType': 'all',
					                'stylers': {
					                    'visibility': 'off'
					                }
					            }, {
					                'featureType': 'green',
					                'elementType': 'all',
					                'stylers': {
					                    'visibility': 'off'
					                }
					            }, {
					                'featureType': 'subway',
					                'elementType': 'all',
					                'stylers': {
					                    'visibility': 'off'
					                }
					            }, {
					                'featureType': 'manmade',
					                'elementType': 'all',
					                'stylers': {
					                    'color': '#d1d1d1'
					                }
					            }, {
					                'featureType': 'local',
					                'elementType': 'all',
					                'stylers': {
					                    'color': '#d1d1d1'
					                }
					            }, {
					                'featureType': 'arterial',
					                'elementType': 'labels',
					                'stylers': {
					                    'visibility': 'off'
					                }
					            }, {
					                'featureType': 'boundary',
					                'elementType': 'all',
					                'stylers': {
					                    'color': '#fefefe'
					                }
					            }, {
					                'featureType': 'building',
					                'elementType': 'all',
					                'stylers': {
					                    'color': '#d1d1d1'
					                }
					            }, {
					                'featureType': 'label',
					                'elementType': 'labels.text.fill',
					                'stylers': {
					                    'color': '#999999'
					                }
					            }]
					        }
					    },
					    series : [
					        {
					            name: '水质',
					            type: 'effectScatter',
					            coordinateSystem: 'bmap',
					            data: convertData00(data00.sort(function (a, b) {
					                return b.value - a.value;
					            }).slice(0, 6)),
					            symbolSize: function (val) {
					                return val[2] / 10;
					            },
					            showEffectOn: 'render',
					            rippleEffect: {
					                brushType: 'stroke'
					            },
					            hoverAnimation: true,
					            label: {
					                normal: {
					                    formatter: '{b}',
					                    position: 'right',
					                    show: true
					                }
					            },
					            encode: {                    //可以定义 data 的哪个维度被编码成什么
					                tooltip: [3, 2, 4],      // 表示维度 3、2、4 会在 tooltip 中显示。
					            },
					            tooltip: {
					                trigger: 'item',
					                formatter: function(params) {
					                    var res = params.name+':水质达标优率'+params.value[2]+'%';
					                    //var res = '水质达标优率'+params.value[2]+'%';
					                    return res;
					                }
					            },
					            itemStyle: {
					                normal: {
					                    color: 'purple',
					                    shadowBlur: 10,
					                    shadowColor: '#333'
					                }
					            },
					            zlevel: 1
					        }
					    ]
					});
				
				jsonstr=data.data.home_info_02[0].typename;
				var data01=JSON.parse(jsonstr);
				mapvalue=data01;
				//绘制全国地图
				$.getJSON(mapurl, function(data){
					d = [];
					for( var i=0;i<data.features.length;i++ ){
						var value11=mapvalue[data.features[i].properties.name];
						if (typeof(value11) == "undefined") value11=0; 
						d.push({
							name:data.features[i].properties.name,
							value:value11
						})
					}
					mapdata = d;
					//注册地图
					echarts.registerMap(mapname, data);
					//绘制地图
					renderMap(mapname,d);
				});
				
				jsonstr=data.data.home_info_03[0].typename;
				var data02=JSON.parse(jsonstr);
				jsonstr=data.data.home_info_03[1].typename;
				var data020=JSON.parse(jsonstr);
				jsonstr=data.data.home_info_03[2].typename;
				var data021=JSON.parse(jsonstr);
				jsonstr=data.data.home_info_03[3].typename;
				var data022=JSON.parse(jsonstr);
				chart02.setOption(option = {
					    tooltip: {
					        trigger: 'axis',
					        axisPointer: {
					            type: 'cross',
					            crossStyle: {
					                color: '#999'
					            }
					        },
					        formatter: function(params){ 
					            str =  params[0].name+"<br/>"+params[0].seriesName+":"+params[0].value+"人"+"<br/>"+params[1].seriesName+":"+params[1].value+"人"+"<br/>"+params[2].seriesName+":"+params[2].value+"%";
					            return str
					        }
					    },
					    toolbox: {
					        feature: {
					            dataView: {show: true, readOnly: false},
					            magicType: {show: true, type: ['line', 'bar']},
					            restore: {show: true},
					            saveAsImage: {show: true}
					        }
					    },
					    legend: {
					        data:['已巡人数','应巡人数','完成率']
					    },
					    xAxis: [
					        {
					            type: 'category',
					            data: data02,
					            axisPointer: {
					                type: 'shadow'
					            }
					        }
					    ],
					    yAxis: [
					        {
					            type: 'value',
					            name: '人数',
					            min: 0,
					            axisLabel: {
					                formatter: '{value}人'
					            }
					        },
					        {
					            type: 'value',
					            name: '百分比',
					            min: 0,
					            axisLabel: {
					                formatter: '{value}%'
					            }
					        }
					    ],
					    series: [
					        {
					            name:'已巡人数',
					            type:'bar',
					            yAxisIndex: 0,
					            data:data020
					        },
					        {
					            name:'应巡人数',
					            type:'bar',
					            yAxisIndex: 0,
					            data:data021
					        },
					        {
					            name:'完成率',
					            type:'line',
					            yAxisIndex: 1,
					            data:data022
					        }
					    ]
					});
				
				
				
				jsonstr=data.data.home_info_04[0].typename;
				var data10=JSON.parse(jsonstr);
				jsonstr=data.data.home_info_04[1].typename;
				var data100=JSON.parse(jsonstr);
				jsonstr=data.data.home_info_04[2].typename;
				var data101=JSON.parse(jsonstr);
				jsonstr=data.data.home_info_04[3].typename;
				var data102=JSON.parse(jsonstr);
				jsonstr=data.data.home_info_04[4].typename;
				var data103=JSON.parse(jsonstr);
				jsonstr=data.data.home_info_04[5].typename;
				var data104=JSON.parse(jsonstr);
				chart10.setOption(option = {
					    tooltip : {
					        trigger: 'axis',
					        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
					            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
					        },
					        formatter: function(params){ 
					        	//debugger;
					            str =  params[0].name+"<br/>"+params[0].seriesName+":"+params[0].value+"%"+"<br/>"+params[1].seriesName+":"+params[1].value+"%"+"<br/>"+params[2].seriesName+":"+params[2].value+"%"+"<br/>"+params[3].seriesName+":"+params[3].value+"%"+"<br/>"+params[4].seriesName+":"+params[4].value+"%";
					            return str
					        }
					    },
					    legend: {
					        data: ['Ⅰ类 ', 'Ⅱ类','Ⅲ类','Ⅳ类','Ⅴ类']
					    },
					    grid: {
					        left: '3%',
					        right: '4%',
					        bottom: '3%',
					        containLabel: true
					    },
					    xAxis:  {
				        	type: 'category',
					        data: data10			        
					    },
					    yAxis: {
					    	type: 'value',
					    	max:100,
					    	name: '百分比',
				            min: 0,
				            axisLabel: {
				                formatter: '{value}%'
				            }
					    },
					    series: [
					        {
					            name: 'Ⅰ类',
					            type: 'bar',
					            stack: '总量',
					            label: {
					                normal: {
					                    show: true,
					                    position: 'insideRight'
					                }
					            },
					            data: data100
					        },
					        {
					            name: 'Ⅱ类',
					            type: 'bar',
					            stack: '总量',
					            label: {
					                normal: {
					                    show: true,
					                    position: 'insideRight'
					                }
					            },
					            data: data101
					        },
					        {
					            name: 'Ⅲ类',
					            type: 'bar',
					            stack: '总量',
					            label: {
					                normal: {
					                    show: true,
					                    position: 'insideRight'
					                }
					            },
					            data: data102
					        },
					        {
					            name: 'Ⅳ类',
					            type: 'bar',
					            stack: '总量',
					            label: {
					                normal: {
					                    show: true,
					                    position: 'insideRight'
					                }
					            },
					            data: data103
					        },
					        {
					            name: 'Ⅴ类',
					            type: 'bar',
					            stack: '总量',
					            label: {
					                normal: {
					                    show: true,
					                    position: 'insideRight'
					                }
					            },
					            data: data104
					        }
					    ]
					});
				

				jsonstr=data.data.home_info_05[0].typename;
				var data11=JSON.parse(jsonstr);
				jsonstr=data.data.home_info_05[1].typename;
				var data110=JSON.parse(jsonstr);
				chart11.setOption(option = {
					    color: ['#3398DB'],
					    tooltip : {
					        trigger: 'axis',
					        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
					            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
					        },
					        formatter: function(params){ 
					        	//debugger;
					            str =  params[0].name+"<br/>"+params[0].seriesName+":"+params[0].value+"%";
					            return str
					        }
					    },
					    grid: {
					        left: '3%',
					        right: '4%',
					        bottom: '3%',
					        containLabel: true
					    },
					    xAxis : [
					        {
					            type : 'category',
					            data : data11,
					            axisTick: {
					                alignWithLabel: true
					            }
					        }
					    ],
					    yAxis : [
					        {
					            type : 'value'
					        }
					    ],
					    series : [
					        {
					            name:'水质达标率',
					            type:'bar',
					            barWidth: '60%',
					            data:data110
					        }
					    ]
					});
				
				jsonstr=data.data.home_info_06[0].typename;
				var data12=JSON.parse(jsonstr);
				jsonstr=data.data.home_info_06[1].typename;
				var data120=JSON.parse(jsonstr);
				jsonstr=data.data.home_info_06[2].typename;
				var data121=JSON.parse(jsonstr);
				jsonstr=data.data.home_info_06[3].typename;
				var data122=JSON.parse(jsonstr);
				chart12.setOption(option = {
					    tooltip: {
					        trigger: 'axis',
					        axisPointer: {
					            type: 'cross',
					            crossStyle: {
					                color: '#999'
					            }
					        },
					        formatter: function(params){ 
					        	//debugger;
					            str =  params[0].name+"<br/>"+params[0].seriesName+":"+params[0].value+"次"+"<br/>"+params[1].seriesName+":"+params[1].value+"次"+"<br/>"+params[2].seriesName+":"+params[2].value+"%";
					            return str
					        }
					    },
					    toolbox: {
					        feature: {
					            dataView: {show: true, readOnly: false},
					            magicType: {show: true, type: ['line', 'bar']},
					            restore: {show: true},
					            saveAsImage: {show: true}
					        }
					    },
					    legend: {
					        data:['问题总数','已处理数','处理率']
					    },
					    xAxis: [
					        {
					            type: 'category',
					            data: data12,
					            axisPointer: {
					                type: 'shadow'
					            },
					            axisLabel: {  
                                    interval: 0,  
                                    formatter:function(value)  
                                    {  
                                        // debugger
                                        var ret = "";//拼接加\n返回的类目项  
                                        var maxLength = 3;//每项显示文字个数  
                                        var valLength = value.length;//X轴类目项的文字个数  
                                        var rowN = Math.ceil(valLength / maxLength); //类目项需要换行的行数  
                                        if (rowN > 1)//如果类目项的文字大于3,  
                                        {  
                                            for (var i = 0; i < rowN; i++) {  
                                                var temp = "";//每次截取的字符串  
                                                var start = i * maxLength;//开始截取的位置  
                                                var end = start + maxLength;//结束截取的位置  
                                                //这里也可以加一个是否是最后一行的判断，但是不加也没有影响，那就不加吧  
                                                temp = value.substring(start, end) + "\n";  
                                                ret += temp; //凭借最终的字符串  
                                            }  
                                            return ret;  
                                        }  
                                        else {  
                                            return value;  
                                        }  
                                    }  
                                } 
					        }
					    ],
					    yAxis: [
					        {
					            type: 'value',
					            name: '数量',
					            min: 0,
					            axisLabel: {
					                formatter: '{value}次'
					            }
					        },
					        {
					            type: 'value',
					            name: '百分比',
					            min: 0,
					            axisLabel: {
					                formatter: '{value}%'
					            }
					        }
					    ],
					    series: [
					        {
					            name:'问题总数',
					            type:'bar',
					            yAxisIndex: 0,
					            data:data120
					        },
					        {
					            name:'已处理数',
					            type:'bar',
					            yAxisIndex: 0,
					            data:data121
					        },
					        {
					            name:'处理率',
					            type:'line',
					            yAxisIndex: 1,
					            data:data122
					        }
					    ]
					});
				
				
				
			}
	 	},
		error: function () {
			tip('服务器未响应，请稍后再试！');  
		}
	});
});
</script>
</body>
</html>


<!--     
                            <table class="table table-hover no-margins">
                                <thead>
                                    <tr>
                                        <th>序号</th>
                                        <th>类型</th>
                                        <th>任务</th>
                                        <th>数量</th></tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>1</td>
                                        <td>
                                            <span class="label label-warning">站内信</span></td>
                                        <td>会议</td>
                                        <td class="text-navy">5</td></tr>
                                    <tr>
                                        <td>2</td>
                                        <td>
                                            <span class="label label-primary">通知</span></td>
                                        <td>任务7</td>
                                        <td class="text-navy">4</td></tr>
                                    <tr>
                                        <td>3</td>
                                        <td>
                                            <span class="label label-warning">类型1</span></td>
                                        <td>任务六</td>
                                        <td class="text-navy">2</td></tr>
                                    <tr>
                                        <td>4</td>
                                        <td>
                                            <span class="label label-primary">类型2</span></td>
                                        <td>任务5</td>
                                        <td class="text-navy">0</td></tr>
                                    <tr>
                                        <td>5</td>
                                        <td>
                                            <span class="label label-warning">类型3</span></td>
                                        <td>任务4</td>
                                        <td class="text-navy">16</td></tr>
                                    <tr>
                                        <td>6</td>
                                        <td>
                                            <span class="label label-primary">类型4</span></td>
                                        <td>任务3</td>
                                        <td class="text-navy">3</td></tr>
                                    <tr>
                                        <td>7</td>
                                        <td>
                                            <span class="label label-warning">类型5</span></td>
                                        <td>任务2</td>
                                        <td class="text-navy">7</td></tr>
                                    <tr>
                                        <td>8</td>
                                        <td>
                                            <span class="label label-primary">类型6</span></td>
                                        <td>任务1</td>
                                        <td class="text-navy">2</td></tr>
                                </tbody>
                            </table>
                         	-->

<!-- <iframe src="http://tianqi.5ikfc.com:90/plugin-code/?style=1&dy=1&fs=12" frameborder="0" scrolling="no" width="300" height="60" id="weather_frame"></iframe>
					<iframe name="weather_inc" src="http://i.tianqi.com/index.php?c=code&id=7" style="border:solid 1px #7ec8ea" width="105%" height="90" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
					<div id="calendar"></div>
					
					
					     <div class="wrapper wrapper-content">
    
    <div class="row">
        <div class="col-sm-4">

            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>支持</h5>
                </div>
                <div class="ibox-content">
                </div>
            </div>
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>联系信息</h5>

                </div>
                <div class="ibox-content">
                </div>
            </div>
        </div>
        <div class="col-sm-4">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    
                </div>
                <div class="ibox-content no-padding">
                    <div class="panel-body">
                        <div class="panel-group" id="version">
                       
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-4">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>公众投诉</h5>
                    <div class="pull-right">
                        <div class="btn-group">
                            <button type="button" class="chart5 btn btn-xs btn-white active">天</button>
                            <button type="button" class="chart5 btn btn-xs btn-white">月</button>
                            <button type="button" class="chart5 btn btn-xs btn-white">年</button>
                         </div>
                    </div>
                </div>
                <div class="ibox-content">
                 	<div class="row">
                        <div class="col-sm-12">
                            <div class="flot-chart">
                                <div class="flot-chart-content" id="chart5"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
    </div>
</div>
$.ajax({
		type : "POST",
		url : "jeecgListDemoController.do?broswerCount&reportType=pie",
		success : function(jsondata) {
			jsondata=JSON.parse(jsondata);
			var data=jsondata[0].data;
			var xAxisData=[];
			var seriesData=[];
			var picData = [];
			for(var i in data){
				xAxisData.push(data[i].name);
				seriesData.push(data[i].percentage);
				picData.push({"value":data[i].percentage,"name":data[i].name});
			}
			chart10.setOption({
				tooltip: {
					trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
				},
			    legend: {
			        data: xAxisData
			    },
			    series : [
					        {
					            name: "用户人数",
					            type: 'pie',
					            radius : '55%',
					            center: ['50%', '60%'],
					            data:picData,
					            itemStyle: {
					                emphasis: {
					                    shadowBlur: 10,
					                    shadowOffsetX: 0,
					                    shadowColor: 'rgba(0, 0, 0, 0.5)'
					                }
					            }
					        }
					    ]
			});
		}
	});



	$.ajax({
		type : "POST",
		url : "jeecgListDemoController.do?broswerCount&reportType=column",
		success : function(jsondata) {
			jsondata=JSON.parse(jsondata);
			var data=jsondata[0].data;
			var xAxisData=[];
			var seriesData=[];
			for(var i in data){
				xAxisData.push(data[i].name);
				seriesData.push(data[i].percentage);
			}
			var option3 = {
		            tooltip: {},
		            legend: {
		                data:[jsondata[0].name],
		                left:'center'
		            },
		            xAxis: {
		            	type: 'category',
		                data: xAxisData,
		                axisLabel:{
		                	interval:0,//横轴信息全部显示
		                	rotate:-30,//-10角度倾斜展示
		                }
		            },
		            yAxis: {},
		            series: [{
		                name: jsondata[0].name,
		                type: 'bar',
		                data: seriesData
		            }]
		        };
			chart3.setOption(option3);
		}
	});
		$.ajax({
			type : "POST",
			url : "jeecgListDemoController.do?broswerCount&reportType=line",
			success : function(jsondata) {
				jsondata=JSON.parse(jsondata);
				var data=jsondata[0].data;
				var xAxisData=[];
				var seriesData=[];
				for(var i in data){
					xAxisData.push(data[i].name);
					seriesData.push(data[i].percentage);
				}
				var option4 = {
					    color: ['#3398DB'],
					    tooltip : {
					        trigger: 'axis',
					        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
					            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
					        }
					    },
					    grid: {
					        left: '3%',
					        right: '4%',
					        bottom: '10%',
					        containLabel: true
					    },
					    xAxis : [
					        {
					            type : 'category',
					            data : xAxisData,
					            axisTick: {
					                alignWithLabel: true
					            },
					            axisLabel:{
				                	interval:0,//横轴信息全部显示
				                	rotate:-30,//-10角度倾斜展示
				                }
					        }
					    ],
					    yAxis : [
					        {
					            type : 'value'
					        }
					    ],
					    series : [
					        {
					            name:'用户人数',
					            type:'line',
					            barWidth: '60%',
					            data:seriesData
					        }
					    ]
					};
				chart01.setOption(option4, true);
			}
		});					
					 -->

<!-- 
			<div class="row">
                <div class="col-md-1 col-cs-2 col-xs-4">
					<div class="widget  p-lg text-center" style="background: #cfa972;">
						<div>
                            <i class="iconfont icon-zhihuizhongxin" style="font-size: 30px;"></i>
                            <h3 class="font-bold no-margins"></h3>
                            <small>功能1</small>
                        </div>
                    </div>
                </div>
                <div class="col-md-1 col-cs-2 col-xs-4">
					<div class="widget  p-lg text-center" style="background: #f29b76;">
						<div>
                            <i class="iconfont icon-yujing" style="font-size: 30px;"></i>
                            <h3 class="font-bold no-margins"></h3>
                            <small>功能2</small>
                        </div>
                    </div>
                </div>
                <div class="col-md-1 col-cs-2 col-xs-4">
					<div class="widget  p-lg text-center" style="background: #acd598;">
						<div>
                            <i class="iconfont icon-ln-" style="font-size: 30px;"></i>
                            <h3 class="font-bold no-margins"></h3>
                            <small>功能3</small>
                        </div>
                    </div>
                </div>
                <div class="col-md-1 col-cs-2 col-xs-4">
					<div class="widget  p-lg text-center" style="background: #84ccc9;">
						<div>
                            <i class="iconfont icon-wuliu" style="font-size: 30px;"></i>
                            <h3 class="font-bold no-margins"></h3>
                            <small>功能4</small>
                        </div>
                    </div>
                </div>
                <div class="col-md-1 col-cs-2 col-xs-4">
					<div class="widget  p-lg text-center" style="background: #89c997;">
						<div>
                            <i class="iconfont icon-quanshengmingzhouqiguanli" style="font-size: 30px;"></i>
                            <h3 class="font-bold no-margins"></h3>
                            <small>功能5</small>
                        </div>
                    </div>
                </div>
                <div class="col-md-1 col-cs-2 col-xs-4">
					<div class="widget  p-lg text-center" style="background: #88abda;">
						<div>
                            <i class="iconfont icon-jixiao" style="font-size: 30px;"></i>
                            <h3 class="font-bold no-margins"></h3>
                            <small>功能6</small>
                        </div>
                    </div>
                </div>
				<div class="col-md-1 col-cs-2 col-xs-4">
					<div class="widget  p-lg text-center" style="background: #8c97cb;">
						<div>
                            <i class="iconfont icon-fangdajing-copy" style="font-size: 30px;"></i>
                            <h3 class="font-bold no-margins"></h3>
                            <small>功能7</small>
                        </div>
                    </div>
                </div>
				<div class="col-md-1 col-cs-2 col-xs-4">
					<div class="widget  p-lg text-center" style="background: #c490bf;">
						<div>
                            <i class="iconfont icon--youhuajieduan" style="font-size: 30px;"></i>
                            <h3 class="font-bold no-margins"></h3>
                            <small>功能8</small>
                        </div>
                    </div>
                </div>
				<div class="col-md-1 col-cs-2 col-xs-4">
					<div class="widget  p-lg text-center" style="background: #f19ec2;">
						<div>
                            <i class="iconfont icon-duoren" style="font-size: 30px;"></i>
                            <h3 class="font-bold no-margins"></h3>
                            <small>功能9</small>
                        </div>
                    </div>
                </div>
            </div>
	//直接嵌套显示
    laydate.render({
      elem: '#calendar'
      ,position: 'static'
      ,theme: 'molv'
    });            
    
	
	var option3 = {
		    tooltip : {
		        formatter: "{a} <br/>{b} : {c}%"
		    },
		    toolbox: {
		        feature: {
		            restore: {},
		            saveAsImage: {}
		        }
		    },
		    series: [
		        {
		            name: '业务指标',
		            type: 'gauge',
		            detail: {formatter:'{value}%'},
		            data: [{value: 50, name: '完成率'}]
		        }
		    ]
		};
function onchart5(type){
	if (type=='天'){
		var date = new Date();
		var date1=date.pattern("yyyy-MM-dd")
		getchart5(date1,date1);
	}
	if (type=='月'){
		var date1 = getCurrentMonthFirst();
		var date11=date1.pattern("yyyy-MM-dd")
		var date2 = getCurrentMonthLast();
		var date21=date2.pattern("yyyy-MM-dd")

		getchart5(date11,date21);
	}
	if (type=='年'){
		var date = new Date();
		var year=date.getFullYear();
		getchart5(year+'-01-01',year+'-12-31');
	}
	$(this).parent().find(".active").removeClass("active");
}
// 获取当前月的第一天
function getCurrentMonthFirst(){
    var date=new Date();
    date.setDate(1);
    return date;
}
// 获取当前月的最后一天
function getCurrentMonthLast(){
    var date=new Date();
    var currentMonth=date.getMonth();
    var nextMonth=++currentMonth;
    var nextMonthFirstDay=new Date(date.getFullYear(),nextMonth,1);
    var oneDay=1000*60*60*24;
    return new Date(nextMonthFirstDay-oneDay);
}
function getchart5(event_date_begin,event_date_end){
	var chart5 = echarts.init(document.getElementById('chart5'));
	$(window).resize(chart5.resize);
	$.ajax({
		type : "POST",
		url : "graphReportController.do?datagridGraph",
		data:{
			configId:'gztstj',
			event_date_begin:event_date_begin,
			event_date_end:event_date_end
		},
		success : function(jsondata) {
			var data = jsondata.rows;
			var xcode=[];
			var ycount0=[];
			var ycount1=[];
			var ycount2=[];
			var ycount3=[];
			if(data!=null){
				for(var i = data.length-1; i >= 0; i--){
					xcode.push(data[i].code);	//名
					ycount0.push(data[i].count0);//次数
					ycount1.push(data[i].count1);//次数
					ycount2.push(data[i].count2);//次数
					ycount3.push(data[i].count3);//次数
				}
			}
			var option5 = {
		            tooltip : {
		                trigger: 'axis',
		                axisPointer : {
		                    type : 'shadow'
		                }
		            },
		            legend: {
		                data: ['巡河','微信','热线','96322']
		            },
		            grid: {
		                left: '1%',
		                right: '6%',
		                bottom: '3%',
		                containLabel: true
		            },
		            xAxis:  {
		                type: 'value'
		            },
		            yAxis: {
		                type: 'category',
		                data: xcode
		            },
		            series: [
		                {
		                    name: '巡河',
		                    type: 'bar',
		                    stack: '总量',
		                    label: {
		                        normal: {
		                            show: true,
		                            position: 'insideRight'
		                        }
		                    },
		                    data : ycount0
		                },
		                {
		                    name: '微信',
		                    type: 'bar',
		                    stack: '总量',
		                    label: {
		                        normal: {
		                            show: true,
		                            position: 'insideRight'
		                        }
		                    },
		                    data : ycount1
		                },
		                {
		                    name: '热线',
		                    type: 'bar',
		                    stack: '总量',
		                    label: {
		                        normal: {
		                            show: true,
		                            position: 'insideRight'
		                        }
		                    },
		                    data : ycount2
		                },
		                {
		                    name: '96322',
		                    type: 'bar',
		                    stack: '总量',
		                    label: {
		                        normal: {
		                            show: true,
		                            position: 'insideRight'
		                        }
		                    },
		                    data : ycount3
		                }
		            ]
		        };
			chart5.setOption(option5, true);
		}
	});
}

Date.prototype.pattern=function(fmt) {         
    var o = {         
    "M+" : this.getMonth()+1, //月份         
    "d+" : this.getDate(), //日         
    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时         
    "H+" : this.getHours(), //小时         
    "m+" : this.getMinutes(), //分         
    "s+" : this.getSeconds(), //秒         
    "q+" : Math.floor((this.getMonth()+3)/3), //季度         
    "S" : this.getMilliseconds() //毫秒         
    };         
    var week = {         
    "0" : "/u65e5",         
    "1" : "/u4e00",         
    "2" : "/u4e8c",         
    "3" : "/u4e09",         
    "4" : "/u56db",         
    "5" : "/u4e94",         
    "6" : "/u516d"        
    };         
    if(/(y+)/.test(fmt)){         
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));         
    }         
    if(/(E+)/.test(fmt)){         
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);         
    }         
    for(var k in o){         
        if(new RegExp("("+ k +")").test(fmt)){         
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));         
        }         
    }         
    return fmt;         
}  
var colors = ['#5793f3', '#d14a61', '#675bba'];

		//1111111111111111
//存储切换的每一级地图信息
var mapStack01 = [];
var curMap01 = {};


        //去除默认的鼠标事件
        document.oncontextmenu = function () { return false; };
      	//初始化地图
        loadMap('100000', 'china');
        /**
	        绑定用户切换地图区域事件
	        cityMap对象存储着地图区域名称和区域的信息(name-code)
	        当mapCode有值说明可以切换到下级地图
	        此时保存上级地图的编号和名称
	     */
	     chart01.on('click', function(params) {   
	         var code = params.data.code;
	         var name = params.data.name;
	         loadMap(code, name); 
	         //将上一级地图信息压入mapStack
	         mapStack01.push({
	             mapCode: curMap01.mapCode,
	             mapName: curMap01.mapName
	         }); 
	     });
	     /**
	       绑定右键事件
	    */
	    chart01.on('contextmenu', function(params) {
	       $('#contextMenu').css({
	           left: params.event.offsetX,
	           top: params.event.offsetY
	       }).show();
	       return false;
	    });
	    /**
	       响应图表的右键事件，返回上一级地图
	    */
	    $('#contextMenu').on('click', function () {
	        $(this).hide();
	        //获取上一级地图信息
	        var map = mapStack01.pop();
	        if (!mapStack01.length && !map) {
	            //alert('已经到达最上一级地图了');
	            layer.msg("已经到达最上一级地图了!");
	            return;
	        }
	        loadMap(map.mapCode, map.mapName);  
	    });
    	/**
    	   加载地图：根据地图所在省市的行政编号，
    	   获取对应的json地图数据，然后向echarts注册该区域的地图
    	   最后加载地图信息
    	   @params {String} mapCode:地图行政编号,for example['中国':'100000', '湖南': '430000']
    	   @params {String} mapName: 地图名称
    	*/
    	function loadMap(mapCode, mapName) {
			chart01.showLoading();
   			$.get('plug-in/echart/echart_bak/HK.json', function (geoJson) {
   				chart01.hideLoading();
   				echarts.registerMap('HK', geoJson);
   				var option01={
   				        title: {
   				            text: '',
   				        },
   				        tooltip: {
   				            trigger: 'item',
   				            formatter: '{b}<br/>{c}%'
   				        },
   				        toolbox: {
   				            show: true,
   				            orient: 'vertical',
   				            left: 'right',
   				            top: 'center',
   				            feature: {
   				                dataView: {readOnly: false},
   				                restore: {},
   				                saveAsImage: {}
   				            }
   				        },
   				        visualMap: {
   				            min: 0,
   				            max: 100,
   				            text:['最高','最低'],
   				            realtime: false,
   				            calculable: true,
   				            inRange: {
   				                color: ['lightskyblue','yellow', 'orangered']
   				            }
   				        },
   				        series: [
   				            {
   				                type: 'map',
   				                mapType: 'HK', // 自定义扩展图表类型
   				                itemStyle:{
   				                    normal:{label:{show:true}},
   				                    emphasis:{label:{show:true}}
   				                },
   				                data:[
   				                    {code: 'a01',name: '中西区', value: 57.34},
   				                    {code: 'a02',name: '湾仔', value: 77.48},
   				                    {code: 'a03',name: '东区', value: 86.1},
   				                    {code: 'a04',name: '南区', value: 92.6},
   				                    {code: 'a05',name: '油尖旺', value: 45.49},
   				                    {code: 'a06',name: '深水埗', value: 89.64},
   				                    {code: 'a07',name: '九龙城', value: 59.78},
   				                    {code: 'a08',name: '黄大仙', value: 80.97},
   				                    {code: 'a09',name: '观塘', value: 4.26},
   				                    {code: 'a10',name: '葵青', value: 0.9},
   				                    {code: 'a11',name: '荃湾', value: 18.26},
   				                    {code: 'a12',name: '屯门', value: 81.84},
   				                    {code: 'a13',name: '元朗', value: 78.01},
   				                    {code: 'a14',name: '北区', value: 27.92},
   				                    {code: 'a15',name: '大埔', value: 80.98},
   				                    {code: 'a16',name: '沙田', value: 72.94},
   				                    {code: 'a17',name: '西贡', value: 68},
   				                    {code: 'a18',name: '离岛', value: 6.98}
   				                ],
   				                // 自定义名称映射
   				                nameMap: {
   				                    'Central and Western': '中西区',
   				                    'Eastern': '东区',
   				                    'Islands': '离岛',
   				                    'Kowloon City': '九龙城',
   				                    'Kwai Tsing': '葵青',
   				                    'Kwun Tong': '观塘',
   				                    'North': '北区',
   				                    'Sai Kung': '西贡',
   				                    'Sha Tin': '沙田',
   				                    'Sham Shui Po': '深水埗',
   				                    'Southern': '南区',
   				                    'Tai Po': '大埔',
   				                    'Tsuen Wan': '荃湾',
   				                    'Tuen Mun': '屯门',
   				                    'Wan Chai': '湾仔',
   				                    'Wong Tai Sin': '黄大仙',
   				                    'Yau Tsim Mong': '油尖旺',
   				                    'Yuen Long': '元朗'
   				                }
   				            }
   				        ]
   				    };
   				chart01.setOption(option01);
   				curMap01 = {
  		                mapCode: mapCode,
  		                mapName: mapName
  		             };
   			});
    	}
             -->
