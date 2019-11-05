<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
<head>
<title>河长集合</title>
<t:base type="jquery,easyui,tools"></t:base>
<link rel="stylesheet" href="plug-in/leaflet1.3/leaflet.css">
<script src="plug-in/leaflet1.3/leaflet-src.js"></script>
<script src="plug-in/leaflet1.3/proj4.js"></script>
<script src="plug-in/leaflet1.3/proj4leaflet.js"></script>
<script src="plug-in/leaflet1.3/tileLayer.baidu.js"></script>

<script src="plug-in/leaflet/heatmap.min.js"></script>
<script src="plug-in/leaflet/leaflet-heatmap.js"></script>
<style>
body {
	padding: 0;
	margin: 0;
}

html, body, #map {
	height: 100%;
	font: 10pt "Helvetica Neue", Arial, Helvetica, sans-serif;
}

.lorem {
	font-style: italic;
	color: #AAA;
}

.leaflet-control-zoom-fullscreen {
	background-image: url(plug-in/leaflet/FullScreen/icon-fullscreen.png);
}
</style>
<link rel="stylesheet" href="plug-in/leaflet/label/leaflet.label.css" />
<script src="plug-in/leaflet/FullScreen/Control.FullScreen.js"></script>

<link href="plug-in/leaflet/font-awesome-4.7.0/css/font-awesome.min.css"
	rel="stylesheet">
<link rel="stylesheet" href="plug-in/leaflet/leaflet-sidebar.css" />
<script src="plug-in/leaflet/leaflet-sidebar.js"></script>
</head>
<body style="overflow-y: hidden" scroll="no">
	<div id="sidebar" class="sidebar collapsed">
		<div class="sidebar-tabs">
			<ul role="tablist">
				<li title="类型选择"><a href="#home" role="tab"><i
						class="fa fa-bars"></i></a></li>
			</ul>
		</div>
		<div class="sidebar-content">
			<div class="sidebar-pane" id="home">
				<h1 class="sidebar-header">
					类型选择<span class="sidebar-close"><i class="fa fa-caret-right"></i></span>
				</h1>
				<ul style="list-style: none;">
					<li><input tabindex="1" type="checkbox" id="input-1"
						name="checkbox11" value="11"> <label for="input-1">巡河热力图</label>
					</li>
				</ul>
				<ul style="list-style: none;">
					<li><input tabindex="2" type="checkbox" id="input-2"
						name="checkbox11" value="12"> <label for="input-2">测站</label>
					</li>
				</ul>
				<ul style="list-style: none;">
					<li><input tabindex="3" type="checkbox" id="input-3"
						name="checkbox11" value="13"> <label for="input-3">排污口</label>
					</li>
				</ul>
				<ul style="list-style: none;">
					<li><input tabindex="4" type="checkbox" id="input-4"
						name="checkbox11" value="14"> <label for="input-4">取水口</label>
					</li>
				</ul>
				<ul style="list-style: none;">
					<li><input tabindex="5" type="checkbox" id="input-5"
						name="checkbox11" value="15"> <label for="input-5">河湖</label>
					</li>
				</ul>
				<ul style="list-style: none;">
					<li><label class="Validform_label"> 行政地区:</label> <input
						id="dd" name="TSLDivision.code"
						value="${entity.TSLDivision.code}"></li>
				</ul>
			</div>
		</div>
	</div>
	<div id="map" class="sidebar-map">


		<script>
 	//测站
	var dmsz = L.icon({
		iconUrl:'plug-in/leaflet/image/dmsz.png',
		iconSize:[33,40],
		iconAnchor:[10,20],//标记点在图标上位置
		popupAnchor:[-3,-20],
		labelAnchor: [14, 0] // as I want the label to appear 2px past the icon (18 + 2 - 6)
	});
 	//断面
	var duanmian1 = L.icon({
		iconUrl:'plug-in/leaflet/image/duanmian1.png',
		iconSize:[33,40],
		iconAnchor:[10,20],//标记点在图标上位置
		popupAnchor:[-3,-20],
		labelAnchor: [14, 0] // as I want the label to appear 2px past the icon (18 + 2 - 6)
	});
	var duanmian2 = L.icon({
		iconUrl:'plug-in/leaflet/image/duanmian2.png',
		iconSize:[33,40],
		iconAnchor:[10,20],//标记点在图标上位置
		popupAnchor:[-3,-20],
		labelAnchor: [14, 0] // as I want the label to appear 2px past the icon (18 + 2 - 6)
	});
	var duanmian3 = L.icon({
		iconUrl:'plug-in/leaflet/image/duanmian3.png',
		iconSize:[33,40],
		iconAnchor:[10,20],//标记点在图标上位置
		popupAnchor:[-3,-20],
		labelAnchor: [14, 0] // as I want the label to appear 2px past the icon (18 + 2 - 6)
	});
	var duanmian4 = L.icon({
		iconUrl:'plug-in/leaflet/image/duanmian4.png',
		iconSize:[33,40],
		iconAnchor:[10,20],//标记点在图标上位置
		popupAnchor:[-3,-20],
		labelAnchor: [14, 0] // as I want the label to appear 2px past the icon (18 + 2 - 6)
	});
	var duanmian5 = L.icon({
		iconUrl:'plug-in/leaflet/image/duanmian5.png',
		iconSize:[33,40],
		iconAnchor:[10,20],//标记点在图标上位置
		popupAnchor:[-3,-20],
		labelAnchor: [14, 0] // as I want the label to appear 2px past the icon (18 + 2 - 6)
	});
	//排污口
	var paiwukou = L.icon({
		iconUrl:'plug-in/leaflet/image/paiwukou.png',
		iconSize:[33,40],
		iconAnchor:[10,20],//标记点在图标上位置
		popupAnchor:[-3,-20],
		labelAnchor: [14, 0] // as I want the label to appear 2px past the icon (18 + 2 - 6)
	});
	//取水口
	var qushuikou = L.icon({
		iconUrl:'plug-in/leaflet/image/qushuikou.png',
		iconSize:[33,40],
		iconAnchor:[10,20],//标记点在图标上位置
		popupAnchor:[-3,-20],
		labelAnchor: [14, 0] // as I want the label to appear 2px past the icon (18 + 2 - 6)
	});
	//省级
	var shengji = L.icon({
		iconUrl:'plug-in/leaflet/image/shengji.png',
		iconSize:[33,40],
		iconAnchor:[10,20],//标记点在图标上位置
		popupAnchor:[-3,-20],
		labelAnchor: [14, 0] // as I want the label to appear 2px past the icon (18 + 2 - 6)
	});
	//市级
	var shiji = L.icon({
		iconUrl:'plug-in/leaflet/image/shiji.png',
		iconSize:[33,40],
		iconAnchor:[10,20],//标记点在图标上位置
		popupAnchor:[-3,-20],
		labelAnchor: [14, 0] // as I want the label to appear 2px past the icon (18 + 2 - 6)
	});
	//县级
	var xianji = L.icon({
		iconUrl:'plug-in/leaflet/image/xianji.png',
		iconSize:[33,40],
		iconAnchor:[10,20],//标记点在图标上位置
		popupAnchor:[-3,-20],
		labelAnchor: [14, 0] // as I want the label to appear 2px past the icon (18 + 2 - 6)
	});	    
	//乡级
	var xiangji = L.icon({
		iconUrl:'plug-in/leaflet/image/xiangji.png',
		iconSize:[33,40],
		iconAnchor:[10,20],//标记点在图标上位置
		popupAnchor:[-3,-20],
		labelAnchor: [14, 0] // as I want the label to appear 2px past the icon (18 + 2 - 6)
	});	
 	//村级
 	var cunji = L.icon({
		iconUrl:'plug-in/leaflet/image/cunji.png',
		iconSize:[33,40],
		iconAnchor:[10,20],//标记点在图标上位置
		popupAnchor:[-3,-20],
		labelAnchor: [14, 0] // as I want the label to appear 2px past the icon (18 + 2 - 6)
	});
	

  	var testData = {
	  max: 15,
	  data: [
	  //{lat: 24.6408, lng:46.7728, count: 3},{lat: 50.75, lng:-1.55, count: 1},{lat: 52.6333, lng:1.75, count: 1},{lat: 48.15, lng:9.4667, count: 1},{lat: 52.35, lng:4.9167, count: 2},
	  // {lat: 60.8, lng:11.1, count: 1},{lat: 43.561, lng:-116.214, count: 1},{lat: 47.5036, lng:-94.685, count: 1},{lat: 42.1818, lng:-71.1962, count: 1},{lat: 42.0477, lng:-74.1227, count: 1},
	  //{lat: 40.0326, lng:-75.719, count: 1},{lat: 40.7128, lng:-73.2962, count: 2},{lat: 27.9003, lng:-82.3024, count: 1},{lat: 38.2085, lng:-85.6918, count: 1},{lat: 46.8159, lng:-100.706, count: 1},
	  //{lat: 30.5449, lng:-90.8083, count: 1},{lat: 44.735, lng:-89.61, count: 1},{lat: 41.4201, lng:-75.6485, count: 2},{lat: 39.4209, lng:-74.4977, count: 1},{lat: 39.7437, lng:-104.979, count: 1},
	  //{lat: 39.5593, lng:-105.006, count: 1},{lat: 45.2673, lng:-93.0196, count: 1},{lat: 41.1215, lng:-89.4635, count: 1},{lat: 43.4314, lng:-83.9784, count: 1},{lat: 43.7279, lng:-86.284, count: 1},
	  //{lat: 40.7168, lng:-73.9861, count: 1},{lat: 47.7294, lng:-116.757, count: 1},{lat: 47.7294, lng:-116.757, count: 2},{lat: 35.5498, lng:-118.917, count: 1},{lat: 34.1568, lng:-118.523, count: 1},
	  //{lat: 39.501, lng:-87.3919, count: 3},{lat: 33.5586, lng:-112.095, count: 1},{lat: 38.757, lng:-77.1487, count: 1},{lat: 33.223, lng:-117.107, count: 1},{lat: 30.2316, lng:-85.502, count: 1},
	  //{lat: 39.1703, lng:-75.5456, count: 8},{lat: 30.0041, lng:-95.2984, count: 2},{lat: 29.7755, lng:-95.4152, count: 1},{lat: 41.8014, lng:-87.6005, count: 1},{lat: 37.8754, lng:-121.687, count: 7},
	  //{lat: 38.4493, lng:-122.709, count: 1},{lat: 40.5494, lng:-89.6252, count: 1},{lat: 42.6105, lng:-71.2306, count: 1},{lat: 40.0973, lng:-85.671, count: 1},{lat: 40.3987, lng:-86.8642, count: 1},
	  //{lat: 40.4224, lng:-86.8031, count: 4},{lat: 47.2166, lng:-122.451, count: 1},{lat: 32.2369, lng:-110.956, count: 1},{lat: 41.3969, lng:-87.3274, count: 2},{lat: 41.7364, lng:-89.7043, count: 2},
	  //{lat: 42.3425, lng:-71.0677, count: 1},{lat: 33.8042, lng:-83.8893, count: 1},{lat: 36.6859, lng:-121.629, count: 2},{lat: 41.0957, lng:-80.5052, count: 1},{lat: 46.8841, lng:-123.995, count: 1},
	  //{lat: 40.2851, lng:-75.9523, count: 2},{lat: 42.4235, lng:-85.3992, count: 1},{lat: 39.7437, lng:-104.979, count: 2},{lat: 25.6586, lng:-80.3568, count: 7},{lat: 33.0975, lng:-80.1753, count: 1},
	  //{lat: 25.7615, lng:-80.2939, count: 1},{lat: 26.3739, lng:-80.1468, count: 1},{lat: 37.6454, lng:-84.8171, count: 1},{lat: 34.2321, lng:-77.8835, count: 1},{lat: 34.6774, lng:-82.928, count: 1},
	  //{lat: 39.9744, lng:-86.0779, count: 1},{lat: 35.6784, lng:-97.4944, count: 2},{lat: 33.5547, lng:-84.1872, count: 1},{lat: 27.2498, lng:-80.3797, count: 1},{lat: 41.4789, lng:-81.6473, count: 1},
	  //{lat: 41.813, lng:-87.7134, count: 1},{lat: 41.8917, lng:-87.9359, count: 1},{lat: 35.0911, lng:-89.651, count: 1},{lat: 32.6102, lng:-117.03, count: 1},{lat: 41.758, lng:-72.7444, count: 1},
	  //{lat: 39.8062, lng:-86.1407, count: 1},{lat: 41.872, lng:-88.1662, count: 1},{lat: 34.1404, lng:-81.3369, count: 1},{lat: 46.15, lng:-60.1667, count: 1},{lat: 36.0679, lng:-86.7194, count: 1},
	  //{lat: 43.45, lng:-80.5, count: 1},{lat: 44.3833, lng:-79.7, count: 1},{lat: 45.4167, lng:-75.7, count: 2},{lat: 43.75, lng:-79.2, count: 2},{lat: 45.2667, lng:-66.0667, count: 3},
	  //{lat: 42.9833, lng:-81.25, count: 2},{lat: 44.25, lng:-79.4667, count: 3},{lat: 45.2667, lng:-66.0667, count: 2},{lat: 34.3667, lng:-118.478, count: 3},{lat: 42.734, lng:-87.8211, count: 1},
	  //{lat: 39.9738, lng:-86.1765, count: 1},{lat: 33.7438, lng:-117.866, count: 1},{lat: 37.5741, lng:-122.321, count: 1},{lat: 42.2843, lng:-85.2293, count: 1},{lat: 34.6574, lng:-92.5295, count: 1},
	  //{lat: bd09togcj011[1], lng:bd09togcj011[0], count: 3},{lat: bd09togcj012[1], lng:bd09togcj012[0], count: 2},{lat: bd09togcj013[1], lng:bd09togcj013[0], count: 4}
	  ]
	};

        

	var cfg = {
		//热力图的配置项
		// radius: 'radius',       //设置每一个热力点的半径
		radius: 0.05,       //设置每一个热力点的半径
		maxOpacity: 0.6,        //设置最大的不透明度
		// minOpacity: 0.3,     //设置最小的不透明度
		scaleRadius: true,      //设置热力点是否平滑过渡
		blur: 0.95,             //系数越高，渐变越平滑，默认是0.85,
								//滤镜系数将应用于所有热点数据。
		useLocalExtrema: true,  //使用局部极值
		// which field name in your data represents the latitude - default "lat"
		latField: 'lat',
		// which field name in your data represents the longitude - default "lng"
		lngField: 'lng',
		// which field name in your data represents the data value - default "value"
		valueField: 'count',
		gradient: {
			 //过渡，颜色过渡和过渡比例,范例：
			"0.99": "rgba(255,0,0,1)",
			"0.75": "rgba(255,255,0,1)",
			"0.5": "rgba(0,255,0,1)",
			"0.25": "rgba(0,255,255,1)",
			"0": "rgba(0,0,255,1)"
		}
		// backgroundColor: 'rgba(27,34,44,0.5)'    //热力图Canvas背景
	};


	var heatmapLayer = new HeatmapOverlay(cfg);
	var map;
    
	//heatmapLayer.setData(testData);

	


    

	//国测局坐标(火星坐标,比如高德地图在用),百度坐标,wgs84坐标(谷歌国外以及绝大部分国外在线地图使用的坐标)
    //百度经纬度坐标转国测局坐标
	//34.2863030000,117.0884960000
    //var bd09togcj02 = coordtransform.bd09togcj02(116.404, 39.915);
    //国测局坐标转百度经纬度坐标
    //var gcj02tobd09 = coordtransform.gcj02tobd09(116.404, 39.915);
    //wgs84转国测局坐标
    //var wgs84togcj02 = coordtransform.wgs84togcj02(116.404, 39.915);
    //国测局坐标转wgs84坐标
    //var gcj02towgs84 = coordtransform.gcj02towgs84(116.404, 39.915);
    //console.log(bd09togcj02);
    //console.log(gcj02tobd09);
    //console.log(wgs84togcj02);
    //console.log(gcj02towgs84);

//	var bd09togcj06 = coordtransform.bd09togcj02(117.1963015107, 34.2610739190);
  //  console.log("bd09togcj06="+bd09togcj06);
    //result
    //bd09togcj02:   [ 116.39762729119315, 39.90865673957631 ]
    //gcj02tobd09:   [ 116.41036949371029, 39.92133699351021 ]
    //wgs84togcj02:  [ 116.41024449916938, 39.91640428150164 ]
    //gcj02towgs84:  [ 116.39775550083061, 39.91359571849836 ]

	//34.276645,117.201102
	//34.2610739190,117.1963015107

//谷歌地图：34.2553224354,117.1897328511
//百度地图：34.2610739190,117.1963015107
//腾讯高德：34.2553136232,117.1897353973
//图吧地图：34.2562472632,117.1746107373
//谷歌地球：34.2565372632,117.1839607373
//北纬N34°15′23.53″ 东经E117°11′2.26″

//靠近：中国江苏省徐州市云龙区项王路1号
//周边：戏马台
//参考：江苏省徐州市云龙区彭城街道户部山社区东南方向

//leaflet实用插件整理
//https://www.giserdqy.com/gis/leaflet/4920/#23
//在地图中点击显示经纬度。或输入纬度,经度或地名后查询位置
//http://www.gpsspg.com/maps.htm

//jquery获取复选框值    
var chk_value;
var radio_value;
$(document).ready(function(){
	chk_value =[];
	$('input[name="checkbox11"]:checked').each(function(){//遍历每一个名字为interest的复选框，其中选中的执行函数    
		chk_value.push($(this).val());//将选中的值添加到数组chk_value中    
	});
	radio_value=$('input[name="TSLDivision.code"]').val();
	$("input[name='checkbox11']").change(function() { 
		chk_value =[];
		$('input[name="checkbox11"]:checked').each(function(){//遍历每一个名字为interest的复选框，其中选中的执行函数    
			chk_value.push($(this).val());//将选中的值添加到数组chk_value中    
		});
		aa();
	}); 
	$('#dd').combotree({
		url : 'slDivisionController.do?setPFunction&selfId=${depart.id}',
        width: 155,
        onSelect : function(node) {
        	radio_value=node.id;
        	aa();
        }
    });	 
	aa();
	
	jQuery.ajax({  
		type: 'POST',  
		dataType : "json",  
		url: 'slRiverLakeController.do?getAPictureInfo',
		success:function(data){
			//layer.msg("已经到达最上一级地图了!");
			if ("成功"==data.message){
				info_data=data.data.info_data;
				var home_info_01=data.data.home_info_01;
				var jsonstr=home_info_01[2].typename;
				var center00=JSON.parse(jsonstr);
				var zoom00=home_info_01[3].typename;
				
				
//				bd09togcj06=117.18970708714448,34.25537876602132
				//var ss=[34.2610739190,117.1963015107];
				//var bd09togcj06 = coordtransform.bd09togcj02(117.1963015107, 34.2610739190);
				//var bd09togcj06 = aa1({"lng":"117.1963015107","lat":"34.2610739190"});
				var bd09togcj06 = aa1({"lng":center00[0],"lat":center00[1]});
				//bd09togcj06
				//0 : 117.18970708714448
				//1 : 34.25537876602132
				//var ss=[bd09togcj06[1],bd09togcj06[0]];	
				//var ss=[bd09togcj06.lat,bd09togcj06[0]];

				var options = {
		            center: bd09togcj06, // The initial center(baidu BD-09 format) of map
		            attributionControl: false,//隐藏copyright
		            zoom: 11, // initial zoom of map
					crs: L.CRS.Baidu,
					layers: [new L.tileLayer.baidu({ layer: 'custom'}), heatmapLayer],
					zoomControl: false,
 					fullscreenControl: false,
					fullscreenControlOptions: { // optional
						title:"全屏"
					}
		        };

				map = new L.Map('map', options);
				//L.control.layers(baseLayers, null).addTo(map);

				L.control.zoom({
						zoomInTitle: '放大',
						zoomOutTitle: '缩小'
					}).addTo(map);
				L.control.sidebar('sidebar', {position: 'right'}).addTo(map);
				
				//var latlng = L.latLng(50.5, 30.5);
				//map.panTo([50, 30]);
				//map.panTo({lon: 30, lat: 50});
				//map.panTo({lat: 50, lng: 30});
				//map.panTo(L.latLng(50, 30));
				//L.marker(ss).addTo(map);
				
				
			}
	 	},
		error: function () {
			tip('服务器未响应，请稍后再试！');  
		}
	});
});

var info_data=[];
var myGroup;
var layers=[];  
var max = 0;
function aa1(obj_groups){
	//"{"lng":117.260966,"lat":34.616963}"
	//var cc = coordtransform.bd09togcj02(obj_groups.lng, obj_groups.lat);
	//return {'lng':cc[0],'lat':cc[1]};
	return obj_groups;
}
function aa(){
	if (layers.length==0){

	}else{
		layers =[];
		myGroup.clearLayers(); 
	}
	testData = {
	  max: 0,
	  data: [
	  ]
	};
	if (chk_value.length==0||radio_value==""){
		console.log("111");
	}else{
		console.log("chk_value=="+chk_value);
		console.log("radio_value=="+radio_value);
		var testData_data=[];  
		for( var i=0;i<info_data.length;i++ ){
			var curdata=info_data[i];
			var river_lake_type=curdata.river_lake_type;
			var count0=curdata.count0;
			var mapCenterLngLat=curdata.mapCenterLngLat;
			var name=curdata.name;
			var division_divisionCode=curdata.division_divisionCode;
			if (radio_value==division_divisionCode){
				if (chk_value.indexOf("11")>=0&&(river_lake_type=="10"||river_lake_type=="11"||river_lake_type=="12"||river_lake_type=="13")){
					var obj_groups = JSON.parse(mapCenterLngLat);
					//var cc = coordtransform.bd09togcj02(obj_groups.lat, obj_groups.lng);
					var cc = aa1(obj_groups);
					count0=count0+1;
					max = Math.max(max, count0);
					var jsonObj={'lat':cc.lat,'lng':cc.lng,count:count0};
					testData_data.push(jsonObj);
				}
				if (chk_value.indexOf("15")>=0&&(river_lake_type=="10"||river_lake_type=="11"||river_lake_type=="12"||river_lake_type=="13")){
					var obj_groups = JSON.parse(mapCenterLngLat);
					//var cc = coordtransform.bd09togcj02(obj_groups.lat, obj_groups.lng);
					var cc = aa1(obj_groups);
					if (division_divisionCode.length==3){
						var layer = L.marker(cc,{icon:shengji})
							.bindPopup("河湖:"+name);
						layers.push(layer); 						
					}
					if (division_divisionCode.length==6){
						var layer = L.marker(cc,{icon:shiji})
							.bindPopup("河湖:"+name);
						layers.push(layer); 						
					}
					if (division_divisionCode.length==9){
						var layer = L.marker(cc,{icon:xianji})
							.bindPopup("河湖:"+name);
						layers.push(layer); 						
					}
					if (division_divisionCode.length==12){
						var layer = L.marker(cc,{icon:xiangji})
							.bindPopup("河湖:"+name);
						layers.push(layer); 						
					}
					if (division_divisionCode.length==15){
						var layer = L.marker(cc,{icon:cunji})
							.bindPopup("河湖:"+name);
						layers.push(layer); 						
					}
				}
				if (chk_value.indexOf("12")>=0&&(river_lake_type=="21"||river_lake_type=="22")){
					if (river_lake_type=="21"){
						var obj_groups = JSON.parse(mapCenterLngLat);
						//var cc = coordtransform.bd09togcj02(obj_groups.lat, obj_groups.lng);
						var cc = aa1(obj_groups);
						if (count0==10){
							var layer = L.marker(cc,{icon:duanmian1})
								.bindPopup("断面:"+name+",水质:Ⅰ");
							layers.push(layer);  
						}
						if (count0==11){
							var layer = L.marker(cc,{icon:duanmian2})
								.bindPopup("断面:"+name+",水质:Ⅱ");
							layers.push(layer);  
						}
						if (count0==12){
							var layer = L.marker(cc,{icon:duanmian3})
								.bindPopup("断面:"+name+",水质:Ⅲ");
							layers.push(layer);  
						}
						if (count0==13){
							var layer = L.marker(cc,{icon:duanmian4})
								.bindPopup("断面:"+name+",水质:Ⅳ");
							layers.push(layer);  
						}
						if (count0==14){
							var layer = L.marker(cc,{icon:duanmian5})
								.bindPopup("断面:"+name+",水质:Ⅴ");
							layers.push(layer);  
						}
					}
					if (river_lake_type=="22"){
						var obj_groups = JSON.parse(mapCenterLngLat);
						//var cc = coordtransform.bd09togcj02(obj_groups.lat, obj_groups.lng);
						var cc = aa1(obj_groups);
						var layer = L.marker(cc,{icon:dmsz})
							.bindPopup("测站:"+name);
						layers.push(layer);  
					}
				}
				if (chk_value.indexOf("13")>=0&&river_lake_type=="23"){
					var obj_groups = JSON.parse(mapCenterLngLat);
					//var cc = coordtransform.bd09togcj02(obj_groups.lat, obj_groups.lng);
					var cc = aa1(obj_groups);
					var layer = L.marker(cc,{icon:paiwukou})
						.bindPopup("排污口:"+name);
					layers.push(layer);  
				}
				if (chk_value.indexOf("14")>=0&&river_lake_type=="20"){
					var obj_groups = JSON.parse(mapCenterLngLat);
					//var cc = coordtransform.bd09togcj02(obj_groups.lat, obj_groups.lng);
					var cc = aa1(obj_groups);
					var layer = L.marker(cc,{icon:qushuikou})
						.bindPopup("取水口:"+name);
					layers.push(layer);  
				}
			}
		}
		
		myGroup=L.layerGroup(layers);  
		myGroup.addTo(map);
		
		testData = {max:max,data:testData_data};
	}
	heatmapLayer.setData(testData);
}
  </script>
</body>
</html>