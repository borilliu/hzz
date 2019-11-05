<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true" style="padding: 0px;">
	<div region="center" style="padding: 0px; border: 0px;">
		<t:datagrid name="entityList" checkbox="false" pagination="true"
			fitColumns="true" title="巡河报表" sortName="createDate"
			actionUrl="slRiverLakeController.do?datagridpr" idField="id"
			fit="true" queryMode="group">
			<t:dgCol title="common.id" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="区划名称" field="tslDivision.divisionName"
				sortable="false" query="false" width="100"></t:dgCol>
			<t:dgCol title="年月" field="ym" sortable="false" query="false"
				width="100"></t:dgCol>
			<t:dgCol title="应巡人数 " field="shouldNumber" sortable="false"
				query="false" width="100"></t:dgCol>
			<t:dgCol title="已巡人数" field="visitedNumber" sortable="false"
				query="false" width="100"></t:dgCol>
			<t:dgCol title="完成率" formatterjs="formatFun"
				field="tslDivision.code" sortable="false" query="false"
				width="100"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
			<t:dgDelOpt title="删除"
				url="slRiverLakeController.do?delEntityPR&id={id}"
				urlclass="ace_button" urlStyle="background-color:#ec4758;"
				urlfont="fa-trash-o" />
			<t:dgToolBar icon="icon-add" title="common.add" id="addEntityId"
				url="slRiverLakeController.do?addorupdateEntityPR"
				funname="addEntity"></t:dgToolBar>
			<t:dgToolBar icon="icon-edit" title="common.edit"
				url="slRiverLakeController.do?addorupdateEntityPR"
				funname="updateEntity"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script type="text/javascript">
 function formatFun(value,row,index){

	var shouldNumber=row.shouldNumber;
	var visitedNumber=row.visitedNumber;
	var a=parseFloat(shouldNumber);
	var b=parseFloat(visitedNumber);
	var rvalue = ((b/a)*100).toFixed(2);
	return rvalue+"%";
	 
 }
 function addEntity(title, url, id) {
		add(title, url, 'entityList');
	}
 function updateEntity(title, url, id) {
	update(title, url, 'entityList');
}
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'slDivisionController.do?uploadDivision', "slDivisionList");
}

//导出
function ExportXls() {
	JeecgExcelExport("slDivisionController.do?exportXls","slDivisionList");
}

//模板下载
function ExportXlsByT() {
	//JeecgExcelExport("slDivisionController.do?exportXlsByT","slDivisionList");
	location.href = "../../../../../../export/template/divisionTemplate.xls";
}


function reloadTreeNode(){
	var node = $('#slDivisionList').treegrid('getSelected');
    if (node) {
	   	 var pnode = $('#slDivisionList').treegrid('getParent',node.id);
	   	 if(pnode){
	   		 if(node.parentId==""){
	   			$('#slDivisionList').treegrid('reload');
	   		 }else{
	   	 		$('#slDivisionList').treegrid('reload',pnode.id);
	   		 }
	   	 }else{
	   		if(node.parentId==""){
	   			$('#slDivisionList').treegrid('reload');
	   		 }else{
	   			$('#slDivisionList').treegrid('reload',node.id);
	   		 }
	   	 }
    }else{
    	 $('#slDivisionList').treegrid('reload');
    }
}
/**
 * 获取表格对象
 * @return 表格对象
 */
function getDataGrid(){
	var datagrid = $('#'+gridname);
	return datagrid;
}
 </script>
