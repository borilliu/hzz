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
		layout="table" action="slRiverLakeController.do?doComplaintReturn">
		<input id="id" name="id" type="hidden" value="${entity.id }">
		<table style="width: 100%" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label">
						群众满意度: </label></td>
				<td class="value"><t:dictSelect field="massSatisP" type="radio"
						typeGroupCode="massSatisP" defaultVal="10" hasLabel="false"
						title="群众满意度" datatype="*"></t:dictSelect> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">审查通过</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"
					style="white-space: nowrap;"> 回访时间: </label></td>
				<td class="value"><input type="text" class="Wdate"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					style="width: 150px" datatype="*" id="returnDate" name="returnDate"
					value="<fmt:formatDate value='${curDate}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>">
					<span class="Validform_checktip"></span> <label
					class="Validform_label" style="display: none;">回访时间</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>