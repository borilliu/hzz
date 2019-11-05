<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true" style="padding: 0px;">
	<div region="center" style="padding: 0px; border: 0px;">
		<t:datagrid name="entityList" checkbox="false" pagination="true"
			fitColumns="true" title="水质报表" sortName="createDate"
			actionUrl="slRiverLakeController.do?datagridwq" idField="id"
			fit="true" queryMode="group">
			<t:dgCol title="common.id" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="代码" field="tslRLProjectWaterFunction.code"
				sortable="false" query="false" width="100"></t:dgCol>
			<t:dgCol title="名称" field="tslRLProjectWaterFunction.name"
				sortable="false" query="false" width="100"></t:dgCol>
			<t:dgCol title="所属流域" field="tslRLProjectWaterFunction.parent.name"
				sortable="false" query="false" width="100"></t:dgCol>
			<t:dgCol title="一级水功能区" field="tslRLProjectWaterFunction.primaryName"
				sortable="false" query="false" width="100"></t:dgCol>
			<t:dgCol title="二级水功能区"
				field="tslRLProjectWaterFunction.secondaryName" sortable="false"
				query="false" width="100"></t:dgCol>
			<t:dgCol title="监测断面"
				field="tslRLProjectWaterFunction.monitoringSection" sortable="false"
				query="false" width="100"></t:dgCol>
			<t:dgCol title="水质目标" field="tslRLProjectWaterFunction.waterQuality"
				dictionary="waterQuality" sortable="false" query="false" width="100"></t:dgCol>
			<t:dgCol title="年月" field="ym" sortable="false" query="false"
				width="100"></t:dgCol>
			<t:dgCol title="本月水质类别" field="waterQuality"
				dictionary="waterQuality" sortable="false" query="false" width="100"></t:dgCol>
			<t:dgCol title="本月达标评价" field="waterEvaluate"
				dictionary="waterEvaluate" sortable="false" query="false"
				width="100"></t:dgCol>
			<t:dgCol title="本月项目及倍数" field="projectMultiples" sortable="false"
				query="false" width="100"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
			<t:dgDelOpt title="删除"
				url="slRiverLakeController.do?delEntityWQ&id={id}"
				urlclass="ace_button" urlStyle="background-color:#ec4758;"
				urlfont="fa-trash-o" />
			<t:dgToolBar icon="icon-add" title="common.add" id="addEntityId"
				url="slRiverLakeController.do?addorupdateEntityWQ"
				funname="addEntity"></t:dgToolBar>
			<t:dgToolBar icon="icon-edit" title="common.edit"
				url="slRiverLakeController.do?addorupdateEntityWQ"
				funname="updateEntity"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script type="text/javascript">
  
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
