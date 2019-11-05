<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:0px;border:0px">
		<t:datagrid name="actModelList" checkbox="false" pagination="true" fitColumns="true" title="流程管理"
					actionUrl="/act/process.do?dataGrid" idField="id" fit="true"
					queryMode="group">
			<t:dgCol title="流程分类" field="processDefinition.category" dictionary="processCategory" query="true" queryMode="single" width="90"></t:dgCol>
			<t:dgCol title="流程ID" field="id" width="140"></t:dgCol>
			<t:dgCol title="processId" field="processId" width="140" hidden="true"></t:dgCol>
			<t:dgCol title="deploymentId" field="deploymentId" width="140" hidden="true"></t:dgCol>
			<t:dgCol title="流程标识" field="processDefinition.key" width="90"></t:dgCol>
			<t:dgCol title="流程名称" field="processDefinition.name" width="120"></t:dgCol>
			<t:dgCol title="流程版本" field="processDefinition.version" width="50" formatterjs="formatVersionFun"></t:dgCol>
			<t:dgCol title="部署时间" field="deployment.deploymentTime" width="50"></t:dgCol>
			<t:dgCol title="流程xml" field="processDefinition.resourceName" width="120" formatterjs="readbpmnXml"></t:dgCol>
			<t:dgCol title="流程图片" field="processDefinition.diagramResourceName" width="100" formatterjs="readbpmnPng"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="190"></t:dgCol>
			<t:dgConfOpt title="激活" url="process/update/active.do?procDefId={id}" message="确认要激活吗？" urlclass="ace_button" urlfont="fa-toggle-on"/>
			<t:dgConfOpt title="挂起" url="process/update/suspend.do?procDefId={id}" message="确认要挂起吗？" urlclass="ace_button" urlfont="fa-toggle-on"/>
			<t:dgConfOpt title="转换为模型" url="process/convert/toModel.do?procDefId={id}" message="确认要转换为模型吗？" urlclass="ace_button" urlfont="fa-toggle-on"/>
			<t:dgDelOpt title="删除" url="process/delete.do?deploymentId={deploymentId}" urlclass="ace_button" urlfont="fa-trash-o" />

			<t:dgToolBar title="部署流程" icon="icon-add" funname="deployProcess"></t:dgToolBar>
			<t:dgToolBar title="运行中的流程" icon="icon-add" funname="runningProcess"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script type="text/javascript">
	function runningProcess() {
		addOneTab( '运行中流程', '/act/process/running.do')
	}

	function deployProcess() {
		addOneTab( '流程部署', '/act/process/deploy.do')
	}

	function formatVersionFun(version,row,index){
		return "V: "+version
	}

	function readbpmnXml(resourceName, row, index) {
		<%--return '<a target="_blank" href="${webRoot}/act/process/resource/read.do?procDefId='+row.processId+'&resType=xml"><span style="color:#68a4e8;font-size: 16px;">' + resourceName + '</span></a>'--%>
		return '<a target="_blank" href="${webRoot}/act/process/resource/read.do?procDefId='+row.processId+'&resType=xml">' + resourceName + '</a>'
	}

	function readbpmnPng(resourceName, row, index) {
		<%--return '<a target="_blank" href="${webRoot}/act/process/resource/read.do?procDefId='+row.processId+'&resType=image"><span style="color:#68a4e8;font-size: 16px;">'+resourceName+'</span></a>'--%>
		return '<a target="_blank" href="${webRoot}/act/process/resource/read.do?procDefId='+row.processId+'&resType=image">'+resourceName+'</a>'
	}
</script>
