<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title>河湖长名录</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<script type="text/javascript">
		function goForm() {
			$("#modelForm").submit();
			// 等待一秒在关闭窗口, 否则提交不成功
			setTimeout(function() {
				frameElement.api.close();
			}, 1000);
		}
	</script>
</head>
<body>
<form id="modelForm" action="act/process/deploy.do" method="post" enctype="multipart/form-data" target="_blank">
	<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">
					流程分类:
				</label>
			</td>
			<td class="value">
				<t:dictSelect field="category"  type="select" typeGroupCode="processCategory" defaultVal="category1" hasLabel="false" title="流程分类"></t:dictSelect>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">流程分类</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					流程文件:
				</label>
			</td>
			<td class="value">
				<input id="file" name="file" type="file" maxlength="32" style="width: 150px" class="inputxt" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">支持文件格式：zip、bar、bpmn、bpmn20.xml</label>
			</td>
		</tr>
		<tr>
			<td>
				<div class="form-actions">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交"/>
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</td>
		</tr>
	</table>
</form>
</body>
