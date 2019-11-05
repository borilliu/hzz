<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
<head>
<title>河长集合</title>
<t:base type="jquery"></t:base>
<link rel="stylesheet" type="text/css"
	href="plug-in/ztree/css/metroStyle.css">
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.2"></script>
<style type="text/css">
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
<body style="overflow-y: auto" scroll="no">
	<ul id="container"
		style="width: 540px; height: 500px; border: 1px solid gray"></ul>
	<p>
		<input id="paramType" name="paramType" type="hidden" value="">
		<input id="paramTypeValue" name="paramTypeValue" type="hidden"
			value=""> <input type="button" onclick="getinfo();"
			style="display: none" value="地图基本信息" /> <input id="startBtn"
			type="button" onclick="startTool();" style="display: none"
			value="开启取点工具" /> <input type="button"
			onclick="map.clearOverlays();document.getElementById('info').innerHTML = '';points=[];"
			value="清除" />
	</p>
	<div id="info" style="display: none"></div>
</body>
</html>
<script type="text/javascript">
var map = new BMap.Map("container");                        // 创建Map实例
map.centerAndZoom("徐州", 11);     // 初始化地图,设置中心点坐标和地图级别
//map.centerAndZoom(new BMap.Point(116.403765, 39.914850), 5);
map.enableScrollWheelZoom(true);

var scrollFunc=function(e){
	e=e || window.event;
	//var t1=document.getElementById("mapInfo");
	//t1.value=
	//var ss=map.getZoom();
	//document.getElementById("info").innerHTML +="map.getZoom="+ss+",</br>";
}
/*注册事件*/
if(document.addEventListener){
	//document.addEventListener('DOMMouseScroll',scrollFunc,false);
}//W3C
window.onmousewheel=scrollFunc;//IE/Opera/Chrome

$("#paramType").val("${paramType}");
var key =${paramType};    //开关
var newpoint;   //一个经纬度
var points = [];    //数组，放经纬度信息
var polyline = new BMap.Polyline(); //折线覆盖物


function getinfo(){
	map.clearOverlays();
	document.getElementById('info').innerHTML = '';
	points=[];
	
	var ss=map.getZoom();
	document.getElementById("info").innerHTML +="map.getZoom="+ss+",</br>";
	document.getElementById("info").innerHTML +='当前地图中心点(例子:{"lng":117.156475,"lat":34.244185})==="lng":' + map.getCenter().lng + ',"lat":' + map.getCenter().lat+'</br>';
	
	
	
	var point = new BMap.Point(map.getCenter().lng, map.getCenter().lat);//创建点坐标
	var marker = new BMap.Marker(point);  // 创建标注
	map.addOverlay(marker); 
}
function startTool(){   //开关函数
	if(key==11){
        document.getElementById("startBtn").style.background = "green";
        document.getElementById("startBtn").style.color = "white";
        document.getElementById("startBtn").value = "开启状态";
        key=0;
    }
    else{
        document.getElementById("startBtn").style.background = "red";
        document.getElementById("startBtn").value = "关闭状态";
        key=1;
    }
}
map.addEventListener("click",function(e){   //单击地图，形成折线覆盖物
    if(key==11){
        newpoint = new BMap.Point(e.point.lng,e.point.lat);
    	//document.getElementById("info").innerHTML +="当前地图中心点：" + e.point.lng + "," + e.point.lat+",</br>";
    	//if(points[points.length].lng==points[points.length-1].lng){alert(111);}
        points.push(newpoint);  //将新增的点放到数组中
        polyline.setPath(points);   //设置折线的点数组
        map.addOverlay(polyline);   //将折线添加到地图上
        document.getElementById("info").innerHTML += '{"lng":' + e.point.lng + ',"lat":' + e.point.lat + '},';    //输出数组里的经纬度"
        
        $("#paramTypeValue").val(document.getElementById("info").innerHTML);
    }
    if(key==10){
    	map.clearOverlays();
    	document.getElementById('info').innerHTML = '';
    	points=[];
    	var point = new BMap.Point(e.point.lng,e.point.lat);//创建点坐标
    	var marker = new BMap.Marker(point);  // 创建标注
    	map.addOverlay(marker); 
    	
    	
    	var zoom=map.getZoom();
    	
    	var aa={'zoom':zoom,'lng':e.point.lng,'lat':e.point.lat};
    	$("#paramTypeValue").val(JSON.stringify(aa));
    }
});
map.addEventListener("dblclick",function(e){   //双击地图，形成多边形覆盖物
	if(key==11){
        map.disableDoubleClickZoom();   //关闭双击放大
		var polygon = new BMap.Polygon(points);
        map.addOverlay(polygon);   //将折线添加到地图上
    }
});
</script>
