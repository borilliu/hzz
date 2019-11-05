<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/8/16
  Time: 15:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="/static/js/modules/act/act-task.js"></script>

<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="waitHandleList" checkbox="false" pagination="true" fitColumns="true" title="流程管理"
                    actionUrl="/riverLakePatrolController.do?taskList" idField="taskId" fit="true"
                    queryMode="group">
            <t:dgCol title="任务id" field="id" width="90" hidden="true"></t:dgCol>
            <t:dgCol title="任务id" field="taskId" width="90" hidden="true"></t:dgCol>
            <t:dgCol title="标题" field="vars.map.title" query="true" queryMode="single" width="90"></t:dgCol>
            <t:dgCol title="状态" field="status" dictionary="problem_status" query="true" queryMode="single"
                     width="90"></t:dgCol>
            <t:dgCol title="当前环节" field="taskName" width="50" formatterjs="readbpmnXml"></t:dgCol>
            <t:dgCol title="流程名称" field="procDef.name" width="90"></t:dgCol>
            <t:dgCol title="流程版本" field="procDef.version" width="120"></t:dgCol>
            <t:dgCol title="创建时间" field="task.createTime" width="50"></t:dgCol>
            <t:dgCol title="taskDefKey" field="taskDefKey" width="50"></t:dgCol>
            <t:dgCol title="procDefKey" field="procDefKey" width="50"></t:dgCol>
            <t:dgCol title="procInsId" field="procInsId" width="50"></t:dgCol>
            <t:dgCol title="procDefId" field="procDefId" width="50"></t:dgCol>
            <t:dgCol title="assignee" field="assignee" width="50" hidden="true"></t:dgCol>
            <t:dgCol title="taskExecutionId" field="taskExecutionId" width="50" hidden="true"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="120"></t:dgCol>
            <t:dgConfOpt exp="assignee#empty#true" title="签收" url="riverLakePatrolController/task/claim/{taskId}.do"
                         message="确认签收?" urlclass="ace_button" urlfont="fa-toggle-on"/>
            <t:dgConfOpt exp="assignee#empty#false" title="上报" url="riverLakePatrolController/task/submit/{taskId}.do"
                         message="确认上报?" urlclass="ace_button" urlfont="fa-toggle-on"/>
            <t:dgFunOpt exp="assignee#empty#false" title="处理" urlclass="ace_button" urlfont="fa-edit"
                        funname="handleDialog"></t:dgFunOpt>
            <t:dgDelOpt exp="taskExecutionId#empty#true" url="" title="删除" urlclass="ace_button"
                        urlfont="fa-edit"></t:dgDelOpt>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript">
    function szqm1(taskId, taskName, taskDefKey, procInsId, procDefId, status,index) {
        debugger
        var row = $('#waitHandleList').datagrid('getData').rows[index];
        createwindow('问题交办', 'riverLakePatrolController/form.do?taskId='+taskId+'&taskName='+taskName+'&taskDefKey='+taskDefKey+'&procInsId='+procInsId+'&procDefId='+procDefId+'&status='+status,500,400);
    }

    function readbpmnXml(taskName, row, index) {
        return '<a target="_blank" href="${webRoot}/act/diagram-viewer/index.html?processDefinitionId=' + row.procDefId + '&processInstanceId=' + row.procInsId + '">' + taskName + '</a>'
    }
</script>
