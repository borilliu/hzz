<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="jformOrderMain2List" checkbox="false"
			filterBtn="true" fit="true" fitColumns="true" title=""
			actionUrl="slRiverLakeController.do?patrolDataGrid21" idField="id"
			queryMode="group"
			extendParams="checkOnSelect:false,onSelect:function(index,row){datagridSelect(index,row);}"
			showFooter="false" pagination="false" sortOrder="asc"
			sortName="gpsDate" onLoadSuccess="loadSuccess">
			<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="gpsLng" field="gpsLng"></t:dgCol>
			<t:dgCol title="gpsLat" field="gpsLat"></t:dgCol>
			<t:dgCol title="gpsDate" field="gpsDate"
				formatter="yyyy-MM-dd hh:mm:ss" width="100"></t:dgCol>
			<t:dgCol title="mapCenterLngLat"
				field="tslBaseRiverLakePatrol.tslBaseRiverLake.mapCenterLngLat"></t:dgCol>
			<t:dgCol title="mapZoom"
				field="tslBaseRiverLakePatrol.tslBaseRiverLake.mapZoom"></t:dgCol>
		</t:datagrid>
		<ul id="container"
			style="width: 540px; height: 500px; border: 1px solid gray; display: none;"></ul>
	</div>
</div>
<script type="text/javascript"
	src="plug-in/mutitables/mutitables.urd.js"></script>
<script type="text/javascript"
	src="plug-in/mutitables/mutitables.curdInIframe.js"></script>

<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=7aLuDD5cNosFGfxoyzrWlKZwbA6oAzFK"></script>
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

<script type="text/javascript">
$(function(){
	  curd = $.curdInIframe({
		  name:"jformOrderMain2",
		  isMain:true,
		  describe:"订单信息",
		  form:{width:'100%',height:'100%'},
	  });
	  gridname = curd.getGridname();
	  //$(".gridview").hide();
	  //$("#container").hide();
	  $("#container").show();
	  $("#container").width($("#panel-noscroll").width());
	  $("#container").height($("#panel-noscroll").height());
});
//formatterjs 例子--------------------
//单元格的格式化函数  value：字段的值 row：行的记录数据 index：行的索引
function formatPhotoFun(value,row,index){

	if(value!=null&&value.length>0){
        var strs = new Array(); //定义一数组
        //value = value.substr(1, value.length - 1);
        strs = value.split(","); //字符分割
        var rvalue = "";
        for (var i = 0; i < strs.length; i++) {
            rvalue += "<img onclick='viewimg(\"" + value + "\")' style='width:100px; height: 100px;' src='${webRoot}/" + strs[i] + "' title='点击查看图片'/>";
        }
        return rvalue;
    }
}
function viewimg(img){

    parent.viewimg(img);
}
function formatAddressFun(value,row,index){

	if(value!=null&&value.length>0){
        var strs = new Array(); //定义一数组
        //value = value.substr(1, value.length - 1);
        strs = value.split(","); //字符分割
        var rvalue = "";
        for (var i = 0; i < strs.length; i++) {
            rvalue += "<img onclick='viewaddress(\"" + row.photoLat + "\",\""+row.photoLng+"\")' style='width:100px; height: 100px;' src='${webRoot}/plug-in/viewer/timg.jpg' title='点击查看图片'/>";
        }
        return rvalue;
    }
}
function viewaddress(photoLat,photoLng){

	$.dialog.setting.zIndex = getzIndex(); 

	$.dialog({
	    content: 'url:slRiverLakeController.do?mapDetail2&photoLat='+photoLat+'&photoLng='+photoLng,
	    zIndex: getzIndex(),
	    title: '详细地址',
	    lock: true,
	    width: '600px',
	    height: '600px',
	    opacity: 0.4,
	    button: [{
	        name: '<t:mutiLang langKey="common.confirm"/>',
	        callback: function() {},
	        focus: true
	    },
	    {
	        name: '<t:mutiLang langKey="common.cancel"/>',
	        callback: function() {}
	    }]
	}).zindex();
}
/**
* 双击事件开始编辑
*/
function datagridDbclick(index,field,value){
	$("#jformOrderMain2List").datagrid('beginEdit', index);
}
/**
* 选中事件加载子表数据
*/
function datagridSelect(index,row){
	//$('#jformOrderMain2List').datagrid('unselectAll');
	//parent.initSubList(row.id);
}
/**
* 主页面重置调用方法
*/
function queryResetit(){
	searchReset('jformOrderMain2List');
	jformOrderMain2Listsearch();
}
function loadSuccess(data) {

	//alert("12");
	//$(".gridview").hide();
	//$("#container").hide();
	$("#container").show();	
	$("#container").width($(".panel-noscroll").width());
	$("#container").height($(".panel-noscroll").height());
	if (data&&data.rows.length>0) {
		var mapCenterLngLat="";
		var mapZoom="";
		var newChartData = []; 
		for(var i=0;i<data.rows.length;i++){
			if (i==0){
				mapCenterLngLat=data.rows[i]["tslBaseRiverLakePatrol.tslBaseRiverLake.mapCenterLngLat"];
				mapZoom=data.rows[i]["tslBaseRiverLakePatrol.tslBaseRiverLake.mapZoom"];	
			}
			var gpsLat=data.rows[i]["gpsLat"];
			var gpsLng=data.rows[i]["gpsLng"];
			
			var ipoint=new BMap.Point(gpsLng, gpsLat);
			newChartData.push(ipoint);
		}

		var groups_code =mapCenterLngLat;
		var obj_groups = JSON.parse(groups_code);
		
		var map = new BMap.Map("container");                        // 创建Map实例
		//map.centerAndZoom("徐州", 11);     // 初始化地图,设置中心点坐标和地图级别
		map.centerAndZoom(new BMap.Point(obj_groups.lng, obj_groups.lat), mapZoom);
		//map.enableScrollWheelZoom(true);
		map.addControl(new BMap.NavigationControl());    
		map.addControl(new BMap.ScaleControl());    
		map.addControl(new BMap.OverviewMapControl());    
		map.addControl(new BMap.MapTypeControl());    
		var firstPoint;
        var endPoint;
      //为了获得起点及终点的坐标值,方便对它进行文字处理
        firstPoint = newChartData[0];
        endPoint = newChartData[newChartData.length - 1];
        
        var sym = new BMap.Symbol
        (
            BMap_Symbol_SHAPE_BACKWARD_OPEN_ARROW, //百度预定义的 箭头方向向下的非闭合箭头
            {
                fillColor : '#0F0', //设置矢量图标的填充颜色。支持颜色常量字符串、十六进制、RGB、RGBA等格式
                fillOpacity : 1, //设置矢量图标填充透明度,范围0~1
                scale : 0.5, //设置矢量图标的缩放比例
                rotation : 90, //设置矢量图标的旋转角度,参数为角度
                strokeColor: "white", //折线颜色
                strokeOpacity : 1, //设置矢量图标线的透明度,opacity范围0~1
                strokeWeight : 4, //旋设置线宽。如果此属性没有指定，则线宽跟scale数值相
            }
        );
        
        var iconSequence = new BMap.IconSequence
        (
            sym, //symbol为符号样式
            '5%', //offset为符号相对于线起点的位置，取值可以是百分比也可以是像素位置，默认为"100%"
            '5%', //repeat为符号在线上重复显示的距离，可以是百分比也可以是距离值，同时设置repeat与offset时，以repeat为准
            false //fixedRotation设置图标的旋转角度是否与线走向一致，默认为true
        );
        
		var polyline = new BMap.Polyline(newChartData, {
            icons : [iconSequence], //图标集合  **也是我之前没有实现样式改变的最大原因**
            strokeColor : 'blue', //折线颜色 尽量与图标填充色保持一样
            strokeOpacity: 1, //线透明度
            strokeWeight: 8, //线宽
            strokeStyle: "solid", //线样式
            showDir: true,
            lineJoin: "round",
            lineCap: "round",
            strokeDasharray: [10, 5] //补充线样式
        });
        map.addOverlay(polyline);
        
      	//对起点、终点、途经点做一个简单的处理，泡泡跟文字提示
        var m1 = new BMap.Marker(firstPoint);
        var icon1 = new BMap.Icon('images/start.png',new BMap.Size(43,59));
        icon1.imageSize = new BMap.Size(43,59);
        icon1.anchor = new BMap.Size(22,59);
        m1.setIcon(icon1); 
      
        var infoWindow1 = new BMap.InfoWindow("起点", { enableMessage: false, width: 30, height: 20 });
        m1.addEventListener("click", function ()
        {
            this.openInfoWindow(infoWindow1);
        });
        
        var m2 = new BMap.Marker(endPoint);
        var icon2 = new BMap.Icon('images/end.png',new BMap.Size(43,59));
        icon2.imageSize = new BMap.Size(43,59);
        icon2.anchor = new BMap.Size(22,59);
        m2.setIcon(icon2); 
        
        var infoWindow2 = new BMap.InfoWindow("终点", { enableMessage: false, width: 30, height: 20 });
        m2.addEventListener("click", function ()
        {
            this.openInfoWindow(infoWindow2);
        });
        map.addOverlay(m1);
        map.addOverlay(m2);
        //var lab1 = new BMap.Label("起点", { position: firstPoint });
        //var lab2 = new BMap.Label("终点", { position: endPoint });
        //map.addOverlay(lab1);
        //map.addOverlay(lab2);
		                                
	}else{
		$("#container").html("");
	}
   return;
}		
</script>