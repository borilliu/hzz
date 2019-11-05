<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="riverLakeChiefList" checkbox="false" pagination="true" fitColumns="true" title="河湖长名录" sortName="createDate" actionUrl="riverLakeChiefController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="河长姓名"  field="name"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="河长级别"  field="level.name"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="河长类型"  field="typeName"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="行政区划"  field="division.divisionName"  queryMode="single"  formatterjs="treeFormater" width="120"></t:dgCol>
   <t:dgCol title="河长职位"  field="duty"  queryMode="single"  formatterjs="treeFormater" width="120"></t:dgCol>
   <t:dgCol title="河长账户"  field="account.userName"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="riverLakeChiefController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="riverLakeChiefController.do?goAdd" funname="add"></t:dgToolBar>
	<t:dgToolBar title="编辑" icon="icon-edit" url="riverLakeChiefController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="riverLakeChiefController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="riverLakeChiefController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'riverLakeChiefController.do?upload', "riverLakeChiefList");
}

//导出
function ExportXls() {
	JeecgExcelExport("riverLakeChiefController.do?exportXls","riverLakeChiefList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("riverLakeChiefController.do?exportXlsByT","riverLakeChiefList");
}

 </script>
