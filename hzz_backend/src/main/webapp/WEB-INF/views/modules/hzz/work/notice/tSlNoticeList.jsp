<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tSlNoticeList" checkbox="true" pagination="true" fitColumns="true" title="公告管理信息表" sortName="createDate" actionUrl="tSlNoticeController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="文号"  field="docNo"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="标题"  field="title"  query="true"  queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="分类名称"  field="groupCode"  query="true"  queryMode="single"  dictionary="notic_class_name"  width="120"></t:dgCol>
   <t:dgCol title="创建人"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="发布机构编码"  field="publishOrgCode"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="发布机构名称"  field="publishOrgName"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="发布日期"  field="publishDate"  formatter="yyyy-MM-dd"  query="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="公开形式"  field="openWay"  queryMode="single"  dictionary="notic_open_way"  width="120"></t:dgCol>
   <t:dgCol title="公开范围"  field="openScope"  queryMode="single"  dictionary="notic_open_scope"  width="120"></t:dgCol>
   <t:dgCol title="公开时限"  field="openTimeLimit"  queryMode="single"  dictionary="notice_time_limit"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tSlNoticeController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="tSlNoticeController.do?goAdd" funname="add"></t:dgToolBar>
	<t:dgToolBar title="编辑" icon="icon-edit" url="tSlNoticeController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tSlNoticeController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tSlNoticeController.do?goUpdate" funname="detail"></t:dgToolBar>
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
	openuploadwin('Excel导入', 'tSlNoticeController.do?upload', "tSlNoticeList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tSlNoticeController.do?exportXls","tSlNoticeList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tSlNoticeController.do?exportXlsByT","tSlNoticeList");
}

 </script>
