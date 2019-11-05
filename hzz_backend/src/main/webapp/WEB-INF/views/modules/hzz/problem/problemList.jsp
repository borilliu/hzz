<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="problemList" checkbox="false" pagination="true" fitColumns="true" title="问题信息表" sortName="reportTime" actionUrl="problemController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="问题编号"  field="code"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="问题类型"  field="problemDict.typename"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="地区编号"  field="division.divisionName"  queryMode="single"  formatterjs="treeFormater" width="120"></t:dgCol>
   <t:dgCol title="相关河长"  field="user.userName"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="相关河段"  field="reach"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="地址"  field="address"  queryMode="single"  width="300"></t:dgCol>
   <t:dgCol title="上报时间"  field="reportTime"  formatter="yyyy-MM-dd hh:mm:ss"  query="true"  queryMode="group"  width="120"></t:dgCol>
<%--   <t:dgCol title="上报事件"  field="eventDict.typename"  query="true"  queryMode="single"  width="120"></t:dgCol>--%>
   <t:dgCol title="问题上报图片"  field="photo"  queryMode="single"  image="true" imageSize="50,50"  width="120"></t:dgCol>
   <t:dgCol title="问题描述"  field="remarks"  queryMode="single"  width="400"></t:dgCol>
   <t:dgCol title="创建人id"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd hh:mm:ss"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人id"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate"  formatter="yyyy-MM-dd hh:mm:ss"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="problemController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="problemController.do?goAdd" funname="add"></t:dgToolBar>
	<t:dgToolBar title="编辑" icon="icon-edit" url="problemController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="problemController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="problemController.do?goUpdate" funname="detail"></t:dgToolBar>
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
	openuploadwin('Excel导入', 'problemController.do?upload', "problemList");
}

//导出
function ExportXls() {
	JeecgExcelExport("problemController.do?exportXls","problemList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("problemController.do?exportXlsByT","problemList");
}

 </script>
