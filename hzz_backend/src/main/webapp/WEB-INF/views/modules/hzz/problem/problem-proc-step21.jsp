<%@ page language="java" import="java.util.*"
         contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>jeecg_demo</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script type="text/javascript">
        //编写自定义JS代码
    </script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password"
             layout="table" action="/riverLakePatrolController/task/handled.do">
    <input id="taskId" name="taskId" value="${act.taskId}" type="hidden">
    <input id="procInsId" name="procInsId" value="${act.procInsId}" type="hidden">
    <table style="width: 320px;" cellpadding="0" cellspacing="1" class="formtable">
<%--        <tr>--%>
<%--            <td align="right">--%>
<%--                <label class="Validform_label"> 审查通过:</label>--%>
<%--            </td>--%>
<%--            <td class="value">--%>
<%--                <t:dictSelect field="status" type="radio" typeGroupCode="sf_yn" defaultVal="Y" hasLabel="false" title="状态"></t:dictSelect>--%>
<%--                <span class="Validform_checktip"></span>--%>
<%--                <label class="Validform_label" style="display: none;">审查通过</label></td>--%>
<%--        </tr>--%>
        <tr>
            <td align="right">
                <label class="Validform_label"> 处理详情:</label>
            </td>
            <td class="value">
                <textarea name="content" rows="6" cols="36"></textarea>
                <span class="Validform_checktip"></span>
            </td>
        </tr>
    </table>
</t:formvalid>
</body>