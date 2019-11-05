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
<form id="modelForm" action="/act/model/save.do" method="post" target="_blank">
	<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">
					流程分类:
				</label>
			</td>
			<td class="value">
				<t:dictSelect field="category"  type="select" typeGroupCode="processCategory" defaultVal="0" hasLabel="false" title="流程分类"></t:dictSelect>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">流程分类</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					模块名称:
				</label>
			</td>
			<td class="value">
				<input id="name" name="name" type="text" maxlength="32" style="width: 150px" class="inputxt" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">模块名称</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					模块标识:
				</label>
			</td>
			<td class="value">
				<input id="key" name="key" type="text" maxlength="32" style="width: 150px" class="inputxt" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">模块标识</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					模块描述:
				</label>
			</td>
			<td class="value">
				<textarea id="description" name="description" class="text-area" ></textarea>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">模块描述</label>
			</td>
		</tr>
	</table>
</form>
</body>
