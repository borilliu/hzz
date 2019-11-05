<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true" style="padding: 0px;">
	<div region="center" style="padding: 0px; border: 0px;">
		<t:datagrid name="slDivisionList" checkbox="false" pagination="true"
			treegrid="true" fitColumns="true" title="行政区划" sortName="createDate"
			actionUrl="slDivisionController.do?divisiongrid" idField="id"
			fit="true" queryMode="group">
			<t:dgCol title="区划名称" field="name" treefield="text"
				queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="区划编码" field="code" treefield="id"
				queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="区划排序" field="sort"
				treefield="fieldMap.sort" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="区划备注" field="remarks"
				treefield="fieldMap.remarks" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
			<t:dgDelOpt title="删除" url="slDivisionController.do?goDel&id={id}"
				urlclass="ace_button" urlStyle="background-color:#ec4758;"
				urlfont="fa-trash-o" />
		</t:datagrid>
		<div id="departListtb" style="padding: 3px; height: 25px">
			<div style="float: left;">
				<a href="#" class="easyui-linkbutton" plain="true" icon="icon-add"
					onclick="addDivision()">行政区划录入</a> <a href="#"
					class="easyui-linkbutton" plain="true" icon="icon-edit"
					onclick="update('<t:mutiLang langKey="common.edit.param" langArg="base.division"/>','slDivisionController.do?goUpdate','slDivisionList','680px','450px')">行政区划编辑</a>
				<!-- //update--end--author:zhangjiaqiang Date:20170112 for:增加排序功能 
                <a href="#" class="easyui-linkbutton" plain="true" icon="icon-put" onclick="ImportXls()"><t:mutiLang langKey="excelImport" langArg="common.department"/></a>
                -->
				<a href="#" class="easyui-linkbutton" plain="true"
					icon="icon-putout" onclick="ImportXls()">导入</a> <a href="#"
					class="easyui-linkbutton" plain="true" icon="icon-putout"
					onclick="ExportXls()">导出</a> <a href="#" class="easyui-linkbutton"
					plain="true" icon="icon-put" onclick="ExportXlsByT()">模板下载</a>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
 $(document).ready(function(){
		$("#slDivisionList").treegrid({
 				 onExpand : function(row){
 					var children = $("#slDivisionList").treegrid('getChildren',row.id);
 					 if(children.length<=0){
 					 	row.leaf=true;
 					 	$("#slDivisionList").treegrid('refresh', row.id);
 					 }
 				}
 		});
 });
 

 function addDivision() {
     var id = "";
     var rowsData = $('#slDivisionList').datagrid('getSelections');
     if (rowsData.length == 1) {
         id = rowsData[0].id;
     }
     var url = "slDivisionController.do?goAdd&id=" + id;

     add('<t:mutiLang langKey="common.add.param" langArg="base.division"/>', url, "slDivisionList","660px","480px");
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
	location.href = "../../../../../export/template/divisionTemplate.xls";
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
