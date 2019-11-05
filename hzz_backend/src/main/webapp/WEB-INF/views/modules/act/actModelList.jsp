<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="actModelList" checkbox="false" pagination="true" fitColumns="true" title="模型管理"
                    sortName="createDate" actionUrl="/act/model.do?dataGrid" idField="id" fit="true"
                    queryMode="group">
            <t:dgCol title="模型分类" field="category" dictionary="processCategory" query="true" queryMode="single" width="100"></t:dgCol>
            <t:dgCol title="模型ID" field="id" width="140"></t:dgCol>
            <t:dgCol title="模型标识" field="key" width="120"></t:dgCol>
            <t:dgCol title="模型名称" field="name" width="120"></t:dgCol>
            <t:dgCol title="版本号" field="version" formatterjs="formatVersionFun" width="50"></t:dgCol>
            <t:dgCol title="创建时间" field="createTime" formatterjs="treeFormater" width="120"></t:dgCol>
            <t:dgCol title="最后更新时间" field="lastUpdateTime" formatterjs="treeFormater" width="100"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="120"></t:dgCol>
            <t:dgFunOpt title="编辑" urlclass="ace_button" urlfont="fa-edit" funname="editoperation(id)"></t:dgFunOpt>
            <t:dgConfOpt title="部署" url="model.do?deploy&id={id}" message="确认部署?" urlclass="ace_button" urlfont="fa-toggle-on"/>
            <t:dgFunOpt title="导出" urlclass="ace_button" urlfont="icon-putout" funname="exportModel(id)"></t:dgFunOpt>
            <t:dgDelOpt title="删除" url="model.do?delete&id={id}" urlclass="ace_button" urlfont="fa-trash-o" />

            <t:dgToolBar title="创建模板" icon="icon-add" funname="createModel"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript">
    function createModel() {
        $.dialog({
            content: "url:/act/model.do?create",
            lock : true,
            title:"添加流程模型",
            opacity : 0.3,
            width:900,
            height:500,
            cache:false,
            ok: function(){
                iframe = this.iframe.contentWindow;
                iframe.goForm();
                location.reload();
                return false;
            },
            cancelVal: '关闭',
            cancel: true /*为true等价于function(){}*/
        });
    }

    function editoperation(id) {
        window.open("${webRoot}/act/process-editor/modeler.jsp?modelId="+id,"_blank");
    }
    
    function exportModel(id) {
        // $.get("actModel/export.do", {id: id});
        window.open("${webRoot}/act/model/export.do?id="+id, "_blank")
    }

    function formatVersionFun(version,row,index){
        return "V: "+version
    }
</script>