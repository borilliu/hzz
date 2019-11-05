<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="contentList" checkbox="true" pagination="true" fitColumns="true" title="公文管理信息报" sortName="createDate" actionUrl="contentController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="内容标题"  field="title"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="分类名称"  field="groupCode"  query="true"  queryMode="single"  dictionary="content_class_name"  width="120"></t:dgCol>
   <t:dgCol title="发布来源"  field="publishSources"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="发布时间"  field="publishDate"  formatter="yyyy-MM-dd"  query="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="发布单位编码"  field="publishOrgCode"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="发布单位名称"  field="publishOrgName"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="附件"  field="accessory"  queryMode="single"  downloadName="附件下载"  width="500"></t:dgCol>
   <t:dgCol title="标题图片"  field="titleImage"  hidden="true"  queryMode="single"  image="true" imageSize="50,50"  width="500"></t:dgCol>
   <t:dgCol title="发布内容"  field="publishContent"  hidden="true"  queryMode="single"  width="500"></t:dgCol>
   <t:dgCol title="备注"  field="remarks"  hidden="true"  queryMode="single"  width="500"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="contentController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="contentController.do?goAdd" funname="add"></t:dgToolBar>
	<t:dgToolBar title="编辑" icon="icon-edit" url="contentController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="contentController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="contentController.do?goUpdate" funname="detail"></t:dgToolBar>
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
	openuploadwin('Excel导入', 'contentController.do?upload', "contentList");
}

//导出
function ExportXls() {
	JeecgExcelExport("contentController.do?exportXls","contentList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("contentController.do?exportXlsByT","contentList");
}

 </script>
