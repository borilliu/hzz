<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title>删除流程</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<script type="text/javascript">
		function goForm(processInstanceId) {
			$("#procInsId").val(processInstanceId)
			$("#modelForm").submit();
			// 等待一秒在关闭窗口, 否则提交不成功
			setTimeout(function() {
				frameElement.api.close();
			}, 1000);
		}
	</script>
</head>
<body>
<form id="modelForm" action="act/process/deleteProcIns.do" method="post">
	<input type="hidden" id="procInsId" name="procInsId" />
	<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">
					删除原因:
				</label>
			</td>
			<td class="value">
				<textarea id="reason" name="reason" class="text-area" ></textarea>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">删除原因</label>
			</td>
		</tr>
	</table>
</form>
</body>
