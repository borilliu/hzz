<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>河湖长名录</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
     <link href="/plug-in/hplus/css/plugins/chosen/chosen.css" rel="stylesheet">
     <link href="/plug-in/hplus/css/style.css" rel="stylesheet">
     <script src="/plug-in/hplus/js/plugins/chosen/chosen.jquery.js"></script>
  <script type="text/javascript">
      var selectedLevel = "${riverLakeChiefPage.level.code}";
      var selectedType = "${riverLakeChiefPage.type.code}";
  //编写自定义JS代码
  $(function () {
      $("#depart").prev().hide();

	  $('#type').combotree({
		  url: 'slUserTypeController.do?combotree',
		  width: 155
	  });

      $('#division').combotree({
          url: 'slDivisionController.do?divisionTree',
          width: 155
      });

      $('#depart').combotree({
          url: 'departController.do?setPFunction',
          width: 155
      });

  });
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="riverLakeChiefController.do?doUpdate" >
					<input id="id" name="id" type="hidden" value="${riverLakeChiefPage.id}"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								河长账户:
							</label>
						</td>
						<td class="value">
                            <input id="user" name="account.userName" type="text" value="${riverLakeChiefPage.account.userName}" maxlength="32" style="width: 150px" class="inputxt" ignore="ignore" disabled="disabled"/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">河长账户</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								*河长姓名:
							</label>
						</td>
						<td class="value">
                            <input id="name" name="name" type="text" value="${riverLakeChiefPage.name}" maxlength="32" style="width: 150px" class="inputxt" datatype="*"/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">河长姓名</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								河长级别:
							</label>
						</td>
						<td class="value">
							<t:dictSelect type="select" field="level.code" typeGroupCode="river_office_level" datatype="*"></t:dictSelect>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">河长级别</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								河长类型:
							</label>
						</td>
						<td class="value">
							<t:dictSelect type="select" field="type" typeGroupCode="river_duty_user_type" datatype="*"></t:dictSelect>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">河长类型</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								行政区划:
							</label>
						</td>
						<td class="value">
                            <input id="division" name="division.code" value="${riverLakeChiefPage.division.code}">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">行政区划</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								河长部门:
							</label>
						</td>
						<td class="value">
                            <input id="depart" name="depart.id" type="text" value="${riverLakeChiefPage.depart.id}" readonly="readonly" class="inputxt" <%--datatype="*"--%>/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">河长部门</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								河长职位:
							</label>
						</td>
						<td class="value">
                            <input id="duty" name="duty" type="text" value="${riverLakeChiefPage.duty}" maxlength="32" style="width: 150px" class="inputxt" ignore="ignore"/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">河长职位</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								手机号码:
							</label>
						</td>
						<td class="value">
                            <input id="phone" name="phone" type="text" value="${riverLakeChiefPage.phone}" maxlength="32" style="width: 150px" class="inputxt"
                                   ignore="ignore"/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">手机号码</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								邮箱:
							</label>
						</td>
						<td class="value">
                            <input id="email" name="email" type="email" value="${riverLakeChiefPage.email}" maxlength="32" style="width: 150px" class="inputxt"
                                   ignore="ignore"/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">邮箱</label>
						</td>
					</tr>
				
			</table>
		</t:formvalid>
 </body>
