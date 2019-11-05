<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="jformOrderMain2List" checkbox="false"
			filterBtn="true" fit="true" fitColumns="true" title=""
			actionUrl="slRiverLakeController.do?patrolDataGrid" idField="id"
			queryMode="group"
			extendParams="checkOnSelect:false,onSelect:function(index,row){datagridSelect(index,row);}">
			<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="巡河人员" field="riverLakeChief.name" width="100"></t:dgCol>
			<t:dgCol title="河道" field="tslBaseRiverLake.name"></t:dgCol>
			<t:dgCol title="巡河开始时间" field="patrolStartDate"
				formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
			<t:dgCol title="巡河结束时间" field="patrolEndDate"
				formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
			<t:dgCol title="巡河时长(分钟)" field="patrolDuration"></t:dgCol>
			<t:dgCol title="巡河里程(公里)" field="patrolMileage"></t:dgCol>
			<t:dgCol title="巡河手机型号" field="patrolMobileModel"></t:dgCol>
			<t:dgCol title="巡河App版本号" field="patrolAppVer"></t:dgCol>
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
	$('#jformOrderMain2List').datagrid('unselectAll');
	parent.initSubList(row.id);
}
/**
* 主页面重置调用方法
*/
function queryResetit(){
	searchReset('jformOrderMain2List');
	jformOrderMain2Listsearch();
}

</script>