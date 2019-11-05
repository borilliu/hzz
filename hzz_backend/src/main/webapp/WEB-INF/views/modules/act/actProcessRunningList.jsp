<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:0px;border:0px">
		<t:datagrid name="actModelList" checkbox="false" pagination="true" fitColumns="true" title="流程管理"
					actionUrl="/act/process/running.do?dataGrid" idField="id" fit="true"
					queryMode="group">
			<t:dgCol title="执行ID" field="id" query="true" queryMode="single" width="90"></t:dgCol>
			<t:dgCol title="流程实例ID" field="processInstanceId" width="140"></t:dgCol>
			<t:dgCol title="流程定义ID" field="processDefinitionId" width="90"></t:dgCol>
			<t:dgCol title="当前环节" field="activityId" width="120"></t:dgCol>
			<t:dgCol title="是否挂起" field="suspended"  width="50"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="190"></t:dgCol>
			<t:dgFunOpt title="删除" urlclass="ace_button" urlfont="icon-putout" funname="deleteProcIns(processInstanceId)"></t:dgFunOpt>
		</t:datagrid>
	</div>
</div>
<script type="text/javascript">
	function deleteProcIns(processInstanceId) {
		$.dialog({
			content: "/act/process/deleteReson.do",
			lock : true,
			title:"删除流程",
			opacity : 0.3,
			width:900,
			height:500,
			cache:false,
			ok: function(){
				iframe = this.iframe.contentWindow;
				iframe.goForm(processInstanceId);
				location.reload();
				return false;
			},
			cancelVal: '关闭',
			cancel: true /*为true等价于function(){}*/
		});
	}
</script>
