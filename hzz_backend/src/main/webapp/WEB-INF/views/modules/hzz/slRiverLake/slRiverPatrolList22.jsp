<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="jformOrderMain2List" checkbox="false"
			filterBtn="true" fit="true" fitColumns="true" title=""
			actionUrl="slRiverLakeController.do?patrolDataGrid22" idField="id"
			queryMode="group"
			extendParams="checkOnSelect:false,onSelect:function(index,row){datagridSelect(index,row);}"
			showFooter="false" pagination="false" sortOrder="asc"
			sortName="eventDate" onLoadSuccess="loadSuccess">
			<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="eventLng" field="eventLng"></t:dgCol>
			<t:dgCol title="eventLat" field="eventLat"></t:dgCol>
			<t:dgCol title="eventAddress" field="eventAddress"></t:dgCol>
			<t:dgCol title="eventDate" field="eventDate"
				formatter="yyyy-MM-dd hh:mm:ss" width="100"></t:dgCol>
			<t:dgCol title="memo" field="memo"></t:dgCol>
			<t:dgCol title="name" field="name"></t:dgCol>
			<t:dgCol title="code" field="code"></t:dgCol>
			<t:dgCol title="handlememo" field="handlememo"></t:dgCol>
			<t:dgCol title="sourceType" field="sourceType"
				dictionary="sourceType"></t:dgCol>
			<t:dgCol title="questType" field="questType" dictionary="questType"></t:dgCol>
			<t:dgCol title="status" field="status" dictionary="eventStatus"></t:dgCol>
			<t:dgCol title="tempName" field="tempName"></t:dgCol>
		</t:datagrid>
		<ul id="container"
			style="width: 540px; height: 500px; border: 1px solid gray; display: none; overflow: auto;"></ul>
	</div>
</div>
<script type="text/javascript"
	src="plug-in/mutitables/mutitables.urd.js"></script>
<script type="text/javascript"
	src="plug-in/mutitables/mutitables.curdInIframe.js"></script>

<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.2"></script>

<script type="text/javascript">
$(function(){
	  curd = $.curdInIframe({
		  name:"jformOrderMain2",
		  isMain:true,
		  describe:"订单信息",
		  form:{width:'100%',height:'100%'},
	  });
	  gridname = curd.getGridname();
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

function viewprocess(stprocess){
	//alert(stprocess);
	var st1="";
	st1=st1+"<div id=\"divInspectEventPicture\" style=\"width:600px;height:500px;overflow:auto;\">";
	st1=st1+"<table style=\"width:100%;height:100%;border: 1px solid #ececec;background-color: #FFF;padding: 3px 10px 3px 20px;font-size: 15px;\"><tbody><tr>";
	for(var i=0;i<stalldata.rows.length;i++){
		if (i==stprocess){
			var tempName=stalldata.rows[i]["tempName"];
			var tempJson=JSON.parse(tempName);
			tempJson.EventProcess
			for(var j=0;j<tempJson.EventProcess.length;j++){
				var processDate=tempJson.EventProcess[j].processDate;
				var Memo=tempJson.EventProcess[j].Memo;
				
				st1=st1+"<td style=\"width:40%;border: 1px solid #ececec;height: 45px;\">"+processDate+"</td>";
				st1=st1+"<td style=\"width:60%;border: 1px solid #ececec;height: 45px;\">"+Memo+"</td>";
				st1=st1+"</tr><tr>";
			}
			break;
		}
	}
	st1 += "</tr></tbody></table>";
	st1=st1+"</div>";

	$.dialog.setting.zIndex = getzIndex(); 
	$.dialog({
	    content: st1,
	    zIndex: getzIndex(),
	    title: '处理列表',
	    lock: true,
	    width: '600px',
	    height: '600px',
	    opacity: 0.4,
	    button: [
	    {
	        name: '<t:mutiLang langKey="common.cancel"/>',
	        callback: function() {}
	    }]
	}).zindex();
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
var stalldata;
function loadSuccess(data) {
	//return;

	//alert("12");
	//$(".gridview").hide();
	//$("#container").hide();
	$("#container").show();	
	$("#container").width($(".panel-noscroll").width());
	$("#container").height($(".panel-noscroll").height());
	if (data&&data.rows.length>0) {
		stalldata=data;
		var st1="";
		for(var i=0;i<data.rows.length;i++){
			var code=data.rows[i]["code"];
			var eventDate=data.rows[i]["eventDate"];
			var eventLat=data.rows[i]["eventLat"];
			var eventLng=data.rows[i]["eventLng"];
			var handlememo=data.rows[i]["handlememo"];
			var id=data.rows[i]["id"];
			var memo=data.rows[i]["memo"];
			var name=data.rows[i]["name"];
			var tempName=data.rows[i]["tempName"];
			var eventAddress=data.rows[i]["eventAddress"];
			var tempJson=JSON.parse(tempName);
			
			
		
			var i1=i+1;

			st1=st1+"<h3 style=\"margin-top: 5px;\">事件"+i1+"</h3>";
			st1=st1+"<table style=\"width:100%;\"><tbody><tr>";
				st1=st1+"<td style=\"width:15%;\">事件编号：</td>";
				st1=st1+"<td style=\"width:35%\">"+code+"</td>";
				st1=st1+"<td style=\"width:15%;\">处理状态</td>";
				st1=st1+"<td style=\"width:35%; color:green;\"><span style=\"cursor:hand;\" onclick='viewprocess(\"" + i + "\")'>"+tempJson.eventstatus.typename+"</span></td>";
			st1=st1+"</tr><tr>";
				st1=st1+"<td style=\"width:15%;\">事件地址：</td>";
				st1=st1+"<td style=\"width:35%\">";
				st1=st1+"<img onclick='viewaddress(\"" + eventLat + "\",\""+eventLng+"\")' src=\"${webRoot}/plug-in/viewer/timg.jpg\" style=\"cursor:hand;width:50px; height:50px; margin-right:20px;\">";
				st1=st1+eventAddress;
				st1=st1+"</td>";
				st1=st1+"<td style=\"width:15%;\">事件类型</td>";
				st1=st1+"<td style=\"width:35%;\">"+tempJson.questtype.typename+"</td>";
			st1=st1+"</tr><tr>";
				st1=st1+"<td>事件描述：</td><td colspan=\"3\">"+memo+"</td>";
			st1=st1+"</tr><tr>";
				st1=st1+"<td>事件地址图片：</td><td colspan=\"3\">";
				st1=st1+"<div id=\"divInspectEventPicture\" style=\"height:130px;overflow:auto;\"><table><tbody><tr>";
					for(var j=0;j<tempJson.EventPhoto.length;j++){
						var Photo=tempJson.EventPhoto[j].Photo;
						st1=st1+"<td style=\"border:0px; padding:0px;\">";
						st1=st1+"<img onclick='viewimg(\"" + Photo + "\")' src=\"${webRoot}/"+Photo+"\" style=\"cursor:hand;width:120px; height:120px; margin-right:20px;\">";
						st1=st1+"</td>";
					}
				st1=st1+"</tr></tbody></table></div></td>";
			st1=st1+"</tr><tr>";
			st1=st1+"<td>事件处理：</td><td colspan=\"3\">"+handlememo+"</td>";
			st1=st1+"</tr><tr>";
				st1=st1+"<td>事件处理图片：</td><td colspan=\"3\">";
				st1=st1+"<div id=\"divInspectEventPicture\" style=\"height:130px;overflow:auto;\"><table><tbody><tr>";
				for(var j=0;j<tempJson.EventPhoto1.length;j++){
					var Photo=tempJson.EventPhoto1[j].Photo;
					st1=st1+"<td style=\"border:0px; padding:0px;\">";
					st1=st1+"<img onclick='viewimg(\"" + Photo + "\")' src=\"${webRoot}/"+Photo+"\" style=\"width:120px; height:120px; margin-right:20px;\">";
					st1=st1+"</td>";
				}
			st1=st1+"</tr></tbody></table></div></td>";
			st1=st1+"</tr></tbody></table>";
		}
		$("#container").html(st1);
	}else{
		$("#container").html("");
	}
   return;
}		
</script>
<style>
#container td {
	border: 1px solid #ececec;
	background-color: #FFF;
	padding: 3px 10px 3px 20px;
	height: 45px;
	font-size: 15px;
}
</style>