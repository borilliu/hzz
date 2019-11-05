<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
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
		layout="table" action="slRiverLakeController.do?doComplaintCheck">
		<input id="id" name="id" type="hidden" value="${entity.id }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td class="value">处理时间</td>
				<td class="value">内容</td>
				<td class="value">备注</td>
			</tr>
			<c:forEach var="process" items="${processList}">
				<tr>
					<td class="value"><fmt:formatDate
							value="${process.processDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td class="value">${process.processMemo}</td>
					<td class="value">${process.approvalMemo}</td>
				</tr>
			</c:forEach>
		</table>
	</t:formvalid>
</body>