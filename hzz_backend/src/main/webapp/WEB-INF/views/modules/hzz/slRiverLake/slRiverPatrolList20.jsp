<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="jformOrderMain2List" checkbox="false"
			filterBtn="true" fit="true" fitColumns="true" title=""
			actionUrl="slRiverLakeController.do?patrolDataGrid20" idField="id"
			queryMode="group"
			extendParams="checkOnSelect:false,onSelect:function(index,row){datagridSelect(index,row);}"
			showFooter="false" pagination="false" sortOrder="asc"
			sortName="photoDate" onLoadSuccess="loadSuccess">
			<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="photoLng" field="photoLng" hidden="true"></t:dgCol>
			<t:dgCol title="photoLat" field="photoLat" hidden="true"></t:dgCol>
			<t:dgCol title="时间" field="photoDate" formatter="yyyy-MM-dd hh:mm:ss"
				width="100"></t:dgCol>
			<t:dgCol title="图片" field="phono" width="100"
				formatterjs="formatPhotoFun"></t:dgCol>
			<t:dgCol title="地点" field="tslBaseRiverLakePatrol.id" width="100"
				formatterjs="formatAddressFun"></t:dgCol>
		</t:datagrid>
	</div>
</div>
<script type="text/javascript"
	src="plug-in/mutitables/mutitables.urd.js"></script>
<script type="text/javascript"
	src="plug-in/mutitables/mutitables.curdInIframe.js"></script>
<script type="text/javascript">
$(function(){
	  curd = $.curdInIframe({
		  name:"jformOrderMain2",
		  isMain:true,
		  describe:"订单信息",
		  form:{width:'100%',height:'100%'},
	  });
	  gridname = curd.getGridname();
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
            rvalue += "<img onclick='viewimg(\"" + value + "\")' style='cursor:hand;width:100px; height: 100px;' src='${webRoot}/" + strs[i] + "' title='点击查看图片'/>";
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
            rvalue += "<img onclick='viewaddress(\"" + row.photoLat + "\",\""+row.photoLng+"\")' style='cursor:hand;width:100px; height: 100px;' src='${webRoot}/plug-in/viewer/timg.jpg' title='点击查看图片'/>";
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
function loadSuccess() {
   //alert("223");
   return;
}	
</script>