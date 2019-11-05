<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="workReportList" checkbox="true" pagination="true" fitColumns="true" title="工作简报信息表" sortName="createDate" actionUrl="workReportController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="简报标题"  field="title"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="简报批次"  field="batch"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="发布日期"  field="publishDate"  formatter="yyyy-MM-dd"  query="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="发布来源"  field="publishSources"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="发布单位编码"  field="publishOrgCode"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="发布单位"  field="publishOrgName"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="附件"  field="accessory"  queryMode="single"  downloadName="附件下载"  width="120"></t:dgCol>
   <t:dgCol title="发布内容"  field="publishContent"  hidden="true"  queryMode="single"  width="500"></t:dgCol>
   <t:dgCol title="备注"  field="remarks"  hidden="true"  queryMode="single"  width="500"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="workReportController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="workReportController.do?goAdd" funname="add"></t:dgToolBar>
	<t:dgToolBar title="编辑" icon="icon-edit" url="workReportController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="workReportController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="workReportController.do?goUpdate" funname="detail"></t:dgToolBar>
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
	openuploadwin('Excel导入', 'workReportController.do?upload', "workReportList");
}

//导出
function ExportXls() {
	JeecgExcelExport("workReportController.do?exportXls","workReportList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("workReportController.do?exportXlsByT","workReportList");
}

 </script>
