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
	<div id="info"></div>
</body>
</html>
<script type="text/javascript">
var mapZoom=15;


var photoLat ='${photoLat}';
var photoLng ='${photoLng}';


var map = new BMap.Map("container");                        // 创建Map实例
//map.centerAndZoom("徐州", mapZoom);     // 初始化地图,设置中心点坐标和地图级别
//map.centerAndZoom(new BMap.Point(116.403765, 39.914850), 5);
map.centerAndZoom(new BMap.Point(photoLng,photoLat), mapZoom);
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


var key = 1;    //开关
var newpoint;   //一个经纬度
var points = [];    //数组，放经纬度信息
var polyline = new BMap.Polyline(); //折线覆盖物

function getinfo(){
	var ss=map.getZoom();
	document.getElementById("info").innerHTML +="map.getZoom="+ss+",</br>";
	document.getElementById("info").innerHTML +="当前地图中心点'lng':" + map.getCenter().lng + ",'lat':" + map.getCenter().lat+",</br>";
}
function startTool(){   //开关函数
	if(key==1){
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
    newpoint = new BMap.Point(e.point.lng,e.point.lat);
	//document.getElementById("info").innerHTML +="当前地图中心点：" + e.point.lng + "," + e.point.lat+",</br>";
    if(key==0){
    //    if(points[points.length].lng==points[points.length-1].lng){alert(111);}
        points.push(newpoint);  //将新增的点放到数组中
        polyline.setPath(points);   //设置折线的点数组
        map.addOverlay(polyline);   //将折线添加到地图上
        document.getElementById("info").innerHTML += "{'lng':" + e.point.lng + ",'lat':" + e.point.lat + "},</br>";    //输出数组里的经纬度
    }
});
map.addEventListener("dblclick",function(e){   //双击地图，形成多边形覆盖物
	if(key==0){
        map.disableDoubleClickZoom();   //关闭双击放大
		var polygon = new BMap.Polygon(points);
        map.addOverlay(polygon);   //将折线添加到地图上
    }
});



var marker1 = new BMap.Marker(new BMap.Point(photoLng,photoLat));
var label1 = new BMap.Label("当前地点", { offset: new BMap.Size(20, 0) });
label1.setStyle({
    color: "red",
    fontSize: "9px",
    backgroundColor: "0.05",
    border: "0",
    fontWeight: "bold"
});
marker1.setLabel(label1);
marker1.addEventListener(
"click",
function () {
    var sContent =
        "<form method='post' action=''>" +
            "<table>" +
                "<tr>" +
                    "<td>当前地点" +
                    "</td>" +
                "</tr>" +                     
            "</talbe>" +
        "</form>";
    var opts = {
        enableMessage: false
    };
    var infoWindow = new BMap.InfoWindow(sContent, opts);
    this.openInfoWindow(infoWindow);
});
map.addOverlay(marker1);
</script>
