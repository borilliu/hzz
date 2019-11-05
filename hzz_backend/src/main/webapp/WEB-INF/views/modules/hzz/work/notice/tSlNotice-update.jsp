<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>公告管理信息表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tSlNoticeController.do?doUpdate" >
					<input id="id" name="id" type="hidden" value="${tSlNoticePage.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								公告标题:
							</label>
						</td>
						<td class="value">
						    <input id="title" name="title" type="text" maxlength="100" style="width: 150px" class="inputxt"  datatype="*" ignore="checked"  value='${tSlNoticePage.title}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">公告标题</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								公告文号:
							</label>
						</td>
						<td class="value">
						    <input id="docNo" name="docNo" type="text" maxlength="32" style="width: 150px" class="inputxt"  ignore="ignore"  value='${tSlNoticePage.docNo}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">公告文号</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								分类名称标识:
							</label>
						</td>
						<td class="value">
									<t:dictSelect field="groupCode" type="list"  datatype="n"  typeGroupCode="notic_class_name"   defaultVal="${tSlNoticePage.groupCode}" hasLabel="false"  title="分类名称标识" ></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">分类名称标识</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								公开形式标识:
							</label>
						</td>
						<td class="value">
									<t:dictSelect field="openWay" type="list"  datatype="*" typeGroupCode="notic_open_way"   defaultVal="${tSlNoticePage.openWay}" hasLabel="false"  title="公开形式标识" ></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">公开形式标识</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								公开范围标识:
							</label>
						</td>
						<td class="value">
									<t:dictSelect field="openScope" type="list"  datatype="*" typeGroupCode="notic_open_scope"   defaultVal="${tSlNoticePage.openScope}" hasLabel="false"  title="公开范围标识" ></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">公开范围标识</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								公开时限标识:
							</label>
						</td>
						<td class="value">
									<t:dictSelect field="openTimeLimit" type="list"  datatype="*" typeGroupCode="notice_time_limit"   defaultVal="${tSlNoticePage.openTimeLimit}" hasLabel="false"  title="公开时限标识" ></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">公开时限标识</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								公开程序:
							</label>
						</td>
						<td class="value">
						    <input id="openProcedure" name="openProcedure" type="text" maxlength="100" style="width: 150px" class="inputxt"  ignore="ignore"  value='${tSlNoticePage.openProcedure}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">公开程序</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								发布机构编码:
							</label>
						</td>
						<td class="value">
						    <input id="publishOrgCode" name="publishOrgCode" type="text" maxlength="32" style="width: 150px" class="inputxt"  ignore="ignore"  value='${tSlNoticePage.publishOrgCode}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">发布机构编码</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								发布机构名称:
							</label>
						</td>
						<td class="value">
						    <input id="publishOrgName" name="publishOrgName" type="text" maxlength="50" style="width: 150px" class="inputxt"  ignore="ignore"  value='${tSlNoticePage.publishOrgName}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">发布机构名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								发布日期:
							</label>
						</td>
						<td class="value">
									  <input id="publishDate" name="publishDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()"  ignore="ignore" value='<fmt:formatDate value='${tSlNoticePage.publishDate}' type="date" pattern="yyyy-MM-dd"/>'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">发布日期</label>
						</td>
					</tr>
				
					<tr>
						<td align="right">
							<label class="Validform_label">
								内容描述:
							</label>
						</td>
						<td class="value" >
						  	 	<textarea id="contentDescribe" style="height:auto;width:95%;" class="inputxt" rows="6" name="contentDescribe"  datatype="*" ignore="checked" >${tSlNoticePage.contentDescribe}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">内容描述</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								公开内容:
							</label>
						</td>
						<td class="value" >
									<script type="text/javascript"  charset="utf-8" src="plug-in/ueditor/ueditor.config.js"></script>
									<script type="text/javascript"  charset="utf-8" src="plug-in/ueditor/ueditor.all.min.js"></script>
                                <textarea name="publishContent" id="publishContent" style="width: 650px;height:300px">${tSlNoticePage.publishContent }</textarea>

                                <script type="text/javascript">
							        var publishContent_editor = UE.getEditor('publishContent');
								    </script>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">公开内容</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								备注:
							</label>
						</td>
						<td class="value" >
						  	 	<textarea id="remarks" style="height:auto;width:95%;" class="inputxt" rows="6" name="remarks"  ignore="ignore" >${tSlNoticePage.remarks}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
